package com.batch.CP5P110;

import org.apache.log4j.Logger;

import com.batch.Cp5Exception;
import com.batch.MessageInfo;
import com.batch.SystemInfo;
import com.batch.SystemStatus;

/**
 * 環境変数設定クラス
 * @author chou
 *
 */
public class CP5P110Env {

	 // DBコミット単位
	 private String mCmtLmtVal = null;

	// 共通検索一覧表示用情報(国内)(外国)(非特許)
    private String mFileCms = null;

    // 蓄積確認パラメータ(国内)(外国)(非特許)
    private String mFileChkPrm = null;

	// ログ出力クラス(DEBUG 用)
	private static final Logger logger = Logger.getLogger(CP5P110Env.class);

	/**
	 * 環境変数設定メソッド
	 * @throws Cp5Exception
	 * @throws Exception
	 */
    public void setEnv() throws Cp5Exception, Exception {

    	String msg     = null;
        String envName = null;

        logger.debug("メッソド setEnv start");
        try {

        	// DBコミット単位設定
            // 環境変数を取得
        	envName = SystemInfo.ENV_COMMIT_LIMIT;
        	mCmtLmtVal = System.getenv(envName);
        	logger.debug("DBコミット単位(環境変数)は" + mCmtLmtVal);

        	// 取得値がnullの場合
            if (mCmtLmtVal == null) {
            	msg = String.format(MessageInfo.CP5J110009, envName);
            	throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110009-E", msg);
            }

	        // 共通検索一覧表示用情報(国内)(外国)(非特許)設定
            // 環境変数を取得
        	envName = SystemInfo.ENV_FILE_CMS;
        	mFileCms = System.getenv(envName);
        	logger.debug("共通検索一覧表示用情報(環境変数)は" + mFileCms);

        	// 取得値がnullの場合
            if (mFileCms == null) {
            	msg = String.format(MessageInfo.CP5J110009, envName);
            	throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110009-E", msg);
            }

            // 蓄積パラメータ(国内)(外国)(非特許)設定
            // 環境変数を取得
            envName = SystemInfo.ENV_FILE_CHKPRM;
            mFileChkPrm = System.getenv(envName);
            logger.debug("蓄積パラメータ(環境変数)は" + mFileChkPrm);

        	// 取得値がnullの場合
            if (mFileChkPrm == null) {
            	msg = String.format(MessageInfo.CP5J110009, envName);
            	throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110009-E", msg);
            }

            logger.debug("メッソド setEnv end");
		} catch (Cp5Exception ex1) {
			throw new Cp5Exception(ex1.getStatus(), ex1.getMessageId(), ex1.getMessage());
		} catch (Exception ex2) {
			// 予期しない例外発生
			throw new Exception();
		}
    }

    /**
     * DBコミット単位取得メソッド
     * @return
     */
    public String getCmtLmtVal() {
		return mCmtLmtVal;
	}

	/**
     * 共通検索一覧表示用情報取得メソッド
     * @return
     */
	public String getFileCms() {
		return mFileCms;
	}

//	/**
//     * 共通検索一覧表示用情報(国内)取得メソッド
//     * @return
//     */
//	public String getFileCmsjptbl() {
//		return mFileCmsjptbl;
//	}
//
//	/**
//	 * 共通検索一覧表示用情報(外国)取得メソッド
//	 * @return
//	 */
//	public String getFileCmsfptbl() {
//		return mFileCmsfptbl;
//	}
//
//	/**
//	 * 共通検索一覧表示用情報(非特許)取得メソッド
//	 * @return
//	 */
//	public String getFileCmsnptbl() {
//		return mFileCmsnptbl;
//	}

	/**
	 * 蓄積確認パラメータ取得メソッド
	 * @return
	 */
	public String getFileChkPrm() {
		return mFileChkPrm;
	}

//	/**
//	 * 蓄積確認パラメータ(国内)取得メソッド
//	 * @return
//	 */
//	public String getFileChkPrmJ() {
//		return mFileChkPrmJ;
//	}
//
//	/**
//	 * 蓄積確認パラメータ(外国)取得メソッド
//	 * @return
//	 */
//	public String getFileChkPrmF() {
//		return mFileChkPrmF;
//	}
//
//	/**
//	 * 蓄積確認パラメータ(非特許)取得メソッド
//	 * @return
//	 */
//	public String getFileChkPrmN() {
//		return mFileChkPrmN;
//	}
}
