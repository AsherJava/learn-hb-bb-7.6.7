/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.calibre2.service.impl;

import com.jiuqi.nr.calibre2.ICalibreGroupService;
import com.jiuqi.nr.calibre2.common.CalibreGroupOption;
import com.jiuqi.nr.calibre2.domain.CalibreGroupDTO;
import com.jiuqi.nr.calibre2.service.ICalibreGroupManageService;
import com.jiuqi.nr.calibre2.vo.GroupNodeVO;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class CalibreGroupManageServiceImpl
implements ICalibreGroupManageService {
    @Autowired
    ICalibreGroupService calibreGroupService;

    @Override
    public List<ITree<GroupNodeVO>> initTree() {
        ArrayList<ITree<GroupNodeVO>> treeObjs = new ArrayList<ITree<GroupNodeVO>>();
        CalibreGroupDTO rootGroupDTO = this.getRootGroupDTO();
        ITree virtualRootNode = new ITree((INode)new GroupNodeVO(rootGroupDTO));
        virtualRootNode.setIcons(new String[]{"#icon-_Tfenzu"});
        virtualRootNode.setParent(null);
        List<ITree<GroupNodeVO>> rootNodes = this.getChildrenNodes(rootGroupDTO);
        virtualRootNode.setChildren(rootNodes);
        virtualRootNode.setSelected(true);
        virtualRootNode.setExpanded(true);
        treeObjs.add(virtualRootNode);
        return treeObjs;
    }

    @Override
    public List<ITree<GroupNodeVO>> getChildrenNodes(CalibreGroupDTO groupTreeParam) {
        if (groupTreeParam.getKey().equals("00000000-0000-0000-0000-000000000000")) {
            groupTreeParam.setGroupTreeType(CalibreGroupOption.GroupTreeType.ROOT);
        } else {
            groupTreeParam.setGroupTreeType(CalibreGroupOption.GroupTreeType.DIRECT_CHILDREN);
        }
        ArrayList<ITree<GroupNodeVO>> treeObj = new ArrayList<ITree<GroupNodeVO>>();
        List<CalibreGroupDTO> childCalibreGroupDOList = this.calibreGroupService.list(groupTreeParam).getData();
        if (!CollectionUtils.isEmpty(childCalibreGroupDOList)) {
            for (CalibreGroupDTO calibreGroupDTO : childCalibreGroupDOList) {
                ITree node = new ITree((INode)new GroupNodeVO(calibreGroupDTO));
                node.setIcons(new String[]{"#icon-_Tfenzu"});
                calibreGroupDTO.setGroupTreeType(CalibreGroupOption.GroupTreeType.DIRECT_CHILDREN);
                node.setLeaf(CollectionUtils.isEmpty((Collection)this.calibreGroupService.list(calibreGroupDTO).getData()));
                treeObj.add((ITree<GroupNodeVO>)node);
            }
        }
        return treeObj;
    }

    @Override
    public List<ITree<GroupNodeVO>> locationTreeNode(CalibreGroupDTO groupTreeParam) {
        ArrayList<ITree<GroupNodeVO>> treeObjs = new ArrayList<ITree<GroupNodeVO>>();
        CalibreGroupDTO rootGroupDTO = this.getRootGroupDTO();
        ITree virtualRootNode = new ITree((INode)new GroupNodeVO(rootGroupDTO));
        virtualRootNode.setParent(null);
        if (groupTreeParam.getKey().equals("00000000-0000-0000-0000-000000000000")) {
            virtualRootNode.setSelected(true);
            treeObjs.add(virtualRootNode);
        } else {
            ITree superNode = null;
            List<Object> childrenNodes = new ArrayList();
            CalibreGroupDTO superGroupParams = new CalibreGroupDTO();
            CalibreGroupDTO groupTreeParams = this.calibreGroupService.get(groupTreeParam.getKey()).getData();
            ITree thisNode = new ITree((INode)new GroupNodeVO(groupTreeParams));
            thisNode.setSelected(true);
            while (StringUtils.hasText(groupTreeParams.getParent())) {
                superGroupParams = this.calibreGroupService.get(groupTreeParams.getParent()).getData();
                superNode = new ITree((INode)new GroupNodeVO(superGroupParams));
                childrenNodes = this.getChildrenNodes(superGroupParams, (ITree<GroupNodeVO>)thisNode);
                thisNode = superNode;
                superNode.setChildren(childrenNodes);
                superNode.setExpanded(true);
                groupTreeParams = superGroupParams;
            }
            treeObjs.add(superNode);
        }
        return treeObjs;
    }

    CalibreGroupDTO getRootGroupDTO() {
        CalibreGroupDTO calibreRootGroupDTO = new CalibreGroupDTO();
        calibreRootGroupDTO.setKey("00000000-0000-0000-0000-000000000000");
        calibreRootGroupDTO.setName("\u5168\u90e8\u53e3\u5f84");
        calibreRootGroupDTO.setParent(null);
        calibreRootGroupDTO.setGroupTreeType(CalibreGroupOption.GroupTreeType.ROOT);
        return calibreRootGroupDTO;
    }

    public List<ITree<GroupNodeVO>> getChildrenNodes(CalibreGroupDTO groupTreeParam, ITree<GroupNodeVO> thisNode) {
        if (groupTreeParam.getKey().equals("00000000-0000-0000-0000-000000000000")) {
            groupTreeParam.setGroupTreeType(CalibreGroupOption.GroupTreeType.ROOT);
        } else {
            groupTreeParam.setGroupTreeType(CalibreGroupOption.GroupTreeType.DIRECT_CHILDREN);
        }
        ArrayList<ITree<GroupNodeVO>> treeObj = new ArrayList<ITree<GroupNodeVO>>();
        List<CalibreGroupDTO> childCalibreGroupDOList = this.calibreGroupService.list(groupTreeParam).getData();
        if (!CollectionUtils.isEmpty(childCalibreGroupDOList)) {
            for (CalibreGroupDTO calibreGroupDTO : childCalibreGroupDOList) {
                calibreGroupDTO.setGroupTreeType(CalibreGroupOption.GroupTreeType.DIRECT_CHILDREN);
                ITree node = null;
                node = Objects.equals(calibreGroupDTO.getKey(), thisNode.getKey()) ? thisNode : new ITree((INode)new GroupNodeVO(calibreGroupDTO));
                node.setLeaf(CollectionUtils.isEmpty((Collection)this.calibreGroupService.list(calibreGroupDTO).getData()));
                treeObj.add((ITree<GroupNodeVO>)node);
            }
        }
        return treeObj;
    }
}

