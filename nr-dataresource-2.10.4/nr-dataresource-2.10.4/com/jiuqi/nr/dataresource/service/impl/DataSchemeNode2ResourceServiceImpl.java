/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.loader.run.RuntimeLevelLoader
 *  com.jiuqi.nr.datascheme.api.loader.run.RuntimeSchemeVisitor
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao
 *  com.jiuqi.nr.datascheme.internal.dao.IDataTableDao
 *  com.jiuqi.nr.datascheme.internal.dao.IDataTableRelDao
 *  com.jiuqi.nr.datascheme.internal.entity.DataFieldDO
 *  com.jiuqi.nr.datascheme.internal.entity.DataTableDO
 *  com.jiuqi.nr.datascheme.internal.entity.DataTableRelDO
 *  com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO
 *  com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery
 *  com.jiuqi.nr.datascheme.web.param.SchemeNodeFilter
 *  com.jiuqi.nr.datascheme.web.param.ZbSchemeNodeFilter
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.zb.scheme.service.IZbSchemeService
 *  com.jiuqi.nvwa.definition.common.UUIDUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.dataresource.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.dataresource.DataLinkKind;
import com.jiuqi.nr.dataresource.DataResource;
import com.jiuqi.nr.dataresource.DataResourceDefine;
import com.jiuqi.nr.dataresource.DataResourceKind;
import com.jiuqi.nr.dataresource.DataResourceLink;
import com.jiuqi.nr.dataresource.common.DataResourceEnum;
import com.jiuqi.nr.dataresource.common.DataResourceException;
import com.jiuqi.nr.dataresource.service.IDataLinkService;
import com.jiuqi.nr.dataresource.service.IDataResourceDefineService;
import com.jiuqi.nr.dataresource.service.IDataResourceService;
import com.jiuqi.nr.dataresource.service.IDataSchemeNode2ResourceService;
import com.jiuqi.nr.dataresource.service.impl.CollectAllChildren;
import com.jiuqi.nr.dataresource.web.param.CheckedParam;
import com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.loader.run.RuntimeLevelLoader;
import com.jiuqi.nr.datascheme.api.loader.run.RuntimeSchemeVisitor;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableRelDao;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DataTableDO;
import com.jiuqi.nr.datascheme.internal.entity.DataTableRelDO;
import com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery;
import com.jiuqi.nr.datascheme.web.param.SchemeNodeFilter;
import com.jiuqi.nr.datascheme.web.param.ZbSchemeNodeFilter;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.zb.scheme.service.IZbSchemeService;
import com.jiuqi.nvwa.definition.common.UUIDUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class DataSchemeNode2ResourceServiceImpl
implements IDataSchemeNode2ResourceService {
    private static final Logger logger = LoggerFactory.getLogger(DataSchemeNode2ResourceServiceImpl.class);
    @Autowired
    private IDataLinkService linkService;
    @Autowired
    private IDataResourceService resourceService;
    @Autowired
    private IDataResourceDefineService defineService;
    @Autowired
    private RuntimeLevelLoader runtimeLevelLoader;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IDataTableRelDao<DataTableRelDO> iDataTableRelDao;
    @Autowired
    private IDataTableDao<DataTableDO> iDataTableDao;
    @Autowired
    private IDataFieldDao<DataFieldDO> iDataFieldDao;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private IZbSchemeService zbSchemeService;
    private static final int IS_FIELD_VALUE = NodeType.FIELD.getValue() | NodeType.FIELD_ZB.getValue() | NodeType.TABLE_DIM.getValue();

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    @Transactional(rollbackFor={Exception.class})
    public void add(CheckedParam checked) {
        String defineKey;
        if (checked == null) {
            return;
        }
        String group = checked.getGroup();
        boolean onlyField = checked.isOnlyField();
        Set<RuntimeDataSchemeNodeDTO> selected = checked.getSelected();
        if (CollectionUtils.isEmpty(selected)) {
            return;
        }
        DataResource dataResource = this.resourceService.get(group);
        if (dataResource == null) {
            DataResourceDefine dataResourceDefine = this.defineService.get(group);
            if (dataResourceDefine == null) return;
            if (onlyField) {
                throw new DataResourceException("\u6307\u6807\u53ea\u80fd\u6dfb\u52a0\u5230\u76ee\u5f55\u4e0b");
            }
            defineKey = dataResourceDefine.getKey();
        } else {
            defineKey = dataResource.getResourceDefineKey();
        }
        HashMap<String, RuntimeDataSchemeNodeDTO> keyMap = new HashMap<String, RuntimeDataSchemeNodeDTO>(selected.size());
        HashMap<String, Set<RuntimeDataSchemeNodeDTO>> parentMap = new HashMap<String, Set<RuntimeDataSchemeNodeDTO>>(selected.size());
        for (RuntimeDataSchemeNodeDTO runtimeDataSchemeNodeDTO : selected) {
            keyMap.put(runtimeDataSchemeNodeDTO.getKey(), runtimeDataSchemeNodeDTO);
            Set set = parentMap.computeIfAbsent(runtimeDataSchemeNodeDTO.getParentKey(), r -> new HashSet());
            set.add(runtimeDataSchemeNodeDTO);
        }
        HashMap root = new HashMap(selected.size());
        for (Map.Entry entry : parentMap.entrySet()) {
            if (keyMap.containsKey(entry.getKey())) continue;
            Set value = (Set)entry.getValue();
            if (dataResource == null) {
                for (RuntimeDataSchemeNodeDTO runtimeDataSchemeNodeDTO : value) {
                    int type = runtimeDataSchemeNodeDTO.getType();
                    boolean tableDim = runtimeDataSchemeNodeDTO.isTableDim();
                    if (tableDim || (IS_FIELD_VALUE & type) == 0) continue;
                    throw new DataResourceException("\u6307\u6807\u53ea\u80fd\u6dfb\u52a0\u5230\u76ee\u5f55\u4e0b");
                }
            }
            root.put(entry.getKey(), value);
        }
        if (CollectionUtils.isEmpty(root)) {
            return;
        }
        ArrayList<DataResource> arrayList = new ArrayList<DataResource>();
        ArrayList<DataResourceLink> arrayList2 = new ArrayList<DataResourceLink>();
        HashMap<String, String> key2NewKey = new HashMap<String, String>(selected.size());
        if (defineKey.equals(group)) {
            group = null;
        }
        for (Map.Entry entry : root.entrySet()) {
            Set value = (Set)entry.getValue();
            this.deepChild(group, onlyField, defineKey, arrayList, arrayList2, key2NewKey, value, parentMap);
        }
        if (!arrayList2.isEmpty()) {
            try {
                this.linkService.insert(arrayList2);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new DataResourceException(DataResourceEnum.DATA_RESOURCE_DR_1_2.getMessage());
            }
        }
        if (onlyField || arrayList.isEmpty()) return;
        this.resourceService.insert(arrayList);
    }

    private void deepChild(String group, boolean onlyField, String defineKey, List<DataResource> resources, List<DataResourceLink> links, Map<String, String> key2NewKey, Set<RuntimeDataSchemeNodeDTO> value, Map<String, Set<RuntimeDataSchemeNodeDTO>> parentMap) {
        if (!CollectionUtils.isEmpty(value)) {
            List sortedValue = value.stream().sorted(RuntimeDataSchemeNodeDTO::compareTo).collect(Collectors.toList());
            for (RuntimeDataSchemeNodeDTO nodeDTO : sortedValue) {
                int type = nodeDTO.getType();
                boolean tableDim = nodeDTO.isTableDim();
                if (!tableDim && (IS_FIELD_VALUE & type) != 0) {
                    DataResourceLink init = this.initLink(group, defineKey, key2NewKey, nodeDTO);
                    links.add(init);
                } else if (!onlyField) {
                    DataResource resource = this.initGroupNode(group, defineKey, nodeDTO, key2NewKey);
                    resources.add(resource);
                    key2NewKey.put(nodeDTO.getKey(), resource.getKey());
                }
                Set<RuntimeDataSchemeNodeDTO> child = parentMap.get(nodeDTO.getKey());
                this.deepChild(group, onlyField, defineKey, resources, links, key2NewKey, child, parentMap);
            }
        }
    }

    private DataResourceLink initLink(String group, String defineKey, Map<String, String> key2NewKey, RuntimeDataSchemeNodeDTO nodeDTO) {
        DataResourceLink init = this.linkService.init();
        init.setGroupKey(key2NewKey.getOrDefault(nodeDTO.getParentKey(), group));
        init.setOrder(OrderGenerator.newOrder());
        init.setDataFieldKey(nodeDTO.getKey());
        if (NodeType.FIELD.getValue() == nodeDTO.getType()) {
            init.setKind(DataLinkKind.FIELD_LINK);
        } else {
            init.setKind(DataLinkKind.FIELD_ZB_LINK);
        }
        init.setResourceDefineKey(defineKey);
        return init;
    }

    private DataResource initGroupNode(String group, String defineKey, RuntimeDataSchemeNodeDTO root, Map<String, String> key2NewKey) {
        String refDataEntityKey;
        boolean tableDim;
        int rootType = root.getType();
        String rootKey = root.getKey();
        DataResource init = this.resourceService.init();
        init.setResourceKind(DataResourceKind.RESOURCE_GROUP);
        init.setResourceDefineKey(defineKey);
        init.setParentKey(key2NewKey.getOrDefault(root.getParentKey(), group));
        init.setOrder(OrderGenerator.newOrder());
        init.setTitle(root.getTitle());
        init.setKey(UUIDUtils.getKey());
        if (rootType == NodeType.DIM.getValue()) {
            String schemeKey = root.getParentKey();
            List dims = this.runtimeDataSchemeService.getDataSchemeDimension(schemeKey);
            for (DataDimension dim : dims) {
                String id;
                String dimKey = dim.getDimKey();
                DimensionType dimensionType = dim.getDimensionType();
                if (dimensionType == DimensionType.PERIOD) {
                    String key;
                    try {
                        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
                        IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(dimKey);
                        key = periodEntity.getKey();
                    }
                    catch (Exception e) {
                        throw new DataResourceException(" \u5b9e\u4f53 " + dimKey + " \u672a\u627e\u5230\u6216\u5df2\u4e22\u5931", e);
                    }
                    if (!rootKey.equals(schemeKey + ":" + key)) continue;
                    init.setResourceKind(DataResourceKind.DIM_GROUP);
                    init.setDataSchemeKey(schemeKey);
                    init.setDimKey(dimKey);
                    continue;
                }
                try {
                    if (AdjustUtils.isAdjust((String)dimKey).booleanValue()) continue;
                    IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(dimKey);
                    id = iEntityDefine.getId();
                }
                catch (Exception e) {
                    throw new DataResourceException(" \u5b9e\u4f53 " + dimKey + " \u672a\u627e\u5230\u6216\u5df2\u4e22\u5931", e);
                }
                if (!rootKey.equals(schemeKey + ":" + id)) continue;
                init.setDimKey(dimKey);
                init.setDataSchemeKey(schemeKey);
                init.setResourceKind(DataResourceKind.DIM_GROUP);
            }
        } else if (rootType == NodeType.FMDM_TABLE.getValue()) {
            String fieldKey;
            DataField dataField;
            String dimKey;
            init.setResourceKind(DataResourceKind.DIM_GROUP);
            DataTable dataTable = this.runtimeDataSchemeService.getDataTable(rootKey);
            String[] bizKeys = dataTable.getBizKeys();
            if (bizKeys != null && bizKeys.length > 0 && (dimKey = (dataField = this.runtimeDataSchemeService.getDataField(fieldKey = bizKeys[0])).getRefDataEntityKey()) != null) {
                init.setDimKey(dimKey);
            }
        }
        if ((tableDim = root.isTableDim()) && (refDataEntityKey = root.getRefDataEntityKey()) != null) {
            init.setDimKey(refDataEntityKey);
            init.setResourceKind(DataResourceKind.TABLE_DIM_GROUP);
            init.setDataTableKey(root.getDataTableKey());
            init.setSource(rootKey);
        }
        if (root.getType() == NodeType.MD_INFO.getValue()) {
            init.setResourceKind(DataResourceKind.MD_INFO);
            init.setSource(rootKey);
        }
        return init;
    }

    @Override
    public Set<RuntimeDataSchemeNodeDTO> queryAllChildren(DataSchemeTreeQuery<RuntimeDataSchemeNodeDTO> param, NodeFilter filter) {
        CollectAllChildren childrenKey = new CollectAllChildren(this.entityMetaService, this.periodEngineService, this.iDataTableRelDao, this.iDataTableDao, this.iDataFieldDao);
        childrenKey.setFilter(filter);
        String dimKey = param.getDimKey();
        RuntimeDataSchemeNodeDTO parent = (RuntimeDataSchemeNodeDTO)param.getDataSchemeNode();
        if (StringUtils.hasLength(dimKey)) {
            Object schemeNodeFilter = null;
            schemeNodeFilter = StringUtils.hasText(param.getPeriod()) ? new ZbSchemeNodeFilter(this.designDataSchemeService, this.runtimeDataSchemeService, this.iEntityMetaService, param, true, this.zbSchemeService) : new SchemeNodeFilter(this.designDataSchemeService, this.runtimeDataSchemeService, this.iEntityMetaService, param);
            schemeNodeFilter.and((Predicate)filter);
            childrenKey.setFilter((NodeFilter)schemeNodeFilter);
        }
        this.runtimeLevelLoader.walkDataSchemeTree(new SchemeNode(parent.getKey(), parent.getType()), (RuntimeSchemeVisitor)childrenKey);
        return childrenKey.getValues();
    }
}

