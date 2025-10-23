/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.FieldSearchQuery
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.RuntimeDataSchemeNode
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.exception.NotFindSchemeDataException
 *  com.jiuqi.nr.datascheme.api.loader.run.RuntimeLevelLoader
 *  com.jiuqi.nr.datascheme.api.loader.run.RuntimeReverseSchemeVisitor
 *  com.jiuqi.nr.datascheme.api.loader.run.RuntimeSchemeVisitor
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeTreeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 */
package com.jiuqi.nr.datascheme.internal.tree;

import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.FieldSearchQuery;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.RuntimeDataSchemeNode;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.exception.NotFindSchemeDataException;
import com.jiuqi.nr.datascheme.api.loader.run.RuntimeLevelLoader;
import com.jiuqi.nr.datascheme.api.loader.run.RuntimeReverseSchemeVisitor;
import com.jiuqi.nr.datascheme.api.loader.run.RuntimeSchemeVisitor;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeTreeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableRelDao;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataGroupDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataTableDTO;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DataTableDO;
import com.jiuqi.nr.datascheme.internal.entity.DataTableRelDO;
import com.jiuqi.nr.datascheme.internal.service.DataFieldService;
import com.jiuqi.nr.datascheme.internal.service.DataGroupService;
import com.jiuqi.nr.datascheme.internal.service.DataSchemeService;
import com.jiuqi.nr.datascheme.internal.service.DataTableService;
import com.jiuqi.nr.datascheme.internal.tree.BaseTreeNodeSearch;
import com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.internal.tree.run.DataResourceTree;
import com.jiuqi.nr.datascheme.internal.tree.run.RunInterestReverseTreeVisitor;
import com.jiuqi.nr.datascheme.internal.tree.run.RunInterestTreeVisitor;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class RuntimeDataSchemeTreeServiceImpl
extends BaseTreeNodeSearch<RuntimeDataSchemeNode>
implements IDataSchemeTreeService<RuntimeDataSchemeNode> {
    @Autowired
    private RuntimeLevelLoader runtimeLevelLoader;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private DataFieldService fieldService;
    @Autowired
    private DataTableService tableService;
    @Autowired
    private DataSchemeService schemeService;
    @Autowired
    private DataGroupService groupService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IDataTableRelDao<DataTableRelDO> iDataTableRelDao;
    @Autowired
    private IDataTableDao<DataTableDO> iDataTableDao;
    @Autowired
    private IDataFieldDao<DataFieldDO> dataFieldDao;

    public List<ITree<RuntimeDataSchemeNode>> getRootTree(NodeType leaf, String scheme) {
        throw new UnsupportedOperationException();
    }

    public List<ITree<RuntimeDataSchemeNode>> getChildTree(NodeType leaf, RuntimeDataSchemeNode parent) {
        throw new UnsupportedOperationException();
    }

    public List<ITree<RuntimeDataSchemeNode>> getSpecifiedTree(NodeType leaf, RuntimeDataSchemeNode node, String schemeKey) {
        throw new UnsupportedOperationException();
    }

    public List<ITree<RuntimeDataSchemeNode>> getSchemeGroupRootTree(NodeType leafType) {
        throw new UnsupportedOperationException();
    }

    public List<ITree<RuntimeDataSchemeNode>> getQuerySchemeGroupRootTree(NodeType leafType) {
        throw new UnsupportedOperationException();
    }

    public List<ITree<RuntimeDataSchemeNode>> getSchemeGroupChildTree(NodeType leafType, RuntimeDataSchemeNode parent) {
        throw new UnsupportedOperationException();
    }

    public List<ITree<RuntimeDataSchemeNode>> getSchemeGroupSpecifiedTree(NodeType leaf, RuntimeDataSchemeNode node) {
        throw new UnsupportedOperationException();
    }

    public List<ITree<RuntimeDataSchemeNode>> getRootTree(String scheme, int interestType, NodeFilter filter) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(scheme);
        if (dataScheme == null) {
            return Collections.emptyList();
        }
        SchemeNode root = new SchemeNode(dataScheme.getKey(), NodeType.SCHEME.getValue());
        RunInterestTreeVisitor visitor = new RunInterestTreeVisitor(filter, interestType, this.entityMetaService, this.periodEngineService);
        visitor.setDataTableDao(this.iDataTableDao);
        visitor.setDataTableRelDao(this.iDataTableRelDao);
        this.runtimeLevelLoader.walkDataSchemeTree(root, (RuntimeSchemeVisitor)visitor, Integer.valueOf(interestType));
        return visitor.getValue();
    }

    public List<ITree<RuntimeDataSchemeNode>> getChildTree(RuntimeDataSchemeNode parent, int interestType, NodeFilter filter) {
        Assert.notNull((Object)parent, "parent must not be null.");
        String key = parent.getKey();
        int type = parent.getType();
        if (NodeType.DIM.getValue() == type) {
            key = key.substring(key.indexOf(":") + 1);
        }
        SchemeNode root = new SchemeNode(key, type);
        RunInterestTreeVisitor visitor = new RunInterestTreeVisitor(filter, interestType, this.entityMetaService, this.periodEngineService);
        visitor.setDataTableDao(this.iDataTableDao);
        visitor.setDataTableRelDao(this.iDataTableRelDao);
        visitor.setChildren(true);
        this.runtimeLevelLoader.walkDataSchemeTree(root, (RuntimeSchemeVisitor)visitor, Integer.valueOf(interestType));
        return visitor.getValue();
    }

    public List<ITree<RuntimeDataSchemeNode>> getSpecifiedTree(RuntimeDataSchemeNode node, String schemeKey, int interestType, NodeFilter filter) {
        boolean isScheme;
        Assert.notNull((Object)node, "node must not be null.");
        String key = node.getKey();
        Assert.notNull((Object)key, "key must not be null.");
        int type = node.getType();
        boolean bl = isScheme = (NodeType.SCHEME.getValue() & type) != 0;
        if (isScheme) {
            return this.getRootTree(key, interestType, filter);
        }
        if ((NodeType.SCHEME_GROUP.getValue() & type) != 0) {
            throw new IllegalArgumentException("\u53c2\u6570\u9519\u8bef");
        }
        SchemeNode leaf = new SchemeNode(key, node.getType());
        RunInterestReverseTreeVisitor specifiedVisitor = new RunInterestReverseTreeVisitor(this.entityMetaService, this.periodEngineService, interestType ^= NodeType.SCHEME.getValue(), key);
        specifiedVisitor.setDataTableDao(this.iDataTableDao);
        specifiedVisitor.setDataFieldDao(this.dataFieldDao);
        specifiedVisitor.setDataTableRelDao(this.iDataTableRelDao);
        try {
            specifiedVisitor.setNodeFilter(filter);
            this.runtimeLevelLoader.reverseDataSchemeTree(leaf, (RuntimeReverseSchemeVisitor)specifiedVisitor, Integer.valueOf(interestType));
            List<ITree<RuntimeDataSchemeNode>> children = specifiedVisitor.getValues();
            DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(schemeKey);
            ArrayList<ITree<RuntimeDataSchemeNode>> value = new ArrayList<ITree<RuntimeDataSchemeNode>>();
            ITree root = new ITree((INode)new RuntimeDataSchemeNodeDTO(dataScheme));
            root.setIcons(NodeIconGetter.getIconByType(NodeType.SCHEME));
            value.add(root);
            if (children != null && !children.isEmpty()) {
                root.setChildren(children);
            } else {
                root.setLeaf(true);
            }
            root.setExpanded(true);
            return value;
        }
        catch (Exception e) {
            throw new NotFindSchemeDataException("\u5b9a\u4f4d\u6811\u5f62\u8282\u70b9\u5931\u8d25", (Throwable)e);
        }
    }

    public List<ITree<RuntimeDataSchemeNode>> getSchemeGroupRootTree(int interestType, NodeFilter filter) {
        SchemeNode root = new SchemeNode("00000000-0000-0000-0000-000000000000", NodeType.SCHEME_GROUP.getValue());
        RunInterestTreeVisitor visitor = new RunInterestTreeVisitor(filter, interestType, this.entityMetaService, this.periodEngineService);
        visitor.setDataTableDao(this.iDataTableDao);
        visitor.setDataTableRelDao(this.iDataTableRelDao);
        this.runtimeLevelLoader.walkDataSchemeTree(root, (RuntimeSchemeVisitor)visitor, Integer.valueOf(interestType));
        SchemeNode queryRoot = new SchemeNode("00000000-0000-0000-0000-111111111111", NodeType.SCHEME_GROUP.getValue());
        RunInterestTreeVisitor queryVisitor = new RunInterestTreeVisitor(filter, interestType, this.entityMetaService, this.periodEngineService);
        queryVisitor.setDataTableDao(this.iDataTableDao);
        queryVisitor.setDataTableRelDao(this.iDataTableRelDao);
        this.runtimeLevelLoader.walkDataSchemeTree(queryRoot, (RuntimeSchemeVisitor)queryVisitor, Integer.valueOf(interestType));
        List<ITree<RuntimeDataSchemeNode>> value = visitor.getValue();
        if (!queryVisitor.getValue().isEmpty() && queryVisitor.getValue().get(0).hasChildren()) {
            value.addAll(queryVisitor.getValue());
        }
        return value;
    }

    public List<ITree<RuntimeDataSchemeNode>> getSchemeGroupChildTree(RuntimeDataSchemeNode parent, int interestType, NodeFilter filter) {
        Assert.notNull((Object)parent, "parent must not be null.");
        return this.getChildTree(parent, interestType, filter);
    }

    public List<ITree<RuntimeDataSchemeNode>> getSchemeGroupSpecifiedTree(RuntimeDataSchemeNode node, int interestType, NodeFilter filter) {
        Assert.notNull((Object)node, "node must not be null.");
        String key = node.getKey();
        Assert.notNull((Object)key, "key must not be null.");
        SchemeNode leaf = new SchemeNode(key, node.getType());
        RunInterestReverseTreeVisitor specifiedVisitor = new RunInterestReverseTreeVisitor(this.entityMetaService, this.periodEngineService, interestType, key);
        specifiedVisitor.setDataTableDao(this.iDataTableDao);
        specifiedVisitor.setDataTableRelDao(this.iDataTableRelDao);
        specifiedVisitor.setDataFieldDao(this.dataFieldDao);
        try {
            specifiedVisitor.setNodeFilter(filter);
            this.runtimeLevelLoader.reverseDataSchemeTree(leaf, (RuntimeReverseSchemeVisitor)specifiedVisitor, Integer.valueOf(interestType));
            return specifiedVisitor.getValues();
        }
        catch (Exception e) {
            throw new NotFindSchemeDataException("\u5b9a\u4f4d\u6811\u5f62\u8282\u70b9\u5931\u8d25", (Throwable)e);
        }
    }

    public List<ITree<RuntimeDataSchemeNode>> getCheckBoxSchemeGroupRootTree(NodeType leafType, int checkboxOptional, NodeFilter filter) {
        return this.getCheckBoxSchemeGroupRootTree(NodeType.getInterestTypeByLeft((NodeType)leafType), checkboxOptional, filter);
    }

    public List<ITree<RuntimeDataSchemeNode>> getCheckBoxSchemeGroupChildTree(NodeType leafType, RuntimeDataSchemeNode parent, int checkboxOptional, NodeFilter filter) {
        return this.getCheckBoxSchemeGroupChildTree(NodeType.getInterestTypeByLeft((NodeType)leafType), parent, checkboxOptional, filter);
    }

    public List<ITree<RuntimeDataSchemeNode>> getCheckBoxSchemeSpecifiedTree(NodeType leafType, RuntimeDataSchemeNode node, int checkboxOptional, NodeFilter filter) {
        return this.getCheckBoxSchemeSpecifiedTree(NodeType.getInterestTypeByLeft((NodeType)leafType), node, checkboxOptional, filter);
    }

    @Override
    protected List<RuntimeDataSchemeNode> searchDataField(List<String> schemeKey, String keyword, int kind) {
        FieldSearchQuery fieldSearchQuery = new FieldSearchQuery();
        fieldSearchQuery.setSchemes(schemeKey);
        fieldSearchQuery.setKeyword(keyword);
        fieldSearchQuery.setKind(Integer.valueOf(kind));
        List<DataFieldDTO> fields = this.fieldService.searchField(fieldSearchQuery);
        Set<String> dataSchemeKeys = fields.stream().map(DataFieldDTO::getDataSchemeKey).collect(Collectors.toSet());
        Map<String, Boolean> canReadSchemes = this.canReadSchemes(dataSchemeKeys);
        ArrayList<RuntimeDataSchemeNode> list = new ArrayList<RuntimeDataSchemeNode>(fields.size());
        for (DataFieldDTO field : fields) {
            if (Boolean.FALSE.equals(canReadSchemes.get(field.getDataSchemeKey()))) continue;
            list.add(new RuntimeDataSchemeNodeDTO(field));
        }
        return list;
    }

    @Override
    protected List<RuntimeDataSchemeNode> searchDataTable(List<String> schemeKey, String keyword, int searchType0) {
        List<DataTableDTO> dataTables = this.tableService.searchBy(schemeKey, keyword, searchType0);
        Set<String> dataSchemeKeys = dataTables.stream().map(DataTableDTO::getDataSchemeKey).collect(Collectors.toSet());
        Map<String, Boolean> canReadSchemes = this.canReadSchemes(dataSchemeKeys);
        ArrayList<RuntimeDataSchemeNode> list = new ArrayList<RuntimeDataSchemeNode>(dataTables.size());
        for (DataTableDTO table : dataTables) {
            if (Boolean.FALSE.equals(canReadSchemes.get(table.getDataSchemeKey()))) continue;
            list.add(new RuntimeDataSchemeNodeDTO(table));
        }
        return list;
    }

    @Override
    protected List<RuntimeDataSchemeNode> searchDataTableGroup(List<String> schemeKey, String keyword) {
        List<DataGroupDTO> groups = this.groupService.searchBy(schemeKey, keyword, DataGroupKind.TABLE_GROUP.getValue());
        Set<String> dataSchemeKeys = groups.stream().map(DataGroupDTO::getDataSchemeKey).collect(Collectors.toSet());
        Map<String, Boolean> canReadSchemes = this.canReadSchemes(dataSchemeKeys);
        ArrayList<RuntimeDataSchemeNode> list = new ArrayList<RuntimeDataSchemeNode>(groups.size());
        for (DataGroupDTO group : groups) {
            if (Boolean.FALSE.equals(canReadSchemes.get(group.getDataSchemeKey()))) continue;
            list.add(new RuntimeDataSchemeNodeDTO(group));
        }
        return list;
    }

    @Override
    protected List<RuntimeDataSchemeNode> searchDataSchemeGroup(String keyword) {
        List<DataGroupDTO> groups = this.groupService.searchBy((String)null, keyword, DataGroupKind.SCHEME_GROUP.getValue() + DataGroupKind.QUERY_SCHEME_GROUP.getValue());
        ArrayList<RuntimeDataSchemeNode> list = new ArrayList<RuntimeDataSchemeNode>(groups.size());
        for (DataGroupDTO group : groups) {
            list.add(new RuntimeDataSchemeNodeDTO(group));
        }
        return list;
    }

    @Override
    protected List<RuntimeDataSchemeNode> searchDataScheme(String keyword) {
        List<DataSchemeDTO> schemes = this.schemeService.searchByKeyword(keyword);
        ArrayList<RuntimeDataSchemeNode> list = new ArrayList<RuntimeDataSchemeNode>(schemes.size());
        for (DataSchemeDTO scheme : schemes) {
            if (!this.dataSchemeAuthService.canReadScheme(scheme.getKey())) continue;
            list.add(new RuntimeDataSchemeNodeDTO(scheme));
        }
        return list;
    }

    public List<ITree<RuntimeDataSchemeNode>> getCheckBoxSchemeGroupRootTree(int interestType, int checkboxOptional, NodeFilter filter) {
        SchemeNode root = new SchemeNode("00000000-0000-0000-0000-000000000000", NodeType.SCHEME_GROUP.getValue());
        DataResourceTree serve = new DataResourceTree(this.entityMetaService, this.periodEngineService, filter);
        serve.setDataTableDao(this.iDataTableDao);
        serve.setDataTableRelDao(this.iDataTableRelDao);
        serve.setCheckboxOptional(checkboxOptional);
        this.runtimeLevelLoader.walkDataSchemeTree(root, (RuntimeSchemeVisitor)serve);
        SchemeNode queryRoot = new SchemeNode("00000000-0000-0000-0000-111111111111", NodeType.SCHEME_GROUP.getValue());
        DataResourceTree queryServe = new DataResourceTree(this.entityMetaService, this.periodEngineService, filter);
        queryServe.setDataTableDao(this.iDataTableDao);
        queryServe.setDataTableRelDao(this.iDataTableRelDao);
        queryServe.setCheckboxOptional(checkboxOptional);
        this.runtimeLevelLoader.walkDataSchemeTree(queryRoot, (RuntimeSchemeVisitor)queryServe);
        List<ITree<RuntimeDataSchemeNode>> value = serve.getValue();
        if (!queryServe.getValue().isEmpty() && queryServe.getValue().get(0).hasChildren()) {
            value.addAll(queryServe.getValue());
        }
        return value;
    }

    public List<ITree<RuntimeDataSchemeNode>> getCheckBoxSchemeGroupChildTree(int interestType, RuntimeDataSchemeNode parent, int checkboxOptional, NodeFilter filter) {
        Assert.notNull((Object)parent, "parent must not be null.");
        SchemeNode root = new SchemeNode(parent.getKey(), parent.getType());
        DataResourceTree serve = new DataResourceTree(this.entityMetaService, this.periodEngineService, filter);
        serve.setDataTableDao(this.iDataTableDao);
        serve.setDataTableRelDao(this.iDataTableRelDao);
        serve.setChildren(true);
        serve.setCheckboxOptional(checkboxOptional);
        this.runtimeLevelLoader.walkDataSchemeTree(root, (RuntimeSchemeVisitor)serve);
        return serve.getValue();
    }

    public List<ITree<RuntimeDataSchemeNode>> getCheckBoxSchemeSpecifiedTree(int interestType, RuntimeDataSchemeNode node, int checkboxOptional, NodeFilter filter) {
        Assert.notNull((Object)node, "node must not be null.");
        SchemeNode root = new SchemeNode(node.getKey(), node.getType());
        RunInterestReverseTreeVisitor visitor = new RunInterestReverseTreeVisitor(this.entityMetaService, this.periodEngineService, interestType, node.getKey());
        visitor.setDataTableDao(this.iDataTableDao);
        visitor.setDataTableRelDao(this.iDataTableRelDao);
        visitor.setDataFieldDao(this.dataFieldDao);
        visitor.setNodeFilter(filter);
        this.runtimeLevelLoader.reverseDataSchemeTree(root, (RuntimeReverseSchemeVisitor)visitor, Integer.valueOf(interestType));
        return visitor.getValues();
    }
}

