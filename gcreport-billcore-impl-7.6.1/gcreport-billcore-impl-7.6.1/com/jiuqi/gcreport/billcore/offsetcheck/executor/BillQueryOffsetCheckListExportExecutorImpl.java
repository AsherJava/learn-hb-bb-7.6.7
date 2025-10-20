/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelModelExecutor
 */
package com.jiuqi.gcreport.billcore.offsetcheck.executor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelModelExecutor;
import com.jiuqi.gcreport.billcore.offsetcheck.executor.model.BillQueryOffsetCheckListExcelModel;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BillQueryOffsetCheckListExportExecutorImpl
extends AbstractExportExcelModelExecutor<BillQueryOffsetCheckListExcelModel> {
    protected BillQueryOffsetCheckListExportExecutorImpl() {
        super(BillQueryOffsetCheckListExcelModel.class);
    }

    protected List<BillQueryOffsetCheckListExcelModel> exportExcelModels(ExportContext context) {
        List billQueryOffsetCheckListExcelModels = (List)JsonUtils.readValue((String)context.getParam(), (TypeReference)new TypeReference<List<BillQueryOffsetCheckListExcelModel>>(){});
        ArrayList<BillQueryOffsetCheckListExcelModel> billQueryOffsetCheckListExcelModelsAll = new ArrayList<BillQueryOffsetCheckListExcelModel>();
        this.collectAllNodes(billQueryOffsetCheckListExcelModels, billQueryOffsetCheckListExcelModelsAll);
        return billQueryOffsetCheckListExcelModelsAll;
    }

    private void collectAllNodes(List<BillQueryOffsetCheckListExcelModel> models, List<BillQueryOffsetCheckListExcelModel> allModels) {
        if (models == null) {
            return;
        }
        for (BillQueryOffsetCheckListExcelModel model : models) {
            allModels.add(model);
            this.collectAllNodes(model.getChildren(), allModels);
        }
    }

    public String getName() {
        return "BillQueryOffsetCheckListExportExecutor";
    }
}

