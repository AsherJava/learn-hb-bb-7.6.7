/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.distributeds.IDistributeDsValueProvider
 *  com.jiuqi.bi.sql.ConnectionWapper
 *  com.jiuqi.bi.sql.IConnectionProvider
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic;

import com.jiuqi.bi.distributeds.IDistributeDsValueProvider;
import com.jiuqi.bi.sql.ConnectionWapper;
import com.jiuqi.bi.sql.IConnectionProvider;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.sf.Framework;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.DynamicDataSource;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.exception.DataSourceNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.sql.DataSource;

public class NvwaDynamicConnectionProvicer
implements IConnectionProvider {
    private static final Set<String> defaultDatasourceNames = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("nrsqlDataset", "JQBI", "bPro")));

    public boolean isMultipleConnection(String arg0, IDistributeDsValueProvider arg1) throws SQLException {
        return false;
    }

    public Connection open(String name) throws SQLException {
        if (name == null || name.length() == 0 || defaultDatasourceNames.contains(name)) {
            return this.openDefault();
        }
        DynamicDataSource dynamicDataSource = (DynamicDataSource)SpringBeanUtils.getBean(DynamicDataSource.class);
        try {
            DataSource dataSource = dynamicDataSource.getDataSource(name);
            return dataSource.getConnection();
        }
        catch (DataSourceNotFoundException e) {
            throw new SQLException("DataSource " + name + " is not found", e);
        }
    }

    public Connection openDefault() throws SQLException {
        return Framework.getInstance().getConnectionProvider().getConnection();
    }

    public ConnectionWapper[] openDistributeConnecion(String arg0, IDistributeDsValueProvider arg1) throws SQLException {
        return new ConnectionWapper[0];
    }
}

