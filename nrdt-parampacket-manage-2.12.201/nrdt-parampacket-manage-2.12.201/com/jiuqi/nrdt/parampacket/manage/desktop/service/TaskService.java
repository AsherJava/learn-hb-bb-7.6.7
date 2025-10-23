/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nrdt.parampacket.manage.desktop.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.List;

public interface TaskService {
    public List<FormSchemeDefine> queryFormSchemeInTask(String var1) throws JQException;
}

