/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 */
package com.jiuqi.va.organization.service.impl.help;

import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.organization.config.VaOrganizationCoreConfig;
import com.jiuqi.va.organization.dao.VaOrgCategoryDao;
import com.jiuqi.va.organization.domain.OrgCategorySyncCacheDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="vaOrgCategoryCacheService")
public class OrgCategoryCacheService {
    private static Logger logger = LoggerFactory.getLogger(OrgCategoryCacheService.class);
    @Autowired
    private VaOrgCategoryDao orgCategoryDao;
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, BigDecimal>> dataVerMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, OrgCategoryDO>> dataCodeMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, Boolean> lockMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, Boolean> checkSyncMap = new ConcurrentHashMap();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void handleSyncCacheMsg(OrgCategorySyncCacheDTO ocscd) {
        String tenantName = ocscd.getTenantName();
        try {
            if (ocscd.isForceUpdate()) {
                dataCodeMap.remove(tenantName);
                return;
            }
            String[] names = ocscd.getCategoryNames();
            if (names == null) {
                return;
            }
            if (ocscd.isRemove()) {
                Map currCodeCacheMap = dataCodeMap.get(tenantName);
                if (currCodeCacheMap != null) {
                    for (String name : names) {
                        currCodeCacheMap.remove(name);
                    }
                }
                return;
            }
            ConcurrentHashMap<String, BigDecimal> tenantVer = dataVerMap.get(tenantName);
            if (tenantVer == null) {
                dataVerMap.putIfAbsent(tenantName, new ConcurrentHashMap());
                tenantVer = dataVerMap.get(tenantName);
            }
            for (String name : names) {
                tenantVer.put(name, ocscd.getVer());
            }
        }
        catch (Throwable e) {
            logger.error("\u673a\u6784\u7c7b\u578b\u540c\u6b65\u6d88\u606f\u5f02\u5e38", e);
        }
        finally {
            if (EnvConfig.getCurrNodeId().equals(ocscd.getCurrNodeId())) {
                checkSyncMap.put(ocscd.getCheckKey(), true);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void syncCache(OrgCategoryDO orgCategoryDO) {
        boolean locked;
        String tenantName = orgCategoryDO.getTenantName();
        boolean forceUpdate = false;
        ConcurrentHashMap<String, Object> codeCacheMap = dataCodeMap.get(tenantName);
        if (codeCacheMap == null) {
            forceUpdate = true;
        }
        ConcurrentHashMap<String, BigDecimal> tenantVer = dataVerMap.get(tenantName);
        if (!forceUpdate && tenantVer != null && tenantVer.isEmpty()) {
            return;
        }
        HashMap<String, BigDecimal> currVerMap = null;
        BigDecimal startVer = null;
        if (tenantVer != null && !tenantVer.isEmpty() && !(currVerMap = new HashMap<String, BigDecimal>(tenantVer)).isEmpty()) {
            startVer = (BigDecimal)Collections.min(currVerMap.values());
        }
        if (!forceUpdate && startVer == null) {
            return;
        }
        boolean first = codeCacheMap == null;
        String lockKey = startVer == null ? tenantName : tenantName + startVer.toString();
        boolean bl = locked = lockMap.putIfAbsent(lockKey, true) != null;
        if (locked) {
            while (lockMap.get(lockKey) != null) {
                try {
                    Thread.sleep(50L);
                }
                catch (InterruptedException e) {
                    logger.error("orgCategoryCacheWaittingErro", e);
                    Thread.currentThread().interrupt();
                }
            }
            return;
        }
        try {
            OrgCategoryDO param = new OrgCategoryDO();
            param.setTenantName(tenantName);
            boolean isAll = false;
            ArrayList<OrgCategoryDO> listAll = null;
            if (forceUpdate || first || startVer == null) {
                listAll = this.orgCategoryDao.select(param);
                isAll = true;
            } else {
                listAll = new ArrayList<OrgCategoryDO>();
                OrgCategoryDO category = null;
                for (String string : currVerMap.keySet()) {
                    param.setName(string);
                    category = (OrgCategoryDO)this.orgCategoryDao.selectOne(param);
                    if (category == null) continue;
                    listAll.add(category);
                }
            }
            if (listAll == null || listAll.isEmpty()) {
                if (isAll && !first) {
                    codeCacheMap.clear();
                }
                if (currVerMap != null) {
                    for (Map.Entry e : currVerMap.entrySet()) {
                        tenantVer.remove(e.getKey(), e.getValue());
                    }
                }
                return;
            }
            if (first) {
                codeCacheMap = new ConcurrentHashMap();
            }
            HashSet<String> codeSet = new HashSet<String>();
            for (OrgCategoryDO orgCategoryDO2 : listAll) {
                orgCategoryDO2.setTenantName(tenantName);
                orgCategoryDO2.setLocked(true);
                codeCacheMap.put(orgCategoryDO2.getName(), orgCategoryDO2);
                codeSet.add(orgCategoryDO2.getName());
            }
            if (!first && isAll) {
                codeCacheMap.entrySet().removeIf(entry -> !codeSet.contains(entry.getKey()));
            }
            if (currVerMap != null) {
                for (Map.Entry entry2 : currVerMap.entrySet()) {
                    tenantVer.remove(entry2.getKey(), entry2.getValue());
                }
            }
            if (first) {
                dataCodeMap.put(tenantName, codeCacheMap);
            }
        }
        catch (Throwable e) {
            logger.error("\u673a\u6784\u7c7b\u578b\u7f13\u5b58\u540c\u6b65\u5f02\u5e38", e);
        }
        finally {
            if (!locked) {
                lockMap.remove(lockKey);
            }
        }
    }

    public void pushSyncMsg(OrgCategorySyncCacheDTO ocscd) {
        ocscd.setCurrNodeId(EnvConfig.getCurrNodeId());
        ocscd.setVer(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        ocscd.setRetry(0);
        try {
            if (!EnvConfig.getRedisEnable()) {
                this.handleSyncCacheMsg(ocscd);
                return;
            }
            if (!this.tryPushBroadcast(ocscd)) {
                logger.error("\u673a\u6784\u7c7b\u578b\u4fe1\u606f\u5e7f\u64ad\u9a8c\u8bc1\u6b21\u6570\u8fbe\u5230\u4e0a\u9650\uff0c \u4ec5\u5904\u7406\u672c\u5730\u7f13\u5b58\u3002");
                this.handleSyncCacheMsg(ocscd);
            }
        }
        catch (Throwable e) {
            logger.error("\u673a\u6784\u7c7b\u578b\u4fe1\u606f\u5e7f\u64ad\u5931\u8d25", e);
        }
        finally {
            checkSyncMap.remove(ocscd.getCheckKey());
        }
    }

    private boolean tryPushBroadcast(OrgCategorySyncCacheDTO ocscd) {
        int cnt;
        if (ocscd.getRetry() > 3) {
            return false;
        }
        EnvConfig.sendRedisMsg((String)VaOrganizationCoreConfig.getOrgCategorySyncCachePub(), (String)JSONUtil.toJSONString((Object)((Object)ocscd)));
        String checkKey = ocscd.getCheckKey();
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
            ocscd.setRetry(ocscd.getRetry() + 1);
            return this.tryPushBroadcast(ocscd);
        }
        return true;
    }

    public OrgCategoryDO getByName(OrgCategoryDO param) {
        this.syncCache(param);
        OrgCategoryDO data = null;
        Map currCodeCacheMap = dataCodeMap.get(param.getTenantName());
        if (currCodeCacheMap != null && (data = (OrgCategoryDO)currCodeCacheMap.get(param.getName())) != null) {
            return param.isDeepClone() ? this.clone(data) : data;
        }
        return null;
    }

    public List<OrgCategoryDO> list(OrgCategoryDO param) {
        this.syncCache(param);
        ArrayList<OrgCategoryDO> list = new ArrayList<OrgCategoryDO>();
        Map currCodeCacheMap = dataCodeMap.get(param.getTenantName());
        if (currCodeCacheMap != null) {
            for (OrgCategoryDO data : currCodeCacheMap.values()) {
                list.add(param.isDeepClone() ? this.clone(data) : data);
            }
        }
        return list;
    }

    private OrgCategoryDO clone(OrgCategoryDO data) {
        OrgCategoryDO temp = new OrgCategoryDO();
        temp.setTenantName(data.getTenantName());
        temp.setId(data.getId());
        temp.setName(data.getName());
        temp.setTitle(data.getTitle());
        temp.setRemark(data.getRemark());
        temp.setCreatetime(data.getCreatetime());
        temp.setOrdernum(data.getOrdernum());
        temp.setExtinfo(data.getExtinfo());
        temp.setVersionflag(data.getVersionflag());
        return temp;
    }
}

