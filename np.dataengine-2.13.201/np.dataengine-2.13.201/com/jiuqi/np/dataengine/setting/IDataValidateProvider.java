/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.setting;

import com.jiuqi.np.dataengine.exception.DataValidateException;
import com.jiuqi.np.dataengine.intf.IDataRow;

public interface IDataValidateProvider {
    public boolean checkRow(IDataRow var1) throws DataValidateException;
}

