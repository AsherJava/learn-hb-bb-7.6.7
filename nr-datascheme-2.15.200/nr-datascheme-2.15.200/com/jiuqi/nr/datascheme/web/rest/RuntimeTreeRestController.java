/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  com.jiuqi.nr.datascheme.api.type.DataSchemeType
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.datascheme.web.rest;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.api.type.DataSchemeType;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO;
import com.jiuqi.nr.datascheme.internal.service.DataSchemeService;
import com.jiuqi.nr.datascheme.web.param.TreeNodeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/datascheme/"})
@Api(tags={"\u6570\u636e\u65b9\u6848\u6811\u5f62\u7ed3\u6784\u670d\u52a1"})
public class RuntimeTreeRestController {
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DataSchemeService dataSchemeService;

    private Map<String, ITree<TreeNodeVO>> buildTree(List<ITree<TreeNodeVO>> allTreeNodes, Predicate<? super ITree<TreeNodeVO>> filter, Predicate<ITree<TreeNodeVO>> disabled) {
        HashMap iTreeNodeGroup = new HashMap();
        allTreeNodes.stream().filter(filter).forEach(node -> {
            node.setDisabled(disabled.test((ITree<TreeNodeVO>)node));
            iTreeNodeGroup.computeIfAbsent(((TreeNodeVO)node.getData()).getParentKey(), k -> new ArrayList()).add(node);
        });
        HashMap<String, ITree<TreeNodeVO>> allTreeNodeMap = new HashMap<String, ITree<TreeNodeVO>>();
        for (ITree<TreeNodeVO> iTreeNode : allTreeNodes) {
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

    private void localNode(Map<String, ITree<TreeNodeVO>> allTreeNodeMap, ITree<TreeNodeVO> rootNode, String localKey) {
        ITree<TreeNodeVO> node;
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
                expandedNodeKey = ((TreeNodeVO)node.getData()).getParentKey();
                node.setExpanded(true);
                continue;
            }
            expandedNodeKey = null;
        }
    }

    private ITree<TreeNodeVO> toNode(DataGroup group) {
        return new ITree((INode)new TreeNodeVO(group));
    }

    private ITree<TreeNodeVO> toNode(DataScheme scheme) {
        return new ITree((INode)new TreeNodeVO(scheme));
    }

    private TreeNodeVO getRoot() {
        TreeNodeVO node = new TreeNodeVO();
        node.setKey("00000000-0000-0000-0000-000000000000");
        node.setType(NodeType.SCHEME_GROUP.getValue());
        node.setCode("root");
        node.setTitle("\u5168\u90e8\u6570\u636e\u65b9\u6848");
        return node;
    }

    @ApiOperation(value="\u8fd0\u884c\u671f\u62a5\u8868\u6570\u636e\u65b9\u6848\u5b8c\u6574\u6811\u5f62")
    @GetMapping(value={"r-tree/nr-scheme/full", "r-tree/nr-scheme/full/{localKey}"})
    public List<ITree<TreeNodeVO>> fullNrSchemeTree(@PathVariable(required=false) String localKey) {
        ArrayList<ITree<TreeNodeVO>> allTreeNodes = new ArrayList<ITree<TreeNodeVO>>();
        ITree rootNode = new ITree((INode)this.getRoot());
        rootNode.setExpanded(true);
        allTreeNodes.add(rootNode);
        List allGroups = this.designDataSchemeService.getDataGroupByKind(DataGroupKind.SCHEME_GROUP.getValue()).stream().map(this::toNode).collect(Collectors.toList());
        allTreeNodes.addAll(allGroups);
        List allSchemes = this.runtimeDataSchemeService.getAllDataScheme().stream().filter(s -> DataSchemeType.NR == s.getType()).map(this::toNode).collect(Collectors.toList());
        allTreeNodes.addAll(allSchemes);
        Map<String, ITree<TreeNodeVO>> allTreeNodeMap = this.buildTree(allTreeNodes, g -> true, g -> false);
        this.localNode(allTreeNodeMap, (ITree<TreeNodeVO>)rootNode, localKey);
        return Collections.singletonList(rootNode);
    }

    @ApiOperation(value="\u8fd0\u884c\u671f\u62a5\u8868\u6570\u636e\u65b9\u6848\u641c\u7d22")
    @PostMapping(value={"r-tree/nr-scheme/search"})
    public List<TreeNodeVO> searchNrScheme(@RequestBody String keyword) {
        List<DataSchemeDTO> dataSchemes = this.dataSchemeService.searchByKeyword(keyword);
        return dataSchemes.stream().filter(s -> DataSchemeType.NR == s.getType()).map(TreeNodeVO::new).collect(Collectors.toList());
    }
}

