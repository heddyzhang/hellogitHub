package com.batch;

/**
 * ジョブコードを扱うクラス。
 * @author chou
 *
 */
public class SystemStatus {

    /* ステータス(正常) */
    public static final int NORMAL   = 0;
    /* ステータス(警告) */
    public static final int WARNING  = 4;
    /* ステータス(異常_8) */
    public static final int ERROR_8  = 8;
	/* ステータス(異常_16) */
	public static final int ERROR_16 = 16;

    /* ステータス */
    private int mStatus = NORMAL;

    /**
     * ステータス取得メソッド
     * @return
     */
    public int getStatus() {
        return mStatus;
    }

    /**
     * ステータス4設定メソッド
     */
    public void setWarn() {
        if (mStatus <= NORMAL) {
            mStatus = WARNING;
        }
    }

    /**
     * ステータス8設定メソッド
     */
    public void setError_8() {
        if (mStatus <= WARNING) {
            mStatus = ERROR_8;
        }
    }

//    /**
//     * ステータス16設定メソッド
//     */
//    public void setError_16() {
//        if (mStatus <= ERROR_8) {
//            mStatus = ERROR_16;
//        }
//    }
}

