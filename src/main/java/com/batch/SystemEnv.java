package com.batch;

import org.apache.log4j.Logger;

/**
 * 環境変数を設定するクラス。
 * @author chou
 *
 */
public class SystemEnv {
	// LogFormatter
	//private LogFormatter objLogFm = null;

    // PDHOST
    private String mPdnameportVal = null;

	// PDNAMEPORT
	private String mPdhostVal     = null;

	// ログ出力クラス(DEBUG 用)
	private static final Logger logger = Logger.getLogger(SystemEnv.class);

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

            // PDNAMEPORT
        	envName = SystemInfo.ENV_PDNAMEPORT;
        	mPdnameportVal = System.getenv(envName);
        	//mPdnameportVal = "15108";
        	logger.debug("環境変数PDNAMEPORTは" + mPdnameportVal);

            if (mPdnameportVal == null) {
            	msg = String.format(MessageInfo.CP5J110009, envName);
            	throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110009-E", msg);
            }

			// PDHOST
        	envName = SystemInfo.ENV_PDHOST;
        	mPdhostVal = System.getenv(envName);
        	//mPdhostVal = "dsdb02d-db-clt-d-v";
        	logger.debug("環境変数PDHOSTは" + mPdhostVal);

            if (mPdhostVal == null) {
            	msg = String.format(MessageInfo.CP5J110009, envName);
            	throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110009-E", msg);
            }
        } catch (Cp5Exception ex1) {
            throw new Cp5Exception(ex1.getStatus(), ex1.getMessageId(), ex1.getMessage());
        } catch (Exception ex2) {
        	// 予期しない例外発生
        	throw new Exception();
        }

        logger.debug("メッソド setEnv end");
        // 正常終了
    	return;
    }

	/**
	 * DBIDを取得メソッド
	 * @return mPdnameportVal
	 */
    public String getPdnameportVal() {
        return mPdnameportVal;
    }

    /**
     * DBHOSTを取得メソッド
     * @return mPdhostVal
     */
    public String getPdhostVal() {
        return mPdhostVal;
    }
}
