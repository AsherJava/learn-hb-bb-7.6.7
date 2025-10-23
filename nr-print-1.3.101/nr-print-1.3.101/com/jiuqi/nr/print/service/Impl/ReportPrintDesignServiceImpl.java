/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.api.IParamDeployController
 *  com.jiuqi.nr.definition.common.ParamResourceType
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 */
package com.jiuqi.nr.print.service.Impl;

import com.jiuqi.grid.GridData;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.api.IParamDeployController;
import com.jiuqi.nr.definition.common.ParamResourceType;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.print.dto.PrintTableGridDTO;
import com.jiuqi.nr.print.dto.ReportLabelDTO;
import com.jiuqi.nr.print.exception.PrintDesignException;
import com.jiuqi.nr.print.service.IPrintDesignExtendService;
import com.jiuqi.nr.print.service.IReportPrintDesignService;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ReportPrintDesignServiceImpl
implements IReportPrintDesignService {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IDesignTimePrintController designTimePrintController;
    @Autowired
    private IPrintDesignExtendService printExtendService;
    @Autowired
    private IParamDeployController paramDeployController;

    @Override
    public void deployTemplate(String designerId, String schemeId, String formId) throws Exception {
        if (!this.printExtendService.templateIsSave(designerId)) {
            throw new JQException((ErrorEnum)PrintDesignException.TEMPLATE_UNSAVED);
        }
        this.paramDeployController.deploy(ParamResourceType.PRINT_TEMPLATE, schemeId, Collections.singletonList(formId));
    }

    @Override
    public List<ReportLabelDTO> updateReportLabel(ReportLabelDTO[] reportLabels, String designerId) {
        ReportLabelDTO oldLabel = reportLabels[0];
        ReportLabelDTO newLabel = reportLabels[1];
        return this.printExtendService.updateReportLabel(designerId, oldLabel, newLabel);
    }

    @Override
    public Map<String, Object> getAttribute(String designerId) {
        return this.printExtendService.getAttribute(designerId);
    }

    @Override
    public String getTableGrid(String designerId, String elementId) {
        return this.printExtendService.getTableGrid(designerId, elementId);
    }

    @Override
    public void updateTableGrid(PrintTableGridDTO tableGrid) {
        GridData gridData = IPrintDesignExtendService.cell2GridData(tableGrid.getCellBook());
        this.printExtendService.updateTableGrid(tableGrid.getDesignerId(), tableGrid.getElementId(), GridData.gridToBytes((GridData)gridData));
    }

    @Override
    public Map<String, Object> getFormulaEditorParams(String designerId) {
        String printSchemeKey = this.printExtendService.getCurrPrintSchemeKey(designerId);
        String taskKey = null;
        String formSchemeKey = null;
        String formGroupKey = null;
        String formKey = this.printExtendService.getCurrFormKey(designerId);
        if (StringUtils.hasText(formKey) && !"commonTem".equals(formKey) && !"coverTem".equals(formKey)) {
            DesignFormDefine form = this.designTimeViewController.getForm(formKey);
            formSchemeKey = form.getFormScheme();
            DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(formSchemeKey);
            taskKey = formScheme.getTaskKey();
            List groups = this.designTimeViewController.listFormGroupByForm(formKey);
            for (DesignFormGroupDefine group : groups) {
                if (!formSchemeKey.equals(group.getFormSchemeKey())) continue;
                formGroupKey = group.getKey();
                break;
            }
        } else {
            formKey = null;
            DesignPrintTemplateSchemeDefine printScheme = this.designTimePrintController.getPrintTemplateScheme(printSchemeKey);
            formSchemeKey = printScheme.getFormSchemeKey();
            DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(formSchemeKey);
            taskKey = formScheme.getTaskKey();
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("taskId", taskKey);
        params.put("schemeId", formSchemeKey);
        params.put("formGroupId", formGroupKey);
        params.put("formId", formKey);
        params.put("localFormId", formKey);
        params.put("runType", "designer");
        params.put("showPeriod", true);
        params.put("showMode", true);
        params.put("defaultShowMode", "2");
        params.put("formulaCheck", false);
        params.put("showWildcard", true);
        return params;
    }
}

