/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.formulamapping.facade.TreeObj
 */
package com.jiuqi.nr.formulamapping.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.formulamapping.facade.TreeObj;
import java.util.List;

public interface InitTreeSevice {
    public List<TreeObj> initFormTree(String var1, String var2) throws JQException;
}

