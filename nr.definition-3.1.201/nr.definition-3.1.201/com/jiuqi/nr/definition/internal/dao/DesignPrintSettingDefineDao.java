/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.nr.definition.internal.dao.AbstractPrintSettingDefineDao;
import com.jiuqi.nr.definition.internal.impl.DesignPrintSettingDefineImpl;
import org.springframework.stereotype.Repository;

@Repository
public class DesignPrintSettingDefineDao
extends AbstractPrintSettingDefineDao<DesignPrintSettingDefineImpl> {
    @Override
    public Class<DesignPrintSettingDefineImpl> getClz() {
        return DesignPrintSettingDefineImpl.class;
    }
}

