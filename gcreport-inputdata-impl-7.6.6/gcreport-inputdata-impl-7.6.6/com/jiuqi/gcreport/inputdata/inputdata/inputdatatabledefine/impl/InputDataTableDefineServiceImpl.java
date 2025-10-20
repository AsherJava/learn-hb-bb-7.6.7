/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.consolidatedsystem.dao.InputDataSchemeDao
 *  com.jiuqi.gcreport.consolidatedsystem.entity.InputDataSchemeEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.InputDataSchemeService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndexs
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.base.provider.util.EntityFieldInfoUtils
 *  com.jiuqi.gcreport.definition.impl.basic.base.sql.EntSqlTool
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.event.EntTableDefineInitAfterEvent
 *  com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.common.TableIndexType
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.IndexModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  org.apache.commons.lang3.RandomStringUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.inputdata.inputdatatabledefine.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.consolidatedsystem.dao.InputDataSchemeDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.InputDataSchemeEO;
import com.jiuqi.gcreport.consolidatedsystem.service.InputDataSchemeService;
import com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBIndexs;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.base.provider.util.EntityFieldInfoUtils;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.EntSqlTool;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.init.table.event.EntTableDefineInitAfterEvent;
import com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.inputdatatabledefine.InputDataTableDefineService;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.common.TableIndexType;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableGatherType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.IndexModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

@Service
public class InputDataTableDefineServiceImpl
implements InputDataTableDefineService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InputDataTableDefineServiceImpl.class);
    private final String RANDOMSRT = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String GC_INPUTDATASCHEME_SQL = "SELECT  * FROM GC_INPUTDATASCHEME";
    private InputDataSchemeDao inputDataSchemeDao;
    private IDesignDataSchemeService iDesignDataSchemeService;
    private DesignDataModelService designDataModelService;
    @Autowired
    @Qualifier(value="RuntimeDataSchemeNoCacheServiceImpl-NO_CACHE")
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    private IDataSchemeDeployService iDataSchemeDeployService;
    private DataModelDeployService dataModelDeployService;
    private DataModelService dataModelService;

    public InputDataTableDefineServiceImpl(InputDataSchemeDao inputDataSchemeDao, IDesignDataSchemeService iDesignDataSchemeService, DesignDataModelService designDataModelService, IDataSchemeDeployService iDataSchemeDeployService, DataModelDeployService dataModelDeployService, DataModelService dataModelService) {
        this.inputDataSchemeDao = inputDataSchemeDao;
        this.iDesignDataSchemeService = iDesignDataSchemeService;
        this.designDataModelService = designDataModelService;
        this.iDataSchemeDeployService = iDataSchemeDeployService;
        this.dataModelDeployService = dataModelDeployService;
        this.dataModelService = dataModelService;
    }

    @Override
    public void createInputDataTableByDataSchemeKey(String dataSchemeKey) {
        if (StringUtils.isEmpty((String)dataSchemeKey)) {
            LOGGER.error("\u6570\u636e\u65b9\u6848\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
            return;
        }
        DataScheme dataScheme = this.iRuntimeDataSchemeService.getDataScheme(dataSchemeKey);
        if (dataScheme == null) {
            LOGGER.error("\u83b7\u53d6\u6570\u636e\u65b9\u6848\u4e3a\u7a7a\uff0cdataSchemeKey\uff1a" + dataSchemeKey);
            return;
        }
        InputDataSchemeEO inputDataScheme = this.inputDataSchemeDao.getInputDataSchemeByDataSchemeKey(dataSchemeKey);
        if (inputDataScheme != null) {
            return;
        }
        this.createInputDataTableByDataScheme(dataScheme);
    }

    @EventListener
    public void onApplicationEvent(EntTableDefineInitAfterEvent event) {
        if (null == event || event.getEntTableDefineInitAfterInfo() == null) {
            return;
        }
        String tableName = event.getEntTableDefineInitAfterInfo().getTableName();
        if (!"GC_INPUTDATATEMPLATE".equalsIgnoreCase(tableName)) {
            return;
        }
        this.handleInputDataFieldAndIndex();
    }

    @Override
    public List<DBIndex> listNeedAddInputDataIndex(DesignDataTable dataTable) {
        List<DBIndex> dbIndexList = this.listIndexesByEntityType(InputDataEO.class);
        return this.listNeedAddInputDataIndex(dataTable, dbIndexList);
    }

    public void handleInputDataFieldAndIndex() {
        List inputDataSchemes = EntNativeSqlDefaultDao.newInstance((String)"GC_INPUTDATASCHEME", InputDataSchemeEO.class).selectEntity("SELECT  * FROM GC_INPUTDATASCHEME", new Object[0]);
        if (CollectionUtils.isEmpty(inputDataSchemes)) {
            return;
        }
        List<DBIndex> dbIndexList = this.listIndexesByEntityType(InputDataEO.class);
        for (InputDataSchemeEO inputDataScheme : inputDataSchemes) {
            DataScheme dataScheme = this.iRuntimeDataSchemeService.getDataScheme(inputDataScheme.getDataSchemeKey());
            if (dataScheme == null) {
                LOGGER.error("\u6570\u636e\u65b9\u6848\u4e0d\u5b58\u5728\uff0c DataSchemeKey\uff1a" + inputDataScheme.getDataSchemeKey());
                continue;
            }
            DesignDataTable dataTable = this.iDesignDataSchemeService.getDataTable(inputDataScheme.getTableKey());
            if (dataTable == null) {
                LOGGER.error("\u6570\u636e\u65b9\u6848\u4e2d\u5185\u90e8\u8868\u4e0d\u5b58\u5728\uff0c TableKey\uff1a" + inputDataScheme.getTableKey());
                continue;
            }
            boolean isDeployFlag = ((InputDataTableDefineServiceImpl)SpringContextUtils.getBean(InputDataTableDefineServiceImpl.class)).addInputDataField(inputDataScheme, dataTable, dataScheme);
            if (isDeployFlag) {
                this.deployDataTable(dataTable);
            }
            ((InputDataTableDefineServiceImpl)SpringContextUtils.getBean(InputDataTableDefineServiceImpl.class)).addInputDataIndex(dataTable, dbIndexList);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public boolean addInputDataField(InputDataSchemeEO inputDataScheme, DesignDataTable dataTable, DataScheme dataScheme) {
        List dataFieldByTable = this.iDesignDataSchemeService.getDataFieldByTable(inputDataScheme.getTableKey());
        if (CollectionUtils.isEmpty(dataFieldByTable)) {
            LOGGER.error("\u5185\u90e8\u8868\u5b57\u6bb5\u4fe1\u606f\u4e3a\u7a7a\uff0c tableKey\uff1a" + inputDataScheme.getTableKey());
            return false;
        }
        Set<String> existFieldCodeSet = dataFieldByTable.stream().map(Basic::getCode).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(existFieldCodeSet)) {
            LOGGER.error("\u5185\u90e8\u8868\u5b57\u6bb5code\u4e3a\u7a7a\uff0c tableKey\uff1a" + inputDataScheme.getTableKey());
            return false;
        }
        ArrayList<DesignDataField> updateDataFields = new ArrayList<DesignDataField>();
        List<DesignDataField> insertDataFields = this.listDataField(dataScheme, dataTable, existFieldCodeSet, updateDataFields);
        this.addDesignDataFields(insertDataFields);
        this.updateDesignDataFields(updateDataFields);
        return !CollectionUtils.isEmpty(insertDataFields) || !CollectionUtils.isEmpty(updateDataFields);
    }

    private void deployDataTable(DesignDataTable dataTable) {
        try {
            this.iDataSchemeDeployService.deployDataTable(dataTable.getKey(), true);
        }
        catch (JQException e) {
            LOGGER.error("\u53d1\u5e03\u6570\u636e\u8868" + dataTable.getCode() + "\u5f02\u5e38", e);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void addInputDataIndex(DesignDataTable dataTable, List<DBIndex> dbIndexList) {
        List<DBIndex> inputDataDBIndexes = this.listNeedAddInputDataIndex(dataTable, dbIndexList);
        this.addInputDataIndexes(dataTable, inputDataDBIndexes);
    }

    private void createInputDataTableByDataScheme(DataScheme dataScheme) {
        DesignDataTable dataTable = this.getInputDataDesignDataTableBySchemeKey(dataScheme.getKey());
        if (null != dataTable) {
            LOGGER.info("\u6570\u636e\u65b9\u6848\u4e2d\u5b58\u5728\u5185\u90e8\u8868\uff0c\u6570\u636e\u65b9\u6848\uff1a" + dataScheme.getTitle() + " \uff0c\u5185\u90e8\u8868\uff1a" + dataTable.getCode());
        }
        if (dataTable == null) {
            List dataDimensions = this.iRuntimeDataSchemeService.getDataSchemeDimension(dataScheme.getKey());
            String periodDimKey = this.getPeriodDimKey(dataDimensions);
            dataTable = this.createDataTable(dataScheme, periodDimKey);
            List<DesignDataField> insertDataFields = this.listDataField(dataScheme, dataTable, null, null);
            this.addDesignDataFields(insertDataFields);
            this.deployDataTable(dataTable);
            this.addInputDataIndexes(dataTable);
        }
        this.saveInputDataAndDataScheme(dataScheme, dataTable);
    }

    private void addInputDataIndexes(DesignDataTable dataTable) {
        List<DBIndex> dbIndexList = this.listIndexesByEntityType(InputDataEO.class);
        List<DBIndex> inputDataDBIndexes = this.listNeedAddInputDataIndex(dataTable, dbIndexList);
        this.addInputDataIndexes(dataTable, inputDataDBIndexes);
    }

    private String getPeriodDimKey(List<DataDimension> dataDimensions) {
        String dimKey = dataDimensions.stream().filter(dataDimension -> dataDimension.getDimensionType().equals((Object)DimensionType.PERIOD)).map(DataDimension::getDimKey).findFirst().orElse(null);
        return dimKey;
    }

    private DesignDataTable createDataTable(DataScheme dataScheme, String periodDimKey) {
        DesignDataTable dataTable = this.iDesignDataSchemeService.initDataTable();
        dataTable.setDataSchemeKey(dataScheme.getKey());
        dataTable.setCode(this.getRandomString(dataScheme.getPrefix(), periodDimKey));
        dataTable.setDataTableType(DataTableType.DETAIL);
        dataTable.setDataTableGatherType(DataTableGatherType.NONE);
        dataTable.setRepeatCode(Boolean.valueOf(true));
        dataTable.setTitle("\u5185\u90e8\u5f55\u5165\u8868");
        dataTable.setDesc(dataScheme.getTitle() + "\u5185\u90e8\u5f55\u5165\u8868");
        this.iDesignDataSchemeService.insertDataTable(dataTable);
        return dataTable;
    }

    private DesignDataField getInputDataField(DBColumn column, DataScheme dataScheme, DesignDataTable dataTable) {
        String fieldName = column.nameInDB().toUpperCase();
        if ("FLOATORDER".equals(fieldName) || "BIZKEYORDER".equals(fieldName) || "MDCODE".equals(fieldName) || "DATATIME".equals(fieldName) || "MD_CURRENCY".equals(fieldName) || "MD_GCORGTYPE".equals(fieldName)) {
            return null;
        }
        DesignDataField designDataField = this.iDesignDataSchemeService.initDataField();
        designDataField.setDataSchemeKey(dataScheme.getKey());
        designDataField.setDataTableKey(dataTable.getKey());
        designDataField.setCode(fieldName);
        designDataField.setTitle(column.title());
        ColumnModelType columnModelType = ColumnModelType.forValue((int)column.dbType().getBiType());
        DataFieldType dataFieldType = DataFieldType.valueOf((int)columnModelType.getValue());
        designDataField.setDataFieldType(dataFieldType);
        if (DataFieldType.BIGDECIMAL.equals((Object)designDataField.getDataFieldType())) {
            designDataField.setPrecision(Integer.valueOf(column.precision()));
            designDataField.setDataFieldGatherType(DataFieldGatherType.SUM);
        } else {
            designDataField.setPrecision(Integer.valueOf(column.length()));
        }
        designDataField.setDecimal(Integer.valueOf(column.scale()));
        if ("ID".equals(fieldName)) {
            designDataField.setNullable(Boolean.valueOf(true));
        } else {
            designDataField.setNullable(Boolean.valueOf(!column.isRequired()));
        }
        designDataField.setDataFieldKind(DataFieldKind.FIELD);
        if (!StringUtils.isEmpty((String)column.refTabField())) {
            designDataField.setRefDataEntityKey(column.refTabField());
        }
        designDataField.setDesc("\u81ea\u52a8\u751f\u6210\uff0c\u52ff\u52a8");
        designDataField.setOrder(OrderGenerator.newOrder());
        return designDataField;
    }

    private List<DesignDataField> listDataField(DataScheme dataScheme, DesignDataTable dataTable, Set<String> existFieldCodeSet, List<DesignDataField> updateDataFields) {
        ArrayList<DesignDataField> insertDataFields = new ArrayList<DesignDataField>();
        List propertyDescriptors = EntityFieldInfoUtils.getFields(InputDataEO.class);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            DesignDataField designDataField;
            DBColumn column = this.getAnnotation(ReflectionUtils.findField(InputDataEO.class, propertyDescriptor.getName()), propertyDescriptor);
            if (column == null || StringUtils.isEmpty((String)column.nameInDB())) continue;
            if (!CollectionUtils.isEmpty(existFieldCodeSet) && existFieldCodeSet.contains(column.nameInDB().toUpperCase())) {
                if (null == updateDataFields) continue;
                designDataField = this.iDesignDataSchemeService.getDataFieldByTableKeyAndCode(dataTable.getKey(), column.nameInDB().toUpperCase());
                if (StringUtils.isEmpty((String)column.refTabField()) || !StringUtils.isEmpty((String)designDataField.getRefDataEntityKey())) continue;
                designDataField.setRefDataEntityKey(column.refTabField());
                designDataField.setUpdateTime(Instant.now());
                updateDataFields.add(designDataField);
                continue;
            }
            designDataField = this.getInputDataField(column, dataScheme, dataTable);
            if (null == designDataField) continue;
            LOGGER.info("\u5411\u5185\u90e8\u8868\u3010{}\u3011\u6dfb\u52a0\u65b0\u589e\u56fa\u5b9a\u5b57\u6bb5\u3010{}\u3011", (Object)dataTable.getCode(), (Object)column.nameInDB());
            insertDataFields.add(designDataField);
        }
        return insertDataFields;
    }

    private List<DBIndex> listIndexesByEntityType(Class<? extends BaseEntity> entityType) {
        DBIndexs indexes;
        DBIndex index;
        DBTable entityTableAnn = entityType.getAnnotation(DBTable.class);
        ArrayList<DBIndex> dbIndexList = new ArrayList<DBIndex>();
        if (null != entityTableAnn) {
            DBIndex[] dbIndexArr;
            for (DBIndex dbIndex : dbIndexArr = entityTableAnn.indexs()) {
                dbIndexList.add(dbIndex);
            }
        }
        if ((index = entityType.getAnnotation(DBIndex.class)) != null) {
            dbIndexList.add(index);
        }
        if ((indexes = entityType.getAnnotation(DBIndexs.class)) != null && indexes.value().length > 0) {
            for (DBIndex dbIndex : indexes.value()) {
                dbIndexList.add(dbIndex);
            }
        }
        return dbIndexList;
    }

    private void saveInputDataAndDataScheme(DataScheme dataScheme, DesignDataTable dataTable) {
        InputDataSchemeVO inputDataAndDataScheme = new InputDataSchemeVO();
        inputDataAndDataScheme.setDataSchemeKey(dataScheme.getKey());
        inputDataAndDataScheme.setTableKey(dataTable.getKey());
        inputDataAndDataScheme.setId(UUIDUtils.newUUIDStr());
        inputDataAndDataScheme.setTableCode(dataTable.getCode());
        ((InputDataSchemeService)SpringContextUtils.getBean(InputDataSchemeService.class)).createInputDataScheme(inputDataAndDataScheme);
    }

    private String getRandomString(String prefix, String periodDimKey) {
        String tableCodeNew;
        String inputDataName = "GC_INPUTDATA";
        if (!StringUtils.isEmpty((String)prefix)) {
            inputDataName = prefix + "_" + inputDataName;
        }
        List tableNames = this.inputDataSchemeDao.loadAll().stream().map(InputDataSchemeEO::getTableCode).collect(Collectors.toList());
        while (tableNames.contains(tableCodeNew = inputDataName + "_" + RandomStringUtils.random((int)2, (String)"ABCDEFGHIJKLMNOPQRSTUVWXYZ"))) {
        }
        return tableCodeNew + periodDimKey;
    }

    private DBColumn getAnnotation(Field field, PropertyDescriptor propertyDescriptor) {
        DBColumn t = null;
        if (field != null && (t = field.getAnnotation(DBColumn.class)) == null) {
            t = propertyDescriptor.getReadMethod().getAnnotation(DBColumn.class);
        }
        return t;
    }

    private void addDesignDataFields(List<DesignDataField> insertDataFields) {
        if (CollectionUtils.isEmpty(insertDataFields)) {
            return;
        }
        this.iDesignDataSchemeService.insertDataFields(insertDataFields);
    }

    private void updateDesignDataFields(List<DesignDataField> updateDataFields) {
        if (CollectionUtils.isEmpty(updateDataFields)) {
            return;
        }
        this.iDesignDataSchemeService.updateDataFields(updateDataFields);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void addInputDataIndexes(DesignDataTable dataTable, List<DBIndex> dbIndexList) {
        TableModelDefine inputDataModelDefine = this.getInputDataTableModel(dataTable.getKey());
        if (null == inputDataModelDefine) {
            LOGGER.error("\u5185\u90e8\u8868\u8868\u5b9a\u4e49\u4e3a\u7a7a\uff0c tableCode:" + dataTable.getCode());
            return;
        }
        List columnModelDefinesByTable = this.dataModelService.getColumnModelDefinesByTable(inputDataModelDefine.getID());
        if (CollectionUtils.isEmpty(columnModelDefinesByTable)) {
            return;
        }
        String tableModelId = inputDataModelDefine.getID();
        String tableName = inputDataModelDefine.getName();
        Map<String, String> fieldGroupByCode = columnModelDefinesByTable.stream().collect(Collectors.toMap(ColumnModelDefine::getName, IModelDefineItem::getID));
        for (DBIndex dbIndex : dbIndexList) {
            String[] columnsFields = dbIndex.columnsFields();
            ArrayList<String> fields = new ArrayList<String>();
            for (String fieldName : columnsFields) {
                String columnKey;
                if (StringUtils.isEmpty((String)fieldName) || StringUtils.isEmpty((String)(columnKey = fieldGroupByCode.get(fieldName.toUpperCase())))) continue;
                fields.add(columnKey);
            }
            this.designDataModelService.addIndexToTable(tableModelId, fields.toArray(new String[0]), dbIndex.name().toUpperCase().replace("GC_INPUTDATATEMPLATE", tableName), IndexModelType.forValue((int)dbIndex.type().getValue()));
        }
        boolean isUpdateIdIndex = this.canUpdateInputDataIndexIdType(tableModelId, fieldGroupByCode.get("ID"));
        try {
            if (isUpdateIdIndex || !CollectionUtils.isEmpty(dbIndexList)) {
                this.dataModelDeployService.deployTable(tableModelId);
            }
        }
        catch (Exception e) {
            LOGGER.error("\u8868" + tableName + "\u589e\u52a0\u7d22\u5f15\u5f02\u5e38", e);
        }
    }

    private boolean canUpdateInputDataIndexIdType(String tableModelId, String idFieldKey) {
        List inputDataIndexes = this.designDataModelService.getIndexsByTable(tableModelId);
        if (CollectionUtils.isEmpty(inputDataIndexes) || StringUtils.isEmpty((String)idFieldKey)) {
            return false;
        }
        Optional<DesignIndexModelDefine> idIndexModelDefineOptional = inputDataIndexes.stream().filter(index -> idFieldKey.equals(index.getFieldIDs()) && !TableIndexType.TABLE_INDEX_UNIQUE.equals((Object)index.getType())).findFirst();
        if (idIndexModelDefineOptional.isPresent()) {
            DesignIndexModelDefine designIndexModelDefine = idIndexModelDefineOptional.get();
            designIndexModelDefine.setType(IndexModelType.UNIQUE);
            this.designDataModelService.updateIndexModelDefine(designIndexModelDefine);
            return true;
        }
        return false;
    }

    private List<DBIndex> listNeedAddInputDataIndex(DesignDataTable dataTable, List<DBIndex> dbIndexList) {
        TableModelDefine inputDataModelDefine = this.getInputDataTableModel(dataTable.getKey());
        if (null == inputDataModelDefine) {
            LOGGER.error("\u65b0\u589e\u7684\u7d22\u5f15\u4fe1\u606f(\u8df3\u8fc7)\uff1a\u5185\u90e8\u8868\u8868\u5b9a\u4e49\u4e3a\u7a7a\uff0c tableCode:" + dataTable.getCode());
            return null;
        }
        List columnModelDefines = this.dataModelService.getColumnModelDefinesByTable(inputDataModelDefine.getID());
        if (CollectionUtils.isEmpty(columnModelDefines)) {
            LOGGER.error("\u65b0\u589e\u7684\u7d22\u5f15\u4fe1\u606f(\u8df3\u8fc7)\uff1a\u83b7\u53d6\u5185\u6807\u6307\u6807\u4fe1\u606f\u4e3a\u7a7a\uff0c tableCode:" + dataTable.getCode());
            return null;
        }
        List inputDataIndexes = this.designDataModelService.getIndexsByTable(inputDataModelDefine.getID());
        Map<String, String> inputDataColumnCodeGroupById = columnModelDefines.stream().collect(Collectors.toMap(IModelDefineItem::getID, ColumnModelDefine::getName));
        Map<String, DesignIndexModelDefine> indexModelDefineGroupByFieldCodes = this.getIndexModelDefineGroupByFieldCodes(inputDataIndexes, inputDataColumnCodeGroupById);
        List<DBIndex> inputDataDBIndexes = this.compareInputDataIndexes(dbIndexList, indexModelDefineGroupByFieldCodes.keySet());
        return inputDataDBIndexes;
    }

    private Map<String, DesignIndexModelDefine> getIndexModelDefineGroupByFieldCodes(List<DesignIndexModelDefine> inputDataTemplateIndexes, Map<String, String> inputDataColumnCodeGroupById) {
        if (CollectionUtils.isEmpty(inputDataTemplateIndexes)) {
            return Collections.emptyMap();
        }
        HashMap<String, DesignIndexModelDefine> designIndexModelDefineGroupByFieldCodes = new HashMap<String, DesignIndexModelDefine>(inputDataColumnCodeGroupById.size());
        for (DesignIndexModelDefine indexModelDefine : inputDataTemplateIndexes) {
            String indexFieldIds = indexModelDefine.getFieldIDs();
            if (StringUtils.isEmpty((String)indexFieldIds)) continue;
            String[] indexFieldIdArrays = indexFieldIds.split(";");
            StringBuilder codes = new StringBuilder();
            for (String fieldId : indexFieldIdArrays) {
                String code = inputDataColumnCodeGroupById.get(fieldId);
                if (StringUtils.isEmpty((String)code)) continue;
                codes.append(code).append(";");
            }
            if (codes.length() > 0) {
                codes.delete(codes.length() - 1, codes.length());
            }
            designIndexModelDefineGroupByFieldCodes.put(codes.toString(), indexModelDefine);
        }
        return designIndexModelDefineGroupByFieldCodes;
    }

    private List<DBIndex> compareInputDataIndexes(List<DBIndex> dbIndexList, Set<String> inputDataIndexCodes) {
        ArrayList<DBIndex> inputDataIndexes = new ArrayList<DBIndex>();
        for (DBIndex dbIndex : dbIndexList) {
            String[] templateIndexCodeArray = new String[dbIndex.columnsFields().length];
            for (int i = 0; i < dbIndex.columnsFields().length; ++i) {
                String fieldCode;
                templateIndexCodeArray[i] = fieldCode = dbIndex.columnsFields()[i].toUpperCase();
            }
            if (!this.canAddIndex(templateIndexCodeArray, inputDataIndexCodes)) continue;
            inputDataIndexes.add(dbIndex);
        }
        return inputDataIndexes;
    }

    private boolean canAddIndex(String[] templateIndexCodeArray, Set<String> inputDataIndexCodes) {
        for (String indexCodes : inputDataIndexCodes) {
            String[] indexCodeArray = indexCodes.toUpperCase().split(";");
            if (!InputDataTableDefineServiceImpl.equalArray(templateIndexCodeArray, indexCodeArray)) continue;
            return false;
        }
        return true;
    }

    private static boolean equalArray(String[] templateIndexCodes, String[] inputDataIndexCodes) {
        if (templateIndexCodes == null || inputDataIndexCodes == null) {
            return false;
        }
        Arrays.sort(templateIndexCodes);
        Arrays.sort(inputDataIndexCodes);
        return Arrays.equals(templateIndexCodes, inputDataIndexCodes);
    }

    private DesignDataTable getInputDataDesignDataTableBySchemeKey(String schemeKey) {
        String sql = "SELECT DT_KEY FROM  NR_DATASCHEME_TABLE_DES ndtd  WHERE ndtd.DT_CODE  LIKE '%GC_INPUTDATA%' AND ndtd.DT_DS_KEY =?";
        FEntSqlTemplate template = (FEntSqlTemplate)SpringContextUtils.getBean(FEntSqlTemplate.class);
        String designDataTableKey = (String)template.query(EntSqlTool.newDqlInstance((String)sql, Collections.singletonList(schemeKey), rs -> (String)rs.getObject(1, String.class)));
        if (StringUtils.isEmpty((String)designDataTableKey)) {
            return null;
        }
        DesignDataTable designDataTable = this.iDesignDataSchemeService.getDataTable(designDataTableKey);
        return designDataTable;
    }

    private TableModelDefine getInputDataTableModel(String dataTableKey) {
        List dataFieldDeployInfos = this.iRuntimeDataSchemeService.getDeployInfoByDataTableKey(dataTableKey);
        if (CollectionUtils.isEmpty(dataFieldDeployInfos)) {
            LOGGER.error("\u6839\u636e\u6570\u636e\u8868dataTableKey\u83b7\u53d6\u53d1\u5e03\u4fe1\u606f\u4e3a\u7a7a\uff0cdataTableKey:" + dataTableKey);
            return null;
        }
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineById(((DataFieldDeployInfo)dataFieldDeployInfos.get(0)).getTableModelKey());
        if (null == tableModel || StringUtils.isEmpty((String)tableModel.getName())) {
            LOGGER.error("\u83b7\u53d6nvwa\u5185\u90e8\u8868\u4fe1\u606f\u4e3a\u7a7a\uff0cTableId:" + ((DataFieldDeployInfo)dataFieldDeployInfos.get(0)).getTableModelKey());
            return null;
        }
        return tableModel;
    }
}

