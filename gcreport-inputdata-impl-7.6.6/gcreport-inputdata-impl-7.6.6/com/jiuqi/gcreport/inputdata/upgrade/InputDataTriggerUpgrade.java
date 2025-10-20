/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.InputDataSchemeEO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.inputdata.upgrade;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.InputDataSchemeEO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class InputDataTriggerUpgrade
implements CustomClassExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(InputDataTriggerUpgrade.class);
    private IRuntimeDataSchemeService iRuntimeDataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean((String)"RuntimeDataSchemeNoCacheServiceImpl-NO_CACHE", IRuntimeDataSchemeService.class);
    private DataModelService modelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
    private JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringContextUtils.getBean(JdbcTemplate.class);
    private String TRIGGER_SQL_ORACLE = "SELECT * FROM ALL_TRIGGERS WHERE TABLE_NAME=?";
    private static final String ORACLESQL_NAME = "ORACLE";
    private static final String DM_NAME = "DM";

    public void execute(DataSource dataSource) throws Exception {
        Set<String> inputDataNames = this.listInputDataTableName();
        if (CollectionUtils.isEmpty(inputDataNames)) {
            return;
        }
        String dataBaseName = this.getDataBaseName(dataSource);
        if (StringUtils.isEmpty((String)dataBaseName) || !ORACLESQL_NAME.equalsIgnoreCase(dataBaseName) && !DM_NAME.equalsIgnoreCase(dataBaseName)) {
            return;
        }
        for (String tableName : inputDataNames) {
            boolean isExistTriggerFlag = this.isExistTrigger(tableName);
            if (isExistTriggerFlag) continue;
            try {
                this.executeUpdateTriggerSQL(tableName);
                this.executeDeleteTriggerSQL(tableName);
            }
            catch (Exception e) {
                LOGGER.error("\u5185\u90e8\u8868\u89e6\u53d1\u5668\u521b\u5efa\u5931\u8d25", e);
            }
        }
    }

    private void executeUpdateTriggerSQL(String tableName) {
        StringBuffer sql = new StringBuffer("CREATE OR REPLACE TRIGGER ");
        sql.append(tableName).append("_UPDATE_TRIGGER \n");
        sql.append(" BEFORE UPDATE ON ").append(tableName).append("\n");
        sql.append(" FOR EACH ROW \n");
        sql.append(" BEGIN  \n");
        sql.append("  BEGIN  IF  (:old.OFFSETSTATE='1'  and :NEW.OFFSETSTATE='0' AND  :NEW.offsetgroupid is not null AND :old.CONVERTGROUPID IS null) THEN \n");
        sql.append("  RAISE_APPLICATION_ERROR(-20002, '\u3010\u5185\u90e8\u8868\u89e6\u53d1\u5668\u3011\u5bf9\u5185\u90e8\u8868\u5df2\u62b5\u9500\u6570\u636e\u975e\u6cd5\u53d8\u66f4\u72b6\u6001\u64cd\u4f5c\uff0c\u975e\u6b63\u5e38\u7a0b\u5e8f\u6267\u884c\u3002'); \n");
        sql.append("  END IF; \n");
        sql.append(" END; \n");
        sql.append(" BEGIN   \n");
        sql.append("  IF  (:NEW.OFFSETSTATE='1' AND :old.offsetgroupid is not null and :NEW.offsetgroupid <> :old.offsetgroupid AND :old.CONVERTGROUPID IS null) THEN  \n");
        sql.append("  RAISE_APPLICATION_ERROR(-20003, '\u3010\u5185\u90e8\u8868\u89e6\u53d1\u5668\u3011\u5bf9\u5185\u90e8\u8868\u5df2\u62b5\u9500\u6570\u636e\u975e\u6cd5\u53d8\u66f4\u62b5\u9500\u5206\u5f55\u64cd\u4f5c\uff0c\u975e\u6b63\u5e38\u7a0b\u5e8f\u6267\u884c\u3002'); \n");
        sql.append(" END IF; \n");
        sql.append("   END ; \n");
        sql.append(" END ; \n");
        this.jdbcTemplate.execute(sql.toString());
    }

    private void executeDeleteTriggerSQL(String tableName) {
        StringBuffer sql = new StringBuffer("CREATE OR REPLACE TRIGGER ");
        sql.append(tableName).append("_DEL_TRIGGER \n");
        sql.append(" BEFORE DELETE ON ").append(tableName).append("\n");
        sql.append(" FOR EACH ROW \n");
        sql.append(" BEGIN  \n");
        sql.append("  IF (:old.OFFSETSTATE='1') THEN \n");
        sql.append("  RAISE_APPLICATION_ERROR(-20001, '\u3010\u5185\u90e8\u8868\u89e6\u53d1\u5668\u3011\u5bf9\u5185\u90e8\u8868\u5df2\u62b5\u9500\u6570\u636e\u975e\u6cd5\u5220\u9664\u64cd\u4f5c\uff0c\u975e\u6b63\u5e38\u7a0b\u5e8f\u6267\u884c\u3002'); \n");
        sql.append("  END IF; \n");
        sql.append(" END; \n");
        this.jdbcTemplate.execute(sql.toString());
    }

    private boolean isExistTrigger(String tableName) {
        List triggerDatas = this.jdbcTemplate.queryForList(this.TRIGGER_SQL_ORACLE, new Object[]{tableName});
        return !CollectionUtils.isEmpty((Collection)triggerDatas);
    }

    private String getDataBaseName(DataSource dataSource) {
        String dataBaseName = null;
        try (Connection connection = dataSource.getConnection();){
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            dataBaseName = database.getName();
        }
        catch (SQLException e) {
            LOGGER.error("\u83b7\u53d6\u6570\u636e\u5e93dataBaseName\u4e3a\u7a7a", e);
        }
        return dataBaseName;
    }

    private Set<String> listInputDataTableName() {
        EntNativeSqlDefaultDao inputDataSchemeDao = EntNativeSqlDefaultDao.newInstance((String)"GC_INPUTDATASCHEME", InputDataSchemeEO.class);
        List inputDataSchemes = inputDataSchemeDao.selectEntity("select * from GC_INPUTDATASCHEME", new Object[0]);
        if (CollectionUtils.isEmpty((Collection)inputDataSchemes)) {
            return CollectionUtils.newHashSet();
        }
        HashSet<String> inputDataNames = new HashSet<String>();
        for (InputDataSchemeEO inputDataScheme : inputDataSchemes) {
            String tableName = this.getInputDataTableNameByDataTableKey(inputDataScheme.getTableKey());
            if (StringUtils.isEmpty((String)tableName)) continue;
            inputDataNames.add(tableName);
        }
        return inputDataNames;
    }

    private String getInputDataTableNameByDataTableKey(String dataTableKey) {
        String tableId;
        TableModelDefine tableModel;
        List dataFieldDeployInfos = this.iRuntimeDataSchemeService.getDeployInfoByDataTableKey(dataTableKey);
        if (CollectionUtils.isEmpty((Collection)dataFieldDeployInfos)) {
            LOGGER.error("\u6839\u636etableKey\u83b7\u53d6\u5185\u90e8\u8868\u6570\u636e\u6a21\u7c7b\u578b\u53d1\u5e03\u4fe1\u606f\u4e3a\u7a7a\uff0ctableKey=" + dataTableKey);
        }
        if (null == (tableModel = this.modelService.getTableModelDefineById(tableId = ((DataFieldDeployInfo)dataFieldDeployInfos.get(0)).getTableModelKey())) || StringUtils.isEmpty((String)tableModel.getName())) {
            LOGGER.error("\u6839\u636etableKey\u83b7\u53d6\u5185\u90e8\u8868\u5b9a\u4e49\u4e3a\u7a7a\uff0ctableKey=" + dataTableKey + " tableModelKey=" + tableId);
            return null;
        }
        return tableModel.getName();
    }
}

