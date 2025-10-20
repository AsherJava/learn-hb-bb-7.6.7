/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDetailDO
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.OrgContext
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.org.OrgCacheDO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.OrgVersionDTO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.extend.OrgDataCacheExtend
 *  com.jiuqi.va.feign.client.BaseDataDetailClient
 */
package com.jiuqi.va.organization.service.impl.help;

import com.jiuqi.va.domain.basedata.BaseDataDetailDO;
import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.OrgContext;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.org.OrgCacheDO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.extend.OrgDataCacheExtend;
import com.jiuqi.va.feign.client.BaseDataDetailClient;
import com.jiuqi.va.organization.common.OrgDataCacheUtil;
import com.jiuqi.va.organization.common.OrgDataKryoSerializer;
import com.jiuqi.va.organization.config.VaOrganizationCoreConfig;
import com.jiuqi.va.organization.dao.VaOrgDataDao;
import com.jiuqi.va.organization.domain.OrgDataSyncCacheDTO;
import com.jiuqi.va.organization.domain.ZBDTO;
import com.jiuqi.va.organization.service.OrgCategoryService;
import com.jiuqi.va.organization.service.OrgVersionService;
import com.jiuqi.va.organization.service.impl.help.OrgDataParamService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class OrgDataCacheService {
    private static Logger logger = LoggerFactory.getLogger(OrgDataCacheService.class);
    @Value(value="${nvwa.organization.cache.compression:false}")
    private boolean cacheCompression;
    @Value(value="${nvwa.organization.cache.sync-wait:200}")
    private int cacheSyncWait = 200;
    @Value(value="${nvwa.organization.compatible-with-error-possible:true}")
    private boolean compatibleWithErrorPossible;
    @Autowired
    private OrgDataParamService orgDataParamService;
    @Autowired
    private VaOrgDataDao orgDataDao;
    @Autowired
    private OrgCategoryService orgCategoryService;
    @Autowired
    public OrgVersionService orgVersionService;
    @Autowired
    private BaseDataDetailClient baseDataDetailClient;
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, OrgDO>>> idMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, byte[]>>> idByteMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>>>> orgcodeMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>>>> codeMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>>>> codeDirectSubMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>>>> codeAllSubMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Date>>> verActiveMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<BigDecimal, Boolean>>> forceUpadteMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<BigDecimal, Boolean>>> dataVerMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, Boolean> lockMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, Boolean> checkSyncMap = new ConcurrentHashMap();
    private static OrgDataKryoSerializer dataSerializer = new OrgDataKryoSerializer();
    private ExecutorService executorService = null;

    private ExecutorService getExecutorService() {
        if (this.executorService == null) {
            this.executorService = Executors.newWorkStealingPool();
        }
        return this.executorService;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void handleSyncCacheMsg(OrgDataSyncCacheDTO odscd) {
        String tenantName = odscd.getTenantName();
        OrgDTO paramDTO = odscd.getOrgDTO();
        String categoryName = paramDTO.getCategoryname();
        try {
            ConcurrentHashMap<BigDecimal, Boolean> tableVer;
            ConcurrentHashMap<String, ConcurrentHashMap<UUID, Date>> tenantActiveMap = verActiveMap.get(tenantName);
            if (tenantActiveMap == null || !tenantActiveMap.containsKey(categoryName)) {
                return;
            }
            if (odscd.isForceUpdate()) {
                ConcurrentHashMap<BigDecimal, Boolean> tableForceInfo;
                ConcurrentHashMap<String, ConcurrentHashMap<BigDecimal, Boolean>> tenantForceInfo = forceUpadteMap.get(tenantName);
                if (tenantForceInfo == null) {
                    forceUpadteMap.putIfAbsent(tenantName, new ConcurrentHashMap());
                    tenantForceInfo = forceUpadteMap.get(tenantName);
                }
                if ((tableForceInfo = tenantForceInfo.get(categoryName)) == null) {
                    tenantForceInfo.putIfAbsent(categoryName, new ConcurrentHashMap());
                    tableForceInfo = tenantForceInfo.get(categoryName);
                }
                if (paramDTO.getQueryStartVer() != null) {
                    tableForceInfo.put(paramDTO.getQueryStartVer(), true);
                } else {
                    tableForceInfo.put(OrderNumUtil.getOrderNumByCurrentTimeMillis(), true);
                }
                return;
            }
            if (odscd.isClean()) {
                this.cleanCategoryCache(paramDTO, false);
                return;
            }
            if (odscd.isRemove()) {
                Set<UUID> removeIds = odscd.getRemoveIds();
                if (removeIds != null) {
                    this.removeCacheByIds(paramDTO, removeIds);
                    return;
                }
                this.removeCache(paramDTO);
                return;
            }
            ConcurrentHashMap<String, ConcurrentHashMap<BigDecimal, Boolean>> tenantVer = dataVerMap.get(tenantName);
            if (tenantVer == null) {
                dataVerMap.putIfAbsent(tenantName, new ConcurrentHashMap());
                tenantVer = dataVerMap.get(tenantName);
            }
            if ((tableVer = tenantVer.get(categoryName)) == null) {
                tenantVer.putIfAbsent(categoryName, new ConcurrentHashMap());
                tableVer = tenantVer.get(categoryName);
            }
            tableVer.put(paramDTO.getQueryStartVer(), true);
        }
        catch (Throwable e) {
            logger.error("\u673a\u6784\u7c7b\u578b\u540c\u6b65\u6d88\u606f\u5f02\u5e38", e);
        }
        finally {
            if (EnvConfig.getCurrNodeId().equals(odscd.getCurrNodeId())) {
                checkSyncMap.put(odscd.getCheckKey(), true);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void syncCache(OrgDTO param, boolean force) {
        HashMap oldColAllIndex;
        HashMap oldColIndex;
        Map currColIndex;
        boolean isCurrentCategory;
        boolean locked;
        String lockKey;
        HashSet currVerSet;
        ConcurrentHashMap<BigDecimal, Boolean> tableVerMap;
        HashSet forceVerSet;
        ConcurrentHashMap<BigDecimal, Boolean> forceVerMap;
        String categoryName;
        String tenantName;
        block102: {
            block101: {
                block98: {
                    block99: {
                        block100: {
                            boolean dataChanged;
                            boolean forceUpdate = force;
                            tenantName = param.getTenantName();
                            categoryName = param.getCategoryname();
                            ConcurrentHashMap<String, ConcurrentHashMap<UUID, Date>> tenantActiveMap = verActiveMap.get(tenantName);
                            if (tenantActiveMap == null) {
                                return;
                            }
                            ConcurrentHashMap<UUID, Date> verActive = tenantActiveMap.get(categoryName);
                            if (verActive == null) return;
                            if (verActive.isEmpty()) {
                                return;
                            }
                            String allLockKey = tenantName + categoryName;
                            while (lockMap.get(allLockKey) != null) {
                                try {
                                    Thread.sleep(this.cacheSyncWait);
                                }
                                catch (InterruptedException e) {
                                    logger.error("orgDataCacheWaittingErro", e);
                                    Thread.currentThread().interrupt();
                                }
                            }
                            boolean first = false;
                            ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>>> tenantCodeMap = codeMap.get(tenantName);
                            ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> tableCodeMap = null;
                            if (tenantCodeMap == null) {
                                first = true;
                                forceUpdate = true;
                            } else {
                                tableCodeMap = tenantCodeMap.get(categoryName);
                                if (tableCodeMap == null) {
                                    first = true;
                                    forceUpdate = true;
                                }
                            }
                            ConcurrentHashMap<String, ConcurrentHashMap<BigDecimal, Boolean>> tenantForceVer = forceUpadteMap.get(tenantName);
                            forceVerMap = null;
                            if (tenantForceVer != null) {
                                forceVerMap = tenantForceVer.get(categoryName);
                            }
                            forceVerSet = null;
                            if (forceVerMap != null && !forceVerMap.isEmpty()) {
                                forceUpdate = true;
                                forceVerSet = new HashSet(forceVerMap.keySet());
                            }
                            ConcurrentHashMap<String, ConcurrentHashMap<BigDecimal, Boolean>> tenantVerMap = dataVerMap.get(tenantName);
                            tableVerMap = null;
                            if (tenantVerMap != null) {
                                tableVerMap = tenantVerMap.get(categoryName);
                            }
                            boolean bl = dataChanged = tableVerMap != null && !tableVerMap.isEmpty();
                            if (!forceUpdate && !dataChanged) {
                                return;
                            }
                            currVerSet = null;
                            BigDecimal startVer = null;
                            if (dataChanged && !(currVerSet = new HashSet(tableVerMap.keySet())).isEmpty()) {
                                startVer = (BigDecimal)Collections.min(currVerSet);
                            }
                            if (!forceUpdate && dataChanged && startVer == null) {
                                return;
                            }
                            lockKey = startVer == null ? allLockKey : allLockKey + startVer.toString();
                            boolean bl2 = locked = lockMap.putIfAbsent(lockKey, true) != null;
                            if (locked) {
                                while (true) {
                                    if (lockMap.get(lockKey) == null) {
                                        this.syncCache(param, force);
                                        return;
                                    }
                                    try {
                                        Thread.sleep(this.cacheSyncWait);
                                    }
                                    catch (InterruptedException e) {
                                        logger.error("orgDataCacheWaittingErro", e);
                                        Thread.currentThread().interrupt();
                                    }
                                }
                            }
                            isCurrentCategory = true;
                            currColIndex = null;
                            oldColIndex = null;
                            oldColAllIndex = null;
                            try {
                                Map<String, OrgDataCacheExtend> cacheExtends;
                                boolean isQueryAll = forceUpdate || startVer == null;
                                OrgDTO queryParam = new OrgDTO();
                                queryParam.setTenantName(tenantName);
                                queryParam.setCategoryname(categoryName);
                                queryParam.addExtInfo("VERSION_DATE_LIST", verActive.values());
                                if (!isQueryAll) {
                                    queryParam.setQueryStartVer(startVer);
                                }
                                if (!(isCurrentCategory = OrgContext.isCurrentCategory((String)tenantName, (String)categoryName))) {
                                    currColIndex = OrgContext.getColIndexContext();
                                    if (currColIndex != null) {
                                        oldColIndex = new HashMap(currColIndex);
                                        oldColAllIndex = new HashMap(OrgContext.getColAllKeyContext());
                                    }
                                    OrgContext.bindColIndex((String)tenantName, (String)categoryName);
                                }
                                if ((cacheExtends = OrgDataCacheUtil.getCacheExtends()) != null && !cacheExtends.isEmpty()) {
                                    for (OrgDataCacheExtend orgDataCacheExtend : cacheExtends.values()) {
                                        if (!orgDataCacheExtend.trySyncCache(queryParam, isQueryAll)) continue;
                                        if (!first) return;
                                        this.initCategoryCache(tenantName, categoryName);
                                        return;
                                    }
                                }
                                LinkedList<OrgCacheDO> dataList = this.orgDataDao.selectByStartVer(queryParam);
                                Set<UUID> allIds = null;
                                if (isQueryAll) {
                                    allIds = this.orgDataDao.selectAllId(queryParam);
                                }
                                if (first) {
                                    this.initCategoryCache(tenantName, categoryName);
                                }
                                if (dataList == null || dataList.isEmpty()) {
                                    if (!isQueryAll) return;
                                    if (allIds.isEmpty()) {
                                        this.cleanCategoryCache(queryParam, true);
                                        return;
                                    }
                                    this.removeIds(tenantName, categoryName, allIds);
                                    return;
                                }
                                ConcurrentHashMap<UUID, OrgDO> currIdMap = null;
                                ConcurrentHashMap<UUID, byte[]> currIdByteMap = null;
                                if (this.cacheCompression) {
                                    currIdByteMap = idByteMap.get(tenantName).get(categoryName);
                                } else {
                                    currIdMap = idMap.get(tenantName).get(categoryName);
                                }
                                if (currIdByteMap == null && currIdMap == null) {
                                    return;
                                }
                                UUID id = null;
                                HashMap<UUID, String> changeParentsIdMap = new HashMap<UUID, String>();
                                if (this.cacheCompression && !currIdByteMap.isEmpty() || !this.cacheCompression && !currIdMap.isEmpty()) {
                                    boolean inActive = false;
                                    HashSet<UUID> notInActiveIds = new HashSet<UUID>();
                                    Object newCache = null;
                                    Object var38_53 = null;
                                    Iterator iterator = dataList.iterator();
                                    while (iterator.hasNext()) {
                                        void var38_57;
                                        newCache = (OrgCacheDO)iterator.next();
                                        id = newCache.getId();
                                        if (this.cacheCompression) {
                                            OrgCacheDO orgCacheDO = dataSerializer.deserialize(currIdByteMap.get(id));
                                        } else {
                                            OrgDO orgDO = currIdMap.get(id);
                                        }
                                        if (var38_57 == null) continue;
                                        if (var38_57.getVer().compareTo(newCache.getVer()) == 0) {
                                            iterator.remove();
                                            continue;
                                        }
                                        inActive = false;
                                        for (Date date : verActive.values()) {
                                            if (date.before(newCache.getValidtime()) || !date.before(newCache.getInvalidtime())) continue;
                                            inActive = true;
                                            break;
                                        }
                                        if (!inActive) {
                                            notInActiveIds.add(id);
                                            iterator.remove();
                                            continue;
                                        }
                                        if (var38_57.getParents().equals(newCache.getParents())) continue;
                                        changeParentsIdMap.put(newCache.getId(), var38_57.getParents());
                                    }
                                    this.removeCacheByIds(queryParam, notInActiveIds);
                                }
                                if (allIds != null) {
                                    this.removeIds(tenantName, categoryName, allIds);
                                }
                                if (dataList.isEmpty()) {
                                    if (locked) return;
                                    if (isCurrentCategory) break block98;
                                    if (currColIndex != null) break block99;
                                    break block100;
                                }
                                this.loadMultiDetailData((OrgDO)param, dataList, allIds != null);
                                String code = null;
                                String orgcode = null;
                                for (OrgCacheDO orgCacheDO : dataList) {
                                    code = orgCacheDO.getCode();
                                    orgcode = orgCacheDO.getOrgcode();
                                    if (orgcode == null) {
                                        orgCacheDO.setOrgcode(code);
                                        orgcode = code;
                                    }
                                    orgCacheDO.setLocked(true);
                                    id = orgCacheDO.getId();
                                    if (StringUtils.hasText(orgCacheDO.getParents())) continue;
                                    logger.error("\u7ec4\u7ec7\u673a\u6784" + categoryName + "[id=" + id + "] \u7684 parents \u4e3a null\uff0c\u6570\u636e\u5f02\u5e38\u5df2\u5ffd\u7565\u540c\u6b65\u5230\u7f13\u5b58\u3002");
                                }
                                ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> currOrgcodeCacheMap = orgcodeMap.get(tenantName).get(categoryName);
                                ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> concurrentHashMap = codeMap.get(tenantName).get(categoryName);
                                ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> currCodeDirectSubCacheMap = codeDirectSubMap.get(tenantName).get(categoryName);
                                ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> currCodeAllSubCacheMap = codeAllSubMap.get(tenantName).get(categoryName);
                                if (concurrentHashMap == null) {
                                    if (locked) return;
                                    break block101;
                                }
                                Object var41_68 = null;
                                int tmpPoint = 0;
                                String tmpPath = null;
                                String[] tmpPathCodes = null;
                                StringBuilder tmpSb = new StringBuilder();
                                ConcurrentHashMap<UUID, Boolean> tmpCodeSubMap = null;
                                if (!changeParentsIdMap.isEmpty()) {
                                    for (Map.Entry entry : changeParentsIdMap.entrySet()) {
                                        id = (UUID)entry.getKey();
                                        String string = (String)entry.getValue();
                                        tmpPoint = string.lastIndexOf("/");
                                        tmpPath = tmpPoint == -1 ? "-" : string.substring(0, tmpPoint);
                                        tmpCodeSubMap = currCodeDirectSubCacheMap.get(tmpPath);
                                        if (tmpCodeSubMap != null) {
                                            tmpCodeSubMap.remove(id);
                                        }
                                        if (tmpPoint == -1) continue;
                                        if (tmpSb.length() > 0) {
                                            tmpSb.setLength(0);
                                        }
                                        tmpPathCodes = string.split("\\/");
                                        for (int i = 0; i < tmpPathCodes.length - 1; ++i) {
                                            if (i > 0) {
                                                tmpSb.append("/");
                                            }
                                            tmpSb.append(tmpPathCodes[i]);
                                            tmpCodeSubMap = currCodeAllSubCacheMap.get(tmpSb.toString());
                                            if (tmpCodeSubMap == null) continue;
                                            tmpCodeSubMap.remove(id);
                                        }
                                    }
                                }
                                for (OrgCacheDO orgCacheDO : dataList) {
                                    id = orgCacheDO.getId();
                                    orgcode = orgCacheDO.getOrgcode();
                                    code = orgCacheDO.getCode();
                                    if (this.cacheCompression) {
                                        orgCacheDO.setSerializing(true);
                                        currIdByteMap.put(id, dataSerializer.serialize(orgCacheDO));
                                        orgCacheDO.setSerializing(false);
                                    } else {
                                        currIdMap.put(id, (OrgDO)orgCacheDO);
                                    }
                                    if (currOrgcodeCacheMap.containsKey(orgcode)) {
                                        currOrgcodeCacheMap.get(orgcode).put(id, true);
                                    } else {
                                        ConcurrentHashMap<UUID, Boolean> orgcodeIdSet = new ConcurrentHashMap<UUID, Boolean>();
                                        orgcodeIdSet.put(id, true);
                                        currOrgcodeCacheMap.put(orgcode, orgcodeIdSet);
                                    }
                                    if (concurrentHashMap.containsKey(code)) {
                                        concurrentHashMap.get(code).put(id, true);
                                    } else {
                                        ConcurrentHashMap<UUID, Boolean> codeIdSet = new ConcurrentHashMap<UUID, Boolean>();
                                        codeIdSet.put(id, true);
                                        concurrentHashMap.put(code, codeIdSet);
                                    }
                                    String string = orgCacheDO.getParents();
                                    tmpPoint = string.lastIndexOf("/");
                                    tmpPath = tmpPoint == -1 ? "-" : string.substring(0, tmpPoint);
                                    tmpCodeSubMap = currCodeDirectSubCacheMap.get(tmpPath);
                                    if (tmpCodeSubMap != null) {
                                        tmpCodeSubMap.put(id, true);
                                    } else {
                                        tmpCodeSubMap = new ConcurrentHashMap();
                                        tmpCodeSubMap.put(id, true);
                                        currCodeDirectSubCacheMap.put(tmpPath, tmpCodeSubMap);
                                    }
                                    if (tmpPoint == -1) continue;
                                    if (tmpSb.length() > 0) {
                                        tmpSb.setLength(0);
                                    }
                                    tmpPathCodes = string.split("\\/");
                                    for (int i = 0; i < tmpPathCodes.length - 1; ++i) {
                                        if (i > 0) {
                                            tmpSb.append("/");
                                        }
                                        tmpSb.append(tmpPathCodes[i]);
                                        tmpPath = tmpSb.toString();
                                        tmpCodeSubMap = currCodeAllSubCacheMap.get(tmpPath);
                                        if (tmpCodeSubMap != null) {
                                            tmpCodeSubMap.put(id, true);
                                            continue;
                                        }
                                        tmpCodeSubMap = new ConcurrentHashMap();
                                        tmpCodeSubMap.put(id, true);
                                        currCodeAllSubCacheMap.put(tmpPath, tmpCodeSubMap);
                                    }
                                }
                                if (locked) return;
                                break block102;
                            }
                            catch (Exception e) {
                                logger.error(categoryName + "\u7ec4\u7ec7\u673a\u6784\u7f13\u5b58\u540c\u6b65\u5f02\u5e38", e);
                                return;
                            }
                        }
                        OrgContext.unbindColIndex();
                        break block98;
                    }
                    OrgContext.bindColIndex((String)tenantName, (String)categoryName, oldColIndex, oldColAllIndex);
                }
                if (forceVerSet != null) {
                    for (BigDecimal e : forceVerSet) {
                        forceVerMap.remove(e);
                    }
                }
                if (currVerSet != null) {
                    for (BigDecimal e : currVerSet) {
                        tableVerMap.remove(e);
                    }
                }
                lockMap.remove(lockKey);
                return;
            }
            if (!isCurrentCategory) {
                if (currColIndex == null) {
                    OrgContext.unbindColIndex();
                } else {
                    OrgContext.bindColIndex((String)tenantName, (String)categoryName, oldColIndex, oldColAllIndex);
                }
            }
            if (forceVerSet != null) {
                for (BigDecimal e : forceVerSet) {
                    forceVerMap.remove(e);
                }
            }
            if (currVerSet != null) {
                for (BigDecimal e : currVerSet) {
                    tableVerMap.remove(e);
                }
            }
            lockMap.remove(lockKey);
            return;
        }
        if (!isCurrentCategory) {
            if (currColIndex == null) {
                OrgContext.unbindColIndex();
            } else {
                OrgContext.bindColIndex((String)tenantName, (String)categoryName, oldColIndex, oldColAllIndex);
            }
        }
        if (forceVerSet != null) {
            for (BigDecimal e : forceVerSet) {
                forceVerMap.remove(e);
            }
        }
        if (currVerSet != null) {
            for (BigDecimal e : currVerSet) {
                tableVerMap.remove(e);
            }
        }
        lockMap.remove(lockKey);
        return;
        finally {
            if (!locked) {
                if (!isCurrentCategory) {
                    if (currColIndex == null) {
                        OrgContext.unbindColIndex();
                    } else {
                        OrgContext.bindColIndex((String)tenantName, (String)categoryName, oldColIndex, oldColAllIndex);
                    }
                }
                if (forceVerSet != null) {
                    for (BigDecimal e : forceVerSet) {
                        forceVerMap.remove(e);
                    }
                }
                if (currVerSet != null) {
                    for (BigDecimal e : currVerSet) {
                        tableVerMap.remove(e);
                    }
                }
                lockMap.remove(lockKey);
            }
        }
    }

    private void removeIds(String tenantName, String categoryName, Set<UUID> allIdSet) {
        ConcurrentHashMap<UUID, OrgDO> currIdMap = null;
        ConcurrentHashMap<UUID, byte[]> currIdByteMap = null;
        if (this.cacheCompression) {
            if (idByteMap.containsKey(tenantName)) {
                currIdByteMap = idByteMap.get(tenantName).get(categoryName);
            }
        } else if (idMap.containsKey(tenantName)) {
            currIdMap = idMap.get(tenantName).get(categoryName);
        }
        if (currIdMap == null && currIdByteMap == null) {
            return;
        }
        if (this.cacheCompression) {
            currIdByteMap.entrySet().removeIf(entry -> !allIdSet.contains(entry.getKey()));
        } else {
            currIdMap.entrySet().removeIf(entry -> !allIdSet.contains(entry.getKey()));
        }
        ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> currOrgcodeCacheMap = orgcodeMap.get(tenantName).get(categoryName);
        currOrgcodeCacheMap.entrySet().forEach(obj -> ((ConcurrentHashMap)obj.getValue()).keySet().removeIf(acid -> !allIdSet.contains(acid)));
        ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> currCodeCacheMap = codeMap.get(tenantName).get(categoryName);
        currCodeCacheMap.entrySet().forEach(obj -> ((ConcurrentHashMap)obj.getValue()).keySet().removeIf(acid -> !allIdSet.contains(acid)));
        ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> currCodeDirectSubCacheMap = codeDirectSubMap.get(tenantName).get(categoryName);
        currCodeDirectSubCacheMap.entrySet().forEach(obj -> ((ConcurrentHashMap)obj.getValue()).keySet().removeIf(acid -> !allIdSet.contains(acid)));
        ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> currCodeAllSubCacheMap = codeAllSubMap.get(tenantName).get(categoryName);
        currCodeAllSubCacheMap.entrySet().forEach(obj -> ((ConcurrentHashMap)obj.getValue()).keySet().removeIf(acid -> !allIdSet.contains(acid)));
    }

    private void initCategoryCache(String tenantName, String categoryName) {
        ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>>> currTableCodeAllSubCacheMap;
        ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>>> currTableCodeDirectSubCacheMap;
        ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>>> currTableCodeCacheMap;
        ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>>> currTableOrgcodeCacheMap;
        if (this.cacheCompression) {
            ConcurrentHashMap<String, ConcurrentHashMap<UUID, byte[]>> currTableIdByteMap;
            if (!idByteMap.containsKey(tenantName)) {
                idByteMap.putIfAbsent(tenantName, new ConcurrentHashMap());
            }
            if (!(currTableIdByteMap = idByteMap.get(tenantName)).containsKey(categoryName)) {
                currTableIdByteMap.putIfAbsent(categoryName, new ConcurrentHashMap());
            }
        } else {
            ConcurrentHashMap<String, ConcurrentHashMap<UUID, OrgDO>> currTableIdCacheMap;
            if (!idMap.containsKey(tenantName)) {
                idMap.putIfAbsent(tenantName, new ConcurrentHashMap());
            }
            if (!(currTableIdCacheMap = idMap.get(tenantName)).containsKey(categoryName)) {
                currTableIdCacheMap.putIfAbsent(categoryName, new ConcurrentHashMap());
            }
        }
        if (!orgcodeMap.containsKey(tenantName)) {
            orgcodeMap.putIfAbsent(tenantName, new ConcurrentHashMap());
        }
        if (!(currTableOrgcodeCacheMap = orgcodeMap.get(tenantName)).containsKey(categoryName)) {
            currTableOrgcodeCacheMap.putIfAbsent(categoryName, new ConcurrentHashMap());
        }
        if (!codeMap.containsKey(tenantName)) {
            codeMap.putIfAbsent(tenantName, new ConcurrentHashMap());
        }
        if (!(currTableCodeCacheMap = codeMap.get(tenantName)).containsKey(categoryName)) {
            currTableCodeCacheMap.putIfAbsent(categoryName, new ConcurrentHashMap());
        }
        if (!codeDirectSubMap.containsKey(tenantName)) {
            codeDirectSubMap.putIfAbsent(tenantName, new ConcurrentHashMap());
        }
        if (!(currTableCodeDirectSubCacheMap = codeDirectSubMap.get(tenantName)).containsKey(categoryName)) {
            currTableCodeDirectSubCacheMap.putIfAbsent(categoryName, new ConcurrentHashMap());
        }
        if (!codeAllSubMap.containsKey(tenantName)) {
            codeAllSubMap.putIfAbsent(tenantName, new ConcurrentHashMap());
        }
        if (!(currTableCodeAllSubCacheMap = codeAllSubMap.get(tenantName)).containsKey(categoryName)) {
            currTableCodeAllSubCacheMap.putIfAbsent(categoryName, new ConcurrentHashMap());
        }
    }

    public int countCache(OrgDO param) {
        String tenantName = param.getTenantName();
        String categoryName = param.getCategoryname();
        return codeMap.get(tenantName).get(categoryName).size();
    }

    public void pushSyncMsg(OrgDataSyncCacheDTO odscd) {
        odscd.setCurrNodeId(EnvConfig.getCurrNodeId());
        odscd.setRetry(0);
        try {
            if (!EnvConfig.getRedisEnable()) {
                this.handleSyncCacheMsg(odscd);
                return;
            }
            if (!this.tryPushBroadcast(odscd)) {
                logger.error("\u673a\u6784\u6570\u636e\u4fe1\u606f\u5e7f\u64ad\u9a8c\u8bc1\u6b21\u6570\u8fbe\u5230\u4e0a\u9650\uff0c \u4ec5\u5904\u7406\u672c\u5730\u7f13\u5b58\u3002");
                this.handleSyncCacheMsg(odscd);
            }
        }
        catch (Throwable e) {
            logger.error("\u673a\u6784\u6570\u636e\u4fe1\u606f\u5e7f\u64ad\u5931\u8d25", e);
        }
        finally {
            checkSyncMap.remove(odscd.getCheckKey());
        }
    }

    private boolean tryPushBroadcast(OrgDataSyncCacheDTO odscd) {
        int cnt;
        if (odscd.getRetry() > 3) {
            return false;
        }
        EnvConfig.sendRedisMsg((String)VaOrganizationCoreConfig.getOrgDataSyncCachePub(), (String)JSONUtil.toJSONString((Object)((Object)odscd)));
        String checkKey = odscd.getCheckKey();
        for (cnt = 0; !checkSyncMap.containsKey(checkKey) && cnt < 300; ++cnt) {
            try {
                Thread.sleep(cnt < 10 ? 50L : (long)(cnt < 100 ? 500 : 5000));
                continue;
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (cnt == 300) {
            odscd.setRetry(odscd.getRetry() + 1);
            return this.tryPushBroadcast(odscd);
        }
        return true;
    }

    private void cleanCategoryCache(OrgDTO param, boolean isOnlyCleanData) {
        String tenantName = param.getTenantName();
        String categoryName = param.getCategoryname();
        if (this.cacheCompression) {
            if (idByteMap.containsKey(tenantName)) {
                idByteMap.get(tenantName).remove(categoryName);
                if (isOnlyCleanData) {
                    idByteMap.get(tenantName).putIfAbsent(categoryName, new ConcurrentHashMap());
                }
            }
        } else if (idMap.containsKey(tenantName)) {
            idMap.get(tenantName).remove(categoryName);
            if (isOnlyCleanData) {
                idMap.get(tenantName).put(categoryName, new ConcurrentHashMap());
            }
        }
        if (orgcodeMap.containsKey(tenantName)) {
            orgcodeMap.get(tenantName).remove(categoryName);
            if (isOnlyCleanData) {
                orgcodeMap.get(tenantName).put(categoryName, new ConcurrentHashMap());
            }
        }
        if (codeMap.containsKey(tenantName)) {
            codeMap.get(tenantName).remove(categoryName);
            if (isOnlyCleanData) {
                codeMap.get(tenantName).putIfAbsent(categoryName, new ConcurrentHashMap());
            }
        }
        if (codeDirectSubMap.containsKey(tenantName)) {
            codeDirectSubMap.get(tenantName).remove(categoryName);
            if (isOnlyCleanData) {
                codeDirectSubMap.get(tenantName).put(categoryName, new ConcurrentHashMap());
            }
        }
        if (codeAllSubMap.containsKey(tenantName)) {
            codeAllSubMap.get(tenantName).remove(categoryName);
            if (isOnlyCleanData) {
                codeAllSubMap.get(tenantName).put(categoryName, new ConcurrentHashMap());
            }
        }
        if (!isOnlyCleanData && verActiveMap.containsKey(tenantName)) {
            verActiveMap.get(tenantName).remove(categoryName);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removeCache(OrgDTO param) {
        HashSet<UUID> ids = new HashSet<UUID>();
        if (param.getId() != null) {
            ids.add(param.getId());
            this.removeCacheByIds(param, ids);
            return;
        }
        Date verDate = param.getVersionDate();
        if (verDate == null) {
            return;
        }
        OrgVersionDO versionDO = this.orgDataParamService.getOrgVersion(param);
        if (versionDO == null) {
            return;
        }
        String tenantName = param.getTenantName();
        String categoryName = param.getCategoryname();
        ConcurrentHashMap<String, ConcurrentHashMap<UUID, Date>> tenantActiveMap = verActiveMap.get(tenantName);
        if (tenantActiveMap == null) {
            return;
        }
        ConcurrentHashMap<UUID, Date> verActive = tenantActiveMap.get(categoryName);
        if (verActive == null || verActive.isEmpty() || !verActive.containsKey(versionDO.getId())) {
            return;
        }
        String lockKey = versionDO.getId().toString();
        boolean locked = lockMap.putIfAbsent(lockKey, true) != null;
        try {
            if (locked) {
                while (lockMap.get(lockKey) != null) {
                    try {
                        Thread.sleep(this.cacheSyncWait);
                    }
                    catch (InterruptedException e) {
                        logger.error("orgCacheWaittingErro", e);
                        Thread.currentThread().interrupt();
                    }
                }
                return;
            }
            verActive.remove(versionDO.getId());
            if (verActive.isEmpty()) {
                this.cleanCategoryCache(param, true);
                return;
            }
            ArrayList<Object> cacheList = new ArrayList<Object>();
            if (this.cacheCompression) {
                ConcurrentHashMap<String, ConcurrentHashMap<UUID, byte[]>> currTableIdByteMap = idByteMap.get(tenantName);
                if (currTableIdByteMap != null && currTableIdByteMap.containsKey(categoryName)) {
                    for (byte[] val : currTableIdByteMap.get(categoryName).values()) {
                        cacheList.add(dataSerializer.deserialize(val));
                    }
                }
            } else {
                ConcurrentHashMap<String, ConcurrentHashMap<UUID, OrgDO>> currTableIdCacheMap = idMap.get(tenantName);
                if (currTableIdCacheMap != null && currTableIdCacheMap.containsKey(categoryName)) {
                    cacheList.addAll(currTableIdCacheMap.get(categoryName).values());
                }
            }
            if (cacheList.isEmpty()) {
                return;
            }
            for (OrgDO orgDO : cacheList) {
                if (!orgDO.getValidtime().equals(versionDO.getValidtime()) || !orgDO.getInvalidtime().equals(versionDO.getInvalidtime())) continue;
                ids.add(orgDO.getId());
            }
            if (!ids.isEmpty()) {
                this.removeCacheByIds(param, ids);
            }
        }
        finally {
            if (!locked) {
                lockMap.remove(lockKey);
            }
        }
    }

    private void removeCacheByIds(OrgDTO orgDO, Set<UUID> ids) {
        ConcurrentHashMap<String, ConcurrentHashMap<UUID, byte[]>> orgTableMap;
        if (ids == null || ids.isEmpty()) {
            return;
        }
        String tenantName = orgDO.getTenantName();
        String categoryName = orgDO.getCategoryname();
        boolean flag = false;
        if (this.cacheCompression) {
            orgTableMap = idByteMap.get(tenantName);
            if (orgTableMap != null && orgTableMap.containsKey(categoryName)) {
                for (UUID id : ids) {
                    orgTableMap.get(categoryName).remove(id);
                }
                flag = true;
            }
        } else {
            orgTableMap = idMap.get(tenantName);
            if (orgTableMap != null && orgTableMap.containsKey(categoryName)) {
                for (UUID id : ids) {
                    orgTableMap.get(categoryName).remove(id);
                }
                flag = true;
            }
        }
        if (flag) {
            Map codeAllSubCache;
            Map codeDirectSubCache;
            Map codeCache;
            Map orgcodeCache = orgcodeMap.get(tenantName).get(categoryName);
            if (orgcodeCache != null) {
                orgcodeCache.entrySet().forEach(obj -> ((ConcurrentHashMap)obj.getValue()).keySet().removeIf(acid -> ids.contains(acid)));
            }
            if ((codeCache = (Map)codeMap.get(tenantName).get(categoryName)) != null) {
                codeCache.entrySet().forEach(obj -> ((ConcurrentHashMap)obj.getValue()).keySet().removeIf(acid -> ids.contains(acid)));
            }
            if ((codeDirectSubCache = (Map)codeDirectSubMap.get(tenantName).get(categoryName)) != null) {
                codeDirectSubCache.entrySet().forEach(obj -> ((ConcurrentHashMap)obj.getValue()).keySet().removeIf(acid -> ids.contains(acid)));
            }
            if ((codeAllSubCache = (Map)codeAllSubMap.get(tenantName).get(categoryName)) != null) {
                codeAllSubCache.entrySet().forEach(obj -> ((ConcurrentHashMap)obj.getValue()).keySet().removeIf(acid -> ids.contains(acid)));
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<OrgDO> listBasicCacheData(OrgDTO param) {
        boolean needAuth;
        this.initCache(param);
        Set<String> orgAuthCodes = null;
        boolean bl = needAuth = param.getAuthType() != OrgDataOption.AuthType.NONE;
        if (needAuth) {
            UserLoginDTO currLoginUser = ShiroUtil.getUser();
            if (currLoginUser == null) {
                return null;
            }
            if ("super".equalsIgnoreCase(currLoginUser.getMgrFlag())) {
                needAuth = false;
            } else {
                orgAuthCodes = this.orgDataParamService.getOrgAuthService().listAuthOrg((UserDO)currLoginUser, param);
                if (orgAuthCodes == null) {
                    orgAuthCodes = new HashSet<String>();
                }
            }
        }
        if (needAuth && orgAuthCodes.isEmpty() && !param.isOnlyMarkAuth()) {
            return null;
        }
        if (orgAuthCodes != null) {
            param.put("orgAuthCodes", (Object)orgAuthCodes);
        } else {
            param.remove((Object)"orgAuthCodes");
        }
        String tenantName = param.getTenantName();
        String categoryName = param.getCategoryname();
        boolean isCurrentCategory = OrgContext.isCurrentCategory((String)tenantName, (String)categoryName);
        Map currColIndex = OrgContext.getColIndexContext();
        HashMap oldColIndex = null;
        HashMap oldColAllIndex = null;
        if (!isCurrentCategory) {
            if (currColIndex != null) {
                oldColIndex = new HashMap(currColIndex);
                oldColAllIndex = new HashMap(OrgContext.getColAllKeyContext());
            }
            OrgContext.bindColIndex((String)tenantName, (String)categoryName);
        }
        try {
            List<OrgDO> cacheList = null;
            if (param.getId() != null) {
                cacheList = this.listCacheById(param, needAuth);
                if (param.getQueryChildrenType() == null) {
                    List<OrgDO> list = cacheList;
                    return list;
                }
            } else {
                cacheList = StringUtils.hasText(param.getCode()) ? this.listCacheByCode(param, needAuth) : (param.getOrgCodes() != null ? this.listBatchCacheByCodes(param) : (param.getOrgOrgcodes() != null ? this.listBatchCacheByOrgcodes(param) : (StringUtils.hasText(param.getOrgcode()) ? this.listCacheByOrgcode(param, needAuth) : (StringUtils.hasText(param.getParentcode()) ? this.listCacheByParentCode(param, needAuth) : this.listAllCache(param, needAuth, orgAuthCodes)))));
            }
            this.doFilter(cacheList, param);
            List<OrgDO> list = cacheList;
            return list;
        }
        catch (Throwable e) {
            logger.error("\u7ec4\u7ec7\u673a\u6784\u67e5\u8be2\u5931\u8d25", e);
            List<OrgDO> list = null;
            return list;
        }
        finally {
            if (!isCurrentCategory) {
                if (currColIndex == null) {
                    OrgContext.unbindColIndex();
                } else {
                    OrgContext.bindColIndex((String)tenantName, (String)categoryName, oldColIndex, oldColAllIndex);
                }
            }
        }
    }

    private List<OrgDO> listAllCache(OrgDTO param, boolean needAuth, Set<String> orgAuthCodes) {
        String tenantName = param.getTenantName();
        String categoryName = param.getCategoryname();
        ArrayList<String> codeScopeParam = param.getCodeScope();
        if (!param.isOnlyMarkAuth() && needAuth) {
            if (codeScopeParam != null) {
                codeScopeParam.retainAll(orgAuthCodes);
            } else {
                codeScopeParam = new ArrayList<String>(orgAuthCodes);
            }
        }
        OrgVersionDO versionData = this.orgDataParamService.getOrgVersion(param);
        if (codeScopeParam != null) {
            OrgDTO paramByAuth = new OrgDTO();
            paramByAuth.setTenantName(tenantName);
            paramByAuth.setCategoryname(categoryName);
            paramByAuth.setOrgCodes(codeScopeParam);
            paramByAuth.put("orgVersionData", (Object)versionData);
            return this.listBatchCacheByCodes(paramByAuth);
        }
        if (versionData != null && !versionData.isActive()) {
            LinkedList<OrgDO> dataList = new LinkedList<OrgDO>();
            OrgDTO subParam = new OrgDTO();
            subParam.setTenantName(tenantName);
            subParam.setCategoryname(categoryName);
            subParam.setVersionDate(versionData.getValidtime());
            subParam.setStopflag(param.getStopflag());
            subParam.setRecoveryflag(param.getRecoveryflag());
            dataList.addAll(this.orgDataDao.list(subParam));
            return dataList;
        }
        LinkedList<Object> cacheList = null;
        if (this.cacheCompression) {
            ConcurrentHashMap<String, ConcurrentHashMap<UUID, byte[]>> currTableIdByteMap = idByteMap.get(tenantName);
            if (currTableIdByteMap != null && currTableIdByteMap.containsKey(categoryName)) {
                cacheList = new LinkedList<Object>();
                for (byte[] val : currTableIdByteMap.get(categoryName).values()) {
                    cacheList.add(dataSerializer.deserialize(val));
                }
            }
        } else {
            ConcurrentHashMap<String, ConcurrentHashMap<UUID, OrgDO>> currTableIdCacheMap = idMap.get(tenantName);
            if (currTableIdCacheMap != null && currTableIdCacheMap.containsKey(categoryName)) {
                cacheList = new LinkedList();
                cacheList.addAll(currTableIdCacheMap.get(categoryName).values());
            }
        }
        return cacheList;
    }

    private void doFilter(List<OrgDO> cacheList, OrgDTO param) {
        if (cacheList == null || cacheList.isEmpty()) {
            return;
        }
        OrgVersionDO versionData = this.orgDataParamService.getOrgVersion(param);
        if (versionData == null) {
            cacheList.clear();
            return;
        }
        if (!versionData.isActive()) {
            return;
        }
        Date versionDate = versionData.getValidtime();
        Integer paramStopflag = param.getStopflag();
        Integer dataStopflag = null;
        Integer paramRecoveryflag = param.getRecoveryflag();
        Integer dataRecoveryflag = null;
        OrgDO obj = null;
        Iterator<OrgDO> it = cacheList.iterator();
        while (it.hasNext()) {
            obj = it.next();
            if (versionDate.before(obj.getValidtime()) || !versionDate.before(obj.getInvalidtime())) {
                it.remove();
                continue;
            }
            dataStopflag = obj.getStopflag();
            if (paramStopflag != null) {
                if (paramStopflag != -1 && paramStopflag.intValue() != dataStopflag.intValue()) {
                    it.remove();
                    continue;
                }
            } else if (dataStopflag != null && dataStopflag == 1) {
                it.remove();
                continue;
            }
            dataRecoveryflag = obj.getRecoveryflag();
            if (paramRecoveryflag != null) {
                if (paramRecoveryflag == -1 || paramRecoveryflag.intValue() == dataRecoveryflag.intValue()) continue;
                it.remove();
                continue;
            }
            if (dataRecoveryflag == null || dataRecoveryflag != 1) continue;
            it.remove();
        }
    }

    public void initCache(OrgDTO param) {
        OrgVersionDO versionData;
        ConcurrentHashMap<String, ConcurrentHashMap<UUID, Date>> tenantVerActiveMap;
        String tenantName = param.getTenantName();
        String categoryName = param.getCategoryname();
        boolean isFirst = false;
        if (!verActiveMap.containsKey(tenantName)) {
            verActiveMap.putIfAbsent(tenantName, new ConcurrentHashMap());
            isFirst = true;
        }
        if (!(tenantVerActiveMap = verActiveMap.get(tenantName)).containsKey(categoryName)) {
            tenantVerActiveMap.putIfAbsent(categoryName, new ConcurrentHashMap());
            isFirst = true;
        }
        ConcurrentHashMap<UUID, Date> tableVerActive = tenantVerActiveMap.get(categoryName);
        if (isFirst && param.getVersionDate() == null) {
            OrgVersionDTO versionParam = new OrgVersionDTO();
            versionParam.setTenantName(tenantName);
            versionParam.setCategoryname(categoryName);
            List<OrgVersionDO> versionList = this.orgVersionService.listCache(versionParam);
            if (versionList != null && !versionList.isEmpty()) {
                for (OrgVersionDO orgVersionDO : versionList) {
                    if (!orgVersionDO.isActive()) continue;
                    tableVerActive.put(orgVersionDO.getId(), orgVersionDO.getValidtime());
                }
                this.syncCache(param, true);
                return;
            }
        }
        if ((versionData = this.orgDataParamService.getOrgVersion(param)) == null) {
            return;
        }
        if (!versionData.isActive()) {
            tableVerActive.remove(versionData.getId());
        } else if (!tableVerActive.containsKey(versionData.getId())) {
            tableVerActive.put(versionData.getId(), versionData.getValidtime());
            this.syncCache(param, true);
            return;
        }
        this.syncCache(param, false);
    }

    public void loadMultiDetailData(OrgDO param, List<? extends OrgDO> dataList) {
        this.loadMultiDetailData(param, dataList, false);
    }

    private void loadMultiDetailData(OrgDO param, List<? extends OrgDO> dataList, boolean loadAllData) {
        OrgCategoryDO categoryDO = new OrgCategoryDO();
        categoryDO.setTenantName(param.getTenantName());
        categoryDO.setName(param.getCategoryname());
        List<ZBDTO> zbList = this.orgCategoryService.listZB(categoryDO);
        if (zbList == null || zbList.isEmpty()) {
            return;
        }
        ArrayList<String> multipleField = new ArrayList<String>();
        for (ZBDTO zb : zbList) {
            if (zb.getMultiple() == null || zb.getMultiple() != 1) continue;
            multipleField.add(zb.getName().toLowerCase());
        }
        if (multipleField.isEmpty()) {
            return;
        }
        BaseDataDetailDO detailParam = new BaseDataDetailDO();
        detailParam.setTenantName(param.getTenantName());
        detailParam.setTablename(param.getCategoryname() + "_SUBLIST");
        if (loadAllData || dataList.size() > 300) {
            this.loadAllDataDetail(detailParam, multipleField, dataList);
        } else {
            this.loadPartDataDetail(detailParam, multipleField, dataList);
        }
    }

    private void loadPartDataDetail(BaseDataDetailDO param, List<String> multipleField, List<? extends OrgDO> dataList) {
        for (OrgDO orgDO : dataList) {
            for (String fieldname : multipleField) {
                param.setMasterid(orgDO.getId());
                param.setFieldname(fieldname.toUpperCase());
                List details = this.baseDataDetailClient.get(param);
                if (details != null && !details.isEmpty()) {
                    ArrayList<String> listCode = new ArrayList<String>();
                    for (BaseDataDetailDO detaildo : details) {
                        listCode.add(detaildo.getFieldvalue());
                    }
                    orgDO.put(fieldname, listCode);
                    continue;
                }
                orgDO.put(fieldname, null);
            }
        }
    }

    private void loadAllDataDetail(BaseDataDetailDO param, List<String> multipleField, List<? extends OrgDO> dataList) {
        List details = this.baseDataDetailClient.list(param);
        HashMap detailDataQueryMap = new HashMap(dataList.size());
        String fieldName = null;
        for (BaseDataDetailDO detailDO : details) {
            if (detailDataQueryMap.get(detailDO.getMasterid()) == null) {
                detailDataQueryMap.put(detailDO.getMasterid(), new HashMap());
            }
            fieldName = detailDO.getFieldname().toLowerCase();
            Map map = (Map)detailDataQueryMap.get(detailDO.getMasterid());
            if (map.get(fieldName) == null) {
                map.put(fieldName, new ArrayList());
            }
            ((List)map.get(fieldName)).add(detailDO.getFieldvalue());
        }
        Map subMap = null;
        for (OrgDO orgDO : dataList) {
            subMap = (Map)detailDataQueryMap.get(orgDO.getId());
            for (String fieldname : multipleField) {
                if (subMap == null) {
                    orgDO.put(fieldname, null);
                    continue;
                }
                List listCode = (List)subMap.get(fieldname);
                if (listCode != null && !listCode.isEmpty()) {
                    orgDO.put(fieldname, (Object)listCode);
                    continue;
                }
                orgDO.put(fieldname, null);
            }
        }
    }

    private List<OrgDO> listCacheById(OrgDTO param, boolean needAuth) {
        if (param.getId() == null) {
            return null;
        }
        OrgVersionDO versionData = this.orgDataParamService.getOrgVersion(param);
        if (versionData == null) {
            return null;
        }
        OrgDO data = null;
        if (!versionData.isActive()) {
            LinkedList<OrgCacheDO> dataList = this.orgDataDao.list(param);
            if (dataList != null && !dataList.isEmpty()) {
                data = (OrgDO)dataList.get(0);
            }
        } else {
            String tenantName = param.getTenantName();
            String categoryname = param.getCategoryname();
            if (this.cacheCompression) {
                ConcurrentHashMap<String, ConcurrentHashMap<UUID, byte[]>> currTableIdByteMap = idByteMap.get(tenantName);
                if (currTableIdByteMap != null && currTableIdByteMap.containsKey(categoryname)) {
                    data = dataSerializer.deserialize(currTableIdByteMap.get(categoryname).get(param.getId()));
                }
            } else {
                ConcurrentHashMap<String, ConcurrentHashMap<UUID, OrgDO>> currTableIdCacheMap = idMap.get(tenantName);
                if (currTableIdCacheMap != null && currTableIdCacheMap.containsKey(categoryname)) {
                    data = currTableIdCacheMap.get(categoryname).get(param.getId());
                }
            }
        }
        if (data == null) {
            return null;
        }
        if (param.getQueryChildrenType() != null) {
            param.setCode(data.getCode());
            return this.listCacheByCode(param, needAuth);
        }
        LinkedList<OrgDO> endList = new LinkedList<OrgDO>();
        endList.add(data);
        return endList;
    }

    private List<OrgDO> listCacheByCode(OrgDTO param, boolean needAuth) {
        ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> currCodeAllSubMap;
        ConcurrentHashMap<UUID, Boolean> codeIdMap;
        if ("-".equals(param.getCode())) {
            return this.listCacheByRoot(param, needAuth);
        }
        OrgVersionDO versionData = this.orgDataParamService.getOrgVersion(param);
        if (versionData == null) {
            return null;
        }
        String tenantName = param.getTenantName();
        String categoryname = param.getCategoryname();
        OrgDataOption.QueryChildrenType queryChildrenType = param.getQueryChildrenType();
        if (!versionData.isActive()) {
            OrgDTO subParam = new OrgDTO();
            subParam.setTenantName(tenantName);
            subParam.setCategoryname(categoryname);
            subParam.setCode(param.getCode());
            subParam.setVersionDate(versionData.getValidtime());
            subParam.setStopflag(param.getStopflag());
            subParam.setRecoveryflag(param.getRecoveryflag());
            LinkedList<OrgCacheDO> dataList = this.orgDataDao.list(subParam);
            if (dataList == null || dataList.isEmpty()) {
                return null;
            }
            LinkedList<OrgDO> datas = new LinkedList<OrgDO>();
            if (queryChildrenType == null) {
                datas.addAll(dataList);
                return datas;
            }
            subParam.setParents(((OrgCacheDO)dataList.get(0)).getParents());
            subParam.setQueryChildrenType(queryChildrenType);
            dataList = this.orgDataDao.list(subParam);
            if (dataList != null && !dataList.isEmpty()) {
                datas.addAll(dataList);
                if (param.isLazyLoad() && (queryChildrenType == OrgDataOption.QueryChildrenType.DIRECT_CHILDREN || queryChildrenType == OrgDataOption.QueryChildrenType.DIRECT_CHILDREN_WITH_SELF)) {
                    ArrayList<String> subCodes = new ArrayList<String>();
                    for (OrgCacheDO org : dataList) {
                        if (param.getCode().equals(org.getCode())) continue;
                        subCodes.add(org.getCode());
                    }
                    subParam.setParentcode(null);
                    subParam.setOrgCodes(subCodes);
                    subParam.setQueryChildrenType(OrgDataOption.QueryChildrenType.DIRECT_CHILDREN);
                    List<OrgDO> subSubDatas = this.listBatchCacheByCodes(subParam);
                    if (subSubDatas != null && !subSubDatas.isEmpty()) {
                        datas.addAll(subSubDatas);
                    }
                }
            }
            return datas;
        }
        ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>>> currTableDataCodeMap = codeMap.get(tenantName);
        if (currTableDataCodeMap == null) {
            return null;
        }
        Map currDataCodeMap = currTableDataCodeMap.get(categoryname);
        if (currDataCodeMap == null || currDataCodeMap.isEmpty()) {
            return null;
        }
        ConcurrentHashMap currCodeMap = (ConcurrentHashMap)currDataCodeMap.get(param.getCode());
        if (currCodeMap == null) {
            return null;
        }
        HashSet<UUID> refIds = new HashSet<UUID>();
        refIds.addAll(currCodeMap.keySet());
        List<OrgDO> orgs = this.listByIdSet(tenantName, categoryname, refIds, false);
        if (orgs == null || orgs.isEmpty()) {
            return null;
        }
        if (queryChildrenType == null) {
            return orgs;
        }
        this.doFilter(orgs, param);
        if (orgs.isEmpty()) {
            return null;
        }
        if (queryChildrenType != OrgDataOption.QueryChildrenType.DIRECT_CHILDREN_WITH_SELF && queryChildrenType != OrgDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF) {
            refIds.clear();
        }
        OrgDO currOrg = orgs.get(0);
        boolean loadNextChildren = false;
        if (queryChildrenType == OrgDataOption.QueryChildrenType.DIRECT_CHILDREN || queryChildrenType == OrgDataOption.QueryChildrenType.DIRECT_CHILDREN_WITH_SELF) {
            ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> currCodeDirectSubMap = codeDirectSubMap.get(tenantName).get(categoryname);
            codeIdMap = currCodeDirectSubMap.get(currOrg.getParents());
            if (codeIdMap != null) {
                refIds.addAll(codeIdMap.keySet());
            }
            loadNextChildren = param.isLazyLoad();
        }
        if ((queryChildrenType == OrgDataOption.QueryChildrenType.ALL_CHILDREN || queryChildrenType == OrgDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF) && (codeIdMap = (currCodeAllSubMap = codeAllSubMap.get(tenantName).get(categoryname)).get(currOrg.getParents())) != null) {
            refIds.addAll(codeIdMap.keySet());
        }
        return this.listByIdSet(tenantName, categoryname, refIds, loadNextChildren);
    }

    private List<OrgDO> listCacheByRoot(OrgDTO param, boolean needAuth) {
        Object includeOrg;
        ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> currCodeDirectSubMap;
        ConcurrentHashMap<UUID, Boolean> rootCodeCache;
        OrgVersionDO versionData;
        OrgDataOption.QueryChildrenType queryChildrenType = param.getQueryChildrenType();
        if (queryChildrenType == null) {
            return null;
        }
        boolean lazyLoad = param.isLazyLoad();
        if (lazyLoad && (needAuth || this.compatibleWithErrorPossible)) {
            queryChildrenType = OrgDataOption.QueryChildrenType.ALL_CHILDREN;
        }
        if ((versionData = this.orgDataParamService.getOrgVersion(param)) == null) {
            return null;
        }
        LinkedList<OrgDO> datas = new LinkedList<OrgDO>();
        String tenantName = param.getTenantName();
        String categoryname = param.getCategoryname();
        Collection allSubCodeScopeParam = null;
        if (queryChildrenType == OrgDataOption.QueryChildrenType.ALL_CHILDREN || queryChildrenType == OrgDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF) {
            allSubCodeScopeParam = param.getCodeScope();
            Set orgAuthCodes = (Set)param.get((Object)"orgAuthCodes");
            if (!param.isOnlyMarkAuth() && orgAuthCodes != null) {
                if (allSubCodeScopeParam != null) {
                    allSubCodeScopeParam.retainAll(orgAuthCodes);
                } else {
                    allSubCodeScopeParam = orgAuthCodes;
                }
            }
        }
        if (!versionData.isActive()) {
            OrgDTO subParam = new OrgDTO();
            subParam.setTenantName(tenantName);
            subParam.setCategoryname(categoryname);
            subParam.setVersionDate(versionData.getValidtime());
            subParam.setStopflag(param.getStopflag());
            subParam.setRecoveryflag(param.getRecoveryflag());
            if (allSubCodeScopeParam != null) {
                subParam.put("orgVersionData", param.get((Object)"orgVersionData"));
                subParam.setOrgCodes(new ArrayList(allSubCodeScopeParam));
                return this.listBatchCacheByCodes(subParam);
            }
            if (queryChildrenType == OrgDataOption.QueryChildrenType.DIRECT_CHILDREN || queryChildrenType == OrgDataOption.QueryChildrenType.DIRECT_CHILDREN_WITH_SELF) {
                subParam.setParentcode("-");
                LinkedList<OrgCacheDO> dataList = this.orgDataDao.list(subParam);
                if (dataList != null && !dataList.isEmpty()) {
                    datas.addAll(dataList);
                    if (lazyLoad) {
                        ArrayList<String> subCodes = new ArrayList<String>();
                        for (OrgCacheDO org : dataList) {
                            subCodes.add(org.getCode());
                        }
                        subParam.setParentcode(null);
                        subParam.setOrgCodes(subCodes);
                        subParam.setQueryChildrenType(OrgDataOption.QueryChildrenType.DIRECT_CHILDREN);
                        List<OrgDO> subSubDatas = this.listBatchCacheByCodes(subParam);
                        if (subSubDatas != null && !subSubDatas.isEmpty()) {
                            datas.addAll(subSubDatas);
                        }
                    }
                }
            } else {
                LinkedList<OrgCacheDO> dataList = this.orgDataDao.list(subParam);
                if (dataList != null && !dataList.isEmpty()) {
                    datas.addAll(dataList);
                }
            }
            return datas;
        }
        ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>>> currTableDataCodeMap = codeMap.get(tenantName);
        if (currTableDataCodeMap == null) {
            return null;
        }
        Map currDataCodeMap = currTableDataCodeMap.get(categoryname);
        if (currDataCodeMap == null || currDataCodeMap.isEmpty()) {
            return null;
        }
        ConcurrentHashMap<UUID, byte[]> currIdByteMap = null;
        ConcurrentHashMap<UUID, OrgDO> currIdMap = null;
        if (this.cacheCompression) {
            currIdByteMap = idByteMap.get(tenantName).get(categoryname);
        } else {
            currIdMap = idMap.get(tenantName).get(categoryname);
        }
        if ((queryChildrenType == OrgDataOption.QueryChildrenType.DIRECT_CHILDREN || queryChildrenType == OrgDataOption.QueryChildrenType.DIRECT_CHILDREN_WITH_SELF) && (rootCodeCache = (currCodeDirectSubMap = codeDirectSubMap.get(tenantName).get(categoryname)).get("-")) != null) {
            includeOrg = null;
            ConcurrentHashMap<UUID, Boolean> codeDCO = null;
            for (UUID id : rootCodeCache.keySet()) {
                includeOrg = this.cacheCompression ? dataSerializer.deserialize(currIdByteMap.get(id)) : currIdMap.get(id);
                if (includeOrg == null) continue;
                datas.add((OrgDO)includeOrg);
                if (!lazyLoad || (codeDCO = currCodeDirectSubMap.get(includeOrg.getParents())) == null) continue;
                for (UUID subid : codeDCO.keySet()) {
                    includeOrg = this.cacheCompression ? dataSerializer.deserialize(currIdByteMap.get(subid)) : currIdMap.get(subid);
                    if (includeOrg == null) continue;
                    datas.add((OrgDO)includeOrg);
                }
            }
        }
        if (queryChildrenType == OrgDataOption.QueryChildrenType.ALL_CHILDREN || queryChildrenType == OrgDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF) {
            if (allSubCodeScopeParam != null) {
                ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> currCodeMap = codeMap.get(tenantName).get(categoryname);
                ConcurrentHashMap<UUID, Boolean> codeCache = null;
                includeOrg = null;
                for (String orgCode : allSubCodeScopeParam) {
                    codeCache = currCodeMap.get(orgCode);
                    if (codeCache == null) continue;
                    for (UUID id : codeCache.keySet()) {
                        includeOrg = this.cacheCompression ? dataSerializer.deserialize(currIdByteMap.get(id)) : currIdMap.get(id);
                        if (includeOrg == null) continue;
                        datas.add((OrgDO)includeOrg);
                    }
                }
            } else if (this.cacheCompression) {
                for (byte[] val : currIdByteMap.values()) {
                    datas.add((OrgDO)dataSerializer.deserialize(val));
                }
            } else {
                datas.addAll(currIdMap.values());
            }
        }
        return datas;
    }

    private List<OrgDO> listBatchCacheByCodes(OrgDTO param) {
        List orgCodes = param.getOrgCodes();
        if (orgCodes == null || orgCodes.isEmpty()) {
            return null;
        }
        OrgVersionDO versionData = this.orgDataParamService.getOrgVersion(param);
        if (versionData == null) {
            return null;
        }
        String tenantName = param.getTenantName();
        String categoryname = param.getCategoryname();
        OrgDataOption.QueryChildrenType queryChildrenType = param.getQueryChildrenType();
        if (!versionData.isActive()) {
            LinkedList<OrgDO> datas = new LinkedList<OrgDO>();
            OrgDTO subParam = new OrgDTO();
            subParam.setTenantName(tenantName);
            subParam.setCategoryname(categoryname);
            subParam.setVersionDate(versionData.getValidtime());
            subParam.setStopflag(param.getStopflag());
            subParam.setRecoveryflag(param.getRecoveryflag());
            ArrayList<CompletableFuture<List>> futures = new ArrayList<CompletableFuture<List>>();
            if (queryChildrenType == null) {
                int dataSize = orgCodes.size();
                int perSize = 500;
                int cnt = dataSize / perSize + (dataSize % perSize > 0 ? 1 : 0);
                for (int i = 0; i < cnt; ++i) {
                    int startIndex = i * perSize;
                    int endIndex = startIndex + perSize > dataSize ? dataSize : startIndex + perSize;
                    CompletableFuture<List> future = CompletableFuture.supplyAsync(() -> {
                        OrgDTO tempParam = new OrgDTO((Map)subParam);
                        tempParam.setOrgCodes(orgCodes.subList(startIndex, endIndex));
                        return this.orgDataDao.list(tempParam);
                    }, this.getExecutorService());
                    futures.add(future);
                }
            } else {
                subParam.setQueryChildrenType(queryChildrenType);
                for (String code : orgCodes) {
                    CompletableFuture<List> future = CompletableFuture.supplyAsync(() -> {
                        OrgDTO tempParam = new OrgDTO((Map)subParam);
                        tempParam.setCode(code);
                        return this.orgDataDao.list(tempParam);
                    }, this.getExecutorService());
                    futures.add(future);
                }
            }
            CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            allFutures.thenAccept(v -> {
                for (CompletableFuture future : futures) {
                    try {
                        datas.addAll((Collection)future.get());
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            });
            allFutures.join();
            return datas;
        }
        ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>>> currTableDataCodeMap = codeMap.get(tenantName);
        if (currTableDataCodeMap == null) {
            return null;
        }
        Map currDataCodeMap = currTableDataCodeMap.get(categoryname);
        if (currDataCodeMap == null || currDataCodeMap.isEmpty()) {
            return null;
        }
        ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> currCodeDirectSubMap = null;
        ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> currCodeAllSubMap = null;
        if (queryChildrenType != null) {
            currCodeDirectSubMap = codeDirectSubMap.get(tenantName).get(categoryname);
            currCodeAllSubMap = codeAllSubMap.get(tenantName).get(categoryname);
        }
        HashSet<UUID> refIds = new HashSet<UUID>();
        ConcurrentHashMap<UUID, Boolean> codeIdMap = null;
        for (String code : orgCodes) {
            codeIdMap = (ConcurrentHashMap<UUID, Boolean>)currDataCodeMap.get(code);
            if (codeIdMap == null) continue;
            refIds.addAll(codeIdMap.keySet());
        }
        List<OrgDO> orgs = this.listByIdSet(tenantName, categoryname, refIds, false);
        if (orgs == null || orgs.isEmpty()) {
            return null;
        }
        if (queryChildrenType == null) {
            return orgs;
        }
        this.doFilter(orgs, param);
        if (orgs.isEmpty()) {
            return null;
        }
        if (queryChildrenType != OrgDataOption.QueryChildrenType.DIRECT_CHILDREN_WITH_SELF && queryChildrenType != OrgDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF) {
            refIds.clear();
        }
        for (OrgDO org : orgs) {
            if (queryChildrenType == OrgDataOption.QueryChildrenType.DIRECT_CHILDREN || queryChildrenType == OrgDataOption.QueryChildrenType.DIRECT_CHILDREN_WITH_SELF) {
                codeIdMap = currCodeDirectSubMap.get(org.getParents());
                if (codeIdMap == null) continue;
                refIds.addAll(codeIdMap.keySet());
                continue;
            }
            if (queryChildrenType != OrgDataOption.QueryChildrenType.ALL_CHILDREN && queryChildrenType != OrgDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF || (codeIdMap = currCodeAllSubMap.get(org.getParents())) == null) continue;
            refIds.addAll(codeIdMap.keySet());
        }
        return this.listByIdSet(tenantName, categoryname, refIds, false);
    }

    private List<OrgDO> listBatchCacheByOrgcodes(OrgDTO param) {
        List orgCodes = param.getOrgOrgcodes();
        if (orgCodes == null || orgCodes.isEmpty()) {
            return null;
        }
        OrgVersionDO versionData = this.orgDataParamService.getOrgVersion(param);
        if (versionData == null) {
            return null;
        }
        String tenantName = param.getTenantName();
        String categoryname = param.getCategoryname();
        if (!versionData.isActive()) {
            LinkedList<OrgDO> datas = new LinkedList<OrgDO>();
            OrgDTO subParam = new OrgDTO();
            subParam.setTenantName(tenantName);
            subParam.setCategoryname(categoryname);
            subParam.setVersionDate(versionData.getValidtime());
            subParam.setStopflag(param.getStopflag());
            subParam.setRecoveryflag(param.getRecoveryflag());
            ArrayList<CompletableFuture<List>> futures = new ArrayList<CompletableFuture<List>>();
            int dataSize = orgCodes.size();
            int perSize = 500;
            int cnt = dataSize / perSize + (dataSize % perSize > 0 ? 1 : 0);
            for (int i = 0; i < cnt; ++i) {
                int startIndex = i * perSize;
                int endIndex = startIndex + perSize > dataSize ? dataSize : startIndex + perSize;
                CompletableFuture<List> future = CompletableFuture.supplyAsync(() -> {
                    OrgDTO tempParam = new OrgDTO((Map)subParam);
                    tempParam.setOrgOrgcodes(orgCodes.subList(startIndex, endIndex));
                    return this.orgDataDao.list(tempParam);
                }, this.getExecutorService());
                futures.add(future);
            }
            CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            allFutures.thenAccept(v -> {
                for (CompletableFuture future : futures) {
                    try {
                        datas.addAll((Collection)future.get());
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            });
            allFutures.join();
            return datas;
        }
        ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>>> currTableDataCodeMap = orgcodeMap.get(tenantName);
        if (currTableDataCodeMap == null) {
            return null;
        }
        Map currDataCodeMap = currTableDataCodeMap.get(categoryname);
        if (currDataCodeMap == null || currDataCodeMap.isEmpty()) {
            return null;
        }
        HashSet<UUID> refIds = new HashSet<UUID>();
        ConcurrentHashMap codeDco = null;
        for (String code : orgCodes) {
            codeDco = (ConcurrentHashMap)currDataCodeMap.get(code);
            if (codeDco == null) continue;
            refIds.addAll(codeDco.keySet());
        }
        return this.listByIdSet(tenantName, categoryname, refIds, false);
    }

    private List<OrgDO> listCacheByOrgcode(OrgDTO param, boolean needAuth) {
        OrgDataOption.QueryChildrenType queryChildrenType = param.getQueryChildrenType();
        if (queryChildrenType != null) {
            OrgDTO param2 = new OrgDTO();
            param2.putAll((Map)param);
            param2.setQueryChildrenType(null);
            List<OrgDO> orgs = this.listCacheByOrgcode(param2, needAuth);
            this.doFilter(orgs, param2);
            if (orgs != null && !orgs.isEmpty()) {
                param2.setCode(orgs.get(0).getCode());
                param2.setQueryChildrenType(queryChildrenType);
                return this.listCacheByCode(param2, needAuth);
            }
            return null;
        }
        OrgVersionDO versionData = this.orgDataParamService.getOrgVersion(param);
        if (versionData == null) {
            return null;
        }
        String tenantName = param.getTenantName();
        String categoryname = param.getCategoryname();
        if (!versionData.isActive()) {
            LinkedList<OrgDO> datas = new LinkedList<OrgDO>();
            OrgDTO subParam = new OrgDTO();
            subParam.setTenantName(tenantName);
            subParam.setCategoryname(categoryname);
            subParam.setVersionDate(versionData.getValidtime());
            subParam.setStopflag(param.getStopflag());
            subParam.setRecoveryflag(param.getRecoveryflag());
            subParam.setOrgcode(param.getOrgcode());
            LinkedList<OrgCacheDO> dataList = this.orgDataDao.list(subParam);
            if (dataList != null && !dataList.isEmpty()) {
                datas.addAll(dataList);
            }
            return datas;
        }
        ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>>> currTableDataOrgcodeMap = orgcodeMap.get(tenantName);
        if (currTableDataOrgcodeMap == null) {
            return null;
        }
        Map currOrgcodeMap = currTableDataOrgcodeMap.get(categoryname);
        if (currOrgcodeMap == null || currOrgcodeMap.isEmpty()) {
            return null;
        }
        ConcurrentHashMap refIds = (ConcurrentHashMap)currOrgcodeMap.get(param.getOrgcode());
        if (refIds == null || refIds.isEmpty()) {
            return null;
        }
        return this.listByIdSet(tenantName, categoryname, refIds.keySet(), false);
    }

    private List<OrgDO> listCacheByParentCode(OrgDTO param, boolean needAuth) {
        OrgDTO parentParam = new OrgDTO();
        parentParam.setTenantName(param.getTenantName());
        parentParam.setCategoryname(param.getCategoryname());
        parentParam.setCode(param.getParentcode());
        parentParam.setQueryChildrenType(OrgDataOption.QueryChildrenType.DIRECT_CHILDREN);
        parentParam.setLazyLoad(Boolean.valueOf(param.isLazyLoad()));
        parentParam.setCodeScope(param.getCodeScope());
        parentParam.setStopflag(param.getStopflag());
        parentParam.setRecoveryflag(param.getRecoveryflag());
        parentParam.setVersionDate(param.getVersionDate());
        parentParam.put("orgVersionData", param.get((Object)"orgVersionData"));
        parentParam.put("orgAuthCodes", param.get((Object)"orgAuthCodes"));
        return this.listCacheByCode(parentParam, needAuth);
    }

    private List<OrgDO> listByIdSet(String tenantName, String categoryname, Set<UUID> refIds, boolean loadNextChildren) {
        Map currIdByteMap = null;
        Map currIdMap = null;
        if (this.cacheCompression) {
            ConcurrentHashMap<String, ConcurrentHashMap<UUID, byte[]>> currTableDataIdByteMap = idByteMap.get(tenantName);
            if (currTableDataIdByteMap == null) {
                return null;
            }
            currIdByteMap = currTableDataIdByteMap.get(categoryname);
            if (currIdByteMap == null || currIdByteMap.isEmpty()) {
                return null;
            }
        } else {
            ConcurrentHashMap<String, ConcurrentHashMap<UUID, OrgDO>> currTableDataIdMap = idMap.get(tenantName);
            if (currTableDataIdMap == null) {
                return null;
            }
            currIdMap = currTableDataIdMap.get(categoryname);
            if (currIdMap == null || currIdMap.isEmpty()) {
                return null;
            }
        }
        ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> currCodeDirectSubMap = null;
        if (loadNextChildren) {
            currCodeDirectSubMap = codeDirectSubMap.get(tenantName).get(categoryname);
        }
        LinkedList<OrgDO> endList = new LinkedList<OrgDO>();
        Object includeOrg = null;
        ConcurrentHashMap<UUID, Boolean> codeDCO = null;
        HashSet<UUID> tempSet = new HashSet<UUID>();
        for (UUID id : refIds) {
            includeOrg = this.cacheCompression ? dataSerializer.deserialize((byte[])currIdByteMap.get(id)) : (OrgDO)currIdMap.get(id);
            if (includeOrg == null) continue;
            if (!tempSet.contains(id)) {
                tempSet.add(id);
                endList.add((OrgDO)includeOrg);
            }
            if (!loadNextChildren || (codeDCO = currCodeDirectSubMap.get(includeOrg.getParents())) == null) continue;
            for (UUID subid : codeDCO.keySet()) {
                includeOrg = this.cacheCompression ? dataSerializer.deserialize((byte[])currIdByteMap.get(subid)) : (OrgDO)currIdMap.get(subid);
                if (includeOrg == null || tempSet.contains(subid)) continue;
                tempSet.add(subid);
                endList.add((OrgDO)includeOrg);
            }
        }
        return endList;
    }

    public Set<String> getRefCodeList(OrgDTO param) {
        this.initCache(param);
        HashSet<String> codeSet = new HashSet<String>();
        String code = param.getCode();
        OrgDataOption.QueryChildrenType queryChildrenType = param.getQueryChildrenType();
        if (code == null || queryChildrenType == null) {
            return codeSet;
        }
        OrgDTO subParam = new OrgDTO();
        subParam.setTenantName(param.getTenantName());
        subParam.setCategoryname(param.getCategoryname());
        subParam.setAuthType(OrgDataOption.AuthType.NONE);
        subParam.setStopflag(Integer.valueOf(-1));
        subParam.setRecoveryflag(Integer.valueOf(-1));
        subParam.setCode(code);
        subParam.setQueryChildrenType(queryChildrenType);
        subParam.setVersionDate(param.getVersionDate());
        List<OrgDO> list = this.listCacheByCode(subParam, false);
        this.doFilter(list, subParam);
        if (list != null && !list.isEmpty()) {
            for (OrgDO orgDO : list) {
                codeSet.add(orgDO.getCode());
            }
        }
        return codeSet;
    }
}

