/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.domain.enumdata.EnumDataDTO
 */
package com.jiuqi.va.basedata.service.impl.help;

import com.jiuqi.va.basedata.config.VaBasedataCoreConfig;
import com.jiuqi.va.basedata.dao.VaEnumDataDao;
import com.jiuqi.va.basedata.domain.EnumDataSyncCacheDTO;
import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.enumdata.EnumDataDO;
import com.jiuqi.va.domain.enumdata.EnumDataDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service(value="vaEnumDataCacheService")
public class EnumDataCacheService {
    private static Logger logger = LoggerFactory.getLogger(EnumDataCacheService.class);
    @Autowired
    private VaEnumDataDao enumDataDao;
    private static ConcurrentHashMap<String, ConcurrentHashMap<BigDecimal, Boolean>> dataVerMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, Map<String, Map<String, EnumDataDO>>> dataValMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, Boolean> lockMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, Boolean> checkSyncMap = new ConcurrentHashMap();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void handleSyncCacheMsg(EnumDataSyncCacheDTO edscd) {
        String tenantName = edscd.getTenantName();
        try {
            if (edscd.isForceUpdate()) {
                dataValMap.remove(tenantName);
                return;
            }
            EnumDataDO enumDataDO = edscd.getEnumDataDO();
            if (enumDataDO == null) {
                return;
            }
            if (edscd.isRemove()) {
                Map<String, Map<String, EnumDataDO>> tenantCacheMap = dataValMap.get(tenantName);
                if (tenantCacheMap == null) {
                    return;
                }
                Map<String, EnumDataDO> bizCacheMap = tenantCacheMap.get(enumDataDO.getBiztype());
                if (bizCacheMap == null) {
                    return;
                }
                bizCacheMap.remove(enumDataDO.getVal());
                return;
            }
            ConcurrentHashMap<BigDecimal, Boolean> tenantVer = dataVerMap.get(tenantName);
            if (tenantVer == null) {
                dataVerMap.putIfAbsent(tenantName, new ConcurrentHashMap());
                tenantVer = dataVerMap.get(tenantName);
            }
            tenantVer.put(enumDataDO.getVer(), true);
        }
        catch (Throwable e) {
            logger.error("\u679a\u4e3e\u540c\u6b65\u6d88\u606f\u5f02\u5e38", e);
        }
        finally {
            if (EnvConfig.getCurrNodeId().equals(edscd.getCurrNodeId())) {
                checkSyncMap.put(edscd.getCheckKey(), true);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void syncCache(String tenantName, boolean forceUpdate) {
        ConcurrentHashMap<BigDecimal, Boolean> tenantVer;
        boolean dataChanged;
        String allLockKey = tenantName;
        while (lockMap.get(allLockKey) != null) {
            try {
                Thread.sleep(200L);
            }
            catch (InterruptedException e) {
                logger.error("enumDataCacheWaittingErro", e);
                Thread.currentThread().interrupt();
            }
        }
        Map<String, Map<String, EnumDataDO>> tenanCacheMap = dataValMap.get(tenantName);
        if (tenanCacheMap == null) {
            forceUpdate = true;
        }
        boolean bl = dataChanged = (tenantVer = dataVerMap.get(tenantName)) != null && !tenantVer.isEmpty();
        if (!forceUpdate && !dataChanged) {
            return;
        }
        HashSet currVerSet = null;
        BigDecimal startVer = null;
        if (dataChanged && !(currVerSet = new HashSet(tenantVer.keySet())).isEmpty()) {
            startVer = (BigDecimal)Collections.min(currVerSet);
        }
        if (!forceUpdate && dataChanged && startVer == null) {
            return;
        }
        boolean first = tenanCacheMap == null;
        String lockKey = startVer == null ? allLockKey : allLockKey + startVer.toString();
        boolean locked = lockMap.putIfAbsent(lockKey, true) != null;
        try {
            if (locked) {
                while (lockMap.get(lockKey) != null) {
                    try {
                        Thread.sleep(100L);
                    }
                    catch (InterruptedException e) {
                        logger.error("enumDataCacheWaittingErro", e);
                        Thread.currentThread().interrupt();
                    }
                }
                this.syncCache(tenantName, forceUpdate);
                return;
            }
            boolean isQueryAll = forceUpdate || startVer == null;
            EnumDataDTO param = new EnumDataDTO();
            param.setTenantName(tenantName);
            List<EnumDataDO> listAll = null;
            if (isQueryAll) {
                listAll = this.enumDataDao.select(param);
            } else {
                param.setVer(startVer);
                listAll = this.enumDataDao.selectByStartVer(param);
            }
            if (first) {
                tenanCacheMap = new ConcurrentHashMap<String, Map<String, EnumDataDO>>();
                dataValMap.put(tenantName, tenanCacheMap);
            }
            if (listAll == null || listAll.isEmpty()) {
                if (isQueryAll) {
                    tenanCacheMap.clear();
                }
                return;
            }
            HashMap valSet = new HashMap();
            String bizType = null;
            for (EnumDataDO enumDataDO : listAll) {
                enumDataDO.setTenantName(tenantName);
                enumDataDO.setLocked(true);
                bizType = enumDataDO.getBiztype();
                Map<String, EnumDataDO> bizMap = tenanCacheMap.get(bizType);
                if (bizMap == null) {
                    tenanCacheMap.putIfAbsent(bizType, new ConcurrentHashMap());
                    bizMap = tenanCacheMap.get(bizType);
                }
                bizMap.put(enumDataDO.getVal(), enumDataDO);
                if (!isQueryAll) continue;
                Set vals = (Set)valSet.get(enumDataDO.getBiztype());
                if (vals == null) {
                    valSet.putIfAbsent(enumDataDO.getBiztype(), new HashSet());
                    vals = (Set)valSet.get(enumDataDO.getBiztype());
                }
                vals.add(enumDataDO.getVal());
            }
            if (!first && isQueryAll) {
                tenanCacheMap.entrySet().removeIf(entry -> !valSet.containsKey(entry.getKey()));
                tenanCacheMap.entrySet().forEach(entry -> ((Map)entry.getValue()).entrySet().removeIf(valEntry -> !((Set)valSet.get(entry.getKey())).contains(valEntry.getKey())));
            }
        }
        catch (Throwable e) {
            logger.error("\u679a\u4e3e\u7f13\u5b58\u540c\u6b65\u5f02\u5e38", e);
        }
        finally {
            if (!locked) {
                if (currVerSet != null) {
                    for (BigDecimal e : currVerSet) {
                        tenantVer.remove(e);
                    }
                }
                lockMap.remove(lockKey);
            }
        }
    }

    public void pushSyncMsg(EnumDataSyncCacheDTO edsc) {
        edsc.setCurrNodeId(EnvConfig.getCurrNodeId());
        edsc.setRetry(0);
        try {
            if (!EnvConfig.getRedisEnable()) {
                this.handleSyncCacheMsg(edsc);
                return;
            }
            if (!this.tryPushBroadcast(edsc)) {
                logger.error("\u679a\u4e3e\u4fe1\u606f\u5e7f\u64ad\u9a8c\u8bc1\u6b21\u6570\u8fbe\u5230\u4e0a\u9650\uff0c \u4ec5\u5904\u7406\u672c\u5730\u7f13\u5b58\u3002");
                this.handleSyncCacheMsg(edsc);
            }
        }
        catch (Throwable e) {
            logger.error("\u673a\u6784\u7c7b\u578b\u4fe1\u606f\u5e7f\u64ad\u5931\u8d25", e);
        }
        finally {
            checkSyncMap.remove(edsc.getCheckKey());
        }
    }

    private boolean tryPushBroadcast(EnumDataSyncCacheDTO edsc) {
        int cnt;
        if (edsc.getRetry() > 3) {
            return false;
        }
        EnvConfig.sendRedisMsg((String)VaBasedataCoreConfig.getEnumDataSyncCachePub(), (String)JSONUtil.toJSONString((Object)((Object)edsc)));
        String checkKey = edsc.getCheckKey();
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
            edsc.setRetry(edsc.getRetry() + 1);
            return this.tryPushBroadcast(edsc);
        }
        return true;
    }

    public EnumDataDO getByVal(EnumDataDTO param) {
        List<EnumDataDO> list = this.list(param);
        if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public List<EnumDataDO> list(EnumDataDTO param) {
        String tenantName = param.getTenantName();
        this.syncCache(tenantName, false);
        ArrayList<EnumDataDO> list = new ArrayList<EnumDataDO>();
        if (!StringUtils.hasText(param.getBiztype())) {
            return list;
        }
        if (!dataValMap.containsKey(tenantName) || !dataValMap.get(tenantName).containsKey(param.getBiztype())) {
            return list;
        }
        Map<String, EnumDataDO> bizMap = dataValMap.get(tenantName).get(param.getBiztype());
        if (StringUtils.hasText(param.getVal())) {
            EnumDataDO obj = bizMap.get(param.getVal());
            if (obj != null) {
                if (param.isDeepClone().booleanValue()) {
                    list.add(this.clone(obj));
                } else {
                    list.add(obj);
                }
            }
        } else {
            for (EnumDataDO data : bizMap.values()) {
                if (param.isDeepClone().booleanValue()) {
                    list.add(this.clone(data));
                    continue;
                }
                list.add(data);
            }
        }
        return list;
    }

    private EnumDataDO clone(EnumDataDO data) {
        EnumDataDO temp = new EnumDataDO();
        temp.setTenantName(data.getTenantName());
        temp.setId(data.getId());
        temp.setVer(data.getVer());
        temp.setVal(data.getVal());
        temp.setTitle(data.getTitle());
        temp.setBiztype(data.getBiztype());
        temp.setDescription(data.getDescription());
        temp.setStatus(data.getStatus());
        temp.setRemark(data.getRemark());
        temp.setOrdernum(data.getOrdernum());
        return temp;
    }
}

