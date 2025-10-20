/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.jsontype.NamedType
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.MetaDesignDTO
 *  com.jiuqi.va.domain.meta.OperateType
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.data.redis.core.HashOperations
 *  org.springframework.data.redis.core.RedisTemplate
 */
package com.jiuqi.va.biz.impl.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.jiuqi.va.biz.cache.ModelDefineCache;
import com.jiuqi.va.biz.config.VaBizBindingConfig;
import com.jiuqi.va.biz.domain.ModelDefineSyncCacheDTO;
import com.jiuqi.va.biz.impl.meta.MetaInfoImpl;
import com.jiuqi.va.biz.impl.model.ModelDefineCacheServiceImpl;
import com.jiuqi.va.biz.impl.model.ModelDefineImpl;
import com.jiuqi.va.biz.intf.model.BillFrontDefineService;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelContext;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.intf.model.ModelManager;
import com.jiuqi.va.biz.intf.model.ModelType;
import com.jiuqi.va.biz.intf.model.PluginManager;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.impl.RulerDefineImpl;
import com.jiuqi.va.biz.ruler.intf.Formula;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.biz.utils.Env;
import com.jiuqi.va.biz.view.impl.ViewDefineImpl;
import com.jiuqi.va.biz.view.intf.ExternalViewDefine;
import com.jiuqi.va.biz.view.intf.ExternalViewManager;
import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.MetaDesignDTO;
import com.jiuqi.va.domain.meta.OperateType;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ModelDefineServiceImpl
implements ModelDefineService {
    private static final Logger logger = LoggerFactory.getLogger(ModelDefineServiceImpl.class);
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private MetaDataClient metaDataClient;
    @Autowired
    private ModelManager modelManager;
    @Autowired
    private PluginManager pluginManager;
    @Autowired
    private ExternalViewManager externalViewManager;
    @Autowired
    ModelDefineCacheServiceImpl modelDefineCacheService;

    public ModelManager getModelManager() {
        return this.modelManager;
    }

    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    @Override
    public ModelDefine createDefine(String name) {
        ModelDefine modelDefine;
        ModelType modelType = (ModelType)this.modelManager.get(name);
        try {
            modelDefine = modelType.getModelDefineClass().newInstance();
        }
        catch (IllegalAccessException | InstantiationException e) {
            throw new ModelException(e);
        }
        modelType.initModelDefine(modelDefine, name);
        return modelDefine;
    }

    @Override
    public ModelDefine getDefine(String defineName) {
        try {
            ModelDefine modelDefine = this.getDefineWithBuffer(defineName, Env.getTenantName());
            return modelDefine;
        }
        catch (IOException e) {
            throw new ModelException(e);
        }
        finally {
            this.modelDefineCacheService.handleStatusCache(defineName, -1L, 0, true);
        }
    }

    private ModelDefine getDefineWithBuffer(String defineName, String tenantName) throws IOException {
        return this.getDefineWithBuffer(defineName, tenantName, null);
    }

    private ModelDefine getDefineWithBuffer(String defineName, String tenantName, Long defineVersion) throws IOException {
        boolean syncFlag = true;
        while (syncFlag) {
            syncFlag = defineVersion == null ? this.modelDefineCacheService.isSyncCache(defineName, -1L) : this.modelDefineCacheService.isSyncCache(defineName, defineVersion);
            if (!syncFlag) continue;
            try {
                Thread.sleep(100L);
            }
            catch (InterruptedException e) {
                this.modelDefineCacheService.handleStatusCache(defineName, defineVersion == null ? -1L : defineVersion, 0, true);
                logger.error("Interrupted!", e);
                Thread.currentThread().interrupt();
            }
        }
        Map<Long, Long> tenantVerMap = this.modelDefineCacheService.getTenantVerMap(tenantName, defineName);
        Map<Long, ModelDefine> tenantBuffer = this.modelDefineCacheService.getTenantBuffer(tenantName, defineName);
        if (!tenantVerMap.isEmpty() && (defineVersion == null || tenantVerMap.containsKey(defineVersion))) {
            Long cacheVersion = null;
            Long cacheRowVersion = null;
            if (defineVersion == null) {
                for (Map.Entry<Long, Long> entry : tenantVerMap.entrySet()) {
                    Long version = entry.getKey();
                    if (cacheVersion != null && cacheVersion >= version) continue;
                    cacheVersion = version;
                    cacheRowVersion = entry.getValue();
                }
                String rediskey = tenantName + ":" + defineName;
                HashOperations HashOperation = this.redisTemplate.opsForHash();
                String redisVersion = (String)HashOperation.get((Object)rediskey, (Object)"version");
                String redisRowVersion = (String)HashOperation.get((Object)rediskey, (Object)"rowVersion");
                if (redisVersion != null && redisRowVersion != null && cacheVersion != null && redisVersion.equals(cacheVersion.toString()) && redisRowVersion.equals(cacheRowVersion.toString())) {
                    return tenantBuffer.get(cacheVersion);
                }
            } else {
                cacheRowVersion = tenantVerMap.get(defineVersion);
                String rediskey = tenantName + ":" + defineName + ":" + defineVersion;
                String redisRowVersion = (String)this.redisTemplate.opsForValue().get((Object)rediskey);
                if (redisRowVersion != null && redisRowVersion.equals(cacheRowVersion.toString()) || !"W".equals(defineName.split("_")[1]) && tenantBuffer.containsKey(defineVersion)) {
                    return tenantBuffer.get(defineVersion);
                }
            }
        }
        return this.modelDefineCacheService.handleCache(tenantName, defineName, defineVersion);
    }

    private void updateDefine(String defineName, String tenantName, Long defineVersion) {
        try {
            this.modelDefineCacheService.handleCache(tenantName, defineName, defineVersion);
        }
        catch (Throwable e) {
            this.modelDefineCacheService.handleStatusCache(defineName, defineVersion == null ? -1L : defineVersion, 0, true);
        }
    }

    @Override
    public Model createModel(ModelContext context, ModelDefine define) {
        Model model;
        ModelType modelType = (ModelType)this.modelManager.get(define.getModelType());
        try {
            model = modelType.getModelClass().newInstance();
        }
        catch (IllegalAccessException | InstantiationException e) {
            throw new ModelException(e);
        }
        modelType.initModel(model, context, define);
        return model;
    }

    @Override
    public Model createModel(ModelContext context, String billType) {
        ModelDefine define = this.getDefine(billType);
        return this.createModel(context, define);
    }

    @Override
    public ModelDefine getDefine(TenantDO tenantDO) {
        String defineName = tenantDO.getExtInfo().get("DEFINECODE") == null ? "" : tenantDO.getExtInfo().get("DEFINECODE").toString();
        try {
            ModelDefine modelDefine = this.getDefineWithBuffer(defineName, tenantDO.getTenantName());
            return modelDefine;
        }
        catch (Exception e) {
            throw new ModelException(e);
        }
        finally {
            this.modelDefineCacheService.handleStatusCache(defineName, -1L, 0, true);
        }
    }

    @Override
    public ModelDefine getDefine(String defineName, Long defineversion) {
        try {
            ModelDefine modelDefine = this.getDefineWithBuffer(defineName, Env.getTenantName(), defineversion);
            this.modelDefineCacheService.handleStatusCache(defineName, defineversion == null ? -1L : defineversion, 0, true);
            return modelDefine;
        }
        catch (IOException e) {
            try {
                throw new ModelException(e);
            }
            catch (Throwable throwable) {
                this.modelDefineCacheService.handleStatusCache(defineName, defineversion == null ? -1L : defineversion, 0, true);
                throw throwable;
            }
        }
    }

    @Override
    public ModelDefine getDefine(String defineName, String externalViewName) {
        try {
            return this.getExternalDefine(defineName, externalViewName);
        }
        catch (IOException e) {
            throw new ModelException(e);
        }
    }

    private ModelDefine getExternalDefine(String billType, String externalViewName) throws JsonProcessingException {
        ExternalViewDefine externalViewDefine = (ExternalViewDefine)this.externalViewManager.find(externalViewName);
        Long ver = externalViewDefine.getVer(billType);
        String tenantName = ShiroUtil.getTenantName();
        Map<String, Long> verMap = this.modelDefineCacheService.getTenantExternalVer(tenantName);
        Map<String, ModelDefine> bufferMap = this.modelDefineCacheService.getTenantExternalBuffer(tenantName);
        Map<String, String> tenantExternalPcVerMap = this.modelDefineCacheService.getTenantExternalPcVer(tenantName);
        String cacheKey = billType + ":" + externalViewName;
        Long oldVer = verMap.get(cacheKey);
        String rediskey = tenantName + ":" + billType;
        HashOperations HashOperation = this.redisTemplate.opsForHash();
        String redisRowVersion = (String)HashOperation.get((Object)rediskey, (Object)"rowVersion");
        if (redisRowVersion == null) {
            redisRowVersion = "0";
        }
        if (oldVer != null && oldVer.longValue() == ver.longValue()) {
            String pcVer = tenantExternalPcVerMap.get(cacheKey);
            if (pcVer == null) {
                return this.getExternalModelDefine(billType, externalViewName, externalViewDefine, ver, tenantName, verMap, bufferMap, tenantExternalPcVerMap, redisRowVersion);
            }
            if (redisRowVersion.equals(pcVer)) {
                return bufferMap.get(cacheKey);
            }
            return this.getExternalModelDefine(billType, externalViewName, externalViewDefine, ver, tenantName, verMap, bufferMap, tenantExternalPcVerMap, redisRowVersion);
        }
        return this.getExternalModelDefine(billType, externalViewName, externalViewDefine, ver, tenantName, verMap, bufferMap, tenantExternalPcVerMap, redisRowVersion);
    }

    private ModelDefineImpl getExternalModelDefine(String billType, String externalViewName, ExternalViewDefine externalViewDefine, Long ver, String tenantName, Map<String, Long> verMap, Map<String, ModelDefine> bufferMap, Map<String, String> tenantExternalPcVerMap, String redisRowVersion) throws JsonProcessingException {
        MetaDesignDTO metaDesignDTO = new MetaDesignDTO();
        metaDesignDTO.setDefineCode(billType);
        metaDesignDTO.setDefineVersion(null);
        metaDesignDTO.setOperateType(OperateType.EXECUTE);
        metaDesignDTO.setTenantName(tenantName);
        R r = this.metaDataClient.getMetaDesign(metaDesignDTO);
        if (r.getCode() != 0) {
            throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.modeldefineservice.getmetadatafailed"));
        }
        ObjectMapper mapper = new ObjectMapper();
        String data = String.valueOf(r.get((Object)"data"));
        metaDesignDTO = (MetaDesignDTO)mapper.readValue(data, MetaDesignDTO.class);
        String datas = metaDesignDTO.getDatas();
        if (!StringUtils.hasText(datas)) {
            throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.modeldefineservice.getmetadataisnull"));
        }
        Map jsonObject = JSONUtil.parseMap((String)datas);
        String modelTypeName = jsonObject.get("modelType").toString();
        ModelType modelType = (ModelType)this.modelManager.get(modelTypeName);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
        this.modelManager.stream().forEach(o -> mapper.registerSubtypes(new NamedType[]{new NamedType(o.getModelDefineClass(), o.getName())}));
        this.pluginManager.stream().forEach(o -> mapper.registerSubtypes(new NamedType[]{new NamedType(o.getPluginDefineClass(), o.getName())}));
        ModelDefineImpl modelDefine = (ModelDefineImpl)mapper.readValue(datas, modelType.getModelDefineClass());
        long newVer = Convert.cast(r.get((Object)"versionNO"), Long.TYPE);
        MetaInfoImpl metaInfo = new MetaInfoImpl();
        metaInfo.setId(metaDesignDTO.getId());
        metaInfo.setVersion(newVer);
        metaInfo.setTitle((String)r.get((Object)"title"));
        modelDefine.setMetaInfo(metaInfo);
        ViewDefineImpl viewDefine = (ViewDefineImpl)modelDefine.getPlugins().find("view");
        viewDefine.setSchemes(externalViewDefine.getSchemes(billType));
        viewDefine.setTemplate(externalViewDefine.getTemplate(billType));
        List<Formula> formulas = externalViewDefine.getFormulas(billType);
        if (formulas != null && formulas.size() > 0) {
            RulerDefineImpl rulerDefine = (RulerDefineImpl)modelDefine.getPlugins().find("ruler");
            rulerDefine.addAllFormula(formulas.stream().map(o -> (FormulaImpl)o).collect(Collectors.toList()));
        }
        modelType.initModelDefine(modelDefine, billType, externalViewName);
        String cacheKey = billType + ":" + externalViewName;
        verMap.put(cacheKey, ver);
        bufferMap.put(cacheKey, modelDefine);
        tenantExternalPcVerMap.put(cacheKey, redisRowVersion);
        return modelDefine;
    }

    @Override
    public final void clearCache(String tenantName, String name) {
        Map<String, Set<String>> defineTable = ModelDefineCache.tenantDefineTableMap.get(tenantName);
        if (defineTable == null) {
            return;
        }
        HashSet<String> defines = new HashSet<String>();
        for (Map.Entry<String, Set<String>> entry : defineTable.entrySet()) {
            if (!entry.getValue().contains(name)) continue;
            defines.add(entry.getKey());
        }
        Map<String, Map<Long, ModelDefine>> buffer = ModelDefineCache.tenantBuffer.get(tenantName);
        Map<String, Map<Long, Long>> ver = ModelDefineCache.tenantVerMap.get(tenantName);
        Map<String, String> externalPcVer = ModelDefineCache.tenantExternalPcVer.get(tenantName);
        if (buffer == null || buffer.size() == 0 || ver == null || ver.size() == 0 || externalPcVer == null || externalPcVer.size() == 0) {
            return;
        }
        for (String define : defines) {
            buffer.remove(define);
            ver.remove(define);
            externalPcVer.remove(define);
        }
    }

    @Override
    public final void clearBillCache(String tenantName, String defineName) {
        this.clearLocalBillCache(tenantName, defineName);
        BillFrontDefineService frontDefineService = (BillFrontDefineService)ApplicationContextRegister.getBean(BillFrontDefineService.class);
        if (frontDefineService != null) {
            frontDefineService.clearCache(tenantName, defineName);
        }
        if (VaBizBindingConfig.isRedisEnable()) {
            ModelDefineSyncCacheDTO cacheDTO = new ModelDefineSyncCacheDTO();
            cacheDTO.setCurrNodeId(VaBizBindingConfig.getCurrNodeId());
            cacheDTO.setDefineName(defineName);
            EnvConfig.sendRedisMsg((String)VaBizBindingConfig.getBizBindingSyncCachePub(), (String)JSONUtil.toJSONString((Object)((Object)cacheDTO)));
        }
    }

    @Override
    public void clearLocalBillCache(String tenantName, String defineName) {
        Map<String, Map<Long, ModelDefine>> buffer = ModelDefineCache.tenantBuffer.get(tenantName);
        Map<String, Map<Long, Long>> ver = ModelDefineCache.tenantVerMap.get(tenantName);
        Map<String, String> externalPcVer = ModelDefineCache.tenantExternalPcVer.get(tenantName);
        if (buffer != null && buffer.size() > 0) {
            buffer.remove(defineName);
        }
        if (ver != null && ver.size() > 0) {
            ver.remove(defineName);
        }
        if (externalPcVer != null && externalPcVer.size() > 0) {
            externalPcVer.remove(defineName);
        }
    }
}

