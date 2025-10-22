/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.access.param.AccessCode
 *  com.jiuqi.nr.data.access.param.AccessItem
 *  com.jiuqi.nr.data.access.param.IAccessMessage
 *  com.jiuqi.nr.data.access.service.IDataExtendAccessItemService
 *  com.jiuqi.nr.data.estimation.common.utils.EstimationSchemeConst
 *  com.jiuqi.nr.data.estimation.service.IEstimationSchemeTemplateService
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationSchemeTemplate
 *  com.jiuqi.nr.data.estimation.storage.enumeration.EstimationFormType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.data.estimation.web.ext.dataentry;

import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.AccessItem;
import com.jiuqi.nr.data.access.param.IAccessMessage;
import com.jiuqi.nr.data.access.service.IDataExtendAccessItemService;
import com.jiuqi.nr.data.estimation.common.utils.EstimationSchemeConst;
import com.jiuqi.nr.data.estimation.service.IEstimationSchemeTemplateService;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationSchemeTemplate;
import com.jiuqi.nr.data.estimation.storage.enumeration.EstimationFormType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EstimationFormReadWriteAccess
implements IDataExtendAccessItemService {
    @Resource
    private IEstimationSchemeTemplateService estimationSchemeTemplateService;

    public AccessCode visible(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return new AccessCode(this.name());
    }

    public AccessCode readable(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return new AccessCode(this.name());
    }

    public AccessCode writeable(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        String estimationSchemeKey;
        IEstimationSchemeTemplate estimationTemplate;
        JtableContext context;
        Map variableMap;
        Object params = param.getParams();
        if (params instanceof JtableContext && EstimationSchemeConst.isEstimationDataEntry((Map)(variableMap = (context = (JtableContext)params).getVariableMap())) && (estimationTemplate = this.estimationSchemeTemplateService.findSchemeTemplateByKey(estimationSchemeKey = variableMap.get("estimationScheme").toString())).getEstimationForms().stream().filter(e -> e.getFormType() == EstimationFormType.outputForm).anyMatch(e -> e.getFormDefine().getKey().equals(formKey))) {
            return new AccessCode(this.name(), "2");
        }
        return new AccessCode(this.name());
    }

    public String name() {
        return "Estimation_FORM_RWA";
    }

    public boolean isServerAccess() {
        return true;
    }

    public IAccessMessage getAccessMessage() {
        return code -> "\u6d4b\u7b97\u65b9\u6848\u4e2d\u7684\u8f93\u51fa\u8868\u4e0d\u5141\u8bb8\u7f16\u8f91\u6570\u636e";
    }

    public int getOrder() {
        return 1;
    }
}

