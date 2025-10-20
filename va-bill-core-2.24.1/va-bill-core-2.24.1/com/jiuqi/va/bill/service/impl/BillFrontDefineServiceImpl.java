/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.afterload.AfterLoadEventImpl
 *  com.jiuqi.va.biz.afterload.FrontAfterLoadEventDefine
 *  com.jiuqi.va.biz.front.FrontDataDefine
 *  com.jiuqi.va.biz.front.FrontModelDefine
 *  com.jiuqi.va.biz.front.FrontPluginDefine
 *  com.jiuqi.va.biz.impl.model.I18nPluginManager
 *  com.jiuqi.va.biz.intf.model.BillFrontDefineService
 *  com.jiuqi.va.biz.intf.model.I18nPlugin
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.PluginDefine
 *  com.jiuqi.va.biz.utils.Env
 *  com.jiuqi.va.biz.view.impl.ViewDefineImpl
 *  com.jiuqi.va.biz.view.intf.ExternalViewDefine
 *  com.jiuqi.va.biz.view.intf.ExternalViewManager
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.i18n.domain.VaI18nResourceDTO
 *  com.jiuqi.va.i18n.feign.VaI18nClient
 *  com.jiuqi.va.i18n.utils.VaI18nParamUtils
 *  org.springframework.data.redis.core.HashOperations
 *  org.springframework.data.redis.core.RedisTemplate
 */
package com.jiuqi.va.bill.service.impl;

import com.jiuqi.va.bill.config.VaBillCoreConfig;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.afterload.AfterLoadEventImpl;
import com.jiuqi.va.biz.afterload.FrontAfterLoadEventDefine;
import com.jiuqi.va.biz.front.FrontDataDefine;
import com.jiuqi.va.biz.front.FrontModelDefine;
import com.jiuqi.va.biz.front.FrontPluginDefine;
import com.jiuqi.va.biz.impl.model.I18nPluginManager;
import com.jiuqi.va.biz.intf.model.BillFrontDefineService;
import com.jiuqi.va.biz.intf.model.I18nPlugin;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.utils.Env;
import com.jiuqi.va.biz.view.impl.ViewDefineImpl;
import com.jiuqi.va.biz.view.intf.ExternalViewDefine;
import com.jiuqi.va.biz.view.intf.ExternalViewManager;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.i18n.domain.VaI18nResourceDTO;
import com.jiuqi.va.i18n.feign.VaI18nClient;
import com.jiuqi.va.i18n.utils.VaI18nParamUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class BillFrontDefineServiceImpl
implements BillFrontDefineService {
    private static final Logger logger = LoggerFactory.getLogger(BillFrontDefineServiceImpl.class);
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private static final String EMPTY_VERSION = "0";
    private Map<String, Map<String, Map<String, FrontModelDefine>>> tenantBuffer = new ConcurrentHashMap<String, Map<String, Map<String, FrontModelDefine>>>();
    private Map<String, Map<String, Map<String, Map<Long, FrontModelDefine>>>> multiTenantBuffer = new ConcurrentHashMap<String, Map<String, Map<String, Map<Long, FrontModelDefine>>>>();
    private Map<String, Map<String, Long>> tenantVerMap = new ConcurrentHashMap<String, Map<String, Long>>();
    private Map<String, Map<String, Map<Long, Long>>> multiTenantVerMap = new ConcurrentHashMap<String, Map<String, Map<Long, Long>>>();
    private Map<String, Map<String, Map<String, FrontModelDefine>>> tenantExternalBuffer = new ConcurrentHashMap<String, Map<String, Map<String, FrontModelDefine>>>();
    private Map<String, Map<String, String>> tenanExternaltVerMap = new ConcurrentHashMap<String, Map<String, String>>();
    @Autowired(required=false)
    private ExternalViewManager externalViewManager;
    @Autowired
    private I18nPluginManager i18nPluginManager;
    @Autowired
    private VaI18nClient vaDataResourceClient;

    public FrontModelDefine getDefine(Model billModel, String schemeCode, String viewName) {
        BillModelImpl model = (BillModelImpl)billModel;
        if (!StringUtils.hasText(schemeCode)) {
            Optional<Map> optional;
            ViewDefineImpl view = model.getDefine().getView();
            List schemes = view.getSchemes();
            schemeCode = schemes == null ? "defaultScheme" : ((optional = schemes.stream().filter(o -> (Boolean)o.get("default")).findAny()).isPresent() ? String.valueOf(optional.get().get("code")) : "defaultScheme");
        }
        String tenantName = Env.getTenantName();
        String defineName = model.getDefine().getName();
        Map<String, Map<String, Map<Long, FrontModelDefine>>> multiBuffer = this.multiTenantBuffer.get(tenantName);
        Map<String, Map<Long, Long>> multiVerMap = this.multiTenantVerMap.get(tenantName);
        if (multiBuffer == null || multiVerMap == null) {
            multiBuffer = new ConcurrentHashMap<String, Map<String, Map<Long, FrontModelDefine>>>();
            multiVerMap = new ConcurrentHashMap<String, Map<Long, Long>>();
            this.multiTenantBuffer.put(tenantName, multiBuffer);
            this.multiTenantVerMap.put(tenantName, multiVerMap);
        }
        Map<Long, Long> multiCacheVer = multiVerMap.get(defineName);
        String rediskey = tenantName + ":" + defineName;
        HashOperations HashOperation = this.redisTemplate.opsForHash();
        String redisRowVersion = (String)HashOperation.get((Object)rediskey, (Object)"rowVersion");
        if (!StringUtils.hasText(redisRowVersion)) {
            redisRowVersion = EMPTY_VERSION;
        }
        if (multiCacheVer == null || multiCacheVer.isEmpty()) {
            FrontModelDefine define = this.buildFrontModelDefine(model, schemeCode, viewName);
            ConcurrentHashMap<Long, FrontModelDefine> schemeDefineMap = new ConcurrentHashMap<Long, FrontModelDefine>();
            ConcurrentHashMap<String, ConcurrentHashMap<Long, FrontModelDefine>> schemeDefineVer = new ConcurrentHashMap<String, ConcurrentHashMap<Long, FrontModelDefine>>();
            schemeDefineMap.put(Long.valueOf(redisRowVersion), define);
            schemeDefineVer.put(schemeCode, schemeDefineMap);
            multiBuffer.put(defineName, schemeDefineVer);
            ConcurrentHashMap<Long, Long> ver = new ConcurrentHashMap<Long, Long>();
            ver.put(Long.valueOf(redisRowVersion), Long.valueOf(redisRowVersion));
            multiVerMap.put(defineName, ver);
            return this.getI18nDefine(define);
        }
        Set<Long> longs = multiCacheVer.keySet();
        Object[] verSort = longs.toArray();
        Arrays.sort(verSort);
        Object verMax = verSort[verSort.length - 1];
        if (String.valueOf(verMax).equals(redisRowVersion)) {
            Map<String, Map<Long, FrontModelDefine>> schemeDefineMap = multiBuffer.get(defineName);
            Map<Long, FrontModelDefine> frontDefine = schemeDefineMap.get(schemeCode);
            if (frontDefine != null && !frontDefine.isEmpty() && frontDefine.containsKey(Long.valueOf(redisRowVersion))) {
                FrontModelDefine frontModelDefine = frontDefine.get(Long.valueOf(redisRowVersion));
                return this.getI18nDefine(frontModelDefine);
            }
            FrontModelDefine define = this.buildFrontModelDefine(model, schemeCode, viewName);
            if (frontDefine == null) {
                frontDefine = new ConcurrentHashMap<Long, FrontModelDefine>();
            }
            frontDefine.put(Long.valueOf(redisRowVersion), define);
            schemeDefineMap.put(schemeCode, frontDefine);
            return this.getI18nDefine(define);
        }
        FrontModelDefine define = this.buildFrontModelDefine(model, schemeCode, viewName);
        ConcurrentHashMap<Long, FrontModelDefine> frontModel = new ConcurrentHashMap<Long, FrontModelDefine>();
        frontModel.put(Long.valueOf(redisRowVersion), define);
        Map<String, Map<Long, FrontModelDefine>> initFrontModelDefine = multiBuffer.get(defineName);
        initFrontModelDefine.clear();
        initFrontModelDefine.put(schemeCode, frontModel);
        multiCacheVer.clear();
        multiCacheVer.put(Long.valueOf(redisRowVersion), Long.valueOf(redisRowVersion));
        return this.getI18nDefine(define);
    }

    public FrontModelDefine getDefine(Model billModel, String schemeCode, String viewName, long defineVer) {
        BillModelImpl model = (BillModelImpl)billModel;
        if (!StringUtils.hasText(schemeCode)) {
            Optional<Map> optional;
            ViewDefineImpl view = model.getDefine().getView();
            List schemes = view.getSchemes();
            schemeCode = schemes == null ? "defaultScheme" : ((optional = schemes.stream().filter(o -> (Boolean)o.get("default")).findAny()).isPresent() ? String.valueOf(optional.get().get("code")) : "defaultScheme");
        }
        String tenantName = Env.getTenantName();
        String defineName = model.getDefine().getName();
        Map<String, Map<String, Map<Long, FrontModelDefine>>> buffer = this.multiTenantBuffer.get(tenantName);
        Map<String, Map<Long, Long>> verMap = this.multiTenantVerMap.get(tenantName);
        if (verMap == null || buffer == null) {
            buffer = new ConcurrentHashMap<String, Map<String, Map<Long, FrontModelDefine>>>();
            verMap = new ConcurrentHashMap<String, Map<Long, Long>>();
            this.multiTenantBuffer.put(tenantName, buffer);
            this.multiTenantVerMap.put(tenantName, verMap);
        }
        Map<Long, Long> cacheVerMap = verMap.get(defineName);
        String rediskey = tenantName + ":" + defineName;
        HashOperations HashOperation = this.redisTemplate.opsForHash();
        String redisRowVersion = (String)HashOperation.get((Object)rediskey, (Object)"rowVersion");
        if (!StringUtils.hasText(redisRowVersion)) {
            redisRowVersion = EMPTY_VERSION;
        }
        if (cacheVerMap == null || cacheVerMap.isEmpty()) {
            FrontModelDefine define = this.buildFrontModelDefine(model, schemeCode, viewName);
            ConcurrentHashMap<Long, FrontModelDefine> schemeDefineMap = new ConcurrentHashMap<Long, FrontModelDefine>();
            ConcurrentHashMap<String, ConcurrentHashMap<Long, FrontModelDefine>> schemeDefineVer = new ConcurrentHashMap<String, ConcurrentHashMap<Long, FrontModelDefine>>();
            schemeDefineMap.put(defineVer, define);
            schemeDefineVer.put(schemeCode, schemeDefineMap);
            buffer.put(defineName, schemeDefineVer);
            ConcurrentHashMap<Long, Long> ver = new ConcurrentHashMap<Long, Long>();
            if (defineVer == 0L) {
                ver.put(defineVer, Long.valueOf(redisRowVersion));
            } else {
                ver.put(defineVer, defineVer);
            }
            verMap.put(defineName, ver);
            return this.getI18nDefine(define);
        }
        Set<Long> longs = cacheVerMap.keySet();
        Object[] verSort = longs.toArray();
        Arrays.sort(verSort);
        Object verMax = verSort[verSort.length - 1];
        Map<String, Map<Long, FrontModelDefine>> schemeDefineMap = buffer.get(defineName);
        Map<Long, FrontModelDefine> frontDefine = schemeDefineMap.get(schemeCode);
        if (frontDefine != null && !frontDefine.isEmpty() && frontDefine.containsKey(defineVer)) {
            FrontModelDefine frontModelDefine = frontDefine.get(defineVer);
            return this.getI18nDefine(frontModelDefine);
        }
        FrontModelDefine define = this.buildFrontModelDefine(model, schemeCode, viewName);
        if (defineVer == 0L || String.valueOf(verMax).equals(redisRowVersion)) {
            ConcurrentHashMap<Long, FrontModelDefine> frontModel = new ConcurrentHashMap<Long, FrontModelDefine>();
            frontModel.put(defineVer, define);
            Map<String, Map<Long, FrontModelDefine>> initFrontModelDefine = buffer.get(defineName);
            initFrontModelDefine.clear();
            initFrontModelDefine.put(schemeCode, frontModel);
            cacheVerMap.clear();
            cacheVerMap.put(defineVer, defineVer);
        } else if (defineVer < Long.parseLong(String.valueOf(verMax))) {
            if (frontDefine == null) {
                ConcurrentHashMap<Long, FrontModelDefine> frontModel = new ConcurrentHashMap<Long, FrontModelDefine>();
                frontModel.put(defineVer, define);
                schemeDefineMap.put(schemeCode, frontModel);
            } else {
                frontDefine.put(defineVer, define);
            }
            cacheVerMap.put(defineVer, defineVer);
        }
        return this.getI18nDefine(define);
    }

    public FrontModelDefine getExternalDefine(Model billModel, String externalViewName, String viewName) {
        BillModelImpl model = (BillModelImpl)billModel;
        String tenantName = Env.getTenantName();
        String defineName = model.getDefine().getName();
        ExternalViewDefine externalViewDefine = (ExternalViewDefine)this.externalViewManager.find(externalViewName);
        Map<String, Map<String, FrontModelDefine>> buffer = this.tenantExternalBuffer.get(tenantName);
        Map<String, String> verMap = this.tenanExternaltVerMap.get(tenantName);
        if (verMap == null || buffer == null) {
            buffer = new ConcurrentHashMap<String, Map<String, FrontModelDefine>>();
            verMap = new ConcurrentHashMap<String, String>();
            this.tenantExternalBuffer.put(tenantName, buffer);
            this.tenanExternaltVerMap.put(tenantName, verMap);
        }
        String redisRowVersion = "";
        if (VaBillCoreConfig.isRedisEnable()) {
            String rediskey = tenantName + ":" + defineName;
            HashOperations HashOperation = this.redisTemplate.opsForHash();
            redisRowVersion = (String)HashOperation.get((Object)rediskey, (Object)"rowVersion");
        }
        String cacheVer = verMap.get(defineName);
        String version = externalViewDefine.getVer(defineName) + redisRowVersion;
        if (cacheVer == null) {
            FrontModelDefine define = this.buildExternalFrontModelDefine(model, externalViewDefine, viewName);
            ConcurrentHashMap<String, FrontModelDefine> externalDefineMap = new ConcurrentHashMap<String, FrontModelDefine>();
            externalDefineMap.put(externalViewName, define);
            buffer.put(defineName, externalDefineMap);
            verMap.put(defineName, version);
            return define;
        }
        if (version.equals(cacheVer)) {
            Map<String, FrontModelDefine> externalDefineMap = buffer.get(defineName);
            FrontModelDefine frontDefine = externalDefineMap.get(externalViewName);
            if (frontDefine != null) {
                return frontDefine;
            }
            FrontModelDefine define = this.buildExternalFrontModelDefine(model, externalViewDefine, viewName);
            externalDefineMap.put(externalViewName, define);
            return define;
        }
        FrontModelDefine define = this.buildExternalFrontModelDefine(model, externalViewDefine, viewName);
        Map<String, FrontModelDefine> externalDefineMap = buffer.get(defineName);
        externalDefineMap.clear();
        externalDefineMap.put(externalViewName, define);
        verMap.put(defineName, version);
        return define;
    }

    public void clearCache(String tenantName, String defineName) {
        Map<String, Map<String, Map<Long, FrontModelDefine>>> buffer = this.multiTenantBuffer.get(tenantName);
        Map<String, Map<Long, Long>> verMap = this.multiTenantVerMap.get(tenantName);
        if (buffer != null && buffer.size() > 0) {
            buffer.remove(defineName);
        }
        if (verMap != null && verMap.size() > 0) {
            verMap.remove(defineName);
        }
    }

    private FrontModelDefine buildFrontModelDefine(BillModelImpl model, String schemeCode, String viewName) {
        FrontModelDefine frontModelDefine = schemeCode != null ? new FrontModelDefine((ModelDefine)model.getDefine(), false, viewName, schemeCode) : new FrontModelDefine((ModelDefine)model.getDefine(), false, viewName);
        try {
            FrontAfterLoadEventDefine frontAfterLoadEventDefine = (FrontAfterLoadEventDefine)frontModelDefine.get(FrontAfterLoadEventDefine.class);
            AfterLoadEventImpl afterLoadEvent = (AfterLoadEventImpl)model.getPlugins().get(AfterLoadEventImpl.class);
            afterLoadEvent.initEvents();
            frontAfterLoadEventDefine.setOptions(afterLoadEvent.getOptions());
            FrontDataDefine frontDataDefine = (FrontDataDefine)frontModelDefine.get(FrontDataDefine.class);
            HashMap fieldIndexs = new HashMap();
            model.getData().getTables().forEach((index, t) -> {
                HashMap fieldIndex = new HashMap();
                t.getFields().forEachIndex((f_index, f) -> fieldIndex.put(f.getName(), f.getIndex()));
                fieldIndexs.put(t.getName(), fieldIndex);
            });
            frontDataDefine.setFieldIndexs(fieldIndexs);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return frontModelDefine;
    }

    private FrontModelDefine buildExternalFrontModelDefine(BillModelImpl model, ExternalViewDefine externalViewDefine, String viewName) {
        FrontModelDefine frontModelDefine = new FrontModelDefine((ModelDefine)model.getDefine(), false, viewName, externalViewDefine);
        try {
            FrontAfterLoadEventDefine frontAfterLoadEventDefine = (FrontAfterLoadEventDefine)frontModelDefine.get(FrontAfterLoadEventDefine.class);
            AfterLoadEventImpl afterLoadEvent = (AfterLoadEventImpl)model.getPlugins().get(AfterLoadEventImpl.class);
            frontAfterLoadEventDefine.setOptions(afterLoadEvent.getOptions());
            FrontDataDefine frontDataDefine = (FrontDataDefine)frontModelDefine.get(FrontDataDefine.class);
            HashMap fieldIndexs = new HashMap();
            model.getData().getTables().forEach((index, t) -> {
                HashMap fieldIndex = new HashMap();
                t.getFields().forEachIndex((f_index, f) -> fieldIndex.put(f.getName(), f.getIndex()));
                fieldIndexs.put(t.getName(), fieldIndex);
            });
            frontDataDefine.setFieldIndexs(fieldIndexs);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return frontModelDefine;
    }

    private FrontModelDefine cloneFrontModelDefine(FrontModelDefine billDefine) {
        List i18nPluginList = this.i18nPluginManager.getI18nPluginList();
        HashMap<String, I18nPlugin> needClonePluginMap = new HashMap<String, I18nPlugin>();
        for (I18nPlugin i18nPlugin : i18nPluginList) {
            if (!i18nPlugin.isFrontTrans()) continue;
            needClonePluginMap.put(i18nPlugin.getName(), i18nPlugin);
        }
        FrontModelDefine define = new FrontModelDefine(billDefine.getModelDefine());
        define.setName(billDefine.getName());
        define.setMetaInfo(billDefine.getMetaInfo());
        define.setModelType(billDefine.getModelType());
        define.setTableFields(billDefine.getTableFields());
        List plugins = billDefine.getPlugins();
        ArrayList<Object> newPlugins = new ArrayList<Object>();
        for (FrontPluginDefine plugin : plugins) {
            if (plugin == null) continue;
            try {
                if (needClonePluginMap.containsKey(plugin.getType())) {
                    newPlugins.add(JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)plugin), plugin.getClass()));
                    continue;
                }
                newPlugins.add(plugin);
            }
            catch (Exception e) {
                newPlugins.add(plugin);
            }
        }
        define.setPlugins(newPlugins);
        return define;
    }

    private void defineI18nTrans(FrontModelDefine billDefine) {
        try {
            List i18nPluginList = this.i18nPluginManager.getI18nPluginList();
            List frontPlugins = billDefine.getPlugins();
            HashMap<String, Object> frontPluginMap = new HashMap<String, Object>();
            for (Object frontPlugin : frontPlugins) {
                if (frontPlugin == null) continue;
                frontPluginMap.put(frontPlugin.getType(), frontPlugin);
            }
            ArrayList keys = new ArrayList();
            for (I18nPlugin i18nPlugin : i18nPluginList) {
                List pluginKeys;
                PluginDefine plugin;
                if (!i18nPlugin.isFrontTrans() || (plugin = (PluginDefine)billDefine.getModelDefine().getPlugins().find(i18nPlugin.getName())) == null || (pluginKeys = i18nPlugin.getAllI18nKeys(plugin, billDefine.getModelDefine())) == null || pluginKeys.size() <= 0) continue;
                keys.addAll(pluginKeys);
            }
            VaI18nResourceDTO dataResourceDTO = new VaI18nResourceDTO();
            dataResourceDTO.setKey(keys);
            List results = this.vaDataResourceClient.queryList(dataResourceDTO);
            if (results == null || results.size() <= 0) {
                return;
            }
            HashMap i18nValueMap = new HashMap();
            int i = 0;
            for (String result : results) {
                i18nValueMap.put(keys.get(i), result);
                ++i;
            }
            for (I18nPlugin i18nPlugin : i18nPluginList) {
                FrontPluginDefine frontPlugin;
                if (!i18nPlugin.isFrontTrans() || (frontPlugin = (FrontPluginDefine)frontPluginMap.get(i18nPlugin.getName())) == null) continue;
                i18nPlugin.processForI18n(frontPlugin, billDefine.getModelDefine(), i18nValueMap);
            }
        }
        catch (Exception e) {
            logger.error("\u5355\u636e\u5b9a\u4e49\u56fd\u9645\u5316\u8f6c\u5316\u5f02\u5e38", e);
        }
    }

    private FrontModelDefine getI18nDefine(FrontModelDefine billDefine) {
        if (!VaI18nParamUtils.getTranslationEnabled().booleanValue()) {
            return billDefine;
        }
        List i18nPluginList = this.i18nPluginManager.getI18nPluginList();
        if (i18nPluginList == null || i18nPluginList.size() <= 0) {
            return billDefine;
        }
        List frontPlugins = billDefine.getPlugins();
        if (frontPlugins == null || frontPlugins.size() <= 0) {
            return billDefine;
        }
        FrontModelDefine i18nDefine = this.cloneFrontModelDefine(billDefine);
        this.defineI18nTrans(i18nDefine);
        return i18nDefine;
    }
}

