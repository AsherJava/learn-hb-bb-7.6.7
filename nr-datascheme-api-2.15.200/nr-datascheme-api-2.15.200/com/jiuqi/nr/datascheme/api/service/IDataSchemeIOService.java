/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.datascheme.api.service;

import com.jiuqi.np.common.exception.JQException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IDataSchemeIOService {
    public void exportDataScheme(String var1, OutputStream var2) throws JQException;

    default public String importDataScheme(String dataSchemeKey, InputStream inputStream) throws JQException {
        return this.importDataScheme(dataSchemeKey, inputStream, true);
    }

    public String importDataScheme(String var1, InputStream var2, boolean var3) throws JQException;
}

