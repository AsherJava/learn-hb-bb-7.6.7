/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.transaction.PlatformTransactionManager
 *  org.springframework.transaction.TransactionDefinition
 *  org.springframework.transaction.TransactionStatus
 *  org.springframework.transaction.support.DefaultTransactionDefinition
 */
package com.jiuqi.nr.data.logic.internal.impl.cksr;

import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.data.logic.exeception.LogicRuntimeException;
import com.jiuqi.nr.data.logic.facade.listener.ICheckRecordListener;
import com.jiuqi.nr.data.logic.facade.param.input.ActionEnum;
import com.jiuqi.nr.data.logic.facade.param.output.CheckRecord;
import com.jiuqi.nr.data.logic.facade.param.output.CheckRecordData;
import com.jiuqi.nr.data.logic.internal.impl.cksr.CheckStatusHelper;
import com.jiuqi.nr.data.logic.internal.impl.cksr.obj.CheckStatusTableInfo;
import com.jiuqi.nr.data.logic.internal.util.CheckResultUtil;
import com.jiuqi.nr.data.logic.internal.util.SplitCheckTableHelper;
import com.jiuqi.nr.data.logic.internal.util.SystemOptionUtil;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.exception.IncorrectQueryException;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class CheckStatusRecordListener
implements ICheckRecordListener {
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private INvwaDataAccessProvider dataAccessProvider;
    @Autowired
    private SplitCheckTableHelper splitCheckTableHelper;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private CheckStatusHelper checkStatusHelper;
    @Autowired
    private SystemOptionUtil systemOptionUtil;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;
    private static final Logger logger = LoggerFactory.getLogger(CheckStatusRecordListener.class);

    @Override
    public void processCheckRecord(CheckRecord checkRecord) throws LogicRuntimeException {
        if (checkRecord == null || CollectionUtils.isEmpty(checkRecord.getCheckRecordData())) {
            return;
        }
        String formSchemeKey = checkRecord.getFormSchemeKey();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        CheckStatusTableInfo tableInfo = this.checkStatusHelper.checkTableModel(formScheme);
        if (tableInfo == null) {
            logger.error("CKS\u8868\u4e0d\u5b58\u5728\uff0c\u65e0\u6cd5\u4fdd\u5b58\u5ba1\u6838\u72b6\u6001");
            return;
        }
        TransactionStatus transactionStatus = null;
        try {
            TableUpdateEnv cksTableUpdateEnv = this.getCksTableUpdateEnv(tableInfo, formScheme);
            TableUpdateEnv cksSubTableUpdateEnv = this.getCksSubTableUpdateEnv(tableInfo, formScheme);
            transactionStatus = this.getTransactionStatus();
            this.save(checkRecord, cksTableUpdateEnv, cksSubTableUpdateEnv);
            this.platformTransactionManager.commit(transactionStatus);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            if (transactionStatus != null) {
                this.platformTransactionManager.rollback(transactionStatus);
            }
            throw new LogicRuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public boolean isEnabled() {
        return this.systemOptionUtil.recordCheckStatus();
    }

    private TransactionStatus getTransactionStatus() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(0);
        return this.platformTransactionManager.getTransaction((TransactionDefinition)def);
    }

    private void save(CheckRecord checkRecord, TableUpdateEnv cksTableUpdateEnv, TableUpdateEnv cksSubTableUpdateEnv) throws Exception {
        CksUpdateResult result = this.upsertCksTable(checkRecord, cksTableUpdateEnv);
        this.updateCksSubTable(cksSubTableUpdateEnv, result);
    }

    private void updateCksSubTable(TableUpdateEnv cksSubTableUpdateEnv, CksUpdateResult result) throws Exception {
        this.delCksSubTable(cksSubTableUpdateEnv, result.recordKeys);
        CheckStatusRecordListener.insertCksSubTable(cksSubTableUpdateEnv, result.cKSSubInfos);
    }

    @NonNull
    private CksUpdateResult upsertCksTable(CheckRecord checkRecord, TableUpdateEnv cksTableUpdateEnv) throws Exception {
        ArrayList<String> recordKeys = new ArrayList<String>();
        ArrayList<CKSSubInfo> cKSSubInfos = new ArrayList<CKSSubInfo>();
        for (CheckRecordData checkRecordDatum : checkRecord.getCheckRecordData()) {
            String recordKey = this.getRecordKey(checkRecord, checkRecordDatum);
            CheckStatusRecordListener.upsertCksRow(checkRecord, cksTableUpdateEnv, checkRecordDatum, recordKey);
            recordKeys.add(recordKey);
            for (Map.Entry<Integer, Integer> e : checkRecordDatum.getCheckTypeCount().entrySet()) {
                cKSSubInfos.add(new CKSSubInfo(recordKey, e.getKey(), e.getValue()));
            }
        }
        cksTableUpdateEnv.getUpdater().commitChanges(cksTableUpdateEnv.getDataAccessContext());
        return new CksUpdateResult(recordKeys, cKSSubInfos);
    }

    private void delCksSubTable(TableUpdateEnv cksSubTableUpdateEnv, List<String> recordKeys) throws Exception {
        NvwaQueryModel cksSubDelModel = new NvwaQueryModel();
        cksSubTableUpdateEnv.getAllColumns().forEach(column -> {
            cksSubDelModel.getColumns().add(new NvwaQueryColumn(column));
            String columnCode = column.getCode();
            if ("CKS_S_REC_KEY".equals(columnCode)) {
                cksSubDelModel.getColumnFilters().put(column, recordKeys);
            }
            if (cksSubTableUpdateEnv.getSplitKeyValue().containsKey(columnCode)) {
                cksSubDelModel.getColumnFilters().put(column, cksSubTableUpdateEnv.getSplitKeyValue().get(columnCode));
            }
        });
        DataAccessContext delContext = new DataAccessContext(this.dataModelService);
        INvwaDataUpdator delUpdater = this.dataAccessProvider.createUpdatableDataAccess(cksSubDelModel).openForUpdate(delContext);
        delUpdater.deleteAll();
        delUpdater.commitChanges(delContext);
    }

    private static void insertCksSubTable(TableUpdateEnv cksSubTableUpdateEnv, List<CKSSubInfo> cKSSubInfos) throws Exception {
        for (CKSSubInfo cKSSubInfo : cKSSubInfos) {
            INvwaDataRow cksSubRow = cksSubTableUpdateEnv.getUpdater().addInsertRow();
            for (int i = 0; i < cksSubTableUpdateEnv.getAllColumns().size(); ++i) {
                ColumnModelDefine cksSubCol = cksSubTableUpdateEnv.getAllColumns().get(i);
                Object value = null;
                switch (cksSubCol.getCode()) {
                    case "CKS_S_REC_KEY": {
                        value = cKSSubInfo.getRecordKey();
                        break;
                    }
                    case "CKS_S_CK_TYPE": {
                        value = cKSSubInfo.getCheckType();
                        break;
                    }
                    case "CKS_S_ERR_COUNT": {
                        value = cKSSubInfo.getErrorCount();
                        break;
                    }
                }
                if (cksSubTableUpdateEnv.getKeyCols().contains(cksSubCol.getCode()) || cksSubTableUpdateEnv.getSplitKeyValue().containsKey(cksSubCol.getCode())) {
                    cksSubRow.setKeyValue(cksSubCol, value);
                    continue;
                }
                cksSubRow.setValue(i, value);
            }
        }
        cksSubTableUpdateEnv.getUpdater().commitChanges(cksSubTableUpdateEnv.getDataAccessContext());
    }

    private static void upsertCksRow(CheckRecord checkRecord, TableUpdateEnv cksTableUpdateEnv, CheckRecordData checkRecordDatum, String recordKey) throws IncorrectQueryException {
        INvwaDataRow cksDataRow = cksTableUpdateEnv.getUpdater().addUpdateOrInsertRow();
        for (int i = 0; i < cksTableUpdateEnv.getAllColumns().size(); ++i) {
            ColumnModelDefine cksCol = cksTableUpdateEnv.getAllColumns().get(i);
            Object value = null;
            switch (cksCol.getCode()) {
                case "CKS_REC_KEY": {
                    value = recordKey;
                    break;
                }
                case "CKS_FLS_KEY": {
                    value = checkRecord.getFormulaSchemeKey();
                    break;
                }
                case "CKS_FRM_KEY": {
                    value = checkRecordDatum.getFormKey();
                    break;
                }
                case "CKS_CK_STATE": {
                    value = checkRecordDatum.getStatus();
                    break;
                }
                case "CKS_CK_TIME": {
                    value = new Time(checkRecord.getCheckTime());
                    break;
                }
                case "CKS_BATCH_ID": {
                    value = checkRecord.getActionID();
                    break;
                }
                case "CKS_ACTION": {
                    value = Objects.requireNonNull(ActionEnum.getByName(checkRecord.getActionName())).getCode();
                    break;
                }
                case "CKS_REUSE": {
                    break;
                }
                default: {
                    if (cksTableUpdateEnv.getSplitKeyValue().containsKey(cksCol.getCode())) {
                        value = cksTableUpdateEnv.getSplitKeyValue().get(cksCol.getCode());
                        break;
                    }
                    String dimensionName = cksTableUpdateEnv.getDimensionChanger().getDimensionName(cksCol);
                    if (!StringUtils.hasText(dimensionName)) break;
                    value = checkRecordDatum.getDimensionValueSet().getValue(dimensionName);
                }
            }
            if (cksTableUpdateEnv.getKeyCols().contains(cksCol.getCode()) || cksTableUpdateEnv.getSplitKeyValue().containsKey(cksCol.getCode())) {
                cksDataRow.setKeyValue(cksCol, value);
                continue;
            }
            cksDataRow.setValue(i, value);
        }
    }

    private TableUpdateEnv getCksTableUpdateEnv(CheckStatusTableInfo tableInfo, FormSchemeDefine formScheme) throws Exception {
        List cksCols = this.dataModelService.getColumnModelDefinesByTable(tableInfo.getCksTable().getID());
        NvwaQueryModel cksUpsertModel = new NvwaQueryModel();
        cksCols.forEach(cksCol -> cksUpsertModel.getColumns().add(new NvwaQueryColumn(cksCol)));
        INvwaUpdatableDataAccess cksAccess = this.dataAccessProvider.createUpdatableDataAccess(cksUpsertModel);
        DataAccessContext cksContext = new DataAccessContext(this.dataModelService);
        INvwaDataUpdator cksUpdater = cksAccess.openForUpdate(cksContext);
        Set<String> cksKeyCols = cksUpdater.getRowKeyColumns().stream().map(IModelDefineItem::getCode).collect(Collectors.toSet());
        DimensionChanger cksDimChanger = this.dataEngineAdapter.getDimensionChanger(tableInfo.getCksTable().getName());
        Map<String, String> cksSplitKeyValue = this.splitCheckTableHelper.getSplitKeyValue(formScheme, tableInfo.getCksTable().getName());
        TableUpdateEnv tableUpdateEnv = new TableUpdateEnv();
        tableUpdateEnv.setAllColumns(cksCols);
        tableUpdateEnv.setUpdater(cksUpdater);
        tableUpdateEnv.setDataAccessContext(cksContext);
        tableUpdateEnv.setKeyCols(cksKeyCols);
        tableUpdateEnv.setDimensionChanger(cksDimChanger);
        tableUpdateEnv.setSplitKeyValue(cksSplitKeyValue);
        return tableUpdateEnv;
    }

    private TableUpdateEnv getCksSubTableUpdateEnv(CheckStatusTableInfo tableInfo, FormSchemeDefine formScheme) throws Exception {
        List cksSubCols = this.dataModelService.getColumnModelDefinesByTable(tableInfo.getCksSubTable().getID());
        NvwaQueryModel cksSubUpsertModel = new NvwaQueryModel();
        cksSubCols.forEach(cksSubCol -> cksSubUpsertModel.getColumns().add(new NvwaQueryColumn(cksSubCol)));
        INvwaUpdatableDataAccess cksSubAccess = this.dataAccessProvider.createUpdatableDataAccess(cksSubUpsertModel);
        DataAccessContext cksSubContext = new DataAccessContext(this.dataModelService);
        INvwaDataUpdator cksSubUpdater = cksSubAccess.openForUpdate(cksSubContext);
        Set<String> cksSubKeyCols = cksSubUpdater.getRowKeyColumns().stream().map(IModelDefineItem::getCode).collect(Collectors.toSet());
        Map<String, String> cksSubSplitKeyValue = this.splitCheckTableHelper.getSplitKeyValue(formScheme, tableInfo.getCksSubTable().getName());
        TableUpdateEnv tableUpdateEnv = new TableUpdateEnv();
        tableUpdateEnv.setAllColumns(cksSubCols);
        tableUpdateEnv.setUpdater(cksSubUpdater);
        tableUpdateEnv.setDataAccessContext(cksSubContext);
        tableUpdateEnv.setKeyCols(cksSubKeyCols);
        tableUpdateEnv.setSplitKeyValue(cksSubSplitKeyValue);
        return tableUpdateEnv;
    }

    private String getRecordKey(CheckRecord checkRecord, CheckRecordData checkRecordData) {
        String keyStr = String.join((CharSequence)";", checkRecord.getFormulaSchemeKey(), checkRecordData.getDimensionValueSet().toString(), checkRecordData.getFormKey());
        return CheckResultUtil.toFakeUUID(keyStr).toString();
    }

    private static class CKSSubInfo {
        private final String recordKey;
        private final int checkType;
        private final int errorCount;

        public CKSSubInfo(String recordKey, int checkType, int errorCount) {
            this.recordKey = recordKey;
            this.checkType = checkType;
            this.errorCount = errorCount;
        }

        public String getRecordKey() {
            return this.recordKey;
        }

        public int getCheckType() {
            return this.checkType;
        }

        public int getErrorCount() {
            return this.errorCount;
        }
    }

    private static class TableUpdateEnv {
        private List<ColumnModelDefine> allColumns;
        private INvwaDataUpdator updater;
        private DataAccessContext dataAccessContext;
        private Set<String> keyCols;
        private DimensionChanger dimensionChanger;
        private Map<String, String> splitKeyValue;

        private TableUpdateEnv() {
        }

        public List<ColumnModelDefine> getAllColumns() {
            return this.allColumns;
        }

        public void setAllColumns(List<ColumnModelDefine> allColumns) {
            this.allColumns = allColumns;
        }

        public INvwaDataUpdator getUpdater() {
            return this.updater;
        }

        public void setUpdater(INvwaDataUpdator updater) {
            this.updater = updater;
        }

        public DataAccessContext getDataAccessContext() {
            return this.dataAccessContext;
        }

        public void setDataAccessContext(DataAccessContext dataAccessContext) {
            this.dataAccessContext = dataAccessContext;
        }

        public Set<String> getKeyCols() {
            return this.keyCols;
        }

        public void setKeyCols(Set<String> keyCols) {
            this.keyCols = keyCols;
        }

        public DimensionChanger getDimensionChanger() {
            return this.dimensionChanger;
        }

        public void setDimensionChanger(DimensionChanger dimensionChanger) {
            this.dimensionChanger = dimensionChanger;
        }

        public Map<String, String> getSplitKeyValue() {
            return this.splitKeyValue;
        }

        public void setSplitKeyValue(Map<String, String> splitKeyValue) {
            this.splitKeyValue = splitKeyValue;
        }
    }

    private static class CksUpdateResult {
        public final List<String> recordKeys;
        public final List<CKSSubInfo> cKSSubInfos;

        public CksUpdateResult(List<String> recordKeys, List<CKSSubInfo> cKSSubInfos) {
            this.recordKeys = recordKeys;
            this.cKSSubInfos = cKSSubInfos;
        }
    }
}

