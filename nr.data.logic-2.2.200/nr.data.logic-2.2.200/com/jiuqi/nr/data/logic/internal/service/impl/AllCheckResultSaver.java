/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
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
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.data.logic.internal.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.logic.internal.cache.MemoryDataSetHelper;
import com.jiuqi.nr.data.logic.internal.entity.FmlCheckResultEntity;
import com.jiuqi.nr.data.logic.internal.obj.ColModelSaveObj;
import com.jiuqi.nr.data.logic.internal.util.SplitCheckTableHelper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
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
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class AllCheckResultSaver {
    private static final String MSG_FORM_SCHEME_NOT_FOUND = "\u62a5\u8868\u65b9\u6848\u4e0d\u5b58\u5728";
    private static final Logger logger = LoggerFactory.getLogger(AllCheckResultSaver.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private INvwaDataAccessProvider dataAccessProvider;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private MemoryDataSetHelper memoryDataSetHelper;
    @Autowired
    private SplitCheckTableHelper splitTableHelper;

    public void dbSave(List<FmlCheckResultEntity> checkResultEntities, String formSchemeKey, String cacheKey) {
        String[] split;
        this.memoryDataSetHelper.clearData(cacheKey);
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        assert (formScheme != null) : "\u62a5\u8868\u65b9\u6848\u4e0d\u5b58\u5728";
        String tableName = this.splitTableHelper.getSplitAllCKRTableName(formScheme);
        TableModelDefine table = this.dataModelService.getTableModelDefineByName(tableName);
        assert (table != null) : "\u5ba1\u6838\u6570\u636e\u8868" + tableName + "\u4e0d\u5b58\u5728";
        DataAccessContext dataAccessContext = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List allColumns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
        HashMap<String, ColModelSaveObj> colMap = new HashMap<String, ColModelSaveObj>();
        for (int i = 0; i < allColumns.size(); ++i) {
            ColumnModelDefine columnModel = (ColumnModelDefine)allColumns.get(i);
            queryModel.getColumns().add(new NvwaQueryColumn(columnModel));
            ColModelSaveObj colModelSaveObj = new ColModelSaveObj(columnModel, i);
            colMap.put(columnModel.getName(), colModelSaveObj);
        }
        ArrayList<String> keyColumns = new ArrayList<String>();
        for (String s : split = table.getBizKeys().split(";")) {
            ColumnModelDefine columnModelDefineByID = this.dataModelService.getColumnModelDefineByID(s);
            keyColumns.add(columnModelDefineByID.getName());
        }
        INvwaUpdatableDataAccess iNvwaUpdatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
        try {
            INvwaDataUpdator updater = iNvwaUpdatableDataAccess.openForUpdate(dataAccessContext);
            for (FmlCheckResultEntity checkResult : checkResultEntities) {
                this.appendDataRow(colMap, updater, checkResult, keyColumns);
            }
            updater.commitChanges(dataAccessContext);
        }
        catch (Exception e) {
            logger.error("\u5168\u5ba1\u6838\u7ed3\u679c\u4fdd\u5b58\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
    }

    private void appendDataRow(Map<String, ColModelSaveObj> colMap, INvwaDataUpdator updater, FmlCheckResultEntity checkResult, List<String> keyColumns) throws IncorrectQueryException {
        INvwaDataRow row = updater.addInsertRow();
        row.setValue(colMap.get("ALLCKR_FORMULASCHEMEKEY").getIndex(), (Object)checkResult.getFormulaSchemeKey());
        row.setValue(colMap.get("ALLCKR_FORMKEY").getIndex(), (Object)checkResult.getFormKey());
        row.setValue(colMap.get("ALLCKR_FORMULACODE").getIndex(), (Object)checkResult.getFormulaExpressionKey());
        row.setValue(colMap.get("ALLCKR_GLOBROW").getIndex(), (Object)checkResult.getGlobRow());
        row.setValue(colMap.get("ALLCKR_GLOBCOL").getIndex(), (Object)checkResult.getGlobCol());
        row.setValue(colMap.get("ALLCKR_DIMSTR").getIndex(), (Object)checkResult.getDimStr());
        row.setValue(colMap.get("ALLCKR_ERRORDESC").getIndex(), (Object)checkResult.getErrorDesc());
        row.setValue(colMap.get("ALLCKR_LEFT").getIndex(), (Object)checkResult.getLeft());
        row.setValue(colMap.get("ALLCKR_RIGHT").getIndex(), (Object)checkResult.getRight());
        row.setValue(colMap.get("ALLCKR_BALANCE").getIndex(), (Object)checkResult.getBalance());
        row.setValue(colMap.get("ALLCKR_FORMORDER").getIndex(), (Object)checkResult.getFormOrder());
        row.setValue(colMap.get("ALLCKR_FORMULAORDER").getIndex(), (Object)checkResult.getFormulaOrder());
        row.setValue(colMap.get("ALLCKR_FORMULACHECKTYPE").getIndex(), (Object)checkResult.getFormulaCheckType());
        row.setValue(colMap.get("ALLCKR_FORMULAID").getIndex(), (Object)checkResult.getFormulaKey());
        row.setKeyValue(colMap.get("ALLCKR_RECID").getColumn(), (Object)checkResult.getRecId());
        row.setKeyValue(colMap.get("ALLCKR_ASYNCTASKID").getColumn(), (Object)checkResult.getActionID());
        if (keyColumns.contains("VERSIONID")) {
            row.setKeyValue(colMap.get("VERSIONID").getColumn(), (Object)"00000000-0000-0000-0000-000000000000");
        }
        for (Map.Entry<String, String> entry : checkResult.getDimMap().entrySet()) {
            row.setKeyValue(colMap.get(entry.getKey()).getColumn(), (Object)entry.getValue());
        }
    }

    public void cacheSave(List<FmlCheckResultEntity> checkResultEntities, String formSchemeKey, String cacheKey, DimensionValueSet dimensionValueSet, String actionID) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        assert (formScheme != null) : "\u62a5\u8868\u65b9\u6848\u4e0d\u5b58\u5728";
        this.memoryDataSetHelper.writeData(checkResultEntities, formScheme, cacheKey, dimensionValueSet, actionID);
    }

    public void delResult(String formSchemeKey, List<String> cacheKeys, String actionID) {
        if (!CollectionUtils.isEmpty(cacheKeys)) {
            for (String cacheKey : cacheKeys) {
                this.memoryDataSetHelper.clearData(cacheKey);
            }
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        assert (formScheme != null) : "\u62a5\u8868\u65b9\u6848\u4e0d\u5b58\u5728";
        String tableName = this.splitTableHelper.getSplitAllCKRTableName(formScheme);
        TableModelDefine table = this.dataModelService.getTableModelDefineByName(tableName);
        assert (table != null) : "\u5ba1\u6838\u6570\u636e\u8868" + tableName + "\u4e0d\u5b58\u5728";
        DataAccessContext dataAccessContext = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List allColumns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
        ColumnModelDefine asyncIdCol = null;
        for (ColumnModelDefine column : allColumns) {
            queryModel.getColumns().add(new NvwaQueryColumn(column));
            if (!"ALLCKR_ASYNCTASKID".equals(column.getCode())) continue;
            asyncIdCol = column;
        }
        if (asyncIdCol == null) {
            return;
        }
        queryModel.getColumnFilters().put(asyncIdCol, actionID);
        INvwaUpdatableDataAccess updatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
        try {
            INvwaDataUpdator dataUpdator = updatableDataAccess.openForUpdate(dataAccessContext);
            dataUpdator.deleteAll();
            dataUpdator.commitChanges(dataAccessContext);
        }
        catch (Exception e) {
            logger.error("\u5168\u5ba1\u7ed3\u679c\u5220\u9664\u5f02\u5e38\uff1a{}", (Object)e.getMessage(), (Object)e);
        }
    }
}

