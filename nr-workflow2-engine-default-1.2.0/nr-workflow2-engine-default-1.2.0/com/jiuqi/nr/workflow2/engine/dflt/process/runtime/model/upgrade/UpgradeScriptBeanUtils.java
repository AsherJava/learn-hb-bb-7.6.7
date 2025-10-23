/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.model.upgrade;

import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class UpgradeScriptBeanUtils
implements InitializingBean {
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private DataModelDeployService dataModelDeployService;
    public static UpgradeScriptBeanUtils INSTANCE;

    @Override
    public void afterPropertiesSet() throws Exception {
        INSTANCE = this;
    }

    public static IRunTimeViewController getRunTimeViewController() {
        return UpgradeScriptBeanUtils.INSTANCE.viewController;
    }

    public static DesignDataModelService getDesignDataModelService() {
        return UpgradeScriptBeanUtils.INSTANCE.designDataModelService;
    }

    public static DataModelDeployService getDataModelDeployService() {
        return UpgradeScriptBeanUtils.INSTANCE.dataModelDeployService;
    }
}

