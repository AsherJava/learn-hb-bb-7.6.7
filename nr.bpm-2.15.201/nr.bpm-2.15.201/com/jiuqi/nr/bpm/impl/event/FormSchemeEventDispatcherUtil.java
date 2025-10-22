/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.bpm.impl.event;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.businesskey.MasterEntityImpl;
import com.jiuqi.nr.bpm.businesskey.MasterEntityInfo;
import com.jiuqi.nr.bpm.common.UploadRecord;
import com.jiuqi.nr.bpm.common.UploadStateVO;
import com.jiuqi.nr.bpm.exception.BpmException;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.upload.dao.TableConstant;
import com.jiuqi.nr.bpm.upload.UploadAction;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormSchemeEventDispatcherUtil {
    private static final Logger logger = LoggerFactory.getLogger(FormSchemeEventDispatcherUtil.class);
    private static final String DW_FIELD = "MDCODE";
    private static final String PERIOD_FIELD = "PERIOD";
    private static final ThreadLocal<SimpleDateFormat> stf = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    NrParameterUtils nrParameterUtils;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;

    public void insertHiRecord(FormSchemeDefine formScheme, UploadRecord uploadRecord) {
        if (formScheme == null) {
            return;
        }
        String tableName = TableConstant.getSysUploadRecordTableName(formScheme);
        TableModelDefine tableDefine = this.dataModelService.getTableModelDefineByCode(tableName);
        if (tableDefine == null) {
            return;
        }
        NvwaQueryModel queryModel = new NvwaQueryModel();
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        List allColumns = this.dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
        for (ColumnModelDefine column : allColumns) {
            queryModel.getColumns().add(new NvwaQueryColumn(column));
        }
        MasterEntityInfo masterEntity = this.getMasterEntityInfo(uploadRecord.getEntiryId());
        if (masterEntity == null) {
            return;
        }
        DimensionValueSet masterKeys = this.nrParameterUtils.convertDimensionName(masterEntity, uploadRecord.getPeriod());
        this.nrParameterUtils.addFormKeyToMasterKeys(masterKeys, null, uploadRecord.getFormKey());
        this.commitHiQueryByNvwa(queryModel, context, masterKeys, tableDefine, allColumns, this.calculateCurEvent(uploadRecord.getAction()), this.calculateHiNode(uploadRecord.getAction()), uploadRecord.getCmt(), uploadRecord.getOperator(), uploadRecord.getOperationid(), false, 0);
    }

    public void insertStateData(FormSchemeDefine formScheme, UploadStateVO uploadStateVO) {
        if (formScheme == null) {
            return;
        }
        NvwaQueryModel queryModel = new NvwaQueryModel();
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        String tableName = TableConstant.getSysUploadStateTableName(formScheme);
        TableModelDefine tableDefine = this.dataModelService.getTableModelDefineByCode(tableName);
        if (tableDefine == null) {
            return;
        }
        List allColumns = this.dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
        for (ColumnModelDefine fieldDefine : allColumns) {
            queryModel.getColumns().add(new NvwaQueryColumn(fieldDefine));
        }
        MasterEntityInfo masterEntity = this.getMasterEntityInfo(uploadStateVO.getEntiryId());
        if (masterEntity == null) {
            return;
        }
        DimensionValueSet masterKeys = this.nrParameterUtils.convertDimensionName(masterEntity, uploadStateVO.getPeriod());
        this.nrParameterUtils.addFormKeyToMasterKeys(masterKeys, null, uploadStateVO.getFormKey());
        List<ColumnModelDefine> dimensionValueSetColumns = this.nrParameterUtils.getDimensionValueSetColumns(tableDefine, allColumns);
        this.commitStateDataNvwa(queryModel, context, tableDefine, allColumns, dimensionValueSetColumns, masterKeys, this.calculatePreEvent(uploadStateVO.getState()), this.calculateStateNode(uploadStateVO.getState(), formScheme), false);
    }

    private MasterEntityInfo getMasterEntityInfo(String entity) {
        if (entity == null || entity.isEmpty()) {
            return null;
        }
        MasterEntityImpl masterEntityImpl = new MasterEntityImpl();
        String dimessionName = entity.split("@")[1];
        String entityKey = entity.split("@")[0];
        masterEntityImpl.setMasterEntityDimessionValue(dimessionName, entityKey);
        return masterEntityImpl;
    }

    private String calculatePreEvent(UploadState state) {
        String result = "";
        if (UploadState.CONFIRMED.equals((Object)state)) {
            result = "act_confirm";
        } else if (UploadState.ORIGINAL.equals((Object)state)) {
            result = "start";
        } else if (UploadState.REJECTED.equals((Object)state)) {
            result = "act_reject";
        } else if (UploadState.RETURNED.equals((Object)state)) {
            result = "act_return";
        } else if (UploadState.SUBMITED.equals((Object)state)) {
            result = "act_submit";
        } else if (UploadState.UPLOADED.equals((Object)state)) {
            result = "act_upload";
        }
        return result;
    }

    private String calculateStateNode(UploadState state, FormSchemeDefine formScheme) {
        String result = "";
        if (UploadState.CONFIRMED.equals((Object)state)) {
            result = "tsk_audit_after_confirm";
        } else if (UploadState.ORIGINAL.equals((Object)state)) {
            result = "tsk_start";
        } else if (UploadState.REJECTED.equals((Object)state)) {
            result = this.getFirstNode(formScheme);
        } else if (UploadState.RETURNED.equals((Object)state)) {
            result = "tsk_submit";
        } else if (UploadState.SUBMITED.equals((Object)state)) {
            result = "tsk_upload";
        } else if (UploadState.UPLOADED.equals((Object)state)) {
            result = "tsk_audit";
        }
        return result;
    }

    private String getFirstNode(FormSchemeDefine formScheme) {
        return "tsk_submit";
    }

    private String calculateCurEvent(String action) {
        String result = "";
        UploadAction act = UploadAction.valueOf(action);
        if (UploadAction.CONFIRM.equals((Object)act)) {
            result = "act_confirm";
        } else if (UploadAction.REJECT.equals((Object)act)) {
            result = "act_reject";
        } else if (UploadAction.RETURN.equals((Object)act)) {
            result = "act_return";
        } else if (UploadAction.SUBMIT.equals((Object)act)) {
            result = "act_submit";
        } else if (UploadAction.UPLOAD.equals((Object)act)) {
            result = "act_upload";
        }
        return result;
    }

    private String calculateHiNode(String action) {
        String result = "";
        UploadAction act = UploadAction.valueOf(action);
        if (UploadAction.CONFIRM.equals((Object)act)) {
            result = "tsk_audit";
        } else if (UploadAction.REJECT.equals((Object)act)) {
            result = "tsk_audit";
        } else if (UploadAction.RETURN.equals((Object)act)) {
            result = "tsk_upload";
        } else if (UploadAction.SUBMIT.equals((Object)act)) {
            result = "tsk_submit";
        } else if (UploadAction.UPLOAD.equals((Object)act)) {
            result = "tsk_upload";
        }
        return result;
    }

    public void commitHiQueryByNvwa(NvwaQueryModel queryModel, DataAccessContext context, DimensionValueSet masterKeys, TableModelDefine tableModelDefine, List<ColumnModelDefine> fieldDefines, String curEvent, String curNode, String comment, String actorId, String operationId, Boolean force, Integer exeOrder) {
        try {
            INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
            INvwaDataUpdator updaor = updatableDataAccess.openForUpdate(context);
            INvwaDataRow newDataRow = updaor.addInsertRow();
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableModelDefine.getName());
            int i = 0;
            for (ColumnModelDefine fieldDefine : fieldDefines) {
                if ("RECORDKEY".equals(fieldDefine.getCode())) {
                    newDataRow.setValue(0, (Object)UUID.randomUUID().toString());
                }
                if (DW_FIELD.equals(fieldDefine.getCode()) || PERIOD_FIELD.equals(fieldDefine.getCode())) {
                    newDataRow.setValue(i++, masterKeys.getValue(dimensionChanger.getDimensionName(fieldDefine)));
                }
                if (fieldDefine.getCode().equals("CUREVENT")) {
                    newDataRow.setValue(i++, (Object)curEvent);
                    continue;
                }
                if (fieldDefine.getCode().equals("CURNODE")) {
                    newDataRow.setValue(i++, (Object)curNode);
                    continue;
                }
                if (fieldDefine.getCode().equals("CMT")) {
                    newDataRow.setValue(i++, (Object)comment);
                    continue;
                }
                if (fieldDefine.getCode().equals("OPERATOR")) {
                    newDataRow.setValue(i++, (Object)actorId);
                    continue;
                }
                if (fieldDefine.getCode().equals("OPERATIONID")) {
                    newDataRow.setValue(i++, (Object)operationId);
                    continue;
                }
                if (fieldDefine.getCode().equals("TIME_")) {
                    newDataRow.setValue(i++, (Object)this.formateDate(new Date()));
                    continue;
                }
                if (fieldDefine.getCode().equals("EXECUTE_ORDER")) {
                    newDataRow.setValue(i++, (Object)exeOrder);
                    continue;
                }
                if (!fieldDefine.getCode().equals("FORCE_STATE")) continue;
                if (force == null || !force.booleanValue()) {
                    newDataRow.setValue(i++, (Object)0);
                    continue;
                }
                newDataRow.setValue(i++, (Object)1);
            }
            updaor.commitChanges(context);
        }
        catch (Exception e) {
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append("commit history data error. masterKeys:");
            logBuilder.append(masterKeys.toString());
            logBuilder.append(", curEvent:");
            logBuilder.append(curEvent).append(", curNode:").append(curNode);
            logBuilder.append(", tableName:").append(fieldDefines.get(0).getTableID());
            throw new BpmException(logBuilder.toString(), e);
        }
    }

    private void commitStateDataNvwa(NvwaQueryModel queryModel, DataAccessContext context, TableModelDefine tableModelDefine, List<ColumnModelDefine> allColumns, List<ColumnModelDefine> dimensionColumns, DimensionValueSet masterKeys, String prevEvent, String curNode, Boolean force) {
        try {
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableModelDefine.getName());
            ArrayKey arrayKey = dimensionChanger.getArrayKey(masterKeys, dimensionColumns);
            INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
            INvwaUpdatableDataSet iNvwaDataRows = updatableDataAccess.executeQueryForUpdate(context);
            INvwaDataRow findRow = iNvwaDataRows.findRow(arrayKey);
            if (findRow == null) {
                findRow = iNvwaDataRows.appendRow();
                int index = 0;
                for (ColumnModelDefine fieldDefine : allColumns) {
                    if (fieldDefine.getCode().equals("PREVEVENT")) {
                        findRow.setValue(index, (Object)prevEvent);
                    } else if (fieldDefine.getCode().equals("CURNODE")) {
                        findRow.setValue(index, (Object)curNode);
                    } else if (fieldDefine.getCode().equals("START_TIME")) {
                        if (prevEvent.equals("start")) {
                            findRow.setValue(index, (Object)new Date());
                        }
                    } else if (fieldDefine.getCode().equals("UPDATE_TIME") && !prevEvent.equals("start")) {
                        findRow.setValue(index, (Object)new Date());
                    }
                    if (fieldDefine.getCode().equals("FORCE_STATE")) {
                        if (force == null || !force.booleanValue()) {
                            findRow.setValue(index, (Object)0);
                        } else {
                            findRow.setValue(index, (Object)1);
                        }
                    }
                    ++index;
                }
            }
            iNvwaDataRows.commitChanges(context);
        }
        catch (Exception e1) {
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append("commit state data error. masterKeys:");
            logBuilder.append(masterKeys.toString());
            logBuilder.append(", prevEvent:");
            logBuilder.append(prevEvent).append(", curNode:").append(curNode);
            logBuilder.append(", tableName:").append(allColumns.get(0).getTableID());
            throw new BpmException(logBuilder.toString(), e1);
        }
    }

    private Date formateDate(Date date) {
        SimpleDateFormat format = stf.get();
        Date operateTime = null;
        try {
            String dateStr = format.format(date);
            operateTime = format.parse(dateStr);
        }
        catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        return operateTime;
    }

    class EntityChangeInfo {
        static final int TYPE_TASK = 1;
        static final int TYPE_FORMSCHEM = 2;
        static final int TYPE_FORM = 3;
        static final int TYPE_REGION = 4;
        boolean hasChange;
        String oldKeys;
        String newKeys;
        int type;
        List<String> removeKeys = new ArrayList<String>();
        List<String> addKeys = new ArrayList<String>();

        EntityChangeInfo() {
        }
    }
}

