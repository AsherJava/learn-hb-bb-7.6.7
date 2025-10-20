/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.TenantInfoClient
 *  com.jiuqi.va.mapper.common.TenantUtil
 */
package com.jiuqi.va.biz.cache;

import com.jiuqi.va.biz.utils.ConcurrentHashMapUtils;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.TenantInfoClient;
import com.jiuqi.va.mapper.common.TenantUtil;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component(value="billBaseDataDefineCache")
public class BaseDataDefineCache {
    private static boolean baseDataDefineClientImpl = false;
    private static final Logger log = LoggerFactory.getLogger(BaseDataDefineCache.class);
    private static final Map<String, Map<String, BaseDataDefineDO>> tenantBaseDataDefine = new ConcurrentHashMap<String, Map<String, BaseDataDefineDO>>();
    private static BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private TenantInfoClient tenantInfoClient;

    @Autowired
    public void setBaseDataDefineClient(BaseDataDefineClient baseDataDefineClient) {
        BaseDataDefineCache.baseDataDefineClient = baseDataDefineClient;
        if (baseDataDefineClient.getClass().getName().contains("Impl")) {
            baseDataDefineClientImpl = true;
        }
    }

    public static void updateBaseDataDefineCache(String tenantName, DataModelDO dataModel) {
        String dataModelName;
        Map baseDataDefines = ConcurrentHashMapUtils.computeIfAbsent(tenantBaseDataDefine, tenantName, key -> new ConcurrentHashMap());
        if (!baseDataDefines.containsKey(dataModelName = dataModel.getName())) {
            return;
        }
        if (dataModel.getExtInfo() != null) {
            BaseDataDefineDTO baseDataDefine = (BaseDataDefineDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString(dataModel.getExtInfo().get("baseDataDefine")), BaseDataDefineDTO.class);
            if (baseDataDefine != null) {
                baseDataDefines.put(dataModelName, baseDataDefine);
            } else {
                BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
                baseDataDefineDTO.setName(dataModelName);
                BaseDataDefineDO baseDataDefineDO = baseDataDefineClient.get(baseDataDefineDTO);
                if (baseDataDefineDO == null) {
                    baseDataDefines.remove(dataModelName);
                    return;
                }
                baseDataDefines.put(dataModelName, baseDataDefineDO);
            }
        } else {
            BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
            baseDataDefineDTO.setName(dataModelName);
            BaseDataDefineDO baseDataDefineDO = baseDataDefineClient.get(baseDataDefineDTO);
            if (baseDataDefineDO == null) {
                baseDataDefines.remove(dataModelName);
                return;
            }
            baseDataDefines.put(dataModelName, baseDataDefineDO);
        }
    }

    public static BaseDataDefineDO getBaseDataDefine(String tableName) {
        if (baseDataDefineClientImpl) {
            BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
            baseDataDefineDTO.setName(tableName);
            return baseDataDefineClient.get(baseDataDefineDTO);
        }
        Map baseDataDefines = ConcurrentHashMapUtils.computeIfAbsent(tenantBaseDataDefine, ShiroUtil.getTenantName(), key -> new ConcurrentHashMap());
        if (!baseDataDefines.containsKey(tableName)) {
            BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
            baseDataDefineDTO.setName(tableName);
            BaseDataDefineDO baseDataDefineDO = baseDataDefineClient.get(baseDataDefineDTO);
            if (baseDataDefineDO == null) {
                log.error("\u67e5\u8be2\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u5931\u8d25\uff1a" + tableName);
                return null;
            }
            baseDataDefines.put(tableName, baseDataDefineDO);
            return baseDataDefineDO;
        }
        return (BaseDataDefineDO)baseDataDefines.get(tableName);
    }

    @Scheduled(initialDelay=0L, fixedRate=1L, timeUnit=TimeUnit.HOURS)
    public void syncBaseDataDefineSchedule() {
        List<String> tenantList = TenantUtil.isMultiTenant() ? this.tenantInfoClient.nameList() : Arrays.asList("__default_tenant__");
        for (String tenant : tenantList) {
            Map<String, BaseDataDefineDO> baseDataDefines = tenantBaseDataDefine.get(tenant);
            if (CollectionUtils.isEmpty(baseDataDefines)) continue;
            for (String key : baseDataDefines.keySet()) {
                BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
                baseDataDefineDTO.setName(key);
                BaseDataDefineDO newDefine = baseDataDefineClient.get(baseDataDefineDTO);
                if (newDefine == null) {
                    baseDataDefines.remove(key);
                    continue;
                }
                BaseDataDefineDO oldDefine = baseDataDefines.get(key);
                if (newDefine.getModifytime().compareTo(oldDefine.getModifytime()) == 0) continue;
                log.info("syncBaseDataDefineSchedule\u540c\u6b65\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u7f13\u5b58:" + key);
                baseDataDefines.put(key, newDefine);
            }
        }
    }
}

