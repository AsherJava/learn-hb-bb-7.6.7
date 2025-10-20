/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelExecutor
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.dc.base.impl.orgcomb.impexp;

import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelExecutor;
import com.jiuqi.dc.base.impl.orgcomb.service.OrgCombGroupService;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrgCombExpExcelExecutorImpl
extends AbstractExportExcelExecutor {
    @Autowired
    OrgCombGroupService orgCombGroupService;

    public String getName() {
        return "OrgCombExportExcelExecutor";
    }

    public Object dataExport(HttpServletRequest request, HttpServletResponse response, ExportContext context) throws Exception {
        context.getProgressData().setProgressValueAndRefresh(0.1);
        JsonNode param = JsonUtils.readTree((String)context.getParam());
        String schemeId = Optional.ofNullable(param.get("schemeId")).map(JsonNode::textValue).orElse(null);
        this.orgCombGroupService.exportSchemes(schemeId, context.isTemplateExportFlag(), response);
        context.getProgressData().setProgressValueAndRefresh(0.8);
        return "\u5bfc\u51fa\u6210\u529f";
    }
}

