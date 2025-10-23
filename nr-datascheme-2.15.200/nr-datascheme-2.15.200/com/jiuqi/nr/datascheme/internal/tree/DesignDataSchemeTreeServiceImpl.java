/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.FieldSearchQuery
 *  com.jiuqi.nr.datascheme.api.TreeSearchQuery
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeNode
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.exception.NotFindSchemeDataException
 *  com.jiuqi.nr.datascheme.api.loader.des.LevelLoader
 *  com.jiuqi.nr.datascheme.api.loader.des.ReverseSchemeNodeVisitor
 *  com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeTreeService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 */
package com.jiuqi.nr.datascheme.internal.tree;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.FieldSearchQuery;
import com.jiuqi.nr.datascheme.api.TreeSearchQuery;
import com.jiuqi.nr.datascheme.api.core.DataSchemeNode;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.exception.NotFindSchemeDataException;
import com.jiuqi.nr.datascheme.api.loader.des.LevelLoader;
import com.jiuqi.nr.datascheme.api.loader.des.ReverseSchemeNodeVisitor;
import com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeTreeService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableRelDao;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableRelDO;
import com.jiuqi.nr.datascheme.internal.service.DataFieldDesignService;
import com.jiuqi.nr.datascheme.internal.service.DataGroupDesignService;
import com.jiuqi.nr.datascheme.internal.service.DataSchemeDesignService;
import com.jiuqi.nr.datascheme.internal.service.DataTableDesignService;
import com.jiuqi.nr.datascheme.internal.tree.BaseTreeNodeSearch;
import com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.internal.tree.DataTableNodeDTO;
import com.jiuqi.nr.datascheme.internal.tree.des.CheckBoxBuildTreeVisitor;
import com.jiuqi.nr.datascheme.internal.tree.des.DesInterestSpecifiedTree;
import com.jiuqi.nr.datascheme.internal.tree.des.InterestSpecifiedTree;
import com.jiuqi.nr.datascheme.internal.tree.des.InterestTreeVisitor;
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
public class DesignDataSchemeTreeServiceImpl
extends BaseTreeNodeSearch<DataSchemeNode>
implements IDataSchemeTreeService<DataSchemeNode> {
    @Autowired
    private IDesignDataSchemeService iDesignDataSchemeService;
    @Autowired
    private DataSchemeDesignService dataSchemeService;
    @Autowired
    private DataGroupDesignService dataGroupDesignService;
    @Autowired
    private DataTableDesignService dataTableDesignService;
    @Autowired
    private DataFieldDesignService dataFieldDesignService;
    @Autowired
    private LevelLoader levelLoader;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IDataFieldDao<DesignDataFieldDO> dataFieldDao;
    @Autowired
    private IDataTableRelDao<DesignDataTableRelDO> iDataTableRelDao;
    @Autowired
    private IDataTableDao<DesignDataTableDO> iDataTableDao;

    public List<ITree<DataSchemeNode>> getRootTree(NodeType leaf, String scheme) {
        return this.getRootTree(scheme, NodeType.getInterestTypeByLeft((NodeType)leaf), null);
    }

    public List<ITree<DataSchemeNode>> getChildTree(NodeType leaf, DataSchemeNode parent) {
        return this.getChildTree(parent, NodeType.getInterestTypeByLeft((NodeType)leaf), null);
    }

    public List<ITree<DataSchemeNode>> getSpecifiedTree(NodeType leafType, DataSchemeNode node, String schemeKey) {
        return this.getSpecifiedTree(node, schemeKey, NodeType.getInterestTypeByLeft((NodeType)leafType), null);
    }

    public List<ITree<DataSchemeNode>> getSchemeGroupRootTree(NodeType nodeType) {
        return this.getSchemeGroupRootTree(NodeType.getInterestTypeByLeft((NodeType)nodeType), null);
    }

    public List<ITree<DataSchemeNode>> getQuerySchemeGroupRootTree(NodeType nodeType) {
        SchemeNode root = new SchemeNode("00000000-0000-0000-0000-111111111111", NodeType.SCHEME_GROUP.getValue());
        InterestTreeVisitor serve = new InterestTreeVisitor(this.entityMetaService, this.periodEngineService, NodeType.getInterestTypeByLeft((NodeType)nodeType));
        serve.setDataTableDao(this.iDataTableDao);
        serve.setDataTableRelDao(this.iDataTableRelDao);
        serve.setDataFieldDao(this.dataFieldDao);
        serve.setNodeFilter(null);
        this.levelLoader.walkDataSchemeTree(root, (SchemeNodeVisitor)serve, Integer.valueOf(NodeType.getInterestTypeByLeft((NodeType)nodeType)));
        return serve.getValue();
    }

    public List<ITree<DataSchemeNode>> getSchemeGroupChildTree(NodeType nodeType, DataSchemeNode parent) {
        return this.getSchemeGroupChildTree(parent, NodeType.getInterestTypeByLeft((NodeType)nodeType), null);
    }

    public List<ITree<DataSchemeNode>> getSchemeGroupSpecifiedTree(NodeType leafType, DataSchemeNode node) {
        return this.getSchemeGroupSpecifiedTree(node, NodeType.getInterestTypeByLeft((NodeType)leafType), null);
    }

    public List<ITree<DataSchemeNode>> getCheckBoxSchemeGroupRootTree(NodeType leafType, int checkboxOptional, NodeFilter filter) {
        return this.getCheckBoxSchemeGroupRootTree(NodeType.getInterestTypeByLeft((NodeType)leafType), checkboxOptional, filter);
    }

    public List<ITree<DataSchemeNode>> getCheckBoxSchemeGroupChildTree(NodeType leafType, DataSchemeNode parent, int checkboxOptional, NodeFilter filter) {
        return this.getCheckBoxSchemeGroupChildTree(NodeType.getInterestTypeByLeft((NodeType)leafType), parent, checkboxOptional, filter);
    }

    public List<ITree<DataSchemeNode>> getCheckBoxSchemeSpecifiedTree(NodeType leafType, DataSchemeNode node, int checkboxOptional, NodeFilter filter) {
        return null;
    }

    public List<ITree<DataSchemeNode>> getSchemeGroupRootTree(int interestType, NodeFilter filter) {
        SchemeNode root = new SchemeNode("00000000-0000-0000-0000-000000000000", NodeType.SCHEME_GROUP.getValue());
        InterestTreeVisitor serve = new InterestTreeVisitor(this.entityMetaService, this.periodEngineService, interestType);
        serve.setDataTableDao(this.iDataTableDao);
        serve.setDataTableRelDao(this.iDataTableRelDao);
        serve.setDataFieldDao(this.dataFieldDao);
        serve.setNodeFilter(filter);
        this.levelLoader.walkDataSchemeTree(root, (SchemeNodeVisitor)serve, Integer.valueOf(interestType));
        return serve.getValue();
    }

    public List<ITree<DataSchemeNode>> getSchemeGroupChildTree(DataSchemeNode parent, int interestType, NodeFilter filter) {
        Assert.notNull((Object)parent, "parent must not be null.");
        int type = parent.getType();
        String key = parent.getKey();
        if (NodeType.DIM.getValue() == type) {
            key = key.substring(key.indexOf(":") + 1);
        }
        SchemeNode root = new SchemeNode(key, type);
        InterestTreeVisitor serve = new InterestTreeVisitor(this.entityMetaService, this.periodEngineService, interestType);
        serve.setDataTableDao(this.iDataTableDao);
        serve.setDataTableRelDao(this.iDataTableRelDao);
        serve.setDataFieldDao(this.dataFieldDao);
        serve.setNodeFilter(filter);
        serve.setChildren(true);
        this.levelLoader.walkDataSchemeTree(root, (SchemeNodeVisitor)serve, Integer.valueOf(interestType));
        return serve.getValue();
    }

    public List<ITree<DataSchemeNode>> getSchemeGroupSpecifiedTree(DataSchemeNode node, int interestType, NodeFilter filter) {
        Assert.notNull((Object)node, "node must not be null.");
        SchemeNode root = new SchemeNode(node.getKey(), node.getType());
        if (interestType == 0) {
            return Collections.emptyList();
        }
        DesInterestSpecifiedTree visitor = new DesInterestSpecifiedTree(this.entityMetaService, this.periodEngineService, interestType, node.getKey());
        visitor.setNodeFilter(filter);
        this.levelLoader.reverseDataSchemeTree(root, (ReverseSchemeNodeVisitor)visitor, Integer.valueOf(interestType));
        return visitor.getValues();
    }

    public List<ITree<DataSchemeNode>> getRootTree(String scheme, int interestType, NodeFilter filter) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        DesignDataScheme dataScheme = this.iDesignDataSchemeService.getDataScheme(scheme);
        if (dataScheme == null) {
            return Collections.emptyList();
        }
        SchemeNode root = new SchemeNode(dataScheme.getKey(), NodeType.SCHEME.getValue());
        InterestTreeVisitor serve = new InterestTreeVisitor(this.entityMetaService, this.periodEngineService, interestType);
        serve.setDataTableDao(this.iDataTableDao);
        serve.setDataTableRelDao(this.iDataTableRelDao);
        serve.setDataFieldDao(this.dataFieldDao);
        serve.setNodeFilter(filter);
        this.levelLoader.walkDataSchemeTree(root, (SchemeNodeVisitor)serve, Integer.valueOf(interestType));
        return serve.getValue();
    }

    public List<ITree<DataSchemeNode>> getChildTree(DataSchemeNode parent, int interestType, NodeFilter filter) {
        Assert.notNull((Object)parent, "parent must not be null.");
        int type = parent.getType();
        String key = parent.getKey();
        if (NodeType.DIM.getValue() == type) {
            key = key.substring(key.indexOf(":") + 1);
        }
        SchemeNode root = new SchemeNode(key, type);
        InterestTreeVisitor serve = new InterestTreeVisitor(this.entityMetaService, this.periodEngineService, interestType);
        serve.setDataTableDao(this.iDataTableDao);
        serve.setDataTableRelDao(this.iDataTableRelDao);
        serve.setDataFieldDao(this.dataFieldDao);
        serve.setNodeFilter(filter);
        serve.setChildren(true);
        this.levelLoader.walkDataSchemeTree(root, (SchemeNodeVisitor)serve, Integer.valueOf(interestType));
        return serve.getValue();
    }

    public List<ITree<DataSchemeNode>> getSpecifiedTree(DataSchemeNode node, String schemeKey, int interestType, NodeFilter filter) {
        boolean isScheme;
        Assert.notNull((Object)node, "node must not be null.");
        String key = node.getKey();
        Assert.notNull((Object)key, "key must not be null.");
        int type = node.getType();
        boolean bl = isScheme = (NodeType.SCHEME.getValue() & type) != 0;
        if (isScheme) {
            return this.getRootTree(key, interestType, filter);
        }
        SchemeNode leaf = new SchemeNode(key, node.getType());
        InterestSpecifiedTree specifiedVisitor = new InterestSpecifiedTree(this.entityMetaService, this.periodEngineService, interestType, key);
        specifiedVisitor.setDataTableDao(this.iDataTableDao);
        specifiedVisitor.setDataTableRelDao(this.iDataTableRelDao);
        specifiedVisitor.setDataFieldDao(this.dataFieldDao);
        try {
            specifiedVisitor.setNodeFilter(filter);
            this.levelLoader.reverseDataSchemeTree(leaf, (ReverseSchemeNodeVisitor)specifiedVisitor, Integer.valueOf(interestType));
            List<ITree<DataSchemeNode>> children = specifiedVisitor.getValues();
            DesignDataScheme dataScheme = this.iDesignDataSchemeService.getDataScheme(schemeKey);
            ArrayList<ITree<DataSchemeNode>> value = new ArrayList<ITree<DataSchemeNode>>();
            ITree root = new ITree((INode)new DataSchemeNodeDTO(dataScheme));
            value.add(root);
            root.setIcons(NodeIconGetter.getIconByType(NodeType.SCHEME));
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

    @Override
    public List<DataSchemeNode> search(TreeSearchQuery searchQuery) {
        return super.search(searchQuery);
    }

    @Override
    protected List<DataSchemeNode> searchDataField(List<String> schemeKey, String keyword, int kind) {
        ArrayList<DataSchemeNode> list = new ArrayList<DataSchemeNode>();
        FieldSearchQuery query = new FieldSearchQuery();
        query.setSchemes(schemeKey);
        query.setKeyword(keyword);
        query.setKind(Integer.valueOf(kind));
        List<DesignDataField> fields = this.dataFieldDesignService.filterField(query);
        Set<String> dataSchemeKeys = fields.stream().map(DataField::getDataSchemeKey).collect(Collectors.toSet());
        Map<String, Boolean> canReadSchemes = this.canReadSchemes(dataSchemeKeys);
        for (DesignDataField dataField : fields) {
            if (Boolean.FALSE.equals(canReadSchemes.get(dataField.getDataSchemeKey()))) continue;
            list.add(new DataSchemeNodeDTO(dataField));
        }
        return list;
    }

    @Override
    protected List<DataSchemeNode> searchDataTable(List<String> schemeKey, String keyword, int searchType) {
        ArrayList<DataSchemeNode> list = new ArrayList<DataSchemeNode>();
        List<DesignDataTable> designDataTables = this.dataTableDesignService.searchBy(schemeKey, keyword, searchType);
        Set<String> dataSchemeKeys = designDataTables.stream().map(DataTable::getDataSchemeKey).collect(Collectors.toSet());
        Map<String, Boolean> canReadSchemes = this.canReadSchemes(dataSchemeKeys);
        for (DesignDataTable designDataTable : designDataTables) {
            if (Boolean.FALSE.equals(canReadSchemes.get(designDataTable.getDataSchemeKey()))) continue;
            list.add(new DataTableNodeDTO(designDataTable));
        }
        return list;
    }

    @Override
    protected List<DataSchemeNode> searchDataTableGroup(List<String> schemeKey, String keyword) {
        ArrayList<DataSchemeNode> list = new ArrayList<DataSchemeNode>();
        List<DesignDataGroup> designDataGroups = this.dataGroupDesignService.searchBy(schemeKey, keyword, DataGroupKind.TABLE_GROUP.getValue());
        Set<String> dataSchemeKeys = designDataGroups.stream().map(DataGroup::getDataSchemeKey).collect(Collectors.toSet());
        Map<String, Boolean> canReadSchemes = this.canReadSchemes(dataSchemeKeys);
        for (DesignDataGroup designDataGroup : designDataGroups) {
            if (Boolean.FALSE.equals(canReadSchemes.get(designDataGroup.getDataSchemeKey()))) continue;
            list.add(new DataSchemeNodeDTO(designDataGroup));
        }
        return list;
    }

    @Override
    protected List<DataSchemeNode> searchDataSchemeGroup(String keyword) {
        ArrayList<DataSchemeNode> list = new ArrayList<DataSchemeNode>();
        List<DesignDataGroup> designDataGroups = this.dataGroupDesignService.searchBy((String)null, keyword, DataGroupKind.SCHEME_GROUP.getValue() + DataGroupKind.QUERY_SCHEME_GROUP.getValue());
        for (DesignDataGroup designDataGroup : designDataGroups) {
            list.add(new DataSchemeNodeDTO(designDataGroup));
        }
        return list;
    }

    @Override
    protected List<DataSchemeNode> searchDataScheme(String keyword) {
        ArrayList<DataSchemeNode> list = new ArrayList<DataSchemeNode>();
        List<DesignDataScheme> designDataSchemes = this.dataSchemeService.searchByKeyword(keyword);
        for (DesignDataScheme designDataScheme : designDataSchemes) {
            if (!this.dataSchemeAuthService.canReadScheme(designDataScheme.getKey())) continue;
            list.add(new DataSchemeNodeDTO(designDataScheme));
        }
        return list;
    }

    public List<ITree<DataSchemeNode>> getCheckBoxSchemeGroupRootTree(int interestType, int checkboxOptional, NodeFilter filter) {
        SchemeNode root = new SchemeNode("00000000-0000-0000-0000-000000000000", NodeType.SCHEME_GROUP.getValue());
        CheckBoxBuildTreeVisitor serve = new CheckBoxBuildTreeVisitor(interestType);
        serve.setRoot(true);
        serve.setCheckboxOptional(checkboxOptional);
        serve.setFilter(filter);
        serve.setDataTableDao(this.iDataTableDao);
        serve.setDataTableRelDao(this.iDataTableRelDao);
        serve.setEntityMetaService(this.entityMetaService);
        serve.setPeriodEngineService(this.periodEngineService);
        this.levelLoader.walkDataSchemeTree(root, (SchemeNodeVisitor)serve, Integer.valueOf(interestType));
        return serve.getValue();
    }

    public List<ITree<DataSchemeNode>> getCheckBoxSchemeGroupChildTree(int interestType, DataSchemeNode parent, int checkboxOptional, NodeFilter filter) {
        SchemeNode root = new SchemeNode(parent.getKey(), parent.getType());
        CheckBoxBuildTreeVisitor serve = new CheckBoxBuildTreeVisitor(interestType);
        serve.setRoot(true);
        serve.setCheckboxOptional(checkboxOptional);
        serve.setFilter(filter);
        serve.setChildren(true);
        serve.setDataTableDao(this.iDataTableDao);
        serve.setDataTableRelDao(this.iDataTableRelDao);
        serve.setEntityMetaService(this.entityMetaService);
        serve.setPeriodEngineService(this.periodEngineService);
        this.levelLoader.walkDataSchemeTree(root, (SchemeNodeVisitor)serve, Integer.valueOf(interestType));
        return serve.getValue();
    }

    public List<ITree<DataSchemeNode>> getCheckBoxSchemeSpecifiedTree(int interestType, DataSchemeNode node, int checkboxOptional, NodeFilter filter) {
        return null;
    }
}

