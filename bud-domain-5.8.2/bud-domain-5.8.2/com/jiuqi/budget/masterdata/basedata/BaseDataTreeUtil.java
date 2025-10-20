/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.utils.MapSizeUtil
 */
package com.jiuqi.budget.masterdata.basedata;

import com.jiuqi.budget.autoconfigure.DimensionConst;
import com.jiuqi.budget.common.utils.MapSizeUtil;
import com.jiuqi.budget.domain.BaseDataNode;
import com.jiuqi.budget.domain.NRTreeItemContentProvider;
import com.jiuqi.budget.domain.TreeItemContentProvider;
import com.jiuqi.budget.iview.LightWightTreeItemVO;
import com.jiuqi.budget.masterdata.intf.FBaseDataObj;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class BaseDataTreeUtil {
    public static <T extends BaseDataNode> List<ITree<T>> buildNRLightTree(List<FBaseDataObj> dataList, NRTreeItemContentProvider<FBaseDataObj, T> provider) {
        if (dataList == null || dataList.isEmpty()) {
            return Collections.emptyList();
        }
        int mapSize = MapSizeUtil.getHashMapInitCapacities((int)dataList.size());
        HashMap<String, ITree<INode>> itemMap = new HashMap<String, ITree<INode>>(mapSize);
        ArrayList<ITree<T>> roots = new ArrayList<ITree<T>>();
        for (FBaseDataObj baseDataObj : dataList) {
            ITree<INode> treeItemVO = new ITree<INode>((INode)provider.getData(baseDataObj));
            treeItemVO.setIcons(provider.getIcons(baseDataObj));
            treeItemVO.setLeaf(true);
            treeItemVO.setSelected(provider.needSelect(baseDataObj));
            itemMap.put(baseDataObj.getCode(), treeItemVO);
            if (!BaseDataTreeUtil.isRootNode(baseDataObj)) continue;
            roots.add(treeItemVO);
        }
        for (FBaseDataObj baseDataObj : dataList) {
            if (BaseDataTreeUtil.isRootNode(baseDataObj)) continue;
            ITree iTree = (ITree)itemMap.get(baseDataObj.getCode());
            ITree parent = (ITree)itemMap.get(baseDataObj.getParent());
            if (parent != null) {
                parent.setLeaf(false);
                parent.appendChild(iTree);
                if (!iTree.isSelected() || parent.isExpanded()) continue;
                parent.setExpanded(true);
                continue;
            }
            roots.add(iTree);
        }
        return roots;
    }

    public static List<LightWightTreeItemVO> buildFullLightTree(List<FBaseDataObj> dataList) {
        return BaseDataTreeUtil.buildFullLightTree(dataList, null);
    }

    public static LightWightTreeItemVO createTreeItem(FBaseDataObj data, TreeItemContentProvider<FBaseDataObj> provider) {
        LightWightTreeItemVO treeItemVO = new LightWightTreeItemVO();
        treeItemVO.setId(data.getKey());
        treeItemVO.setBizCode(data.getCode());
        if (provider != null) {
            treeItemVO.setText(provider.getText(data));
            treeItemVO.setAttributes(provider.getAttributes(data));
        } else {
            treeItemVO.setText(data.getShowCode().concat(" ").concat(data.getName()));
        }
        treeItemVO.setDataType(data.getClass().getSimpleName());
        return treeItemVO;
    }

    public static List<LightWightTreeItemVO> buildFullLightTree(List<FBaseDataObj> dataList, TreeItemContentProvider<FBaseDataObj> provider) {
        if (dataList == null || dataList.isEmpty()) {
            return Collections.emptyList();
        }
        int mapSize = MapSizeUtil.getHashMapInitCapacities((int)dataList.size());
        HashMap<String, LightWightTreeItemVO> itemMap = new HashMap<String, LightWightTreeItemVO>(mapSize);
        ArrayList<LightWightTreeItemVO> roots = new ArrayList<LightWightTreeItemVO>();
        LightWightTreeItemVO superRoot = null;
        for (FBaseDataObj baseDataObj : dataList) {
            LightWightTreeItemVO treeItemVO = BaseDataTreeUtil.createTreeItem(baseDataObj, provider);
            itemMap.put(baseDataObj.getCode(), treeItemVO);
            if (!BaseDataTreeUtil.isRootNode(baseDataObj)) continue;
            if (DimensionConst.VIRTUAL_OBJ.getCode().equals(baseDataObj.getCode())) {
                superRoot = treeItemVO;
                continue;
            }
            roots.add(treeItemVO);
        }
        for (FBaseDataObj baseDataObj : dataList) {
            if (BaseDataTreeUtil.isRootNode(baseDataObj)) continue;
            LightWightTreeItemVO lightWightTreeItemVO = (LightWightTreeItemVO)itemMap.get(baseDataObj.getCode());
            LightWightTreeItemVO parent = (LightWightTreeItemVO)itemMap.get(baseDataObj.getParent());
            if (parent != null) {
                parent.addChild(lightWightTreeItemVO);
                continue;
            }
            roots.add(lightWightTreeItemVO);
        }
        if (superRoot == null) {
            return roots;
        }
        superRoot.setChildren(roots);
        ArrayList<LightWightTreeItemVO> result = new ArrayList<LightWightTreeItemVO>(1);
        result.add(superRoot);
        return result;
    }

    private static boolean isRootNode(FBaseDataObj baseDataObj) {
        return "-".equals(baseDataObj.getParent()) || baseDataObj.getParent() == null;
    }
}

