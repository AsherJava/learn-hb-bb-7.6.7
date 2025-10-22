/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.font.util;

import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import org.springframework.core.io.ClassPathResource;

public enum FontFamily {
    SUN("\u5b8b\u4f53", "\u5b8b\u4f53", "SimSun", "simsun.ttc,0"),
    SUNCHINESE("\u534e\u6587\u4e2d\u5b8b", "\u534e\u6587\u4e2d\u5b8b", "", "stzhongs.ttf"),
    KTGB("\u6977\u4f53_GB2312", "\u6977\u4f53_GB2312", "", "kt_GB2312.ttf"),
    FZXBSJ("\u65b9\u6b63\u5c0f\u6807\u5b8b\u7b80\u4f53", "\u65b9\u6b63\u5c0f\u6807\u5b8b\u7b80\u4f53", "", "fzxbsj.ttf"),
    MICROSOFTYAHEIUI("Microsoft YaHei", "Microsoft YaHei UI", "", "msyh.ttc"),
    MICROSOFTYAHEI("\u5fae\u8f6f\u96c5\u9ed1", "Microsoft YaHei UI", "Microsoft YaHei", "msyh.ttc,0"),
    SIMSUNGB2312("\u4eff\u5b8b_GB2312", "\u4eff\u5b8b_GB2312", "", "fangsonggb2312.ttf"),
    SIMSUN("\u4eff\u5b8b", "\u4eff\u5b8b", "SimFang", "simfang.ttf"),
    REGULARSCRIPT("\u6977\u4f53", "\u6977\u4f53", "SimKai", "simkai.ttf"),
    SIMHEI("\u9ed1\u4f53", "\u9ed1\u4f53", "SimHei", "simhei.ttf"),
    OFFICIALSCRIPT("\u96b6\u4e66", "\u96b6\u4e66", "SimLi", "simli.ttf"),
    DENG("\u7b49\u7ebf", "\u7b49\u7ebf", "\u7b49\u7ebf", "DENG.TTF"),
    ANDALEMONO("andale mono", "andale mono", "andale mono", "andalemono.ttf"),
    ARIALBLACK("arial black", "arial black", "arial black,avant garde", "ariblk.ttf"),
    ARIAL("arial", "arial", "arial, helvetica,sans-serif", "arial.ttf"),
    COMIC_SANS_MS("comic sans ms", "comic sans ms", "comic sans ms", "comic.ttf"),
    IMPACT("impact", "impact", "impact,chicago", "impact.ttf"),
    TIMES_NEW_ROMAN("times new roman", "times new roman", "times new roman", "times.ttf"),
    FZXBSGBK("\u65b9\u6b63\u5c0f\u6807\u5b8b_GBK", "\u65b9\u6b63\u5c0f\u6807\u5b8b_GBK", "", "fzxbsgbk.ttf");

    String name = "";
    String value = "";
    String mapping = "";
    String path = "";
    private static final String FONTROOTPATH;
    private static final String FONT_FILE_PATH = "font";
    private static Set<String> localFontNames;

    private static Set<String> getLocalFontNames() {
        if (localFontNames == null) {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            String[] fontNames = ge.getAvailableFontFamilyNames();
            localFontNames = new HashSet<String>();
            for (String fontName : fontNames) {
                localFontNames.add(fontName);
            }
        }
        return localFontNames;
    }

    private FontFamily(String name, String value, String mapping, String path) {
        this.name = name;
        this.value = value;
        this.path = path;
        this.mapping = mapping;
    }

    public static FontFamily getFontFamily(String font) {
        for (FontFamily fontfamily : FontFamily.values()) {
            if (font.indexOf(fontfamily.name) < 0) continue;
            return fontfamily;
        }
        return null;
    }

    public static String getFont(String font) {
        FontFamily fontFamily = FontFamily.getFontFamily(font = font.replace("'", ""));
        if (fontFamily == null) {
            return FontFamily.getLocalFontNames().contains(font) ? font : SUN.getValue();
        }
        return fontFamily.getValue();
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public String getMapping() {
        return this.mapping;
    }

    public String getPath() {
        if (!"".equals(this.path)) {
            ClassPathResource classPathResource = new ClassPathResource(FONT_FILE_PATH + File.separator + this.path);
            return classPathResource.getPath();
        }
        return this.path;
    }

    @SafeVarargs
    public static FontFamily checkFontFamily(String mapping, FontFamily ... defaultFont) {
        if (SUN.getName().equals(mapping) || SUN.getValue().equals(mapping) || SUN.getMapping().equals(mapping)) {
            return SUN;
        }
        if (MICROSOFTYAHEI.getName().equals(mapping) || MICROSOFTYAHEI.getValue().equals(mapping) || MICROSOFTYAHEI.getMapping().equals(mapping)) {
            return MICROSOFTYAHEI;
        }
        if (SIMSUN.getName().equals(mapping) || SIMSUN.getValue().equals(mapping) || SIMSUN.getMapping().equals(mapping)) {
            return SIMSUN;
        }
        if (REGULARSCRIPT.getName().equals(mapping) || REGULARSCRIPT.getValue().equals(mapping) || REGULARSCRIPT.getMapping().equals(mapping)) {
            return REGULARSCRIPT;
        }
        if (SIMHEI.getName().equals(mapping) || SIMHEI.getValue().equals(mapping) || SIMHEI.getMapping().equals(mapping)) {
            return SIMHEI;
        }
        if (OFFICIALSCRIPT.getName().equals(mapping) || OFFICIALSCRIPT.getValue().equals(mapping) || OFFICIALSCRIPT.getMapping().equals(mapping)) {
            return OFFICIALSCRIPT;
        }
        if (ANDALEMONO.getName().equals(mapping) || ANDALEMONO.getValue().equals(mapping) || ANDALEMONO.getMapping().equals(mapping)) {
            return ANDALEMONO;
        }
        if (ARIAL.getName().equals(mapping) || ARIAL.getValue().equals(mapping) || ARIAL.getMapping().equals(mapping)) {
            return ARIAL;
        }
        if (COMIC_SANS_MS.getName().equals(mapping) || COMIC_SANS_MS.getValue().equals(mapping) || COMIC_SANS_MS.getMapping().equals(mapping)) {
            return COMIC_SANS_MS;
        }
        if (IMPACT.getName().equals(mapping) || IMPACT.getValue().equals(mapping) || IMPACT.getMapping().equals(mapping)) {
            return IMPACT;
        }
        if (TIMES_NEW_ROMAN.getName().equals(mapping) || TIMES_NEW_ROMAN.getValue().equals(mapping) || TIMES_NEW_ROMAN.getMapping().equals(mapping)) {
            return TIMES_NEW_ROMAN;
        }
        if (KTGB.getName().equals(mapping) || KTGB.getValue().equals(mapping) || KTGB.getMapping().equals(mapping)) {
            return KTGB;
        }
        if (FZXBSJ.getName().equals(mapping) || FZXBSJ.getValue().equals(mapping) || FZXBSJ.getMapping().equals(mapping)) {
            return FZXBSJ;
        }
        return defaultFont != null && defaultFont.length > 0 ? defaultFont[0] : null;
    }

    static {
        FONTROOTPATH = File.separator + "component" + File.separator + "report" + File.separator + "nr.analysisreport" + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + FONT_FILE_PATH + File.separator;
        localFontNames = null;
    }
}

