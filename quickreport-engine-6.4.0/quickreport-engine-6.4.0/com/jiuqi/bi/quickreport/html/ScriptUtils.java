/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.html;

import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.bi.quickreport.model.ReportScript;
import com.jiuqi.bi.quickreport.storage.ReportStorageException;
import com.jiuqi.bi.quickreport.storage.ReportStorageManager;
import com.jiuqi.bi.util.StringUtils;

public class ScriptUtils {
    private ScriptUtils() {
    }

    public static String generateScript(String reportGuid) throws ReportStorageException {
        QuickReport report = ReportStorageManager.getStorage().loadReport(reportGuid);
        ReportScript script = report.getScript();
        if (StringUtils.isEmpty((String)script.getUIScript())) {
            return "";
        }
        return script.getUIScript();
    }
}

