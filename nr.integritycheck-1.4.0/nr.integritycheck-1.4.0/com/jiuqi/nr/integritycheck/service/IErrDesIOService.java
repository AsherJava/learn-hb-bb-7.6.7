/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.model.Result
 */
package com.jiuqi.nr.integritycheck.service;

import com.jiuqi.np.core.model.Result;
import com.jiuqi.nr.integritycheck.common.ExpErrDesFileParam;
import com.jiuqi.nr.integritycheck.common.ExpErrDesFileParam2;
import com.jiuqi.nr.integritycheck.common.ImpErrDesFileParam;
import com.jiuqi.nr.integritycheck.common.ImpErrDesFileParam2;
import java.io.File;

public interface IErrDesIOService {
    public Result<File> exportErrorDes(ExpErrDesFileParam var1);

    public Result<String> exportErrorDes(ExpErrDesFileParam2 var1);

    public Result<Void> importErrorDes(File var1, ImpErrDesFileParam var2);

    public Result<Void> importErrorDes(String var1, ImpErrDesFileParam2 var2);
}

