/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.datascheme.internal.service;

import com.jiuqi.np.common.exception.JQException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface IDataSchemeEntityIOService {
    public void correlateExport(List<String> var1, OutputStream var2) throws JQException;

    public void correlateImport(InputStream var1) throws JQException;
}

