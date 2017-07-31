package com.batch;

/**
 * 定数を扱うクラス。
 * @author chou
 *
 */
public interface SystemInfo {

    public final String LOG_FORMAT			= "%s %s %s%n";
	public final String DATETIME_FORMAT		= "yyyy/MM/dd HH:mm:ss";
    public final String ENV_PDNAMEPORT		= "PDNAMEPORT";
	public final String ENV_PDHOST			= "PDHOST";
	public final String DRIVER				= "JP.co.Hitachi.soft.HiRDB.JDBC.HiRDBDriver";
	public final String URL					= "jdbc:hitachi:hirdb://DBID=%s,DBHOST=%s,ENCODELANG=EUC_JP";
	public final String USER					= "\"willdba\"";
	public final String PASSWD				= "\"hirdb1\"";

	// ****************共通検索一覧表示用情報作成用****************
	// 共通検索一覧表示用情報(国内)(外国)(非特許)
	public final String ENV_FILE_CMS		= "CP5JCOM_FILE_CMS";

	// 共通検索一覧表示用情報(国内)(外国)(非特許)エラー一覧
	public final String ENV_FILE_ERR_CMS		= "CP5JCOM_FILE_ERR_CMS";

	// 番号索引用情報データ(特許－出願情報)
	public final String ENV_FILE_P_APPM			= "CP5JCOM_FILE_P_APPM";
	// 番号索引用情報データ(特許－審判情報)
	public final String ENV_FILE_P_APPEALM		= "CP5JCOM_FILE_P_APPEALM";
	// 番号索引用情報データ(実用－出願情報)
	public final String ENV_FILE_U_APPM			= "CP5JCOM_FILE_U_APPM";
	// 番号索引用情報データ(実用－審判情報)
	public final String ENV_FILE_U_APPEALM		= "CP5JCOM_FILE_U_APPEALM";

	// 番号索引用情報データ(特許－出願情報)出力用フォーマット
	public final String PAPPM_INFO_FORMAT  = "%s,%s,%s,%s,%s,%s,%s,%s,%s";
	// 番号索引用情報データ(特許－審判情報)出力用フォーマット
	public final String PAPPEALM_INFO_FORMAT  = "%s,%s,%s,%s,%s";
	// 番号索引用情報データ(実用－出願情報)出力用フォーマット
	public final String UAPPM_INFO_FORMAT  = "%s,%s,%s,%s,%s,%s,%s,%s,%s";
	// 番号索引用情報データ(実用－審判番号)出力用フォーマット
	public final String UAPPEALM_INFO_FORMAT  = "%s,%s,%s,%s,%s";
	// 共通検索一覧表示用情報(国内)出力用フォーマット
	public final String CMSJP_INFO_FORMAT  = "%s	%s	%s	%s	%s	%s	%s	%s	%s	%s	%s	%s	%s	%s	%s	%s	%s	%s	%s	%s	%s	%s	%s	%s	%s	%s	%s	%-3s	%-3s	%s%n";
	// 共通検索一覧表示用情報(国外)出力用フォーマット
	public final String CMSFP_INFO_FORMAT  = "%s	%s	%s	%s	%s	%s	%s	%s	%s	%s	%-3s	%s%n";
	// 共通検索一覧表示用情報(非特許)出力用フォーマット
	public final String CMSNP_INFO_FORMAT  = "%s	%s	%s	%s	%s	%s	%s	%s	%s	%-3s	%s%n";

	// 定義ファイルデリミタ
	public final String DEF_FILE_DELIMITER = ",";
	// JPP表示用情報デリミタ
	public final String JPP_FILE_DELIMITER = "\t";
	// 共通検索一覧表示用情報デリミタ
	public final String CMS_FILE_DELIMITER = "\t";

	// ****************共通検索一覧表示用情報登録用****************
	// 蓄積確認パラメータ(国内)(外国)(非特許)
	public final String ENV_FILE_CHKPRM			= "CP5JCOM_FILE_CHKPRM";
	// DBコミット単位
	public final String ENV_COMMIT_LIMIT		= "CP5J110_COMMIT_LIMIT";
	// 種別コード数(国内)
	public final int SCD_NUM_J				= 13;
	// 種別コード数(外国)
	public final int SCD_NUM_F				= 23;
	// 種別コード数(非特許)
	public final int SCD_NUM_N				= 11;
	// 蓄積確認パラメータ単位数
	public final int CHKPRM_NUM				= 5;
	// 蓄積確認パラメータレコードフォーマット
	public final String CHK_PRM_FORMAT		= "%s,%s";

	// 最大レコードID取得(国内)クエリ
	public final String QUERY1_01		= "SELECT MAX(RECID) FROM CMSJPTBL WITHOUT LOCK NOWAIT";
	// 最大レコードID取得(外国)クエリ
	public final String QUERY2_01		= "SELECT MAX(RECID) FROM CMSFPTBL WITHOUT LOCK NOWAIT";
	// 最大レコードID取得(非特許)クエリ
	public final String QUERY3_01		= "SELECT MAX(RECID) FROM CMSNPTBL WITHOUT LOCK NOWAIT";

	// 重複カウント(国内)クエリ
	public final String QUERY1_02		= "SELECT COUNT(ISN) FROM CMSJPTBL WHERE ISN = ? AND SLTCD = '0' WITHOUT LOCK NOWAIT";
	// 重複カウント(外国)クエリ
	public final String QUERY2_02		= "SELECT COUNT(ISN) FROM CMSFPTBL WHERE ISN = ? AND SLTCD = '0' WITHOUT LOCK NOWAIT";
	// 重複カウント(非特許)クエリ
	public final String QUERY3_02		= "SELECT COUNT(ISN) FROM CMSNPTBL WHERE ISN = ? AND SLTCD = '0' WITHOUT LOCK NOWAIT";

	// DB削除(国内)クエリ
	public final String QUERY1_03		= "DELETE FROM CMSJPTBL WHERE ISN = ? AND SLTCD = '0'";
	// DB削除(外国)クエリ
	public final String QUERY2_03		= "DELETE FROM CMSFPTBL WHERE ISN = ? AND SLTCD = '0'";
	// DB削除(非特許)クエリ
	public final String QUERY3_03		= "DELETE FROM CMSNPTBL WHERE ISN = ? AND SLTCD = '0'";

	// DB挿入(国内)クエリ
	public final String QUERY1_04		= "INSERT INTO CMSJPTBL (RECID, ISN, AI, KY, KZ, PN, SR, AD, HK, KD, PD, RD1, RJ, TPND, RTPND"
											+ " , APLN, PCTD, IDN, PRIN, DA, GO1, FI, NO1, AA, BB, CC, DD, FW, SCD_KY, SCD_KZ, SLTCD)"
											+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	// DB挿入(外国)クエリ
	public final String QUERY2_04		= "INSERT INTO CMSFPTBL (RECID, ISN, KY1, KY2, KY3, KZ, AD, HK, FI, FT, IC, SCD_KY, SLTCD)"
											+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	// DB挿入(非特許)クエリ
	public final String QUERY3_04		= "INSERT INTO CMSNPTBL (RECID, ISN, KY1, KY2, KY3, KY4, HK, HI, FT, TL, SCD_KY, SLTCD)"
											+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	// 蓄積確認件数取得(国内)クエリ
	public final String QUERY1_05		= "SELECT COUNT(*) FROM (SELECT ISN FROM CMSJPTBL WHERE SCD_KY = ? AND RECID > ? UNION ALL SELECT ISN FROM CMSJPTBL WHERE SCD_KZ = ? AND RECID > ?) WITHOUT LOCK NOWAIT";
	// 蓄積確認件数取得(国外)クエリ
	public final String QUERY2_05		= "SELECT COUNT(*) FROM CMSFPTBL WHERE SCD_KY = ? AND RECID > ? WITHOUT LOCK NOWAIT";
	// 蓄積確認件数取得(非特許)クエリ
	public final String QUERY3_05		= "SELECT COUNT(*) FROM CMSNPTBL WHERE SCD_KY = ? AND RECID > ? WITHOUT LOCK NOWAIT";

	// 蓄積確認上位10件取得(国内)クエリ
	public final String QUERY1_06		= "SELECT ISN, DNO FROM (SELECT ISN, KY AS DNO FROM CMSJPTBL WHERE SCD_KY = ? AND RECID > ? UNION ALL SELECT ISN, KZ AS DNO FROM CMSJPTBL WHERE SCD_KZ = ? AND RECID > ?) T ORDER BY DNO LIMIT 10 WITHOUT LOCK NOWAIT";

	// 蓄積確認上位10件取得(国外)クエリ
	public final String QUERY21_06		= "SELECT ISN, KY3 AS DNO FROM CMSFPTBL WHERE SCD_KY = ? AND RECID > ? ORDER BY DNO LIMIT 10 WITHOUT LOCK NOWAIT";
	public final String QUERY22_06		= "SELECT ISN, SUBSTR(DNO,3) FROM ("
											+ " SELECT RECID, ISN, '19' || KY3 AS DNO, SCD_KY, SLTCD  FROM CMSFPTBL WHERE SCD_KY IN ('FP1', 'KR1', 'KRA')"
											+ " AND (CASE WHEN SCD_KY ='FP1' THEN SUBSTR(KY3,2,2) ELSE SUBSTR(KY3,1,2) END) >= '30' AND (CASE WHEN SCD_KY ='FP1' THEN SUBSTR(KY3,2,2) ELSE SUBSTR(KY3,1,2) END) <= '99'"
											+ " UNION ALL"
											+ " SELECT RECID, ISN, '20' || KY3 AS DNO, SCD_KY, SLTCD FROM CMSFPTBL WHERE SCD_KY IN ('FP1', 'KR1', 'KRA')"
											+ " AND (CASE WHEN SCD_KY ='FP1' THEN SUBSTR(KY3,2,2) ELSE SUBSTR(KY3,1,2) END) >= '00' AND (CASE WHEN SCD_KY ='FP1' THEN SUBSTR(KY3,2,2) ELSE SUBSTR(KY3,1,2) END) <= '29'"
											+ ") T WHERE SLTCD = '0' AND SCD_KY = ? AND RECID > ? ORDER BY DNO LIMIT 10 WITHOUT LOCK NOWAIT";

	// 蓄積確認上位10件取得(非特許)クエリ
	public final String QUERY31_06		= "SELECT ISN, KY3 || KY4 AS DNO, CASE KY4 WHEN '   ' THEN 0 ELSE CAST(KY4 AS INTEGER) END AS IKY4 FROM CMSNPTBL WHERE SCD_KY = ? AND RECID > ? ORDER BY KY3, IKY4 LIMIT 10 WITHOUT LOCK NOWAIT";
	public final String QUERY32_06		= "SELECT ISN, SUBSTR(DNO,3) FROM ("
											+ " SELECT RECID, ISN, '19' || KY3 AS DNO, SCD_KY, SLTCD FROM CMSNPTBL WHERE SLTCD = '0' AND SUBSTR(KY3,2,2) >= '30' AND SUBSTR(KY3,2,2) <= '99' AND SCD_KY IN ('N10')"
											+ " UNION ALL"
											+ " SELECT RECID, ISN, '20' || KY3 AS DNO, SCD_KY, SLTCD FROM CMSNPTBL WHERE SLTCD = '0' AND SUBSTR(KY3,2,2) >= '00' AND SUBSTR(KY3,2,2) <= '29' AND SCD_KY IN ('N10')"
											+ ") T WHERE SLTCD = '0' AND SCD_KY = ? AND RECID > ? ORDER BY DNO LIMIT 10 WITHOUT LOCK NOWAIT";

	// 蓄積確認下位5件(降順)取得(国内)クエリ
	public final String QUERY1_07		= "SELECT ISN, DNO FROM (SELECT ISN, KY AS DNO FROM CMSJPTBL WHERE SCD_KY = ? AND RECID > ? UNION ALL SELECT ISN, KZ AS DNO FROM CMSJPTBL WHERE SCD_KZ = ? AND RECID > ?) T ORDER BY DNO DESC LIMIT 5 WITHOUT LOCK NOWAIT";

	// 蓄積確認下位5件(降順)取得(国外)クエリ
	public final String QUERY21_07		= "SELECT ISN, KY3 AS DNO FROM CMSFPTBL WHERE SCD_KY = ? AND RECID > ? ORDER BY DNO DESC LIMIT 5 WITHOUT LOCK NOWAIT";
	public final String QUERY22_07		= "SELECT ISN, SUBSTR(DNO,3) FROM ("
											+ " SELECT RECID, ISN, '19' || KY3 AS DNO, SCD_KY, SLTCD  FROM CMSFPTBL WHERE SCD_KY IN ('FP1', 'KR1', 'KRA')"
											+ " AND (CASE WHEN SCD_KY ='FP1' THEN SUBSTR(KY3,2,2) ELSE SUBSTR(KY3,1,2) END) >= '30' AND (CASE WHEN SCD_KY ='FP1' THEN SUBSTR(KY3,2,2) ELSE SUBSTR(KY3,1,2) END) <= '99'"
											+ " UNION ALL"
											+ " SELECT RECID, ISN, '20' || KY3 AS DNO, SCD_KY, SLTCD FROM CMSFPTBL WHERE SCD_KY IN ('FP1', 'KR1', 'KRA')"
											+ " AND (CASE WHEN SCD_KY ='FP1' THEN SUBSTR(KY3,2,2) ELSE SUBSTR(KY3,1,2) END) >= '00' AND (CASE WHEN SCD_KY ='FP1' THEN SUBSTR(KY3,2,2) ELSE SUBSTR(KY3,1,2) END) <= '29'"
											+ ") T WHERE SLTCD = '0' AND SCD_KY = ? AND RECID > ? ORDER BY DNO DESC LIMIT 5 WITHOUT LOCK NOWAIT";

	// 蓄積確認下位5件(降順)取得(非特許)クエリ
	public final String QUERY31_07		= "SELECT ISN, KY3 || KY4 AS DNO, CASE KY4 WHEN '   ' THEN 0 ELSE CAST(KY4 AS INTEGER) END AS IKY4 FROM CMSNPTBL WHERE SCD_KY = ? AND RECID > ? ORDER BY KY3 DESC, IKY4 DESC LIMIT 5 WITHOUT LOCK NOWAIT";
	public final String QUERY32_07		= "SELECT ISN, SUBSTR(DNO,3) FROM ("
											+ " SELECT RECID, ISN, '19' || KY3 AS DNO, SCD_KY, SLTCD FROM CMSNPTBL WHERE SLTCD = '0' AND SUBSTR(KY3,2,2) >= '30' AND SUBSTR(KY3,2,2) <= '99' AND SCD_KY IN ('N10')"
											+ " UNION ALL"
											+ " SELECT RECID, ISN, '20' || KY3 AS DNO, SCD_KY, SLTCD FROM CMSNPTBL WHERE SLTCD = '0' AND SUBSTR(KY3,2,2) >= '00' AND SUBSTR(KY3,2,2) <= '29' AND SCD_KY IN ('N10')"
											+ ") T WHERE SLTCD = '0' AND SCD_KY = ? AND RECID > ? ORDER BY DNO DESC LIMIT 5 WITHOUT LOCK NOWAIT";

	// ****************共通検索一覧表示用情報文蓄情報ファイル作成用****************
	// 文蓄情報抽出(国内)
	public final String TBLNAME1			= "CMSJPTBL";
	// 文蓄情報抽出(外国)
	public final String TBLNAME2			= "CMSFPTBL";
	// 文蓄情報抽出(非特許)
	public final String TBLNAME3			= "CMSNPTBL";

	// 文蓄情報抽出(国内)①
	public final String QUERY1_1			= "SELECT SCD_KY, MIN(KY), MAX(KY), MAX(HK), COUNT(*) FROM CMSJPTBL WHERE SLTCD = '0' GROUP BY SCD_KY WITHOUT LOCK NOWAIT";
	// 文蓄情報抽出(国内)②
	public final String QUERY1_2			= "SELECT SCD_KZ, MIN(KZ), MAX(KZ), MAX(HK), COUNT(*) FROM CMSJPTBL WHERE SLTCD = '0' GROUP BY SCD_KZ WITHOUT LOCK NOWAIT";
	// 文蓄情報抽出(外国)①
	public final String QUERY2_1			= "SELECT SCD_KY, MIN(KY3), MAX(KY3), MAX(HK), COUNT(*) FROM CMSFPTBL WHERE SLTCD = '0' AND SCD_KY NOT IN ('FP1','KR1','KRA') GROUP BY SCD_KY WITHOUT LOCK NOWAIT";
	// 文蓄情報抽出(外国)②
	public final String QUERY2_2			= "SELECT SCD_KY, SUBSTR(MIN(K3),3), SUBSTR(MAX(K3),3), MAX(HK), COUNT(*) FROM ("
											+ " SELECT SCD_KY, '19' || KY3 AS K3, HK FROM CMSFPTBL WHERE SLTCD = '0' AND SUBSTR(KY3,1,2) >= '30' AND SUBSTR(KY3,1,2) <= '99' AND SCD_KY IN ('KR1','KRA')"
											+ " UNION ALL"
											+ " SELECT SCD_KY, '20' || KY3 AS K3, HK FROM CMSFPTBL WHERE SLTCD = '0' AND SUBSTR(KY3,1,2) >= '00' AND SUBSTR(KY3,1,2) <= '29' AND SCD_KY IN ('KR1','KRA')"
											+ " UNION ALL"
											+ " SELECT SCD_KY, '19' || KY3 AS K3, HK FROM CMSFPTBL WHERE SLTCD = '0' AND SUBSTR(KY3,2,2) >= '30' AND SUBSTR(KY3,2,2) <= '99' AND SCD_KY IN ('FP1')"
											+ " UNION ALL"
											+ " SELECT SCD_KY, '20' || KY3 AS K3, HK FROM CMSFPTBL WHERE SLTCD = '0' AND SUBSTR(KY3,2,2) >= '00' AND SUBSTR(KY3,2,2) <= '29' AND SCD_KY IN ('FP1')"
											+ ") T GROUP BY SCD_KY WITHOUT LOCK NOWAIT";
	// 文蓄情報抽出(非特許)①
	public final String QUERY3_1			= "SELECT SCD_KY, MIN(KY3), MAX(KY3), MAX(HK), COUNT(*) FROM CMSNPTBL WHERE SLTCD = '0' AND SCD_KY NOT IN ('N10') GROUP BY SCD_KY WITHOUT LOCK NOWAIT";
	// 文献情報抽出(非特許)②
	public final String QUERY3_2			= "SELECT MIN(KY4) FROM CMSNPTBL WHERE SLTCD = '0' AND SCD_KY = ? AND KY3 = ? WITHOUT LOCK NOWAIT";
	// 文献情報抽出(非特許)③
	public final String QUERY3_3			= "SELECT MAX(KY4) FROM CMSNPTBL WHERE SLTCD = '0' AND SCD_KY = ? AND KY3 = ? WITHOUT LOCK NOWAIT";
	// 文蓄情報抽出(非特許)④
	public final String QUERY3_4			= "SELECT SCD_KY, SUBSTR(MIN(K3),3), SUBSTR(MAX(K3),3), MAX(HK), COUNT(*) FROM ("
											+ " SELECT SCD_KY, '19' || KY3 AS K3, HK FROM CMSNPTBL WHERE SLTCD = '0' AND SUBSTR(KY3,2,2) >= '30' AND SUBSTR(KY3,2,2) <= '99' AND SCD_KY = 'N10'"
											+ " UNION ALL"
											+ " SELECT SCD_KY, '20' || KY3 AS K3, HK FROM CMSNPTBL WHERE SLTCD = '0' AND SUBSTR(KY3,2,2) >= '00' AND SUBSTR(KY3,2,2) <= '29' AND SCD_KY = 'N10'"
											+ ") T GROUP BY SCD_KY WITHOUT LOCK NOWAIT";
	public final String DATE_FORMAT 			= "yyyyMMdd";
	public final String MANAGE_INFO_FORMAT	= "0000 0003 %04d %s                                                        %n";
	public final String RANGE_INFO_FORMAT	= "%s %s %s %-2s %-12s %-12s %-8s %-8s          %n";
	public final int MAX_SCD_NUM			= 300;


}
