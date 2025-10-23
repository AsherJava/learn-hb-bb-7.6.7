/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.TreeSearchQuery
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeNode
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.RuntimeDataSchemeNode
 *  com.jiuqi.nr.datascheme.api.exception.NotFindSchemeDataException
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeTreeService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  com.jiuqi.nr.datascheme.api.type.DataSchemeType
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.zb.scheme.core.ZbInfo
 *  com.jiuqi.nr.zb.scheme.core.ZbSchemeVersion
 *  com.jiuqi.nr.zb.scheme.service.IZbSchemeService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.datascheme.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.TreeSearchQuery;
import com.jiuqi.nr.datascheme.api.core.DataSchemeNode;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.RuntimeDataSchemeNode;
import com.jiuqi.nr.datascheme.api.exception.NotFindSchemeDataException;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeTreeService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.api.type.DataSchemeType;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import com.jiuqi.nr.datascheme.internal.service.IDesignDataSchemeTreeService;
import com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.internal.tree.RuntimeDataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.web.facade.BusinessError;
import com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery;
import com.jiuqi.nr.datascheme.web.param.SchemeNodeFilter;
import com.jiuqi.nr.datascheme.web.param.ZbSchemeNodeFilter;
import com.jiuqi.nr.datascheme.web.tree.NoOtherDimFilter;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.core.ZbSchemeVersion;
import com.jiuqi.nr.zb.scheme.service.IZbSchemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/datascheme/"})
@Api(tags={"\u6570\u636e\u65b9\u6848\u6811\u5f62\u7ed3\u6784\u670d\u52a1"})
public class DataSchemeTreeRestController {
    @Autowired
    private IDataSchemeTreeService<DataSchemeNode> treeService;
    @Autowired
    private IDataSchemeTreeService<RuntimeDataSchemeNode> runRimeTreeService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private IDataSchemeAuthService iDataSchemeAuthService;
    @Autowired
    private IDesignDataSchemeTreeService iDesignDataSchemeTreeService;
    @Autowired
    private IZbSchemeService zbSchemeService;
    private final Logger logger = LoggerFactory.getLogger(DataSchemeTreeRestController.class);

    @ApiOperation(value="\u83b7\u53d6\u53ef\u7f16\u8f91\u6811\u5f62")
    @GetMapping(value={"tree/write/{localKey}"})
    public List<ITree<DataSchemeNode>> queryWriteGroupTree(@PathVariable String localKey) {
        List<ITree<DataSchemeNode>> allTreeNodes = this.designDataSchemeService.getDataGroupByKind(DataGroupKind.SCHEME_GROUP.getValue()).stream().map(this::toNode).collect(Collectors.toList());
        ITree rootNode = new ITree((INode)this.getRoot());
        rootNode.setExpanded(true);
        allTreeNodes.add(rootNode);
        Map<String, ITree<DataSchemeNode>> allTreeNodeMap = this.buildTree(allTreeNodes, g -> this.iDataSchemeAuthService.canReadGroup(g.getKey()), g -> !this.iDataSchemeAuthService.canWriteGroup(g.getKey()));
        this.localNode(allTreeNodeMap, (ITree<DataSchemeNode>)rootNode, localKey);
        return Collections.singletonList(rootNode);
    }

    private Map<String, ITree<DataSchemeNode>> buildTree(List<ITree<DataSchemeNode>> allTreeNodes, Predicate<? super ITree<DataSchemeNode>> filter, Predicate<ITree<DataSchemeNode>> disabled) {
        HashMap iTreeNodeGroup = new HashMap();
        allTreeNodes.stream().filter(filter).forEach(node -> {
            node.setDisabled(disabled.test((ITree<DataSchemeNode>)node));
            iTreeNodeGroup.computeIfAbsent(((DataSchemeNode)node.getData()).getParentKey(), k -> new ArrayList()).add(node);
        });
        HashMap<String, ITree<DataSchemeNode>> allTreeNodeMap = new HashMap<String, ITree<DataSchemeNode>>();
        for (ITree<DataSchemeNode> iTreeNode : allTreeNodes) {
            iTreeNode.setIcons(NodeIconGetter.getIconByType(NodeType.SCHEME_GROUP));
            if (iTreeNodeGroup.containsKey(iTreeNode.getKey())) {
                iTreeNode.setChildren((List)iTreeNodeGroup.get(iTreeNode.getKey()));
            } else {
                iTreeNode.setLeaf(true);
            }
            allTreeNodeMap.put(iTreeNode.getKey(), iTreeNode);
        }
        return allTreeNodeMap;
    }

    private void localNode(Map<String, ITree<DataSchemeNode>> allTreeNodeMap, ITree<DataSchemeNode> rootNode, String localKey) {
        ITree<DataSchemeNode> node;
        String expandedNodeKey;
        if (!StringUtils.hasText(localKey)) {
            expandedNodeKey = "00000000-0000-0000-0000-000000000000";
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

    private ITree<DataSchemeNode> toNode(DesignDataGroup group) {
        return new ITree((INode)new DataSchemeNodeDTO(group));
    }

    private DataSchemeNodeDTO getRoot() {
        DataSchemeNodeDTO dataSchemeNodeDTO = new DataSchemeNodeDTO();
        dataSchemeNodeDTO.setKey("00000000-0000-0000-0000-000000000000");
        dataSchemeNodeDTO.setType(NodeType.SCHEME_GROUP.getValue());
        dataSchemeNodeDTO.setCode("root");
        dataSchemeNodeDTO.setTitle("\u5168\u90e8\u6570\u636e\u65b9\u6848");
        return dataSchemeNodeDTO;
    }

    private NodeFilter getDesSchemeTypeFilter(DataSchemeType type) {
        return node -> {
            if (node.getType() == NodeType.SCHEME.getValue()) {
                Object data = node.getData();
                DataScheme scheme = null;
                if (data instanceof DataScheme) {
                    scheme = (DataScheme)data;
                }
                if (scheme == null) {
                    scheme = this.designDataSchemeService.getDataScheme(node.getKey());
                }
                if (scheme != null) {
                    return scheme.getType() == type;
                }
            }
            return true;
        };
    }

    private NodeFilter getDesGroupKindFilter(DataGroupKind kind) {
        return node -> {
            if (node.getType() == NodeType.SCHEME_GROUP.getValue()) {
                Object data = node.getData();
                DataGroup group = null;
                if (data instanceof DataGroup) {
                    group = (DataGroup)data;
                }
                if (group == null) {
                    group = this.designDataSchemeService.getDataGroup(node.getKey());
                }
                if (null != group) {
                    return group.getDataGroupKind() == kind;
                }
            }
            return true;
        };
    }

    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u65b9\u6848\u6811\u5f62\u6839\u8282\u70b9")
    @PostMapping(value={"tree/root"})
    public List<ITree<DataSchemeNode>> querySchemeTreeRoot(@RequestBody DataSchemeTreeQuery<DataSchemeNodeDTO> param) throws JQException {
        boolean checkbox = param.isCheckbox();
        this.logger.debug("\u83b7\u53d6\u6811\u5f62\u6839\u8282\u70b9 \u65b9\u6848 {} \u8282\u70b9 {} \u53f6\u5b50\u8282\u70b9\u7c7b\u578b {} \u662f\u5426\u4e3a\u590d\u9009 {}", param.getDataSchemeKey(), param.getDataSchemeNode(), param.getNodeType(), checkbox);
        NodeType nodeType = param.getNodeType();
        try {
            if (checkbox) {
                if (nodeType == null) {
                    nodeType = NodeType.FMDM_TABLE;
                }
                return this.treeService.getCheckBoxSchemeGroupRootTree(nodeType, NodeType.TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.ACCOUNT_TABLE.getValue() | NodeType.MUL_DIM_TABLE.getValue() | NodeType.GROUP.getValue() | NodeType.MD_INFO.getValue(), null);
            }
            if (null != param.getDataSchemeType()) {
                return this.treeService.getSchemeGroupRootTree(NodeType.SCHEME_GROUP.getValue() | NodeType.SCHEME.getValue(), this.getDesSchemeTypeFilter(param.getDataSchemeType()));
            }
            String dataSchemeKey = param.getDataSchemeKey();
            if (!StringUtils.hasLength(dataSchemeKey)) {
                if (nodeType == null) {
                    nodeType = NodeType.SCHEME_GROUP;
                }
                if ("00000000-0000-0000-0000-111111111111".equals(param.getDataSchemeNode().getKey())) {
                    return this.treeService.getQuerySchemeGroupRootTree(nodeType);
                }
                return this.treeService.getSchemeGroupRootTree(nodeType);
            }
            if (nodeType == null) {
                nodeType = NodeType.MUL_DIM_TABLE;
            }
            int interestType = NodeType.getInterestTypeByLeft((NodeType)nodeType);
            return this.iDesignDataSchemeTreeService.getRootTree(dataSchemeKey, interestType, new NoOtherDimFilter());
        }
        catch (NotFindSchemeDataException | SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)new BusinessError((RuntimeException)e));
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u65b9\u6848\u67d0\u4e2aKEY\u4e0b\u6240\u6709\u76f4\u63a5\u5b50\u8282\u70b9")
    @PostMapping(value={"tree/children"})
    @RequiresPermissions(value={"nr:dataScheme_tree:query"})
    public List<ITree<DataSchemeNode>> querySchemeTreeChildren(@RequestBody DataSchemeTreeQuery<DataSchemeNodeDTO> param) throws JQException {
        boolean checkbox = param.isCheckbox();
        NodeType nodeType = param.getNodeType();
        DataSchemeNodeDTO parent = param.getDataSchemeNode();
        this.logger.debug("\u83b7\u53d6\u6811\u5f62\u4e0b\u7ea7\u8282\u70b9 \u65b9\u6848 {} \u8282\u70b9 {} \u53f6\u5b50\u8282\u70b9\u7c7b\u578b {} \u662f\u5426\u4e3a\u590d\u9009 {}", param.getDataSchemeKey(), parent, nodeType, checkbox);
        try {
            if (checkbox) {
                if (nodeType == null) {
                    nodeType = NodeType.FMDM_TABLE;
                }
                return this.treeService.getCheckBoxSchemeGroupChildTree(nodeType, (INode)parent, NodeType.TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.MUL_DIM_TABLE.getValue() | NodeType.ACCOUNT_TABLE.getValue() | NodeType.GROUP.getValue() | NodeType.MD_INFO.getValue(), null);
            }
            if (null != param.getDataSchemeType()) {
                return this.treeService.getSchemeGroupChildTree((INode)parent, NodeType.SCHEME_GROUP.getValue() | NodeType.SCHEME.getValue(), this.getDesSchemeTypeFilter(param.getDataSchemeType()));
            }
            if (!StringUtils.hasLength(param.getDataSchemeKey())) {
                if (nodeType == null) {
                    nodeType = NodeType.SCHEME_GROUP;
                }
                return this.treeService.getSchemeGroupChildTree(nodeType, (INode)parent);
            }
            if (nodeType == null) {
                nodeType = NodeType.FMDM_TABLE;
            }
            int interestType = NodeType.getInterestTypeByLeft((NodeType)nodeType);
            return this.iDesignDataSchemeTreeService.getChildTree(parent, interestType, new NoOtherDimFilter());
        }
        catch (NotFindSchemeDataException | SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)new BusinessError((RuntimeException)e));
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u65b9\u6848\u67d0\u4e2a\u8282\u70b9\u7684\u8def\u5f84")
    @PostMapping(value={"tree/path"})
    @RequiresPermissions(value={"nr:dataScheme_tree:query"})
    public List<ITree<DataSchemeNode>> querySchemeTreePath(@RequestBody DataSchemeTreeQuery<DataSchemeNodeDTO> param) throws JQException {
        DataSchemeNodeDTO node = param.getDataSchemeNode();
        if (node == null || "00000000-0000-0000-0000-000000000000".equals(node.getKey())) {
            return this.querySchemeTreeRoot(param);
        }
        if ("00000000-0000-0000-0000-111111111111".equals(node.getKey())) {
            return this.querySchemeTreeRoot(param);
        }
        try {
            if (null != param.getDataSchemeType()) {
                return this.treeService.getSchemeGroupSpecifiedTree((INode)node, NodeType.SCHEME_GROUP.getValue() | NodeType.SCHEME.getValue(), this.getDesSchemeTypeFilter(param.getDataSchemeType()));
            }
            NodeType nodeType = param.getNodeType();
            if (!StringUtils.hasLength(param.getDataSchemeKey())) {
                if (nodeType == null) {
                    nodeType = NodeType.SCHEME_GROUP;
                }
                return this.treeService.getSchemeGroupSpecifiedTree(nodeType, (INode)node);
            }
            if (nodeType == null) {
                nodeType = NodeType.FMDM_TABLE;
            }
            int interestType = NodeType.getInterestTypeByLeft((NodeType)nodeType);
            return this.iDesignDataSchemeTreeService.getSpecifiedTree(node, param.getDataSchemeKey(), interestType, new NoOtherDimFilter());
        }
        catch (NotFindSchemeDataException | SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)new BusinessError((RuntimeException)e));
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u6240\u6709\u6ee1\u8db3\u6761\u4ef6\u7684\u6570\u636e\u65b9\u6848\u660e\u7ec6\u8282\u70b9")
    @PostMapping(value={"tree/filter"})
    public List<DataSchemeNode> filterSchemeTree(@RequestBody DataSchemeTreeQuery<DataSchemeNodeDTO> param) throws JQException {
        NodeType[] searchType = param.getSearchType();
        if (!StringUtils.hasLength(param.getDataSchemeKey())) {
            TreeSearchQuery searchQuery = new TreeSearchQuery(param.getFilter(), null);
            if (searchType == null) {
                searchQuery.appendSearchType(NodeType.SCHEME_GROUP);
            } else {
                for (NodeType nodeType : searchType) {
                    searchQuery.appendSearchType(nodeType);
                }
            }
            try {
                Predicate<DataSchemeNode> filter = dataSchemeNode -> true;
                if (null != param.getDataSchemeType()) {
                    filter = filter.and(n -> this.getDesSchemeTypeFilter(param.getDataSchemeType()).test(n));
                }
                if (null != param.getDataGroupKind()) {
                    filter = filter.and(n -> this.getDesGroupKindFilter(param.getDataGroupKind()).test(n));
                }
                if (null != param.getDimKey()) {
                    filter = filter.and(n -> this.getDesDimKeyFilter(param.getDimKey()).test((DataSchemeNode)n));
                }
                return this.treeService.search(searchQuery).stream().filter(filter).collect(Collectors.toList());
            }
            catch (NotFindSchemeDataException | SchemeDataException e) {
                this.logger.error(e.getMessage(), e);
                throw new JQException((ErrorEnum)new BusinessError((RuntimeException)e));
            }
        }
        TreeSearchQuery searchQuery = new TreeSearchQuery(param.getFilter(), param.getDataSchemeKey());
        if (searchType == null) {
            searchQuery.setSearchType(NodeType.GROUP.getValue() | NodeType.MD_INFO.getValue() | NodeType.TABLE.getValue() | NodeType.DETAIL_TABLE.getValue() | NodeType.ACCOUNT_TABLE.getValue());
        } else {
            for (NodeType nodeType : searchType) {
                searchQuery.appendSearchType(nodeType);
            }
        }
        try {
            return this.treeService.search(searchQuery);
        }
        catch (NotFindSchemeDataException | SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)new BusinessError((RuntimeException)e));
        }
    }

    private Predicate<DataSchemeNode> getDesDimKeyFilter(String dimKey) {
        return node -> {
            if (node.getType() == NodeType.SCHEME.getValue()) {
                List dimension = this.designDataSchemeService.getDataSchemeDimension(node.getKey());
                if (CollectionUtils.isEmpty(dimension)) {
                    return false;
                }
                for (DesignDataDimension dataDimension : dimension) {
                    if (!dataDimension.getDimKey().equals(dimKey)) continue;
                    return true;
                }
                return false;
            }
            return true;
        };
    }

    @ApiOperation(value="\u83b7\u53d6\u8fd0\u884c\u671f\u6570\u636e\u65b9\u6848\u6811\u5f62\u6839\u8282\u70b9")
    @PostMapping(value={"r-tree/root"})
    public List<ITree<RuntimeDataSchemeNode>> queryResourceTreeRoot(@RequestBody DataSchemeTreeQuery<RuntimeDataSchemeNodeDTO> param) throws JQException {
        this.logger.debug("\u83b7\u53d6\u8fd0\u884c\u671f\u6811\u5f62\u6839\u8282\u70b9");
        int checkboxOptional = this.allNode();
        String dimKey = param.getDimKey();
        SchemeNodeFilter filter = null;
        if (StringUtils.hasLength(dimKey)) {
            filter = StringUtils.hasLength(param.getPeriod()) ? new ZbSchemeNodeFilter(this.designDataSchemeService, this.runtimeDataSchemeService, this.iEntityMetaService, param, this.zbSchemeService) : new SchemeNodeFilter(this.designDataSchemeService, this.runtimeDataSchemeService, this.iEntityMetaService, param);
        }
        try {
            return this.runRimeTreeService.getCheckBoxSchemeGroupRootTree(NodeType.FIELD, checkboxOptional, filter);
        }
        catch (NotFindSchemeDataException | SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)new BusinessError((RuntimeException)e));
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u8fd0\u884c\u671f\u6570\u636e\u65b9\u6848\u67d0\u4e2aKEY\u4e0b\u6240\u6709\u76f4\u63a5\u5b50\u8282\u70b9")
    @PostMapping(value={"r-tree/children"})
    public List<ITree<RuntimeDataSchemeNode>> queryResourceTreeChildren(@RequestBody DataSchemeTreeQuery<RuntimeDataSchemeNodeDTO> param) throws JQException {
        RuntimeDataSchemeNodeDTO parent = param.getDataSchemeNode();
        Assert.notNull((Object)parent, "parent must not be null.");
        this.logger.debug("\u83b7\u53d6\u6811\u5f62\u4e0b\u7ea7\u8282\u70b9 \u8282\u70b9 {}", (Object)parent);
        String dimKey = param.getDimKey();
        SchemeNodeFilter filter = null;
        if (StringUtils.hasLength(dimKey)) {
            filter = StringUtils.hasLength(param.getPeriod()) ? new ZbSchemeNodeFilter(this.designDataSchemeService, this.runtimeDataSchemeService, this.iEntityMetaService, param, this.zbSchemeService) : new SchemeNodeFilter(this.designDataSchemeService, this.runtimeDataSchemeService, this.iEntityMetaService, param);
        }
        int checkboxOptional = this.allNode();
        try {
            return this.runRimeTreeService.getCheckBoxSchemeGroupChildTree(NodeType.FIELD, (INode)parent, checkboxOptional, filter);
        }
        catch (NotFindSchemeDataException | SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)new BusinessError((RuntimeException)e));
        }
    }

    @ApiOperation(value="\u8fd0\u884c\u671f\u6570\u636e\u65b9\u6848\u5b9a\u4f4d,\u6570\u636e\u65b9\u6848\u5206\u7ec4\u6811\u5f62")
    @PostMapping(value={"r-tree/path"})
    public List<ITree<RuntimeDataSchemeNode>> queryResourceTreePath(@RequestBody DataSchemeTreeQuery<RuntimeDataSchemeNodeDTO> param) throws JQException {
        RuntimeDataSchemeNodeDTO node = param.getDataSchemeNode();
        if (node == null || "00000000-0000-0000-0000-000000000000".equals(node.getKey())) {
            return this.queryResourceTreeRoot(param);
        }
        NodeType nodeType = param.getNodeType();
        if (nodeType == null) {
            nodeType = NodeType.TABLE_DIM;
        }
        int checkboxOptional = this.allNode();
        String dimKey = param.getDimKey();
        SchemeNodeFilter filter = null;
        if (StringUtils.hasLength(dimKey)) {
            filter = StringUtils.hasLength(param.getPeriod()) ? new ZbSchemeNodeFilter(this.designDataSchemeService, this.runtimeDataSchemeService, this.iEntityMetaService, param, true, this.zbSchemeService) : new SchemeNodeFilter(this.designDataSchemeService, this.runtimeDataSchemeService, this.iEntityMetaService, param, true);
        }
        try {
            return this.runRimeTreeService.getCheckBoxSchemeSpecifiedTree(nodeType, (INode)node, checkboxOptional, filter);
        }
        catch (NotFindSchemeDataException | SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)new BusinessError((RuntimeException)e));
        }
    }

    private int allNode() {
        int checkboxOptional = 0;
        for (NodeType value : NodeType.values()) {
            checkboxOptional |= value.getValue();
        }
        return checkboxOptional;
    }

    @ApiOperation(value="\u8fd0\u884c\u671f\u83b7\u53d6\u6240\u6709\u6ee1\u8db3\u6761\u4ef6\u7684\u6570\u636e\u65b9\u6848\u660e\u7ec6\u8282\u70b9")
    @PostMapping(value={"r-tree/filter"})
    public List<RuntimeDataSchemeNode> filterResourceSchemeData(@RequestBody DataSchemeTreeQuery<RuntimeDataSchemeNodeDTO> param) {
        NodeType[] searchType = param.getSearchType();
        String paramFilter = param.getFilter();
        if (paramFilter == null) {
            return Collections.emptyList();
        }
        TreeSearchQuery searchQuery = new TreeSearchQuery(paramFilter, param.getDataSchemeKey());
        if (searchType == null) {
            searchType = new NodeType[]{NodeType.GROUP, NodeType.TABLE, NodeType.DETAIL_TABLE, NodeType.MUL_DIM_TABLE, NodeType.ACCOUNT_TABLE, NodeType.FIELD_ZB, NodeType.FIELD, NodeType.TABLE_DIM, NodeType.MD_INFO};
        }
        ArrayList<RuntimeDataSchemeNode> list = new ArrayList<RuntimeDataSchemeNode>();
        String dimKey = param.getDimKey();
        if (StringUtils.hasLength(dimKey)) {
            RuntimeDataSchemeNodeDTO nodeDTO;
            String title;
            SchemeNodeFilter filter = StringUtils.hasLength(param.getPeriod()) ? new ZbSchemeNodeFilter(this.designDataSchemeService, this.runtimeDataSchemeService, this.iEntityMetaService, param, this.zbSchemeService) : new SchemeNodeFilter(this.designDataSchemeService, this.runtimeDataSchemeService, this.iEntityMetaService, param);
            Set<String> schemes = filter.getSchemes();
            if (CollectionUtils.isEmpty(schemes)) {
                return Collections.emptyList();
            }
            List<DataScheme> schemesData = filter.getSchemesData();
            ArrayList<String> searchSchemes = new ArrayList<String>(schemes);
            searchQuery.setSchemes(searchSchemes);
            String filterUpper = paramFilter.toUpperCase();
            for (DataScheme dataScheme : schemesData) {
                String code = dataScheme.getCode();
                title = dataScheme.getTitle();
                if (!code.toUpperCase().contains(filterUpper) && !title.toUpperCase().contains(filterUpper)) continue;
                nodeDTO = new RuntimeDataSchemeNodeDTO(dataScheme);
                list.add(nodeDTO);
            }
            List<DataGroup> groupData = filter.getGroupData();
            for (DataGroup groupDatum : groupData) {
                title = groupDatum.getTitle();
                if (!title.toUpperCase().contains(filterUpper)) continue;
                nodeDTO = new RuntimeDataSchemeNodeDTO(groupDatum);
                list.add(nodeDTO);
            }
            if (StringUtils.hasLength(param.getPeriod()) && searchSchemes.isEmpty()) {
                return list;
            }
            if (filter instanceof ZbSchemeNodeFilter) {
                int n = NodeType.FIELD_ZB.getValue() | NodeType.TABLE_DIM.getValue() | NodeType.FIELD.getValue();
                searchType = (NodeType[])Arrays.stream(searchType).filter(t -> (t.getValue() & noFilter) == 0).toArray(NodeType[]::new);
                List<RuntimeDataSchemeNode> data = this.filterResourceSchemeDataWithPeriod((ZbSchemeNodeFilter)filter, filterUpper, searchType, searchQuery);
                list.addAll(data);
            }
        }
        for (NodeType nodeType : searchType) {
            searchQuery.appendSearchType(nodeType);
        }
        List search = this.runRimeTreeService.search(searchQuery);
        list.addAll(search);
        return list;
    }

    private List<RuntimeDataSchemeNode> filterResourceSchemeDataWithPeriod(ZbSchemeNodeFilter filter, String filterUpper, NodeType[] searchType, TreeSearchQuery searchQuery) {
        List<DataScheme> dataSchemes = filter.getSchemesData();
        ArrayList<RuntimeDataSchemeNode> res = new ArrayList<RuntimeDataSchemeNode>();
        Map<String, Map<String, ZbInfo>> zbInfoMap = filter.getZbInfoMap();
        int fieldType = DataFieldKind.FIELD.getValue() | DataFieldKind.FIELD_ZB.getValue() | DataFieldKind.TABLE_FIELD_DIM.getValue();
        for (DataScheme dataScheme : dataSchemes) {
            List dataFields = this.runtimeDataSchemeService.getAllDataField(dataScheme.getKey());
            Map infoMap = zbInfoMap.getOrDefault(dataScheme.getKey(), Collections.emptyMap());
            for (DataField dataField : dataFields) {
                if ((dataField.getDataFieldKind().getValue() & fieldType) == 0) continue;
                if (infoMap.containsKey(dataField.getCode())) {
                    ZbInfo zbInfo = (ZbInfo)infoMap.get(dataField.getCode());
                    if (!zbInfo.getCode().contains(filterUpper) && !zbInfo.getTitle().contains(filterUpper)) continue;
                    RuntimeDataSchemeNodeDTO nodeDTO = new RuntimeDataSchemeNodeDTO(dataField);
                    nodeDTO.setTitle(zbInfo.getTitle());
                    res.add(nodeDTO);
                    continue;
                }
                if (!dataField.getCode().contains(filterUpper) && !dataField.getTitle().contains(filterUpper)) continue;
                RuntimeDataSchemeNodeDTO nodeDTO = new RuntimeDataSchemeNodeDTO(dataField);
                res.add(nodeDTO);
            }
        }
        return res;
    }

    @ApiOperation(value="\u8fd0\u884c\u671f\u83b7\u53d6\u6240\u6709\u6ee1\u8db3\u6761\u4ef6\u7684\u6570\u636e\u65b9\u6848\u660e\u7ec6\u8282\u70b9")
    @PostMapping(value={"r-tree/tree-filter"})
    public List<ITree<RuntimeDataSchemeNode>> filterResourceSchemeTree(@RequestBody DataSchemeTreeQuery<RuntimeDataSchemeNodeDTO> param) {
        List<RuntimeDataSchemeNode> data = this.filterResourceSchemeData(param);
        ArrayList<ITree<RuntimeDataSchemeNode>> list = new ArrayList<ITree<RuntimeDataSchemeNode>>(data.size());
        for (RuntimeDataSchemeNode node : data) {
            ITree iNode = new ITree((INode)node);
            iNode.setLeaf(true);
            iNode.setIcons(NodeIconGetter.getIconByType(node.getType()));
            list.add((ITree<RuntimeDataSchemeNode>)iNode);
        }
        return list;
    }

    @ApiOperation(value="\u8fd0\u884c\u671f\u83b7\u53d6\u6240\u6709\u6ee1\u8db3\u6761\u4ef6\u7684\u6570\u636e\u65b9\u6848\u660e\u7ec6\u8282\u70b9")
    @PostMapping(value={"r-tree/tree-filter-precise"})
    public List<ITree<RuntimeDataSchemeNode>> preciseFilterResourceSchemeTree(@RequestBody DataSchemeTreeQuery<RuntimeDataSchemeNodeDTO> param) {
        DataScheme dataScheme;
        ZbSchemeVersion zbSchemeVersion;
        List<String> filters = param.getFilters();
        if (CollectionUtils.isEmpty(filters)) {
            return Collections.emptyList();
        }
        filters = filters.stream().map(String::toUpperCase).collect(Collectors.toList());
        String scheme = param.getDataSchemeKey();
        String period = param.getPeriod();
        List dataFields = this.runtimeDataSchemeService.getAllDataField(scheme);
        HashMap zbInfoMap = new HashMap(dataFields.size());
        if (StringUtils.hasLength(period) && (zbSchemeVersion = this.zbSchemeService.getZbSchemeVersion((dataScheme = this.runtimeDataSchemeService.getDataScheme(scheme)).getZbSchemeKey(), period)) != null) {
            List zbInfos = this.zbSchemeService.listZbInfoByVersion(zbSchemeVersion.getKey());
            zbInfos.forEach(z -> zbInfoMap.put(z.getCode(), z));
        }
        ArrayList<ITree<RuntimeDataSchemeNode>> res = new ArrayList<ITree<RuntimeDataSchemeNode>>(dataFields.size());
        int fieldType = DataFieldKind.FIELD.getValue() | DataFieldKind.FIELD_ZB.getValue() | DataFieldKind.TABLE_FIELD_DIM.getValue();
        for (DataField dataField : dataFields) {
            if ((dataField.getDataFieldKind().getValue() & fieldType) == 0) continue;
            if (zbInfoMap.containsKey(dataField.getCode())) {
                ZbInfo zbInfo = (ZbInfo)zbInfoMap.get(dataField.getCode());
                if (!this.match(filters, zbInfo.getCode(), zbInfo.getTitle(), param.isPrecise())) continue;
                RuntimeDataSchemeNodeDTO nodeDTO = new RuntimeDataSchemeNodeDTO(dataField);
                nodeDTO.setTitle(zbInfo.getTitle());
                res.add(this.buildTreeNode(nodeDTO));
                continue;
            }
            if (!this.match(filters, dataField.getCode(), dataField.getTitle(), param.isPrecise())) continue;
            RuntimeDataSchemeNodeDTO nodeDTO = new RuntimeDataSchemeNodeDTO(dataField);
            res.add(this.buildTreeNode(nodeDTO));
        }
        return res;
    }

    private ITree<RuntimeDataSchemeNode> buildTreeNode(RuntimeDataSchemeNodeDTO nodeDTO) {
        ITree iNode = new ITree((INode)nodeDTO);
        iNode.setLeaf(true);
        iNode.setIcons(NodeIconGetter.getIconByType(nodeDTO.getType()));
        return iNode;
    }

    private boolean match(List<String> filters, String code, String title, Boolean precise) {
        if (precise.booleanValue()) {
            return filters.stream().anyMatch(t -> code.equals(t) || title.equals(t));
        }
        return filters.stream().anyMatch(t -> code.contains((CharSequence)t) || title.contains((CharSequence)t));
    }

    @PostMapping(value={"tree/path/all"})
    public List<ITree<DataSchemeNode>> querySchemeTreeAllData(@RequestBody DataSchemeTreeQuery<DataSchemeNodeDTO> param) {
        String dataSchemeKey = param.getDataSchemeKey();
        String filter = param.getFilter();
        int interestType = NodeType.getInterestTypeByLeft((NodeType)NodeType.FMDM_TABLE);
        if ((interestType & NodeType.DIM.getValue()) != 0) {
            interestType ^= NodeType.DIM.getValue();
        }
        List<ITree<DataSchemeNode>> rootTree = this.iDesignDataSchemeTreeService.getRootTree(dataSchemeKey, interestType, null);
        for (ITree<DataSchemeNode> tree : rootTree) {
            List children = tree.getChildren();
            for (ITree child : children) {
                if (((DataSchemeNode)child.getData()).getType() != NodeType.GROUP.getValue()) continue;
                this.loadChildren((ITree<DataSchemeNode>)child);
            }
        }
        return rootTree;
    }

    private void loadChildren(ITree<DataSchemeNode> rootTree) {
        if (this.needLoad(rootTree)) {
            List<ITree<DataSchemeNode>> childTree;
            int interestType = NodeType.getInterestTypeByLeft((NodeType)NodeType.FIELD_ZB);
            if ((interestType & NodeType.DIM.getValue()) != 0) {
                interestType ^= NodeType.DIM.getValue();
            }
            if (!CollectionUtils.isEmpty(childTree = this.iDesignDataSchemeTreeService.getChildTree((DataSchemeNode)rootTree.getData(), interestType, null))) {
                rootTree.setChildren(childTree);
                rootTree.setLeaf(false);
                childTree.forEach(x -> {
                    x.setLeaf(true);
                    this.loadChildren((ITree<DataSchemeNode>)x);
                });
            }
        }
    }

    private boolean needLoad(ITree<DataSchemeNode> data) {
        if (null != data && null == data.getChildren()) {
            return null != data.getData() && NodeType.GROUP.getValue() == ((DataSchemeNode)data.getData()).getType();
        }
        return false;
    }

    @PostMapping(value={"child/path/get"})
    public Set<String> getChildPathKey(@RequestBody DataSchemeTreeQuery<DataSchemeNodeDTO> param) {
        String dataSchemeKey = param.getDataSchemeKey();
        DataSchemeNodeDTO dataSchemeNode = param.getDataSchemeNode();
        String key = dataSchemeNode.getKey();
        HashSet<String> res = new HashSet<String>();
        this.loadChildren(res, key);
        return res;
    }

    private void loadChildren(Set<String> res, String key) {
        res.add(key);
        List dataTableByGroup = this.designDataSchemeService.getDataTableByGroup(key);
        dataTableByGroup.forEach(x -> res.add(x.getKey()));
        List groupByScheme = this.designDataSchemeService.getDataGroupByParent(key);
        for (DesignDataGroup designDataGroup : groupByScheme) {
            this.loadChildren(res, designDataGroup.getKey());
        }
    }

    private void loadChildren(List<DesignDataGroup> groupByScheme, Set<String> res, String parent) {
        for (DesignDataGroup designDataGroup : groupByScheme) {
            if (parent == null) {
                if (designDataGroup.getParentKey() != null) continue;
                res.add(designDataGroup.getKey());
                continue;
            }
            if (!parent.equals(designDataGroup.getParentKey())) continue;
            res.add(designDataGroup.getKey());
        }
    }
}

