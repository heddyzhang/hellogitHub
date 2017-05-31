package com.batch.CP5P100;

import com.batch.Cp5Exception;
import com.batch.IComConst;
import com.batch.LogFormatter;
import com.batch.MessageInfo;
import com.batch.SystemStatus;

/**
 * 共通検索一覧表示用情報作成のメインクラス
 * @author chou
 *
 */
public class CP5P100 {

	public static void main(String[] args) {

		// 変数初期化
		LogFormatter objLogFm   		= new LogFormatter();
		String msg               		= null;
		int jobCode             		= SystemStatus.NORMAL;
		String mFileJppInfoPath		= null;
		String mFileCmsDir				= null;
		String mFileAppDir	 			= null;
		String mSyoriKbn	 			= null;
		String mMakeDataDefinePath	= null;

		try {

			// プログラム開始メッセージ出力
			msg = String.format(MessageInfo.CP5J110001);
			System.out.println(objLogFm.format("CP5J110001-I", msg));

			// 入力パラメータチェック
			// 入力パラメータ数をチェック
			if (args.length != 5) {
				msg = String.format(MessageInfo.CP5J110005, args.length);
				throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110005-E", msg);
			}

			// 入力パラメータ長をチェック
			// JPP表示用情報パス
			if (args[0].length() == 0 || 256 <= args[0].length()) {
				msg = String.format(MessageInfo.CP5J110006, args[0], args[0].length());
				throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110006-E", msg);
			}

			// 共通検索一覧表示用情報ディレクトリ
			if (args[1].length() == 0 || 256 <= args[1].length()) {
				msg = String.format(MessageInfo.CP5J110006, args[1], args[1].length());
				throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110006-E", msg);
			}

			// 番号索引用情報データディレクトリ
			if (args[2].length() == 0 || 256 <= args[2].length()) {
				msg = String.format(MessageInfo.CP5J110006, args[2], args[2].length());
				throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110006-E", msg);
			}

			// 処理区分
			if ((!(IComConst.SHORI_KBN_JP).equals(args[3])) && (!(IComConst.SHORI_KBN_FP).equals(args[3])) && (!(IComConst.SHORI_KBN_NP).equals(args[3]))) {
				msg = String.format(MessageInfo.CP5J110007, args[3]);
				throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110007-E", msg);
			}

			// データ作成定義ファイルパス
			if (args[4].length() == 0 || 256 <= args[4].length()) {
				msg = String.format(MessageInfo.CP5J110006, args[4], args[4].length());
				throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110006-E", msg);
			}

			// JPP表示用情報パス
			mFileJppInfoPath	= args[0];
			// 共通検索一覧表示用情報ディレクトリ
			mFileCmsDir			= args[1];
			// 番号索引用情報データディレクトリ
			mFileAppDir			= args[2];
			// 処理区分
			mSyoriKbn			= args[3];
			// データ作成定義ファイルパス
			mMakeDataDefinePath	= args[4];

			// オブジェクト生成
			CP5P100Env objCP5P100Env    = new CP5P100Env();
			JppInfo objJppInfo = new JppInfo();

			// 環境変数の設定
			objCP5P100Env.setEnv(mSyoriKbn);

			// データ作成定義ファイルの読み込み
			objJppInfo.setDefine(mMakeDataDefinePath);

			// ファイル名設定
			objJppInfo.setFileName(objCP5P100Env, mFileCmsDir, mFileAppDir, mSyoriKbn);

			// データ作成
			jobCode = objJppInfo.makeData(mFileJppInfoPath, mSyoriKbn);

		} catch (Cp5Exception ex1) {
			// 例外キャッチ(CP5Exception)
			System.out.println(objLogFm.format(ex1.getMessageId(), ex1.getMessage()));
			// ジョブコードに設定
			jobCode = ex1.getStatus();
		} catch (Exception ex3) {
			// 例外キャッチ(Exception)
			msg = String.format(MessageInfo.CP5J110028, ex3.getMessage());
			System.out.println(objLogFm.format("CP5J110028-E", msg));
			ex3.printStackTrace();

			// ジョブコードに設定
			jobCode = SystemStatus.ERROR_16;

		} finally {

			if (jobCode == SystemStatus.NORMAL) {
				msg = String.format(MessageInfo.CP5J110002);
				System.out.println(objLogFm.format("CP5J110002-I", msg));

			} else if (jobCode == SystemStatus.WARNING) {
				msg = String.format(MessageInfo.CP5J110003);
				System.out.println(objLogFm.format("CP5J510003-W", msg));

			} else {
				msg = String.format(MessageInfo.CP5J110004);
				System.out.println(objLogFm.format("CP5J510004-E", msg));
			}

			// 終了処理
			System.exit(jobCode);
		}

	}

}
