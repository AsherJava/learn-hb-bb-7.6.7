/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.manager.TimeKeyHelper
 *  com.jiuqi.bi.dataset.model.field.AggregationType
 *  com.jiuqi.bi.dataset.model.field.ApplyType
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.common.util.TimeDimUtils
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.snapshot.dataset.builder;

import com.jiuqi.bi.dataset.manager.TimeKeyHelper;
import com.jiuqi.bi.dataset.model.field.AggregationType;
import com.jiuqi.bi.dataset.model.field.ApplyType;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.common.util.TimeDimUtils;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.snapshot.dataset.model.SnapshotDSModel;
import com.jiuqi.nr.snapshot.dataset.model.SnapshotDsModelDefine;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SnapshotDSModelBuilder {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;

    public void buildModel(SnapshotDSModel snapshotDSModel) throws Exception {
        List fields = snapshotDSModel.getCommonFields();
        fields.clear();
        SnapshotDsModelDefine snapshotDsModelDefine = snapshotDSModel.getSnapshotDsModelDefine();
        ArrayList<String> allFieldKeys = new ArrayList<String>();
        snapshotDSModel.setAllFieldKeys(allFieldKeys);
        DSField snapshotIDDSField = new DSField();
        snapshotIDDSField.setName("snapshotID");
        snapshotIDDSField.setTitle("\u5feb\u7167\u6807\u8bc6");
        snapshotIDDSField.setValType(6);
        snapshotIDDSField.setFieldType(FieldType.GENERAL_DIM);
        snapshotIDDSField.setKeyField("snapshotID");
        snapshotIDDSField.setNameField("snapshotID");
        fields.add(snapshotIDDSField);
        allFieldKeys.add("snapshotID");
        DSField snapshotNameDSField = new DSField();
        snapshotNameDSField.setName("snapshotName");
        snapshotNameDSField.setTitle("\u5feb\u7167\u6807\u9898");
        snapshotNameDSField.setValType(6);
        snapshotNameDSField.setFieldType(FieldType.GENERAL_DIM);
        snapshotNameDSField.setKeyField("snapshotName");
        snapshotNameDSField.setNameField("snapshotName");
        fields.add(snapshotNameDSField);
        allFieldKeys.add("snapshotName");
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(snapshotDsModelDefine.getTaskId());
        List dims = this.dataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
        for (DataDimension dim : dims) {
            DSField dimDSField;
            IEntityDefine entityDefine;
            DimensionType dimensionType = dim.getDimensionType();
            if (dimensionType == DimensionType.UNIT) {
                entityDefine = this.entityMetaService.queryEntity(dim.getDimKey());
                snapshotDSModel.setDWDimensionName(entityDefine.getDimensionName());
                dimDSField = new DSField();
                dimDSField.setName(entityDefine.getDimensionName());
                dimDSField.setTitle(entityDefine.getTitle());
                dimDSField.setValType(6);
                dimDSField.setFieldType(FieldType.GENERAL_DIM);
                dimDSField.setKeyField(entityDefine.getDimensionName());
                dimDSField.setNameField(entityDefine.getDimensionName());
                fields.add(dimDSField);
                allFieldKeys.add(entityDefine.getDimensionName());
                continue;
            }
            if (dimensionType == DimensionType.DIMENSION) {
                if (dim.getDimKey().equals("ADJUST")) continue;
                entityDefine = this.entityMetaService.queryEntity(dim.getDimKey());
                dimDSField = new DSField();
                dimDSField.setName(entityDefine.getDimensionName());
                dimDSField.setTitle(entityDefine.getTitle());
                dimDSField.setValType(6);
                dimDSField.setFieldType(FieldType.GENERAL_DIM);
                dimDSField.setKeyField(entityDefine.getDimensionName());
                dimDSField.setNameField(entityDefine.getDimensionName());
                fields.add(dimDSField);
                allFieldKeys.add(entityDefine.getDimensionName());
                continue;
            }
            if (dimensionType != DimensionType.PERIOD) continue;
            IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
            IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(dim.getDimKey());
            snapshotDSModel.setDataTimeDimensionName(periodEntity.getDimensionName());
            snapshotDSModel.setPeriodType(periodEntity.getType());
            DSField dimDSField2 = new DSField();
            dimDSField2.setName(periodEntity.getDimensionName());
            dimDSField2.setTitle(periodEntity.getTitle());
            dimDSField2.setValType(6);
            dimDSField2.setFieldType(FieldType.TIME_DIM);
            dimDSField2.setKeyField(periodEntity.getDimensionName());
            dimDSField2.setNameField(periodEntity.getDimensionName());
            dimDSField2.setTimegranularity(TimeDimUtils.periodTypeToTimeGranularity((PeriodType)periodEntity.getPeriodType()));
            dimDSField2.setDataPattern("yyyyMMdd");
            dimDSField2.setShowPattern(TimeKeyHelper.getDefaultShowDatePattern((int)dimDSField2.getTimegranularity().value()));
            dimDSField2.setTimekey(true);
            fields.add(dimDSField2);
            allFieldKeys.add(periodEntity.getDimensionName());
        }
        String regionKey = snapshotDsModelDefine.getRegionKey();
        if (StringUtils.isNotEmpty((String)regionKey)) {
            snapshotDSModel.setDataRegionKey(regionKey);
            List fieldKeys = this.runTimeViewController.getFieldKeysInRegion(regionKey);
            for (String fieldKey : fieldKeys) {
                FieldDefine fieldDefine = this.runTimeViewController.queryFieldDefine(fieldKey);
                if (!this.checktype(fieldDefine)) continue;
                DSField formDSField = new DSField();
                formDSField.setName(fieldDefine.getCode());
                formDSField.setTitle(fieldDefine.getTitle());
                formDSField.setValType(this.fieldTypeToDataType(fieldDefine.getType()));
                formDSField.setFieldType(FieldType.MEASURE);
                formDSField.setKeyField(fieldDefine.getCode());
                formDSField.setNameField(fieldDefine.getCode());
                formDSField.setAggregation(AggregationType.SUM);
                formDSField.setApplyType(ApplyType.PERIOD);
                formDSField.setSourceType("");
                formDSField.setSourceData("");
                fields.add(formDSField);
                allFieldKeys.add(fieldKey);
            }
        } else {
            List regions = this.runTimeViewController.getAllRegionsInForm(snapshotDsModelDefine.getFormKey());
            for (DataRegionDefine region : regions) {
                if (DataRegionKind.DATA_REGION_SIMPLE != region.getRegionKind()) continue;
                regionKey = region.getKey();
                snapshotDSModel.setDataRegionKey(regionKey);
                List fieldKeys = this.runTimeViewController.getFieldKeysInRegion(regionKey);
                for (String fieldKey : fieldKeys) {
                    FieldDefine fieldDefine = this.runTimeViewController.queryFieldDefine(fieldKey);
                    if (!this.checktype(fieldDefine)) continue;
                    DSField formDSField = new DSField();
                    formDSField.setName(fieldDefine.getCode());
                    formDSField.setTitle(fieldDefine.getTitle());
                    formDSField.setValType(this.fieldTypeToDataType(fieldDefine.getType()));
                    formDSField.setFieldType(FieldType.MEASURE);
                    formDSField.setKeyField(fieldDefine.getCode());
                    formDSField.setNameField(fieldDefine.getCode());
                    formDSField.setAggregation(AggregationType.SUM);
                    formDSField.setApplyType(ApplyType.PERIOD);
                    formDSField.setSourceType("");
                    formDSField.setSourceData("");
                    fields.add(formDSField);
                    allFieldKeys.add(fieldKey);
                }
                break;
            }
        }
    }

    private boolean checktype(FieldDefine fieldDefine) {
        return null != fieldDefine && (fieldDefine.getType() == com.jiuqi.np.definition.common.FieldType.FIELD_TYPE_FLOAT || fieldDefine.getType() == com.jiuqi.np.definition.common.FieldType.FIELD_TYPE_STRING || fieldDefine.getType() == com.jiuqi.np.definition.common.FieldType.FIELD_TYPE_INTEGER || fieldDefine.getType() == com.jiuqi.np.definition.common.FieldType.FIELD_TYPE_LOGIC || fieldDefine.getType() == com.jiuqi.np.definition.common.FieldType.FIELD_TYPE_DATE || fieldDefine.getType() == com.jiuqi.np.definition.common.FieldType.FIELD_TYPE_DATE_TIME || fieldDefine.getType() == com.jiuqi.np.definition.common.FieldType.FIELD_TYPE_TIME || fieldDefine.getType() == com.jiuqi.np.definition.common.FieldType.FIELD_TYPE_DECIMAL);
    }

    private int fieldTypeToDataType(com.jiuqi.np.definition.common.FieldType fieldType) {
        switch (fieldType) {
            case FIELD_TYPE_GENERAL: {
                return 6;
            }
            case FIELD_TYPE_FLOAT: 
            case FIELD_TYPE_DECIMAL: {
                return 3;
            }
            case FIELD_TYPE_STRING: {
                return 6;
            }
            case FIELD_TYPE_INTEGER: {
                return 5;
            }
            case FIELD_TYPE_LOGIC: {
                return 1;
            }
            case FIELD_TYPE_DATE: {
                return 2;
            }
            case FIELD_TYPE_DATE_TIME: {
                return 2;
            }
            case FIELD_TYPE_TIME: {
                return 2;
            }
            case FIELD_TYPE_BINARY: {
                return 9;
            }
            case FIELD_TYPE_FILE: {
                return 9;
            }
            case FIELD_TYPE_PICTURE: {
                return 9;
            }
            case FIELD_TYPE_TEXT: {
                return 9;
            }
            case FIELD_TYPE_UUID: {
                return 6;
            }
        }
        return 0;
    }
}

