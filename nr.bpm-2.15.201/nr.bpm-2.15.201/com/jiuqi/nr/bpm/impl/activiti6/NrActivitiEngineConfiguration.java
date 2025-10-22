/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.spring.SpringProcessEngineConfiguration
 */
package com.jiuqi.nr.bpm.impl.activiti6;

import com.jiuqi.nr.bpm.impl.activiti6.NrDbSqlSessionConfigurator;
import com.jiuqi.nr.bpm.impl.activiti6.config.ActivitiEngineConfigurationConfigurer;
import java.util.ArrayList;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NrActivitiEngineConfiguration
implements ActivitiEngineConfigurationConfigurer {
    @Autowired
    private NrDbSqlSessionConfigurator dbSqlSessionConfigurator;

    @Override
    public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
        processEngineConfiguration.setUsingRelationalDatabase(false);
        ArrayList<NrDbSqlSessionConfigurator> configurators = new ArrayList<NrDbSqlSessionConfigurator>();
        configurators.add(this.dbSqlSessionConfigurator);
        processEngineConfiguration.setConfigurators(configurators);
    }
}

