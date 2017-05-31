package com.batch.CP5P100;

/**
 * 共通検索一覧表示用情報出力クラス
 * @author chou
 *
 */
public class CmsInfoBean {

	// ISN
	private String mIsn = "";

	// 出願番号
	private String mAi = "";

	// 代表文献番号(公開／公告番号)
	private String mKy = "";

	// 文献番号
	private String mKz = "";

	// 公告番号・明細番号・JIS番号
	private String mPn = "";

	// 資料型
	private String mSr = "";

	// 出願日
	private String mAd = "";

	// 公知日
	private String mHk = "";

	// 公開日
	private String mKd = "";

	// 公告日
	private String mPd = "";

	// 登録日
	private String mRd = "";

	// 登録公報発行日
	private String mRj = "";

	// 公表日
	private String mTpnd = "";

	// 再公表発行日
	private String mRtpnd = "";

	// 審判番号返し数
	private String mAplnNum = "";

	// 審判番号
	private String mApln = "";

	// 国際公開日
	private String mPctd = "";

	// 申請人識別番号
	private String mIdn = "";

	// 優先権主張番号
	private String mPrin = "";

	// 発明名称
	private String mDa = "";

	// 出願人
	private String mGo = "";

	//  FI
	private String mFi = "";

	// 発明番号
	private String mNo = "";

	// タームA
	private String mAa = "";

	// タームB
	private String mBb = "";

	// タームC
	private String mCc = "";

	// タームD
	private String mDd = "";

	// 合金フリーワード
	private String mFw = "";

	// 種別コード(代表文献番号)
	private String mScdKy = "";

	// 種別コード(文献番号)
	private String mScdKz = "";

	// 検索コード
	private String mSltCd = "";

	// 代表文献番号(非特許種別)
	private String mKy1 = "";

	// 代表文献番号(文献種別)
	private String mKy2 = "";

	// 代表文献番号(書籍番号)
	private String mKy3 = "";

	// 代表文献番号(記事番号)
	private String mKy4 = "";

	// 発明名称／書籍タイトル
	private String mHi = "";

	// 文献タイトル
	private String mTl = "";

	// Fターム
	private String mFt = "";

	// IPC
	private String mIc = "";

	/**
	 * ISN取得メソッド
	 * @return
	 */
	public String getIsn() {
		return mIsn;
	}

	/**
	 * 出願番号取得メソッド
	 * @return
	 */
	public String getAi() {
		return mAi;
	}

	/**
	 * 代表文献番号(公開／公告番号)取得メソッド
	 * @return
	 */
	public String getKy() {
		return mKy;
	}

	/**
	 * 文献番号取得メソッド
	 * @return
	 */
	public String getKz() {
		return mKz;
	}

	/**
	 * 公告番号・明細番号・JIS番号取得メソッド
	 * @return
	 */
	public String getPn() {
		return mPn;
	}

	/**
	 * 資料型取得メソッド
	 * @return
	 */
	public String getSr() {
		return mSr;
	}

	/**
	 * 出願日取得メソッド
	 * @return
	 */
	public String getAd() {
		return mAd;
	}

	/**
	 * 公知日取得メソッド
	 * @return
	 */
	public String getHk() {
		return mHk;
	}

	/**
	 * 公開日取得メソッド
	 * @return
	 */
	public String getKd() {
		return mKd;
	}

	/**
	 * 公告日取得メソッド
	 * @return
	 */
	public String getPd() {
		return mPd;
	}

	/**
	 * 登録日取得メソッド
	 * @return
	 */
	public String getRd() {
		return mRd;
	}

	/**
	 * 登録公報発行日取得メソッド
	 * @return
	 */
	public String getRj() {
		return mRj;
	}

	/**
	 * 公表日取得メソッド
	 * @return
	 */
	public String getTpnd() {
		return mTpnd;
	}

	/**
	 * 再公表発行日取得メソッド
	 * @return
	 */
	public String getRtpnd() {
		return mRtpnd;
	}

	/**
	 * 審判番号繰り返し数取得メソッド
	 * @return
	 */
	public String getAplnNum() {
		return mAplnNum;
	}

	/**
	 * 審判番号取得メソッド
	 * @return
	 */
	public String getApln() {
		return mApln;
	}

	/**
	 * 国際公開日取得メソッド
	 * @return
	 */
	public String getPctd() {
		return mPctd;
	}

	/**
	 * 申請人識別番号取得メソッド
	 * @return
	 */
	public String getIdn() {
		return mIdn;
	}

	/**
	 * 優先権主張番号取得メソッド
	 * @return
	 */
	public String getPrin() {
		return mPrin;
	}

	/**
	 * 発明名称取得メソッド
	 * @return
	 */
	public String getDa() {
		return mDa;
	}

	/**
	 * 出願人取得メソッド
	 * @return
	 */
	public String getGo() {
		return mGo;
	}

	/**
	 * FI取得メソッド
	 * @return
	 */
	public String getFi() {
		return mFi;
	}

	/**
	 * 発明番号取得メソッド
	 * @return
	 */
	public String getNo() {
		return mNo;
	}

	/**
	 * タームA取得メソッド
	 * @return
	 */
	public String getAa() {
		return mAa;
	}

	/**
	 * タームB取得メソッド
	 * @return
	 */
	public String getBb() {
		return mBb;
	}

	/**
	 * タームC取得メソッド
	 * @return
	 */
	public String getCc() {
		return mCc;
	}

	/**
	 * タームD取得メソッド
	 * @return
	 */
	public String getDd() {
		return mDd;
	}

	/**
	 * 合金フリーワード取得メソッド
	 * @return
	 */
	public String getFw() {
		return mFw;
	}

	/**
	 * 種別コード(代表文献番号)取得メソッド
	 * @return
	 */
	public String getScdKy() {
		return mScdKy;
	}

	/**
	 * 種別コード(文献番号)	取得メソッド
	 * @return
	 */
	public String getScdKz() {
		return mScdKz;
	}

	/**
	 * 検索コード取得メソッド
	 * @return
	 */
	public String getSltCd() {
		return mSltCd;
	}

	/**
	 * 代表文献番号(非特許種別)取得メソッド
	 * @return
	 */
	public String getKy1() {
		return mKy1;
	}

	/**
	 * 代表文献番号(文献種別)取得メソッド
	 * @return
	 */
	public String getKy2() {
		return mKy2;
	}

	/**
	 * 代表文献番号(書籍番号)取得メソッド
	 * @return
	 */
	public String getKy3() {
		return mKy3;
	}

	/**
	 * 代表文献番号(記事番号)取得メソッド
	 * @return
	 */
	public String getKy4() {
		return mKy4;
	}

	/**
	 * 発明名称／書籍タイトル取得メソッド
	 * @return
	 */
	public String getHi() {
		return mHi;
	}

	/**
	 * 文献タイトル取得メソッド
	 * @return
	 */
	public String getTl() {
		return mTl;
	}

	/**
	 * Fターム取得メソッド
	 * @return
	 */
	public String getFt() {
		return mFt;
	}

	/**
	 * IPC取得メソッド
	 * @return
	 */
	public String getIc() {
		return mIc;
	}

	/**
	 * ISN設定メソッド
	 * @param mIsn
	 */
	public void setIsn(String mIsn) {
		this.mIsn = mIsn;
	}

	/**
	 * 出願番号設定メソッド
	 * @param mAi
	 */
	public void setAi(String mAi) {
		this.mAi = mAi;
	}

	/**
	 * 代表文献番号(公開／公告番号)設定メソッド
	 * @param mKy
	 */
	public void setKy(String mKy) {
		this.mKy = mKy;
	}

	/**
	 * 文献番号設定メソッド
	 * @param mKz
	 */
	public void setKz(String mKz) {
		this.mKz = mKz;
	}

	/**
	 * 公告番号・明細番号・JIS番号設定メソッド
	 * @param mPn
	 */
	public void setPn(String mPn) {
		this.mPn = mPn;
	}

	/**
	 * 資料型設定メソッド
	 * @param mSr
	 */
	public void setSr(String mSr) {
		this.mSr = mSr;
	}

	/**
	 * 出願日設定メソッド
	 * @param mAd
	 */
	public void setAd(String mAd) {
		this.mAd = mAd;
	}

	/**
	 * 公知日設定メソッド
	 * @param mHk
	 */
	public void setHk(String mHk) {
		this.mHk = mHk;
	}

	/**
	 * 公開日設定メソッド
	 * @param mKd
	 */
	public void setKd(String mKd) {
		this.mKd = mKd;
	}

	/**
	 * 公告日設定メソッド
	 * @param mPd
	 */
	public void setPd(String mPd) {
		this.mPd = mPd;
	}

	/**
	 * 登録日設定メソッド
	 * @param mRd
	 */
	public void setRd(String mRd) {
		this.mRd = mRd;
	}

	/**
	 * 登録公報発行日設定メソッド
	 * @param mRj
	 */
	public void setRj(String mRj) {
		this.mRj = mRj;
	}

	/**
	 * 公表日設定メソッド
	 * @param mTpnd
	 */
	public void setTpnd(String mTpnd) {
		this.mTpnd = mTpnd;
	}

	/**
	 * 再公表発行日設定メソッド
	 * @param mRtpnd
	 */
	public void setRtpnd(String mRtpnd) {
		this.mRtpnd = mRtpnd;
	}

	/**
	 * 審判番号繰り返し数設定メソッド
	 * @param mAplnNum
	 */
	public void setAplnNum(String mAplnNum) {
		this.mAplnNum = mAplnNum;
	}

	/**
	 * 審判番号設定メソッド
	 * @param mApln
	 */
	public void setApln(String mApln) {
		this.mApln = mApln;
	}

	/**
	 * 国際公開日設定メソッド
	 * @param mPctd
	 */
	public void setPctd(String mPctd) {
		this.mPctd = mPctd;
	}

	/**
	 * 申請人識別番号設定メソッド
	 * @param mIdn
	 */
	public void setIdn(String mIdn) {
		this.mIdn = mIdn;
	}

	/**
	 * 優先権主張番号設定メソッド
	 * @param mPrin
	 */
	public void setPrin(String mPrin) {
		this.mPrin = mPrin;
	}

	/**
	 * 発明名称設定メソッド
	 * @param mDa
	 */
	public void setDa(String mDa) {
		this.mDa = mDa;
	}

	/**
	 * 出願人設定メソッド
	 * @param mGo
	 */
	public void setGo(String mGo) {
		this.mGo = mGo;
	}

	/**
	 * FI設定メソッド
	 * @param mFi
	 */
	public void setFi(String mFi) {
		this.mFi = mFi;
	}

	/**
	 * 発明番号設定メソッド
	 * @param mNo
	 */
	public void setNo(String mNo) {
		this.mNo = mNo;
	}

	/**
	 * タームA設定メソッド
	 * @param mAa
	 */
	public void setAa(String mAa) {
		this.mAa = mAa;
	}

	/**
	 * タームB設定メソッド
	 * @param mBb
	 */
	public void setBb(String mBb) {
		this.mBb = mBb;
	}

	/**
	 * タームC設定メソッド
	 * @param mCc
	 */
	public void setCc(String mCc) {
		this.mCc = mCc;
	}

	/**
	 * タームD設定メソッド
	 * @param mDd
	 */
	public void setDd(String mDd) {
		this.mDd = mDd;
	}

	/**
	 * 合金フリーワード設定メソッド
	 * @param mFw
	 */
	public void setFw(String mFw) {
		this.mFw = mFw;
	}

	/**
	 * 種別コード(代表文献番号)設定メソッド
	 * @param mScdKy
	 */
	public void setScdKy(String mScdKy) {
		this.mScdKy = mScdKy;
	}

	/**
	 * 種別コード(文献番号)設定メソッド
	 * @param mScdKz
	 */
	public void setScdKz(String mScdKz) {
		this.mScdKz = mScdKz;
	}

	/**
	 * 検索コード設定メソッド
	 * @param mSltCd
	 */
	public void setSltCd(String mSltCd) {
		this.mSltCd = mSltCd;
	}

	/**
	 * 代表文献番号(非特許種別)設定メソッド
	 * @param mKy1
	 */
	public void setKy1(String mKy1) {
		this.mKy1 = mKy1;
	}

	/**
	 * 代表文献番号(文献種別)設定メソッド
	 * @param mKy2
	 */
	public void setKy2(String mKy2) {
		this.mKy2 = mKy2;
	}

	/**
	 * 代表文献番号(書籍番号)設定メソッド
	 * @param mKy3
	 */
	public void setKy3(String mKy3) {
		this.mKy3 = mKy3;
	}

	/**
	 * 代表文献番号(記事番号)設定メソッド
	 * @param mKy4
	 */
	public void setKy4(String mKy4) {
		this.mKy4 = mKy4;
	}

	/**
	 * 発明名称／書籍タイトル設定メソッド
	 * @param mHi
	 */
	public void setHi(String mHi) {
		this.mHi = mHi;
	}

	/**
	 * 文献タイトル設定メソッド
	 * @param mTl
	 */
	public void setTl(String mTl) {
		this.mTl = mTl;
	}

	/**
	 * Fターム設定メソッド
	 * @param mFt
	 */
	public void setFt(String mFt) {
		this.mFt = mFt;
	}

	/**
	 * IPC設定メソッド
	 * @param iC
	 */
	public void setIc(String miC) {
		this.mIc = miC;
	}

}