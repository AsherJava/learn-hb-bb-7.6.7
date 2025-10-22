/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IDataUpdator
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeExpressionService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.datacrud.impl.crud.strategy;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datacrud.IClearInfo;
import com.jiuqi.nr.datacrud.ISaveInfo;
import com.jiuqi.nr.datacrud.ParamRelation;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.SaveData;
import com.jiuqi.nr.datacrud.SaveResItem;
import com.jiuqi.nr.datacrud.SaveReturnRes;
import com.jiuqi.nr.datacrud.SaveRowData;
import com.jiuqi.nr.datacrud.impl.Const;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.impl.crud.IRegionDataSetUpdatableStrategy;
import com.jiuqi.nr.datacrud.impl.crud.strategy.BaseRegionDataUpdatableStrategy;
import com.jiuqi.nr.datacrud.impl.loggger.DataServiceLogWrapper;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.datacrud.impl.out.CrudOperateException;
import com.jiuqi.nr.datacrud.impl.out.CrudSaveException;
import com.jiuqi.nr.datacrud.impl.service.DataEngineService;
import com.jiuqi.nr.datacrud.spi.IExecutorContextFactory;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeExpressionService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class FloatRegionDataUpdatableStrategy
extends BaseRegionDataUpdatableStrategy
implements IRegionDataSetUpdatableStrategy {
    private IDataUpdator dataUpdator;
    private IDataTable dataTable;
    private SaveReturnRes returnRes;
    private int floatOrderIndex = -1;
    private List<MetaData> metas;

    public FloatRegionDataUpdatableStrategy(RegionRelationFactory regionRelationFactory, IExecutorContextFactory executorContextFactory, DataEngineService dataEngineService, IDataAccessServiceProvider dataAccessServiceProvider, IRuntimeExpressionService expressionService, DataServiceLogWrapper dataServiceLogWrapper, IEntityMetaService entityMetaService) {
        super(regionRelationFactory, executorContextFactory, dataEngineService, dataAccessServiceProvider, expressionService, dataServiceLogWrapper, entityMetaService);
    }

    @Override
    public ReturnRes clearData(IClearInfo clearInfo, RegionRelation relation) throws CrudOperateException {
        super.initDataQuery(clearInfo, relation);
        DimensionCollection dimensionCollection = clearInfo.getDimensionCollection();
        if (dimensionCollection != null) {
            this.dataQuery.setMasterKeys(DimensionValueSetUtil.mergeDimensionValueSet((DimensionCollection)dimensionCollection));
            this.context = this.executorContextFactory.getExecutorContext((ParamRelation)relation, this.dataQuery.getMasterKeys());
            this.crudLogger = this.dataServiceLogWrapper.getCrudLogger(relation, dimensionCollection);
        } else {
            this.crudLogger = this.dataServiceLogWrapper.getCrudLogger(relation, clearInfo.getDimensionCombination());
        }
        boolean readOnly = this.isReadRegionOnly();
        if (readOnly) {
            return ReturnRes.build(1102, "\u533a\u57df\u53ea\u8bfb");
        }
        List<MetaData> metaData = relation.getMetaData(null);
        if (metaData.isEmpty()) {
            return ReturnRes.build(0, "\u6d6e\u52a8\u533a\u57df\u4e0b\u94fe\u63a5\u4e3a\u7a7a,\u7ec8\u6b62\u6267\u884c\u6e05\u9664\u533a\u57df\u6570\u636e");
        }
        ReturnRes haveWrite = this.haveWrite(metaData);
        if (haveWrite != null) {
            return haveWrite;
        }
        this.crudLogger.beginClearData("\u5f00\u59cb\u6e05\u9664\u6d6e\u52a8\u533a\u57df\u6570\u636e");
        super.addQueryCol(metaData);
        super.addFilter(clearInfo.rowFilterItr());
        try {
            IDataUpdator openForUpdate = this.dataQuery.openForUpdate(this.context, true);
            openForUpdate.commitChanges();
            this.crudLogger.clearSuccess("\u6d6e\u52a8\u533a\u57df\u6267\u884c\u533a\u57df\u6570\u636e\u6e05\u9664\u5b8c\u6210");
            return ReturnRes.ok("\u6d6e\u52a8\u533a\u57df\u6267\u884c\u533a\u57df\u6570\u636e\u6e05\u9664\u5b8c\u6210");
        }
        catch (Exception e) {
            this.crudLogger.clearFail("\u6d6e\u52a8\u533a\u57df\u6267\u884c\u533a\u57df\u6570\u636e\u6e05\u9664\u51fa\u9519");
            logger.error("\u533a\u57df\u6570\u636e\u6e05\u9664\u6267\u884c\u51fa\u9519", e);
            throw new CrudOperateException(4111, "\u533a\u57df\u6570\u636e\u6e05\u9664\u6267\u884c\u51fa\u9519", e);
        }
    }

    private ReturnRes haveWrite(List<MetaData> metaData) {
        try {
            for (MetaData metaDatum : metaData) {
                IAccessResult canWrite = metaDatum.canWrite();
                if (canWrite == null) {
                    return null;
                }
                if (canWrite.haveAccess()) continue;
                return ReturnRes.build(1101, "\u6d6e\u52a8\u533a\u57df\u4e0b\u94fe\u63a5\u65e0\u6743\u9650,\u7ec8\u6b62\u6267\u884c\u6e05\u9664\u533a\u57df\u6570\u636e");
            }
        }
        catch (Exception e) {
            logger.error("\u6307\u6807\u6743\u9650\u5224\u65ad\u51fa\u9519", e);
            throw new CrudException(4001);
        }
        return null;
    }

    @Override
    public SaveReturnRes saveData(ISaveInfo saveInfo, RegionRelation relation) throws CrudOperateException {
        List<MetaData> metaData;
        SaveData saveData = saveInfo.getSaveData();
        List<String> links = saveData.getLinks();
        super.initDataQuery(saveInfo, relation);
        Iterator<String> cellItr = null;
        if (!CollectionUtils.isEmpty(links)) {
            cellItr = links.iterator();
        }
        if (CollectionUtils.isEmpty(metaData = relation.getMetaData(cellItr))) {
            return SaveReturnRes.build(1001, "\u6d6e\u52a8\u533a\u57df\u4e0b\u4fdd\u5b58\u6570\u636e\u4e3a\u7a7a,\u7ec8\u6b62\u6267\u884c\u4fdd\u5b58\u533a\u57df\u6570\u636e");
        }
        this.crudLogger = this.dataServiceLogWrapper.getCrudLogger(relation, saveInfo.getDimensionCombination());
        boolean readOnly = this.isReadRegionOnly();
        if (readOnly) {
            return SaveReturnRes.build(1102, "\u533a\u57df\u53ea\u8bfb");
        }
        ReturnRes haveWrite = this.haveWrite(metaData);
        if (haveWrite != null) {
            return new SaveReturnRes(haveWrite);
        }
        super.addQueryCol(metaData);
        this.floatOrderIndex = this.dataQuery.addColumn((FieldDefine)((DataFieldDTO)relation.getFloatOrderField()));
        this.returnRes = new SaveReturnRes();
        this.doBeforeDelete(saveInfo);
        if (saveData.isNotEmpty(0)) {
            super.addFilter(saveInfo.rowFilterItr());
            this.nodeTypeCommit(saveData);
        } else {
            int rowCount = saveData.getRowCount();
            if (rowCount == 0) {
                this.crudLogger.saveSuccess("\u6d6e\u52a8\u533a\u57df\u4e0b\u94fe\u63a5\u4e3a\u7a7a,\u7ec8\u6b62\u6267\u884c\u4fdd\u5b58\u533a\u57df\u6570\u636e");
                return SaveReturnRes.ok("\u6d6e\u52a8\u533a\u57df\u4e0b\u94fe\u63a5\u4e3a\u7a7a,\u7ec8\u6b62\u6267\u884c\u4fdd\u5b58\u533a\u57df\u6570\u636e");
            }
            if (saveData.isNotEmpty(1, 2, 3)) {
                this.typeCommit(saveData);
            }
            if (saveData.isNotEmpty(4)) {
                this.partialTypeCommit(saveData);
            }
        }
        if (!CollectionUtils.isEmpty(this.returnRes.getSaveResItems())) {
            this.returnRes.setCode(1900);
            this.returnRes.setMessage("\u4fdd\u5b58\u5931\u8d25");
            StringBuilder msg = new StringBuilder();
            for (SaveResItem saveResItem : this.returnRes.getSaveResItems()) {
                msg.append(saveResItem.getMessage()).append(";");
            }
            this.crudLogger.saveFail(msg.toString());
            throw new CrudSaveException(1900, "\u4fdd\u5b58\u5931\u8d25", this.returnRes.getSaveResItems());
        }
        return this.returnRes;
    }

    private void doBeforeDelete(ISaveInfo saveInfo) throws CrudOperateException {
        if (saveInfo.enableDeleteBeforeSave()) {
            try {
                super.addFilter(saveInfo.rowFilterItr());
                IDataUpdator deleteUpdater = this.dataQuery.openForUpdate(this.context, true);
                deleteUpdater.deleteAll();
                deleteUpdater.commitChanges();
            }
            catch (Exception e) {
                this.crudLogger.saveFail("\u6d6e\u52a8\u533a\u57df\u6267\u884c\u6570\u636e\u4fdd\u5b58\u53d1\u751f\u9519\u8bef");
                logger.error("\u6d6e\u52a8\u533a\u57df\u6267\u884c\u6570\u636e\u4fdd\u5b58\u53d1\u751f\u9519\u8bef", e);
                throw new CrudOperateException(4121, "\u6d6e\u52a8\u533a\u57df\u6267\u884c\u6570\u636e\u4fdd\u5b58\u53d1\u751f\u9519\u8bef", e);
            }
        }
    }

    private void typeCommit(SaveData saveData) throws CrudOperateException {
        try {
            this.dataUpdator = this.dataQuery.openForUpdate(this.context);
        }
        catch (Exception e) {
            this.crudLogger.saveFail("\u6d6e\u52a8\u533a\u57df\u6267\u884c\u6570\u636e\u4fdd\u5b58\u53d1\u751f\u9519\u8bef");
            logger.error("\u6d6e\u52a8\u533a\u57df\u6267\u884c\u6570\u636e\u4fdd\u5b58\u53d1\u751f\u9519\u8bef", e);
            throw new CrudOperateException(4121, "\u6d6e\u52a8\u533a\u57df\u6267\u884c\u6570\u636e\u4fdd\u5b58\u53d1\u751f\u9519\u8bef", e);
        }
        this.dataUpdator.needCheckDuplicateKeys(!this.relation.getRegionDefine().getAllowDuplicateKey());
        for (SaveRowData row : saveData.getTypeRows(3)) {
            this.doDel(row);
        }
        for (SaveRowData row : saveData.getTypeRows(2)) {
            this.doUpdate(row);
        }
        for (SaveRowData row : saveData.getTypeRows(1)) {
            this.doAdd(row);
        }
        if (CollectionUtils.isEmpty(this.returnRes.getSaveResItems())) {
            this.dataUpdator.setValidExpression(this.relation.getExpressions());
            try {
                this.dataUpdator.commitChanges(true);
                this.returnRes.setMessage("\u4fdd\u5b58\u6210\u529f");
                this.crudLogger.saveSuccess("\u4fdd\u5b58\u6d6e\u52a8\u533a\u57df\u6570\u636e\u5b8c\u6210");
            }
            catch (Exception e) {
                this.returnRes.getSaveResItems().addAll(this.exceptionErrors(saveData, e));
            }
        }
    }

    private void nodeTypeCommit(SaveData saveData) throws CrudOperateException {
        List<SaveRowData> typeRows = saveData.getTypeRows(0);
        DimensionValueSet masterKeys = FloatRegionDataUpdatableStrategy.buildMasterKeysByRow(typeRows);
        this.executeQuery(masterKeys);
        for (SaveRowData row : typeRows) {
            this.doDelByTable(row);
        }
        for (SaveRowData row : typeRows) {
            this.doAddByTable(row);
        }
        this.doCommit(saveData);
    }

    private static DimensionValueSet buildMasterKeysByRow(List<SaveRowData> typeRows) {
        ArrayList<DimensionValueSet> rowKeys = new ArrayList<DimensionValueSet>();
        for (SaveRowData typeRow : typeRows) {
            DimensionCombination combination = typeRow.getCombination();
            rowKeys.add(combination.toDimensionValueSet());
        }
        return DimensionValueSetUtil.mergeDimensionValueSet(rowKeys);
    }

    private void partialTypeCommit(SaveData saveData) throws CrudOperateException {
        List<SaveRowData> typeRows = saveData.getTypeRows(4);
        DimensionValueSet masterKeys = FloatRegionDataUpdatableStrategy.buildMasterKeysByRow(typeRows);
        this.executeQuery(masterKeys);
        for (SaveRowData typeRow : typeRows) {
            DimensionCombination combination = typeRow.getCombination();
            DimensionValueSet rowKeys = combination.toDimensionValueSet();
            IDataRow row = this.dataTable.findRow(rowKeys);
            if (row == null) continue;
            this.putValue(typeRow, row);
        }
        this.doCommit(saveData);
    }

    private void executeQuery(DimensionValueSet masterKeys) throws CrudOperateException {
        try {
            this.dataQuery.setMasterKeys(masterKeys);
            this.dataTable = this.dataQuery.executeQuery(this.context);
        }
        catch (Exception e) {
            this.crudLogger.saveFail("\u6d6e\u52a8\u533a\u57df\u6267\u884c\u6570\u636e\u4fdd\u5b58\u53d1\u751f\u9519\u8bef");
            logger.error("\u6d6e\u52a8\u533a\u57df\u6267\u884c\u6570\u636e\u4fdd\u5b58\u53d1\u751f\u9519\u8bef", e);
            throw new CrudOperateException(4121, "\u6d6e\u52a8\u533a\u57df\u6267\u884c\u6570\u636e\u4fdd\u5b58\u53d1\u751f\u9519\u8bef", e);
        }
        this.dataTable.needCheckDuplicateKeys(!this.relation.getRegionDefine().getAllowDuplicateKey());
    }

    private void doCommit(SaveData saveData) {
        if (CollectionUtils.isEmpty(this.returnRes.getSaveResItems())) {
            try {
                this.dataTable.setValidExpression(this.relation.getExpressions());
                this.dataTable.commitChanges(true);
                this.returnRes.setMessage("\u4fdd\u5b58\u6210\u529f");
                this.crudLogger.saveSuccess("\u4fdd\u5b58\u6d6e\u52a8\u533a\u57df\u6570\u636e\u5b8c\u6210");
            }
            catch (Exception e) {
                this.returnRes.getSaveResItems().addAll(this.exceptionErrors(saveData, e));
            }
        }
    }

    private void doAdd(SaveRowData rowData) throws CrudOperateException {
        try {
            DimensionCombination combination = rowData.getCombination();
            this.checkDimensionCombination(combination);
            DimensionValueSet rowDim = rowData.getCombination().toDimensionValueSet();
            rowData.getSaveData().addRowsByDimensionValueSet(rowDim, rowData);
            IDataRow iDataRow = this.dataUpdator.addInsertedRow(rowDim);
            this.putValue(rowData, iDataRow);
        }
        catch (IncorrectQueryException e) {
            logger.error("\u6d6e\u52a8\u533a\u57df\u65b0\u589e\u6570\u636e\u53d1\u751f\u9519\u8bef", e);
            List<SaveResItem> saveResItems = this.exceptionErrors((Exception)((Object)e));
            for (SaveResItem saveResItem : saveResItems) {
                saveResItem.setRowIndex(rowData.getRowIndex());
            }
            this.returnRes.getSaveResItems().addAll(saveResItems);
        }
        catch (Exception e) {
            logger.error("\u6d6e\u52a8\u533a\u57df\u65b0\u589e\u6570\u636e\u53d1\u751f\u9519\u8bef", e);
            throw new CrudOperateException(4122, "\u6d6e\u52a8\u533a\u57df\u65b0\u589e\u6570\u636e\u53d1\u751f\u9519\u8bef", e);
        }
    }

    private Integer getColIndex(String linkKey) {
        MetaData metaData = this.relation.getMetaDataByLink(linkKey);
        int colIndex = -1;
        if (metaData == null) {
            if ("FLOATORDER".equals(linkKey)) {
                colIndex = this.floatOrderIndex;
            }
        } else {
            colIndex = metaData.getIndex();
        }
        if (colIndex <= -1) {
            return null;
        }
        return colIndex;
    }

    private void checkDimensionCombination(DimensionCombination combination) {
        if (combination == null) {
            SaveResItem resItem = new SaveResItem();
            resItem.setMessage("\u4e1a\u52a1\u4e3b\u952e\u4e0d\u80fd\u4e3a\u7a7a");
            this.returnRes.getSaveResItems().add(resItem);
            return;
        }
        DataTable dataTable = this.relation.getDataTable();
        DataTableType dataTableType = dataTable.getDataTableType();
        DimensionCombination dimensionCombination = this.regionInfo.getDimensionCombination();
        DimensionValueSet master = dimensionCombination.toDimensionValueSet();
        DimensionValueSet dimensionValueSet = combination.toDimensionValueSet();
        for (int i = 0; i < master.size(); ++i) {
            String name = master.getName(i);
            dimensionValueSet.clearValue(name);
        }
        if (dataTableType != DataTableType.ACCOUNT) {
            if (dimensionValueSet.hasAnyNull()) {
                SaveResItem resItem = new SaveResItem();
                resItem.setMessage("\u6d6e\u52a8\u884c\u4e1a\u52a1\u4e3b\u952e\u4e0d\u80fd\u4e3a\u7a7a" + dimensionValueSet);
                this.returnRes.getSaveResItems().add(resItem);
            }
        } else if (dimensionValueSet.isAllNull()) {
            SaveResItem resItem = new SaveResItem();
            resItem.setMessage("\u6d6e\u52a8\u884c\u4e1a\u52a1\u4e3b\u952e\u4e0d\u80fd\u5168\u4e3a\u7a7a" + dimensionValueSet);
            this.returnRes.getSaveResItems().add(resItem);
        }
    }

    private void doUpdate(SaveRowData rowData) throws CrudOperateException {
        if (rowData.isPkUpdate()) {
            this.doDel(rowData);
            this.doAdd(rowData.getNewRow());
            return;
        }
        try {
            this.checkDimensionCombination(rowData.getCombination());
            DimensionValueSet rowDim = rowData.getCombination().toDimensionValueSet();
            rowData.getSaveData().addRowsByDimensionValueSet(rowDim, rowData);
            IDataRow iDataRow = this.dataUpdator.addModifiedRow(rowDim);
            this.putValue(rowData, iDataRow);
        }
        catch (IncorrectQueryException e) {
            logger.warn("\u6d6e\u52a8\u533a\u57df\u66f4\u65b0\u6570\u636e\u53d1\u751f\u9519\u8bef", e);
            List<SaveResItem> saveResItems = this.exceptionErrors((Exception)((Object)e));
            for (SaveResItem saveResItem : saveResItems) {
                saveResItem.setRowIndex(rowData.getRowIndex());
            }
            this.returnRes.getSaveResItems().addAll(saveResItems);
        }
        catch (Exception e) {
            logger.error("\u6d6e\u52a8\u533a\u57df\u66f4\u65b0\u6570\u636e\u53d1\u751f\u9519\u8bef", e);
            throw new CrudOperateException(4123, "\u6d6e\u52a8\u533a\u57df\u66f4\u65b0\u6570\u636e\u53d1\u751f\u9519\u8bef", e);
        }
    }

    private void putValue(SaveRowData rowData, IDataRow iDataRow) {
        SaveData saveData = rowData.getSaveData();
        List<String> links = saveData.getLinks();
        for (int i = 0; i < links.size(); ++i) {
            DataField dataField;
            MetaData meta;
            String linkKey = links.get(i);
            Integer colIndex = this.getColIndex(linkKey);
            if (colIndex == null) continue;
            AbstractData value = null;
            if (rowData.getLinkValues().length > i) {
                value = rowData.getLinkValues()[i];
            }
            if ((value == null || value.getAsNull()) && (meta = this.relation.getMetaDataByLink(linkKey)) != null && (dataField = meta.getDataField()) != null) {
                if (StringUtils.hasLength(dataField.getDefaultValue())) {
                    value = this.evaluateDefaultValue(this.context, this.relation, iDataRow.getRowKeys(), meta);
                } else if (dataField.getDataFieldKind().equals((Object)DataFieldKind.TABLE_FIELD_DIM) && dataField.getDataFieldType().equals((Object)DataFieldType.STRING)) {
                    value = this.relation.getTableDimDefaultValue(dataField.getKey());
                }
            }
            if (value == Const.UNMODIFIED_VALUE) continue;
            iDataRow.setValue(colIndex.intValue(), (Object)value);
        }
    }

    private void doDel(SaveRowData rowData) throws CrudOperateException {
        try {
            this.checkDimensionCombination(rowData.getCombination());
            DimensionValueSet rowDim = rowData.getCombination().toDimensionValueSet();
            rowData.getSaveData().addRowsByDimensionValueSet(rowDim, rowData);
            this.dataUpdator.addDeletedRow(rowDim);
        }
        catch (IncorrectQueryException e) {
            logger.warn("\u6d6e\u52a8\u533a\u57df\u5220\u9664\u6570\u636e\u53d1\u751f\u9519\u8bef", e);
            List<SaveResItem> saveResItems = this.exceptionErrors((Exception)((Object)e));
            for (SaveResItem saveResItem : saveResItems) {
                saveResItem.setRowIndex(rowData.getRowIndex());
            }
            this.returnRes.getSaveResItems().addAll(saveResItems);
        }
        catch (Exception e) {
            logger.error("\u6d6e\u52a8\u533a\u57df\u5220\u9664\u6570\u636e\u53d1\u751f\u9519\u8bef", e);
            throw new CrudOperateException(4124, "\u6d6e\u52a8\u533a\u57df\u5220\u9664\u6570\u636e\u53d1\u751f\u9519\u8bef", e);
        }
    }

    private void doAddByTable(SaveRowData row) throws CrudOperateException {
        try {
            this.checkDimensionCombination(row.getCombination());
            DimensionValueSet rowDim = row.getCombination().toDimensionValueSet();
            row.getSaveData().addRowsByDimensionValueSet(rowDim, row);
            IDataRow iDataRow = this.dataTable.appendRow(rowDim);
            this.putValue(row, iDataRow);
        }
        catch (IncorrectQueryException e) {
            logger.warn("\u6d6e\u52a8\u533a\u57df\u66f4\u65b0\u6570\u636e\u53d1\u751f\u9519\u8bef", e);
            List<SaveResItem> saveResItems = this.exceptionErrors(row.getSaveData(), (Exception)((Object)e));
            for (SaveResItem saveResItem : saveResItems) {
                saveResItem.setRowIndex(row.getRowIndex());
            }
            this.returnRes.getSaveResItems().addAll(saveResItems);
        }
        catch (Exception e) {
            logger.error("\u6d6e\u52a8\u533a\u57df\u66f4\u65b0\u6570\u636e\u53d1\u751f\u9519\u8bef", e);
            throw new CrudOperateException(4124, "\u6d6e\u52a8\u533a\u57df\u66f4\u65b0\u6570\u636e\u53d1\u751f\u9519\u8bef", e);
        }
    }

    private void doDelByTable(SaveRowData row) throws CrudOperateException {
        try {
            this.checkDimensionCombination(row.getCombination());
            DimensionValueSet rowDim = row.getCombination().toDimensionValueSet();
            row.getSaveData().addRowsByDimensionValueSet(rowDim, row);
            this.dataTable.deleteRow(rowDim);
        }
        catch (IncorrectQueryException e) {
            logger.warn("\u6d6e\u52a8\u533a\u57df\u66f4\u65b0\u6570\u636e\u53d1\u751f\u9519\u8bef", e);
            List<SaveResItem> saveResItems = this.exceptionErrors(row.getSaveData(), (Exception)((Object)e));
            for (SaveResItem saveResItem : saveResItems) {
                saveResItem.setRowIndex(row.getRowIndex());
            }
            this.returnRes.getSaveResItems().addAll(saveResItems);
        }
        catch (Exception e) {
            logger.error("\u6d6e\u52a8\u533a\u57df\u66f4\u65b0\u6570\u636e\u53d1\u751f\u9519\u8bef", e);
            throw new CrudOperateException(4124, "\u6d6e\u52a8\u533a\u57df\u66f4\u65b0\u6570\u636e\u53d1\u751f\u9519\u8bef", e);
        }
    }
}

