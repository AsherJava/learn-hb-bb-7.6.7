/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.config.VaMapperConfig
 */
package com.jiuqi.va.query.utils;

import com.jiuqi.va.mapper.config.VaMapperConfig;
import com.jiuqi.va.query.common.DefineQuerySqlParseInfoVO;
import com.jiuqi.va.query.exception.DefinedQuerySqlException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;

public final class DefineQuerySqlUtils {
    private DefineQuerySqlUtils() {
    }

    public static DefineQuerySqlParseInfoVO getSqlParseInfoVO(DefinedQuerySqlException definedQuerySqlException) {
        String message = StringUtils.hasText(definedQuerySqlException.getMessage()) ? definedQuerySqlException.getMessage() : "\u7cfb\u7edf\u5904\u7406\u8fc7\u7a0b\u4e2d\u53d1\u751f\u5f02\u5e38\u9519\u8bef\u3002";
        String dbType = VaMapperConfig.getDbType();
        if ("dameng".equalsIgnoreCase(dbType)) {
            message = DefineQuerySqlUtils.dealDaMengMessage(message);
        }
        String regex = "at line (\\d+), column (\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        DefineQuerySqlParseInfoVO defineQuerySqlParseInfoVO = new DefineQuerySqlParseInfoVO();
        message = DefineQuerySqlUtils.removeBlankStr(message);
        defineQuerySqlParseInfoVO.setMessage(message);
        if (matcher.find()) {
            String lineNumber = matcher.group(1);
            String columnNumber = matcher.group(2);
            int startRow = Integer.parseInt(lineNumber);
            int startColumn = Integer.parseInt(columnNumber);
            defineQuerySqlParseInfoVO.setStartLineNumber(startRow);
            defineQuerySqlParseInfoVO.setEndLineNumber(startRow);
            defineQuerySqlParseInfoVO.setStartColumn(startColumn);
            defineQuerySqlParseInfoVO.setEndColumn(startColumn);
            defineQuerySqlParseInfoVO.setExistSqlErrorLine(true);
        }
        return defineQuerySqlParseInfoVO;
    }

    private static String dealDaMengMessage(String message) {
        if (!StringUtils.hasText(message)) {
            return message;
        }
        Pattern pattern = Pattern.compile("\u7b2c\\s*\\d+\\s*\u884c\u9644\u8fd1\u51fa\u73b0\u9519\u8bef:");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return matcher.replaceAll("");
        }
        return message;
    }

    private static String removeBlankStr(String text) {
        if (!StringUtils.hasText(text)) {
            return text;
        }
        StringBuilder result = new StringBuilder();
        boolean lastWasSpace = false;
        for (char c : text.toCharArray()) {
            if (c == '\n') continue;
            if (Character.isWhitespace(c)) {
                if (!lastWasSpace) {
                    result.append(' ');
                }
                lastWasSpace = true;
                continue;
            }
            result.append(c);
            lastWasSpace = false;
        }
        return result.toString().trim();
    }
}

