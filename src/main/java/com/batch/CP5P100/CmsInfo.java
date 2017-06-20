package com.batch.CP5P100;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
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
 * 共通検索一覧表示用情報クラス
 * @author chou
 *
 */
public class CmsInfo {

	// 共通検索一覧表示用情報
	private String mFileCms = null;
	// 処理区分
	private String mShoriKbn = null;

	// PrintWriterオブジェクト(共通検索一覧表示用情報)
	private PrintWriter mFileCmsPw = null;

	// 共通検索一覧表示用情報出力エリアクラス
	private CmsInfoBean objCmsInfoBean = null;

	// ログ出力クラス(DEBUG 用)
	private static final Logger logger = LoggerFactory.getLogger(CmsInfo.class);

	/**
	 * コンストラクタ
	 * @param fileErrPath
	 */
	public CmsInfo(String fileCmsPath, String shoriKbn) {
		mFileCms = fileCmsPath;
		mShoriKbn = shoriKbn;

		// オブジェクト生成
		try {
			mFileCmsPw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mFileCms), IComConst.FILE_OUTPUT_ENCODING)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 共通検索一覧表示用情報出力エリアを取得(番号索引用データ情報出力)
	 * @return
	 */
	public CmsInfoBean getObjCmsInfoBean() {
		return objCmsInfoBean;
	}

	/**
	 * 出力メソッド
	 * @param inRecErr
	 * @throws Cp5Exception
	 */
	public int outFile(List<JppInfoBean> objJppInfoList , int rowNum) throws Cp5Exception {

		int jobCode = SystemStatus.NORMAL;
		String msg = "";

		try {

			logger.debug("method outFile start");
			if (IComConst.SHORI_KBN_JP.equals(mShoriKbn)) {
				// 処理区分が'1'(国内)の場合、共通検索一覧表示用情報(国内)編集
				editCmsjptbl(objJppInfoList);

				// ファイル出力
				mFileCmsPw.format(SystemInfo.CMSJP_INFO_FORMAT, objCmsInfoBean.getIsn(), objCmsInfoBean.getAi(), objCmsInfoBean.getKy(), objCmsInfoBean.getKz(), objCmsInfoBean.getPn(),
										objCmsInfoBean.getSr(), objCmsInfoBean.getAd(), objCmsInfoBean.getHk(), objCmsInfoBean.getKd(), objCmsInfoBean.getPd(),
										objCmsInfoBean.getRd(), objCmsInfoBean.getRj(), objCmsInfoBean.getTpnd(), objCmsInfoBean.getRtpnd(), objCmsInfoBean.getApln(),
										objCmsInfoBean.getPctd(), objCmsInfoBean.getIdn(), objCmsInfoBean.getPrin(), objCmsInfoBean.getDa(), objCmsInfoBean.getGo(),
										objCmsInfoBean.getFi(), objCmsInfoBean.getNo(), objCmsInfoBean.getAa(), objCmsInfoBean.getBb(), objCmsInfoBean.getCc(),
										objCmsInfoBean.getDd(), objCmsInfoBean.getFw(), objCmsInfoBean.getScdKy(), objCmsInfoBean.getScdKz(), objCmsInfoBean.getSltCd());

			} else if (IComConst.SHORI_KBN_FP.equals(mShoriKbn)) {
				// 処理区分が'2'(国内)の場合、共通検索一覧表示用情報(外国)編集
				editCmsfptbl(objJppInfoList);

				// ファイル出力
				mFileCmsPw.format(SystemInfo.CMSFP_INFO_FORMAT, objCmsInfoBean.getIsn(), objCmsInfoBean.getKy1(), objCmsInfoBean.getKy2(), objCmsInfoBean.getKy3(),
								objCmsInfoBean.getKz(), objCmsInfoBean.getAd(), objCmsInfoBean.getHk(), objCmsInfoBean.getFi(), objCmsInfoBean.getFt(),
							objCmsInfoBean.getIc(), objCmsInfoBean.getScdKy(), objCmsInfoBean.getSltCd());

			} else if (IComConst.SHORI_KBN_NP.equals(mShoriKbn)) {

				// 処理区分が'3'(国内)の場合、共通検索一覧表示用情報(非特許)編集
				editCmsnptbl(objJppInfoList);

				// ファイル出力
				mFileCmsPw.format(SystemInfo.CMSNP_INFO_FORMAT, objCmsInfoBean.getIsn(), objCmsInfoBean.getKy1(), objCmsInfoBean.getKy2(),
									objCmsInfoBean.getKy3(), objCmsInfoBean.getKy4(), objCmsInfoBean.getHk(), objCmsInfoBean.getHi(),
								objCmsInfoBean.getFt(), objCmsInfoBean.getTl(), objCmsInfoBean.getScdKy(), objCmsInfoBean.getSltCd());
			}
			logger.debug("method outFile end");
			return jobCode;

		} catch (Exception e) {
			msg = String.format(MessageInfo.CP5J110022, rowNum);
			throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110022-E", msg);
		}
	}

	/**
	 * 共通検索一覧表示用情報(国内)編集メソッド
	 * @param objJppInfoList
	 */
	public void editCmsjptbl(List<JppInfoBean> objJppInfoList) throws Exception {

		// 共通検索一覧表示用情報(国内)出力クラス
		objCmsInfoBean = new CmsInfoBean();
		// 審判番号繰り返し数
		String aplnNum = "0";
		// 申請人識別番号繰り返し数
		String idnNum = "0";
		// 優先権主張番号繰り返し数
		String prinNum = "0";
		// 出願人繰り返し数
		String goNum = "0";
		// FI繰り返し数
		String fiNum = "0";
		// 発明番号繰り返し数
		String noNum = "0";

		// 審判番号リスト
		List<String> aplnList = new ArrayList<String>();
		// 申請人識別番号リスト
		List<String> idnList = new ArrayList<String>();
		// 優先権主張番号リスト
		List<String> prinList = new ArrayList<String>();
		// 出願人リスト
		List<String> goList = new ArrayList<String>();
		// FIリスト
		List<String> fiList = new ArrayList<String>();
		// 発明番号リスト
		List<String> noList = new ArrayList<String>();

		try {
			// 共通検索一覧表示用情報(国内)出力エリアを設定
			for (JppInfoBean objJppInfoBean : objJppInfoList) {

				// 項目番号
				String no = objJppInfoBean.getNo();
				// 項目内容
				String item= objJppInfoBean.getItem();

				// ISNを設定
				if (IComConst.ISN_NO_JP.equals(no)) {

					objCmsInfoBean.setIsn(item);
					continue;
				}

				// 出願番号を設定
				if (IComConst.AI_NO_JP.equals(no)) {

					objCmsInfoBean.setAi(item);
					continue;
				}

				// 代表文献番号(公開／公告番号)を設定
				if (IComConst.KY_NO_JP.equals(no)) {

					objCmsInfoBean.setKy(item);
					continue;
				}

				// 文献番号を設定
				if (IComConst.KZ_NO_JP.equals(no)) {

					objCmsInfoBean.setKz(item);
					continue;
				}

				// 公告番号・明細番号・JIS番号を設定
				if (IComConst.PN_NO_JP.equals(no)) {

					objCmsInfoBean.setPn(item);
					continue;
				}

				// 資料型を設定
				if (IComConst.SR_NO_JP.equals(no)) {

					objCmsInfoBean.setSr(item);
					continue;
				}

				// 出願日を設定
				if (IComConst.AD_NO_JP.equals(no)) {

					objCmsInfoBean.setAd(item);
					continue;
				}

				// 公知日を設定
				if (IComConst.HK_NO_JP.equals(no)) {

					objCmsInfoBean.setHk(item);
					continue;
				}

				// 公開日を設定
				if (IComConst.KD_NO_JP.equals(no)) {

					objCmsInfoBean.setKd(item);
					continue;
				}

				// 公告日を設定
				if (IComConst.PD_NO_JP.equals(no)) {

					objCmsInfoBean.setPd(item);
					continue;
				}

				// 登録日を設定
				if (IComConst.RD1_NO_JP.equals(no)) {

					objCmsInfoBean.setRd(item);
					continue;
				}

				// 登録公報発行日を設定
				if (IComConst.RJ_NO_JP.equals(no)) {

					objCmsInfoBean.setRj(item);
					continue;
				}

				// 公表日を設定
				if (IComConst.TPND_NO_JP.equals(no)) {

					objCmsInfoBean.setTpnd(item);
					continue;
				}

				// 再公表発行日を設定
				if (IComConst.RTPND_NO_JP.equals(no)) {

					objCmsInfoBean.setRtpnd(item);
					continue;
				}

				// 審判番号繰り返し数を設定
				if (IComConst.APLN_REP_NO_JP.equals(no)) {

					aplnNum = item;
					objCmsInfoBean.setAplnNum(aplnNum);
					continue;
				}

				// 審判番号リストを設定
				if (IComConst.APLN_NO_JP.equals(no)) {

					aplnList.add(item);
					continue;
				}

				// 国際公開日を設定
				if (IComConst.PCTD_NO_JP.equals(no)) {

					objCmsInfoBean.setPctd(item);
					continue;
				}

				// 申請人識別番号繰り返し数を設定
				if (IComConst.IDN_REP_NO_JP.equals(no)) {

					idnNum = item;
					continue;
				}

				// 申請人識別番号リストを設定
				if (IComConst.IDN_NO_JP.equals(no)) {

					idnList.add(item);
					continue;
				}

				// 優先権主張番号繰り返し数を設定
				if (IComConst.PRIN_REP_NO_JP.equals(no)) {

					prinNum = item;
					continue;
				}

				// 優先権主張番号リストを設定
				if (IComConst.PRIN_NO_JP.equals(no)) {

					prinList.add(item);
					continue;
				}

				// 発明名称を設定
				if (IComConst.DA_NO_JP.equals(no)) {

					objCmsInfoBean.setDa(item);
					continue;
				}

				// 出願人繰り返し数を設定
				if (IComConst.GO1_REP_NO_JP.equals(no)) {

					goNum = item;
					continue;
				}

				// 出願人リストを設定
				if (IComConst.GO1_NO_JP.equals(no)) {

					//先頭から200バイトを編集
					if (ComUtil.getByteLength(item, IComConst.FILE_DEFAULT_ENCODING) > IComConst.GO_LEN) {
						goList.add(ComUtil.leftB(item, IComConst.GO_LEN, IComConst.FILE_DEFAULT_ENCODING));
					} else {
						goList.add(item);
					}

					continue;
				}

				// FI繰り返し数を設定
				if (IComConst.FI_REP_NO_JP.equals(no)) {

					fiNum = item;
					continue;
				}

				// FIリストを設定
				if (IComConst.FI_NO_JP.equals(no)) {

					fiList.add(item);
					continue;
				}

				// 発明番号繰り返し数を設定
				if (IComConst.NO1_REP_NO_JP.equals(no)) {

					noNum = item;
					continue;
				}

				// 発明番号リストを設定
				if (IComConst.NO1_NO_JP.equals(no)) {

					noList.add(item);
					continue;
				}
			}

			// 公開日
			String kd = objCmsInfoBean.getKd();
			// 登録公報発行日
			String rj = objCmsInfoBean.getRj();
			// 代表文献番号(公開／公告番号)
			String oldKy = objCmsInfoBean.getKy();
			// 文献番号
			String oldKz = objCmsInfoBean.getKz();

			// 公開日 > 登録公報発行日の場合
			if (!ComUtil.isEmpty(kd) && !ComUtil.isEmpty(rj) && Integer.parseInt(kd) > Integer.parseInt(rj)) {
				// 代表文献番号(公開／公告番号)を文献番号で設定
				objCmsInfoBean.setKy(oldKz);
				// 文献番号を代表文献番号(公開／公告番号)で編集
				objCmsInfoBean.setKz(oldKy);
			}

			// 審判番号を設定
			String aplnData = convLstToData(aplnNum, aplnList, IComConst.DEFAULT_MAX_REPEAT_NUM);
			objCmsInfoBean.setApln(aplnData);

			// 申請人識別番号を設定
			String idnData = convLstToData(idnNum, idnList, IComConst.DEFAULT_MAX_REPEAT_NUM);
			objCmsInfoBean.setIdn(idnData);

			// 優先権主張番号を設定
			String prinData = convLstToData(prinNum, prinList, IComConst.DEFAULT_MAX_REPEAT_NUM);
			objCmsInfoBean.setPrin(prinData);

			// 出願人を設定
			String goData = convLstToData(goNum, goList, IComConst.DEFAULT_MAX_REPEAT_NUM);
			objCmsInfoBean.setGo(goData);

			// FIを設定
			String fiData = convLstToData(fiNum, fiList, IComConst.FI_MAX_REPEAT_NUM_JP);
			objCmsInfoBean.setFi(fiData);

			// 発明番号を設定
			String noData = convLstToData(noNum, noList, IComConst.DEFAULT_MAX_REPEAT_NUM);
			objCmsInfoBean.setNo(noData);

			// タームAの編集
			String termA = convRepeatData(objJppInfoList, IComConst.AA_REP_NO_JP);
			// タームAの設定
			objCmsInfoBean.setAa(termA);

			// タームBの編集
			String termB = convRepeatData(objJppInfoList, IComConst.BB_REP_NO_JP);
			// タームBの設定
			objCmsInfoBean.setBb(termB);

			// タームCの編集
			String termC = convRepeatData(objJppInfoList, IComConst.CC_REP_NO_JP);
			// タームCの設定
			objCmsInfoBean.setCc(termC);

			// タームDの編集
			String termD = convRepeatData(objJppInfoList, IComConst.DD_REP_NO_JP);
			// タームDの設定
			objCmsInfoBean.setDd(termD);

			// 合金フリーワードの編集
			String goKin = convRepeatData(objJppInfoList, IComConst.FW_REP_NO_JP);
			// 合金フリーワードの設定
			objCmsInfoBean.setFw(goKin);

			// 種別コード(代表文献番号)を設定
			objCmsInfoBean.setScdKy(getScdKy(objCmsInfoBean.getKy()));

			// 種別コード(文献番号)を設定
			objCmsInfoBean.setScdKz(getScdKy(objCmsInfoBean.getKz()));

			// 検索コード
			objCmsInfoBean.setSltCd("0");

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 共通検索一覧表示用情報(外国)編集メソッド
	 * @param objJppInfoList
	 */
	/**
	 * @param objJppInfoList
	 */
	public void editCmsfptbl(List<JppInfoBean> objJppInfoList) {

		// 共通検索一覧表示用情報(外国)出力クラス
		objCmsInfoBean = new CmsInfoBean();

		String scdKy = "";

		// 文献番号返し数
		String kzNum = "";
		// FI繰り返し数
		String fiNum = "";
		// Fターム繰り返し数
		String ftNum = "";
		// IPC返し数
		String ipcNum = "";
		// 文献番号の内容
		String kz = "";
		// FIの内容
		String fi = "";
		// Fタームの内容
		String ft = "";
		// IPCの内容
		String ipc = "";

		// 文献番号リスト
		List<String> kzList = new ArrayList<String>();
		// FIリスト
		List<String> fiList = new ArrayList<String>();
		// Fタームリスト
		List<String> ftList = new ArrayList<String>();
		// IPCリスト
		List<String> ipcList = new ArrayList<String>();

		try {
			// 共通検索一覧表示用情報(外国)出力エリアを設定
			for (JppInfoBean objJppInfoBean : objJppInfoList) {

				// 項目番号
				String no = objJppInfoBean.getNo();
				// 項目内容
				String item= objJppInfoBean.getItem();

				// ISNを設定
				if (IComConst.ISN_NO_FP.equals(no)) {

					objCmsInfoBean.setIsn(item);
					continue;
				}

				// 代表文献番号(コード)を設定
				if (IComConst.KY1_NO_FP.equals(no)) {

					objCmsInfoBean.setKy1(item);
					continue;
				}

				// 代表文献番号(種別)を設定
				if (IComConst.KY2_NO_FP.equals(no)) {

					objCmsInfoBean.setKy2(item);
					continue;
				}

				// 代表文献番号(公開／公告番号)を設定
				if (IComConst.KY3_NO_FP.equals(no)) {

					objCmsInfoBean.setKy3(item);
					// 種別コードを取得
					scdKy =  getScdKy(String.format("%s%s%s", objCmsInfoBean.getKy1(),  objCmsInfoBean.getKy2(),  objCmsInfoBean.getKy3()));
					objCmsInfoBean.setScdKy(scdKy);

					continue;
				}

				// 文献番号繰り返し数を設定
				if (IComConst.KZ_REP_NO_FP.equals(no)) {

					kzNum = item;
					continue;
				}

				// 文献番号を設定
				if (IComConst.KZ_NO_FP.equals(no)) {

					kzList.add(item);
					continue;
				}

				// 出願日を設定
				if (IComConst.AD_NO_FP.equals(no)) {

					objCmsInfoBean.setAd(item);
					continue;
				}

				// 公知日を設定
				if (IComConst.HK_NO_FP.equals(no)) {

					objCmsInfoBean.setHk(item);
					continue;
				}

				// FI繰り返し数を設定
				if (IComConst.FI_REP_NO_FP.equals(no)) {

					fiNum = item;
					continue;
				}

				// FIを設定
				if (IComConst.FI_NO_FP.equals(no)) {

					fiList.add(item);
					continue;
				}

				// Fターム繰り返し数を設定
				if (IComConst.FT_REP_NO_FP.equals(no)) {

					ftNum = item;
					continue;
				}

				//  Fタームを設定
				if (IComConst.FT_NO_FP.equals(no)) {

					ftList.add(item);
					continue;
				}

				// IPC繰り返し数を設定
				if (IComConst.IC_REP_NO_FP.equals(no)) {

					ipcNum = item;
					continue;
				}

				// IPCを設定
				if (IComConst.IC_NO_FP.equals(no)) {

					ipcList.add(item);
					continue;
				}
			}

			// 文献番号を設定
			kz = convLstToData(kzNum, kzList, IComConst.KZ_MAX_REPEAT_NUM);
			objCmsInfoBean.setKz(kz);

			// FIを設定
			fi = convLstToData(fiNum, fiList, IComConst.FI_MAX_REPEAT_NUM);
			objCmsInfoBean.setFi(fi);

			// Fタームを設定
			ft = convLstToData(ftNum, ftList, IComConst.FT_MAX_REPEAT_NUM);
			objCmsInfoBean.setFt(ft);

			// IPCを設定
			ipc = convLstToData(ipcNum, ipcList, IComConst.IC_MAX_REPEAT_NUM);
			objCmsInfoBean.setIc(ipc);

			// 検索コード
			objCmsInfoBean.setSltCd("0");

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 共通検索一覧表示用情報(非特許)編集メソッド
	 * @param objJppInfoList
	 */
	public void editCmsnptbl(List<JppInfoBean> objJppInfoList) {

		// 変数の初期化
		// 共通検索一覧表示用情報(非特許)出力クラス
		objCmsInfoBean = new CmsInfoBean();

		// Fターム繰り返し数
		String ftNum = "";
		// Fタームの内容
		String ft = "";
		// Fタームリスト
		List<String> ftList = new ArrayList<String>();

		try {

			// 共通検索一覧表示用情報(非特許)出力エリアを設定
			for (JppInfoBean objJppInfoBean : objJppInfoList) {

				// 項目番号
				String no = objJppInfoBean.getNo();
				// 項目内容
				String item= objJppInfoBean.getItem();

				// ISNを設定
				if (IComConst.ISN_NO_NP.equals(no)) {

					objCmsInfoBean.setIsn(item);
					continue;
				}

				// 代表文献番号(非特許種別)を設定
				if (IComConst.KY1_NO_NP.equals(no)) {

					objCmsInfoBean.setKy1(item);
					continue;
				}

				// 代表文献番号(文献種別)を設定
				if (IComConst.KY2_NO_NP.equals(no)) {

					objCmsInfoBean.setKy2(item);
					continue;
				}

				// 代表文献番号(書籍番号)を設定
				if (IComConst.KY3_NO_NP.equals(no)) {

					objCmsInfoBean.setKy3(item);
					continue;
				}

				// 代表文献番号(記事番号)を設定
				if (IComConst.KY4_NO_NP.equals(no)) {

					objCmsInfoBean.setKy4(item);
					continue;
				}

				// 公知日を設定
				if (IComConst.HK_NO_NP.equals(no)) {

					objCmsInfoBean.setHk(item);
					continue;
				}

				// 発明名称／書籍タイトルを設定
				if (IComConst.HI_NO_NP.equals(no)) {

					objCmsInfoBean.setHi(item);
					continue;
				}

				// Fターム繰り返し数を設定
				if (IComConst.FT_REP_NO_NP.equals(no)) {

					ftNum = item;
					continue;
				}

				// Fタームを設定
				if (IComConst.FT_NO_NP.equals(no)) {

					ftList.add(item);
					continue;
				}

				// 文献タイトルを設定
				if (IComConst.TL_NO_NP.equals(no)) {

					objCmsInfoBean.setTl(item);
					continue;
				}
			}

			// Fタームを設定
			ft = convLstToData(ftNum, ftList, IComConst.FT_MAX_REPEAT_NUM);
			objCmsInfoBean.setFt(ft);

			// 種別コード(代表文献番号)を設定
			objCmsInfoBean.setScdKy(getScdKy(objCmsInfoBean.getKy1().concat(objCmsInfoBean.getKy2())));

			// 検索コード
			objCmsInfoBean.setSltCd("0");

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 種別コードを取得
	 * @param item
	 * @return
	 */
	public String getScdKy(String item) {

		String ScdKz = "";
		String cond1 = "";
		String cond2 = "";
		String cond31 = "";
		String cond32 = "";
		String cond33 = "";

		// 代表文献番号(文献番号)が初期値の場合
		if (IComConst.ITEM_DEFAULT_VALUE.equals(item)) return ScdKz;

		// 国内の場合
		if (IComConst.SHORI_KBN_JP.equals(mShoriKbn)) {
			// 条件1(先頭から2バイト)
			cond1 = item.substring(0,2);
			// 条件2(3バイト)
			cond2 =item.substring(2,3);
			// 条件3(7バイト)
			cond31 =item.substring(6,7);
			// 条件3(4バイト)
			cond32 =item.substring(3,4);

			if ("JP".equals(cond1)) {
				if ("A".equals(cond2)) {
					if (ComUtil.toint(cond31) < 5) {
						ScdKz = "PA0";
					} else if (ComUtil.toint(cond31) >= 5 && ComUtil.toint(cond31) != 8) {
						ScdKz = "PT0";
					} else {
						ScdKz = "PS0";
					}
				}

				if  ("B".equals(cond2)) {
					if (ComUtil.toint(cond32) != 0) {
						ScdKz = "PB0";
					} else {
						ScdKz = "PR0";
					}
				}

				if  ("C".equals(cond2)) {
					ScdKz = "PC0";
				}

				if ("U".equals(cond2)) {
					if (ComUtil.toint(cond32) == 0) {
						ScdKz = "UN0";
					} else if (ComUtil.toLng(item.substring(6, 12)) < 500001) {
						ScdKz = "UA0";
					} else if (ComUtil.toint(cond31) >= 5 && ComUtil.toint(cond31) != 8 ) {
						ScdKz = "UT0";
					} else {
						ScdKz = "US0";
					}
				}

				if ("Y".equals(cond2)) {
					if (ComUtil.toint(cond32) != 0) {
						ScdKz = "UB0";
					} else {
						ScdKz = "UR0";
					}
				}

				if ("Z".equals(cond2)) {
					ScdKz = "UC0";
				}
			}

		}

		// 外国の場合
		if (IComConst.SHORI_KBN_FP.equals(mShoriKbn)) {
			// 条件1(先頭から2バイト)
			cond1 = item.substring(0,2);
			// 条件2(3～4バイト)
			cond2 =item.substring(2,4);
			// 条件3(5バイト)
			cond31 =item.substring(4,5);
			// 条件3(5～6バイト)
			cond32 =item.substring(4,6);
			// 条件3(5バイト～)
			cond33 =item.substring(5);

			if ("US".equals(cond1)) {
				if ("A ".equals(cond2)) {
					if (!"00".equals(cond32)) {
						ScdKz = "UA1";
					} else {
						ScdKz = "FU0";
					}
				}
				if ("B ".equals(cond2)) {
					ScdKz = "UB1";
				}
			}

			if ("EP".equals(cond1)) {
				if ("A ".equals(cond2)) {
					ScdKz = "FE1";
				}
				if ("B ".equals(cond2)) {
					ScdKz = "FEA";
				}
			}

			if("WO".equals(cond1) && "A ".equals(cond2)) {
				ScdKz = "FP1";
			}

			if ("CN".equals(cond1)) {
				if ("A ".equals(cond2)){
					if ("0".equals(cond31)) {
						ScdKz = "CN1";
					} else if ("1".equals(cond31)){
						ScdKz = "CN2";
					}
				}

				if ("B ".equals(cond2)){
					if ("0".equals(cond31) && 1019872 <= ComUtil.toLng(cond33)) {
						ScdKz = "CN3";
					} else if ("0".equals(cond31) && 1019873 >= ComUtil.toLng(cond33)){
						ScdKz = "CN4";
					} else if ("1".equals(cond31)){
						ScdKz = "CN5";
					}
				}

				if ("C ".equals(cond2)){
					if ("0".equals(cond31)) {
						ScdKz = "CN6";
					} else if ("1".equals(cond31)){
						ScdKz = "CN7";
					}
				}

				if ("U ".equals(cond2)){
					if ("0".equals(cond31)) {
						ScdKz = "CNA";
					} else if ("2".equals(cond31)){
						ScdKz = "CNB";
					}
				}

				if ("Y ".equals(cond2)){
					if ("0".equals(cond31)) {
						ScdKz = "CNC";
					} else if ("2".equals(cond31)){
						ScdKz = "CND";
					}
				}
			}

			if ("KR".equals(cond1)) {
				if ("A ".equals(cond2)) {
					ScdKz = "KR1";
				}

				if ("B ".equals(cond2)){
					if (!("00".equals(cond32))){
						ScdKz = "KR2";
					} else {
						ScdKz = "KR3";
					}
				}

				if ("U ".equals(cond2)) {
					ScdKz = "KRA";
				}

				if ("Y ".equals(cond2)){
					if (!("00".equals(cond32))){
						ScdKz = "KRB";
					} else {
						ScdKz = "KRC";
					}
				}
			}
		}

		// 特許の場合
		if (IComConst.SHORI_KBN_NP.equals(mShoriKbn)) {

			// 条件1(先頭から2バイト)
			cond1 = item.substring(0,2);
			// 条件2(3～4バイト)
			cond2 =item.substring(2,4);

			if ("CS".equals(cond1)) {
				switch (cond2) {
				case "NA":
					ScdKz = "NA0";
					break;
				case "NB":
					ScdKz = "NB0";
					break;
				case "ND":
					ScdKz = "ND0";
					break;
				case "NE":
					ScdKz = "NE0";
					break;
				case "NF":
					ScdKz = "NF0";
					break;
				case "NG":
					ScdKz = "NG0";
					break;
				case "NH":
					ScdKz = "NH0";
					break;
				case "NI":
					ScdKz = "NI0";
					break;
				case "NJ":
					ScdKz = "NJ0";
					break;
				case "NC":
					ScdKz = "NC0";
					break;
				}
			} else if ("JP".equals(cond1) && "N1".equals(cond2)) {
				ScdKz = "N10";
			}
		}

		return ScdKz;
	}

	/**
	 * 繰り返し数分リストを出力用文字列を変換
	 * @param repeatNum
	 * @param repeatLst
	 * @param outNum
	 * @return
	 */
	public String convLstToData(String repeatNum, List<String> repeatLst, int outNum) {

		String outItem = "";
		int i;

		if (Integer.parseInt(repeatNum) > 0) {
			// 繰り返し数まで編集
			for (i = 0; i < repeatLst.size(); i++) {

				// 先頭から何個
				if (i == outNum) break;

				 // 内容を編集
				if (i == 0) {
					outItem = repeatLst.get(0);
				} else {
					outItem = String.format("%s	%s", outItem, repeatLst.get(i));
				}
			}
		}

		// 最大繰り返し数まで空バイト埋め込む
		for (i = 0; i < outNum - repeatLst.size(); i++ ){

			// 繰り返し数が0の場合
			if (repeatLst.size() == 0 && i == 0) {
				outItem = "";
			} else {
				outItem = String.format("%s	%s", outItem, "");
			}
		}

		return outItem;

	}

	/**
	 * タームA、B、C、D、合金フリーワードの編集
	 * @param mJppInfoList
	 * @param itemNo
	 * @param hasSubFlg
	 * @return
	 */
	public String convRepeatData(List<JppInfoBean> mJppInfoList, String itemNo) throws Exception {

		String repeatNumHatsumei = "";
		int repeatItemNum = 0;
		String itemData = "";
		List<String> itemList = new ArrayList<String>();
		String outData = "";

		for (int i = 0; i < mJppInfoList.size(); i++) {

			// 発明番号繰り返し数を取得
			if (IComConst.NO1_REP_NO_JP.equals(mJppInfoList.get(i).getNo())) {
				repeatNumHatsumei = mJppInfoList.get(i).getItem();

				// 発明番号繰り返し数をループ
				for (int j = 0; j < Integer.parseInt(repeatNumHatsumei); j++) {

					// （タイムA or B or C or D or 合金）の繰り返し数インデクスを探す
					while (i < mJppInfoList.size() && !(itemNo.equals(mJppInfoList.get(i).getNo()))) {
						i++;
					}

					// 上記ループの無限を防ぐ
					if (i == mJppInfoList.size())
					{
						// データエラー
						System.out.println(String.format("データエラー：NO %sが見つかりません", itemNo));
						throw new Exception();
					}

					//（タイムA or B or C or D or 合金）の繰り返し数を取得
					repeatItemNum = Integer.parseInt(mJppInfoList.get(i).getItem());

					// 繰り返し数が1個以上の場合
					if (repeatItemNum > 0)
					{
						itemData = "";

						//（タイムA or B or C or D or 合金）の繰り返し数をループ
						for (int k = 1 ; k <= repeatItemNum; k++) {
							i++;

							// 1個目の場合
							if (k == 1) {
								// （タイムA or C or D or 合金）の場合
								if (!(IComConst.BB_REP_NO_JP).equals(itemNo)) {
									itemData = mJppInfoList.get(i).getItem();
								} else {
									// タイムBの場合
									// 1個目をデリミタなしで連結
									itemData = String.format("%s%s%s%s",
											mJppInfoList.get(i++).getItem(),
											mJppInfoList.get(i++).getItem(),
											mJppInfoList.get(i++).getItem(),
											mJppInfoList.get(i).getItem());
								}
							} else {
								// （タイムA or C or D or 合金）の場合
								if (!(IComConst.BB_REP_NO_JP).equals(itemNo)) {
									// 半角カンマで区切った文字列とし、繰り返し数だけ連結
									itemData = String.format("%s,%s", itemData, mJppInfoList.get(i).getItem());
								} else {
									// タイムBの場合
									// デリミタなしで連結し、半角カンマで区切った文字列とし、繰り返し数だけ連結する
									itemData = String.format("%s,%s", itemData, String.format("%s%s%s%s",
											mJppInfoList.get(i++).getItem(),
											mJppInfoList.get(i++).getItem(),
											mJppInfoList.get(i++).getItem(),
											mJppInfoList.get(i).getItem()));
								}
							}
						}

						// １個データを追加
						itemList.add(itemData);
					} else {
						// 繰り返し数が0の場合、次の項目へ進行
						i++;
						itemList.add("");
					}
				}
			}
		}

		// 出力データに変換
		outData = convLstToData(repeatNumHatsumei, itemList, IComConst.DEFAULT_MAX_REPEAT_NUM);

		return outData;
	}

	/**
	 * リソース解放メソッド
	 */
	public void close()  throws Exception {
		try {

			if (mFileCmsPw != null) {
				mFileCmsPw.close();
			}
		} catch (Exception e) {
			// 予期しない例外発生
			throw new Exception();
		}

		// 正常終了
		return;
	}
}
