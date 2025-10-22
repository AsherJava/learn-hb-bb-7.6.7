/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.NrPeriodConst
 *  com.jiuqi.nr.period.common.utils.PeriodTableColumn
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 */
package com.jiuqi.bi.dataset.report.builder;

import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.report.builder.intf.IReportDsModelDataBuilder;
import com.jiuqi.bi.dataset.report.builder.intf.IReportDsModelFieldParam;
import com.jiuqi.bi.dataset.report.exception.ReportDsModelDataBuildException;
import com.jiuqi.bi.dataset.report.model.DefaultValueMode;
import com.jiuqi.bi.dataset.report.model.ReportDsModelDefine;
import com.jiuqi.bi.dataset.report.model.ReportDsParameter;
import com.jiuqi.bi.dataset.report.model.ReportExpField;
import com.jiuqi.bi.dataset.report.model.ReportFieldType;
import com.jiuqi.bi.dataset.report.provider.ExpParseProvider;
import com.jiuqi.bi.dataset.report.remote.controller.vo.ExpFieldVo;
import com.jiuqi.bi.dataset.report.remote.controller.vo.ExpParsedFieldVo;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.common.utils.NrPeriodConst;
import com.jiuqi.nr.period.common.utils.PeriodTableColumn;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class ReportDsModelDataBuilder
implements IReportDsModelDataBuilder {
    private static final String CODE = "CODE";
    private static final String ORGCODE = "ORGCODE";
    private static final String OBJECTCODE = "OBJECTCODE";
    private static final String PARENTCODE = "PARENTCODE";
    private static final String NAME = "NAME";
    private static final String SHORTNAME = "SHORTNAME";
    private static final String ORDINAL = "ORDINAL";
    private static final String CODE_ATTR = "CODE";
    private static final String NAME_ATTR = "NAME";
    private static final String PARENT_CODE_ATTR = "PARENTCODE";
    private static final String OBJECT_CODE_ATTR = "OBJECTCODE";
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private ExpParseProvider expParseProvider;
    @Autowired
    private DataModelService dataModelService;

    @Override
    public ReportDsModelDefine buildDefault(String taskKey) throws ReportDsModelDataBuildException {
        if (!StringUtils.hasLength(taskKey)) {
            throw new ReportDsModelDataBuildException("\u4efb\u52a1key\u4e3a\u7a7a");
        }
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        if (taskDefine == null) {
            throw new ReportDsModelDataBuildException("\u672a\u627e\u5230\u4efb\u52a1");
        }
        ReportDsModelDefine reportDsModelDefine = new ReportDsModelDefine();
        ArrayList<ReportExpField> fields = new ArrayList<ReportExpField>();
        ArrayList<ReportDsParameter> parameters = new ArrayList<ReportDsParameter>();
        String dataTimeKey = taskDefine.getDateTime();
        boolean isCustom = taskDefine.getPeriodType().equals((Object)PeriodType.CUSTOM);
        String dwDimKey = taskDefine.getDw();
        String dimKeys = taskDefine.getDims();
        this.buildDataTimeField(fields, dataTimeKey, isCustom);
        this.buildDataTimeParameter(parameters, dataTimeKey);
        String[] dwSplit = dwDimKey.split("@");
        IEntityDefine entity = this.entityMetaService.queryEntity(dwSplit[0]);
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entity.getId());
        if (dwSplit[1].equals("ORG")) {
            this.buildDimField(fields, dwSplit[0], "CODE", true, entity);
            this.buildDimField(fields, dwSplit[0], ORGCODE, true, entity);
            this.buildDimField(fields, dwSplit[0], "NAME", true, entity);
            this.buildDimField(fields, dwSplit[0], SHORTNAME, true, entity);
            this.buildDimField(fields, dwSplit[0], "PARENTCODE", true, entity);
            this.buildDimField(fields, dwSplit[0], ORDINAL, true, entity);
            this.buildDimParameter(parameters, dwSplit[0], true, entity);
        } else {
            this.buildEntityFieldsAndParameters(fields, entityModel, parameters, entity, true);
        }
        if (StringUtils.hasLength(dimKeys)) {
            String[] dimKeyList;
            HashMap<String, DataDimension> dataDimensionMap = new HashMap();
            List dimensions = this.runtimeDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme(), DimensionType.DIMENSION);
            if (!CollectionUtils.isEmpty(dimensions)) {
                dataDimensionMap = dimensions.stream().collect(Collectors.toMap(DataDimension::getDimKey, Function.identity()));
            }
            for (String dimKey : dimKeyList = dimKeys.split(";")) {
                String[] dimKeySplit = dimKey.split("@");
                IEntityDefine sceneEntity = this.entityMetaService.queryEntity(dimKeySplit[0]);
                if (this.isMergeUnitScene(taskDefine, sceneEntity, dataDimensionMap)) continue;
                this.buildEntityFieldsAndParameters(fields, entityModel, parameters, sceneEntity, false);
            }
        }
        reportDsModelDefine.setFields(fields);
        reportDsModelDefine.setParameters(parameters);
        return reportDsModelDefine;
    }

    @Override
    public List<ReportExpField> buildExpFields(List<ReportDsParameter> params, List<IReportDsModelFieldParam> fieldParams) throws ReportDsModelDataBuildException {
        ArrayList<ReportExpField> expFields = new ArrayList<ReportExpField>();
        HashMap<String, List> expFieldTaskMap = new HashMap<String, List>();
        HashMap<String, List> parseExpFieldMap = new HashMap<String, List>();
        if (CollectionUtils.isEmpty(fieldParams)) {
            return null;
        }
        try {
            HashMap<String, IEntityDefine> entityMap = new HashMap<String, IEntityDefine>();
            for (IReportDsModelFieldParam fieldParam : fieldParams) {
                List<ReportExpField> tempFields;
                DataField dataField = this.runtimeDataSchemeService.getDataField(fieldParam.getKey());
                if (dataField == null) {
                    tempFields = this.handleFMDMField(fieldParam, dataField);
                } else {
                    boolean isInner = false;
                    IEntityDefine entity = null;
                    if (dataField.getDataFieldKind() == DataFieldKind.TABLE_FIELD_DIM) {
                        isInner = StringUtils.hasLength(dataField.getRefDataEntityKey()) ? (entity = this.entityMetaService.queryEntity(dataField.getRefDataEntityKey())) != null && new Integer(1).equals(entity.getDimensionFlag()) : true;
                    }
                    if (isInner) {
                        tempFields = this.handleInnerField(fieldParam, entity);
                        if (entity != null && !entityMap.containsKey(entity.getCode())) {
                            entityMap.put(entity.getCode(), entity);
                            if (!entity.getId().endsWith("@ORG")) {
                                this.addInnerDimObjectCodeField(tempFields, entity);
                                this.addInnerDimParentCodeField(tempFields, entity, tempFields.get(0).getExp());
                            }
                        }
                    } else {
                        tempFields = this.handleZbField(fieldParam, dataField);
                    }
                }
                expFields.addAll(tempFields);
                for (ReportExpField field : tempFields) {
                    ExpFieldVo expFieldVo = new ExpFieldVo();
                    expFieldVo.setKey(field.getCode());
                    expFieldVo.setExpresion(field.getExp());
                    List parseExpFieldVos = parseExpFieldMap.computeIfAbsent(fieldParam.getTaskKey(), k -> new ArrayList());
                    parseExpFieldVos.add(expFieldVo);
                    List fields = expFieldTaskMap.computeIfAbsent(fieldParam.getTaskKey(), k -> new ArrayList());
                    fields.add(field);
                }
            }
            for (String taskId : parseExpFieldMap.keySet()) {
                List parseExpFieldParam = (List)parseExpFieldMap.get(taskId);
                List fields = (List)expFieldTaskMap.get(taskId);
                List<ExpParsedFieldVo> parsedFields = this.expParseProvider.doParse(taskId, parseExpFieldParam, params);
                for (int i = 0; i < parsedFields.size(); ++i) {
                    ReportExpField field = (ReportExpField)fields.get(i);
                    ExpParsedFieldVo parsedField = parsedFields.get(i);
                    field.setDataType(parsedField.getDatatype());
                    field.setFieldType(parsedField.getFieldType());
                }
            }
        }
        catch (Exception e) {
            throw new ReportDsModelDataBuildException(e);
        }
        return expFields;
    }

    private void buildEntityFieldsAndParameters(List<ReportExpField> fields, IEntityModel entityModel, List<ReportDsParameter> parameters, IEntityDefine entity, boolean isMainDim) {
        this.buildDimField(fields, entity.getCode(), "OBJECTCODE", false, entity);
        this.buildDimField(fields, entity.getCode(), "CODE", false, entity);
        this.buildDimField(fields, entity.getCode(), "NAME", false, entity);
        this.buildDimField(fields, entity.getCode(), SHORTNAME, false, entity);
        this.buildDimField(fields, entity.getCode(), "PARENTCODE", false, entity);
        if (isMainDim) {
            this.buildDimField(fields, entity.getCode(), entityModel.getOrderField().getCode(), true, entity);
        }
        this.buildDimParameter(parameters, entity.getCode(), false, entity);
    }

    private void buildDataTimeField(List<ReportExpField> fields, String dataTimeKey, boolean isCustom) {
        ReportExpField dataTimeField = new ReportExpField();
        dataTimeField.setCode(NrPeriodConst.PREFIX_CODE + dataTimeKey + "_" + PeriodTableColumn.TIMEKEY.getCode());
        dataTimeField.setTitle("\u65f6\u671f");
        dataTimeField.setZbTitle(dataTimeField.getTitle());
        dataTimeField.setExp("[CUR_TIMEKEY]");
        dataTimeField.setFieldType(FieldType.TIME_DIM);
        if (isCustom) {
            dataTimeField.setFieldType(FieldType.GENERAL_DIM);
        }
        dataTimeField.setDataType(6);
        dataTimeField.setMessageAlias(PeriodTableColumn.TIMEKEY.getCode());
        dataTimeField.setKeyField(dataTimeField.getCode());
        dataTimeField.setReportFieldType(ReportFieldType.TIMEKEY);
        dataTimeField.setKeyField(dataTimeField.getCode());
        fields.add(dataTimeField);
        if (isCustom) {
            ReportExpField dataTimeTitleField = new ReportExpField();
            dataTimeTitleField.setCode("PERIOD_TITLE");
            dataTimeTitleField.setTitle("\u65f6\u671f\u6807\u9898");
            dataTimeTitleField.setZbTitle(dataTimeTitleField.getTitle());
            dataTimeTitleField.setExp("[CUR_PERIODTITLE]");
            dataTimeTitleField.setFieldType(FieldType.GENERAL_DIM);
            dataTimeTitleField.setDataType(6);
            dataTimeTitleField.setKeyField(dataTimeField.getCode());
            dataTimeTitleField.setReportFieldType(ReportFieldType.TIMEKEY);
            fields.add(dataTimeTitleField);
        }
    }

    private void buildDataTimeParameter(List<ReportDsParameter> parameters, String dataTimeKey) {
        ReportDsParameter dataTimeParameter = new ReportDsParameter();
        dataTimeParameter.setName(NrPeriodConst.PREFIX_CODE + dataTimeKey);
        dataTimeParameter.setTitle("\u65f6\u671f");
        dataTimeParameter.setDataType(6);
        dataTimeParameter.setSelectMode(ParameterSelectMode.SINGLE);
        dataTimeParameter.setDefaultValueMode(DefaultValueMode.CURRENT);
        dataTimeParameter.setMessageAlias(PeriodTableColumn.TIMEKEY.getCode());
        dataTimeParameter.setEntityId(dataTimeKey);
        parameters.add(dataTimeParameter);
    }

    private void buildDimField(List<ReportExpField> fields, String dimKey, String fieldCode, boolean isOrg, IEntityDefine entity) {
        ReportExpField dwField = new ReportExpField();
        if (isOrg) {
            dwField.setTitle(entity.getTitle() + fieldCode);
            dwField.setMessageAlias("MD_ORG." + fieldCode);
            dwField.setReportFieldType(ReportFieldType.UNIT);
            dwField.setKeyField(dimKey + "_CODE");
        } else {
            dwField.setTitle(entity.getTitle() + fieldCode);
            dwField.setMessageAlias(dimKey + "." + fieldCode);
            dwField.setReportFieldType(ReportFieldType.DIMENSION);
            dwField.setKeyField(dimKey + "_OBJECTCODE");
        }
        dwField.setZbTitle(dwField.getTitle());
        dwField.setCode(dimKey + "_" + fieldCode);
        dwField.setExp(String.format("%s[%s]", dimKey, fieldCode));
        dwField.setFieldType(FieldType.GENERAL_DIM);
        dwField.setDataType(6);
        fields.add(dwField);
    }

    private void buildDimParameter(List<ReportDsParameter> parameters, String dimKey, boolean isOrg, IEntityDefine entity) {
        ReportDsParameter dimParameter = new ReportDsParameter();
        dimParameter.setName(dimKey);
        dimParameter.setTitle(entity.getTitle());
        dimParameter.setDataType(6);
        dimParameter.setSelectMode(ParameterSelectMode.SINGLE);
        dimParameter.setDefaultValueMode(DefaultValueMode.FIRST);
        dimParameter.setEntityId(dimKey);
        dimParameter.setMessageAlias(dimKey + ".OBJECTCODE");
        if (isOrg) {
            dimParameter.setSelectMode(ParameterSelectMode.MUTIPLE);
            dimParameter.setDefaultValueMode(DefaultValueMode.FIRST_CHILD);
            dimParameter.setMessageAlias(dimKey + ".ORGCODE");
        }
        parameters.add(dimParameter);
    }

    private List<ReportExpField> handleFMDMField(IReportDsModelFieldParam fieldParam, DataField dataField) {
        String dimKey;
        IEntityDefine entityDefine;
        ArrayList<ReportExpField> fields = new ArrayList<ReportExpField>();
        if (fieldParam.isFmdmField() && (entityDefine = this.entityMetaService.queryEntity(dimKey = fieldParam.getDim())) != null) {
            IEntityModel entityModel = this.entityMetaService.getEntityModel(dimKey);
            IEntityAttribute attribute = entityModel.getAttribute(fieldParam.getCode());
            String referTableId = attribute.getReferTableID();
            if (referTableId != null) {
                TableModelDefine referTableModel = this.dataModelService.getTableModelDefineById(referTableId);
                String referTableCode = referTableModel.getCode();
                String childDimKey = this.entityMetaService.getEntityIdByCode(referTableCode);
                ReportExpField codeField = new ReportExpField();
                codeField.setCode(String.format("%s_%s_%s", fieldParam.getTableName(), fieldParam.getCode(), "CODE"));
                codeField.setTitle(String.format("%s%s", fieldParam.getTitle(), "CODE"));
                codeField.setZbTitle(codeField.getTitle());
                codeField.setExp(fieldParam.getExpression());
                codeField.setReportFieldType(ReportFieldType.COMMON);
                codeField.setKeyField(codeField.getCode());
                codeField.setMessageAlias(String.format("%s.%s", referTableCode, "CODE"));
                fields.add(codeField);
                ReportExpField nameField = new ReportExpField();
                nameField.setCode(String.format("%s_%s_%s", fieldParam.getTableName(), fieldParam.getCode(), "NAME"));
                nameField.setTitle(String.format("%s%s", fieldParam.getTitle(), "NAME"));
                nameField.setZbTitle(codeField.getTitle());
                nameField.setExp(fieldParam.getExpression());
                nameField.setReportFieldType(ReportFieldType.COMMON);
                nameField.setKeyField(codeField.getCode());
                nameField.setMessageAlias(String.format("%s.%s", referTableCode, "NAME"));
                fields.add(nameField);
                IEntityDefine referEntityDefine = this.entityMetaService.queryEntity(childDimKey);
                if (referEntityDefine.isTree().booleanValue()) {
                    ReportExpField parentField = new ReportExpField();
                    parentField.setCode(String.format("%s_%s_%s", fieldParam.getTableName(), fieldParam.getCode(), "PARENTCODE"));
                    parentField.setTitle(String.format("%s%s", fieldParam.getTitle(), "PARENTCODE"));
                    parentField.setZbTitle(parentField.getTitle());
                    parentField.setExp(String.format("GETMASTERDATA(\"%s\", %s, \"%s\")", referTableCode, codeField.getExp(), "PARENTCODE"));
                    parentField.setReportFieldType(ReportFieldType.COMMON);
                    parentField.setKeyField(codeField.getCode());
                    parentField.setMessageAlias(String.format("%s.%s", referTableCode, "PARENTCODE"));
                    fields.add(parentField);
                }
            } else {
                ReportExpField expField = new ReportExpField();
                expField.setCode(fieldParam.getTableName() + "_" + fieldParam.getCode());
                expField.setTitle(fieldParam.getTitle());
                expField.setZbTitle(fieldParam.getTitle());
                expField.setExp(fieldParam.getExpression());
                expField.setReportFieldType(ReportFieldType.COMMON);
                if (fieldParam.getTableName().startsWith("MD_ORG_")) {
                    expField.setKeyField(String.format("%s_%s", fieldParam.getTableName(), "CODE"));
                    expField.setMessageAlias(String.format("MD_ORG.%s", fieldParam.getCode()));
                } else {
                    expField.setKeyField(String.format("%s_%s", fieldParam.getTableName(), "OBJECTCODE"));
                    expField.setMessageAlias(String.format("%s.%s", fieldParam.getTableName(), fieldParam.getCode()));
                }
                fields.add(expField);
            }
        }
        return fields;
    }

    private List<ReportExpField> handleInnerField(IReportDsModelFieldParam fieldParam, IEntityDefine entity) {
        ArrayList<ReportExpField> fields = new ArrayList<ReportExpField>();
        ReportExpField codeField = new ReportExpField();
        codeField.setCode(String.format("%s_%s_%s", fieldParam.getTableName(), fieldParam.getCode(), "CODE"));
        codeField.setTitle(fieldParam.getTitle() + "CODE");
        codeField.setZbTitle(codeField.getTitle());
        codeField.setExp(fieldParam.getExpression());
        codeField.setReportFieldType(ReportFieldType.INNER_DIM);
        if (entity != null) {
            if (entity.getId().endsWith("@ORG")) {
                codeField.setKeyField(String.format("%s_%s", entity.getCode(), "CODE"));
                codeField.setMessageAlias(String.format("MD_ORG.%s", "CODE"));
            } else {
                codeField.setKeyField(String.format("%s_%s", entity.getCode(), "OBJECTCODE"));
                codeField.setMessageAlias(String.format("%s.%s", entity.getCode(), "CODE"));
            }
        }
        fields.add(codeField);
        ReportExpField nameField = new ReportExpField();
        nameField.setCode(String.format("%s_%s_%s", fieldParam.getTableName(), fieldParam.getCode(), "NAME"));
        nameField.setTitle(fieldParam.getTitle() + "NAME");
        nameField.setZbTitle(nameField.getTitle());
        nameField.setExp(String.format("%s$", codeField.getExp()));
        nameField.setReportFieldType(ReportFieldType.INNER_DIM);
        nameField.setKeyField(codeField.getKeyField());
        if (entity != null) {
            if (entity.getId().endsWith("@ORG")) {
                nameField.setMessageAlias(String.format("MD_ORG.%s", "NAME"));
            } else {
                nameField.setMessageAlias(String.format("%s.%s", entity.getCode(), "NAME"));
            }
        }
        fields.add(nameField);
        return fields;
    }

    private void addInnerDimObjectCodeField(List<ReportExpField> expFields, IEntityDefine entity) {
        if (entity != null) {
            ReportExpField objectCodeField = new ReportExpField();
            objectCodeField.setCode(entity.getCode() + "_OBJECTCODE");
            objectCodeField.setTitle(entity.getTitle() + "OBJECTCODE");
            objectCodeField.setZbTitle(objectCodeField.getTitle());
            objectCodeField.setExp(String.format("%s[%s]", entity.getCode(), "OBJECTCODE"));
            objectCodeField.setMessageAlias(entity.getCode() + ".OBJECTCODE");
            objectCodeField.setKeyField(entity.getCode() + "_OBJECTCODE");
            objectCodeField.setReportFieldType(ReportFieldType.INNER_DIM);
            expFields.add(objectCodeField);
        }
    }

    private void addInnerDimParentCodeField(List<ReportExpField> expFields, IEntityDefine entity, String codeExp) {
        if (entity != null) {
            IEntityModel entityModel = this.entityMetaService.getEntityModel(entity.getId());
            IEntityAttribute parentAttr = entityModel.getParentField();
            ReportExpField parentField = new ReportExpField();
            parentField.setCode(String.format("%s_%s", entity.getCode(), parentAttr.getCode()));
            parentField.setTitle(String.format("%s%s", entity.getTitle(), parentAttr.getTitle()));
            parentField.setZbTitle(parentField.getTitle());
            parentField.setExp(String.format("GETMASTERDATA(\"%s\", %s, \"%s\")", entity.getCode(), codeExp, parentAttr.getCode()));
            parentField.setReportFieldType(ReportFieldType.INNER_DIM);
            parentField.setKeyField(String.format("%s_%s", entity.getCode(), "OBJECTCODE"));
            parentField.setMessageAlias(String.format("%s.%s", entity.getCode(), parentAttr.getCode()));
            expFields.add(parentField);
        }
    }

    private List<ReportExpField> handleZbField(IReportDsModelFieldParam fieldParam, DataField dataField) {
        ArrayList<ReportExpField> fields = new ArrayList();
        String referEntityKey = dataField.getRefDataEntityKey();
        if (StringUtils.hasLength(referEntityKey)) {
            IEntityDefine referEntity = this.entityMetaService.queryEntity(referEntityKey);
            fields = this.buildReferEntityCodeAndNameField(fieldParam, referEntity);
        } else {
            ReportExpField expField = new ReportExpField();
            expField.setCode(fieldParam.getTableName() + "_" + fieldParam.getCode());
            expField.setTitle(fieldParam.getTitle());
            expField.setZbTitle(fieldParam.getTitle());
            expField.setExp(fieldParam.getExpression());
            expField.setMessageAlias(String.format("%s.%s", fieldParam.getTableName(), fieldParam.getCode()));
            expField.setReportFieldType(ReportFieldType.COMMON);
            fields.add(expField);
        }
        return fields;
    }

    private List<ReportExpField> buildReferEntityCodeAndNameField(IReportDsModelFieldParam fieldParam, IEntityDefine referEntity) {
        ArrayList<ReportExpField> fields = new ArrayList<ReportExpField>();
        ReportExpField codeField = new ReportExpField();
        codeField.setCode(String.format("%s_%s_%s", fieldParam.getTableName(), fieldParam.getCode(), "CODE"));
        codeField.setTitle(fieldParam.getTitle() + "CODE");
        codeField.setZbTitle(codeField.getTitle());
        codeField.setExp(fieldParam.getExpression());
        codeField.setReportFieldType(ReportFieldType.COMMON);
        codeField.setMessageAlias(String.format("%s.%s", fieldParam.getTableName(), "CODE"));
        codeField.setKeyField(codeField.getCode());
        fields.add(codeField);
        ReportExpField nameField = new ReportExpField();
        nameField.setCode(String.format("%s_%s_%s", fieldParam.getTableName(), fieldParam.getCode(), "NAME"));
        nameField.setTitle(fieldParam.getTitle() + "NAME");
        nameField.setZbTitle(nameField.getTitle());
        nameField.setExp(String.format("%s$", codeField.getExp()));
        nameField.setReportFieldType(ReportFieldType.COMMON);
        nameField.setMessageAlias(String.format("%s.%s", fieldParam.getTableName(), "NAME"));
        nameField.setKeyField(codeField.getCode());
        fields.add(nameField);
        if (referEntity.isTree().booleanValue()) {
            ReportExpField parentField = new ReportExpField();
            parentField.setCode(String.format("%s_%s_%s", fieldParam.getTableName(), fieldParam.getCode(), "PARENTCODE"));
            parentField.setTitle(fieldParam.getTitle() + "PARENTCODE");
            parentField.setZbTitle(parentField.getTitle());
            parentField.setExp(String.format("GETMASTERDATA(\"%s\", %s, \"%s\")", referEntity.getCode(), codeField.getExp(), "PARENTCODE"));
            parentField.setReportFieldType(ReportFieldType.COMMON);
            parentField.setKeyField(codeField.getCode());
            parentField.setMessageAlias(String.format("%s.%s", fieldParam.getTableName(), "PARENTCODE"));
            fields.add(parentField);
        }
        return fields;
    }

    private boolean isMergeUnitScene(TaskDefine taskDefine, IEntityDefine entityDefine, Map<String, DataDimension> dimensionMap) {
        String masterDim = taskDefine.getDw();
        IEntityModel entityModel = this.entityMetaService.getEntityModel(masterDim);
        DataDimension dataDimension = dimensionMap.get(entityDefine.getId());
        Iterator attributes = entityModel.getAttributes();
        while (attributes.hasNext()) {
            IEntityAttribute attribute = (IEntityAttribute)attributes.next();
            String dimAttribute = dataDimension.getDimAttribute();
            if (!StringUtils.hasLength(dimAttribute) || !dimAttribute.equals(attribute.getCode()) || attribute.isMultival()) continue;
            return true;
        }
        return false;
    }
}

