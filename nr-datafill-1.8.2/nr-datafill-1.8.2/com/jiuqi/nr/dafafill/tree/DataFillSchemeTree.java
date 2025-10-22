/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.FieldSearchQuery
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.Grouped
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  com.jiuqi.nr.datascheme.api.type.DataSchemeType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.common.NodeIconGetter
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
 *  com.jiuqi.nr.datascheme.internal.service.DataFieldService
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.dafafill.tree;

import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.dafafill.model.FieldFormat;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.ColWidthType;
import com.jiuqi.nr.dafafill.model.enums.EditorType;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import com.jiuqi.nr.dafafill.model.enums.RatioType;
import com.jiuqi.nr.dafafill.model.enums.ShowContent;
import com.jiuqi.nr.dafafill.tree.ResourceNode;
import com.jiuqi.nr.dafafill.tree.SearchTreeNode;
import com.jiuqi.nr.dafafill.util.NrDataFillI18nUtil;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.FieldSearchQuery;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.Grouped;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.api.type.DataSchemeType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.datascheme.internal.service.DataFieldService;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class DataFillSchemeTree {
    private static Logger logger = LoggerFactory.getLogger(DataFillSchemeTree.class);
    @Autowired
    private IDesignDataSchemeService dschemeService;
    @Autowired
    private IRuntimeDataSchemeService schemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private DataFieldService fieldService;
    @Autowired
    IDataSchemeAuthService dataSchemeAuthService;

    public ITree<ResourceNode> buildRootNode() {
        ResourceNode root = new ResourceNode();
        root.setKey("00000000-0000-0000-0000-000000000000");
        root.setTitle("nr.dataFill.dataSchemeRoot");
        root.setCode("ROOT");
        root.setNodeType(NodeType.SCHEME_GROUP.getValue());
        ITree node = new ITree((INode)root);
        node.setIcons(new String[]{NodeIconGetter.getIconByType((NodeType)NodeType.SCHEME_GROUP)});
        node.setExpanded(true);
        node.setSelected(true);
        node.setLeaf(false);
        node.setDisabled(true);
        ArrayList<ITree<ResourceNode>> children = new ArrayList<ITree<ResourceNode>>();
        this.buildDataSchemeGroupChildren("00000000-0000-0000-0000-000000000000", children);
        node.setChildren(children);
        return node;
    }

    public List<ITree<ResourceNode>> buildChildrenNodes(String key, int nodeType, String entityid) {
        ArrayList<ITree<ResourceNode>> nodes = new ArrayList<ITree<ResourceNode>>();
        NodeType type = NodeType.valueOf((int)nodeType);
        switch (type) {
            case SCHEME_GROUP: {
                this.buildDataSchemeGroupChildren(key, nodes);
                break;
            }
            case SCHEME: {
                this.buildDataSchemeChildren(key, nodes, entityid);
                break;
            }
            case GROUP: {
                this.buildTableGroupChildren(key, nodes);
                break;
            }
            case ACCOUNT_TABLE: 
            case TABLE: 
            case DETAIL_TABLE: 
            case MUL_DIM_TABLE: {
                this.buildTableChildren(key, nodes);
                break;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + nodeType);
            }
        }
        return nodes;
    }

    private void buildTableChildren(String tableId, List<ITree<ResourceNode>> nodes) {
        List zbs = this.schemeService.getDataFieldByTableKeyAndKind(tableId, new DataFieldKind[]{DataFieldKind.FIELD_ZB, DataFieldKind.FIELD, DataFieldKind.TABLE_FIELD_DIM});
        if (!CollectionUtils.isEmpty(zbs)) {
            zbs = zbs.stream().filter(zb -> zb.getDataFieldType() != DataFieldType.PICTURE && zb.getDataFieldType() != DataFieldType.FILE).sorted(Comparator.comparing(DataField::getDataFieldKind).reversed()).collect(Collectors.toList());
            DataScheme scheme = this.schemeService.getDataScheme(((DataField)zbs.get(0)).getDataSchemeKey());
            DataTable table = this.schemeService.getDataTable(((DataField)zbs.get(0)).getDataTableKey());
            nodes.addAll(zbs.stream().map(zb -> this.convertFiledToResourceNode((DataField)zb, scheme, table)).collect(Collectors.toList()));
        }
    }

    private void buildTableGroupChildren(String tableGroupId, List<ITree<ResourceNode>> nodes) {
        List dataGroups = this.schemeService.getDataGroupByParent(tableGroupId);
        if (!CollectionUtils.isEmpty(dataGroups)) {
            nodes.addAll(dataGroups.stream().map(b -> this.convertBasicToResourceNode((Basic)b, NodeType.GROUP)).collect(Collectors.toList()));
        }
        List tables = this.schemeService.getDataTableByGroup(tableGroupId);
        DataGroup tableGroup = this.schemeService.getDataGroup(tableGroupId);
        DataScheme scheme = this.schemeService.getDataScheme(tableGroup.getDataSchemeKey());
        this.convertTbaleToResourceNode(scheme, tables, nodes);
    }

    private void buildDataSchemeGroupChildren(String schemeGroupId, List<ITree<ResourceNode>> nodes) {
        List dataGroups = this.dschemeService.getDataGroupByParent(schemeGroupId);
        List dataScheme = this.schemeService.getDataSchemeByParent(schemeGroupId);
        if (!CollectionUtils.isEmpty(dataGroups)) {
            nodes.addAll(dataGroups.stream().filter(group -> this.dataSchemeAuthService.canReadGroup(group.getKey())).map(b -> this.convertBasicToResourceNode((Basic)b, NodeType.SCHEME_GROUP)).collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty(dataScheme)) {
            nodes.addAll(dataScheme.stream().filter(scheme -> this.dataSchemeAuthService.canReadScheme(scheme.getKey())).filter(scheme -> scheme.getType() != DataSchemeType.QUERY).map(b -> this.convertBasicToResourceNode((Basic)b, NodeType.SCHEME)).collect(Collectors.toList()));
        }
    }

    private void buildDataSchemeChildren(String schemeId, List<ITree<ResourceNode>> nodes, String entityid) {
        DataScheme scheme = this.schemeService.getDataScheme(schemeId);
        if (this.checkDataScheme(scheme)) {
            ResourceNode r = new ResourceNode();
            r.setKey("ERROR_DATASCHEME");
            r.setCode("ERROR_DATASCHEME_NO_UNIT_DIM");
            r.setTitle("\u7f3a\u5931\u7ef4\u5ea6");
            r.setNodeType(NodeType.DIM.getValue());
            ITree node = new ITree((INode)r);
            node.setIcons(new String[]{NodeIconGetter.getIconByType((NodeType)NodeType.SCHEME), "#icon-16_SHU_E_NR_cuowu"});
            node.setLeaf(true);
            node.setChecked(false);
            node.setDisabled(true);
            nodes.add((ITree<ResourceNode>)node);
            return;
        }
        nodes.add(this.buildPubDimResourceNode(scheme, entityid));
        List tableGroups = this.schemeService.getDataGroupByScheme(schemeId);
        if (!CollectionUtils.isEmpty(tableGroups)) {
            nodes.addAll(tableGroups.stream().map(b -> this.convertBasicToResourceNode((Basic)b, NodeType.GROUP)).collect(Collectors.toList()));
        }
        List<DataTable> tables = this.schemeService.getDataTableByScheme(schemeId).stream().filter(e -> e.getDataTableType() != DataTableType.MD_INFO).collect(Collectors.toList());
        this.convertTbaleToResourceNode(scheme, tables, nodes);
    }

    private boolean checkDataScheme(DataScheme scheme) {
        List dataDimensionList = this.schemeService.getDataSchemeDimension(scheme.getKey(), DimensionType.UNIT);
        List scopes = this.schemeService.getDataSchemeDimension(scheme.getKey(), DimensionType.UNIT_SCOPE);
        if (!CollectionUtils.isEmpty(scopes)) {
            dataDimensionList.addAll(scopes);
        }
        for (DataDimension dim : dataDimensionList) {
            IEntityDefine define = this.entityMetaService.queryEntity(dim.getDimKey());
            if (define != null) continue;
            return true;
        }
        return false;
    }

    private ITree<ResourceNode> buildPubDimResourceNode(DataScheme scheme, String entityid) {
        ResourceNode r = new ResourceNode();
        r.setKey("DIM_PUB." + scheme.getCode());
        r.setTitle("\u516c\u5171\u7ef4\u5ea6\u5206\u7ec4");
        r.setCode("DIM_PUB." + scheme.getCode());
        r.setNodeType(NodeType.DIM.getValue());
        ITree node = new ITree((INode)r);
        node.setIcons(new String[]{NodeIconGetter.getIconByType((NodeType)NodeType.DIM)});
        node.setLeaf(false);
        node.setDisabled(true);
        if (scheme != null) {
            List dims = this.schemeService.getDataSchemeDimension(scheme.getKey());
            DataDimension unit = null;
            ArrayList<DataDimension> scopes = new ArrayList<DataDimension>();
            for (DataDimension dim : dims) {
                DimensionType dimensionType = dim.getDimensionType();
                if (DimensionType.UNIT == dimensionType) {
                    unit = dim;
                    continue;
                }
                if (DimensionType.UNIT_SCOPE != dimensionType) continue;
                scopes.add(dim);
            }
            if (unit == null) {
                throw new SchemeDataException("\u672a\u627e\u5230\u4e3b\u7ef4\u5ea6");
            }
            ArrayList<ITree<ResourceNode>> children = new ArrayList<ITree<ResourceNode>>();
            node.setChildren(children);
            boolean dim_public_check = false;
            if (scopes.isEmpty()) {
                ITree<ResourceNode> tempNode = this.convertDataDimToResourceNode(unit, scheme, entityid);
                if (tempNode.isChecked()) {
                    dim_public_check = true;
                }
                children.add(tempNode);
            } else {
                for (DataDimension scope : scopes) {
                    ITree<ResourceNode> tempNode = this.convertDataDimToResourceNode(scope, scheme, entityid);
                    if (tempNode.isChecked()) {
                        dim_public_check = true;
                    }
                    children.add(tempNode);
                }
            }
            node.setChecked(dim_public_check);
        }
        return node;
    }

    private ITree<ResourceNode> convertFiledToResourceNode(DataField zb, DataScheme scheme, DataTable table) {
        ResourceNode r = new ResourceNode();
        StringBuffer fullCode = new StringBuffer();
        fullCode.append(table.getCode()).append(".").append(zb.getCode());
        r.setKey(fullCode.toString());
        r.setTitle(zb.getTitle());
        r.setNodeType(this.convertDataFieldKindToNodeType(zb.getDataFieldKind()).getValue());
        r.setCode(zb.getCode());
        r.setField(this.convertZbToQueryField(zb, scheme, table));
        r.setDataSchemeCode(scheme.getCode());
        ITree node = new ITree((INode)r);
        node.setIcons(new String[]{NodeIconGetter.getIconByType((NodeType)NodeType.FIELD)});
        node.setLeaf(true);
        return node;
    }

    private NodeType convertDataFieldKindToNodeType(DataFieldKind kind) {
        switch (kind) {
            case FIELD_ZB: {
                return NodeType.FIELD_ZB;
            }
            case FIELD: {
                return NodeType.FIELD;
            }
            case TABLE_FIELD_DIM: {
                return NodeType.TABLE_DIM;
            }
        }
        return NodeType.FIELD_ZB;
    }

    private QueryField convertZbToQueryField(DataField zb, DataScheme scheme, DataTable table) {
        return this.convertZbToQueryField(zb, scheme, table, null);
    }

    public QueryField convertZbToQueryField(DataField zb, DataScheme scheme, DataTable table, DataLinkDefine datalink) {
        FormatProperties formatProperties;
        QueryField field = new QueryField();
        field.setId(zb.getKey());
        field.setCode(zb.getCode());
        StringBuffer fullCode = new StringBuffer();
        fullCode.append(table.getCode()).append(".").append(zb.getCode());
        field.setFullCode(fullCode.toString());
        field.setTitle(zb.getTitle());
        field.setDataSchemeCode(scheme.getCode());
        field.setDataType(zb.getDataFieldType());
        field.setFieldType(this.convertDataFieldKind(zb.getDataFieldKind()));
        if (datalink != null) {
            formatProperties = datalink.getFormatProperties();
            field.setNotEmpty(datalink.getAllowNullAble() == false);
            field.setFilterExpression(datalink.getKey());
        } else {
            formatProperties = zb.getFormatProperties();
        }
        FieldFormat fieldFormat = new FieldFormat();
        fieldFormat.setRatioType(RatioType.NONE);
        if (formatProperties != null) {
            String pattern = formatProperties.getPattern();
            if (pattern.indexOf("%") > -1) {
                fieldFormat.setDecimal(pattern.substring(pattern.indexOf(".") + 1, pattern.indexOf("%")).length());
                fieldFormat.setRatioType(RatioType.PERCENT);
            } else if (pattern.indexOf("\u2030") > -1) {
                fieldFormat.setDecimal(pattern.substring(pattern.indexOf(".") + 1, pattern.indexOf("\u2030")).length());
                fieldFormat.setRatioType(RatioType.PERMIL);
            } else if (pattern.indexOf("#,##0") > -1) {
                fieldFormat.setDecimal(pattern.substring(pattern.indexOf(".") + 1).length());
                fieldFormat.setPermil(true);
            } else {
                fieldFormat.setFormat(pattern);
            }
        } else {
            if (zb.getDecimal() != null) {
                fieldFormat.setDecimal(zb.getDecimal());
            }
            if (zb.getDataFieldType() == DataFieldType.BIGDECIMAL) {
                fieldFormat.setPermil(true);
            }
        }
        field.setShowFormat(fieldFormat);
        if (zb.getDataFieldType() == DataFieldType.STRING && StringUtils.hasText(zb.getRefDataFieldKey()) && StringUtils.hasText(zb.getRefDataEntityKey())) {
            String referTableID = zb.getRefDataEntityKey();
            field.setExpression(referTableID);
            field.setEditorType(EditorType.DROPDOWN);
        } else if (zb.getDataFieldType() == DataFieldType.DATE) {
            field.setEditorType(EditorType.DATE);
        }
        return field;
    }

    public FieldType convertDataFieldKind(DataFieldKind dataFieldKind) {
        switch (dataFieldKind) {
            case FIELD_ZB: {
                return FieldType.ZB;
            }
            case FIELD: {
                return FieldType.FIELD;
            }
            case TABLE_FIELD_DIM: {
                return FieldType.TABLEDIMENSION;
            }
        }
        return FieldType.ZB;
    }

    private void convertTbaleToResourceNode(DataScheme scheme, List<DataTable> tables, List<ITree<ResourceNode>> nodes) {
        if (!CollectionUtils.isEmpty(tables)) {
            for (DataTable t : tables) {
                NodeType tType = NodeType.TABLE;
                switch (t.getDataTableType()) {
                    case TABLE: {
                        tType = NodeType.TABLE;
                        break;
                    }
                    case DETAIL: {
                        tType = NodeType.DETAIL_TABLE;
                        break;
                    }
                    case MULTI_DIM: {
                        tType = NodeType.MUL_DIM_TABLE;
                        break;
                    }
                    case ACCOUNT: {
                        tType = NodeType.ACCOUNT_TABLE;
                        break;
                    }
                    default: {
                        tType = NodeType.TABLE;
                    }
                }
                ResourceNode r = new ResourceNode();
                r.setKey(scheme.getCode() + "." + t.getCode());
                r.setTitle(t.getTitle());
                r.setNodeType(tType.getValue());
                r.setCode(t.getKey());
                r.setDataSchemeCode(scheme.getCode());
                ITree tableNode = new ITree((INode)r);
                tableNode.setIcons(new String[]{NodeIconGetter.getIconByType((NodeType)tType)});
                tableNode.setLeaf(false);
                tableNode.setDisabled(false);
                nodes.add((ITree<ResourceNode>)tableNode);
            }
        }
    }

    private ITree<ResourceNode> convertBasicToResourceNode(Basic b, NodeType type) {
        ResourceNode r = new ResourceNode();
        r.setKey(b.getKey());
        r.setTitle(b.getTitle());
        r.setNodeType(type.getValue());
        r.setCode(b.getCode());
        ITree node = new ITree((INode)r);
        node.setIcons(new String[]{NodeIconGetter.getIconByType((NodeType)type)});
        node.setLeaf(false);
        node.setDisabled(true);
        return node;
    }

    private ITree<ResourceNode> convertDataDimToResourceNode(DataDimension dim, DataScheme scheme, String entityid) {
        String dimKey = dim.getDimKey();
        IEntityDefine define = this.entityMetaService.queryEntity(dimKey);
        if (define == null) {
            return null;
        }
        ResourceNode r = new ResourceNode();
        r.setKey(scheme.getCode() + "." + define.getCode());
        r.setTitle(define.getTitle());
        r.setCode(scheme.getCode() + "." + define.getCode());
        r.setNodeType(NodeType.FMDM_TABLE.getValue());
        r.setDataSchemeCode(scheme.getCode());
        ITree node = new ITree((INode)r);
        node.setIcons(new String[]{NodeIconGetter.getIconByType((NodeType)NodeType.FMDM_TABLE)});
        node.setLeaf(true);
        if (define.getId().equals(entityid)) {
            node.setChecked(true);
        }
        node.setDisabled(true);
        return node;
    }

    public List<SearchTreeNode> search(String fuzzyKey) {
        ArrayList<SearchTreeNode> res = new ArrayList<SearchTreeNode>();
        FieldSearchQuery fieldSearchQuery = new FieldSearchQuery();
        fieldSearchQuery.setKeyword(fuzzyKey);
        fieldSearchQuery.setKind(Integer.valueOf(DataFieldKind.FIELD_ZB.getValue() | DataFieldKind.FIELD.getValue() | DataFieldKind.TABLE_FIELD_DIM.getValue()));
        List dataFieldDTOS = this.fieldService.searchField(fieldSearchQuery);
        HashMap<String, Set<String>> effectiveMap = new HashMap<String, Set<String>>();
        for (DataFieldDTO f : dataFieldDTOS) {
            HashSet<String> tables = (HashSet<String>)effectiveMap.get(f.getDataSchemeKey());
            if (tables == null) {
                tables = new HashSet<String>();
                effectiveMap.put(f.getDataSchemeKey(), tables);
            }
            tables.add(f.getDataTableKey());
        }
        HashMap<String, List<String>> schemePathMap = new HashMap<String, List<String>>();
        HashMap<String, List<String>> schemeTitleMap = new HashMap<String, List<String>>();
        HashMap<String, List<String>> tablePathMap = new HashMap<String, List<String>>();
        HashMap<String, List<String>> tableTitleMap = new HashMap<String, List<String>>();
        HashMap<String, String> tableMap = new HashMap<String, String>();
        this.searchSchemePath(effectiveMap, schemePathMap, schemeTitleMap, tablePathMap, tableTitleMap, tableMap);
        for (DataFieldDTO f : dataFieldDTOS) {
            SearchTreeNode n = new SearchTreeNode();
            n.setKey(f.getKey());
            n.setTitle(f.getTitle());
            n.setCode(f.getCode());
            ArrayList<String> paths = new ArrayList<String>();
            paths.add("00000000-0000-0000-0000-000000000000");
            paths.addAll((Collection)schemePathMap.get(f.getDataSchemeKey()));
            paths.addAll((Collection)tablePathMap.get(f.getDataTableKey()));
            paths.add((String)tableMap.get(f.getDataTableKey()) + "." + f.getCode());
            n.setKeyPath(paths);
            ArrayList<String> titlePaths = new ArrayList<String>();
            titlePaths.add(NrDataFillI18nUtil.parseMsg("{{nr.dataFill.dataSchemeRoot}}"));
            titlePaths.addAll((Collection)schemeTitleMap.get(f.getDataSchemeKey()));
            titlePaths.addAll((Collection)tableTitleMap.get(f.getDataTableKey()));
            titlePaths.add(f.getTitle());
            Collections.reverse(titlePaths);
            n.setTitlePath(titlePaths.stream().collect(Collectors.joining("/")));
            res.add(n);
        }
        return res;
    }

    private void searchSchemePath(Map<String, Set<String>> effectiveMap, Map<String, List<String>> schemePathMap, Map<String, List<String>> schemeTitleMap, Map<String, List<String>> tablePathMap, Map<String, List<String>> tableTitleMap, Map<String, String> tableMap) {
        List schemeGroupList = this.dschemeService.getDataGroupByKind(DataGroupKind.SCHEME_GROUP.getValue());
        Map<String, DesignDataGroup> schemeGroupMap = schemeGroupList.stream().collect(Collectors.toMap(Grouped::getKey, g -> g));
        ArrayList<String> schemeKeys = new ArrayList<String>();
        schemeKeys.addAll(effectiveMap.keySet());
        List schemes = this.schemeService.getDataSchemes(schemeKeys);
        HashMap<String, ArrayList<DesignDataGroup>> schemeGroupParentMap = new HashMap<String, ArrayList<DesignDataGroup>>();
        for (DataScheme s : schemes) {
            ArrayList<DesignDataGroup> groupArr = (ArrayList<DesignDataGroup>)schemeGroupParentMap.get(s.getDataGroupKey());
            if (groupArr == null) {
                groupArr = new ArrayList<DesignDataGroup>();
                this.searchSchemeGroup(s.getDataGroupKey(), schemeGroupMap, groupArr);
                schemeGroupParentMap.put(s.getDataGroupKey(), groupArr);
            }
            List paths = groupArr.stream().map(g -> g.getKey()).collect(Collectors.toList());
            List titles = groupArr.stream().map(g -> g.getTitle()).collect(Collectors.toList());
            paths.add(s.getKey());
            titles.add(s.getTitle());
            schemePathMap.put(s.getKey(), paths);
            schemeTitleMap.put(s.getKey(), titles);
            ArrayList tableKeys = new ArrayList();
            tableKeys.addAll(effectiveMap.get(s.getKey()));
            List tables = this.schemeService.getDataTables(tableKeys);
            List tableGroups = this.schemeService.getAllDataGroup(s.getKey());
            Map<String, DataGroup> tableGroupMap = tableGroups.stream().collect(Collectors.toMap(Grouped::getKey, g -> g));
            HashMap<String, ArrayList<DataGroup>> tableGroupParentMap = new HashMap<String, ArrayList<DataGroup>>();
            for (DataTable t : tables) {
                tableMap.put(t.getKey(), t.getCode());
                ArrayList<DataGroup> tableGroupArr = (ArrayList<DataGroup>)tableGroupParentMap.get(t.getDataGroupKey());
                if (tableGroupArr == null) {
                    tableGroupArr = new ArrayList<DataGroup>();
                    this.searchTableGroup(t.getDataGroupKey(), tableGroupMap, tableGroupArr);
                    tableGroupParentMap.put(t.getDataGroupKey(), tableGroupArr);
                }
                List tablePaths = tableGroupArr.stream().map(g -> g.getKey()).collect(Collectors.toList());
                List tableTitles = tableGroupArr.stream().map(g -> g.getTitle()).collect(Collectors.toList());
                tablePaths.add(s.getCode() + "." + t.getCode());
                tableTitles.add(t.getTitle());
                tablePathMap.put(t.getKey(), tablePaths);
                tableTitleMap.put(t.getKey(), tableTitles);
            }
        }
    }

    private void searchTableGroup(String groupId, Map<String, DataGroup> schemeGroupMap, List<DataGroup> groups) {
        DataGroup schemeGroup = schemeGroupMap.get(groupId);
        if (schemeGroup != null) {
            if (schemeGroupMap.containsKey(schemeGroup.getParentKey())) {
                this.searchTableGroup(schemeGroup.getParentKey(), schemeGroupMap, groups);
            }
            groups.add(schemeGroup);
        }
    }

    private void searchSchemeGroup(String groupId, Map<String, DesignDataGroup> schemeGroupMap, List<DesignDataGroup> groups) {
        DesignDataGroup schemeGroup = schemeGroupMap.get(groupId);
        if (schemeGroup != null) {
            if (schemeGroupMap.containsKey(schemeGroup.getParentKey())) {
                this.searchSchemeGroup(schemeGroup.getParentKey(), schemeGroupMap, groups);
            }
            groups.add(schemeGroup);
        }
    }

    public List<String> locate(String id) {
        ArrayList<String> paths = new ArrayList<String>();
        String zbPath = null;
        String tablePath = null;
        ArrayList<String> tableGroupPaths = new ArrayList<String>();
        String schemePath = null;
        ArrayList<String> dataSchemeGroupPaths = new ArrayList<String>();
        String rootPath = "00000000-0000-0000-0000-000000000000";
        DataField zb = this.schemeService.getDataField(id);
        if (zb != null) {
            DataTable table = this.schemeService.getDataTable(zb.getDataTableKey());
            zbPath = table.getCode() + "." + zb.getCode();
            DataScheme dataScheme = this.schemeService.getDataScheme(zb.getDataSchemeKey());
            tablePath = dataScheme.getCode() + "." + table.getCode();
            this.buildGroupPath(true, table.getDataGroupKey(), tableGroupPaths);
            schemePath = dataScheme.getKey();
            this.buildGroupPath(false, dataScheme.getDataGroupKey(), dataSchemeGroupPaths);
        }
        paths.add(rootPath);
        paths.addAll(dataSchemeGroupPaths);
        paths.add(schemePath);
        paths.addAll(tableGroupPaths);
        paths.add(tablePath);
        paths.add(zbPath);
        return paths;
    }

    private void buildGroupPath(boolean istable, String childGuid, List<String> groupPaths) {
        if (!StringUtils.hasText(childGuid)) {
            return;
        }
        Object dataGroup = null;
        dataGroup = istable ? this.schemeService.getDataGroup(childGuid) : this.dschemeService.getDataGroup(childGuid);
        if (dataGroup != null) {
            if (StringUtils.hasText(dataGroup.getParentKey())) {
                this.buildGroupPath(istable, dataGroup.getParentKey(), groupPaths);
            }
            groupPaths.add(dataGroup.getKey());
        }
    }

    public QueryField convertSchemeDimToQueryField(DataDimension dim, DataScheme scheme) {
        if (DimensionType.PERIOD == dim.getDimensionType()) {
            IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(dim.getDimKey());
            if (periodEntity == null) {
                logger.error("\u6570\u636e\u65b9\u6848\uff1a" + scheme.getTitle() + "[" + scheme.getCode() + "]\u5173\u8054\u7684\u65f6\u671f\u7ef4\u5ea6\u4e0d\u5b58\u5728\uff01 dimKey:" + dim.getDimKey());
                return null;
            }
            QueryField field = new QueryField();
            field.setId(periodEntity.getKey());
            field.setCode(periodEntity.getCode());
            field.setTitle(periodEntity.getTitle());
            field.setFullCode(scheme.getCode() + "." + periodEntity.getCode());
            field.setDataSchemeCode(scheme.getCode());
            field.setFieldType(FieldType.PERIOD);
            field.setColWidthType(ColWidthType.DEFAULT);
            field.setEditorType(EditorType.CALENDAR);
            if (PeriodType.CUSTOM == periodEntity.getPeriodType()) {
                field.setDataType(DataFieldType.UUID);
            }
            return field;
        }
        QueryField field = new QueryField();
        if ("ADJUST".equals(dim.getDimKey())) {
            field.setId("ADJUST");
            field.setCode("ADJUST");
            field.setTitle("\u8c03\u6574\u671f");
            field.setFullCode(scheme.getCode() + "." + "ADJUST");
        } else {
            IEntityDefine define = this.entityMetaService.queryEntity(dim.getDimKey());
            if (define == null) {
                logger.error("\u6570\u636e\u65b9\u6848\uff1a" + scheme.getTitle() + "[" + scheme.getCode() + "]\u5173\u8054\u7ef4\u5ea6\u4e0d\u5b58\u5728\uff01 dimKey:" + dim.getDimKey());
                return null;
            }
            field.setId(define.getId());
            field.setCode(define.getCode());
            field.setTitle(define.getTitle());
            field.setFullCode(scheme.getCode() + "." + define.getCode());
        }
        field.setDataSchemeCode(scheme.getCode());
        field.setColWidthType(ColWidthType.DEFAULT);
        switch (dim.getDimensionType()) {
            case UNIT: 
            case UNIT_SCOPE: {
                field.setFieldType(FieldType.MASTER);
                FieldFormat fieldFormat = new FieldFormat();
                fieldFormat.setShowContent(ShowContent.TITLE);
                field.setShowFormat(fieldFormat);
                field.setEditorType(EditorType.UNITSELECTOR);
                break;
            }
            case DIMENSION: {
                field.setFieldType(FieldType.SCENE);
                field.setEditorType(EditorType.DROPDOWN);
            }
        }
        return field;
    }
}

