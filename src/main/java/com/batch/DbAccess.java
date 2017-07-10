package com.batch;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DB接続・切断を行うクラス
 * @author chou
 *
 */
public class DbAccess{

	// LogFormatter
	private LogFormatter objLogFm = null;

	// Connection
	private Connection mCon       = null;

	// HiRDB JDBC Driver
	private String mDbDriver      = null;

	// HiRDB JDBC URL
	private String mDbUrl         = null;

	// HiRDB User
	private String mDbUser        = null;

	// HiRDB Password
	private String mDbPass        = null;

	// ログ出力クラス(DEBUG 用)
	private static final Logger logger = LoggerFactory.getLogger(DbAccess.class);

	/**
	 * DB接続用変数の設定
	 * @param dbDriver
	 * @param dbUrl
	 * @param dbUser
	 * @param dbPass
	 */
	public void setConfig(String dbDriver, String dbUrl, String dbUser, String dbPass){
		mDbDriver = dbDriver;
		mDbUrl    = dbUrl;
		mDbUser   = dbUser;
		mDbPass   = dbPass;

	}


	/**
	 * DB接続メソッド
	 * @return
	 * @throws Cp5Exception
	 * @throws Exception
	 */
	public Connection connect() throws Cp5Exception, Exception {
		String msg = null;

		mCon = null;
		logger.debug("メソッド connect start");
		try {
			// オブジェクト生成
			objLogFm = new LogFormatter();

			// HiRDB JDBC Driver 登録
			Class.forName(mDbDriver);

			// HiRDB接続
			mCon = DriverManager.getConnection(mDbUrl, mDbUser, mDbPass);

		} catch (ClassNotFoundException ex1) {
			// ドライバ登録エラー
			msg = String.format(MessageInfo.CP5J010001, mDbDriver);
			throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J010001-E", msg);
		} catch (SQLException ex2) {
			// データベースエラー
			msg = String.format(MessageInfo.CP5J010002, ex2.getErrorCode());
			throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J010002-E", msg);
		} catch (Exception ex3) {
			// 予期しない例外発生
			throw ex3;
		}
		logger.debug("メソッド connect end");
		return mCon;
	}

	/**
	 * DB切断メソッド
	 * @return
	 */
	public int disconnect() {
		String msg = null;
		logger.debug("メソッド disconnect start");
		try {
			// オブジェクト生成
			objLogFm = new LogFormatter();

			// HiRDB切断
			if (mCon != null && !mCon.isClosed()) {
				mCon.close();
				msg = String.format(MessageInfo.CP5J010003);
				System.out.println(objLogFm.format("CP5J010003-I", msg));
			}
		} catch (SQLException ex1) {
			msg = String.format(MessageInfo.CP5J010004,  ex1.getErrorCode());
			System.out.println(objLogFm.format("CP5J010004-E", msg));

			return SystemStatus.ERROR_16;

		} catch (Exception ex2) {
			// 予期しない例外発生
			msg = String.format(MessageInfo.CP5J110028, ex2.getMessage());
			System.out.println(objLogFm.format("CP5J110028-E", msg));

			ex2.printStackTrace();

			return SystemStatus.ERROR_16;
		}

		logger.debug("メソッド disconnect end");
		// 正常終了
		return SystemStatus.NORMAL;
	}
}