/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.impl.cfg.IdGenerator
 *  org.activiti.engine.impl.interceptor.CommandInterceptor
 *  org.activiti.engine.impl.persistence.StrongUuidGenerator
 *  org.activiti.spring.SpringProcessEngineConfiguration
 */
package com.jiuqi.nr.bpm.impl.activiti6;

import com.jiuqi.nr.bpm.impl.activiti6.extension.UpdateResourceCmd;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.activiti.engine.impl.cfg.IdGenerator;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.persistence.StrongUuidGenerator;
import org.activiti.spring.SpringProcessEngineConfiguration;

public class NrProcessEngineConfiguration
extends SpringProcessEngineConfiguration {
    private static final IdGenerator DEFAULTIDGENERATOR = new StrongUuidGenerator();
    private static final List<Class<?>> CUSTOMMYBATISMAPPERS = Arrays.asList(UpdateResourceCmd.UpdateResourceMapper.class);

    public NrProcessEngineConfiguration() {
        this.idGenerator = DEFAULTIDGENERATOR;
        this.databaseSchemaUpdate = null;
        this.enableProcessDefinitionInfoCache = true;
        this.setCustomMybatisMappers(new HashSet(CUSTOMMYBATISMAPPERS));
    }

    public CommandInterceptor createTransactionInterceptor() {
        if (this.transactionManager == null) {
            return null;
        }
        return super.createTransactionInterceptor();
    }
}

