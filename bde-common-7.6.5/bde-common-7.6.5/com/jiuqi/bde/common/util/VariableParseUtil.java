/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.util;

import java.util.Map;

public class VariableParseUtil {
    private static final String OPEN_TOKEN = "${";
    private static final String CLOSE_TOKEN = "}";

    public static String parse(String text, Map<String, String> variableMap) {
        if (variableMap == null || variableMap.isEmpty()) {
            return text;
        }
        boolean containsVariable = false;
        for (String val : variableMap.values()) {
            if (val == null || !val.contains(OPEN_TOKEN)) continue;
            containsVariable = true;
            break;
        }
        String sql = VariableParseUtil.parse(text, variableMap, OPEN_TOKEN, CLOSE_TOKEN);
        if (!containsVariable) {
            return sql;
        }
        return VariableParseUtil.parse(VariableParseUtil.parse(sql, variableMap, OPEN_TOKEN, CLOSE_TOKEN), variableMap, OPEN_TOKEN, CLOSE_TOKEN);
    }

    public static String parse(String text, Map<String, String> variableMap, String openToken, String closeToken) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        int start = text.indexOf(openToken);
        if (start == -1) {
            return text;
        }
        char[] src = text.toCharArray();
        int offset = 0;
        StringBuilder builder = new StringBuilder();
        StringBuilder expression = null;
        do {
            if (start > 0 && src[start - 1] == '\\') {
                builder.append(src, offset, start - offset - 1).append(openToken);
                offset = start + openToken.length();
                continue;
            }
            if (expression == null) {
                expression = new StringBuilder();
            } else {
                expression.setLength(0);
            }
            builder.append(src, offset, start - offset);
            offset = start + openToken.length();
            int end = text.indexOf(closeToken, offset);
            while (end > -1) {
                if (end > offset && src[end - 1] == '\\') {
                    expression.append(src, offset, end - offset - 1).append(closeToken);
                    offset = end + closeToken.length();
                    end = text.indexOf(closeToken, offset);
                    continue;
                }
                expression.append(openToken).append(src, offset, end - offset).append(closeToken);
                break;
            }
            if (end == -1) {
                builder.append(src, start, src.length - start);
                offset = src.length;
                continue;
            }
            builder.append(VariableParseUtil.replaceVariable(expression.toString(), variableMap));
            offset = end + closeToken.length();
        } while ((start = text.indexOf(openToken, offset)) > -1);
        if (offset < src.length) {
            builder.append(src, offset, src.length - offset);
        }
        return builder.toString();
    }

    private static String replaceVariable(String expression, Map<String, String> variableMap) {
        if (variableMap.containsKey(expression)) {
            return variableMap.get(expression);
        }
        return expression;
    }
}

