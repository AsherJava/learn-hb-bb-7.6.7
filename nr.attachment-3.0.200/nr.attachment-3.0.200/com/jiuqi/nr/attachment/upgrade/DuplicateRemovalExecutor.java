/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.attachment.upgrade;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class DuplicateRemovalExecutor
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(DuplicateRemovalExecutor.class);
    private IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringBeanUtils.getBean(IRuntimeDataSchemeService.class);
    private DataModelService dataModelService = (DataModelService)SpringBeanUtils.getBean(DataModelService.class);
    private JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringBeanUtils.getBean(JdbcTemplate.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute(DataSource dataSource) throws Exception {
        Connection connection = this.createConnection();
        try {
            List allDataScheme = this.runtimeDataSchemeService.getAllDataScheme();
            for (DataScheme dataScheme : allDataScheme) {
                String tableName = "NR_FILE_" + dataScheme.getBizCode();
                TableModelDefine tableModelDefineByName = this.dataModelService.getTableModelDefineByName(tableName);
                if (null == tableModelDefineByName) continue;
                StringBuilder sql = new StringBuilder();
                sql.append("delete from ").append(tableName).append(" t ");
                sql.append("where (t.").append("GROUPKEY").append(",t.").append("FILEKEY").append(") in ");
                sql.append("(select tt.").append("GROUPKEY").append(",tt.").append("FILEKEY");
                sql.append(" from (select ").append("GROUPKEY").append(",").append("FILEKEY");
                sql.append(" from ").append(tableName);
                sql.append(" group by ").append("GROUPKEY").append(",").append("FILEKEY");
                sql.append(" having count(*)>1) tt) ");
                sql.append("and t.").append("ID").append(" not in (select ttt.id");
                sql.append(" from (").append("select min(t1.").append("ID").append(") id ");
                sql.append("from ").append(tableName).append(" t1 ");
                sql.append("group by t1.").append("GROUPKEY").append(",t1.").append("FILEKEY");
                sql.append(" having count(*)>1) ttt)");
                this.implementSQL(connection, sql.toString());
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            this.closeConnection(connection);
            logger.info("\u9644\u4ef6\u53bb\u91cd\u5b8c\u6210");
        }
    }

    public Connection createConnection() {
        return DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getDataSource());
    }

    public void closeConnection(Connection connection) {
        if (null != connection) {
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean implementSQL(Connection connection, String sql) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.execute();
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
            boolean bl = false;
            return bl;
        }
        finally {
            this.close(statement);
        }
        return true;
    }

    private void close(Statement prep) {
        try {
            if (prep != null) {
                prep.close();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}

