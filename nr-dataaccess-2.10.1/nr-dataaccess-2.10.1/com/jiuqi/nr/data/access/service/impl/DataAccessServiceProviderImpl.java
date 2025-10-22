/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemePeriodService
 */
package com.jiuqi.nr.data.access.service.impl;

import com.jiuqi.nr.data.access.service.IDataAccessExtraResultService;
import com.jiuqi.nr.data.access.service.IDataAccessFormService;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.service.TaskDimensionFilterFactory;
import com.jiuqi.nr.data.access.service.impl.AccessCacheManager;
import com.jiuqi.nr.data.access.service.impl.AccessItemServiceCollector;
import com.jiuqi.nr.data.access.service.impl.DataAccessServiceImpl;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemePeriodService;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataAccessServiceProviderImpl
implements IDataAccessServiceProvider {
    @Autowired
    private IDataAccessFormService dataAccessFormService;
    @Autowired
    private List<IDataAccessExtraResultService> accessExtraResultController;
    @Autowired
    private AccessItemServiceCollector accessItemCollector;
    @Autowired
    private TaskDimensionFilterFactory filterFactory;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeFormSchemePeriodService periodService;

    @Override
    public IDataAccessService getDataAccessService(String taskKey, String formSchemeKey) {
        FormSchemeDefine formScheme;
        if (taskKey == null && (formScheme = this.runTimeViewController.getFormScheme(formSchemeKey)) != null) {
            taskKey = formScheme.getTaskKey();
        }
        AccessCacheManager accessCacheManager = new AccessCacheManager(this.accessExtraResultController, this.accessItemCollector, this.runTimeViewController, this.filterFactory, this.periodService);
        return new DataAccessServiceImpl(taskKey, formSchemeKey, accessCacheManager);
    }

    @Override
    public IDataAccessService getDataAccessService(String taskKey, String formSchemeKey, Set<String> ignoreItems) {
        FormSchemeDefine formScheme;
        if (taskKey == null && (formScheme = this.runTimeViewController.getFormScheme(formSchemeKey)) != null) {
            taskKey = formScheme.getTaskKey();
        }
        AccessCacheManager accessCacheManager = new AccessCacheManager(this.accessExtraResultController, this.accessItemCollector, this.runTimeViewController, this.filterFactory, ignoreItems, this.periodService);
        return new DataAccessServiceImpl(taskKey, formSchemeKey, accessCacheManager);
    }

    @Override
    public IDataAccessService getZBDataAccessService() {
        AccessCacheManager accessCacheManager = new AccessCacheManager(this.accessExtraResultController, this.accessItemCollector, this.runTimeViewController, this.filterFactory, this.periodService);
        return new DataAccessServiceImpl(null, null, accessCacheManager);
    }

    @Override
    public IDataAccessService getZBDataAccessService(String taskKey) {
        AccessCacheManager accessCacheManager = new AccessCacheManager(this.accessExtraResultController, this.accessItemCollector, this.runTimeViewController, this.filterFactory, Collections.singletonList(taskKey), null, this.periodService);
        return new DataAccessServiceImpl(null, null, accessCacheManager);
    }

    @Override
    public IDataAccessService getZBDataAccessService(String taskKey, Set<String> ignoreItems) {
        AccessCacheManager accessCacheManager = new AccessCacheManager(this.accessExtraResultController, this.accessItemCollector, this.runTimeViewController, this.filterFactory, Collections.singletonList(taskKey), ignoreItems, this.periodService);
        return new DataAccessServiceImpl(null, null, accessCacheManager);
    }

    @Override
    public IDataAccessService getZBDataAccessService(List<String> taskKeys) {
        AccessCacheManager accessCacheManager = new AccessCacheManager(this.accessExtraResultController, this.accessItemCollector, this.runTimeViewController, this.filterFactory, taskKeys, null, this.periodService);
        return new DataAccessServiceImpl(null, null, accessCacheManager);
    }

    @Override
    public IDataAccessService getZBDataAccessService(List<String> taskKeys, Set<String> ignoreItems) {
        AccessCacheManager accessCacheManager = new AccessCacheManager(this.accessExtraResultController, this.accessItemCollector, this.runTimeViewController, this.filterFactory, taskKeys, ignoreItems, this.periodService);
        return new DataAccessServiceImpl(null, null, accessCacheManager);
    }

    @Override
    public IDataAccessFormService getDataAccessFormService() {
        return this.dataAccessFormService;
    }
}

