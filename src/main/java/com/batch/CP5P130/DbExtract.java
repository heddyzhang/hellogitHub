package com.batch.CP5P130;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.batch.Cp5Exception;
import com.batch.MessageInfo;
import com.batch.SystemStatus;

/**
 * DB抽出クラス
 * @author chou
 *
 */
public class DbExtract{
	private PreparedStatement mStmt          = null;
	private ResultSet  mRs                   = null;
	private String     mTblName              = null;
	private List<RangeInfoBean> mDbItemsList = null;

	// ログ出力クラス(DEBUG 用)
	private static final Logger logger = LoggerFactory.getLogger(DbExtract.class);

	public DbExtract(String tblName) {
		mTblName   = tblName;
		logger.debug("メンバ変数mTblNameは" + mTblName);
	}

	/**
	 * DB検索メソッド
	 * @param Con
	 * @param query
	 * @throws Cp5Exception
	 * @throws SQLException
	 * @throws Exception
	 */
	public void select(Connection Con, String query) throws Cp5Exception, SQLException, Exception {
		String msg           = null;
		mStmt                = null;

		logger.debug("メソッドselect start");
		try {

			logger.debug("DB検索SQL文:" + query);
			// PreparedStatement オブジェクトの生成
			mStmt = Con.prepareStatement(query);
			// ResultSet取得
			mRs = mStmt.executeQuery();

			mDbItemsList = new ArrayList<RangeInfoBean>();

			while (mRs.next()) {
				RangeInfoBean dbItems = new RangeInfoBean();

				// 種別コード
				dbItems.setScd(mRs.getString(1));
				// 蓄積先頭番号
				dbItems.setDnoMin(mRs.getString(2));
				// 蓄積最終番号
				dbItems.setDnoMax(mRs.getString(3));
				// 公知日(年)
				dbItems.setIsdateMax(mRs.getString(4));
				// 蓄積総件数
				dbItems.setCount(mRs.getString(5));

				// 文蓄情報Bean型ListオブジェクトにDB検索結果を設定する。
				mDbItemsList.add(dbItems);

			}
			// DB検索結果がログに出力(DEBUG用)
			printDbItemsList();

		} catch (SQLException ex1) {
			// データベースエラー
			msg = String.format(MessageInfo.CP5J510009, mTblName, ex1.getErrorCode());
			throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J510009-E", msg);
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
		logger.debug("メソッドselect end");

		// 正常終了
		return;
	}

	/**
	 * DB検索結果を表示(DEBUG用)
	 * @throws Exception
	 */
	public void printDbItemsList() throws Exception {
		int i;

		for (i = 0; i < mDbItemsList.size(); i++) {
			logger.debug("****DEBUG DB検索結果" + (i + 1) + "行目表示 ***");
			logger.debug("SCD      =" + mDbItemsList.get(i).getScd());
			logger.debug("MIN(DNO) =" + mDbItemsList.get(i).getDnoMin());
			logger.debug("MAX(DNO) =" + mDbItemsList.get(i).getDnoMax());
			logger.debug("ISDATE   =" + mDbItemsList.get(i).getIsdateMax());
			logger.debug("COUNT    =" + mDbItemsList.get(i).getCount());
		}
		// 正常終了
		return;
	}

	/**
	 * 最小記事番号検索メソッド
	 * @param Con
	 * @param query
	 * @throws Cp5Exception
	 * @throws SQLException
	 * @throws Exception
	 */
	public void selectKy4Min(Connection Con, String query) throws Cp5Exception, SQLException, Exception {
		String msg = null;
		int i;
		mStmt = null;
		mRs = null;

		logger.debug("メソッドselectKy4Min start");
		try {

			logger.debug("最小記事番号取得SQL文:" + query);
			// PreparedStatement オブジェクトの生成
			mStmt = Con.prepareStatement(query);

			for (i = 0; i < mDbItemsList.size(); i++) {
				// ?パラメタの設定
				mStmt.setString(1, mDbItemsList.get(i).getScd());
				mStmt.setString(2, mDbItemsList.get(i).getDnoMin());

				logger.debug("パラメタ1は" + mDbItemsList.get(i).getScd());
				logger.debug("パラメタ2は" + mDbItemsList.get(i).getDnoMin());
				// ResultSet取得
				mRs = mStmt.executeQuery();

				//フェッチ
				mRs.next();

				// 代表文献番号(記事番号)の最小記事番号
				mDbItemsList.get(i).setKy4Min(mRs.getString(1));
				logger.debug((i + 1) + "行目の最小記事番号は" + mDbItemsList.get(i).getKy4Min());
			}
		} catch (SQLException ex1) {
			// データベースエラー
			msg = String.format(MessageInfo.CP5J510009, mTblName, ex1.getErrorCode());
			throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J510009-E", msg);

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
		logger.debug("メソッドselectKy4Min end");
		// 正常終了
		return;
	}

	/**
	 * 最大記事番号検索メソッド
	 * @param Con
	 * @param query
	 * @throws Cp5Exception
	 * @throws SQLException
	 * @throws Exception
	 */
	public void selectKy4Max(Connection Con, String query) throws Cp5Exception, SQLException, Exception {
		String msg = null;
		int i;
		mStmt = null;
		mRs = null;

		logger.debug("メソッドselectKy4Max start");
		try {

			logger.debug("最大記事番号取得SQL文:" + query);
			// PreparedStatement オブジェクトの生成
			mStmt = Con.prepareStatement(query);

			for (i = 0; i < mDbItemsList.size(); i++) {
				// ?パラメタの設定
				mStmt.setString(1, mDbItemsList.get(i).getScd());
				mStmt.setString(2, mDbItemsList.get(i).getDnoMax());

				logger.debug("パラメタ1は" + mDbItemsList.get(i).getScd());
				logger.debug("パラメタ2は" + mDbItemsList.get(i).getDnoMax());
				// ResultSet取得
				mRs = mStmt.executeQuery();

				//フェッチ
				mRs.next();

				// 代表文献番号(記事番号)の最大記事番号
				mDbItemsList.get(i).setKy4Max(mRs.getString(1));
				logger.debug((i + 1) + "行目の最大記事番号は" + mDbItemsList.get(i).getKy4Max());
			}
		} catch (SQLException ex1) {
			// データベースエラー
			msg = String.format(MessageInfo.CP5J510009, mTblName, ex1.getErrorCode());
			throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J510009-E", msg);

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
		logger.debug("メソッドselectKy4Max end");
		// 正常終了
		return;
	}

	/**
	 * フェッチ数取得メソッド
	 * @return
	 */
	public int getFetchNum() {
		return mDbItemsList.size();
	}

	/**
	 * 種別コード取得メソッド
	 * @param idx
	 * @return
	 */
	public String getScd(int idx) {
		return mDbItemsList.get(idx).getScd();
	}

	/**
	 * 蓄積先頭番号取得メソッド
	 * @param idx
	 * @return
	 */
	public String getDnoMin(int idx) {
		return mDbItemsList.get(idx).getDnoMin();
	}

	/**
	 * 蓄積最終番号取得メソッド
	 * @param idx
	 * @return
	 */
	public String getDnoMax(int idx) {
		return mDbItemsList.get(idx).getDnoMax();
	}

	/**
	 * 公知日(年)取得メソッド
	 * @param idx
	 * @return
	 */
	public String getIsdateMax(int idx) {
		return mDbItemsList.get(idx).getIsdateMax();
	}

	/**
	 * 蓄積総件数取得メソッド
	 * @param idx
	 * @return
	 */
	public String getCount(int idx) {
		return mDbItemsList.get(idx).getCount();
	}

	/**
	 * 最小記事番号取得メソッド
	 * @param idx
	 * @return
	 */
	public String getKy4Min(int idx) {
		return mDbItemsList.get(idx).getKy4Min();
	}

	/**
	 * 最大記事番号取得メソッド
	 * @param idx
	 * @return
	 */
	public String getKy4Max(int idx) {
		return mDbItemsList.get(idx).getKy4Max();
	}

}
