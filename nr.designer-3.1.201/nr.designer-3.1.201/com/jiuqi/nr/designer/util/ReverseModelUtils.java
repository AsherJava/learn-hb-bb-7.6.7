/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.np.definition.internal.format.FixMode
 *  com.jiuqi.np.definition.internal.format.NegativeStyle
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.np.definition.internal.parser.NumberFormatParser
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.ValidationRule
 *  com.jiuqi.nr.datascheme.api.service.FormatPropertiesBuilder
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.CompareType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO
 *  com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO
 *  com.jiuqi.nr.datascheme.web.facade.FormatVO
 *  com.jiuqi.nr.datascheme.web.facade.ValidationRuleVO
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.impl.DesignDataLinkDefineImpl
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.json.Grid2DataDeserialize
 *  com.jiuqi.nvwa.grid2.json.Grid2DataSerialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataDeserialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataSerialize
 *  com.jiuqi.util.OrderGenerator
 */
package com.jiuqi.nr.designer.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.np.definition.internal.format.FixMode;
import com.jiuqi.np.definition.internal.format.NegativeStyle;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.np.definition.internal.parser.NumberFormatParser;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.ValidationRule;
import com.jiuqi.nr.datascheme.api.service.FormatPropertiesBuilder;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.CompareType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import com.jiuqi.nr.datascheme.web.facade.FormatVO;
import com.jiuqi.nr.datascheme.web.facade.ValidationRuleVO;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.impl.DesignDataLinkDefineImpl;
import com.jiuqi.nr.designer.common.ReverseItemState;
import com.jiuqi.nr.designer.web.rest.vo.ReverseDataFieldVO;
import com.jiuqi.nr.designer.web.rest.vo.ReverseDataTableVO;
import com.jiuqi.nr.designer.web.rest.vo.ReverseFormVO;
import com.jiuqi.nr.designer.web.rest.vo.ReverseLinkVO;
import com.jiuqi.nr.designer.web.rest.vo.ReverseRegionVO;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.json.Grid2DataDeserialize;
import com.jiuqi.nvwa.grid2.json.Grid2DataSerialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataDeserialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataSerialize;
import com.jiuqi.util.OrderGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class ReverseModelUtils {
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IEntityMetaService iEntityMetaService;

    public String serializeFormObject(ReverseFormVO reverseFormVO) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
        module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
        mapper.registerModule((Module)module);
        return mapper.writeValueAsString((Object)reverseFormVO);
    }

    private String serializeGrid2Data(Grid2Data grid2Data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
        module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
        mapper.registerModule((Module)module);
        return mapper.writeValueAsString((Object)grid2Data);
    }

    public static Grid2Data deserializeGrid2Data(String formStyle) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Grid2Data.class, (JsonDeserializer)new Grid2DataDeserialize());
        module.addDeserializer(GridCellData.class, (JsonDeserializer)new GridCellDataDeserialize());
        mapper.registerModule((Module)module);
        return (Grid2Data)mapper.readValue(formStyle, Grid2Data.class);
    }

    public void fillForm(ReverseFormVO reverseFormVO, DesignFormDefine formDefine) throws JsonProcessingException {
        reverseFormVO.setFormKey(formDefine.getKey());
        reverseFormVO.setFormCode(formDefine.getFormCode());
        reverseFormVO.setFormTitle(formDefine.getTitle());
        reverseFormVO.setFormType(formDefine.getFormType().getValue());
        reverseFormVO.setFormSchemeKey(formDefine.getFormScheme());
        try {
            DesignFormSchemeDefine formSchemeDefine = this.designTimeViewController.queryFormSchemeDefine(formDefine.getFormScheme());
            List formGroupsByFormId = this.designTimeViewController.getFormGroupsByFormId(formDefine.getKey());
            DesignTaskDefine taskDefine = this.designTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
            DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(taskDefine.getDataScheme());
            reverseFormVO.setDataSchemeKey(dataScheme.getKey());
            reverseFormVO.setDataSchemePrefix(dataScheme.getPrefix());
            reverseFormVO.setFormSchemeTitle(formSchemeDefine.getTitle());
            if (formGroupsByFormId.size() > 0) {
                reverseFormVO.setFormGroupTitle(((DesignFormGroupDefine)formGroupsByFormId.get(0)).getTitle());
            }
        }
        catch (Exception formSchemeDefine) {
            // empty catch block
        }
        Grid2Data grid2Data = Grid2Data.bytesToGrid((byte[])this.designTimeViewController.getReportDataFromForm(formDefine.getKey(), reverseFormVO.getLanguage()));
        reverseFormVO.setFormStyle(grid2Data);
        HashMap<String, ReverseRegionVO> regionMap = null;
        String key = formDefine.getKey();
        List regionList = this.nrDesignTimeController.getAllRegionsInForm(key);
        if (regionList != null) {
            regionMap = new HashMap<String, ReverseRegionVO>();
            for (DesignDataRegionDefine regionDefine : regionList) {
                regionMap.put(regionDefine.getKey(), this.fillRegion(regionDefine));
            }
        }
        reverseFormVO.setRegions(regionMap);
    }

    public ReverseRegionVO fillRegion(DesignDataRegionDefine regionDefine) {
        ReverseRegionVO reverseRegionVO = new ReverseRegionVO();
        reverseRegionVO.setRegionKind(regionDefine.getRegionKind());
        reverseRegionVO.setRegionLeft(regionDefine.getRegionLeft());
        reverseRegionVO.setRegionTop(regionDefine.getRegionTop());
        reverseRegionVO.setRegionRight(regionDefine.getRegionRight());
        reverseRegionVO.setRegionBottom(regionDefine.getRegionBottom());
        reverseRegionVO.setKey(regionDefine.getKey());
        reverseRegionVO.setFormKey(regionDefine.getFormKey());
        List allLinksInRegion = this.nrDesignTimeController.getAllLinksInRegion(regionDefine.getKey());
        LinkedHashMap<String, ReverseLinkVO> reverseLinkVOMap = new LinkedHashMap<String, ReverseLinkVO>();
        for (DesignDataLinkDefine dataLinkDefine : allLinksInRegion) {
            ReverseLinkVO linkVO = this.fillLink(dataLinkDefine);
            reverseLinkVOMap.put(dataLinkDefine.getKey(), linkVO);
        }
        reverseRegionVO.setLinks(reverseLinkVOMap);
        ArrayList fieldKeys = new ArrayList();
        allLinksInRegion.forEach(l -> {
            if (!fieldKeys.contains(l.getLinkExpression()) && StringUtils.hasLength(l.getLinkExpression())) {
                fieldKeys.add(l.getLinkExpression());
            }
        });
        HashMap tableFieldMap = new HashMap();
        ArrayList tableKeys = new ArrayList();
        List dataFields = this.designDataSchemeService.getDataFields(fieldKeys);
        dataFields.forEach(dataField -> {
            if (!tableKeys.contains(dataField.getDataTableKey())) {
                tableKeys.add(dataField.getDataTableKey());
            }
            if (!tableFieldMap.containsKey(dataField.getDataTableKey())) {
                tableFieldMap.put(dataField.getDataTableKey(), this.designDataSchemeService.getDataFieldByTable(dataField.getDataTableKey()));
            }
        });
        HashMap<String, ReverseDataTableVO> dataTableVOMap = new HashMap<String, ReverseDataTableVO>();
        List dataTables = this.designDataSchemeService.getDataTables(tableKeys);
        dataTables.forEach(dataTable -> {
            ReverseDataTableVO reverseDataTableVO = new ReverseDataTableVO();
            reverseDataTableVO.setState(ReverseItemState.DEFAULT);
            reverseDataTableVO.setKey(dataTable.getKey());
            reverseDataTableVO.setDataTableType(dataTable.getDataTableType());
            reverseDataTableVO.setDataTableGatherType(dataTable.getDataTableGatherType());
            reverseDataTableVO.setCode(dataTable.getCode());
            reverseDataTableVO.setDataGroupKey(dataTable.getDataGroupKey());
            reverseDataTableVO.setDesc(dataTable.getDesc());
            reverseDataTableVO.setDataSchemeKey(dataTable.getDataSchemeKey());
            reverseDataTableVO.setOwner(dataTable.getOwner());
            reverseDataTableVO.setRepeatCode(dataTable.getRepeatCode());
            reverseDataTableVO.setTitle(dataTable.getTitle());
            reverseDataTableVO.setDataFields(this.fillField((List)tableFieldMap.get(dataTable.getKey()), (DesignDataTable)dataTable));
            dataTableVOMap.put(dataTable.getKey(), reverseDataTableVO);
        });
        reverseRegionVO.setTables(dataTableVOMap);
        return reverseRegionVO;
    }

    public Map<String, ReverseDataFieldVO> fillField(List<DesignDataField> designDataFields, DesignDataTable dataTable) {
        HashMap<String, ReverseDataFieldVO> map = new HashMap<String, ReverseDataFieldVO>();
        for (DesignDataField designDataField : designDataFields) {
            DataFieldType dataFieldType;
            String measureUnit;
            ReverseDataFieldVO dataFieldVO = new ReverseDataFieldVO();
            dataFieldVO.setState(ReverseItemState.DEFAULT);
            if (designDataField.getDataFieldGatherType() != null) {
                dataFieldVO.setGatherTypeTitle(designDataField.getDataFieldGatherType().getTitle());
            }
            dataFieldVO.setDataFieldKind(designDataField.getDataFieldKind());
            dataFieldVO.setDataFieldType(designDataField.getDataFieldType());
            dataFieldVO.setFieldName(designDataField.getTitle());
            dataFieldVO.setRefDataEntityTitle(designDataField.getRefDataEntityKey());
            dataFieldVO.setApplyType(designDataField.getDataFieldApplyType());
            dataFieldVO.setFormatVO(this.fillFormatVO(designDataField.getFormatProperties()));
            dataFieldVO.setTitle(designDataField.getTitle());
            dataFieldVO.setTableName(dataTable.getTitle());
            dataFieldVO.setDataTableKey(designDataField.getDataTableKey());
            dataFieldVO.setKey(designDataField.getKey());
            dataFieldVO.setCode(designDataField.getCode());
            dataFieldVO.setNullable(designDataField.getNullable());
            dataFieldVO.setSecretLevel(designDataField.getSecretLevel());
            dataFieldVO.setPrecision(designDataField.getPrecision());
            dataFieldVO.setDataFieldGatherType(designDataField.getDataFieldGatherType());
            dataFieldVO.setDecimal(designDataField.getDecimal());
            dataFieldVO.setUseAuthority(designDataField.getUseAuthority());
            dataFieldVO.setRefDataFieldKey(designDataField.getRefDataFieldKey());
            dataFieldVO.setRefDataEntityKey(designDataField.getRefDataEntityKey());
            List validationRules = designDataField.getValidationRules();
            if (!CollectionUtils.isEmpty(validationRules)) {
                ArrayList<ValidationRuleVO> list = new ArrayList<ValidationRuleVO>(validationRules.size());
                for (ValidationRule validationRule : validationRules) {
                    if (validationRule.getCompareType() == null || validationRule.getCompareType() == CompareType.NOTNULL) continue;
                    ValidationRuleVO validationRuleVO = new ValidationRuleVO(validationRule);
                    list.add(validationRuleVO);
                }
                dataFieldVO.setValidationRules(list);
            }
            dataFieldVO.setAllowMultipleSelect(designDataField.getAllowMultipleSelect());
            dataFieldVO.setAllowUndefinedCode(designDataField.getAllowUndefinedCode());
            if (StringUtils.hasLength(designDataField.getRefDataEntityKey())) {
                try {
                    IEntityDefine queryEntity = this.iEntityMetaService.queryEntity(designDataField.getRefDataEntityKey());
                    dataFieldVO.setRefDataEntityTitle(queryEntity.getTitle());
                }
                catch (Exception queryEntity) {
                    // empty catch block
                }
            }
            if (StringUtils.hasLength(measureUnit = designDataField.getMeasureUnit())) {
                dataFieldVO.setMeasureUnit(measureUnit.replace("9493b4eb-6516-48a8-a878-25a63a23e63a;", ""));
            }
            if ((dataFieldType = dataFieldVO.getDataFieldType()) == DataFieldType.INTEGER || dataFieldType == DataFieldType.BIGDECIMAL) {
                if (!StringUtils.hasLength(dataFieldVO.getMeasureUnit()) || "NotDimession".equals(dataFieldVO.getMeasureUnit())) {
                    dataFieldVO.setDimension(1);
                } else {
                    dataFieldVO.setDimension(0);
                }
            }
            map.put(dataFieldVO.getKey(), dataFieldVO);
        }
        return map;
    }

    public FormatVO fillFormatVO(FormatProperties formatProperties) {
        NegativeStyle negativeStyle;
        FixMode fixMode;
        NumberFormatParser parse = NumberFormatParser.parse((FormatProperties)formatProperties);
        FormatVO formatVO = new FormatVO();
        formatVO.setFormatType(parse.getFormatType().intValue());
        formatVO.setCurrency(parse.getCurrency());
        formatVO.setThousands(parse.isThousands().booleanValue());
        Integer displayDigits = parse.getDisplayDigits();
        if (displayDigits != null) {
            formatVO.setDisplayDigits(displayDigits.intValue());
        }
        if ((fixMode = parse.getFixMode()) != null) {
            formatVO.setFixMode(fixMode.getValue());
        }
        if ((negativeStyle = parse.getNegativeStyle()) != null) {
            formatVO.setNegativeStyle(negativeStyle.getValue());
        }
        if (formatProperties != null && formatProperties.getFormatType() == 6) {
            formatVO.setPattern(formatProperties.getPattern());
        }
        return formatVO;
    }

    public ReverseLinkVO fillLink(DesignDataLinkDefine dataLinkDefine) {
        ReverseLinkVO reverseLinkVO = new ReverseLinkVO();
        reverseLinkVO.setLinkExpression(dataLinkDefine.getLinkExpression());
        reverseLinkVO.setState(ReverseItemState.DEFAULT);
        reverseLinkVO.setAllowMultipleSelect(dataLinkDefine.getAllowMultipleSelect());
        reverseLinkVO.setColNum(dataLinkDefine.getColNum());
        reverseLinkVO.setRowNum(dataLinkDefine.getRowNum());
        reverseLinkVO.setCaptionFieldsString(dataLinkDefine.getCaptionFieldsString());
        reverseLinkVO.setFormatProperties(dataLinkDefine.getFormatProperties());
        reverseLinkVO.setPosX(dataLinkDefine.getPosX());
        reverseLinkVO.setPosY(dataLinkDefine.getPosY());
        reverseLinkVO.setRegionKey(dataLinkDefine.getRegionKey());
        reverseLinkVO.setDropDownFieldsString(dataLinkDefine.getDropDownFieldsString());
        reverseLinkVO.setAllowNotLeafNodeRefer(dataLinkDefine.getAllowNotLeafNodeRefer());
        reverseLinkVO.setIsFormulaOrField(dataLinkDefine.getType().getValue());
        return reverseLinkVO;
    }

    public DesignDataTableDO convertTableDO(ReverseDataTableVO table) {
        DesignDataTableDO dataTableDO = new DesignDataTableDO();
        dataTableDO.setKey(table.getKey());
        dataTableDO.setDataSchemeKey(table.getDataSchemeKey());
        dataTableDO.setDataGroupKey(table.getDataGroupKey());
        dataTableDO.setCode(table.getCode());
        dataTableDO.setTitle(table.getTitle());
        dataTableDO.setDataTableType(table.getDataTableType());
        dataTableDO.setDesc(table.getDesc());
        dataTableDO.setDataTableGatherType(table.getDataTableGatherType());
        dataTableDO.setRepeatCode(table.getRepeatCode());
        dataTableDO.setOwner(table.getOwner());
        return dataTableDO;
    }

    public DesignDataFieldDO convertFieldDO(ReverseDataFieldVO field) {
        DesignDataFieldDO designDataFieldDO = new DesignDataFieldDO();
        designDataFieldDO.setKey(field.getKey());
        designDataFieldDO.setCode(field.getCode());
        designDataFieldDO.setAlias(field.getAlias());
        designDataFieldDO.setTitle(field.getTitle());
        designDataFieldDO.setOrder(field.getOrder());
        designDataFieldDO.setDataSchemeKey(field.getDataSchemeKey());
        designDataFieldDO.setDataTableKey(field.getDataTableKey());
        designDataFieldDO.setDataFieldKind(field.getDataFieldKind());
        designDataFieldDO.setVersion(field.getVersion());
        designDataFieldDO.setLevel(field.getLevel());
        designDataFieldDO.setUpdateTime(field.getUpdatetime());
        designDataFieldDO.setDesc(field.getDesc());
        designDataFieldDO.setDefaultValue(field.getDefaultValue());
        designDataFieldDO.setPrecision(field.getPrecision());
        designDataFieldDO.setDataFieldType(field.getDataFieldType());
        designDataFieldDO.setDecimal(field.getDecimal());
        designDataFieldDO.setNullable(field.getNullable());
        designDataFieldDO.setRefDataFieldKey(field.getRefDataFieldKey());
        designDataFieldDO.setMeasureUnit(this.getMeasureUnit(field));
        designDataFieldDO.setDataFieldGatherType(field.getDataFieldGatherType());
        designDataFieldDO.setAllowMultipleSelect(field.getAllowMultipleSelect());
        designDataFieldDO.setSecretLevel(field.getSecretLevel());
        designDataFieldDO.setUseAuthority(field.getUseAuthority());
        designDataFieldDO.setDataFieldApplyType(field.getApplyType());
        designDataFieldDO.setRefDataEntityKey(field.getRefDataEntityKey());
        designDataFieldDO.setAllowUndefinedCode(field.getAllowUndefinedCode());
        FormatVO formatVO = field.getFormatVO();
        FormatProperties formatProperties = new FormatPropertiesBuilder().setFormatType(formatVO.getFormatType()).setCurrency(formatVO.getCurrency()).setFixMode(formatVO.getFixMode()).setDisplayDigits(formatVO.getDisplayDigits()).setNegativeStyle(formatVO.getNegativeStyle()).setThousands(formatVO.isThousands()).setFieldType(field.getDataFieldType()).setPattern(formatVO.getPattern()).build();
        designDataFieldDO.setFormatProperties(formatProperties);
        ArrayList validationRules = new ArrayList();
        if (field.getValidationRules() != null) {
            field.getValidationRules().forEach(v -> validationRules.add(v.toValidationRuleDTO()));
        }
        designDataFieldDO.setValidationRules(validationRules);
        return designDataFieldDO;
    }

    private String getMeasureUnit(ReverseDataFieldVO field) {
        if (field.getDimension() == null) {
            return null;
        }
        if (field.getDimension() == 0) {
            return field.getMeasureUnit() == null ? "9493b4eb-6516-48a8-a878-25a63a23e63a;-" : "9493b4eb-6516-48a8-a878-25a63a23e63a;" + field.getMeasureUnit();
        }
        return "NotDimession";
    }

    public DesignDataLinkDefineImpl convertLinkDO(ReverseLinkVO link) {
        DesignDataLinkDefineImpl designDataLinkDefineImpl = new DesignDataLinkDefineImpl();
        designDataLinkDefineImpl.setRegionKey(link.getRegionKey());
        designDataLinkDefineImpl.setLinkExpression(link.getLinkExpression());
        designDataLinkDefineImpl.setPosX(link.getPosX());
        designDataLinkDefineImpl.setPosY(link.getPosY());
        designDataLinkDefineImpl.setColNum(link.getColNum());
        designDataLinkDefineImpl.setRowNum(link.getRowNum());
        designDataLinkDefineImpl.setCaptionFieldsString(link.getCaptionFieldsString());
        designDataLinkDefineImpl.setDropDownFieldsString(link.getDropDownFieldsString());
        designDataLinkDefineImpl.setAllowNotLeafNodeRefer(link.isAllowNotLeafNodeRefer());
        designDataLinkDefineImpl.setFormatProperties(link.getFormatProperties());
        designDataLinkDefineImpl.setUniqueCode(OrderGenerator.newOrder());
        designDataLinkDefineImpl.setType(DataLinkType.DATA_LINK_TYPE_FIELD);
        return designDataLinkDefineImpl;
    }

    public DesignDataGroup initDataGroup(String dataSchemeKey, String formSchemeTitle, String parentKey) {
        DesignDataGroup designDataGroup = this.designDataSchemeService.initDataGroup();
        designDataGroup.setDataSchemeKey(dataSchemeKey);
        designDataGroup.setParentKey(parentKey);
        designDataGroup.setTitle(formSchemeTitle);
        designDataGroup.setCode(OrderGenerator.newOrder());
        designDataGroup.setOrder(OrderGenerator.newOrder());
        return designDataGroup;
    }

    public List<DesignDataTable> updateDataTableByVO(List<ReverseDataTableVO> updateTables) {
        ArrayList keys = new ArrayList(16);
        updateTables.forEach(t -> keys.add(t.getKey()));
        List oldTables = this.designDataSchemeService.getDataTables(keys);
        for (int i = 0; i < oldTables.size() && i < updateTables.size(); ++i) {
            this.copyDataTableVO((DesignDataTable)oldTables.get(i), updateTables.get(i));
        }
        return oldTables;
    }

    private void copyDataTableVO(DesignDataTable designDataTable, ReverseDataTableVO reverseDataTableVO) {
        designDataTable.setDataSchemeKey(reverseDataTableVO.getDataSchemeKey());
        designDataTable.setDataGroupKey(reverseDataTableVO.getDataGroupKey());
        designDataTable.setDataTableType(reverseDataTableVO.getDataTableType());
        designDataTable.setDataTableGatherType(reverseDataTableVO.getDataTableGatherType());
        designDataTable.setRepeatCode(reverseDataTableVO.getRepeatCode());
        designDataTable.setOwner(reverseDataTableVO.getOwner());
        designDataTable.setTitle(reverseDataTableVO.getTitle());
        designDataTable.setKey(reverseDataTableVO.getKey());
        designDataTable.setDesc(reverseDataTableVO.getDesc());
        designDataTable.setCode(reverseDataTableVO.getCode());
    }
}

