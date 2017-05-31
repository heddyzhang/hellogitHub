package com.batch.CP5P110;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.batch.ComUtil;
import com.batch.Cp5Exception;
import com.batch.IComConst;
import com.batch.LogFormatter;
import com.batch.MessageInfo;
import com.batch.SystemInfo;
import com.batch.SystemStatus;

/**
 * DB更新クラス
 * @author chou
 *
 */
public class DbUpdate {

	// PreparedStatementオブジェクト
	private PreparedStatement mStmt			= null;
	// ResultSetオブジェクト
	private ResultSet  mRs						= null;
	// テーブル名
	private String mTblName 					= "";
	// 処理区分
	private String mShoriKbn 					= null;
	// 最大レコードID
	private int mMaxRecid = 0;
	// ログ出力クラス(DEBUG 用)
	private static final Logger logger = Logger.getLogger(DbUpdate.class);

	/**
	 * コンストラクタ
	 * @param shoriKbn
	 */
	public DbUpdate(String shoriKbn){

		logger.debug("コンストラクタ DbUpdate start");
		// 処理区分
		mShoriKbn = shoriKbn;

		// テーブル名の設定
		if (IComConst.SHORI_KBN_JP.equals(shoriKbn)) {
			// 処理区分(国内)
			mTblName = SystemInfo.TBLNAME1;

		} else 	if (IComConst.SHORI_KBN_FP.equals(shoriKbn)) {
			// 処理区分(外国)
			mTblName = SystemInfo.TBLNAME2;

		} else {
			// 処理区分(非特許)
			mTblName = SystemInfo.TBLNAME3;
		}

		logger.debug("コンストラクタ DbUpdate end");
	}

	/**
	 * DB更新メソッド
	 * @param objCon
	 * @param fileCmsPath
	 * @param cmtLmtVal
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public int update(Connection objCon, String fileCmsPath, String cmtLmtVal) throws IOException, Cp5Exception, Exception {

		// 変数初期化
		LogFormatter objLogFm = null;
		String msg = "";
		// 重複カウント用クエリ
		String cntDupQuery = "";
		// DB削除用クエリ
		String delQuery = "";
		// DB挿入用クエリ
		String insQuery = "";
		String inRec = "";
		int isn;
		int dupCnt = 0;
		int rowCnt = 0;
		int comitCnt = 0;
		int cmtLmtKen = Integer.parseInt(cmtLmtVal);
		int jobCode = SystemStatus.NORMAL;
		BufferedReader br = null;
		String[] cmsData = null;
		PreparedStatement stmt	= null;

		logger.debug("メッソド update start");
		try {

			// クエリの設定
			if (IComConst.SHORI_KBN_JP.equals(mShoriKbn)) {

				// 重複カウント用クエリ(国内)
				cntDupQuery = SystemInfo.QUERY1_02;
				// DB削除用クエリ(国内)
				delQuery = SystemInfo.QUERY1_03;
				// DB挿入用クエリ(国内)
				insQuery = SystemInfo.QUERY1_04;

			} else 	if (IComConst.SHORI_KBN_FP.equals(mShoriKbn)) {

				// 重複カウント用クエリ(国外)
				cntDupQuery = SystemInfo.QUERY2_02;
				// DB削除用クエリ(国外)
				delQuery = SystemInfo.QUERY2_03;
				// DB挿入用クエリ(国外)
				insQuery = SystemInfo.QUERY2_04;

			} else {

				// 重複カウント用クエリ(非特許)
				cntDupQuery = SystemInfo.QUERY3_02;
				// DB削除用クエリ(非特許)
				delQuery = SystemInfo.QUERY3_03;
				// DB挿入用クエリ(非特許)
				insQuery = SystemInfo.QUERY3_04;
			}

			// オブジェクト生成
			objLogFm = new LogFormatter();

			// UTF-8文字コード指定で読み込む
			FileInputStream file = new FileInputStream(fileCmsPath);
			br = new BufferedReader(new InputStreamReader(file, IComConst.FILE_OUTPUT_ENCODING));

			// 共通検索用情報を1レコードづつ読み込む
			while ((inRec = br.readLine()) != null) {

				logger.debug("引数共通検索用情報の1行dataは" + inRec);
				// 入力レコードをタブ区切りで切り出す
				cmsData = inRec.split(SystemInfo.CMS_FILE_DELIMITER, -1);

				// 行の累加
				rowCnt++;

				// ISN
				isn = Integer.parseInt(cmsData[0]);

				// 重複件数を取得
				dupCnt = countDuplicate(objCon, cntDupQuery, isn);

				// 重複件数≧2件の場合
				if (dupCnt >= 2) {
					// メッセージを出力
					msg = String.format(MessageInfo.CP5J210031, mTblName, isn, dupCnt);
					System.out.println(objLogFm.format("CP5J210031-E", msg));
					// ステータス8を設定
					jobCode = SystemStatus.ERROR_8;

					msg = String.format(MessageInfo.CP5J210022, rowCnt);
					System.out.println(objLogFm.format("CP5J210022-E", msg));
					continue;

				// 重複件数＝1件の場合
				} else if (dupCnt == 1) {
					// 重複レコードを削除
					delete(objCon, delQuery, isn);
				}

				// PreparedStatementオブジェクトを生成
				stmt = createStatement(objCon, cmsData, rowCnt, insQuery);

				// DB挿入を行う
				insert(stmt, isn);

				// メッセージ(CP5J210021)を標準出力
				msg = String.format(MessageInfo.CP5J210021, rowCnt);
				System.out.println(objLogFm.format("CP5J210021-I", msg));

				// コミット件数累加
				comitCnt++;

				logger.debug("DBコミット待ち件数は" + String.valueOf(comitCnt % cmtLmtKen));
				if (comitCnt % cmtLmtKen == 0) {

					// DBコミットを行い
					objCon.commit();
					logger.debug("DBコミットを実行しました。");

					// メッセージ(CP5J210023)を標準出力
					msg = String.format(MessageInfo.CP5J210023, rowCnt, comitCnt);
					System.out.println(objLogFm.format("CP5J210023-I", msg));
				}
			}

			logger.debug("DBコミット単位は" + cmtLmtVal);
			// DBコミット待ち件数が1件以上の場合
			logger.debug("DBコミット待ち件数は" + String.valueOf(comitCnt % cmtLmtKen));
			if (comitCnt % cmtLmtKen >= 1) {

				// DBコミットを行い
				objCon.commit();
				logger.debug("DBコミットを実行しました。");

				// メッセージ(CP5J210023)を標準出力
				msg = String.format(MessageInfo.CP5J210023, rowCnt, comitCnt);
				System.out.println(objLogFm.format("CP5J210023-I", msg));
			}

		} catch (Cp5Exception ex1) {
			throw new Cp5Exception(ex1.getStatus(), ex1.getMessageId(), ex1.getMessage());

		} catch (SQLException ex2) {
			msg = String.format(MessageInfo.CP5J210024, rowCnt, ex2.getErrorCode());
			throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J210024-E", msg);

		} catch (IOException ex3) {
			msg = String.format(MessageInfo.CP5J210011,  fileCmsPath);
			throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J210011-E", msg);

		} catch (Exception ex4) {
			logger.debug("Exception" + ex4.getMessage());
			throw ex4;
		} finally {

			if (br != null) {
				// リソース解放
				br.close();
				logger.debug("br対象のcloseメソッドを実行");
			}
		}

		logger.debug("メッソド updateの戻り値jobCodeは" + jobCode);
		logger.debug("メッソド update end");
		return jobCode;
	}

	/**
	 * 蓄積確認パラメータ出力メソッド
	 * @param fileChkPrmPath
	 * @throws Cp5Exception,
	 */
	public void outFileChkPrm(Connection objCon, String fileChkPrmPath) throws Cp5Exception, Exception {


		int rowCnt = 1;
		String msg = "";
		PrintWriter fileChkPrmPw = null;
		String[] scd = null;
		String[] scdNm = null;
		// 蓄積確認パラメータレコード数(種別コード単位)
		int chkPrmKen =  2 * SystemInfo.CHKPRM_NUM;
		int i,j,k;

		logger.debug("メッソド outFileChkPrm start");
		// 蓄積確認パラメータ用変数の設定
		// 処理区分国内の場合
		if (IComConst.SHORI_KBN_JP.equals(mShoriKbn)) {

			// 種別コード(蓄積確認パラメータ(国内)出力用)
			scd = IComConst.SCD_JP_ARRAY;
			// 種別名称(蓄積確認パラメータ(国内)出力用)
			scdNm = IComConst.SCD_NM_JP_ARRAY;

		// 処理区分国外の場合
		} else if (IComConst.SHORI_KBN_FP.equals(mShoriKbn)){

			// 種別コード(蓄積確認パラメータ(国外)出力用)
			scd =IComConst.SCD_FP_ARRAY;
			// 種別名称(蓄積確認パラメータ(国外)出力用)
			scdNm =IComConst.SCD_NM_FP_ARRAY;

		// 処理区分非特許の場合
		} else {

			// 種別コード(蓄積確認パラメータ(非特許)出力用)
			scd = IComConst.SCD_NP_ARRAY;
			// 種別名称(蓄積確認パラメータ(非特許)出力用)
			scdNm = IComConst.SCD_NM_NP_ARRAY;
		}
		logger.debug("種別コードは" + Arrays.toString(scd));

		try {

			// PrintWriterオブジェクト(蓄積確認パラメータファイル)
			fileChkPrmPw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileChkPrmPath), IComConst.FILE_OUTPUT_ENCODING)));

			// 種別コードループ分を出力
			for (i = 0; i < scd.length; i++) {

				// 種別コード(出力用)
				String scdOut = scd[i];
				// 種別コード名称(出力用)
				String scdNmOut = scdNm[i];

				// 種別コードより蓄積確認件数取得
				int cnt = getCntByScd(objCon, scdOut, mMaxRecid);
				// 蓄積確認の件数が0件の場合
				if (cnt == 0) {
					// 空行を蓄積確認パラメータファイルに出力
					for (j = 0; j < chkPrmKen; j++) {
						fileChkPrmPw.println(String.format("%s,%s,%s,%s", scdNmOut, j + 1, scdOut,""));

						// 出力件数
						rowCnt++;
					}
				} else {

					// 同一文献種別名称内での行番号
					int dnoNo = 0;
					// 種別コードに関連する蓄積確認上位10件情報を取得
					List<UpdateDnoBean> outDnoList1 = getTopByScd(objCon, scdOut, mMaxRecid);

					// 蓄積確認の件数が蓄積確認パラメータレコード数(種別コード単位)より以下の場合
					if (cnt <= chkPrmKen) {

						// 最小側から昇順に蓄積確認パラメータファイルに出力
						for (UpdateDnoBean outBean : outDnoList1) {
							fileChkPrmPw.println(String.format("%s,%s,%s,%s", scdNmOut, ++dnoNo, scdOut, outBean.getIsn()));

							// 出力件数
							rowCnt++;
						}
						// 残りレコードは空行を出力
						for (j = 0; j < chkPrmKen - cnt ; j++) {
							fileChkPrmPw.println(String.format("%s,%s,%s,%s", scdNmOut, ++dnoNo, scdOut, ""));

							// 出力件数
							rowCnt++;
						}

					// 蓄積確認の件数が蓄積確認パラメータレコード数(種別コード単位)より大きいの場合
					} else{

						// 蓄積文献番号の最小単位数(昇順)を出力
						for (j = 0; j < SystemInfo.CHKPRM_NUM; j++) {
							fileChkPrmPw.println(String.format("%s,%s,%s,%s", scdNmOut, ++dnoNo, scdOut, outDnoList1.get(j).getIsn()));

							// 出力件数
							rowCnt++;
						}

						// 種別コードに関連する蓄積確認下位5件情報(降順)を取得
						List<UpdateDnoBean> outDnoList2= getLastByScd(objCon, scdOut, mMaxRecid);

						// 蓄積文献番号の最大単位数(昇順)を出力
						for (k = SystemInfo.CHKPRM_NUM - 1; k >= 0; k--) {
							fileChkPrmPw.println(String.format("%s,%s,%s,%s", scdNmOut, ++dnoNo, scdOut, outDnoList2.get(k).getIsn()));

							// 出力件数
							rowCnt++;
						}
					}
				}
			}

		} catch (IOException ex1) {
			msg = String.format(MessageInfo.CP5J210020,  rowCnt);
			throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J210020-E", msg);

		} catch (Cp5Exception ex2) {
			throw new Cp5Exception(ex2.getStatus(), ex2.getMessageId(), ex2.getMessage());

		} catch (Exception ex3) {
			ex3.printStackTrace();
			throw ex3;
		} finally {

			// リソース解放
			if (fileChkPrmPw != null) {
				fileChkPrmPw.close();
				logger.debug("fileChkPrmPw対象のcloseメソッドを実行");
			}
		}
		logger.debug("メッソド outFileChkPrm end");
	}

	/**
	 * PreparedStatementを生成(DB挿入用)
	 * @param stmt
	 * @param data
	 * @param rowCnt
	 * @throws SQLException
	 */
	public PreparedStatement createStatement(Connection con, String[] data, int rowCnt, String query) throws SQLException, Exception {

		logger.debug("メッソド createStatement start");
		try {

			logger.debug("登録SQL文:" + query);
			PreparedStatement stmt = con.prepareStatement(query);

			// 最大レコードID+処理レコード行
			stmt.setInt(1, mMaxRecid + rowCnt);
			//logger.debug("stmtの1番目は" + String.valueOf(mMaxRecid + rowCnt));

			logger.debug("引数共通検索用情報の1行dataは" + Arrays.toString(data));
			// ISN
			int mIsn = Integer.parseInt(data[0]);
			stmt.setInt(2, mIsn);
			logger.debug("stmtの2番目は" + mIsn);

			// 処理区分国内の場合
			if (IComConst.SHORI_KBN_JP.equals(mShoriKbn)) {

				// 出願番号
				String mAi = data[IComConst.AI_IDX_JP];
				stmt.setString(3, mAi);
				//logger.debug("stmtの3番目は" + mAi);

				// 代表文献番号(公開／公告番号)
				String mKy = data[IComConst.KY_IDX_JP];
				stmt.setString(4, mKy);
				//logger.debug("stmtの4番目は" + mKy);

				// 文献番号
				String mKz = data[IComConst.KZ_IDX_JP];
				stmt.setString(5, mKz);
				//logger.debug("stmtの5番目は" + mKz);

				// 公告番号・明細番号・JIS番号
				String mPn = data[IComConst.PN_IDX_JP];
				stmt.setString(6, mPn);
				//logger.debug("stmtの6番目は" + mPn);

				// 資料型
				String mSr = data[IComConst.SR_IDX_JP];
				stmt.setString(7, mSr);
				//logger.debug("stmtの7番目は" + mSr);

				// 出願日
				String mAd = data[IComConst.AD_IDX_JP];
				stmt.setString(8, mAd);
				//logger.debug("stmtの8番目は" + mAd);

				// 公知日
				String mHk = data[IComConst.HK_IDX_JP];
				stmt.setString(9, mHk);
				//ogger.debug("stmtの9番目は" + mHk);

				// 公開日
				String mKd = data[IComConst.KD_IDX_JP];
				stmt.setString(10, mKd);
				//logger.debug("stmtの10番目は" + mKd);

				// 公告日
				String mPd = data[IComConst.PD_IDX_JP];
				stmt.setString(11, mPd);
				//logger.debug("stmtの11番目は" + mPd);

				// 登録日
				String mRd = data[IComConst.RD1_IDX_JP];
				stmt.setString(12, mRd);
				//logger.debug("stmtの12番目は" + mRd);

				// 登録公報発行日
				String mRj = data[IComConst.RJ_IDX_JP];
				stmt.setString(13, mRj);
				//logger.debug("stmtの13番目は" + mRj);

				// 公表日
				String mTpnd = data[IComConst.TPND_IDX_JP];
				stmt.setString(14, mTpnd);
				//logger.debug("stmtの14番目は" + mTpnd);

				// 再公表発行日
				String mRtpnd = data[IComConst.RTPND_IDX_JP];
				stmt.setString(15, mRtpnd);
				//logger.debug("stmtの15番目は" + mRtpnd);

				// 審判番号
				List<String> aplnLst = new ArrayList<String>();
				for (int i = IComConst.APLN_IDX_JP; i < IComConst.APLN_IDX_JP + IComConst.DEFAULT_MAX_REPEAT_NUM; i++) {
					if (ComUtil.isEmpty(data[i])) break;
					aplnLst.add(ComUtil.convEmptyToNull(data[i]));
				}
				String[] aplnArr = (String[]) aplnLst.toArray(new String[0]);
				if (aplnArr.length ==0) aplnArr = null;
				stmt.setObject(16, aplnArr);
				//hirdb is not support createArrayOf method???
				//stmt.setArray(16, con.createArrayOf("VARCHAR", aplnArr));
				//logger.debug("stmtの16番目は" + Arrays.toString(aplnArr));

				// 国際公開日
				String mPctd = data[IComConst.PCTD_IDX_JP];
				stmt.setString(17, mPctd);
				//logger.debug("stmtの17番目は" + mPctd);

				// 申請人識別番号
				List<String> idnLst = new ArrayList<String>();
				for (int i = IComConst.IDN_IDX_JP; i < IComConst.IDN_IDX_JP + IComConst.DEFAULT_MAX_REPEAT_NUM; i++) {
					if (ComUtil.isEmpty(data[i])) break;
					idnLst.add(ComUtil.convEmptyToNull(data[i]));
				}
				String[] idnArr = (String[]) idnLst.toArray(new String[0]);
				if (idnArr.length ==0) idnArr = null;
				stmt.setObject(18, idnArr);
				//logger.debug("stmtの18番目は" + Arrays.toString(idnArr));

				// 優先権主張番号
				List<String> prinLst = new ArrayList<String>();
				for (int i = IComConst.PRIN_IDX_JP; i < IComConst.PRIN_IDX_JP + IComConst.DEFAULT_MAX_REPEAT_NUM; i++) {
					if (ComUtil.isEmpty(data[i])) break;
					prinLst.add(ComUtil.convEmptyToNull(data[i]));
				}
				String[] prinArr = (String[]) prinLst.toArray(new String[0]);
				if (prinArr.length ==0) prinArr = null;
				stmt.setObject(19, prinArr);
				//logger.debug("stmtの19番目は" + Arrays.toString(prinArr));

				// 発明名称
				String mDa = data[IComConst.DA_IDX_JP];
				stmt.setString(20, mDa);
				//logger.debug("stmtの20番目は" + mDa);

				// 出願人
				List<String> goLst = new ArrayList<String>();
				for (int i = IComConst.GO1_IDX_JP; i < IComConst.GO1_IDX_JP + IComConst.DEFAULT_MAX_REPEAT_NUM; i++) {
					if (ComUtil.isEmpty(data[i])) break;
					goLst.add(ComUtil.convEmptyToNull(data[i]));
				}
				String[] goArr = (String[]) goLst.toArray(new String[0]);
				if (goArr.length ==0) goArr = null;
				stmt.setObject(21, goArr);
				//logger.debug("stmtの21番目は" + Arrays.toString(goArr));

				// FI
				List<String> fiLst = new ArrayList<String>();
				for (int i = IComConst.FI_IDX_JP; i < IComConst.FI_IDX_JP + IComConst.FI_MAX_REPEAT_NUM_JP; i++) {
					if (ComUtil.isEmpty(data[i])) break;
					fiLst.add(ComUtil.convEmptyToNull(data[i]));
				}
				String[] fiArr = (String[]) fiLst.toArray(new String[0]);
				if (fiArr.length ==0) fiArr = null;
				stmt.setObject(22, fiArr);
				//logger.debug("stmtの22番目は" + Arrays.toString(fiArr));

				// 発明番号
				List<String> noLst = new ArrayList<String>();
				for (int i = IComConst.NO1_IDX_JP; i < IComConst.NO1_IDX_JP + IComConst.DEFAULT_MAX_REPEAT_NUM; i++) {
					if (ComUtil.isEmpty(data[i])) break;
					noLst.add(ComUtil.convEmptyToNull(data[i]));
				}
				String[] noArr = (String[]) noLst.toArray(new String[0]);
				if (noArr.length ==0) noArr = null;
				stmt.setObject(23, noArr);
				//logger.debug("stmtの23番目は" + Arrays.toString(noArr));

				// 発明繰り返し数
				int hatsuLen = noArr.length;
				// タームA
				List<String> aaLst = new ArrayList<String>();
				for (int i = IComConst.AA_IDX_JP; i < IComConst.AA_IDX_JP + hatsuLen; i++) {
					aaLst.add(ComUtil.convEmptyToNull(data[i]));
				}
				// タームA長さは発明繰り返し数と同じ
				String[] aaArr = (String[]) aaLst.toArray(new String[0]);
				if (aaArr.length ==0) aaArr = null;
				stmt.setObject(24, aaArr);
				logger.debug("stmtの24番目は" + Arrays.toString(aaArr));

				// タームB
				List<String> bbLst = new ArrayList<String>();
				for (int i = IComConst.BB_IDX_JP; i < IComConst.BB_IDX_JP + hatsuLen; i++) {
					bbLst.add(ComUtil.convEmptyToNull(data[i]));
				}
				// タームB長さは発明繰り返し数と同じ
				String[] bbArr = (String[]) bbLst.toArray(new String[0]);
				if (bbArr.length ==0) bbArr = null;
				stmt.setObject(25, bbArr);
				logger.debug("stmtの25番目は" + Arrays.toString(bbArr));

				// タームC
				List<String> ccLst = new ArrayList<String>();
				for (int i = IComConst.CC_IDX_JP; i < IComConst.CC_IDX_JP + hatsuLen; i++) {
					ccLst.add(ComUtil.convEmptyToNull(data[i]));
				}
				// タームC長さは発明繰り返し数と同じ
				String[] ccArr = (String[]) ccLst.toArray(new String[0]);
				if (ccArr.length ==0) ccArr = null;
				stmt.setObject(26, ccArr);
				logger.debug("stmtの26番目は" + Arrays.toString(ccArr));

				// タームD
				List<String> ddLst = new ArrayList<String>();
				for (int i = IComConst.DD_IDX_JP; i < IComConst.DD_IDX_JP + hatsuLen; i++) {
					ddLst.add(ComUtil.convEmptyToNull(data[i]));
				}
				// タームD長さは発明繰り返し数と同じ
				String[] ddArr = (String[]) ddLst.toArray(new String[0]);
				if (ddArr.length ==0) ddArr = null;
				stmt.setObject(27, ddArr);
				logger.debug("stmtの27番目は" + Arrays.toString(ddArr));

				// 合金フリーワード
				List<String> fwLst = new ArrayList<String>();
				for (int i = IComConst.FW_IDX_JP; i < IComConst.FW_IDX_JP + hatsuLen; i++) {
					fwLst.add(ComUtil.convEmptyToNull(data[i]));
				}
				// 合金フリーワード長さは発明繰り返し数と同じ
				String[] fwArr = (String[]) fwLst.toArray(new String[0]);
				if (fwArr.length ==0) fwArr = null;
				stmt.setObject(28, fwArr);
				logger.debug("stmtの28番目は" + Arrays.toString(fwArr));

				// 種別コード(代表文献番号)
				String mScdKy = data[IComConst.SCD_KY_IDX_JP];
				stmt.setString(29, mScdKy);
				//logger.debug("stmtの29番目は" + mScdKy);

				// 種別コード(文献番号)
				String mScdKz = data[IComConst.SCD_KZ_IDX_JP];
				stmt.setString(30, mScdKz);
				//logger.debug("stmtの30番目は" + mScdKz);

				// 検索コード
				String mSltCd = data[IComConst.SLTCD_IDX_JP];
				stmt.setString(31, mSltCd);
				//logger.debug("stmtの31番目は" + mSltCd);

				// 処理区分国外の場合
			} else if (IComConst.SHORI_KBN_FP.equals(mShoriKbn)) {

				// 代表文献番号(国コード)
				stmt.setString(3, data[IComConst.KY1_IDX_FP]);
				//logger.debug("stmtの3番目は" + ky1);

				// 代表文献番号(種別)
				stmt.setString(4, data[IComConst.KY2_IDX_FP]);
				//logger.debug("stmtの4番目は" + ky2);

				// 代表文献番号(公開／公告番号)
				stmt.setString(5, data[IComConst.KY3_IDX_FP]);
				//logger.debug("stmtの5番目は" + ky3);

				// 文献番号
				List<String> kzLst = new ArrayList<String>();
				for (int i = IComConst.KZ_IDX_FP; i < IComConst.KZ_IDX_FP + IComConst.KZ_MAX_REPEAT_NUM; i++) {
					if (ComUtil.isEmpty(data[i])) break;
					kzLst.add(ComUtil.convEmptyToNull(data[i]));
				}
				String[] kzArr = (String[]) kzLst.toArray(new String[0]);
				if (kzArr.length ==0) kzArr = null;
				stmt.setObject(6, kzArr);
				//logger.debug("stmtの6番目は" + Arrays.toString(kzArr));

				// 出願日
				stmt.setString(7, data[IComConst.AD_IDX_FP]);
				//logger.debug("stmtの7番目は" + ad);

				// 公知日
				stmt.setString(8, data[IComConst.HK_IDX_FP]);
				//logger.debug("stmtの8番目は" + hk);

				// FI
				List<String> fiLst = new ArrayList<String>();
				for (int i = IComConst.FI_IDX_FP; i < IComConst.FI_IDX_FP + IComConst.FI_MAX_REPEAT_NUM; i++) {
					if (ComUtil.isEmpty(data[i])) break;
					fiLst.add(ComUtil.convEmptyToNull(data[i]));
				}
				String[] fiArr = (String[]) fiLst.toArray(new String[0]);
				if (fiArr.length ==0) fiArr = null;
				stmt.setObject(9, fiArr);
				//logger.debug("stmtの9番目は" + Arrays.toString(fiArr));

				// Fターム
				List<String> ftLst = new ArrayList<String>();
				for (int i = IComConst.FT_IDX_FP; i < IComConst.FT_IDX_FP + IComConst.FT_MAX_REPEAT_NUM; i++) {
					if (ComUtil.isEmpty(data[i])) break;
					ftLst.add(ComUtil.convEmptyToNull(data[i]));
				}
				String[] ftArr = (String[]) ftLst.toArray(new String[0]);
				if (ftArr.length ==0) ftArr = null;
				stmt.setObject(10, ftArr);
				//logger.debug("stmtの10番目は" + Arrays.toString(ftArr));

				// IPC
				List<String> icLst = new ArrayList<String>();
				for (int i = IComConst.IC_IDX_FP; i < IComConst.IC_IDX_FP + IComConst.IC_MAX_REPEAT_NUM; i++) {
					if (ComUtil.isEmpty(data[i])) break;
					icLst.add(ComUtil.convEmptyToNull(data[i]));
				}
				String[] icArr = (String[]) icLst.toArray(new String[0]);
				if (icArr.length ==0) icArr = null;
				stmt.setObject(11, icArr);
				//logger.debug("stmtの11番目は" + Arrays.toString(icArr));

				// 種別コード(代表文献番号)
				stmt.setString(12, data[IComConst.SCD_KY_IDX_FP]);
				//logger.debug("stmtの12番目は" + scdKy);

				// 検索コード
				stmt.setString(13, data[IComConst.SLTCD_IDX_FP]);
				//logger.debug("stmtの13番目は" + sltCd);

			// 処理区分非特許の場合
			} else {

				// 代表文献番号(非特許種別)
				stmt.setString(3, data[IComConst.KY1_IDX_NP]);
				//logger.debug("stmtの3番目は" + data[IComConst.KY1_IDX_NP]);

				// 代表文献番号(文献種別)
				stmt.setString(4, data[IComConst.KY2_IDX_NP]);
				//logger.debug("stmtの4番目は" + data[IComConst.KY2_IDX_NP]);

				// 代表文献番号(書籍番号)
				stmt.setString(5, data[IComConst.KY3_IDX_NP]);
				//logger.debug("stmtの5番目は" + data[IComConst.KY3_IDX_NP]);

				// 代表文献番号(記事番号)
				stmt.setString(6, data[IComConst.KY4_IDX_NP]);
				//logger.debug("stmtの6番目は" + data[IComConst.KY4_IDX_NP]);

				// 公知日
				stmt.setString(7, data[IComConst.HK_IDX_NP]);
				//logger.debug("stmtの7番目は" + data[IComConst.HK_IDX_NP]);

				// 発明名称／書籍タイトル
				stmt.setString(8, data[IComConst.HI_IDX_NP]);
				//logger.debug("stmtの8番目は" + data[IComConst.HI_IDX_NP]);

				// Fターム
				List<String> ftLst = new ArrayList<String>();
				for (int i = IComConst.FT_IDX_NP; i < IComConst.FT_IDX_NP + IComConst.FT_MAX_REPEAT_NUM; i++) {
					if (ComUtil.isEmpty(data[i]))
						break;
					ftLst.add(ComUtil.convEmptyToNull(data[i]));
				}
				String[] ftArr = (String[]) ftLst.toArray(new String[0]);
				if (ftArr.length ==0) ftArr = null;
				stmt.setObject(9, ftArr);
				//logger.debug("stmtの9番目は" + Arrays.toString(ftArr));

				// 文献タイトル
				stmt.setString(10, data[IComConst.TL_IDX_NP]);
				//logger.debug("stmtの10番目は" + data[IComConst.TL_IDX_NP]);

				// 種別コード(代表文献番号)
				stmt.setString(11, data[IComConst.SCD_KY_IDX_NP]);
				//logger.debug("stmtの11番目は" + data[IComConst.SCD_KY_IDX_NP]);

				// 検索コード
				stmt.setString(12, data[IComConst.SLTCD_IDX_NP]);
				//logger.debug("stmtの12番目は" + data[IComConst.SLTCD_IDX_NP]);

			}

			logger.debug("メッソド createStatement end");
			return stmt;

		} catch (SQLException e1) {
			logger.debug("SQL State:" + e1.getSQLState());
			throw e1;
		} catch (Exception e2) {
			logger.debug("Exception" + e2.getMessage());
			throw e2;
		}

	}

	/**
	 * 最大レコードID取得メソッド
	 * @param Con
	 * @throws Cp5Exception
	 * @throws SQLException
	 */
	public void getRecid(Connection Con) throws Cp5Exception, SQLException, Exception {

		String query = "";
		String msg = "";

		logger.debug("メッソド getRecid start");
		try {
			// クエリを取得
			if (IComConst.SHORI_KBN_JP.equals(mShoriKbn)) {
				// 最大レコードID取得(国内)クエリ
				query = SystemInfo.QUERY1_01;

			} else 	if (IComConst.SHORI_KBN_FP.equals(mShoriKbn)) {
				// 最大レコードID取得(国外)クエリ
				query = SystemInfo.QUERY2_01;

			} else {
				// 最大レコードID取得(非特許)クエリ
				query = SystemInfo.QUERY3_01;
			}

			logger.debug("最大レコードID取得SQL文:" + query);
			// オブジェクトを生成
			mStmt = Con.prepareStatement(query);

			// ResultSet取得
			mRs = mStmt.executeQuery();
			logger.debug("最大レコードID検索executeQueryを実行");

			while(mRs.next()) {
				// 最大レコードIDを設定
				mMaxRecid = mRs.getInt(1);
			}
			logger.debug("最大レコードIDは" + String.valueOf(mMaxRecid));

		} catch (SQLException ex1) {
			// データベースエラー
			msg = String.format(MessageInfo.CP5J210010, ex1.getErrorCode());
			throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J210010-E", msg);

		} catch (Exception ex2) {
			throw ex2;

		} finally {
			// Resultset オブジェクトのクローズ
			if (mRs != null) {
				mRs.close();
				logger.debug("Resultset対象のcloseメソッドを実行");
			}

			// Statement オブジェクトのクローズ
			if (mStmt != null) {
				mStmt.close();
				logger.debug("Statement対象のcloseメソッドを実行");
			}

			logger.debug("メッソド getRecid end");
		}
	}

	/**
	 * 重複カウントメソッド
	 * @param Con
	 * @param query
	 * @param isn
	 * @return
	 * @throws Cp5Exception
	 * @throws SQLException
	 * @throws Exception
	 */
	public int countDuplicate(Connection Con, String query, int isn) throws Cp5Exception, Exception {

		String msg = "";
		// 重複件数
		int count = 0;

		logger.debug("メッソド countDuplicate start");
		try {

			logger.debug("重複件数取得SQL文:" + query);
			// オブジェクトを生成
			mStmt = Con.prepareStatement(query);
			// クエリのパラメタを設定
			mStmt.setInt(1, isn);
			logger.debug("パラメタISN:" + String.valueOf(isn));
			// ResultSet取得
			mRs = mStmt.executeQuery();
			logger.debug("重複件数検索executeQueryメソッドを実行");

			//フェッチ
			while (mRs.next()){
				// DB検索結果の1要素目(重複件数)を取得
				count = mRs.getInt(1);
			}

			logger.debug("重複件数は" + String.valueOf(count));
		} catch (SQLException ex1) {
			// データベースエラー
			msg = String.format(MessageInfo.CP5J210019, mTblName, isn, ex1.getErrorCode());
			throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J210019-E", msg);

		} catch (Exception ex2) {
			// 予期しない例外発生
			throw new Exception();
		} finally {
			// Resultset オブジェクトのクローズ
			if (mRs != null) {
				mRs.close();
				logger.debug("Resultset対象のcloseメソッドを実行");
			}
			// Statement オブジェクトのクローズ
			if (mStmt != null) {
				mStmt.close();
				logger.debug("Statement対象のcloseメソッドを実行");
			}
		}

		logger.debug("メッソド countDuplicate end");
		return count;
	}

	/**
	 * 削除メソッド
	 * @param Con
	 * @param querty
	 * @param isn
	 * @throws Cp5Exception
	 * @throws SQLException
	 * @throws Exception
	 */
	private void delete(Connection Con, String query, int isn) throws Cp5Exception, Exception {

		String msg             = "";
		LogFormatter objLogFm = null;

		logger.debug("メッソド delete start");
		try {
			// オブジェクト生成
			objLogFm = new LogFormatter();

			logger.debug("削除SQL文:" + query);
			// PreparedStatement オブジェクトの生成
			mStmt = Con.prepareStatement(query);
			// パラメタ1(引数のISN)
			mStmt.setInt(1, isn);
			logger.debug("パラメタISN:" + String.valueOf(isn));
			// DB削除を実行
			mStmt.execute();
			logger.debug("削除executeメソッドを実行");

			// メッセージ(CP5J210016)を標準出力
			msg = String.format(MessageInfo.CP5J210016, mTblName, isn);
			System.out.println(objLogFm.format("CP5J210016-I", msg));

		} catch (SQLException ex1) {
			// データベースエラー
			msg = String.format(MessageInfo.CP5J210029, mTblName, isn, ex1.getErrorCode());
			throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J210029-E", msg);

		} catch (Exception ex2) {
			// 予期しない例外発生
			throw ex2;
		} finally {
			// Statement オブジェクトのクローズ
			if (mStmt != null) {
				mStmt.close();
				logger.debug("Statement対象のcloseメソッドを実行");
			}
		}
		logger.debug("メッソド delete end");
	}

	/**
	 * 挿入メソッド
	 * @param stmt
	 * @throws Cp5Exception
	 */
	private void insert(PreparedStatement stmt, int isn) throws Cp5Exception, Exception {

		String msg             = "";
		LogFormatter objLogFm = null;

		logger.debug("メッソド insert start");
		try {
			// オブジェクト生成
			objLogFm = new LogFormatter();

			// 挿入SQL文を実行
			stmt.execute();
			logger.debug("登録executeメソッドを実行");

			// メッセージ(CP5J210016)を標準出力
			msg = String.format(MessageInfo.CP5J210014, mTblName, isn);
			System.out.println(objLogFm.format("CP5J210014-I", msg));

		} catch (SQLException ex1) {
			logger.debug(ex1.getMessage());
			// データベースエラー
			msg = String.format(MessageInfo.CP5J210015, mTblName, isn, ex1.getErrorCode());
			throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J210015-E", msg);

		} catch (Exception ex2) {
			// 予期しない例外発生
			throw ex2;
		}  finally {
			// Statement オブジェクトのクローズ
			if (mStmt != null) {
				mStmt.close();
				logger.debug("Statement対象のcloseメソッドを実行");
			}
		}
		logger.debug("メッソド insert end");
	}


	/**
	 * 蓄積確認件数取得
	 * @param Con
	 * @param scdKy
	 * @return
	 * @throws Cp5Exception
	 * @throws Exception
	 */
	public int getCntByScd(Connection Con, String scdKy, int maxRecId) throws Cp5Exception, Exception {

		String msg = "";
		// 蓄積確認件数
		int count = 0;
		// SQL文
		String query = "";

		logger.debug("メッソド getCntByScd start");
		try {

			// クエリを取得
			if (IComConst.SHORI_KBN_JP.equals(mShoriKbn)) {
				// 蓄積確認件数取得(国内)クエリ
				query = SystemInfo.QUERY1_05;

			} else 	if (IComConst.SHORI_KBN_FP.equals(mShoriKbn)) {
				// 蓄積確認件数取得(国外)クエリ
				query = SystemInfo.QUERY2_05;

			} else {
				// 蓄積確認件数取得(非特許)クエリ
				query = SystemInfo.QUERY3_05;
			}

			logger.debug("蓄積確認件数取得SQL文:" + query);

			// オブジェクトを生成
			mStmt = Con.prepareStatement(query);
			// クエリのパラメタを設定
			mStmt.setString(1, scdKy);
			mStmt.setInt(2, maxRecId);
			// 国内の場合
			if (IComConst.SHORI_KBN_JP.equals(mShoriKbn)) {
				mStmt.setString(3, scdKy);
				mStmt.setInt(4, maxRecId);
			}

			logger.debug("種別コード:" + scdKy);
			logger.debug("登録前最大レコードID:" + maxRecId);

			// ResultSet取得
			mRs = mStmt.executeQuery();

			//フェッチ
			while (mRs.next()){
				// DB検索結果の1要素目(蓄積確認件数)を取得
				count = mRs.getInt(1);
				logger.debug("蓄積確認件数は" + String.valueOf(count));
			}

		} catch (SQLException ex1) {
			// データベースエラー
			msg = String.format(MessageInfo.CP5J210041, mTblName, scdKy, ex1.getErrorCode());
			throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J210041-E", msg);

		} catch (Exception ex2) {
			// 予期しない例外発生
			throw new Exception();
		} finally {
			// Resultset オブジェクトのクローズ
			if (mRs != null) {
				mRs.close();
			}
			// Statement オブジェクトのクローズ
			if (mStmt != null) {
				mStmt.close();
			}
		}

		logger.debug("メッソド getCntByScd end");
		return count;
	}

	/**
	 * 蓄積確認上位10件取得
	 * @param Con
	 * @param scdKy
	 * @return
	 * @throws Cp5Exception
	 * @throws Exception
	 */
	public List<UpdateDnoBean> getTopByScd(Connection Con, String scdKy, int maxRecId) throws Cp5Exception, Exception {

		String msg = "";
		// SQL文
		String query = "";
		// 更新文献Beanクラス
		UpdateDnoBean objUpdateBean = null;
		// 更新文献情報の初期化
		List<UpdateDnoBean> mUpdateDnoList = new ArrayList<UpdateDnoBean>();

		logger.debug("メッソド getTopByScd start");
		try {

			// クエリを取得
			if (IComConst.SHORI_KBN_JP.equals(mShoriKbn)) {
				// 蓄積確認上位10件取得(国内)クエリ
				query = SystemInfo.QUERY1_06;

			} else 	if (IComConst.SHORI_KBN_FP.equals(mShoriKbn)) {
				// 蓄積確認上位10件取得(国外)クエリ
				query = SystemInfo.QUERY2_06;

			} else {
				// 蓄積確認上位10件取得(非特許)クエリ
				query = SystemInfo.QUERY3_06;
			}

			logger.debug("蓄積確認上位10件取得SQL文:" + query);

			// オブジェクトを生成
			mStmt = Con.prepareStatement(query);
			// クエリのパラメタを設定
			mStmt.setString(1, scdKy);
			mStmt.setInt(2, maxRecId);
			// 国内の場合
			if (IComConst.SHORI_KBN_JP.equals(mShoriKbn)) {
				mStmt.setString(3, scdKy);
				mStmt.setInt(4, maxRecId);
			}

			logger.debug("種別コード:" + scdKy);
			logger.debug("登録前最大レコードID:" + maxRecId);

			// ResultSet取得
			mRs = mStmt.executeQuery();

			//フェッチ
			while (mRs.next()){
				// 更新文献Beanクラスのオブジェクト1を生成
				objUpdateBean = new UpdateDnoBean();
				// ISN
				objUpdateBean.setIsn(mRs.getString(1));
				// 代表文献番号(公開／公告番号)
				objUpdateBean.setDno(mRs.getString(2));

				// 更新文献BeanのListにDB検索結果を設定する。
				mUpdateDnoList.add(objUpdateBean);
			}

			// 更新文献BeanListの内容を表示(DEBUG用)
			printUpdateDnoList(mUpdateDnoList);

		} catch (SQLException ex1) {
			// データベースエラー
			msg = String.format(MessageInfo.CP5J210042, mTblName, scdKy, ex1.getErrorCode());
			throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J210042-E", msg);

		} catch (Exception ex2) {
			// 予期しない例外発生
			throw new Exception();
		} finally {
			// Resultset オブジェクトのクローズ
			if (mRs != null) {
				mRs.close();
			}
			// Statement オブジェクトのクローズ
			if (mStmt != null) {
				mStmt.close();
			}
		}

		logger.debug("メッソド getTopByScd end");
		return mUpdateDnoList;
	}

	/**
	 * 蓄積確認下位5件取得
	 * @param Con
	 * @param scdKy
	 * @return
	 * @throws Cp5Exception
	 * @throws Exception
	 */
	public List<UpdateDnoBean> getLastByScd(Connection Con, String scdKy, int maxRecId) throws Cp5Exception, Exception {

		String msg = "";
		// SQL文
		String query = "";
		// 更新文献Beanクラス
		UpdateDnoBean objUpdateBean = null;
		// 更新文献情報の初期化
		List<UpdateDnoBean> mUpdateDnoList = new ArrayList<UpdateDnoBean>();

		logger.debug("メッソド getLastByScd start");
		try {

			// クエリを取得
			if (IComConst.SHORI_KBN_JP.equals(mShoriKbn)) {
				// 蓄積確認下位5件取得(国内)クエリ
				query = SystemInfo.QUERY1_07;

			} else 	if (IComConst.SHORI_KBN_FP.equals(mShoriKbn)) {
				// 蓄積確認下位5件取得(国外)クエリ
				query = SystemInfo.QUERY2_07;

			} else {
				// 蓄積確認下位5件取得(非特許)クエリ
				query = SystemInfo.QUERY3_07;
			}

			logger.debug("蓄積確認下位5件取得SQL文:" + query);

			// オブジェクトを生成
			mStmt = Con.prepareStatement(query);
			// クエリのパラメタを設定
			mStmt.setString(1, scdKy);
			mStmt.setInt(2, maxRecId);
			// 国内の場合
			if (IComConst.SHORI_KBN_JP.equals(mShoriKbn)) {
				mStmt.setString(3, scdKy);
				mStmt.setInt(4, maxRecId);
			}
			logger.debug("種別コード:" + scdKy);
			logger.debug("登録前最大レコードID:" + maxRecId);

			// ResultSet取得
			mRs = mStmt.executeQuery();

			//フェッチ
			while (mRs.next()){
				// 更新文献Beanクラスのオブジェクト1を生成
				objUpdateBean = new UpdateDnoBean();
				// ISN
				objUpdateBean.setIsn(mRs.getString(1));
				// 代表文献番号(公開／公告番号)
				objUpdateBean.setDno(mRs.getString(2));

				// 更新文献BeanのListにDB検索結果を設定する。
				mUpdateDnoList.add(objUpdateBean);
			}

			// 更新文献BeanListの内容を表示(DEBUG用)
			printUpdateDnoList(mUpdateDnoList);

		} catch (SQLException ex1) {
			// データベースエラー
			msg = String.format(MessageInfo.CP5J210043, mTblName, scdKy, ex1.getErrorCode());
			throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J210043-E", msg);

		} catch (Exception ex2) {
			// 予期しない例外発生
			throw new Exception();
		} finally {
			// Resultset オブジェクトのクローズ
			if (mRs != null) {
				mRs.close();
			}
			// Statement オブジェクトのクローズ
			if (mStmt != null) {
				mStmt.close();
			}
		}

		logger.debug("メッソド getLastByScd end");
		return mUpdateDnoList;
	}

	/**
	 * 更新文献BeanListの内容を表示(Debug用)
	 * @param updateDnoList
	 */
	private void printUpdateDnoList(List<UpdateDnoBean> updateDnoList) {
		logger.debug("method printUpdateDnoList start");
		for (int m = 0; m <= updateDnoList.size() - 1; m++) {
			logger.debug((m + 1) + "個目Isn is " + updateDnoList.get(m).getIsn());
			logger.debug((m + 1) + "個目Dno is " + updateDnoList.get(m).getDno());

		}
		logger.debug("method printUpdateDnoList end");
	}
}
