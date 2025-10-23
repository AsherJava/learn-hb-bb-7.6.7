/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.manager.TimeKeyHelper
 *  com.jiuqi.bi.dataset.model.field.AggregationType
 *  com.jiuqi.bi.dataset.model.field.ApplyType
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy
 *  com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType
 *  com.jiuqi.bi.sql.DataTypes
 *  com.jiuqi.bi.util.Guid
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.common.util.TimeDimUtils
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.adapter.impl.org.util.OrgAdapterUtil
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.NrPeriodConst
 *  com.jiuqi.nr.period.common.utils.PeriodTableColumn
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.xlib.test.Assert
 */
package com.jiuqi.nr.summary.executor.query.ds;

import com.jiuqi.bi.dataset.manager.TimeKeyHelper;
import com.jiuqi.bi.dataset.model.field.AggregationType;
import com.jiuqi.bi.dataset.model.field.ApplyType;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType;
import com.jiuqi.bi.sql.DataTypes;
import com.jiuqi.bi.util.Guid;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.common.util.TimeDimUtils;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.adapter.impl.org.util.OrgAdapterUtil;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.common.utils.NrPeriodConst;
import com.jiuqi.nr.period.common.utils.PeriodTableColumn;
import com.jiuqi.nr.summary.executor.query.ds.SummaryDSField;
import com.jiuqi.nr.summary.executor.query.ds.SummaryDSModel;
import com.jiuqi.nr.summary.model.caliber.CaliberApplyType;
import com.jiuqi.nr.summary.model.caliber.CaliberFloatInfo;
import com.jiuqi.nr.summary.model.caliber.CaliberInfo;
import com.jiuqi.nr.summary.model.cell.DataCell;
import com.jiuqi.nr.summary.model.cell.MainCell;
import com.jiuqi.nr.summary.model.cell.SummaryZb;
import com.jiuqi.nr.summary.model.report.SummaryFloatRegion;
import com.jiuqi.nr.summary.model.soulution.SummarySolutionModel;
import com.jiuqi.nr.summary.utils.SummaryReportModelHelper;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.xlib.test.Assert;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class SummaryDSModelBuilder {
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private IRunTimeViewController runTimeViewController;
    private final IEntityMetaService entityMetaService;
    private SummaryFloatRegion floatRegion;
    private SummaryDSModel dsModel;
    private final String ds_name;
    private final String ds_title;
    private SummaryReportModelHelper reportModelHelper;
    private Map<String, String> dimTableToDimFieldMap = new HashMap<String, String>();

    public SummaryDSModelBuilder(IEntityMetaService entityMetaService, String ds_name, String ds_title) {
        this.runTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
        this.runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringBeanUtils.getBean(IRuntimeDataSchemeService.class);
        this.entityMetaService = entityMetaService;
        Assert.assertNotNull((Object)ds_name);
        this.ds_name = ds_name;
        if (!StringUtils.hasText(ds_title)) {
            ds_title = "\u9ed8\u8ba4\u6807\u9898";
        }
        this.ds_title = ds_title;
    }

    public void setFloatRegion(SummaryFloatRegion region) {
        this.floatRegion = region;
    }

    public SummaryDSModel build(SummarySolutionModel solutionModel, SummaryReportModelHelper reportModelHelper) {
        this.init(reportModelHelper);
        this.dsModel._setGuid(Guid.newGuid());
        this.dsModel.setName(this.ds_name);
        this.dsModel.setTitle(this.ds_title);
        this.dsModel.setRow(this.floatRegion != null ? this.floatRegion.getPosition() : 0);
        this.buildDimensions(solutionModel);
        this.buildMeasures();
        return this.dsModel;
    }

    private void init(SummaryReportModelHelper reportModelHelper) {
        this.dsModel = new SummaryDSModel();
        this.reportModelHelper = reportModelHelper;
    }

    private void buildDimensions(SummarySolutionModel solutionModel) {
        if (this.floatRegion != null) {
            List dimDataFields = this.runtimeDataSchemeService.getDataFieldByTableCodeAndKind(this.floatRegion.getTableName(), new DataFieldKind[]{DataFieldKind.TABLE_FIELD_DIM});
            dimDataFields.forEach(dataField -> {
                SummaryDSField summaryDSField = this.buildDimField((DataField)dataField);
                this.dsModel.getPublicDimFields().add(summaryDSField.getName());
                if (StringUtils.hasLength(dataField.getRefDataEntityKey())) {
                    IEntityDefine entity = this.entityMetaService.queryEntity(dataField.getRefDataEntityKey());
                    IEntityModel entityModel = this.entityMetaService.getEntityModel(entity.getId());
                    SummaryDSField parentField = this.buildDimOtherField(entity.getCode(), entityModel.getParentField(), summaryDSField.getKeyField(), entity.getTitle());
                    this.processHierarchy(summaryDSField, parentField);
                    SummaryDSField orderField = this.buildDimOtherField(entity.getCode(), entityModel.getOrderField(), summaryDSField.getKeyField(), entity.getTitle());
                    if (!ObjectUtils.isEmpty((Object)orderField)) {
                        this.dsModel.addKeyField2OrderRef(summaryDSField.getKeyField(), orderField.getName());
                    }
                }
            });
            List<MainCell> mainCells = this.reportModelHelper.getMainCellsByRegion(this.floatRegion);
            mainCells.forEach(mainCell -> this.processFloatInfo((MainCell)mainCell));
        }
    }

    private SummaryDSField buildDimOtherField(String dimKey, IEntityAttribute entityAttribute, String keyField, String fieldTitle) {
        if (ObjectUtils.isEmpty(entityAttribute)) {
            return null;
        }
        String fieldCode = entityAttribute.getCode();
        String code = dimKey + "_" + fieldCode;
        String exp = dimKey + "[" + fieldCode + "]";
        String title = fieldTitle + fieldCode;
        String alias = dimKey + "." + fieldCode;
        FieldType fieldType = FieldType.GENERAL_DIM;
        SummaryDSField dsField = new SummaryDSField();
        dsField.setName(code);
        dsField.setTitle(title);
        dsField.setMessageAlias(alias);
        dsField.setAggregation(AggregationType.SUM);
        dsField.setApplyType(ApplyType.PERIOD);
        dsField.setKeyField(keyField);
        dsField.setFieldType(fieldType);
        dsField.setValType(this.getValType(entityAttribute.getColumnType()));
        dsField.setExpression(exp);
        if (this.addFieldToModel(code, dsField)) {
            return dsField;
        }
        return null;
    }

    private int getValType(ColumnModelType columnType) {
        if (columnType == ColumnModelType.BIGDECIMAL) {
            return ColumnModelType.DOUBLE.getValue();
        }
        return columnType.getValue();
    }

    private SummaryDSField buildDimField(DataField dataField) {
        DataTable dataTable = this.runtimeDataSchemeService.getDataTable(dataField.getDataTableKey());
        String code = "INNER_" + dataTable.getCode() + "_" + dataField.getCode();
        String title = dataField.getTitle();
        String alias = dataTable.getCode() + "." + dataField.getCode();
        if (StringUtils.hasLength(dataField.getRefDataEntityKey())) {
            alias = this.entityMetaService.getEntityCode(dataField.getRefDataEntityKey());
            alias = this.isOrg(dataField.getRefDataEntityKey()) ? alias + ".CODE" : alias + ".OBJECTCODE";
        }
        FieldType fieldType = FieldType.GENERAL_DIM;
        int dataType = 6;
        SummaryDSField dsField = new SummaryDSField();
        dsField.setName(code);
        dsField.setKeyField(code);
        dsField.setTitle(title);
        dsField.setMessageAlias(alias);
        dsField.setAggregation(AggregationType.SUM);
        dsField.setApplyType(ApplyType.PERIOD);
        dsField.setFieldType(fieldType);
        dsField.setValType(dataType);
        dsField.setZbKey(dataField.getKey());
        if (this.addFieldToModel(code, dsField)) {
            this.dimTableToDimFieldMap.put(dataField.getRefDataEntityKey(), code);
            return dsField;
        }
        return null;
    }

    private void processFloatInfo(MainCell mainCell) {
        List<CaliberInfo> caliberInfos = mainCell.getCaliberInfos();
        caliberInfos.forEach(caliberInfo -> {
            String displayField;
            String[] split;
            CaliberFloatInfo floatInfo;
            CaliberApplyType applyType = caliberInfo.getApplyType();
            if (applyType == CaliberApplyType.FLOAT && (floatInfo = caliberInfo.getFloatInfo()) != null && (split = (displayField = floatInfo.getDisplayField()).split("\\.")).length >= 2) {
                String showField = split[1];
                String entityTableName = split[0];
                String fieldName = this.buildMainCellField(entityTableName, showField);
                String mainCellKey = this.reportModelHelper.getMainCellKey(mainCell);
                this.dsModel.addKey2NameRef(mainCellKey, fieldName);
            }
        });
    }

    private String buildMainCellField(String dwDimKey, String fieldCode) {
        IEntityDefine entity = this.entityMetaService.queryEntity(dwDimKey);
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entity.getId());
        String fieldName = null;
        if (this.isOrg(dwDimKey)) {
            String entityCode = this.entityMetaService.getEntityCode(dwDimKey);
            SummaryDSField summaryDSField = this.buildInnerDimField(entity.getCode(), fieldCode, false, entity.getTitle());
            if (!ObjectUtils.isEmpty((Object)summaryDSField)) {
                fieldName = summaryDSField.getName();
            }
        } else {
            SummaryDSField summaryDSField = this.buildInnerDimField(entity.getCode(), fieldCode, false, entity.getTitle());
            if (!ObjectUtils.isEmpty((Object)summaryDSField)) {
                fieldName = summaryDSField.getName();
            }
        }
        return fieldName;
    }

    private SummaryDSField buildInnerDimField(String dimKey, String fieldCode, boolean isOrg, String fieldTitle) {
        String code = dimKey + "_" + fieldCode;
        String exp = dimKey + "[" + fieldCode + "]";
        String title = fieldTitle + fieldCode;
        String alias = dimKey + "." + fieldCode;
        FieldType fieldType = FieldType.GENERAL_DIM;
        int dataType = 6;
        String keyField = dimKey + "_OBJECTCODE";
        if (isOrg) {
            alias = "MD_ORG." + fieldCode;
            keyField = dimKey + "_CODE";
        }
        SummaryDSField dsField = new SummaryDSField();
        dsField.setName(code);
        dsField.setTitle(title);
        dsField.setMessageAlias(alias);
        dsField.setAggregation(AggregationType.SUM);
        dsField.setApplyType(ApplyType.PERIOD);
        keyField = this.dimTableToDimFieldMap.get(this.entityMetaService.getEntityIdByCode(dimKey));
        dsField.setKeyField(keyField);
        dsField.setFieldType(fieldType);
        dsField.setValType(dataType);
        dsField.setExpression(exp);
        if (this.addFieldToModel(code, dsField)) {
            return dsField;
        }
        return null;
    }

    private void buildDimensionFieldBySolution(SummarySolutionModel summarySolutionModel) {
        String mainTask = summarySolutionModel.getMainTask();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(mainTask);
        String dwDimKey = summarySolutionModel.getTargetDimension();
        this.buildMainDimFields(dwDimKey);
        String dimKeys = taskDefine.getDims();
        this.buildSceneField(dimKeys);
    }

    private void buildDataTimeField(String dataTimeKey, boolean isCustom, PeriodType periodType) {
        String code = NrPeriodConst.PREFIX_CODE + dataTimeKey + "_" + PeriodTableColumn.TIMEKEY.getCode();
        String title = "\u65f6\u671f";
        SummaryDSField dsField = new SummaryDSField();
        dsField.setName(code);
        dsField.setTitle(title);
        dsField.setMessageAlias(PeriodTableColumn.TIMEKEY.getCode());
        dsField.setAggregation(AggregationType.SUM);
        dsField.setApplyType(ApplyType.PERIOD);
        dsField.setKeyField(code);
        if (isCustom) {
            dsField.setFieldType(FieldType.GENERAL_DIM);
        } else {
            dsField.setFieldType(FieldType.TIME_DIM);
        }
        dsField.setTimekey(true);
        dsField.setTimegranularity(TimeDimUtils.periodTypeToTimeGranularity((PeriodType)periodType));
        dsField.setDataPattern("yyyyMMdd");
        if (dsField.getTimegranularity() != null) {
            dsField.setShowPattern(TimeKeyHelper.getDefaultShowDatePattern((int)dsField.getTimegranularity().value()));
        }
        dsField.setValType(6);
        this.dsModel.getPublicDimFields().add(dsField.getName());
        this.addFieldToModel(null, dsField);
        if (isCustom) {
            SummaryDSField dataTimeTitleField = new SummaryDSField();
            code = "PERIOD_TITLE";
            title = "\u65f6\u671f\u6807\u9898";
            dataTimeTitleField.setName(code);
            dataTimeTitleField.setTitle(title);
            dataTimeTitleField.setMessageAlias(PeriodTableColumn.TIMEKEY.getCode());
            dataTimeTitleField.setAggregation(AggregationType.SUM);
            dataTimeTitleField.setApplyType(ApplyType.PERIOD);
            dataTimeTitleField.setKeyField(code);
            dataTimeTitleField.setFieldType(FieldType.GENERAL_DIM);
            dataTimeTitleField.setValType(6);
            dataTimeTitleField.setKeyField(dsField.getName());
            this.addFieldToModel(null, dataTimeTitleField);
        }
    }

    private void buildMainDimFields(String dwDimKey) {
        IEntityDefine entity = this.entityMetaService.queryEntity(dwDimKey);
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entity.getId());
        if (this.isOrg(dwDimKey)) {
            this.buildOrgEntityFields(entityModel, this.entityMetaService.getEntityCode(dwDimKey), entity.getTitle(), true);
        } else {
            this.buildEntityFieldsAndParameters(entityModel, entity.getCode(), entity.getTitle(), true);
        }
    }

    private void buildOrgEntityFields(IEntityModel entityModel, String entityCode, String entityTitle, boolean isMainDim) {
        SummaryDSField codeField = this.buildDimField(entityCode, "CODE", true, entityTitle);
        this.buildDimField(entityCode, "ORGCODE", true, entityTitle);
        this.buildDimField(entityCode, entityModel.getNameField().getCode(), true, entityTitle);
        SummaryDSField parentField = this.buildDimField(entityCode, entityModel.getParentField().getCode(), true, entityTitle);
        this.processHierarchy(codeField, parentField);
        if (isMainDim) {
            this.buildDimField(entityCode, entityModel.getOrderField().getCode(), true, entityTitle);
        }
        this.dsModel.getPublicDimFields().add(entityCode + "_CODE");
    }

    private void processHierarchy(SummaryDSField codeField, SummaryDSField parentField) {
        if (codeField == null || parentField == null) {
            return;
        }
        DSHierarchy hierarchy = new DSHierarchy();
        hierarchy.setName(parentField.getName());
        hierarchy.setTitle(parentField.getTitle());
        hierarchy.setType(DSHierarchyType.PARENT_HIERARCHY);
        hierarchy.getLevels().add(codeField.getName());
        hierarchy.setParentFieldName(parentField.getName());
        this.dsModel.getHiers().add(hierarchy);
    }

    private void buildEntityFieldsAndParameters(IEntityModel entityModel, String entityCode, String entityTitle, boolean isMainDim) {
        this.buildDimField(entityCode, "OBJECTCODE", false, entityTitle);
        this.buildDimField(entityCode, "CODE", false, entityTitle);
        this.buildDimField(entityCode, entityModel.getNameField().getCode(), false, entityTitle);
        this.buildDimField(entityCode, entityModel.getParentField().getCode(), false, entityTitle);
        if (isMainDim) {
            this.buildDimField(entityCode, entityModel.getOrderField().getCode(), false, entityTitle);
        }
        this.dsModel.getPublicDimFields().add(entityCode + "_OBJECTCODE");
    }

    private SummaryDSField buildDimField(String dimKey, String fieldCode, boolean isOrg, String fieldTitle) {
        String code = dimKey + "_" + fieldCode;
        String exp = dimKey + "[" + fieldCode + "]";
        String title = fieldTitle + fieldCode;
        String alias = dimKey + "." + fieldCode;
        FieldType fieldType = FieldType.GENERAL_DIM;
        int dataType = 6;
        String keyField = dimKey + "_OBJECTCODE";
        if (isOrg) {
            alias = "MD_ORG." + fieldCode;
            keyField = dimKey + "_CODE";
        }
        SummaryDSField dsField = new SummaryDSField();
        dsField.setName(code);
        dsField.setTitle(title);
        dsField.setMessageAlias(alias);
        dsField.setAggregation(AggregationType.SUM);
        dsField.setApplyType(ApplyType.PERIOD);
        dsField.setKeyField(keyField);
        dsField.setFieldType(fieldType);
        dsField.setValType(dataType);
        dsField.setExpression(exp);
        if (this.addFieldToModel(code, dsField)) {
            return dsField;
        }
        return null;
    }

    private boolean addFieldToModel(String key, SummaryDSField dsField) {
        if (this.dsModel.addSummaryField(dsField.getName(), dsField)) {
            this.dsModel.addKey2NameRef(key, dsField.getName());
            return true;
        }
        return false;
    }

    private void buildSceneField(String dimKeys) {
        if (StringUtils.hasLength(dimKeys)) {
            String[] dimKeyList;
            for (String dimKey : dimKeyList = dimKeys.split(";")) {
                String[] dimKeySplit = dimKey.split("@");
                IEntityDefine sceneEntity = this.entityMetaService.queryEntity(dimKeySplit[0]);
                IEntityModel entityModel = this.entityMetaService.getEntityModel(sceneEntity.getId());
                this.buildEntityFieldsAndParameters(entityModel, sceneEntity.getCode(), sceneEntity.getTitle(), false);
            }
        }
    }

    private void buildMeasures() {
        List<DataCell> dataCells = this.getDataCells();
        dataCells.stream().forEach(dataCell -> {
            String tableName = dataCell.getSummaryZb().getTableName();
            String fieldCode = dataCell.getSummaryZb().getName();
            String code = tableName + "_" + fieldCode;
            String title = dataCell.getSummaryZb().getTitle();
            String alias = tableName + "." + fieldCode;
            int dataType = dataCell.getSummaryZb().getDataType().getValue();
            FieldType fieldType = FieldType.MEASURE;
            if (!DataTypes.isNumber((int)dataType)) {
                fieldType = FieldType.DESCRIPTION;
            }
            SummaryDSField dsField = new SummaryDSField();
            dsField.setName(code);
            dsField.setKeyField(code);
            dsField.setNameField(code);
            dsField.setTitle(title);
            dsField.setValType(dataType);
            dsField.setFieldType(fieldType);
            dsField.setAggregation(AggregationType.SUM);
            dsField.setApplyType(ApplyType.PERIOD);
            dsField.setMessageAlias(alias);
            SummaryZb summaryZb = dataCell.getSummaryZb();
            if (summaryZb.getDataType() == DataFieldType.INTEGER) {
                dsField.setShowPattern("#,##0");
            } else if (summaryZb.getDataType() == DataFieldType.BIGDECIMAL) {
                String showPattern = "#,##0";
                if (summaryZb.getDecimal() > 0) {
                    for (int i = 0; i < summaryZb.getDecimal(); ++i) {
                        if (i == 0) {
                            showPattern = showPattern + ".";
                        }
                        showPattern = showPattern + "0";
                    }
                } else {
                    showPattern = "#,##0.00";
                }
                dsField.setShowPattern(showPattern);
            }
            dsField.setZbKey(dataCell.getSummaryZb().getFieldKey());
            String key = this.reportModelHelper.getDataCellKey((DataCell)dataCell);
            this.addFieldToModel(key, dsField);
            this.dsModel.getQueryMeasureFields().add(code);
        });
    }

    private List<DataCell> getDataCells() {
        if (this.floatRegion != null) {
            return this.reportModelHelper.getFloatDataCells(this.floatRegion);
        }
        return this.reportModelHelper.getFixDataCells();
    }

    private boolean isOrg(String dimKey) {
        if (OrgAdapterUtil.isOrg((String)dimKey)) {
            return true;
        }
        return dimKey.startsWith("MD_ORG_");
    }
}

