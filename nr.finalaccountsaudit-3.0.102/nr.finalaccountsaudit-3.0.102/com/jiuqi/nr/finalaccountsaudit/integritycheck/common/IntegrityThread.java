/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowCallbackHandler
 */
package com.jiuqi.nr.finalaccountsaudit.integritycheck.common;

import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.finalaccountsaudit.integritycheck.common.DBTypeUtil;
import com.jiuqi.nr.finalaccountsaudit.integritycheck.common.EmptyZeroCheckHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

public class IntegrityThread
implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(IntegrityThread.class);
    private final List<String> dwKeys;
    private int x;
    private IRunTimeViewController runTimeViewController;
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private JdbcTemplate jdbcTpl;
    private EmptyZeroCheckHelper emptyCheckHelper;
    private DBTypeUtil dbTypeUtil;
    private String taskKey;
    private String formKey;
    private String period;
    private String tempTableName;
    private String masterDimName;
    private Map<String, Map<String, String>> formUnits;

    public IntegrityThread(List<String> dwKeys, int x, IRuntimeDataSchemeService runtimeDataSchemeService, IRunTimeViewController runTimeViewController, IDataDefinitionRuntimeController dataDefinitionRuntimeController, JdbcTemplate jdbcTpl, EmptyZeroCheckHelper emptyCheckHelper, DBTypeUtil dbTypeUtil, String taskKey, String formKey, String period, String tempTableName, String masterDimName, Map<String, Map<String, String>> formUnits) {
        this.x = x;
        this.dwKeys = dwKeys;
        this.runtimeDataSchemeService = runtimeDataSchemeService;
        this.runTimeViewController = runTimeViewController;
        this.dataDefinitionRuntimeController = dataDefinitionRuntimeController;
        this.jdbcTpl = new JdbcTemplate(jdbcTpl.getDataSource());
        this.emptyCheckHelper = emptyCheckHelper;
        this.dbTypeUtil = dbTypeUtil;
        this.taskKey = taskKey;
        this.formKey = formKey;
        this.period = period;
        this.tempTableName = tempTableName;
        this.masterDimName = masterDimName;
        this.formUnits = formUnits;
    }

    @Override
    public void run() {
        try {
            HashMap unitResult = new HashMap();
            final HashMap<String, Integer> unitFlag = new HashMap<String, Integer>();
            List fieldKeys = this.runTimeViewController.getFieldKeysInForm(this.formKey);
            ArrayList<DataTable> tableDefines = new ArrayList<DataTable>();
            HashSet<String> dicDataTables = new HashSet<String>();
            for (String string : fieldKeys) {
                DataField fieldDefine = this.runtimeDataSchemeService.getDataField(string);
                DataFieldDeployInfo deployInfo = (DataFieldDeployInfo)this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{fieldDefine.getKey()}).get(0);
                if (fieldDefine == null || dicDataTables.contains(deployInfo.getTableName())) continue;
                dicDataTables.add(deployInfo.getTableName());
                tableDefines.add(this.runtimeDataSchemeService.getDataTable(fieldDefine.getDataTableKey()));
            }
            for (DataTable dataTable : tableDefines) {
                String querySql;
                if (this.dbTypeUtil.getDbType() == DBTypeUtil.DbType.MYSQL) {
                    querySql = this.emptyCheckHelper.getEmptyMySqlSql(this.taskKey, this.period, this.tempTableName, dataTable, this.masterDimName);
                    final HashSet dicDwHasData = new HashSet();
                    this.jdbcTpl.query(querySql, new RowCallbackHandler(){

                        public void processRow(ResultSet rs) throws SQLException {
                            String dmKey = rs.getString("DW");
                            dicDwHasData.add(dmKey);
                        }
                    });
                    HashSet<String> dicDwAdded = new HashSet<String>();
                    for (String dw : this.dwKeys) {
                        if (dicDwHasData.contains(dw) || dicDwAdded.contains(dw)) continue;
                        dicDwAdded.add(dw);
                        if (unitFlag.containsKey(dw)) {
                            unitFlag.put(dw, (Integer)unitFlag.get(dw) + 1);
                            continue;
                        }
                        unitFlag.put(dw, 1);
                    }
                    continue;
                }
                querySql = this.emptyCheckHelper.getEmptyOracleSql(this.taskKey, this.period, this.tempTableName, dataTable, this.masterDimName);
                final HashMap rows = new HashMap();
                List re = this.jdbcTpl.queryForList(querySql);
                this.jdbcTpl.query(querySql, new RowCallbackHandler(){

                    public void processRow(ResultSet rs) throws SQLException {
                        String dmKey = rs.getString("CODE");
                        if (rows.containsKey(dmKey)) {
                            return;
                        }
                        rows.put(dmKey, dmKey);
                        if (unitFlag.containsKey(dmKey)) {
                            unitFlag.put(dmKey, (Integer)unitFlag.get(dmKey) + 1);
                        } else {
                            unitFlag.put(dmKey, 1);
                        }
                    }
                });
            }
            for (Map.Entry entry : unitFlag.entrySet()) {
                if (((Integer)entry.getValue()).intValue() != tableDefines.size()) continue;
                unitResult.put(entry.getKey(), "\u7a7a\u8868");
            }
            this.formUnits.put(this.formKey, unitResult);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}

