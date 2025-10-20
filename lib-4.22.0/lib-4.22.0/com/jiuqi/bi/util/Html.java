/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringHelper
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.bi.util;

import com.jiuqi.bi.text.DecimalFormat;
import com.jiuqi.bi.util.StringHelper;
import com.jiuqi.bi.util.StringUtils;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

public class Html {
    public static final int TRANSPARENT_COLOR = 0x1FFFFFFF;
    public static final String TRANSPARENT = "transparent";
    private static final String[] SYS_COLOR_NAMES = new String[]{"scrollbar", "background", "activecaption", "inactivecaption", "menu", "window", "windowframe", "menutext", "windowtext", "captiontext", "inactiveborder", "inactivecaption", "appworkspace", "highlight", "highlighttext", "buttonface", "buttonshadow", "graytext", "buttontext", "inactivecaption", "buttonhighlight", "threeddarkshadow", "threedhighlight", "infotext", "infobackground"};
    private static final int SYS_COLOR_MASK = -16777216;

    private Html() {
    }

    public static String getHtmlColor(int colorValue) {
        String returnStr = "";
        returnStr = colorValue < 0 ? Html.getSystemColor(colorValue) : "#" + Html.repairLength(Integer.toHexString(Html.BGR2RGB(colorValue)));
        return returnStr;
    }

    public static String rgb2HtmlColor(int rgb) {
        if (rgb < 0) {
            return Html.getSystemColor(rgb);
        }
        if (Html.isTransparent(rgb)) {
            return TRANSPARENT;
        }
        return "#" + Html.repairLength(Integer.toHexString(rgb));
    }

    public static int BGR2RGB(int color) {
        if (color < 0) {
            return color;
        }
        int red = (color & 0xFF) << 16;
        int blue = (color & 0xFF0000) >> 16;
        int green = color & 0xFF00;
        return red | blue | green;
    }

    private static String repairLength(String colorStr) {
        if (colorStr == null) {
            return "000000";
        }
        if (colorStr.length() == 6) {
            return colorStr;
        }
        StringBuffer buffer = new StringBuffer(colorStr);
        while (buffer.length() < 6) {
            buffer.insert(0, '0');
        }
        return buffer.toString();
    }

    public static final boolean isTransparent(int colorValue) {
        return colorValue == 0x1FFFFFFF;
    }

    public static String getSystemColor(int intColor) {
        if ((intColor & 0xFF000000) != -16777216) {
            return "";
        }
        int colorValue = intColor & 0xFFFFFF;
        return colorValue < SYS_COLOR_NAMES.length ? SYS_COLOR_NAMES[colorValue] : "";
    }

    public static String strToHtml(String instring) {
        int lIndex = 0;
        if (instring == null) {
            return "";
        }
        StringBuffer lStrbuff = new StringBuffer(instring);
        while (lIndex < lStrbuff.length()) {
            char lChar = lStrbuff.charAt(lIndex);
            lChar = lStrbuff.charAt(lIndex);
            if (lChar == '\"') {
                lStrbuff.replace(lIndex, lIndex + 1, "&#34;");
                lIndex += 5;
                continue;
            }
            if (lChar == '&') {
                lStrbuff.replace(lIndex, lIndex + 1, "&amp;");
                lIndex += 5;
                continue;
            }
            if (lChar == '<') {
                lStrbuff.replace(lIndex, lIndex + 1, "&lt;");
                lIndex += 4;
                continue;
            }
            if (lChar == '>') {
                lStrbuff.replace(lIndex, lIndex + 1, "&gt;");
                lIndex += 4;
                continue;
            }
            if (lChar == ' ') {
                lStrbuff.replace(lIndex, lIndex + 1, "&nbsp;");
                lIndex += 6;
                continue;
            }
            ++lIndex;
        }
        return lStrbuff.toString();
    }

    public static String encodeText(String inString) {
        if (inString == null) {
            return "";
        }
        int lIndex = 0;
        char endChar = '\u0000';
        StringBuffer outString = new StringBuffer(inString);
        while (lIndex < outString.length()) {
            char lChar = outString.charAt(lIndex);
            if (lIndex + 1 < outString.length()) {
                endChar = outString.charAt(lIndex + 1);
            }
            if (lChar == '&') {
                outString.replace(lIndex, lIndex + 1, "&amp;");
                lIndex += 5;
                continue;
            }
            if (lChar == '<') {
                outString.replace(lIndex, lIndex + 1, "&lt;");
                lIndex += 4;
                continue;
            }
            if (lChar == '>') {
                outString.replace(lIndex, lIndex + 1, "&gt;");
                lIndex += 4;
                continue;
            }
            lChar = outString.charAt(lIndex);
            if (lChar == '\"') {
                outString.replace(lIndex, lIndex + 1, "&#34;");
                lIndex += 5;
                continue;
            }
            if (lChar == '\r' && endChar == '\n') {
                outString.replace(lIndex, lIndex + 2, "<br>");
                lIndex += 4;
                continue;
            }
            if (lChar == ' ') {
                outString.replace(lIndex, lIndex + 1, "&nbsp;");
                lIndex += 6;
                continue;
            }
            if (lChar == '/') {
                outString.replace(lIndex, lIndex + 1, "&#47;");
                lIndex += 5;
                continue;
            }
            ++lIndex;
        }
        return outString.toString();
    }

    public static String cleanUrlXSS(String value) {
        if (value != null) {
            value = value.replaceAll("", "");
            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", 2);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("<iframe(.*?)src[\r\n]*=(.*?)>", 2);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("<iframe(.*?)src[\r\n]*=(.*?)", 2);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("<img(.*?)src[\r\n]*=(.*?)>", 2);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("<a(.*?)href[\r\n]*=(.*?)>", 2);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("<img(.*?)src[\r\n]*=(.*?)", 2);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\'(.*?)\\'", 42);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", 42);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("</script>", 2);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("</a>", 2);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("<script(.*?)>", 42);
            value = Pattern.compile("<(.*?)>", 2).matcher(value).replaceAll("");
            value = Pattern.compile("prompt\\((.*?)\\)", 2).matcher(value).replaceAll("");
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("eval\\((.*?)\\)", 42);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("e-xpression\\((.*?)\\)", 42);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("javascript:", 2);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("vbscript:", 2);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("onload(.*?)=", 42);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("onerror(.*?)=", 42);
            value = scriptPattern.matcher(value).replaceAll("");
        }
        return value;
    }

    public static void cleanJsonXSS(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        Iterator itr = jsonObject.keys();
        while (itr.hasNext()) {
            String key = (String)itr.next();
            Object valueObject = jsonObject.opt(key);
            if (valueObject == null) continue;
            if (valueObject instanceof JSONObject) {
                Html.cleanJsonXSS((JSONObject)valueObject);
                continue;
            }
            if (valueObject instanceof JSONArray) {
                Html.cleanJsonXSS((JSONArray)valueObject);
                continue;
            }
            if (!(valueObject instanceof String)) continue;
            jsonObject.put(key, (Object)Html.cleanUrlXSS((String)valueObject));
        }
    }

    public static void cleanJsonXSS(JSONArray jsonArray) {
        if (jsonArray == null) {
            return;
        }
        for (int idx = 0; idx < jsonArray.length(); ++idx) {
            Object valueObject = jsonArray.get(idx);
            if (valueObject == null) continue;
            if (valueObject instanceof JSONObject) {
                Html.cleanJsonXSS((JSONObject)valueObject);
                continue;
            }
            if (valueObject instanceof JSONArray) {
                Html.cleanJsonXSS((JSONArray)valueObject);
                continue;
            }
            if (!(valueObject instanceof String)) continue;
            jsonArray.put(idx, (Object)Html.cleanUrlXSS((String)valueObject));
        }
    }

    public static String cleanJsFuncName(String inString) {
        if (inString == null) {
            return "";
        }
        int lIndex = 0;
        char lChar = '\u0000';
        StringBuffer outString = new StringBuffer(inString);
        while (lIndex < outString.length()) {
            lChar = outString.charAt(lIndex);
            if (!(lChar > '`' && lChar < '{' || lChar == '_' || lChar > '@' && lChar < '[' || lChar > '/' && lChar < ':')) {
                outString.replace(lIndex, lIndex + 1, "");
                continue;
            }
            ++lIndex;
        }
        return outString.toString();
    }

    public static String cleanName(String inString, char ... chars) {
        if (inString == null) {
            return "";
        }
        int lIndex = 0;
        char lChar = '\u0000';
        Arrays.sort(chars);
        StringBuffer outString = new StringBuffer(inString);
        while (lIndex < outString.length()) {
            lChar = outString.charAt(lIndex);
            if (!(lChar > '`' && lChar < '{' || lChar == '_' || lChar == '@' || lChar > '@' && lChar < '[' || lChar > '/' && lChar < ':' || lChar == '.' || lChar == '-' || lChar == '!' || chars.length > 0 && Arrays.binarySearch(chars, lChar) >= 0)) {
                outString.replace(lIndex, lIndex + 1, "");
                continue;
            }
            ++lIndex;
        }
        return outString.toString();
    }

    public static String cleanNonIdentifier(String inString) {
        return StringUtils.isEmpty(inString) ? inString : inString.replaceAll("[^a-zA-Z0-9_]", "");
    }

    public static String encodeJsText(String inString) {
        if (inString == null) {
            return "";
        }
        int lIndex = 0;
        char lChar = '\u0000';
        StringBuffer outString = new StringBuffer(inString);
        while (lIndex < outString.length()) {
            lChar = outString.charAt(lIndex);
            if (lChar == '\"') {
                outString.replace(lIndex, lIndex + 1, "\\\"");
                lIndex += 2;
                continue;
            }
            if (lChar == '\'') {
                outString.replace(lIndex, lIndex + 1, "\\'");
                lIndex += 2;
                continue;
            }
            if (lChar == '\\') {
                outString.replace(lIndex, lIndex + 1, "\\\\");
                lIndex += 2;
                continue;
            }
            if (lChar == '\r' || lChar == '\n') {
                outString.replace(lIndex, lIndex + 1, " ");
                ++lIndex;
                continue;
            }
            if (lChar == '/') {
                outString.replace(lIndex, lIndex + 1, "&#47;");
                lIndex += 5;
                continue;
            }
            ++lIndex;
        }
        return outString.toString();
    }

    public static String encodeQueryString(String inString) {
        if (inString == null) {
            return "";
        }
        int lIndex = 0;
        char lChar = '\u0000';
        StringBuffer outString = new StringBuffer(inString);
        while (lIndex < outString.length()) {
            lChar = outString.charAt(lIndex);
            if (lChar == '\"') {
                outString.replace(lIndex, lIndex + 1, "%22");
                lIndex += 3;
                continue;
            }
            if (lChar == '\'') {
                outString.replace(lIndex, lIndex + 1, "%27");
                lIndex += 3;
                continue;
            }
            if (lChar == '\r' || lChar == '\n') {
                outString.replace(lIndex, lIndex + 1, " ");
                ++lIndex;
                continue;
            }
            if (lChar == '/') {
                outString.replace(lIndex, lIndex + 1, "%2F");
                lIndex += 3;
                continue;
            }
            if (lChar == '<') {
                outString.replace(lIndex, lIndex + 1, "%3C");
                lIndex += 3;
                continue;
            }
            if (lChar == '>') {
                outString.replace(lIndex, lIndex + 1, "%3E");
                lIndex += 3;
                continue;
            }
            ++lIndex;
        }
        return outString.toString();
    }

    public static String encodeSQLText(String inString) {
        return inString;
    }

    public static String encodeResponseSplit(String inString) {
        if (inString == null) {
            return "";
        }
        return inString.replaceAll("\r", "").replaceAll("\n", "").replaceAll("\\\\", "").replaceAll("%0a|%0A", "").replaceAll("%0d|%0D", "");
    }

    public static String Double2Str(double value) {
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(value);
    }

    public static String fmtDouble(double value) {
        DecimalFormat df = new DecimalFormat("0.0000");
        return df.format(value);
    }

    public static String encodeHashMap(HashMap hashMap) {
        if (hashMap == null || hashMap.isEmpty()) {
            return null;
        }
        StringBuffer result = new StringBuffer();
        for (Object key : hashMap.keySet()) {
            result.append(key);
            result.append(':');
            Object value = hashMap.get(key);
            if (value != null) {
                result.append(value);
            }
            result.append(';');
        }
        if (result.length() > 0) {
            result.deleteCharAt(result.length() - 1);
        }
        return result.toString();
    }

    public static HashMap decodeHashMap(String s) {
        if (StringHelper.isEmpty((String)s)) {
            return null;
        }
        HashMap<String, String> result = new HashMap<String, String>();
        String[] maps = s.split(";");
        if (maps == null) {
            return result;
        }
        for (int i = 0; i < maps.length; ++i) {
            String[] entries;
            if (StringHelper.isEmpty((String)maps[i]) || (entries = maps[i].split(":")) == null) continue;
            if (entries.length == 1) {
                result.put(entries[0], null);
                continue;
            }
            if (entries.length != 2) continue;
            result.put(entries[0], entries[1]);
        }
        return result;
    }
}

