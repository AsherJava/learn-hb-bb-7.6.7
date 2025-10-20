/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodGroupVO
 *  com.jiuqi.dc.base.client.orgcomb.dto.OrgCombGroupDTO
 *  com.jiuqi.dc.base.common.module.IModule
 *  com.jiuqi.dc.base.common.module.IModuleGather
 */
package com.jiuqi.dc.base.impl.onlinePeriod.service.impl;

import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodGroupVO;
import com.jiuqi.dc.base.client.orgcomb.dto.OrgCombGroupDTO;
import com.jiuqi.dc.base.common.module.IModule;
import com.jiuqi.dc.base.common.module.IModuleGather;
import com.jiuqi.dc.base.impl.onlinePeriod.domain.OnlinePeriodDefineDO;
import com.jiuqi.dc.base.impl.onlinePeriod.mapper.OnlinePeriodDefineMapper;
import com.jiuqi.dc.base.impl.onlinePeriod.service.OnlinePeriodGroupService;
import com.jiuqi.dc.base.impl.orgcomb.domain.OrgCombGroupDO;
import com.jiuqi.dc.base.impl.orgcomb.mapper.OrgCombGroupMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnlinePeriodGroupServiceImpl
implements OnlinePeriodGroupService {
    @Autowired
    private OnlinePeriodDefineMapper onlinePeriodDefineMapper;
    @Autowired
    private OrgCombGroupMapper orgCombGroupMapper;
    @Autowired
    private IModuleGather iModuleGather;

    @Override
    public List<OnlinePeriodGroupVO> getAllGroup() {
        List iModules = this.iModuleGather.getModules();
        ArrayList<OnlinePeriodGroupVO> groupDTOList = new ArrayList<OnlinePeriodGroupVO>();
        OnlinePeriodGroupVO groupVO = new OnlinePeriodGroupVO();
        groupVO.setId(UUIDUtils.emptyUUIDStr());
        groupVO.setTitle("\u4e0a\u7ebf\u671f\u95f4");
        groupVO.setNodeType("root");
        groupVO.setExpand(Boolean.valueOf(true));
        groupVO.setModuleCode("root");
        if (iModules != null && iModules.size() > 0) {
            groupVO.setChildren(this.getChildren(iModules));
        } else {
            groupVO.setChildren(new ArrayList());
        }
        groupDTOList.add(groupVO);
        return groupDTOList;
    }

    @Override
    public List<OrgCombGroupDTO> getUnitGroup() {
        List<Object> orgCombGroupDOList = new ArrayList();
        OrgCombGroupDO orgCombGroupDO = new OrgCombGroupDO();
        orgCombGroupDOList = this.orgCombGroupMapper.getAllTreeNodes(orgCombGroupDO);
        ArrayList<OrgCombGroupDTO> groupDTOList = new ArrayList<OrgCombGroupDTO>();
        for (OrgCombGroupDO orgCombGroupDO2 : orgCombGroupDOList) {
            OrgCombGroupDTO orgCombGroupDTO = new OrgCombGroupDTO();
            BeanUtils.copyProperties((Object)orgCombGroupDO2, orgCombGroupDTO);
            groupDTOList.add(orgCombGroupDTO);
        }
        for (OrgCombGroupDTO orgCombGroupDTO : groupDTOList) {
            orgCombGroupDTO.setChildren(this.orgCombGroupMapper.getDefineByGroupId(orgCombGroupDTO));
        }
        return groupDTOList;
    }

    public List<OnlinePeriodGroupVO> getChildren(List<IModule> iModules) {
        ArrayList<OnlinePeriodGroupVO> onlinePeriodGroupVOList = new ArrayList<OnlinePeriodGroupVO>();
        for (IModule iModule : iModules) {
            OnlinePeriodGroupVO onlinePeriodGroupVO = new OnlinePeriodGroupVO();
            onlinePeriodGroupVO.setId(UUIDUtils.newUUIDStr());
            onlinePeriodGroupVO.setTitle(iModule.getName());
            onlinePeriodGroupVO.setNodeType("folder");
            onlinePeriodGroupVO.setExpand(Boolean.valueOf(true));
            onlinePeriodGroupVO.setModuleCode(iModule.getCode());
            onlinePeriodGroupVO.setChildren(this.getPeriodData(iModule.getCode()));
            onlinePeriodGroupVOList.add(onlinePeriodGroupVO);
        }
        return onlinePeriodGroupVOList;
    }

    public List<OnlinePeriodGroupVO> getPeriodData(String moduleCode) {
        ArrayList<OnlinePeriodGroupVO> onlinePeriodGroupVOList = new ArrayList<OnlinePeriodGroupVO>();
        List manageDOList = this.onlinePeriodDefineMapper.select((Object)new OnlinePeriodDefineDO(moduleCode));
        Collections.sort(manageDOList, Comparator.comparing(OnlinePeriodDefineDO::getOnlinePeriod));
        if (manageDOList != null && manageDOList.size() > 0) {
            for (OnlinePeriodDefineDO manageDO : manageDOList) {
                OnlinePeriodGroupVO onlinePeriodGroupVO = new OnlinePeriodGroupVO();
                onlinePeriodGroupVO.setId(manageDO.getId());
                String[] tempPeriod = manageDO.getOnlinePeriod().split("-");
                onlinePeriodGroupVO.setTitle(tempPeriod[0] + "\u5e74" + tempPeriod[1] + "\u6708");
                onlinePeriodGroupVO.setModuleCode(moduleCode);
                onlinePeriodGroupVOList.add(onlinePeriodGroupVO);
            }
        }
        return onlinePeriodGroupVOList;
    }
}

