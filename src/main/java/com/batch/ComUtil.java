package com.batch;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 共通メッソドクラス
 * @author chou
 *
 */
public class ComUtil {

	// フォーマットチェック用の正規表現パターン
	private static Pattern PATTERN_NUMBER = Pattern.compile("^(-|)(0|[1-9]{1}[0-9]*)([\\.]{1}[0-9]{1}\\d*|)$");// 半角数値
	private static Pattern PATTERN_ALPHA_NUM = Pattern.compile("^[0-9a-zA-Z\\s]+$");// 半角英数字

	/**
	 * 文字列が空白かどうか
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {

		if ((s == null) || (s.trim().length() == 0))
			return true;

		return false;
	}

	/**
	 * 指定された長さ(第2引数)になるまで文字列の左側(先頭)をゼロで埋めます。
	 * @param value
	 * @param length
	 * @return
	 */
	public static String leftPadZero(String value, int length) {
		if (isEmpty(value))
			return value;

		return StringUtils.leftPad(value.trim(), length, "0");
	}

	/**
	 * 比較メソッド(最大値)
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean compareBigger(String str1, String str2) {

		if (isEmpty(str2))
			return true;

		if (isEmpty(str1))
			return false;

		if (Long.parseLong(str1) > Long.parseLong(str2))
			return true;

		return false;
	}

	/**
	 * 比較メソッド(最小値)
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean compareSmaller(String str1, String str2) {

		if (isEmpty(str2))
			return true;

		if (isEmpty(str1))
			return false;

		if (Long.parseLong(str1) < Long.parseLong(str2))
			return true;

		return false;
	}

	/**
	 * 値が半角英数字かどうかチェック
	 *
	 * @param data
	 *            validate対象データ
	 * @return true：半角英数字である
	 *         false：半角英数字ではない
	 */
	public static boolean isAlphaNum(String data) {

		Matcher mc = PATTERN_ALPHA_NUM.matcher(data);
		return mc.matches();
	}

	/**
	 * 文字列がNumberかどうかを判定します。
	 *
	 * @param data
	 *            文字列
	 * @return true：Numberである
	 *         false：Numberでない
	 */
	public static boolean isNumber(String data) {

		Matcher mc = PATTERN_NUMBER.matcher(data);
		return mc.matches();
	}

	/**
	 * 和暦⇒西暦変換を行う
	 * @param date 和暦年月日(和暦元号(1:明治 2:大正 3:昭和 4:平成)+和暦年(YY))
	 * @return String 西暦年号(YYYY)
	 * */
	public static String convertJc2Ad(String date){

		int imperialCd = toint(date.substring(0, 1));
		if ( imperialCd == 0 ) {
			return "";
		}

		Calendar cal = Calendar.getInstance(new Locale("ja","JP","JP"));

		cal.set(Calendar.ERA, imperialCd);
		cal.set(toint(date.substring(1,3)), 1-1, 1);
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd", Locale.JAPAN);

		return dateFormat1.format(cal.getTime()).substring(0,4);
	}

	/**
	 * 文字列をint数値に変換します。
	 *
	 * @param s
	 *          文字列
	 * @return 数値
	 */
	public static int toint(String s) {

		if (isEmpty(s))
			return 0;

		return Integer.parseInt(s);
	}

	/**
	 * 文字列をlong数値に変換します。
	 *
	 * @param s
	 *          文字列
	 * @return 数値
	 */
	public static long toLng(String s) {

		if (isEmpty(s))
			return 0;

		return Long.parseLong(s);
	}

	/**
	 * 空白文字列をnullに変換
	 * @param str
	 * @return
	 */
	public static String convEmptyToNull(String str) {

		if (isEmpty(str)){
			return null;
		} else {
			return str;
		}

	}


	/**
	 * 文字列バイト数を取得
	 * @param string
	 * @param charset
	 * @return
	 */
    public static int getByteLength(String str, String charset) {

    	return str.getBytes(Charset.forName(charset)).length;
    }

    /**
     * 文字列切り出し
     * 先頭から指定バイト数分文字列を切り出す
     * 切り出し終了部分が日本語の途中にかかる場合は直前の文字までを切り出す
     * @param str
     * @param len
     * @param charset
     * @return
     */
    public static String leftB(String str, Integer len, String charset){

    	StringBuffer sb = new StringBuffer();
        int cnt = 0;

        for (int i = 0; i < str.length(); i++) {
          String tmpStr = str.substring(i, i + 1);
          int bLen = getByteLength(tmpStr, charset);
          if (cnt + bLen > len) {
            return sb.toString();
          } else {
            sb.append(tmpStr);
            cnt += bLen;
          }
        }

        return sb.toString();
    }

}

