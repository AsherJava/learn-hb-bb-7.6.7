/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.jsontype.NamedType
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.MetaDesignDTO
 *  com.jiuqi.va.domain.meta.OperateType
 *  com.jiuqi.va.feign.client.MetaDataClient
 */
package com.jiuqi.va.biz.impl.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.jiuqi.va.biz.cache.ModelDefineCache;
import com.jiuqi.va.biz.impl.data.DataDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.impl.meta.MetaInfoImpl;
import com.jiuqi.va.biz.impl.model.ModelDefineImpl;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.intf.model.ModelManager;
import com.jiuqi.va.biz.intf.model.ModelType;
import com.jiuqi.va.biz.intf.model.PluginManager;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.MetaDesignDTO;
import com.jiuqi.va.domain.meta.OperateType;
import com.jiuqi.va.feign.client.MetaDataClient;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component(value="vaModelDefineCacheImpl")
public class ModelDefineCacheServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(ModelDefineCacheServiceImpl.class);
    @Autowired
    private MetaDataClient metaDataClient;
    @Autowired
    private ModelManager modelManager;
    @Autowired
    private PluginManager pluginManager;

    public void handleStatusCache(String uniqueCode, Long ver, Integer state, boolean flag) {
        String tenantName = ShiroUtil.getTenantName();
        if (!ModelDefineCache.tenantSyncStatusCache.containsKey(tenantName)) {
            ModelDefineCache.tenantSyncStatusCache.putIfAbsent(tenantName, new ConcurrentHashMap());
        }
        if (!ModelDefineCache.tenantSyncStatusCache.get(tenantName).containsKey(uniqueCode)) {
            ModelDefineCache.tenantSyncStatusCache.get(tenantName).putIfAbsent(uniqueCode, new ConcurrentHashMap());
        }
        if (flag) {
            ModelDefineCache.tenantSyncStatusCache.get(tenantName).get(uniqueCode).remove(ver);
        } else {
            ModelDefineCache.tenantSyncStatusCache.get(tenantName).get(uniqueCode).put(ver, state);
        }
    }

    public boolean isSyncCache(String uniqueCode, Long ver) {
        String tenantName = ShiroUtil.getTenantName();
        return ModelDefineCache.tenantSyncStatusCache.containsKey(tenantName) && ModelDefineCache.tenantSyncStatusCache.get(tenantName).containsKey(uniqueCode) && ModelDefineCache.tenantSyncStatusCache.get(tenantName).get(uniqueCode).containsKey(ver);
    }

    public ModelDefineImpl handleCache(String tenantName, String uniqueCode, Long version) {
        return this.handleCache(tenantName, uniqueCode, version, false);
    }

    public ModelDefineImpl handleCache(String tenantName, String uniqueCode, Long version, boolean readDesignFromDb) {
        ModelDefineImpl modelDefine;
        long realVersion = version == null ? -1L : version;
        this.handleStatusCache(uniqueCode, realVersion, 0, false);
        Map<Long, Long> tenantVerMap = this.getTenantVerMap(tenantName, uniqueCode);
        Map<Long, ModelDefine> tenantBuffer = this.getTenantBuffer(tenantName, uniqueCode);
        MetaDesignDTO metaDesignDTO = new MetaDesignDTO();
        metaDesignDTO.setDefineCode(uniqueCode);
        metaDesignDTO.setDefineVersion(version);
        metaDesignDTO.setOperateType(OperateType.EXECUTE);
        metaDesignDTO.setTenantName(tenantName);
        metaDesignDTO.addExtInfo("readDesignFromDb", (Object)readDesignFromDb);
        R r = this.metaDataClient.getMetaDesign(metaDesignDTO);
        if (r.getCode() != 0) {
            this.handleStatusCache(uniqueCode, realVersion, 0, true);
            throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.modeldefineservice.getmetadatafailed"));
        }
        String data = String.valueOf(r.get((Object)"data"));
        ObjectMapper mapper = new ObjectMapper();
        try {
            metaDesignDTO = (MetaDesignDTO)mapper.readValue(data, MetaDesignDTO.class);
        }
        catch (JsonProcessingException e) {
            this.handleStatusCache(uniqueCode, realVersion, 0, true);
            logger.error("\u53d1\u5e03\u5143\u6570\u636e\u65f6\uff0c\u751f\u6210ModelDefine\u7f13\u5b58\u5931\u8d25\uff1a", e);
            throw new RuntimeException(e);
        }
        String datas = metaDesignDTO.getDatas();
        if (!StringUtils.hasText(datas)) {
            this.handleStatusCache(uniqueCode, realVersion, 0, true);
            throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.modeldefineservice.getmetadataisnull"));
        }
        Map jsonObject = JSONUtil.parseMap((String)datas);
        String modelTypeName = jsonObject.get("modelType").toString();
        ModelType modelType = (ModelType)this.modelManager.get(modelTypeName);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
        this.modelManager.stream().forEach(o -> mapper.registerSubtypes(new NamedType[]{new NamedType(o.getModelDefineClass(), o.getName())}));
        this.pluginManager.stream().forEach(o -> mapper.registerSubtypes(new NamedType[]{new NamedType(o.getPluginDefineClass(), o.getName())}));
        try {
            modelDefine = (ModelDefineImpl)mapper.readValue(datas, modelType.getModelDefineClass());
        }
        catch (JsonProcessingException e) {
            this.handleStatusCache(uniqueCode, realVersion, 0, true);
            logger.error("\u53d1\u5e03\u5143\u6570\u636e\u65f6\uff0c\u751f\u6210ModelDefine\u7f13\u5b58\u5931\u8d25\uff1a", e);
            throw new RuntimeException(e);
        }
        long newVer = Convert.cast(r.get((Object)"versionNO"), Long.TYPE);
        MetaInfoImpl metaInfo = new MetaInfoImpl();
        metaInfo.setId(metaDesignDTO.getId());
        metaInfo.setVersion(newVer);
        metaInfo.setTitle((String)r.get((Object)"title"));
        metaInfo.setGroupName((String)r.get((Object)"groupName"));
        modelDefine.setMetaInfo(metaInfo);
        modelType.initModelDefine(modelDefine, uniqueCode);
        DataDefineImpl dataDefine = (DataDefineImpl)modelDefine.getPlugins().find("data");
        if (dataDefine != null) {
            Set<String> tables = dataDefine.getTableList().stream().map(DataTableDefineImpl::getName).collect(Collectors.toSet());
            this.updateTenantDefineTableMap(tenantName, uniqueCode, tables);
        }
        Long rowVersion = r.get((Object)"rowVersion") == null ? -1L : (Long)r.get((Object)"rowVersion");
        if (!tenantBuffer.isEmpty() && "workflow".equals(r.get((Object)"metaType"))) {
            tenantVerMap.put(newVer, rowVersion);
            tenantBuffer.put(newVer, modelDefine);
        } else if (tenantBuffer.isEmpty()) {
            this.updateTenantBuffer(tenantName, uniqueCode, newVer, modelDefine);
            ConcurrentHashMap<Long, Long> ver = new ConcurrentHashMap<Long, Long>();
            ver.put(newVer, rowVersion);
            this.updateTenantVerMap(tenantName, uniqueCode, ver);
        } else {
            long verMax = (Long)tenantBuffer.keySet().stream().max(Long::compareTo).get();
            if (newVer < verMax) {
                tenantBuffer.put(newVer, modelDefine);
                ConcurrentHashMap<Long, Long> ver = new ConcurrentHashMap<Long, Long>();
                ver.put(newVer, rowVersion);
                this.updateTenantVerMap(tenantName, uniqueCode, ver);
            } else {
                this.updateTenantBuffer(tenantName, uniqueCode, newVer, modelDefine);
                ConcurrentHashMap<Long, Long> ver = new ConcurrentHashMap<Long, Long>();
                ver.put(newVer, rowVersion);
                this.updateTenantVerMap(tenantName, uniqueCode, ver);
            }
        }
        this.handleExternalModelDefine(uniqueCode, tenantName);
        this.handleStatusCache(uniqueCode, realVersion, 0, true);
        return modelDefine;
    }

    private void handleExternalModelDefine(String billType, String tenantName) {
        Map<String, Long> verMap = this.getTenantExternalVer(tenantName);
        verMap.remove(billType);
        Map<String, ModelDefine> bufferMap = this.getTenantExternalBuffer(tenantName);
        bufferMap.remove(billType);
        Map<String, String> tenantExternalPcVerMap = this.getTenantExternalPcVer(tenantName);
        tenantExternalPcVerMap.remove(billType);
    }

    public Map<String, Set<String>> getTenantDefineTableMap(String tenantName, String uniqueCode) {
        if (!ModelDefineCache.tenantDefineTableMap.containsKey(tenantName)) {
            ModelDefineCache.tenantDefineTableMap.putIfAbsent(tenantName, new ConcurrentHashMap());
        }
        if (!ModelDefineCache.tenantDefineTableMap.get(tenantName).containsKey(uniqueCode)) {
            ModelDefineCache.tenantDefineTableMap.get(tenantName).putIfAbsent(uniqueCode, Collections.emptySet());
        }
        return ModelDefineCache.tenantDefineTableMap.get(tenantName);
    }

    private void updateTenantDefineTableMap(String tenantName, String uniqueCode, Set<String> tables) {
        if (!ModelDefineCache.tenantDefineTableMap.containsKey(tenantName)) {
            ModelDefineCache.tenantDefineTableMap.putIfAbsent(tenantName, new ConcurrentHashMap());
        }
        if (!ModelDefineCache.tenantDefineTableMap.get(tenantName).containsKey(uniqueCode)) {
            ModelDefineCache.tenantDefineTableMap.get(tenantName).putIfAbsent(uniqueCode, tables);
        }
        ModelDefineCache.tenantDefineTableMap.get(tenantName).put(uniqueCode, tables);
    }

    public Map<Long, ModelDefine> getTenantBuffer(String tenantName, String defineName) {
        if (ModelDefineCache.tenantBuffer.containsKey(tenantName) && ModelDefineCache.tenantBuffer.get(tenantName).containsKey(defineName)) {
            return ModelDefineCache.tenantBuffer.get(tenantName).get(defineName);
        }
        ModelDefineCache.tenantBuffer.putIfAbsent(tenantName, new ConcurrentHashMap());
        ModelDefineCache.tenantBuffer.get(tenantName).putIfAbsent(defineName, new ConcurrentHashMap());
        return ModelDefineCache.tenantBuffer.get(tenantName).get(defineName);
    }

    public void updateTenantBuffer(String tenantName, String defineName, long newVer, ModelDefineImpl modelDefine) {
        ConcurrentHashMap<Long, ModelDefineImpl> newBuffer = new ConcurrentHashMap<Long, ModelDefineImpl>();
        newBuffer.put(newVer, modelDefine);
        if (!ModelDefineCache.tenantBuffer.containsKey(tenantName)) {
            ModelDefineCache.tenantBuffer.putIfAbsent(tenantName, new ConcurrentHashMap());
        }
        ModelDefineCache.tenantBuffer.get(tenantName).put(defineName, newBuffer);
    }

    public Map<Long, Long> getTenantVerMap(String tenantName, String defineName) {
        if (ModelDefineCache.tenantVerMap.containsKey(tenantName) && ModelDefineCache.tenantVerMap.get(tenantName).containsKey(defineName)) {
            return ModelDefineCache.tenantVerMap.get(tenantName).get(defineName);
        }
        ModelDefineCache.tenantVerMap.putIfAbsent(tenantName, new ConcurrentHashMap());
        ModelDefineCache.tenantVerMap.get(tenantName).putIfAbsent(defineName, new ConcurrentHashMap());
        return ModelDefineCache.tenantVerMap.get(tenantName).get(defineName);
    }

    public void updateTenantVerMap(String tenantName, String defineName, Map<Long, Long> verMap) {
        if (!ModelDefineCache.tenantVerMap.containsKey(tenantName)) {
            ModelDefineCache.tenantVerMap.putIfAbsent(tenantName, new ConcurrentHashMap());
        }
        ModelDefineCache.tenantVerMap.get(tenantName).put(defineName, verMap);
    }

    public Map<String, Long> getTenantExternalVer(String tenantName) {
        if (ModelDefineCache.tenantExternalVer.containsKey(tenantName)) {
            return ModelDefineCache.tenantExternalVer.get(tenantName);
        }
        ModelDefineCache.tenantExternalVer.putIfAbsent(tenantName, new ConcurrentHashMap());
        return ModelDefineCache.tenantExternalVer.get(tenantName);
    }

    public Map<String, ModelDefine> getTenantExternalBuffer(String tenantName) {
        if (ModelDefineCache.tenantExternalBuffer.containsKey(tenantName)) {
            return ModelDefineCache.tenantExternalBuffer.get(tenantName);
        }
        ModelDefineCache.tenantExternalBuffer.putIfAbsent(tenantName, new ConcurrentHashMap());
        return ModelDefineCache.tenantExternalBuffer.get(tenantName);
    }

    public Map<String, String> getTenantExternalPcVer(String tenantName) {
        if (ModelDefineCache.tenantExternalPcVer.containsKey(tenantName)) {
            return ModelDefineCache.tenantExternalPcVer.get(tenantName);
        }
        ModelDefineCache.tenantExternalPcVer.putIfAbsent(tenantName, new ConcurrentHashMap());
        return ModelDefineCache.tenantExternalPcVer.get(tenantName);
    }
}

