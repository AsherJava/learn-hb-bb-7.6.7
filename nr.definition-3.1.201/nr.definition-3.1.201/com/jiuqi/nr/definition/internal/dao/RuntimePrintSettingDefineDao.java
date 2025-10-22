/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.nr.definition.internal.dao.AbstractPrintSettingDefineDao;
import com.jiuqi.nr.definition.internal.impl.RuntimePrintSettingDefineImpl;
import org.springframework.stereotype.Repository;

@Repository
public class RuntimePrintSettingDefineDao
extends AbstractPrintSettingDefineDao<RuntimePrintSettingDefineImpl> {
    @Override
    public Class<RuntimePrintSettingDefineImpl> getClz() {
        return RuntimePrintSettingDefineImpl.class;
    }
}

