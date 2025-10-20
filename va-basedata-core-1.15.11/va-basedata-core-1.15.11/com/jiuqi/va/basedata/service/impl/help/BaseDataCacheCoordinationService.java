/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.impl.BaseDataClientImpl
 *  com.jiuqi.va.feign.remote.BaseDataFeign
 *  com.jiuqi.va.feign.util.FeignUtil
 */
package com.jiuqi.va.basedata.service.impl.help;

import com.jiuqi.va.basedata.config.VaBasedataCoreConfig;
import com.jiuqi.va.basedata.domain.BaseDataRegisterCacheDTO;
import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.impl.BaseDataClientImpl;
import com.jiuqi.va.feign.remote.BaseDataFeign;
import com.jiuqi.va.feign.util.FeignUtil;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component(value="vaBaseDataCacheCoordinationService")
public class BaseDataCacheCoordinationService {
    @Value(value="${nvwa.basedata.cache.addr:}")
    private String currentNodeAddr;
    private static String definesIncludeStr;
    private static Set<String> definesInclude;
    private static Set<String> definesExclude;
    private static BaseDataRegisterCacheDTO register;
    private static Map<String, Long> otherNodeRegisterTime;
    private static Set<String> otherNodeIsAll;
    private static Map<String, Set<String>> otherNodeDefinesInclude;
    private static Map<String, Set<String>> otherNodeDefinesExclude;
    private static Map<String, BaseDataClient> clientCache;

    @Value(value="${nvwa.basedata.cache.defines.include:*}")
    public void setDefinesInclude(String defines) {
        definesIncludeStr = "*".equals(defines) && !StringUtils.hasText(defines) ? "*" : defines;
        if (!"*".equals(definesIncludeStr)) {
            for (String define : definesIncludeStr.split("\\,")) {
                definesInclude.add(define.toUpperCase());
            }
        }
    }

    @Value(value="${nvwa.basedata.cache.defines.exclude:}")
    public void setDefinesExclude(String defines) {
        if (!StringUtils.hasText(defines)) {
            return;
        }
        for (String define : defines.split("\\,")) {
            definesExclude.add(define.toUpperCase());
        }
    }

    @Scheduled(fixedRate=15L, timeUnit=TimeUnit.SECONDS)
    public void submitCacheNodeInfo() {
        if (!EnvConfig.getRedisEnable() || !StringUtils.hasText(this.currentNodeAddr)) {
            return;
        }
        if (register == null) {
            register = new BaseDataRegisterCacheDTO();
            register.setCurrNodeId(EnvConfig.getCurrNodeId());
            register.setCurrentNodeAddr(this.currentNodeAddr);
            register.setDefinesIncludeStr(definesIncludeStr);
            register.setDefinesInclude(definesInclude);
            register.setDefinesExclude(definesExclude);
        }
        this.validationCacheNodeInfo();
        EnvConfig.sendRedisMsg((String)VaBasedataCoreConfig.getBaseDataRegisterCachePub(), (String)JSONUtil.toJSONString((Object)register));
    }

    private void validationCacheNodeInfo() {
        long currTime = System.currentTimeMillis();
        String nodeAddr2 = null;
        Long lastTime = null;
        for (String nodeAddr2 : otherNodeRegisterTime.keySet()) {
            lastTime = otherNodeRegisterTime.get(nodeAddr2);
            if (currTime - lastTime <= 30000L) continue;
            otherNodeRegisterTime.remove(nodeAddr2);
            otherNodeIsAll.remove(nodeAddr2);
            for (Map.Entry<String, Set<String>> entry : otherNodeDefinesInclude.entrySet()) {
                entry.getValue().remove(nodeAddr2);
            }
            otherNodeDefinesInclude.entrySet().removeIf(obj -> ((Set)obj.getValue()).isEmpty());
            for (Map.Entry<String, Set<String>> entry : otherNodeDefinesExclude.entrySet()) {
                entry.getValue().remove(nodeAddr2);
            }
            otherNodeDefinesExclude.entrySet().removeIf(obj -> ((Set)obj.getValue()).isEmpty());
        }
    }

    public void registerCacheNodeInfo(BaseDataRegisterCacheDTO bdrc) {
        String nodeAddr = bdrc.getCurrentNodeAddr();
        otherNodeRegisterTime.put(nodeAddr, System.currentTimeMillis());
        String includeStr = bdrc.getDefinesIncludeStr();
        if ("*".equals(includeStr)) {
            otherNodeIsAll.add(nodeAddr);
        } else {
            otherNodeIsAll.remove(nodeAddr);
        }
        Set<String> defineInclude = bdrc.getDefinesInclude();
        for (Map.Entry<String, Set<String>> entry : otherNodeDefinesInclude.entrySet()) {
            if (!entry.getValue().contains(nodeAddr) || defineInclude.contains(entry.getKey())) continue;
            entry.getValue().remove(nodeAddr);
        }
        for (String define : defineInclude) {
            otherNodeDefinesInclude.computeIfAbsent(define, key -> new HashSet());
            otherNodeDefinesInclude.get(define).add(nodeAddr);
        }
        otherNodeDefinesInclude.entrySet().removeIf(obj -> ((Set)obj.getValue()).isEmpty());
        Set<String> defineExclude = bdrc.getDefinesExclude();
        for (Map.Entry<String, Set<String>> entry : otherNodeDefinesExclude.entrySet()) {
            if (!entry.getValue().contains(nodeAddr) || defineExclude.contains(entry.getKey())) continue;
            entry.getValue().remove(nodeAddr);
        }
        for (String define : defineExclude) {
            otherNodeDefinesExclude.computeIfAbsent(define, key -> new HashSet());
            otherNodeDefinesExclude.get(define).add(nodeAddr);
        }
        otherNodeDefinesExclude.entrySet().removeIf(obj -> ((Set)obj.getValue()).isEmpty());
    }

    public String getAddrByOtherNode(String defineName) {
        HashSet<String> defineSet = new HashSet<String>();
        defineSet.addAll(otherNodeIsAll);
        if (otherNodeDefinesInclude.containsKey(defineName)) {
            defineSet.addAll((Collection)otherNodeDefinesInclude.get(defineName));
        }
        if (otherNodeDefinesExclude.containsKey(defineName)) {
            defineSet.removeAll((Collection)otherNodeDefinesExclude.get(defineName));
        }
        if (defineSet.isEmpty()) {
            return null;
        }
        if (defineSet.size() == 1) {
            return (String)defineSet.iterator().next();
        }
        ArrayList<String> addrList = new ArrayList<String>();
        addrList.addAll(defineSet);
        SecureRandom sr = new SecureRandom();
        return (String)addrList.get(sr.nextInt(addrList.size()));
    }

    public boolean isCanLoadByCurrentNode(String defineName) {
        if (!EnvConfig.getRedisEnable()) {
            return true;
        }
        if (!StringUtils.hasText(this.currentNodeAddr)) {
            return true;
        }
        if (definesExclude.contains(defineName.toUpperCase())) {
            return false;
        }
        if ("*".equals(definesIncludeStr)) {
            return true;
        }
        return definesInclude.contains(defineName.toUpperCase());
    }

    public BaseDataClient getClient(String defientName) {
        String url = this.getAddrByOtherNode(defientName);
        if (url == null) {
            return null;
        }
        if (clientCache.containsKey(url)) {
            return clientCache.get(url);
        }
        BaseDataFeign feign = (BaseDataFeign)FeignUtil.getDynamicClient(BaseDataFeign.class, (String)url);
        BaseDataClientImpl client = new BaseDataClientImpl(feign);
        clientCache.put(url, (BaseDataClient)client);
        return client;
    }

    static {
        definesInclude = new HashSet<String>();
        definesExclude = new HashSet<String>();
        otherNodeRegisterTime = new HashMap<String, Long>();
        otherNodeIsAll = new HashSet<String>();
        otherNodeDefinesInclude = new HashMap<String, Set<String>>();
        otherNodeDefinesExclude = new HashMap<String, Set<String>>();
        clientCache = new ConcurrentHashMap<String, BaseDataClient>();
    }
}

