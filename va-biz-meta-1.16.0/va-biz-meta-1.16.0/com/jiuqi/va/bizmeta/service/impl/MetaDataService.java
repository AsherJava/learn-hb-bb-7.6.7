/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.biz.MetaDataDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.meta.MetaDesignDTO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.MetaInfoEditionDO
 *  com.jiuqi.va.domain.meta.OperateType
 *  com.jiuqi.va.feign.client.BizClient
 *  com.jiuqi.va.feign.util.FeignUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bizmeta.service.impl;

import com.jiuqi.va.bizmeta.common.consts.MetaState;
import com.jiuqi.va.bizmeta.common.utils.ModulesServerProvider;
import com.jiuqi.va.bizmeta.dao.IMetaDataDesignDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataDesignUserDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataDesignVersionDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataInfoUserDao;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDO;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataEditionDO;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataHistoryDO;
import com.jiuqi.va.bizmeta.domain.multimodule.ModuleServer;
import com.jiuqi.va.bizmeta.service.IMetaDataService;
import com.jiuqi.va.bizmeta.service.impl.help.MetaSyncCacheService;
import com.jiuqi.va.domain.biz.MetaDataDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.meta.MetaDesignDTO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.MetaInfoEditionDO;
import com.jiuqi.va.domain.meta.OperateType;
import com.jiuqi.va.feign.client.BizClient;
import com.jiuqi.va.feign.util.FeignUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MetaDataService
implements IMetaDataService {
    private static final Logger logger = LoggerFactory.getLogger(MetaDataService.class);
    @Autowired
    private IMetaDataInfoUserDao metaInfoEditionDao;
    @Autowired
    private IMetaDataDesignDao metaDesignDao;
    @Autowired
    private IMetaDataDesignUserDao metaDesignEditionDao;
    @Autowired
    private IMetaDataDesignVersionDao metaDesignHistoryDao;

    @Override
    public com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO getMetaDataById(UUID id) {
        MetaDataEditionDO resultMetaDataEdition = this.getMetaDataUserById(id);
        if (resultMetaDataEdition != null) {
            com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO dataDTO = new com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO();
            dataDTO.setId(id);
            dataDTO.setDesignData(resultMetaDataEdition.getDesignData());
            return dataDTO;
        }
        return this.getMetaDataVById(id);
    }

    @Override
    public com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO getMetaDataVById(UUID id) {
        MetaDataDO paramMetaData = new MetaDataDO();
        paramMetaData.setId(id);
        MetaDataDO resultMetaData = (MetaDataDO)((Object)this.metaDesignDao.selectOne((Object)paramMetaData));
        if (resultMetaData != null) {
            com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO dataDTO = new com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO();
            dataDTO.setId(id);
            dataDTO.setDesignData(resultMetaData.getDesignData());
            return dataDTO;
        }
        return null;
    }

    @Override
    public com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO updateMetaData(com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO dataDTO) {
        MetaDataEditionDO paramMetaDataEdition = new MetaDataEditionDO();
        paramMetaDataEdition.setId(dataDTO.getId());
        paramMetaDataEdition.setDesignData(dataDTO.getDesignData());
        this.metaDesignEditionDao.updateByPrimaryKey((Object)paramMetaDataEdition);
        return dataDTO;
    }

    @Override
    public com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO createMetaData(com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO dataDTO) {
        MetaDataEditionDO dataDO = new MetaDataEditionDO();
        dataDO.setId(dataDTO.getId());
        dataDO.setDesignData(dataDTO.getDesignData());
        this.metaDesignEditionDao.insert((Object)dataDO);
        return dataDTO;
    }

    @Override
    public MetaDataDO createMetaVData(MetaDataDO dataDO) {
        this.metaDesignDao.insert((Object)dataDO);
        return dataDO;
    }

    @Override
    public MetaDataHistoryDO createMetaHData(MetaDataHistoryDO dataHistoryDO) {
        this.metaDesignHistoryDao.insert((Object)dataHistoryDO);
        return dataHistoryDO;
    }

    @Override
    public com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO getMetaDataHistoryById(UUID id) {
        com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO dataDTO = null;
        MetaDataHistoryDO consitionDataHistoryDO = new MetaDataHistoryDO();
        consitionDataHistoryDO.setId(id);
        MetaDataHistoryDO queryDataHistoryDO = (MetaDataHistoryDO)((Object)this.metaDesignHistoryDao.selectOne((Object)consitionDataHistoryDO));
        if (queryDataHistoryDO != null) {
            dataDTO = new com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO();
            dataDTO.setId(id);
            dataDTO.setDesignData(queryDataHistoryDO.getDesignData());
            return dataDTO;
        }
        return null;
    }

    @Override
    public MetaDataEditionDO getMetaDataUserById(UUID id) {
        MetaDataEditionDO paramMetaDataEdition = new MetaDataEditionDO();
        paramMetaDataEdition.setId(id);
        return (MetaDataEditionDO)((Object)this.metaDesignEditionDao.selectOne((Object)paramMetaDataEdition));
    }

    @Override
    public int deleteMetaDataById(UUID id) {
        MetaDataEditionDO dataEditionDO = new MetaDataEditionDO();
        dataEditionDO.setId(id);
        return this.metaDesignEditionDao.delete((Object)dataEditionDO);
    }

    @Override
    public int deleteMetaDataVById(UUID id) {
        MetaDataDO metaDataDO = new MetaDataDO();
        metaDataDO.setId(id);
        return this.metaDesignDao.delete((Object)metaDataDO);
    }

    @Override
    public R getDatasByMetaType(TenantDO tenantDO) {
        R r = R.ok();
        String metaType = (String)tenantDO.getExtInfo("metaType");
        MetaInfoDTO metaInfoDTO = new MetaInfoDTO();
        metaInfoDTO.setMetaType(metaType);
        metaInfoDTO.setTenantName(tenantDO.getTenantName());
        List<String> metaDataList = this.metaDesignDao.getMetaDataByMetaType(metaInfoDTO);
        ArrayList list = new ArrayList();
        metaDataList.forEach(info -> list.add(info));
        r.put("rows", (Object)list.size());
        r.put("metaDatas", list);
        return r;
    }

    @Override
    public R checkMetaData(MetaDataDTO metaDataDTO) {
        ModuleServer itemServer = ModulesServerProvider.getModuleServer(metaDataDTO.getModule(), metaDataDTO.getMetaType());
        BizClient bizClient = (BizClient)FeignUtil.getDynamicClient(BizClient.class, (String)itemServer.getServer(), (String)itemServer.getRealPath());
        return bizClient.checkMetaData(metaDataDTO);
    }

    @Override
    public List<String> getPublishedDatasByMetaType(TenantDO tenantDO) {
        String metaType = (String)tenantDO.getExtInfo("metaType");
        MetaInfoDTO metaInfoDTO = new MetaInfoDTO();
        if (!StringUtils.isEmpty(metaType)) {
            metaInfoDTO.setMetaType(metaType);
        }
        metaInfoDTO.setState(Integer.valueOf(MetaState.DEPLOYED.getValue()));
        metaInfoDTO.setTenantName(tenantDO.getTenantName());
        return this.metaDesignDao.getMetaDataByMetaType(metaInfoDTO);
    }

    @Override
    public MetaInfoDTO getMetaDesignByUniqueCode(MetaDesignDTO designParam) throws Exception {
        try {
            MetaInfoDTO resultData = new MetaInfoDTO();
            com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO dataDTO = null;
            String defineCode = designParam.getDefineCode();
            boolean notFoundUserData = false;
            if (OperateType.DESIGN.equals((Object)designParam.getOperateType())) {
                UUID id = designParam.getId();
                if (id == null && !StringUtils.hasText(defineCode)) {
                    throw new NullPointerException("\u7f3a\u5c11\u5fc5\u8981\u53c2\u6570@id\u3001@defineCode");
                }
                MetaInfoEditionDO param = new MetaInfoEditionDO();
                param.setId(id);
                param.setUniqueCode(defineCode);
                MetaInfoEditionDO currEditionObj = (MetaInfoEditionDO)this.metaInfoEditionDao.selectOne(param);
                if (currEditionObj == null) {
                    notFoundUserData = true;
                } else {
                    id = currEditionObj.getId();
                    resultData.setId(id);
                    resultData.setModelName(currEditionObj.getModelName());
                    resultData.setModuleName(currEditionObj.getModuleName());
                    resultData.setName(currEditionObj.getName());
                    resultData.setTitle(currEditionObj.getTitle());
                    resultData.setVersionNO(currEditionObj.getVersionNO());
                    resultData.setRowVersion(currEditionObj.getRowVersion());
                    resultData.setMetaType(currEditionObj.getMetaType());
                    resultData.setGroupName(currEditionObj.getGroupName());
                    resultData.setUniqueCode(defineCode);
                    resultData.setState(currEditionObj.getMetaState());
                    dataDTO = this.getMetaDataById(id);
                }
            }
            if (OperateType.EXECUTE.equals((Object)designParam.getOperateType()) || notFoundUserData) {
                MetaSyncCacheService metaSyncCacheService = (MetaSyncCacheService)ApplicationContextRegister.getBean(MetaSyncCacheService.class);
                Long defineVersion = designParam.getDefineVersion();
                Long cacheVersion = defineVersion != null && defineVersion > 0L ? defineVersion : -1L;
                try {
                    boolean syncFlag = true;
                    while (syncFlag) {
                        syncFlag = metaSyncCacheService.isSyncCache(defineCode, cacheVersion);
                        if (!syncFlag) continue;
                        try {
                            Thread.sleep(100L);
                        }
                        catch (InterruptedException e) {
                            logger.error("Interrupted!", e);
                            Thread.currentThread().interrupt();
                        }
                    }
                }
                catch (Exception e) {
                    logger.error("\u5143\u6570\u636e\u7f13\u5b58\u540c\u6b65\u672a\u5b8c\u6210 ", e);
                    throw new Exception("\u83b7\u53d6\u5143\u6570\u636e\u8bbe\u8ba1\u5f02\u5e38\uff1a" + e.getMessage(), e);
                }
                MetaInfoDTO infoDTO = metaSyncCacheService.getMetaInfoByCache(defineCode, cacheVersion);
                com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO metaDataDTO = dataDTO = infoDTO != null ? metaSyncCacheService.getDesignByCache(infoDTO.getId()) : null;
                if (infoDTO != null) {
                    resultData.setId(infoDTO.getId());
                    resultData.setModelName(infoDTO.getModelName());
                    resultData.setModuleName(infoDTO.getModuleName());
                    resultData.setName(infoDTO.getName());
                    resultData.setTitle(infoDTO.getTitle());
                    resultData.setVersionNO(infoDTO.getVersionNO());
                    resultData.setRowVersion(infoDTO.getRowVersion());
                    resultData.setMetaType(infoDTO.getMetaType());
                    resultData.setGroupName(infoDTO.getGroupName());
                    resultData.setUniqueCode(defineCode);
                    resultData.setState(Integer.valueOf(MetaState.DEPLOYED.getValue()));
                }
            }
            if (dataDTO == null) {
                return null;
            }
            resultData.setDesignData(dataDTO.getDesignData());
            return resultData;
        }
        catch (Exception e) {
            throw new Exception("\u83b7\u53d6\u5143\u6570\u636e\u8bbe\u8ba1\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
    }
}

