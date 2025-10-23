/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeNode
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  com.jiuqi.nr.datascheme.common.NodeIconGetter
 *  com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.storage.IParameterStorageProvider
 *  com.jiuqi.nvwa.framework.parameter.storage.ParameterBean
 *  com.jiuqi.nvwa.framework.parameter.storage.ParameterFolderBean
 *  com.jiuqi.nvwa.framework.parameter.storage.ParameterResourceIdentify
 *  com.jiuqi.nvwa.framework.parameter.storage.ParameterStorageException
 *  com.jiuqi.nvwa.framework.parameter.storage.ParameterStorageManager
 *  com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.service.IDataSourceInfoFilterService
 *  com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.service.IDynamicDataSourceManager
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.query.datascheme.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.core.DataSchemeNode;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO;
import com.jiuqi.nr.query.datascheme.common.QueryDataSchemeErrorEnum;
import com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException;
import com.jiuqi.nr.query.datascheme.extend.DataTableFactoryManager;
import com.jiuqi.nr.query.datascheme.extend.DataTableFolder;
import com.jiuqi.nr.query.datascheme.extend.DataTableNode;
import com.jiuqi.nr.query.datascheme.extend.IDataTableFactory;
import com.jiuqi.nr.query.datascheme.extend.IDataTableMetaDataProvider;
import com.jiuqi.nr.query.datascheme.service.IDesignQueryDataSchemeService;
import com.jiuqi.nr.query.datascheme.service.dto.QueryDataTableDTO;
import com.jiuqi.nr.query.datascheme.web.param.BaseNodeVO;
import com.jiuqi.nr.query.datascheme.web.param.TableModelLoadVO;
import com.jiuqi.nr.query.datascheme.web.param.TableModelSearchVO;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.storage.IParameterStorageProvider;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterBean;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterFolderBean;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterResourceIdentify;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterStorageException;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterStorageManager;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.service.IDataSourceInfoFilterService;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.service.IDynamicDataSourceManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/query-datascheme/"})
@Api(tags={"\u67e5\u8be2\u6570\u636e\u65b9\u6848\u6811\u5f62\u6784\u5efa\u670d\u52a1"})
public class QueryDataSchemeTreeRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryDataSchemeTreeRestController.class);
    protected static final String ICON_FENZU = "#icon-16_SHU_A_NR_fenzu";
    protected static final String ICON_MULU = "#icon-16_SHU_A_NR_mulu";
    protected static final String ICON_TABLE = "#icon-16_SHU_A_NR_zhibiaobiao";
    @Autowired
    private IDesignQueryDataSchemeService designDataSchemeService;
    @Autowired
    private IDataSchemeAuthService iDataSchemeAuthService;
    @Autowired
    private IDynamicDataSourceManager dynamicDataSourceManager;
    @Autowired
    private IDataSourceInfoFilterService dataSourceInfoFilterService;

    @ApiOperation(value="\u52a0\u8f7d\u8868\u6a21\u578b\u7684\u6811\u5f62")
    @PostMapping(value={"tree/table-model/load"})
    public List<ITree<BaseNodeVO>> loadTableModelTree(@RequestBody TableModelLoadVO loadVO) {
        BaseNodeVO parent = loadVO.getParent();
        if (null == parent) {
            List<Object> rootTree = new ArrayList<ITree<BaseNodeVO>>();
            Iterator<IDataTableFactory> iterator = DataTableFactoryManager.getInstance().iterator();
            while (iterator.hasNext()) {
                rootTree.add(QueryDataSchemeTreeRestController.createNode(iterator.next()));
            }
            if (loadVO.isSubTable()) {
                rootTree = rootTree.stream().filter(node -> ((BaseNodeVO)node.getData()).getType().equals("nvwaDataSource")).collect(Collectors.toList());
            }
            if (loadVO.isLeaf() || StringUtils.hasLength(loadVO.getMainTable())) {
                this.handleRootTree(rootTree, loadVO.getMainTable());
            }
            return rootTree;
        }
        IDataTableFactory factory = DataTableFactoryManager.getInstance().getFactory(parent.getType());
        IDataTableMetaDataProvider provider = factory.getMetaDataProvider();
        List<ITree<BaseNodeVO>> childrenTree = QueryDataSchemeTreeRestController.loadChildren(provider, parent);
        return childrenTree;
    }

    private void handleRootTree(List<ITree<BaseNodeVO>> rootTrees, String mainTable) {
        for (ITree<BaseNodeVO> rootTree : rootTrees) {
            if (((BaseNodeVO)rootTree.getData()).getType().equals("nvwaDataSource")) {
                IDataTableFactory factory = DataTableFactoryManager.getInstance().getFactory(((BaseNodeVO)rootTree.getData()).getType());
                IDataTableMetaDataProvider provider = factory.getMetaDataProvider();
                List<String> dataSourcekeys = this.getDataSourcekeys();
                String mainDataSourcekey = this.getMainDataSourcekey(mainTable);
                this.handleChildTree(provider, rootTree, dataSourcekeys, mainDataSourcekey);
                continue;
            }
            rootTree.setLeaf(true);
            rootTree.setIcons("#icon16_DH_A_NW_shujuyuanguanli");
        }
    }

    private void handleChildTree(IDataTableMetaDataProvider provider, ITree<BaseNodeVO> parentTree, List<String> dataSourcekeys, String mainDataSourcekey) {
        parentTree.setIcons("#icon16_SHU_A_NW_fenzu");
        List<ITree<BaseNodeVO>> children = this.loadNvwaDataSourceChildren(provider, (BaseNodeVO)parentTree.getData());
        for (ITree<BaseNodeVO> child : children) {
            if (dataSourcekeys.contains(((BaseNodeVO)child.getData()).getKey())) {
                child.setLeaf(true);
                if (((BaseNodeVO)child.getData()).getKey().equals(mainDataSourcekey)) {
                    child.setSelected(true);
                }
                child.setIcons("#icon16_DH_A_NW_shujuyuanguanli");
                continue;
            }
            this.handleChildTree(provider, child, dataSourcekeys, mainDataSourcekey);
        }
        parentTree.setChildren(children);
    }

    private String getMainDataSourcekey(String mainTable) {
        if (StringUtils.hasLength(mainTable)) {
            QueryDataTableDTO queryDataTable = this.designDataSchemeService.getQueryDataTable(mainTable);
            if (queryDataTable == null) {
                return null;
            }
            return queryDataTable.getTableKey().split("\\.")[0];
        }
        return null;
    }

    private List<String> getDataSourcekeys() {
        try {
            return this.dataSourceInfoFilterService.doFilter(this.dynamicDataSourceManager.listAllDataSourceInfos()).stream().map(dataSourceInfo -> dataSourceInfo.getKey()).collect(Collectors.toList());
        }
        catch (Exception e) {
            LOGGER.error("\u67e5\u8be2\u6570\u636e\u65b9\u6848\u5973\u5a32\u6570\u636e\u6e90\u5f02\u5e38", e);
            return Collections.EMPTY_LIST;
        }
    }

    private List<ITree<BaseNodeVO>> loadNvwaDataSourceChildren(IDataTableMetaDataProvider provider, BaseNodeVO parent) {
        ArrayList<ITree<BaseNodeVO>> childrenTreeNodes = new ArrayList<ITree<BaseNodeVO>>();
        try {
            List<DataTableFolder> childrenGroups = provider.getTableFolders(parent.getKey());
            if (!CollectionUtils.isEmpty(childrenGroups)) {
                List groupTreeNodes = childrenGroups.stream().map(c -> QueryDataSchemeTreeRestController.createNode(parent, c)).collect(Collectors.toList());
                childrenTreeNodes.addAll(groupTreeNodes);
            }
        }
        catch (DataTableAdaptException e) {
            LOGGER.error("\u67e5\u8be2\u6570\u636e\u65b9\u6848\u52a0\u8f7d\u6a21\u578b\u7684\u6811\u5f62\u5931\u8d25", e);
        }
        return childrenTreeNodes;
    }

    private static ITree<BaseNodeVO> createNode(IDataTableFactory factory) {
        BaseNodeVO baseNode = new BaseNodeVO();
        baseNode.setType(factory.getType());
        baseNode.setTitle(factory.getTitle());
        ITree treeNode = new ITree((INode)baseNode);
        treeNode.setLeaf(false);
        treeNode.setIcons(ICON_FENZU);
        return treeNode;
    }

    private static ITree<BaseNodeVO> createNode(BaseNodeVO parent, DataTableFolder folder) {
        BaseNodeVO baseNode = new BaseNodeVO();
        baseNode.setKey(folder.getId());
        baseNode.setTitle(folder.getTitle());
        baseNode.setType(parent.getType());
        baseNode.setParentKey(parent.getKey());
        ITree treeNode = new ITree((INode)baseNode);
        treeNode.setLeaf(false);
        treeNode.setIcons(ICON_MULU);
        return treeNode;
    }

    private static ITree<BaseNodeVO> createNode(BaseNodeVO parent, DataTableNode table) {
        BaseNodeVO baseNode = new BaseNodeVO();
        baseNode.setKey(table.getKey());
        baseNode.setCode(table.getName());
        baseNode.setTitle(table.getTitle());
        baseNode.setType(parent.getType());
        baseNode.setParentKey(parent.getKey());
        ITree treeNode = new ITree((INode)baseNode);
        treeNode.setLeaf(true);
        treeNode.setIcons(ICON_TABLE);
        return treeNode;
    }

    @ApiOperation(value="\u5b9a\u4f4d\u6a21\u578b\u7684\u6811\u5f62")
    @PostMapping(value={"tree/table-model/locate"})
    public List<ITree<BaseNodeVO>> locateTableModelTree(@RequestBody BaseNodeVO node) {
        TableModelLoadVO loadVO = new TableModelLoadVO();
        List<ITree<BaseNodeVO>> rootTree = this.loadTableModelTree(loadVO);
        ITree<BaseNodeVO> currentTreeNode = rootTree.stream().filter(treeNode -> ((BaseNodeVO)treeNode.getData()).getType().equals(node.getType())).findFirst().orElse(null);
        if (null == currentTreeNode) {
            return rootTree;
        }
        IDataTableFactory factory = DataTableFactoryManager.getInstance().getFactory(node.getType());
        IDataTableMetaDataProvider provider = factory.getMetaDataProvider();
        try {
            List<String> paths = provider.getPath(node.getKey());
            block2: for (String path : paths) {
                List<ITree<BaseNodeVO>> childrenTreeNodes = QueryDataSchemeTreeRestController.loadChildren(provider, (BaseNodeVO)currentTreeNode.getData());
                for (ITree<BaseNodeVO> childTreeNode : childrenTreeNodes) {
                    if (!childTreeNode.getKey().equals(path)) continue;
                    currentTreeNode.setExpanded(true);
                    currentTreeNode.setChildren(childrenTreeNodes);
                    currentTreeNode = childTreeNode;
                    continue block2;
                }
            }
            List<ITree<BaseNodeVO>> childrenTreeNodes = QueryDataSchemeTreeRestController.loadChildren(provider, (BaseNodeVO)currentTreeNode.getData());
            for (ITree<BaseNodeVO> childTreeNode : childrenTreeNodes) {
                if (!childTreeNode.getKey().equals(node.getKey())) continue;
                currentTreeNode.setExpanded(true);
                currentTreeNode.setChildren(childrenTreeNodes);
                childTreeNode.setSelected(true);
                break;
            }
        }
        catch (DataTableAdaptException e) {
            LOGGER.error("\u67e5\u8be2\u6570\u636e\u65b9\u6848\u5b9a\u4f4d\u6a21\u578b\u7684\u6811\u5f62\u5931\u8d25", e);
        }
        return rootTree;
    }

    private static List<ITree<BaseNodeVO>> loadChildren(IDataTableMetaDataProvider provider, BaseNodeVO parentNode) {
        ArrayList<ITree<BaseNodeVO>> childrenTreeNodes = new ArrayList<ITree<BaseNodeVO>>();
        try {
            List<DataTableFolder> childrenGroups = provider.getTableFolders(parentNode.getKey());
            if (!CollectionUtils.isEmpty(childrenGroups)) {
                List groupTreeNodes = childrenGroups.stream().map(c -> QueryDataSchemeTreeRestController.createNode(parentNode, c)).collect(Collectors.toList());
                childrenTreeNodes.addAll(groupTreeNodes);
            }
        }
        catch (DataTableAdaptException e) {
            LOGGER.error("\u67e5\u8be2\u6570\u636e\u65b9\u6848\u52a0\u8f7d\u6a21\u578b\u7684\u6811\u5f62\u5931\u8d25", e);
        }
        try {
            List<DataTableNode> childrenTables = provider.getTables(parentNode.getKey());
            if (!CollectionUtils.isEmpty(childrenTables)) {
                List tableTreeNodes = childrenTables.stream().map(t -> QueryDataSchemeTreeRestController.createNode(parentNode, t)).collect(Collectors.toList());
                childrenTreeNodes.addAll(tableTreeNodes);
            }
        }
        catch (DataTableAdaptException e) {
            LOGGER.error("\u67e5\u8be2\u6570\u636e\u65b9\u6848\u52a0\u8f7d\u6a21\u578b\u7684\u6811\u5f62\u5931\u8d25", e);
        }
        return childrenTreeNodes;
    }

    @ApiOperation(value="\u641c\u7d22\u6a21\u578b")
    @PostMapping(value={"tree/table-model/search"})
    public List<BaseNodeVO> searchTableModelTree(@RequestBody TableModelSearchVO searchVO) {
        ArrayList<BaseNodeVO> nodes = new ArrayList<BaseNodeVO>();
        ArrayList<IDataTableFactory> factorys = new ArrayList<IDataTableFactory>();
        if (StringUtils.hasLength(searchVO.getType())) {
            IDataTableFactory factory = DataTableFactoryManager.getInstance().getFactory(searchVO.getType());
            if (factory != null) {
                factorys.add(factory);
            }
        } else {
            Iterator<IDataTableFactory> iterator = DataTableFactoryManager.getInstance().iterator();
            while (iterator.hasNext()) {
                factorys.add(iterator.next());
            }
        }
        for (IDataTableFactory factory : factorys) {
            try {
                List<DataTableNode> tableNodes = factory.getMetaDataProvider().search(searchVO.getParentKey(), searchVO.getKeyword());
                if (CollectionUtils.isEmpty(tableNodes)) continue;
                for (DataTableNode table : tableNodes) {
                    BaseNodeVO baseNode = new BaseNodeVO();
                    baseNode.setKey(table.getKey());
                    baseNode.setCode(table.getName());
                    baseNode.setTitle(table.getTitle());
                    baseNode.setType(factory.getType());
                    nodes.add(baseNode);
                }
            }
            catch (DataTableAdaptException e) {
                LOGGER.error("\u67e5\u8be2\u6570\u636e\u65b9\u6848\u641c\u7d22\u6a21\u578b\u5931\u8d25", e);
            }
        }
        return nodes;
    }

    @ApiOperation(value="\u5b9a\u4f4d\u5206\u7ec4\u6811\u5f62")
    @GetMapping(value={"tree/query-group/{key}/{auth}"})
    public List<ITree<DataSchemeNode>> loadDesGroupTree(@PathVariable(required=false) String key, @PathVariable(required=false) String auth) {
        if (!StringUtils.hasLength(key)) {
            key = "00000000-0000-0000-0000-111111111111";
        }
        List<ITree<DataSchemeNode>> allTreeNodes = this.designDataSchemeService.getDataGroupByKind(DataGroupKind.QUERY_SCHEME_GROUP.getValue()).stream().map(this::toNode).collect(Collectors.toList());
        ITree rootNode = new ITree((INode)this.createRootQueryGroupNode());
        rootNode.setExpanded(true);
        allTreeNodes.add(rootNode);
        Map<String, ITree<DataSchemeNode>> allTreeNodeMap = StringUtils.hasLength(auth) && "write".equals(auth) ? this.buildTree(allTreeNodes, g -> this.iDataSchemeAuthService.canReadGroup(g.getKey()), g -> !this.iDataSchemeAuthService.canWriteGroup(g.getKey())) : this.buildTree(allTreeNodes, g -> true, g -> true);
        this.localNode(allTreeNodeMap, (ITree<DataSchemeNode>)rootNode, key);
        return Collections.singletonList(rootNode);
    }

    private DataSchemeNode createRootQueryGroupNode() {
        DataSchemeNodeDTO dataSchemeNodeDTO = new DataSchemeNodeDTO();
        dataSchemeNodeDTO.setKey("00000000-0000-0000-0000-111111111111");
        dataSchemeNodeDTO.setTitle("\u5168\u90e8\u67e5\u8be2\u6570\u636e\u65b9\u6848");
        dataSchemeNodeDTO.setType(NodeType.SCHEME_GROUP.getValue());
        dataSchemeNodeDTO.setCode("query-root");
        return dataSchemeNodeDTO;
    }

    private Map<String, ITree<DataSchemeNode>> buildTree(List<ITree<DataSchemeNode>> allTreeNodes, Predicate<? super ITree<DataSchemeNode>> filter, Predicate<ITree<DataSchemeNode>> disabled) {
        HashMap iTreeNodeGroup = new HashMap();
        allTreeNodes.stream().filter(filter).forEach(node -> {
            node.setDisabled(disabled.test((ITree<DataSchemeNode>)node));
            iTreeNodeGroup.computeIfAbsent(((DataSchemeNode)node.getData()).getParentKey(), k -> new ArrayList()).add(node);
        });
        HashMap<String, ITree<DataSchemeNode>> allTreeNodeMap = new HashMap<String, ITree<DataSchemeNode>>();
        for (ITree<DataSchemeNode> iTreeNode : allTreeNodes) {
            iTreeNode.setIcons(NodeIconGetter.getIconByType((NodeType)NodeType.SCHEME_GROUP));
            if (iTreeNodeGroup.containsKey(iTreeNode.getKey())) {
                iTreeNode.setChildren((List)iTreeNodeGroup.get(iTreeNode.getKey()));
            } else {
                iTreeNode.setLeaf(true);
            }
            allTreeNodeMap.put(iTreeNode.getKey(), iTreeNode);
        }
        return allTreeNodeMap;
    }

    private ITree<DataSchemeNode> toNode(DesignDataGroup group) {
        return new ITree((INode)new DataSchemeNodeDTO(group));
    }

    private void localNode(Map<String, ITree<DataSchemeNode>> allTreeNodeMap, ITree<DataSchemeNode> rootNode, String localKey) {
        ITree<DataSchemeNode> node;
        String expandedNodeKey;
        if (!StringUtils.hasText(localKey)) {
            expandedNodeKey = "00000000-0000-0000-0000-111111111111";
            rootNode.setSelected(true);
        } else {
            node = allTreeNodeMap.get(localKey);
            if (null != node) {
                node.setSelected(true);
                expandedNodeKey = node.getKey();
            } else {
                expandedNodeKey = null;
            }
        }
        while (StringUtils.hasText(expandedNodeKey)) {
            node = allTreeNodeMap.get(expandedNodeKey);
            if (null != node) {
                expandedNodeKey = ((DataSchemeNode)node.getData()).getParentKey();
                node.setExpanded(true);
                continue;
            }
            expandedNodeKey = null;
        }
    }

    @ApiOperation(value="\u516c\u5171\u53c2\u6570\u7684\u6811\u5f62")
    @PostMapping(value={"tree/parameter/load/{parentId}"})
    public List<ITree> loadParameterTree(@PathVariable String parentId) throws ParameterException {
        ArrayList<ITree> trees = new ArrayList<ITree>();
        try {
            IParameterStorageProvider storageProvider = ParameterStorageManager.getInstance().getStorageProvider("com.jiuqi.nvwa.parameter.storage");
            List subFolders = storageProvider.getFolder(parentId);
            for (ParameterFolderBean folder : subFolders) {
                ITree tree = new ITree();
                tree.setKey(folder.getId());
                tree.setTitle(folder.getTitle());
                tree.setIcons("#icon16_DH_A_NW_gongnengfenzushouqi");
                trees.add(tree);
            }
            List items = storageProvider.getParameterBeans(parentId);
            for (ParameterBean item : items) {
                ITree tree = new ITree();
                tree.setKey(item.getId());
                tree.setCode(item.getName());
                tree.setTitle(item.getName() + " | " + item.getTitle());
                tree.setLeaf(true);
                tree.setIcons("#icon16_SHU_A_NW_canshu");
                trees.add(tree);
            }
        }
        catch (ParameterStorageException e) {
            throw new ParameterException("\u83b7\u53d6\u53c2\u6570\u51fa\u9519", (Throwable)e);
        }
        return trees;
    }

    @ApiOperation(value="\u53c2\u6570\u8def\u5f84")
    @PostMapping(value={"tree/parameter/path/{parameterName}"})
    public List<String> getParameterPath(@PathVariable String parameterName) throws JQException {
        try {
            IParameterStorageProvider storageProvider = ParameterStorageManager.getInstance().getStorageProvider("com.jiuqi.nvwa.parameter.storage");
            ParameterResourceIdentify ParameterResourceIdentify2 = new ParameterResourceIdentify(parameterName);
            return storageProvider.getPath(ParameterResourceIdentify2);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)QueryDataSchemeErrorEnum.REFPARAMETER_EXCEPTION_000, "\u83b7\u53d6\u516c\u5171\u53c2\u6570\u3010" + parameterName + "\u3011\u51fa\u9519", (Throwable)e);
        }
    }
}

