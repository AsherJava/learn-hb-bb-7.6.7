/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.model.Result
 */
package com.jiuqi.nr.annotation.service;

import com.jiuqi.np.core.model.Result;
import com.jiuqi.nr.annotation.input.ExpAnnotationFileParam;
import com.jiuqi.nr.annotation.input.ImpAnnotationFileParam;
import java.io.File;

public interface IAnnotationIOService {
    public Result<File> exportAnnotation(ExpAnnotationFileParam var1);

    public Result<Void> importAnnotation(File var1, ImpAnnotationFileParam var2);
}

