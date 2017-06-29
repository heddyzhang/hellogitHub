package com.batch.CP5P130;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.batch.ComUtil;
import com.batch.Cp5Exception;
import com.batch.IComConst;
import com.batch.MessageInfo;
import com.batch.SystemInfo;
import com.batch.SystemStatus;

/**
 * 定義情報と文蓄情報の保持とファイル出力を行うクラス。
 * @author chou
 *
 */
public class RangeInfo {

	private List<RangeDefineBean> mDefineList    = null;
	private List<RangeInfoBean>   mRangeInfoList = null;

	// ログ出力クラス(DEBUG 用)
	private final Logger logger = LoggerFactory.getLogger(RangeInfo.class);

	public RangeInfo(int maxScdNum) {

	}

	/**
	 * 文蓄情報作成定義設定メソッド
	 * @param fileName
	 * @throws Cp5Exception
	 * @throws Exception
	 */
	public void setDefine(String fileName) throws Cp5Exception, Exception {
		String   msg     = null;
		String   inRec   = null;
		String[] inItems = null;
		BufferedReader br = null;

		logger.debug("メソッドsetDefine start");
		try {
			// オブジェクト生成
			FileInputStream file = new FileInputStream(fileName);
			br = new BufferedReader(new InputStreamReader(file, IComConst.FILE_DEFINE_ENCODING));

			mDefineList = new ArrayList<RangeDefineBean>();
			while ((inRec = br.readLine()) != null) {
			    // 項目切り出し（カンマ毎）
				inItems = inRec.split(SystemInfo.DEF_FILE_DELIMITER, -1);

				// 文蓄情報作成定義取得
				RangeDefineBean defines = new RangeDefineBean();
				// 文献種別ID
				defines.setBid(inItems[0]);
				// 文献名称
				defines.setBnm(inItems[1]);
				// 種別コード
				defines.setScd(inItems[2]);
				// 国コード
				defines.setCtry(inItems[3]);
				// 文献種別
				defines.setKind(inItems[4]);

				mDefineList.add(defines);
			}

			// 出力用エリアを文蓄定義情報Listを表示DEBUG用
			printDefineInfo();
		} catch (IOException ex1) {
			// 入出力エラー
			msg = String.format(MessageInfo.CP5J510016, fileName);
			throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J510016-E", msg);
		} catch (Exception ex2) {
			// 予期しない例外発生
			throw new Exception();
		} finally {
			// リソース解放
			if (br != null) {
				br.close();
				logger.debug("br対象のcloseメソッドを実行");
			}
		}

		logger.debug("メソッドsetDefine end");
		// 正常終了
    	return;
	}

	/**
	 * 抽出文蓄情報設定メソッド
	 * @param objDbExtract1
	 * @param objDbExtract2
	 * @throws Exception
	 */
	public void setRangeInfo(DbExtract objDbExtract1, DbExtract objDbExtract2, DbExtract objDbExtract31,
					DbExtract objDbExtract32, DbExtract objDbExtract41, DbExtract objDbExtract42) throws Exception {

		int i, j;
		String scd1 = "";
		String scd2 = "";
		String scd3 = "";
		String scd4 = "";

		logger.debug("メソッドsetRangeInfo start");
		try {

			// 文蓄情報Bean型出力エリアリスト
			mRangeInfoList = new ArrayList<RangeInfoBean>();

			// 文蓄定義Bean型リスト毎に繰り返し
			for (i = 0; i < mDefineList.size(); i++) {

				// 文蓄情報Beanクラスを生成する
				RangeInfoBean matchItems = new RangeInfoBean();

				// 文蓄定義Bean型Listオブジェクトの種別コードを取得
				String scd = mDefineList.get(i).getScd();

				// 変数の初期化
				String dnoMin = "";
				String dnoMax = "";
				String isdateMax = "";
				long count = 0;
				boolean bFound = false;

				String tDnoMin = "";
				String tDnoMax = "";
				String tIsdateMax = "";

				// 共通検索一覧表示用情報(国内)管理DB抽出テーブルについて検索
				for (j = 0; j < objDbExtract1.getFetchNum(); j++) {

					scd1 = objDbExtract1.getScd(j);
					// 文蓄定義Bean型Listオブジェクトの種別コードと抽出テーブル一致する場合
					if (scd.equals(scd1)) {

						// 存在フラグ
						bFound = true;

						// 蓄積先頭番号(4バイト目～12バイト目)
						tDnoMin = objDbExtract1.getDnoMin(j).substring(3,12);
						// 蓄積最終番号(4バイト目～12バイト目)
						tDnoMax = objDbExtract1.getDnoMax(j).substring(3,12);
						// 公知日(年)
						tIsdateMax = objDbExtract1.getIsdateMax(j);

						// 複数存在する場合、一致するデータ中の最小値を設定
						//if (ComUtil.compareSmaller(tDnoMin, dnoMin)) {
						// 最小蓄積先頭番号
						dnoMin = tDnoMin;
						//}

						// 複数存在する場合、一致するデータ中の最大値を設定
						//if (ComUtil.compareBigger(tDnoMax, dnoMax)) {
						// 最大蓄積最終番号
						dnoMax = tDnoMax;
						//}

						// 複数存在する場合、一致するデータ中の最大値を設定
						//if (ComUtil.compareBigger(tIsdateMax, isdateMax)) {
						// 最大公知日(年)
						isdateMax = tIsdateMax;
						//}

						// 蓄積総件数の合計値
						count = count + Long.parseLong(objDbExtract1.getCount(j));
						logger.debug("種別コード(国内1)" + scd1 + "に対する蓄積総件数は" + count);
					}
				}

				// 共通検索一覧表示用情報(国内)管理DB抽出テーブルについて検索
				for (j = 0; j < objDbExtract2.getFetchNum(); j++) {

					scd2 = objDbExtract2.getScd(j);
					// 文蓄定義Bean型Listオブジェクトの種別コードと抽出テーブル一致する場合
					if (scd.equals(scd2)) {

						// 存在フラグ
						bFound = true;

						// 蓄積先頭番号(4バイト目～12バイト目)
						tDnoMin = objDbExtract2.getDnoMin(j).substring(3,12);
						logger.debug("種別コード(国内2)" + scd2 + "に対する蓄積先頭番号" + tDnoMin);
						// 蓄積最終番号(4バイト目～12バイト目)
						tDnoMax = objDbExtract2.getDnoMax(j).substring(3,12);
						logger.debug("種別コード(国内2)" + scd2 + "に対する蓄積最終番号" + tDnoMax);
						// 公知日(年)
						tIsdateMax = objDbExtract2.getIsdateMax(j);
						logger.debug("種別コード(国内2)" + scd2 + "に対する公知日(年)は" + tIsdateMax);

						// 複数存在する場合、一致するデータ中の最小値を設定
						if (ComUtil.compareSmaller(tDnoMin, dnoMin)) {
							// 最小蓄積先頭番号
							dnoMin = tDnoMin;
						}

						// 複数存在する場合、一致するデータ中の最大値を設定
						if (ComUtil.compareBigger(tDnoMax, dnoMax)) {
							// 最大蓄積最終番号
							dnoMax = tDnoMax;
						}

						// 複数存在する場合、一致するデータ中の最大値を設定
						if (ComUtil.compareBigger(tIsdateMax, isdateMax)) {
							// 最大公知日(年)
							isdateMax = tIsdateMax;
						}

						// 蓄積総件数の合計値
						count = count + Long.parseLong(objDbExtract2.getCount(j));
						logger.debug("種別コード(国内)" + scd2 + "に対する蓄積総件数は" + count);
					}
				}

				// 共通検索一覧表示用情報(外国)管理DB抽出テーブル①について検索
				for (j = 0; j < objDbExtract31.getFetchNum(); j++) {

					scd3 = objDbExtract31.getScd(j);
					// 文蓄定義Bean型Listオブジェクトの種別コードと抽出テーブル一致する場合
					if (scd.equals(scd3)) {

						// 存在フラグ
						bFound = true;

						// 蓄積先頭番号(1バイト目～9バイト目)
						dnoMin = objDbExtract31.getDnoMin(j).substring(0,9);
						// 蓄積最終番号(1バイト目～9バイト目)
						dnoMax = objDbExtract31.getDnoMax(j).substring(0,9);
						// 公知日(年)
						isdateMax = objDbExtract31.getIsdateMax(j);

						// 蓄積総件数
						count = Long.parseLong(objDbExtract31.getCount(j));
					}
				}

				// 共通検索一覧表示用情報(外国)管理DB抽出テーブル②について検索
				for (j = 0; j < objDbExtract32.getFetchNum(); j++) {

					scd3 = objDbExtract32.getScd(j);
					// 文蓄定義Bean型Listオブジェクトの種別コードと抽出テーブル一致する場合
					if (scd.equals(scd3)) {

						// 存在フラグ
						bFound = true;

						// 蓄積先頭番号(1バイト目～9バイト目)
						dnoMin = objDbExtract32.getDnoMin(j).substring(0,9);
						// 蓄積最終番号(1バイト目～9バイト目)
						dnoMax = objDbExtract32.getDnoMax(j).substring(0,9);
						// 公知日(年)
						isdateMax = objDbExtract32.getIsdateMax(j);

						// 蓄積総件数
						count = Long.parseLong(objDbExtract32.getCount(j));
					}
				}

				// 共通検索一覧表示用情報(非特許)管理DB抽出テーブル(N10以外)について検索
				for (j = 0; j < objDbExtract41.getFetchNum(); j++) {

					scd4 = objDbExtract41.getScd(j);
					// 文蓄定義Bean型Listオブジェクトの種別コードと抽出テーブル一致する場合
					if (scd.equals(scd4)) {

						// 存在フラグ
						bFound = true;

						// 蓄積先頭番号は代表文献番号(書籍番号)と最小代表文献番号(記事番号)を連結
						dnoMin = String.format("%s%s", objDbExtract41.getDnoMin(j), ComUtil.leftPadZero(objDbExtract41.getKy4Min(j), 3));
						// 蓄積最終番号は代表文献番号(書籍番号)と最大代表文献番号(記事番号)を連結
						dnoMax = String.format("%s%s", objDbExtract41.getDnoMax(j), ComUtil.leftPadZero(objDbExtract41.getKy4Max(j), 3));
						// 公知日(年)
						isdateMax = objDbExtract41.getIsdateMax(j);

						// 蓄積総件数
						count = Long.parseLong(objDbExtract41.getCount(j));
					}
				}

				// 共通検索一覧表示用情報(非特許)管理DB抽出テーブル(N10)について検索
				for (j = 0; j < objDbExtract42.getFetchNum(); j++) {

					scd4 = objDbExtract42.getScd(j);
					// 文蓄定義Bean型Listオブジェクトの種別コードと抽出テーブル一致する場合
					if (scd.equals(scd4)) {

						// 存在フラグ
						bFound = true;

						// 蓄積先頭番号は代表文献番号(書籍番号)
						dnoMin = objDbExtract42.getDnoMin(j);
						// 蓄積最終番号は代表文献番号(書籍番号)
						dnoMax = objDbExtract42.getDnoMax(j);
						// 公知日(年)
						isdateMax = objDbExtract42.getIsdateMax(j);

						// 蓄積総件数
						count = Long.parseLong(objDbExtract42.getCount(j));
					}
				}

				// 文蓄情報出力用エリアを作成
				// 文献種別ID
				matchItems.setBid(mDefineList.get(i).getBid());
				// 文献名称
				matchItems.setBnm(mDefineList.get(i).getBnm());
				// 国コード
				matchItems.setCtry(mDefineList.get(i).getCtry());
				// 文献種別
				matchItems.setKind(mDefineList.get(i).getKind());

				// 種別コード
				matchItems.setScd(scd);

				// 一致するデータが存在した場合
				if (bFound) {
					// 蓄積先頭番号
					matchItems.setDnoMin(dnoMin);
					// 蓄積最終番号
					matchItems.setDnoMax(dnoMax);
					// 公知日(年)
					matchItems.setIsdateMax(isdateMax);
					// 蓄積総件数
					matchItems.setCount(ComUtil.leftPadZero(String.valueOf(count), 8));
				} else {
					// 一致するデータが存在しない場合、未設定にする
					// 蓄積先頭番号
					matchItems.setDnoMin("");
					// 蓄積最終番号
					matchItems.setDnoMax("");
					// 公知日(年)
					matchItems.setIsdateMax("");
					// 蓄積総件数
					matchItems.setCount("");
				}

				// 出力用エリアを文蓄情報Bean型Listオブジェクトに追加する
				mRangeInfoList.add(matchItems);
			}
			// 出力用文蓄情報Bean型Listを表示(DEBUG用)
			printRangeInfo();

		} catch (Exception ex) {
			// 予期しない例外発生
			throw ex;
		}
		logger.debug("メソッドsetRangeInfo end");
		// 正常終了
		return;
	}

	/**
	 * 出力用文蓄情報Listを表示(DEBUG用)
	 * @throws Exception
	 */
	public void printRangeInfo() throws Exception {

		for (int i = 0; i < mRangeInfoList.size(); i++) {
			logger.debug("****DEBUG 文蓄情報Bean型List" + (i + 1) + "行目表示 ***");
			logger.debug("SCD      =" + mRangeInfoList.get(i).getScd());
			logger.debug("MIN(DNO) =" + mRangeInfoList.get(i).getDnoMin());
			logger.debug("MAX(DNO) =" + mRangeInfoList.get(i).getDnoMax());
			logger.debug("ISDATE   =" + mRangeInfoList.get(i).getIsdateMax());
			logger.debug("COUNT    =" + mRangeInfoList.get(i).getCount());
		}

		// 正常終了
		return;
	}

	/**
	 * 文蓄定義情報Listを表示(DEBUG用)
	 * @throws Exception
	 */
	public void printDefineInfo() throws Exception {

		for (int i = 0; i < mDefineList.size(); i++) {
			logger.debug("****DEBUG 文蓄定義情報List" + (i + 1) + "行目表示 ***");
			logger.debug("文献種別ID =" + mDefineList.get(i).getBid());
			logger.debug("文献名称   =" + mDefineList.get(i).getBnm());
			logger.debug("種別コード =" + mDefineList.get(i).getScd());
			logger.debug("国コード   =" + mDefineList.get(i).getCtry());
			logger.debug("文献種別   =" + mDefineList.get(i).getKind());
		}

		// 正常終了
		return;
	}

	/**
	 * 文蓄情報出力メソッド
	 * @param fileName
	 * @throws Cp5Exception
	 * @throws Exception
	 */
	public void outRangeInfo(String fileName) throws Cp5Exception, Exception{
		String msg = null;
		int i, rowNum = 0;
		PrintWriter pw = null;

		logger.debug("メソッドoutRangeInfo start");
		try {

			Date objDate = new Date();
			SimpleDateFormat objSdFormat = new SimpleDateFormat(SystemInfo.DATE_FORMAT);

			// 出力ファイルはUTF-8文字コードを指定
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), IComConst.FILE_OUTPUT_ENCODING)));

			// 管理情報レコード出力
			rowNum++;

			pw.format(SystemInfo.MANAGE_INFO_FORMAT, mRangeInfoList.size(), objSdFormat.format(objDate));
			logger.debug("文蓄情報ファイルにヘッダを出力");

			logger.debug("文蓄情報ファイル出力件数は" + mRangeInfoList.size());
			for (i = 0; i < mRangeInfoList.size(); i++) {
				// 文蓄情報レコード出力
				rowNum++;
				pw.format(SystemInfo.RANGE_INFO_FORMAT, mRangeInfoList.get(i).getBid(), mRangeInfoList.get(i).getBnm(), mRangeInfoList.get(i).getCtry(), mRangeInfoList.get(i).getKind(),
						mRangeInfoList.get(i).getDnoMin(), mRangeInfoList.get(i).getDnoMax(), mRangeInfoList.get(i).getIsdateMax(), mRangeInfoList.get(i).getCount());
				logger.debug("文蓄情報ファイルに" + rowNum + "行目レコードを出力");
			}

		} catch (IOException ex1) {
			// 入出力エラー
			msg = String.format(MessageInfo.CP5J510011, rowNum);
			throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J510011-E", msg);
		} catch (Exception ex2) {
			// 予期しない例外発生
			throw ex2;
		} finally {
			if (pw != null) {
				pw.close();
				logger.debug("pw対象のcloseメソッドを実行");
			}
		}
		logger.debug("メソッドoutRangeInfo end");
		// 正常終了
		return;
	}
}
