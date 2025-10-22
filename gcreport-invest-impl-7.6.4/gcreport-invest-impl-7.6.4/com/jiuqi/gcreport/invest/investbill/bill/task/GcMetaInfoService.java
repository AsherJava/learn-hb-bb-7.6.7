/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.nr.common.util.JsonUtil
 *  com.jiuqi.va.bill.controller.BillMetaController
 *  com.jiuqi.va.biz.utils.R
 *  com.jiuqi.va.bizmeta.common.consts.MetaState
 *  com.jiuqi.va.bizmeta.common.consts.MetaTypeEnum
 *  com.jiuqi.va.bizmeta.dao.IMetaDataInfoDao
 *  com.jiuqi.va.bizmeta.dao.IMetaDataInfoUserDao
 *  com.jiuqi.va.bizmeta.dao.IMetaDataInfoVersionDao
 *  com.jiuqi.va.bizmeta.dao.IMetaDataLockDao
 *  com.jiuqi.va.bizmeta.domain.metadata.MetaDataDO
 *  com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO
 *  com.jiuqi.va.bizmeta.domain.metadata.MetaDataEditionDO
 *  com.jiuqi.va.bizmeta.domain.metadata.MetaDataHistoryDO
 *  com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDO
 *  com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoHistoryDO
 *  com.jiuqi.va.bizmeta.domain.metainfo.MetaLockDo
 *  com.jiuqi.va.bizmeta.exception.MetaCheckException
 *  com.jiuqi.va.bizmeta.service.IMetaDataService
 *  com.jiuqi.va.bizmeta.service.IMetaGroupService
 *  com.jiuqi.va.bizmeta.service.impl.MetaInfoService
 *  com.jiuqi.va.bizmeta.service.impl.help.MetaSyncCacheService
 *  com.jiuqi.va.domain.biz.DeployDTO
 *  com.jiuqi.va.domain.biz.DeployType
 *  com.jiuqi.va.domain.biz.MetaDataDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.MetaInfoDO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.MetaInfoEditionDO
 *  com.jiuqi.va.domain.metadeploy.MetaDataDeployDim
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  org.springframework.data.redis.core.RedisTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.invest.investbill.bill.task;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.nr.common.util.JsonUtil;
import com.jiuqi.va.bill.controller.BillMetaController;
import com.jiuqi.va.bizmeta.common.consts.MetaState;
import com.jiuqi.va.bizmeta.common.consts.MetaTypeEnum;
import com.jiuqi.va.bizmeta.dao.IMetaDataInfoDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataInfoUserDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataInfoVersionDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataLockDao;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDO;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataEditionDO;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataHistoryDO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoHistoryDO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaLockDo;
import com.jiuqi.va.bizmeta.exception.MetaCheckException;
import com.jiuqi.va.bizmeta.service.IMetaDataService;
import com.jiuqi.va.bizmeta.service.IMetaGroupService;
import com.jiuqi.va.bizmeta.service.impl.MetaInfoService;
import com.jiuqi.va.bizmeta.service.impl.help.MetaSyncCacheService;
import com.jiuqi.va.domain.biz.DeployDTO;
import com.jiuqi.va.domain.biz.DeployType;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.MetaInfoDO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.MetaInfoEditionDO;
import com.jiuqi.va.domain.metadeploy.MetaDataDeployDim;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Component
public class GcMetaInfoService {
    private static final Logger logger = LoggerFactory.getLogger(GcMetaInfoService.class);
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
    private IMetaDataLockDao iMetaDataLockDao;
    @Autowired
    private AuthUserClient authUserClient;
    @Autowired
    private MetaSyncCacheService metaSyncCacheService;
    @Autowired
    private BillMetaController billMetaController;
    @Autowired
    private MetaInfoService metaInfoService;

    @Transactional(rollbackFor={Exception.class})
    public void deployMetaById(String userName, MetaDataDeployDim metaDataDeployDim, long version) {
        MetaInfoDO metaInfoDO;
        String tenantName = ShiroUtil.getTenantName();
        UUID id = metaDataDeployDim.getId();
        MetaInfoEditionDO editionDO = this.metaInfoService.findMetaEditionById(id);
        MetaLockDo metaLockDo = new MetaLockDo();
        metaLockDo.setUniqueCode(editionDO.getUniqueCode());
        MetaLockDo metaLock = (MetaLockDo)this.iMetaDataLockDao.selectOne((Object)metaLockDo);
        if (metaLock != null && !userName.equals(metaLock.getLockuser())) {
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
            R checkData = this.checkMetaData(metadatadto);
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
                throw new MetaCheckException("\u6821\u9a8c\u89c4\u5219\u4e0d\u901a\u8fc7,\u8bf7\u91cd\u65b0\u68c0\u67e5\u5b9a\u4e49\u3002", (List)checkinfos);
            }
        }
        MetaInfoDO paramInfoDO = new MetaInfoDO();
        paramInfoDO.setUniqueCode(editionDO.getUniqueCode());
        MetaInfoDO resultMetaInfo = (MetaInfoDO)this.metaInfoDao.selectOne((Object)paramInfoDO);
        if (resultMetaInfo != null && resultMetaInfo.getVersionNO() > editionDO.getVersionNO()) {
            throw new RuntimeException("\u5f53\u524d\u53d1\u5e03\u7684\u57fa\u7ebf\u7248\u672c\u4f4e\u4e8e\u5f53\u524d\u6700\u65b0\u7248\u672c\uff0c\u4e0d\u5141\u8bb8\u53d1\u5e03\uff0c\u8bf7\u5220\u9664\u5f53\u524d\u672a\u53d1\u5e03\u7248\u672c\u540e\u83b7\u53d6\u6700\u65b0\u57fa\u7ebf\u7248\u672c\u518d\u8fdb\u884c\u8bbe\u8ba1\u3002");
        }
        MetaInfoDO infoDO = new MetaInfoDO();
        MetaDataDO dataDO = new MetaDataDO();
        DeployDTO deployDTO = new DeployDTO();
        deployDTO.setDeployType(DeployType.UPDATE);
        this.metaSyncCacheService.handleStatusCache(editionDO.getUniqueCode(), editionDO.getVersionNO(), 0, false);
        if (MetaState.DELETED.getValue() == editionDO.getMetaState().intValue()) {
            infoDO.setId(id);
            metaInfoDO = (MetaInfoDO)this.metaInfoDao.selectOne((Object)infoDO);
            this.metaInfoDao.delete((Object)infoDO);
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
                metaInfoDO = (MetaInfoDO)this.metaInfoDao.selectOne((Object)infoDO);
                if (metaInfoDO == null) {
                    throw new RuntimeException("\u57fa\u7ebf\u7248\u672c\u5df2\u5220\u9664\uff0c\u4e0d\u5141\u8bb8\u53d1\u5e03\uff0c\u4ec5\u53ef\u5220\u9664");
                }
                this.metaDataService.deleteMetaDataVById(metaInfoDO.getId());
                this.metaInfoDao.delete((Object)infoDO);
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
            this.metaInfoDao.insert((Object)infoDO);
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
        if (MetaState.DELETED.getValue() != editionDO.getMetaState().intValue()) {
            MetaInfoHistoryDO historyDO = new MetaInfoHistoryDO();
            historyDO.setId(id);
            historyDO.setGroupName(editionDO.getGroupName());
            historyDO.setMetaType(editionDO.getMetaType());
            historyDO.setModelName(editionDO.getModelName());
            historyDO.setModuleName(editionDO.getModuleName());
            historyDO.setName(editionDO.getName());
            historyDO.setTitle(editionDO.getTitle());
            historyDO.setVersionNO(Long.valueOf(version));
            historyDO.setRowVersion(Long.valueOf(version));
            historyDO.setGroupName(editionDO.getGroupName());
            historyDO.setUniqueCode(editionDO.getUniqueCode());
            this.metaInfoHistoryDao.insert((Object)historyDO);
            MetaDataHistoryDO dataHistoryDO = new MetaDataHistoryDO();
            dataHistoryDO.setId(id);
            dataHistoryDO.setDesignData(dataEditionDO.getDesignData());
            this.metaDataService.createMetaHData(dataHistoryDO);
        }
        this.metaInfoEditionDao.delete((Object)editionDO);
        this.metaDataService.deleteMetaDataById(id);
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
        }
        catch (Exception e) {
            String metaType = editionDO.getMetaType();
            List metaTypeEnumList = Arrays.stream(MetaTypeEnum.values()).filter(metaTypeEnum -> metaTypeEnum.getName().equals(metaType)).collect(Collectors.toList());
            throw new RuntimeException(((MetaTypeEnum)metaTypeEnumList.get(0)).getTitle() + "\u670d\u52a1\u53d1\u5e03\u53d1\u751f\u5f02\u5e38", e);
        }
    }

    private void checkGroupExists(String title, String name, String groupName, String moduleName, String metaType) {
        MetaGroupDO metaGroupDO = this.metaGroupService.findGroup(moduleName, metaType, groupName);
        if (Objects.isNull(metaGroupDO)) {
            throw new RuntimeException(title + "(" + name + ") \u7684\u4e0a\u7ea7\u5206\u7ec4\u4e0d\u5b58\u5728!");
        }
    }

    private R deployMeta(DeployDTO deployDTO, String metaType) {
        com.jiuqi.va.biz.utils.R r = this.billMetaController.deploy(deployDTO);
        try {
            return (R)JsonUtil.toObject((String)JsonUtil.objectToJson((Object)r), R.class);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessRuntimeException((Throwable)e);
        }
    }

    private R checkMetaData(com.jiuqi.va.domain.biz.MetaDataDTO metaDataDTO) {
        R r = this.billMetaController.checkMetaData(metaDataDTO);
        try {
            return (R)JsonUtil.toObject((String)JsonUtil.objectToJson((Object)r), R.class);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessRuntimeException((Throwable)e);
        }
    }

    public MetaInfoDTO findMeta(String moduleName, String metaType, String name) {
        return this.metaInfoService.findMeta(moduleName, metaType, name);
    }

    public MetaInfoDTO updateMeta(String userName, MetaInfoDTO info, MetaDataDTO data) {
        return this.metaInfoService.updateMeta(userName, info, data);
    }
}

