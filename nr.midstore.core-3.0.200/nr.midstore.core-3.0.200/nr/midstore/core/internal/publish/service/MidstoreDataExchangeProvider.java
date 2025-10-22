/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeProvider
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.DynamicDataSource
 */
package nr.midstore.core.internal.publish.service;

import com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeProvider;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.DynamicDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import nr.midstore.core.internal.publish.service.MidstorePublishTaskServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MidstoreDataExchangeProvider
implements IDataExchangeProvider {
    private static final Logger logger = LoggerFactory.getLogger(MidstorePublishTaskServiceImpl.class);
    private DataSource dataSource = null;

    public MidstoreDataExchangeProvider(String dbName) {
        try {
            DynamicDataSource dynamicDataSource = (DynamicDataSource)SpringBeanUtils.getBean(DynamicDataSource.class);
            this.dataSource = dynamicDataSource.getDataSource(dbName);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public Connection getConnection() throws DataExchangeException {
        if (this.dataSource != null) {
            try {
                Connection connect = this.dataSource.getConnection();
                return connect;
            }
            catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new DataExchangeException(e.getMessage());
            }
        }
        return null;
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }
}

