/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.analysisreport.font.util.FontFamily
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.poi.xwpf.usermodel.UnderlinePatterns
 *  org.apache.poi.xwpf.usermodel.XWPFRun
 *  org.jsoup.nodes.Element
 *  org.jsoup.nodes.Node
 */
package com.jiuqi.nr.analysisreport.utils;

import com.jiuqi.nr.analysisreport.font.util.FontFamily;
import com.jiuqi.nr.analysisreport.utils.FontSizeConverter;
import com.jiuqi.nr.analysisreport.utils.IntegerParser;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

public class WordStyle {
    int isBlod = -1;
    int isItalic = -1;
    int hasUnderLine = -1;
    String color = "";
    String background_color = null;
    String background = null;
    String font_family = "";
    String font_size = "";
    boolean isSpecial = false;
    String origin_font_size = "";

    public WordStyle(Node node) {
        this(node, "p", "h1", "h2", "h3", "h4", "table");
    }

    public WordStyle(Node node, String ... outerElements) {
        Element pNode;
        for (pNode = (Element)node.parent(); pNode != null && !this.includeTag(outerElements, pNode.tagName()) && pNode.parent() != null; pNode = pNode.parent()) {
            String[] tokens;
            String style;
            if ("em".equals(pNode.tagName()) && this.isItalic == -1) {
                this.isItalic = 1;
                continue;
            }
            if ("strong".equals(pNode.tagName()) && this.isBlod == -1) {
                this.isBlod = 1;
                continue;
            }
            if (!"span".equals(pNode.tagName()) && !"td".equals(pNode.tagName()) || "".equals(style = pNode.attr("style").trim())) continue;
            for (String token : tokens = style.split(";")) {
                if ("".equals(token.trim()) || !token.contains(":")) continue;
                String key = token.split(":")[0].trim();
                String value = token.split(":")[1].trim();
                if ("color".equals(key) && "".equals(this.color)) {
                    this.color = this.RGB2HEX(value);
                    if (!"red".equals(value) || !StringUtils.isEmpty((CharSequence)this.color)) continue;
                    this.color = this.RGB2HEX("rgb(255,0,0)");
                    continue;
                }
                if ("background-color".equals(key) && this.background_color == null) {
                    this.background_color = this.convertColorToHex(value);
                    continue;
                }
                if ("background".equals(key) && this.background == null) {
                    if (!StringUtils.isNotEmpty((CharSequence)value) || !value.contains("rgb")) continue;
                    this.background = this.convertColorToHex(value);
                    continue;
                }
                if ("font-size".equals(key) && "".equals(this.font_size)) {
                    this.font_size = !"".equals(value) ? value : "14";
                    continue;
                }
                if ("font-family".equals(key) && "".equals(this.font_family)) {
                    this.font_family = !"".equals(value) ? value : "\u5b8b\u4f53";
                    continue;
                }
                if ("text-decoration".equals(key) && this.hasUnderLine == -1) {
                    this.hasUnderLine = "underline".equals(value) ? 1 : 0;
                    continue;
                }
                if ("font-weight".equals(key) && Pattern.matches("(bold|bolder|400|600|800)", value) && this.isBlod == -1) {
                    this.isBlod = 1;
                    continue;
                }
                if ("font-weight".equals(key) && Pattern.matches("(^bold|bolder|400|600|800)", value) && this.isBlod == -1) {
                    this.isBlod = 0;
                    continue;
                }
                if ("font-style".equals(key) && Pattern.matches("(italic|oblique)", value) && this.isItalic == -1) {
                    this.isItalic = 1;
                    continue;
                }
                if ("font-style".equals(key) && Pattern.matches("(^italic|oblique)", value) && this.isItalic == -1) {
                    this.isItalic = 0;
                    continue;
                }
                if (!"origin_font_size".equals(key) || !"".equals(this.origin_font_size) || !StringUtils.isNotEmpty((CharSequence)value)) continue;
                this.origin_font_size = value;
            }
        }
        if (this.includeTag(new String[]{"h1", "h2", "h3", "h4"}, pNode.tagName())) {
            this.isSpecial = true;
        }
    }

    public void setWordStyle(XWPFRun run) {
        if (!"".equals(this.color)) {
            run.setColor(this.color);
        }
        if (StringUtils.isNotEmpty((CharSequence)this.background_color)) {
            run.getCTR().addNewRPr().addNewShd().setFill((Object)this.background_color);
        } else if (StringUtils.isNotEmpty((CharSequence)this.background)) {
            run.getCTR().addNewRPr().addNewShd().setFill((Object)this.background);
        }
        if (!"".equals(this.font_size)) {
            run.setFontSize(FontSizeConverter.convertToPt(this.font_size));
            if (StringUtils.isNotEmpty((CharSequence)this.origin_font_size)) {
                run.setFontSize(Double.valueOf(this.origin_font_size).doubleValue());
            }
        }
        if (!"".equals(this.font_family)) {
            run.setFontFamily(FontFamily.getFont((String)this.font_family));
        } else if (!this.isSpecial) {
            run.setFontFamily("\u5b8b\u4f53");
        }
        if (this.hasUnderLine == 1) {
            run.setUnderline(UnderlinePatterns.SINGLE);
        } else if (this.hasUnderLine != 1) {
            run.setUnderline(UnderlinePatterns.NONE);
        }
        if (this.isBlod == 1) {
            run.setBold(true);
        } else if (this.isBlod != 1) {
            run.setBold(false);
        }
        if (this.isItalic == 1) {
            run.setItalic(true);
        } else if (this.isItalic != 1) {
            run.setItalic(false);
        }
    }

    private String RGB2HEX(String rgb) {
        String[] rgbs = rgb.replace("rgb(", "").replace(")", "").split(",");
        if (rgbs.length == 3) {
            return WordStyle.convertRGBToHex(IntegerParser.parseInt(rgbs[0].trim()), IntegerParser.parseInt(rgbs[1].trim()), IntegerParser.parseInt(rgbs[2].trim()));
        }
        return "";
    }

    private boolean includeTag(String[] tags, String target) {
        for (String tag : tags) {
            if (!target.equals(tag)) continue;
            return true;
        }
        return false;
    }

    public String convertColorToHex(String color) {
        if (StringUtils.isEmpty((CharSequence)color)) {
            return "";
        }
        if ((color = color.replaceAll("\\s+", "")).contains("#")) {
            return color.replace("#", "");
        }
        if (color.contains("rgb")) {
            return this.RGB2HEX(color).replace("#", "");
        }
        switch (color) {
            case "yellow": {
                return "FFFF00";
            }
            case "lime": {
                return "00FF00";
            }
            case "aqua": {
                return "00FFFF";
            }
            case "fuchsia": {
                return "FF00FF";
            }
            case "blue": {
                return "0000FF";
            }
            case "red": {
                return "FF0000";
            }
            case "navy": {
                return "000080";
            }
            case "teal": {
                return "008080";
            }
            case "green": {
                return "006400";
            }
            case "purple": {
                return "800080";
            }
            case "maroon": {
                return "800000";
            }
            case "olive": {
                return "808000";
            }
            case "gray": {
                return "A9A9A9";
            }
            case "silver": {
                return "C0C0C0";
            }
            case "black": {
                return "000000";
            }
        }
        return "";
    }

    public static String convertRGBToHex(int r, int g, int b) {
        int red = r / 16;
        int rred = r % 16;
        String rFString = red == 10 ? "A" : (red == 11 ? "B" : (red == 12 ? "C" : (red == 13 ? "D" : (red == 14 ? "E" : (red == 15 ? "F" : String.valueOf(red))))));
        String rSString = rred == 10 ? "A" : (rred == 11 ? "B" : (rred == 12 ? "C" : (rred == 13 ? "D" : (rred == 14 ? "E" : (rred == 15 ? "F" : String.valueOf(rred))))));
        rFString = rFString + rSString;
        int green = g / 16;
        int rgreen = g % 16;
        String gFString = green == 10 ? "A" : (green == 11 ? "B" : (green == 12 ? "C" : (green == 13 ? "D" : (green == 14 ? "E" : (green == 15 ? "F" : String.valueOf(green))))));
        String gSString = rgreen == 10 ? "A" : (rgreen == 11 ? "B" : (rgreen == 12 ? "C" : (rgreen == 13 ? "D" : (rgreen == 14 ? "E" : (rgreen == 15 ? "F" : String.valueOf(rgreen))))));
        gFString = gFString + gSString;
        int blue = b / 16;
        int rblue = b % 16;
        String bFString = blue == 10 ? "A" : (blue == 11 ? "B" : (blue == 12 ? "C" : (blue == 13 ? "D" : (blue == 14 ? "E" : (blue == 15 ? "F" : String.valueOf(blue))))));
        String bSString = rblue == 10 ? "A" : (rblue == 11 ? "B" : (rblue == 12 ? "C" : (rblue == 13 ? "D" : (rblue == 14 ? "E" : (rblue == 15 ? "F" : String.valueOf(rblue))))));
        bFString = bFString + bSString;
        String result = rFString + gFString + bFString;
        return result;
    }
}

