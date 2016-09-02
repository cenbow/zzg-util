package com.zzg.util.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.py.Pinyin;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;

public class CopyOfSearchUtil {
	private final static Logger LOG = LoggerFactory.getLogger(CopyOfSearchUtil.class);
	private final static int PIN_YIN_LENGTH = 2;
	private static int maxNgram = 7;
	private static int pinYinMaxNgram = 7 * PIN_YIN_LENGTH;
	private final static String NONE = "none";

	private CopyOfSearchUtil() {

	}

	/**
	 * 获取首字母
	 * @author zhang_zg
	 * @param words
	 * @return
	 */
	public static List<String> getAcronymPinYin(String words) {
		return getPinYin(words, true);
	}

	/**
	 * 获取全拼
	 * @author zhang_zg
	 * @param words
	 * @return
	 */
	public static List<String> getFullPinYin(String words) {
		return getPinYin(words, false);
	}

	/**
	 * 获取拼音
	 * @author zhang_zg
	 * @param words
	 * @param acronym 是否只取首字母
	 * @return
	 */
	public static List<String> getPinYin(String words, boolean acronym) {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
		char[] chars = words.trim().toCharArray();
		List<String> result = new ArrayList<String>();
		try {
			StringBuilder notChinese = null;
			for (char c : chars) {
				if (!isChinese(c)) {
					if (StringUtils.isBlank(notChinese)) {
						notChinese = new StringBuilder();
					}
					notChinese.append(c);
					continue;
				}
				if (StringUtils.isNotBlank(notChinese)) {
					result.add(notChinese.toString());
					notChinese = null;
				}

				String[] pinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(c, format);
				if (acronym) {
					result.add(String.valueOf(pinyinStringArray[0].charAt(0)));
				} else {
					result.add(pinyinStringArray[0]);
				}
			}
		} catch (Exception ex) {
			LOG.error("获取拼音失败", ex);
		}
		//		if (ignoredCount == chars.length) {
		//			return null;
		//		}
		return result;
	}

	public static List<String> tokenize(String text) {
		return tokenize(text, true);
	}

	/**
	 * 分词
	 * @author zhang_zg
	 * @param text
	 * @param generatePinyin 是否得到拼音
	 * @return
	 */
	public static List<String> tokenize(String text, boolean generatePinyin) {
		if (StringUtils.isBlank(text)) {
			return Collections.emptyList();
		}
		int maxNgramLimit = maxNgram;
		if (isEnglish(text)) {
			maxNgramLimit = pinYinMaxNgram;
		}
		List<String> tokens = new ArrayList<String>();
		if (text.length() < 3) {
			addWithPinyin(tokens, text, generatePinyin);
		} else {
			// n gram
			int nGramMax = text.length();
			if (nGramMax > maxNgramLimit) {
				nGramMax = maxNgramLimit;
			}
			for (int i = 2; i < nGramMax; i++) {
				for (int j = 0; j < text.length() - i + 1; j++) {
					if (text.length() - j >= i) {
						String token = text.substring(j, j + i);
						addWithPinyin(tokens, token, generatePinyin);
					}
				}
			}
			// total text as a term if text is short
			if (text.length() < maxNgramLimit - 1) {
				addWithPinyin(tokens, text, generatePinyin);
			}
		}
		return tokens;
	}

	public static List<String> ngramPinYin(List<String> pinYin) {
		if (pinYin.isEmpty()) {
			return Collections.emptyList();
		}
		List<String> terms = new ArrayList<String>();
		if (pinYin.size() < 3) {
			StringBuilder term = new StringBuilder();
			for (String item : pinYin) {
				term.append(item);
			}
			terms.add(term.toString());
		} else {
			// n gram
			int nGramMax = pinYin.size();
			if (nGramMax > maxNgram) {
				nGramMax = maxNgram;
			}
			for (int i = 2; i < nGramMax; i++) {
				for (int j = 0; j < pinYin.size() - i + 1; j++) {
					if (pinYin.size() - j >= i) {
						StringBuilder term = new StringBuilder();
						for (String sub : pinYin.subList(j, j + i)) {
							term.append(sub);
						}
						terms.add(term.toString());
					}
				}
			}
			// total text as a term if text is short
			if (pinYin.size() < maxNgram) {
				StringBuilder term = new StringBuilder();
				for (String item : pinYin) {
					term.append(item);
				}
				terms.add(term.toString());
			}
		}
		return terms;
	}


	private static void addWithPinyin(List<String> tokens, String token, boolean generatePinyin) {
		token = token.toLowerCase();
		if (!tokens.contains(token)) {
			tokens.add(token);
		}
		if (generatePinyin) {
			String pinyin = list2String(getAcronymPinYin(token));
			if (pinyin != null) {
				if (!tokens.contains(pinyin)) {
					tokens.add(pinyin);
				}
			}
			pinyin = list2String(getFullPinYin(token));
			if (pinyin != null) {
				if (!tokens.contains(pinyin)) {
					tokens.add(pinyin);
				}
			}
		}
	}

	public static String list2String(List<String> list) {
		StringBuilder sb = new StringBuilder();
		if (list != null && list.size() > 0) {
			for (String s : list) {
				sb.append(s);
			}
		}
		return sb.toString();
	}

	public static boolean isNumber(String text) {
		boolean number = true;
		for (char c : text.toCharArray()) {
			if (!isNumber(c)) {
				number = false;
			}
		}
		return number;
	}

	public static boolean isNumber(char c) {
		//大部分字符在这个范围
		if (c > '9' && c < '０') {
			return false;
		}
		if (c < '0') {
			return false;
		}
		if (c > '９') {
			return false;
		}
		return true;
	}

	public static boolean isEnglishOrNumber(String text) {
		boolean englishOrNumber = true;
		for (char c : text.toCharArray()) {
			if (!isEnglish(c) && !isNumber(c)) {
				englishOrNumber = false;
			}
		}
		return englishOrNumber;
	}

	public static boolean isEnglish(String text) {
		boolean english = true;
		for (char c : text.toCharArray()) {
			if (!isEnglish(c)) {
				english = false;
			}
		}
		return english;
	}

	public static boolean isEnglish(char c) {
		//大部分字符在这个范围
		if (c > 'z' && c < 'Ａ') {
			return false;
		}
		if (c < 'A') {
			return false;
		}
		if (c > 'Z' && c < 'a') {
			return false;
		}
		if (c > 'Ｚ' && c < 'ａ') {
			return false;
		}
		if (c > 'ｚ') {
			return false;
		}
		return true;
	}

	public static boolean isChinese(String text) {
		boolean chinese = true;
		for (char c : text.toCharArray()) {
			if (!isChinese(c)) {
				chinese = false;
			}
		}
		return chinese;
	}

	public static boolean isChinese(char c) {
		return c >= '\u4e00' && c <= '\u9fa5';
	}

	public static String extractChinese(String text) {
		StringBuilder normal = new StringBuilder();
		for (char c : text.toCharArray()) {
			if (isChinese(c)) {
				normal.append(c);
			}
		}
		return normal.toString().toLowerCase();
	}

	public static String normalize(String text) {
		StringBuilder normal = new StringBuilder();
		for (char c : text.toCharArray()) {
			if (isEnglish(c) || isNumber(c) || isChinese(c)) {
				normal.append(c);
			}
		}
		if (text.length() != normal.length()) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("移除非英语数字、中文字符, 移除之前: {}, 移除之后: {}", text, normal.toString());
			}
		}
		return normal.toString().toLowerCase();
	}

	/**
	 * HanLP分词
	 * @author zhang_zg
	 * @param text
	 * @return
	 */
	public static List<String> tokenizeHanLP(String text) {
		if (StringUtils.isBlank(text)) {
			return Collections.emptyList();
		}
		
		List<String> tokens = new ArrayList<String>();

		List<Term> terms = IndexTokenizer.segment(text);
		for (Term term : terms) {
			String word = term.word;
			if (!isChinese(word) && !isEnglish(word) && !isNumber(word)) {
				continue;
			}
			tokens.add(word.toLowerCase());

			List<Pinyin> pinyinList = HanLP.convertToPinyinList(word);

			StringBuilder full = new StringBuilder();
			StringBuilder acronym = new StringBuilder();
			for (Pinyin py : pinyinList) {
				if (!NONE.equals(py.getPinyinWithoutTone())) {
					full.append(py.getPinyinWithoutTone());
					acronym.append(py.getFirstChar());
					tokens.add(full.toString());
					tokens.add(acronym.toString());
				}
			}
		}

		return tokens;
	}

	public static void main(String[] args) {
		//		List<String> pinyin = SearchUtil.getPinYin("华为(HUAWEI) Mate8 4G手机 双卡双待 全网通高配(4GRAM+64GROM) 颜色随机", false);
		//		System.out.println(pinyin);
		//		//
		List<String> list = CopyOfSearchUtil.tokenize("华为(HUAWEI) Mate8 4G手机 双卡双待 全网通高配(4GRAM+64GROM) 颜色随机", true);
		System.out.println("tokennize:" + list);

		System.out.println("tokenizeHanLP:" + CopyOfSearchUtil.tokenizeHanLP("华为(HUAWEI) Mate8 4G手机 双卡双待 全网通高配(4GRAM+64GROM) 颜色随机"));
	}
}
