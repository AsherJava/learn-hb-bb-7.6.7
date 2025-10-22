/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.asynctask;

import com.jiuqi.np.asynctask.AsyncTaskParam;
import com.jiuqi.np.asynctask.exception.ParameterConversionException;

public interface ParamConverter {
    public Object fromParam(AsyncTaskParam var1) throws ParameterConversionException;

    public Object fromParam(byte[] var1) throws ParameterConversionException;

    public AsyncTaskParam createParam(Object var1) throws ParameterConversionException;
}

