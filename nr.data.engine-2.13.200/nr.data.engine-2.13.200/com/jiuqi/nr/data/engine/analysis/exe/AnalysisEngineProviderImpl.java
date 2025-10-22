/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IConnectionProvider
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 */
package com.jiuqi.nr.data.engine.analysis.exe;

import com.jiuqi.np.dataengine.IConnectionProvider;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.data.engine.analysis.exe.AnalysisEngine;
import com.jiuqi.nr.data.engine.analysis.exe.IAnalysisEngine;
import com.jiuqi.nr.data.engine.analysis.exe.IAnalysisEngineProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnalysisEngineProviderImpl
implements IAnalysisEngineProvider {
    @Autowired
    private IConnectionProvider connectionProvider;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private IDataAccessProvider dataAccessProvider;

    @Override
    public IAnalysisEngine createAnalysisEngine() {
        AnalysisEngine engine = new AnalysisEngine();
        QueryParam queryParam = new QueryParam(this.connectionProvider, this.runtimeController);
        queryParam.setEncryptDecryptProcesser(this.dataAccessProvider.getEncryptDecryptProcesser());
        engine.setQueryParam(queryParam);
        return engine;
    }
}

