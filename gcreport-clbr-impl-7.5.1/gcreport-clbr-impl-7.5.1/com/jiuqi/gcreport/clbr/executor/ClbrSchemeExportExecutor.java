/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelModelExecutor
 *  com.jiuqi.gcreport.clbr.vo.ClbrSchemeCondition
 */
package com.jiuqi.gcreport.clbr.executor;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelModelExecutor;
import com.jiuqi.gcreport.clbr.executor.model.ClbrSchemeExcelModel;
import com.jiuqi.gcreport.clbr.service.ClbrSchemeService;
import com.jiuqi.gcreport.clbr.vo.ClbrSchemeCondition;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClbrSchemeExportExecutor
extends AbstractExportExcelModelExecutor<ClbrSchemeExcelModel> {
    @Autowired
    private ClbrSchemeService clbrSchemeService;

    protected ClbrSchemeExportExecutor() {
        super(ClbrSchemeExcelModel.class);
    }

    public String getName() {
        return "ClbrSchemeExportExecutor";
    }

    protected List<ClbrSchemeExcelModel> exportExcelModels(ExportContext context) {
        ClbrSchemeCondition clbrSchemeCondition = (ClbrSchemeCondition)JsonUtils.readValue((String)context.getParam(), ClbrSchemeCondition.class);
        return this.clbrSchemeService.clbrSchemeExport(clbrSchemeCondition);
    }
}

