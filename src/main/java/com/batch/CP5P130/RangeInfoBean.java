package com.batch.CP5P130;

import org.apache.log4j.Logger;

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

	// ログ出力クラス(DEBUG 用)
	private static final Logger logger = Logger.getLogger(RangeInfoBean.class);

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
		logger.debug("文献名称取得メソッドの戻り値は" + mBnm);
		return mBnm;
	}

	/**
	 * 国コード取得メソッド
	 * @return mCtry
	 */
	public String getCtry() {
		logger.debug("国コード取得メソッドの戻り値は" + mCtry);
		return mCtry;
	}

	/**
	 * 文献種別取得メソッド
	 * @return mKind
	 */
	public String getKind() {
		logger.debug("文献種別取得の戻り値は" + mKind);
		return mKind;
	}

	/**
	 * 種別コード取得メソッド
	 * @return
	 */
	public String getScd() {
		logger.debug("種別コード取得の戻り値は" + mScd);
		return mScd;
	}

	/**
	 * 蓄積先頭番号取得メソッド
	 * @return
	 */
	public String getDnoMin() {
		logger.debug("蓄積先頭番号取得の戻り値は" + mDnoMin);
		return mDnoMin;
	}

	/**
	 * 蓄積最終番号取得メソッド
	 * @return
	 */
	public String getDnoMax() {
		logger.debug("蓄積最終番号取得の戻り値は" + mDnoMax);
		return mDnoMax;
	}


	/**
	 * 公知日(年)取得メソッド
	 * @return
	 */
	public String getIsdateMax() {
		logger.debug("公知日(年)取得の戻り値は" + mIsdateMax);
		return mIsdateMax;
	}

	/**
	 * 蓄積総件数取得メソッド
	 * @return
	 */
	public String getCount() {
		logger.debug("蓄積総件数取得の戻り値は" + mCount);
		return mCount;
	}


	/**
	 * 最小記事番号取得メソッド
	 * @return
	 */
	public String getKy4Min() {
		logger.debug("最小記事番号取得の戻り値は" + mKy4Min);
		return mKy4Min;
	}

	/**
	 * 最大記事番号取得メソッド
	 * @return
	 */
	public String getKy4Max() {
		logger.debug("最大記事番号取得の戻り値は" + mKy4Max);
		return mKy4Max;
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
	 * 国コード設定メソッド
	 * @param ctry
	 */
	public void setCtry(String ctry) {
		this.mCtry = ctry;
		logger.debug("メンバ変数国コード設定後は" + mCtry);
	}

	/**
	 * 文献種別設定メソッド
	 * @param kind
	 */
	public void setKind(String kind) {
		this.mKind = kind;
		logger.debug("メンバ変数文献種別設定後は" + mKind);
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
	 * 蓄積先頭番号設定メソッド
	 * @param scd
	 */
	public void setDnoMin(String dno) {
		this.mDnoMin = dno;
		logger.debug("メンバ変数蓄積先頭番号設定後は" + mDnoMin);
	}

	/**
	 * 蓄積最終番号設定メソッド
	 * @param scd
	 */
	public void setDnoMax(String dno) {
		this.mDnoMax = dno;
		logger.debug("メンバ変数蓄積最終番号設定後は" + mDnoMax);
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
	 * 公知日(年)設定メソッド
	 * @param scd
	 */
	public void setIsdateMax(String date) {
		this.mIsdateMax = date;
		logger.debug("メンバ変数公知日(年)設定後は" + mIsdateMax);
	}

	/**
	 * 蓄積総件数設定メソッド
	 * @param scd
	 */
	public void setCount(String count) {
		this.mCount = count;
		logger.debug("メンバ変数蓄積総件数設定後は" + mCount);
	}

	/**
	 * 最小記事番号設定メソッド
	 * @param scd
	 */
	public void setKy4Min(String dno) {
		this.mKy4Min = dno;
		logger.debug("メンバ変数最小記事番号設定後は" + mKy4Min);
	}

	/**
	 * 最大記事番号設定メソッド
	 * @param scd
	 */
	public void setKy4Max(String dno) {
		this.mKy4Max = dno;
		logger.debug("メンバ変数最大記事番号設定後は" + mKy4Max);
	}
}
