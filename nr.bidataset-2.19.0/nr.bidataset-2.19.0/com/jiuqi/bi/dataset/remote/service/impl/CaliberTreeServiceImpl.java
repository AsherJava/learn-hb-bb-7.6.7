/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.calibre2.ICalibreDefineService
 *  com.jiuqi.nr.calibre2.ICalibreGroupService
 *  com.jiuqi.nr.calibre2.common.CalibreGroupOption$GroupTreeType
 *  com.jiuqi.nr.calibre2.domain.CalibreDefineDTO
 *  com.jiuqi.nr.calibre2.domain.CalibreGroupDO
 *  com.jiuqi.nr.calibre2.domain.CalibreGroupDTO
 *  com.jiuqi.nr.calibre2.vo.GroupNodeVO
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.bi.dataset.remote.service.impl;

import com.jiuqi.bi.dataset.remote.service.CaliberTreeService;
import com.jiuqi.nr.calibre2.ICalibreDefineService;
import com.jiuqi.nr.calibre2.ICalibreGroupService;
import com.jiuqi.nr.calibre2.common.CalibreGroupOption;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;
import com.jiuqi.nr.calibre2.domain.CalibreGroupDO;
import com.jiuqi.nr.calibre2.domain.CalibreGroupDTO;
import com.jiuqi.nr.calibre2.vo.GroupNodeVO;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class CaliberTreeServiceImpl
implements CaliberTreeService {
    @Autowired
    private ICalibreGroupService calibreGroupService;
    @Autowired
    private ICalibreDefineService calibreDefineService;

    @Override
    public List<ITree<GroupNodeVO>> initTree() {
        ArrayList<ITree<GroupNodeVO>> treeObjs = new ArrayList<ITree<GroupNodeVO>>();
        CalibreGroupDTO rootGroupDTO = this.getRootGroupDTO();
        ITree virtualRootNode = new ITree((INode)new GroupNodeVO((CalibreGroupDO)rootGroupDTO));
        virtualRootNode.setParent(null);
        List<ITree<GroupNodeVO>> rootNodes = this.getChildrenNodes(rootGroupDTO);
        virtualRootNode.setChildren(rootNodes);
        virtualRootNode.setExpanded(true);
        treeObjs.add(virtualRootNode);
        return treeObjs;
    }

    private List<ITree<GroupNodeVO>> getCaliberNodes(String groupId) {
        CalibreDefineDTO groupDTO = new CalibreDefineDTO();
        groupDTO.setGroup(groupId);
        List list = (List)this.calibreDefineService.list(groupDTO).getData();
        ArrayList<ITree<GroupNodeVO>> nodeList = new ArrayList<ITree<GroupNodeVO>>();
        if (list.size() > 0) {
            for (CalibreDefineDTO calibreDefineDTO : list) {
                ITree treeNode = new ITree();
                treeNode.setTitle(calibreDefineDTO.getName());
                treeNode.setKey(calibreDefineDTO.getKey());
                treeNode.setCode(calibreDefineDTO.getCode());
                treeNode.setLeaf(true);
                treeNode.setSelected(false);
                treeNode.setChildren(null);
                nodeList.add((ITree<GroupNodeVO>)treeNode);
            }
        }
        return nodeList;
    }

    private CalibreGroupDTO getRootGroupDTO() {
        CalibreGroupDTO calibreRootGroupDTO = new CalibreGroupDTO();
        calibreRootGroupDTO.setKey("00000000-0000-0000-0000-000000000000");
        calibreRootGroupDTO.setName("\u5168\u90e8\u53e3\u5f84");
        calibreRootGroupDTO.setParent(null);
        calibreRootGroupDTO.setGroupTreeType(CalibreGroupOption.GroupTreeType.ROOT);
        return calibreRootGroupDTO;
    }

    @Override
    public List<ITree<GroupNodeVO>> getChildrenNodes(CalibreGroupDTO groupTreeParam) {
        if (groupTreeParam.getKey().equals("00000000-0000-0000-0000-000000000000")) {
            groupTreeParam.setGroupTreeType(CalibreGroupOption.GroupTreeType.ROOT);
        } else {
            groupTreeParam.setGroupTreeType(CalibreGroupOption.GroupTreeType.DIRECT_CHILDREN);
        }
        ArrayList<ITree<GroupNodeVO>> treeObj = new ArrayList<ITree<GroupNodeVO>>();
        List childCalibreGroupDOList = (List)this.calibreGroupService.list(groupTreeParam).getData();
        List<ITree<GroupNodeVO>> caliberNodes = this.getCaliberNodes(groupTreeParam.getKey());
        if (!CollectionUtils.isEmpty(childCalibreGroupDOList)) {
            for (CalibreGroupDTO calibreGroupDTO : childCalibreGroupDOList) {
                ITree node = new ITree((INode)new GroupNodeVO((CalibreGroupDO)calibreGroupDTO));
                calibreGroupDTO.setGroupTreeType(CalibreGroupOption.GroupTreeType.DIRECT_CHILDREN);
                treeObj.add((ITree<GroupNodeVO>)node);
            }
        }
        if (caliberNodes.size() > 0) {
            treeObj.addAll(caliberNodes);
        }
        return treeObj;
    }

    @Override
    public List<ITree<GroupNodeVO>> locationTreeNode(String caliberId) {
        CalibreDefineDTO calibreDefineDTO = new CalibreDefineDTO();
        calibreDefineDTO.setKey(caliberId);
        CalibreDefineDTO calibreDefine = (CalibreDefineDTO)this.calibreDefineService.get(calibreDefineDTO).getData();
        String groupId = calibreDefine.getGroup();
        ArrayList<ITree<GroupNodeVO>> treeObjs = new ArrayList<ITree<GroupNodeVO>>();
        CalibreGroupDTO rootGroupDTO = this.getRootGroupDTO();
        ITree virtualRootNode = new ITree((INode)new GroupNodeVO((CalibreGroupDO)rootGroupDTO));
        virtualRootNode.setParent(null);
        if (groupId == null || groupId.equals("00000000-0000-0000-0000-000000000000")) {
            virtualRootNode.setSelected(true);
            treeObjs.add(virtualRootNode);
        } else {
            ITree superNode = null;
            List<Object> childrenNodes = new ArrayList();
            CalibreGroupDTO superGroupParams = new CalibreGroupDTO();
            CalibreGroupDTO groupTreeParams = (CalibreGroupDTO)this.calibreGroupService.get(groupId).getData();
            ITree thisNode = new ITree((INode)new GroupNodeVO((CalibreGroupDO)groupTreeParams));
            List<ITree<GroupNodeVO>> groupNodes = this.getChildrenNodes(groupTreeParams);
            for (ITree<GroupNodeVO> node : groupNodes) {
                if (!node.getKey().equals(caliberId)) continue;
                node.setSelected(true);
            }
            thisNode.setChildren(groupNodes);
            thisNode.setExpanded(true);
            while (StringUtils.hasText(groupTreeParams.getParent())) {
                superGroupParams = (CalibreGroupDTO)this.calibreGroupService.get(groupTreeParams.getParent()).getData();
                superNode = new ITree((INode)new GroupNodeVO((CalibreGroupDO)superGroupParams));
                childrenNodes = this.getChildrenNodes(superGroupParams, (ITree<GroupNodeVO>)thisNode);
                thisNode = superNode;
                superNode.setChildren(childrenNodes);
                superNode.setExpanded(true);
                groupTreeParams = superGroupParams;
            }
            List<ITree<GroupNodeVO>> allCaliberNodes = this.getCaliberNodes("00000000-0000-0000-0000-000000000000");
            List children = superNode.getChildren();
            children.addAll(allCaliberNodes);
            treeObjs.add(superNode);
        }
        return treeObjs;
    }

    public List<ITree<GroupNodeVO>> getChildrenNodes(CalibreGroupDTO groupTreeParam, ITree<GroupNodeVO> thisNode) {
        if (groupTreeParam.getKey().equals("00000000-0000-0000-0000-000000000000")) {
            groupTreeParam.setGroupTreeType(CalibreGroupOption.GroupTreeType.ROOT);
        } else {
            groupTreeParam.setGroupTreeType(CalibreGroupOption.GroupTreeType.DIRECT_CHILDREN);
        }
        ArrayList<ITree<GroupNodeVO>> treeObj = new ArrayList<ITree<GroupNodeVO>>();
        List childCalibreGroupDOList = (List)this.calibreGroupService.list(groupTreeParam).getData();
        if (!CollectionUtils.isEmpty(childCalibreGroupDOList)) {
            for (CalibreGroupDTO calibreGroupDTO : childCalibreGroupDOList) {
                calibreGroupDTO.setGroupTreeType(CalibreGroupOption.GroupTreeType.DIRECT_CHILDREN);
                ITree node = null;
                node = Objects.equals(calibreGroupDTO.getKey(), thisNode.getKey()) ? thisNode : new ITree((INode)new GroupNodeVO((CalibreGroupDO)calibreGroupDTO));
                treeObj.add(node);
            }
        }
        return treeObj;
    }
}

