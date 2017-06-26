package com.batch.CP5P130;

import org.apache.log4j.Logger;

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

	// ログ出力クラス(DEBUG 用)
	private static final Logger logger = Logger.getLogger(RangeDefineBean.class);

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
		logger.debug("メンバ変数文献種別ID設定後は" + mBid);
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
		logger.debug("メンバ変数文献名称設定後は" + mBnm);
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
		logger.debug("メンバ変数種別コード設定後は" + mScd);
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
		logger.debug("メンバ変数国コード設定後は" + mCtry);
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
		logger.debug("メンバ変数文献種別設定後は" + mKind);
	}
}
