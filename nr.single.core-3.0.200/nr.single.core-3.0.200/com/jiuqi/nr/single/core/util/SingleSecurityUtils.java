/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.FormulaUtils
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 */
package com.jiuqi.nr.single.core.util;

import com.jiuqi.bi.security.FormulaUtils;
import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleSecurityUtils {
    private static final Logger logger = LoggerFactory.getLogger(SingleSecurityUtils.class);

    public static void validatePathManipulation(String filePath) throws SingleFileException {
        try {
            PathUtils.validatePathManipulation((String)filePath);
        }
        catch (SecurityContentException e) {
            logger.error(e.getMessage(), e);
            throw new SingleFileException(e.getMessage(), e);
        }
    }

    public static void validateHeaderValue(String vlaue) throws SingleFileException {
        try {
            HtmlUtils.validateHeaderValue((String)vlaue);
        }
        catch (SecurityContentException e) {
            logger.error(e.getMessage(), e);
            throw new SingleFileException(e.getMessage(), e);
        }
    }

    public static String cleanHeaderValue(String vlaue) {
        return HtmlUtils.cleanHeaderValue((String)vlaue);
    }

    public static String cleanJsFuncName(String inString) {
        return HtmlUtils.cleanJsFuncName((String)inString);
    }

    public static String cleanJsText(String inString) {
        return HtmlUtils.cleanJsText((String)inString);
    }

    public static String cleanQueryStringPollution(String queryString) {
        return HtmlUtils.cleanQueryStringPollution((String)queryString);
    }

    public static String cleanUrlXSS(String value) {
        return HtmlUtils.cleanUrlXSS((String)value);
    }

    public static String cleanFormulaSymbol(String formula) {
        return FormulaUtils.cleanFormulaSymbol((String)formula);
    }

    public static void validateFormulaSymbol(String formula) throws SingleFileException {
        try {
            FormulaUtils.validateFormulaSymbol((String)formula);
        }
        catch (SecurityContentException e) {
            logger.error(e.getMessage(), e);
            throw new SingleFileException(e.getMessage(), e);
        }
    }
}

