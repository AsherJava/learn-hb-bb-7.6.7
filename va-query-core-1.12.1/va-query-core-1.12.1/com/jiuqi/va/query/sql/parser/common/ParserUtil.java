/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.sql.parser.common;

import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParserUtil {
    private ParserUtil() {
    }

    public static boolean existParams(String srcSql, List<String> params) {
        StringBuilder prepareSql = new StringBuilder(srcSql.length());
        int paramSize = params.size();
        for (int sqlIndex = 0; sqlIndex < srcSql.length(); ++sqlIndex) {
            char ch = srcSql.charAt(sqlIndex);
            if (!ParserUtil.isStartChar(srcSql, sqlIndex)) {
                prepareSql.append(ch);
                continue;
            }
            boolean findParam = false;
            for (int paramIndex = 0; paramIndex < paramSize; ++paramIndex) {
                String paramName = params.get(paramIndex);
                if ('#' == ch) {
                    if (!ParserUtil.contains(srcSql, sqlIndex + 1, paramName = paramName + '#')) continue;
                    findParam = true;
                    break;
                }
                if ('@' != ch || !ParserUtil.contains(srcSql, sqlIndex + 1, paramName)) continue;
                findParam = true;
                break;
            }
            if (!findParam) continue;
            return true;
        }
        return false;
    }

    private static boolean isStartChar(String srcSql, int sqlIndex) {
        char ch = srcSql.charAt(sqlIndex);
        return '@' == ch || '#' == ch;
    }

    private static boolean contains(String srcSql, int beginIndex, String paramName) {
        int pSize = paramName.length();
        if (beginIndex + pSize > srcSql.length()) {
            return false;
        }
        for (int i = beginIndex; i < beginIndex + pSize; ++i) {
            char a = srcSql.charAt(i);
            char b = paramName.charAt(i - beginIndex);
            if (Character.toLowerCase(a) == Character.toLowerCase(b)) continue;
            return false;
        }
        if (beginIndex + pSize < srcSql.length()) {
            char c = srcSql.charAt(beginIndex + pSize);
            return !Character.isLetterOrDigit(c) && c != '_';
        }
        return true;
    }

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(ParserUtil.class);
        logger.info("{}", (Object)ParserUtil.existParams("select * from Harry_test_enum where q1 = @Q1 AND q2 like concat(concat('%', @Q1ABC), '%')", Collections.singletonList("Q1")));
        logger.info("{}", (Object)ParserUtil.existParams("select * from Harry_test_enum where q1 = @Q1 AND q2 like concat(concat('%', @Q1ABC), '%')", Collections.singletonList("Q1ABC")));
        logger.info("{}", (Object)ParserUtil.existParams("select * from Harry_test_enum where q2 like concat(concat('%', @Q1ABC), '%')", Collections.singletonList("Q1")));
    }
}

