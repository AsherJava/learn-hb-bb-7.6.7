/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.TreeSearchQuery
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeNode
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeTreeService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.datascheme.api.type.DataSchemeType
 *  com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO
 *  com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery
 *  com.jiuqi.nr.task.api.tree.TreeData
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.task.form.controller;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.TreeSearchQuery;
import com.jiuqi.nr.datascheme.api.core.DataSchemeNode;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeTreeService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.api.type.DataSchemeType;
import com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery;
import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nr.task.form.controller.vo.DataSchemeTreeData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v2/datascheme/"})
@Api(value="\u4efb\u52a1\u8bbe\u8ba12.0\u2014\u2014\u2014\u2014\u6570\u636e\u65b9\u6848\u6811\u5f62Controller")
public class DataSchemeTreeController {
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IDataSchemeTreeService<DataSchemeNode> treeService;

    @ApiOperation(value="\u67e5\u8be2\u6570\u636e\u65b9\u6848\u6811")
    @PostMapping(value={"tree/root"})
    public List<UITreeNode<DataSchemeTreeData>> querySchemeTreeRoot(@RequestBody DataSchemeTreeQuery<DataSchemeNodeDTO> param) {
        List schemeGroupRootTree = this.treeService.getSchemeGroupRootTree(NodeType.SCHEME_GROUP.getValue() | NodeType.SCHEME.getValue(), this.getDesSchemeTypeFilter(param.getDataSchemeType()));
        if (schemeGroupRootTree.size() > 0) {
            ITree dataSchemeNodeITree = (ITree)schemeGroupRootTree.get(0);
            dataSchemeNodeITree.setExpanded(false);
            dataSchemeNodeITree.setSelected(false);
        }
        return schemeGroupRootTree.stream().map(this::convert).collect(Collectors.toList());
    }

    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u65b9\u6848\u67d0\u4e2aKEY\u4e0b\u6240\u6709\u76f4\u63a5\u5b50\u8282\u70b9")
    @PostMapping(value={"tree/children"})
    public List<UITreeNode<DataSchemeTreeData>> querySchemeTreeChildren(@RequestBody DataSchemeTreeQuery<DataSchemeNodeDTO> param) {
        DataSchemeNodeDTO parent = (DataSchemeNodeDTO)param.getDataSchemeNode();
        List schemeGroupChildTree = this.treeService.getSchemeGroupChildTree((INode)parent, NodeType.SCHEME_GROUP.getValue() | NodeType.SCHEME.getValue(), this.getDesSchemeTypeFilter(param.getDataSchemeType()));
        return schemeGroupChildTree.stream().map(this::convert).collect(Collectors.toList());
    }

    @ApiOperation(value="\u83b7\u53d6\u6240\u6709\u6ee1\u8db3\u6761\u4ef6\u7684\u6570\u636e\u65b9\u6848\u660e\u7ec6\u8282\u70b9")
    @PostMapping(value={"tree/filter"})
    public List<DataSchemeNode> filterSchemeTree(@RequestBody DataSchemeTreeQuery<DataSchemeNodeDTO> param) {
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
            return this.treeService.search(searchQuery).stream().filter(n -> this.getDesSchemeTypeFilter(param.getDataSchemeType()).test(n)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u65b9\u6848\u67d0\u4e2a\u8282\u70b9\u7684\u8def\u5f84")
    @PostMapping(value={"tree/path"})
    public List<UITreeNode<DataSchemeTreeData>> querySchemeTreePath(@RequestBody DataSchemeTreeQuery<DataSchemeNodeDTO> param) {
        DataSchemeNodeDTO node = (DataSchemeNodeDTO)param.getDataSchemeNode();
        if (node == null || "00000000-0000-0000-0000-000000000000".equals(node.getKey())) {
            return this.querySchemeTreeRoot(param);
        }
        List specifiedTree = this.treeService.getSchemeGroupSpecifiedTree((INode)node, NodeType.SCHEME_GROUP.getValue() | NodeType.SCHEME.getValue(), this.getDesSchemeTypeFilter(param.getDataSchemeType()));
        return specifiedTree.stream().map(this::convert).collect(Collectors.toList());
    }

    private UITreeNode<DataSchemeTreeData> convert(ITree<DataSchemeNode> dataSchemeNodeITree) {
        UITreeNode treeNode = new UITreeNode();
        if (dataSchemeNodeITree.getData() != null) {
            treeNode.setData((TreeData)new DataSchemeTreeData((DataSchemeNode)dataSchemeNodeITree.getData()));
        }
        treeNode.setKey(dataSchemeNodeITree.getKey());
        if (dataSchemeNodeITree.getParent() != null) {
            treeNode.setParent(dataSchemeNodeITree.getParent().getKey());
        }
        treeNode.setTitle(dataSchemeNodeITree.getTitle());
        treeNode.setExpand(dataSchemeNodeITree.isExpanded());
        treeNode.setSelected(dataSchemeNodeITree.isSelected());
        treeNode.setChecked(dataSchemeNodeITree.isChecked());
        treeNode.setDisabled(dataSchemeNodeITree.isDisabled());
        treeNode.setIcon(dataSchemeNodeITree.getIcons());
        treeNode.setLeaf(dataSchemeNodeITree.isLeaf());
        List children = dataSchemeNodeITree.getChildren();
        if (children != null && children.size() > 0) {
            ArrayList<UITreeNode<DataSchemeTreeData>> childList = new ArrayList<UITreeNode<DataSchemeTreeData>>(dataSchemeNodeITree.getChildCount());
            for (ITree child : dataSchemeNodeITree.getChildren()) {
                childList.add(this.convert((ITree<DataSchemeNode>)child));
            }
            treeNode.setChildren(childList);
        }
        if (!dataSchemeNodeITree.isLeaf()) {
            treeNode.setDisabled(true);
        }
        return treeNode;
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
}

