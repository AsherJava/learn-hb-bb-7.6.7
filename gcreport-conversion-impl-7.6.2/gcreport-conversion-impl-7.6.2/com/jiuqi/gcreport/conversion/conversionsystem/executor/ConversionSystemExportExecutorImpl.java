/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelModelExecutor
 *  com.jiuqi.np.log.LogHelper
 */
package com.jiuqi.gcreport.conversion.conversionsystem.executor;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelModelExecutor;
import com.jiuqi.gcreport.conversion.conversionsystem.executor.common.ConversionSystemItemExcelModel;
import com.jiuqi.gcreport.conversion.conversionsystem.service.ConversionSystemService;
import com.jiuqi.np.log.LogHelper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConversionSystemExportExecutorImpl
extends AbstractExportExcelModelExecutor<ConversionSystemItemExcelModel> {
    @Autowired
    private ConversionSystemService service;

    protected ConversionSystemExportExecutorImpl() {
        super(ConversionSystemItemExcelModel.class);
    }

    public String getName() {
        return "ConversionSystemExportExecutor";
    }

    protected List<ConversionSystemItemExcelModel> exportExcelModels(ExportContext context) {
        context.getProgressData().setProgressValueAndRefresh(0.1);
        ConversionSystemExportParam exportParam = (ConversionSystemExportParam)JsonUtils.readValue((String)context.getParam(), ConversionSystemExportParam.class);
        List<ConversionSystemItemExcelModel> systemItemExcelModels = this.service.exportExcel(exportParam);
        context.getProgressData().setProgressValueAndRefresh(0.9);
        LogHelper.info((String)"\u5408\u5e76-\u5916\u5e01\u6298\u7b97\u4f53\u7cfb\u8bbe\u7f6e", (String)"\u5bfc\u51fa--\u5916\u5e01\u6298\u7b97\u4f53\u7cfb", (String)("\u53c2\u6570\uff1a" + JsonUtils.writeValueAsString((Object)exportParam)));
        return systemItemExcelModels;
    }

    public static class ConversionSystemExportParam {
        private String exportType;
        private String systemId;
        private String taskId;
        private String schemeId;
        private String formGroupId;
        private String formId;

        public String getSystemId() {
            return this.systemId;
        }

        public void setSystemId(String systemId) {
            this.systemId = systemId;
        }

        public String getTaskId() {
            return this.taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getSchemeId() {
            return this.schemeId;
        }

        public void setSchemeId(String schemeId) {
            this.schemeId = schemeId;
        }

        public String getFormGroupId() {
            return this.formGroupId;
        }

        public void setFormGroupId(String formGroupId) {
            this.formGroupId = formGroupId;
        }

        public String getFormId() {
            return this.formId;
        }

        public void setFormId(String formId) {
            this.formId = formId;
        }

        public String getExportType() {
            return this.exportType;
        }

        public void setExportType(String exportType) {
            this.exportType = exportType;
        }
    }
}

