package com.batch;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ログファイル出力用の書式整形クラス
 * @author chou
 *
 */
public class LogFormatter {

	/**
	 * ログ書式整形メソッド
	 * @param messageId
	 * @param message
	 * @return
	 */
	public String format(String messageId, String message) {

		// オブジェクト生成
		Date objDate = new Date();
		SimpleDateFormat objSdFormat = new SimpleDateFormat(SystemInfo.DATETIME_FORMAT);

		// ログフォーマット整形
        return String.format(SystemInfo.LOG_FORMAT, messageId, objSdFormat.format(objDate), message);
    }
}