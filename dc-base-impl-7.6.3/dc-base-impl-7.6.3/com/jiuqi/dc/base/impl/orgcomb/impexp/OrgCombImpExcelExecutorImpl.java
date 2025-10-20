/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelOneSheetExecutor
 */
package com.jiuqi.dc.base.impl.orgcomb.impexp;

import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelOneSheetExecutor;
import com.jiuqi.dc.base.impl.orgcomb.service.OrgCombGroupService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrgCombImpExcelExecutorImpl
extends AbstractImportExcelOneSheetExecutor {
    @Autowired
    OrgCombGroupService orgCombGroupService;

    protected Object importExcelSheet(ImportContext context, List<Object[]> excelDatas) {
        if (excelDatas == null || excelDatas.size() == 0) {
            return "Excel\u4e2d\u6ca1\u6709\u6570\u636e\uff0c\u5bfc\u5165\u5931\u8d25";
        }
        context.getProgressData().setProgressValueAndRefresh(0.1);
        JsonNode param = JsonUtils.readTree((String)context.getParam());
        String importRepeatType = Optional.ofNullable(param.get("impMode")).map(JsonNode::textValue).orElse("");
        String importGroupId = Optional.ofNullable(param.get("groupId")).map(JsonNode::textValue).orElse("");
        return this.orgCombGroupService.checkOrgCombImportData(importRepeatType, importGroupId, excelDatas);
    }

    public String getName() {
        return "OrgCombImportExcelExecutor";
    }
}

