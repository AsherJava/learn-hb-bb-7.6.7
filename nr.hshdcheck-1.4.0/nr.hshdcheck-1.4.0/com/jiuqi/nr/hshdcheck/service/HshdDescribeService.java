/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.model.Result
 */
package com.jiuqi.nr.hshdcheck.service;

import com.jiuqi.np.core.model.Result;
import com.jiuqi.nr.hshdcheck.DescribeParam;
import com.jiuqi.nr.hshdcheck.DescribeQueryParam;
import java.io.File;

public interface HshdDescribeService {
    public Result<File> exportDescribe(DescribeQueryParam var1);

    public Result<Void> importDescribe(DescribeParam var1, File var2);
}

