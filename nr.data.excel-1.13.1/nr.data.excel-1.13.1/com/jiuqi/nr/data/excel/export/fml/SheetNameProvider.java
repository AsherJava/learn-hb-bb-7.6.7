/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.intf.ISheetNameProvider
 */
package com.jiuqi.nr.data.excel.export.fml;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.intf.ISheetNameProvider;
import java.util.Map;
import java.util.regex.Pattern;

public class SheetNameProvider
implements ISheetNameProvider {
    private static final Pattern regex = Pattern.compile("[()&]+");
    private final String currentSheetName;
    private final String currentReportName;
    private final Map<String, String> reportMap;

    public SheetNameProvider(String currentReportName, String currentSheetName, Map<String, String> reportMap) {
        this.currentReportName = currentReportName;
        this.currentSheetName = currentSheetName;
        this.reportMap = reportMap;
    }

    public String getSheetName(IContext context, String reportName) {
        return SheetNameProvider.getFormulaSheetName(this.getSheetName(reportName));
    }

    private String getSheetName(String reportName) {
        if (this.currentReportName.equals(reportName)) {
            return this.currentSheetName;
        }
        return this.reportMap.get(reportName);
    }

    private static String getFormulaSheetName(String sheetName) {
        if (!SheetNameProvider.isValidSheetName(sheetName)) {
            return "'" + sheetName + "'";
        }
        return sheetName;
    }

    private static boolean isValidSheetName(String sheetName) {
        if (regex.matcher(sheetName).find()) {
            return false;
        }
        return !Character.isDigit(sheetName.charAt(0));
    }
}

