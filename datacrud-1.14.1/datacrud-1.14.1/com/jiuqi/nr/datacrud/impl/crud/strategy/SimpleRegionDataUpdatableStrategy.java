/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.setting.IFieldsInfo
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeExpressionService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.datacrud.impl.crud.strategy;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
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
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeExpressionService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.HashSet;
import java.util.List;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class SimpleRegionDataUpdatableStrategy
extends BaseRegionDataUpdatableStrategy
implements IRegionDataSetUpdatableStrategy {
    public SimpleRegionDataUpdatableStrategy(RegionRelationFactory regionRelationFactory, IExecutorContextFactory executorContextFactory, DataEngineService dataEngineService, IDataAccessServiceProvider dataAccessServiceProvider, IRuntimeExpressionService expressionService, DataServiceLogWrapper dataServiceLogWrapper, IEntityMetaService entityMetaService) {
        super(regionRelationFactory, executorContextFactory, dataEngineService, dataAccessServiceProvider, expressionService, dataServiceLogWrapper, entityMetaService);
    }

    @Override
    public ReturnRes clearData(IClearInfo clearInfo, RegionRelation relation) throws CrudOperateException {
        super.initDataQuery(clearInfo, relation);
        DimensionCollection dimensionCollection = clearInfo.getDimensionCollection();
        if (dimensionCollection != null) {
            this.crudLogger = this.dataServiceLogWrapper.getCrudLogger(relation, dimensionCollection);
            this.dataQuery.setMasterKeys(DimensionValueSetUtil.mergeDimensionValueSet((DimensionCollection)dimensionCollection));
            this.context = this.executorContextFactory.getExecutorContext((ParamRelation)relation, this.dataQuery.getMasterKeys());
        } else {
            this.crudLogger = this.dataServiceLogWrapper.getCrudLogger(relation, clearInfo.getDimensionCombination());
        }
        List<MetaData> metaData = relation.getMetaData(null);
        if (metaData.isEmpty()) {
            return ReturnRes.build(0, "\u56fa\u5b9a\u533a\u57df\u4e0b\u94fe\u63a5\u4e3a\u7a7a,\u7ec8\u6b62\u6267\u884c\u6e05\u9664\u533a\u57df\u6570\u636e");
        }
        ReturnRes haveWrite = this.haveWrite(metaData);
        if (haveWrite != null) {
            return haveWrite;
        }
        super.addQueryCol(metaData);
        this.crudLogger.beginClearData("\u5f00\u59cb\u5220\u9664\u56fa\u5b9a\u533a\u57df\u6570\u636e");
        this.doClear();
        this.crudLogger.clearSuccess("\u56fa\u5b9a\u533a\u57df\u6267\u884c\u533a\u57df\u6570\u636e\u6e05\u9664\u5b8c\u6210");
        return ReturnRes.ok("\u56fa\u5b9a\u533a\u57df\u6267\u884c\u533a\u57df\u6570\u636e\u6e05\u9664\u5b8c\u6210");
    }

    private boolean doClear() throws CrudOperateException {
        try {
            IDataTable modifyTable = this.dataQuery.executeQuery(this.context);
            IFieldsInfo fieldsInfo = modifyTable.getFieldsInfo();
            for (int i = 0; i < modifyTable.getCount(); ++i) {
                IDataRow dataRow = modifyTable.getItem(i);
                for (int j = 0; j < fieldsInfo.getFieldCount(); ++j) {
                    FieldDefine fieldDefine = fieldsInfo.getFieldDefine(j);
                    if (fieldDefine == null) continue;
                    dataRow.setValue(fieldDefine, null);
                }
            }
            return modifyTable.commitChanges(true);
        }
        catch (Exception e) {
            this.crudLogger.clearFail("\u56fa\u5b9a\u533a\u57df\u6570\u636e\u5220\u9664\u51fa\u9519");
            logger.error("\u533a\u57df\u6570\u636e\u6e05\u9664\u6267\u884c\u51fa\u9519", e);
            throw new CrudOperateException(4111);
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
                return ReturnRes.build(1101, "\u533a\u57df\u4e0b\u94fe\u63a5\u65e0\u6743\u9650,\u7ec8\u6b62\u6267\u884c\u6e05\u9664\u533a\u57df\u6570\u636e");
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
        SaveData saveData = saveInfo.getSaveData();
        List<String> links = saveData.getLinks();
        this.crudLogger = this.dataServiceLogWrapper.getCrudLogger(relation, saveInfo.getDimensionCombination());
        if (CollectionUtils.isEmpty(links)) {
            this.crudLogger.saveFail("\u56fa\u5b9a\u533a\u57df\u4e0b\u94fe\u63a5\u4e3a\u7a7a");
            return SaveReturnRes.build(1001, "\u56fa\u5b9a\u533a\u57df\u4e0b\u94fe\u63a5\u4e3a\u7a7a");
        }
        super.initDataQuery(saveInfo, relation);
        List<MetaData> metaData = relation.getMetaData(links.iterator());
        if (CollectionUtils.isEmpty(metaData)) {
            this.crudLogger.saveFail("\u56fa\u5b9a\u533a\u57df\u4e0b\u94fe\u63a5\u4e3a\u7a7a");
            return SaveReturnRes.build(1001, "\u56fa\u5b9a\u533a\u57df\u4e0b\u94fe\u63a5\u4e3a\u7a7a");
        }
        List<SaveRowData> rows = saveData.getAllRows();
        if (CollectionUtils.isEmpty(rows)) {
            this.crudLogger.saveSuccess("\u65e0\u6570\u636e\u9700\u8981\u4fdd\u5b58");
            return SaveReturnRes.ok("\u65e0\u6570\u636e\u9700\u8981\u4fdd\u5b58");
        }
        ReturnRes haveWrite = this.haveWrite(metaData);
        if (haveWrite != null) {
            return new SaveReturnRes(haveWrite);
        }
        super.addQueryCol(metaData);
        return this.doSave();
    }

    /*
     * WARNING - void declaration
     */
    private SaveReturnRes doSave() throws CrudOperateException {
        void var12_18;
        AbstractData value;
        IDataRow dataRow;
        IDataTable dataTable;
        SaveReturnRes res = new SaveReturnRes();
        ISaveInfo saveInfo = (ISaveInfo)this.regionInfo;
        SaveData saveData = saveInfo.getSaveData();
        List<String> links = saveData.getLinks();
        SaveRowData row = saveData.getAllRows().get(0);
        try {
            dataTable = this.dataQuery.executeQuery(this.context);
        }
        catch (Exception e) {
            this.crudLogger.saveFail("\u56fa\u5b9a\u533a\u57df\u6267\u884c\u66f4\u65b0\u6570\u636e\u96c6\u67e5\u8be2\u5931\u8d25");
            logger.error("\u56fa\u5b9a\u533a\u57df\u6267\u884c\u66f4\u65b0\u6570\u636e\u96c6\u67e5\u8be2\u5931\u8d25", e);
            throw new CrudOperateException(4101, "\u56fa\u5b9a\u533a\u57df\u6267\u884c\u66f4\u65b0\u6570\u636e\u96c6\u67e5\u8be2\u5931\u8d25");
        }
        int count = dataTable.getCount();
        if (count > 0) {
            dataRow = dataTable.getItem(0);
        } else {
            try {
                dataRow = dataTable.appendRow(this.dataQuery.getMasterKeys());
            }
            catch (IncorrectQueryException e) {
                this.crudLogger.saveFail("\u56fa\u5b9a\u533a\u57df\u6570\u636e\u4fdd\u5b58\u5931\u8d25");
                logger.error("\u56fa\u5b9a\u533a\u57df\u4fdd\u5b58\u51fa\u9519", e);
                throw new CrudOperateException(1401, "\u56fa\u5b9a\u533a\u57df\u4fdd\u5b58\u51fa\u9519", e);
            }
        }
        List<MetaData> allMetas = this.relation.initMetaData(null, false).getMetaFields();
        List<MetaData> saveMeta = this.relation.getMetaData(null);
        if (allMetas.size() != saveMeta.size()) {
            for (MetaData metaData : allMetas) {
                DataField dataField = metaData.getDataField();
                if (dataField == null || !StringUtils.hasLength(dataField.getDefaultValue())) continue;
                AbstractData value2 = dataRow.getValue((FieldDefine)((DataFieldDTO)dataField));
                if (!value2.isNull) continue;
                AbstractData defaultValue = this.evaluateDefaultValue(this.context, this.relation, this.dataQuery.getMasterKeys(), metaData);
                dataRow.setValue((FieldDefine)((DataFieldDTO)dataField), (Object)defaultValue);
            }
        }
        HashSet<String> linkSet = new HashSet<String>(links);
        for (MetaData metaData : saveMeta) {
            DataField dataField = metaData.getDataField();
            if (linkSet.contains(metaData.getLinkKey()) || metaData.isNullAble()) continue;
            value = dataRow.getValue((FieldDefine)((DataFieldDTO)dataField));
            if (!value.isNull) continue;
            res.setCode(1201);
            SaveResItem saveResItem = new SaveResItem();
            String title = dataField.getTitle();
            saveResItem.setMessage("\u6307\u6807:" + title + "\u4e0d\u80fd\u4e3a\u7a7a");
            saveResItem.setLinkKey(metaData.getLinkKey());
            res.getSaveResItems().add(saveResItem);
        }
        if (!res.getSaveResItems().isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (SaveResItem saveResItem : res.getSaveResItems()) {
                stringBuilder.append(saveResItem.getMessage()).append(";");
            }
            this.crudLogger.dataCheckFail(stringBuilder.toString());
            throw new CrudSaveException(1401, "\u56fa\u5b9a\u533a\u57df\u6570\u636e\u6821\u9a8c\u4e0d\u901a\u8fc7", res.getSaveResItems());
        }
        boolean bl = false;
        while (var12_18 < links.size()) {
            String string = links.get((int)var12_18);
            MetaData metaData = this.relation.getMetaDataByLink(string);
            if (metaData.isFieldLink()) {
                DataField dataField;
                value = null;
                if (row.getLinkValues().length > var12_18) {
                    value = row.getLinkValues()[var12_18];
                }
                if ((value == null || value.getAsNull()) && (dataField = metaData.getDataField()) != null && StringUtils.hasLength(dataField.getDefaultValue())) {
                    value = this.evaluateDefaultValue(this.context, this.relation, this.dataQuery.getMasterKeys(), metaData);
                }
                if (value != Const.UNMODIFIED_VALUE) {
                    dataRow.setValue(metaData.getIndex(), (Object)value);
                }
            }
            ++var12_18;
        }
        dataTable.setValidExpression(this.relation.getExpressions());
        try {
            dataTable.commitChanges(true);
            res.setMessage("\u4fdd\u5b58\u6210\u529f");
            this.crudLogger.saveSuccess("\u4fdd\u5b58\u56fa\u5b9a\u533a\u57df\u6570\u636e\u5b8c\u6210");
        }
        catch (Exception exception) {
            res.setCode(1401);
            res.getSaveResItems().addAll(this.exceptionErrors(exception));
            this.crudLogger.saveFail("\u56fa\u5b9a\u533a\u57df\u6570\u636e\u4fdd\u5b58\u5931\u8d25");
            throw new CrudSaveException(1401, "\u56fa\u5b9a\u533a\u57df\u6570\u636e\u4fdd\u5b58\u5931\u8d25", res.getSaveResItems());
        }
        return res;
    }
}

