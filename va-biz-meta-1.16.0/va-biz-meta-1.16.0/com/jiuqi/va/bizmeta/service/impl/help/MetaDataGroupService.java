/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.OperateType
 */
package com.jiuqi.va.bizmeta.service.impl.help;

import com.jiuqi.va.bizmeta.common.consts.MetaState;
import com.jiuqi.va.bizmeta.dao.IMetaDataGroupDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataGroupUserDao;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDTO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupEditionDO;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.OperateType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="MetaDataGroupService")
public class MetaDataGroupService {
    @Autowired
    private IMetaDataGroupUserDao metaGroupEditionDao;
    @Autowired
    private IMetaDataGroupDao metaGroupDao;

    public List<MetaGroupEditionDO> getGroupEditionList(String module, String metaType) {
        MetaGroupEditionDO editionDO = new MetaGroupEditionDO();
        editionDO.setUserName(ShiroUtil.getUser().getId().toString());
        editionDO.setModuleName(module);
        editionDO.setMetaType(metaType);
        return this.metaGroupEditionDao.select((Object)editionDO);
    }

    public List<MetaGroupDTO> getGroupList(String moduleName, String metaType, OperateType operateType) {
        ArrayList<MetaGroupDTO> groupDTOs = new ArrayList<MetaGroupDTO>();
        MetaGroupDO paramGroupDO = new MetaGroupDO();
        paramGroupDO.setModuleName(moduleName);
        paramGroupDO.setMetaType(metaType);
        List groupDOs = this.metaGroupDao.select((Object)paramGroupDO);
        if (!OperateType.EXECUTE.equals((Object)operateType)) {
            List<MetaGroupEditionDO> groupGroupEditionDOs = this.getGroupEditionList(moduleName, metaType);
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
            groupDTOs.add(groupDTO);
        }
        return groupDTOs;
    }

    public List<MetaGroupEditionDO> getGroupEditionList() {
        MetaGroupEditionDO editionDO = new MetaGroupEditionDO();
        editionDO.setUserName(ShiroUtil.getUser().getId().toString());
        return this.metaGroupEditionDao.select((Object)editionDO);
    }
}

