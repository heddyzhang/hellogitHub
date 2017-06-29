package com.batch.CP5P100;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.batch.ComUtil;
import com.batch.Cp5Exception;
import com.batch.IComConst;
import com.batch.LogFormatter;
import com.batch.MessageInfo;
import com.batch.SystemInfo;
import com.batch.SystemStatus;


/**
 * JPP表示用情報クラス
 * @author chou
 *
 */
public class JppInfo {

	// データ作成定義Bean型Listオブジェクト
	private List<MakeDataDefineBean> mDefineList    = null;
	// JPP表示用情報Bean型Listオブジェクト
	private List<JppInfoBean> mJppInfoList = null;

	// 共通検索一覧表示用情報ファイルパス
	private String mFileCmsPath	 	= null;
	// 共通検索一覧表示用情報エラー一覧ファイルパス
	private String mFileErrCmsPath	= null;
	// 番号索引用情報データ(特許－出願情報)ファイルパス
	private String mFilePAppmPath		= null;
	// 番号索引用情報データ(特許－審判情報)
	private String mFilePAppealmPath	= null;
	// 番号索引用情報データ(実用－出願情報)ファイルパス
	private String mFileUAppmPath		= null;
	// 番号索引用情報データ(実用－審判番号)ファイルパス
	private String mFileUAppealmPath	= null;

	// ログ出力クラス(DEBUG 用)
	private static final Logger logger = LoggerFactory.getLogger(JppInfo.class);

	/**
	 * データ作成定義設定メソッド
	 * @param fileName データ作成定義ファイルパス
	 * @throws Cp5Exception
	 * @throws Exception
	 */
	public void setDefine(String fileName) throws Cp5Exception, Exception {

		String   msg     = null;
		String   inRec   = null;
		String[] inItems = null;
		BufferedReader br = null;

		try {

			// InputStreamReaderで文字コードを指定できる
			FileInputStream file = new FileInputStream(fileName);
			br = new BufferedReader(new InputStreamReader(file, IComConst.FILE_DEFINE_ENCODING));

			// データ作成定義Bean型ArrayList
			mDefineList = new ArrayList<MakeDataDefineBean>();

			// データ作成定義ファイル読み込み
			while ((inRec = br.readLine()) != null) {
			    // 項目切り出し（カンマ毎）
				inItems = inRec.split(SystemInfo.DEF_FILE_DELIMITER, -1);

				// データ作成定義Beanクラスを生成
				MakeDataDefineBean defines = new MakeDataDefineBean();
				// 項目番号
				defines.setNo(inItems[0]);
				// 項目タイプ
				defines.setType(inItems[1]);
				// レベル(子項目数)
				defines.setLevel(inItems[2]);
				// 要否区分
				defines.setKubun(inItems[3]);
				// 長さ(Byte)最大値
				defines.setMaxLen(inItems[4]);
				// 長さ(Byte)有効値
				//defines.setLen(inItems[5]);
				// 半角数字チェックフラグ
				defines.setM09ChkFlg(inItems[6]);
				// 半角英数字チェックフラグ
				defines.setM09azAZChkFlg(inItems[7]);
				// 繰り返し数最大値
				defines.setMaxRepeat(inItems[8]);
				// 繰り返し数有効値
				//defines.setRepeat(inItems[9]);

				// データ作成定義Bean型ArrayListに追加する。
				mDefineList.add(defines);
			}

		} catch (IOException ex1) {
			// 入出力エラー
			msg = String.format(MessageInfo.CP5J110008, fileName);
			throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110008-E", msg);
		} catch (Exception ex2) {
			// 予期しない例外発生
			throw ex2;
		} finally {
			// リソース解放
			if (br != null) {
				br.close();
			}
		}
		// 正常終了
    	return;
	}

	/**
	 * ファイル名設定メソッド
	 * @param objCP5P100Env
	 * @param fileCmsDir
	 * @param fileAppDir
	 * @param syoriKbn
	 */
	public void setFileName(CP5P100Env objCP5P100Env, String fileCmsDir, String fileAppDir, String syoriKbn) {

		// 共通検索一覧表示用情報ファイルパスを設定。
		mFileCmsPath = String.format("%s%s", fileCmsDir, objCP5P100Env.getFileCms());
		// 共通検索一覧表示用情報エラー一覧ファイルパスを設定。
		mFileErrCmsPath = String.format("%s%s", fileCmsDir, objCP5P100Env.getFileErrCms());

		// 処理区分が'1'(国内)の場合
		if (IComConst.SHORI_KBN_JP.equals(syoriKbn)) {
			// 番号索引用情報データ(特許－出願情報)ファイルパスを設定
			mFilePAppmPath = String.format("%s%s", fileAppDir, objCP5P100Env.getFilePAppm());
			// 番号索引用情報データ(特許－審判情報)ファイルパスを設定
			mFilePAppealmPath = String.format("%s%s", fileAppDir, objCP5P100Env.getFilePAppealm());
			// 番号索引用情報データ(実用－出願情報)ファイルパスを設定
			mFileUAppmPath = String.format("%s%s", fileAppDir, objCP5P100Env.getFileUAppm());
			// 番号索引用情報データ(実用－審判番号)ファイルパスを設定
			mFileUAppealmPath = String.format("%s%s", fileAppDir, objCP5P100Env.getFileUAppealm());
		}
	}

	/**
	 * データ作成メソッド
	 * @param fileJppInfoPath
	 */
	public int makeData(String fileJppInfoPath, String shoriKbn) throws Exception {

		// 変数初期化
		LogFormatter objLogFm = null;
		String inRec = null;
		int jobCode = SystemStatus.NORMAL;
		SystemStatus objSysStatus =  new SystemStatus();
		String msg = "";
		int rowNum = 0;
		CmsInfo objCmsInfo = null;
		NumIndexInfo objNumIndexInfo = null;
		CmsErrInfo objCmsErrInfo = null;
		BufferedReader br = null;

		try {
			// オブジェクト生成
			objLogFm = new LogFormatter();

			// 標準形式DB更新情報データはEUCで文字コードを指定して読み込む
			FileInputStream file = new FileInputStream(fileJppInfoPath);
			br = new BufferedReader(new InputStreamReader(file, IComConst.FILE_DEFAULT_ENCODING));

			// 共通検索一覧表示用情報を出力
			objCmsInfo = new CmsInfo(mFileCmsPath, shoriKbn);
			// 共通検索一覧表示用情報エラー一覧クラス
			objCmsErrInfo = new CmsErrInfo(mFileErrCmsPath);

			// 処理区分が'1'(国内)の場合、
			if (IComConst.SHORI_KBN_JP.equals(shoriKbn)) {
				objNumIndexInfo = new NumIndexInfo(mFilePAppmPath, mFilePAppealmPath, mFileUAppmPath, mFileUAppealmPath);
			}

			logger.debug("method makeData start");
			while ((inRec = br.readLine()) != null) {

				// 行数
				rowNum ++;

				// JPP表示用情報設定メソッド
				jobCode = setJppInfo(inRec, shoriKbn, rowNum);

				// レコードの処理で警告が発生した場合、
				if (SystemStatus.WARNING == jobCode) {

					// 共通検索一覧表示用情報エラー一覧を出力
					objCmsErrInfo.outFileCmsErr(inRec, rowNum);

					// メッセージを出力
					msg = String.format(MessageInfo.CP5J110026, rowNum);
					System.out.println(objLogFm.format("CP5J110026-W", msg));

					objSysStatus.setWarn();

					// 警告のレコードも共通検索一覧表示用情報ファイルを出力
				}

				// レコードの処理でデターエラーが発生した場合、
				if (SystemStatus.ERROR_8 == jobCode) {

					// 共通検索一覧表示用情報エラー一覧を出力
					objCmsErrInfo.outFileCmsErr(inRec, rowNum);

					// メッセージを出力
					msg = String.format(MessageInfo.CP5J110027, rowNum);
					System.out.println(objLogFm.format("CP5J110027-E", msg));

					objSysStatus.setError_8();
					// 当該レコードをスキップ
					continue;
				}

				// 共通検索一覧表示用情報ファイルを出力
				objCmsInfo.outFile(mJppInfoList, rowNum);

				// 処理区分が'1'(国内)の場合、
				if (IComConst.SHORI_KBN_JP.equals(shoriKbn)) {

					// 番号索引用データ情報ファイルを出力
					objNumIndexInfo.outFile(objCmsInfo.getObjCmsInfoBean(), rowNum);
				}

				// 正常の場合
				msg = String.format(MessageInfo.CP5J110025, rowNum);
				System.out.println(objLogFm.format("CP5J110025-I", msg));

			}
			logger.debug("method makeData end");

		} catch (Cp5Exception ex1) {
            throw new Cp5Exception(ex1.getStatus(), ex1.getMessageId(), ex1.getMessage());

        } catch (IOException ex2) {
			msg = String.format(MessageInfo.CP5J110010, fileJppInfoPath);
			throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110010-E", msg);
        }  catch (Exception ex3) {
        	ex3.printStackTrace();
        	// 予期しない例外発生
        	throw new Exception();
        } finally {

        	// BufferedReaderオブジェクトJPP表示用情報リソース解放
        	if (br != null) {
            	br.close();
        	}

        	// 共通検索一覧表示用情報リソース解放
        	if (objCmsInfo != null){
            	objCmsInfo.close();
        	}

			// 処理区分が'1'(国内)の場合、
			if (IComConst.SHORI_KBN_JP.equals(shoriKbn)) {
				//  番号索引用情報データリソース解放
				if (objNumIndexInfo != null) {
					objNumIndexInfo.close();
				}
			}

        	// エラー一覧リソース解放
			if (objCmsErrInfo != null){
	        	objCmsErrInfo.close();
			}
        }

		return objSysStatus.getStatus();
	}

	/**
	 * JPP表示用情報設定メソッド
	 * @param inRec
	 * @param shoriKbn
	 * @param rowNum
	 * @return
	 * @throws Exception
	 */
	public int setJppInfo(String inRec, String shoriKbn, int rowNum)  throws Exception {

		JppInfoBean objJppInfoBean = null;
		MakeDataDefineBean curDefBean = null;
		int rtn;
		SystemStatus objSystemStatus = new SystemStatus();

		String jppData[] = inRec.split(SystemInfo.JPP_FILE_DELIMITER, -1);

		logger.debug("method setJppInfo start");

		try {
			int dataIdx = 0, defIdx = 0;
			// JPP表示用情報設定
			mJppInfoList = new ArrayList<JppInfoBean>();

			while(dataIdx < jppData.length) {

				objJppInfoBean = new JppInfoBean();

				curDefBean = mDefineList.get(defIdx);
				// 項目番号を特定
				objJppInfoBean.setNo(curDefBean.getNo());
				// 項目内容を特定
				objJppInfoBean.setItem(jppData[dataIdx]);

				// チェックの結果を取得
				rtn = checkData(objJppInfoBean, curDefBean, rowNum, dataIdx + 1);

				if (SystemStatus.WARNING == rtn) {

					// 項目内容が初期値になる
					objJppInfoBean.setItem(IComConst.ITEM_DEFAULT_VALUE);

					// 警告が発生した場合、次項目の処理を行う。
					objSystemStatus.setWarn();

				} else if (SystemStatus.ERROR_8 == rtn){

					// データエラーが発生した場合、レコードをスキップ
					objSystemStatus.setError_8();

					return objSystemStatus.getStatus();
				}

				// JPP表示用情報Bean型Listオブジェクトに追加
				mJppInfoList.add(objJppInfoBean);

				// 繰り返し項目の場合
				if ("2".equals(mDefineList.get(defIdx).getType())) {

					// 繰り返し数を取得
					int repeatNum = Integer.parseInt(jppData[dataIdx]);
					// 子項目数を取得
					int subItems = Integer.parseInt(mDefineList.get(defIdx).getLevel());

					// 定義ファイルの最初位置を一応記録
					int subDefIdx_start = defIdx;

					int defIdx_total_increment = 0;

					// 繰り返し数をループ
					for (int repeatDataCount = 0; repeatDataCount < repeatNum; repeatDataCount++) {

						// 定義ファイルのCurrentインデスクを一応記録(繰り返し数の位置)
						int subDefIdx = subDefIdx_start;

						// 子項目数をループ
						for (int subDefine = 1; subDefine <= subItems; subDefine++) {

							objJppInfoBean = new JppInfoBean();

							curDefBean = mDefineList.get(++subDefIdx);
							// 項目番号を特定
							objJppInfoBean.setNo(curDefBean.getNo());
							// 項目内容を特定
							objJppInfoBean.setItem(jppData[++dataIdx]);

							// チェックの結果を取得
							rtn = checkData(objJppInfoBean, curDefBean, rowNum, dataIdx + 1);

							if (SystemStatus.WARNING == rtn) {

								// 項目内容が初期値になる
								objJppInfoBean.setItem(IComConst.ITEM_DEFAULT_VALUE);

								// 警告が発生した場合、次項目の処理を行う。
								objSystemStatus.setWarn();

							} else if (SystemStatus.ERROR_8 == rtn){

								// データエラーが発生した場合、レコードをスキップ
								objSystemStatus.setError_8();

								return objSystemStatus.getStatus();
							}
							// JPP表示用情報Bean型Listオブジェクトに追加
							mJppInfoList.add(objJppInfoBean);

							// **********************内層ループ Start**********************

							// 繰り返し項目の場合
							if ("2".equals(mDefineList.get(subDefIdx).getType())) {

								// 繰り返し数を取得
								int repeatNum2 = Integer.parseInt(jppData[dataIdx]);
								// 子項目数を取得
								int subItems2 = Integer.parseInt(mDefineList.get(subDefIdx).getLevel());

								// 繰り返し数をループ
								for (int repeatDataCount2 = 0; repeatDataCount2 < repeatNum2; repeatDataCount2++) {

									// 定義ファイルのCurrentインデスクを一応記録(子項目の繰り返し数の位置)
									int subDefIdx2 = subDefIdx;

									// 子項目数をループ
									for (int subDefine2 = 1; subDefine2 <= subItems2; subDefine2++) {

										objJppInfoBean = new JppInfoBean();

										curDefBean = mDefineList.get(++subDefIdx2);
										// 項目番号を特定
										objJppInfoBean.setNo(curDefBean.getNo());
										// 項目内容を特定
										objJppInfoBean.setItem(jppData[++dataIdx]);

										// チェックの結果を取得
										rtn = checkData(objJppInfoBean, curDefBean, rowNum, dataIdx + 1);

										if (SystemStatus.WARNING == rtn) {

											// 項目内容が初期値になる
											objJppInfoBean.setItem(IComConst.ITEM_DEFAULT_VALUE);

											// 警告が発生した場合、次項目の処理を行う。
											objSystemStatus.setWarn();

										} else if (SystemStatus.ERROR_8 == rtn){

											// データエラーが発生した場合、レコードをスキップ
											objSystemStatus.setError_8();

											return objSystemStatus.getStatus();
										}
										// JPP表示用情報Bean型Listオブジェクトに追加
										mJppInfoList.add(objJppInfoBean);

									}
								}

								subDefIdx += subItems2;
							}

							// **********************内層ループ End**********************
						}

						// 繰り返し数一個目が終了した場合、定義ファイルのoffsetを計算
						if (repeatDataCount == 0) {
							defIdx_total_increment = subDefIdx - subDefIdx_start;
						}
					}

					// TODO 定義ファイルのCurrentインデックスを計算(all sub level)
					if (repeatNum == 0) {
						defIdx += subItems;
					} else {
						defIdx += defIdx_total_increment;
					}
				}
				// JPP表示用情報Listのインデックス+1
				dataIdx++;
				// データ作成定義Listのインデックス+1
				defIdx++;

			}

			//******************** DEBUG用 ********************
			//printJppInfoList(mJppInfoList, rowNum);

			logger.debug("method setJppInfo end");

			return objSystemStatus.getStatus();

		} catch (Exception e) {
			//e.printStackTrace();
			throw e;
		}
	}

//	/**
//	 * TEST用
//	 * @param jppInfoList
//	 * @param rowNum
//	 */
//	private void printJppInfoList(List<JppInfoBean> jppInfoList, int rowNum){
//		int i =0;
//		logger.debug("********************TEST用 printJppInfoList Start********************");
//		logger.debug(rowNum +"行目" + " SIZEは" + jppInfoList.size());
//		for (JppInfoBean jppInfoBean: jppInfoList) {
//			i++;
//			logger.debug(i + "個目"+" 項目番号 = "+jppInfoBean.getNo());
//			logger.debug(i + "個目"+" 項目内容 = "+jppInfoBean.getItem());
//		}
//		logger.debug("********************TEST用 printJppInfoList End********************");
//	}

//	/**
//	 * TEST用
//	 * @param jppInfoBean
//	 * @param rowNum
//	 * @param i
//	 */
//	private void printJppInfo(JppInfoBean jppInfoBean, int rowNum, int i) {
//
//		logger.debug(rowNum + "行目:" + i + "個目" + " 項目番号 = " + jppInfoBean.getNo() + " 項目内容 = " + jppInfoBean.getItem());
//
//	}

	/**
	 * チェックメソッド
	 * @param objJppData
	 * @param objDefineBean
	 * @param rowNum
	 * @param columnNum
	 * @return
	 * @throws Exception
	 */
	public int checkData(JppInfoBean objJppData, MakeDataDefineBean objDefineBean, int rowNum, int columnNum) throws Exception {
		// 変数初期化
		LogFormatter objLogFm   = null;
		String dataItem;
		String kubun = "";
		String msg = "";

		// DEBUG用
		//printJppInfo(objJppData, rowNum, columnNum);
		try {
			// ログ書式整形クラス
			objLogFm = new LogFormatter();

			// チェック要否判定を取得
			kubun = objDefineBean.getKubun();

			// データ作成定義Beanオブジェクト.要否区分が'3'(不要)の場合
			if (IComConst.YOFU_KBN_FUYO.equals(kubun)) {
				return SystemStatus.NORMAL;
			}

			// データ内容を取得
			dataItem = objJppData.getItem();

			// データ作成定義Beanオブジェクト.要否区分が'1'(必須)の場合
			if (IComConst.YOFU_KBN_HISU.equals(kubun)) {
				// 必須チェックを行う
				if (dataItem.length() == 0) {

					msg = String.format(MessageInfo.CP5J110012, rowNum, columnNum);
					System.out.println(objLogFm.format("CP5J110012-E", msg));

					return SystemStatus.ERROR_8;
				}
			}

			// MAXサイズチェックを行う。
			if (ComUtil.getByteLength(dataItem, IComConst.FILE_DEFAULT_ENCODING)> Integer.parseInt(objDefineBean.getMaxLen())) {

				// 要否区分'2'(任意)の場合
				if (IComConst.YOFU_KBN_NINYI.equals(kubun)) {

					msg = String.format(MessageInfo.CP5J110013, rowNum, columnNum);
					System.out.println(objLogFm.format("CP5J110013-W", msg));

					return SystemStatus.WARNING;
				}

				// 要否区分'1'(必須)の場合
				if (IComConst.YOFU_KBN_HISU.equals(kubun)) {

					msg = String.format(MessageInfo.CP5J110014, rowNum, columnNum);
					System.out.println(objLogFm.format("CP5J110014-E", msg));

					return SystemStatus.ERROR_8;
				}
			}

			// 半角数字チェックを行う。
			if (IComConst.CHECK_KBN_NUM.equals(objDefineBean.getM09ChkFlg())) {

				// チェック対象に半角数字以外が含まれている場合
				if (!ComUtil.isNumber(dataItem)) {

					// 要否区分'2'(任意)の場合
					if (IComConst.YOFU_KBN_NINYI.equals(kubun)) {

						msg = String.format(MessageInfo.CP5J110015, rowNum, columnNum);
						System.out.println(objLogFm.format("CP5J110015-W", msg));

						return SystemStatus.WARNING;
					}

					// 要否区分'1'(必須)の場合
					if (IComConst.YOFU_KBN_HISU.equals(kubun)) {

						msg = String.format(MessageInfo.CP5J110016, rowNum, columnNum);
						System.out.println(objLogFm.format("CP5J110016-E", msg));

						return SystemStatus.ERROR_8;
					}
				}
			}

			// 半角英数字チェックを行う
			if (IComConst.CHECK_KBN_ALPNUM.equals(objDefineBean.getM09azAZChkFlg())) {

				// チェック対象に半角英数字以外が含まれている場合
				if (!ComUtil.isAlphaNum(dataItem)) {

					// 要否区分'2'(任意)の場合
					if (IComConst.YOFU_KBN_NINYI.equals(kubun)) {

						msg = String.format(MessageInfo.CP5J110017, rowNum, columnNum);
						System.out.println(objLogFm.format("CP5J110017-W", msg));

						return SystemStatus.WARNING;
					}

					// 要否区分'1'(必須)の場合
					if (IComConst.YOFU_KBN_HISU.equals(kubun)) {

						msg = String.format(MessageInfo.CP5J110018, rowNum, columnNum);
						System.out.println(objLogFm.format("CP5J110018-E", msg));

						return SystemStatus.ERROR_8;
					}
				}
			}

			// 繰り返し数MAXチェックを行う
			if (IComConst.TYPE_KBN_REPEAT.equals(objDefineBean.getType())) {

				// チェック対象 > データ作成定義Beanオブジェクト.繰り返し数最大値
				if (ComUtil.toint(dataItem) > ComUtil.toint(objDefineBean.getMaxRepeat()))	 {

					msg = String.format(MessageInfo.CP5J110019, rowNum, columnNum, Integer.parseInt(dataItem));
					System.out.println(objLogFm.format("CP5J110019-E", msg));

					return SystemStatus.ERROR_8;
				}
			}

			return SystemStatus.NORMAL;

		} catch (Exception e) {
			e.printStackTrace();
			// 予期しない例外発生
			throw e;
		}
	}

}