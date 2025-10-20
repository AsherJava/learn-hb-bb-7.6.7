/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.common.Consts
 *  com.jiuqi.nr.entity.adapter.impl.org.util.OrgAdapterUtil
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.va.domain.org.OrgCategoryDTO
 *  com.jiuqi.va.organization.service.OrgDataService
 */
package com.jiuqi.nr.definition.internal.runtime.service;

import com.jiuqi.nr.datascheme.common.Consts;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.service.NrFormParamCacheService;
import com.jiuqi.nr.definition.internal.runtime.service.NrFormulaCacheService;
import com.jiuqi.nr.definition.internal.runtime.service.NrParamCacheManagerService;
import com.jiuqi.nr.definition.internal.runtime.service.NrTaskParamCacheService;
import com.jiuqi.nr.entity.adapter.impl.org.util.OrgAdapterUtil;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.va.domain.org.OrgCategoryDTO;
import com.jiuqi.va.organization.service.OrgDataService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@ConditionalOnProperty(value={"jiuqi.nr.definition.cache.preload.enable"}, matchIfMissing=true)
public class NrParamCachePreloadService {
    private static final Logger LOGGER = Consts.NR_PARAM_GRAPH_LOGGER;
    @Autowired
    private OrgDataService orgDataService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private NrTaskParamCacheService taskParamCacheService;
    @Autowired
    private NrFormParamCacheService formParamCacheService;
    @Autowired
    private NrFormulaCacheService formFormulaCacheService;

    public void preload(Collection<NrParamCacheManagerService.PreloadSetting> oldSettings, Collection<NrParamCacheManagerService.PreloadSetting> newSettings) {
        if (null == oldSettings) {
            this.preloadParallel(newSettings);
        } else {
            HashMap<String, NrParamCacheManagerService.PreloadSetting> oldSettingMap = new HashMap<String, NrParamCacheManagerService.PreloadSetting>(oldSettings.size());
            for (NrParamCacheManagerService.PreloadSetting oldSetting : oldSettings) {
                oldSettingMap.put(oldSetting.getFormSchemeKey(), oldSetting);
            }
            HashSet<NrParamCacheManagerService.PreloadSetting> settings = new HashSet<NrParamCacheManagerService.PreloadSetting>();
            for (NrParamCacheManagerService.PreloadSetting newSetting : newSettings) {
                NrParamCacheManagerService.PreloadSetting oldSetting = (NrParamCacheManagerService.PreloadSetting)oldSettingMap.get(newSetting.getFormSchemeKey());
                if (null == oldSetting) {
                    settings.add(newSetting);
                    continue;
                }
                HashMap<String, Date> newPeriodMap = new HashMap<String, Date>(newSetting.getPeriods());
                Map<String, Date> oldPeriodMap = oldSetting.getPeriods();
                newPeriodMap.entrySet().removeAll(oldPeriodMap.entrySet());
                if (newPeriodMap.isEmpty()) continue;
                settings.add(new NrParamCacheManagerService.PreloadSetting(newSetting.getTaskKey(), newSetting.getFormSchemeKey(), newPeriodMap));
            }
            this.preload(settings);
        }
    }

    private List<Callable<PreloadResult>> initPreloadTask(Collection<NrParamCacheManagerService.PreloadSetting> settings) {
        ArrayList<Callable<PreloadResult>> paramPreloadTasks = new ArrayList<Callable<PreloadResult>>();
        ArrayList<PreloadOrgDataTask> orgDataPreloadTasks = new ArrayList<PreloadOrgDataTask>();
        for (NrParamCacheManagerService.PreloadSetting setting : settings) {
            paramPreloadTasks.add(new PreloadParamTask(setting.getFormSchemeKey()));
            TaskDefine task = this.taskParamCacheService.queryTaskDefine(setting.getTaskKey());
            if (!OrgAdapterUtil.isOrg((String)task.getDw())) continue;
            String entityCode = this.entityMetaService.getEntityCode(task.getDw());
            orgDataPreloadTasks.add(new PreloadOrgDataTask(entityCode, setting.getPeriods()));
        }
        paramPreloadTasks.addAll(orgDataPreloadTasks);
        return paramPreloadTasks;
    }

    private void preloadParallel(Collection<NrParamCacheManagerService.PreloadSetting> settings) {
        List<Future<PreloadResult>> futures;
        if (CollectionUtils.isEmpty(settings)) {
            return;
        }
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u9884\u70ed\uff1a\u7a0b\u5e8f\u542f\u52a8\u65f6\u9884\u70ed\u5f00\u59cb");
        long startTime = System.currentTimeMillis();
        List<Callable<PreloadResult>> preloadTasks = this.initPreloadTask(settings);
        int maxThreadCount = Runtime.getRuntime().availableProcessors();
        int threadCount = Math.min(maxThreadCount, preloadTasks.size());
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(settings.size());
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(threadCount, threadCount, 1L, TimeUnit.SECONDS, queue);
        try {
            futures = threadPool.invokeAll(preloadTasks);
        }
        catch (InterruptedException e) {
            LOGGER.error("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u9884\u70ed\uff1a\u7a0b\u5e8f\u542f\u52a8\u65f6\uff0c\u53c2\u6570\u7f13\u5b58\u9884\u70ed\u5931\u8d25", e);
            return;
        }
        StringBuilder sbr = new StringBuilder();
        for (int i = 0; i < futures.size(); ++i) {
            PreloadResult result;
            try {
                result = futures.get(i).get(30L, TimeUnit.MINUTES);
            }
            catch (InterruptedException | ExecutionException e) {
                LOGGER.error("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u9884\u70ed\uff1a\u7a0b\u5e8f\u542f\u52a8\u65f6\uff0c{}\u7684\u53c2\u6570\u7f13\u5b58\u9884\u70ed\u5931\u8d25", (Object)preloadTasks.get(i), (Object)e);
                sbr.append(String.format("%s\u7684\u53c2\u6570\u7f13\u5b58\u9884\u70ed\u5931\u8d25\n", preloadTasks.get(i)));
                continue;
            }
            catch (TimeoutException e) {
                LOGGER.error("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u9884\u70ed\uff1a\u7a0b\u5e8f\u542f\u52a8\u65f6\uff0c{}\u7684\u53c2\u6570\u7f13\u5b58\u9884\u70ed\u6267\u884c\u8d85\u65f6", (Object)preloadTasks.get(i), (Object)e);
                sbr.append(String.format("%s\u7684\u53c2\u6570\u7f13\u5b58\u9884\u70ed\u6267\u884c\u8d85\u65f6\n", preloadTasks.get(i)));
                continue;
            }
            sbr.append(result.getMessage()).append("\n");
        }
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u9884\u70ed\uff1a\u7a0b\u5e8f\u542f\u52a8\u65f6\u9884\u70ed\u7ed3\u675f\n{}\u603b\u8017\u65f6\uff1a{}ms", (Object)sbr, (Object)(System.currentTimeMillis() - startTime));
    }

    private void preload(Collection<NrParamCacheManagerService.PreloadSetting> settings) {
        if (CollectionUtils.isEmpty(settings)) {
            return;
        }
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u9884\u70ed\uff1a\u9884\u70ed\u5f00\u59cb");
        long startTime = System.currentTimeMillis();
        List<Callable<PreloadResult>> preloadTasks = this.initPreloadTask(settings);
        StringBuilder sbr = new StringBuilder();
        for (Callable<PreloadResult> task : preloadTasks) {
            try {
                PreloadResult result = task.call();
                sbr.append(result.getMessage()).append("\n");
            }
            catch (Exception exception) {}
        }
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u9884\u70ed\uff1a\u9884\u70ed\u7ed3\u675f\n{}\u603b\u8017\u65f6\uff1a{}ms", (Object)sbr, (Object)(System.currentTimeMillis() - startTime));
    }

    private class PreloadOrgDataTask
    extends PreloadResult
    implements Callable<PreloadResult> {
        private final String orgCode;
        private final Map<String, Date> versions;

        PreloadOrgDataTask(String orgCode, Map<String, Date> versions) {
            this.orgCode = orgCode;
            this.versions = versions;
        }

        public String getOrgCode() {
            return this.orgCode;
        }

        public Map<String, Date> getVersions() {
            return this.versions;
        }

        public String toString() {
            return "\u7ec4\u7ec7\u673a\u6784[" + this.orgCode + "]";
        }

        @Override
        public PreloadOrgDataTask call() throws Exception {
            long startTime = System.currentTimeMillis();
            try {
                OrgCategoryDTO orgCategoryDTO = new OrgCategoryDTO();
                orgCategoryDTO.setName(this.orgCode);
                for (Date date : this.versions.values()) {
                    orgCategoryDTO.setVersionDate(date);
                    NrParamCachePreloadService.this.orgDataService.initCache(orgCategoryDTO);
                }
            }
            catch (Exception e) {
                LOGGER.error("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u9884\u70ed\uff1a{}\u7684\u6570\u636e\u7f13\u5b58\u9884\u70ed\u5931\u8d25", (Object)this, (Object)e);
                super.setResult(false, String.format("%s\u7684\u6570\u636e\u7f13\u5b58\u9884\u70ed\u5931\u8d25\uff0c\u8017\u65f6\uff1a%dms", this, System.currentTimeMillis() - startTime));
                return this;
            }
            super.setResult(true, String.format("%s\u7684\u6570\u636e\u7f13\u5b58\u9884\u70ed\u6210\u529f\uff0c\u8017\u65f6\uff1a%dms", this, System.currentTimeMillis() - startTime));
            return this;
        }
    }

    private class PreloadParamTask
    extends PreloadResult
    implements Callable<PreloadResult> {
        private final String formSchemeKey;

        PreloadParamTask(String formSchemeKey) {
            this.formSchemeKey = formSchemeKey;
        }

        public String getFormSchemeKey() {
            return this.formSchemeKey;
        }

        public String toString() {
            return "\u62a5\u8868\u65b9\u6848[" + this.formSchemeKey + "]";
        }

        @Override
        public PreloadParamTask call() throws Exception {
            long startTime = System.currentTimeMillis();
            try {
                FormSchemeDefine formScheme = NrParamCachePreloadService.this.formParamCacheService.getFormScheme(this.formSchemeKey);
                List<FormulaSchemeDefine> formulaSchemes = NrParamCachePreloadService.this.formFormulaCacheService.getFormulaSchemesByFormScheme(formScheme.getKey());
                for (FormulaSchemeDefine formulaScheme : formulaSchemes) {
                    NrParamCachePreloadService.this.formFormulaCacheService.getFormulasInForm(formulaScheme.getKey(), null);
                }
            }
            catch (Exception e) {
                LOGGER.error("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u9884\u70ed\uff1a{}\u7684\u7f13\u5b58\u9884\u70ed\u5931\u8d25", (Object)this, (Object)e);
                super.setResult(false, String.format("%s\u7684\u7f13\u5b58\u9884\u70ed\u5931\u8d25\uff0c\u8017\u65f6\uff1a%dms", this, System.currentTimeMillis() - startTime));
                return this;
            }
            super.setResult(true, String.format("%s\u7684\u7f13\u5b58\u9884\u70ed\u6210\u529f\uff0c\u8017\u65f6\uff1a%dms", this, System.currentTimeMillis() - startTime));
            return this;
        }
    }

    private static class PreloadResult {
        private boolean success;
        private String message;

        private PreloadResult() {
        }

        public void setResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return this.success;
        }

        public String getMessage() {
            return this.message;
        }
    }
}

