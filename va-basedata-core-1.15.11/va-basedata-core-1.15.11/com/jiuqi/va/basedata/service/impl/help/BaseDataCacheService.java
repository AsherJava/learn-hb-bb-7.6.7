/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.basedata.auth.service.VaBaseDataAuthService
 *  com.jiuqi.va.basedata.common.BaseDataContext
 *  com.jiuqi.va.basedata.domain.BaseDataVersionDO
 *  com.jiuqi.va.basedata.domain.BaseDataVersionDTO
 *  com.jiuqi.va.domain.basedata.BaseDataCacheDO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.basedata.service.impl.help;

import com.jiuqi.va.basedata.auth.service.VaBaseDataAuthService;
import com.jiuqi.va.basedata.common.BaseDataContext;
import com.jiuqi.va.basedata.common.BaseDataKryoSerializer;
import com.jiuqi.va.basedata.config.VaBasedataCoreConfig;
import com.jiuqi.va.basedata.dao.VaBaseDataDao;
import com.jiuqi.va.basedata.domain.BaseDataSyncCacheDTO;
import com.jiuqi.va.basedata.domain.BaseDataVersionDO;
import com.jiuqi.va.basedata.domain.BaseDataVersionDTO;
import com.jiuqi.va.basedata.service.BaseDataDetailService;
import com.jiuqi.va.basedata.service.BaseDataVersionService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataCacheCoordinationService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataParamService;
import com.jiuqi.va.domain.basedata.BaseDataCacheDO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
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

@Component(value="vaBaseDataCacheService")
public class BaseDataCacheService {
    private static Logger logger = LoggerFactory.getLogger(BaseDataCacheService.class);
    @Value(value="${nvwa.basedata.cache.compression:false}")
    private boolean cacheCompression = false;
    @Value(value="${nvwa.basedata.cache.sync-wait:200}")
    private int cacheSyncWait = 200;
    @Autowired
    private VaBaseDataDao baseDataDao;
    @Autowired
    private BaseDataParamService baseDataParamService;
    @Autowired
    private BaseDataVersionService baseDataVersionService;
    @Autowired
    private BaseDataCacheCoordinationService baseDataCacheCoordinationService;
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, BaseDataCacheDO>>> idMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, byte[]>>> idByteMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>>>> objcodeMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>>>> codeMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>>>> codeDirectSubMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>>>> codeAllSubMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Date>>> verActiveMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<BigDecimal, Boolean>>> forceUpadteMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<BigDecimal, Boolean>>> dataVerMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, Boolean> lockMap = new ConcurrentHashMap();
    private static ConcurrentHashMap<String, Boolean> checkSyncMap = new ConcurrentHashMap();
    private static BaseDataKryoSerializer dataSerializer = new BaseDataKryoSerializer();
    private BaseDataDetailService baseDataDetailService;
    private VaBaseDataAuthService baseDataAuthService;
    private ExecutorService executorService = null;

    private BaseDataDetailService getBaseDataDetailService() {
        if (this.baseDataDetailService == null) {
            this.baseDataDetailService = (BaseDataDetailService)ApplicationContextRegister.getBean(BaseDataDetailService.class);
        }
        return this.baseDataDetailService;
    }

    private ExecutorService getExecutorService() {
        if (this.executorService == null) {
            this.executorService = Executors.newWorkStealingPool();
        }
        return this.executorService;
    }

    private VaBaseDataAuthService getVaBaseDataAuthService() {
        if (this.baseDataAuthService == null) {
            this.baseDataAuthService = (VaBaseDataAuthService)ApplicationContextRegister.getBean(VaBaseDataAuthService.class);
        }
        return this.baseDataAuthService;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void handleSyncCacheMsg(BaseDataSyncCacheDTO bdscd) {
        String tenantName = bdscd.getTenantName();
        BaseDataDTO paramDTO = bdscd.getBaseDataDTO();
        String tableName = paramDTO.getTableName();
        try {
            ConcurrentHashMap<BigDecimal, Boolean> tableVer;
            ConcurrentHashMap<String, ConcurrentHashMap<UUID, Date>> tenantActiveMap = verActiveMap.get(tenantName);
            if (tenantActiveMap == null || !tenantActiveMap.containsKey(tableName)) {
                return;
            }
            if (bdscd.isForceUpdate()) {
                ConcurrentHashMap<BigDecimal, Boolean> tableForceInfo;
                ConcurrentHashMap<String, ConcurrentHashMap<BigDecimal, Boolean>> tenantForceInfo = forceUpadteMap.get(tenantName);
                if (tenantForceInfo == null) {
                    forceUpadteMap.putIfAbsent(tenantName, new ConcurrentHashMap());
                    tenantForceInfo = forceUpadteMap.get(tenantName);
                }
                if ((tableForceInfo = tenantForceInfo.get(tableName)) == null) {
                    tenantForceInfo.putIfAbsent(tableName, new ConcurrentHashMap());
                    tableForceInfo = tenantForceInfo.get(tableName);
                }
                if (paramDTO.getQueryStartVer() != null) {
                    tableForceInfo.put(paramDTO.getQueryStartVer(), true);
                } else {
                    tableForceInfo.put(OrderNumUtil.getOrderNumByCurrentTimeMillis(), true);
                }
                return;
            }
            if (bdscd.isClean()) {
                this.cleanTableCache((BaseDataDO)paramDTO, false);
                return;
            }
            if (bdscd.isRemove()) {
                this.removeCache(paramDTO);
                return;
            }
            ConcurrentHashMap<String, ConcurrentHashMap<BigDecimal, Boolean>> tenantVer = dataVerMap.get(tenantName);
            if (tenantVer == null) {
                dataVerMap.putIfAbsent(tenantName, new ConcurrentHashMap());
                tenantVer = dataVerMap.get(tenantName);
            }
            if ((tableVer = tenantVer.get(tableName)) == null) {
                tenantVer.putIfAbsent(tableName, new ConcurrentHashMap());
                tableVer = tenantVer.get(tableName);
            }
            tableVer.put(paramDTO.getQueryStartVer(), true);
        }
        catch (Throwable e) {
            logger.error("\u57fa\u7840\u6570\u636e\u540c\u6b65\u6d88\u606f\u5f02\u5e38", e);
        }
        finally {
            if (EnvConfig.getCurrNodeId().equals(bdscd.getCurrNodeId())) {
                checkSyncMap.put(bdscd.getCheckKey(), true);
            }
        }
    }

    /*
     * Exception decompiling
     */
    public void syncCache(BaseDataDO param, boolean force) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[TRYBLOCK]], but top level block is 17[WHILELOOP]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public void handleSensitive(BaseDataDO param, Map<String, String> sensitiveFields) {
        block8: {
            BaseDataCacheDO data;
            HashMap<String, Integer> diffColIndex;
            String tenantName;
            String tableName;
            block7: {
                tableName = param.getTableName();
                if (!this.baseDataCacheCoordinationService.isCanLoadByCurrentNode(tableName)) {
                    return;
                }
                if (sensitiveFields == null || sensitiveFields.isEmpty()) {
                    return;
                }
                tenantName = param.getTenantName();
                Map colIndex = BaseDataContext.getColIndexMap((String)tenantName, (String)tableName);
                diffColIndex = new HashMap<String, Integer>();
                for (String col : sensitiveFields.keySet()) {
                    if (!colIndex.containsKey(col)) continue;
                    diffColIndex.put(col, (Integer)colIndex.get(col));
                }
                if (diffColIndex.isEmpty()) {
                    return;
                }
                data = null;
                if (!this.cacheCompression) break block7;
                ConcurrentHashMap<String, ConcurrentHashMap<UUID, byte[]>> currTableIdByteMap = idByteMap.get(tenantName);
                if (currTableIdByteMap == null || !currTableIdByteMap.containsKey(tableName)) break block8;
                ConcurrentHashMap<UUID, byte[]> chMap = currTableIdByteMap.get(tableName);
                for (Map.Entry<UUID, byte[]> entry : chMap.entrySet()) {
                    data = dataSerializer.deserialize(entry.getValue());
                    this.baseDataParamService.convertRealValue(data, diffColIndex, sensitiveFields);
                    chMap.put(entry.getKey(), dataSerializer.serialize(data));
                }
                break block8;
            }
            ConcurrentHashMap<String, ConcurrentHashMap<UUID, BaseDataCacheDO>> currTableIdMap = idMap.get(tenantName);
            if (currTableIdMap != null && currTableIdMap.containsKey(tableName)) {
                ConcurrentHashMap<UUID, BaseDataCacheDO> chMap = currTableIdMap.get(tableName);
                for (Map.Entry<UUID, BaseDataCacheDO> entry : chMap.entrySet()) {
                    data = entry.getValue();
                    this.baseDataParamService.convertRealValue(data, diffColIndex, sensitiveFields);
                    chMap.put(entry.getKey(), data);
                }
            }
        }
    }

    private void removeIds(String tenantName, String tableName, boolean isTreeStyle, Set<UUID> allIdSet) {
        ConcurrentHashMap<UUID, BaseDataCacheDO> currIdMap = null;
        ConcurrentHashMap<UUID, byte[]> currIdByteMap = null;
        if (this.cacheCompression) {
            if (idByteMap.containsKey(tenantName)) {
                currIdByteMap = idByteMap.get(tenantName).get(tableName);
            }
        } else if (idMap.containsKey(tenantName)) {
            currIdMap = idMap.get(tenantName).get(tableName);
        }
        if (currIdMap == null && currIdByteMap == null) {
            return;
        }
        if (this.cacheCompression) {
            currIdByteMap.entrySet().removeIf(entry -> !allIdSet.contains(entry.getKey()));
        } else {
            currIdMap.entrySet().removeIf(entry -> !allIdSet.contains(entry.getKey()));
        }
        ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> currObjcodeCacheMap = objcodeMap.get(tenantName).get(tableName);
        currObjcodeCacheMap.entrySet().forEach(obj -> ((ConcurrentHashMap)obj.getValue()).keySet().removeIf(acid -> !allIdSet.contains(acid)));
        ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> currCodeCacheMap = codeMap.get(tenantName).get(tableName);
        currCodeCacheMap.entrySet().forEach(obj -> ((ConcurrentHashMap)obj.getValue()).keySet().removeIf(acid -> !allIdSet.contains(acid)));
        if (!isTreeStyle) {
            return;
        }
        ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> currCodeDirectSubCacheMap = codeDirectSubMap.get(tenantName).get(tableName);
        currCodeDirectSubCacheMap.entrySet().forEach(obj -> ((ConcurrentHashMap)obj.getValue()).keySet().removeIf(acid -> !allIdSet.contains(acid)));
        ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> currCodeAllSubCacheMap = codeAllSubMap.get(tenantName).get(tableName);
        currCodeAllSubCacheMap.entrySet().forEach(obj -> ((ConcurrentHashMap)obj.getValue()).keySet().removeIf(acid -> !allIdSet.contains(acid)));
    }

    private void initTableCache(String tenantName, String tableName, boolean isTreeStyle) {
        ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>>> currTableCodeCacheMap;
        ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>>> currTableObjetCodeMap;
        if (this.cacheCompression) {
            ConcurrentHashMap<String, ConcurrentHashMap<UUID, byte[]>> currTableIdByteMap;
            if (!idByteMap.containsKey(tenantName)) {
                idByteMap.putIfAbsent(tenantName, new ConcurrentHashMap());
            }
            if (!(currTableIdByteMap = idByteMap.get(tenantName)).containsKey(tableName)) {
                currTableIdByteMap.putIfAbsent(tableName, new ConcurrentHashMap());
            }
        } else {
            ConcurrentHashMap<String, ConcurrentHashMap<UUID, BaseDataCacheDO>> currTableIdMap;
            if (!idMap.containsKey(tenantName)) {
                idMap.putIfAbsent(tenantName, new ConcurrentHashMap());
            }
            if (!(currTableIdMap = idMap.get(tenantName)).containsKey(tableName)) {
                currTableIdMap.putIfAbsent(tableName, new ConcurrentHashMap());
            }
        }
        if (!objcodeMap.containsKey(tenantName)) {
            objcodeMap.putIfAbsent(tenantName, new ConcurrentHashMap());
        }
        if (!(currTableObjetCodeMap = objcodeMap.get(tenantName)).containsKey(tableName)) {
            currTableObjetCodeMap.putIfAbsent(tableName, new ConcurrentHashMap());
        }
        if (!codeMap.containsKey(tenantName)) {
            codeMap.putIfAbsent(tenantName, new ConcurrentHashMap());
        }
        if (!(currTableCodeCacheMap = codeMap.get(tenantName)).containsKey(tableName)) {
            currTableCodeCacheMap.putIfAbsent(tableName, new ConcurrentHashMap());
        }
        if (isTreeStyle) {
            this.initSubCache(tenantName, tableName);
        }
    }

    private void initSubCache(String tenantName, String tableName) {
        ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>>> currTableCodeAllSubCacheMap;
        ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>>> currTableCodeDirectSubCacheMap;
        if (!codeDirectSubMap.containsKey(tenantName)) {
            codeDirectSubMap.putIfAbsent(tenantName, new ConcurrentHashMap());
        }
        if (!(currTableCodeDirectSubCacheMap = codeDirectSubMap.get(tenantName)).containsKey(tableName)) {
            currTableCodeDirectSubCacheMap.putIfAbsent(tableName, new ConcurrentHashMap());
        }
        if (!codeAllSubMap.containsKey(tenantName)) {
            codeAllSubMap.putIfAbsent(tenantName, new ConcurrentHashMap());
        }
        if (!(currTableCodeAllSubCacheMap = codeAllSubMap.get(tenantName)).containsKey(tableName)) {
            currTableCodeAllSubCacheMap.putIfAbsent(tableName, new ConcurrentHashMap());
        }
    }

    public void pushSyncMsg(BaseDataSyncCacheDTO bdscd) {
        bdscd.setCurrNodeId(EnvConfig.getCurrNodeId());
        bdscd.setRetry(0);
        try {
            if (!EnvConfig.getRedisEnable()) {
                this.handleSyncCacheMsg(bdscd);
                return;
            }
            if (!this.tryPushBroadcast(bdscd)) {
                logger.error(bdscd.getBaseDataDTO().getTableName() + "\u57fa\u7840\u6570\u636e\u9879\u4fe1\u606f\u901a\u8fc7redis\u5e7f\u64ad\u9a8c\u8bc1\u6b21\u6570\u8fbe\u5230\u4e0a\u9650\uff0c\u4ec5\u5904\u7406\u672c\u5730\u7f13\u5b58\u3002");
                this.handleSyncCacheMsg(bdscd);
            }
        }
        catch (Throwable e) {
            logger.error("\u673a\u6784\u6570\u636e\u4fe1\u606f\u5e7f\u64ad\u5931\u8d25", e);
        }
        finally {
            checkSyncMap.remove(bdscd.getCheckKey());
        }
    }

    private boolean tryPushBroadcast(BaseDataSyncCacheDTO bdscd) {
        int cnt;
        if (bdscd.getRetry() > 3) {
            return false;
        }
        EnvConfig.sendRedisMsg((String)VaBasedataCoreConfig.getBaseDataSyncCachePub(), (String)JSONUtil.toJSONString((Object)((Object)bdscd)));
        String checkKey = bdscd.getCheckKey();
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
            bdscd.setRetry(bdscd.getRetry() + 1);
            return this.tryPushBroadcast(bdscd);
        }
        return true;
    }

    public void cleanTableCache(BaseDataDO param, boolean isOnlyCleanData) {
        String tableName = param.getTableName();
        String tenantName = param.getTenantName();
        if (this.cacheCompression) {
            if (idByteMap.containsKey(tenantName)) {
                idByteMap.get(tenantName).remove(tableName);
                if (isOnlyCleanData) {
                    idByteMap.get(tenantName).putIfAbsent(tableName, new ConcurrentHashMap());
                }
            }
        } else if (idMap.containsKey(tenantName)) {
            idMap.get(tenantName).remove(tableName);
            if (isOnlyCleanData) {
                idMap.get(tenantName).putIfAbsent(tableName, new ConcurrentHashMap());
            }
        }
        if (objcodeMap.containsKey(tenantName)) {
            objcodeMap.get(tenantName).remove(tableName);
            if (isOnlyCleanData) {
                objcodeMap.get(tenantName).putIfAbsent(tableName, new ConcurrentHashMap());
            }
        }
        if (codeMap.containsKey(tenantName)) {
            codeMap.get(tenantName).remove(tableName);
            if (isOnlyCleanData) {
                codeMap.get(tenantName).putIfAbsent(tableName, new ConcurrentHashMap());
            }
        }
        if (codeDirectSubMap.containsKey(tenantName) && codeDirectSubMap.get(tenantName).containsKey(tableName)) {
            codeDirectSubMap.get(tenantName).remove(tableName);
            if (isOnlyCleanData) {
                codeDirectSubMap.get(tenantName).putIfAbsent(tableName, new ConcurrentHashMap());
            }
        }
        if (codeAllSubMap.containsKey(tenantName) && codeAllSubMap.get(tenantName).containsKey(tableName)) {
            codeAllSubMap.get(tenantName).remove(tableName);
            if (isOnlyCleanData) {
                codeAllSubMap.get(tenantName).putIfAbsent(tableName, new ConcurrentHashMap());
            }
        }
        if (!isOnlyCleanData && verActiveMap.containsKey(tenantName)) {
            verActiveMap.get(tenantName).remove(tableName);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void removeCache(BaseDataDTO param) {
        if (this.baseDataParamService.isCacheDisabled((BaseDataDO)param)) {
            return;
        }
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
        BaseDataVersionDO versionDO = this.baseDataParamService.getBaseDataVersion(param);
        if (versionDO == null) {
            return;
        }
        String tenantName = param.getTenantName();
        String tableName = param.getTableName();
        ConcurrentHashMap<String, ConcurrentHashMap<UUID, Date>> tenantActiveMap = verActiveMap.get(tenantName);
        if (tenantActiveMap == null) {
            return;
        }
        ConcurrentHashMap<UUID, Date> verActive = tenantActiveMap.get(tableName);
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
                this.cleanTableCache((BaseDataDO)param, true);
                return;
            }
            ArrayList<BaseDataCacheDO> cacheList = new ArrayList<BaseDataCacheDO>();
            if (this.cacheCompression) {
                ConcurrentHashMap<String, ConcurrentHashMap<UUID, byte[]>> currTableIdByteMap = idByteMap.get(tenantName);
                if (currTableIdByteMap != null && currTableIdByteMap.containsKey(tableName)) {
                    for (byte[] val : currTableIdByteMap.get(tableName).values()) {
                        cacheList.add(dataSerializer.deserialize(val));
                    }
                }
            } else {
                ConcurrentHashMap<String, ConcurrentHashMap<UUID, BaseDataCacheDO>> currTableIdCacheMap = idMap.get(tenantName);
                if (currTableIdCacheMap != null && currTableIdCacheMap.containsKey(tableName)) {
                    cacheList.addAll(currTableIdCacheMap.get(tableName).values());
                }
            }
            if (cacheList.isEmpty()) {
                return;
            }
            for (BaseDataDO baseDataDO : cacheList) {
                if (!baseDataDO.getValidtime().equals(versionDO.getValidtime()) || !baseDataDO.getInvalidtime().equals(versionDO.getInvalidtime())) continue;
                ids.add(baseDataDO.getId());
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

    private void removeCacheByIds(BaseDataDTO param, Set<UUID> ids) {
        ConcurrentHashMap<String, ConcurrentHashMap<UUID, byte[]>> dataTableMap;
        if (ids == null || ids.isEmpty()) {
            return;
        }
        String tenantName = param.getTenantName();
        String tableName = param.getTableName();
        boolean flag = false;
        if (this.cacheCompression) {
            dataTableMap = idByteMap.get(tenantName);
            if (dataTableMap != null && dataTableMap.containsKey(tableName)) {
                for (UUID id : ids) {
                    dataTableMap.get(tableName).remove(id);
                }
                flag = true;
            }
        } else {
            dataTableMap = idMap.get(tenantName);
            if (dataTableMap != null && dataTableMap.containsKey(tableName)) {
                for (UUID id : ids) {
                    dataTableMap.get(tableName).remove(id);
                }
                flag = true;
            }
        }
        if (flag) {
            Map codeAllSubCache;
            Map codeCache;
            Map orgcodeCache = objcodeMap.get(tenantName).get(tableName);
            if (orgcodeCache != null) {
                orgcodeCache.entrySet().forEach(obj -> ((ConcurrentHashMap)obj.getValue()).keySet().removeIf(acid -> ids.contains(acid)));
            }
            if ((codeCache = (Map)codeMap.get(tenantName).get(tableName)) != null) {
                codeCache.entrySet().forEach(obj -> ((ConcurrentHashMap)obj.getValue()).keySet().removeIf(acid -> ids.contains(acid)));
            }
            if (codeDirectSubMap.get(tenantName) == null) {
                return;
            }
            Map codeDirectSubCache = codeDirectSubMap.get(tenantName).get(tableName);
            if (codeDirectSubCache != null) {
                codeDirectSubCache.entrySet().forEach(obj -> ((ConcurrentHashMap)obj.getValue()).keySet().removeIf(acid -> ids.contains(acid)));
            }
            if ((codeAllSubCache = (Map)codeAllSubMap.get(tenantName).get(tableName)) != null) {
                codeAllSubCache.entrySet().forEach(obj -> ((ConcurrentHashMap)obj.getValue()).keySet().removeIf(acid -> ids.contains(acid)));
            }
        }
    }

    public void initCache(BaseDataDTO param) {
        BaseDataVersionDO versionData;
        ConcurrentHashMap<String, ConcurrentHashMap<UUID, Date>> tenantVerActiveMap;
        if (this.baseDataParamService.isCacheDisabled((BaseDataDO)param)) {
            return;
        }
        String tenantName = param.getTenantName();
        String tableName = param.getTableName();
        boolean isFirst = false;
        if (!verActiveMap.containsKey(tenantName)) {
            verActiveMap.putIfAbsent(tenantName, new ConcurrentHashMap());
            isFirst = true;
        }
        if (!(tenantVerActiveMap = verActiveMap.get(tenantName)).containsKey(tableName)) {
            tenantVerActiveMap.putIfAbsent(tableName, new ConcurrentHashMap());
            isFirst = true;
        }
        ConcurrentHashMap<UUID, Date> tableVerActive = tenantVerActiveMap.get(tableName);
        if (isFirst && param.getVersionDate() == null) {
            BaseDataVersionDTO versionParam = new BaseDataVersionDTO();
            versionParam.setTenantName(tenantName);
            versionParam.setTablename(param.getTableName());
            List<BaseDataVersionDO> versionList = this.baseDataVersionService.listCache(versionParam);
            if (versionList != null && !versionList.isEmpty()) {
                for (BaseDataVersionDO versionDO : versionList) {
                    if (!versionDO.isActive()) continue;
                    tableVerActive.put(versionDO.getId(), versionDO.getValidtime());
                }
                this.syncCache((BaseDataDO)param, true);
                return;
            }
        }
        if ((versionData = this.baseDataParamService.getBaseDataVersion(param)) == null) {
            return;
        }
        if (!versionData.isActive()) {
            tableVerActive.remove(versionData.getId());
        } else if (!tableVerActive.containsKey(versionData.getId())) {
            tableVerActive.put(versionData.getId(), versionData.getValidtime());
            this.syncCache((BaseDataDO)param, true);
            return;
        }
        this.syncCache((BaseDataDO)param, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<BaseDataDO> listBasicCacheData(BaseDataDTO param) {
        boolean needAuth;
        if (!this.baseDataCacheCoordinationService.isCanLoadByCurrentNode(param.getTableName())) {
            return null;
        }
        this.initCache(param);
        HashSet<String> dataAuthSets = null;
        boolean bl = needAuth = param.getAuthType() != BaseDataOption.AuthType.NONE;
        if (needAuth && !this.baseDataParamService.isAuthStarted((BaseDataDO)param)) {
            needAuth = false;
        }
        if (needAuth) {
            UserLoginDTO user = ShiroUtil.getUser();
            if (user == null) {
                return null;
            }
            boolean hasAllDataAuth = this.getVaBaseDataAuthService().hasAllDataAuth(param, (UserDO)user);
            if (hasAllDataAuth) {
                needAuth = false;
            }
            if (needAuth && (dataAuthSets = this.getVaBaseDataAuthService().getAuthByUser(param, (UserDO)user, new BaseDataDO[0])) == null) {
                dataAuthSets = new HashSet<String>();
            }
        }
        if (needAuth && dataAuthSets.isEmpty() && !param.isOnlyMarkAuth()) {
            return null;
        }
        if (dataAuthSets != null) {
            param.put("dataAuthSet", (Object)dataAuthSets);
        } else {
            param.remove((Object)"dataAuthSet");
        }
        boolean ignoreIsolation = false;
        String tenantName = param.getTenantName();
        String tableName = param.getTableName();
        boolean isCurrentCategory = BaseDataContext.isCurrentCategory((String)tenantName, (String)tableName);
        Map currColIndex = BaseDataContext.getColIndexContext();
        HashMap oldColIndex = null;
        HashMap oldColAllIndex = null;
        if (!isCurrentCategory) {
            if (currColIndex != null) {
                oldColIndex = new HashMap(currColIndex);
                oldColAllIndex = new HashMap(BaseDataContext.getColAllKeyContext());
            }
            BaseDataContext.bindColIndex((String)tenantName, (String)tableName);
        }
        try {
            List<BaseDataCacheDO> cacheList = null;
            if (param.getId() != null) {
                cacheList = this.listCacheById(param);
                if (param.getQueryChildrenType() == null) {
                    if (cacheList == null) {
                        List<BaseDataDO> list = null;
                        return list;
                    }
                    LinkedList<BaseDataCacheDO> linkedList = new LinkedList<BaseDataCacheDO>(cacheList);
                    return linkedList;
                }
            } else if (StringUtils.hasText(param.getObjectcode())) {
                cacheList = this.listCacheByObjectcode(param);
                ignoreIsolation = param.getQueryChildrenType() == null;
            } else if (param.getBaseDataObjectcodes() != null) {
                cacheList = this.listBatchCacheByObjectcodes(param);
                ignoreIsolation = true;
            } else {
                cacheList = StringUtils.hasText(param.getCode()) ? this.listCacheByCode(param) : (param.getBaseDataCodes() != null ? this.listBatchCacheByCodes(param) : (StringUtils.hasText(param.getParentcode()) ? this.listCacheByParentCode(param) : this.listAllCache(param, dataAuthSets)));
            }
            this.doFilter(cacheList, param, ignoreIsolation);
            if (cacheList == null) {
                List<BaseDataDO> list = null;
                return list;
            }
            LinkedList<BaseDataCacheDO> linkedList = new LinkedList<BaseDataCacheDO>(cacheList);
            return linkedList;
        }
        catch (Throwable e) {
            logger.error("\u57fa\u7840\u6570\u636e\u67e5\u8be2\u5f02\u5e38", e);
            List<BaseDataDO> list = null;
            return list;
        }
        finally {
            if (!isCurrentCategory) {
                if (currColIndex == null) {
                    BaseDataContext.unbindColIndex();
                } else {
                    BaseDataContext.bindColIndex((String)tenantName, (String)tableName, oldColIndex, oldColAllIndex);
                }
            }
        }
    }

    private List<BaseDataCacheDO> listAllCache(BaseDataDTO param, Set<String> dataAuthSets) {
        String tenantName = param.getTenantName();
        String tableName = param.getTableName();
        List codeScopeParam = param.getCodeScope();
        ArrayList<String> objectcodeScopeParam = param.getObjectcodeScope();
        if (!param.isOnlyMarkAuth() && dataAuthSets != null) {
            if (objectcodeScopeParam != null) {
                objectcodeScopeParam.retainAll(dataAuthSets);
            } else {
                objectcodeScopeParam = new ArrayList<String>(dataAuthSets);
            }
        }
        if (objectcodeScopeParam != null) {
            BaseDataDTO paramByAuth = new BaseDataDTO();
            paramByAuth.setTenantName(tenantName);
            paramByAuth.setTableName(tableName);
            paramByAuth.setBaseDataObjectcodes(objectcodeScopeParam);
            return this.listBatchCacheByObjectcodes(paramByAuth);
        }
        if (codeScopeParam != null) {
            BaseDataDTO paramByAuth = new BaseDataDTO();
            paramByAuth.setTenantName(tenantName);
            paramByAuth.setTableName(tableName);
            paramByAuth.setBaseDataCodes(codeScopeParam);
            return this.listBatchCacheByCodes(paramByAuth);
        }
        BaseDataVersionDO versionData = this.baseDataParamService.getBaseDataVersion(param);
        if (versionData != null && !versionData.isActive()) {
            BaseDataDTO subParam = new BaseDataDTO();
            subParam.setTenantName(tenantName);
            subParam.setTableName(tableName);
            subParam.setVersionDate(versionData.getValidtime());
            subParam.setStopflag(param.getStopflag());
            subParam.setRecoveryflag(param.getRecoveryflag());
            LinkedList<BaseDataCacheDO> datas = this.baseDataDao.list(subParam);
            this.baseDataParamService.decodeSecurityFields(datas, (BaseDataDO)subParam);
            this.baseDataParamService.convertRealValue(datas, (BaseDataDO)subParam);
            return datas;
        }
        LinkedList<BaseDataCacheDO> cacheList = null;
        if (this.cacheCompression) {
            ConcurrentHashMap<String, ConcurrentHashMap<UUID, byte[]>> currTableIdByteMap = idByteMap.get(tenantName);
            if (currTableIdByteMap != null && currTableIdByteMap.containsKey(tableName)) {
                cacheList = new LinkedList<BaseDataCacheDO>();
                for (byte[] val : currTableIdByteMap.get(tableName).values()) {
                    cacheList.add(dataSerializer.deserialize(val));
                }
            }
        } else {
            ConcurrentHashMap<String, ConcurrentHashMap<UUID, BaseDataCacheDO>> currTableIdMap = idMap.get(tenantName);
            if (currTableIdMap != null && currTableIdMap.containsKey(tableName)) {
                cacheList = new LinkedList();
                cacheList.addAll(currTableIdMap.get(tableName).values());
            }
        }
        return cacheList;
    }

    private void doFilter(List<BaseDataCacheDO> cacheList, BaseDataDTO param, boolean ignoreIsolation) {
        if (cacheList == null || cacheList.isEmpty()) {
            return;
        }
        if (param.containsKey((Object)"shareForceCheck") && ((Boolean)param.get((Object)"shareForceCheck")).booleanValue()) {
            ignoreIsolation = false;
        }
        List sharefields = null;
        if (!ignoreIsolation && param.containsKey((Object)"sharefields")) {
            sharefields = (List)param.get((Object)"sharefields");
        }
        Set shareUnitcodes = null;
        if (!ignoreIsolation && param.containsKey((Object)"shareUnitcodes")) {
            Object shareUtCds = param.get((Object)"shareUnitcodes");
            shareUnitcodes = shareUtCds instanceof List ? new HashSet((List)shareUtCds) : (Set)shareUtCds;
        }
        if (!ignoreIsolation && sharefields != null && shareUnitcodes == null && param.getUnitcode() != null) {
            shareUnitcodes = new HashSet();
            shareUnitcodes.add(param.getUnitcode());
        }
        String groupFieldName = param.getGroupFieldName();
        int groupFiledIndex = -1;
        if (StringUtils.hasText(groupFieldName)) {
            Map colIndexMap = BaseDataContext.getColIndexContext();
            groupFieldName = groupFieldName.toLowerCase();
            groupFiledIndex = (Integer)colIndexMap.get(groupFieldName);
        } else {
            groupFieldName = null;
        }
        List groupNameList = param.getGroupNames();
        boolean groupFilter = groupFieldName != null && groupNameList != null;
        HashSet groupNames = groupFilter ? new HashSet(groupNameList) : null;
        Date versionDate = param.getVersionDate();
        if (versionDate == null) {
            UserLoginDTO userLoginDTO = ShiroUtil.getUser();
            if (userLoginDTO != null) {
                versionDate = userLoginDTO.getLoginDate();
            }
            if (versionDate == null) {
                versionDate = new Date();
            }
        }
        BaseDataCacheDO data = null;
        Iterator<BaseDataCacheDO> it = cacheList.iterator();
        while (it.hasNext()) {
            data = it.next();
            if (shareUnitcodes != null && !shareUnitcodes.contains(data.getUnitcode())) {
                it.remove();
                continue;
            }
            if (sharefields != null) {
                boolean insulateFilterFlag = false;
                for (String field : sharefields) {
                    if ("unitcode".equals(field) || param.get((Object)field) == null || "-".equals(data.get((Object)field)) || param.get((Object)field).equals(data.get((Object)field))) continue;
                    insulateFilterFlag = true;
                }
                if (insulateFilterFlag) {
                    it.remove();
                    continue;
                }
            }
            if (groupFilter && !groupNames.contains((String)data.getFieldValue((Object)groupFieldName, groupFiledIndex, true))) {
                it.remove();
                continue;
            }
            if (data.getValidtime() != null && versionDate.before(data.getValidtime())) {
                it.remove();
                continue;
            }
            if (data.getInvalidtime() != null && !versionDate.before(data.getInvalidtime())) {
                it.remove();
                continue;
            }
            if (data.getStopflag() != null) {
                if (param.getStopflag() != null) {
                    if (param.getStopflag() != -1 && param.getStopflag().intValue() != data.getStopflag().intValue()) {
                        it.remove();
                        continue;
                    }
                } else if (data.getStopflag() != 0) {
                    it.remove();
                    continue;
                }
            }
            if (data.getRecoveryflag() == null) continue;
            if (param.getRecoveryflag() != null) {
                if (param.getRecoveryflag() == -1 || param.getRecoveryflag().intValue() == data.getRecoveryflag().intValue()) continue;
                it.remove();
                continue;
            }
            if (data.getRecoveryflag() == 0) continue;
            it.remove();
        }
    }

    private List<BaseDataCacheDO> listCacheById(BaseDataDTO param) {
        if (param.getId() == null) {
            return null;
        }
        BaseDataVersionDO versionData = this.baseDataParamService.getBaseDataVersion(param);
        if (versionData == null) {
            return null;
        }
        String tenantName = param.getTenantName();
        String tableName = param.getTableName();
        BaseDataCacheDO data = null;
        if (!versionData.isActive()) {
            LinkedList<BaseDataCacheDO> dataList = this.baseDataDao.list(param);
            if (dataList != null && !dataList.isEmpty()) {
                data = (BaseDataCacheDO)dataList.get(0);
            }
        } else if (this.cacheCompression) {
            ConcurrentHashMap<String, ConcurrentHashMap<UUID, byte[]>> currTableIdByteMap = idByteMap.get(tenantName);
            if (currTableIdByteMap != null && currTableIdByteMap.containsKey(tableName)) {
                data = dataSerializer.deserialize(currTableIdByteMap.get(tableName).get(param.getId()));
            }
        } else {
            ConcurrentHashMap<String, ConcurrentHashMap<UUID, BaseDataCacheDO>> currTableIdMap = idMap.get(tenantName);
            if (currTableIdMap != null && currTableIdMap.containsKey(tableName)) {
                data = currTableIdMap.get(tableName).get(param.getId());
            }
        }
        if (data == null) {
            return null;
        }
        if (param.getQueryChildrenType() != null) {
            param.setCode(data.getCode());
            return this.listCacheByCode(param);
        }
        LinkedList<BaseDataCacheDO> endList = new LinkedList<BaseDataCacheDO>();
        endList.add(data);
        if (!versionData.isActive()) {
            this.baseDataParamService.decodeSecurityFields(endList, (BaseDataDO)param);
            this.baseDataParamService.convertRealValue(endList, (BaseDataDO)param);
        }
        return endList;
    }

    private List<BaseDataCacheDO> listCacheByObjectcode(BaseDataDTO param) {
        BaseDataOption.QueryChildrenType queryChildrenType = param.getQueryChildrenType();
        if (queryChildrenType != null) {
            if (param.getCode() == null) {
                BaseDataDTO param2 = new BaseDataDTO();
                param2.putAll((Map)param);
                param2.setQueryChildrenType(null);
                List<BaseDataCacheDO> dataList = this.listCacheByObjectcode(param2);
                this.doFilter(dataList, param2, false);
                if (dataList != null && !dataList.isEmpty()) {
                    param2.setCode(dataList.get(0).getCode());
                    param2.setQueryChildrenType(queryChildrenType);
                    return this.listCacheByCode(param2);
                }
                return null;
            }
            return this.listCacheByCode(param);
        }
        BaseDataVersionDO versionData = this.baseDataParamService.getBaseDataVersion(param);
        if (versionData == null) {
            return null;
        }
        String tenantName = param.getTenantName();
        String tableName = param.getTableName();
        if (!versionData.isActive()) {
            BaseDataDTO subParam = new BaseDataDTO();
            subParam.setTenantName(tenantName);
            subParam.setTableName(tableName);
            subParam.setVersionDate(versionData.getValidtime());
            subParam.setStopflag(param.getStopflag());
            subParam.setRecoveryflag(param.getRecoveryflag());
            subParam.setObjectcode(param.getObjectcode());
            LinkedList<BaseDataCacheDO> dataList = this.baseDataDao.list(subParam);
            this.baseDataParamService.decodeSecurityFields(dataList, (BaseDataDO)subParam);
            this.baseDataParamService.convertRealValue(dataList, (BaseDataDO)subParam);
            return dataList;
        }
        ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>>> currTableObjetCodeMap = objcodeMap.get(tenantName);
        if (currTableObjetCodeMap == null || currTableObjetCodeMap.isEmpty()) {
            return null;
        }
        Map currObjectcodeMap = currTableObjetCodeMap.get(tableName);
        if (currObjectcodeMap == null || currObjectcodeMap.isEmpty()) {
            return null;
        }
        ConcurrentHashMap refIds = (ConcurrentHashMap)currObjectcodeMap.get(param.getObjectcode());
        if (refIds == null || refIds.isEmpty()) {
            return null;
        }
        return this.listByIdSet(param, refIds.keySet(), false);
    }

    private List<BaseDataCacheDO> listBatchCacheByObjectcodes(BaseDataDTO param) {
        List objcodeList = param.getBaseDataObjectcodes();
        if (objcodeList == null || objcodeList.isEmpty()) {
            return null;
        }
        BaseDataVersionDO versionData = this.baseDataParamService.getBaseDataVersion(param);
        if (versionData == null) {
            return null;
        }
        String tenantName = param.getTenantName();
        String tableName = param.getTableName();
        if (!versionData.isActive()) {
            LinkedList<BaseDataCacheDO> datas = new LinkedList<BaseDataCacheDO>();
            BaseDataDTO subParam = new BaseDataDTO();
            subParam.setTenantName(tenantName);
            subParam.setTableName(tableName);
            subParam.setVersionDate(versionData.getValidtime());
            subParam.setStopflag(param.getStopflag());
            subParam.setRecoveryflag(param.getRecoveryflag());
            ArrayList<CompletableFuture<List>> futures = new ArrayList<CompletableFuture<List>>();
            int dataSize = objcodeList.size();
            int perSize = 2000;
            int cnt = dataSize / perSize + (dataSize % perSize > 0 ? 1 : 0);
            for (int i = 0; i < cnt; ++i) {
                int startIndex = i * perSize;
                int endIndex = startIndex + perSize > dataSize ? dataSize : startIndex + perSize;
                CompletableFuture<List> future = CompletableFuture.supplyAsync(() -> {
                    BaseDataDTO tempParam = new BaseDataDTO((Map)subParam);
                    tempParam.setBaseDataObjectcodes(objcodeList.subList(startIndex, endIndex));
                    return this.baseDataDao.list(tempParam);
                }, this.getExecutorService());
                futures.add(future);
            }
            CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            allFutures.thenAccept(v -> {
                for (CompletableFuture future : futures) {
                    try {
                        datas.addAll((Collection)future.get());
                    }
                    catch (Throwable e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            });
            allFutures.join();
            this.baseDataParamService.decodeSecurityFields(datas, (BaseDataDO)subParam);
            this.baseDataParamService.convertRealValue(datas, (BaseDataDO)subParam);
            return datas;
        }
        ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>>> currTableObjetCodeMap = objcodeMap.get(tenantName);
        if (currTableObjetCodeMap == null || currTableObjetCodeMap.isEmpty()) {
            return null;
        }
        Map currObjectcodeMap = currTableObjetCodeMap.get(tableName);
        if (currObjectcodeMap == null || currObjectcodeMap.isEmpty()) {
            return null;
        }
        HashSet<UUID> refIds = new HashSet<UUID>();
        ConcurrentHashMap codeDco = null;
        for (String code : objcodeList) {
            codeDco = (ConcurrentHashMap)currObjectcodeMap.get(code);
            if (codeDco == null) continue;
            refIds.addAll(codeDco.keySet());
        }
        return this.listByIdSet(param, refIds, false);
    }

    private List<BaseDataCacheDO> listByIdSet(BaseDataDTO param, Set<UUID> refIds, boolean loadNextChildren) {
        String tenantName = param.getTenantName();
        String tableName = param.getTableName();
        Map currIdByteMap = null;
        Map currIdMap = null;
        if (this.cacheCompression) {
            ConcurrentHashMap<String, ConcurrentHashMap<UUID, byte[]>> currTableDataIdByteMap = idByteMap.get(tenantName);
            if (currTableDataIdByteMap == null) {
                return null;
            }
            currIdByteMap = currTableDataIdByteMap.get(tableName);
            if (currIdByteMap == null || currIdByteMap.isEmpty()) {
                return null;
            }
        } else {
            ConcurrentHashMap<String, ConcurrentHashMap<UUID, BaseDataCacheDO>> currTableDataIdMap = idMap.get(tenantName);
            if (currTableDataIdMap == null) {
                return null;
            }
            currIdMap = currTableDataIdMap.get(tableName);
            if (currIdMap == null || currIdMap.isEmpty()) {
                return null;
            }
        }
        ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> currCodeDirectSubMap = null;
        Set shareUnitcodes = null;
        if (loadNextChildren) {
            currCodeDirectSubMap = codeDirectSubMap.get(tenantName).get(tableName);
            if (param.containsKey((Object)"shareUnitcodes")) {
                Object shareUtCds = param.get((Object)"shareUnitcodes");
                shareUnitcodes = shareUtCds instanceof List ? new HashSet((List)shareUtCds) : (Set)shareUtCds;
            }
            if (shareUnitcodes == null && param.getUnitcode() != null) {
                shareUnitcodes = new HashSet();
                shareUnitcodes.add("-");
                shareUnitcodes.add(param.getUnitcode());
            }
        }
        LinkedList<BaseDataCacheDO> endList = new LinkedList<BaseDataCacheDO>();
        BaseDataCacheDO includeData = null;
        ConcurrentHashMap<UUID, Boolean> codeDCO = null;
        HashSet<UUID> tempSet = new HashSet<UUID>();
        for (UUID id : refIds) {
            if (tempSet.contains(id) || (includeData = this.cacheCompression ? dataSerializer.deserialize((byte[])currIdByteMap.get(id)) : (BaseDataCacheDO)currIdMap.get(id)) == null || shareUnitcodes != null && !shareUnitcodes.contains(includeData.getUnitcode())) continue;
            tempSet.add(id);
            endList.add(includeData);
            if (!loadNextChildren || (codeDCO = currCodeDirectSubMap.get(includeData.getCode())) == null) continue;
            for (UUID subid : codeDCO.keySet()) {
                if (tempSet.contains(subid) || (includeData = this.cacheCompression ? dataSerializer.deserialize((byte[])currIdByteMap.get(subid)) : (BaseDataCacheDO)currIdMap.get(subid)) == null || shareUnitcodes != null && !shareUnitcodes.contains(includeData.getUnitcode())) continue;
                tempSet.add(subid);
                endList.add(includeData);
            }
        }
        return endList;
    }

    private List<BaseDataCacheDO> listCacheByCode(BaseDataDTO param) {
        BaseDataOption.QueryChildrenType queryChildrenType = param.getQueryChildrenType();
        boolean isTreeStyle = true;
        BaseDataDefineDO define = this.baseDataParamService.getBaseDataDefineDO((BaseDataDO)param);
        if (define.getStructtype() == null || define.getStructtype() < 2) {
            isTreeStyle = false;
        }
        if (!isTreeStyle && queryChildrenType != null && !"-".equals(param.getCode())) {
            if (queryChildrenType == BaseDataOption.QueryChildrenType.DIRECT_CHILDREN_WITH_SELF || queryChildrenType == BaseDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF) {
                queryChildrenType = null;
            } else {
                return null;
            }
        }
        if ("-".equals(param.getCode())) {
            return this.listCacheByRoot(param, isTreeStyle);
        }
        BaseDataVersionDO versionData = this.baseDataParamService.getBaseDataVersion(param);
        if (versionData == null) {
            return null;
        }
        String tenantName = param.getTenantName();
        String tableName = param.getTableName();
        if (!versionData.isActive()) {
            LinkedList<BaseDataCacheDO> dataList;
            BaseDataDTO subParam = new BaseDataDTO();
            subParam.setTenantName(tenantName);
            subParam.setTableName(tableName);
            subParam.setCode(param.getCode());
            subParam.setVersionDate(versionData.getValidtime());
            subParam.setStopflag(param.getStopflag());
            subParam.setRecoveryflag(param.getRecoveryflag());
            if (isTreeStyle && queryChildrenType != null) {
                if (param.isLazyLoad()) {
                    queryChildrenType = BaseDataOption.QueryChildrenType.ALL_CHILDREN;
                }
                if (!(queryChildrenType != BaseDataOption.QueryChildrenType.ALL_CHILDREN && queryChildrenType != BaseDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF || (dataList = this.baseDataDao.list(subParam)) == null || dataList.isEmpty())) {
                    subParam.setParents(((BaseDataCacheDO)dataList.get(0)).getParents());
                }
                subParam.setQueryChildrenType(queryChildrenType);
            }
            dataList = this.baseDataDao.list(subParam);
            this.baseDataParamService.decodeSecurityFields(dataList, (BaseDataDO)subParam);
            this.baseDataParamService.convertRealValue(dataList, (BaseDataDO)subParam);
            return dataList;
        }
        ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>>> currTableDataCodeMap = codeMap.get(tenantName);
        if (currTableDataCodeMap == null) {
            return null;
        }
        Map currDataCodeMap = currTableDataCodeMap.get(tableName);
        if (currDataCodeMap == null || currDataCodeMap.isEmpty()) {
            return null;
        }
        ConcurrentHashMap currCodeMap = (ConcurrentHashMap)currDataCodeMap.get(param.getCode());
        if (currCodeMap == null) {
            return null;
        }
        HashSet<UUID> refIds = new HashSet<UUID>();
        if (queryChildrenType == null || queryChildrenType == BaseDataOption.QueryChildrenType.DIRECT_CHILDREN_WITH_SELF || queryChildrenType == BaseDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF) {
            refIds.addAll(currCodeMap.keySet());
        }
        boolean loadNextChildren = false;
        if (isTreeStyle && queryChildrenType != null) {
            ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> currCodeAllSubMap;
            ConcurrentHashMap<UUID, Boolean> codeIdMap;
            if (queryChildrenType == BaseDataOption.QueryChildrenType.DIRECT_CHILDREN || queryChildrenType == BaseDataOption.QueryChildrenType.DIRECT_CHILDREN_WITH_SELF) {
                ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> currCodeDirectSubMap = codeDirectSubMap.get(tenantName).get(tableName);
                codeIdMap = currCodeDirectSubMap.get(param.getCode());
                if (codeIdMap != null) {
                    refIds.addAll(codeIdMap.keySet());
                }
                loadNextChildren = param.isLazyLoad();
            }
            if ((queryChildrenType == BaseDataOption.QueryChildrenType.ALL_CHILDREN || queryChildrenType == BaseDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF) && (codeIdMap = (currCodeAllSubMap = codeAllSubMap.get(tenantName).get(tableName)).get(param.getCode())) != null) {
                refIds.addAll(codeIdMap.keySet());
            }
        }
        return this.listByIdSet(param, refIds, loadNextChildren);
    }

    private List<BaseDataCacheDO> listCacheByRoot(BaseDataDTO param, boolean isTreeStyle) {
        ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> currCodeDirectSubMap;
        ConcurrentHashMap<UUID, Boolean> rootCodeCache;
        BaseDataVersionDO versionData;
        BaseDataOption.QueryChildrenType queryChildrenType = param.getQueryChildrenType();
        if (queryChildrenType == null) {
            return null;
        }
        if (param.isLazyLoad() || !isTreeStyle) {
            queryChildrenType = BaseDataOption.QueryChildrenType.ALL_CHILDREN;
        }
        if ((versionData = this.baseDataParamService.getBaseDataVersion(param)) == null) {
            return null;
        }
        String tenantName = param.getTenantName();
        String tableName = param.getTableName();
        if (versionData != null && !versionData.isActive()) {
            BaseDataDTO subParam = new BaseDataDTO();
            subParam.setTenantName(tenantName);
            subParam.setTableName(tableName);
            subParam.setVersionDate(versionData.getValidtime());
            subParam.setStopflag(param.getStopflag());
            subParam.setRecoveryflag(param.getRecoveryflag());
            if (queryChildrenType == BaseDataOption.QueryChildrenType.DIRECT_CHILDREN || queryChildrenType == BaseDataOption.QueryChildrenType.DIRECT_CHILDREN_WITH_SELF) {
                subParam.setParentcode("-");
            }
            LinkedList<BaseDataCacheDO> dataList = this.baseDataDao.list(subParam);
            this.baseDataParamService.decodeSecurityFields(dataList, (BaseDataDO)subParam);
            this.baseDataParamService.convertRealValue(dataList, (BaseDataDO)subParam);
            return dataList;
        }
        ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>>> currTableDataCodeMap = codeMap.get(tenantName);
        if (currTableDataCodeMap == null) {
            return null;
        }
        Map currDataCodeMap = currTableDataCodeMap.get(tableName);
        if (currDataCodeMap == null || currDataCodeMap.isEmpty()) {
            return null;
        }
        ConcurrentHashMap<UUID, byte[]> currIdByteMap = null;
        ConcurrentHashMap<UUID, BaseDataCacheDO> currIdMap = null;
        if (this.cacheCompression) {
            currIdByteMap = idByteMap.get(tenantName).get(tableName);
        } else {
            currIdMap = idMap.get(tenantName).get(tableName);
        }
        LinkedList<BaseDataCacheDO> datas = new LinkedList<BaseDataCacheDO>();
        if ((queryChildrenType == BaseDataOption.QueryChildrenType.DIRECT_CHILDREN || queryChildrenType == BaseDataOption.QueryChildrenType.DIRECT_CHILDREN_WITH_SELF) && (rootCodeCache = (currCodeDirectSubMap = codeDirectSubMap.get(tenantName).get(tableName)).get("-")) != null) {
            BaseDataCacheDO includeData = null;
            for (UUID id : rootCodeCache.keySet()) {
                includeData = this.cacheCompression ? dataSerializer.deserialize(currIdByteMap.get(id)) : currIdMap.get(id);
                if (includeData == null) continue;
                datas.add(includeData);
            }
        }
        if (queryChildrenType == BaseDataOption.QueryChildrenType.ALL_CHILDREN || queryChildrenType == BaseDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF) {
            List codeScopeParam = param.getCodeScope();
            Collection objectcodeScopeParam = param.getObjectcodeScope();
            Set dataAuthCodes = (Set)param.get((Object)"dataAuthSet");
            if (!param.isOnlyMarkAuth() && dataAuthCodes != null) {
                if (objectcodeScopeParam != null) {
                    objectcodeScopeParam.retainAll(dataAuthCodes);
                } else {
                    objectcodeScopeParam = dataAuthCodes;
                }
            }
            if (objectcodeScopeParam != null) {
                ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> currObjectcodeMap = objcodeMap.get(tenantName).get(tableName);
                ConcurrentHashMap<UUID, Boolean> objcodeCache = null;
                BaseDataCacheDO includeData = null;
                for (String dataCode : objectcodeScopeParam) {
                    objcodeCache = currObjectcodeMap.get(dataCode);
                    if (objcodeCache == null) continue;
                    for (UUID id : objcodeCache.keySet()) {
                        includeData = this.cacheCompression ? dataSerializer.deserialize(currIdByteMap.get(id)) : currIdMap.get(id);
                        if (includeData == null) continue;
                        datas.add(includeData);
                    }
                }
            } else if (codeScopeParam != null) {
                ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> currCodeMap = codeMap.get(tenantName).get(tableName);
                ConcurrentHashMap<UUID, Boolean> codeCache = null;
                BaseDataCacheDO includeData = null;
                for (String dataCode : codeScopeParam) {
                    codeCache = currCodeMap.get(dataCode);
                    if (codeCache == null) continue;
                    for (UUID id : codeCache.keySet()) {
                        includeData = this.cacheCompression ? dataSerializer.deserialize(currIdByteMap.get(id)) : currIdMap.get(id);
                        if (includeData == null) continue;
                        datas.add(includeData);
                    }
                }
            } else if (this.cacheCompression) {
                for (byte[] val : currIdByteMap.values()) {
                    datas.add(dataSerializer.deserialize(val));
                }
            } else {
                datas.addAll(currIdMap.values());
            }
        }
        return datas;
    }

    private List<BaseDataCacheDO> listCacheByParentCode(BaseDataDTO param) {
        BaseDataDTO parentParam = new BaseDataDTO();
        parentParam.putAll((Map)param);
        parentParam.setParentcode(null);
        parentParam.setCode(param.getParentcode());
        parentParam.setQueryChildrenType(BaseDataOption.QueryChildrenType.DIRECT_CHILDREN);
        return this.listCacheByCode(parentParam);
    }

    private List<BaseDataCacheDO> listBatchCacheByCodes(BaseDataDTO param) {
        List dataCodes = param.getBaseDataCodes();
        if (dataCodes == null || dataCodes.isEmpty()) {
            return null;
        }
        BaseDataVersionDO versionData = this.baseDataParamService.getBaseDataVersion(param);
        if (versionData == null) {
            return null;
        }
        String tenantName = param.getTenantName();
        String tableName = param.getTableName();
        BaseDataOption.QueryChildrenType queryChildrenType = param.getQueryChildrenType();
        boolean isTreeStyle = true;
        BaseDataDefineDO define = this.baseDataParamService.getBaseDataDefineDO((BaseDataDO)param);
        if (define.getStructtype() == null || define.getStructtype() < 2) {
            isTreeStyle = false;
        }
        if (!versionData.isActive()) {
            LinkedList<BaseDataCacheDO> datas = new LinkedList<BaseDataCacheDO>();
            BaseDataDTO subParam = new BaseDataDTO();
            subParam.setTenantName(tenantName);
            subParam.setTableName(tableName);
            subParam.setVersionDate(versionData.getValidtime());
            subParam.setStopflag(param.getStopflag());
            subParam.setRecoveryflag(param.getRecoveryflag());
            ArrayList<CompletableFuture<List>> futures = new ArrayList<CompletableFuture<List>>();
            if (queryChildrenType == null || !isTreeStyle && (queryChildrenType == BaseDataOption.QueryChildrenType.DIRECT_CHILDREN_WITH_SELF || queryChildrenType == BaseDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF)) {
                int dataSize = dataCodes.size();
                int perSize = 2000;
                int cnt = dataSize / perSize + (dataSize % perSize > 0 ? 1 : 0);
                for (int i = 0; i < cnt; ++i) {
                    int startIndex = i * perSize;
                    int endIndex = startIndex + perSize > dataSize ? dataSize : startIndex + perSize;
                    CompletableFuture<List> future = CompletableFuture.supplyAsync(() -> {
                        BaseDataDTO tempParam = new BaseDataDTO((Map)subParam);
                        tempParam.setBaseDataCodes(dataCodes.subList(startIndex, endIndex));
                        return this.baseDataDao.list(tempParam);
                    }, this.getExecutorService());
                    futures.add(future);
                }
            } else if (isTreeStyle && queryChildrenType != null) {
                subParam.setQueryChildrenType(queryChildrenType);
                for (String code : dataCodes) {
                    CompletableFuture<List> future = CompletableFuture.supplyAsync(() -> {
                        BaseDataDTO tempParam = new BaseDataDTO((Map)subParam);
                        tempParam.setCode(code);
                        return this.baseDataDao.list(tempParam);
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
                    catch (Throwable e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            });
            allFutures.join();
            this.baseDataParamService.decodeSecurityFields(datas, (BaseDataDO)subParam);
            this.baseDataParamService.convertRealValue(datas, (BaseDataDO)subParam);
            return datas;
        }
        ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>>> currTableDataCodeMap = codeMap.get(tenantName);
        if (currTableDataCodeMap == null) {
            return null;
        }
        ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> currCodeMap = currTableDataCodeMap.get(tableName);
        if (currCodeMap == null || currCodeMap.isEmpty()) {
            return null;
        }
        ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> currCodeDirectSubMap = null;
        ConcurrentHashMap<String, ConcurrentHashMap<UUID, Boolean>> currCodeAllSubMap = null;
        if (isTreeStyle && queryChildrenType != null) {
            currCodeDirectSubMap = codeDirectSubMap.get(tenantName).get(tableName);
            currCodeAllSubMap = codeAllSubMap.get(tenantName).get(tableName);
        }
        HashSet<UUID> refIds = new HashSet<UUID>();
        ConcurrentHashMap<UUID, Boolean> codeIdMap = null;
        for (String code : dataCodes) {
            codeIdMap = currCodeMap.get(code);
            if (codeIdMap == null) continue;
            if (queryChildrenType == null || queryChildrenType == BaseDataOption.QueryChildrenType.DIRECT_CHILDREN_WITH_SELF || queryChildrenType == BaseDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF) {
                refIds.addAll(codeIdMap.keySet());
            }
            if (!isTreeStyle || queryChildrenType == null) continue;
            if (queryChildrenType == BaseDataOption.QueryChildrenType.DIRECT_CHILDREN || queryChildrenType == BaseDataOption.QueryChildrenType.DIRECT_CHILDREN_WITH_SELF) {
                codeIdMap = currCodeDirectSubMap.get(code);
                if (codeIdMap == null) continue;
                refIds.addAll(codeIdMap.keySet());
                continue;
            }
            if (queryChildrenType != BaseDataOption.QueryChildrenType.ALL_CHILDREN && queryChildrenType != BaseDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF || (codeIdMap = currCodeAllSubMap.get(code)) == null) continue;
            refIds.addAll(codeIdMap.keySet());
        }
        return this.listByIdSet(param, refIds, false);
    }
}

