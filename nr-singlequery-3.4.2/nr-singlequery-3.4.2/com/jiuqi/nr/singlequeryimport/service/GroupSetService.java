/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.designer.util.EntityDefineObject
 */
package com.jiuqi.nr.singlequeryimport.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.designer.util.EntityDefineObject;
import java.util.List;

public interface GroupSetService {
    public String getMnum(String var1);

    public List<EntityDefineObject> getLinks(String var1, String var2) throws JQException;
}

