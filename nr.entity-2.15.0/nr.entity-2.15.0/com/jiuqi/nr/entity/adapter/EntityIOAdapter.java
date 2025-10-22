/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.entity.adapter;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.entity.model.IEntityDefine;
import java.io.InputStream;
import java.io.OutputStream;

public interface EntityIOAdapter {
    public void importEntityData(String var1, InputStream var2) throws JQException;

    public void exportEntityData(String var1, OutputStream var2) throws JQException;

    public void exportEntityDefine(String var1, OutputStream var2) throws JQException;

    public IEntityDefine importEntityDefine(InputStream var1) throws JQException;
}

