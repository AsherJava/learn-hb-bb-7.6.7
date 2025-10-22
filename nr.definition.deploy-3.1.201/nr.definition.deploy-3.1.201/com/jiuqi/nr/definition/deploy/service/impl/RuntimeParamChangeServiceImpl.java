/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.deploy.DeployItem
 *  com.jiuqi.nr.definition.deploy.DeployFinishedEvent
 *  com.jiuqi.nr.definition.deploy.DeployParams
 *  com.jiuqi.nr.definition.exception.NrParamSyncException
 *  com.jiuqi.nr.definition.service.IParamCacheManagerService
 */
package com.jiuqi.nr.definition.deploy.service.impl;

import com.jiuqi.np.definition.deploy.DeployItem;
import com.jiuqi.nr.definition.deploy.DeployFinishedEvent;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.definition.deploy.common.ParamDeployItem;
import com.jiuqi.nr.definition.deploy.extend.RuntimeParamChangeEvent;
import com.jiuqi.nr.definition.deploy.service.IRuntimeParamChangeService;
import com.jiuqi.nr.definition.exception.NrParamSyncException;
import com.jiuqi.nr.definition.service.IParamCacheManagerService;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class RuntimeParamChangeServiceImpl
implements IRuntimeParamChangeService {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private IParamCacheManagerService paramCacheManagerService;

    @Override
    public void reload(List<ParamDeployItem> deployItems) throws NrParamSyncException {
        DeployParams deployParams = this.getDeployParams(deployItems);
        this.paramCacheManagerService.refreshCache((Collection)deployParams.getFormScheme().getRuntimeUninDesignTimeKeys(), (Collection)deployParams.getFormulaScheme().getRuntimeUninDesignTimeKeys());
        this.applicationContext.publishEvent(new RuntimeParamChangeEvent(new RuntimeParamChangeEvent.RuntimeParamChangeSource(deployItems)));
        this.applicationContext.publishEvent((ApplicationEvent)new DeployFinishedEvent((Object)deployParams, deployParams));
    }

    @Override
    public void reload(ParamDeployItem deployItem) throws NrParamSyncException {
        DeployParams deployParams = this.getDeployParams(Collections.singletonList(deployItem));
        this.paramCacheManagerService.refreshCache((Collection)deployParams.getFormScheme().getRuntimeUninDesignTimeKeys(), (Collection)deployParams.getFormulaScheme().getRuntimeUninDesignTimeKeys());
        this.applicationContext.publishEvent(new RuntimeParamChangeEvent(new RuntimeParamChangeEvent.RuntimeParamChangeSource(deployItem)));
        this.applicationContext.publishEvent((ApplicationEvent)new DeployFinishedEvent((Object)deployParams, deployParams));
    }

    private DeployParams getDeployParams(List<ParamDeployItem> deployItems) {
        DeployParams deployParams = new DeployParams();
        for (ParamDeployItem deployItem : deployItems) {
            switch (deployItem.getType()) {
                case FORM: {
                    DeployItem formScheme = deployParams.getFormScheme();
                    if (CollectionUtils.isEmpty(formScheme.getDesignTimeKeys())) {
                        formScheme.setDesignTimeKeys(new HashSet());
                        formScheme.setRunTimeKeys(new HashSet());
                    }
                    formScheme.getDesignTimeKeys().add(deployItem.getSchemeKey());
                    DeployItem form = deployParams.getForm();
                    if (CollectionUtils.isEmpty(form.getDesignTimeKeys())) {
                        form.setDesignTimeKeys(new HashSet());
                        form.setRunTimeKeys(new HashSet());
                    }
                    form.getDesignTimeKeys().addAll(deployItem.getResourceKeys());
                    break;
                }
                case FORMULA: {
                    DeployItem formulaScheme = deployParams.getFormulaScheme();
                    if (CollectionUtils.isEmpty(formulaScheme.getDesignTimeKeys())) {
                        formulaScheme.setDesignTimeKeys(new HashSet());
                        formulaScheme.setRunTimeKeys(new HashSet());
                    }
                    formulaScheme.getDesignTimeKeys().add(deployItem.getSchemeKey());
                    DeployItem formula = deployParams.getFormula();
                    if (CollectionUtils.isEmpty(formula.getDesignTimeKeys())) {
                        formula.setDesignTimeKeys(new HashSet());
                        formula.setRunTimeKeys(new HashSet());
                    }
                    formula.getDesignTimeKeys().addAll(deployItem.getResourceKeys());
                    break;
                }
                case PRINT_TEMPLATE: {
                    DeployItem printScheme = deployParams.getPrintScheme();
                    if (CollectionUtils.isEmpty(printScheme.getDesignTimeKeys())) {
                        printScheme.setDesignTimeKeys(new HashSet());
                        printScheme.setRunTimeKeys(new HashSet());
                    }
                    printScheme.getDesignTimeKeys().add(deployItem.getSchemeKey());
                    DeployItem printTemplate = deployParams.getPrintTemplate();
                    if (CollectionUtils.isEmpty(printTemplate.getDesignTimeKeys())) {
                        printTemplate.setDesignTimeKeys(new HashSet());
                        printTemplate.setRunTimeKeys(new HashSet());
                    }
                    printTemplate.getDesignTimeKeys().addAll(deployItem.getResourceKeys());
                    break;
                }
            }
        }
        return deployParams;
    }
}

