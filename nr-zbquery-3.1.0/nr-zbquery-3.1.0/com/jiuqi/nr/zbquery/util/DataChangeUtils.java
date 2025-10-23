/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.model.TimeGranularity
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.dataresource.DataResourceNode
 *  com.jiuqi.nr.dataresource.DimAttribute
 *  com.jiuqi.nr.dataresource.NodeType
 *  com.jiuqi.nr.dataresource.dto.DimAttributeDTO
 *  com.jiuqi.nr.dataresource.util.SceneUtilService
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DataTableRel
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.internal.dto.DataDimDTO
 *  com.jiuqi.nr.datascheme.internal.service.DataSchemeService
 *  com.jiuqi.nr.datascheme.internal.service.DataTableRelService
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.BqlTimeDimUtils
 *  com.jiuqi.nr.period.common.utils.NrPeriodConst
 *  com.jiuqi.nr.period.common.utils.PeriodTableColumn
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.common.utils.TimeDimField
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  javax.annotation.PostConstruct
 */
package com.jiuqi.nr.zbquery.util;

import com.jiuqi.bi.adhoc.model.TimeGranularity;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.dataresource.DataResourceNode;
import com.jiuqi.nr.dataresource.DimAttribute;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.dataresource.dto.DimAttributeDTO;
import com.jiuqi.nr.dataresource.util.SceneUtilService;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DataTableRel;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.internal.dto.DataDimDTO;
import com.jiuqi.nr.datascheme.internal.service.DataSchemeService;
import com.jiuqi.nr.datascheme.internal.service.DataTableRelService;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.common.utils.BqlTimeDimUtils;
import com.jiuqi.nr.period.common.utils.NrPeriodConst;
import com.jiuqi.nr.period.common.utils.PeriodTableColumn;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.common.utils.TimeDimField;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.zbquery.bean.facade.IResourceTreeNode;
import com.jiuqi.nr.zbquery.bean.impl.ChildQueryDimension;
import com.jiuqi.nr.zbquery.bean.impl.SelectedFieldDefineEx;
import com.jiuqi.nr.zbquery.bean.impl.ZBFieldEx;
import com.jiuqi.nr.zbquery.extend.ZBQueryExtendProviderManager;
import com.jiuqi.nr.zbquery.model.DimensionAttributeField;
import com.jiuqi.nr.zbquery.model.DisplayContent;
import com.jiuqi.nr.zbquery.model.FormulaField;
import com.jiuqi.nr.zbquery.model.HiddenDimension;
import com.jiuqi.nr.zbquery.model.MagnitudeType;
import com.jiuqi.nr.zbquery.model.MagnitudeValue;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.model.QueryDimensionType;
import com.jiuqi.nr.zbquery.model.QueryObject;
import com.jiuqi.nr.zbquery.model.QueryObjectType;
import com.jiuqi.nr.zbquery.model.ZBFieldType;
import com.jiuqi.nr.zbquery.util.FullNameWrapper;
import com.jiuqi.nr.zbquery.util.ZBQueryI18nUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
@Lazy(value=false)
public class DataChangeUtils {
    private static final Logger logger = LoggerFactory.getLogger(DataChangeUtils.class);
    private static DataChangeUtils utils;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DataSchemeService dataSchemeService;
    @Autowired
    private DataTableRelService dataTableRelService;
    @Autowired
    private SceneUtilService sceneUtilService;
    @Autowired
    private ZBQueryExtendProviderManager zbQueryExtendProviderManager;
    @Autowired
    private PeriodEngineService periodEngineService;

    public static DimensionAttributeField change2DimAttribute(QueryObject queryObject, String fullName, String name, String enumName) {
        DimensionAttributeField dimAttribute = new DimensionAttributeField();
        dimAttribute.setId(queryObject.getId());
        dimAttribute.setFullName(fullName);
        dimAttribute.setAlias(queryObject.getAlias());
        dimAttribute.setName(name);
        dimAttribute.setType(queryObject.getType());
        dimAttribute.setSchemeName(queryObject.getSchemeName());
        dimAttribute.setTitle(queryObject.getTitle());
        dimAttribute.setMessageAlias(queryObject.getMessageAlias());
        dimAttribute.setAlias(queryObject.getAlias());
        dimAttribute.setParent(queryObject.getParent());
        dimAttribute.setRelatedDimension(enumName);
        return dimAttribute;
    }

    @PostConstruct
    public void init() {
        utils = this;
    }

    public static ZBFieldEx change2ZBField(DataField dataField, String id) {
        return DataChangeUtils.change2ZBField(dataField, id, null);
    }

    public static ZBFieldEx change2ZBField(DataField dataField, String id, List<String> dws) {
        String measureUnit;
        IEntityDefine entity;
        DataTableRel dataTableRel;
        ZBFieldEx zbField = new ZBFieldEx();
        String dataSchemeKey = dataField.getDataSchemeKey();
        DataScheme dataScheme = DataChangeUtils.utils.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        String schemeName = dataScheme.getCode();
        String dataTableKey = dataField.getDataTableKey();
        DataTable dataTable = DataChangeUtils.utils.runtimeDataSchemeService.getDataTable(dataTableKey);
        LinkedHashMap<String, QueryDimensionType> relatedDimensionMap = new LinkedHashMap<String, QueryDimensionType>();
        DataFieldKind dataFieldKind = dataField.getDataFieldKind();
        if (dataFieldKind == DataFieldKind.FIELD_ZB) {
            zbField.setZbType(ZBFieldType.FIXED);
        } else if (dataFieldKind == DataFieldKind.FIELD) {
            zbField.setZbType(ZBFieldType.DETAIL);
            DataChangeUtils.utils.runtimeDataSchemeService.getDataFieldByTableKeyAndKind(dataTable.getKey(), new DataFieldKind[]{DataFieldKind.TABLE_FIELD_DIM}).stream().filter(DataChangeUtils::isChangeTableDim).forEach(dimDataField -> {
                String dimKey = schemeName + "." + dataTable.getCode() + "." + dimDataField.getCode();
                relatedDimensionMap.put(dimKey, QueryDimensionType.INNER);
            });
        } else if (id == null && dataFieldKind == DataFieldKind.TABLE_FIELD_DIM) {
            zbField.setZbType(ZBFieldType.DETAIL);
            if (StringUtils.hasLength(dataField.getRefDataEntityKey()) && DataChangeUtils.isChangeTableDim(dataField)) {
                String dimKey = schemeName + "." + dataTable.getCode() + "." + dataField.getCode();
                relatedDimensionMap.put(dimKey, QueryDimensionType.INNER);
            }
        }
        if (dataTable.getDataTableType() == DataTableType.SUB_TABLE && !ObjectUtils.isEmpty(dataTableRel = DataChangeUtils.utils.dataTableRelService.getBySrcTable(dataTable.getKey()))) {
            String desTableKey = dataTableRel.getDesTableKey();
            DataTable dataTable1 = DataChangeUtils.utils.runtimeDataSchemeService.getDataTable(desTableKey);
            DataChangeUtils.utils.runtimeDataSchemeService.getDataFieldByTableKeyAndKind(desTableKey, new DataFieldKind[]{DataFieldKind.TABLE_FIELD_DIM}).stream().filter(DataChangeUtils::isChangeTableDim).forEach(dimDataField -> {
                String dimKey = schemeName + "." + dataTable1.getCode() + "." + dimDataField.getCode();
                relatedDimensionMap.put(dimKey, QueryDimensionType.INNER);
            });
        }
        zbField.setSchemeName(schemeName);
        String fullName = dataTable.getCode() + "." + dataField.getCode();
        zbField.setName(dataField.getCode());
        zbField.setTitle(dataField.getTitle());
        zbField.setFullName(fullName);
        zbField.setId(id == null ? dataField.getKey() : id);
        zbField.setType(QueryObjectType.ZB);
        zbField.setApplyType(dataField.getDataFieldApplyType());
        DataChangeUtils.handleDataSchemeDimension(dataSchemeKey, id, dws, relatedDimensionMap);
        zbField.setRelatedDimensionMap(relatedDimensionMap);
        FormatProperties formatProperties = dataField.getFormatProperties();
        if (ObjectUtils.isEmpty(formatProperties)) {
            Integer decimal = dataField.getDecimal();
            if (ObjectUtils.isEmpty(decimal)) {
                if (dataField.getDataFieldType() == DataFieldType.INTEGER) {
                    zbField.setShowFormat("#,##0");
                } else if (dataField.getDataFieldType() == DataFieldType.BIGDECIMAL) {
                    zbField.setShowFormat("#,##0.00");
                }
            } else if (dataField.getDataFieldType() == DataFieldType.INTEGER || dataField.getDataFieldType() == DataFieldType.BIGDECIMAL) {
                String showFormat = "#,##0";
                for (int i = 0; i < decimal; ++i) {
                    if (i == 0) {
                        showFormat = showFormat + ".";
                    }
                    showFormat = showFormat + "0";
                }
                zbField.setShowFormat(showFormat);
            }
        } else {
            zbField.setShowFormat(formatProperties.getPattern());
        }
        if (dataField.getDataFieldType() == DataFieldType.INTEGER || dataField.getDataFieldType() == DataFieldType.BIGDECIMAL) {
            zbField.setDisplaySum(true);
        }
        zbField.setDataType(dataField.getDataFieldType().getValue());
        String refDataEntityKey = dataField.getRefDataEntityKey();
        if (StringUtils.hasLength(refDataEntityKey) && Objects.nonNull(entity = DataChangeUtils.utils.entityMetaService.queryEntity(refDataEntityKey))) {
            zbField.setRelatedDimension(entity.getCode());
        }
        if (StringUtils.hasLength(measureUnit = dataField.getMeasureUnit()) && measureUnit.startsWith("9493b4eb-6516-48a8-a878-25a63a23e63a;")) {
            if (!measureUnit.endsWith("NotDimession")) {
                String[] measures = measureUnit.split(";");
                if (measures.length == 2) {
                    String measureCode = measures[1];
                    zbField.setFieldMagnitude(DataChangeUtils.toMagnitudeValue(measureCode));
                    zbField.setFieldMagnitudeType(MagnitudeType.AMOUNT);
                }
            } else {
                zbField.setFieldMagnitudeType(MagnitudeType.NOTDIMESSION);
            }
        }
        zbField.setMessageAlias(fullName);
        zbField.setRelatedPublicParameter(dataField.getRefParameter());
        return zbField;
    }

    public static FormulaField change2FormulaField(SelectedFieldDefineEx selectedField) {
        FormulaField formulaField = new FormulaField();
        formulaField.setId("nrfs_fieldfml_" + selectedField.getLinkKey());
        formulaField.setType(QueryObjectType.FORMULA);
        int dataType = DataTypesConvert.fieldTypeToDataType((FieldType)selectedField.getDataType());
        formulaField.setDataType(dataType);
        formulaField.setFormula(selectedField.getCode());
        formulaField.setTitle(selectedField.getFieldTitle());
        formulaField.setFullName("CALC_" + selectedField.getLinkKey().replace("-", ""));
        formulaField.setName(formulaField.getFullName());
        formulaField.setDisplayContent(DisplayContent.CODE);
        if (dataType == 3 || dataType == 10 || dataType == 11) {
            formulaField.setShowFormat(DataChangeUtils.generateZbShowFormat(2, true, null));
            formulaField.setDisplaySum(true);
            formulaField.setDataType(3);
        } else if (dataType == 5 || dataType == 8) {
            formulaField.setShowFormat(DataChangeUtils.generateZbShowFormat(0, true, null));
        }
        return formulaField;
    }

    public static QueryObject change2ChildDim(DimensionAttributeField dimAttrField) {
        ChildQueryDimension childDim = new ChildQueryDimension();
        childDim.setId(dimAttrField.getId());
        childDim.setTitle(dimAttrField.getTitle());
        childDim.setType(QueryObjectType.DIMENSION);
        childDim.setSchemeName(dimAttrField.getSchemeName());
        childDim.setDimAttribute(dimAttrField);
        if (!ObjectUtils.isEmpty(dimAttrField.getPeriodType()) && dimAttrField.getPeriodType().type() > 0) {
            String periodEntityId = BqlTimeDimUtils.getPeriodEntityId((String)dimAttrField.getParent());
            IPeriodEntityAdapter periodAdapter = DataChangeUtils.utils.periodEngineService.getPeriodAdapter();
            IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(periodEntityId);
            if (PeriodUtils.isPeriod13((String)periodEntity.getCode(), (PeriodType)periodEntity.getPeriodType()) && PeriodTableColumn.MONTH.getCode().equals(dimAttrField.getName())) {
                childDim.setMinFiscalMonth(periodEntity.getMinFiscalMonth());
                childDim.setMaxFiscalMonth(periodEntity.getMaxFiscalMonth());
            }
            childDim.setName(dimAttrField.getName());
            childDim.setParent(dimAttrField.getParent());
            childDim.setFullName(dimAttrField.getFullName() + "." + "DIM");
            dimAttrField.setParent(childDim.getFullName());
            childDim.setPeriodType(dimAttrField.getPeriodType());
        } else {
            String tableCode = dimAttrField.getRelatedDimension();
            childDim.setName(tableCode);
            childDim.setParent(dimAttrField.getParent());
            childDim.setFullName(dimAttrField.getFullName() + "." + tableCode);
            String dimKey = DataChangeUtils.utils.entityMetaService.getEntityIdByCode(tableCode);
            IEntityDefine entity = DataChangeUtils.utils.entityMetaService.queryEntity(dimKey);
            if (Objects.nonNull(entity)) {
                childDim.setTreeStructure(entity.isTree());
                childDim.setEnableVersion(new Integer(1).equals(entity.getVersion()));
            }
            try {
                IEntityModel entityModel = DataChangeUtils.utils.entityMetaService.getEntityModel(dimKey);
                IEntityAttribute bizKeyField = entityModel.getBizKeyField();
                childDim.setBizKey(bizKeyField.getName());
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        childDim.setMessageAlias(FullNameWrapper.getMessageAlias(childDim));
        return childDim;
    }

    public static List<DimAttribute> getCBDimFields(String dimKey) {
        IEntityDefine entity = DataChangeUtils.utils.entityMetaService.queryEntity(dimKey);
        ArrayList<DimAttribute> dimFields = new ArrayList<DimAttribute>();
        DimAttributeDTO codeAttribute = new DimAttributeDTO();
        codeAttribute.setDimKey(dimKey);
        codeAttribute.setKey("CODE");
        codeAttribute.setCode("CODE");
        codeAttribute.setTitle(ZBQueryI18nUtils.getMessage("zbquery.code", new Object[0]));
        dimFields.add((DimAttribute)codeAttribute);
        DimAttributeDTO nameAttribute = new DimAttributeDTO();
        nameAttribute.setDimKey(dimKey);
        nameAttribute.setKey("NAME");
        nameAttribute.setCode("NAME");
        nameAttribute.setTitle(ZBQueryI18nUtils.getMessage("zbquery.name", new Object[0]));
        dimFields.add((DimAttribute)nameAttribute);
        if (entity.isTree().booleanValue()) {
            DimAttributeDTO parentCodeAttribute = new DimAttributeDTO();
            parentCodeAttribute.setDimKey(dimKey);
            parentCodeAttribute.setKey("PARENTCODE");
            parentCodeAttribute.setCode("PARENTCODE");
            parentCodeAttribute.setTitle(ZBQueryI18nUtils.getMessage("zbquery.parent", new Object[0]));
            dimFields.add((DimAttribute)parentCodeAttribute);
        }
        return dimFields;
    }

    public static List<TimeDimField> getPeriodChildDimFields(ChildQueryDimension childDimension) {
        DimensionAttributeField parentDimAttribute = childDimension.getDimAttribute();
        ArrayList<TimeDimField> timeDimFields = new ArrayList<TimeDimField>();
        TimeDimField timeDimField = new TimeDimField();
        timeDimField.setName(parentDimAttribute.getName());
        timeDimField.setDataType(parentDimAttribute.getDataType());
        timeDimField.setTitle(parentDimAttribute.getTitle());
        timeDimField.setTimeGranularity(DataChangeUtils.toTimeGranularity(parentDimAttribute.getPeriodType()));
        timeDimFields.add(timeDimField);
        return timeDimFields;
    }

    public static List<TimeDimField> getZPeriodChildDimFields(QueryDimension queryDimension) {
        ArrayList<TimeDimField> timeDimFields = new ArrayList<TimeDimField>();
        TimeDimField timeDimField = new TimeDimField();
        timeDimField.setName("P_TITLE");
        timeDimField.setDataType(PeriodTableColumn.TITLE.getType().getValue());
        timeDimField.setTitle(ZBQueryI18nUtils.getMessage("zbquery.pTitle", new Object[0]));
        timeDimField.setTimeGranularity(DataChangeUtils.toTimeGranularity(queryDimension.getPeriodType()));
        timeDimFields.add(timeDimField);
        return timeDimFields;
    }

    public static String handleDimKey(String dimKey, QueryDimensionType queryDimensionType, Map<String, String> extendedDatas) {
        if (dimKey.startsWith(NrPeriodConst.PREFIX_CODE)) {
            dimKey = PeriodUtils.removePerfix((String)dimKey);
        }
        if (DataChangeUtils.utils.zbQueryExtendProviderManager.getExtendProvider() != null && queryDimensionType == QueryDimensionType.MASTER) {
            try {
                dimKey = DataChangeUtils.utils.zbQueryExtendProviderManager.getExtendProvider().getMasterDimension(extendedDatas, dimKey);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return dimKey;
    }

    public static void handleDataSchemeDimension(String dataSchemeKey, String id, List<String> dws, Map<String, QueryDimensionType> relatedDimensionMap) {
        List allDataDims = DataChangeUtils.utils.dataSchemeService.getDataSchemeDimension(dataSchemeKey);
        DataDimDTO masterDataDim = null;
        for (DataDimDTO dataDim : allDataDims) {
            if (DimensionType.UNIT != dataDim.getDimensionType()) continue;
            masterDataDim = dataDim;
            break;
        }
        List<DataDimDTO> dataDims = allDataDims.stream().filter(dataDimDTO -> !"MD_ORG@ORG".equals(dataDimDTO.getDimKey()) && !"ADJUST".equals(dataDimDTO.getDimKey())).filter(dataDimDTO -> {
            if (!CollectionUtils.isEmpty(dws) && DimensionType.UNIT_SCOPE == dataDimDTO.getDimensionType()) {
                return dws.contains(dataDimDTO.getDimKey());
            }
            return true;
        }).collect(Collectors.toList());
        DataDimDTO finalMasterDataDim = masterDataDim;
        dataDims.forEach(dataDimDTO -> {
            DimensionType dimensionType = dataDimDTO.getDimensionType();
            switch (dimensionType) {
                case DIMENSION: {
                    if (!DataChangeUtils.utils.sceneUtilService.isAddScene(finalMasterDataDim.getDimKey(), (DataDimension)dataDimDTO)) break;
                    relatedDimensionMap.put(dataDimDTO.getDimKey(), QueryDimensionType.SCENE);
                    break;
                }
                case PERIOD: {
                    if (id == null) {
                        relatedDimensionMap.put(dataDimDTO.getDimKey(), QueryDimensionType.PERIOD);
                        break;
                    }
                    relatedDimensionMap.put(BqlTimeDimUtils.getBqlTimeDimTable((String)dataDimDTO.getDimKey()), QueryDimensionType.PERIOD);
                    break;
                }
                default: {
                    relatedDimensionMap.put(dataDimDTO.getDimKey(), QueryDimensionType.MASTER);
                }
            }
        });
    }

    public static TimeGranularity toTimeGranularity(PeriodType periodType) {
        if (periodType == PeriodType.YEAR) {
            return TimeGranularity.YEAR;
        }
        if (periodType == PeriodType.HALFYEAR) {
            return TimeGranularity.HALFYEAR;
        }
        if (periodType == PeriodType.SEASON) {
            return TimeGranularity.QUARTER;
        }
        if (periodType == PeriodType.MONTH) {
            return TimeGranularity.MONTH;
        }
        if (periodType == PeriodType.TENDAY) {
            return TimeGranularity.XUN;
        }
        if (periodType == PeriodType.DAY) {
            return TimeGranularity.DAY;
        }
        return null;
    }

    public static MagnitudeValue toMagnitudeValue(String measureCode) {
        if ("YUAN".equals(measureCode)) {
            return MagnitudeValue.ONE;
        }
        if ("BAIYUAN".equals(measureCode)) {
            return MagnitudeValue.HUNDRED;
        }
        if ("QIANYUAN".equals(measureCode)) {
            return MagnitudeValue.THOUSAND;
        }
        if ("WANYUAN".equals(measureCode)) {
            return MagnitudeValue.TENTHOUSAND;
        }
        if ("BAIWAN".equals(measureCode)) {
            return MagnitudeValue.MILLION;
        }
        if ("QIANWAN".equals(measureCode)) {
            return MagnitudeValue.TENMILLION;
        }
        if ("YIYUAN".equals(measureCode)) {
            return MagnitudeValue.HUNDREDMILLION;
        }
        if ("WANYI".equals(measureCode)) {
            return MagnitudeValue.TRILLION;
        }
        return MagnitudeValue.NONE;
    }

    public static List<IResourceTreeNode> hiddenDimension4Node(List<IResourceTreeNode> resourceTreeNodes, List<HiddenDimension> hiddenDimensions) {
        if (CollectionUtils.isEmpty(hiddenDimensions)) {
            return resourceTreeNodes;
        }
        Map<String, HiddenDimension> hiddenDimensionMap = hiddenDimensions.stream().collect(Collectors.toMap(HiddenDimension::getName, hiddenDimension -> hiddenDimension));
        return resourceTreeNodes.stream().filter(iResourceTreeNode -> {
            QueryObject queryObject = iResourceTreeNode.getQueryObject();
            return ObjectUtils.isEmpty(queryObject) || queryObject.getType() != QueryObjectType.DIMENSION || !hiddenDimensionMap.containsKey(queryObject.getFullName());
        }).collect(Collectors.toList());
    }

    public static List<ITree<IResourceTreeNode>> hiddenDimension(List<ITree<IResourceTreeNode>> iTrees, List<HiddenDimension> hiddenDimensions) {
        if (CollectionUtils.isEmpty(hiddenDimensions)) {
            return iTrees;
        }
        Map<String, HiddenDimension> hiddenDimensionMap = hiddenDimensions.stream().collect(Collectors.toMap(HiddenDimension::getName, hiddenDimension -> hiddenDimension));
        return DataChangeUtils.hiddenDimension(iTrees, hiddenDimensionMap);
    }

    public static List<ITree<IResourceTreeNode>> hiddenDimension(List<ITree<IResourceTreeNode>> iTrees, Map<String, HiddenDimension> hiddenDimensionMap) {
        if (CollectionUtils.isEmpty(iTrees)) {
            return iTrees;
        }
        return iTrees.stream().filter(iTree -> {
            QueryObject queryObject = ((IResourceTreeNode)iTree.getData()).getQueryObject();
            if (!ObjectUtils.isEmpty(queryObject) && queryObject.getType() == QueryObjectType.DIMENSION) {
                if (hiddenDimensionMap.containsKey(queryObject.getFullName())) {
                    return false;
                }
            } else {
                iTree.setChildren(DataChangeUtils.hiddenDimension((List<ITree<IResourceTreeNode>>)iTree.getChildren(), hiddenDimensionMap));
            }
            return true;
        }).collect(Collectors.toList());
    }

    public static boolean isChangeTableDim(DataField dataField) {
        return !dataField.isAllowMultipleSelect();
    }

    public static boolean isRelZBNode(DataResourceNode dataResourceNode) {
        return dataResourceNode.getType() == NodeType.RESOURCE_GROUP.getValue() && StringUtils.hasLength(dataResourceNode.getLinkZb());
    }

    public static String generateZbShowFormat(int decimal, boolean thousandth, String percentage) {
        String showFormat = "";
        if (thousandth) {
            showFormat = showFormat + "#,##0";
        }
        if (decimal > 0) {
            showFormat = showFormat + ".";
            for (int j = 0; j < decimal; ++j) {
                showFormat = showFormat + "0";
            }
        }
        if (StringUtils.hasLength(percentage) && !"NONE".equals(percentage)) {
            showFormat = showFormat + percentage;
        }
        return showFormat;
    }

    public static void sortNodes(List<IResourceTreeNode> nodeList) {
        Collections.sort(nodeList, (o1, o2) -> {
            if (o1.getType() == NodeType.DIM_GROUP.getValue()) {
                if (o2.getType() == NodeType.DIM_GROUP.getValue()) {
                    return 0;
                }
                return -1;
            }
            if (o1.getType() == NodeType.FIELD_ZB_LINK.getValue()) {
                if (o2.getType() == NodeType.DIM_GROUP.getValue()) {
                    return 1;
                }
                if (o2.getType() == NodeType.FIELD_ZB_LINK.getValue()) {
                    return 0;
                }
                return -1;
            }
            if (o1.getType() == NodeType.TABLE_DIM_GROUP.getValue()) {
                if (o2.getType() == NodeType.FIELD_ZB_LINK.getValue()) {
                    return -1;
                }
                if (o2.getType() == NodeType.TABLE_DIM_GROUP.getValue()) {
                    return 0;
                }
                return 1;
            }
            if (o1.getType() == NodeType.FIELD_LINK.getValue()) {
                if (o2.getType() == NodeType.FIELD_LINK.getValue()) {
                    return 0;
                }
                return 1;
            }
            return 0;
        });
    }
}

