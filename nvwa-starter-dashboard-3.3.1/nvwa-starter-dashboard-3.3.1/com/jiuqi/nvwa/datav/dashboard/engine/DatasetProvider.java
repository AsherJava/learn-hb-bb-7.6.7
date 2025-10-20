/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.BIDataSetException
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.BIDataSetImpl
 *  com.jiuqi.bi.dataset.DSContext
 *  com.jiuqi.bi.dataset.IDSContext
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.manager.DataSetManagerFactory
 *  com.jiuqi.bi.dataset.manager.IDataSetManager
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.DataSetTypeNotFoundException
 *  com.jiuqi.nvwa.datav.dashboard.engine.dataset.DSRef
 *  com.jiuqi.nvwa.datav.dashboard.engine.dataset.IDatasetProvider
 *  com.jiuqi.nvwa.datav.dashboard.exception.DashboardException
 *  com.jiuqi.nvwa.dispatch.core.TaskException
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue
 *  com.jiuqi.nvwa.framework.parameter.model.value.DefaultParameterValueFormat
 *  com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat
 */
package com.jiuqi.nvwa.datav.dashboard.engine;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.DSContext;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.manager.DataSetManagerFactory;
import com.jiuqi.bi.dataset.manager.IDataSetManager;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.DataSetTypeNotFoundException;
import com.jiuqi.nvwa.datav.dashboard.engine.cache.DashboardRenderCacheManager;
import com.jiuqi.nvwa.datav.dashboard.engine.cache.ICacheDashboardRenderProvider;
import com.jiuqi.nvwa.datav.dashboard.engine.dataset.DSRef;
import com.jiuqi.nvwa.datav.dashboard.engine.dataset.IDatasetProvider;
import com.jiuqi.nvwa.datav.dashboard.exception.DashboardException;
import com.jiuqi.nvwa.dispatch.core.TaskException;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.DefaultParameterValueFormat;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class DatasetProvider
implements IDatasetProvider {
    public BIDataSet getDataSet(DSContext context, String sessionId) throws DashboardException {
        ICacheDashboardRenderProvider cacheProvider = null;
        try {
            cacheProvider = DashboardRenderCacheManager.getInstance().getCacheProvider(sessionId, false);
        }
        catch (TaskException e) {
            throw new DashboardException((Throwable)e);
        }
        if (cacheProvider == null) {
            throw new DashboardException("\u7f13\u5b58\u8fc7\u671f\uff0c\u8bf7\u5237\u65b0\u9875\u9762");
        }
        DSModel dsModel = context.getDsModel();
        DSRef dsRef = new DSRef();
        dsRef.setDsName(dsModel.getName());
        dsRef.setType(dsModel.getType());
        Map<String, List<Object>> kvs = null;
        BIDataSet dataset = null;
        if (cacheProvider.isDatasetCached(dsRef)) {
            try {
                kvs = this.collectParamValue(context.getEnhancedParameterEnv());
            }
            catch (ParameterException e) {
                throw new DashboardException((Throwable)e);
            }
            cacheProvider.setParamValue(dsRef, kvs);
            MemoryDataSet<BIDataSetFieldInfo> memory = cacheProvider.getDataSet(dsRef);
            if (memory != null) {
                dataset = new BIDataSetImpl(memory);
            }
        }
        if (dataset == null) {
            IDataSetManager dataSetManager = DataSetManagerFactory.create();
            try {
                dataset = dataSetManager.open((IDSContext)context, context.getDsModel());
            }
            catch (BIDataSetException | DataSetTypeNotFoundException e) {
                throw new DashboardException(e);
            }
        }
        return dataset;
    }

    private Map<String, List<Object>> collectParamValue(IParameterEnv env) throws ParameterException {
        HashMap<String, List<Object>> kvs = new HashMap<String, List<Object>>();
        for (ParameterModel pm : env.getParameterModels()) {
            AbstractParameterValue v = env.getOriginalValue(pm.getName());
            List<Object> values = this.getValues(v, pm);
            if (values == null) continue;
            kvs.put(pm.getName(), values);
        }
        return kvs;
    }

    private List<Object> getValues(AbstractParameterValue parameterValue, ParameterModel parameterModel) throws ParameterException {
        if (parameterValue == null) {
            return null;
        }
        IParameterValueFormat format = this.getParameterValueFormat(parameterModel);
        return parameterValue.toValueList(format);
    }

    private IParameterValueFormat getParameterValueFormat(ParameterModel parameterModel) {
        ParameterDataSourceManager mgr = ParameterDataSourceManager.getInstance();
        AbstractParameterDataSourceFactory factory = mgr.getFactory(parameterModel.getDatasource().getType());
        Object format = factory != null ? factory.createValueFormat(parameterModel.getDatasource()) : new DefaultParameterValueFormat(parameterModel.getDatasource());
        return format;
    }
}

