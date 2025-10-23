/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.transmission.data.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.transmission.data.common.MultilingualLog;
import com.jiuqi.nr.transmission.data.common.Utils;
import com.jiuqi.nr.transmission.data.dao.ISchemeGroupDao;
import com.jiuqi.nr.transmission.data.domain.SyncSchemeGroupDO;
import com.jiuqi.nr.transmission.data.dto.SyncSchemeGroupDTO;
import com.jiuqi.nr.transmission.data.exception.SchemeGroupServiceException;
import com.jiuqi.nr.transmission.data.service.ISchemeGroupService;
import com.jiuqi.nr.transmission.data.service.ISyncSchemeService;
import com.jiuqi.nr.transmission.data.vo.SchemeGroupNodeVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class SchemeGroupServiceImpl
implements ISchemeGroupService {
    public static final String SYNC_SCHEME_GROUP_ROOT_TITLE = "\u5168\u90e8\u65b9\u6848";
    public static final String SYNC_SCHEME_GROUP_ROOT_KEY = "00000000-0000-0000-0000-000000000000";
    @Autowired
    private ISchemeGroupDao schemeGroupDao;
    @Autowired
    private ISyncSchemeService syncSchemeService;

    @Override
    public List<ITree<SchemeGroupNodeVO>> getRoot() {
        ArrayList<ITree<SchemeGroupNodeVO>> treeObj = new ArrayList<ITree<SchemeGroupNodeVO>>();
        SyncSchemeGroupDTO rootSchemeGroup = this.getRootSchemeGroup();
        ITree node = new ITree((INode)new SchemeGroupNodeVO(rootSchemeGroup));
        List<ITree<SchemeGroupNodeVO>> children = this.getChildren(rootSchemeGroup);
        node.setChildren(children);
        node.setExpanded(true);
        node.setSelected(true);
        treeObj.add(node);
        return treeObj;
    }

    @Override
    public List<ITree<SchemeGroupNodeVO>> getChildren(SyncSchemeGroupDTO groupDTO) {
        List<SyncSchemeGroupDO> syncSchemeGroupDOS = this.schemeGroupDao.listByParent(groupDTO.getKey());
        Collections.sort(syncSchemeGroupDOS, (t1, t2) -> Utils.getChineseComparator().compare(t1.getTitle(), t2.getTitle()));
        List<SyncSchemeGroupDTO> syncSchemeGroupDTOS = SyncSchemeGroupDTO.toListInstance(syncSchemeGroupDOS);
        ArrayList<ITree<SchemeGroupNodeVO>> treeObj = new ArrayList<ITree<SchemeGroupNodeVO>>();
        if (!CollectionUtils.isEmpty(syncSchemeGroupDTOS)) {
            for (SyncSchemeGroupDTO syncSchemeGroupDTO : syncSchemeGroupDTOS) {
                ITree node = new ITree((INode)new SchemeGroupNodeVO(syncSchemeGroupDTO));
                node.setLeaf(CollectionUtils.isEmpty(this.schemeGroupDao.listByParent(syncSchemeGroupDTO.getKey())));
                treeObj.add((ITree<SchemeGroupNodeVO>)node);
            }
        }
        return treeObj;
    }

    @Override
    public List<SyncSchemeGroupDTO> search(SyncSchemeGroupDTO groupDTO) {
        List<SyncSchemeGroupDO> syncSchemeGroupDOS = this.schemeGroupDao.listByName(groupDTO.getFuzzyNameWord());
        Collections.sort(syncSchemeGroupDOS, (g1, g2) -> Utils.getChineseComparator().compare(g1.getTitle(), g2.getTitle()));
        return SyncSchemeGroupDTO.toListInstance(syncSchemeGroupDOS);
    }

    @Override
    public SyncSchemeGroupDTO get(SyncSchemeGroupDTO groupDTO) {
        Assert.notNull((Object)groupDTO.getKey(), MultilingualLog.schemeGroupServiceMessage(1));
        return this.get(groupDTO.getKey());
    }

    @Override
    public SyncSchemeGroupDTO getByTitle(SyncSchemeGroupDTO groupDTO) {
        SyncSchemeGroupDO syncSchemeGroupDO = this.schemeGroupDao.getByTitle(groupDTO.getTitle());
        if (syncSchemeGroupDO == null) {
            return null;
        }
        SyncSchemeGroupDTO syncSchemeGroupDTO = SyncSchemeGroupDTO.getInstance(syncSchemeGroupDO);
        return syncSchemeGroupDTO;
    }

    @Override
    public List<ITree<SchemeGroupNodeVO>> location(SyncSchemeGroupDTO groupDTO) {
        ArrayList<ITree<SchemeGroupNodeVO>> treeObjs = new ArrayList<ITree<SchemeGroupNodeVO>>();
        SyncSchemeGroupDTO rootGroupDTO = this.getRootSchemeGroup();
        ITree virtualRootNode = new ITree((INode)new SchemeGroupNodeVO(rootGroupDTO));
        virtualRootNode.setParent(null);
        if (groupDTO.getKey().equals(SYNC_SCHEME_GROUP_ROOT_KEY)) {
            virtualRootNode.setSelected(true);
            treeObjs.add(virtualRootNode);
        } else {
            ITree superNode = null;
            List<Object> childrenNodes = new ArrayList();
            SyncSchemeGroupDTO superGroupParams = new SyncSchemeGroupDTO();
            SyncSchemeGroupDTO thisParams = this.get(groupDTO);
            ITree thisNode = new ITree((INode)new SchemeGroupNodeVO(thisParams));
            thisNode.setSelected(true);
            while (StringUtils.hasText(thisParams.getParent())) {
                superGroupParams = this.get(thisParams.getParent());
                superNode = new ITree((INode)new SchemeGroupNodeVO(superGroupParams));
                childrenNodes = this.getChildrenNodes(superGroupParams, (ITree<SchemeGroupNodeVO>)thisNode);
                thisNode = superNode;
                superNode.setChildren(childrenNodes);
                superNode.setExpanded(true);
                thisParams = superGroupParams;
            }
            treeObjs.add(superNode);
        }
        return treeObjs;
    }

    @Override
    public boolean insert(SyncSchemeGroupDTO groupDTO) throws SchemeGroupServiceException {
        int result;
        groupDTO.setKey(UUIDUtils.getKey());
        groupDTO.setOrder(OrderGenerator.newOrder());
        if (!StringUtils.hasText(groupDTO.getTitle())) {
            throw new SchemeGroupServiceException(MultilingualLog.schemeGroupServiceMessage(2));
        }
        groupDTO.setTitle(groupDTO.getTitle().trim());
        if (!StringUtils.hasText(groupDTO.getParent())) {
            groupDTO.setParent(SYNC_SCHEME_GROUP_ROOT_KEY);
        }
        if ((result = this.schemeGroupDao.add(groupDTO)) == 1) {
            return true;
        }
        throw new SchemeGroupServiceException(MultilingualLog.schemeGroupServiceMessage(3));
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public boolean delete(SyncSchemeGroupDTO groupDTO) throws Exception {
        Assert.notNull((Object)groupDTO.getKey(), MultilingualLog.schemeGroupServiceMessage(4));
        if (SYNC_SCHEME_GROUP_ROOT_KEY.equals(groupDTO.getKey())) {
            throw new SchemeGroupServiceException(MultilingualLog.schemeGroupServiceMessage(5));
        }
        SyncSchemeGroupDO syncSchemeGroupDO = this.schemeGroupDao.get(groupDTO.getKey());
        String parentId = syncSchemeGroupDO.getParent();
        this.schemeGroupDao.updates(groupDTO.getKey(), parentId, "TG_PARENT");
        this.handleCalibreDefineForGroup(groupDTO);
        int result = this.schemeGroupDao.delete(groupDTO.getKey());
        if (result != 1) {
            throw new SchemeGroupServiceException(MultilingualLog.schemeGroupServiceMessage(6));
        }
        return result == 1;
    }

    @Override
    public boolean update(SyncSchemeGroupDTO groupDTO) throws SchemeGroupServiceException {
        if (!StringUtils.hasText(groupDTO.getTitle())) {
            throw new SchemeGroupServiceException(MultilingualLog.schemeGroupServiceMessage(2));
        }
        if (groupDTO.getKey().equals(groupDTO.getParent())) {
            throw new SchemeGroupServiceException(MultilingualLog.schemeGroupServiceMessage(7));
        }
        groupDTO.setTitle(groupDTO.getTitle().trim());
        int result = this.schemeGroupDao.update(groupDTO);
        return result == 1;
    }

    private void handleCalibreDefineForGroup(SyncSchemeGroupDTO syncSchemeGroupDO) throws Exception {
        this.syncSchemeService.updates(syncSchemeGroupDO.getKey(), SYNC_SCHEME_GROUP_ROOT_KEY, "TS_GROUP");
    }

    public SyncSchemeGroupDTO getRootSchemeGroup() {
        SyncSchemeGroupDTO rootSchemeGroupDTO = new SyncSchemeGroupDTO();
        rootSchemeGroupDTO.setTitle(SYNC_SCHEME_GROUP_ROOT_TITLE);
        rootSchemeGroupDTO.setKey(SYNC_SCHEME_GROUP_ROOT_KEY);
        rootSchemeGroupDTO.setParent(null);
        rootSchemeGroupDTO.setOrder("00000000");
        return rootSchemeGroupDTO;
    }

    public List<ITree<SchemeGroupNodeVO>> getChildrenNodes(SyncSchemeGroupDTO groupTreeParam, ITree<SchemeGroupNodeVO> thisNode) {
        ArrayList<ITree<SchemeGroupNodeVO>> treeObj = new ArrayList<ITree<SchemeGroupNodeVO>>();
        List<SyncSchemeGroupDTO> childSchemeGroupDTOList = this.getChildrenDTO(groupTreeParam);
        if (!CollectionUtils.isEmpty(childSchemeGroupDTOList)) {
            for (SyncSchemeGroupDTO calibreGroupDTO : childSchemeGroupDTOList) {
                ITree node = null;
                node = Objects.equals(calibreGroupDTO.getKey(), thisNode.getKey()) ? thisNode : new ITree((INode)new SchemeGroupNodeVO(calibreGroupDTO));
                node.setLeaf(CollectionUtils.isEmpty(this.schemeGroupDao.listByParent(calibreGroupDTO.getKey())));
                treeObj.add((ITree<SchemeGroupNodeVO>)node);
            }
        }
        return treeObj;
    }

    private SyncSchemeGroupDTO get(String groupKey) {
        Assert.notNull((Object)groupKey, MultilingualLog.schemeGroupServiceMessage(1));
        SyncSchemeGroupDTO syncSchemeGroupDTO = new SyncSchemeGroupDTO();
        if (SYNC_SCHEME_GROUP_ROOT_KEY.equals(groupKey)) {
            syncSchemeGroupDTO = this.getRootSchemeGroup();
        } else {
            SyncSchemeGroupDO syncSchemeGroupDO = this.schemeGroupDao.get(groupKey);
            syncSchemeGroupDTO = SyncSchemeGroupDTO.getInstance(syncSchemeGroupDO);
        }
        return syncSchemeGroupDTO;
    }

    private List<SyncSchemeGroupDTO> getChildrenDTO(SyncSchemeGroupDTO groupDTO) {
        List<SyncSchemeGroupDO> syncSchemeGroupDOS = this.schemeGroupDao.listByParent(groupDTO.getKey());
        Collections.sort(syncSchemeGroupDOS, (g1, g2) -> Utils.getChineseComparator().compare(g1.getTitle(), g2.getTitle()));
        return SyncSchemeGroupDTO.toListInstance(syncSchemeGroupDOS);
    }
}

