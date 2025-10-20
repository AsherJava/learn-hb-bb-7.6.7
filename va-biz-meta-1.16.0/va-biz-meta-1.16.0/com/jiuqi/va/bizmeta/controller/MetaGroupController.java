/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.MetaTreeInfoDTO
 *  com.jiuqi.va.domain.meta.MetaType
 *  com.jiuqi.va.domain.meta.OperateType
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bizmeta.controller;

import com.jiuqi.va.bizmeta.domain.dimension.MetaGroupDim;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDTO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupEditionDO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupVO;
import com.jiuqi.va.bizmeta.domain.metamodel.MetaModelDTO;
import com.jiuqi.va.bizmeta.service.IMetaAuthService;
import com.jiuqi.va.bizmeta.service.IMetaGroupService;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.MetaTreeInfoDTO;
import com.jiuqi.va.domain.meta.MetaType;
import com.jiuqi.va.domain.meta.OperateType;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/biz/meta/groups"})
public class MetaGroupController {
    @Autowired
    private IMetaGroupService groupService;
    @Autowired
    private IMetaAuthService metaAuthService;

    @PostMapping(value={"/getAllGroup"})
    public MetaGroupVO getAllGroup(@RequestBody MetaModelDTO infoDTO) {
        return this.metaAuthService.getAllGroup(infoDTO);
    }

    @PostMapping(value={"/all"})
    public MetaGroupVO getMetaGroups(@RequestBody MetaModelDTO infoDTO) {
        if ("all".equals(infoDTO.getMetaType())) {
            infoDTO.setMetaType(null);
        }
        List<MetaGroupDTO> groupDTOs = this.metaAuthService.getAuthGroupList(infoDTO.getModuleName(), infoDTO.getMetaType(), infoDTO.getOperateType());
        ArrayList<MetaGroupDim> groupDims = new ArrayList<MetaGroupDim>();
        for (MetaGroupDTO metaGroupDTO : groupDTOs) {
            MetaGroupDim groupDim = new MetaGroupDim();
            groupDim.setId(metaGroupDTO.getId());
            groupDim.setMetaType(metaGroupDTO.getMetaType());
            groupDim.setModuleName(metaGroupDTO.getModuleName());
            groupDim.setName(metaGroupDTO.getName());
            groupDim.setParentName(metaGroupDTO.getParentName());
            groupDim.setTitle(metaGroupDTO.getTitle());
            groupDim.setVersion(metaGroupDTO.getVersionNo());
            groupDim.setRowVersion(metaGroupDTO.getRowVersion());
            groupDim.setState(metaGroupDTO.getState());
            groupDim.setUniqueCode(metaGroupDTO.getUniqueCode());
            groupDims.add(groupDim);
        }
        MetaGroupVO groupVO = new MetaGroupVO();
        groupVO.setGroups(groupDims);
        return groupVO;
    }

    @PostMapping(value={"/check/metaName"})
    public R checkMetaName(@RequestBody MetaInfoDTO metaInfoDTO) {
        return this.groupService.findGroup(metaInfoDTO.getModuleName(), metaInfoDTO.getMetaType(), metaInfoDTO.getName()) == null ? R.ok() : R.error((String)"\u540c\u5143\u6570\u636e\u7c7b\u578b\u4e0b\u5206\u7ec4\u540d\u91cd\u590d");
    }

    @PostMapping(value={"/add"})
    public MetaGroupVO addMetaGroup(@RequestBody MetaGroupDTO groupDTO) {
        MetaGroupVO groupVO = new MetaGroupVO();
        try {
            MetaGroupEditionDO newGroupDTO = this.groupService.createGroup(ShiroUtil.getUser().getId().toString(), groupDTO);
            if (newGroupDTO != null) {
                MetaGroupDim groupDim = new MetaGroupDim();
                groupDim.setId(newGroupDTO.getId());
                groupDim.setMetaType(newGroupDTO.getMetaType());
                groupDim.setModuleName(newGroupDTO.getModuleName());
                groupDim.setName(newGroupDTO.getName());
                groupDim.setParentName(newGroupDTO.getParentName());
                groupDim.setTitle(newGroupDTO.getTitle());
                groupDim.setVersion(newGroupDTO.getVersionNo());
                groupDim.setRowVersion(newGroupDTO.getRowVersion());
                groupDim.setState(newGroupDTO.getMetaState());
                groupDim.setUniqueCode(newGroupDTO.getUniqueCode());
                groupVO.setGroupInfo(groupDim);
            }
            groupVO.setFlag(true);
        }
        catch (Exception e) {
            groupVO.setFlag(false);
            groupVO.setMessage("\u6dfb\u52a0\u5143\u6570\u636e\u5206\u7ec4\u65f6\u53d1\u751f\u5f02\u5e38\uff1a" + e.getMessage());
        }
        return groupVO;
    }

    @PostMapping(value={"/update/{groupId}"})
    public MetaGroupVO updateMetaGroup(@RequestBody MetaGroupDTO groupDTO, @PathVariable UUID groupId) {
        MetaGroupVO groupVO = new MetaGroupVO();
        try {
            groupDTO.setId(groupId);
            MetaGroupEditionDO metaGroup = this.groupService.updateGroup(ShiroUtil.getUser().getId().toString(), groupDTO);
            if (metaGroup != null) {
                MetaGroupDim groupDim = new MetaGroupDim();
                groupDim.setId(metaGroup.getId());
                groupDim.setMetaType(metaGroup.getMetaType());
                groupDim.setModuleName(metaGroup.getModuleName());
                groupDim.setName(metaGroup.getName());
                groupDim.setParentName(metaGroup.getParentName());
                groupDim.setTitle(metaGroup.getTitle());
                groupDim.setVersion(metaGroup.getVersionNo());
                groupDim.setRowVersion(metaGroup.getRowVersion());
                groupDim.setState(metaGroup.getMetaState());
                groupDim.setUniqueCode(metaGroup.getUniqueCode());
                if (!groupDTO.getId().equals(metaGroup.getId())) {
                    groupDim.setOrgId(groupDTO.getId());
                }
                groupVO.setGroupInfo(groupDim);
            }
            groupVO.setFlag(true);
        }
        catch (Exception e) {
            groupVO.setFlag(false);
            groupVO.setMessage("\u66f4\u65b0\u5143\u6570\u636e\u5206\u7ec4\u65f6\u53d1\u751f\u5f02\u5e38\uff1a" + e.getMessage());
        }
        return groupVO;
    }

    @PostMapping(value={"delete/{groupId}"})
    @Transactional
    public MetaGroupVO deleteMetaGroup(@PathVariable UUID groupId) {
        MetaGroupVO groupVO = new MetaGroupVO();
        try {
            MetaGroupEditionDO editionDO = this.groupService.deleteGroupById(ShiroUtil.getUser().getId().toString(), groupId);
            groupVO.setFlag(true);
            groupVO.setMessage("\u5220\u9664\u5206\u7ec4\u6210\u529f");
            if (editionDO != null) {
                MetaGroupDim groupDim = new MetaGroupDim();
                groupDim.setId(editionDO.getId());
                groupDim.setMetaType(editionDO.getMetaType());
                groupDim.setModuleName(editionDO.getModuleName());
                groupDim.setName(editionDO.getName());
                groupDim.setParentName(editionDO.getParentName());
                groupDim.setTitle(editionDO.getTitle());
                groupDim.setVersion(editionDO.getVersionNo());
                groupDim.setRowVersion(editionDO.getRowVersion());
                groupDim.setState(editionDO.getMetaState());
                groupDim.setUniqueCode(editionDO.getUniqueCode());
                groupVO.setGroupInfo(groupDim);
            }
        }
        catch (Exception e) {
            groupVO.setFlag(false);
            groupVO.setMessage(e.getMessage());
        }
        return groupVO;
    }

    @PostMapping(value={"/list"})
    public List<MetaTreeInfoDTO> getMetaDataGroup(@RequestBody TenantDO param) {
        ArrayList<MetaTreeInfoDTO> groupList = new ArrayList<MetaTreeInfoDTO>();
        String module = param.getExtInfo("module") != null ? param.getExtInfo("module").toString() : null;
        String bizType = param.getExtInfo("bizType") != null ? param.getExtInfo("bizType").toString() : null;
        Boolean bizAuth = param.getExtInfo("bizAuth") != null && (Boolean)param.getExtInfo("bizAuth") != false;
        List<Object> groups = new ArrayList();
        groups = Boolean.TRUE.equals(bizAuth) ? this.metaAuthService.getAuthGroupList(module, bizType, OperateType.EXECUTE) : this.groupService.getGroupList(module, bizType, OperateType.EXECUTE);
        for (MetaGroupDTO metaGroupDO : groups) {
            MetaTreeInfoDTO infoDTO = new MetaTreeInfoDTO();
            infoDTO.setId(metaGroupDO.getId());
            infoDTO.setName(metaGroupDO.getName());
            infoDTO.setTitle(metaGroupDO.getTitle());
            infoDTO.setModuleName(metaGroupDO.getModuleName());
            infoDTO.setParentName(metaGroupDO.getParentName());
            infoDTO.setUniqueCode(metaGroupDO.getUniqueCode());
            infoDTO.setType(MetaType.GROUP);
            groupList.add(infoDTO);
        }
        return groupList;
    }
}

