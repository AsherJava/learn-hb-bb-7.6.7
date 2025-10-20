/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 */
package com.jiuqi.bde.common.init;

import javax.servlet.ServletContext;

public interface IBdeModuleItemInitiator {
    public String getName();

    public void init(ServletContext var1) throws Exception;

    public void initWhenStarted(ServletContext var1) throws Exception;

    public int getOrder();
}

