/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener
 */
package com.jiuqi.nr.definition.internal.runtime.service;

import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener;
import com.jiuqi.nr.definition.common.MetaComparator;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.definition.facade.PrintTemplateDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimePrintTemplateDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimePrintTemplateSchemeDefineDao;
import com.jiuqi.nr.definition.internal.runtime.controller.IPrintRuntimeService;
import com.jiuqi.nr.definition.internal.runtime.service.RuntimeDefinitionChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

@Service
public class RuntimePrintService
implements IPrintRuntimeService,
RuntimeDefinitionChangeListener,
RuntimeDefinitionRefreshListener {
    private final RunTimePrintTemplateSchemeDefineDao schemeDefineDao;
    private final RunTimePrintTemplateDefineDao templateDefineDao;
    private final RuntimeDefinitionCache<PrintTemplateSchemeDefine> schemeCache;
    private final RuntimeDefinitionCache<PrintTemplateDefine> templateCache;

    public RuntimePrintService(RunTimePrintTemplateSchemeDefineDao schemeDefineDao, RunTimePrintTemplateDefineDao templateDefineDao, NedisCacheProvider cacheProvider) {
        if (schemeDefineDao == null) {
            throw new IllegalArgumentException("'schemeDefineDao' must not be null.");
        }
        if (templateDefineDao == null) {
            throw new IllegalArgumentException("'templateDefineDao' must not be null.");
        }
        if (cacheProvider == null) {
            throw new IllegalArgumentException("'cacheProvider' must not be null.");
        }
        this.schemeDefineDao = schemeDefineDao;
        this.templateDefineDao = templateDefineDao;
        this.schemeCache = new RuntimeDefinitionCache(cacheProvider.getCacheManager("NR_DEFINITION_CACHE_CONFIGURATION"), PrintTemplateSchemeDefine.class);
        this.templateCache = new RuntimeDefinitionCache(cacheProvider.getCacheManager("NR_DEFINITION_CACHE_CONFIGURATION"), PrintTemplateDefine.class);
    }

    @Override
    public List<PrintTemplateSchemeDefine> getAllPrintSchemeByTask(String taskKey) {
        if (taskKey == null) {
            return Collections.emptyList();
        }
        return this.schemeDefineDao.queryPrintSchemeDefineByTask(taskKey);
    }

    public List<PrintTemplateSchemeDefine> loadPrintSchemeCache(String formSchemeKey) {
        return (List)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
            List<PrintTemplateSchemeDefine> schemeDefines = this.getPrintSchemeFromCache(formSchemeKey);
            if (null != schemeDefines) {
                return schemeDefines;
            }
            List<PrintTemplateSchemeDefine> defines = this.schemeDefineDao.queryPrintSchemeDefineByScheme(formSchemeKey);
            if (null == defines) {
                defines = Collections.emptyList();
            }
            MetaComparator.sort(defines);
            this.schemeCache.putObjects(defines);
            List keys = defines.stream().map(IBaseMetaItem::getKey).filter(Objects::nonNull).collect(Collectors.toList());
            this.schemeCache.putKVIndex(formSchemeKey, keys);
            return defines;
        });
    }

    private List<PrintTemplateSchemeDefine> getPrintSchemeFromCache(String formSchemeKey) {
        Cache.ValueWrapper valueWrapper = this.schemeCache.getKVIndexValue(formSchemeKey);
        if (Objects.nonNull(valueWrapper)) {
            List keys = (List)valueWrapper.get();
            Map objects = this.schemeCache.getObjects((Collection)keys);
            List<PrintTemplateSchemeDefine> defines = objects.values().stream().filter(Objects::nonNull).map(v -> (PrintTemplateSchemeDefine)v.get()).filter(Objects::nonNull).collect(Collectors.toList());
            MetaComparator.sort(defines);
            return defines;
        }
        return null;
    }

    @Override
    public List<PrintTemplateSchemeDefine> getAllPrintSchemeByFormScheme(String formSchemeKey) {
        if (formSchemeKey == null) {
            return Collections.emptyList();
        }
        List<PrintTemplateSchemeDefine> schemeDefines = this.getPrintSchemeFromCache(formSchemeKey);
        if (null != schemeDefines) {
            return schemeDefines;
        }
        return this.loadPrintSchemeCache(formSchemeKey);
    }

    @Override
    public PrintTemplateSchemeDefine queryPrintTemplateSchemeDefine(String printSchemeKey) {
        if (printSchemeKey == null) {
            return null;
        }
        Cache.ValueWrapper valueWrapper = this.schemeCache.getObject(printSchemeKey);
        if (Objects.nonNull(valueWrapper)) {
            return (PrintTemplateSchemeDefine)valueWrapper.get();
        }
        return (PrintTemplateSchemeDefine)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
            Cache.ValueWrapper revalueWrapper = this.schemeCache.getObject(printSchemeKey);
            if (Objects.nonNull(revalueWrapper)) {
                return (PrintTemplateSchemeDefine)revalueWrapper.get();
            }
            PrintTemplateSchemeDefine define = this.schemeDefineDao.getDefineByKey(printSchemeKey);
            if (null == define) {
                this.schemeCache.putNullObject(printSchemeKey);
            } else {
                this.loadPrintSchemeCache(define.getFormSchemeKey());
            }
            return define;
        });
    }

    private List<PrintTemplateDefine> loadPrintTemplateCache(String printSchemeKey) {
        return (List)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
            List<PrintTemplateDefine> templates = this.getPrintTemplateFromCache(printSchemeKey);
            if (null != templates) {
                return templates;
            }
            List<PrintTemplateDefine> defines = this.templateDefineDao.queryPrintDefineByScheme(printSchemeKey);
            if (null == defines) {
                defines = Collections.emptyList();
            }
            ArrayList<String> keys = new ArrayList<String>();
            for (PrintTemplateDefine define : defines) {
                keys.add(define.getKey());
                this.templateCache.putObject((IBaseMetaItem)define);
                this.templateCache.putKVIndex(RuntimePrintService.createIndex(define.getPrintSchemeKey(), define.getFormKey()), (Object)define);
            }
            this.templateCache.putKVIndex(printSchemeKey, keys);
            return defines;
        });
    }

    private List<PrintTemplateDefine> getPrintTemplateFromCache(String printSchemeKey) {
        Cache.ValueWrapper valueWrapper = this.templateCache.getKVIndexValue(printSchemeKey);
        if (Objects.nonNull(valueWrapper)) {
            List keys = (List)valueWrapper.get();
            Map objects = this.templateCache.getObjects((Collection)keys);
            List<PrintTemplateDefine> defines = objects.values().stream().filter(Objects::nonNull).map(v -> (PrintTemplateDefine)v.get()).filter(Objects::nonNull).collect(Collectors.toList());
            MetaComparator.sort(defines);
            return defines;
        }
        return null;
    }

    @Override
    public List<PrintTemplateDefine> getAllPrintTemplateInScheme(String printSchemeKey) {
        if (printSchemeKey == null) {
            return Collections.emptyList();
        }
        List<PrintTemplateDefine> templates = this.getPrintTemplateFromCache(printSchemeKey);
        if (null != templates) {
            return templates;
        }
        return this.loadPrintTemplateCache(printSchemeKey);
    }

    @Override
    public PrintTemplateDefine queryPrintTemplateDefine(String printTemplateKey) {
        if (printTemplateKey == null) {
            return null;
        }
        Cache.ValueWrapper valueWrapper = this.templateCache.getObject(printTemplateKey);
        if (Objects.nonNull(valueWrapper)) {
            return (PrintTemplateDefine)valueWrapper.get();
        }
        return (PrintTemplateDefine)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
            Cache.ValueWrapper revalueWrapper = this.templateCache.getObject(printTemplateKey);
            if (Objects.nonNull(revalueWrapper)) {
                return (PrintTemplateDefine)revalueWrapper.get();
            }
            PrintTemplateDefine define = this.templateDefineDao.getDefineByKey(printTemplateKey);
            if (null == define) {
                this.templateCache.putNullObject(printTemplateKey);
            } else {
                this.loadPrintTemplateCache(define.getPrintSchemeKey());
            }
            return define;
        });
    }

    @Override
    public PrintTemplateDefine queryPrintTemplateDefineBySchemeAndForm(String printSchemeKey, String formKey) {
        if (printSchemeKey == null || formKey == null) {
            return null;
        }
        String index = RuntimePrintService.createIndex(printSchemeKey, formKey);
        Cache.ValueWrapper valueWrapper = this.templateCache.getKVIndexValue(index);
        if (Objects.nonNull(valueWrapper)) {
            return (PrintTemplateDefine)valueWrapper.get();
        }
        return (PrintTemplateDefine)RuntimeDefinitionCache.synchronizedRun((Object)this, () -> {
            Cache.ValueWrapper revalueWrapper = this.templateCache.getKVIndexValue(index);
            if (Objects.nonNull(revalueWrapper)) {
                return (PrintTemplateDefine)revalueWrapper.get();
            }
            PrintTemplateDefine define = this.templateDefineDao.queryPrintDefineBySchemeAndForm(printSchemeKey, formKey).stream().findFirst().orElse(null);
            if (null == define) {
                this.templateCache.putNullObject(index);
            } else {
                this.loadPrintTemplateCache(define.getPrintSchemeKey());
            }
            return define;
        });
    }

    private static String createIndex(String printSchemeKey, String formKey) {
        return printSchemeKey + ";" + formKey;
    }

    private void refreshPrintTemplateCache(String printSchemeKey) {
        List<PrintTemplateDefine> defines = this.getPrintTemplateFromCache(printSchemeKey);
        if (null != defines) {
            for (PrintTemplateDefine define : defines) {
                this.templateCache.removeObjects(Collections.singleton(define.getKey()));
                this.templateCache.removeIndexs(Collections.singleton(RuntimePrintService.createIndex(define.getPrintSchemeKey(), define.getFormKey())));
            }
        }
        this.templateCache.removeIndexs(Collections.singleton(printSchemeKey));
    }

    private void refreshPrintSchemeCache(String formSchemeKey) {
        List<PrintTemplateSchemeDefine> defines = this.getPrintSchemeFromCache(formSchemeKey);
        if (null != defines) {
            for (PrintTemplateSchemeDefine define : defines) {
                this.schemeCache.removeObjects(Collections.singleton(define.getKey()));
                this.refreshPrintTemplateCache(define.getKey());
            }
        }
        this.schemeCache.removeIndexs(Collections.singleton(formSchemeKey));
    }

    private void refreshPrintCache(String printSchemeKey) {
        Cache.ValueWrapper valueWrapper = this.schemeCache.getObject(printSchemeKey);
        PrintTemplateSchemeDefine scheme = null;
        if (null != valueWrapper) {
            scheme = (PrintTemplateSchemeDefine)valueWrapper.get();
        }
        if (null == scheme) {
            scheme = this.schemeDefineDao.getDefineByKey(printSchemeKey);
        }
        this.refreshPrintTemplateCache(printSchemeKey);
        if (null != scheme) {
            this.refreshPrintSchemeCache(scheme.getFormSchemeKey());
        }
    }

    @Override
    public void onDeploy(DeployParams deployParams) {
        Set formSchemeKeys = deployParams.getFormScheme().getRuntimeUninDesignTimeKeys();
        if (!formSchemeKeys.isEmpty()) {
            for (String formSchemeKey : formSchemeKeys) {
                this.refreshPrintSchemeCache(formSchemeKey);
            }
            return;
        }
        HashSet<String> printSchemeKeys = deployParams.getPrintScheme().getRuntimeUninDesignTimeKeys();
        if (!printSchemeKeys.isEmpty()) {
            for (String printSchemeKey : printSchemeKeys) {
                this.refreshPrintCache(printSchemeKey);
            }
            return;
        }
        Set printTemplateKeys = deployParams.getPrintTemplate().getRuntimeUninDesignTimeKeys();
        if (!printTemplateKeys.isEmpty()) {
            printSchemeKeys = new HashSet<String>();
            for (String printTemplateKey : printTemplateKeys) {
                PrintTemplateDefine template;
                Cache.ValueWrapper valueWrapper = this.templateCache.getObject(printTemplateKey);
                if (Objects.isNull(valueWrapper) || null == (template = (PrintTemplateDefine)valueWrapper.get())) continue;
                printSchemeKeys.add(template.getPrintSchemeKey());
            }
            for (String printSchemeKey : printSchemeKeys) {
                this.refreshPrintCache(printSchemeKey);
            }
        }
    }

    public void onClearCache() {
        this.schemeCache.clear();
        this.templateCache.clear();
    }
}

