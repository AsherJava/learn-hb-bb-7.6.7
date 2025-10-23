/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IMetaGroup
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.task.api.dto.IFormTreeNode
 *  com.jiuqi.nr.task.api.dto.IFormTreeNode$NodeType
 *  com.jiuqi.nr.task.api.tree.TreeConfig
 *  com.jiuqi.nr.task.api.tree.TreeData
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 *  com.jiuqi.xlib.utils.CollectionUtils
 *  com.jiuqi.xlib.utils.StringUtil
 */
package com.jiuqi.nr.print.helper;

import com.jiuqi.np.definition.facade.IMetaGroup;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.task.api.dto.IFormTreeNode;
import com.jiuqi.nr.task.api.tree.TreeConfig;
import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.xlib.utils.CollectionUtils;
import com.jiuqi.xlib.utils.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;

public class TreeBuildHelper {
    public List<UITreeNode<IFormTreeNode>> buildTree(List<UITreeNode<IFormTreeNode>> nodes, TreeConfig treeConfig) {
        if (CollectionUtils.isEmpty(nodes)) {
            return Collections.emptyList();
        }
        LinkedHashMap<String, UITreeNode<IFormTreeNode>> treeMapTemp = new LinkedHashMap<String, UITreeNode<IFormTreeNode>>();
        nodes.stream().sorted(treeConfig.getComparator()).forEachOrdered(e -> treeMapTemp.put(null != e.getKey() ? e.getKey() : "root", (UITreeNode<IFormTreeNode>)e));
        UITreeNode rootNode = new UITreeNode();
        for (UITreeNode node : treeMapTemp.values()) {
            if (null == node) continue;
            String parent = node.getParent();
            if (treeMapTemp.containsKey(parent)) {
                UITreeNode parentNode = (UITreeNode)treeMapTemp.get(parent);
                if (null == parentNode) continue;
                this.addChild((UITreeNode<IFormTreeNode>)parentNode, (UITreeNode<IFormTreeNode>)node, treeConfig, treeMapTemp);
                continue;
            }
            this.addChild((UITreeNode<IFormTreeNode>)rootNode, (UITreeNode<IFormTreeNode>)node);
            if (!treeConfig.isSelected(node.getKey())) continue;
            node.setSelected(true);
        }
        return rootNode.getChildren();
    }

    public UITreeNode<IFormTreeNode> buildNode(IMetaGroup group) {
        UITreeNode treeNode = new UITreeNode();
        treeNode.setKey(group.getKey());
        treeNode.setTitle(group.getTitle());
        if (StringUtil.isEmpty((String)group.getParentKey())) {
            treeNode.setParent("formRootNode");
        } else {
            treeNode.setParent(group.getParentKey());
        }
        treeNode.setOrder(group.getOrder());
        treeNode.setIcon("icon-folder");
        IFormTreeNode formTreeNode = new IFormTreeNode();
        formTreeNode.setType(IFormTreeNode.NodeType.FORMGROUP);
        treeNode.setData((TreeData)formTreeNode);
        return treeNode;
    }

    public UITreeNode<IFormTreeNode> buildNode(DesignFormDefine item, String parentKey) {
        UITreeNode treeNode = new UITreeNode();
        treeNode.setKey(item.getKey());
        treeNode.setParent(parentKey);
        treeNode.setTitle(item.getTitle());
        treeNode.setOrder(item.getOrder());
        treeNode.setIcon(this.getFormIcon(item.getFormType()));
        IFormTreeNode formTreeNode = new IFormTreeNode();
        formTreeNode.setType(IFormTreeNode.NodeType.FORM);
        formTreeNode.setCode(item.getFormCode());
        treeNode.setData((TreeData)formTreeNode);
        return treeNode;
    }

    private void addChild(UITreeNode<IFormTreeNode> parentNode, UITreeNode<IFormTreeNode> node, TreeConfig treeConfig, Map<String, UITreeNode<IFormTreeNode>> treeMapTemp) {
        this.addChild(parentNode, node);
        if (treeConfig.isSelected(node.getKey())) {
            this.nodeSelect(node, treeMapTemp);
        }
        parentNode.setLeaf(false);
    }

    private void addChild(UITreeNode<IFormTreeNode> parentNode, UITreeNode<IFormTreeNode> node) {
        ArrayList<UITreeNode<IFormTreeNode>> children = parentNode.getChildren();
        if (null == children) {
            children = new ArrayList<UITreeNode<IFormTreeNode>>();
            children.add(node);
            parentNode.setChildren(children);
        } else {
            children.add(node);
            children.sort(new TreeConfig().getComparator());
        }
    }

    private void nodeSelect(UITreeNode<IFormTreeNode> node, Map<String, UITreeNode<IFormTreeNode>> treeMapTemp) {
        node.setSelected(true);
        this.nodeExpand(node, treeMapTemp);
    }

    private void nodeExpand(UITreeNode<IFormTreeNode> node, Map<String, UITreeNode<IFormTreeNode>> treeMapTemp) {
        node.setExpand(true);
        String parent = node.getParent();
        UITreeNode<IFormTreeNode> parentNode = new UITreeNode<IFormTreeNode>();
        if (treeMapTemp.containsKey(parent)) {
            parentNode = treeMapTemp.get(parent);
            parentNode.setExpand(true);
        }
        if (parentNode != null && StringUtils.hasText(parentNode.getParent())) {
            this.nodeExpand(parentNode, treeMapTemp);
        }
    }

    private String getFormIcon(FormType formType) {
        if (formType == FormType.FORM_TYPE_FLOAT) {
            return "icon-J_GJ_A_NR_fudongbiao";
        }
        if (formType == FormType.FORM_TYPE_SURVEY) {
            return "icon-J_GJ_A_NR_wenjuan";
        }
        if (formType == FormType.FORM_TYPE_INSERTANALYSIS) {
            return "icon-J_GJ_A_NR_fenxibiao";
        }
        if (formType == FormType.FORM_TYPE_ACCOUNT) {
            return "icon-J_GJ_A_NR_taizhang";
        }
        if (formType == FormType.FORM_TYPE_NEWFMDM) {
            return "icon-J_GJ_A_NR_fengmiandaima";
        }
        return "icon-J_GJ_A_NR_gudingbiao";
    }
}

