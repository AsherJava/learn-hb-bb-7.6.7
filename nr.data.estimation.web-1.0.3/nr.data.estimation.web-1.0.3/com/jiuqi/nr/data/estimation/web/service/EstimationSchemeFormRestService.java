/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.estimation.service.IEstimationSchemeTemplateService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.data.estimation.web.service;

import com.jiuqi.nr.data.estimation.service.IEstimationSchemeTemplateService;
import com.jiuqi.nr.data.estimation.web.request.ActionOfCheckFormsParam;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EstimationSchemeFormRestService {
    @Resource
    private IEstimationSchemeTemplateService estimationSchemeTemplateService;

    public String restEstimationSchemeForms(ActionOfCheckFormsParam actionParameter) {
        return this.estimationSchemeTemplateService.updateEstimationSchemeForms(actionParameter.getEstimationScheme(), actionParameter.getFormIds());
    }
}

