/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.query.account.IAccountColumnModelFinder
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.definition.common.RuntimeDefinitionTransfer
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.data.engine.provider.account;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.query.account.IAccountColumnModelFinder;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.definition.common.RuntimeDefinitionTransfer;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class AccountColumnModelFinder
implements IAccountColumnModelFinder {
    private static final Logger logger = LoggerFactory.getLogger(AccountColumnModelFinder.class);
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DataModelService dataModelService;
    private static final String TN_ACCOUNT_RPT_SUFFIX = "_RPT";
    private static final String TN_ACCOUNT_HIS_SUFFIX = "_HIS";

    public boolean isAccountTable(ExecutorContext context, String tableKey) throws Exception {
        if (StringUtils.isEmpty((String)tableKey)) {
            return false;
        }
        DataTable dataTable = this.runtimeDataSchemeService.getDataTable(tableKey);
        return dataTable.getDataTableType() == DataTableType.ACCOUNT;
    }

    public String getAccountTableName(String tableName) {
        if (tableName.endsWith(TN_ACCOUNT_HIS_SUFFIX) || tableName.endsWith(TN_ACCOUNT_RPT_SUFFIX)) {
            tableName = tableName.substring(0, tableName.length() - 4);
        }
        return tableName;
    }

    public TableModelDefine getAccountTableModelByTableKey(ExecutorContext context, String tableKey) throws Exception {
        return this.getTableModelByTableKey(tableKey, AccountType.ACCOUNT);
    }

    public TableModelDefine getAccountHiTableModelByTableKey(ExecutorContext context, String tableKey) throws Exception {
        return this.getTableModelByTableKey(tableKey, AccountType.ACCOUNT_HI);
    }

    public TableModelDefine getAccountRpTableModelByTableKey(ExecutorContext context, String tableKey) throws Exception {
        return this.getTableModelByTableKey(tableKey, AccountType.ACCOUNT_RP);
    }

    private TableModelDefine getTableModelByTableKey(String tableKey, AccountType accountType) {
        List tableDeployInfo = this.runtimeDataSchemeService.getDeployInfoByDataTableKey(tableKey);
        if (CollectionUtils.isEmpty(tableDeployInfo)) {
            return null;
        }
        TableModelDefine tableModel = null;
        for (DataFieldDeployInfo dataFieldDeployInfo : tableDeployInfo) {
            tableModel = this.dataModelService.getTableModelDefineById(dataFieldDeployInfo.getTableModelKey());
            String tableModelCode = tableModel.getCode();
            if (accountType == AccountType.ACCOUNT_HI && tableModelCode.endsWith(TN_ACCOUNT_HIS_SUFFIX)) {
                return tableModel;
            }
            if (accountType == AccountType.ACCOUNT_RP && tableModelCode.endsWith(TN_ACCOUNT_RPT_SUFFIX)) {
                return tableModel;
            }
            if (accountType != AccountType.ACCOUNT || tableModelCode.endsWith(TN_ACCOUNT_HIS_SUFFIX) || tableModelCode.endsWith(TN_ACCOUNT_RPT_SUFFIX)) continue;
            return tableModel;
        }
        return tableModel;
    }

    public boolean isAccountFiled(ExecutorContext context, String fieldKey) throws Exception {
        DataField dataField = this.runtimeDataSchemeService.getDataField(fieldKey);
        return !dataField.isChangeWithPeriod();
    }

    public boolean isAccountColumn(ExecutorContext context, String columnModelId) throws Exception {
        DataFieldDeployInfo deployInfo = this.runtimeDataSchemeService.getDeployInfoByColumnKey(columnModelId);
        return this.isAccountFiled(context, deployInfo.getDataFieldKey());
    }

    public boolean isAccountVersionFiled(ExecutorContext context, String fieldKey) throws Exception {
        DataField dataField = this.runtimeDataSchemeService.getDataField(fieldKey);
        return dataField.isGenerateVersion();
    }

    public boolean isAccountVersionColumn(ExecutorContext context, String columnModelId) throws Exception {
        DataFieldDeployInfo deployInfo = this.runtimeDataSchemeService.getDeployInfoByColumnKey(columnModelId);
        return this.isAccountVersionFiled(context, deployInfo.getDataFieldKey());
    }

    public ColumnModelDefine findAccountColumnModelDefine(ExecutorContext context, String fieldKey) throws Exception {
        String dataScheme = this.getDataScheme(context);
        return this.getColumnModelByFieldKey(dataScheme, fieldKey, AccountType.ACCOUNT);
    }

    public ColumnModelDefine findAccountHiColumnModelDefine(ExecutorContext context, String fieldKey) throws Exception {
        String dataScheme = this.getDataScheme(context);
        return this.getColumnModelByFieldKey(dataScheme, fieldKey, AccountType.ACCOUNT_HI);
    }

    public ColumnModelDefine findAccountRpColumnModelDefine(ExecutorContext context, String fieldKey) throws Exception {
        String dataScheme = this.getDataScheme(context);
        return this.getColumnModelByFieldKey(dataScheme, fieldKey, AccountType.ACCOUNT_RP);
    }

    public List<ColumnModelDefine> getAllAccountColumnModelsByTableKey(ExecutorContext context, String tableKey) throws Exception {
        DataTable dataTable = this.runtimeDataSchemeService.getDataTable(tableKey);
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByCode(dataTable.getCode());
        List columnModelDefines = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        return columnModelDefines;
    }

    public List<ColumnModelDefine> getAllAccountHiColumnModelsByTableKey(ExecutorContext context, String tableKey) throws Exception {
        DataTable dataTable = this.runtimeDataSchemeService.getDataTable(tableKey);
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByCode(dataTable.getCode() + TN_ACCOUNT_HIS_SUFFIX);
        List columnModelDefines = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        return columnModelDefines;
    }

    public List<ColumnModelDefine> getAllAccountRpColumnModelsByTableKey(ExecutorContext context, String tableKey) throws Exception {
        DataTable dataTable = this.runtimeDataSchemeService.getDataTable(tableKey);
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByCode(dataTable.getCode() + TN_ACCOUNT_RPT_SUFFIX);
        List columnModelDefines = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        return columnModelDefines;
    }

    public List<ColumnModelDefine> getAllBizkColumnByColumnId(ExecutorContext context, String columnId) {
        DataFieldDeployInfo deployInfo = this.runtimeDataSchemeService.getDeployInfoByColumnKey(columnId);
        if (deployInfo == null) {
            return Collections.emptyList();
        }
        DataTable dataTable = this.runtimeDataSchemeService.getDataTable(deployInfo.getDataTableKey());
        if (dataTable == null) {
            return Collections.emptyList();
        }
        List deployInfos = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(dataTable.getBizKeys());
        List columns = deployInfos.stream().map(e -> this.dataModelService.getColumnModelDefineByID(e.getColumnModelKey())).collect(Collectors.toList());
        ArrayList dinstinctColumns = columns.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<ColumnModelDefine>(Comparator.comparing(n -> n.getName()))), ArrayList::new));
        return dinstinctColumns;
    }

    public List<FieldDefine> getAllFieldByColumnKeys(List<String> columnKeys) {
        List deployInfo = this.runtimeDataSchemeService.getDeployInfoByColumnKeys(columnKeys);
        if (CollectionUtils.isEmpty(deployInfo)) {
            return Collections.emptyList();
        }
        return deployInfo.stream().map(e -> RuntimeDefinitionTransfer.toFieldDefine((DataField)this.runtimeDataSchemeService.getDataField(e.getDataFieldKey()))).collect(Collectors.toList());
    }

    public boolean isBNWDColumn(String columnKey) {
        DataField dataField = this.runtimeDataSchemeService.getDataFieldByColumnKey(columnKey);
        return dataField != null && dataField.getDataFieldKind() == DataFieldKind.TABLE_FIELD_DIM;
    }

    public boolean ifTrackHistory(String tableModelKey) {
        String dataTableKey = this.runtimeDataSchemeService.getDataTableByTableModel(tableModelKey);
        DataTable dataTable = this.runtimeDataSchemeService.getDataTable(dataTableKey);
        if (null != dataTable) {
            return dataTable.getTrackHistory();
        }
        return false;
    }

    private String getDataScheme(ExecutorContext context) {
        IFmlExecEnvironment env = context.getEnv();
        String dataScheme = "";
        if (env instanceof ReportFmlExecEnvironment) {
            ReportFmlExecEnvironment rptEnv = (ReportFmlExecEnvironment)env;
            try {
                dataScheme = rptEnv.getTaskDefine().getDataScheme();
            }
            catch (Exception e) {
                logger.error("\u83b7\u53d6\u5f53\u524d\u6570\u636e\u65b9\u6848");
                e.printStackTrace();
            }
        }
        return dataScheme;
    }

    private ColumnModelDefine getColumnModelByFieldKey(String dataScheme, String fieldKey, AccountType accountType) {
        DataField dataField = this.runtimeDataSchemeService.getDataField(fieldKey);
        List deployInfos = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{dataField.getKey()});
        if (CollectionUtils.isEmpty(deployInfos)) {
            return null;
        }
        ColumnModelDefine columnModel = null;
        TableModelDefine tableModel = null;
        for (DataFieldDeployInfo dataFieldDeployInfo : deployInfos) {
            columnModel = this.dataModelService.getColumnModelDefineByID(dataFieldDeployInfo.getColumnModelKey());
            tableModel = this.dataModelService.getTableModelDefineById(dataFieldDeployInfo.getTableModelKey());
            String tableModelCode = tableModel.getCode();
            if (accountType == AccountType.ACCOUNT_HI && tableModelCode.endsWith(TN_ACCOUNT_HIS_SUFFIX)) {
                return columnModel;
            }
            if (accountType == AccountType.ACCOUNT_RP && tableModelCode.endsWith(TN_ACCOUNT_RPT_SUFFIX)) {
                return columnModel;
            }
            if (accountType != AccountType.ACCOUNT || tableModelCode.endsWith(TN_ACCOUNT_HIS_SUFFIX) || tableModelCode.endsWith(TN_ACCOUNT_RPT_SUFFIX)) continue;
            return columnModel;
        }
        return columnModel;
    }

    private static enum AccountType {
        ACCOUNT,
        ACCOUNT_HI,
        ACCOUNT_RP;

    }
}

