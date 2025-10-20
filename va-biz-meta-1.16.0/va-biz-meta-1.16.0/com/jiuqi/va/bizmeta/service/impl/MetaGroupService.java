/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.MetaInfoDO
 *  com.jiuqi.va.domain.meta.MetaInfoEditionDO
 *  com.jiuqi.va.domain.meta.OperateType
 *  com.jiuqi.va.domain.metadeploy.MetaDataDeployDim
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.bizmeta.service.impl;

import com.jiuqi.va.bizmeta.common.consts.MetaState;
import com.jiuqi.va.bizmeta.common.utils.MetaUtils;
import com.jiuqi.va.bizmeta.common.utils.VersionManage;
import com.jiuqi.va.bizmeta.dao.IMetaDataGroupDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataGroupUserDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataGroupVersionDao;
import com.jiuqi.va.bizmeta.domain.metadeploy.MetaDataDeployDTO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDTO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupEditionDO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupHistoryDO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoFilterDTO;
import com.jiuqi.va.bizmeta.exception.MetaCheckException;
import com.jiuqi.va.bizmeta.service.IMetaGroupService;
import com.jiuqi.va.bizmeta.service.IMetaVersionService;
import com.jiuqi.va.bizmeta.service.impl.MetaDeployService;
import com.jiuqi.va.bizmeta.service.impl.help.MetaDataGroupService;
import com.jiuqi.va.bizmeta.service.impl.help.MetaDataInfoService;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.MetaInfoDO;
import com.jiuqi.va.domain.meta.MetaInfoEditionDO;
import com.jiuqi.va.domain.meta.OperateType;
import com.jiuqi.va.domain.metadeploy.MetaDataDeployDim;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class MetaGroupService
implements IMetaGroupService {
    @Autowired
    private IMetaDataGroupDao metaGroupDao;
    @Autowired
    private IMetaDataGroupUserDao metaGroupEditionDao;
    @Autowired
    private IMetaVersionService metaVersionService;
    @Autowired
    private IMetaDataGroupVersionDao metaGroupHistoryDO;
    @Autowired
    private MetaDataInfoService metaDataInfoService;
    @Autowired
    private MetaDataGroupService metaDataGroupService;
    @Autowired
    private MetaDeployService metaDeployService;

    @Override
    public MetaGroupEditionDO createGroup(String userName, MetaGroupDTO group) {
        if (!StringUtils.isEmpty(group.getName()) && !StringUtils.isEmpty(group.getParentName()) && group.getName().equals(group.getParentName())) {
            throw new RuntimeException("\u5206\u7ec4\u6807\u8bc6\u4e0e\u7236\u7ea7\u5206\u7ec4\u6807\u8bc6\u76f8\u540c");
        }
        MetaGroupDO groupDO = this.findGroup(group.getModuleName(), group.getMetaType(), group.getName());
        if (groupDO != null) {
            throw new RuntimeException("\u540c\u5143\u6570\u636e\u7c7b\u578b\u4e0b\u5206\u7ec4\u540d\u91cd\u590d");
        }
        MetaGroupEditionDO groupEditionDO = new MetaGroupEditionDO();
        groupEditionDO.setId(UUID.randomUUID());
        groupEditionDO.setVersionNo(VersionManage.getInstance().newVersion(this.metaVersionService));
        groupEditionDO.setRowVersion(groupEditionDO.getVersionNo());
        groupEditionDO.setName(group.getName());
        groupEditionDO.setMetaState(MetaState.APPENDED.getValue());
        groupEditionDO.setMetaType(group.getMetaType());
        groupEditionDO.setModuleName(group.getModuleName());
        groupEditionDO.setParentName(group.getParentName());
        groupEditionDO.setTitle(group.getTitle());
        groupEditionDO.setUserName(userName);
        groupEditionDO.setOrgVersion(0L);
        groupEditionDO.setUniqueCode(MetaUtils.buildUniqueCode(group.getModuleName(), group.getMetaType(), group.getName()));
        this.metaGroupEditionDao.insert((Object)groupEditionDO);
        return groupEditionDO;
    }

    @Override
    public MetaGroupEditionDO deleteGroupById(String userName, UUID id) {
        MetaGroupDTO groupDTO = this.getGroupById(id);
        if (groupDTO == null) {
            throw new RuntimeException("\u5220\u9664\u7684\u5206\u7ec4\u4e0d\u5b58\u5728");
        }
        if (MetaState.DELETED.getValue() == groupDTO.getState()) {
            throw new RuntimeException("\u5220\u9664\u72b6\u6001\u7684\u5206\u7ec4\u4e0d\u80fd\u5220\u9664");
        }
        MetaGroupEditionDO editionDO = new MetaGroupEditionDO();
        if (MetaState.DEPLOYED.getValue() == groupDTO.getState()) {
            editionDO.setId(id);
            editionDO.setMetaState(MetaState.DELETED.getValue());
            editionDO.setMetaType(groupDTO.getMetaType());
            editionDO.setModuleName(groupDTO.getModuleName());
            editionDO.setName(groupDTO.getName());
            editionDO.setOrgVersion(groupDTO.getVersionNo());
            editionDO.setParentName(groupDTO.getParentName());
            editionDO.setTitle(groupDTO.getTitle());
            editionDO.setUserName(userName);
            editionDO.setVersionNo(VersionManage.getInstance().newVersion(this.metaVersionService));
            editionDO.setRowVersion(editionDO.getVersionNo());
            editionDO.setUniqueCode(groupDTO.getUniqueCode());
            this.checkDelete(editionDO);
            this.metaGroupEditionDao.insert((Object)editionDO);
            this.publishEditAndDelGroup(groupDTO, editionDO);
        } else {
            List<MetaInfoEditionDO> metaInfoEditions = this.metaDataInfoService.listAllMetaInfoByGroupName(groupDTO.getModuleName(), groupDTO.getMetaType(), groupDTO.getName(), null);
            if (!CollectionUtils.isEmpty(metaInfoEditions)) {
                throw new RuntimeException("\u5206\u7ec4\u4e0b\u5305\u542b\u5143\u6570\u636e\u65f6\u4e0d\u80fd\u88ab\u5220\u9664");
            }
            List<String> nameLists = MetaUtils.getGroupNamesByParent(this, groupDTO);
            groupDTO.setUserName(userName);
            groupDTO.setNameList(nameLists);
            this.metaGroupEditionDao.deleteGroupByName(groupDTO);
            editionDO = null;
        }
        return editionDO;
    }

    private void checkDelete(MetaGroupDO groupDO) {
        List groups = this.metaGroupDao.select((Object)new MetaGroupDO());
        ArrayList<String> names = new ArrayList<String>();
        MetaUtils.getGroupNamesByParent(groups, groupDO, names);
        if (!names.isEmpty()) {
            throw new MetaCheckException("\u5df2\u53d1\u5e03\u7684\u5206\u7ec4\u4e0b\u5305\u542b\u5b50\u5206\u7ec4\u4e0d\u80fd\u88ab\u5220\u9664\uff01");
        }
        List<MetaInfoDO> metaInfos = this.metaDataInfoService.getMetaVList(groupDO.getModuleName(), groupDO.getName(), groupDO.getMetaType());
        if (!CollectionUtils.isEmpty(metaInfos)) {
            throw new MetaCheckException("\u5f53\u524d\u5206\u7ec4\u4e0d\u80fd\u5220\u9664\uff1a\u5206\u7ec4\u4e0b\u6709\u57fa\u7ebf\u7248\u672c\u7684\u5143\u6570\u636e\uff01");
        }
        List<MetaInfoEditionDO> metaInfoEditions = this.metaDataInfoService.listAllMetaInfoByGroupName(groupDO.getModuleName(), groupDO.getMetaType(), groupDO.getName(), null);
        if (!CollectionUtils.isEmpty(metaInfoEditions)) {
            throw new MetaCheckException("\u5f53\u524d\u5206\u7ec4\u4e0d\u80fd\u5220\u9664\uff1a\u5206\u7ec4\u4e0b\u6709\u5143\u6570\u636e\uff01");
        }
    }

    @Override
    @Transactional
    public MetaGroupEditionDO updateGroup(String userName, MetaGroupDTO group) {
        if (!StringUtils.isEmpty(group.getName()) && !StringUtils.isEmpty(group.getParentName()) && group.getName().equals(group.getParentName())) {
            throw new RuntimeException("\u5206\u7ec4\u6807\u8bc6\u4e0e\u7236\u7ea7\u5206\u7ec4\u6807\u8bc6\u76f8\u540c");
        }
        MetaGroupEditionDO groupEditionDO = new MetaGroupEditionDO();
        groupEditionDO.setId(group.getId());
        MetaGroupEditionDO editionDO = (MetaGroupEditionDO)((Object)this.metaGroupEditionDao.selectOne((Object)groupEditionDO));
        MetaGroupEditionDO paramEditionDO = new MetaGroupEditionDO();
        paramEditionDO.setMetaState(MetaState.MODIFIED.getValue());
        boolean isUpdate = true;
        if (editionDO == null) {
            MetaGroupDO groupDO = new MetaGroupDO();
            groupDO.setId(group.getId());
            MetaGroupDO metaGroupDO = (MetaGroupDO)((Object)this.metaGroupDao.selectOne((Object)groupDO));
            if (metaGroupDO == null) {
                throw new RuntimeException("\u5f53\u524d\u7f16\u8f91\u6570\u636e\u4e0d\u5b58\u5728\uff0c\u6216\u5df2\u88ab\u5220\u9664");
            }
            paramEditionDO.setId(UUID.randomUUID());
            paramEditionDO.setOrgVersion(metaGroupDO.getVersionNo());
            isUpdate = false;
        } else {
            paramEditionDO.setId(group.getId());
            paramEditionDO.setOrgVersion(editionDO.getOrgVersion());
            if (MetaState.APPENDED.getValue() == editionDO.getMetaState().intValue()) {
                paramEditionDO.setMetaState(MetaState.APPENDED.getValue());
            }
        }
        paramEditionDO.setMetaType(group.getMetaType());
        paramEditionDO.setModuleName(group.getModuleName());
        paramEditionDO.setName(group.getName());
        paramEditionDO.setParentName(group.getParentName());
        paramEditionDO.setTitle(group.getTitle());
        paramEditionDO.setUserName(ShiroUtil.getUser().getId().toString());
        paramEditionDO.setVersionNo(VersionManage.getInstance().newVersion(this.metaVersionService));
        paramEditionDO.setRowVersion(paramEditionDO.getVersionNo());
        paramEditionDO.setUniqueCode(group.getUniqueCode());
        if (isUpdate) {
            this.metaGroupEditionDao.updateByPrimaryKey((Object)paramEditionDO);
        } else {
            this.metaGroupEditionDao.insert((Object)paramEditionDO);
            this.publishEditAndDelGroup(group, paramEditionDO);
            paramEditionDO.setMetaState(MetaState.DEPLOYED.getValue());
        }
        return paramEditionDO;
    }

    private void publishEditAndDelGroup(MetaGroupDTO groupDTO, MetaGroupEditionDO editionDO) {
        MetaDataDeployDTO metaDataDeployDTO = new MetaDataDeployDTO();
        ArrayList<MetaDataDeployDim> deployDatas = new ArrayList<MetaDataDeployDim>();
        MetaDataDeployDim metaDataDeployDim = new MetaDataDeployDim();
        metaDataDeployDim.setId(editionDO.getId());
        metaDataDeployDim.setVersion(editionDO.getVersionNo().longValue());
        metaDataDeployDim.setModuleName(editionDO.getModuleName());
        metaDataDeployDim.setType("group");
        metaDataDeployDim.setState(editionDO.getMetaState().intValue());
        ArrayList<MetaGroupDTO> metaGroupDTOS = new ArrayList<MetaGroupDTO>();
        metaGroupDTOS.add(groupDTO);
        metaDataDeployDim.setPath(MetaUtils.getGroupPath(metaGroupDTOS, editionDO));
        deployDatas.add(metaDataDeployDim);
        metaDataDeployDTO.setDeployDatas(deployDatas);
        this.metaDeployService.publishMetaData(ShiroUtil.getUser().getId().toString(), metaDataDeployDTO);
    }

    @Override
    public MetaGroupDO findGroup(String moduleName, String metaType, String name) {
        if (StringUtils.isEmpty(metaType) || StringUtils.isEmpty(name)) {
            throw new RuntimeException("\u53c2\u6570\u975e\u6cd5\uff0c\u5143\u6570\u636e\u7c7b\u578b\u6216\u5206\u7ec4\u540d\u79f0\u4e3a\u7a7a");
        }
        MetaGroupEditionDO groupEditionDO = new MetaGroupEditionDO();
        groupEditionDO.setModuleName(moduleName);
        groupEditionDO.setMetaType(metaType);
        groupEditionDO.setName(name);
        groupEditionDO.setUserName(ShiroUtil.getUser().getId().toString());
        MetaGroupEditionDO editionDO = (MetaGroupEditionDO)((Object)this.metaGroupEditionDao.selectOne((Object)groupEditionDO));
        if (editionDO != null) {
            return editionDO;
        }
        MetaGroupDO paramGroupDO = new MetaGroupDO();
        paramGroupDO.setModuleName(moduleName);
        paramGroupDO.setMetaType(metaType);
        paramGroupDO.setName(name);
        MetaGroupDO metaGroupDO = (MetaGroupDO)((Object)this.metaGroupDao.selectOne((Object)paramGroupDO));
        if (metaGroupDO != null) {
            return metaGroupDO;
        }
        return null;
    }

    @Override
    public MetaGroupDO getGroup(String module, String metaType, String name) {
        return null;
    }

    @Override
    public MetaGroupDTO getGroupById(UUID groupId) {
        MetaGroupDTO groupDTO = this.getGroupEditionById(groupId);
        if (groupDTO != null) {
            return groupDTO;
        }
        MetaGroupDO metaGroup = new MetaGroupDO();
        metaGroup.setId(groupId);
        MetaGroupDO queryGroupDO = (MetaGroupDO)((Object)this.metaGroupDao.selectOne((Object)metaGroup));
        if (queryGroupDO != null) {
            groupDTO = new MetaGroupDTO();
            groupDTO.setId(queryGroupDO.getId());
            groupDTO.setMetaType(queryGroupDO.getMetaType());
            groupDTO.setModuleName(queryGroupDO.getModuleName());
            groupDTO.setName(queryGroupDO.getName());
            groupDTO.setParentName(queryGroupDO.getParentName());
            groupDTO.setTitle(queryGroupDO.getTitle());
            groupDTO.setVersionNo(queryGroupDO.getVersionNo());
            groupDTO.setRowVersion(queryGroupDO.getRowVersion());
            groupDTO.setState(MetaState.DEPLOYED.getValue());
            groupDTO.setUniqueCode(queryGroupDO.getUniqueCode());
        }
        return groupDTO;
    }

    @Override
    public List<MetaGroupDTO> getGroupList(String moduleName, String metaType, OperateType operateType) {
        ArrayList<MetaGroupDTO> groupDTOs = new ArrayList<MetaGroupDTO>();
        MetaGroupDO paramGroupDO = new MetaGroupDO();
        paramGroupDO.setModuleName(moduleName);
        paramGroupDO.setMetaType(metaType);
        List groupDOs = this.metaGroupDao.select((Object)paramGroupDO);
        if (!OperateType.EXECUTE.equals((Object)operateType)) {
            List<MetaGroupEditionDO> groupGroupEditionDOs = this.metaDataGroupService.getGroupEditionList(moduleName, metaType);
            for (MetaGroupEditionDO metaGroupEditionDO : groupGroupEditionDOs) {
                MetaGroupDTO groupDTO = new MetaGroupDTO();
                groupDTO.setId(metaGroupEditionDO.getId());
                groupDTO.setMetaType(metaGroupEditionDO.getMetaType());
                groupDTO.setModuleName(metaGroupEditionDO.getModuleName());
                groupDTO.setName(metaGroupEditionDO.getName());
                groupDTO.setParentName(metaGroupEditionDO.getParentName());
                groupDTO.setState(metaGroupEditionDO.getMetaState());
                groupDTO.setTitle(metaGroupEditionDO.getTitle());
                groupDTO.setVersionNo(metaGroupEditionDO.getVersionNo());
                groupDTO.setRowVersion(metaGroupEditionDO.getRowVersion());
                groupDTO.setUniqueCode(metaGroupEditionDO.getUniqueCode());
                groupDTOs.add(groupDTO);
                if (metaGroupEditionDO.getMetaState().intValue() == MetaState.APPENDED.getValue()) continue;
                Iterator groupIterator = groupDOs.iterator();
                while (groupIterator.hasNext()) {
                    MetaGroupDO groupDO = (MetaGroupDO)((Object)groupIterator.next());
                    if (!groupDTO.getName().equals(groupDO.getName()) || !groupDTO.getMetaType().equals(groupDO.getMetaType())) continue;
                    groupIterator.remove();
                }
            }
        }
        for (MetaGroupDO metaGroupDO : groupDOs) {
            MetaGroupDTO groupDTO = new MetaGroupDTO();
            groupDTO.setId(metaGroupDO.getId());
            groupDTO.setMetaType(metaGroupDO.getMetaType());
            groupDTO.setModuleName(metaGroupDO.getModuleName());
            groupDTO.setName(metaGroupDO.getName());
            groupDTO.setParentName(metaGroupDO.getParentName());
            groupDTO.setState(MetaState.DEPLOYED.getValue());
            groupDTO.setTitle(metaGroupDO.getTitle());
            groupDTO.setVersionNo(metaGroupDO.getVersionNo());
            groupDTO.setRowVersion(metaGroupDO.getRowVersion());
            groupDTO.setUniqueCode(metaGroupDO.getUniqueCode());
            groupDTOs.add(groupDTO);
        }
        return groupDTOs;
    }

    @Override
    public List<MetaGroupDTO> getChildGroupList(String moduleName, String metaType, UUID groupId, OperateType operateType) {
        ArrayList<MetaGroupDTO> groupDTOs = new ArrayList<MetaGroupDTO>();
        MetaGroupDO paramGroupDO = new MetaGroupDO();
        paramGroupDO.setModuleName(moduleName);
        paramGroupDO.setMetaType(metaType);
        paramGroupDO.setId(groupId);
        List<MetaGroupDO> groupDOs = this.metaGroupDao.getChildGroupList(paramGroupDO);
        if (groupId != null) {
            MetaGroupDO groupDO = (MetaGroupDO)((Object)this.metaGroupDao.selectOne((Object)paramGroupDO));
            this.addGroupToList(groupDTOs, groupDO);
        }
        for (MetaGroupDO metaGroupDO : groupDOs) {
            this.addGroupToList(groupDTOs, metaGroupDO);
        }
        return groupDTOs;
    }

    public void addGroupToList(List<MetaGroupDTO> groupDTOs, MetaGroupDO groupDO) {
        MetaGroupDTO groupDTO = new MetaGroupDTO();
        groupDTO.setId(groupDO.getId());
        groupDTO.setMetaType(groupDO.getMetaType());
        groupDTO.setModuleName(groupDO.getModuleName());
        groupDTO.setName(groupDO.getName());
        groupDTO.setParentName(groupDO.getParentName());
        groupDTO.setState(MetaState.DEPLOYED.getValue());
        groupDTO.setTitle(groupDO.getTitle());
        groupDTO.setVersionNo(groupDO.getVersionNo());
        groupDTO.setRowVersion(groupDO.getRowVersion());
        groupDTO.setUniqueCode(groupDO.getUniqueCode());
        groupDTOs.add(groupDTO);
    }

    @Override
    public void addChildrenToSet(String name, HashMap<String, String> groupRelationship, Set<String> groupSet) {
        HashSet<String> hashSet = new HashSet<String>();
        for (Map.Entry<String, String> entry : groupRelationship.entrySet()) {
            if (!StringUtils.hasText(entry.getValue()) || !name.equals(entry.getValue())) continue;
            hashSet.add(entry.getKey());
        }
        for (String group : hashSet) {
            this.addChildrenToSet(group, groupRelationship, groupSet);
        }
        groupSet.addAll(hashSet);
    }

    @Override
    public void addParentToSet(String name, HashMap<String, String> groupRelationship, Set<String> groupSet) {
        if (!groupRelationship.containsKey(name) || "".equals(groupRelationship.get(name))) {
            return;
        }
        groupSet.add(groupRelationship.get(name));
        this.addParentToSet(groupRelationship.get(name), groupRelationship, groupSet);
    }

    @Override
    public List<MetaGroupDO> getGroupListByModule(String module) {
        return null;
    }

    @Override
    public List<MetaGroupDO> getGroupListByMetaType(String metaType) {
        MetaGroupDO groupDO = new MetaGroupDO();
        groupDO.setMetaType(metaType);
        return this.metaGroupDao.select((Object)groupDO);
    }

    @Override
    public List<MetaGroupDO> getGroupListByFilter(MetaInfoFilterDTO metaInfoFilterDTO) {
        MetaGroupDO groupDO = new MetaGroupDO();
        groupDO.setModuleName(metaInfoFilterDTO.getModuleName());
        groupDO.setMetaType(metaInfoFilterDTO.getMetaType());
        return this.metaGroupDao.select((Object)groupDO);
    }

    @Override
    public List<MetaGroupEditionDO> getGroupEditionListByModule(String module) {
        return null;
    }

    @Override
    public List<MetaGroupEditionDO> getGroupEditionListByMetaType(String metaType) {
        return null;
    }

    @Override
    public List<MetaGroupHistoryDO> getGroupHistoryList(String module, String metaType, String name) {
        return null;
    }

    @Override
    public void deployGroupById(String userName, MetaDataDeployDim metaDataDeployDim, long version) {
        UUID id = metaDataDeployDim.getId();
        MetaGroupDTO groupDTO = this.getGroupEditionById(id);
        if (groupDTO == null) {
            throw new RuntimeException("\u6570\u636e\u53d1\u751f\u53d8\u5316");
        }
        MetaGroupDO metaGroup = new MetaGroupDO();
        metaGroup.setUniqueCode(groupDTO.getUniqueCode());
        MetaGroupDO queryGroupDO = (MetaGroupDO)((Object)this.metaGroupDao.selectOne((Object)metaGroup));
        if (queryGroupDO != null && !queryGroupDO.getVersionNo().equals(groupDTO.getOrgVersion())) {
            throw new RuntimeException("\u5f53\u524d\u53d1\u5e03\u7684\u57fa\u7ebf\u7248\u672c\u4f4e\u4e8e\u5f53\u524d\u6700\u65b0\u7248\u672c\uff0c\u4e0d\u5141\u8bb8\u53d1\u5e03\uff0c\u8bf7\u5220\u9664\u5f53\u524d\u672a\u53d1\u5e03\u7248\u672c\u540e\u83b7\u53d6\u6700\u65b0\u57fa\u7ebf\u7248\u672c\u518d\u8fdb\u884c\u8bbe\u8ba1\u3002");
        }
        MetaGroupDO groupDO = new MetaGroupDO();
        if (MetaState.DELETED.getValue() == groupDTO.getState()) {
            List<MetaInfoDO> resultMetaInfos = this.metaDataInfoService.getMetaVList(groupDTO.getName(), groupDTO.getName(), groupDTO.getMetaType());
            if (resultMetaInfos != null && !resultMetaInfos.isEmpty()) {
                throw new RuntimeException("\u5206\u7ec4" + groupDTO.getName() + "\u5b58\u5728\u5143\u6570\u636e");
            }
            MetaGroupDO paramMetaGroup = new MetaGroupDO();
            paramMetaGroup.setMetaType(groupDTO.getMetaType());
            paramMetaGroup.setModuleName(groupDTO.getModuleName());
            paramMetaGroup.setParentName(groupDTO.getName());
            int count = this.metaGroupDao.selectCount((Object)paramMetaGroup);
            if (count > 0 && resultMetaInfos != null && !resultMetaInfos.isEmpty()) {
                throw new RuntimeException("\u5206\u7ec4" + groupDTO.getName() + "\u5b58\u5728\u5b50\u5206\u7ec4");
            }
            groupDO.setId(groupDTO.getId());
            this.metaGroupDao.delete((Object)groupDO);
        } else {
            if (MetaState.APPENDED.getValue() != groupDTO.getState()) {
                groupDO.setVersionNo(groupDTO.getOrgVersion());
                groupDO.setName(groupDTO.getName());
                groupDO.setMetaType(groupDTO.getMetaType());
                groupDO.setModuleName(groupDTO.getModuleName());
                this.metaGroupDao.delete((Object)groupDO);
            }
            groupDO.setId(groupDTO.getId());
            groupDO.setMetaType(groupDTO.getMetaType());
            groupDO.setModuleName(groupDTO.getModuleName());
            groupDO.setName(groupDTO.getName());
            groupDO.setParentName(groupDTO.getParentName());
            groupDO.setTitle(groupDTO.getTitle());
            groupDO.setVersionNo(version);
            groupDO.setRowVersion(version);
            groupDO.setUniqueCode(groupDTO.getUniqueCode());
            this.metaGroupDao.insert((Object)groupDO);
        }
        if (MetaState.DELETED.getValue() != groupDTO.getState()) {
            MetaGroupHistoryDO groupHistoryDO = new MetaGroupHistoryDO();
            groupHistoryDO.setId(groupDTO.getId());
            groupHistoryDO.setMetaType(groupDTO.getMetaType());
            groupHistoryDO.setModuleName(groupDTO.getModuleName());
            groupHistoryDO.setName(groupDTO.getName());
            groupHistoryDO.setParentName(groupDTO.getParentName());
            groupHistoryDO.setTitle(groupDTO.getTitle());
            groupHistoryDO.setVersionNo(version);
            groupHistoryDO.setRowVersion(version);
            groupHistoryDO.setUniqueCode(groupDTO.getUniqueCode());
            this.metaGroupHistoryDO.insert((Object)groupHistoryDO);
        }
        MetaGroupEditionDO editionDO = new MetaGroupEditionDO();
        editionDO.setId(id);
        this.metaGroupEditionDao.deleteEGroupById(editionDO);
    }

    @Override
    public MetaGroupDTO getGroupEditionById(UUID groupId) {
        MetaGroupDTO groupDTO = null;
        MetaGroupEditionDO editionDO = new MetaGroupEditionDO();
        editionDO.setId(groupId);
        MetaGroupEditionDO queryEditionDO = this.metaGroupEditionDao.selectById(editionDO);
        if (queryEditionDO == null) {
            return groupDTO;
        }
        groupDTO = new MetaGroupDTO();
        groupDTO.setId(queryEditionDO.getId());
        groupDTO.setMetaType(queryEditionDO.getMetaType());
        groupDTO.setModuleName(queryEditionDO.getModuleName());
        groupDTO.setName(queryEditionDO.getName());
        groupDTO.setParentName(queryEditionDO.getParentName());
        groupDTO.setTitle(queryEditionDO.getTitle());
        groupDTO.setVersionNo(queryEditionDO.getVersionNo());
        groupDTO.setRowVersion(queryEditionDO.getRowVersion());
        groupDTO.setState(queryEditionDO.getMetaState());
        groupDTO.setOrgVersion(queryEditionDO.getOrgVersion());
        groupDTO.setUniqueCode(queryEditionDO.getUniqueCode());
        return groupDTO;
    }

    @Override
    public void addGroupToList(HashMap<String, String> groupTemp, HashMap<String, String> groupRelationship, String name) {
        if (!groupTemp.containsKey(name)) {
            groupTemp.put(name, name);
        }
        if (!StringUtils.hasText(groupRelationship.get(name))) {
            return;
        }
        this.addGroupToList(groupTemp, groupRelationship, groupRelationship.get(name));
    }
}

