/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.domain.DataType
 *  com.jiuqi.budget.param.hypermodel.domain.ModelShowDimensionDTO
 *  com.jiuqi.budget.param.hypermodel.domain.ModelShowMeasurementDTO
 *  com.jiuqi.budget.param.measurement.domain.IMeasurement
 *  com.jiuqi.budget.param.measurement.service.MeasurementService
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.gcreport.definition.impl.basic.init.table.util;

import com.jiuqi.budget.domain.DataType;
import com.jiuqi.budget.param.hypermodel.domain.ModelShowDimensionDTO;
import com.jiuqi.budget.param.hypermodel.domain.ModelShowMeasurementDTO;
import com.jiuqi.budget.param.measurement.domain.IMeasurement;
import com.jiuqi.budget.param.measurement.service.MeasurementService;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import org.springframework.util.ObjectUtils;

public class DimensionPublishUtil {
    public static final String VARCHAR_DEFAULT_VALUE = "'#'";
    public static final String INTEGER_DEFAULT_VALUE = "0";
    public static final String DATE_DEFAULT_VALUE = "1970-01-01";
    public static final String NUMERIC_DEFAULT_VALUE = "0.00";
    public static final String BUD_DEFAULT_VALUE = "00000000";

    public static DesignColumnModelDefine toDesignFieldDefineImpl(ModelShowDimensionDTO definitionField) {
        return DimensionPublishUtil.toDesignFieldDefineImpl(definitionField, (boolean)Boolean.TRUE);
    }

    public static DesignColumnModelDefine toDesignFieldDefineImpl(ModelShowDimensionDTO definitionField, boolean needDefaultVal) {
        DesignDataModelService designDataModelService = (DesignDataModelService)SpringBeanUtils.getBean(DesignDataModelService.class);
        DesignColumnModelDefine columnModelDefine = designDataModelService.createColumnModelDefine();
        columnModelDefine.setName(definitionField.getCode());
        columnModelDefine.setCode(definitionField.getCode());
        columnModelDefine.setTitle(definitionField.getName().trim());
        columnModelDefine.setDesc(definitionField.getName());
        columnModelDefine.setNullAble(true);
        columnModelDefine.setColumnType(ColumnModelType.STRING);
        columnModelDefine.setPrecision(60);
        if (needDefaultVal) {
            columnModelDefine.setDefaultValue(StringUtils.isEmpty((String)definitionField.getDefaultVal()) || BUD_DEFAULT_VALUE.equals(definitionField.getDefaultVal()) ? VARCHAR_DEFAULT_VALUE : definitionField.getDefaultVal());
        }
        if (!StringUtils.isEmpty((String)definitionField.getBaseDataCode())) {
            DataModelService dataModelService = (DataModelService)SpringBeanUtils.getBean(DataModelService.class);
            TableModelDefine tableModel = dataModelService.getTableModelDefineByCode(definitionField.getBaseDataCode());
            columnModelDefine.setReferColumnID(tableModel.getBizKeys());
            columnModelDefine.setReferTableID(tableModel.getID());
        } else {
            columnModelDefine.setReferColumnID(null);
            columnModelDefine.setReferTableID(null);
        }
        return columnModelDefine;
    }

    public static DesignColumnModelDefine toDesignFieldDefineImpl(ModelShowMeasurementDTO definitionField) {
        return DimensionPublishUtil.toDesignFieldDefineImpl(definitionField, (boolean)Boolean.TRUE);
    }

    public static DesignColumnModelDefine toDesignFieldDefineImpl(ModelShowMeasurementDTO definitionField, boolean needDefaultVal) {
        DesignDataModelService designDataModelService = (DesignDataModelService)SpringBeanUtils.getBean(DesignDataModelService.class);
        DesignColumnModelDefine columnModelDefine = designDataModelService.createColumnModelDefine();
        columnModelDefine.setName(definitionField.getCode());
        columnModelDefine.setCode(definitionField.getCode());
        columnModelDefine.setTitle(definitionField.getName().trim());
        columnModelDefine.setDesc(definitionField.getName());
        columnModelDefine.setNullAble(true);
        ColumnModelType columnModelType = null;
        String defaultValue = "";
        if (DataType.DATE.name().equals(definitionField.getDataType().name())) {
            columnModelType = ColumnModelType.STRING;
            defaultValue = DATE_DEFAULT_VALUE;
        } else if (DataType.NUM.name().equals(definitionField.getDataType().name()) || DataType.MONEY.name().equals(definitionField.getDataType().name())) {
            columnModelType = ColumnModelType.DOUBLE;
            defaultValue = NUMERIC_DEFAULT_VALUE;
            MeasurementService measurementService = (MeasurementService)SpringBeanUtils.getBean(MeasurementService.class);
            IMeasurement measurement = measurementService.getByCode(definitionField.getCode());
            columnModelDefine.setDecimal(measurement.getDataPrecision().intValue());
        } else if (DataType.INT.name().equals(definitionField.getDataType().name())) {
            columnModelType = ColumnModelType.INTEGER;
            defaultValue = INTEGER_DEFAULT_VALUE;
        } else {
            columnModelType = ColumnModelType.STRING;
            defaultValue = VARCHAR_DEFAULT_VALUE;
        }
        columnModelDefine.setColumnType(columnModelType);
        columnModelDefine.setPrecision(ObjectUtils.isEmpty(definitionField.getDataLength()) ? 60 : definitionField.getDataLength());
        if (needDefaultVal) {
            columnModelDefine.setDefaultValue(StringUtils.isEmpty((String)definitionField.getDefaultVal()) ? defaultValue : definitionField.getDefaultVal());
        }
        if (!StringUtils.isEmpty((String)definitionField.getBaseDataCode())) {
            DataModelService dataModelService = (DataModelService)SpringBeanUtils.getBean(DataModelService.class);
            TableModelDefine tableModel = dataModelService.getTableModelDefineByCode(definitionField.getBaseDataCode());
            columnModelDefine.setReferColumnID(tableModel.getBizKeys());
            columnModelDefine.setReferTableID(tableModel.getID());
        } else {
            columnModelDefine.setReferColumnID(null);
            columnModelDefine.setReferTableID(null);
        }
        return columnModelDefine;
    }
}

