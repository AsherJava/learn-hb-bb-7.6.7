/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.text.CalendarFormatEx
 *  com.jiuqi.bi.text.DateFormatEx
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterCalculator
 *  com.jiuqi.nvwa.framework.parameter.ParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultItem
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultset
 *  com.jiuqi.nvwa.framework.parameter.datasource.extend.CustomListDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.datasource.extend.CustomListDataSourceModel$FixedMemberItem
 *  com.jiuqi.nvwa.framework.parameter.datasource.extend.NonDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterWidgetType
 *  com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue
 *  com.jiuqi.nvwa.framework.parameter.model.value.FixedMemberParameterValue
 *  org.json.JSONObject
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DSContext;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.embedded.EmbeddedDSModel;
import com.jiuqi.bi.dataset.format.BooleanFormat;
import com.jiuqi.bi.dataset.format.TimeDimFormat;
import com.jiuqi.bi.dataset.manager.DataSetManagerFactory;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.DataSetTypeNotFoundException;
import com.jiuqi.bi.dataset.model.field.DSCalcField;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.text.CalendarFormatEx;
import com.jiuqi.bi.text.DateFormatEx;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterCalculator;
import com.jiuqi.nvwa.framework.parameter.ParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultItem;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.CustomListDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.NonDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterWidgetType;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.FixedMemberParameterValue;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import org.json.JSONObject;

public class DSUtils {
    public static BIDataSetFieldInfo transform(DSField field) {
        BIDataSetFieldInfo fieldInfo = new BIDataSetFieldInfo();
        fieldInfo.setName(field.getName());
        fieldInfo.setTitle(field.getTitle());
        fieldInfo.setValType(field.getValType());
        fieldInfo.setFieldType(field.getFieldType());
        fieldInfo.setKeyField(field.getKeyField());
        fieldInfo.setNameField(field.getNameField());
        fieldInfo.setAggregation(field.getAggregation());
        fieldInfo.setApplyType(field.getApplyType());
        fieldInfo.setTimegranularity(field.getTimegranularity());
        fieldInfo.setDataPattern(field.getDataPattern());
        fieldInfo.setShowPattern(field.getShowPattern());
        fieldInfo.setSourceType(field.getSourceType());
        fieldInfo.setSourceData(field.getSourceData());
        if (field instanceof DSCalcField) {
            DSCalcField calcField = (DSCalcField)field;
            fieldInfo.setCalcField(true);
            fieldInfo.setFormula(calcField.getFormula());
            fieldInfo.setCalcMode(calcField.getCalcMode());
        } else {
            fieldInfo.setCalcField(false);
        }
        fieldInfo.setUnitDim(field.isUnitDim());
        fieldInfo.setTimekey(field.isTimekey());
        fieldInfo.setMessageAlias(field.getMessageAlias());
        return fieldInfo;
    }

    public static Format[] generateFormat(Metadata<BIDataSetFieldInfo> metadata, Locale locale) {
        int size = metadata.getColumnCount();
        Format[] formats = new Format[size];
        for (int i = 0; i < size; ++i) {
            Column column = metadata.getColumn(i);
            formats[i] = DSUtils.generateFormat(metadata, (BIDataSetFieldInfo)column.getInfo(), locale);
        }
        return formats;
    }

    public static Format[] generateFormat(Metadata<BIDataSetFieldInfo> metadata) {
        return DSUtils.generateFormat(metadata, Locale.getDefault());
    }

    public static Format generateFormat(BIDataSetFieldInfo fieldInfo) {
        return DSUtils.generateFormat(fieldInfo, Locale.getDefault());
    }

    public static Format generateFormat(BIDataSetFieldInfo fieldInfo, Locale locale) {
        return DSUtils.generateFormat(null, fieldInfo, locale);
    }

    public static Format generateFormat(Metadata<BIDataSetFieldInfo> metadata, BIDataSetFieldInfo fieldInfo, Locale locale) {
        String showPattern = fieldInfo.getShowPattern();
        if (fieldInfo.getFieldType() == FieldType.TIME_DIM) {
            Object fiscalMonth;
            TimeDimFormat timeDimFormat = new TimeDimFormat(fieldInfo, locale);
            if (metadata != null && metadata.getProperties() != null && (fiscalMonth = metadata.getProperties().get("FiscalMonth")) != null) {
                JSONObject json = new JSONObject((String)fiscalMonth);
                int min = json.optInt("min", -1);
                int max = json.optInt("max", -1);
                if (min >= 0 && max >= 12) {
                    timeDimFormat.setFiscalMonth(min, max);
                }
            }
            return timeDimFormat;
        }
        if (fieldInfo.getValType() == 5 || fieldInfo.getValType() == 8) {
            if (StringUtils.isEmpty((String)showPattern)) {
                return new DecimalFormat("#");
            }
            return new DecimalFormat(showPattern);
        }
        if (fieldInfo.getValType() == 10 || fieldInfo.getValType() == 3) {
            if (showPattern == null || showPattern.length() == 0) {
                showPattern = "#,##0.00";
            }
            try {
                DecimalFormat df = new DecimalFormat(showPattern);
                return df;
            }
            catch (Exception e) {
                return new DecimalFormat("#,##0.00");
            }
        }
        if (fieldInfo.getValType() == 2) {
            if (showPattern == null || showPattern.length() == 0) {
                showPattern = "yyyy-MM-dd";
            }
            try {
                DateFormatEx sdf = new DateFormatEx(showPattern, locale);
                return new CalendarFormatEx(sdf);
            }
            catch (Exception e) {
                DateFormatEx sdf = new DateFormatEx("yyyy-MM-dd", locale);
                return new CalendarFormatEx(sdf);
            }
        }
        if (fieldInfo.getValType() == DataType.BOOLEAN.value()) {
            return new BooleanFormat(fieldInfo);
        }
        return null;
    }

    public static Format generateFormat(DSField field) {
        return DSUtils.generateFormat(DSUtils.transform(field), Locale.getDefault());
    }

    public static Format generateFormat(DSField field, Locale locale) {
        return DSUtils.generateFormat(DSUtils.transform(field), locale);
    }

    public static Format generateFormat(DSModel dsModel, DSField field, Locale locale) {
        Format format = DSUtils.generateFormat(DSUtils.transform(field), locale);
        if (dsModel != null && format instanceof TimeDimFormat) {
            TimeDimFormat timeDimFormat = (TimeDimFormat)((Object)format);
            timeDimFormat.setFiscalMonth(dsModel.getMinFiscalMonth(), dsModel.getMaxFiscalMonth());
        }
        return format;
    }

    public static MemoryDataSet<BIDataSetFieldInfo> convertDataset(BIDataSet dataset) throws BIDataSetException {
        MemoryDataSet ds = new MemoryDataSet(dataset.getMetadata());
        Iterator<BIDataRow> itor = dataset.iterator();
        try {
            while (itor.hasNext()) {
                ds.add(itor.next().getBuffer());
            }
        }
        catch (DataSetException e) {
            throw new BIDataSetException(e.getMessage(), e);
        }
        return ds;
    }

    public static EmbeddedDSModel convertDatasetToEmbeddedDS(DSModel model, String userId) throws BIDataSetException, DataSetTypeNotFoundException {
        if (model instanceof EmbeddedDSModel) {
            return (EmbeddedDSModel)model;
        }
        ParameterEnv env = new ParameterEnv(userId, model.getParameterModels());
        DSContext dxCxt = new DSContext(model, userId, (IParameterEnv)env);
        BIDataSet dataset = DataSetManagerFactory.create().open(dxCxt, model);
        MemoryDataSet<BIDataSetFieldInfo> memoryDataSet = DSUtils.convertDataset(dataset);
        EmbeddedDSModel embedded = new EmbeddedDSModel(memoryDataSet);
        embedded._setGuid(model._getGuid());
        embedded.setName(model.getName());
        embedded.setTitle(model.getTitle());
        embedded.setDescr(model.getDescr());
        model.getCommonFields().forEach(c -> embedded.getCommonFields().add(c.clone()));
        model.getCalcFields().forEach(c -> embedded.getCalcFields().add(c.clone()));
        model.getHiers().forEach(c -> embedded.getHiers().add(c.clone()));
        for (ParameterModel c2 : model.getParameterModels()) {
            try {
                embedded.getParameterModels().add(DSUtils.convertParameterModelToEmbeddedParameter(c2, (IParameterEnv)env));
            }
            catch (ParameterException e) {
                throw new BIDataSetException(e.getMessage(), e);
            }
        }
        return embedded;
    }

    public static ParameterModel convertParameterModelToEmbeddedParameter(ParameterModel model, IParameterEnv env) throws ParameterException {
        NonDataSourceModel newds;
        ParameterModel pm = model.clone();
        pm.setGlobal(false);
        AbstractParameterDataSourceModel datasource = pm.getDatasource();
        if (datasource instanceof NonDataSourceModel || datasource instanceof CustomListDataSourceModel) {
            return pm;
        }
        int widgetType = model.getWidgetType();
        if (widgetType == ParameterWidgetType.DEFAULT.value() || widgetType == ParameterWidgetType.POPUP.value() || widgetType == ParameterWidgetType.UNITSELECTOR.value()) {
            pm.setWidgetType(ParameterWidgetType.DROPDOWN.value());
        }
        ParameterCalculator pc = new ParameterCalculator(env.getUserId(), env.getParameterModels());
        ParameterResultset currValue = pc.getValue(model.getName());
        if (widgetType == ParameterWidgetType.DATEPICKER.value() || widgetType == ParameterWidgetType.DATEPICKER_RANGE.value() || pm.getDataType() == DataType.INTEGER.value() || pm.getDataType() == DataType.DOUBLE.value()) {
            newds = new NonDataSourceModel(datasource.getDataType());
            newds.setTimegranularity(datasource.getTimegranularity());
            newds.setTimekey(datasource.isTimekey());
        } else {
            newds = new CustomListDataSourceModel();
            newds.setDataType(datasource.getDataType());
            newds.setHierarchyType(datasource.getHierarchyType());
            currValue.forEach(arg_0 -> DSUtils.lambda$convertParameterModelToEmbeddedParameter$3((AbstractParameterDataSourceModel)newds, arg_0));
        }
        pm.setDatasource((AbstractParameterDataSourceModel)newds);
        AbstractParameterValueConfig cfg = pm.getValueConfig();
        cfg.setDefaultValueMode("appoint");
        cfg.setDefaultValue((AbstractParameterValue)new FixedMemberParameterValue((Collection)currValue.getValueAsList()));
        return pm;
    }

    private static /* synthetic */ void lambda$convertParameterModelToEmbeddedParameter$3(AbstractParameterDataSourceModel newds, ParameterResultItem c) {
        CustomListDataSourceModel.FixedMemberItem item = new CustomListDataSourceModel.FixedMemberItem(c.getValue().toString(), c.getTitle());
        ((CustomListDataSourceModel)newds).getItems().add(item);
    }
}

