/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.parameter.manager;

import com.jiuqi.bi.parameter.extend.customlist.CustomListDataSourceDataProvider;
import com.jiuqi.bi.parameter.extend.customlist.CustomListDataSourceModelFactory;
import com.jiuqi.bi.parameter.extend.ds.field.DsFieldDataSourceDataProvider;
import com.jiuqi.bi.parameter.extend.ds.field.DsFieldDataSourceModelFactory;
import com.jiuqi.bi.parameter.extend.ds.hier.DsHierDataSourceDataProvider;
import com.jiuqi.bi.parameter.extend.ds.hier.DsHierDataSourceModelFactory;
import com.jiuqi.bi.parameter.extend.sql.SQLDSDataProvider;
import com.jiuqi.bi.parameter.extend.sql.SqlDataSourceModelFactory;
import com.jiuqi.bi.parameter.extend.zb.ZbParameterDataSourceDataProvider;
import com.jiuqi.bi.parameter.extend.zb.ZbParameterDataSourceModelFactory;
import com.jiuqi.bi.parameter.manager.DataSourceDataFactory;
import com.jiuqi.bi.parameter.manager.DataSourceModelFactory;
import com.jiuqi.bi.parameter.manager.IDataSourceDataProvider;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataSourceFactoryManager {
    private static Map<String, DataSourceDataFactory> dataFactoryMap = new HashMap<String, DataSourceDataFactory>();
    private static Map<String, DataSourceModelFactory> modelFactoryMap = new LinkedHashMap<String, DataSourceModelFactory>();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void registDataSourceDataFactory(String type, DataSourceDataFactory dataFactory) {
        if (StringUtils.isEmpty((String)type) || dataFactory == null) {
            return;
        }
        Map<String, DataSourceDataFactory> map = dataFactoryMap;
        synchronized (map) {
            if (!dataFactoryMap.containsKey(type)) {
                dataFactoryMap.put(type, dataFactory);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void registDataSourceModelFactory(String type, DataSourceModelFactory modelFactory) {
        if (StringUtils.isEmpty((String)type) || modelFactory == null) {
            return;
        }
        Map<String, DataSourceModelFactory> map = modelFactoryMap;
        synchronized (map) {
            if (!modelFactoryMap.containsKey(type)) {
                modelFactoryMap.put(type, modelFactory);
            }
        }
    }

    public static DataSourceDataFactory getDataSourceDataFactory(String type) {
        return dataFactoryMap.get(type);
    }

    public static DataSourceModelFactory getDataSourceModelFactory(String type) {
        return modelFactoryMap.get(type);
    }

    public static List<DataSourceModelFactory> getAllDataSourceModelFactory() {
        ArrayList<DataSourceModelFactory> modelFactories = new ArrayList<DataSourceModelFactory>();
        for (Map.Entry<String, DataSourceModelFactory> entry : modelFactoryMap.entrySet()) {
            modelFactories.add(entry.getValue());
        }
        return modelFactories;
    }

    static {
        DataSourceFactoryManager.registDataSourceDataFactory("com.jiuqi.bi.datasource.dsfield", new DataSourceDataFactory(){

            @Override
            public IDataSourceDataProvider createDataProvider(DataSourceModel dataSourceModel) {
                return new DsFieldDataSourceDataProvider(dataSourceModel);
            }
        });
        DataSourceFactoryManager.registDataSourceModelFactory("com.jiuqi.bi.datasource.dsfield", new DsFieldDataSourceModelFactory());
        DataSourceFactoryManager.registDataSourceDataFactory("com.jiuqi.bi.datasource.dshier", new DataSourceDataFactory(){

            @Override
            public IDataSourceDataProvider createDataProvider(DataSourceModel dataSourceModel) {
                return new DsHierDataSourceDataProvider(dataSourceModel);
            }
        });
        DataSourceFactoryManager.registDataSourceModelFactory("com.jiuqi.bi.datasource.dshier", new DsHierDataSourceModelFactory());
        DataSourceFactoryManager.registDataSourceDataFactory("com.jiuqi.bi.datasource.sql", new DataSourceDataFactory(){

            @Override
            public IDataSourceDataProvider createDataProvider(DataSourceModel dataSourceModel) {
                return new SQLDSDataProvider(dataSourceModel);
            }
        });
        DataSourceFactoryManager.registDataSourceModelFactory("com.jiuqi.bi.datasource.sql", new SqlDataSourceModelFactory());
        DataSourceFactoryManager.registDataSourceDataFactory("com.jiuqi.bi.datasource.customlist", new DataSourceDataFactory(){

            @Override
            public IDataSourceDataProvider createDataProvider(DataSourceModel dataSourceModel) {
                return new CustomListDataSourceDataProvider(dataSourceModel);
            }
        });
        DataSourceFactoryManager.registDataSourceModelFactory("com.jiuqi.bi.datasource.customlist", new CustomListDataSourceModelFactory());
        DataSourceFactoryManager.registDataSourceDataFactory("com.jiuqi.bi.datasource.zbparameter", new DataSourceDataFactory(){

            @Override
            public IDataSourceDataProvider createDataProvider(DataSourceModel dataSourceModel) {
                return new ZbParameterDataSourceDataProvider(dataSourceModel);
            }
        });
        DataSourceFactoryManager.registDataSourceModelFactory("com.jiuqi.bi.datasource.zbparameter", new ZbParameterDataSourceModelFactory());
    }
}

