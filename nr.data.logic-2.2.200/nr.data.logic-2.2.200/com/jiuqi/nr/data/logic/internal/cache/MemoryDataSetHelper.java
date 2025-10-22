/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.DataType
 *  com.jiuqi.bi.dataset.IDataRowFilter
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.query.result.ColumnInfo
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.data.logic.internal.cache;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.IDataRowFilter;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.query.result.ColumnInfo;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.logic.internal.cache.CheckResultCache;
import com.jiuqi.nr.data.logic.internal.cache.MemoryDataRowFilter;
import com.jiuqi.nr.data.logic.internal.entity.FmlCheckResultEntity;
import com.jiuqi.nr.data.logic.internal.util.CheckResultUtil;
import com.jiuqi.nr.data.logic.internal.util.CheckTableNameUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityDataLoader;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemoryDataSetHelper {
    private static final Logger logger = LoggerFactory.getLogger(MemoryDataSetHelper.class);
    @Autowired
    private CheckResultCache cache;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private EntityUtil entityUtil;

    public DataSet<ColumnInfo> writeData(List<FmlCheckResultEntity> fmlCheckResultEntities, FormSchemeDefine formScheme, String cacheKey, DimensionValueSet dimensionValueSet, String actionID) {
        assert (formScheme != null) : "\u62a5\u8868\u65b9\u6848\u4e0d\u5141\u8bb8\u4e3a\u7a7a";
        String dw = this.entityUtil.getContextMainDimId(formScheme.getDw());
        IEntityModel dwEntityModel = this.entityMetaService.getEntityModel(dw);
        TableModelDefine ckrTable = this.dataModelService.getTableModelDefineByCode(CheckTableNameUtil.getAllCKRTableName(formScheme.getFormSchemeCode()));
        List allColumns = this.dataModelService.getColumnModelDefinesByTable(ckrTable.getID());
        if (dwEntityModel != null) {
            for (IEntityAttribute showField : dwEntityModel.getShowFields()) {
                ColumnModelType columnType;
                if ("ORGCODE".equalsIgnoreCase(showField.getCode()) || ColumnModelType.STRING != (columnType = showField.getColumnType()) && ColumnModelType.BIGDECIMAL != columnType && ColumnModelType.BOOLEAN != columnType && ColumnModelType.INTEGER != columnType && ColumnModelType.DOUBLE != columnType && ColumnModelType.UUID != columnType) continue;
                allColumns.add(showField);
            }
        }
        Metadata<ColumnInfo> metaData = this.createResultMetaData(allColumns);
        EntityDataLoader entityDataLoader = this.entityUtil.getEntityDataLoader(dw, formScheme.getDateTime(), dimensionValueSet, null);
        MemoryDataSet<ColumnInfo> dataSet = this.getMemoryDataSet(fmlCheckResultEntities, allColumns, metaData, entityDataLoader);
        this.cache.saveResult(cacheKey, (DataSet<ColumnInfo>)dataSet, actionID);
        return dataSet;
    }

    private MemoryDataSet<ColumnInfo> getMemoryDataSet(List<FmlCheckResultEntity> fmlCheckResultEntities, List<ColumnModelDefine> allColumns, Metadata<ColumnInfo> metaData, EntityDataLoader entityDataLoader) {
        MemoryDataSet dataSet = new MemoryDataSet(null, metaData);
        for (FmlCheckResultEntity fmlCheckResultEntity : fmlCheckResultEntities) {
            DataRow row = dataSet.add();
            for (int i = 0; i < allColumns.size(); ++i) {
                ColumnModelDefine curColumn = allColumns.get(i);
                String entityDataKey = fmlCheckResultEntity.getDimMap().get("MDCODE");
                IEntityRow entityRow = entityDataLoader.getRowByEntityDataKey(entityDataKey);
                String curColumnCode = curColumn.getCode();
                this.fillDataRow(fmlCheckResultEntity, row, i, curColumnCode, entityRow);
            }
        }
        return dataSet;
    }

    private void fillDataRow(FmlCheckResultEntity fmlCheckResultEntity, DataRow row, int index, String curColumnCode, IEntityRow entityRow) {
        switch (curColumnCode) {
            case "ALLCKR_FORMSCHEMEKEY": 
            case "ALLCKR_FORMULADESC": 
            case "ALLCKR_FORMULA": 
            case "ALLCKR_UNITORDER": 
            case "VERSIONID": {
                break;
            }
            case "ALLCKR_RECID": {
                row.setValue(index, (Object)fmlCheckResultEntity.getRecId());
                break;
            }
            case "ALLCKR_FORMULASCHEMEKEY": {
                row.setValue(index, (Object)fmlCheckResultEntity.getFormulaSchemeKey());
                break;
            }
            case "ALLCKR_FORMKEY": {
                row.setValue(index, (Object)fmlCheckResultEntity.getFormKey());
                break;
            }
            case "ALLCKR_FORMULAID": {
                row.setValue(index, (Object)fmlCheckResultEntity.getFormulaKey());
                break;
            }
            case "ALLCKR_FORMULACODE": {
                row.setValue(index, (Object)fmlCheckResultEntity.getFormulaExpressionKey());
                break;
            }
            case "ALLCKR_GLOBCOL": {
                row.setValue(index, (Object)fmlCheckResultEntity.getGlobCol());
                break;
            }
            case "ALLCKR_GLOBROW": {
                row.setValue(index, (Object)fmlCheckResultEntity.getGlobRow());
                break;
            }
            case "ALLCKR_LEFT": {
                row.setValue(index, (Object)fmlCheckResultEntity.getLeft());
                break;
            }
            case "ALLCKR_RIGHT": {
                row.setValue(index, (Object)fmlCheckResultEntity.getRight());
                break;
            }
            case "ALLCKR_BALANCE": {
                row.setValue(index, (Object)fmlCheckResultEntity.getBalance());
                break;
            }
            case "ALLCKR_ASYNCTASKID": {
                row.setValue(index, (Object)fmlCheckResultEntity.getActionID());
                break;
            }
            case "ALLCKR_DIMSTR": {
                row.setValue(index, (Object)fmlCheckResultEntity.getDimStr());
                break;
            }
            case "ALLCKR_FORMORDER": {
                row.setValue(index, (Object)fmlCheckResultEntity.getFormOrder());
                break;
            }
            case "ALLCKR_FORMULAORDER": {
                row.setValue(index, (Object)fmlCheckResultEntity.getFormulaOrder());
                break;
            }
            case "ALLCKR_FORMULACHECKTYPE": {
                row.setValue(index, (Object)fmlCheckResultEntity.getFormulaCheckType());
                break;
            }
            case "ALLCKR_ERRORDESC": {
                row.setValue(index, (Object)fmlCheckResultEntity.getErrorDesc());
                break;
            }
            default: {
                if (fmlCheckResultEntity.getDimMap().containsKey(curColumnCode)) {
                    row.setValue(index, (Object)fmlCheckResultEntity.getDimMap().get(curColumnCode));
                    break;
                }
                if (entityRow == null) break;
                row.setValue(index, entityRow.getValue(curColumnCode).getAsObject());
            }
        }
    }

    public DataSet<ColumnInfo> readData(String cacheKey, MemoryDataRowFilter dataRowFilter) {
        DataSet<ColumnInfo> result = this.cache.getResult(cacheKey);
        if (result != null) {
            try {
                return result.filter((IDataRowFilter)dataRowFilter);
            }
            catch (DataSetException e) {
                logger.error("\u5ba1\u6838\u7ed3\u679c\u5185\u5b58\u8fc7\u6ee4\u5f02\u5e38\uff1a" + e.getMessage(), e);
            }
        }
        return result;
    }

    public DataSet<ColumnInfo> findAnyDataSet(List<String> cacheKeys) {
        for (String cacheKey : cacheKeys) {
            DataSet<ColumnInfo> result = this.cache.getResult(cacheKey);
            if (result == null) continue;
            return result;
        }
        return new MemoryDataSet();
    }

    public List<FmlCheckResultEntity> getCheckResultEntity(DataSet<ColumnInfo> dataSet, List<String> dimColNames) {
        ArrayList<FmlCheckResultEntity> checkResultEntities = new ArrayList<FmlCheckResultEntity>();
        if (dataSet != null) {
            Metadata metadata = dataSet.getMetadata();
            for (DataRow dataRow : dataSet) {
                FmlCheckResultEntity checkResultEntity = new FmlCheckResultEntity();
                block37: for (int i = 0; i < metadata.size(); ++i) {
                    Column column = metadata.getColumn(i);
                    String columnName = column.getName();
                    Object value = dataRow.getValue(i);
                    switch (columnName) {
                        case "ALLCKR_RECID": {
                            checkResultEntity.setRecId((String)value);
                            continue block37;
                        }
                        case "ALLCKR_FORMULASCHEMEKEY": {
                            checkResultEntity.setFormulaSchemeKey((String)value);
                            continue block37;
                        }
                        case "ALLCKR_FORMKEY": {
                            checkResultEntity.setFormKey((String)value);
                            continue block37;
                        }
                        case "ALLCKR_FORMULAID": {
                            checkResultEntity.setFormulaKey((String)value);
                            continue block37;
                        }
                        case "ALLCKR_FORMULACODE": {
                            checkResultEntity.setFormulaExpressionKey((String)value);
                            continue block37;
                        }
                        case "ALLCKR_GLOBCOL": {
                            checkResultEntity.setGlobCol((Integer)value);
                            continue block37;
                        }
                        case "ALLCKR_GLOBROW": {
                            checkResultEntity.setGlobRow((Integer)value);
                            continue block37;
                        }
                        case "ALLCKR_LEFT": {
                            checkResultEntity.setLeft((String)value);
                            continue block37;
                        }
                        case "ALLCKR_RIGHT": {
                            checkResultEntity.setRight((String)value);
                            continue block37;
                        }
                        case "ALLCKR_BALANCE": {
                            checkResultEntity.setBalance((String)value);
                            continue block37;
                        }
                        case "ALLCKR_ASYNCTASKID": {
                            checkResultEntity.setActionID((String)value);
                            continue block37;
                        }
                        case "ALLCKR_DIMSTR": {
                            checkResultEntity.setDimStr((String)value);
                            continue block37;
                        }
                        case "ALLCKR_FORMORDER": {
                            checkResultEntity.setFormOrder((String)value);
                            continue block37;
                        }
                        case "ALLCKR_FORMULAORDER": {
                            checkResultEntity.setFormulaOrder((String)value);
                            continue block37;
                        }
                        case "ALLCKR_FORMULACHECKTYPE": {
                            checkResultEntity.setFormulaCheckType((Integer)value);
                            continue block37;
                        }
                        case "ALLCKR_ERRORDESC": {
                            checkResultEntity.setErrorDesc((String)value);
                            continue block37;
                        }
                        default: {
                            if (!dimColNames.contains(columnName)) continue block37;
                            checkResultEntity.getDimMap().put(columnName, (String)value);
                        }
                    }
                }
                checkResultEntities.add(checkResultEntity);
            }
        }
        return checkResultEntities;
    }

    private Metadata<ColumnInfo> createResultMetaData(List<ColumnModelDefine> columnModelDefines) {
        Metadata metadata = new Metadata();
        for (ColumnModelDefine columnModelDefine : columnModelDefines) {
            String columnCode = columnModelDefine.getCode();
            Column column = "ALLCKR_ERRORDESC".equals(columnCode) ? new Column(columnCode, DataType.STRING.value()) : new Column(columnCode, columnModelDefine.getColumnType().getValue());
            metadata.addColumn(column);
        }
        return metadata;
    }

    public void clearData(String cacheKey) {
        this.cache.removeCacheObj(cacheKey);
    }

    public List<String> listCacheKey(DimensionCollection dimensionCollection, List<String> formulaSchemeKeys) {
        ArrayList<String> cacheKeyList = new ArrayList<String>();
        for (DimensionValueSet dimensionValueSet : dimensionCollection) {
            for (String formulaSchemeKey : formulaSchemeKeys) {
                cacheKeyList.add(CheckResultUtil.buildAllCheckCacheKey(formulaSchemeKey, new DimensionValueSet(dimensionValueSet)));
            }
        }
        return cacheKeyList;
    }
}

