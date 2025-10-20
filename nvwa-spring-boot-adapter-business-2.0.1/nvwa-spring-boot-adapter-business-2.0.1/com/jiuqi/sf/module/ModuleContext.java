/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 */
package com.jiuqi.sf.module;

import com.jiuqi.nvwa.springadapterbusiness.util.SpringAdapterBusinessBeanUtils;
import javax.servlet.ServletContext;

@Deprecated
public class ModuleContext {
    private ServletContext context;

    @Deprecated
    public ModuleContext(ServletContext context) {
        this.context = context;
    }

    public ModuleContext() {
        this.context = SpringAdapterBusinessBeanUtils.getBean(ServletContext.class);
    }

    public ServletContext getServletContext() {
        return this.context;
    }
}

