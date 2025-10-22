/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.extend;

import com.jiuqi.nr.data.excel.extend.ISheetNameProvider;
import com.jiuqi.nr.data.excel.extend.param.SheetNameEnv;

public interface ISheetNameProviderFactory {
    public ISheetNameProvider getSheetNameProvider(SheetNameEnv var1);
}

