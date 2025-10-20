/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Filter
 *  org.apache.shiro.web.filter.mgt.DefaultFilter
 *  org.apache.shiro.web.filter.mgt.DefaultFilterChainManager
 */
package com.jiuqi.va.shiro.config.optimize;

import com.jiuqi.va.shiro.config.optimize.MyAnonymousFilter;
import com.jiuqi.va.shiro.config.optimize.MyInvalidRequestFilter;
import javax.servlet.Filter;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;

public class MyDefaultFilterChainManager
extends DefaultFilterChainManager {
    protected void addDefaultFilters(boolean init) {
        for (DefaultFilter defaultFilter : DefaultFilter.values()) {
            if (DefaultFilter.invalidRequest.name().equals(defaultFilter.name())) {
                this.addFilter(defaultFilter.name(), (Filter)new MyInvalidRequestFilter(), init, false);
                continue;
            }
            if (DefaultFilter.anon.name().equals(defaultFilter.name())) {
                this.addFilter(defaultFilter.name(), (Filter)new MyAnonymousFilter(), init, false);
                continue;
            }
            this.addFilter(defaultFilter.name(), defaultFilter.newInstance(), init, false);
        }
    }
}

