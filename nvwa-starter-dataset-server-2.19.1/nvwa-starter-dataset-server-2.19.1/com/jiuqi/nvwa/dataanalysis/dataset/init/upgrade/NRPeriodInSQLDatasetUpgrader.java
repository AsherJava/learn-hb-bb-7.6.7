/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nvwa.dataanalysis.dataset.init.upgrade;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.dataanalysis.dataset.init.upgrade.NRPeriodInSQLDatasetUpgradeHelper;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class NRPeriodInSQLDatasetUpgrader {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean execute(DataSource datasource) throws SQLException {
        Connection conn = datasource.getConnection();
        try {
            NRPeriodInSQLDatasetUpgradeHelper helper = (NRPeriodInSQLDatasetUpgradeHelper)SpringBeanUtils.getBean(NRPeriodInSQLDatasetUpgradeHelper.class);
            boolean bl = helper.execute(conn);
            return bl;
        }
        finally {
            DataSourceUtils.releaseConnection((Connection)conn, (DataSource)datasource);
        }
    }
}

