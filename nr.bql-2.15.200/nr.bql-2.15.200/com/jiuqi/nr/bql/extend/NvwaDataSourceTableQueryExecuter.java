/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.storage.DataSetStorageException
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.IConnectionProvider
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException
 *  com.jiuqi.nr.query.datascheme.extend.DataQueryParam
 *  com.jiuqi.nr.query.datascheme.extend.IDataTableQueryExecutor
 *  com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.DynamicDataSource
 */
package com.jiuqi.nr.bql.extend;

import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.storage.DataSetStorageException;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.IConnectionProvider;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.nr.bql.datasource.ComponentSet;
import com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException;
import com.jiuqi.nr.query.datascheme.extend.DataQueryParam;
import com.jiuqi.nr.query.datascheme.extend.IDataTableQueryExecutor;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.DynamicDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NvwaDataSourceTableQueryExecuter
implements IDataTableQueryExecutor {
    private static final Logger logger = LoggerFactory.getLogger(NvwaDataSourceTableQueryExecuter.class);
    private DynamicConnectionProvider connectionProvider;
    private String datasourceKey;

    public NvwaDataSourceTableQueryExecuter(ComponentSet componentSet, String srcKey) throws DataSetStorageException {
        this.datasourceKey = srcKey.split("\\.")[0];
        this.connectionProvider = new DynamicConnectionProvider(this.datasourceKey);
    }

    public MemoryDataSet<QueryField> execute(DataQueryParam param) throws DataTableAdaptException {
        return null;
    }

    public List<String> getParamNames() throws DataTableAdaptException {
        return null;
    }

    public IConnectionProvider getConnectionProvider() {
        return this.connectionProvider;
    }

    public String getDatasourceKey() {
        return this.datasourceKey;
    }

    private class DynamicConnectionProvider
    implements IConnectionProvider {
        private DataSource dataSource;

        public DynamicConnectionProvider(String datasourceKey) {
            this.dataSource = this.getDataSource(datasourceKey);
        }

        private DataSource getDataSource(String datasourceKey) {
            if (datasourceKey == null || "nrDataSource".equalsIgnoreCase(datasourceKey)) {
                return (DataSource)SpringBeanUtils.getBean(DataSource.class);
            }
            DynamicDataSource dynamicDataSource = (DynamicDataSource)SpringBeanUtils.getBean(DynamicDataSource.class);
            DataSource dataSource = dynamicDataSource.getDataSource(datasourceKey);
            return dataSource;
        }

        public Connection getConnection() {
            try {
                return this.dataSource.getConnection();
            }
            catch (SQLException e) {
                logger.error(e.getMessage(), e);
                return null;
            }
        }

        public void closeConnection(Connection connection) {
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }
}

