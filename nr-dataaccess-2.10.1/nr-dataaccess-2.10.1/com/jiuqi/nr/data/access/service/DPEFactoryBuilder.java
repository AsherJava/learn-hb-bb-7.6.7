/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory
 */
package com.jiuqi.nr.data.access.service;

import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.service.impl.DataAccessEvaluatorFactory;
import com.jiuqi.nr.data.access.service.impl.IgnoreDataAccessEvaluatorFactory;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory;
import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.util.CollectionUtils;

public class DPEFactoryBuilder {
    private final IDataAccessServiceProvider dataAccessServiceProvider;
    private Set<String> ignoreItems = null;
    private boolean ignoreAll;

    public DPEFactoryBuilder(IDataAccessServiceProvider dataAccessServiceProvider) {
        this.dataAccessServiceProvider = dataAccessServiceProvider;
    }

    public boolean ignorePermission(String ignore) {
        if (this.ignoreItems == null) {
            this.ignoreItems = new LinkedHashSet<String>();
        }
        return this.ignoreItems.add(ignore);
    }

    public void ignoreAllPermission() {
        this.ignoreAll = true;
    }

    public void clearIgnorePermission() {
        this.ignoreAll = false;
        this.ignoreItems = null;
    }

    public DataPermissionEvaluatorFactory build() {
        if (this.ignoreAll || !CollectionUtils.isEmpty(this.ignoreItems)) {
            IgnoreDataAccessEvaluatorFactory evaluatorFactory = new IgnoreDataAccessEvaluatorFactory();
            evaluatorFactory.setDataAccessProvider(this.dataAccessServiceProvider);
            evaluatorFactory.setIgnoreAll(this.ignoreAll);
            evaluatorFactory.setIgnoreItems(this.ignoreItems);
            return evaluatorFactory;
        }
        return new DataAccessEvaluatorFactory(this.dataAccessServiceProvider);
    }
}

