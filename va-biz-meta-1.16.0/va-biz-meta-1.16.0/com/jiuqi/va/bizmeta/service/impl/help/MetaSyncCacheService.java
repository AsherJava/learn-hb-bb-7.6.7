/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.config.VaBizBindingConfig
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.MetaDesignDTO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.jiuqi.va.bizmeta.service.impl.help;

import com.jiuqi.va.biz.config.VaBizBindingConfig;
import com.jiuqi.va.bizmeta.cache.MetaCache;
import com.jiuqi.va.bizmeta.common.consts.MetaState;
import com.jiuqi.va.bizmeta.config.VaBizMetaConfig;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO;
import com.jiuqi.va.bizmeta.service.IMetaDataService;
import com.jiuqi.va.bizmeta.service.IMetaInfoService;
import com.jiuqi.va.bizmeta.service.impl.help.MetaDataInfoService;
import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.MetaDesignDTO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component(value="VaMetaSyncCacheService")
public class MetaSyncCacheService {
    private static final Logger logger = LoggerFactory.getLogger(MetaSyncCacheService.class);
    @Autowired(required=false)
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private IMetaDataService metaDataService;
    @Autowired
    private MetaDataInfoService metaDataInfoService;
    private IMetaInfoService metaInfoService;
    public static final String NODE_KEY = "#VaBizBindingNodes";
    public static final String META_ALL_SYNC = "#META_ALL_SYNC#";
    private static final long TIME_OUT = 90000L;
    private static final long WAIT_TIME = 1000L;

    public IMetaInfoService getMetaInfoService() {
        if (this.metaInfoService == null) {
            this.metaInfoService = (IMetaInfoService)ApplicationContextRegister.getBean(IMetaInfoService.class);
        }
        return this.metaInfoService;
    }

    public void pushSyncMsg(UUID id, int operationType) {
        if (!VaBizMetaConfig.isRedisEnable()) {
            return;
        }
        String tenantName = ShiroUtil.getTenantName();
        Map<String, Object> param = this.generateParam(id, operationType);
        String value = JSONUtil.toJSONString(param);
        List<String> survivalNode = this.handleSyncMsg(tenantName, value);
        try {
            Thread.sleep(100L);
        }
        catch (InterruptedException e) {
            logger.error("Interrupted!", e);
            Thread.currentThread().interrupt();
        }
        this.handleSyncResult(survivalNode, tenantName, value);
    }

    private Map<String, Object> generateParam(UUID id, int operationType) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("META_CACHE", id + "#" + operationType);
        if (VaBizMetaConfig.isRedisEnable()) {
            MetaInfoDTO historyVersionById = this.metaDataInfoService.findHistoryVersionById(id);
            String str = historyVersionById.getUniqueCode() + "#" + historyVersionById.getVersionNO() + "#" + historyVersionById.getRowVersion() + "#" + historyVersionById.getModuleName() + "#" + historyVersionById.getMetaType() + "#" + historyVersionById.getModelName();
            param.put("MODELDEFINE_CACHE", str);
        }
        return param;
    }

    private List<String> handleSyncMsg(String tenantName, String value) {
        Map entries = this.stringRedisTemplate.opsForHash().entries((Object)NODE_KEY);
        long timeMillis = System.currentTimeMillis();
        ArrayList<String> survivalNode = new ArrayList<String>();
        for (Map.Entry key : entries.entrySet()) {
            String nodeId = (String)key.getKey();
            String time = (String)key.getValue();
            long nodeTimeMillis = Long.parseLong(time);
            if (timeMillis - nodeTimeMillis > 90000L) {
                this.stringRedisTemplate.opsForHash().delete((Object)NODE_KEY, new Object[]{nodeId});
                this.stringRedisTemplate.opsForSet().pop((Object)(nodeId + META_ALL_SYNC + tenantName), 10000L);
                continue;
            }
            this.stringRedisTemplate.opsForSet().add((Object)(nodeId + META_ALL_SYNC + tenantName), (Object[])new String[]{value});
            survivalNode.add(nodeId);
        }
        if (survivalNode.isEmpty()) {
            return new ArrayList<String>();
        }
        TenantDO redisMsgParam = new TenantDO();
        redisMsgParam.addExtInfo("currNodeId", (Object)VaBizBindingConfig.getCurrNodeId());
        EnvConfig.sendRedisMsg((String)VaBizBindingConfig.getBizBindingCachePub(), (String)JSONUtil.toJSONString((Object)redisMsgParam));
        return survivalNode;
    }

    private int handleSyncResult(List<String> survivalNode, String tenantName, String value) {
        ArrayList<String> retryingNode = new ArrayList<String>();
        for (int i = 0; i < 90; ++i) {
            retryingNode.clear();
            for (String nodeId : survivalNode) {
                Boolean member = this.stringRedisTemplate.opsForSet().isMember((Object)(nodeId + META_ALL_SYNC + tenantName), (Object)value);
                if (!Boolean.TRUE.equals(member)) continue;
                retryingNode.add(nodeId);
            }
            if (retryingNode.isEmpty()) break;
            try {
                Thread.sleep(1000L);
                continue;
            }
            catch (InterruptedException e) {
                logger.error("Interrupted!", e);
                Thread.currentThread().interrupt();
            }
        }
        for (String nodeId : retryingNode) {
            this.stringRedisTemplate.opsForSet().pop((Object)(nodeId + META_ALL_SYNC + tenantName), 1000L);
        }
        return retryingNode.size();
    }

    public MetaDataDTO getDesignByCache(UUID dataId) {
        String tenantName = ShiroUtil.getTenantName();
        if (MetaCache.tenantDesignCache.containsKey(tenantName) && MetaCache.tenantDesignCache.get(tenantName).containsKey(dataId)) {
            return MetaCache.tenantDesignCache.get(tenantName).get(dataId);
        }
        MetaDataDTO dataDTO = this.metaDataService.getMetaDataHistoryById(dataId);
        if (dataDTO != null) {
            this.updateDesignCache(dataDTO, MetaState.APPENDED.getValue());
            return dataDTO;
        }
        MetaDataDTO metaDataVById = this.metaDataService.getMetaDataVById(dataId);
        this.updateDesignCache(metaDataVById, MetaState.APPENDED.getValue());
        return metaDataVById;
    }

    public MetaDataDTO getDesignByDb(UUID dataId) {
        MetaDataDTO dataDTO = this.metaDataService.getMetaDataHistoryById(dataId);
        if (dataDTO != null) {
            return dataDTO;
        }
        return this.metaDataService.getMetaDataVById(dataId);
    }

    public MetaInfoDTO getMetaInfoByCache(String uniqueCode, Long ver) {
        String tenantName = ShiroUtil.getTenantName();
        if (ver != null && MetaCache.tenantMetaInfoCache.containsKey(tenantName) && MetaCache.tenantMetaInfoCache.get(tenantName).containsKey(uniqueCode) && MetaCache.tenantMetaInfoCache.get(tenantName).get(uniqueCode).containsKey(ver)) {
            return MetaCache.tenantMetaInfoCache.get(tenantName).get(uniqueCode).get(ver);
        }
        if (ver != null && ver > 0L) {
            MetaDesignDTO designDTO = new MetaDesignDTO();
            designDTO.setDefineCode(uniqueCode);
            designDTO.setDefineVersion(ver);
            MetaInfoDTO infoByVer = this.getMetaInfoService().getMetaInfoHisByCodeAndVer(designDTO);
            if (infoByVer != null) {
                this.updateMetaInfoCache(infoByVer, MetaState.APPENDED.getValue());
                this.stringRedisTemplate.opsForValue().set((Object)(tenantName + ":" + infoByVer.getUniqueCode() + ":" + infoByVer.getVersionNO()), (Object)infoByVer.getRowVersion().toString());
            }
            return infoByVer;
        }
        MetaInfoDTO infoByUniqueCode = this.getMetaInfoService().getMetaInfoByUniqueCode(uniqueCode);
        if (infoByUniqueCode != null) {
            this.updateMetaInfoCache(infoByUniqueCode, MetaState.DEPLOYED.getValue());
            HashMap<String, String> verMap = new HashMap<String, String>();
            verMap.put("version", infoByUniqueCode.getVersionNO().toString());
            verMap.put("rowVersion", infoByUniqueCode.getRowVersion().toString());
            String redisKey = tenantName + ":" + infoByUniqueCode.getUniqueCode();
            this.stringRedisTemplate.opsForHash().putAll((Object)redisKey, verMap);
        }
        return infoByUniqueCode;
    }

    public MetaInfoDTO getMetaInfoByDb(String uniqueCode, Long ver) {
        if (ver != null && ver > 0L) {
            MetaDesignDTO designDTO = new MetaDesignDTO();
            designDTO.setDefineCode(uniqueCode);
            designDTO.setDefineVersion(ver);
            return this.getMetaInfoService().getMetaInfoHisByCodeAndVer(designDTO);
        }
        return this.getMetaInfoService().getMetaInfoByUniqueCode(uniqueCode);
    }

    public Map<Long, MetaInfoDTO> getMetaInfoCache(String uniqueCode) {
        String tenantName = ShiroUtil.getTenantName();
        if (MetaCache.tenantMetaInfoCache.containsKey(tenantName) && MetaCache.tenantMetaInfoCache.get(tenantName).containsKey(uniqueCode)) {
            return MetaCache.tenantMetaInfoCache.get(tenantName).get(uniqueCode);
        }
        return null;
    }

    public void updateCache(MetaDataDTO metaDataDTO, MetaInfoDTO metaInfoDTO, int operationType) {
        this.updateDesignCache(metaDataDTO, operationType);
        this.updateMetaInfoCache(metaInfoDTO, operationType);
    }

    public void updateDesignCache(MetaDataDTO metaDataDTO, int operationType) {
        UUID designId = metaDataDTO.getId();
        String tenantName = ShiroUtil.getTenantName();
        if (!MetaCache.tenantDesignCache.containsKey(tenantName)) {
            MetaCache.tenantDesignCache.putIfAbsent(tenantName, new ConcurrentHashMap());
        }
        if (MetaState.DELETED.getValue() == operationType) {
            MetaCache.tenantDesignCache.get(tenantName).remove(designId);
        } else {
            MetaCache.tenantDesignCache.get(tenantName).put(designId, metaDataDTO);
        }
    }

    public void updateMetaInfoCache(MetaInfoDTO metaInfoDTO, int operationType) {
        String tenantName = ShiroUtil.getTenantName();
        String uniqueCode = metaInfoDTO.getUniqueCode();
        Long versionNO = metaInfoDTO.getVersionNO();
        if (!MetaCache.tenantMetaInfoCache.containsKey(tenantName)) {
            MetaCache.tenantMetaInfoCache.putIfAbsent(tenantName, new ConcurrentHashMap());
        }
        if (!MetaCache.tenantMetaInfoCache.get(tenantName).containsKey(uniqueCode)) {
            MetaCache.tenantMetaInfoCache.get(tenantName).putIfAbsent(uniqueCode, new ConcurrentHashMap());
        }
        if (MetaState.DELETED.getValue() == operationType) {
            MetaCache.tenantMetaInfoCache.get(tenantName).remove(uniqueCode);
        } else if (MetaState.DEPLOYED.getValue() == operationType) {
            MetaCache.tenantMetaInfoCache.get(tenantName).get(uniqueCode).put(versionNO, metaInfoDTO);
            MetaCache.tenantMetaInfoCache.get(tenantName).get(uniqueCode).put(-1L, metaInfoDTO);
        } else {
            MetaCache.tenantMetaInfoCache.get(tenantName).get(uniqueCode).put(versionNO, metaInfoDTO);
        }
    }

    public void handleStatusCache(String uniqueCode, Long ver, int metaStatus, boolean flag) {
        String tenantName = ShiroUtil.getTenantName();
        if (!MetaCache.tenantSyncStatusCache.containsKey(tenantName)) {
            MetaCache.tenantSyncStatusCache.putIfAbsent(tenantName, new ConcurrentHashMap());
        }
        if (!MetaCache.tenantSyncStatusCache.get(tenantName).containsKey(uniqueCode)) {
            MetaCache.tenantSyncStatusCache.get(tenantName).putIfAbsent(uniqueCode, new ConcurrentHashMap());
        }
        if (flag) {
            MetaCache.tenantSyncStatusCache.get(tenantName).get(uniqueCode).remove(ver);
        } else {
            MetaCache.tenantSyncStatusCache.get(tenantName).get(uniqueCode).put(ver, metaStatus);
        }
    }

    public boolean isSyncCache(String uniqueCode, Long ver) {
        String tenantName = ShiroUtil.getTenantName();
        return MetaCache.tenantSyncStatusCache.containsKey(tenantName) && MetaCache.tenantSyncStatusCache.get(tenantName).containsKey(uniqueCode) && MetaCache.tenantSyncStatusCache.get(tenantName).get(uniqueCode).containsKey(ver);
    }
}

