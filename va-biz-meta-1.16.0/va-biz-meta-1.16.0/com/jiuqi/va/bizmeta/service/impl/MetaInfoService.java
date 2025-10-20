/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.va.domain.biz.DeployDTO
 *  com.jiuqi.va.domain.biz.DeployType
 *  com.jiuqi.va.domain.biz.MetaDataDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.MetaDesignDTO
 *  com.jiuqi.va.domain.meta.MetaInfoDO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.MetaInfoEditionDO
 *  com.jiuqi.va.domain.meta.OperateType
 *  com.jiuqi.va.domain.metadeploy.MetaDataDeployDim
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.BizClient
 *  com.jiuqi.va.feign.util.FeignUtil
 *  com.jiuqi.va.feign.util.RequestContextUtil
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.mapper.domain.UpperKeyMap
 *  org.springframework.data.redis.core.RedisTemplate
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
 */
package com.jiuqi.va.bizmeta.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.va.bizmeta.common.consts.MetaState;
import com.jiuqi.va.bizmeta.common.consts.MetaTypeEnum;
import com.jiuqi.va.bizmeta.common.utils.MetaUtils;
import com.jiuqi.va.bizmeta.common.utils.ModulesServerProvider;
import com.jiuqi.va.bizmeta.common.utils.VersionManage;
import com.jiuqi.va.bizmeta.dao.IMetaDataDesignDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataDesignUserDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataInfoDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataInfoUserDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataInfoVersionDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataLockDao;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDO;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataEditionDO;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataHistoryDO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDTO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoFilterDTO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoHistoryDO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoPageDTO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaLockDo;
import com.jiuqi.va.bizmeta.domain.metamodel.MetaModelDTO;
import com.jiuqi.va.bizmeta.domain.multimodule.ModuleServer;
import com.jiuqi.va.bizmeta.exception.MetaCheckException;
import com.jiuqi.va.bizmeta.service.IMetaBaseInfoService;
import com.jiuqi.va.bizmeta.service.IMetaDataService;
import com.jiuqi.va.bizmeta.service.IMetaGroupService;
import com.jiuqi.va.bizmeta.service.IMetaInfoService;
import com.jiuqi.va.bizmeta.service.IMetaVersionService;
import com.jiuqi.va.bizmeta.service.impl.help.MetaSyncCacheService;
import com.jiuqi.va.bizmeta.util.MetaDataDeployUtils;
import com.jiuqi.va.domain.biz.DeployDTO;
import com.jiuqi.va.domain.biz.DeployType;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.MetaDesignDTO;
import com.jiuqi.va.domain.meta.MetaInfoDO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.MetaInfoEditionDO;
import com.jiuqi.va.domain.meta.OperateType;
import com.jiuqi.va.domain.metadeploy.MetaDataDeployDim;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.BizClient;
import com.jiuqi.va.feign.util.FeignUtil;
import com.jiuqi.va.feign.util.RequestContextUtil;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.mapper.domain.UpperKeyMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class MetaInfoService
implements IMetaInfoService {
    private static final Logger logger = LoggerFactory.getLogger(MetaInfoService.class);
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private IMetaDataInfoDao metaInfoDao;
    @Autowired
    private IMetaDataInfoUserDao metaInfoEditionDao;
    @Autowired
    private IMetaDataInfoVersionDao metaInfoHistoryDao;
    @Autowired
    private IMetaGroupService metaGroupService;
    @Autowired
    private IMetaDataService metaDataService;
    @Autowired
    private IMetaVersionService metaVersionService;
    @Autowired
    private IMetaBaseInfoService metaBaseInfoService;
    @Autowired
    private IMetaDataDesignDao metaDesignDao;
    @Autowired
    private IMetaDataDesignUserDao metaDataDesignUserDao;
    @Autowired
    private IMetaDataLockDao iMetaDataLockDao;
    @Autowired
    private AuthUserClient authUserClient;
    @Autowired
    private MetaSyncCacheService metaSyncCacheService;

    @Override
    @Transactional
    public MetaInfoDTO createMeta(String userName, MetaInfoDTO info) {
        com.jiuqi.va.domain.biz.MetaDataDTO dataDTO = new com.jiuqi.va.domain.biz.MetaDataDTO();
        dataDTO.setMetaType(info.getMetaType());
        dataDTO.setModelName(info.getModelName());
        dataDTO.setModule(info.getModuleName());
        dataDTO.setUniqueCode(MetaUtils.buildUniqueCode(info.getModuleName(), info.getMetaType(), info.getName()));
        dataDTO = this.metaBaseInfoService.gatherMetaData(dataDTO);
        MetaDataDTO dto = new MetaDataDTO();
        dto.setId(UUID.randomUUID());
        dto.setDesignData(dataDTO.getDatas());
        return this.createMeta(userName, info, dto);
    }

    @Override
    public MetaInfoDTO createMeta(String userName, MetaInfoDTO info, MetaDataDTO data) {
        if (!this.checkMetaName(info)) {
            throw new RuntimeException("\u5728\u540c\u6a21\u5757\u540c\u7c7b\u578b\u4e0b\u5143\u6570\u636e\u540d\u79f0\u91cd\u590d");
        }
        MetaGroupDO groupDO = this.metaGroupService.findGroup(info.getModuleName(), info.getMetaType(), info.getGroupName());
        if (groupDO == null) {
            throw new RuntimeException("\u672a\u627e\u5230\u5206\u7ec4");
        }
        this.metaDataService.createMetaData(data);
        MetaInfoEditionDO metaInfoEditionDO = new MetaInfoEditionDO();
        metaInfoEditionDO.setId(data.getId());
        metaInfoEditionDO.setGroupName(info.getGroupName());
        metaInfoEditionDO.setMetaState(Integer.valueOf(MetaState.APPENDED.getValue()));
        metaInfoEditionDO.setMetaType(info.getMetaType());
        metaInfoEditionDO.setModelName(info.getModelName());
        metaInfoEditionDO.setModuleName(info.getModuleName());
        metaInfoEditionDO.setName(info.getName());
        metaInfoEditionDO.setTitle(info.getTitle());
        metaInfoEditionDO.setUserName(userName);
        metaInfoEditionDO.setOrgVersion(0L);
        metaInfoEditionDO.setVersionNO(Long.valueOf(VersionManage.getInstance().newVersion(this.metaVersionService)));
        metaInfoEditionDO.setRowVersion(metaInfoEditionDO.getVersionNO());
        metaInfoEditionDO.setUniqueCode(MetaUtils.buildUniqueCode(info.getModuleName(), info.getMetaType(), info.getName()));
        this.metaInfoEditionDao.insert(metaInfoEditionDO);
        info.setId(metaInfoEditionDO.getId());
        info.setVersionNO(metaInfoEditionDO.getVersionNO());
        info.setRowVersion(metaInfoEditionDO.getRowVersion());
        info.setState(metaInfoEditionDO.getMetaState());
        return info;
    }

    @Override
    @Transactional
    public MetaInfoEditionDO deleteMetaById(String userName, UUID id) {
        MetaInfoDTO infoDTO = this.findMetaById(id);
        if (infoDTO == null) {
            throw new RuntimeException("\u5220\u9664\u7684\u5143\u6570\u636e\u4e0d\u5b58\u5728");
        }
        MetaInfoEditionDO paramMetaInfoEdition = new MetaInfoEditionDO();
        if (MetaState.DEPLOYED.getValue() == infoDTO.getState().intValue()) {
            paramMetaInfoEdition.setId(infoDTO.getId());
            paramMetaInfoEdition.setGroupName(infoDTO.getGroupName());
            paramMetaInfoEdition.setMetaState(Integer.valueOf(MetaState.DELETED.getValue()));
            paramMetaInfoEdition.setMetaType(infoDTO.getMetaType());
            paramMetaInfoEdition.setModelName(infoDTO.getModelName());
            paramMetaInfoEdition.setModuleName(infoDTO.getModuleName());
            paramMetaInfoEdition.setName(infoDTO.getName());
            paramMetaInfoEdition.setOrgVersion(infoDTO.getVersionNO().longValue());
            paramMetaInfoEdition.setTitle(infoDTO.getTitle());
            paramMetaInfoEdition.setUserName(userName);
            paramMetaInfoEdition.setVersionNO(Long.valueOf(VersionManage.getInstance().newVersion(this.metaVersionService)));
            paramMetaInfoEdition.setRowVersion(paramMetaInfoEdition.getVersionNO());
            paramMetaInfoEdition.setUniqueCode(infoDTO.getUniqueCode());
            infoDTO.setState(Integer.valueOf(MetaState.DELETED.getValue()));
            this.metaInfoEditionDao.insert(paramMetaInfoEdition);
            MetaDataDTO metaDataV = this.metaDataService.getMetaDataVById(id);
            MetaDataDTO dataDTO = new MetaDataDTO();
            dataDTO.setId(metaDataV.getId());
            dataDTO.setDesignData(metaDataV.getDesignData());
            this.metaDataService.createMetaData(dataDTO);
        } else {
            paramMetaInfoEdition.setId(infoDTO.getId());
            this.metaInfoEditionDao.delete(paramMetaInfoEdition);
            this.metaDataService.deleteMetaDataById(infoDTO.getId());
            if (MetaTypeEnum.WORKFLOW.getName().equals(infoDTO.getMetaType())) {
                MetaInfoEditionDO editionDO = new MetaInfoEditionDO();
                editionDO.setModuleName(infoDTO.getModuleName());
                editionDO.setMetaType(infoDTO.getMetaType());
                editionDO.setUniqueCode(infoDTO.getUniqueCode());
                editionDO.setVersionNO(infoDTO.getVersionNO());
                editionDO.setUserName(ShiroUtil.getUser().getId());
                this.deleteWorkflowPubParamRel(editionDO);
            }
            paramMetaInfoEdition = null;
        }
        return paramMetaInfoEdition;
    }

    @Override
    public MetaInfoDTO updateMeta(String userName, MetaInfoDTO info, MetaDataDTO data) {
        if (data == null) {
            throw new RuntimeException("\u4fee\u6539\u7684\u5143\u6570\u636e\u8bbe\u8ba1\u6570\u636e\u4e0d\u5b58\u5728");
        }
        MetaInfoEditionDO editionDO = new MetaInfoEditionDO();
        editionDO.setGroupName(info.getGroupName());
        editionDO.setMetaType(info.getMetaType());
        editionDO.setModelName(info.getModelName());
        editionDO.setModuleName(info.getModuleName());
        editionDO.setName(info.getName());
        editionDO.setTitle(info.getTitle());
        editionDO.setUniqueCode(info.getUniqueCode());
        editionDO.setUserName(ShiroUtil.getUser().getId().toString());
        editionDO.setVersionNO(info.getRowVersion());
        editionDO.setRowVersion(Long.valueOf(Calendar.getInstance().getTimeInMillis()));
        if (MetaState.DEPLOYED.getValue() != info.getState().intValue()) {
            this.metaDataService.updateMetaData(data);
            editionDO.setId(info.getId());
            editionDO.setMetaState(info.getState());
            this.metaInfoEditionDao.updateByPrimaryKey(editionDO);
        } else {
            data.setId(UUID.randomUUID());
            this.metaDataService.createMetaData(data);
            editionDO.setId(data.getId());
            editionDO.setOrgVersion(info.getVersionNO().longValue());
            editionDO.setMetaState(Integer.valueOf(MetaState.MODIFIED.getValue()));
            int selectByIndexes = this.metaInfoEditionDao.selectByIndexes(editionDO);
            if (selectByIndexes > 0) {
                throw new RuntimeException("\u6570\u636e\u5df2\u53d1\u751f\u53d8\u5316\uff0c\u8bf7\u5237\u65b0\u5217\u8868\u540e\u91cd\u65b0\u6253\u5f00");
            }
            this.metaInfoEditionDao.insert(editionDO);
        }
        info.setId(editionDO.getId());
        info.setVersionNO(editionDO.getVersionNO());
        info.setRowVersion(editionDO.getRowVersion());
        info.setState(editionDO.getMetaState());
        return info;
    }

    @Override
    public MetaInfoDTO updateMeta(String userName, MetaInfoDTO info) {
        MetaInfoEditionDO editionDO;
        MetaInfoDTO infoDTO = this.findMetaById(info.getId());
        if (infoDTO == null) {
            throw new RuntimeException("\u4fee\u6539\u7684\u5143\u6570\u636e\u4e0d\u5b58\u5728\uff0c\u6216\u5df2\u88ab\u5220\u9664");
        }
        if (MetaState.DEPLOYED.getValue() != infoDTO.getState().intValue()) {
            editionDO = new MetaInfoEditionDO();
            editionDO.setId(info.getId());
            editionDO.setGroupName(info.getGroupName());
            editionDO.setMetaType(info.getMetaType());
            editionDO.setModelName(info.getModelName());
            editionDO.setModuleName(info.getModuleName());
            editionDO.setName(info.getName());
            editionDO.setTitle(info.getTitle());
            if (MetaState.APPENDED.getValue() != infoDTO.getState().intValue()) {
                editionDO.setMetaState(Integer.valueOf(MetaState.MODIFIED.getValue()));
            } else {
                editionDO.setMetaState(Integer.valueOf(MetaState.APPENDED.getValue()));
            }
        } else {
            MetaDataDTO dataDTO = this.metaDataService.getMetaDataById(info.getId());
            infoDTO.setId(info.getId());
            infoDTO.setGroupName(info.getGroupName());
            infoDTO.setMetaType(info.getMetaType());
            infoDTO.setModelName(info.getModelName());
            infoDTO.setModuleName(info.getModuleName());
            infoDTO.setName(info.getName());
            infoDTO.setTitle(info.getTitle());
            infoDTO.setUniqueCode(info.getUniqueCode());
            return this.updateMeta(ShiroUtil.getUser().getId().toString(), infoDTO, dataDTO);
        }
        editionDO.setUserName(ShiroUtil.getUser().getId().toString());
        editionDO.setVersionNO(info.getRowVersion());
        editionDO.setRowVersion(Long.valueOf(Calendar.getInstance().getTimeInMillis()));
        editionDO.setUniqueCode(info.getUniqueCode());
        this.metaInfoEditionDao.updateByPrimaryKeySelective(editionDO);
        info.setState(editionDO.getMetaState());
        info.setVersionNO(editionDO.getVersionNO());
        info.setRowVersion(editionDO.getRowVersion());
        return info;
    }

    @Override
    public MetaInfoEditionDO updateMeta(String userName, MetaDataDTO data) {
        return null;
    }

    @Override
    public MetaInfoDTO findMetaData(String moduleName, String metaType, String name) {
        if (!(StringUtils.hasText(moduleName) && StringUtils.hasText(metaType) && StringUtils.hasText(name))) {
            throw new RuntimeException("\u53c2\u6570\u975e\u6cd5\uff0c\u6a21\u5757\u3001\u5143\u6570\u636e\u7c7b\u578b\u6216\u5143\u6570\u636e\u4fe1\u606f\u540d\u79f0\u4e3a\u7a7a");
        }
        MetaInfoEditionDO paramMetaInfoEdition = new MetaInfoEditionDO();
        paramMetaInfoEdition.setModuleName(moduleName);
        paramMetaInfoEdition.setMetaType(metaType);
        paramMetaInfoEdition.setName(name);
        paramMetaInfoEdition.setUserName(ShiroUtil.getUser().getId().toString());
        MetaInfoEditionDO resultMetaInfoEdition = (MetaInfoEditionDO)this.metaInfoEditionDao.selectOne(paramMetaInfoEdition);
        if (resultMetaInfoEdition != null) {
            MetaInfoDTO infoDTO = new MetaInfoDTO();
            infoDTO.setId(resultMetaInfoEdition.getId());
            infoDTO.setGroupName(resultMetaInfoEdition.getGroupName());
            infoDTO.setMetaType(resultMetaInfoEdition.getMetaType());
            infoDTO.setModuleName(resultMetaInfoEdition.getModuleName());
            infoDTO.setModelName(resultMetaInfoEdition.getModelName());
            infoDTO.setName(resultMetaInfoEdition.getName());
            infoDTO.setTitle(resultMetaInfoEdition.getTitle());
            infoDTO.setVersionNO(resultMetaInfoEdition.getVersionNO());
            infoDTO.setState(resultMetaInfoEdition.getMetaState());
            infoDTO.setUniqueCode(resultMetaInfoEdition.getUniqueCode());
            MetaDataEditionDO record = new MetaDataEditionDO();
            record.setId(resultMetaInfoEdition.getId());
            MetaDataDO selectOne = (MetaDataDO)((Object)this.metaDataDesignUserDao.selectOne((Object)record));
            if (selectOne != null) {
                infoDTO.setDesignData(selectOne.getDesignData());
            }
            return infoDTO;
        }
        MetaInfoDO paramInfoDO = new MetaInfoDO();
        paramInfoDO.setModuleName(moduleName);
        paramInfoDO.setMetaType(metaType);
        paramInfoDO.setName(name);
        MetaInfoDO resultMetaInfo = (MetaInfoDO)this.metaInfoDao.selectOne(paramInfoDO);
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
            infoDTO.setState(Integer.valueOf(MetaState.DEPLOYED.getValue()));
            infoDTO.setUniqueCode(resultMetaInfo.getUniqueCode());
            MetaDataDO record = new MetaDataDO();
            record.setId(resultMetaInfo.getId());
            MetaDataDO selectOne = (MetaDataDO)((Object)this.metaDesignDao.selectOne((Object)record));
            if (selectOne != null) {
                infoDTO.setDesignData(selectOne.getDesignData());
            }
            return infoDTO;
        }
        return null;
    }

    @Override
    public MetaInfoDTO findMeta(String moduleName, String metaType, String name) {
        if (!(StringUtils.hasText(moduleName) && StringUtils.hasText(metaType) && StringUtils.hasText(name))) {
            throw new RuntimeException("\u53c2\u6570\u975e\u6cd5\uff0c\u6a21\u5757\u3001\u5143\u6570\u636e\u7c7b\u578b\u6216\u5143\u6570\u636e\u4fe1\u606f\u540d\u79f0\u4e3a\u7a7a");
        }
        MetaInfoEditionDO paramMetaInfoEdition = new MetaInfoEditionDO();
        paramMetaInfoEdition.setModuleName(moduleName);
        paramMetaInfoEdition.setMetaType(metaType);
        paramMetaInfoEdition.setName(name);
        paramMetaInfoEdition.setUserName(ShiroUtil.getUser().getId().toString());
        MetaInfoEditionDO resultMetaInfoEdition = (MetaInfoEditionDO)this.metaInfoEditionDao.selectOne(paramMetaInfoEdition);
        if (resultMetaInfoEdition != null) {
            MetaInfoDTO infoDTO = new MetaInfoDTO();
            infoDTO.setId(resultMetaInfoEdition.getId());
            infoDTO.setGroupName(resultMetaInfoEdition.getGroupName());
            infoDTO.setMetaType(resultMetaInfoEdition.getMetaType());
            infoDTO.setModuleName(resultMetaInfoEdition.getModuleName());
            infoDTO.setModelName(resultMetaInfoEdition.getModelName());
            infoDTO.setName(resultMetaInfoEdition.getName());
            infoDTO.setTitle(resultMetaInfoEdition.getTitle());
            infoDTO.setVersionNO(resultMetaInfoEdition.getVersionNO());
            infoDTO.setState(resultMetaInfoEdition.getMetaState());
            return infoDTO;
        }
        MetaInfoDO paramInfoDO = new MetaInfoDO();
        paramInfoDO.setModuleName(moduleName);
        paramInfoDO.setMetaType(metaType);
        paramInfoDO.setName(name);
        MetaInfoDO resultMetaInfo = (MetaInfoDO)this.metaInfoDao.selectOne(paramInfoDO);
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
            infoDTO.setState(Integer.valueOf(MetaState.DEPLOYED.getValue()));
            return infoDTO;
        }
        return null;
    }

    @Override
    public MetaInfoDTO findMetaById(UUID infoId) {
        MetaInfoEditionDO resultMetaInfoEdition = this.findMetaEditionById(infoId);
        if (resultMetaInfoEdition != null) {
            MetaInfoDTO infoDTO = new MetaInfoDTO();
            infoDTO.setId(resultMetaInfoEdition.getId());
            infoDTO.setGroupName(resultMetaInfoEdition.getGroupName());
            infoDTO.setMetaType(resultMetaInfoEdition.getMetaType());
            infoDTO.setModuleName(resultMetaInfoEdition.getModuleName());
            infoDTO.setModelName(resultMetaInfoEdition.getModelName());
            infoDTO.setName(resultMetaInfoEdition.getName());
            infoDTO.setTitle(resultMetaInfoEdition.getTitle());
            infoDTO.setVersionNO(resultMetaInfoEdition.getVersionNO());
            infoDTO.setRowVersion(resultMetaInfoEdition.getRowVersion());
            infoDTO.setState(resultMetaInfoEdition.getMetaState());
            infoDTO.setUniqueCode(resultMetaInfoEdition.getUniqueCode());
            return infoDTO;
        }
        MetaInfoDO paramInfoDO = new MetaInfoDO();
        paramInfoDO.setId(infoId);
        MetaInfoDO resultMetaInfo = (MetaInfoDO)this.metaInfoDao.selectOne(paramInfoDO);
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

    @Override
    public List<MetaInfoDTO> getMetaList(MetaInfoPageDTO pageDTO) {
        StringBuilder sb;
        if (pageDTO.getGroupId() != null) {
            MetaGroupDTO groupDTO = this.metaGroupService.getGroupById(pageDTO.getGroupId());
            List<String> groupNames = MetaUtils.getGroupNamesByParent(this.metaGroupService, groupDTO);
            pageDTO.setGroupNames(groupNames);
        }
        ArrayList<MetaInfoDTO> infoDTOs = new ArrayList<MetaInfoDTO>();
        List<MetaInfoDO> resultMetaInfos = this.getMetaVList(pageDTO);
        Iterator<MetaInfoDO> itrMetaInfo = resultMetaInfos.iterator();
        HashMap<String, MetaInfoDO> infoDOHashMap = new HashMap<String, MetaInfoDO>();
        HashMap infoDOTemp = new HashMap();
        while (itrMetaInfo.hasNext()) {
            MetaInfoDO infoDO = itrMetaInfo.next();
            infoDOHashMap.put(infoDO.getName() + infoDO.getMetaType(), infoDO);
        }
        if (!OperateType.EXECUTE.equals((Object)pageDTO.getOperateType())) {
            List<MetaInfoEditionDO> resultMetaInfoEditions = this.getMetaEditionList(pageDTO);
            for (MetaInfoEditionDO metaInfoEditionDO : resultMetaInfoEditions) {
                MetaInfoDTO infoDTO = new MetaInfoDTO();
                infoDTO.setId(metaInfoEditionDO.getId());
                infoDTO.setGroupName(metaInfoEditionDO.getGroupName());
                infoDTO.setMetaType(metaInfoEditionDO.getMetaType());
                infoDTO.setModuleName(metaInfoEditionDO.getModuleName());
                infoDTO.setModelName(metaInfoEditionDO.getModelName());
                infoDTO.setName(metaInfoEditionDO.getName());
                infoDTO.setTitle(metaInfoEditionDO.getTitle());
                infoDTO.setVersionNO(metaInfoEditionDO.getVersionNO());
                infoDTO.setRowVersion(metaInfoEditionDO.getRowVersion());
                infoDTO.setState(metaInfoEditionDO.getMetaState());
                infoDTO.setUniqueCode(metaInfoEditionDO.getUniqueCode());
                infoDTO.setUsername(metaInfoEditionDO.getUserName());
                infoDTOs.add(infoDTO);
                if (metaInfoEditionDO.getMetaState().intValue() == MetaState.APPENDED.getValue()) continue;
                sb = new StringBuilder();
                sb.append(metaInfoEditionDO.getName()).append(metaInfoEditionDO.getMetaType());
                if (!infoDOHashMap.containsKey(sb.toString())) continue;
                infoDOTemp.put(sb.toString(), infoDOHashMap.get(sb.toString()));
            }
        }
        List metaInfoEditionDOS = this.metaInfoEditionDao.select(new MetaInfoEditionDO());
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        for (MetaInfoEditionDO metaEdittion : metaInfoEditionDOS) {
            sb = new StringBuilder();
            sb.append(metaEdittion.getName()).append(metaEdittion.getUserName()).append(metaEdittion.getModuleName()).append(metaEdittion.getMetaType());
            hashMap.put(sb.toString(), 1);
        }
        for (MetaInfoDO metaInfoDO : resultMetaInfos) {
            StringBuilder sbInfo = new StringBuilder();
            sbInfo.append(metaInfoDO.getName()).append(metaInfoDO.getMetaType());
            if (infoDOTemp.containsKey(sbInfo.toString())) continue;
            Integer selectCount = null;
            if (!OperateType.EXECUTE.equals((Object)pageDTO.getOperateType())) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(metaInfoDO.getName()).append(ShiroUtil.getUser().getId()).append(metaInfoDO.getModuleName()).append(metaInfoDO.getMetaType());
                selectCount = (Integer)hashMap.get(sb2.toString());
            }
            if (selectCount != null && selectCount != 0) continue;
            MetaInfoDTO infoDTO = new MetaInfoDTO();
            infoDTO.setId(metaInfoDO.getId());
            infoDTO.setGroupName(metaInfoDO.getGroupName());
            infoDTO.setMetaType(metaInfoDO.getMetaType());
            infoDTO.setModuleName(metaInfoDO.getModuleName());
            infoDTO.setModelName(metaInfoDO.getModelName());
            infoDTO.setName(metaInfoDO.getName());
            infoDTO.setTitle(metaInfoDO.getTitle());
            infoDTO.setVersionNO(metaInfoDO.getVersionNO());
            infoDTO.setRowVersion(metaInfoDO.getRowVersion());
            infoDTO.setState(Integer.valueOf(MetaState.DEPLOYED.getValue()));
            infoDTO.setUniqueCode(metaInfoDO.getUniqueCode());
            infoDTOs.add(infoDTO);
        }
        return infoDTOs;
    }

    @Override
    public int queryCountByGroupId(UUID groupId) {
        return 0;
    }

    @Override
    public List<MetaInfoEditionDO> getMetaEditionList(MetaInfoPageDTO pageDTO) {
        pageDTO.setUserName(ShiroUtil.getUser().getId());
        return this.metaInfoEditionDao.selectEditionByGroupName(pageDTO);
    }

    @Override
    public List<MetaInfoEditionDO> getMetaEditionList(MetaModelDTO infoDTO) {
        MetaInfoEditionDO paramMetaInfoEdition = new MetaInfoEditionDO();
        paramMetaInfoEdition.setUserName(ShiroUtil.getUser().getId());
        paramMetaInfoEdition.setModuleName(infoDTO.getModuleName());
        paramMetaInfoEdition.setMetaType(infoDTO.getMetaType());
        return this.metaInfoEditionDao.select(paramMetaInfoEdition);
    }

    @Override
    public List<MetaInfoDTO> getMetaEditionListByModule(String module) {
        return null;
    }

    @Override
    public List<MetaInfoDO> getMetaListByMetaType(String metaType) {
        MetaInfoDO metaInfoDO = new MetaInfoDO();
        metaInfoDO.setMetaType(metaType);
        return this.metaInfoDao.select(metaInfoDO);
    }

    @Override
    public List<MetaInfoEditionDO> getMetaEditionList(String moduleName, String groupName, String metaType) {
        MetaGroupDTO groupDTO = new MetaGroupDTO();
        groupDTO.setName(groupName);
        groupDTO.setMetaType(metaType);
        groupDTO.setModuleName(moduleName);
        List<String> groupNames = MetaUtils.getGroupNamesByParent(this.metaGroupService, groupDTO);
        MetaInfoPageDTO pageDTO = new MetaInfoPageDTO();
        pageDTO.setModule(moduleName);
        pageDTO.setGroupNames(groupNames);
        pageDTO.setMetaType(metaType);
        return this.getMetaEditionList(pageDTO);
    }

    @Override
    public List<MetaInfoHistoryDO> getMetaHistoryList(String module, String metaType, String name) {
        return null;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deployMetaById(String userName, MetaDataDeployDim metaDataDeployDim, long version) {
        MetaInfoDO metaInfoDO;
        String uuid = "000000000000";
        String orderStr = "";
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes != null) {
            uuid = (String)RequestContextUtil.getAttribute((String)"PUB_LOG_UUID");
            orderStr = (String)RequestContextUtil.getAttribute((String)"PUB_LOG_ITEM_INDEX");
            if (orderStr == null) {
                String metaStr = MetaDataDeployUtils.readMetaStr(metaDataDeployDim.getPath());
                orderStr = String.format("%s\u3010%s\u3011", "", metaStr);
            }
        }
        String tenantName = ShiroUtil.getTenantName();
        UUID id = metaDataDeployDim.getId();
        MetaInfoEditionDO editionDO = this.findMetaEditionById(id);
        MetaLockDo metaLockDo = new MetaLockDo();
        metaLockDo.setUniqueCode(editionDO.getUniqueCode());
        MetaLockDo metaLock = (MetaLockDo)((Object)this.iMetaDataLockDao.selectOne((Object)metaLockDo));
        if (metaLock != null && "super".equals(metaLock.getLockuser())) {
            String mgrFlag = ShiroUtil.getUser().getMgrFlag();
            if (StringUtils.hasText(mgrFlag) && !"super".equals(mgrFlag)) {
                throw new RuntimeException("\u5143\u6570\u636e\u5df2\u88abadmin\u9501\u5b9a\u3002");
            }
        } else if (metaLock != null && !userName.equals(metaLock.getLockuser())) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(metaLock.getLockuser());
            String lockuser = this.authUserClient.get(userDTO).getName();
            throw new RuntimeException("\u5143\u6570\u636e\u5df2\u88ab" + lockuser + "\u9501\u5b9a\u3002");
        }
        MetaDataEditionDO dataEditionDO = this.metaDataService.getMetaDataUserById(id);
        if (editionDO == null || dataEditionDO == null) {
            throw new RuntimeException("\u6570\u636e\u53d1\u751f\u53d8\u5316");
        }
        if (MetaState.DELETED.getValue() != metaDataDeployDim.getState()) {
            com.jiuqi.va.domain.biz.MetaDataDTO metadatadto = new com.jiuqi.va.domain.biz.MetaDataDTO();
            metadatadto.setDatas(dataEditionDO.getDesignData());
            metadatadto.setUniqueCode(editionDO.getUniqueCode());
            metadatadto.setTenantName(tenantName);
            metadatadto.setModule(editionDO.getModuleName());
            metadatadto.setMetaType(editionDO.getMetaType());
            metadatadto.setModelName(editionDO.getModelName());
            R checkData = this.metaDataService.checkMetaData(metadatadto);
            if (checkData.getCode() == 1) {
                throw new RuntimeException("\u6821\u9a8c\u5931\u8d25,\u51fa\u73b0\u5f02\u5e38\u3002");
            }
            ArrayList checkinfos = (ArrayList)checkData.get((Object)"checkInfos");
            boolean errorFlag = checkinfos.stream().anyMatch(checkinfo -> {
                Map checkinfoMap = (Map)checkinfo;
                List checkResults = (List)checkinfoMap.get("checkResults");
                if (CollectionUtils.isEmpty(checkResults)) {
                    return false;
                }
                return checkResults.stream().anyMatch(info -> {
                    Map infoMap = (Map)info;
                    return "ERROR".equals(infoMap.get("type"));
                });
            });
            if (errorFlag) {
                throw new MetaCheckException("\u6821\u9a8c\u89c4\u5219\u4e0d\u901a\u8fc7,\u8bf7\u91cd\u65b0\u68c0\u67e5\u5b9a\u4e49\u3002", checkinfos);
            }
        }
        MetaInfoDO paramInfoDO = new MetaInfoDO();
        paramInfoDO.setUniqueCode(editionDO.getUniqueCode());
        MetaInfoDO resultMetaInfo = (MetaInfoDO)this.metaInfoDao.selectOne(paramInfoDO);
        if (resultMetaInfo != null && resultMetaInfo.getVersionNO() > editionDO.getVersionNO()) {
            throw new RuntimeException("\u5f53\u524d\u53d1\u5e03\u7684\u57fa\u7ebf\u7248\u672c\u4f4e\u4e8e\u5f53\u524d\u6700\u65b0\u7248\u672c\uff0c\u4e0d\u5141\u8bb8\u53d1\u5e03\uff0c\u8bf7\u5220\u9664\u5f53\u524d\u672a\u53d1\u5e03\u7248\u672c\u540e\u83b7\u53d6\u6700\u65b0\u57fa\u7ebf\u7248\u672c\u518d\u8fdb\u884c\u8bbe\u8ba1\u3002");
        }
        MetaInfoDO infoDO = new MetaInfoDO();
        MetaDataDO dataDO = new MetaDataDO();
        DeployDTO deployDTO = new DeployDTO();
        deployDTO.setDeployType(DeployType.UPDATE);
        this.metaSyncCacheService.handleStatusCache(editionDO.getUniqueCode(), editionDO.getVersionNO(), 0, false);
        logger.debug("\u3010\u5143\u6570\u636e\u53d1\u5e03\u3011\u3010{}\u3011{}\u5f00\u59cb\u5904\u7406\u672c\u5730\u7f13\u5b58...", (Object)uuid, (Object)orderStr);
        if (MetaState.DELETED.getValue() == editionDO.getMetaState().intValue()) {
            infoDO.setId(id);
            metaInfoDO = (MetaInfoDO)this.metaInfoDao.selectOne(infoDO);
            this.metaInfoDao.delete(infoDO);
            this.metaDataService.deleteMetaDataVById(id);
            deployDTO.setDeployType(DeployType.DELETE);
            MetaDataDTO metaDataDTO = new MetaDataDTO();
            metaDataDTO.setId(id);
            MetaInfoDTO metaInfoDTO = new MetaInfoDTO();
            metaInfoDTO.setUniqueCode(metaInfoDO.getUniqueCode());
            metaInfoDTO.setVersionNO(metaInfoDO.getVersionNO());
            metaInfoDTO.setRowVersion(metaInfoDO.getRowVersion());
            this.metaSyncCacheService.updateCache(metaDataDTO, metaInfoDTO, MetaState.DELETED.getValue());
            String redisKey = tenantName + ":" + editionDO.getUniqueCode();
            this.redisTemplate.delete((Object)redisKey);
        } else {
            if (MetaState.APPENDED.getValue() != editionDO.getMetaState().intValue()) {
                infoDO.setMetaType(editionDO.getMetaType());
                infoDO.setModuleName(editionDO.getModuleName());
                infoDO.setName(editionDO.getName());
                metaInfoDO = (MetaInfoDO)this.metaInfoDao.selectOne(infoDO);
                if (metaInfoDO == null) {
                    throw new RuntimeException("\u57fa\u7ebf\u7248\u672c\u5df2\u5220\u9664\uff0c\u4e0d\u5141\u8bb8\u53d1\u5e03\uff0c\u4ec5\u53ef\u5220\u9664");
                }
                this.metaDataService.deleteMetaDataVById(metaInfoDO.getId());
                this.metaInfoDao.delete(infoDO);
                deployDTO.setDeployType(DeployType.ADD);
            }
            infoDO.setId(id);
            this.checkGroupExists(editionDO.getTitle(), editionDO.getName(), editionDO.getGroupName(), editionDO.getModuleName(), editionDO.getMetaType());
            infoDO.setGroupName(editionDO.getGroupName());
            infoDO.setMetaType(editionDO.getMetaType());
            infoDO.setModelName(editionDO.getModelName());
            infoDO.setModuleName(editionDO.getModuleName());
            infoDO.setName(editionDO.getName());
            infoDO.setTitle(editionDO.getTitle());
            infoDO.setVersionNO(Long.valueOf(version));
            infoDO.setRowVersion(Long.valueOf(version));
            infoDO.setGroupName(editionDO.getGroupName());
            infoDO.setUniqueCode(editionDO.getUniqueCode());
            this.metaInfoDao.insert(infoDO);
            dataDO.setId(id);
            dataDO.setDesignData(dataEditionDO.getDesignData());
            this.metaDataService.createMetaVData(dataDO);
            MetaDataDTO metaDataDTO = new MetaDataDTO();
            metaDataDTO.setId(id);
            metaDataDTO.setDesignData(dataEditionDO.getDesignData());
            MetaInfoDTO metaInfoDTO = new MetaInfoDTO();
            metaInfoDTO.setId(infoDO.getId());
            metaInfoDTO.setVersionNO(infoDO.getVersionNO());
            metaInfoDTO.setRowVersion(infoDO.getRowVersion());
            metaInfoDTO.setName(infoDO.getName());
            metaInfoDTO.setTitle(infoDO.getTitle());
            metaInfoDTO.setModuleName(infoDO.getModuleName());
            metaInfoDTO.setMetaType(infoDO.getMetaType());
            metaInfoDTO.setGroupName(infoDO.getGroupName());
            metaInfoDTO.setModelName(infoDO.getModelName());
            metaInfoDTO.setState(Integer.valueOf(MetaState.DEPLOYED.getValue()));
            metaInfoDTO.setUniqueCode(infoDO.getUniqueCode());
            metaInfoDTO.setTenantName(infoDO.getTenantName());
            metaInfoDTO.setId(infoDO.getId());
            this.metaSyncCacheService.updateCache(metaDataDTO, metaInfoDTO, MetaState.DEPLOYED.getValue());
            String redisKey = tenantName + ":" + editionDO.getUniqueCode();
            this.redisTemplate.opsForHash().put((Object)redisKey, (Object)"version", (Object)String.valueOf(version));
            this.redisTemplate.opsForHash().put((Object)redisKey, (Object)"rowVersion", (Object)String.valueOf(version));
        }
        this.metaSyncCacheService.handleStatusCache(editionDO.getUniqueCode(), editionDO.getVersionNO(), 0, true);
        logger.debug("\u3010\u5143\u6570\u636e\u53d1\u5e03\u3011\u3010{}\u3011{}\u5904\u7406\u672c\u5730\u7f13\u5b58\u5b8c\u6210", (Object)uuid, (Object)orderStr);
        if (MetaState.DELETED.getValue() != editionDO.getMetaState().intValue()) {
            MetaInfoHistoryDO historyDO = new MetaInfoHistoryDO();
            historyDO.setId(id);
            historyDO.setGroupName(editionDO.getGroupName());
            historyDO.setMetaType(editionDO.getMetaType());
            historyDO.setModelName(editionDO.getModelName());
            historyDO.setModuleName(editionDO.getModuleName());
            historyDO.setName(editionDO.getName());
            historyDO.setTitle(editionDO.getTitle());
            historyDO.setVersionNO(version);
            historyDO.setRowVersion(version);
            historyDO.setGroupName(editionDO.getGroupName());
            historyDO.setUniqueCode(editionDO.getUniqueCode());
            this.metaInfoHistoryDao.insert((Object)historyDO);
            MetaDataHistoryDO dataHistoryDO = new MetaDataHistoryDO();
            dataHistoryDO.setId(id);
            dataHistoryDO.setDesignData(dataEditionDO.getDesignData());
            this.metaDataService.createMetaHData(dataHistoryDO);
            logger.debug("\u3010\u5143\u6570\u636e\u53d1\u5e03\u3011\u3010{}\u3011{}\u5c06\u5f53\u524d\u7248\u672c\u4fe1\u606f\u63d2\u5165\u5230\u5386\u53f2\u7248\u672c\u8868\u3001\u521b\u5efa\u5386\u53f2\u7248\u672c\u5143\u6570\u636e\u8bbe\u8ba1", (Object)uuid, (Object)orderStr);
        }
        this.metaInfoEditionDao.delete(editionDO);
        this.metaDataService.deleteMetaDataById(id);
        logger.debug("\u3010\u5143\u6570\u636e\u53d1\u5e03\u3011\u3010{}\u3011{}\u5df2\u5220\u9664\u7248\u672c\u7f16\u8f91\u8868\u4e2d\u7684\u8bb0\u5f55", (Object)uuid, (Object)orderStr);
        try {
            deployDTO.setDatas(dataEditionDO.getDesignData());
            deployDTO.setUniqueCode(editionDO.getUniqueCode());
            deployDTO.setVersion(Long.valueOf(version));
            deployDTO.setModuleName(metaDataDeployDim.getModuleName());
            deployDTO.addExtInfo("editVersion", (Object)editionDO.getVersionNO());
            R r = this.deployMeta(deployDTO, editionDO.getMetaType());
            if (Integer.valueOf(r.get((Object)"code").toString()) != 0) {
                throw new RuntimeException(r.get((Object)"msg").toString());
            }
            logger.debug("\u3010\u5143\u6570\u636e\u53d1\u5e03\u3011\u3010{}\u3011{}\u5df2\u53d1\u5e03\u5143\u6570\u636e", (Object)uuid, (Object)orderStr);
        }
        catch (Exception e) {
            String metaType = editionDO.getMetaType();
            List metaTypeEnumList = Arrays.stream(MetaTypeEnum.values()).filter(metaTypeEnum -> metaTypeEnum.getName().equals(metaType)).collect(Collectors.toList());
            throw new RuntimeException(((MetaTypeEnum)((Object)metaTypeEnumList.get(0))).getTitle() + "\u670d\u52a1\u53d1\u5e03\u53d1\u751f\u5f02\u5e38", e);
        }
    }

    private void deleteWorkflowPubParamRel(MetaInfoEditionDO editionDO) {
        try {
            ModuleServer itemServer = ModulesServerProvider.getModuleServer(editionDO.getModuleName(), MetaTypeEnum.WORKFLOW.getName());
            BizClient bizClient = (BizClient)FeignUtil.getDynamicClient(BizClient.class, (String)itemServer.getServer(), (String)itemServer.getRealPath());
            R r = bizClient.deletePublicParamRel(editionDO);
            if (r.getCode() != 0) {
                logger.error("\u5220\u9664\u5de5\u4f5c\u6d41\u516c\u5171\u53c2\u6570\u5f15\u7528\u5931\u8d25");
            }
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u5de5\u4f5c\u6d41\u516c\u5171\u53c2\u6570\u5f15\u7528\u5931\u8d25:" + e.getMessage(), e);
        }
    }

    private void checkGroupExists(String title, String name, String groupName, String moduleName, String metaType) {
        MetaGroupDO metaGroupDO = this.metaGroupService.findGroup(moduleName, metaType, groupName);
        if (Objects.isNull((Object)metaGroupDO)) {
            throw new RuntimeException(title + "(" + name + ") \u7684\u4e0a\u7ea7\u5206\u7ec4\u4e0d\u5b58\u5728!");
        }
    }

    private R deployMeta(DeployDTO deployDTO, String metaType) {
        ModuleServer itemServer = ModulesServerProvider.getModuleServer(deployDTO.getModuleName(), metaType);
        BizClient bizClient = (BizClient)FeignUtil.getDynamicClient(BizClient.class, (String)itemServer.getServer(), (String)itemServer.getRealPath());
        return bizClient.publishMeta(deployDTO);
    }

    @Override
    public List<MetaInfoDO> getMetaVList(MetaInfoPageDTO pageDTO) {
        return this.metaInfoDao.selectByGroupName(pageDTO);
    }

    public MetaInfoEditionDO findMetaEditionById(UUID infoId) {
        MetaInfoEditionDO paramMetaInfoEdition = new MetaInfoEditionDO();
        paramMetaInfoEdition.setId(infoId);
        return (MetaInfoEditionDO)this.metaInfoEditionDao.selectOne(paramMetaInfoEdition);
    }

    @Override
    public void deleteMetaByGroup(String userName, MetaInfoDTO info) {
        List<MetaInfoEditionDO> resultMetaInfoEditions = this.getMetaEditionList(info.getModuleName(), info.getGroupName(), info.getMetaType());
        MetaInfoEditionDO editionDO = new MetaInfoEditionDO();
        for (MetaInfoEditionDO metaInfoEditionDO : resultMetaInfoEditions) {
            editionDO.setId(metaInfoEditionDO.getId());
            this.metaInfoEditionDao.delete(editionDO);
            this.metaDataService.deleteMetaDataById(metaInfoEditionDO.getId());
        }
    }

    @Override
    public MetaInfoDTO getMetaInfoByUniqueCode(String uniqueCode) {
        MetaInfoDO metaInfoDO = new MetaInfoDO();
        metaInfoDO.setUniqueCode(uniqueCode);
        MetaInfoDO infoDO = (MetaInfoDO)this.metaInfoDao.selectOne(metaInfoDO);
        if (infoDO == null) {
            return null;
        }
        MetaInfoDTO infoDTO = new MetaInfoDTO();
        infoDTO.setId(infoDO.getId());
        infoDTO.setModelName(infoDO.getModelName());
        infoDTO.setName(infoDO.getName());
        infoDTO.setTitle(infoDO.getTitle());
        infoDTO.setVersionNO(infoDO.getVersionNO());
        infoDTO.setRowVersion(infoDO.getRowVersion());
        infoDTO.setGroupName(infoDO.getGroupName());
        infoDTO.setMetaType(infoDO.getMetaType());
        infoDTO.setModuleName(infoDO.getModuleName());
        infoDTO.setUniqueCode(infoDO.getUniqueCode());
        return infoDTO;
    }

    @Override
    public List<MetaInfoHistoryDO> listMetaInfoHis(MetaInfoHistoryDO historyDO) {
        return this.metaInfoHistoryDao.select((Object)historyDO);
    }

    @Override
    public MetaInfoDTO getMetaInfoHisByCodeAndVer(MetaDesignDTO DesignDTO) {
        MetaInfoHistoryDO metaInfoDO = new MetaInfoHistoryDO();
        metaInfoDO.setUniqueCode(DesignDTO.getDefineCode());
        metaInfoDO.setVersionNO(DesignDTO.getDefineVersion());
        MetaInfoDO infoDO = (MetaInfoDO)this.metaInfoHistoryDao.selectOne((Object)metaInfoDO);
        if (infoDO == null) {
            return null;
        }
        MetaInfoDTO infoDTO = new MetaInfoDTO();
        infoDTO.setId(infoDO.getId());
        infoDTO.setModelName(infoDO.getModelName());
        infoDTO.setName(infoDO.getName());
        infoDTO.setTitle(infoDO.getTitle());
        infoDTO.setVersionNO(infoDO.getVersionNO());
        infoDTO.setRowVersion(infoDO.getRowVersion());
        infoDTO.setGroupName(infoDO.getGroupName());
        infoDTO.setMetaType(infoDO.getMetaType());
        infoDTO.setModuleName(infoDO.getModuleName());
        infoDTO.setUniqueCode(infoDO.getUniqueCode());
        return infoDTO;
    }

    private List<MetaInfoDO> listByMetaTypeModelName(String metaType, String modelName) {
        MetaInfoDO metaInfoDO = new MetaInfoDO();
        metaInfoDO.setMetaType(metaType);
        metaInfoDO.setModelName(modelName);
        return this.metaInfoDao.select(metaInfoDO);
    }

    @Override
    public List<MetaInfoDTO> getMetaInfoListByMetaType(String metaType, String modelName) {
        ArrayList<MetaInfoDTO> metaInfoDTOList = new ArrayList<MetaInfoDTO>();
        List<MetaInfoDO> metaListByMetaType = this.listByMetaTypeModelName(metaType, modelName);
        MetaInfoDTO infoDTO = null;
        for (MetaInfoDO metaInfoDO : metaListByMetaType) {
            infoDTO = new MetaInfoDTO();
            infoDTO.setId(metaInfoDO.getId());
            infoDTO.setGroupName(metaInfoDO.getGroupName());
            infoDTO.setMetaType(metaInfoDO.getMetaType());
            infoDTO.setModuleName(metaInfoDO.getModuleName());
            infoDTO.setModelName(metaInfoDO.getModelName());
            infoDTO.setName(metaInfoDO.getName());
            infoDTO.setTitle(metaInfoDO.getTitle());
            infoDTO.setVersionNO(metaInfoDO.getVersionNO());
            infoDTO.setRowVersion(metaInfoDO.getRowVersion());
            infoDTO.setState(Integer.valueOf(MetaState.DEPLOYED.getValue()));
            infoDTO.setUniqueCode(metaInfoDO.getUniqueCode());
            metaInfoDTOList.add(infoDTO);
        }
        return metaInfoDTOList;
    }

    @Override
    public boolean checkMetaName(MetaInfoDTO info) {
        MetaInfoDTO infoDTO = this.findMeta(info.getModuleName(), info.getMetaType(), info.getName());
        return infoDTO == null;
    }

    @Override
    public List<MetaInfoDO> getMetaListFilter(MetaInfoFilterDTO metaInfoFilterDTO) {
        ArrayList<MetaInfoDO> returnList = new ArrayList<MetaInfoDO>();
        if (!StringUtils.hasText(metaInfoFilterDTO.getTableName())) {
            List<MetaInfoDTO> billMetaInfos = this.metaInfoDao.getNotIncludedDesignMetaList(metaInfoFilterDTO);
            for (MetaInfoDTO billMetaInfo : billMetaInfos) {
                MetaInfoDO metaInfo = new MetaInfoDO();
                metaInfo.setId(billMetaInfo.getId());
                metaInfo.setName(billMetaInfo.getName());
                metaInfo.setTitle(billMetaInfo.getTitle());
                metaInfo.setModuleName(billMetaInfo.getModuleName());
                metaInfo.setGroupName(billMetaInfo.getGroupName());
                metaInfo.setUniqueCode(billMetaInfo.getUniqueCode());
                metaInfo.setModelName(billMetaInfo.getModelName());
                returnList.add(metaInfo);
            }
        } else {
            List<MetaInfoDTO> billMetaInfos = this.metaInfoDao.getMetaListFilter(metaInfoFilterDTO);
            for (MetaInfoDTO billMetaInfo : billMetaInfos) {
                if (!StringUtils.hasText(billMetaInfo.getDesignData())) continue;
                ObjectNode designData = JSONUtil.parseObject((String)billMetaInfo.getDesignData());
                ArrayNode pluginsData = designData.withArray("plugins");
                for (JsonNode plugin : pluginsData) {
                    if (!"data".equals(plugin.get("type").asText())) continue;
                    ArrayNode tablesData = JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)plugin)).withArray("tables");
                    for (JsonNode table : tablesData) {
                        if (!metaInfoFilterDTO.getTableName().equals(table.path("name").asText(null))) continue;
                        MetaInfoDO metaInfo = new MetaInfoDO();
                        metaInfo.setId(billMetaInfo.getId());
                        metaInfo.setName(billMetaInfo.getName());
                        metaInfo.setTitle(billMetaInfo.getTitle());
                        metaInfo.setModuleName(billMetaInfo.getModuleName());
                        metaInfo.setGroupName(billMetaInfo.getGroupName());
                        metaInfo.setUniqueCode(billMetaInfo.getUniqueCode());
                        metaInfo.setModelName(billMetaInfo.getModelName());
                        returnList.add(metaInfo);
                    }
                }
            }
        }
        return returnList;
    }

    @Override
    public List<MetaInfoDO> getMetaInfoList(MetaInfoDO metaInfoDO) {
        return this.metaInfoDao.select(metaInfoDO);
    }

    @Override
    public MetaInfoDTO copyMedta(UUID infoID, MetaInfoDTO info) {
        String uniqueCode = MetaUtils.buildUniqueCode(info.getModuleName(), info.getMetaType(), info.getName());
        MetaDataDTO copyDataDTO = this.metaDataService.getMetaDataById(infoID);
        com.jiuqi.va.domain.biz.MetaDataDTO dataDTO = new com.jiuqi.va.domain.biz.MetaDataDTO();
        dataDTO.setMetaType(info.getMetaType());
        dataDTO.setModelName(info.getModelName());
        dataDTO.setModule(info.getModuleName());
        dataDTO.setUniqueCode(uniqueCode);
        this.metaBaseInfoService.gatherMetaData(dataDTO);
        MetaDataDTO dto = new MetaDataDTO();
        dto.setId(UUID.randomUUID());
        if (copyDataDTO != null && copyDataDTO.getDesignData() != null) {
            String designData = copyDataDTO.getDesignData();
            ObjectNode jsonobject = JSONUtil.parseObject((String)designData);
            jsonobject.put("modelType", info.getModelName());
            jsonobject.put("name", uniqueCode);
            dto.setDesignData(JSONUtil.toJSONString((Object)jsonobject));
        } else {
            dto.setDesignData(dataDTO.getDatas());
        }
        return this.createMeta(ShiroUtil.getUser().getId().toString(), info, dto);
    }

    @Override
    public Integer restoreMetaInfo(String id, Integer cover, MetaInfoDTO metaInfoDTO) {
        MetaInfoEditionDO selectOne;
        MetaInfoEditionDO record;
        if (cover == 1) {
            record = new MetaInfoEditionDO();
            record.setUniqueCode(metaInfoDTO.getUniqueCode());
            record.setUserName(ShiroUtil.getUser().getId());
            selectOne = (MetaInfoEditionDO)this.metaInfoEditionDao.selectOne(record);
            if (selectOne != null) {
                return MetaState.MODIFIED.getValue();
            }
        }
        record = new MetaInfoEditionDO();
        record.setUniqueCode(metaInfoDTO.getUniqueCode());
        record.setUserName(ShiroUtil.getUser().getId());
        selectOne = (MetaInfoEditionDO)this.metaInfoEditionDao.selectOne(record);
        if (selectOne != null) {
            this.deleteMetaById(ShiroUtil.getUser().getId().toString(), selectOne.getId());
        }
        MetaInfoDTO metaInfo = this.getMetaInfoByUniqueCode(metaInfoDTO.getUniqueCode());
        TenantDO param = new TenantDO();
        param.addExtInfo("id", (Object)id);
        MetaDataDTO metaData = this.metaVersionService.getDesignDataById(param);
        metaInfo.setDesignData(JSONUtil.toJSONString((Object)metaData));
        MetaInfoDTO infoDTO = this.findMetaById(metaInfo.getId());
        infoDTO.setId(metaInfo.getId());
        infoDTO.setGroupName(metaInfo.getGroupName());
        infoDTO.setMetaType(metaInfo.getMetaType());
        infoDTO.setModelName(metaInfo.getModelName());
        infoDTO.setModuleName(metaInfo.getModuleName());
        infoDTO.setName(metaInfo.getName());
        infoDTO.setTitle(metaInfo.getTitle());
        infoDTO.setUniqueCode(metaInfoDTO.getUniqueCode());
        this.updateMeta(ShiroUtil.getUser().getId().toString(), infoDTO, metaData);
        return 0;
    }

    @Override
    public MetaInfoDTO getMetaInfoAndData(MetaInfoDTO metaInfoDTO) {
        MetaInfoFilterDTO metaInfoFilterDTO = new MetaInfoFilterDTO();
        metaInfoFilterDTO.setModuleName(metaInfoDTO.getModuleName());
        metaInfoFilterDTO.setMetaType(metaInfoDTO.getMetaType());
        metaInfoFilterDTO.setName(metaInfoDTO.getName());
        metaInfoFilterDTO.setUniqueCode(metaInfoDTO.getUniqueCode());
        return this.metaInfoDao.getMetaInfo(metaInfoFilterDTO);
    }

    @Override
    public List<UpperKeyMap> getMetaEditionGroup(MetaInfoPageDTO pageDTO) {
        pageDTO.setUserName(ShiroUtil.getUser().getId());
        return this.metaInfoEditionDao.getMetaEditionGroup(pageDTO);
    }

    @Override
    public List<UpperKeyMap> getMetaInfoGroup(MetaInfoPageDTO pageDTO) {
        return this.metaInfoEditionDao.getMetaInfoGroup(pageDTO);
    }

    @Override
    public String getFirstPublishedByModelName(MetaInfoHistoryDO metaInfoHistoryDO) {
        List<MetaInfoHistoryDO> metaInfoHistoryDOS = this.metaInfoHistoryDao.selectFirstPublishedByModelName(metaInfoHistoryDO);
        if (CollectionUtils.isEmpty(metaInfoHistoryDOS)) {
            return null;
        }
        return metaInfoHistoryDOS.get(0).getUniqueCode();
    }
}

