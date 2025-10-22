/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.AnalysisFormParamDefine
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.nr.dataentry.bean.FormAnalysisConfig;
import com.jiuqi.nr.dataentry.service.IFormAnalysisService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.AnalysisFormParamDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormAnalysisServiceImpl
implements IFormAnalysisService {
    private final Logger logger = LoggerFactory.getLogger(FormAnalysisServiceImpl.class);
    @Autowired
    IRunTimeViewController runTimeViewController;

    @Override
    public FormAnalysisConfig getFormAnalysisConfig(String formKey) {
        AnalysisFormParamDefine queryAnalysisFormParamDefine = null;
        FormAnalysisConfig analysisConfig = new FormAnalysisConfig();
        try {
            queryAnalysisFormParamDefine = this.runTimeViewController.queryAnalysisFormParamDefine(formKey);
        }
        catch (Exception e) {
            this.logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (queryAnalysisFormParamDefine != null) {
            analysisConfig.setAutoAnalysis(queryAnalysisFormParamDefine.isAutoAnalysis());
            analysisConfig.setFunctionCondition(queryAnalysisFormParamDefine.getFunctionCondition());
            analysisConfig.setFunctionName(queryAnalysisFormParamDefine.getFunctionName());
        }
        return analysisConfig;
    }
}

