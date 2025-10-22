/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.IndexModelType
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.data.access.event;

import com.jiuqi.nr.data.access.common.TableConsts;
import com.jiuqi.nr.data.access.model.ColumnInfo;
import com.jiuqi.nr.data.access.model.IndexInfo;
import com.jiuqi.nr.data.access.model.LogicTableInfo;
import com.jiuqi.nr.data.access.util.LogicTableUpdateUtil;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.IndexModelType;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class StatusTableCreateUtil {
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private LogicTableUpdateUtil logicTableUpdateUtil;
    @Autowired
    private DesignDataModelService designDataModelService;

    public List<String> initDataPublishDeploy(String dataSchemeKey, DataScheme dataScheme, boolean noDDL) throws Exception {
        DesignDataScheme queryDataScheme = this.designDataSchemeService.getDataScheme(dataSchemeKey);
        String schemCode = queryDataScheme == null ? dataScheme.getBizCode() : queryDataScheme.getBizCode();
        String tableName = TableConsts.getSysTableName("NR_DATAPUBLISH_%s", schemCode);
        String title = TableConsts.getSysTableTitle("\u6570\u636e\u53d1\u5e03\u72b6\u6001\u8868", schemCode);
        LogicTableInfo logicTableInfo = new LogicTableInfo(tableName);
        logicTableInfo.setDescription(title);
        logicTableInfo.setTitle(title);
        if (queryDataScheme == null) {
            this.logicTableUpdateUtil.deleteAndDeploy(tableName);
            return new ArrayList<String>();
        }
        logicTableInfo.getPrimaryColumns().addAll(this.logicTableUpdateUtil.initPrimaryColumns(dataSchemeKey, true));
        logicTableInfo.getPrimaryColumns().add(new ColumnInfo("DP_FORMSCHEMEKEY", "DP_FORMSCHEMEKEY", ColumnModelType.STRING, 50, "", true));
        logicTableInfo.getPrimaryColumns().add(new ColumnInfo("DP_FORMKEY", "DP_FORMKEY", ColumnModelType.STRING, 50, "", true));
        logicTableInfo.getColumns().add(new ColumnInfo("DP_ID", "DP_ID", ColumnModelType.STRING, 50, "", false));
        logicTableInfo.getColumns().add(new ColumnInfo("DP_SCENE1", "DP_ID", ColumnModelType.STRING, 50, "", false));
        logicTableInfo.getColumns().add(new ColumnInfo("DP_USER", "DP_ID", ColumnModelType.STRING, 50, "", false));
        logicTableInfo.getColumns().add(new ColumnInfo("DP_ISPUBLISH", "DP_ID", ColumnModelType.STRING, 50, "", false));
        logicTableInfo.getColumns().add(new ColumnInfo("DP_UPDATETIME", "DP_ID", ColumnModelType.DATETIME, 0, "", false));
        return this.logicTableUpdateUtil.createAndDeployTable(logicTableInfo, noDDL);
    }

    public List<String> initUnitStateDeploy(String dataSchemeKey, DataScheme dataScheme, boolean noDDL) throws Exception {
        DesignDataScheme queryDataScheme = this.designDataSchemeService.getDataScheme(dataSchemeKey);
        String schemCode = queryDataScheme == null ? dataScheme.getBizCode() : queryDataScheme.getBizCode();
        String tableName = TableConsts.getSysTableName("NR_UNITSTATE_%s", schemCode);
        String title = TableConsts.getSysTableTitle("\u7ec8\u6b62\u586b\u62a5\u72b6\u6001\u8868", schemCode);
        LogicTableInfo logicTableInfo = new LogicTableInfo(tableName);
        logicTableInfo.setDescription(title);
        logicTableInfo.setTitle(title);
        if (queryDataScheme == null) {
            this.logicTableUpdateUtil.deleteAndDeploy(tableName);
            return new ArrayList<String>();
        }
        logicTableInfo.getPrimaryColumns().addAll(this.logicTableUpdateUtil.initPrimaryColumns(dataSchemeKey, true));
        logicTableInfo.getPrimaryColumns().add(new ColumnInfo("US_FORMSCHEMEKEY", "US_FORMSCHEMEKEY", ColumnModelType.STRING, 50, "", true));
        logicTableInfo.getColumns().add(new ColumnInfo("US_ID", "US_ID", ColumnModelType.STRING, 50, "", false));
        logicTableInfo.getColumns().add(new ColumnInfo("US_FORMKEY", "US_FORMKEY", ColumnModelType.STRING, 50, "", false));
        logicTableInfo.getColumns().add(new ColumnInfo("US_USER", "US_USER", ColumnModelType.STRING, 1000, "", false));
        logicTableInfo.getColumns().add(new ColumnInfo("US_STATE", "US_STATE", ColumnModelType.STRING, 50, "", false));
        logicTableInfo.getColumns().add(new ColumnInfo("US_UPDATETIME", "US_UPDATETIME", ColumnModelType.DATETIME, 0, "", false));
        return this.logicTableUpdateUtil.createAndDeployTable(logicTableInfo, noDDL);
    }

    public List<String> initFormLockDeploy(String dataSchemeKey, DataScheme dataScheme, boolean noDDL) throws Exception {
        List<String> sql2;
        DesignDataScheme queryDataScheme = this.designDataSchemeService.getDataScheme(dataSchemeKey);
        String schemCode = queryDataScheme == null ? dataScheme.getBizCode() : queryDataScheme.getBizCode();
        if (queryDataScheme == null) {
            String lockTable = TableConsts.getSysTableName("NR_STATE_%s_FORMLOCK", schemCode);
            this.logicTableUpdateUtil.deleteAndDeploy(lockTable);
            String lockHisTable = TableConsts.getSysTableName("NR_STATE_%s_FORMLOCK_HIS", schemCode);
            this.logicTableUpdateUtil.deleteAndDeploy(lockHisTable);
            return new ArrayList<String>();
        }
        ArrayList<String> sqls = new ArrayList<String>();
        List<String> sql1 = this.deployFormLockTable(dataSchemeKey, schemCode, noDDL);
        if (!CollectionUtils.isEmpty(sql1)) {
            sqls.addAll(sql1);
        }
        if (!CollectionUtils.isEmpty(sql2 = this.deployFormLockHisTable(dataSchemeKey, schemCode, noDDL))) {
            sqls.addAll(sql2);
        }
        return sqls;
    }

    private List<String> deployFormLockTable(String dataSchemeKey, String schemCode, boolean noDDL) throws Exception {
        String tableName = TableConsts.getSysTableName("NR_STATE_%s_FORMLOCK", schemCode);
        String title = TableConsts.getSysTableTitle("\u9501\u5b9a\u72b6\u6001\u8868", schemCode);
        LogicTableInfo logicTableInfo = new LogicTableInfo(tableName);
        logicTableInfo.setDescription(title);
        logicTableInfo.setTitle(title);
        logicTableInfo.getPrimaryColumns().addAll(this.logicTableUpdateUtil.initPrimaryColumns(dataSchemeKey, false));
        logicTableInfo.getPrimaryColumns().add(new ColumnInfo("FL_FORMSCHEMEKEY", "FL_FORMSCHEMEKEY", ColumnModelType.STRING, 50, "", true));
        logicTableInfo.getPrimaryColumns().add(new ColumnInfo("FL_FORMKEY", "FL_FORMKEY", ColumnModelType.STRING, 50, "", true));
        logicTableInfo.getPrimaryColumns().add(new ColumnInfo("FL_USER", "FL_USER", ColumnModelType.STRING, 50, "", true));
        logicTableInfo.getColumns().add(new ColumnInfo("FL_ID", "FL_ID", ColumnModelType.STRING, 50, "", false));
        logicTableInfo.getColumns().add(new ColumnInfo("FL_SCENE1", "FL_SCENE1", ColumnModelType.STRING, 50, "", false));
        logicTableInfo.getColumns().add(new ColumnInfo("FL_ISLOCK", "FL_ISLOCK", ColumnModelType.STRING, 50, "", false));
        logicTableInfo.getColumns().add(new ColumnInfo("FL_UPDATETIME", "FL_UPDATETIME", ColumnModelType.DATETIME, 0, "", false));
        return this.logicTableUpdateUtil.createAndDeployTable(logicTableInfo, noDDL);
    }

    private List<String> deployFormLockHisTable(String dataSchemeKey, String schemCode, boolean noDDL) throws Exception {
        String tableName = TableConsts.getSysTableName("NR_STATE_%s_FORMLOCK_HIS", schemCode);
        String title = TableConsts.getSysTableTitle("\u9501\u5b9a\u5386\u53f2\u8868", schemCode);
        LogicTableInfo logicTableInfo = new LogicTableInfo(tableName);
        logicTableInfo.setDescription(title);
        logicTableInfo.setTitle(title);
        logicTableInfo.getPrimaryColumns().add(new ColumnInfo("FLH_ID", "FLH_ID", ColumnModelType.STRING, 50, "", true));
        logicTableInfo.getColumns().addAll(this.logicTableUpdateUtil.initPrimaryColumns(dataSchemeKey, false));
        logicTableInfo.getColumns().add(new ColumnInfo("FLH_FORMSCHEME", "FLH_FORMSCHEME", ColumnModelType.STRING, 50, "", true));
        logicTableInfo.getColumns().add(new ColumnInfo("FLH_FORM", "FLH_FORM", ColumnModelType.STRING, 50, "", true));
        logicTableInfo.getColumns().add(new ColumnInfo("FLH_USER", "FLH_USER", ColumnModelType.STRING, 50));
        logicTableInfo.getColumns().add(new ColumnInfo("FLH_OPER", "FLH_OPER", ColumnModelType.INTEGER, 2));
        logicTableInfo.getColumns().add(new ColumnInfo("FLH_OPERTIME", "FLH_OPERTIME", ColumnModelType.DATETIME, 0));
        int dwIndex = -1;
        int perIndex = -1;
        int formIndex = -1;
        int formSchemeIndex = -1;
        for (int i = 0; i < logicTableInfo.getColumns().size(); ++i) {
            ColumnInfo columnInfo = logicTableInfo.getColumns().get(i);
            if ("MDCODE".equals(columnInfo.getCode())) {
                dwIndex = i;
                continue;
            }
            if ("PERIOD".equals(columnInfo.getCode())) {
                perIndex = i;
                continue;
            }
            if ("FLH_FORM".equals(columnInfo.getCode())) {
                formIndex = i;
                continue;
            }
            if (!"FLH_FORMSCHEME".equals(columnInfo.getCode())) continue;
            formSchemeIndex = i;
        }
        int[] indexColumns = new int[]{dwIndex, formIndex, perIndex, formSchemeIndex};
        List<IndexInfo> indexInfos = logicTableInfo.getIndexInfos();
        IndexInfo indexInfo = new IndexInfo("IDX_FORMLOCK_HIS_" + schemCode, indexColumns, IndexModelType.NORMAL);
        indexInfos.add(indexInfo);
        return this.logicTableUpdateUtil.createAndDeployTable(logicTableInfo, noDDL);
    }

    public List<String> initSecretDeploy(String dataSchemeKey, DataScheme dataScheme, boolean noDDL) throws Exception {
        DesignDataScheme queryDataScheme = this.designDataSchemeService.getDataScheme(dataSchemeKey);
        String schemCode = queryDataScheme == null ? dataScheme.getBizCode() : queryDataScheme.getBizCode();
        String tableName = TableConsts.getSysTableName("NR_SECRETLEVEL_%s", schemCode);
        String title = TableConsts.getSysTableTitle("\u5bc6\u7ea7\u72b6\u6001\u8868", schemCode);
        LogicTableInfo logicTableInfo = new LogicTableInfo(tableName);
        logicTableInfo.setDescription(title);
        logicTableInfo.setTitle(title);
        if (queryDataScheme == null) {
            this.logicTableUpdateUtil.deleteAndDeploy(tableName);
            return new ArrayList<String>();
        }
        logicTableInfo.getPrimaryColumns().addAll(this.logicTableUpdateUtil.initPrimaryColumns(dataSchemeKey, false));
        logicTableInfo.getPrimaryColumns().add(new ColumnInfo("SL_FORMSCHEMEKEY", "SL_FORMSCHEMEKEY", ColumnModelType.STRING, 50, "", true));
        logicTableInfo.getColumns().add(new ColumnInfo("SL_ID", "SL_ID", ColumnModelType.STRING, 50, "", false));
        logicTableInfo.getColumns().add(new ColumnInfo("SL_FORMKEY", "SL_FORMKEY", ColumnModelType.STRING, 50, "", false));
        logicTableInfo.getColumns().add(new ColumnInfo("SL_LEVEL", "SL_LEVEL", ColumnModelType.STRING, 50, "", false));
        logicTableInfo.getColumns().add(new ColumnInfo("SL_USER", "SL_USER", ColumnModelType.STRING, 50, "", false));
        logicTableInfo.getColumns().add(new ColumnInfo("SL_UPDATETIME", "SL_UPDATETIME", ColumnModelType.DATETIME, 0, "", false));
        return this.logicTableUpdateUtil.createAndDeployTable(logicTableInfo, noDDL);
    }

    public List<String> getTableIds(String dataSchemeKey) {
        ArrayList<String> list = new ArrayList<String>();
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(dataSchemeKey);
        String schemeCode = dataScheme.getBizCode();
        ArrayList<String> tableNames = new ArrayList<String>();
        tableNames.add(TableConsts.getSysTableName("NR_DATAPUBLISH_%s", schemeCode));
        tableNames.add(TableConsts.getSysTableName("NR_UNITSTATE_%s", schemeCode));
        tableNames.add(TableConsts.getSysTableName("NR_STATE_%s_FORMLOCK", schemeCode));
        tableNames.add(TableConsts.getSysTableName("NR_STATE_%s_FORMLOCK_HIS", schemeCode));
        tableNames.add(TableConsts.getSysTableName("NR_SECRETLEVEL_%s", schemeCode));
        for (String tableName : tableNames) {
            DesignTableModelDefine designTableDefine = this.designDataModelService.getTableModelDefineByCode(tableName);
            if (designTableDefine == null) continue;
            String id = designTableDefine.getID();
            list.add(id);
        }
        return list;
    }
}

