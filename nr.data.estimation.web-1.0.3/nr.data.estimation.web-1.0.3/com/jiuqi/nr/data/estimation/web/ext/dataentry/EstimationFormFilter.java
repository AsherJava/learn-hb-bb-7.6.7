/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.estimation.common.utils.EstimationSchemeConst
 *  com.jiuqi.nr.data.estimation.service.IEstimationSchemeTemplateService
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationSchemeTemplate
 *  com.jiuqi.nr.dataentry.gather.IDataentryFormFilter
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.data.estimation.web.ext.dataentry;

import com.jiuqi.nr.data.estimation.common.utils.EstimationSchemeConst;
import com.jiuqi.nr.data.estimation.service.IEstimationSchemeTemplateService;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationSchemeTemplate;
import com.jiuqi.nr.dataentry.gather.IDataentryFormFilter;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class EstimationFormFilter
implements IDataentryFormFilter {
    @Resource
    private IEstimationSchemeTemplateService estimationSchemeTemplateService;

    public boolean doFilter(JtableContext jtableContext, String formKey) {
        Map variableMap = jtableContext.getVariableMap();
        if (EstimationSchemeConst.isEstimationDataEntry((Map)variableMap)) {
            String estimationSchemeKey = variableMap.get("estimationScheme").toString();
            IEstimationSchemeTemplate estimationTemplate = this.estimationSchemeTemplateService.findSchemeTemplateByKey(estimationSchemeKey);
            return estimationTemplate.getEstimationForms().stream().anyMatch(e -> e.getFormDefine().getKey().equals(formKey));
        }
        return true;
    }
}

