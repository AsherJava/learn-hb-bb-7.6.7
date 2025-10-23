/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DataTableRel
 *  com.jiuqi.nr.datascheme.api.ValidationRule
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.type.CompareType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.graph.GraphHelper
 *  com.jiuqi.nr.graph.IGraph
 *  com.jiuqi.nr.graph.IGraphEditor
 *  com.jiuqi.nr.graph.INode
 *  com.jiuqi.nr.graph.cache.GraphCacheDefine
 *  com.jiuqi.nr.graph.function.AttrValueGetter
 *  com.jiuqi.nr.graph.internal.GraphBuilder
 *  com.jiuqi.nr.graph.label.ILabel
 *  com.jiuqi.nr.graph.label.IndexLabel
 *  com.jiuqi.nr.graph.label.NodeLabel
 *  com.jiuqi.nr.graph.util.GraphUtils
 */
package com.jiuqi.nr.datascheme.internal.service.impl.cache2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DataTableRel;
import com.jiuqi.nr.datascheme.api.ValidationRule;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.type.CompareType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.common.Consts;
import com.jiuqi.nr.datascheme.internal.dao.IDataDimDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataSchemeDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableRelDao;
import com.jiuqi.nr.datascheme.internal.dao.impl.DBSimpleQueryUtils;
import com.jiuqi.nr.datascheme.internal.dao.impl.DataFieldDeployInfoDaoImpl;
import com.jiuqi.nr.datascheme.internal.dto.DataDimDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataTableDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataTableRelDTO;
import com.jiuqi.nr.datascheme.internal.dto.ValidationRuleDTO;
import com.jiuqi.nr.datascheme.internal.entity.DataDimDO;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nr.datascheme.internal.entity.DataSchemeDO;
import com.jiuqi.nr.datascheme.internal.entity.DataTableDO;
import com.jiuqi.nr.datascheme.internal.entity.DataTableRelDO;
import com.jiuqi.nr.datascheme.internal.service.impl.cache2.dto.BasicCacheDTO;
import com.jiuqi.nr.datascheme.internal.service.impl.cache2.dto.ColumnModelCacheDTO;
import com.jiuqi.nr.datascheme.internal.service.impl.cache2.dto.DataFieldCacheDTO;
import com.jiuqi.nr.datascheme.internal.service.impl.cache2.dto.DataSchemeCacheDTO;
import com.jiuqi.nr.datascheme.internal.service.impl.cache2.dto.DataTableCacheDTO;
import com.jiuqi.nr.datascheme.internal.service.impl.cache2.dto.TableModelCacheDTO;
import com.jiuqi.nr.graph.GraphHelper;
import com.jiuqi.nr.graph.IGraph;
import com.jiuqi.nr.graph.IGraphEditor;
import com.jiuqi.nr.graph.INode;
import com.jiuqi.nr.graph.cache.GraphCacheDefine;
import com.jiuqi.nr.graph.function.AttrValueGetter;
import com.jiuqi.nr.graph.internal.GraphBuilder;
import com.jiuqi.nr.graph.label.ILabel;
import com.jiuqi.nr.graph.label.IndexLabel;
import com.jiuqi.nr.graph.label.NodeLabel;
import com.jiuqi.nr.graph.util.GraphUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataSchemeGraphService {
    private static final Logger LOGGER = Consts.NR_PARAM_GRAPH_LOGGER;
    private static final AttrValueGetter<Object, String> BASIC_KEY_GETTER = o -> null == o ? null : ((BasicCacheDTO)o).getKey();
    private static final AttrValueGetter<Object, String> TABLE_REL_KEY_GETTER = o -> null == o ? null : ((DataTableRel)o).getKey();
    private static final AttrValueGetter<Object, String> DEPLOY_INFO_KEY_GETTER = o -> null == o ? null : ((DataFieldDeployInfo)o).getColumnModelKey();
    private static final GraphBuilder BUILDER = GraphHelper.createGraphBuilder((String)"DATASCHEME_GRAPH");
    public static final NodeLabel TABLE = BUILDER.registerNode("TABLE", BASIC_KEY_GETTER);
    public static final NodeLabel TABLE_REL = BUILDER.registerNode("TABLE_REL", TABLE_REL_KEY_GETTER);
    public static final NodeLabel FIELD = BUILDER.registerNode("FIELD", BASIC_KEY_GETTER);
    public static final NodeLabel TABLEMODEL = BUILDER.registerNode("TABLEMODEL", BASIC_KEY_GETTER);
    public static final NodeLabel DEPLOY_INFO = BUILDER.registerNode("DEPLOY_INFO", DEPLOY_INFO_KEY_GETTER);
    private static final AttrValueGetter<INode, String> BASIC_CODE_KEY_GETTER = o -> {
        if (null == o) {
            return null;
        }
        BasicCacheDTO data = (BasicCacheDTO)o.getData(BasicCacheDTO.class);
        if (null == data) {
            return null;
        }
        return data.getCode();
    };
    private static final AttrValueGetter<INode, String> FIELD_CODE_KEY_GETTER = o -> {
        DataFieldDTO data = ((DataFieldCacheDTO)o.getData(DataFieldCacheDTO.class)).getDataField();
        return DataFieldKind.FIELD_ZB != data.getDataFieldKind() ? null : data.getCode();
    };
    public static final IndexLabel TABLE_CODE = BUILDER.registerIndex("TABLE_CODE", TABLE, BASIC_CODE_KEY_GETTER);
    public static final IndexLabel FIELD_CODE = BUILDER.registerIndex("FIELD_CODE", FIELD, FIELD_CODE_KEY_GETTER);
    public static final IndexLabel TABLEMODEL_NAME = BUILDER.registerIndex("TABLEMODEL_NAME", TABLEMODEL, BASIC_CODE_KEY_GETTER);
    private static final GraphBuilder DATA_SCHEME_GRAPH_BUILDER = GraphHelper.createGraphBuilder((String)"DATA_SCHEME_GRAPH_BUILDER");
    public static final NodeLabel SCHEME = DATA_SCHEME_GRAPH_BUILDER.registerNode("SCHEME", BASIC_KEY_GETTER);
    public static final IndexLabel SCHEME_CODE = DATA_SCHEME_GRAPH_BUILDER.registerIndex("SCHEME_CODE", SCHEME, BASIC_CODE_KEY_GETTER);
    @Autowired
    private IDataSchemeDao<DataSchemeDO> dataSchemeDao;
    @Autowired
    private IDataDimDao<DataDimDO> dataDimDao;
    @Autowired
    protected IDataTableDao<DataTableDO> dataTableDao;
    @Autowired
    private IDataTableRelDao<DataTableRelDO> iDataTableRelDao;
    @Autowired
    private IDataFieldDao<DataFieldDO> dataFieldDao;
    @Autowired
    private DataFieldDeployInfoDaoImpl dataFieldDeployInfoDao;
    @Autowired
    private DBSimpleQueryUtils queryUtils;
    private final Supplier<Map<ILabel, Map<String, String>>> globalIndexSupplier = () -> {
        HashMap<Object, Map<String, String>> map = new HashMap<Object, Map<String, String>>();
        Map<String, Map<String, String>> tableIndesMap = this.queryUtils.queryForMap("NR_DATASCHEME_TABLE", Arrays.asList("DT_KEY", "DT_CODE"), "DT_DS_KEY");
        Map<String, Map<String, String>> deployInfoIndesMap = this.queryUtils.queryForMap("NR_DATASCHEME_DEPLOY_INFO", Arrays.asList("DS_CM_KEY", "DS_TM_KEY", "DS_TABLE_NAME"), "DS_DS_KEY");
        map.put(TABLE, tableIndesMap.get("DT_KEY"));
        map.put(FIELD, this.queryUtils.queryForMap("NR_DATASCHEME_FIELD", "DF_KEY", "DF_DS_KEY"));
        map.put(DEPLOY_INFO, deployInfoIndesMap.get("DS_CM_KEY"));
        map.put(TABLEMODEL, deployInfoIndesMap.get("DS_TM_KEY"));
        map.put(TABLE_CODE, tableIndesMap.get("DT_CODE"));
        map.put(TABLEMODEL_NAME, deployInfoIndesMap.get("DS_TABLE_NAME"));
        return map;
    };
    private final Function<String, Map<ILabel, Map<String, String>>> globalIndexRefresher = dataSchemeKey -> {
        HashMap<Object, Map<String, String>> map = new HashMap<Object, Map<String, String>>();
        Map<String, Map<String, String>> tableIndesMap = this.queryUtils.queryForMap("NR_DATASCHEME_TABLE", Arrays.asList("DT_KEY", "DT_CODE"), "DT_DS_KEY", (String)dataSchemeKey);
        Map<String, Map<String, String>> deployInfoIndesMap = this.queryUtils.queryForMap("NR_DATASCHEME_DEPLOY_INFO", Arrays.asList("DS_CM_KEY", "DS_TM_KEY", "DS_TABLE_NAME"), "DS_DS_KEY", (String)dataSchemeKey);
        map.put(TABLE, tableIndesMap.get("DT_KEY"));
        map.put(FIELD, this.queryUtils.queryForMap("NR_DATASCHEME_FIELD", "DF_KEY", "DF_DS_KEY", (String)dataSchemeKey));
        map.put(DEPLOY_INFO, deployInfoIndesMap.get("DS_CM_KEY"));
        map.put(TABLEMODEL, deployInfoIndesMap.get("DS_TM_KEY"));
        map.put(TABLE_CODE, tableIndesMap.get("DT_CODE"));
        map.put(TABLEMODEL_NAME, deployInfoIndesMap.get("DS_TABLE_NAME"));
        return map;
    };

    public GraphCacheDefine getGraphCacheDefine() {
        GraphCacheDefine define = new GraphCacheDefine("NR_DATASCHEME_CACHE_NAME", BUILDER.getGraphDefine());
        define.enableGlobalIndex(this.globalIndexSupplier, this.globalIndexRefresher);
        return define;
    }

    public IGraphEditor createGraph() {
        return BUILDER.createGraph();
    }

    public IGraph getGraph(String dataSchemeKey) {
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u52a0\u8f7d\uff1a\u52a0\u8f7d\u6570\u636e\u65b9\u6848\u5f00\u59cb\uff1a{}", (Object)dataSchemeKey);
        long millis = LOGGER.isDebugEnabled() ? System.currentTimeMillis() : 0L;
        IGraph graph = this.getDataSchemeGraph(dataSchemeKey);
        millis = LOGGER.isDebugEnabled() ? System.currentTimeMillis() - millis : millis;
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u52a0\u8f7d\uff1a\u52a0\u8f7d\u6570\u636e\u65b9\u6848\u7ed3\u675f\uff0c\u8017\u65f6\uff1a{}", (Object)millis);
        return graph;
    }

    private IGraph getDataSchemeGraph(String dataSchemeKey) {
        DataSchemeDO dataScheme = this.dataSchemeDao.get(dataSchemeKey);
        if (null == dataScheme) {
            return GraphUtils.emptyGraph();
        }
        IGraphEditor graph = this.createGraph();
        this.loadTable(graph, dataSchemeKey);
        this.loadTableRel(graph, dataSchemeKey);
        this.loadField(graph, dataSchemeKey);
        this.loadDeployInfo(graph, dataSchemeKey);
        return graph.finish();
    }

    private void loadDeployInfo(IGraphEditor graph, String dataSchemeKey) {
        List<DataFieldDeployInfoDO> allInfos = this.dataFieldDeployInfoDao.getByDataSchemeKey(dataSchemeKey);
        for (DataFieldDeployInfoDO info : allInfos) {
            DataSchemeGraphService.loadDeployInfo(graph, info);
        }
    }

    private static void loadDeployInfo(IGraphEditor graph, DataFieldDeployInfo info) {
        INode dataFieldNode;
        INode tableModelNode = graph.getNode(TABLEMODEL, info.getTableModelKey());
        if (null == tableModelNode) {
            tableModelNode = graph.addNode(TABLEMODEL, (Object)new TableModelCacheDTO(info.getTableModelKey(), info.getTableName()));
        }
        if (null == (dataFieldNode = graph.getNode(FIELD, info.getDataFieldKey()))) {
            return;
        }
        graph.addNode(DEPLOY_INFO, (Object)new ColumnModelCacheDTO((TableModelCacheDTO)tableModelNode.getData(TableModelCacheDTO.class), (DataFieldCacheDTO)dataFieldNode.getData(DataFieldCacheDTO.class), info));
    }

    private void loadField(IGraphEditor graph, String dataSchemeKey) {
        HashMap<String, String> strPool = new HashMap<String, String>();
        HashMap<String, FormatProperties> formatPool = new HashMap<String, FormatProperties>();
        List<DataFieldDO> allDataFields = this.dataFieldDao.getByScheme(dataSchemeKey);
        for (DataFieldDO dataFieldDO : allDataFields) {
            DataFieldDTO dataField = DataFieldDTO.valueOf(dataFieldDO);
            this.loadField(graph, dataField, strPool, formatPool);
        }
    }

    private void loadField(IGraphEditor graph, DataFieldDTO dataField, Map<String, String> strPool, Map<String, FormatProperties> formatPool) {
        INode tableNode = graph.getNode(TABLE, dataField.getDataTableKey());
        if (null == tableNode) {
            return;
        }
        DataTableCacheDTO dataTableCacheDTO = (DataTableCacheDTO)tableNode.getData(DataTableCacheDTO.class);
        dataField.setDataTableKey(strPool.computeIfAbsent(dataField.getDataTableKey(), k -> k));
        dataField.setDataSchemeKey(strPool.computeIfAbsent(dataField.getDataSchemeKey(), k -> k));
        dataField.setRefDataEntityKey(strPool.computeIfAbsent(dataField.getRefDataEntityKey(), k -> k));
        dataField.setMeasureUnit(strPool.computeIfAbsent(dataField.getMeasureUnit(), k -> k));
        ObjectMapper mapper = new ObjectMapper();
        if (null != dataField.getFormatProperties()) {
            try {
                dataField.setFormatProperties(formatPool.computeIfAbsent(this.getFormatPropertiesStr(mapper, dataField), k -> dataField.getFormatProperties()));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        this.loadValidationRule(dataTableCacheDTO.getDataTable(), dataField);
        DataFieldCacheDTO dataFieldCacheDTO = new DataFieldCacheDTO(dataField);
        graph.addNode(FIELD, (Object)dataFieldCacheDTO);
        dataTableCacheDTO.getDataFields().add(dataFieldCacheDTO);
    }

    private String getFormatPropertiesStr(ObjectMapper mapper, DataField dataField) throws JsonProcessingException {
        if (dataField.getFormatProperties() == null) {
            return null;
        }
        return mapper.writeValueAsString((Object)dataField.getFormatProperties());
    }

    private void loadValidationRule(DataTable dataTable, DataFieldDTO dataFieldDTO) {
        List<ValidationRule> validationRules = dataFieldDTO.getValidationRules();
        if (validationRules == null) {
            return;
        }
        if (validationRules.isEmpty()) {
            dataFieldDTO.setValidationRules(null);
            return;
        }
        validationRules.removeIf(r -> r.getCompareType() == CompareType.EQUAL);
        for (ValidationRule validationRule : validationRules) {
            ValidationRuleDTO dto = (ValidationRuleDTO)validationRule;
            dto.setFieldCode(dataFieldDTO.getCode());
            dto.setTableCode(dataTable.getCode());
            dto.setFieldType(dataFieldDTO.getDataFieldType());
            dto.setFieldTitle(dataFieldDTO.getTitle());
        }
    }

    private void loadTableRel(IGraphEditor graph, String dataSchemeKey) {
        this.iDataTableRelDao.getByDataScheme(dataSchemeKey).stream().map(DataTableRelDTO::valueOf).forEach(r -> {
            graph.addNode(TABLE_REL, r);
            INode tableNode = graph.getNode(TABLE, r.getDesTableKey());
            if (null != tableNode) {
                ((DataTableCacheDTO)tableNode.getData(DataTableCacheDTO.class)).getDataTableRels().add((DataTableRel)r);
            }
            if (null != (tableNode = graph.getNode(TABLE, r.getSrcTableKey()))) {
                ((DataTableCacheDTO)tableNode.getData(DataTableCacheDTO.class)).getDataTableRels().add((DataTableRel)r);
            }
        });
    }

    private void loadTable(IGraphEditor graph, String dataSchemeKey) {
        HashMap<String, String> strPool = new HashMap<String, String>();
        List<DataTableDO> tables = this.dataTableDao.getByDataScheme(dataSchemeKey);
        for (DataTableDO dataTableDO : tables) {
            DataTableDTO dataTableDTO = DataTableDTO.valueOf(dataTableDO);
            this.loadTable(graph, dataTableDTO, strPool);
        }
    }

    private void loadTable(IGraphEditor graph, DataTableDTO dataTableDTO, Map<String, String> strPool) {
        dataTableDTO.setDataSchemeKey(strPool.computeIfAbsent(dataTableDTO.getDataSchemeKey(), k -> k));
        dataTableDTO.setDataGroupKey(strPool.computeIfAbsent(dataTableDTO.getDataGroupKey(), k -> k));
        graph.addNode(TABLE, (Object)new DataTableCacheDTO(dataTableDTO));
    }

    public IGraph getDataSchemeGraph() {
        IGraphEditor graph = DATA_SCHEME_GRAPH_BUILDER.createGraph();
        List<DataSchemeDO> dataSchemes = this.dataSchemeDao.getAll();
        List<DataDimDO> dataDims = this.dataDimDao.getAll();
        HashMap<String, DataSchemeCacheDTO> schemes = new HashMap<String, DataSchemeCacheDTO>();
        for (DataSchemeDO dataScheme : dataSchemes) {
            schemes.put(dataScheme.getKey(), new DataSchemeCacheDTO(DataSchemeDTO.valueOf(dataScheme), new ArrayList<DataDimDTO>()));
        }
        for (DataDimDO dataDim : dataDims) {
            DataSchemeCacheDTO scheme = (DataSchemeCacheDTO)schemes.get(dataDim.getDataSchemeKey());
            if (null == scheme) continue;
            scheme.getDataDimensions().add(DataDimDTO.valueOf(dataDim));
        }
        for (DataSchemeCacheDTO scheme : schemes.values()) {
            graph.addNode(SCHEME, (Object)scheme);
        }
        return graph.finish();
    }

    public IGraph getGraph(IGraph graph, String dataSchemeKey, Set<String> dataTableKeys) {
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u52a0\u8f7d\uff1a\u5237\u65b0\u6570\u636e\u65b9\u6848\u5f00\u59cb\uff1a{}, {}", (Object)dataSchemeKey, (Object)dataTableKeys);
        long millis = LOGGER.isDebugEnabled() ? System.currentTimeMillis() : 0L;
        IGraphEditor graphEditor = this.createGraph();
        ArrayList<String> keys = new ArrayList<String>(dataTableKeys);
        List<DataTableDTO> dataTables = this.dataTableDao.batchGet(keys).stream().map(DataTableDTO::valueOf).collect(Collectors.toList());
        List<DataFieldDTO> dataFields = this.dataFieldDao.batchGetByTable(keys).stream().map(DataFieldDTO::valueOf).collect(Collectors.toList());
        ArrayList<DataFieldDeployInfoDO> infos = new ArrayList<DataFieldDeployInfoDO>(this.dataFieldDeployInfoDao.getByDataTableKeys(keys));
        graph.forEachNode(TABLE, node -> {
            DataTableCacheDTO data = (DataTableCacheDTO)node.getData(DataTableCacheDTO.class);
            DataTableDTO dataTable = data.getDataTable();
            if (!dataTableKeys.contains(dataTable.getKey())) {
                dataTables.add(dataTable);
            }
        });
        graph.forEachNode(FIELD, node -> {
            DataFieldCacheDTO data = (DataFieldCacheDTO)node.getData(DataFieldCacheDTO.class);
            DataFieldDTO dataField = data.getDataField();
            if (!dataTableKeys.contains(dataField.getDataTableKey())) {
                dataFields.add(dataField);
            }
        });
        graph.forEachNode(DEPLOY_INFO, node -> {
            ColumnModelCacheDTO data = (ColumnModelCacheDTO)node.getData(ColumnModelCacheDTO.class);
            if (!dataTableKeys.contains(data.getDataTableKey())) {
                infos.add((DataFieldDeployInfoDO)((Object)data));
            }
        });
        HashMap strPool = new HashMap();
        HashMap formatPool = new HashMap();
        dataTables.forEach(dataTable -> this.loadTable(graphEditor, (DataTableDTO)dataTable, strPool));
        dataFields.forEach(dataField -> this.loadField(graphEditor, (DataFieldDTO)dataField, strPool, formatPool));
        infos.forEach(info -> DataSchemeGraphService.loadDeployInfo(graphEditor, info));
        this.loadTableRel(graphEditor, dataSchemeKey);
        millis = LOGGER.isDebugEnabled() ? System.currentTimeMillis() - millis : millis;
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u52a0\u8f7d\uff1a\u5237\u65b0\u6570\u636e\u65b9\u6848\u7ed3\u675f\uff0c\u8017\u65f6\uff1a{}", (Object)millis);
        return graphEditor.finish();
    }
}

