/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.formtype.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.formtype.facade.FormTypeGroupDefine;
import java.util.List;

public interface IFormTypeGroupService {
    public FormTypeGroupDefine createFormTypeGroup();

    public void insertFormTypeGroup(FormTypeGroupDefine var1) throws JQException;

    public void updateFormTypeGroup(FormTypeGroupDefine var1) throws JQException;

    public void updateFormTypeGroup(FormTypeGroupDefine[] var1) throws JQException;

    public void deleteFormTypeGroup(String var1) throws JQException;

    public FormTypeGroupDefine queryById(String var1);

    public List<FormTypeGroupDefine> queryByParentId(String var1);

    public List<FormTypeGroupDefine> queryAll();
}

