package com.batch.CP5P100;

/**
 * JPP表示用情報Beanクラス
 * @author chou
 *
 */
public class JppInfoBean {

	// 項目番号
	private String mNo = "";
	// 項目
	private String mItem = "";

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
	 * 項目取得メソッド
	 * @return
	 */
	public String getItem() {
		return mItem;
	}

	/**
	 * 項目設定メソッド
	 * @param mItem
	 */
	public void setItem(String mItem) {
		this.mItem = mItem;
	}

}
