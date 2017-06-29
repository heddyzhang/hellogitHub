package com.batch.CP5P130;
import java.sql.Connection;
import java.sql.SQLException;

import com.batch.Cp5Exception;
import com.batch.DbAccess;
import com.batch.LogFormatter;
import com.batch.MessageInfo;
import com.batch.SystemEnv;
import com.batch.SystemInfo;
import com.batch.SystemStatus;

/**
 * 共通検索一覧表示用情報文蓄情報作成のメインクラス
 * @author chou
 *
 */
public class CP5P130 {

	public static void main(String[] args) {
		LogFormatter objLogFm   = new LogFormatter();
		DbAccess objDbAccess    = null;
		DbExtract objDbExtract1 = null;
		DbExtract objDbExtract2 = null;
		DbExtract objDbExtract31 = null;
		DbExtract objDbExtract32 = null;
		DbExtract objDbExtract41 = null;
		DbExtract objDbExtract42 = null;
		String mOutFileName     = null;
		String mDefFileName     = null;
		String msg              = null;
		String url              = null;
		int jobCode             = SystemStatus.NORMAL;

		try {

			msg = String.format(MessageInfo.CP5J510001);
			System.out.println(objLogFm.format("CP5J510001-I", msg));

			// 入力パラメータ数をチェック
			if (args.length != 2){
				msg = String.format(MessageInfo.CP5J110005, args.length);
				throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110005-E", msg);
			}

			// 入力パラメータ長をチェック
			if (args[0].length() == 0 || 256 <= args[0].length()) {
				msg = String.format(MessageInfo.CP5J110006, args[0], args[0].length());
				throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110006-E", msg);
			}

			if (args[1].length() == 0 || 256 <= args[1].length()) {
				msg = String.format(MessageInfo.CP5J110006, args[1], args[1].length());
				throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110006-E", msg);
			}

			// 共通検索一覧表示用情報文蓄情報一時ファイルパスを取得
			mOutFileName = args[0];

			// 文蓄情報作成定義ファイルパスを取得
			mDefFileName = args[1];

			// オブジェクト生成
			// 環境変数設定クラス
			SystemEnv objSysEnv    = new SystemEnv();
			// 文蓄情報クラス
			RangeInfo objRangeInfo = new RangeInfo(SystemInfo.MAX_SCD_NUM);

			// DBアクセスクラス
			objDbAccess            = new DbAccess();
			// 文蓄情報抽出(国内)
			objDbExtract1          = new DbExtract(SystemInfo.TBLNAME1);
			// 文蓄情報抽出(国内)
			objDbExtract2          = new DbExtract(SystemInfo.TBLNAME1);
			// 文蓄情報抽出(外国)
			objDbExtract31          = new DbExtract(SystemInfo.TBLNAME2);
			objDbExtract32          = new DbExtract(SystemInfo.TBLNAME2);
			// 文蓄情報抽出(非特許)
			objDbExtract41          = new DbExtract(SystemInfo.TBLNAME3);
			objDbExtract42          = new DbExtract(SystemInfo.TBLNAME3);

			// 環境変数の設定取得
			objSysEnv.setEnv();

			// 定義ファイル読み込み
			objRangeInfo.setDefine(mDefFileName);

			// JDBC URLフォーマット整形
			url = String.format(SystemInfo.URL, objSysEnv.getPdnameportVal(), objSysEnv.getPdhostVal());

			// DB接続用変数の設定
			objDbAccess.setConfig(SystemInfo.DRIVER, url, SystemInfo.USER, SystemInfo.PASSWD);

			// DB接続
			Connection objCon = objDbAccess.connect();
			// DB検索
			// 共通検索一覧表示用情報(国内)管理DBを種別コード(代表文献番号)毎にDB検索を行う
			objDbExtract1.select(objCon, SystemInfo.QUERY1_1);

			// 共通検索一覧表示用情報(国内)管理DBを種別コード(文献番号)毎にDB検索を行う
			objDbExtract2.select(objCon, SystemInfo.QUERY1_2);

			// 共通検索一覧表示用情報(外国)管理DBを種別コード(文献番号)毎にDB検索を行う
			objDbExtract31.select(objCon, SystemInfo.QUERY2_1);
			objDbExtract32.select(objCon, SystemInfo.QUERY2_2);

			// 共通検索一覧表示用情報(非特許)管理DBを種別コード(文献番号)毎にDB検索を行う
			objDbExtract41.select(objCon, SystemInfo.QUERY3_1);
			objDbExtract42.select(objCon, SystemInfo.QUERY3_4);

			// 代表文献番号(記事番号)の蓄積先頭番号取得(非特許用(N10以外))
			objDbExtract41.selectKy4Min(objCon, SystemInfo.QUERY3_2);

			// 代表文献番号(記事番号)の蓄積最終番号取得(非特許用(N10以外))
			objDbExtract41.selectKy4Max(objCon, SystemInfo.QUERY3_3);

			// 文蓄情報設定
			objRangeInfo.setRangeInfo(objDbExtract1, objDbExtract2, objDbExtract31, objDbExtract32, objDbExtract41, objDbExtract42);

			// 文蓄情報出力
			objRangeInfo.outRangeInfo(mOutFileName);

		} catch (Cp5Exception ex1) {
			System.out.println(objLogFm.format(ex1.getMessageId(), ex1.getMessage()));
			jobCode = ex1.getStatus();
		} catch (SQLException ex2) {
			msg = String.format(MessageInfo.CP5J210032,  ex2.getErrorCode());
			System.out.println(objLogFm.format("CP5J210032-E", msg));

			jobCode = SystemStatus.ERROR_16;
		} catch (Exception ex3) {
			// メッセージ出力
			msg = String.format(MessageInfo.CP5J110028, ex3.getMessage());
			System.out.println(objLogFm.format("CP5J110028-E", msg));
			ex3.printStackTrace();

			jobCode = SystemStatus.ERROR_16;
		} finally {
			int rtn = SystemStatus.NORMAL;

			if (objDbAccess != null) {
				rtn = objDbAccess.disconnect();
				if (jobCode < rtn) {
					jobCode = rtn;
				}
			}

			if (jobCode == SystemStatus.NORMAL) {
				msg = String.format(MessageInfo.CP5J510002);
				System.out.println(objLogFm.format("CP5J510002-I", msg));
			} else
			{
				msg = String.format(MessageInfo.CP5J510004);
				System.out.println(objLogFm.format("CP5J510004-E", msg));
			}

			// 終了処理
			System.exit(jobCode);
		}
	}
}
