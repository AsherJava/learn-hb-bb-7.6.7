/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 */
package com.jiuqi.nvwa.sf.models;

import com.jiuqi.nvwa.springadapterbusiness.util.SpringAdapterBusinessBeanUtils;
import javax.servlet.ServletContext;

@Deprecated
public interface ModuleInitiator {
    @Deprecated
    public void init(ServletContext var1) throws Exception;

    default public void init() throws Exception {
        this.init(SpringAdapterBusinessBeanUtils.getBean(ServletContext.class));
    }

    @Deprecated
    public void initWhenStarted(ServletContext var1) throws Exception;

    default public void initWhenStarted() throws Exception {
        this.initWhenStarted(SpringAdapterBusinessBeanUtils.getBean(ServletContext.class));
    }
}

