/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTableRel
 *  com.jiuqi.nr.datascheme.api.FieldSearchQuery
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.event.RefreshCache
 *  com.jiuqi.nr.datascheme.api.event.RefreshScheme
 *  com.jiuqi.nr.datascheme.api.event.RefreshTable
 *  com.jiuqi.nr.datascheme.api.service.IdMutexProvider
 *  com.jiuqi.nr.datascheme.api.service.IdMutexProvider$Mutex
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.graph.GraphHelper
 *  com.jiuqi.nr.graph.IGraph
 *  com.jiuqi.nr.graph.IGraphCache
 *  com.jiuqi.nr.graph.INode
 *  com.jiuqi.nr.graph.IRWLockExecuterManager
 *  com.jiuqi.nr.graph.cache.GraphCacheDefine
 *  com.jiuqi.nr.graph.label.IndexLabel
 *  com.jiuqi.nr.graph.label.NodeLabel
 *  com.jiuqi.nr.graph.util.GraphUtils
 */
package com.jiuqi.nr.datascheme.internal.service.impl.cache2;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTableRel;
import com.jiuqi.nr.datascheme.api.FieldSearchQuery;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.event.RefreshCache;
import com.jiuqi.nr.datascheme.api.event.RefreshScheme;
import com.jiuqi.nr.datascheme.api.event.RefreshTable;
import com.jiuqi.nr.datascheme.api.service.IdMutexProvider;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.internal.dto.DataDimDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataTableDTO;
import com.jiuqi.nr.datascheme.internal.service.DataFieldDeployInfoService;
import com.jiuqi.nr.datascheme.internal.service.DataFieldService;
import com.jiuqi.nr.datascheme.internal.service.DataSchemeService;
import com.jiuqi.nr.datascheme.internal.service.DataTableRelService;
import com.jiuqi.nr.datascheme.internal.service.DataTableService;
import com.jiuqi.nr.datascheme.internal.service.SchemeRefreshListener;
import com.jiuqi.nr.datascheme.internal.service.impl.cache2.DataSchemeGraphService;
import com.jiuqi.nr.datascheme.internal.service.impl.cache2.dto.DataFieldCacheDTO;
import com.jiuqi.nr.datascheme.internal.service.impl.cache2.dto.DataSchemeCacheDTO;
import com.jiuqi.nr.datascheme.internal.service.impl.cache2.dto.DataTableCacheDTO;
import com.jiuqi.nr.datascheme.internal.service.impl.cache2.dto.TableModelCacheDTO;
import com.jiuqi.nr.graph.GraphHelper;
import com.jiuqi.nr.graph.IGraph;
import com.jiuqi.nr.graph.IGraphCache;
import com.jiuqi.nr.graph.INode;
import com.jiuqi.nr.graph.IRWLockExecuterManager;
import com.jiuqi.nr.graph.cache.GraphCacheDefine;
import com.jiuqi.nr.graph.label.IndexLabel;
import com.jiuqi.nr.graph.label.NodeLabel;
import com.jiuqi.nr.graph.util.GraphUtils;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
@Primary
public class DataSchemeCacheService
implements DataSchemeService,
DataTableService,
DataTableRelService,
DataFieldService,
DataFieldDeployInfoService,
SchemeRefreshListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSchemeCacheService.class);
    @Autowired
    @Qualifier(value="RuntimeDataFieldServiceImpl-NO_CACHE")
    private DataFieldService dataFieldService;
    @Autowired
    @Qualifier(value="RuntimeDataTableServiceImpl-NO_CACHE")
    private DataTableService dataTableService;
    @Autowired
    @Qualifier(value="RuntimeSchemeServiceImpl-NO_CACHE")
    private DataSchemeService dataSchemeService;
    @Autowired
    private DataSchemeGraphService dataSchemeGraphService;
    @Autowired
    private IRWLockExecuterManager rwLockExecuterManager;
    private volatile IGraphCache cache;
    private final IdMutexProvider idMutexProvider = new IdMutexProvider();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private IGraph getDataSchemeGraph() {
        if (null != DataSchemeCache.cache) {
            return DataSchemeCache.cache;
        }
        Class<DataSchemeCache> clazz = DataSchemeCache.class;
        synchronized (DataSchemeCache.class) {
            if (null != DataSchemeCache.cache) {
                // ** MonitorExit[var1_1] (shouldn't be in output)
                return DataSchemeCache.cache;
            }
            DataSchemeCache.cache = this.dataSchemeGraphService.getDataSchemeGraph();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return DataSchemeCache.cache;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private IGraphCache getCache() {
        if (null == this.cache) {
            DataSchemeCacheService dataSchemeCacheService = this;
            synchronized (dataSchemeCacheService) {
                if (null == this.cache) {
                    this.cache = GraphHelper.createGraphCache((GraphCacheDefine)this.dataSchemeGraphService.getGraphCacheDefine());
                }
            }
        }
        return this.cache;
    }

    public void init() {
        new Thread(this::getCache).start();
    }

    @Override
    public void onClearCache() {
        LOGGER.info("\u6570\u636e\u65b9\u6848\u7f13\u5b58\u670d\u52a1\uff1a\u6e05\u7406\u5168\u90e8\u6570\u636e\u65b9\u6848\u7f13\u5b58");
        this.getCache().clear();
        DataSchemeCache.cache = null;
    }

    @Override
    public void onClearCache(RefreshCache refreshCache) {
        if (null == refreshCache || refreshCache.isRefreshAll()) {
            this.onClearCache();
            return;
        }
        Map refreshTables = refreshCache.getRefreshTable();
        if (null == refreshTables) {
            this.onClearCache();
            return;
        }
        boolean refreshScheme = false;
        for (Map.Entry entry : refreshTables.entrySet()) {
            RefreshScheme scheme = (RefreshScheme)entry.getKey();
            Set tables = (Set)entry.getValue();
            if (null == tables) {
                refreshScheme = true;
                continue;
            }
            if (scheme.isRefreshAll()) {
                LOGGER.info("\u6570\u636e\u65b9\u6848\u7f13\u5b58\u670d\u52a1\uff1a\u79fb\u9664\u6570\u636e\u65b9\u6848\u7f13\u5b58-{}", (Object)scheme);
                this.cache.remove(scheme.getKey());
                refreshScheme = true;
                continue;
            }
            if (tables.isEmpty()) continue;
            LOGGER.info("\u6570\u636e\u65b9\u6848\u7f13\u5b58\u670d\u52a1\uff1a\u5237\u65b0\u6570\u636e\u65b9\u6848\u7f13\u5b58-{}\uff0c{}", (Object)scheme, (Object)tables);
            IGraph graph = this.cache.get(scheme.getKey());
            if (null == graph) continue;
            IGraph newGraph = this.dataSchemeGraphService.getGraph(graph, scheme.getKey(), tables.stream().map(RefreshTable::getTableKey).collect(Collectors.toSet()));
            this.cache.put(scheme.getKey(), newGraph);
        }
        if (refreshScheme) {
            DataSchemeCache.cache = null;
        }
    }

    public static String getLockName(String dataSchemeKey) {
        return "datascheme_cache_service_".concat(dataSchemeKey);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private IGraph getGraph(String dataSchemeKey) {
        if (!StringUtils.hasText(dataSchemeKey)) {
            return GraphUtils.emptyGraph();
        }
        IGraph iGraph = this.getCache().get(dataSchemeKey);
        if (null != iGraph) {
            return iGraph;
        }
        String lockName = DataSchemeCacheService.getLockName(dataSchemeKey);
        IdMutexProvider.Mutex mutex = this.idMutexProvider.getMutex(lockName);
        synchronized (mutex) {
            iGraph = this.getCache().get(dataSchemeKey);
            if (null != iGraph) {
                return iGraph;
            }
            return (IGraph)this.rwLockExecuterManager.getRWLockExecuter(lockName).tryRead(() -> {
                IGraph g = this.dataSchemeGraphService.getGraph(dataSchemeKey);
                this.getCache().put(dataSchemeKey, g);
                return g;
            });
        }
    }

    private IGraph getGraph(NodeLabel label, String key) {
        return this.getCache().getByIndex(label, key, dataSchemeKey -> {
            if (!StringUtils.hasText(dataSchemeKey) && DataSchemeGraphService.TABLE == label) {
                DataTableDTO dataTable = this.dataTableService.getDataTable(key);
                dataSchemeKey = null == dataTable ? null : dataTable.getDataSchemeKey();
            }
            return this.getGraph((String)dataSchemeKey);
        });
    }

    private IGraph getGraph(IndexLabel label, String key) {
        return this.getCache().getByIndex(label, key, this::getGraph);
    }

    private IGraph getGraphByTableKey(String dataTableKey) {
        return this.getGraph(DataSchemeGraphService.TABLE, dataTableKey);
    }

    private IGraph getGraphByTableCode(String dataTableCode) {
        return this.getGraph(DataSchemeGraphService.TABLE_CODE, dataTableCode);
    }

    private IGraph getGraphByTableModelKey(String tableModelKey) {
        return this.getGraph(DataSchemeGraphService.TABLEMODEL, tableModelKey);
    }

    private IGraph getGraphByTableModelName(String tableModelName) {
        return this.getGraph(DataSchemeGraphService.TABLEMODEL_NAME, tableModelName);
    }

    private IGraph getGraphByFieldKey(String dataFieldKey) {
        return this.getGraph(DataSchemeGraphService.FIELD, dataFieldKey);
    }

    private Map<String, IGraph> getGraphByFieldKeys(String ... dataFieldKeys) {
        HashMap<String, IGraph> map = new HashMap<String, IGraph>();
        for (String dataFieldKey : dataFieldKeys) {
            map.put(dataFieldKey, this.getGraphByFieldKey(dataFieldKey));
        }
        return map;
    }

    private IGraph getGraphByColumnKey(String columnKey) {
        return this.getGraph(DataSchemeGraphService.DEPLOY_INFO, columnKey);
    }

    private Map<String, IGraph> getGraphByColumnKeys(String ... columnKeys) {
        HashMap<String, IGraph> map = new HashMap<String, IGraph>();
        for (String columnKey : columnKeys) {
            map.put(columnKey, this.getGraphByColumnKey(columnKey));
        }
        return map;
    }

    private List<INode> getFieldNodes(String ... dataFieldKeys) {
        Map<String, IGraph> graphByFieldKeys = this.getGraphByFieldKeys(dataFieldKeys);
        ArrayList<INode> nodes = new ArrayList<INode>();
        new HashSet<IGraph>(graphByFieldKeys.values()).forEach(g -> nodes.addAll(g.getNodes(DataSchemeGraphService.FIELD, dataFieldKeys)));
        return nodes;
    }

    private Map<String, IGraph> getGraphByTableKeys(String ... dataTableKeys) {
        HashMap<String, IGraph> map = new HashMap<String, IGraph>();
        for (String dataTableKey : dataTableKeys) {
            map.put(dataTableKey, this.getGraphByTableKey(dataTableKey));
        }
        return map;
    }

    private List<INode> getTableNodes(String ... dataTableKeys) {
        Map<String, IGraph> graphByTableKeys = this.getGraphByTableKeys(dataTableKeys);
        ArrayList<INode> nodes = new ArrayList<INode>();
        new HashSet<IGraph>(graphByTableKeys.values()).forEach(g -> nodes.addAll(g.getNodes(DataSchemeGraphService.TABLE, dataTableKeys)));
        return nodes;
    }

    @Override
    public List<DataFieldDeployInfo> getByDataSchemeKey(String dataSchemeKey) {
        IGraph graph = this.getGraph(dataSchemeKey);
        if (null == graph) {
            return Collections.emptyList();
        }
        return graph.getNodes(DataSchemeGraphService.DEPLOY_INFO).stream().map(n -> (DataFieldDeployInfo)n.getData(DataFieldDeployInfo.class)).collect(Collectors.toList());
    }

    @Override
    public List<DataFieldDeployInfo> getByDataTableKey(String dataTableKey) {
        IGraph graph = this.getGraphByTableKey(dataTableKey);
        if (null == graph) {
            return Collections.emptyList();
        }
        INode node = graph.getNode(DataSchemeGraphService.TABLE, dataTableKey);
        if (null == node) {
            return Collections.emptyList();
        }
        ArrayList<DataFieldDeployInfo> infos = new ArrayList<DataFieldDeployInfo>();
        List<DataFieldCacheDTO> dataFields = ((DataTableCacheDTO)node.getData(DataTableCacheDTO.class)).getDataFields();
        for (DataFieldCacheDTO dataField : dataFields) {
            infos.addAll(dataField.getDeployInfos());
        }
        return infos;
    }

    @Override
    public List<DataFieldDeployInfo> getByTableModelKey(String tableModelKey) {
        IGraph graph = this.getGraphByTableModelKey(tableModelKey);
        if (null == graph) {
            return Collections.emptyList();
        }
        INode node = graph.getNode(DataSchemeGraphService.TABLEMODEL, tableModelKey);
        if (null == node) {
            return Collections.emptyList();
        }
        return ((TableModelCacheDTO)node.getData(TableModelCacheDTO.class)).getDeployInfos();
    }

    @Override
    public List<DataFieldDeployInfo> getByTableName(String tableName) {
        IGraph graph = this.getGraphByTableModelName(tableName);
        if (null == graph) {
            return Collections.emptyList();
        }
        INode node = graph.getNode(DataSchemeGraphService.TABLEMODEL_NAME, tableName);
        if (null == node) {
            return Collections.emptyList();
        }
        return ((TableModelCacheDTO)node.getData(TableModelCacheDTO.class)).getDeployInfos();
    }

    @Override
    public List<DataFieldDeployInfo> getByDataFieldKeys(String ... dataFieldKeys) {
        if (null == dataFieldKeys || 0 == dataFieldKeys.length) {
            return Collections.emptyList();
        }
        List<INode> nodes = this.getFieldNodes(dataFieldKeys);
        ArrayList<DataFieldDeployInfo> infos = new ArrayList<DataFieldDeployInfo>();
        nodes.forEach(n -> {
            if (null != n) {
                infos.addAll(((DataFieldCacheDTO)n.getData(DataFieldCacheDTO.class)).getDeployInfos());
            }
        });
        return infos;
    }

    @Override
    public List<DataFieldDeployInfo> getByColumnModelKeys(List<String> columnModelKeys) {
        if (null == columnModelKeys || columnModelKeys.isEmpty()) {
            return Collections.emptyList();
        }
        String[] keys = columnModelKeys.toArray(new String[0]);
        Map<String, IGraph> graphByFieldKeys = this.getGraphByColumnKeys(keys);
        ArrayList<DataFieldDeployInfo> infos = new ArrayList<DataFieldDeployInfo>();
        new HashSet<IGraph>(graphByFieldKeys.values()).forEach(g -> infos.addAll(g.getNodes(DataSchemeGraphService.DEPLOY_INFO, keys).stream().map(node -> (DataFieldDeployInfo)node.getData(DataFieldDeployInfo.class)).collect(Collectors.toList())));
        return infos;
    }

    @Override
    public DataFieldDeployInfo getByColumnModelKey(String columnModelKey) {
        IGraph graph = this.getGraphByColumnKey(columnModelKey);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(DataSchemeGraphService.DEPLOY_INFO, columnModelKey);
        if (null == node) {
            return null;
        }
        return (DataFieldDeployInfo)node.getData(DataFieldDeployInfo.class);
    }

    @Override
    public String getDataTableByTableModel(String tableModelKey) {
        IGraph graph = this.getGraphByTableModelKey(tableModelKey);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(DataSchemeGraphService.TABLEMODEL, tableModelKey);
        if (null == node) {
            return null;
        }
        TableModelCacheDTO modelCache = (TableModelCacheDTO)node.getData(TableModelCacheDTO.class);
        return modelCache.getDeployInfos().get(0).getDataTableKey();
    }

    @Override
    public List<DataFieldDTO> getAllDataField(String scheme) {
        IGraph graph = this.getGraph(scheme);
        if (null == graph) {
            return Collections.emptyList();
        }
        return graph.getNodes(DataSchemeGraphService.FIELD).stream().map(n -> ((DataFieldCacheDTO)n.getData(DataFieldCacheDTO.class)).getDataField()).sorted().collect(Collectors.toList());
    }

    @Override
    public DataFieldDTO getDataField(String key) {
        IGraph graph = this.getGraphByFieldKey(key);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(DataSchemeGraphService.FIELD, key);
        return null == node ? null : ((DataFieldCacheDTO)node.getData(DataFieldCacheDTO.class)).getDataField();
    }

    @Override
    public List<DataFieldDTO> getDataFields(List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptyList();
        }
        String[] dataFieldKeys = keys.toArray(new String[0]);
        Map<String, IGraph> graphs = this.getGraphByFieldKeys(dataFieldKeys);
        ArrayList<DataFieldDTO> dtos = new ArrayList<DataFieldDTO>();
        for (String key : keys) {
            INode node;
            IGraph graph = graphs.get(key);
            if (null == graph || null == (node = graph.getNode(DataSchemeGraphService.FIELD, key))) continue;
            dtos.add(((DataFieldCacheDTO)node.getData(DataFieldCacheDTO.class)).getDataField());
        }
        return dtos;
    }

    @Override
    public List<DataFieldDTO> getDataFields(String dataSchemeKey, List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptyList();
        }
        IGraph graph = this.getGraph(dataSchemeKey);
        if (null == graph) {
            return Collections.emptyList();
        }
        ArrayList<DataFieldDTO> dtos = new ArrayList<DataFieldDTO>();
        for (String key : keys) {
            INode node = graph.getNode(DataSchemeGraphService.FIELD, key);
            if (null == node) continue;
            dtos.add(((DataFieldCacheDTO)node.getData(DataFieldCacheDTO.class)).getDataField());
        }
        return dtos;
    }

    @Override
    public List<DataFieldDTO> getDataFieldByTable(String tableKey) {
        IGraph graph = this.getGraphByTableKey(tableKey);
        if (null == graph) {
            return Collections.emptyList();
        }
        INode node = graph.getNode(DataSchemeGraphService.TABLE, tableKey);
        if (null == node) {
            return Collections.emptyList();
        }
        return ((DataTableCacheDTO)node.getData(DataTableCacheDTO.class)).getDataFields().stream().map(DataFieldCacheDTO::getDataField).sorted().collect(Collectors.toList());
    }

    @Override
    public DataFieldDTO getDataFieldByTableKeyAndCode(String table, String code) {
        IGraph graph = this.getGraphByTableKey(table);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(DataSchemeGraphService.TABLE, table);
        if (null == node) {
            return null;
        }
        DataTableCacheDTO dataTable = (DataTableCacheDTO)node.getData(DataTableCacheDTO.class);
        DataFieldCacheDTO dataField = dataTable.getDataFieldByCode(code);
        return null == dataField ? null : dataField.getDataField();
    }

    @Override
    public DataFieldDTO getZbKindDataFieldBySchemeKeyAndCode(String scheme, String code) {
        IGraph graph = this.getGraph(scheme);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(DataSchemeGraphService.FIELD_CODE, code);
        return null == node ? null : ((DataFieldCacheDTO)node.getData(DataFieldCacheDTO.class)).getDataField();
    }

    @Override
    public List<DataFieldDTO> getDataFieldByTableCode(String tableCode) {
        IGraph graph = this.getGraphByTableCode(tableCode);
        if (null == graph) {
            return Collections.emptyList();
        }
        INode node = graph.getNode(DataSchemeGraphService.TABLE_CODE, tableCode);
        if (null == node) {
            return Collections.emptyList();
        }
        return ((DataTableCacheDTO)node.getData(DataTableCacheDTO.class)).getDataFields().stream().map(DataFieldCacheDTO::getDataField).sorted().collect(Collectors.toList());
    }

    @Override
    public List<DataFieldDTO> getBizDataFieldByTableKey(String tableKey) {
        IGraph graph = this.getGraphByTableKey(tableKey);
        if (null == graph) {
            return Collections.emptyList();
        }
        INode node = graph.getNode(DataSchemeGraphService.TABLE, tableKey);
        if (null == node) {
            return Collections.emptyList();
        }
        return this.getBizDataFields(node);
    }

    private List<DataFieldDTO> getBizDataFields(INode node) {
        DataTableCacheDTO table = (DataTableCacheDTO)node.getData(DataTableCacheDTO.class);
        Object[] bizKeys = table.getDataTable().getBizKeys();
        if (ObjectUtils.isEmpty(bizKeys)) {
            return Collections.emptyList();
        }
        List<Object> dataFieldKeys = Arrays.asList(bizKeys);
        return table.getDataFields().stream().filter(f -> dataFieldKeys.contains(f.getKey())).map(DataFieldCacheDTO::getDataField).sorted().collect(Collectors.toList());
    }

    @Override
    public List<DataFieldDTO> getBizDataFieldByTableCode(String tableCode) {
        IGraph graph = this.getGraphByTableCode(tableCode);
        if (null == graph) {
            return Collections.emptyList();
        }
        INode node = graph.getNode(DataSchemeGraphService.TABLE_CODE, tableCode);
        if (null == node) {
            return Collections.emptyList();
        }
        return this.getBizDataFields(node);
    }

    @Override
    public List<DataFieldDTO> getDataFieldByTableKeyAndType(String tableKey, DataFieldType ... dataFieldType) {
        if (null == dataFieldType || 0 == dataFieldType.length) {
            return this.getDataFieldByTable(tableKey);
        }
        List<DataFieldType> asList = Arrays.asList(dataFieldType);
        return this.getDataFieldByTable(tableKey).stream().filter(Objects::nonNull).filter(r -> asList.contains(r.getDataFieldType())).collect(Collectors.toList());
    }

    @Override
    public List<DataFieldDTO> getDataFieldByTableKeyAndKind(String tableKey, DataFieldKind ... dataFieldKinds) {
        if (null == dataFieldKinds || 0 == dataFieldKinds.length) {
            return this.getDataFieldByTable(tableKey);
        }
        List<DataFieldKind> asList = Arrays.asList(dataFieldKinds);
        return this.getDataFieldByTable(tableKey).stream().filter(Objects::nonNull).filter(r -> asList.contains(r.getDataFieldKind())).collect(Collectors.toList());
    }

    @Override
    public List<DataFieldDTO> getDataFieldByTableCodeAndType(String tableCode, DataFieldType ... dataFieldType) {
        if (null == dataFieldType || 0 == dataFieldType.length) {
            return this.getDataFieldByTableCode(tableCode);
        }
        List<DataFieldType> asList = Arrays.asList(dataFieldType);
        return this.getDataFieldByTableCode(tableCode).stream().filter(Objects::nonNull).filter(r -> asList.contains(r.getDataFieldType())).collect(Collectors.toList());
    }

    @Override
    public List<DataFieldDTO> getDataFieldByTableCodeAndKind(String tableCode, DataFieldKind ... dataFieldKinds) {
        if (null == dataFieldKinds || 0 == dataFieldKinds.length) {
            return this.getDataFieldByTableCode(tableCode);
        }
        List<DataFieldKind> asList = Arrays.asList(dataFieldKinds);
        return this.getDataFieldByTableCode(tableCode).stream().filter(Objects::nonNull).filter(r -> asList.contains(r.getDataFieldKind())).collect(Collectors.toList());
    }

    @Override
    public List<DataFieldDTO> getDataFieldBySchemeAndKind(String scheme, DataFieldKind ... dataFieldKinds) {
        if (null == dataFieldKinds || 0 == dataFieldKinds.length) {
            return this.getAllDataField(scheme);
        }
        List<DataFieldKind> asList = Arrays.asList(dataFieldKinds);
        return this.getAllDataField(scheme).stream().filter(Objects::nonNull).filter(r -> asList.contains(r.getDataFieldKind())).collect(Collectors.toList());
    }

    @Override
    public List<DataFieldDTO> getDataFieldBySchemeAndCode(String scheme, String code, DataFieldKind ... dataFieldKinds) {
        List<DataFieldKind> asList = Arrays.asList(dataFieldKinds);
        return this.getAllDataField(scheme).stream().filter(Objects::nonNull).filter(r -> r.getCode().equals(code) && asList.contains(r.getDataFieldKind())).collect(Collectors.toList());
    }

    @Override
    public DataTableRel getBySrcTable(String srcTableKey) {
        IGraph graph = this.getGraphByTableKey(srcTableKey);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(DataSchemeGraphService.TABLE, srcTableKey);
        if (null == node) {
            return null;
        }
        return ((DataTableCacheDTO)node.getData(DataTableCacheDTO.class)).getDataTableRels().stream().filter(r -> r.getSrcTableKey().equals(srcTableKey)).findFirst().orElse(null);
    }

    @Override
    public List<DataTableRel> getByDesTable(String desTableKey) {
        IGraph graph = this.getGraphByTableKey(desTableKey);
        if (null == graph) {
            return Collections.emptyList();
        }
        INode node = graph.getNode(DataSchemeGraphService.TABLE, desTableKey);
        if (null == node) {
            return Collections.emptyList();
        }
        return ((DataTableCacheDTO)node.getData(DataTableCacheDTO.class)).getDataTableRels().stream().filter(r -> r.getDesTableKey().equals(desTableKey)).collect(Collectors.toList());
    }

    @Override
    public List<DataTableDTO> getAllDataTable(String scheme) {
        IGraph graph = this.getGraph(scheme);
        if (null == graph) {
            return Collections.emptyList();
        }
        return graph.getNodes(DataSchemeGraphService.TABLE).stream().map(n -> ((DataTableCacheDTO)n.getData(DataTableCacheDTO.class)).getDataTable()).sorted().collect(Collectors.toList());
    }

    @Override
    public DataTableDTO getDataTable(String key) {
        IGraph graph = this.getGraphByTableKey(key);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(DataSchemeGraphService.TABLE, key);
        return null == node ? null : ((DataTableCacheDTO)node.getData(DataTableCacheDTO.class)).getDataTable();
    }

    @Override
    public DataTableDTO getDataTableByCode(String code) {
        IGraph graph = this.getGraphByTableCode(code);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(DataSchemeGraphService.TABLE_CODE, code);
        return null == node ? null : ((DataTableCacheDTO)node.getData(DataTableCacheDTO.class)).getDataTable();
    }

    @Override
    public List<DataTableDTO> getDataTables(List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptyList();
        }
        String[] dataTableKeys = keys.toArray(new String[0]);
        List<INode> nodes = this.getTableNodes(dataTableKeys);
        return nodes.stream().map(n -> ((DataTableCacheDTO)n.getData(DataTableCacheDTO.class)).getDataTable()).sorted().collect(Collectors.toList());
    }

    @Override
    public List<DataTableDTO> getDataTableByScheme(String schemeKey) {
        IGraph graph = this.getGraph(schemeKey);
        if (null == graph) {
            return Collections.emptyList();
        }
        return graph.getNodes(DataSchemeGraphService.TABLE).stream().map(n -> ((DataTableCacheDTO)n.getData(DataTableCacheDTO.class)).getDataTable()).filter(t -> !StringUtils.hasText(t.getDataGroupKey())).sorted().collect(Collectors.toList());
    }

    @Override
    public List<DataSchemeDTO> getAllDataScheme() {
        IGraph graph = this.getDataSchemeGraph();
        return graph.getNodes(DataSchemeGraphService.SCHEME).stream().map(n -> ((DataSchemeCacheDTO)n.getData(DataSchemeCacheDTO.class)).getDataScheme()).sorted().collect(Collectors.toList());
    }

    @Override
    public List<DataSchemeDTO> getDataSchemes(List<String> keys) {
        IGraph graph = this.getDataSchemeGraph();
        ArrayList<DataSchemeDTO> schemes = new ArrayList<DataSchemeDTO>();
        for (String key : keys) {
            INode node = graph.getNode(DataSchemeGraphService.SCHEME, key);
            if (null == node) continue;
            schemes.add(((DataSchemeCacheDTO)node.getData(DataSchemeCacheDTO.class)).getDataScheme());
        }
        return schemes;
    }

    @Override
    public DataSchemeDTO getDataScheme(String key) {
        IGraph graph = this.getDataSchemeGraph();
        INode node = graph.getNode(DataSchemeGraphService.SCHEME, key);
        return null == node ? null : ((DataSchemeCacheDTO)node.getData(DataSchemeCacheDTO.class)).getDataScheme();
    }

    @Override
    public DataSchemeDTO getDataSchemeByCode(String code) {
        IGraph graph = this.getDataSchemeGraph();
        INode node = graph.getNode(DataSchemeGraphService.SCHEME_CODE, code);
        return null == node ? null : ((DataSchemeCacheDTO)node.getData(DataSchemeCacheDTO.class)).getDataScheme();
    }

    @Override
    public List<DataDimDTO> getDataSchemeDimension(String dataSchemeKey) {
        IGraph graph = this.getDataSchemeGraph();
        INode node = graph.getNode(DataSchemeGraphService.SCHEME, dataSchemeKey);
        return null == node ? null : ((DataSchemeCacheDTO)node.getData(DataSchemeCacheDTO.class)).getDataDimensions();
    }

    @Override
    public List<DataSchemeDTO> searchByKeyword(String keyword) {
        return this.dataSchemeService.searchByKeyword(keyword);
    }

    @Override
    public List<DataTableDTO> searchBy(String scheme, String keyword, int type) {
        if (!StringUtils.hasLength(scheme)) {
            return this.searchBy(Collections.emptyList(), keyword, type);
        }
        return this.searchBy(Collections.singletonList(scheme), keyword, type);
    }

    @Override
    public List<DataTableDTO> searchBy(List<String> schemes, String keyword, int type) {
        if (0 >= type) {
            type = 0;
            for (DataTableType value : DataTableType.values()) {
                type += value.getValue();
            }
        }
        return this.dataTableService.searchBy(schemes, keyword, type);
    }

    @Override
    public List<DataFieldDTO> searchField(FieldSearchQuery fieldSearchQuery) {
        return this.dataFieldService.searchField(fieldSearchQuery);
    }

    @Override
    public List<DataTableDTO> getLatestDataTableByScheme(String scheme) {
        return this.dataTableService.getLatestDataTableByScheme(scheme);
    }

    @Override
    public Instant getLatestDataTableUpdateTime(String scheme) {
        return this.dataTableService.getLatestDataTableUpdateTime(scheme);
    }

    @Override
    public List<DataSchemeDTO> getDataSchemeByParent(String parent) {
        return this.dataSchemeService.getDataSchemeByParent(parent);
    }

    @Override
    public List<DataTableDTO> getDataTableByGroup(String parentKey) {
        return this.dataTableService.getDataTableByGroup(parentKey);
    }

    @Override
    public List<String> getTableNames(String ... dataTableKeys) {
        if (null == dataTableKeys || 0 == dataTableKeys.length) {
            return Collections.emptyList();
        }
        ArrayList<String> tableNames = new ArrayList<String>();
        for (String dataTableKey : dataTableKeys) {
            DataFieldCacheDTO dataFieldCacheDTO;
            DataTableCacheDTO dataTableCacheDTO;
            INode node;
            IGraph graph = this.getGraphByTableKey(dataTableKey);
            if (null == graph || null == (node = graph.getNode(DataSchemeGraphService.TABLE, dataTableKey)) || null == (dataTableCacheDTO = (DataTableCacheDTO)node.getData(DataTableCacheDTO.class)) || null == (dataFieldCacheDTO = (DataFieldCacheDTO)dataTableCacheDTO.getDataFields().stream().filter(f -> f.getCode().equals("MDCODE")).findAny().orElse(null))) continue;
            List<DataFieldDeployInfo> deployInfos = dataFieldCacheDTO.getDeployInfos();
            for (DataFieldDeployInfo deployInfo : deployInfos) {
                tableNames.add(deployInfo.getTableName());
            }
        }
        return tableNames;
    }

    @Override
    public DataFieldDTO getDataFieldFromMdInfoByCode(String dataSchemeKey, String dataFieldCode) {
        IGraph graph = this.getGraph(dataSchemeKey);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(DataSchemeGraphService.FIELD_CODE, dataFieldCode);
        if (null == node) {
            return null;
        }
        DataFieldCacheDTO data = (DataFieldCacheDTO)node.getData(DataFieldCacheDTO.class);
        DataFieldDTO dataField = data.getDataField();
        INode tableNode = graph.getNode(DataSchemeGraphService.TABLE, dataField.getDataTableKey());
        if (null == tableNode) {
            return null;
        }
        DataTableDTO dataTable = ((DataTableCacheDTO)tableNode.getData(DataTableCacheDTO.class)).getDataTable();
        if (null != dataTable && DataTableType.MD_INFO == dataTable.getDataTableType()) {
            return dataField;
        }
        return null;
    }

    @Override
    public List<DataFieldDeployInfo> getByDataFieldKey(String dataSchemeKey, String dataFieldKey) {
        IGraph graph = this.getGraph(dataSchemeKey);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(DataSchemeGraphService.FIELD, dataFieldKey);
        if (null == node) {
            return null;
        }
        return ((DataFieldCacheDTO)node.getData(DataFieldCacheDTO.class)).getDeployInfos();
    }

    @Override
    public List<DataFieldDeployInfo> getByDataFieldKeys(String dataSchemeKey, List<String> dataFieldKeys) {
        IGraph graph = this.getGraph(dataSchemeKey);
        if (null == graph) {
            return null;
        }
        ArrayList<DataFieldDeployInfo> infos = new ArrayList<DataFieldDeployInfo>();
        for (String dataFieldKey : dataFieldKeys) {
            INode node = graph.getNode(DataSchemeGraphService.FIELD, dataFieldKey);
            if (null == node) continue;
            infos.addAll(((DataFieldCacheDTO)node.getData(DataFieldCacheDTO.class)).getDeployInfos());
        }
        return infos;
    }

    @Override
    public DataFieldDeployInfo getByColumnModelKey(String dataSchemeKey, String columnKey) {
        IGraph graph = this.getGraph(dataSchemeKey);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(DataSchemeGraphService.DEPLOY_INFO, columnKey);
        if (null == node) {
            return null;
        }
        return (DataFieldDeployInfo)node.getData(DataFieldDeployInfo.class);
    }

    @Override
    public List<DataFieldDeployInfo> getByColumnModelKeys(String dataSchemeKey, List<String> columnKeys) {
        IGraph graph = this.getGraph(dataSchemeKey);
        if (null == graph) {
            return null;
        }
        ArrayList<DataFieldDeployInfo> infos = new ArrayList<DataFieldDeployInfo>();
        for (String columnKey : columnKeys) {
            INode node = graph.getNode(DataSchemeGraphService.DEPLOY_INFO, columnKey);
            if (null == node) continue;
            infos.add((DataFieldDeployInfo)node.getData(DataFieldDeployInfo.class));
        }
        return infos;
    }

    @Override
    public List<DataFieldDeployInfo> getByTableCode(String tableCode) {
        IGraph graph = this.getGraphByTableCode(tableCode);
        if (null == graph) {
            return null;
        }
        INode node = graph.getNode(DataSchemeGraphService.TABLE_CODE, tableCode);
        if (null == node) {
            return Collections.emptyList();
        }
        ArrayList<DataFieldDeployInfo> infos = new ArrayList<DataFieldDeployInfo>();
        List<DataFieldCacheDTO> dataFields = ((DataTableCacheDTO)node.getData(DataTableCacheDTO.class)).getDataFields();
        for (DataFieldCacheDTO dataField : dataFields) {
            infos.addAll(dataField.getDeployInfos());
        }
        return infos;
    }

    @Override
    public DataFieldDTO findDataField(String dataSchemeKey, List<String> dataFieldKeys, Predicate<DataField> predicate) {
        IGraph graph = this.getGraph(dataSchemeKey);
        if (null == graph) {
            return null;
        }
        for (String key : dataFieldKeys) {
            DataFieldDTO field;
            INode node = graph.getNode(DataSchemeGraphService.FIELD, key);
            if (null == node || !predicate.test(field = ((DataFieldCacheDTO)node.getData(DataFieldCacheDTO.class)).getDataField())) continue;
            return field;
        }
        return null;
    }

    private static class DataSchemeCache {
        private static volatile IGraph cache = null;

        private DataSchemeCache() {
        }
    }
}

