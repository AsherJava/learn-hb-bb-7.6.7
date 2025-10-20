/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter;

import com.jiuqi.bi.dataset.DSContext;
import com.jiuqi.bi.dataset.manager.DataSetManagerFactory;
import com.jiuqi.bi.dataset.manager.IDataSetManager;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.parameter.ParameterException;
import com.jiuqi.bi.parameter.engine.ParameterEngineEnv;
import com.jiuqi.bi.parameter.engine.ParameterEnvFactory;

public class DataSetManagerUtils {
    public static IDataSetManager createDataSetManager() {
        IDataSetManager dataSetManager = DataSetManagerFactory.create();
        return dataSetManager;
    }

    public static DSContext createDSContext(ParameterEngineEnv env) throws ParameterException {
        env.clearQueryProperties();
        DSContext context = new DSContext();
        context.setUserGuid(env.getUserGuid());
        context.setParameterEnv(ParameterEnvFactory.createParameterEnv(env));
        return context;
    }

    public static DSContext createDSContext(DSModel dsModel, String userGuid) {
        DSContext context = new DSContext();
        context.setUserGuid(userGuid);
        context.setDsModel(dsModel);
        return context;
    }
}

