/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.entity.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.entity.model.IEntityDefine;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface IEntityIOService {
    public void exportEntityData(String var1, OutputStream var2) throws JQException;

    public void importEntityData(InputStream var1) throws JQException;

    public void exportEntityDefine(List<String> var1, OutputStream var2) throws JQException;

    public List<IEntityDefine> importEntityDefine(InputStream var1) throws JQException;
}

