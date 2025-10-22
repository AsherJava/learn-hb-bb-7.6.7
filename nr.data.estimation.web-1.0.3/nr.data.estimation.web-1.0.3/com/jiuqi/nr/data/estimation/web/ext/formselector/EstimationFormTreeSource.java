/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.estimation.common.utils.EstimationSchemeUtils
 *  com.jiuqi.nr.data.estimation.service.IEstimationSchemeTemplateService
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationSchemeTemplate
 *  com.jiuqi.nr.data.estimation.storage.enumeration.EstimationFormType
 *  com.jiuqi.nr.form.selector.context.IFormQueryHelper
 *  com.jiuqi.nr.form.selector.tree.IReportFormTreeData
 *  com.jiuqi.nr.form.selector.tree.IReportFormTreeProvider
 *  com.jiuqi.nr.form.selector.tree.IReportFormTreeSource
 *  com.jiuqi.nr.form.selector.tree.impl.ReportFormTreeData
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.data.estimation.web.ext.formselector;

import com.jiuqi.nr.data.estimation.common.utils.EstimationSchemeUtils;
import com.jiuqi.nr.data.estimation.service.IEstimationSchemeTemplateService;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationSchemeTemplate;
import com.jiuqi.nr.data.estimation.storage.enumeration.EstimationFormType;
import com.jiuqi.nr.data.estimation.web.auth.IEstimationFormAuthChecker;
import com.jiuqi.nr.data.estimation.web.enumeration.DataEntryAction;
import com.jiuqi.nr.data.estimation.web.enumeration.DataEntryToolBarMenus;
import com.jiuqi.nr.data.estimation.web.ext.formselector.EstimationFormTreeProvider;
import com.jiuqi.nr.form.selector.context.IFormQueryHelper;
import com.jiuqi.nr.form.selector.tree.IReportFormTreeData;
import com.jiuqi.nr.form.selector.tree.IReportFormTreeProvider;
import com.jiuqi.nr.form.selector.tree.IReportFormTreeSource;
import com.jiuqi.nr.form.selector.tree.impl.ReportFormTreeData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class EstimationFormTreeSource
implements IReportFormTreeSource {
    @Resource
    private IEstimationSchemeTemplateService estimationSchemeTemplateService;
    @Resource
    private IEstimationFormAuthChecker formAuthChecker;

    public String getFormSourceId() {
        return "estimation_form_tree_source";
    }

    public IReportFormTreeData getTreeProvider(IFormQueryHelper context) {
        String currentAction = context.getCustomVariable().getString("actionId");
        if (DataEntryToolBarMenus.newEstimationMenu.code.equals(currentAction)) {
            return this.onNewSchemeClick(context);
        }
        if (DataEntryAction.selectForms.name.equals(currentAction)) {
            return this.onRestFormsClick(context);
        }
        return this.onTakeDataFromOriginalClick(context);
    }

    private IReportFormTreeData onNewSchemeClick(IFormQueryHelper context) {
        String estimationSchemeKey = context.getCustomVariable().getString("estimationScheme");
        IEstimationSchemeTemplate schemeTemplate = this.estimationSchemeTemplateService.findSchemeTemplateByKey(estimationSchemeKey);
        List<String> canReadForms = this.formAuthChecker.getCanReadForms(schemeTemplate, EstimationSchemeUtils.convert2DimValueSet((Map)context.getDimValueSet()));
        EstimationFormTreeProvider formProvider = new EstimationFormTreeProvider(context, canReadForms);
        return new ReportFormTreeData((IReportFormTreeProvider)formProvider, new ArrayList());
    }

    private IReportFormTreeData onRestFormsClick(IFormQueryHelper context) {
        String estimationSchemeKey = context.getCustomVariable().getString("estimationScheme");
        IEstimationSchemeTemplate userEstimationScheme = this.estimationSchemeTemplateService.findSchemeTemplateByKey(estimationSchemeKey);
        IEstimationSchemeTemplate schemeTemplate = this.estimationSchemeTemplateService.findSchemeTemplateByFormScheme(userEstimationScheme.getFormSchemeDefine().getKey());
        List<String> canReadForms = this.formAuthChecker.getCanReadForms(schemeTemplate, EstimationSchemeUtils.convert2DimValueSet((Map)context.getDimValueSet()));
        EstimationFormTreeProvider formProvider = new EstimationFormTreeProvider(context, canReadForms);
        List checkList = userEstimationScheme.getEstimationForms().stream().map(e -> e.getFormDefine().getKey()).collect(Collectors.toList());
        return new ReportFormTreeData((IReportFormTreeProvider)formProvider, checkList);
    }

    private IReportFormTreeData onTakeDataFromOriginalClick(IFormQueryHelper context) {
        String estimationSchemeKey = context.getCustomVariable().getString("estimationScheme");
        IEstimationSchemeTemplate estimationScheme = this.estimationSchemeTemplateService.findSchemeTemplateByKey(estimationSchemeKey);
        List<String> filterList = estimationScheme.getEstimationForms().stream().filter(e -> e.getFormType() == EstimationFormType.inputForm).map(e -> e.getFormDefine().getKey()).collect(Collectors.toList());
        EstimationFormTreeProvider formProvider = new EstimationFormTreeProvider(context, filterList);
        return new ReportFormTreeData((IReportFormTreeProvider)formProvider, context.getCheckForms());
    }
}

