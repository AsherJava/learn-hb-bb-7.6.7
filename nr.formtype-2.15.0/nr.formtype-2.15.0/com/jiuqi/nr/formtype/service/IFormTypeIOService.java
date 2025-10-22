/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.formtype.service;

import com.jiuqi.np.common.exception.JQException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IFormTypeIOService {
    public boolean exportFormTypeByEntity(OutputStream var1, String ... var2) throws JQException;

    public void exportFormType(OutputStream var1, String ... var2) throws JQException;

    public void importFormType(InputStream var1) throws JQException;
}

