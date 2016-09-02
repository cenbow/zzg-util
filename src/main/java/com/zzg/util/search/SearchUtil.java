package com.zzg.util.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.py.Pinyin;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;

/**
 * 
 * @author zhang_zg 
 * @version 1.0    
 * @created 2016年7月23日 上午11:30:28
 */
public class SearchUtil {

	private final static String NONE = "none";
	private final static int MAX_NGRAM = 7;

	private SearchUtil() {
	}

	/**
	 * 分词
	 * @author zhang_zg
	 * @param text
	 * @return
	 */
	public static List<String> tokenize(String text) {
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
			tokens.add(term.word.toLowerCase());

			List<Pinyin> pinyinList = HanLP.convertToPinyinList(word);

			StringBuilder full = new StringBuilder();
			StringBuilder acronym = new StringBuilder();
			for (Pinyin py : pinyinList) {
				if (!NONE.equals(py.getPinyinWithoutTone())) {
					full.append(py.getPinyinWithoutTone());
					acronym.append(py.getFirstChar());
				}
			}
			if (StringUtils.isNotBlank(full)) {
				tokens.add(full.toString());
				tokens.add(acronym.toString());
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
			if (nGramMax > MAX_NGRAM) {
				nGramMax = MAX_NGRAM;
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
			if (pinYin.size() < MAX_NGRAM) {
				StringBuilder term = new StringBuilder();
				for (String item : pinYin) {
					term.append(item);
				}
				terms.add(term.toString());
			}
		}
		return terms;
	}

	public static List<String> getPinYin(String text) {
		if (StringUtils.isBlank(text)) {
			return Collections.emptyList();
		}

		List<String> pinYin = new ArrayList<String>();
		for (char c : text.toCharArray()) {
			if (SearchUtil.isEnglish(c)) {
				pinYin.add(String.valueOf(c));
			}
		}
		return pinYin;
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

	public static String extractEnglish(String text) {
		StringBuilder normal = new StringBuilder();
		for (char c : text.toCharArray()) {
			if (isEnglish(c)) {
				normal.append(c);
			}
		}
		return normal.toString().toLowerCase();
	}

	public static void main(String[] args) {
		System.out.println(SearchUtil.tokenize("汽车"));
	}
}
