/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletContextEvent
 */
package com.jiuqi.sf.module;

import com.jiuqi.nvwa.springadapterbusiness.util.SpringAdapterBusinessBeanUtils;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

@Deprecated
public interface ModuleInitiator {
    public void destroyed(ServletContextEvent var1);

    @Deprecated
    public void init(ServletContextEvent var1);

    default public void init() {
        ServletContext context = SpringAdapterBusinessBeanUtils.getBean(ServletContext.class);
        ServletContextEvent event = new ServletContextEvent(context);
        this.init(event);
    }
}

