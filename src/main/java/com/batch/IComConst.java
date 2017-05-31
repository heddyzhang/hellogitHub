package com.batch;

public interface IComConst {

	// ***************処理区分***************
	public final String SHORI_KBN_JP = "1"; // 処理区分（国内）
	public final String SHORI_KBN_FP = "2"; // 処理区分（外国）
	public final String SHORI_KBN_NP = "3"; // 処理区分（非特許）

	// ***************項目番号 共通検索一覧表示用情報(国内用)読込む用***************
	public final String ISN_NO_JP = "1001"; // ISN
	public final String AI_NO_JP = "1002"; // 出願番号
	public final String KY_NO_JP = "1003"; // 代表文献番号(公開／公告番号)
	public final String KZ_NO_JP = "1004"; // 文献番号
	public final String PN_NO_JP = "1005"; // 公告番号・明細番号・JIS番号
	public final String SR_NO_JP = "1006"; // 資料型
	public final String AD_NO_JP = "1007"; // 出願日
	public final String HK_NO_JP = "1008"; // 公知日
	public final String KD_NO_JP = "1009"; // 公開日
	public final String PD_NO_JP = "1010"; // 公告日
	public final String RD1_NO_JP = "1011"; // 登録日
	public final String RJ_NO_JP = "1012"; // 登録公報発行日
	public final String TPND_NO_JP = "1013"; // 公表日
	public final String RTPND_NO_JP = "1014"; // 再公表発行日
	public final String APLN_REP_NO_JP = "1015"; // 審判番号繰り返し数
	public final String APLN_NO_JP = "1016"; // 審判番号
	public final String PCTD_NO_JP = "1017"; // 国際公開日
	public final String IDN_REP_NO_JP = "1018"; // 申請人識別番号繰り返し数
	public final String IDN_NO_JP = "1019"; // 申請人識別番号
	public final String PRIN_REP_NO_JP = "1020"; // 優先権主張番号繰り返し数
	public final String PRIN_NO_JP = "1021"; // 優先権主張番号
	public final String DA_NO_JP = "1022"; // 発明名称
	public final String GO1_REP_NO_JP = "1024"; // 出願人繰り返し数
	public final String GO1_NO_JP = "1025"; // 出願人
	public final String FI_REP_NO_JP = "1030"; // FI繰り返し数
	public final String FI_NO_JP = "1031"; // FI
	public final String NO1_REP_NO_JP = "1047"; // 発明番号繰り返し数
	public final String NO1_NO_JP = "1048"; // 発明番号
	public final String AA_REP_NO_JP = "1049"; // タームA繰り返し数
	public final String BB_REP_NO_JP = "1051"; // タームB繰り返し数
	public final String CC_REP_NO_JP = "1056"; // タームC繰り返し数
	public final String DD_REP_NO_JP = "1058"; // タームD繰り返し数
	public final String FW_REP_NO_JP = "1060"; // 合金フリーワード繰り返し数

	// ***************項目番号 共通検索一覧表示用情報(外国)読込む用***************
	public final String ISN_NO_FP = "2001"; // ISN
	public final String KY1_NO_FP = "2002"; // 代表文献番号(国コード)
	public final String KY2_NO_FP = "2003"; // 代表文献番号(種別)
	public final String KY3_NO_FP = "2004"; // 代表文献番号(公開／公告番号)
	public final String KZ_REP_NO_FP = "2005"; // 文献番号繰り返し数
	public final String KZ_NO_FP = "2006"; // 文献番号
	public final String AD_NO_FP = "2007"; // 出願日
	public final String HK_NO_FP = "2008"; // 公知日
	public final String FI_REP_NO_FP = "2011"; // FI繰り返し数
	public final String FI_NO_FP = "2012"; // FI
	public final String FT_REP_NO_FP = "2015"; // Fターム繰り返し数
	public final String FT_NO_FP = "2016"; // Fターム
	public final String IC_REP_NO_FP = "2022"; // IPC繰り返し数
	public final String IC_NO_FP = "2023"; // IPC

	// ***************項目番号(共通検索一覧表示用情報(非特許)読込む用)***************
	public final String ISN_NO_NP = "3001"; // ISN
	public final String KY1_NO_NP = "3002"; // 代表文献番号(非特許種別)
	public final String KY2_NO_NP = "3003"; // 代表文献番号(文献種別)
	public final String KY3_NO_NP = "3004"; // 代表文献番号(書籍番号)
	public final String KY4_NO_NP = "3005"; // 代表文献番号(記事番号)
	public final String HK_NO_NP = "3006"; // 公知日
	public final String HI_NO_NP = "3007"; // 発明名称／書籍タイトル
	public final String FT_REP_NO_NP = "3014"; // Fターム繰り返し数
	public final String FT_NO_NP = "3015"; // Fターム
	public final String TL_NO_NP = "3019"; // 文献タイトル

	// ***************最大繰り返し数***************
	public final int ZERO_REPEAT_NUM = 0; // 繰り返し数ゼロ
	public final int DEFAULT_MAX_REPEAT_NUM = 99; // デフォルト繰り返し数最大値
	public final int FI_MAX_REPEAT_NUM_JP = 4; // FI繰り返し数最大値(国内用)
	public final int KZ_MAX_REPEAT_NUM = 5; // 文献番号繰り返し数最大値(国外用)
	public final int FI_MAX_REPEAT_NUM = 999; // FI繰り返し数最大値(国外用)
	public final int FT_MAX_REPEAT_NUM = 999; // Fターム繰り返し数最大値(国外用)
	public final int IC_MAX_REPEAT_NUM = 4; // IPC繰り返し数最大値(国外用)

	// ***************最大有効長さ***************
	public final int GO_LEN = 200;// 出願人長さ最大有効値

	// ***************出力位置 共通検索一覧表示用情報(国内)登録用***************
	public final int ISN_IDX_JP = 0; // ISN
	public final int AI_IDX_JP = 1; // 出願番号
	public final int KY_IDX_JP = 2; // 代表文献番号(公開／公告番号)
	public final int KZ_IDX_JP = 3; // 文献番号
	public final int PN_IDX_JP = 4; // 公告番号・明細番号・JIS番号
	public final int SR_IDX_JP = 5; // 資料型
	public final int AD_IDX_JP = 6; // 出願日
	public final int HK_IDX_JP = 7; // 公知日
	public final int KD_IDX_JP = 8; // 公開日
	public final int PD_IDX_JP = 9; // 公告日
	public final int RD1_IDX_JP = 10; // 登録日
	public final int RJ_IDX_JP = 11; // 登録公報発行日
	public final int TPND_IDX_JP = 12; // 公表日
	public final int RTPND_IDX_JP = 13; // 再公表発行日
	public final int APLN_IDX_JP = 14; // 審判番号
	public final int PCTD_IDX_JP = 113; // 国際公開日
	public final int IDN_IDX_JP = 114; // 申請人識別番号
	public final int PRIN_IDX_JP = 213; // 優先権主張番号
	public final int DA_IDX_JP = 312; // 発明名称
	public final int GO1_IDX_JP = 313; // 出願人
	public final int FI_IDX_JP = 412; // FI
	public final int NO1_IDX_JP = 416; // 発明番号
	public final int AA_IDX_JP = 515; // タームA
	public final int BB_IDX_JP = 614; // タームB
	public final int CC_IDX_JP = 713; // タームC
	public final int DD_IDX_JP = 812; // タームD
	public final int FW_IDX_JP = 911; // 合金フリーワード
	public final int SCD_KY_IDX_JP = 1010; // 種別コード(代表文献番号)
	public final int SCD_KZ_IDX_JP = 1011; // 種別コード(文献番号)
	public final int SLTCD_IDX_JP = 1012; // 検索コード

	// ***************出力位置 共通検索一覧表示用情報(外国)登録用***************
	public final int ISN_IDX_FP = 0; // ISN
	public final int KY1_IDX_FP = 1; // 代表文献番号(国コード)
	public final int KY2_IDX_FP = 2; // 代表文献番号(種別)
	public final int KY3_IDX_FP = 3; // 代表文献番号(公開／公告番号)
	public final int KZ_IDX_FP = 4; // 文献番号
	public final int AD_IDX_FP = 9; // 出願日
	public final int HK_IDX_FP = 10; // 公知日
	public final int FI_IDX_FP = 11; // FI
	public final int FT_IDX_FP = 1010; // Fターム
	public final int IC_IDX_FP = 2009; // IPC
	public final int SCD_KY_IDX_FP = 2013; // 種別コード(代表文献番号)
	public final int SLTCD_IDX_FP = 2014; // 検索コード

	// ***************出力位置 共通検索一覧表示用情報(非特許)登録用***************
	public final int ISN_IDX_NP = 0; // ISN
	public final int KY1_IDX_NP = 1; // 代表文献番号(非特許種別)
	public final int KY2_IDX_NP = 2; // 代表文献番号(文献種別)
	public final int KY3_IDX_NP = 3; // 代表文献番号(書籍番号)
	public final int KY4_IDX_NP = 4; // 代表文献番号(記事番号)
	public final int HK_IDX_NP = 5; // 公知日
	public final int HI_IDX_NP = 6; // 発明名称／書籍タイトル
	public final int FT_IDX_NP = 7; // Fターム
	public final int TL_IDX_NP = 1006; // 文献タイトル
	public final int SCD_KY_IDX_NP = 1007; // 種別コード(代表文献番号)
	public final int SLTCD_IDX_NP = 1008; // 検索コード

	// 定義ファイル文字コード
	public final String FILE_DEFINE_ENCODING = "UTF-8";

	// 標準形式DB更新情報ファイル文字コード
	public final String FILE_DEFAULT_ENCODING = "EUC-JP";

	// 出力ファイル文字コード
	public final String FILE_OUTPUT_ENCODING = "EUC-JP";

	// ***************項目タイプ区分***************
	public final String TYPE_KBN_NORMAL = "1"; // 通常
	public final String TYPE_KBN_REPEAT = "2"; // 繰り返し数

	// ***************要否区分***************
	public final String YOFU_KBN_HISU = "1"; // 必須
	public final String YOFU_KBN_NINYI = "2"; // 任意
	public final String YOFU_KBN_FUYO = "3"; // 不要

	// ***************チェック区分***************
	public final String CHECK_KBN_NUM = "1"; // 半角数字チェック区分
	public final String CHECK_KBN_ALPNUM = "1"; // 半角英数字チェック区分

	// 処理対象DB区分
	public final String SHORI_TAISHO_DB_A = "A"; // 蓄積面A
	public final String SHORI_TAISHO_DB_B = "B"; // 蓄積面B

	// 蓄積確認パラメータ(国内)出力用
	public static final String[] SCD_NM_JP_ARRAY = { "公開特許公報", "公表特許公報", "再公表特許公報", "公告特許公報", "特許公報", "公開発明明細書", "公開実用新案公報",
											"登録実用新案公報", "公表実用新案公報", "再公表実用新案公報", "実用新案公報", "実用新案登録公報", "登録実用新案明細書" };
	public static final String[] SCD_JP_ARRAY = { "PA0", "PT0", "PS0", "PB0", "PR0", "PC0", "UA0", "UN0", "UT0", "US0", "UB0", "UR0", "UC0" };
	// 蓄積確認パラメータ(国外)出力用
	public static final String[] SCD_NM_FP_ARRAY = { "米国特許特許出願公開明細書", "米国特許特許明細", "米国特許特許明細", "欧州特許特許出願公開明細書","欧州特許特許発明明細書", "国際公開国際公開",
												"中国特許特許出願公開明細書", "中国特許特許出願公開明細書", "中国特許特許出願公告明細書", "中国特許特許明細書", "中国特許特許明細書", "中国特許特許明細書", "中国特許特許明細書",
											"中国実用新案実用新案公告明細書", "中国実用新案実用新案明細書", "中国実用新案実用新案明細書", "中国実用新案実用新案明細書",
										"韓国特許公開特許公報", "韓国特許特許公報", "韓国特許登録特許公報", "韓国実用新案公開実用新案公報", "韓国実用新案実用新案公報", "韓国実用新案登録実用新案公報" };
	public static final String[] SCD_FP_ARRAY = { "UA1", "FU0", "UB1", "FE1", "FEA", "FP1", "CN1", "CN2", "CN3", "CN4", "CN5", "CN6", "CN7", "CNA", "CNB", "CNC", "CND", "KR1", "KR2", "KR3", "KRA", "KRB", "KRC" };
	// 蓄積確認パラメータ(非特許)出力用
	public static final String[] SCD_NM_NP_ARRAY = { "CSDBマニュアル", "CSDB単行本", "CSDB国内技術雑誌", "CSDB非技術雑誌", "CSDB外国学会論文","CSDB国内学会論文", "CSDB企業技術", "CSDB団体機関紙", "CSDB予稿集",
											"学術文献等学術文献等", "JP非特許公開技報" };
	public static final String[] SCD_NP_ARRAY = { "NA0", "NB0", "ND0", "NE0", "NF0", "NG0", "NH0", "NI0", "NJ0", "NC0", "N10" };

	// ***************出力項目初期値***************
	public final String ITEM_DEFAULT_VALUE = "";
	public final String EMPTY_VALUE = "";

}
