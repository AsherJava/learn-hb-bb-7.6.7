/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.sql.command.SQLCommand
 *  com.jiuqi.bi.database.sql.parser.SQLCommandParser
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.DefinitionAutoCollectionService
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.gcreport.offsetitem.upgrade;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.command.SQLCommand;
import com.jiuqi.bi.database.sql.parser.SQLCommandParser;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.DefinitionAutoCollectionService;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class UpgradeOffsetItemAmtField
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void execute(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringContextUtils.getBean(JdbcTemplate.class);
        try {
            String offsetTableKey = this.getOffsetTableKey(jdbcTemplate);
            if (StringUtils.isEmpty((String)offsetTableKey)) {
                this.logger.info("\u62b5\u9500\u5206\u5f55\u6a21\u578b\u91cd\u6784\uff1a\u672a\u627e\u5230\u62b5\u9500\u5206\u5f55\u8868\uff0c\u5224\u65ad\u4e3a\u7a7a\u5e93\u542f\u52a8\uff0c\u8df3\u8fc7\u5386\u53f2\u6570\u636e\u4fee\u590d\u3002");
                return;
            }
            this.logger.info("\u62b5\u9500\u5206\u5f55\u6a21\u578b\u91cd\u6784\uff1a\u5f00\u59cb\u540c\u6b65\u62b5\u9500\u5206\u5f55\u6570\u636e\u5efa\u6a21");
            ((DefinitionAutoCollectionService)SpringContextUtils.getBean(DefinitionAutoCollectionService.class)).initTableDefineByTableName("GC_OFFSETVCHRITEM");
            this.logger.info("\u62b5\u9500\u5206\u5f55\u6a21\u578b\u91cd\u6784\uff1a\u5f00\u59cb\u4fee\u590d\u91d1\u989d\u5386\u53f2\u6570\u636e");
            this.repairAmtData(jdbcTemplate);
            this.logger.info("\u62b5\u9500\u5206\u5f55\u6a21\u578b\u91cd\u6784\uff1a\u5f00\u59cb\u79fb\u9664\u5e9f\u5f03\u91d1\u989d\u5b57\u6bb5");
            this.removeDiscardAmtField(jdbcTemplate);
            this.logger.info("\u62b5\u9500\u5206\u5f55\u6a21\u578b\u91cd\u6784\uff1a\u91d1\u989d\u5b57\u6bb5\u4fee\u590d\u5b8c\u6210");
        }
        catch (Exception e) {
            this.logger.error("\u62b5\u9500\u5206\u5f55\u6a21\u578b\u91cd\u6784\uff0c\u4fee\u590d\u91d1\u989d\u5386\u53f2\u6570\u636e\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
    }

    private String getOffsetTableKey(JdbcTemplate jdbcTemplate) {
        String sql = "select table_ID from nvwa_tablemodel_des where table_code='GC_OFFSETVCHRITEM' ";
        String offsetTableKey = (String)jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                return rs.getString(1);
            }
            return null;
        });
        return offsetTableKey;
    }

    private void repairAmtData(JdbcTemplate jdbcTemplate) {
        String sql = "UPDATE GC_OFFSETVCHRITEM \n      SET OFFSET_DEBIT  = OFFSET_DEBIT_CNY + OFFSET_DEBIT_HKD + OFFSET_DEBIT_USD, \n          OFFSET_CREDIT = OFFSET_CREDIT_CNY + OFFSET_CREDIT_HKD + OFFSET_CREDIT_USD, \n          DIFFD         = DIFFD_CNY + DIFFD_HKD + DIFFD_USD, \n          DIFFC         = DIFFC_CNY + DIFFC_HKD + DIFFC_USD \n    WHERE 1 = 1 ";
        jdbcTemplate.update(sql);
    }

    private void removeDiscardAmtField(JdbcTemplate jdbcTemplate) {
        List<String> discardFieldList = Arrays.asList("ORIENT", "DEBIT_CNY", "DEBIT_HKD", "DEBIT_USD", "CREDIT_CNY", "CREDIT_HKD", "CREDIT_USD", "OFFSET_DEBIT_CNY", "OFFSET_DEBIT_HKD", "OFFSET_DEBIT_USD", "OFFSET_CREDIT_CNY", "OFFSET_CREDIT_HKD", "OFFSET_CREDIT_USD", "DIFFD_CNY", "DIFFD_HKD", "DIFFD_USD", "DIFFC_CNY", "DIFFC_HKD", "DIFFC_USD");
        String deleteSql = "DELETE FROM %1$s WHERE TABLE_ID IN (SELECT TABLE_ID FROM %2$s WHERE TABLE_CODE = 'GC_OFFSETVCHRITEM') AND  %3$s ";
        String discardFieldInSql = SqlBuildUtil.getStrInCondi((String)"FIELD_CODE", discardFieldList);
        jdbcTemplate.update(String.format(deleteSql, "NVWA_COLUMNMODEL_DES", "NVWA_TABLEMODEL_DES", discardFieldInSql));
        jdbcTemplate.update(String.format(deleteSql, "NVWA_COLUMNMODEL", "NVWA_TABLEMODEL", discardFieldInSql));
        this.executeRemoveSql(discardFieldList, jdbcTemplate);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void executeRemoveSql(List<String> discardFieldList, JdbcTemplate jdbcTemplate) {
        String removeFieldSql = "ALTER TABLE GC_OFFSETVCHRITEM DROP COLUMN %1$s ;\n";
        StringBuilder allFieldRemoveSql = new StringBuilder();
        for (String fieldCode : discardFieldList) {
            allFieldRemoveSql.append(String.format(removeFieldSql, fieldCode));
        }
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection((DataSource)jdbcTemplate.getDataSource());
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            List commands = new SQLCommandParser().parse(database, allFieldRemoveSql.toString());
            for (SQLCommand command : commands) {
                command.execute(connection);
                this.logger.info(command.toString(database.getName()));
            }
        }
        catch (Exception e) {
            this.logger.error("\u62b5\u9500\u5206\u5f55\u6a21\u578b\u91cd\u6784\uff0c\u5220\u9664\u5df2\u5e9f\u9664\u91d1\u989d\u5b57\u6bb5\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)jdbcTemplate.getDataSource());
            }
        }
    }
}

