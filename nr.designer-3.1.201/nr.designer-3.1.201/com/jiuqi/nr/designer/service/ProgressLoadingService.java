/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.internal.impl.ProgressLoadingImpl
 */
package com.jiuqi.nr.designer.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.internal.impl.ProgressLoadingImpl;

public interface ProgressLoadingService {
    public void publishSuccess(String var1, String var2) throws JQException;

    public void publishFail(String var1, String var2, String var3, String var4) throws JQException;

    public void publishWarring(String var1, String var2, String var3, String var4) throws JQException;

    public ProgressLoadingImpl queryProgressLoading(String var1) throws Exception;

    public void deleteProgressLoading(String var1) throws Exception;
}

