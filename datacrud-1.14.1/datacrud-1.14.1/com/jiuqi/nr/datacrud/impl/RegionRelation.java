/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryFields
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.StringData
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.util.DataEngineFormulaParser
 *  com.jiuqi.np.definition.common.FormulaCheckType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.ValidationRule
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.definition.api.IRunTimeFormulaController
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IEntityViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataLinkMappingDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaField
 *  com.jiuqi.nr.definition.facade.FormulaParsedExp
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.RegionSettingDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeExpressionService
 *  com.jiuqi.nr.definition.internal.stream.param.FormulaSchemeStream
 *  com.jiuqi.nr.definition.util.DataValidationIntepretUtil
 *  com.jiuqi.nr.definition.util.EntityDefaultValue
 *  com.jiuqi.nr.definition.util.LevelSetting
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.ITreeStruct
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 */
package com.jiuqi.nr.datacrud.impl;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.StringData;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.np.definition.common.FormulaCheckType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.datacrud.GradeLink;
import com.jiuqi.nr.datacrud.LinkSort;
import com.jiuqi.nr.datacrud.ParamRelation;
import com.jiuqi.nr.datacrud.RegionGradeInfo;
import com.jiuqi.nr.datacrud.SortMode;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.measure.MeasureData;
import com.jiuqi.nr.datacrud.impl.measure.MeasureService;
import com.jiuqi.nr.datacrud.impl.measure.MeasureView;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.datacrud.impl.param.DataTableDTO;
import com.jiuqi.nr.datacrud.impl.param.TaskDefineProxy;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.ValidationRule;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.definition.api.IRunTimeFormulaController;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IEntityViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataLinkMappingDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaField;
import com.jiuqi.nr.definition.facade.FormulaParsedExp;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeExpressionService;
import com.jiuqi.nr.definition.internal.stream.param.FormulaSchemeStream;
import com.jiuqi.nr.definition.util.DataValidationIntepretUtil;
import com.jiuqi.nr.definition.util.EntityDefaultValue;
import com.jiuqi.nr.definition.util.LevelSetting;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.ITreeStruct;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class RegionRelation
implements ParamRelation {
    private static final Logger logger = LoggerFactory.getLogger(RegionRelation.class);
    private final IRunTimeViewController runTimeViewController;
    public final IRuntimeDataSchemeService runtimeDataSchemeService;
    private final MeasureService measureService;
    private final IDataDefinitionRuntimeController dataDefinitionController;
    private final IEntityViewController entityViewController;
    private IEntityMetaService entityMetaService;
    private final IFMDMAttributeService fmAttributeService;
    private final IRuntimeExpressionService expressionService;
    private final IRunTimeFormulaController runTimeFormulaController;
    private final IEntityViewRunTimeController entityViewRunTimeController;
    private final String regionKey;
    private TaskDefine taskDefine;
    private FormSchemeDefine formSchemeDefine;
    private DataRegionDefine regionDefine;
    private RegionSettingDefine regionSettingDefine;
    private FormDefine formDefine;
    private List<MetaData> metaData = null;
    private Map<String, MetaData> metaDataMap = null;
    private Map<String, MetaData> fieldKey2Meta = null;
    private MeasureView measureView;
    private MeasureData measureData;
    private String formulaSchemeKey;
    private FormulaSchemeDefine formulaSchemeDefine;
    private DataTable dataTable;
    private DataField bizKeyOrderField;
    private DataField floatOrderField;
    private DataField periodField;
    private List<DataField> dimFields;
    private RegionGradeInfo gradeInfo;
    private List<IParsedExpression> expressions;
    private List<MetaData> enumMeta = null;
    private String dwDwDimName;
    private Map<String, String> balanceExpression;
    private ReportFmlExecEnvironment environment;

    RegionRelation(IRunTimeViewController runTimeViewController, IRuntimeDataSchemeService runtimeDataSchemeService, MeasureService measureService, IDataDefinitionRuntimeController dataDefinitionController, IFMDMAttributeService fmAttributeService, IEntityViewController entityViewController, IRuntimeExpressionService expressionService, IRunTimeFormulaController runTimeFormulaController, IEntityViewRunTimeController entityViewRunTimeController, String regionKey) {
        this.runTimeViewController = runTimeViewController;
        this.runtimeDataSchemeService = runtimeDataSchemeService;
        this.measureService = measureService;
        this.dataDefinitionController = dataDefinitionController;
        this.fmAttributeService = fmAttributeService;
        this.entityViewController = entityViewController;
        this.expressionService = expressionService;
        this.runTimeFormulaController = runTimeFormulaController;
        this.entityViewRunTimeController = entityViewRunTimeController;
        this.regionKey = regionKey;
    }

    public void setEntityMetaService(IEntityMetaService entityMetaService) {
        this.entityMetaService = entityMetaService;
    }

    public DataRegionDefine getRegionDefine() {
        if (this.regionDefine != null) {
            return this.regionDefine;
        }
        this.regionDefine = this.runTimeViewController.queryDataRegionDefine(this.regionKey);
        if (this.regionDefine == null) {
            throw new CrudException(4503);
        }
        return this.regionDefine;
    }

    public Map<String, String> getBalanceExpression() {
        if (this.balanceExpression != null) {
            return this.balanceExpression;
        }
        if (this.getFormulaSchemeDefine() != null) {
            this.balanceExpression = this.expressionService.getBalanceZBExpressionByForm(this.formulaSchemeKey, this.getFormDefine().getKey());
        }
        if (CollectionUtils.isEmpty(this.balanceExpression)) {
            this.balanceExpression = Collections.emptyMap();
        }
        return this.balanceExpression;
    }

    public TaskDefine getTaskDefine() {
        if (this.taskDefine != null) {
            return this.taskDefine;
        }
        FormSchemeDefine schemeDefine = this.getFormSchemeDefine();
        TaskDefine define = this.runTimeViewController.queryTaskDefine(schemeDefine.getTaskKey());
        if (define == null) {
            throw new CrudException(4506);
        }
        String contextEntityId = DsContextHolder.getDsContext().getContextEntityId();
        if (StringUtils.hasLength(contextEntityId)) {
            define = TaskDefineProxy.createTaskDefineProxy(contextEntityId, define);
        }
        this.taskDefine = define;
        return this.taskDefine;
    }

    public FormulaSchemeDefine getFormulaSchemeDefine() {
        FormulaSchemeStream formulaSchemeStream;
        if (this.formulaSchemeDefine != null) {
            return this.formulaSchemeDefine;
        }
        if (this.formulaSchemeKey != null && (formulaSchemeStream = this.runTimeFormulaController.getFormulaScheme(this.formulaSchemeKey)) != null) {
            this.formulaSchemeDefine = (FormulaSchemeDefine)formulaSchemeStream.get();
        }
        return this.formulaSchemeDefine;
    }

    public FormSchemeDefine getFormSchemeDefine() {
        if (this.formSchemeDefine != null) {
            return this.formSchemeDefine;
        }
        this.getFormDefine();
        String formScheme = this.formDefine.getFormScheme();
        this.formSchemeDefine = this.runTimeViewController.getFormScheme(formScheme);
        if (this.formSchemeDefine == null) {
            throw new CrudException(4505);
        }
        return this.formSchemeDefine;
    }

    public FormDefine getFormDefine() {
        if (this.formDefine != null) {
            return this.formDefine;
        }
        this.getRegionDefine();
        String formKey = this.regionDefine.getFormKey();
        this.formDefine = this.runTimeViewController.queryFormById(formKey);
        if (this.formDefine == null) {
            throw new CrudException(4504);
        }
        return this.formDefine;
    }

    public DataTable getDataTable() {
        return this.dataTable;
    }

    public DataField getBizKeyOrderField() {
        return this.bizKeyOrderField;
    }

    public DataField getFloatOrderField() {
        return this.floatOrderField;
    }

    public List<DataField> getDimFields() {
        return this.dimFields;
    }

    public List<MetaData> getMetaData() {
        return this.getMetaData(null, false);
    }

    public List<MetaData> getMetaData(boolean containFloatOrder) {
        return this.getMetaData(null, containFloatOrder);
    }

    public List<MetaData> getMetaData(Iterator<String> cellItr) {
        return this.getMetaData(cellItr, false);
    }

    public DataTableDTO initMetaData(Iterator<String> cellItr, boolean containFloatOrder) {
        DataTableDTO dataTableDTO = new DataTableDTO();
        List<DataLinkDefine> links = this.selectLinks(cellItr);
        ArrayList<MetaData> metas = new ArrayList<MetaData>(links.size());
        dataTableDTO.setMetaFields(metas);
        HashMap<String, MetaData> linkMetaMap = new HashMap<String, MetaData>();
        DataRegionDefine regionDefine = this.getRegionDefine();
        EnumMap<DataLinkType, List<DataLinkDefine>> linkTypeMap = this.byTypeLink(links, regionDefine);
        block5: for (Map.Entry<DataLinkType, List<DataLinkDefine>> typeEntry : linkTypeMap.entrySet()) {
            DataLinkType type = typeEntry.getKey();
            List<DataLinkDefine> defines = typeEntry.getValue();
            switch (type) {
                case DATA_LINK_TYPE_INFO: 
                case DATA_LINK_TYPE_FIELD: {
                    metas.addAll(this.buildFieldMeta(defines, linkMetaMap));
                    break;
                }
                case DATA_LINK_TYPE_FORMULA: {
                    for (DataLinkDefine link : defines) {
                        MetaData meta = new MetaData(link);
                        linkMetaMap.put(link.getKey(), meta);
                        metas.add(meta);
                    }
                    continue block5;
                }
                case DATA_LINK_TYPE_FMDM: {
                    metas.addAll(this.buildFMDMMeta(defines, linkMetaMap));
                    break;
                }
            }
        }
        for (MetaData metaDatum : metas) {
            DataField dataField = metaDatum.getDataField();
            if (dataField == null) continue;
            String tableKey = dataField.getDataTableKey();
            DataTableDTO dto = this.buildFloatTableInnerField(tableKey);
            if (dto == null) break;
            dataTableDTO.setDataTable(dto.getDataTable());
            dataTableDTO.setBizKeyOrderField(dto.getBizKeyOrderField());
            dataTableDTO.setFloatOrderField(dto.getFloatOrderField());
            dataTableDTO.setDimFields(dto.getDimFields());
            dataTableDTO.setPeriodField(dto.getPeriodField());
            break;
        }
        this.linkMapSet(cellItr, linkMetaMap);
        if (containFloatOrder && dataTableDTO.getFloatOrderField() != null) {
            MetaData floatOrder = new MetaData("FLOATORDER", dataTableDTO.getFloatOrderField());
            metas.add(floatOrder);
        }
        return dataTableDTO;
    }

    public List<MetaData> getMetaData(Iterator<String> cellItr, boolean containFloatOrder) {
        if (this.metaData != null) {
            return this.metaData;
        }
        this.resetMetaData();
        DataTableDTO dataTableDTO = this.initMetaData(cellItr, containFloatOrder);
        this.metaData = dataTableDTO.getMetaFields();
        this.dataTable = dataTableDTO.getDataTable();
        this.periodField = dataTableDTO.getPeriodField();
        this.floatOrderField = dataTableDTO.getFloatOrderField();
        this.dimFields = dataTableDTO.getDimFields();
        this.bizKeyOrderField = dataTableDTO.getBizKeyOrderField();
        return this.metaData;
    }

    public void resetMetaData() {
        this.metaData = null;
        this.metaDataMap = null;
        this.fieldKey2Meta = null;
    }

    private List<MetaData> buildFMDMMeta(List<DataLinkDefine> defines, Map<String, MetaData> linkMetaMap) {
        ArrayList<MetaData> mts = new ArrayList<MetaData>(defines.size());
        for (DataLinkDefine link : defines) {
            FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
            fmdmAttributeDTO.setEntityId(this.getTaskDefine().getDw());
            fmdmAttributeDTO.setFormSchemeKey(this.getFormSchemeDefine().getKey());
            fmdmAttributeDTO.setAttributeCode(link.getLinkExpression());
            fmdmAttributeDTO.setZBKey(link.getLinkExpression());
            IFMDMAttribute fmAttribute = this.fmAttributeService.queryByZbKey(fmdmAttributeDTO);
            if (fmAttribute == null) continue;
            MetaData meta = new MetaData(link);
            meta.setFmAttribute(fmAttribute);
            mts.add(meta);
            linkMetaMap.put(link.getKey(), meta);
        }
        return mts;
    }

    private List<MetaData> buildFieldMeta(List<DataLinkDefine> defines, Map<String, MetaData> linkMetaMap) {
        ArrayList<MetaData> mts = new ArrayList<MetaData>();
        List fieldKeys = defines.stream().map(DataLinkDefine::getLinkExpression).filter(StringUtils::hasLength).distinct().collect(Collectors.toList());
        if (CollectionUtils.isEmpty(fieldKeys)) {
            return mts;
        }
        List dataFields = this.runtimeDataSchemeService.getDataFields(fieldKeys);
        Map<String, DataField> fieldMap = dataFields.stream().collect(Collectors.toMap(Basic::getKey, r -> r, (a, b) -> a));
        Map fDmap = null;
        Map<String, String> bes = this.getBalanceExpression();
        HashMap<String, String> enumPos2Link = new HashMap<String, String>();
        for (DataLinkDefine link : defines) {
            Map enumPosMap = link.getEnumPosMap();
            if (enumPosMap == null) continue;
            for (Map.Entry entry : enumPosMap.entrySet()) {
                String valueStr;
                Object value = entry.getValue();
                if (value == null || !StringUtils.hasLength(valueStr = value.toString())) continue;
                int[] pos = this.getPos(valueStr);
                enumPos2Link.put(Arrays.toString(pos), link.getKey());
            }
        }
        for (DataLinkDefine link : defines) {
            String balanceExpress;
            String posStr;
            DataField field;
            String fieldKey = link.getLinkExpression();
            if (!StringUtils.hasLength(fieldKey) || (field = fieldMap.get(fieldKey)) == null) continue;
            MetaData meta = new MetaData(field, link);
            if (!CollectionUtils.isEmpty(enumPos2Link) && enumPos2Link.containsKey(posStr = Arrays.toString(new int[]{link.getPosX(), link.getPosY()}))) {
                String srcLink = (String)enumPos2Link.get(posStr);
                meta.setEnumShowLink(srcLink);
                meta.setEnumShow(true);
            }
            linkMetaMap.put(link.getKey(), meta);
            mts.add(meta);
            if (fDmap == null) {
                fDmap = this.runtimeDataSchemeService.getDeployInfoByDataTableKey(field.getDataTableKey()).stream().collect(Collectors.groupingBy(DataFieldDeployInfo::getDataFieldKey, Collectors.toList()));
            }
            meta.setDeployInfos((List)fDmap.get(fieldKey));
            if (bes.isEmpty() || !StringUtils.hasLength(balanceExpress = bes.get(fieldKey))) continue;
            meta.setBalanceExpression(balanceExpress);
        }
        return mts;
    }

    private int[] getPos(String position) {
        String[] rows;
        String[] englishs = position.split("\\d");
        StringBuilder english = new StringBuilder();
        for (String n : englishs) {
            english.append(n);
        }
        StringBuilder relationRowString = new StringBuilder();
        for (String r : rows = position.split("\\D")) {
            relationRowString.append(r);
        }
        int relationRow = Integer.parseInt(relationRowString.toString());
        int relationCol = RegionRelation.excelColStrToNum(english.toString(), english.length());
        return new int[]{relationCol, relationRow};
    }

    public static int excelColStrToNum(String colStr, int length) {
        int result = 0;
        for (int i = 0; i < length; ++i) {
            char ch = colStr.charAt(length - i - 1);
            int num = ch - 65 + 1;
            result += (num *= (int)Math.pow(26.0, i));
        }
        return result;
    }

    private EnumMap<DataLinkType, List<DataLinkDefine>> byTypeLink(List<DataLinkDefine> links, DataRegionDefine regionDefine) {
        EnumMap<DataLinkType, List<DataLinkDefine>> linkTypeMap = new EnumMap<DataLinkType, List<DataLinkDefine>>(DataLinkType.class);
        for (DataLinkDefine link : links) {
            if (link.getType() == null || link.getPosX() < regionDefine.getRegionLeft() || link.getPosY() < regionDefine.getRegionTop() || link.getPosX() > regionDefine.getRegionRight() || link.getPosY() > regionDefine.getRegionBottom()) continue;
            List dataLinkDefines = linkTypeMap.computeIfAbsent(link.getType(), k -> new ArrayList());
            dataLinkDefines.add(link);
        }
        return linkTypeMap;
    }

    private List<DataLinkDefine> selectLinks(Iterator<String> cellItr) {
        ArrayList<DataLinkDefine> links;
        DataRegionDefine regionDefine = this.getRegionDefine();
        if (cellItr == null) {
            links = this.runTimeViewController.getAllLinksInRegion(regionDefine.getKey());
            DataRegionKind regionKind = regionDefine.getRegionKind();
            if (DataRegionKind.DATA_REGION_SIMPLE != regionKind) {
                links.sort((linkData0, linkData1) -> {
                    if (DataRegionKind.DATA_REGION_ROW_LIST == regionKind) {
                        return linkData0.getPosX() - linkData1.getPosX();
                    }
                    if (DataRegionKind.DATA_REGION_COLUMN_LIST == regionKind) {
                        return linkData0.getPosY() - linkData1.getPosY();
                    }
                    return 0;
                });
            }
        } else {
            links = new ArrayList();
            while (cellItr.hasNext()) {
                String link = cellItr.next();
                DataLinkDefine dataLinkDefine = this.runTimeViewController.queryDataLinkDefine(link);
                if (dataLinkDefine == null) {
                    logger.warn("\u6307\u5b9a\u94fe\u63a5 {} \u672a\u627e\u5230", (Object)link);
                    continue;
                }
                links.add(dataLinkDefine);
            }
        }
        return links;
    }

    private void linkMapSet(Iterator<String> cellItr, Map<String, MetaData> linkMetaMap) {
        List linkMap = this.runTimeViewController.queryDataLinkMapping(this.getFormDefine().getKey());
        if (linkMap != null) {
            for (DataLinkMappingDefine linkMapDefine : linkMap) {
                MetaData meta = linkMetaMap.get(linkMapDefine.getLeftDataLinkKey());
                if (meta != null) {
                    meta.addDataLinkMappingDefine(linkMapDefine);
                }
                if ((meta = linkMetaMap.get(linkMapDefine.getRightDataLinkKey())) == null) continue;
                meta.addDataLinkMappingDefine(linkMapDefine);
            }
        }
    }

    private DataTableDTO buildFloatTableInnerField(String tableKey) {
        if (tableKey == null) {
            return null;
        }
        DataTable table = this.runtimeDataSchemeService.getDataTable(tableKey);
        if (table == null) {
            return null;
        }
        DataTableDTO dataTableDTO = new DataTableDTO();
        dataTableDTO.setDataTable(table);
        DataTableType dataTableType = table.getDataTableType();
        if (DataTableType.DETAIL != dataTableType && DataTableType.ACCOUNT != dataTableType) {
            return null;
        }
        ArrayList<DataField> dims = new ArrayList<DataField>();
        dataTableDTO.setDimFields(dims);
        List innerOrDimFiled = this.runtimeDataSchemeService.getDataFieldByTableKeyAndKind(tableKey, new DataFieldKind[]{DataFieldKind.BUILT_IN_FIELD, DataFieldKind.PUBLIC_FIELD_DIM, DataFieldKind.TABLE_FIELD_DIM});
        for (DataField dataField : innerOrDimFiled) {
            switch (dataField.getDataFieldKind()) {
                case TABLE_FIELD_DIM: 
                case PUBLIC_FIELD_DIM: {
                    dims.add(dataField);
                    if (!"DATATIME".equals(dataField.getCode())) break;
                    dataTableDTO.setPeriodField(dataField);
                    break;
                }
                case BUILT_IN_FIELD: {
                    if ("BIZKEYORDER".equals(dataField.getCode())) {
                        dataTableDTO.setBizKeyOrderField(dataField);
                        break;
                    }
                    if (!"FLOATORDER".equals(dataField.getCode())) break;
                    dataTableDTO.setFloatOrderField(dataField);
                    break;
                }
            }
        }
        if (table.isRepeatCode() && dataTableDTO.getBizKeyOrderField() != null) {
            dims.add(dataTableDTO.getBizKeyOrderField());
        }
        return dataTableDTO;
    }

    public MetaData getMetaDataByLink(String link) {
        if (!StringUtils.hasLength(link)) {
            return null;
        }
        if (this.metaDataMap == null) {
            List<MetaData> dataList = this.getMetaData(null);
            this.metaDataMap = new HashMap<String, MetaData>();
            for (MetaData data : dataList) {
                this.metaDataMap.put(data.getLinkKey(), data);
            }
        }
        return this.metaDataMap.get(link);
    }

    public MetaData getMetaDataByFieldKey(String fieldKey) {
        if (this.fieldKey2Meta == null) {
            List<MetaData> dataList = this.getMetaData(null);
            this.fieldKey2Meta = new HashMap<String, MetaData>();
            for (MetaData data : dataList) {
                if (data.getDataField() != null) {
                    this.fieldKey2Meta.put(data.getDataField().getKey(), data);
                    continue;
                }
                if (data.getFmAttribute() == null) continue;
                this.fieldKey2Meta.put(data.getFmAttribute().getZBKey(), data);
            }
        }
        return this.fieldKey2Meta.get(fieldKey);
    }

    public MeasureView getMeasureView() {
        if (this.measureView != null) {
            return this.measureView;
        }
        FormDefine formDefine = this.getFormDefine();
        if (StringUtils.hasLength(formDefine.getMeasureUnit())) {
            String[] measureStr = formDefine.getMeasureUnit().split(";");
            if (measureStr.length == 2) {
                String tableKey = measureStr[0];
                String measureValue = measureStr[1];
                this.measureView = this.measureService.getByMeasure(tableKey);
                if (!measureValue.equalsIgnoreCase("NotDimession")) {
                    this.measureData = this.measureService.getByMeasure(tableKey, measureValue);
                }
            }
        } else {
            this.measureView = this.measureService.getByMeasure(null);
            this.measureData = this.measureService.getByMeasure(null, null);
        }
        return this.measureView;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public MeasureData getMeasureData() {
        this.getMeasureView();
        return this.measureData;
    }

    public RegionSettingDefine getRegionSettingDefine() {
        if (this.regionSettingDefine != null) {
            return this.regionSettingDefine;
        }
        this.regionSettingDefine = this.runTimeViewController.getRegionSetting(this.regionKey);
        return this.regionSettingDefine;
    }

    public DataField getPeriodField() {
        return this.periodField;
    }

    public void setGradeInfo(RegionGradeInfo gradeInfo) {
        this.gradeInfo = gradeInfo;
    }

    public RegionGradeInfo getGradeInfo() {
        if (this.gradeInfo != null) {
            return this.gradeInfo;
        }
        DataRegionDefine regionDefine = this.getRegionDefine();
        RegionGradeInfo gradeInfo = new RegionGradeInfo();
        gradeInfo.setQuerySummary(regionDefine.getShowGatherSummaryRow());
        gradeInfo.setQueryDetails(regionDefine.getShowGatherDetailRows());
        gradeInfo.setCollapseTotal(regionDefine.getIsCanFold());
        LevelSetting levelSetting = regionDefine.getLevelSetting();
        if (levelSetting != null) {
            FormulaSchemeDefine schemeDefine;
            FormulaSchemeStream formScheme;
            if (logger.isDebugEnabled()) {
                logger.debug("\u8bfb\u53d6\u5206\u7ea7\u5408\u8ba1\u53c2\u6570\u7ea7\u6b21\u914d\u7f6e {}", (Object)levelSetting);
            }
            gradeInfo.setHideSingleDetail(regionDefine.getShowGatherDetailRowByOne());
            String gatherFieldStr = regionDefine.getGatherFields();
            if (!StringUtils.hasLength(gatherFieldStr)) {
                gradeInfo.setGrade(false);
                this.gradeInfo = gradeInfo;
                return gradeInfo;
            }
            String levelSettingCode = levelSetting.getCode();
            if (StringUtils.hasLength(gatherFieldStr) && StringUtils.hasLength(levelSettingCode) && levelSettingCode.contains("undefined")) {
                logger.error("\u8bbe\u7f6e\u51fa\u9519 regionKey: {}|regionKeyTitle:{}|gatherFields\uff1a{}|gatherSettings :{}", regionDefine.getKey(), regionDefine.getTitle(), regionDefine.getGatherFields(), levelSettingCode);
                gradeInfo.setGrade(false);
                this.gradeInfo = gradeInfo;
                return gradeInfo;
            }
            String[] gatherFields = gatherFieldStr.split(";");
            String[] gatherSettings = levelSettingCode.split(";");
            String hideZeroGatherFieldStr = regionDefine.getHideZeroGatherFields();
            Set end0 = Collections.emptySet();
            if (hideZeroGatherFieldStr != null) {
                end0 = Arrays.stream(hideZeroGatherFieldStr.split(";")).collect(Collectors.toSet());
            }
            gradeInfo.setGradeLinks(new ArrayList<GradeLink>());
            for (int gatherIndex = 0; gatherIndex < gatherFields.length; ++gatherIndex) {
                String[] levels;
                String gatherSetting;
                String[] gatherField = gatherFields[gatherIndex];
                String string = gatherSetting = gatherSettings.length > gatherIndex ? gatherSettings[gatherIndex] : "";
                if (!StringUtils.hasLength((String)gatherField) || "null".equals(gatherField)) continue;
                GradeLink gradeLink = new GradeLink();
                gradeInfo.getGradeLinks().add(gradeLink);
                boolean needEnd0 = this.isNeedEnd0((String)gatherField);
                gradeLink.setFieldKey((String)gatherField);
                gradeLink.setHideEnd0(end0.contains(gatherField));
                gradeLink.setNeedEnd0(needEnd0);
                ArrayList<Integer> gradeSetting = new ArrayList<Integer>();
                gradeLink.setGradeSetting(gradeSetting);
                if (!StringUtils.hasLength(gatherSetting)) continue;
                for (String level : levels = gatherSetting.split(",")) {
                    if (!StringUtils.hasLength(level)) continue;
                    gradeSetting.add(Integer.parseInt(level));
                }
            }
            if (levelSetting.getType() == 1) {
                ArrayList<Integer> gradeLevels = new ArrayList<Integer>();
                for (String gatherSetting : gatherSettings) {
                    if (!StringUtils.hasLength(gatherSetting)) continue;
                    gradeLevels.add(Integer.parseInt(gatherSetting));
                }
                gradeInfo.setGradeLevels(gradeLevels);
                String precisionsConfig = levelSetting.getPrecision();
                if (StringUtils.hasLength(precisionsConfig)) {
                    String[] precisionsStr;
                    ArrayList<Integer> precisions = new ArrayList<Integer>();
                    for (String precisionStr : precisionsStr = precisionsConfig.split(";")) {
                        if (!StringUtils.hasLength(precisionStr)) {
                            precisions.add(null);
                            continue;
                        }
                        int precision = Integer.parseInt(precisionStr);
                        precisions.add(precision);
                    }
                    for (int i = 0; i < gatherFields.length - precisions.size(); ++i) {
                        precisions.add(null);
                    }
                    gradeInfo.setPrecisions(precisions);
                }
            }
            if ((formScheme = this.runTimeFormulaController.getDefaultFormulaSchemeByFormScheme(this.getFormSchemeDefine().getKey())) != null && (schemeDefine = (FormulaSchemeDefine)formScheme.get()) != null) {
                String key = schemeDefine.getKey();
                gradeInfo.setFormulaSchemeKey(key);
            }
            if (StringUtils.hasLength(gradeInfo.getFormulaSchemeKey()) && logger.isDebugEnabled()) {
                logger.debug("\u672a\u627e\u5230\u9ed8\u8ba4\u516c\u5f0f\u65b9\u6848\u5b9a\u4e49,\u62a5\u8868\u65b9\u6848key {}", (Object)this.getFormSchemeDefine().getKey());
            }
        } else {
            gradeInfo.setGrade(false);
        }
        this.gradeInfo = gradeInfo;
        return this.gradeInfo;
    }

    public boolean isNeedEnd0(String gatherField) {
        ITreeStruct treeStruct;
        IEntityDefine define;
        String refDataEntityKey;
        DataField dataField;
        MetaData meta = this.getMetaDataByFieldKey(gatherField);
        boolean needEnd0 = false;
        if (meta != null && (dataField = meta.getDataField()) != null && StringUtils.hasLength(refDataEntityKey = dataField.getRefDataEntityKey()) && (define = this.entityMetaService.queryEntity(refDataEntityKey)) != null && (treeStruct = define.getTreeStruct()) != null) {
            needEnd0 = treeStruct.isFixedSize();
        }
        return needEnd0;
    }

    public List<IParsedExpression> getExpressions() {
        if (this.expressions != null) {
            return this.expressions;
        }
        this.expressions = new ArrayList<IParsedExpression>();
        ArrayList<Formula> formulas = new ArrayList<Formula>();
        for (MetaData metaData : this.getMetaData(null)) {
            List validationRules;
            DataField dataField = metaData.getDataField();
            if (dataField == null || this.getDataTable() != null && this.getDataTable().getDataTableType() == DataTableType.ACCOUNT && dataField.getDataFieldKind() == DataFieldKind.TABLE_FIELD_DIM || (validationRules = dataField.getValidationRules()) == null) continue;
            for (ValidationRule validationRule : validationRules) {
                Formula formula = new Formula();
                formula.setId(metaData.getLinkKey());
                formula.setFormula(validationRule.getVerification());
                formula.setChecktype(Integer.valueOf(FormulaCheckType.FORMULA_CHECK_ERROR.getValue()));
                if (!StringUtils.hasLength(validationRule.getMessage())) {
                    formula.setMeanning(DataValidationIntepretUtil.intepret((IDataDefinitionRuntimeController)this.dataDefinitionController, (String)formula.getFormula()));
                } else {
                    formula.setMeanning(validationRule.getMessage());
                }
                formulas.add(formula);
            }
        }
        if (formulas.isEmpty()) {
            return this.expressions;
        }
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionController);
        try {
            List parsedExpressions = DataEngineFormulaParser.parseFormula((ExecutorContext)executorContext, formulas, (DataEngineConsts.FormulaType)DataEngineConsts.FormulaType.CHECK);
            if (!parsedExpressions.isEmpty()) {
                this.expressions.addAll(parsedExpressions);
            }
        }
        catch (ParseException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
        }
        return this.expressions;
    }

    public List<MetaData> getFilledEnumLinks() {
        String[] zbIdArr;
        if (this.enumMeta != null) {
            return this.enumMeta;
        }
        this.enumMeta = new ArrayList<MetaData>();
        RegionSettingDefine settingDefine = this.getRegionSettingDefine();
        if (settingDefine == null) {
            return this.enumMeta;
        }
        String zbIdStr = settingDefine.getDictionaryFillLinks();
        if (!StringUtils.hasLength(zbIdStr)) {
            return this.enumMeta;
        }
        for (String zbId : zbIdArr = zbIdStr.split(";")) {
            MetaData metaDataByFieldKey = this.getMetaDataByFieldKey(zbId);
            if (metaDataByFieldKey == null) {
                logger.warn("\u679a\u4e3e\u8054\u52a8\u6307\u6807\u914d\u7f6e\u672a\u627e\u5230\u5bf9\u5e94\u6307\u6807{}", (Object)zbId);
                continue;
            }
            this.enumMeta.add(metaDataByFieldKey);
        }
        return this.enumMeta;
    }

    @Override
    public String getDwDimName() {
        if (this.dwDwDimName != null) {
            return this.dwDwDimName;
        }
        TaskDefine taskDefine = this.getTaskDefine();
        try {
            this.dwDwDimName = this.entityViewController.getDimensionNameByViewKey(taskDefine.getDw());
        }
        catch (Exception e) {
            throw new CrudException(4506);
        }
        return this.dwDwDimName;
    }

    public List<LinkSort> getRegionDefaultOrder() {
        String[] sortingInfos;
        String sortFieldsList = this.getRegionDefine().getSortFieldsList();
        if (!StringUtils.hasLength(sortFieldsList)) {
            return Collections.emptyList();
        }
        ArrayList<LinkSort> sorts = new ArrayList<LinkSort>();
        for (String sortingInfo : sortingInfos = sortFieldsList.split(";")) {
            String fieldKey;
            boolean sort = true;
            if (sortingInfo.endsWith("+")) {
                fieldKey = sortingInfo.substring(0, sortingInfo.length() - 1);
            } else if (sortingInfo.endsWith("-")) {
                sort = false;
                fieldKey = sortingInfo.substring(0, sortingInfo.length() - 1);
            } else {
                fieldKey = sortingInfo;
            }
            MetaData metaDataByFieldKey = this.getMetaDataByFieldKey(fieldKey);
            if (metaDataByFieldKey == null) continue;
            LinkSort linkSort = new LinkSort();
            if (metaDataByFieldKey.getLinkKey() == null) continue;
            linkSort.setLinkKey(metaDataByFieldKey.getLinkKey());
            linkSort.setMode(sort ? SortMode.ASC : SortMode.DESC);
            sorts.add(linkSort);
        }
        return sorts;
    }

    public List<IParsedExpression> getParsedExpression(List<MetaData> metaData) {
        return this.getParsedExpression(metaData, this.formulaSchemeKey);
    }

    public List<IParsedExpression> getParsedExpression(List<MetaData> metaData, String formulaSchemeKey) {
        List<IParsedExpression> calcExpression = Collections.emptyList();
        if (!StringUtils.hasLength(formulaSchemeKey)) {
            return calcExpression;
        }
        ArrayList<String> columnModelKeys = new ArrayList<String>();
        HashSet<String> tableMap = new HashSet<String>();
        for (MetaData metaDatum : metaData) {
            DataField dataField = metaDatum.getDataField();
            if (dataField == null) continue;
            List<DataFieldDeployInfo> deploys = metaDatum.getDeployInfos();
            for (DataFieldDeployInfo deployInfo : deploys) {
                String columnModelKey = deployInfo.getColumnModelKey();
                columnModelKeys.add(columnModelKey);
                tableMap.add(deployInfo.getTableName());
            }
        }
        if (CollectionUtils.isEmpty(columnModelKeys)) {
            return calcExpression;
        }
        List formulaFields = this.expressionService.getFormulaFields(formulaSchemeKey, columnModelKeys);
        return this.getRegionExpression(formulaFields, tableMap);
    }

    private List<IParsedExpression> getRegionExpression(List<FormulaField> formulaFields, Set<String> tableMap) {
        ArrayList<IParsedExpression> expressions = new ArrayList<IParsedExpression>();
        for (FormulaField formulaField : formulaFields) {
            Collection writeFormula = formulaField.getWriteParsedExps();
            for (FormulaParsedExp formulaParsedExp : writeFormula) {
                if (!this.isExpressionValid(formulaParsedExp, tableMap)) continue;
                expressions.add(formulaParsedExp.getParsedExpression());
            }
        }
        return expressions;
    }

    private boolean isExpressionValid(FormulaParsedExp formulaParsedExp, Set<String> tableMap) {
        IParsedExpression parsedExpression = formulaParsedExp.getParsedExpression();
        if (parsedExpression.getFormulaType() != DataEngineConsts.FormulaType.CALCULATE) {
            return false;
        }
        QueryFields queryFields = parsedExpression.getQueryFields();
        for (QueryField queryField : queryFields) {
            if (tableMap.contains(queryField.getTableName())) continue;
            return false;
        }
        return true;
    }

    public ReportFmlExecEnvironment getReportFmlExecEnvironment() {
        if (this.environment != null) {
            return this.environment;
        }
        this.environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionController, this.entityViewRunTimeController, this.getFormSchemeDefine().getKey());
        return this.environment;
    }

    public void setReportFmlExecEnvironment(ReportFmlExecEnvironment environment) {
        this.environment = environment;
    }

    public IRuntimeDataSchemeService getRuntimeDataSchemeService() {
        return this.runtimeDataSchemeService;
    }

    @Override
    public String getDefaultGroupName() {
        return this.getFormDefine().getFormCode();
    }

    @Override
    public String getFormSchemeKey() {
        return this.getFormSchemeDefine().getKey();
    }

    @Override
    public String getTaskKey() {
        return this.getTaskDefine().getKey();
    }

    public AbstractData getTableDimDefaultValue(String fieldKey) {
        StringData value = null;
        RegionSettingDefine settingDefine = this.getRegionSettingDefine();
        List defaultValues = settingDefine.getEntityDefaultValue();
        if (defaultValues != null && !defaultValues.isEmpty()) {
            for (EntityDefaultValue entityDefaultValue : defaultValues) {
                String itemValue;
                if (!fieldKey.equals(entityDefaultValue.getFieldKey()) || !StringUtils.hasLength(itemValue = entityDefaultValue.getItemValue())) continue;
                value = new StringData(itemValue);
                break;
            }
        }
        return value;
    }

    public boolean isContainsDesensitized() {
        List<MetaData> metaData = this.metaData;
        if (metaData == null) {
            DataTableDTO dataTableDTO = this.initMetaData(null, false);
            metaData = dataTableDTO.getMetaFields();
        }
        for (MetaData metaDatum : metaData) {
            if (!metaDatum.isSensitive()) continue;
            return true;
        }
        return false;
    }
}

