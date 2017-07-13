package com.batch.CP5P100;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import com.batch.ComUtil;
import com.batch.Cp5Exception;
import com.batch.IComConst;
import com.batch.MessageInfo;
import com.batch.SystemInfo;
import com.batch.SystemStatus;

/**
 * 番号索引用情報データクラス
 * @author chou
 *
 */
public class NumIndexInfo {

	// 番号索引用情報データ(特許－出願情報)ファイルパス
	private String mFilePAppm		= null;
	// 番号索引用情報データ(特許－審判情報)
	private String mFilePAppealm	= null;
	// 番号索引用情報データ(実用－出願情報)ファイルパス
	private String mFileUAppm		= null;
	// 番号索引用情報データ(実用－審判番号)ファイルパス
	private String mFileUAppealm	= null;

	// PrintWriterオブジェクト(番号索引用情報データ(特許－出願情報))
	private PrintWriter mFilePAppmPw	= null;
	// PrintWriterオブジェクト(番号索引用情報データ(特許－審判情報))
	private PrintWriter mFilePAppealmPw	= null;
	// PrintWriterオブジェクト(番号索引用情報データ(実用－出願情報))
	private PrintWriter mFileUAppmPw	= null;
	// PrintWriterオブジェクト(番号索引用情報データ(実用－審判情報))
	private PrintWriter mFileUAppealmPw	= null;

	/**
	 * コンストラクタ
	 * @param filePAppm
	 * @param filePAppealm
	 * @param fileUAppm
	 * @param fileUAppealm
	 */
	public NumIndexInfo(String filePAppm, String filePAppealm, String fileUAppm, String fileUAppealm) {

		// メンバ変数番号索引用情報データ(特許－出願情報)
		mFilePAppm = filePAppm;
		// メンバ変数番号索引用情報データ(特許－審判情報)
		mFilePAppealm = filePAppealm;
		// メンバ変数番号索引用情報データ(実用－出願情報)
		mFileUAppm = fileUAppm;
		// メンバ変数番号索引用情報データ(実用－審判情報)
		mFileUAppealm = fileUAppealm;

		// オブジェクト生成
		try {
			// PrintWriterオブジェクト(番号索引用情報データ(特許－出願情報))
			//mFilePAppmPw = new PrintWriter(mFilePAppm);
			mFilePAppmPw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mFilePAppm), IComConst.FILE_OUTPUT_ENCODING)));

			// PrintWriterオブジェクト(番号索引用情報データ(特許－審判情報))
			//mFilePAppealmPw = new PrintWriter(mFilePAppealm);
			mFilePAppealmPw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mFilePAppealm), IComConst.FILE_OUTPUT_ENCODING)));

			// PrintWriterオブジェクト(番号索引用情報データ(実用－出願情報))
			//mFileUAppmPw = new PrintWriter(mFileUAppm);
			mFileUAppmPw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mFileUAppm), IComConst.FILE_OUTPUT_ENCODING)));

			// PrintWriterオブジェクト(番号索引用情報データ(実用－審判情報))
			//mFileUAppealmPw = new PrintWriter(mFileUAppealm);
			mFileUAppealmPw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mFileUAppealm), IComConst.FILE_OUTPUT_ENCODING)));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 出力メソッド
	 * @param objCmsInfoBean
	 * @param rowNum
	 * @return
	 * @throws Cp5Exception
	 */
	public int outFile(CmsInfoBean objCmsInfoBean, int rowNum) throws Cp5Exception {

		int jobCode = SystemStatus.NORMAL;
		String msg = "";
		String pAppmOutData = "";
		String pAppealmOutData = "";
		String uAppmOutData = "";
		String uAppealmOutData = "";

		try {
			// 番号索引用情報データ(特許－出願情報)を編集
			pAppmOutData = editPAppm(objCmsInfoBean);
			// [資料型]が'P'か’C'の場合
			if (!IComConst.EMPTY_VALUE.equals(pAppmOutData)){
				// 編集内容をPrintWriterオブジェクトで出力
				mFilePAppmPw.println(pAppmOutData);
			}

			// 番号索引用情報データ(特許－審判情報)を編集
			pAppealmOutData = editPAppealm(objCmsInfoBean);
			// [資料型]が'P'か’C'の場合
			if (!IComConst.EMPTY_VALUE.equals(pAppealmOutData)){
				// 編集内容をPrintWriterオブジェクトで出力
				mFilePAppealmPw.println(pAppealmOutData);
			}

			// 番号索引用情報データ(実用－出願情報)を編集
			uAppmOutData = editUAppm(objCmsInfoBean);
			// [資料型]が'U'か’Z'の場合
			if (!IComConst.EMPTY_VALUE.equals(uAppmOutData)){
				// 編集内容をPrintWriterオブジェクトで出力
				mFileUAppmPw.println(uAppmOutData);
			}

			// 番号索引用情報データ(実用－審判情報)を編集
			uAppealmOutData = editUAppealm(objCmsInfoBean);
			// [資料型]が'U'か’Z'の場合
			if (!IComConst.EMPTY_VALUE.equals(uAppealmOutData)){
				// 編集内容をPrintWriterオブジェクトで出力
				mFileUAppealmPw.println(uAppealmOutData);
			}

			return jobCode;

		} catch (Exception e) {
			msg = String.format(MessageInfo.CP5J110023, rowNum);
			throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110023-E", msg);
		}
	}

	/**
	 * 番号索引用情報データ(特許－出願情報)編集メソッド
	 * @param objCmsInfoBean
	 */
	public String editPAppm(CmsInfoBean objCmsInfoBean) {

		// 変数初期化
		// 出力データ
		String outData = "";
		// 出願番号
		String ai = "";
		// 出願日
		String ad = "";
		// 公開番号(公表番号)
		String kyz = "";
		// 公開日(公表日)
		String kpd = "";
		// 公告番号
		String pn = "";
		// 公告日
		String pd = "";
		// 登録番号
		String rn = "";
		// 登録日
		String rd = "";
		// 公報発行日
		String rj = "";

		try {
			// 資料型を取得
			String sr = objCmsInfoBean.getSr();
			// 種別コード(代表文献番号)を取得
			String scdKy = objCmsInfoBean.getScdKy();
			// 種別コード(文献番号)
			String scdKz = objCmsInfoBean.getScdKz();
			// 代表文献番号(公開／公告番号)
			String ky = objCmsInfoBean.getKy().trim();
			// 文献番号
			String kz = objCmsInfoBean.getKz().trim();
			// 公開日
			String kd = objCmsInfoBean.getKd().trim();
			// 公表日
			String tpnd = objCmsInfoBean.getTpnd().trim();

			// [資料型]が'P'か’C'の場合
			if ("P".equals(sr) || "C".equals(sr)) {
				// ①出願番号（2バイト目～11バイト目）を設定
				ai = objCmsInfoBean.getAi().substring(1, 11);

				// ②出願日を設定
				ad = objCmsInfoBean.getAd().trim();

				// 種別コード(代表文献番号)がPA0(公開)の場合
				if ("PA0".equals(scdKy)) {
					// ③公開番号(公表番号)を設定
					// 代表文献番号(公開／公告番号)をYYYYnnnnnnに変換して設定
					kyz = convJpNToWesN(ky);

					// ④公開日(公表日)を設定
					// 公開番号を設定した場合、公開日で設定
					kpd = kd;

				// 種別コード(文献番号)がPA0(公開)の場合
				} else if ("PA0".equals(scdKz)) {
					// ③公開番号(公表番号)を設定
					// 文献番号をYYYYnnnnnnに変換して設定
					kyz = convJpNToWesN(kz);
					// ④公開日(公表日)を設定
					// 公開番号を設定した場合、公開日で設定
					kpd = kd;

				// 種別コード(代表文献番号)がPT0(公表)の場合
				} else if ("PT0".equals(scdKy)) {
					// ③公開番号(公表番号)を設定
					// 代表文献番号(公開／公告番号)をYYYYnnnnnnに変換して設定
					kyz = convJpNToWesN(ky);

					// ④公開日(公表日)を設定
					// 公表番号を設定した場合、公表日で設定
					kpd = tpnd;

				// 種別コード(文献番号)がPT0(公表)の場合
				} else if ("PT0".equals(scdKz)) {
					// ③公開番号(公表番号)を設定
					// 文献番号をYYYYnnnnnnに変換して設定
					kyz = convJpNToWesN(kz);

					// ④公開日(公表日)を設定
					// 公表番号を設定した場合、公表日で設定
					kpd = tpnd;
				}

				// 種別コード(代表文献番号)がPB0(公告)の場合
				if ("PB0".equals(scdKy)) {
					// ⑤公告番号を設定
					// 代表文献番号(公開／公告番号)をYYYYnnnnnnに変換して設定
					pn = convJpNToWesN(ky);

				// 種別コード(文献番号)がPB0(公告)の場合
				} else if ("PB0".equals(scdKz)) {
					// ⑤公告番号を設定
					// 文献番号をYYYYnnnnnnに変換して設定
					pn = convJpNToWesN(kz);

				}

				// ⑥公告日を設定
				pd = objCmsInfoBean.getPd().trim();

				// 種別コード(代表文献番号)がPR0(特許)かPC0(明細書)の場合
				if ("PR0".equals(scdKy) || "PC0".equals(scdKy)) {
					// ⑦登録番号を設定
					// 代表文献番号をnnnnnnに変換して設定
					rn = ky.substring(ky.length() - 7, ky.length());

				// 種別コード(文献番号)がPR0(特許)かPC0(明細書)の場合
				} else if ("PR0".equals(scdKz) || "PC0".equals(scdKz)) {
					// ⑦登録番号を設定
					// 文献番号をnnnnnnに変換して設定
					rn = kz.substring(kz.length() - 7, kz.length());
				}

				// ⑧登録日を設定
				rd = objCmsInfoBean.getRd().trim();

				// ⑨公報発行日
				rj = objCmsInfoBean.getRj().trim();

				// 文字列にカンマ区切りで
				outData = String.format(SystemInfo.PAPPM_INFO_FORMAT, ai, ad, kyz, kpd, pn, pd, rn, rd, rj);
			}

			return outData;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 番号索引用情報データ(特許－審判情報)編集メソッド
	 * @param objCmsInfoBean
	 * @return
	 */
	public String editPAppealm(CmsInfoBean objCmsInfoBean) {
		// 変数初期化
		// 出力データ
		String outData = "";
		// 出願番号
		String ai = "";
		// 審判番号繰り返し数
		int aplnNum = 0;
		// 全審判番号
		String apln = "";
		// 全審判申立日
		String apld = "";
		// 査定系審判番号
		String cApln = "";
		// 査定系審判請求日
		String cApld = "";

		try {
			// 資料型を取得
			String sr = objCmsInfoBean.getSr();

			// // 資料型がPかCの場合
			if ("P".equals(sr) || "C".equals(sr)) {
				// ①出願番号（2バイト目～11バイト目）を設定
				ai = objCmsInfoBean.getAi().substring(1, 11);

				// 審判番号繰り返し数を取得
				aplnNum = Integer.parseInt(objCmsInfoBean.getAplnNum());

				// 審判番号繰り返し数が0以外の場合
				if (IComConst.ZERO_REPEAT_NUM != aplnNum) {

					// 審判番号内容を格納
					String mapln[] = objCmsInfoBean.getApln().split(SystemInfo.CMS_FILE_DELIMITER, -1);

					// 審判番号繰り返し数によって、文字列を作成
					for (int i = 0; i < aplnNum; i++) {

						// 全審判番号を審判番号の1バイト目～10バイト目で設定
						//apln = mapln[i].substring(0, 10);
						apln = mapln[i];
						// 査定系審判番号を審判番号の1バイト目～10バイト目で設定
						//cApln = mapln[i].substring(0, 10);
						cApln = mapln[i];

						// 先頭の場合
						if (i == 0) {
							// 1行目を作成
							outData = String.format(SystemInfo.PAPPEALM_INFO_FORMAT, ai, apln, apld, cApln, cApld);
						} else {
							// 複数行目を作成
							outData = String.format("%s\n%s", outData, String.format(SystemInfo.PAPPEALM_INFO_FORMAT, ai, apln, apld, cApln, cApld));
						}
					}

				// 審判番号繰り返し数が0の場合
				} else {
					// 文字列にカンマ区切りで
					outData = String.format(SystemInfo.PAPPEALM_INFO_FORMAT, ai, apln, apld, cApln, cApld);
				}

			}

			return outData;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 番号索引用情報データ(実用－出願情報)編集メソッド
	 * @param objCmsInfoBean
	 * @return
	 */
	public String editUAppm(CmsInfoBean objCmsInfoBean) {

		// 変数初期化
		// 出力データ
		String outData = "";
		// 出願番号
		String ai = "";
		// 出願日
		String ad = "";
		// 公開番号(公表番号)
		String kyz = "";
		// 公開日(公表日)
		String kpd = "";
		// 公告番号
		String pn = "";
		// 公告日
		String pd = "";
		// 登録番号
		String rn = "";
		// 登録日
		String rd = "";
		// 公報発行日
		String rj = "";

		try {
			// 資料型を取得
			String sr = objCmsInfoBean.getSr();
			// 種別コード(代表文献番号)を取得
			String scdKy = objCmsInfoBean.getScdKy();
			// 種別コード(文献番号)
			String scdKz = objCmsInfoBean.getScdKz();
			// 代表文献番号(公開／公告番号)
			String ky = objCmsInfoBean.getKy().trim();
			// 文献番号
			String kz = objCmsInfoBean.getKz().trim();
			// 公開日
			String kd = objCmsInfoBean.getKd().trim();
			// 公表日
			String tpnd = objCmsInfoBean.getTpnd().trim();

			// ①出願番号（2バイト目～11バイト目）を設定
			ai = objCmsInfoBean.getAi().substring(1, 11);

			// ②出願日を設定
			ad = objCmsInfoBean.getAd().trim();

			// ⑥公告日を設定
			pd = objCmsInfoBean.getPd().trim();

			// ⑧登録日を設定
			rd = objCmsInfoBean.getRd().trim();

			// ⑨公報発行日
			rj = objCmsInfoBean.getRj().trim();

			// [資料型]が'U'か’Z'の場合
			if ("U".equals(sr) || "Z".equals(sr)) {

				// 種別コード(代表文献番号)がUA0(公開)の場合
				if ("UA0".equals(scdKy)) {
					// ③公開番号(公表番号)を設定
					// 代表文献番号(公開／公告番号)をYYYYnnnnnnに変換して設定
					kyz = convJpNToWesN(ky);

					// ④公開日(公表日)を設定
					// 公開番号を設定した場合、公開日で設定
					kpd = kd;

					// 種別コード(文献番号)がUA0(公開)の場合
				} else if ("UA0".equals(scdKz)) {
					// ③公開番号(公表番号)を設定
					// 文献番号をYYYYnnnnnnに変換して設定
					kyz = convJpNToWesN(kz);
					// ④公開日(公表日)を設定
					// 公開番号を設定した場合、公開日で設定
					kpd = kd;

				// 種別コード(代表文献番号)がUT0(公表)の場合
				} else if ("UT0".equals(scdKy)) {
					// ③公開番号(公表番号)を設定
					// 代表文献番号(公開／公告番号)をYYYYnnnnnnに変換して設定
					kyz = convJpNToWesN(ky);

					// ④公開日(公表日)を設定
					// 公表番号を設定した場合、公表日で設定
					kpd = tpnd;

				// 種別コード(文献番号)がUT0(公表)の場合
				} else if ("UT0".equals(scdKz)) {
					// ③公開番号(公表番号)を設定
					// 文献番号をYYYYnnnnnnに変換して設定
					kyz = convJpNToWesN(kz);

					// ④公開日(公表日)を設定
					// 公表番号を設定した場合、公表日で設定
					kpd = tpnd;
				}


				// 種別コード(代表文献番号)がUB0(公告)の場合
				if ("UB0".equals(scdKy)) {
					// ⑤公告番号を設定
					// 代表文献番号(公開／公告番号)をYYYYnnnnnnに変換して設定
					pn = convJpNToWesN(ky);

				// 種別コード(文献番号)がUB0(公告)の場合
				} else if ("UB0".equals(scdKz)) {
					// ⑤公告番号を設定
					// 文献番号をYYYYnnnnnnに変換して設定
					pn = convJpNToWesN(kz);
				}

				// 種別コード(代表文献番号)がUN0(登録実用)かUR0(実用登録)かUC0(明細書)の場合
				if ("UN0".equals(scdKy) || "UR0".equals(scdKy) || "UC0".equals(scdKy)) {
					// ⑦登録番号を設定
					// 代表文献番号をnnnnnnnに変換して設定
					rn = ky.substring(ky.length() - 7, ky.length());

				// 種別コード(文献番号)がUN0(登録実用)かUR0(実用登録)かUC0(明細書)の場合
				} else if ("UN0".equals(scdKz) || "UR0".equals(scdKz) || "UC0".equals(scdKz)) {
					// ⑦登録番号を設定
					// 文献番号をnnnnnnnに変換して設定
					rn = kz.substring(kz.length() - 7, kz.length());
				}

				// 文字列にカンマ区切りで
				outData = String.format(SystemInfo.UAPPM_INFO_FORMAT, ai, ad, kyz, kpd, pn, pd, rn, rd, rj);
			}

			return outData;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 番号索引用情報データ(実用－審判情報)編集メソッド
	 * @param objCmsInfoBean
	 * @return
	 */
	public String editUAppealm(CmsInfoBean objCmsInfoBean) {

		// 変数初期化
		// 出力データ
		String outData = "";
		// 出願番号
		String ai = "";
		// 審判番号繰り返し数
		int aplnNum = 0;
		// 全審判番号
		String apln = "";
		// 全審判申立日
		String apld = "";
		// 査定系審判番号
		String cApln = "";
		// 査定系審判請求日
		String cApld = "";

		try {
			// 資料型を取得
			String sr = objCmsInfoBean.getSr();

			// 資料型がUかZの場合
			if ("U".equals(sr) || "Z".equals(sr)) {

				// ①出願番号（2バイト目～11バイト目）を設定
				ai = objCmsInfoBean.getAi().substring(1, 11);

				// 審判番号繰り返し数を取得
				aplnNum = Integer.parseInt(objCmsInfoBean.getAplnNum());

				// 審判番号繰り返し数が0以外の場合
				if (IComConst.ZERO_REPEAT_NUM != aplnNum) {

					// 審判番号
					String mapln[] = objCmsInfoBean.getApln().split(SystemInfo.CMS_FILE_DELIMITER, -1);

					// 審判番号繰り返し数によって、文字列を作成
					for (int i = 0; i < aplnNum; i++) {

						// 全審判番号を審判番号の1バイト目～10バイト目で設定
						//apln = mapln[i].substring(0, 10);
						apln = mapln[i];
						// 査定系審判番号を審判番号の1バイト目～10バイト目で設定
						//cApln = mapln[i].substring(0, 10);
						cApln = mapln[i];

						// 先頭の場合
						if (i == 0) {
							// 1行目を作成
							outData = String.format(SystemInfo.UAPPEALM_INFO_FORMAT, ai, apln, apld, cApln, cApld);
						} else {
							// 複数行目を作成
							outData = String.format("%s\n%s", outData, String.format(SystemInfo.UAPPEALM_INFO_FORMAT, ai, apln, apld, cApln, cApld));
						}
					}

				// 審判番号繰り返し数が0の場合
				} else {
					// 文字列にカンマ区切りで
					outData = String.format(SystemInfo.UAPPEALM_INFO_FORMAT, ai, apln, apld, cApln, cApld);
				}
			}

			return outData;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * [代表文献番号(公開／公告番号)] or [文献番号]はYYYYnnnnnnに変換
	 * @param kyzNo (JPAbccnnnnnn、JPBbccnnnnnn)
	 * @return YYYYnnnnnn
	 */
	public String convJpNToWesN(String kyz) {

		// 和暦年を取得
		String jpYear = kyz.substring(3,6);
		// 最後から6文字列を取得
		String last6No = kyz.substring(kyz.length() - 6, kyz.length());

		return ComUtil.convertJc2Ad(jpYear).concat(last6No);
	}

	/**
	 * リソース解放メソッド
	 */
	public void close()  throws Exception {
		try {

			// リソース解放
			if (mFilePAppmPw != null) {
				mFilePAppmPw.close();
			}
			if (mFilePAppealmPw != null) {
				mFilePAppealmPw.close();
			}

			if (mFileUAppmPw != null) {
				mFileUAppmPw.close();
			}

			if (mFileUAppealmPw != null) {
				mFileUAppealmPw.close();
			}

		} catch (Exception e) {
			// 予期しない例外発生
			throw e;
		}

		// 正常終了
		return;
	}
}
