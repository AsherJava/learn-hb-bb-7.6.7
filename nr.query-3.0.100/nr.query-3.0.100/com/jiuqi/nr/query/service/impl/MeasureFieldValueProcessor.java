/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.intf.IFieldValueProcessor
 *  com.jiuqi.np.dataengine.intf.impl.ReloadTreeInfo
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.engine.setting.IFieldsInfo
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  io.netty.util.internal.StringUtil
 */
package com.jiuqi.nr.query.service.impl;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.intf.IFieldValueProcessor;
import com.jiuqi.np.dataengine.intf.impl.ReloadTreeInfo;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.engine.setting.IFieldsInfo;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.query.service.impl.MeasureData;
import com.jiuqi.nr.query.service.impl.MeasureViewData;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import io.netty.util.internal.StringUtil;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MeasureFieldValueProcessor
implements IFieldValueProcessor {
    private static final Logger logger = LoggerFactory.getLogger(MeasureFieldValueProcessor.class);
    IDataDefinitionRuntimeController runtimeController;
    IRunTimeViewController viewController;
    IEntityMetaService iEntityMetaService;
    IEntityViewRunTimeController entityViewRunTimeController;
    IEntityDataService entityDataService;
    private double measureRate = 1.0;
    private MeasureViewData measureViewData;
    private String formMeasureCode;
    private Integer decimal = null;
    private DataModelService dataModelService;
    private INvwaDataAccessProvider iNvwaDataAccessProvider;

    public MeasureFieldValueProcessor(String formKey, String viewKey, String selectMeasureCode, DimensionValueSet masterKeys, ReloadTreeInfo reloadTreeInfo, String decimalStr) {
        String[] measureStrs;
        if (!StringUtil.isNullOrEmpty((String)decimalStr)) {
            this.decimal = Integer.valueOf(decimalStr);
        }
        this.viewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        this.entityDataService = (IEntityDataService)BeanUtil.getBean(IEntityDataService.class);
        this.dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
        this.iNvwaDataAccessProvider = (INvwaDataAccessProvider)BeanUtil.getBean(INvwaDataAccessProvider.class);
        FormDefine formDefine = this.viewController.queryFormById(formKey);
        if (StringUtils.isNotEmpty((String)formDefine.getMeasureUnit()) && (measureStrs = formDefine.getMeasureUnit().split(";")).length == 2) {
            String tableKey = measureStrs[0];
            String measureValue = measureStrs[1];
            if (!measureValue.toLowerCase().equals("notdimession")) {
                try {
                    this.iEntityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
                    this.runtimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
                    TableModelDefine tableModel = this.iEntityMetaService.getTableModel(tableKey);
                    this.entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
                    EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(viewKey);
                    this.measureViewData = new MeasureViewData(entityViewDefine, tableModel);
                    this.formMeasureCode = measureValue;
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
        if (this.entityViewRunTimeController == null) {
            this.entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
        }
        IDataAccessProvider dataAccessProvider = (IDataAccessProvider)BeanUtil.getBean(IDataAccessProvider.class);
        if (this.runtimeController == null) {
            this.runtimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        }
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(viewKey);
        if (viewKey == null) {
            // empty if block
        }
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        Object entityTable = null;
        entityQuery.sorted(true);
        entityQuery.setEntityView(entityViewDefine);
        entityQuery.setAuthorityOperations(AuthorityType.Read);
        entityQuery.setIgnoreViewFilter(true);
        entityQuery.setMasterKeys(masterKeys);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine table = this.dataModelService.getTableModelDefineById(viewKey);
        List allColumns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
        allColumns = allColumns.stream().sorted((a, b) -> Double.compare(a.getOrder(), b.getOrder())).collect(Collectors.toList());
        for (ColumnModelDefine column : allColumns) {
            queryModel.getColumns().add(new NvwaQueryColumn(column));
        }
        queryModel.getColumnFilters().put(allColumns.get(1), this.formMeasureCode);
        try {
            INvwaDataAccess dataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            MemoryDataSet result = dataAccess.executeQuery(context);
            if (result.size() == 1) {
                DataRow dataRow = result.get(0);
                MeasureData measureData = this.getMeasureData((IEntityRow)dataRow);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public Object processValue(QueryField queryField, Object value) {
        FieldType type;
        boolean numberField;
        if (this.measureRate == 1.0) {
            return value;
        }
        String fieldKey = queryField.getUID();
        FieldDefine fieldDefine = null;
        try {
            fieldDefine = this.runtimeController.queryFieldDefine(fieldKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (StringUtils.isNotEmpty((String)fieldDefine.getMeasureUnit())) {
            String fieldMeasureCode;
            String fieldMeasureUnit = fieldDefine.getMeasureUnit();
            if (fieldMeasureUnit.contains("NotDimession")) {
                return value;
            }
            String[] measures = fieldMeasureUnit.split(";");
            if (measures.length == 2 && measures[0].equals(this.measureViewData.getTableKey()) && !this.formMeasureCode.equals(fieldMeasureCode = measures[1])) {
                return value;
            }
        }
        boolean bl = numberField = (type = fieldDefine.getType()) == FieldType.FIELD_TYPE_FLOAT || type == FieldType.FIELD_TYPE_INTEGER || type == FieldType.FIELD_TYPE_DECIMAL;
        if (numberField) {
            int fractionDigits = fieldDefine.getFractionDigits();
            if (this.decimal != null) {
                fractionDigits = this.decimal;
            }
            try {
                BigDecimal bigDecimal;
                String tempValue = value.toString();
                if (tempValue.contains(",")) {
                    Number number = NumberFormat.getIntegerInstance(Locale.getDefault()).parse(tempValue);
                    bigDecimal = new BigDecimal(number.doubleValue());
                } else {
                    bigDecimal = new BigDecimal(tempValue);
                }
                bigDecimal = new BigDecimal(value.toString());
                bigDecimal = bigDecimal.divide(BigDecimal.valueOf(this.measureRate), fractionDigits, 4);
                switch (type) {
                    case FIELD_TYPE_FLOAT: {
                        return bigDecimal.doubleValue();
                    }
                    case FIELD_TYPE_INTEGER: {
                        return bigDecimal.intValue();
                    }
                    case FIELD_TYPE_DECIMAL: {
                        return bigDecimal;
                    }
                }
                return bigDecimal.doubleValue();
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                return value;
            }
        }
        return value;
    }

    public double getMultiplier(QueryField queryField) {
        return this.measureRate;
    }

    private MeasureData getMeasureData(IEntityRow row) {
        if (row == null) {
            return null;
        }
        MeasureData measureData = new MeasureData();
        try {
            IFieldsInfo fieldsInfo = row.getFieldsInfo();
            int fieldCount = fieldsInfo.getFieldCount();
            block16: for (int i = 0; i < fieldsInfo.getFieldCount(); ++i) {
                IEntityAttribute attribute = fieldsInfo.getFieldByIndex(i);
                if (attribute == null) continue;
                String fieldCode = attribute.getCode();
                AbstractData value = row.getValue(fieldCode);
                switch (fieldCode) {
                    case "ID": {
                        measureData.setId(value.getAsString());
                        continue block16;
                    }
                    case "CODE": {
                        measureData.setCode(value.getAsString());
                        continue block16;
                    }
                    case "TITLE": {
                        measureData.setTitle(value.getAsString());
                        continue block16;
                    }
                    case "BASEUNIT": {
                        measureData.setBase(value.getAsBool());
                        continue block16;
                    }
                    case "RATIO": {
                        measureData.setRate(value.getAsFloat());
                        continue block16;
                    }
                }
            }
        }
        catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
        }
        return measureData;
    }
}

