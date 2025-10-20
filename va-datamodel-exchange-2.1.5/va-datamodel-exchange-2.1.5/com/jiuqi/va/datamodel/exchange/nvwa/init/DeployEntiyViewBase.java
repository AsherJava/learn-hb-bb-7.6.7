/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.datamodel.exchange.nvwa.init;

import com.jiuqi.va.datamodel.exchange.nvwa.impl.DataModelTableDefine;
import com.jiuqi.va.datamodel.exchange.nvwa.init.FTableDefineModel;
import java.util.List;

public abstract class DeployEntiyViewBase
implements FTableDefineModel {
    public abstract void publishTable(DataModelTableDefine var1) throws Exception;

    @Override
    public List<String> getTableNames() {
        return null;
    }

    @Override
    public boolean isReservedWord(String filedName) {
        return false;
    }

    @Override
    public List<String> depandentTable() {
        return null;
    }

    @Override
    public void init() {
    }
}

