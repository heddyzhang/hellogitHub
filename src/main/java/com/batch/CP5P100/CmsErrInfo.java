package com.batch.CP5P100;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import com.batch.Cp5Exception;
import com.batch.IComConst;
import com.batch.MessageInfo;
import com.batch.SystemStatus;

/**
 * 共通検索一覧表示用情報エラー一覧クラス
 * @author chou
 *
 */
public class CmsErrInfo {

	// 共通検索一覧表示用情報エラー一覧
	private String mFileErrCmsPath = null;

	// PrintWriterオブジェクト(共通検索一覧表示用情報エラー一覧)
	private PrintWriter mFileCmsErrPw = null;

	/**
	 * コンストラクタ
	 * @param fileErrPath
	 */
	public CmsErrInfo(String fileErrPath) {

		// ファイル名の設定
		mFileErrCmsPath = fileErrPath;

		try {
			mFileCmsErrPw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mFileErrCmsPath), IComConst.FILE_OUTPUT_ENCODING)));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 出力メソッド
	 * @param inRecErr
	 * @param rowNum
	 * @throws Cp5Exception
	 */
	public void outFileCmsErr(String inRecErr, int rowNum) throws Cp5Exception {

		String msg = "";

		try {
			// 入力エラーレコードを共通検索一覧表示用情報エラー一覧出力
			mFileCmsErrPw.println(inRecErr);

		} catch (Exception ex2) {
			msg = String.format(MessageInfo.CP5J110024, rowNum);
			throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110024-E", msg);
		}

		// 正常終了
		return;
	}

	/**
	 * リソース解放メソッド
	 */
	public void close()  throws Exception {

		try {
			if (mFileCmsErrPw != null) {
				mFileCmsErrPw.close();
			}

		} catch (Exception e) {
			// 予期しない例外発生
			throw new Exception();
		}

		// 正常終了
		return;
	}

}
