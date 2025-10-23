/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.internal.convert.Convert
 *  com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao
 *  com.jiuqi.nr.datascheme.internal.dao.IDataTableDao
 *  com.jiuqi.nr.datascheme.internal.dao.IDataTableMapDao
 *  com.jiuqi.nr.datascheme.internal.dao.impl.DataFieldDeployInfoDaoImpl
 *  com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO
 *  com.jiuqi.nr.datascheme.internal.entity.DataTableDO
 *  com.jiuqi.nr.datascheme.internal.entity.DataTableMapDO
 *  com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO
 *  com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.query.datascheme.extend;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.internal.convert.Convert;
import com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableMapDao;
import com.jiuqi.nr.datascheme.internal.dao.impl.DataFieldDeployInfoDaoImpl;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nr.datascheme.internal.entity.DataTableDO;
import com.jiuqi.nr.datascheme.internal.entity.DataTableMapDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException;
import com.jiuqi.nr.query.datascheme.extend.DataTableAdaptItem;
import com.jiuqi.nr.query.datascheme.extend.DataTableInfo;
import com.jiuqi.nr.query.datascheme.extend.DimFieldInfo;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractDataTableAdapter {
    @Autowired
    protected IDesignDataSchemeService dataSchemeService;
    @Autowired
    protected IDataTableDao<DesignDataTableDO> dataTableDao;
    @Autowired
    protected IDataFieldDao<DesignDataFieldDO> dataFieldDao;
    @Autowired
    protected DataFieldDeployInfoDaoImpl deployInfoDao;
    @Autowired
    protected IDataTableMapDao tableMapDAO;
    @Autowired
    protected IEntityMetaService entityMetaService;

    public abstract void createDataTable(DataTableInfo var1) throws DataTableAdaptException;

    public void flushDataTable(String dataTableKey) throws DataTableAdaptException {
        DesignDataTable table = this.dataSchemeService.getDataTable(dataTableKey);
        Map dataTableFields = this.dataSchemeService.getDataFieldByTable(dataTableKey).stream().collect(Collectors.toMap(Basic::getKey, Function.identity(), (oldValue, newValue) -> newValue));
        List deployInfos = this.deployInfoDao.getByDataTableKey(dataTableKey);
        HashMap<String, DesignDataField> fieldNameMap = new HashMap<String, DesignDataField>();
        deployInfos.forEach(d -> {
            DesignDataField field = (DesignDataField)dataTableFields.get(d.getDataFieldKey());
            fieldNameMap.put(d.getFieldName(), field);
        });
        DataTableMapDO dataTableMap = this.tableMapDAO.get(dataTableKey);
        String tableName = dataTableMap.getSrcCode();
        String[] strs = dataTableMap.getSrcKey().split("\\.");
        String dataSourceKey = null;
        if (strs.length == 2) {
            dataSourceKey = strs[0];
        }
        ArrayList<DesignDataField> insertFields = new ArrayList<DesignDataField>();
        ArrayList<DesignDataField> updateFields = new ArrayList<DesignDataField>();
        ArrayList<DesignDataField> deleteFields = new ArrayList<DesignDataField>();
        HashMap<String, String> fieldNameByKey = new HashMap<String, String>();
        this.analysisDataFields(table, fieldNameMap, tableName, dataSourceKey, insertFields, updateFields, deleteFields, fieldNameByKey);
        ArrayList<DesignDataFieldDO> list = new ArrayList<DesignDataFieldDO>();
        for (DesignDataField field : updateFields) {
            list.add(Convert.iDf2Do((DesignDataField)field));
        }
        this.dataFieldDao.batchUpdate(list);
        if (insertFields.size() > 0) {
            list.clear();
            DataFieldDeployInfoDO[] newDeployInfos = new DataFieldDeployInfoDO[insertFields.size()];
            for (int i = 0; i < insertFields.size(); ++i) {
                DesignDataField field = (DesignDataField)insertFields.get(i);
                newDeployInfos[i] = this.createDeployInfo((DataField)field, (String)fieldNameByKey.get(field.getKey()), tableName);
                list.add(Convert.iDf2Do((DesignDataField)field));
            }
            this.dataFieldDao.batchInsert(list);
            this.deployInfoDao.insert(newDeployInfos);
        }
        if (deleteFields.size() > 0) {
            List deleteFieldKeys = deleteFields.stream().map(Basic::getKey).collect(Collectors.toList());
            String[] bizKeys = table.getBizKeys();
            if (bizKeys != null) {
                ArrayList<String> newBizkeys = new ArrayList<String>();
                for (String bizkey : bizKeys) {
                    if (deleteFieldKeys.contains(bizkey)) continue;
                    newBizkeys.add(bizkey);
                }
                table.setBizKeys((String[])newBizkeys.stream().toArray(String[]::new));
            }
            this.dataFieldDao.batchDelete(deleteFieldKeys);
            this.deployInfoDao.deleteByDataFields((String[])deleteFieldKeys.stream().toArray(String[]::new));
            table.setUpdateTime(Instant.now());
            DesignDataTableDO designDataTableDO = Convert.iDt2Do((DesignDataTable)table);
            this.dataTableDao.update((DataTableDO)designDataTableDO);
        }
    }

    protected abstract void analysisDataFields(DesignDataTable var1, Map<String, DesignDataField> var2, String var3, String var4, List<DesignDataField> var5, List<DesignDataField> var6, List<DesignDataField> var7, Map<String, String> var8) throws DataTableAdaptException;

    public List<DimFieldInfo> getAllStrFields(String schemeKey, String srcTableCode) throws DataTableAdaptException {
        return this.getAllStrFields(schemeKey, null, srcTableCode);
    }

    public List<DimFieldInfo> getAllStrFields(String schemeKey, String srcTableKey, String srcTableCode) throws DataTableAdaptException {
        return null;
    }

    public abstract Date getDataTableUpdateTime(String var1) throws DataTableAdaptException;

    protected void saveDataTable(DataTableAdaptItem<?> dataTableAdaptItem) {
        DesignDataTable dataTable = dataTableAdaptItem.getDataTable();
        DesignDataTable exsitTable = (DesignDataTable)this.dataTableDao.getByCode(dataTable.getCode());
        if (exsitTable != null) {
            this.dataTableDao.delete(exsitTable.getKey());
        }
        DesignDataTableDO designDataTableDO = Convert.iDt2Do((DesignDataTable)dataTableAdaptItem.getDataTable());
        this.dataTableDao.insert((DataTableDO)designDataTableDO);
        ArrayList<DesignDataFieldDO> list = new ArrayList<DesignDataFieldDO>();
        for (DesignDataField field : dataTableAdaptItem.getFields()) {
            list.add(Convert.iDf2Do((DesignDataField)field));
        }
        this.dataFieldDao.batchInsert(list);
        this.deployInfoDao.insert(dataTableAdaptItem.getFieldDeployInfos().toArray());
        this.tableMapDAO.insert(dataTableAdaptItem.getDataTableMap());
    }

    protected Map<String, DesignDataDimension> getDimMap(String schemeKey) {
        List dims = this.dataSchemeService.getDataSchemeDimension(schemeKey);
        HashMap<String, DesignDataDimension> dimMap = new HashMap<String, DesignDataDimension>();
        dims.forEach(d -> {
            if (d.getDimensionType() == DimensionType.UNIT) {
                dimMap.put("MDCODE", (DesignDataDimension)d);
            } else if (d.getDimensionType() == DimensionType.PERIOD) {
                dimMap.put("DATATIME", (DesignDataDimension)d);
            }
            if (StringUtils.isNotEmpty((String)d.getDimKey())) {
                dimMap.put(d.getDimKey(), (DesignDataDimension)d);
            }
        });
        return dimMap;
    }

    protected void matchDimByEntityCode(Map<String, DesignDataDimension> dimMap, DimFieldInfo dimFieldInfo, String tableCode) {
        DesignDataDimension dim;
        IEntityDefine entity = this.entityMetaService.queryEntityByCode(tableCode);
        if (entity != null && (dim = dimMap.get(entity.getId())) != null) {
            dimFieldInfo.setMatchedDim((DataDimension)dim);
        }
    }

    protected void setDataFieldRef(List<DesignDataDimension> dims, DesignDataField dataField) {
        if (dataField.getDataFieldType() == DataFieldType.STRING) {
            IEntityDefine entityDefine;
            if (dataField.getCode().equals("MDCODE")) {
                for (DesignDataDimension dim : dims) {
                    if (dim.getDimensionType() != DimensionType.UNIT) continue;
                    dataField.setRefDataEntityKey(dim.getDimKey());
                    break;
                }
            } else if (dataField.getCode().equals("DATATIME")) {
                for (DesignDataDimension dim : dims) {
                    if (dim.getDimensionType() != DimensionType.PERIOD) continue;
                    dataField.setRefDataEntityKey(dim.getDimKey());
                    break;
                }
            } else if (dataField.getCode().startsWith("MD_") && (entityDefine = this.entityMetaService.queryEntityByCode(dataField.getCode())) != null) {
                dataField.setRefDataEntityKey(entityDefine.getId());
            }
        }
    }

    protected DataFieldDeployInfoDO createDeployInfo(DataField dataField, String logicFieldName, String srcTableName) {
        DataFieldDeployInfoDO deployInfo = new DataFieldDeployInfoDO();
        deployInfo.setDataSchemeKey(dataField.getDataSchemeKey());
        deployInfo.setDataTableKey(dataField.getDataTableKey());
        deployInfo.setSourceTableKey(dataField.getDataTableKey());
        deployInfo.setDataFieldKey(dataField.getKey());
        deployInfo.setTableModelKey(dataField.getDataTableKey());
        deployInfo.setColumnModelKey(dataField.getKey());
        deployInfo.setFieldName(logicFieldName != null ? logicFieldName : dataField.getCode());
        deployInfo.setTableName(srcTableName);
        deployInfo.setUpdateTime(Instant.now());
        return deployInfo;
    }
}

