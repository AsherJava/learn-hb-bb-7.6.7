/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.exception.BudgetException
 *  com.jiuqi.budget.common.utils.MapSizeUtil
 */
package com.jiuqi.budget.components;

import com.jiuqi.budget.common.exception.BudgetException;
import com.jiuqi.budget.common.utils.MapSizeUtil;
import com.jiuqi.budget.domain.BaseTreeItemVO;
import com.jiuqi.budget.domain.NRTreeItemContentProvider;
import com.jiuqi.budget.domain.TreeItemContentProvider;
import com.jiuqi.budget.init.BaseDO;
import com.jiuqi.budget.iview.IViewTreeItem;
import com.jiuqi.budget.iview.IViewTreeItemFactory;
import com.jiuqi.budget.iview.LightViewTreeItem;
import com.jiuqi.budget.iview.LightWightTreeItemVO;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class TreeUtil {
    private TreeUtil() {
        throw new BudgetException("Utility class");
    }

    public static <T extends BaseDO> List<LightWightTreeItemVO> buildFullLightTree(List<T> dataList) {
        return TreeUtil.buildFullLightTree(dataList, null);
    }

    public static <T extends BaseDO> List<LightViewTreeItem> buildIViewTree(List<T> dataList) {
        LightViewTreeItem treeItem;
        if (dataList == null || dataList.isEmpty()) {
            return Collections.emptyList();
        }
        int mapSize = MapSizeUtil.getHashMapInitCapacities((int)dataList.size());
        HashMap<String, LightViewTreeItem> itemMap = new HashMap<String, LightViewTreeItem>(mapSize);
        ArrayList<LightViewTreeItem> roots = new ArrayList<LightViewTreeItem>();
        for (BaseDO t : dataList) {
            treeItem = new LightViewTreeItem();
            treeItem.setId(t.getId());
            treeItem.setCode(t.getCode());
            treeItem.setName(t.getName());
            treeItem.setTitle(t.getName().concat("\uff08").concat(t.getCode()).concat("\uff09"));
            treeItem.setDataType(t.getClass().getSimpleName());
            itemMap.put(t.getId(), treeItem);
            if (!TreeUtil.isRootNode(t)) continue;
            roots.add(treeItem);
        }
        for (BaseDO t : dataList) {
            if (TreeUtil.isRootNode(t)) continue;
            treeItem = (LightViewTreeItem)itemMap.get(t.getId());
            if (!itemMap.containsKey(t.getParentId())) continue;
            LightViewTreeItem parentItem = (LightViewTreeItem)itemMap.get(t.getParentId());
            parentItem.addChild(treeItem);
        }
        return roots;
    }

    public static <T extends BaseDO, E> List<IViewTreeItem<E>> buildIViewTree(List<T> dataList, IViewTreeItemFactory<T, E> factory) {
        if (dataList == null || dataList.isEmpty()) {
            return Collections.emptyList();
        }
        int mapSize = MapSizeUtil.getHashMapInitCapacities((int)dataList.size());
        HashMap<String, IViewTreeItem<E>> itemMap = new HashMap<String, IViewTreeItem<E>>(mapSize);
        ArrayList<IViewTreeItem<IViewTreeItem<E>>> roots = new ArrayList<IViewTreeItem<IViewTreeItem<E>>>();
        for (BaseDO t : dataList) {
            IViewTreeItem<E> iViewTreeItem = factory.createIViewTreeItem(t);
            itemMap.put(t.getId(), iViewTreeItem);
            if (!TreeUtil.isRootNode(t)) continue;
            roots.add(iViewTreeItem);
        }
        for (BaseDO t : dataList) {
            if (TreeUtil.isRootNode(t)) continue;
            IViewTreeItem treeItem = (IViewTreeItem)itemMap.get(t.getId());
            if (!itemMap.containsKey(t.getParentId())) continue;
            IViewTreeItem parentItem = (IViewTreeItem)itemMap.get(t.getParentId());
            parentItem.addChild(treeItem);
        }
        return roots;
    }

    public static <T extends BaseDO, E extends INode> List<ITree<E>> buildNRLightTree(List<T> dataList, NRTreeItemContentProvider<T, E> provider) {
        if (dataList == null || dataList.isEmpty()) {
            return Collections.emptyList();
        }
        int mapSize = MapSizeUtil.getHashMapInitCapacities((int)dataList.size());
        HashMap<String, ITree<INode>> itemMap = new HashMap<String, ITree<INode>>(mapSize);
        ArrayList<ITree<ITree<INode>>> roots = new ArrayList<ITree<ITree<INode>>>();
        for (BaseDO t : dataList) {
            INode data = (INode)provider.getData(t);
            ITree<INode> treeItemVO = new ITree<INode>(data);
            treeItemVO.setLeaf(true);
            treeItemVO.setIcons(provider.getIcons(t));
            itemMap.put(t.getId(), treeItemVO);
            if (!TreeUtil.isRootNode(t)) continue;
            roots.add(treeItemVO);
        }
        for (BaseDO t : dataList) {
            if (TreeUtil.isRootNode(t)) continue;
            ITree tree = (ITree)itemMap.get(t.getId());
            if (!itemMap.containsKey(t.getParentId())) continue;
            ITree parent = (ITree)itemMap.get(t.getParentId());
            parent.appendChild(tree);
            parent.setLeaf(false);
        }
        return roots;
    }

    public static <T extends BaseDO> List<LightWightTreeItemVO> buildFullLightTree(List<T> dataList, TreeItemContentProvider<T> provider) {
        if (dataList == null || dataList.isEmpty()) {
            return Collections.emptyList();
        }
        int mapSize = MapSizeUtil.getHashMapInitCapacities((int)dataList.size());
        HashMap<String, LightWightTreeItemVO> itemMap = new HashMap<String, LightWightTreeItemVO>(mapSize);
        ArrayList<LightWightTreeItemVO> roots = new ArrayList<LightWightTreeItemVO>();
        for (BaseDO t : dataList) {
            LightWightTreeItemVO treeItemVO = new LightWightTreeItemVO();
            if (provider != null) {
                treeItemVO.setId(t.getId());
                treeItemVO.setText(provider.getText(t));
                treeItemVO.setAttributes(provider.getAttributes(t));
            } else {
                TreeUtil.setDefaultIdAndTitle(treeItemVO, t);
            }
            treeItemVO.setDataType(t.getClass().getSimpleName());
            treeItemVO.setBizCode(t.getCode());
            itemMap.put(t.getId(), treeItemVO);
            if (!TreeUtil.isRootNode(t)) continue;
            roots.add(treeItemVO);
        }
        for (BaseDO t : dataList) {
            if (TreeUtil.isRootNode(t)) continue;
            LightWightTreeItemVO lightWightTreeItemVO = (LightWightTreeItemVO)itemMap.get(t.getId());
            if (!itemMap.containsKey(t.getParentId())) continue;
            LightWightTreeItemVO parent = (LightWightTreeItemVO)itemMap.get(t.getParentId());
            parent.addChild(lightWightTreeItemVO);
        }
        return roots;
    }

    public static <T extends BaseDO> void setDefaultIdAndTitle(BaseTreeItemVO item, T data) {
        item.setId(data.getCode());
        item.setText(data.getCode().concat(" ").concat(data.getName()));
    }

    public static <T extends BaseDO> void setDefaultIdAndTitle(LightWightTreeItemVO item, T data) {
        item.setId(data.getId());
        item.setText(data.getName().concat("\uff08").concat(data.getCode()).concat("\uff09"));
    }

    public static <T extends BaseDO> void setDefaultIdAndTitle(ITree item, T data) {
        item.setKey(data.getId());
        item.setTitle(data.getName().concat("\uff08").concat(data.getCode()).concat("\uff09"));
    }

    public static <T extends BaseDO> boolean isRootNode(T t) {
        return "00000000-0000-0000-0000-000000000000".equals(t.getParentId()) || t.getParentId() == null || t.getId().equals(t.getParentId());
    }

    public static LightWightTreeItemVO createDefaultItem(BaseDO itemData) {
        LightWightTreeItemVO treeItemVO = new LightWightTreeItemVO();
        treeItemVO.setDataType(itemData.getClass().getSimpleName());
        TreeUtil.setDefaultIdAndTitle(treeItemVO, itemData);
        return treeItemVO;
    }
}

