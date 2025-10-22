/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 */
package com.jiuqi.nr.tag.manager.dao.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;

public class TagEntityIDHelper {
    private TagEntityIDHelper() {
    }

    public static String getEntityId(String oriEntityId) {
        DsContext dsContext = DsContextHolder.getDsContext();
        if (StringUtils.isNotEmpty((String)dsContext.getContextEntityId())) {
            return dsContext.getContextEntityId();
        }
        return oriEntityId;
    }
}

