/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IMetaGroup
 *  com.jiuqi.np.definition.facade.IMetaItem
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.task.api.tree.TreeConfig
 *  com.jiuqi.nr.task.api.tree.TreeData
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 */
package com.jiuqi.nr.task.i18n.service.impl;

import com.jiuqi.np.definition.facade.IMetaGroup;
import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.task.api.tree.TreeConfig;
import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nr.task.i18n.bean.I18nBaseObj;
import com.jiuqi.nr.task.i18n.bean.vo.I18nTreeLoactedVO;
import com.jiuqi.nr.task.i18n.bean.vo.I18nTreeSearchVO;
import com.jiuqi.nr.task.i18n.common.TreeBuilderHelper;
import com.jiuqi.nr.task.i18n.service.I18nTreeService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class I18nTreeServiceImpl
implements I18nTreeService {
    @Autowired
    private IDesignTimeViewController designTimeViewController;

    @Override
    public List<UITreeNode<TreeData>> formGroupTreeLoad(String formSchemeKey) {
        List<UITreeNode<TreeData>> nodeList = this.buildTreeNode(formSchemeKey, true);
        List<UITreeNode<TreeData>> tree = this.buildTree(nodeList, new TreeConfig());
        tree.get(0).setExpand(true);
        tree.get(0).setSelected(true);
        return tree;
    }

    @Override
    public List<UITreeNode<TreeData>> formTreeLoad(String formSchemeKey) {
        List<UITreeNode<TreeData>> nodeList = this.buildTreeNode(formSchemeKey, false);
        List<UITreeNode<TreeData>> tree = this.buildTree(nodeList, new TreeConfig());
        tree.get(0).setExpand(true);
        tree.get(0).setSelected(true);
        return tree;
    }

    @Override
    public List<I18nBaseObj> treeSearch(I18nTreeSearchVO treeSearchVO) {
        ArrayList<I18nBaseObj> result = new ArrayList<I18nBaseObj>();
        String formSchemeKey = treeSearchVO.getFormSchemeKey();
        String keyWords = treeSearchVO.getKeyWords();
        if (!StringUtils.hasText(formSchemeKey)) {
            return new ArrayList<I18nBaseObj>();
        }
        Set<Character> characters = this.splitToSet(keyWords);
        ArrayList defines = new ArrayList();
        if (treeSearchVO.isGroupTree()) {
            defines.addAll(this.designTimeViewController.listFormGroupByFormScheme(formSchemeKey));
        } else {
            defines.addAll(this.designTimeViewController.listFormByFormScheme(formSchemeKey));
        }
        for (IMetaItem define : defines) {
            if (!this.contains(characters, define.getTitle())) continue;
            result.add(new I18nBaseObj(define.getKey(), define.getTitle()));
        }
        return result;
    }

    private Set<Character> splitToSet(String keyWords) {
        return keyWords.chars().mapToObj(c -> Character.valueOf((char)c)).collect(Collectors.toSet());
    }

    private boolean contains(Set<Character> characterSet, String title) {
        return title.trim().chars().anyMatch(x -> characterSet.contains(Character.valueOf((char)x)));
    }

    @Override
    public List<UITreeNode<TreeData>> treeLocated(I18nTreeLoactedVO loactedVO) {
        List<UITreeNode<TreeData>> nodeList = this.buildTreeNode(loactedVO.getFormSchemeKey(), loactedVO.isGroupTree());
        TreeConfig config = new TreeConfig();
        config.selected(new String[]{loactedVO.getNodeKey()});
        return this.buildTree(nodeList, config);
    }

    private List<UITreeNode<TreeData>> buildTreeNode(String formSchemeKey, boolean isGroupTree) {
        ArrayList<UITreeNode<TreeData>> nodeList = new ArrayList<UITreeNode<TreeData>>();
        this.buildRootNode(nodeList, isGroupTree);
        this.buildTreeNode(nodeList, formSchemeKey, isGroupTree);
        return nodeList;
    }

    private void buildRootNode(List<UITreeNode<TreeData>> nodeList, boolean isGroupTree) {
        if (isGroupTree) {
            UITreeNode groupRoot = new UITreeNode();
            groupRoot.setKey("I18N_FORM_GROUP_TREE_ROOT");
            groupRoot.setParent(null);
            groupRoot.setTitle("\u6240\u6709\u62a5\u8868");
            groupRoot.setOrder("0");
            nodeList.add((UITreeNode<TreeData>)groupRoot);
        } else {
            UITreeNode rootNode = new UITreeNode();
            rootNode.setKey("I18N_FORM_TREE_ROOT");
            rootNode.setParent(null);
            rootNode.setTitle("\u5168\u90e8");
            rootNode.setOrder("0");
            nodeList.add((UITreeNode<TreeData>)rootNode);
        }
    }

    private List<UITreeNode<TreeData>> buildTree(List<UITreeNode<TreeData>> nodeList, TreeConfig treeConfig) {
        if (!CollectionUtils.isEmpty(nodeList)) {
            return TreeBuilderHelper.buildTree(nodeList, treeConfig);
        }
        return Collections.emptyList();
    }

    private void buildTreeNode(List<UITreeNode<TreeData>> nodeList, String formSchemeKey, boolean isGroupTree) {
        List formGroups = this.designTimeViewController.listFormGroupByFormScheme(formSchemeKey);
        if (!isGroupTree) {
            UITreeNode formulaBetweenFormNode = new UITreeNode();
            formulaBetweenFormNode.setKey("I18N_FORMULA_BETWEEN_FORM_NODE");
            formulaBetweenFormNode.setParent("I18N_FORM_TREE_ROOT");
            formulaBetweenFormNode.setTitle("\u8868\u95f4\u516c\u5f0f");
            formulaBetweenFormNode.setOrder("0");
            nodeList.add((UITreeNode<TreeData>)formulaBetweenFormNode);
        }
        for (DesignFormGroupDefine formGroup : formGroups) {
            nodeList.add(TreeBuilderHelper.buildNode((IMetaGroup)formGroup, isGroupTree));
            if (isGroupTree) continue;
            List formDefines = this.designTimeViewController.listFormByGroup(formGroup.getKey());
            for (DesignFormDefine formDefine : formDefines) {
                nodeList.add(TreeBuilderHelper.buildNode((IMetaItem)formDefine, formGroup.getKey()));
            }
        }
    }
}

