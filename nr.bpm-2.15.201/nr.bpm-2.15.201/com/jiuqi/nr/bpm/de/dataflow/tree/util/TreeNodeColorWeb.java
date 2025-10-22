/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  io.swagger.annotations.ApiOperation
 *  io.swagger.annotations.ApiParam
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.bpm.de.dataflow.tree.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.bpm.de.dataflow.tree.NodeInfo;
import com.jiuqi.nr.bpm.de.dataflow.tree.TreeNodeColorInfo;
import com.jiuqi.nr.bpm.de.dataflow.tree.TreeNodeData;
import com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeNodeIconColorUtil;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/tree-node-color"})
public class TreeNodeColorWeb {
    private static final Logger logger = LoggerFactory.getLogger(TreeNodeColorWeb.class);
    @Autowired
    private TreeNodeIconColorUtil treeNodeIconColorUtil;
    @Autowired
    private INvwaSystemOptionService nvwaSystemOptionService;

    @GetMapping(value={"/query"})
    @ApiOperation(value="\u67e5\u8be2\u6811\u5f62\u8282\u70b9\u989c\u8272")
    public String getUserActions(@ApiParam(value="\u7cfb\u7edf\u6761\u76eekey", required=true) String key) {
        String userActionStr = null;
        TreeNodeData treeNodeData = new TreeNodeData();
        try {
            String value = this.nvwaSystemOptionService.findValueById("TREE-NODE-ICON-COLOR");
            List<TreeNodeColorInfo> defaultTreeNodeColor = this.defaultTreeNodeColorStr().getInfoItems();
            ObjectMapper objectMapper = new ObjectMapper();
            if (value != null) {
                NodeInfo nodeInfo = (NodeInfo)objectMapper.readValue(value, NodeInfo.class);
                List<TreeNodeColorInfo> treeNodeColorInfos = nodeInfo.getInfoItems();
                List<TreeNodeColorInfo> addOrUpdate = this.addOrUpdate(defaultTreeNodeColor, treeNodeColorInfos);
                nodeInfo.setInfoItems(addOrUpdate);
                String valueStr = objectMapper.writeValueAsString((Object)nodeInfo);
                treeNodeData.setValue(valueStr);
                nodeInfo.setInfoItems(defaultTreeNodeColor);
                String defaultJson = objectMapper.writeValueAsString((Object)nodeInfo);
                treeNodeData.setDefaultValue(defaultJson);
            }
            userActionStr = objectMapper.writeValueAsString((Object)treeNodeData);
            return userActionStr;
        }
        catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return userActionStr;
        }
    }

    private NodeInfo defaultTreeNodeColorStr() {
        List<ISystemOptionItem> optionItems = this.treeNodeIconColorUtil.getOptionItems();
        String defaultValue = optionItems.get(0).getDefaultValue();
        ObjectMapper objectMapper = new ObjectMapper();
        NodeInfo nodeinfo = null;
        try {
            nodeinfo = (NodeInfo)objectMapper.readValue(defaultValue, NodeInfo.class);
            return nodeinfo;
        }
        catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return nodeinfo;
        }
    }

    private List<TreeNodeColorInfo> addOrUpdate(List<TreeNodeColorInfo> defaultTreeNodeColor, List<TreeNodeColorInfo> treeNodeColorInfos) {
        ArrayList<TreeNodeColorInfo> add = new ArrayList<TreeNodeColorInfo>();
        TreeNodeColorInfo addTreeItem = null;
        Map<String, TreeNodeColorInfo> collect = treeNodeColorInfos.stream().distinct().collect(Collectors.toMap(TreeNodeColorInfo::getState, e -> e, (e1, e2) -> e1));
        Set treeNodekey = treeNodeColorInfos.stream().map(e -> e.getState()).collect(Collectors.toSet());
        for (TreeNodeColorInfo defaultTree : defaultTreeNodeColor) {
            if (!treeNodekey.contains(defaultTree.getState())) {
                add.add(defaultTree);
                continue;
            }
            addTreeItem = new TreeNodeColorInfo();
            addTreeItem.setColor(collect.get(defaultTree.getState()).getColor());
            addTreeItem.setIcon(collect.get(defaultTree.getState()).getIcon());
            addTreeItem.setId(collect.get(defaultTree.getState()).getId());
            addTreeItem.setState(defaultTree.getState());
            addTreeItem.setStateName(defaultTree.getStateName());
            add.add(addTreeItem);
        }
        return add;
    }
}

