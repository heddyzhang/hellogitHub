package com.batch.CP5P100;

/**
 * データ作成定義Beanクラス
 * @author chou
 *
 */
public class MakeDataDefineBean {

	// 項目番号
	private String mNo = "";

	// 項目タイプ
	private String mType = "";

	// レベル(子項目数)
	private String mLevel = "";

	// 要否区分
	private String mKubun = "";

	// 長さ(Byte)最大値
	private String mMaxLen = "";

//	// 長さ(Byte)有効値
//	private String mLen = "";

	// 半角数字チェックフラグ
	private String m09ChkFlg = "";

	// 半角英数字チェックフラグ
	private String m09azAZChkFlg = "";

	// 繰り返し数最大値
	private String mMaxRepeat = "";

//	// 繰り返し数有効値
//	private String mRepeat = "";

	/**
	 * 項目番号取得メソッド
	 * @return
	 */
	public String getNo() {
		return mNo;
	}

	/**
	 * 項目番号設定メソッド
	 * @param mNo
	 */
	public void setNo(String mNo) {
		this.mNo = mNo;
	}

	/**
	 * 項目タイプ取得メソッド
	 * @return
	 */
	public String getType() {
		return mType;
	}

	/**
	 * 項目タイプ設定メソッド
	 * @param mType
	 */
	public void setType(String mType) {
		this.mType = mType;
	}

	/**
	 * 項目レベル(子項目数)取得メソッド
	 * @return
	 */
	public String getLevel() {
		return mLevel;
	}

	/**
	 * 項目レベル(子項目数)設定メソッド
	 * @param mLevel
	 */
	public void setLevel(String mLevel) {
		this.mLevel = mLevel;
	}

	/**
	 * 要否区分取得メソッド
	 * @return
	 */
	public String getKubun() {
		return mKubun;
	}

	/**
	 * 要否区分設定メソッド
	 * @param mKubun
	 */
	public void setKubun(String mKubun) {
		this.mKubun = mKubun;
	}

	/**
	 * 長さ(Byte)最大値取得メソッド
	 * @return
	 */
	public String getMaxLen() {
		return mMaxLen;
	}

	/**
	 * 長さ(Byte)最大値設定メソッド
	 * @param mMaxLen
	 */
	public void setMaxLen(String mMaxLen) {
		this.mMaxLen = mMaxLen;
	}

//	/**
//	 * 長さ(Byte)有効値取得メソッド
//	 * @return
//	 */
//	public String getLen() {
//		return mLen;
//	}
//
//	/**
//	 * 長さ(Byte)有効値設定メソッド
//	 * @param mLen
//	 */
//	public void setLen(String mLen) {
//		this.mLen = mLen;
//	}

	/**
	 * 半角数字チェックフラグ取得メソッド
	 * @return
	 */
	public String getM09ChkFlg() {
		return m09ChkFlg;
	}

	/**
	 * 半角数字チェックフラグ設定メソッド
	 * @param m09ChkFlg
	 */
	public void setM09ChkFlg(String m09ChkFlg) {
		this.m09ChkFlg = m09ChkFlg;
	}

	/**
	 * 半角英数字チェックフラグ取得メソッド
	 * @return
	 */
	public String getM09azAZChkFlg() {
		return m09azAZChkFlg;
	}

	/**
	 * 半角英数字チェックフラグ設定メソッド
	 * @param m09azAZChkFlg
	 */
	public void setM09azAZChkFlg(String m09azAZChkFlg) {
		this.m09azAZChkFlg = m09azAZChkFlg;
	}

	/**
	 * 繰り返し数最大値取得メソッド
	 * @return
	 */
	public String getMaxRepeat() {
		return mMaxRepeat;
	}

	/**
	 * 繰り返し数最大値設定メソッド
	 * @param mMaxRepeat
	 */
	public void setMaxRepeat(String mMaxRepeat) {
		this.mMaxRepeat = mMaxRepeat;
	}

//	/**
//	 * 繰り返し数有効値取得メソッド
//	 * @return
//	 */
//	public String getRepeat() {
//		return mRepeat;
//	}
//
//	/**
//	 * 繰り返し数有効値設定メソッド
//	 * @param mRepeat
//	 */
//	public void setRepeat(String mRepeat) {
//		this.mRepeat = mRepeat;
//	}
}
