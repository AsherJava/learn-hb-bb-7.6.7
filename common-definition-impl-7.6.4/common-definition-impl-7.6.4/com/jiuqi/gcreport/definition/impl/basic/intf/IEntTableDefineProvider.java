/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.basic.intf;

import com.jiuqi.gcreport.definition.impl.basic.base.provider.impl.EntTableDefine;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;

public interface IEntTableDefineProvider {
    public EntTableDefine getTableDefine(String var1);

    public EntTableDefine getTableDefineByType(Class<? extends BaseEntity> var1);
}

