/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 */
package com.jiuqi.gcreport.billcore.common;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.billcore.util.BillParseTool;
import com.jiuqi.gcreport.billcore.vo.BillInfoVo;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public abstract class AbstractBillParseExportTask {
    private static ThreadLocal<BillInfoVo> billInfoVoLocal = new ThreadLocal();

    public final ExportExcelSheet exportExcelSheet(ExportContext context) {
        try {
            context.getProgressData().setProgressValueAndRefresh(0.1);
            this.parseBillInfo(context.getParam());
            ExportExcelSheet exportExcelSheet = this.doExecuteExport(context);
            return exportExcelSheet;
        }
        finally {
            this.releaseResource();
        }
    }

    protected final List<LinkedHashSet<String>> getAllBillItemSelectedColumnCodes() {
        ArrayList subColumnCodes = billInfoVoLocal.get().getSubColumnCodes();
        return null == subColumnCodes ? new ArrayList() : subColumnCodes;
    }

    protected final List<String> getFirstBillItemSelectedColumnCodes() {
        List<LinkedHashSet<String>> subColumnCodes = this.getAllBillItemSelectedColumnCodes();
        return subColumnCodes.isEmpty() ? new ArrayList<String>() : new ArrayList(subColumnCodes.get(0));
    }

    protected final List<String> getBillMasterSelectedColumnCodes() {
        LinkedHashSet<String> masterColumnCodes = billInfoVoLocal.get().getMasterColumnCodes();
        if (CollectionUtils.isEmpty(masterColumnCodes)) {
            return null;
        }
        return new ArrayList<String>(masterColumnCodes);
    }

    private void parseBillInfo(String param) {
        Map params = (Map)JsonUtils.readValue((String)param, Map.class);
        String defineCode = this.getBillCode(params);
        BillInfoVo billInfoVo = BillParseTool.parseBillInfo(defineCode);
        billInfoVoLocal.set(billInfoVo);
    }

    private void releaseResource() {
        billInfoVoLocal.remove();
    }

    protected final String getBillCode(Map<String, Object> params) {
        String customBillCode = this.getCustomBillCode();
        if (StringUtils.isEmpty((String)customBillCode)) {
            return (String)params.get("defineCode");
        }
        return customBillCode;
    }

    protected final String getMasterTableName() {
        return billInfoVoLocal.get().getMasterTableName();
    }

    protected final List<String> getSubTableNames() {
        return billInfoVoLocal.get().getSubTableNames();
    }

    protected final List<String> getSubPanelTitles() {
        return billInfoVoLocal.get().getSubPanelTitles();
    }

    protected final String getSubTableName() {
        List<String> subTableNames = this.getSubTableNames();
        if (CollectionUtils.isEmpty(subTableNames)) {
            return null;
        }
        return subTableNames.get(0);
    }

    abstract ExportExcelSheet doExecuteExport(ExportContext var1);

    protected abstract String getSheetName();

    protected int getSheetNo() {
        return 0;
    }

    protected String getCustomBillCode() {
        return null;
    }
}

