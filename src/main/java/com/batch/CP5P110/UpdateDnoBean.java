package com.batch.CP5P110;

/**
 * 更新文献Beanクラス
 * @author chou
 *
 */
public class UpdateDnoBean {

	// 種別コード
	private String mScd = "";

	// 文献番号
	private String mDno = "";

	// ISN
	private String mIsn = "";

	/**
	 * 種別コード取得メソッド
	 * @return
	 */
	public String getScd() {
		return mScd;
	}

	/**
	 * 文献番号取得メソッド
	 * @return
	 */
	public String getDno() {
		return mDno;
	}

	/**
	 * ISN取得メソッド
	 * @return
	 */
	public String getIsn() {
		return mIsn;
	}

	/**
	 * 種別コード設定メソッド
	 * @param mScd
	 */
	public void setScd(String mScd) {
		this.mScd = mScd;
	}

	/**
	 * 文献番号設定メソッド
	 * @param mDno
	 */
	public void setDno(String mDno) {
		this.mDno = mDno;
	}

	/**
	 * ISN設定メソッド
	 * @param mIsn
	 */
	public void setIsn(String mIsn) {
		this.mIsn = mIsn;
	}

}
