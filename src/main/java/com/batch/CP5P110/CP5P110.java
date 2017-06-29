package com.batch.CP5P110;

import java.sql.Connection;
import java.sql.SQLException;

import com.batch.Cp5Exception;
import com.batch.DbAccess;
import com.batch.IComConst;
import com.batch.LogFormatter;
import com.batch.MessageInfo;
import com.batch.SystemEnv;
import com.batch.SystemInfo;
import com.batch.SystemStatus;

/**
 * 共通検索一覧表示用情報登録のメインクラス
 * @author chou
 *
 */
public class CP5P110 {

	public static void main(String[] args) {

		// 変数初期化
		LogFormatter objLogFm   		= new LogFormatter();
		int jobCode             		= SystemStatus.NORMAL;
		String msg               		= null;
		String mfileCmsPath	 		= null;
		String mfileChkPrmPath		= null;
		String mShoriKbn				= null;
		String mShoriTaisho			= null;
		String url              		= null;
		Connection objCon				= null;
		DbAccess objDbAccess			= null;
		boolean rollbackFlg = true;

		try {

			// ログ書式整形クラス
			//objLogFm = new LogFormatter();
			// プログラム開始メッセージ出力
			msg = String.format(MessageInfo.CP5J210001);
			System.out.println(objLogFm.format("CP5J210001-I", msg));
			// 入力パラメータチェック
			// 入力パラメータ数をチェック
			if (args.length != 4) {
				msg = String.format(MessageInfo.CP5J110005, args.length);
				throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110005-E", msg);
			}

			// 共通検索一覧表示用情報ディレクトリ
			if (args[0].length() == 0 || 256 <= args[0].length()) {
				msg = String.format(MessageInfo.CP5J110006, args[0], args[0].length());
				throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110006-E", msg);
			}

			// 蓄積データ内容確認ディレクトリ
			if (args[1].length() == 0 || 256 <= args[1].length()) {
				msg = String.format(MessageInfo.CP5J110006, args[1], args[1].length());
				throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110006-E", msg);
			}

			// 処理区分
			if (!IComConst.SHORI_KBN_JP.equals(args[2]) && !IComConst.SHORI_KBN_FP.equals(args[2]) && !IComConst.SHORI_KBN_NP.equals(args[2])) {
				msg = String.format(MessageInfo.CP5J110007, args[2]);
				throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110007-E", msg);
			}

			// 処理対象DB
			if (!IComConst.SHORI_TAISHO_DB_A.equals(args[3]) && !IComConst.SHORI_TAISHO_DB_B.equals(args[3])) {
				msg = String.format(MessageInfo.CP5J210030, args[3]);
				throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J210030-E", msg);
			}

			// 共通検索一覧表示用情報ファイルパス
			mfileCmsPath		= args[0];
			// 蓄積データ内容確認ファイルパス
			mfileChkPrmPath		= args[1];
			// 処理区分
			mShoriKbn			= args[2];
			// 処理対象DB
			mShoriTaisho		= args[3];


			// オブジェクト生成
			// 共通環境変数DB
			SystemEnv objSysEnv    = new SystemEnv();
			// 環境変数
			CP5P110Env objCP5P110Env = new CP5P110Env();
			// DBアクセスクラス
			objDbAccess = new DbAccess();
			// DB更新クラス(処理区分)
			DbUpdate objDbUpdate = new DbUpdate(mShoriKbn);

			// 共通環境変数DBの設定
			objSysEnv.setEnv();
			// 環境変数の設定
			objCP5P110Env.setEnv();

			// 共通検索一覧表示用情報ファイルパスを設定。
			mfileCmsPath = String.format("%s%s", mfileCmsPath, objCP5P110Env.getFileCms());
			// 蓄積データ内容確認ファイルパスを設定。
			mfileChkPrmPath = String.format("%s%s", mfileChkPrmPath, objCP5P110Env.getFileChkPrm());

			// DB接続
			// JDBC URLフォーマット整形
			url = String.format(SystemInfo.URL, objSysEnv.getPdnameportVal(), objSysEnv.getPdhostVal());
			// DB接続情報設定を行う
			objDbAccess.setConfig(SystemInfo.DRIVER, url, SystemInfo.USER, SystemInfo.PASSWD);

			// DB接続
			objCon = objDbAccess.connect();
			// 自動コミットを無効
			objCon.setAutoCommit(false);
			// 最大レコードID取得
			objDbUpdate.getRecid(objCon);

			// DB更新
			jobCode = objDbUpdate.update(objCon, mfileCmsPath, objCP5P110Env.getCmtLmtVal());

			// DBコミットが実施済み、ロールバック不可を設定
			rollbackFlg = false;
			// 処理対象DBが'B'(蓄積面)の場合
			if (IComConst.SHORI_TAISHO_DB_B.equals(mShoriTaisho)) {
				// 蓄積確認パラメータ出力
				objDbUpdate.outFileChkPrm(objCon, mfileChkPrmPath);
			}

		} catch (Cp5Exception ex1) {
			// 例外キャッチ(CP5Exception)
			System.out.println(objLogFm.format(ex1.getMessageId(), ex1.getMessage()));
			// ジョブコードに設定
			jobCode = ex1.getStatus();

		} catch (SQLException ex2) {
			msg = String.format(MessageInfo.CP5J210032, ex2.getErrorCode());
			System.out.println(objLogFm.format("CP5J210032-E", msg));

			// ジョブコードに設定
			jobCode = SystemStatus.ERROR_16;

		} catch (Exception ex3) {
			// 例外キャッチ(Exception)
			msg = String.format(MessageInfo.CP5J110028, ex3.getMessage());
			System.out.println(objLogFm.format("CP5J110028-E", msg));
			ex3.printStackTrace();

			// ジョブコードに設定
			jobCode = SystemStatus.ERROR_16;

		} finally {

			// ジョブコード > ステータスオブジェクト.ステータス8の場合
			if (jobCode > SystemStatus.ERROR_8 && objCon != null && rollbackFlg){
				try {
					// DBロールバック
					objCon.rollback();

					// 正常の場合、メッセージ(CP5J210025)を出力
					msg = String.format(MessageInfo.CP5J210025);
					System.out.println(objLogFm.format("CP5J210025-I", msg));
				} catch (SQLException e) {
					// エラーの場合、メッセージ(CP5J210026)を出力
					msg = String.format(MessageInfo.CP5J210026, e.getErrorCode());
					System.out.println(objLogFm.format("CP5J210026-E", msg));
				}
			}

			// DB切断
			int rtn = SystemStatus.NORMAL;

			if (objDbAccess != null) {

				// 戻り値 > ジョブコードの場合
				rtn = objDbAccess.disconnect();
				if (jobCode < rtn) {
					// ジョブコードに戻り値を設定
					jobCode = rtn;
				}
			}

			if (jobCode == SystemStatus.NORMAL) {
				msg = String.format(MessageInfo.CP5J210002);
				System.out.println(objLogFm.format("CP5J210002-I", msg));

			} else {
				msg = String.format(MessageInfo.CP5J210004);
				System.out.println(objLogFm.format("CP5J210004-E", msg));
			}

			// 終了処理
			System.exit(jobCode);
		}
	}
}
