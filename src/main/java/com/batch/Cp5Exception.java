package com.batch;

import com.batch.SystemStatus;

/**
 * CP5用例外クラス
 * @author chou
 *
 */
public class Cp5Exception extends Exception {

    // システムステータス
    private int mStatus = SystemStatus.NORMAL;
    // メッセージID
    private String mMessageId = null;
    // メッセージ
    private String mMessage = null;

	// コンストラクタ
    public Cp5Exception(int status, String messageId, String message) {
        mStatus    = status;
        mMessageId = messageId;
        mMessage   = message;
    }

	// ステータス取得
    public int getStatus() {
        return mStatus;
    }

    // メッセージID取得
    public String getMessageId() {
        return mMessageId;
    }

    // メッセージを取得
    public String getMessage() {
        return mMessage;
    }
}

