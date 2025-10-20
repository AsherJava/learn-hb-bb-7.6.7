/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.biz.DeployDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.MetaInfoDO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.MetaInfoEditionDO
 *  com.jiuqi.va.feign.client.BizClient
 *  com.jiuqi.va.feign.util.FeignUtil
 *  org.springframework.data.redis.core.RedisTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.bizmeta.service.impl;

import com.jiuqi.va.bizmeta.common.utils.ModulesServerProvider;
import com.jiuqi.va.bizmeta.dao.IMetaDataDesignDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataDesignVersionDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataInfoDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataInfoVersionDao;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDO;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataEditionDO;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataHistoryDO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoHistoryDO;
import com.jiuqi.va.bizmeta.domain.multimodule.ModuleServer;
import com.jiuqi.va.bizmeta.service.impl.help.MetaDataInfoService;
import com.jiuqi.va.domain.biz.DeployDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.MetaInfoDO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.MetaInfoEditionDO;
import com.jiuqi.va.feign.client.BizClient;
import com.jiuqi.va.feign.util.FeignUtil;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MetaUpdateHandle {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private IMetaDataDesignVersionDao metaDataDesignVersionDao;
    @Autowired
    private IMetaDataDesignDao metaDataDesignDao;
    @Autowired
    private IMetaDataInfoVersionDao metaInfoVersionDao;
    @Autowired
    private IMetaDataInfoDao metaDataInfoDao;
    @Autowired
    private MetaDataInfoService metaDataInfoService;
    private static final String EXCEPTION_MSG = "\u66f4\u65b0\u6570\u636e\u5df2\u53d1\u751f\u6539\u53d8";

    @Transactional
    public <T> String doUpdate(UUID id, T metaInfo, T metaData) {
        String errorMsg = "";
        this.updateMetaInfoHistory(metaInfo, id, metaData);
        MetaInfoDTO infoDTO = this.metaDataInfoService.findHistoryVersionById(id);
        ModuleServer itemServer = ModulesServerProvider.getModuleServer("*", "workflow");
        BizClient bizClient = (BizClient)FeignUtil.getDynamicClient(BizClient.class, (String)itemServer.getServer(), (String)itemServer.getRealPath());
        DeployDTO deployDTO = new DeployDTO();
        deployDTO.setUniqueCode(infoDTO.getUniqueCode());
        deployDTO.setVersion(infoDTO.getVersionNO());
        if (metaData instanceof MetaDataDO) {
            deployDTO.setDatas(new String(((MetaDataDO)((Object)metaData)).getDesignData()));
        } else {
            deployDTO.setDatas(new String(((MetaDataEditionDO)((Object)metaData)).getDesignData()));
        }
        R r = bizClient.updatePublished(deployDTO);
        if (0 != (Integer)r.get((Object)"code")) {
            throw new RuntimeException((String)r.get((Object)"msg"));
        }
        return errorMsg;
    }

    private <T> void updateMetaInfoHistory(T metaInfo, UUID id, T metaData) {
        MetaInfoDO metaInfoDO = null;
        MetaDataDO metaDataDO = null;
        MetaInfoEditionDO metaInfoEditionDO = null;
        MetaDataEditionDO metaDataEditionDO = null;
        if (metaInfo instanceof MetaInfoDO && metaData instanceof MetaDataDO) {
            metaInfoDO = (MetaInfoDO)metaInfo;
            metaDataDO = (MetaDataDO)((Object)metaData);
        }
        if (metaInfo instanceof MetaInfoEditionDO && metaData instanceof MetaDataEditionDO) {
            metaInfoEditionDO = (MetaInfoEditionDO)metaInfo;
            metaDataEditionDO = (MetaDataEditionDO)((Object)metaData);
        }
        MetaInfoHistoryDO conditionMetaInfoHistoryDO = new MetaInfoHistoryDO();
        conditionMetaInfoHistoryDO.setId(id);
        MetaInfoHistoryDO queryMetaInfoHistoryDO = (MetaInfoHistoryDO)((Object)this.metaInfoVersionDao.selectOne((Object)conditionMetaInfoHistoryDO));
        if (queryMetaInfoHistoryDO == null) {
            throw new RuntimeException(EXCEPTION_MSG);
        }
        if (metaInfoEditionDO == null) {
            metaInfoEditionDO = new MetaInfoEditionDO();
        }
        if (metaInfoDO != null) {
            queryMetaInfoHistoryDO.setGroupName(metaInfoDO.getGroupName());
            queryMetaInfoHistoryDO.setMetaType(metaInfoDO.getMetaType());
            queryMetaInfoHistoryDO.setModelName(metaInfoDO.getModelName());
            queryMetaInfoHistoryDO.setModuleName(metaInfoDO.getModuleName());
            queryMetaInfoHistoryDO.setName(metaInfoDO.getName());
            queryMetaInfoHistoryDO.setTitle(metaInfoDO.getTitle());
            queryMetaInfoHistoryDO.setRowVersion(metaInfoDO.getRowVersion());
        } else {
            queryMetaInfoHistoryDO.setGroupName(metaInfoEditionDO.getGroupName());
            queryMetaInfoHistoryDO.setMetaType(metaInfoEditionDO.getMetaType());
            queryMetaInfoHistoryDO.setModelName(metaInfoEditionDO.getModelName());
            queryMetaInfoHistoryDO.setModuleName(metaInfoEditionDO.getModuleName());
            queryMetaInfoHistoryDO.setName(metaInfoEditionDO.getName());
            queryMetaInfoHistoryDO.setTitle(metaInfoEditionDO.getTitle());
            queryMetaInfoHistoryDO.setRowVersion(metaInfoEditionDO.getRowVersion());
        }
        int updateInfoVersion = this.metaInfoVersionDao.updateByPrimaryKey((Object)queryMetaInfoHistoryDO);
        if (updateInfoVersion == 0) {
            throw new RuntimeException(EXCEPTION_MSG);
        }
        MetaInfoDO conditionMetaInfoDO = new MetaInfoDO();
        conditionMetaInfoDO.setVersionNO(queryMetaInfoHistoryDO.getVersionNO());
        conditionMetaInfoDO.setUniqueCode(queryMetaInfoHistoryDO.getUniqueCode());
        MetaInfoDO selectMetaInfoDO = (MetaInfoDO)this.metaDataInfoDao.selectOne(conditionMetaInfoDO);
        if (selectMetaInfoDO != null) {
            if (metaInfoDO != null) {
                selectMetaInfoDO.setGroupName(metaInfoDO.getGroupName());
                selectMetaInfoDO.setMetaType(metaInfoDO.getMetaType());
                selectMetaInfoDO.setModelName(metaInfoDO.getModelName());
                selectMetaInfoDO.setModuleName(metaInfoDO.getModuleName());
                selectMetaInfoDO.setName(metaInfoDO.getName());
                selectMetaInfoDO.setTitle(metaInfoDO.getTitle());
                selectMetaInfoDO.setRowVersion(metaInfoDO.getRowVersion());
            } else {
                selectMetaInfoDO.setGroupName(metaInfoEditionDO.getGroupName());
                selectMetaInfoDO.setMetaType(metaInfoEditionDO.getMetaType());
                selectMetaInfoDO.setModelName(metaInfoEditionDO.getModelName());
                selectMetaInfoDO.setModuleName(metaInfoEditionDO.getModuleName());
                selectMetaInfoDO.setName(metaInfoEditionDO.getName());
                selectMetaInfoDO.setTitle(metaInfoEditionDO.getTitle());
                selectMetaInfoDO.setRowVersion(metaInfoEditionDO.getRowVersion());
            }
            int updateInfo = this.metaDataInfoDao.updateByPrimaryKey(selectMetaInfoDO);
            if (updateInfo == 0) {
                throw new RuntimeException(EXCEPTION_MSG);
            }
            String tenantName = ShiroUtil.getTenantName();
            String redisKey = tenantName + ":" + queryMetaInfoHistoryDO.getUniqueCode();
            this.redisTemplate.delete((Object)redisKey);
        }
        MetaDataHistoryDO conditionMetaDataHistoryDO = new MetaDataHistoryDO();
        conditionMetaDataHistoryDO.setId(id);
        MetaDataHistoryDO selectMetaDataHistoryDO = (MetaDataHistoryDO)((Object)this.metaDataDesignVersionDao.selectOne((Object)conditionMetaDataHistoryDO));
        if (selectMetaDataHistoryDO == null) {
            throw new RuntimeException(EXCEPTION_MSG);
        }
        selectMetaDataHistoryDO.setDesignData(metaDataEditionDO != null ? metaDataEditionDO.getDesignData() : Objects.requireNonNull(metaDataDO).getDesignData());
        int updateDataHistory = this.metaDataDesignVersionDao.updateByPrimaryKey((Object)selectMetaDataHistoryDO);
        if (updateDataHistory == 0) {
            throw new RuntimeException(EXCEPTION_MSG);
        }
        String tenantName = ShiroUtil.getTenantName();
        String rediskey = tenantName + ":" + queryMetaInfoHistoryDO.getUniqueCode() + ":" + queryMetaInfoHistoryDO.getVersionNO();
        this.redisTemplate.delete((Object)rediskey);
        MetaDataDTO metaDataDTO = new MetaDataDTO();
        metaDataDTO.setId(id);
        metaDataDTO.setDesignData(selectMetaDataHistoryDO.getDesignData());
        MetaDataDO conditionMetaDataDO = new MetaDataDO();
        conditionMetaDataDO.setId(id);
        MetaDataDO selectMetaDataDO = (MetaDataDO)((Object)this.metaDataDesignDao.selectOne((Object)conditionMetaDataDO));
        if (selectMetaDataDO == null) {
            return;
        }
        selectMetaDataDO.setDesignData(metaDataEditionDO != null ? metaDataEditionDO.getDesignData() : metaDataDO.getDesignData());
        int updateData = this.metaDataDesignDao.updateByPrimaryKey((Object)selectMetaDataDO);
        if (updateData == 0) {
            throw new RuntimeException(EXCEPTION_MSG);
        }
    }
}

