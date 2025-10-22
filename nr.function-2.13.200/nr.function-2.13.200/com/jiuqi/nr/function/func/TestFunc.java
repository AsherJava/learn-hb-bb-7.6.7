/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.reportparser.function.ExistChinese
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.reportparser.function.ExistChinese;
import com.jiuqi.nr.function.func.MatchRegex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFunc {
    private static final Logger logger = LoggerFactory.getLogger(TestFunc.class);

    public static void main(String[] args) {
        logger.info("ExistChinese(\"\u2166\")=" + ExistChinese.callFunction((String)"\u2166"));
        logger.info("MatchRegex(\"fsdfsd!fedw\",\"^.*[!|*|(|)].*$\")=" + MatchRegex.callFunction("fsdfsd!fedw", "^.*[!|*|(|)].*$"));
    }
}

