/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionCacheObject
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IdMutexProvider
 *  com.jiuqi.nr.datascheme.api.service.IdMutexProvider$Mutex
 */
package com.jiuqi.nr.definition.internal.runtime.service;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionCacheObject;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IdMutexProvider;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormFieldInfoDefine;
import com.jiuqi.nr.definition.internal.dao.FormFieldInfoDefineDao;
import com.jiuqi.nr.definition.internal.impl.FormFieldInfoDefineImpl;
import com.jiuqi.nr.definition.internal.runtime.controller.IFormFieldInfoService;
import com.jiuqi.nr.definition.internal.runtime.service.RuntimeDefinitionChangeListener;
import com.jiuqi.nr.definition.internal.service.DesignDataLinkDefineService;
import com.jiuqi.nr.definition.internal.service.DesignFormDefineService;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FormFieldInfoService
implements IFormFieldInfoService,
RuntimeDefinitionChangeListener,
RuntimeDefinitionRefreshListener {
    @Autowired
    private FormFieldInfoDefineDao formFieldInfoDefineDao;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DesignFormDefineService designFormDefineService;
    @Autowired
    private DesignDataLinkDefineService designDataLinkDefineService;
    private final NedisCache cache;
    private final IdMutexProvider idMutexProvider = new IdMutexProvider();
    private static final Logger logger = LoggerFactory.getLogger(FormFieldInfoService.class);

    public FormFieldInfoService(NedisCacheProvider cacheProvider) {
        this.cache = cacheProvider.getCacheManager("NR_DEFINITION_CACHE_CONFIGURATION").getCache(RuntimeDefinitionCacheObject.createCacheName(FormFieldInfoDefine.class));
    }

    @Override
    public Set<FormFieldInfoDefine> getFormFieldInfos(String fieldKey) {
        Cache.ValueWrapper valueWrapper = this.cache.get(fieldKey);
        if (null == valueWrapper) {
            return this.loadCache(fieldKey);
        }
        return (Set)valueWrapper.get();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Set<FormFieldInfoDefine> loadCache(String fieldKey) {
        DataField dataField = this.runtimeDataSchemeService.getDataField(fieldKey);
        if (null == dataField) {
            this.cache.put(fieldKey, null);
            return Collections.emptySet();
        }
        IdMutexProvider.Mutex mutex = this.idMutexProvider.getMutex(dataField.getDataTableKey());
        synchronized (mutex) {
            List dataFields = this.runtimeDataSchemeService.getDataFieldByTable(dataField.getDataTableKey());
            List<FormFieldInfoDefine> infos = this.formFieldInfoDefineDao.getByDataTableKey(dataField.getDataTableKey());
            if (null == infos) {
                infos = Collections.emptyList();
            }
            HashMap infosMap = new HashMap();
            for (FormFieldInfoDefine info : infos) {
                infosMap.computeIfAbsent(info.getFieldKey(), k -> new HashSet()).add(info);
            }
            for (DataField f : dataFields) {
                this.cache.put(f.getKey(), infosMap.getOrDefault(f.getKey(), Collections.emptySet()));
            }
            return infosMap.getOrDefault(fieldKey, Collections.emptySet());
        }
    }

    public void deploy(String taskKey) throws Exception {
        if (!StringUtils.hasLength(taskKey)) {
            return;
        }
        logger.info("\u66f4\u65b0\u62a5\u8868\u6307\u6807\u4fe1\u606f");
        this.formFieldInfoDefineDao.deleteByTask(taskKey);
        List<DesignFormDefine> allForms = this.designFormDefineService.queryFormDefinesByTask(taskKey, false);
        if (null == allForms || allForms.isEmpty()) {
            return;
        }
        HashSet<FormFieldInfoDefineImpl> infos = new HashSet<FormFieldInfoDefineImpl>();
        for (FormDefine formDefine : allForms) {
            List<DataLinkDefine> links = this.designDataLinkDefineService.getAllLinksInForm(formDefine.getKey());
            if (null == links || links.isEmpty()) continue;
            for (DataLinkDefine link : links) {
                String fieldKey = link.getLinkExpression();
                if (!StringUtils.hasText(fieldKey) || link.getType() != DataLinkType.DATA_LINK_TYPE_FIELD && link.getType() != DataLinkType.DATA_LINK_TYPE_INFO) continue;
                infos.add(this.createFormFieldInfo(taskKey, formDefine.getKey(), fieldKey));
            }
        }
        if (!infos.isEmpty()) {
            this.formFieldInfoDefineDao.insert(infos.toArray());
        }
        logger.info("\u66f4\u65b0\u62a5\u8868\u6307\u6807\u4fe1\u606f\u6210\u529f");
    }

    private FormFieldInfoDefineImpl createFormFieldInfo(String taskKey, String formKey, String fieldKey) {
        FormFieldInfoDefineImpl define = new FormFieldInfoDefineImpl();
        define.setTaskKey(taskKey);
        define.setFormKey(formKey);
        define.setFieldKey(fieldKey);
        return define;
    }

    public void onClearCache() {
        logger.info("\u6e05\u7406\u62a5\u8868\u6307\u6807\u4fe1\u606f\u7f13\u5b58");
        this.cache.clear();
    }

    @Override
    public void onDeploy(DeployParams deployParams) {
        logger.info("\u6e05\u7406\u62a5\u8868\u6307\u6807\u4fe1\u606f\u7f13\u5b58");
        this.cache.clear();
    }
}

