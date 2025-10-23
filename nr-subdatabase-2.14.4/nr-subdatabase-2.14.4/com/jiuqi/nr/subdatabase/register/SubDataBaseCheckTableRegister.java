/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.logic.internal.service.impl.CheckSchemeRecordServiceImpl
 *  com.jiuqi.nr.data.logic.internal.util.CheckTableNameUtil
 */
package com.jiuqi.nr.subdatabase.register;

import com.jiuqi.nr.data.logic.internal.service.impl.CheckSchemeRecordServiceImpl;
import com.jiuqi.nr.data.logic.internal.util.CheckTableNameUtil;
import com.jiuqi.nr.subdatabase.controller.SubDataBaseInfoProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubDataBaseCheckTableRegister
extends CheckSchemeRecordServiceImpl {
    @Autowired
    private SubDataBaseInfoProvider subDataBaseInfoProvider;

    public String getTableName() {
        String originName = CheckTableNameUtil.getRWIFTableName();
        if (this.subDataBaseInfoProvider.getCurDataBase() != null) {
            return this.subDataBaseInfoProvider.getCurDataBase().getCode() + originName;
        }
        return originName;
    }
}

