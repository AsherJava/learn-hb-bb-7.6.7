/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletContextEvent
 */
package com.jiuqi.sf.module;

import com.jiuqi.nvwa.springadapterbusiness.util.SpringAdapterBusinessBeanUtils;
import com.jiuqi.sf.module.ModuleInitiator;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

@Deprecated
public interface ModuleInitiatorWithEvent
extends ModuleInitiator {
    @Deprecated
    public void onServerStarted(ServletContextEvent var1);

    default public void onServerStarted() {
        ServletContext context = SpringAdapterBusinessBeanUtils.getBean(ServletContext.class);
        ServletContextEvent event = new ServletContextEvent(context);
        this.onServerStarted(event);
    }
}

