

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.batch.ComUtil;
import com.batch.IComConst;

public class CmsInfoTest {

	@Test
	public void toIntTest() {
		try {
			//long val = ComUtil.toLng("00123456");
			boolean aa = false;
			//if (1234567L == 1234567) { aa = true;}
			assertThat(false, is(aa));
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void compareTest() {
		List<String> noLst = new ArrayList<String>();
		assertThat(0, is(noLst.size()));

	}

	@Test
	public void getScdKyTest() {

		String val = "";
		val = getScdKy("JPU429100006", "1");
		assertThat("UA0", is(val));

		val = getScdKy("JPU001900061", "1");
		assertThat("UN0", is(val));

		val = getScdKy("JPU100960006", "1");
		assertThat("UT0", is(val));

		val = getScdKy("JPA001600061", "1");
		assertThat("PT0", is(val));

//		assertThat("UC0", is(val));

		val = getScdKy("USA 201234567", "2");
		assertThat("UA1", is(val));

		val = getScdKy("USA 001234567", "2");
		assertThat("FU0", is(val));

		val = getScdKy("USB 001234567", "2");
		assertThat("UB1", is(val));

		val = getScdKy("EPA 001234567", "2");
		assertThat("FE1", is(val));

		val = getScdKy("EPB 001234567", "2");
		assertThat("FEA", is(val));

		val = getScdKy("WOA 001234567", "2");
		assertThat("FP1", is(val));

		val = getScdKy("CNA 001234567", "2");
		assertThat("CN1", is(val));

		val = getScdKy("KRA 001234567", "2");
		assertThat("KR1", is(val));

		val = getScdKy("KRB 001234567", "2");
		assertThat("KR3", is(val));

		val = getScdKy("KRB 321234567", "2");
		assertThat("KR2", is(val));

		val = getScdKy("KRU 321234567", "2");
		assertThat("KRA", is(val));

		val = getScdKy("KRY 321234567", "2");
		assertThat("KRB", is(val));

		val = getScdKy("KRY 001234567", "2");
		assertThat("KRC", is(val));

	}

	@Test
	public void testGetBytes() {

		String str2 = "1234ｓｓｓ";
		assertThat(10, is(ComUtil.getByteLength(str2, "EUC-JP")));
	}

	@Test
	public void test() {

		List<String> fwLst = new ArrayList<String>();
		fwLst.add(null);
		fwLst.add(null);
		fwLst.add(null);
		String[] fwArr = (String[]) fwLst.toArray(new String[0]);
		assertThat(3, is(fwArr.length));

		System.out.println(String.format("%-3s", "02"));
		String mapln[] = "1234\t22".split("\t");
		assertThat(2, is(mapln.length));
//
//		String mapln2[] = "\t\t\t\t\t\t\t".split("\t");
//		assertThat(0, is(mapln2.length));
	}

	@Test
	public void testArrayColumns() {
		List<String> testLst = new ArrayList<String>();
		String[] val = (String[]) testLst.toArray(new String[0]);
		assertThat(0, is(val.length));
	}

	@Test
	public void testSubString() {
		String str2 = "あ1ああああ";
		assertThat("あ1", is(str2.substring(0, 2)));


		String str1 = "11あ1ああああ";
		assertThat("11あ1", is(ComUtil.leftB(str1, 6, "EUC-JP")));
	}

//	public static void testArraySize(Connection Con) throws Cp5Exception, SQLException, Exception {
//
//		String query = "";
//		String msg = "";
//		Array arrayBb = null;
//		PreparedStatement stmt1 = null;
//		ResultSet  rs1= null;
//System.out.println("DEBUG method getArray start");
//		try {
//			query = "select bb from cmsjptbl";
//
//			// オブジェクトを生成
//			stmt1 = Con.prepareStatement(query);
//
//			// ResultSet取得
//			rs1 = stmt1.executeQuery();
//
//			while(rs1.next()) {
//				arrayBb = rs1.getArray(1);
//			}
//			String[] values = (String[]) arrayBb.getArray();
//
//System.out.println("DEBUG bb Array長さは"+ values.length);
//System.out.println("DEBUG method getArray end");
//
//
//		} catch (SQLException ex1) {
//			ex1.printStackTrace();
//
//		} catch (Exception ex2) {
//			throw ex2;
//
//		} finally {
//			// Resultset オブジェクトのクローズ
//			if (rs1 != null) {
//				rs1.close();
//			}
//
//			// Statement オブジェクトのクローズ
//			if (stmt1 != null) {
//				stmt1.close();
//			}
//		}
//	}

	/**
	 * 種別コードを取得
	 * @param item
	 * @return
	 */
	private String getScdKy(String item, String mShoriKbn) {

		String ScdKz = "";
		String cond1 = "";
		String cond2 = "";
		String cond31 = "";
		String cond32 = "";
		String cond33 = "";

		// 国内の場合
		if (IComConst.SHORI_KBN_JP.equals(mShoriKbn)) {
			// 条件1(先頭から2バイト)
			cond1 = item.substring(0,2);
			// 条件2(3バイト)
			cond2 =item.substring(2,3);
			// 条件3(7バイト)
			cond31 =item.substring(6,7);
			// 条件3(4バイト)
			cond32 =item.substring(3,4);

			if ("JP".equals(cond1)) {
				if ("A".equals(cond2)) {
					if (ComUtil.toint(cond31) < 5) {
						ScdKz = "PA0";
					} else if (ComUtil.toint(cond31) >= 5 && ComUtil.toint(cond31) != 8) {
						ScdKz = "PT0";
					} else if (ComUtil.toint(cond31) == 8) {
						ScdKz = "PS0";
					}
				}

				if  ("B".equals(cond2)) {
					if (ComUtil.toint(cond32) != 0) {
						ScdKz = "PB0";
					} else {
						ScdKz = "PR0";
					}
				}

				if  ("C".equals(cond2)) {
					ScdKz = "PC0";
				}

				if ("U".equals(cond2)) {
					if (ComUtil.toint(item.substring(6, 12)) < 500001) {
						ScdKz = "UA0";
					} else if (ComUtil.toint(cond32) == 0) {
						ScdKz = "UN0";
					} else if (ComUtil.toint(cond31) >= 5 && ComUtil.toint(cond31) != 8 ) {
						ScdKz = "UT0";
					} else if (ComUtil.toint(cond31) == 8) {
						ScdKz = "US0";
					}
				}

				if ("Y".equals(cond2)) {
					if (ComUtil.toint(cond32) != 0) {
						ScdKz = "UB0";
					} else {
						ScdKz = "UR0";
					}
				}

				if ("Z".equals(cond2)) {
					ScdKz = "UC0";
				}
			}

		}

		// 外国の場合
		if (IComConst.SHORI_KBN_FP.equals(mShoriKbn)) {
			// 条件1(先頭から2バイト)
			cond1 = item.substring(0,2);
			// 条件2(3～4バイト)
			cond2 =item.substring(2,4);
			// 条件3(5バイト)
			cond31 =item.substring(4,5);
			// 条件3(5～6バイト)
			cond32 =item.substring(4,6);
			// 条件3(5バイト～)
			cond33 =item.substring(5);

			if ("US".equals(cond1)) {
				if ("A ".equals(cond2)) {
					if (!"00".equals(cond32)) {
						ScdKz = "UA1";
					} else {
						ScdKz = "FU0";
					}
				}
				if ("B ".equals(cond2)) {
					ScdKz = "UB1";
				}
			}

			if ("EP".equals(cond1)) {
				if ("A ".equals(cond2)) {
					ScdKz = "FE1";
				}
				if ("B ".equals(cond2)) {
					ScdKz = "FEA";
				}
			}

			if("WO".equals(cond1) && "A ".equals(cond2)) {
				ScdKz = "FP1";
			}

			if ("CN".equals(cond1)) {
				if ("A ".equals(cond2)){
					if ("0".equals(cond31)) {
						ScdKz = "CN1";
					} else if ("1".equals(cond31)){
						ScdKz = "CN2";
					}
				}

				if ("B ".equals(cond2)){
					if ("0".equals(cond31) && 1019872 <= ComUtil.toint(cond33)) {
						ScdKz = "CN3";
					} else if ("0".equals(cond31) && 1019873 >= ComUtil.toint(cond33)){
						ScdKz = "CN4";
					} else if ("1".equals(cond31)){
						ScdKz = "CN5";
					}
				}

				if ("C ".equals(cond2)){
					if ("0".equals(cond31)) {
						ScdKz = "CN6";
					} else if ("1".equals(cond31)){
						ScdKz = "CN7";
					}
				}

				if ("U ".equals(cond2)){
					if ("0".equals(cond31)) {
						ScdKz = "CNA";
					} else if ("2".equals(cond31)){
						ScdKz = "CNB";
					}
				}

				if ("Y ".equals(cond2)){
					if ("0".equals(cond31)) {
						ScdKz = "CNC";
					} else if ("2".equals(cond31)){
						ScdKz = "CND";
					}
				}
			}

			if ("KR".equals(cond1)) {
				if ("A ".equals(cond2)) {
					ScdKz = "KR1";
				}

				if ("B ".equals(cond2)){
					if (!("00".equals(cond32))){
						ScdKz = "KR2";
					} else {
						ScdKz = "KR3";
					}
				}

				if ("U ".equals(cond2)) {
					ScdKz = "KRA";
				}

				if ("Y ".equals(cond2)){
					if (!("00".equals(cond32))){
						ScdKz = "KRB";
					} else {
						ScdKz = "KRC";
					}
				}
			}
		}

		// 特許の場合
		if (IComConst.SHORI_KBN_NP.equals(mShoriKbn)) {

			// 条件1(先頭から2バイト)
			cond1 = item.substring(0,2);
			// 条件2(3～4バイト)
			cond2 =item.substring(2,4);

			if ("CS".equals(cond1)) {
				switch (cond2) {
				case "NA":
					ScdKz = "NA0";
					break;
				case "NB":
					ScdKz = "NB0";
					break;
				case "ND":
					ScdKz = "ND0";
					break;
				case "NE":
					ScdKz = "NE0";
					break;
				case "NF":
					ScdKz = "NF0";
					break;
				case "NG":
					ScdKz = "NG0";
					break;
				case "NH":
					ScdKz = "NH0";
					break;
				case "NI":
					ScdKz = "NI0";
					break;
				case "NJ":
					ScdKz = "NJ0";
					break;
				case "NC":
					ScdKz = "NC0";
					break;
				}
			} else if ("JP".equals(cond1) && "N1".equals(cond2)) {
				ScdKz = "N10";
			}
		}

		return ScdKz;
	}
}
