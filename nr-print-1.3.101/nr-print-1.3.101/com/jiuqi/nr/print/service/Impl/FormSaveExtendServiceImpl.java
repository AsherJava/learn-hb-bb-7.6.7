/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.print.common.other.PrintUtil
 *  com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject
 *  com.jiuqi.nr.definition.print.common.PrintElementUtils
 *  com.jiuqi.nr.task.api.save.FormSaveContext
 *  com.jiuqi.nr.task.api.save.IFormSaveSettingProvider
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.xg.process.ITemplateDocument
 *  com.jiuqi.xg.process.ITemplateObject
 *  com.jiuqi.xg.process.util.SerializeUtil
 */
package com.jiuqi.nr.print.service.Impl;

import com.jiuqi.grid.GridData;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.print.common.other.PrintUtil;
import com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject;
import com.jiuqi.nr.definition.print.common.PrintElementUtils;
import com.jiuqi.nr.task.api.save.FormSaveContext;
import com.jiuqi.nr.task.api.save.IFormSaveSettingProvider;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.xg.process.ITemplateDocument;
import com.jiuqi.xg.process.ITemplateObject;
import com.jiuqi.xg.process.util.SerializeUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormSaveExtendServiceImpl
implements IFormSaveSettingProvider {
    @Autowired
    private IDesignTimePrintController printController;
    @Autowired
    private IDesignTimeViewController viewController;

    public String getCode() {
        return "PRINT_TEMPLATE_UPDATE_GRID";
    }

    public String getTitle() {
        return "\u6253\u5370\u7ba1\u7406";
    }

    public Double getOrder() {
        return 2.0;
    }

    public Boolean support(FormSaveContext context) {
        return true;
    }

    public Boolean needChange(FormSaveContext context) {
        if (null == context.isFormStyleChanged() || !context.isFormStyleChanged().booleanValue()) {
            return false;
        }
        List defines = this.printController.listPrintTemplateByForm(context.getFormKey());
        if (null == defines || defines.isEmpty()) {
            return false;
        }
        return defines.stream().anyMatch(t -> !t.isAutoRefreshForm());
    }

    public String executeSave(FormSaveContext context) {
        if (null == context.isFormStyleChanged() || !context.isFormStyleChanged().booleanValue()) {
            return null;
        }
        List defines = this.printController.listPrintTemplateByForm(context.getFormKey());
        if (null == defines || defines.isEmpty()) {
            return null;
        }
        ArrayList<DesignPrintTemplateDefine> update = new ArrayList<DesignPrintTemplateDefine>();
        for (DesignPrintTemplateDefine define : defines) {
            if (define.isAutoRefreshForm()) continue;
            Grid2Data grid2Data = this.viewController.getFormStyle(context.getFormKey());
            ITemplateDocument document = this.printController.getTemplateDocument(define);
            ReportTemplateObject template = PrintElementUtils.getReportTemplate((ITemplateDocument)document);
            if (null == template) continue;
            GridData gridData = new GridData();
            if (null != grid2Data) {
                PrintUtil.grid2DataToGridData((Grid2Data)grid2Data, (GridData)gridData);
            }
            template.setGridData(gridData);
            define.setTemplateData(SerializeUtil.serialize((ITemplateObject)document).getBytes());
            define.setAutoRefreshForm(true);
            define.setFormUpdateTime(new Date());
            update.add(define);
        }
        if (!update.isEmpty()) {
            this.printController.updatePrintTemplate(update.toArray(new DesignPrintTemplateDefine[0]));
        }
        return "";
    }
}

