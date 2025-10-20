/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.metadata.ISQLMetadata
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.sql.command.CombinedSQLCommand
 *  com.jiuqi.bi.database.sql.command.SQLCommand
 *  com.jiuqi.bi.database.sql.command.StatementCommand
 *  com.jiuqi.bi.database.sql.command.TableExistCommand
 *  com.jiuqi.bi.database.statement.AlterColumnStatement
 *  com.jiuqi.bi.database.statement.AlterType
 *  com.jiuqi.bi.database.statement.CreateTableStatement
 *  com.jiuqi.bi.database.statement.SqlStatement
 *  com.jiuqi.bi.dblib.DBConnection
 *  com.jiuqi.bi.syntax.context.IUserBindingContext
 *  com.jiuqi.bi.syntax.env.EnvException
 *  com.jiuqi.bi.syntax.env.EnvProviderManager
 *  com.jiuqi.bi.syntax.env.IEnvProvider
 *  com.jiuqi.bi.syntax.sql.SQLQueryException
 *  com.jiuqi.bi.syntax.sql.SQLQueryExecutor
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.model.Result
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.user.dto.UserDTO
 *  com.jiuqi.np.user.feign.client.NvwaUserClient
 *  com.jiuqi.nvwa.authority.login.service.INvwaLoginLogService
 *  com.jiuqi.nvwa.dataanalysis.dataset.authfilter.NvwaSQLQueryExecutorFactory
 *  com.jiuqi.nvwa.dataanalysis.dataset.migrate.DatasetMigrate
 *  com.jiuqi.nvwa.dataanalysis.dataset.office.DatasetDataProcessor
 *  com.jiuqi.nvwa.dataanalysis.dataset.office.StringDataProcessor
 *  com.jiuqi.nvwa.dataanalyze.office.DataAnalyzeSchemaDataProviderRegistry
 *  com.jiuqi.nvwa.framework.parameter.datasource.extend.ISQLQueryProvider
 *  com.jiuqi.nvwa.framework.parameter.datasource.extend.SQLQueryHelper
 *  com.jiuqi.nvwa.login.service.NvwaLoginService
 *  com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.DynamicDataSource
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.organization.service.OrgDataService
 *  javax.servlet.ServletContext
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nvwa.dataanalysis.dataset.init;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.sql.command.CombinedSQLCommand;
import com.jiuqi.bi.database.sql.command.SQLCommand;
import com.jiuqi.bi.database.sql.command.StatementCommand;
import com.jiuqi.bi.database.sql.command.TableExistCommand;
import com.jiuqi.bi.database.statement.AlterColumnStatement;
import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.CreateTableStatement;
import com.jiuqi.bi.database.statement.SqlStatement;
import com.jiuqi.bi.dblib.DBConnection;
import com.jiuqi.bi.syntax.context.IUserBindingContext;
import com.jiuqi.bi.syntax.env.EnvException;
import com.jiuqi.bi.syntax.env.EnvProviderManager;
import com.jiuqi.bi.syntax.env.IEnvProvider;
import com.jiuqi.bi.syntax.sql.SQLQueryException;
import com.jiuqi.bi.syntax.sql.SQLQueryExecutor;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.model.Result;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.user.dto.UserDTO;
import com.jiuqi.np.user.feign.client.NvwaUserClient;
import com.jiuqi.nvwa.authority.login.service.INvwaLoginLogService;
import com.jiuqi.nvwa.dataanalysis.dataset.authfilter.NvwaSQLQueryExecutorFactory;
import com.jiuqi.nvwa.dataanalysis.dataset.migrate.DatasetMigrate;
import com.jiuqi.nvwa.dataanalysis.dataset.office.DatasetDataProcessor;
import com.jiuqi.nvwa.dataanalysis.dataset.office.StringDataProcessor;
import com.jiuqi.nvwa.dataanalyze.office.DataAnalyzeSchemaDataProviderRegistry;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.ISQLQueryProvider;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.SQLQueryHelper;
import com.jiuqi.nvwa.login.service.NvwaLoginService;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.DynamicDataSource;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.organization.service.OrgDataService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

@Component
public class NvwaDatasetInit
implements ModuleInitiator {
    private static final Logger logger = LoggerFactory.getLogger(NvwaDatasetInit.class);
    private static final String TABLE_AUTHFILTER_TABLE_MAPPING = "AUTHFILTER_TABLE_MAPPING";
    @Autowired
    private DatasetMigrate datasetMigrate;
    @Autowired
    private NvwaSQLQueryExecutorFactory executorFactory;
    @Autowired
    private NvwaUserClient userService;
    @Autowired
    private NvwaLoginService loginService;
    @Autowired
    private INvwaLoginLogService loginLogService;
    @Autowired
    private OrgDataService orgService;

    public void init(ServletContext context) throws Exception {
        if (this.datasetMigrate.needMigrate()) {
            this.datasetMigrate.migrateDSGroup();
            this.datasetMigrate.migrateDSModel();
        }
        SQLQueryHelper.setSQLQueryProvider((ISQLQueryProvider)new ISQLQueryProvider(){

            public Connection getConnection(String datasourceId) throws SQLException {
                if (StringUtils.isEmpty((String)datasourceId)) {
                    datasourceId = "_default";
                }
                DynamicDataSource dynamicDataSource = (DynamicDataSource)SpringBeanUtils.getBean(DynamicDataSource.class);
                final DataSource datasource = dynamicDataSource.getDataSource(datasourceId);
                final Connection conn = dynamicDataSource.getConnection(datasourceId);
                return new DBConnection(conn, true, true){

                    public void close() throws SQLException {
                        super.close();
                        DataSourceUtils.releaseConnection((Connection)conn, (DataSource)datasource);
                    }
                };
            }

            public String getContextUserId() {
                return NpContextHolder.getContext().getUserId();
            }

            public SQLQueryExecutor createQueryExecutor(Connection conn, String userId, String datasourceId) throws SQLQueryException {
                return NvwaDatasetInit.this.executorFactory.createQueryExecutor(conn);
            }
        });
        EnvProviderManager.registerProvider((IEnvProvider)new IEnvProvider(){

            public Object getEnvValue(Object context, String propName) throws EnvException {
                if (propName.equalsIgnoreCase("login.session")) {
                    return NvwaDatasetInit.this.loginService.getLoginSessionCount(null);
                }
                if (propName.equalsIgnoreCase("login.user")) {
                    Result onlineUserCountDistinct = NvwaDatasetInit.this.loginLogService.getOnlineUserCountDistinct();
                    return onlineUserCountDistinct.getDatas();
                }
                String envData = null;
                if (context instanceof IUserBindingContext) {
                    IUserBindingContext bc = (IUserBindingContext)context;
                    envData = NvwaDatasetInit.this.getEnvFromBindingContext(propName, bc);
                }
                if (envData == null) {
                    envData = NvwaDatasetInit.this.getEnvFromNpContext(propName);
                }
                return envData;
            }
        });
        this.createAuthFilterTable();
        DataAnalyzeSchemaDataProviderRegistry providerRegistry = DataAnalyzeSchemaDataProviderRegistry.getInstance();
        providerRegistry.registerDataProviderFactory("dataset", DatasetDataProcessor::new);
        providerRegistry.registerDataProviderFactory("expr", StringDataProcessor::new);
    }

    private String getEnvFromNpContext(String propName) {
        NpContext cxt = NpContextHolder.getContext();
        if (cxt == null) {
            return null;
        }
        if (propName.equalsIgnoreCase("org.name")) {
            if (cxt.getOrganization() == null) {
                String userId = cxt.getUserId();
                if (StringUtils.isNotEmpty((String)userId)) {
                    UserDTO user = this.userService.get(userId);
                    if (user == null) {
                        user = this.userService.getByName(userId);
                    }
                    if (user == null) {
                        return null;
                    }
                    return user.getOrgCode();
                }
                return null;
            }
            return cxt.getOrganization().getCode();
        }
        if (propName.equalsIgnoreCase("org.title")) {
            if (cxt.getOrganization() == null) {
                String userId = cxt.getUserId();
                if (StringUtils.isNotEmpty((String)userId)) {
                    UserDTO user = this.userService.get(userId);
                    if (user == null) {
                        return null;
                    }
                    OrgDTO orgdto = new OrgDTO();
                    orgdto.setCode(user.getOrgCode());
                    OrgDO org = this.orgService.get(orgdto);
                    return org == null ? null : org.getName();
                }
                return null;
            }
            return cxt.getOrganization().getName();
        }
        if (propName.equalsIgnoreCase("user.name")) {
            return cxt.getUserName();
        }
        if (propName.equalsIgnoreCase("user.title")) {
            return cxt.getUser() == null ? null : cxt.getUser().getFullname();
        }
        return null;
    }

    private String getEnvFromBindingContext(String propName, IUserBindingContext context) {
        String userId = context.getUserID();
        UserDTO user = this.userService.get(userId);
        if (user == null) {
            user = this.userService.getByName(userId);
        }
        if (user == null) {
            return null;
        }
        if (propName.equalsIgnoreCase("org.name")) {
            String code = this.getEnvFromNpContext(propName);
            if (code != null) {
                return code;
            }
            return user.getOrgCode();
        }
        if (propName.equalsIgnoreCase("org.title")) {
            String title = this.getEnvFromNpContext(propName);
            if (title != null) {
                return title;
            }
            OrgDTO orgdto = new OrgDTO();
            orgdto.setCode(user.getOrgCode());
            OrgDO org = this.orgService.get(orgdto);
            return org == null ? null : org.getName();
        }
        if (propName.equalsIgnoreCase("user.name")) {
            return user.getName();
        }
        if (propName.equalsIgnoreCase("user.title")) {
            return user.getNickname();
        }
        return null;
    }

    public void initWhenStarted(ServletContext context) throws Exception {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void createAuthFilterTable() {
        DataSource datasource = ((JdbcTemplate)SpringBeanUtils.getBean(JdbcTemplate.class)).getDataSource();
        try (Connection conn = datasource.getConnection();){
            CreateTableStatement createTableStatement = new CreateTableStatement(null, TABLE_AUTHFILTER_TABLE_MAPPING);
            createTableStatement.setJudgeExists(true);
            createTableStatement.addColumn(this.buildUserId());
            createTableStatement.addColumn(this.buildFilterTableName());
            createTableStatement.addColumn(this.buildTableName());
            createTableStatement.addColumn(this.buildLastAccessTime());
            createTableStatement.addColumn(this.buildStatus());
            CombinedSQLCommand cmd = new CombinedSQLCommand();
            cmd.addCommand((SQLCommand)new TableExistCommand(TABLE_AUTHFILTER_TABLE_MAPPING), false);
            cmd.addCommand((SQLCommand)new StatementCommand((SqlStatement)createTableStatement));
            cmd.execute(conn);
            ISQLMetadata meta = DatabaseManager.getInstance().createMetadata(conn);
            List fields = meta.getFieldsByTableName(TABLE_AUTHFILTER_TABLE_MAPPING);
            boolean containStatusCol = false;
            for (LogicField field : fields) {
                if (!field.getFieldName().equalsIgnoreCase("TABLE_STATUS")) continue;
                containStatusCol = true;
                break;
            }
            if (!containStatusCol) {
                AlterColumnStatement alterColumnStatement = new AlterColumnStatement(TABLE_AUTHFILTER_TABLE_MAPPING, AlterType.ADD);
                alterColumnStatement.setNewColumn(this.buildStatus());
                new StatementCommand((SqlStatement)alterColumnStatement).execute(conn);
            }
        }
        catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    private LogicField buildUserId() {
        LogicField field = new LogicField();
        field.setFieldName("USERID");
        field.setDataType(6);
        field.setSize(150);
        field.setNullable(false);
        return field;
    }

    private LogicField buildFilterTableName() {
        LogicField field = new LogicField();
        field.setFieldName("FILTER_TABLE_NAME");
        field.setDataType(6);
        field.setSize(150);
        field.setNullable(false);
        return field;
    }

    private LogicField buildTableName() {
        LogicField field = new LogicField();
        field.setFieldName("TABLE_NAME");
        field.setDataType(6);
        field.setSize(150);
        field.setNullable(true);
        return field;
    }

    private LogicField buildStatus() {
        LogicField field = new LogicField();
        field.setFieldName("TABLE_STATUS");
        field.setDataType(5);
        field.setNullable(true);
        field.setSize(1);
        field.setDefaultValue("1");
        field.setPrecision(0);
        return field;
    }

    private LogicField buildLastAccessTime() {
        LogicField field = new LogicField();
        field.setFieldName("LAST_ACCESS_TIME");
        field.setDataType(5);
        field.setNullable(true);
        field.setSize(32);
        field.setPrecision(0);
        return field;
    }
}

