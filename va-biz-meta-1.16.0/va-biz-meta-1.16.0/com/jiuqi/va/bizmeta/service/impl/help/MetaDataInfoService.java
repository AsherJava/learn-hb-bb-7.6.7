/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.MetaInfoDO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.MetaInfoEditionDO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.bizmeta.service.impl.help;

import com.jiuqi.va.bizmeta.common.consts.MetaState;
import com.jiuqi.va.bizmeta.common.utils.MetaUtils;
import com.jiuqi.va.bizmeta.dao.IMetaDataInfoDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataInfoUserDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataInfoVersionDao;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDTO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoHistoryDO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoPageDTO;
import com.jiuqi.va.bizmeta.domain.metamodel.MetaModelDTO;
import com.jiuqi.va.bizmeta.service.IMetaGroupService;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.MetaInfoDO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.MetaInfoEditionDO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="MetaDataInfoService")
public class MetaDataInfoService {
    @Autowired
    private IMetaDataInfoVersionDao metaInfoHistoryDao;
    @Autowired
    private IMetaDataInfoUserDao metaInfoEditionDao;
    @Autowired
    private IMetaDataInfoDao metaInfoDao;
    private IMetaGroupService metaGroupSerice;

    public IMetaGroupService getMetaGroupSerice() {
        if (this.metaGroupSerice == null) {
            this.metaGroupSerice = (IMetaGroupService)ApplicationContextRegister.getBean(IMetaGroupService.class);
        }
        return this.metaGroupSerice;
    }

    public MetaInfoDTO findHistoryVersionById(UUID infoId) {
        MetaInfoHistoryDO historyDO = new MetaInfoHistoryDO();
        historyDO.setId(infoId);
        MetaInfoHistoryDO resultMetaInfo = (MetaInfoHistoryDO)((Object)this.metaInfoHistoryDao.selectOne((Object)historyDO));
        if (resultMetaInfo != null) {
            MetaInfoDTO infoDTO = new MetaInfoDTO();
            infoDTO.setId(resultMetaInfo.getId());
            infoDTO.setGroupName(resultMetaInfo.getGroupName());
            infoDTO.setMetaType(resultMetaInfo.getMetaType());
            infoDTO.setModuleName(resultMetaInfo.getModuleName());
            infoDTO.setModelName(resultMetaInfo.getModelName());
            infoDTO.setName(resultMetaInfo.getName());
            infoDTO.setTitle(resultMetaInfo.getTitle());
            infoDTO.setVersionNO(resultMetaInfo.getVersionNO());
            infoDTO.setRowVersion(resultMetaInfo.getRowVersion());
            infoDTO.setState(Integer.valueOf(MetaState.DEPLOYED.getValue()));
            infoDTO.setUniqueCode(resultMetaInfo.getUniqueCode());
            return infoDTO;
        }
        return null;
    }

    public List<MetaInfoEditionDO> listAllMetaInfoByGroupName(String moduleName, String metaType, String groupName, UserLoginDTO user) {
        MetaInfoPageDTO pageDTO = new MetaInfoPageDTO();
        pageDTO.setPagination(false);
        pageDTO.setMetaType(metaType);
        pageDTO.setGroupNames(Collections.singletonList(groupName));
        pageDTO.setModule(moduleName);
        if (null != user) {
            pageDTO.setUserName(user.getName());
        }
        return Optional.ofNullable(this.metaInfoEditionDao.selectEditionByGroupName(pageDTO)).orElse(Collections.emptyList());
    }

    public List<MetaInfoDO> getMetaVList(String moduleName, String groupName, String metaType) {
        MetaGroupDTO groupDTO = new MetaGroupDTO();
        groupDTO.setName(groupName);
        groupDTO.setMetaType(metaType);
        groupDTO.setModuleName(moduleName);
        List<String> groupNames = MetaUtils.getGroupNamesByParent(this.getMetaGroupSerice(), groupDTO);
        MetaInfoPageDTO pageDTO = new MetaInfoPageDTO();
        pageDTO.setModule(moduleName);
        pageDTO.setGroupNames(groupNames);
        pageDTO.setMetaType(metaType);
        return this.getMetaVList(pageDTO);
    }

    public List<MetaInfoDO> getMetaVList(MetaInfoPageDTO pageDTO) {
        return this.metaInfoDao.selectByGroupName(pageDTO);
    }

    public List<MetaInfoEditionDO> getMetaEditionList(MetaModelDTO infoDTO) {
        MetaInfoEditionDO paramMetaInfoEdition = new MetaInfoEditionDO();
        paramMetaInfoEdition.setUserName(ShiroUtil.getUser().getId().toString());
        paramMetaInfoEdition.setModuleName(infoDTO.getModuleName());
        paramMetaInfoEdition.setMetaType(infoDTO.getMetaType());
        return this.metaInfoEditionDao.select(paramMetaInfoEdition);
    }

    public Long findMaxVersion(MetaInfoDO infoDO) {
        return this.metaInfoEditionDao.findMaxVersion(infoDO);
    }
}

