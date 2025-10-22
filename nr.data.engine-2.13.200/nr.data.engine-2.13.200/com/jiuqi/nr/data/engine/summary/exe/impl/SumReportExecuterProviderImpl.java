/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 */
package com.jiuqi.nr.data.engine.summary.exe.impl;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.data.engine.summary.define.ISumBaseDefineFactory;
import com.jiuqi.nr.data.engine.summary.exe.ISumReportExecuter;
import com.jiuqi.nr.data.engine.summary.exe.ISumReportExecuterProvider;
import com.jiuqi.nr.data.engine.summary.exe.impl.SumReportExecuterImpl;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SumReportExecuterProviderImpl
implements ISumReportExecuterProvider {
    @Autowired
    private ISumBaseDefineFactory baseDefineFactory;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private IDataAccessProvider dataAccessProvider;

    @Override
    public ISumReportExecuter getSumReportExecuter() {
        return new SumReportExecuterImpl(this.baseDefineFactory, this.dataAccessProvider, this.runtimeController, this.dataSource);
    }
}

