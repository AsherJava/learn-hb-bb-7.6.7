/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sourceforge.pinyin4j.PinyinHelper
 *  net.sourceforge.pinyin4j.format.HanyuPinyinCaseType
 *  net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat
 *  net.sourceforge.pinyin4j.format.HanyuPinyinToneType
 *  net.sourceforge.pinyin4j.format.HanyuPinyinVCharType
 *  net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination
 */
package com.jiuqi.nr.pinyin.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HanyuPinyinHelper {
    private static final Logger logger = LoggerFactory.getLogger(HanyuPinyinHelper.class);

    public static String toHanyuPinyin(String ChineseLanguage) {
        char[] cl_chars = ChineseLanguage.trim().toCharArray();
        String hanyupinyin = "";
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        try {
            for (int i = 0; i < cl_chars.length; ++i) {
                hanyupinyin = String.valueOf(cl_chars[i]).matches("[\u4e00-\u9fa5]+") ? hanyupinyin + PinyinHelper.toHanyuPinyinStringArray((char)cl_chars[i], (HanyuPinyinOutputFormat)defaultFormat)[0] : hanyupinyin + cl_chars[i];
            }
        }
        catch (BadHanyuPinyinOutputFormatCombination e) {
            logger.error("\u5b57\u7b26\u4e0d\u80fd\u8f6c\u6210\u6c49\u8bed\u62fc\u97f3", e);
        }
        return hanyupinyin;
    }

    public static String getFirstLettersUp(String ChineseLanguage) {
        return HanyuPinyinHelper.getFirstLetters(ChineseLanguage, HanyuPinyinCaseType.UPPERCASE);
    }

    public static String getFirstLettersLo(String ChineseLanguage) {
        return HanyuPinyinHelper.getFirstLetters(ChineseLanguage, HanyuPinyinCaseType.LOWERCASE);
    }

    public static String getFirstLetters(String ChineseLanguage, HanyuPinyinCaseType caseType) {
        char[] cl_chars = ChineseLanguage.trim().toCharArray();
        String hanyupinyin = "";
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(caseType);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        try {
            for (int i = 0; i < cl_chars.length; ++i) {
                String str = String.valueOf(cl_chars[i]);
                hanyupinyin = str.matches("[\u4e00-\u9fa5]+") ? hanyupinyin + PinyinHelper.toHanyuPinyinStringArray((char)cl_chars[i], (HanyuPinyinOutputFormat)defaultFormat)[0].substring(0, 1) : (str.matches("[0-9]+") ? hanyupinyin + cl_chars[i] : (str.matches("[a-zA-Z]+") ? hanyupinyin + cl_chars[i] : hanyupinyin + cl_chars[i]));
            }
        }
        catch (BadHanyuPinyinOutputFormatCombination e) {
            logger.error("\u5b57\u7b26\u4e0d\u80fd\u8f6c\u6210\u6c49\u8bed\u62fc\u97f3", e);
        }
        return hanyupinyin;
    }

    public static String getPinyinString(String ChineseLanguage) {
        char[] cl_chars = ChineseLanguage.trim().toCharArray();
        String hanyupinyin = "";
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        try {
            for (int i = 0; i < cl_chars.length; ++i) {
                String str = String.valueOf(cl_chars[i]);
                if (str.matches("[\u4e00-\u9fa5]+")) {
                    hanyupinyin = hanyupinyin + PinyinHelper.toHanyuPinyinStringArray((char)cl_chars[i], (HanyuPinyinOutputFormat)defaultFormat)[0];
                    continue;
                }
                if (str.matches("[0-9]+")) {
                    hanyupinyin = hanyupinyin + cl_chars[i];
                    continue;
                }
                if (!str.matches("[a-zA-Z]+")) continue;
                hanyupinyin = hanyupinyin + cl_chars[i];
            }
        }
        catch (BadHanyuPinyinOutputFormatCombination e) {
            logger.error("\u5b57\u7b26\u4e0d\u80fd\u8f6c\u6210\u6c49\u8bed\u62fc\u97f3", e);
        }
        return hanyupinyin;
    }

    public static String getFirstLetter(String ChineseLanguage) {
        char[] cl_chars = ChineseLanguage.trim().toCharArray();
        String hanyupinyin = "";
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        try {
            String str = String.valueOf(cl_chars[0]);
            if (str.matches("[\u4e00-\u9fa5]+")) {
                hanyupinyin = PinyinHelper.toHanyuPinyinStringArray((char)cl_chars[0], (HanyuPinyinOutputFormat)defaultFormat)[0].substring(0, 1);
            } else if (str.matches("[0-9]+")) {
                hanyupinyin = hanyupinyin + cl_chars[0];
            } else if (str.matches("[a-zA-Z]+")) {
                hanyupinyin = hanyupinyin + cl_chars[0];
            }
        }
        catch (BadHanyuPinyinOutputFormatCombination e) {
            logger.error("\u5b57\u7b26\u4e0d\u80fd\u8f6c\u6210\u6c49\u8bed\u62fc\u97f3", e);
        }
        return hanyupinyin;
    }
}

