/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.basedata.service.impl.help;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.va.basedata.common.BaseDataAsyncTask;
import com.jiuqi.va.basedata.common.BaseDataDefineExtendUtil;
import com.jiuqi.va.basedata.config.VaBasedataCoreConfig;
import com.jiuqi.va.basedata.dao.VaBaseDataDefineDao;
import com.jiuqi.va.basedata.domain.BaseDataDefineSyncCacheDTO;
import com.jiuqi.va.basedata.service.impl.help.BaseDataCacheService;
import com.jiuqi.va.basedata.storage.BaseDataDefineStorage;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component(value="vaBaseDataDefineCacheService")
public class BaseDataDefineCacheService {
    private static Logger logger = LoggerFactory.getLogger(BaseDataDefineCacheService.class);
    @Autowired
    private VaBaseDataDefineDao baseDataDefineDao;
    @Autowired
    private BaseDataAsyncTask baseDataAsyncTask;
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, BigDecimal>> dataVerMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, Map<String, BaseDataDefineDO>> dataCodeMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, Boolean> lockMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, Boolean> checkSyncMap = new ConcurrentHashMap();
    private BaseDataCacheService baseDataCacheService = null;

    private BaseDataCacheService getBaseDataCacheService() {
        if (this.baseDataCacheService == null) {
            this.baseDataCacheService = (BaseDataCacheService)ApplicationContextRegister.getBean(BaseDataCacheService.class);
        }
        return this.baseDataCacheService;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void handleSyncCacheMsg(BaseDataDefineSyncCacheDTO bddscd) {
        String tenantName = bddscd.getTenantName();
        try {
            if (bddscd.isForceUpdate()) {
                dataCodeMap.remove(tenantName);
                return;
            }
            BaseDataDefineDO define = bddscd.getBaseDataDefine();
            if (define == null) {
                return;
            }
            if (bddscd.isRemove()) {
                Map<String, BaseDataDefineDO> tenantCacheMap = dataCodeMap.get(tenantName);
                if (tenantCacheMap == null) {
                    return;
                }
                tenantCacheMap.remove(define.getName());
                return;
            }
            ConcurrentHashMap<String, BigDecimal> tenantVer = dataVerMap.get(tenantName);
            if (tenantVer == null) {
                dataVerMap.putIfAbsent(tenantName, new ConcurrentHashMap());
                tenantVer = dataVerMap.get(tenantName);
            }
            tenantVer.put(define.getName(), new BigDecimal(define.getModifytime().getTime()));
        }
        catch (Throwable e) {
            logger.error("\u679a\u4e3e\u540c\u6b65\u6d88\u606f\u5f02\u5e38", e);
        }
        finally {
            if (EnvConfig.getCurrNodeId().equals(bddscd.getCurrNodeId())) {
                checkSyncMap.put(bddscd.getCheckKey(), true);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void syncCache(String tenantName, boolean forceUpdate) {
        block36: {
            String lockKey;
            block37: {
                List<BaseDataDefineDO> listAll;
                boolean bl;
                boolean locked;
                HashMap<String, BigDecimal> currVerMap;
                ConcurrentHashMap<String, BigDecimal> tenantVer;
                Map<String, BaseDataDefineDO> tenanCacheMap;
                block33: {
                    block34: {
                        block35: {
                            BigDecimal startVer;
                            boolean first;
                            block30: {
                                block31: {
                                    block32: {
                                        boolean dataChanged;
                                        String allLockKey = tenantName;
                                        while (lockMap.get(allLockKey) != null) {
                                            try {
                                                Thread.sleep(100L);
                                            }
                                            catch (InterruptedException e) {
                                                logger.error("baseDataDefineCacheWaittingErro", e);
                                                Thread.currentThread().interrupt();
                                            }
                                        }
                                        first = false;
                                        tenanCacheMap = dataCodeMap.get(tenantName);
                                        if (tenanCacheMap == null) {
                                            first = true;
                                            forceUpdate = true;
                                        }
                                        boolean bl2 = dataChanged = (tenantVer = dataVerMap.get(tenantName)) != null && !tenantVer.isEmpty();
                                        if (!forceUpdate && !dataChanged) {
                                            return;
                                        }
                                        currVerMap = null;
                                        startVer = null;
                                        if (tenantVer != null && !tenantVer.isEmpty() && !(currVerMap = new HashMap<String, BigDecimal>(tenantVer)).isEmpty()) {
                                            startVer = (BigDecimal)Collections.min(currVerMap.values());
                                        }
                                        if (!forceUpdate && dataChanged && startVer == null) {
                                            return;
                                        }
                                        lockKey = startVer == null ? allLockKey : allLockKey + startVer.toString();
                                        locked = lockMap.putIfAbsent(lockKey, true) != null;
                                        if (!locked) break block30;
                                        while (lockMap.get(lockKey) != null) {
                                            try {
                                                Thread.sleep(100L);
                                            }
                                            catch (InterruptedException e) {
                                                logger.error("baseDataDefineCacheWaittingErro", e);
                                                Thread.currentThread().interrupt();
                                            }
                                        }
                                        this.syncCache(tenantName, forceUpdate);
                                        if (locked) break block31;
                                        if (currVerMap == null) break block32;
                                        for (Map.Entry entry2 : currVerMap.entrySet()) {
                                            tenantVer.remove(entry2.getKey(), entry2.getValue());
                                        }
                                    }
                                    lockMap.remove(lockKey);
                                }
                                return;
                            }
                            BaseDataDefineDTO param = new BaseDataDefineDTO();
                            param.setTenantName(tenantName);
                            bl = forceUpdate || startVer == null;
                            listAll = null;
                            if (bl) {
                                listAll = this.baseDataDefineDao.list(param);
                            } else {
                                param.setModifytime(new Date(startVer.longValue()));
                                listAll = this.baseDataDefineDao.selectByStartVer(param);
                            }
                            if (first) {
                                tenanCacheMap = new ConcurrentHashMap<String, BaseDataDefineDO>();
                                dataCodeMap.put(tenantName, tenanCacheMap);
                            }
                            if (listAll != null && !listAll.isEmpty()) break block33;
                            if (bl) {
                                tenanCacheMap.clear();
                            }
                            if (locked) break block34;
                            if (currVerMap == null) break block35;
                            for (Map.Entry e : currVerMap.entrySet()) {
                                tenantVer.remove(e.getKey(), e.getValue());
                            }
                        }
                        lockMap.remove(lockKey);
                    }
                    return;
                }
                try {
                    HashSet<String> codeSet = new HashSet<String>();
                    for (BaseDataDefineDO baseDataDefineDO : listAll) {
                        baseDataDefineDO.setTenantName(tenantName);
                        baseDataDefineDO.setLocked(true);
                        BaseDataDefineDO oldDefine = tenanCacheMap.get(baseDataDefineDO.getName());
                        tenanCacheMap.put(baseDataDefineDO.getName(), baseDataDefineDO);
                        codeSet.add(baseDataDefineDO.getName());
                        if (oldDefine == null) continue;
                        if (baseDataDefineDO.getCachedisabled() != null && baseDataDefineDO.getCachedisabled() == 1 && (oldDefine.getCachedisabled() == null || oldDefine.getCachedisabled() == 0)) {
                            BaseDataDO dataParam = new BaseDataDO();
                            dataParam.setTenantName(tenantName);
                            dataParam.setTableName(baseDataDefineDO.getName());
                            this.baseDataAsyncTask.execute(() -> this.getBaseDataCacheService().cleanTableCache(dataParam, false));
                            continue;
                        }
                        if (baseDataDefineDO.getDummyflag() != null && baseDataDefineDO.getDummyflag() != 0) continue;
                        this.baseDataAsyncTask.execute(() -> this.syncSensitive(oldDefine, baseDataDefineDO));
                    }
                    if (bl) {
                        tenanCacheMap.entrySet().removeIf(entry -> !codeSet.contains(entry.getKey()));
                    }
                    if (locked) break block36;
                    if (currVerMap == null) break block37;
                }
                catch (Throwable e) {
                    try {
                        logger.error("\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u7f13\u5b58\u540c\u6b65\u5f02\u5e38", e);
                        break block36;
                    }
                    catch (Throwable throwable) {
                        throw throwable;
                    }
                    finally {
                        if (!locked) {
                            if (currVerMap != null) {
                                for (Map.Entry entry3 : currVerMap.entrySet()) {
                                    tenantVer.remove(entry3.getKey(), entry3.getValue());
                                }
                            }
                            lockMap.remove(lockKey);
                        }
                    }
                }
                for (Map.Entry entry4 : currVerMap.entrySet()) {
                    tenantVer.remove(entry4.getKey(), entry4.getValue());
                }
            }
            lockMap.remove(lockKey);
        }
    }

    private void syncSensitive(BaseDataDefineDO oldDefine, BaseDataDefineDO newDefine) {
        Map<String, String> oldMap = this.getSensitiveFields(oldDefine);
        Map<String, String> newMap = this.getSensitiveFields(newDefine);
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName(newDefine.getName());
        dataModelDTO.setDeepClone(Boolean.valueOf(false));
        DataModelDO dataModel = BaseDataDefineStorage.getDataModel(dataModelDTO);
        if (dataModel == null) {
            return;
        }
        HashMap<String, String> sensitiveFields = new HashMap<String, String>();
        String colName = null;
        String oldType = null;
        String newType = null;
        for (DataModelColumn column : dataModel.getColumns()) {
            colName = column.getColumnName();
            oldType = oldMap.containsKey(colName) ? oldMap.get(colName) : "";
            String string = newType = newMap.containsKey(colName) ? newMap.get(colName) : "";
            if (oldType.equals(newType)) continue;
            sensitiveFields.put(colName.toLowerCase(), newType);
        }
        if (!sensitiveFields.isEmpty()) {
            BaseDataDTO dataParam = new BaseDataDTO();
            dataParam.setTenantName(newDefine.getTenantName());
            dataParam.setTableName(newDefine.getName());
            this.getBaseDataCacheService().handleSensitive((BaseDataDO)dataParam, sensitiveFields);
        }
    }

    public Map<String, String> getSensitiveFields(BaseDataDefineDO defineDO) {
        HashMap<String, String> sensitiveFields = new HashMap<String, String>();
        String definestr = defineDO.getDefine();
        if (!StringUtils.hasText(definestr)) {
            return sensitiveFields;
        }
        ObjectNode objectNode = JSONUtil.parseObject((String)definestr);
        ArrayNode jsonArray = objectNode.withArray("fieldProps");
        if (jsonArray != null) {
            String sensitiveType = null;
            for (JsonNode node : jsonArray) {
                if (!node.has("fieldSensitive") || !StringUtils.hasText(sensitiveType = node.get("fieldSensitive").asText())) continue;
                sensitiveFields.put(node.get("columnName").asText(), sensitiveType);
            }
        }
        return sensitiveFields;
    }

    public void pushSyncMsg(BaseDataDefineSyncCacheDTO bddscd) {
        bddscd.setCurrNodeId(EnvConfig.getCurrNodeId());
        bddscd.setRetry(0);
        try {
            if (!EnvConfig.getRedisEnable()) {
                this.handleSyncCacheMsg(bddscd);
                return;
            }
            if (!this.tryPushBroadcast(bddscd)) {
                logger.error("\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u4fe1\u606f\u901a\u8fc7redis\u5e7f\u64ad\u9a8c\u8bc1\u6b21\u6570\u8fbe\u5230\u4e0a\u9650\uff0c\u4ec5\u5904\u7406\u672c\u5730\u7f13\u5b58\u3002");
                this.handleSyncCacheMsg(bddscd);
            }
        }
        catch (Throwable e) {
            logger.error("\u673a\u6784\u7c7b\u578b\u4fe1\u606f\u5e7f\u64ad\u5931\u8d25", e);
        }
        finally {
            checkSyncMap.remove(bddscd.getCheckKey());
        }
    }

    private boolean tryPushBroadcast(BaseDataDefineSyncCacheDTO bddscd) {
        int cnt;
        if (bddscd.getRetry() > 3) {
            return false;
        }
        EnvConfig.sendRedisMsg((String)VaBasedataCoreConfig.getBasedataDefineSyncCachePub(), (String)JSONUtil.toJSONString((Object)((Object)bddscd)));
        String checkKey = bddscd.getCheckKey();
        for (cnt = 0; !checkSyncMap.containsKey(checkKey) && cnt < 300; ++cnt) {
            try {
                Thread.sleep(cnt < 10 ? 5L : (long)(cnt < 100 ? 50 : 500));
                continue;
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (cnt == 300) {
            bddscd.setRetry(bddscd.getRetry() + 1);
            return this.tryPushBroadcast(bddscd);
        }
        return true;
    }

    public BaseDataDefineDO getByName(BaseDataDefineDTO param) {
        this.syncCache(param.getTenantName(), false);
        BaseDataDefineDO data = null;
        Map<String, BaseDataDefineDO> currCodeCacheMap = dataCodeMap.get(param.getTenantName());
        if (currCodeCacheMap == null) {
            return null;
        }
        data = currCodeCacheMap.get(param.getName());
        if (data == null) {
            return null;
        }
        if (param.isDeepClone().booleanValue()) {
            BaseDataDefineDO temp = new BaseDataDefineDO();
            BaseDataDefineExtendUtil.clone(temp, data);
            return temp;
        }
        return data;
    }

    public List<BaseDataDefineDO> list(BaseDataDefineDTO param) {
        this.syncCache(param.getTenantName(), false);
        ArrayList<BaseDataDefineDO> list = new ArrayList<BaseDataDefineDO>();
        Map<String, BaseDataDefineDO> currCodeCacheMap = dataCodeMap.get(param.getTenantName());
        if (currCodeCacheMap == null) {
            return list;
        }
        if (param.isDeepClone().booleanValue()) {
            for (BaseDataDefineDO data : currCodeCacheMap.values()) {
                BaseDataDefineDO temp = new BaseDataDefineDO();
                BaseDataDefineExtendUtil.clone(temp, data);
                list.add(temp);
            }
        } else {
            list.addAll(currCodeCacheMap.values());
        }
        return list;
    }
}

