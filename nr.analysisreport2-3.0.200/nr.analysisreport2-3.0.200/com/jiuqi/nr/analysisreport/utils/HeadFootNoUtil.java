/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.utils;

import com.jiuqi.nr.analysisreport.utils.CustomXWPFHeaderFooterPolicy;
import com.jiuqi.nr.analysisreport.utils.EvenHeadFootPolicy;
import com.jiuqi.nr.analysisreport.utils.OddHeadFootPolicy;
import java.util.Map;

public class HeadFootNoUtil {
    public static void createHeadFootNoPolicy(CustomXWPFHeaderFooterPolicy headerFooterPolicy, Map<String, String> headerSettings, Map<String, String> footerSettings, Map<String, String> noSettings) {
        new OddHeadFootPolicy(headerFooterPolicy, headerSettings, footerSettings, noSettings).create();
        new EvenHeadFootPolicy(headerFooterPolicy, headerSettings, footerSettings, noSettings).create();
    }
}

