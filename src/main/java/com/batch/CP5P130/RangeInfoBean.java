package com.batch.CP5P130;

/**
 * 文蓄情報を格納するクラス。
 * @author chou
 *
 */
class RangeInfoBean {

	// 文献種別ID
	private String mBid = "";
	// 文献名称
	private String mBnm = "";
	// 国コード
	private String mCtry = "";
	// 文献種別
	private String mKind = "";

	// 種別コード
	private String mScd = "";
	// 蓄積先頭番号
	private String mDnoMin = "";
	// 蓄積最終番号
	private String mDnoMax = "";
	// 公知日(年)
	private String mIsdateMax = "";
	// 蓄積総件数
	private String mCount = "";
	// 最小記事番号
	private String mKy4Min = "";
	// 最大記事番号
	private String mKy4Max = "";

	/**
	 * 文献種別ID取得メソッド
	 * @return mBid
	 */
	public String getBid() {
		return mBid;
	}

	/**
	 * 文献名称取得メソッド
	 * @return mBnm
	 */
	public String getBnm() {
		return mBnm;
	}

	/**
	 * 国コード取得メソッド
	 * @return mCtry
	 */
	public String getCtry() {
		return mCtry;
	}

	/**
	 * 文献種別取得メソッド
	 * @return mKind
	 */
	public String getKind() {
		return mKind;
	}

	/**
	 * 種別コード取得メソッド
	 * @return
	 */
	public String getScd() {
		return mScd;
	}

	/**
	 * 蓄積先頭番号取得メソッド
	 * @return
	 */
	public String getDnoMin() {
		return mDnoMin;
	}

	/**
	 * 蓄積最終番号取得メソッド
	 * @return
	 */
	public String getDnoMax() {
		return mDnoMax;
	}


	/**
	 * 公知日(年)取得メソッド
	 * @return
	 */
	public String getIsdateMax() {
		return mIsdateMax;
	}

	/**
	 * 蓄積総件数取得メソッド
	 * @return
	 */
	public String getCount() {
		return mCount;
	}


	/**
	 * 最小記事番号取得メソッド
	 * @return
	 */
	public String getKy4Min() {
		return mKy4Min;
	}

	/**
	 * 最大記事番号取得メソッド
	 * @return
	 */
	public String getKy4Max() {
		return mKy4Max;
	}

	/**
	 * 文献名称設定メソッド
	 * @param bnm
	 */
	public void setBnm(String bnm) {
		this.mBnm = bnm;
	}

	/**
	 * 国コード設定メソッド
	 * @param ctry
	 */
	public void setCtry(String ctry) {
		this.mCtry = ctry;
	}

	/**
	 * 文献種別設定メソッド
	 * @param kind
	 */
	public void setKind(String kind) {
		this.mKind = kind;
	}

	/**
	 * 種別コード設定メソッド
	 * @param scd
	 */
	public void setScd(String scd) {
		this.mScd = scd;
	}

	/**
	 * 蓄積先頭番号設定メソッド
	 * @param scd
	 */
	public void setDnoMin(String dno) {
		this.mDnoMin = dno;
	}

	/**
	 * 蓄積最終番号設定メソッド
	 * @param scd
	 */
	public void setDnoMax(String dno) {
		this.mDnoMax = dno;
	}

	/**
	 * 文献種別ID設定メソッド
	 * @param bid
	 */
	public void setBid(String bid) {
		this.mBid = bid;
	}

	/**
	 * 公知日(年)設定メソッド
	 * @param scd
	 */
	public void setIsdateMax(String date) {
		this.mIsdateMax = date;
	}

	/**
	 * 蓄積総件数設定メソッド
	 * @param scd
	 */
	public void setCount(String count) {
		this.mCount = count;
	}

	/**
	 * 最小記事番号設定メソッド
	 * @param scd
	 */
	public void setKy4Min(String dno) {
		this.mKy4Min = dno;
	}

	/**
	 * 最大記事番号設定メソッド
	 * @param scd
	 */
	public void setKy4Max(String dno) {
		this.mKy4Max = dno;
	}
}
