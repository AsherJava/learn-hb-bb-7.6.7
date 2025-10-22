/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.definition.deploy.DeployParams
 *  com.jiuqi.nr.definition.internal.runtime.service.RuntimeDefinitionChangeListener
 */
package com.jiuqi.nr.definition.internal.runtime.service;

import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingRepairService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.definition.internal.runtime.service.RuntimeDefinitionChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=0x7FFFFFFF)
public class BdeRuntimeDefinitionChangeListener
implements RuntimeDefinitionChangeListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdeRuntimeDefinitionChangeListener.class);
    @Autowired
    private FetchSettingRepairService repairService;

    public void onDeploy(DeployParams deployParams) {
        try {
            LOGGER.info("BDE\u53d6\u6570\u8bbe\u7f6e\u5f00\u59cb\u76d1\u542c\u62a5\u8868\u53d1\u5e03\uff0c\u5e76\u5f00\u59cb\u6267\u884c\u53d6\u6570\u8bbe\u7f6e\u4fee\u590d......");
            this.repairService.doRepair(NpContextHolder.getContext().getUserName(), deployParams);
            LOGGER.info("BDE\u53d6\u6570\u8bbe\u7f6e\u5f00\u59cb\u76d1\u542c\u62a5\u8868\u53d1\u5e03\uff0c\u5e76\u5b8c\u6210\u6267\u884c\u53d6\u6570\u8bbe\u7f6e\u4fee\u590d......");
        }
        catch (Exception e) {
            LOGGER.info("BDE\u53d6\u6570\u8bbe\u7f6e\u5f00\u59cb\u76d1\u542c\u62a5\u8868\u53d1\u5e03\uff0c\u5e76\u6267\u884c\u53d6\u6570\u8bbe\u7f6e\u4fee\u590d\u671f\u95f4\u53d1\u751f\u9519\u8bef{}", e);
        }
    }
}

