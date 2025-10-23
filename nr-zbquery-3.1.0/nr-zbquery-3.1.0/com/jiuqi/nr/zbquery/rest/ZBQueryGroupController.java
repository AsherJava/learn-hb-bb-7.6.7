/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  javax.validation.constraints.NotNull
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.zbquery.rest;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.zbquery.bean.ZBQueryGroup;
import com.jiuqi.nr.zbquery.common.ZBQueryConst;
import com.jiuqi.nr.zbquery.rest.vo.GroupTreeNodeVO;
import com.jiuqi.nr.zbquery.service.ZBQueryGroupService;
import com.jiuqi.nr.zbquery.util.ZBQueryLogHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/zbquery/manage/group"})
public class ZBQueryGroupController {
    private static final Logger logger = LoggerFactory.getLogger(ZBQueryGroupController.class);
    @Autowired
    private ZBQueryGroupService zBQueryGroupService;

    @PostMapping(value={"/add_querygroup"})
    public String addQueryGroup(@RequestBody ZBQueryGroup ZBQueryGroup2) throws JQException {
        String res = null;
        try {
            res = this.zBQueryGroupService.addQueryGroup(ZBQueryGroup2);
            ZBQueryLogHelper.info("\u65b0\u589e\u67e5\u8be2\u5206\u7ec4", String.format("\u5206\u7ec4[%s]\u65b0\u589e\u6210\u529f", ZBQueryGroup2.getTitle()));
        }
        catch (JQException e) {
            ZBQueryLogHelper.error("\u65b0\u589e\u67e5\u8be2\u5206\u7ec4", String.format("\u5206\u7ec4[%s]\u65b0\u589e\u5931\u8d25\uff1a%s", ZBQueryGroup2.getTitle(), e.getMessage()));
            logger.error(e.getMessage(), e);
            throw e;
        }
        return res;
    }

    @PostMapping(value={"/modify_querygroup"})
    public String modifyQueryGroup(@RequestBody ZBQueryGroup ZBQueryGroup2) throws JQException {
        String res = null;
        try {
            res = this.zBQueryGroupService.modifyQueryGroup(ZBQueryGroup2);
            ZBQueryLogHelper.info("\u4fee\u6539\u67e5\u8be2\u5206\u7ec4", String.format("\u5206\u7ec4[%s]\u4fee\u6539\u6210\u529f", ZBQueryGroup2.getTitle()));
        }
        catch (JQException e) {
            ZBQueryLogHelper.error("\u4fee\u6539\u67e5\u8be2\u5206\u7ec4", String.format("\u5206\u7ec4[%s]\u4fee\u6539\u5931\u8d25\uff1a%s", ZBQueryGroup2.getTitle(), e.getMessage()));
            logger.error(e.getMessage(), e);
            throw e;
        }
        return res;
    }

    @PostMapping(value={"/delete_querygroup"})
    public String deleteQueryGroup(String groupId) throws JQException {
        String res = null;
        try {
            res = this.zBQueryGroupService.deleteQueryGroup(groupId);
            ZBQueryLogHelper.info("\u5220\u9664\u67e5\u8be2\u5206\u7ec4", String.format("\u5206\u7ec4[%s]\u4fee\u6539\u6210\u529f", groupId));
        }
        catch (JQException e) {
            ZBQueryLogHelper.error("\u5220\u9664\u67e5\u8be2\u5206\u7ec4", String.format("\u5206\u7ec4[%s]\u4fee\u6539\u5931\u8d25\uff1a%s", groupId, e.getMessage()));
            logger.error(e.getMessage(), e);
            throw e;
        }
        return res;
    }

    @PostMapping(value={"/check_querygrouptitle"})
    public boolean checkQueryGroupTitle(@RequestBody ZBQueryGroup ZBQueryGroup2) {
        String newTitle = ZBQueryGroup2.getTitle();
        List<ZBQueryGroup> queryGroupChildren = this.zBQueryGroupService.getQueryGroupChildren(ZBQueryGroup2.getParentId());
        if (queryGroupChildren != null && queryGroupChildren.size() > 0) {
            for (ZBQueryGroup zBQueryGroup : queryGroupChildren) {
                if (!newTitle.equals(zBQueryGroup.getTitle()) || zBQueryGroup.getId().equals(ZBQueryGroup2.getId())) continue;
                return false;
            }
        }
        return true;
    }

    @GetMapping(value={"/search_grouptree"})
    public List<ZBQueryGroup> searchGroupTree(@NotNull String keywords) {
        return this.zBQueryGroupService.getQueryGroupByTitle(keywords, true);
    }

    @GetMapping(value={"/init_grouptree/{nodeId}"})
    public List<ITree<GroupTreeNodeVO>> initGroupTree(@PathVariable String nodeId) {
        return this.getAllChildNode(nodeId);
    }

    @GetMapping(value={"/init_roottree/{ismodel}"})
    public List<ITree<GroupTreeNodeVO>> initRootTree(@PathVariable String ismodel) {
        ArrayList<ITree<GroupTreeNodeVO>> nodes = new ArrayList<ITree<GroupTreeNodeVO>>();
        ITree<GroupTreeNodeVO> rootNode = this.buildRootNode(true, false);
        rootNode.setDisabled("true".equals(ismodel));
        nodes.add(rootNode);
        return nodes;
    }

    @GetMapping(value={"/getchildren_grouptree"})
    public List<ITree<GroupTreeNodeVO>> getChildrenGroupTree(String nodeid, String disbalenode) {
        ArrayList<ITree<GroupTreeNodeVO>> nodes = new ArrayList<ITree<GroupTreeNodeVO>>();
        List<String> alldisableNode = this.zBQueryGroupService.getQueryGroupAllChildrenId(disbalenode);
        alldisableNode.add(disbalenode);
        List<ZBQueryGroup> groupList = this.zBQueryGroupService.getQueryGroupChildren(nodeid);
        List<String> allParentId = this.zBQueryGroupService.getAllQueryGroupParentId();
        for (ZBQueryGroup zBQueryGroup : groupList) {
            GroupTreeNodeVO group = new GroupTreeNodeVO(zBQueryGroup);
            ITree node = new ITree((INode)group);
            node.setIcons(ZBQueryConst.ICONS_FOLDER);
            node.setExpanded(false);
            node.setLeaf(!allParentId.contains(zBQueryGroup.getId()));
            node.setDisabled(alldisableNode.contains(node.getKey()));
            nodes.add((ITree<GroupTreeNodeVO>)node);
        }
        return nodes;
    }

    @GetMapping(value={"/getnodepath_grouptree/{nodeId}"})
    public List<String> getNodePath(@PathVariable String nodeId) {
        ArrayList<String> list = new ArrayList<String>();
        this.zBQueryGroupService.getParentId(list, nodeId);
        list.add(nodeId);
        return list;
    }

    private List<ITree<GroupTreeNodeVO>> getChildrenNode(String nodeId) {
        ArrayList<ITree<GroupTreeNodeVO>> nodes = new ArrayList<ITree<GroupTreeNodeVO>>();
        List<ZBQueryGroup> groupList = this.zBQueryGroupService.getQueryGroupChildren(nodeId);
        List<String> allParentId = this.zBQueryGroupService.getAllQueryGroupParentId();
        for (int i = 0; i < groupList.size(); ++i) {
            GroupTreeNodeVO group = new GroupTreeNodeVO(groupList.get(i));
            ITree node = new ITree((INode)group);
            node.setIcons(ZBQueryConst.ICONS_FOLDER);
            node.setExpanded(false);
            node.setLeaf(!allParentId.contains(groupList.get(i).getId()));
            if (i == 0) {
                node.setSelected(true);
            }
            nodes.add((ITree<GroupTreeNodeVO>)node);
        }
        return nodes;
    }

    private ITree<GroupTreeNodeVO> buildRootNode(boolean isExpanded, boolean isSelected) {
        GroupTreeNodeVO node = new GroupTreeNodeVO();
        node.setKey("00000000-0000-0000-0000-000000000000");
        node.setTitle(ZBQueryConst.TITLE_ALLZBQUERYGROUPS);
        ITree root = new ITree((INode)node);
        root.setIcons(ZBQueryConst.ICONS_FOLDER);
        root.setSelected(isSelected);
        root.setExpanded(isExpanded);
        if (!isExpanded) {
            root.setLeaf(true);
        }
        return root;
    }

    private List<ITree<GroupTreeNodeVO>> getAllChildNode(String nodeId) {
        ArrayList<ITree<GroupTreeNodeVO>> nodes = new ArrayList<ITree<GroupTreeNodeVO>>();
        ITree<GroupTreeNodeVO> root = null;
        if ("00000000-0000-0000-0000-000000000000".equals(nodeId)) {
            List<ITree<GroupTreeNodeVO>> childrenNode = this.getChildrenNode(nodeId);
            root = this.buildRootNode(childrenNode.size() > 0, false);
            for (ITree<GroupTreeNodeVO> child : childrenNode) {
                root.appendChild(child);
            }
        } else {
            Map<String, List<ZBQueryGroup>> parentIdMap = this.zBQueryGroupService.getParentIdMap(true);
            ArrayList<String> parentIdList = new ArrayList<String>();
            this.zBQueryGroupService.getParentId(parentIdList, nodeId);
            root = this.buildRootNode(parentIdMap.size() > 0, false);
            this.getChildNode(parentIdMap, parentIdList, root, nodeId);
        }
        nodes.add(root);
        return nodes;
    }

    private boolean getChildNode(Map<String, List<ZBQueryGroup>> groupMap, List<String> parentIdList, ITree<GroupTreeNodeVO> parentNode, String nodeId) {
        if (groupMap.containsKey(parentNode.getKey())) {
            List<ZBQueryGroup> list = groupMap.get(parentNode.getKey());
            for (ZBQueryGroup zBQueryGroup : list) {
                GroupTreeNodeVO group = new GroupTreeNodeVO(zBQueryGroup);
                ITree treeNode = new ITree((INode)group);
                treeNode.setIcons(ZBQueryConst.ICONS_FOLDER);
                treeNode.setExpanded(parentIdList.contains(zBQueryGroup.getId()));
                treeNode.setSelected(zBQueryGroup.getId().equals(nodeId));
                treeNode.setLeaf(this.getChildNode(groupMap, parentIdList, (ITree<GroupTreeNodeVO>)treeNode, nodeId));
                parentNode.appendChild(treeNode);
            }
            return false;
        }
        return true;
    }
}

