/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.progress.ProgressCacheService
 *  com.jiuqi.np.definition.progress.ProgressItem
 */
package com.jiuqi.nr.definition.internal.controller;

import com.jiuqi.np.definition.progress.ProgressCacheService;
import com.jiuqi.np.definition.progress.ProgressItem;
import com.jiuqi.nr.definition.controller.IViewDeployController;
import com.jiuqi.nr.definition.internal.service.ViewDeployService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ViewDeployController
implements IViewDeployController {
    @Autowired
    private ViewDeployService deployService;
    @Autowired
    private ProgressCacheService progressCacheService;
    private static final Logger logger = LoggerFactory.getLogger(ViewDeployController.class);

    @Override
    public void deployFormDefine(String formKey) throws Exception {
        this.deployService.deployFormDefine(formKey);
    }

    @Override
    public void deployTask(String taskKey, boolean deployDataScheme) throws Exception {
        logger.info("\u53d1\u5e03\u4efb\u52a1\uff1a\u53d1\u5e03\u5f00\u59cb");
        try {
            this.deployService.deployTask(taskKey, deployDataScheme);
        }
        catch (Exception e) {
            logger.error("\u53d1\u5e03\u4efb\u52a1\uff1a\u53d1\u5e03\u5f02\u5e38", e);
            ProgressItem progress = this.progressCacheService.getProgress(taskKey);
            progress.markException(e);
            this.progressCacheService.setProgress(taskKey, progress);
        }
        logger.info("\u53d1\u5e03\u4efb\u52a1\uff1a\u53d1\u5e03\u7ed3\u675f");
    }

    @Override
    public void deployTaskToDes(String taskKey) throws Exception {
        this.deployService.deployTaskToDes(taskKey);
    }

    @Override
    public void deployFormulaScheme(String formulaSchemeKey, boolean deployFormula) throws Exception {
        this.deployService.deployFormulaScheme(formulaSchemeKey, deployFormula);
    }

    @Override
    public void deployFormulaScheme(String formulaSchemeKey, String formKey) throws Exception {
        this.deployService.deployFormulaScheme(formulaSchemeKey, formKey);
    }

    @Override
    public void deployPrintScheme(String printSchemeKey, boolean deployTemplate) throws Exception {
        this.deployService.deployPrintScheme(printSchemeKey, deployTemplate);
    }

    @Override
    public void deployPrintTemplate(String printTemplateKey) throws Exception {
        this.deployService.deployPrintTemplate(printTemplateKey);
    }

    @Override
    public void deployFormulaConditions(String task, String ... conditionKeys) {
        this.deployService.deployFormulaConditions(task, conditionKeys);
    }

    @Override
    public void deployTaskLinkByFormScheme(String formSchemeKey) {
        this.deployService.deployTaskLinkByFormScheme(formSchemeKey);
    }
}

