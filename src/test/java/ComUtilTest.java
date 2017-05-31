

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.batch.ComUtil;

public class ComUtilTest {

	@Test
	public void convertJc2AdTest() {
		String val = ComUtil.convertJc2Ad("028");
		assertThat("", is(val));

		val = ComUtil.convertJc2Ad("128");
		assertThat("1895", is(val));

		val = ComUtil.convertJc2Ad("228");
		assertThat("1939", is(val));

		val = ComUtil.convertJc2Ad("328");
		assertThat("1953", is(val));

		val = ComUtil.convertJc2Ad("428");
		assertThat("2016", is(val));
	}

	@Test
	public void testFor() {
		List<Integer> intList = new ArrayList<Integer>();
	    for (Integer i : intList) {
	        System.out.print(i);// 可以打印出intList中的所有元素
	      }
	    assertThat("005", is("005"));
	}



	@Test
	public void leftPadZero() {
		String val = ComUtil.leftPadZero("5  ", 3);
		assertThat("005", is(val));
	}

	@Test
	public void convertEmptyToNullTest() {
		String val = "";
		assertThat("", is(val));
		val = ComUtil.convEmptyToNull("B1,B2");
		assertThat("B1,B2", is(val));
	}

	@Test
	public void isAlphaNumTest(){
		boolean flg = ComUtil.isAlphaNum("　　　sdfgd123");
		assertFalse(flg);

		flg = ComUtil.isAlphaNum("ｓｄｇ33");
		assertFalse(flg);
	}

	@Test
	public void isEmptyTest(){
		boolean flg = ComUtil.isEmpty(null);
		assertTrue(flg);

		flg = ComUtil.isEmpty("");
		assertTrue(flg);

		flg = ComUtil.isEmpty("334253425 334");
		assertFalse(flg);
	}

	@Test
	public void isNumberTest(){
		boolean flg = ComUtil.isNumber(" 1234");
		assertFalse(flg);

		flg = ComUtil.isAlphaNum("12345");
		assertTrue(flg);
	}

	@Test
	public void toIntTest() {
		int val = ComUtil.toint("");
		assertThat(0, is(val));

		val = ComUtil.toint("3332345");
		assertThat(3332345, is(val));
	}

	@Test
	public void toLngTest() {
		long val = ComUtil.toLng("");
		assertThat(0l, is(val));

		val = ComUtil.toLng("33323333333333345");
		assertThat(33323333333333345L, is(val));
	}

	@Test
	public void compareBiggerTest() {
		try {
			boolean flg = ComUtil.compareBigger("1234723", "");
			assertTrue(flg);
		} catch (Exception e) {
		       fail ("compareBiggerTest failed");
		   }

	}

	@Test
	public void compareSmallerTest() {
		try {
			boolean flg = ComUtil.compareSmaller("09849346", "");
			assertTrue(flg);
		} catch (Exception e) {
		    fail ("compareBiggerTest failed");
		}

	}

	@Test
	public void leftPadZeroTest() {
		try {
			String val = ComUtil.leftPadZero("", 3);
			assertThat("", is(val));

			val = ComUtil.leftPadZero(null, 3);
			assertThat(null, is(val));

			val = ComUtil.leftPadZero("1234    ", 7);
			assertThat("0001234", is(val));

		} catch (Exception e) {
		    fail ("compareBiggerTest failed");
		}
	}
}
