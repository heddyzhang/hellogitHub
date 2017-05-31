package com.batch.CP5P100;

import com.batch.Cp5Exception;
import com.batch.IComConst;
import com.batch.MessageInfo;
import com.batch.SystemInfo;
import com.batch.SystemStatus;

/**
 * 環境変数設定クラス
 * @author chou
 *
 */
public class CP5P100Env {

	// 共通検索一覧表示用情報(国内)(外国)(非特許)
    private String mFileCms = null;

    // 共通検索一覧表示用情報(国内)エラー一覧
    private String mFileErrCms = null;

    // 番号索引用情報データ(特許－出願情報)
    private String mFilePAppm = null;

    // 番号索引用情報データ(特許－審判情報)
    private String mFilePAppealm = null;

    // 番号索引用情報データ(実用－出願情報)
    private String mFileUAppm = null;

    // 番号索引用情報データ(実用－審判情報)
    private String mFileUAppealm = null;

	/**
	 * 環境変数設定メソッド
	 * @throws Cp5Exception
	 * @throws Exception
	 */
    public void setEnv(String shoriKbn) throws Cp5Exception, Exception {

    	String msg     = null;
        String envName = null;

        try {
	        // 共通検索一覧表示用情報(国内)(外国)(非特許)設定
            // 環境変数を取得
        	envName = SystemInfo.ENV_FILE_CMS;
        	mFileCms = System.getenv(envName);

        	// 取得値がnullの場合
            if (mFileCms == null) {
            	msg = String.format(MessageInfo.CP5J110009, envName);
            	throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110009-E", msg);
            }


	        // 共通検索一覧表示用情報(国内)(外国)(非特許)エラー一覧設定
            // 環境変数を取得
        	envName = SystemInfo.ENV_FILE_ERR_CMS;
        	mFileErrCms = System.getenv(envName);

        	// 取得値がnullの場合
            if (mFileErrCms == null) {
            	msg = String.format(MessageInfo.CP5J110009, envName);
            	throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110009-E", msg);
            }

            // 処理区分が国内の場合
            if (IComConst.SHORI_KBN_JP.equals(shoriKbn)) {
    	        // 番号索引用情報データ(特許－出願情報)設定
                // 環境変数を取得
            	envName = SystemInfo.ENV_FILE_P_APPM;
            	mFilePAppm = System.getenv(envName);

            	// 取得値がnullの場合
                if (mFilePAppm == null) {
                	msg = String.format(MessageInfo.CP5J110009, envName);
                	throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110009-E", msg);
                }

    	        // 番号索引用情報データ(特許－審判情報)設定
                // 環境変数を取得
            	envName = SystemInfo.ENV_FILE_P_APPEALM;
            	mFilePAppealm = System.getenv(envName);

            	// 取得値がnullの場合
                if (mFilePAppealm == null) {
                	msg = String.format(MessageInfo.CP5J110009, envName);
                	throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110009-E", msg);
                }

    	        // 番号索引用情報データ(実用－出願情報)設定
                // 環境変数を取得
            	envName = SystemInfo.ENV_FILE_U_APPM;
            	mFileUAppm = System.getenv(envName);

            	// 取得値がnullの場合
                if (mFileUAppm == null) {
                	msg = String.format(MessageInfo.CP5J110009, envName);
                	throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110009-E", msg);
                }

    	        // 番号索引用情報データ(実用－審判情報)設定
                // 環境変数を取得
            	envName = SystemInfo.ENV_FILE_U_APPEALM;
            	mFileUAppealm = System.getenv(envName);

            	// 取得値がnullの場合
                if (mFileUAppealm == null) {
                	msg = String.format(MessageInfo.CP5J110009, envName);
                	throw new Cp5Exception(SystemStatus.ERROR_16, "CP5J110009-E", msg);
                }
            }


		} catch (Cp5Exception ex1) {
			throw new Cp5Exception(ex1.getStatus(), ex1.getMessageId(), ex1.getMessage());
		} catch (Exception ex2) {
			// 予期しない例外発生
			throw new Exception();
		}
    }

	/**
	 * 共通検索一覧表示用情報(国内)(外国)(非特許)取得メソッド
	 *
	 * @return
	 */
	public String getFileCms() {
		return mFileCms;
	}

	/**
	 * 共通検索一覧表示用情報エラー一取得メソッド
	 *
	 * @return
	 */
	public String getFileErrCms() {
		return mFileErrCms;
	}

	/**
	 * 番号索引用情報データ(特許－出願情報)取得メソッド
	 * @return
	 */
	public String getFilePAppm() {
		return mFilePAppm;
	}

	/**
	 * 番号索引用情報データ(特許－審判情報)取得取得メソッド
	 * @return
	 */
	public String getFilePAppealm() {
		return mFilePAppealm;
	}

	/**
	 * 番号索引用情報データ(実用－出願情報)取得取得メソッド
	 * @return
	 */
	public String getFileUAppm() {
		return mFileUAppm;
	}

	/**
	 * 番号索引用情報データ(実用－審判情報)取得メソッド
	 * @return
	 */
	public String getFileUAppealm() {
		return mFileUAppealm;
	}
}
