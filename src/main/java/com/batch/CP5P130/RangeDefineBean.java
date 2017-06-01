package com.batch.CP5P130;

/**
 * 文蓄定義を格納するクラス
 * @author chou
 *
 */
class RangeDefineBean {

	// 文献種別ID
	private String mBid;
	// 文献名称
	private String mBnm;
	// 種別コード
	private String mScd;
	// 国コード
	private String mCtry;
	// 文献種別
	private String mKind;

	/**
	 * 文献種別ID取得メソッド
	 * @return mBid
	 */
	public String getBid() {
		return mBid;
	}

	/**
	 * 文献種別ID設定メソッド
	 * @param bid
	 */
	public void setBid(String bid) {
		this.mBid = bid;
	}

	/**
	 * 文献名称取得メソッド
	 * @return mBnm
	 */
	public String getBnm() {
		return mBnm;
	}

	/**
	 * 文献名称設定メソッド
	 * @param bnm
	 */
	public void setBnm(String bnm) {
		this.mBnm = bnm;
	}

	/**
	 * 種別コード取得メソッド
	 * @return mScd
	 */
	public String getScd() {
		return mScd;
	}

	/**
	 * 種別コード設定メソッド
	 * @param scd
	 */
	public void setScd(String scd) {
		this.mScd = scd;
	}


	/**
	 * 国コード取得メソッド
	 * @return mCtry
	 */
	public String getCtry() {
		return mCtry;
	}

	/**
	 * 国コード設定メソッド
	 * @param ctry
	 */
	public void setCtry(String ctry) {
		this.mCtry = ctry;
	}

	/**
	 * 文献種別取得メソッド
	 * @return mKind
	 */
	public String getKind() {
		return mKind;
	}

	/**
	 * 文献種別設定メソッド
	 * @param kind
	 */
	public void setKind(String kind) {
		this.mKind = kind;
	}
}
