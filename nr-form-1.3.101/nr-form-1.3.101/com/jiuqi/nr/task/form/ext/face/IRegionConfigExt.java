/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.ext.face;

import com.jiuqi.nr.task.form.ext.face.IBaseConfigExt;
import com.jiuqi.nr.task.form.ext.face.IConfigExtCheck;
import com.jiuqi.nr.task.form.service.IReverseModelingExecutor;
import java.util.List;

public interface IRegionConfigExt
extends IConfigExtCheck,
IBaseConfigExt {
    default public List<IReverseModelingExecutor> getReverseModelingExecutor() {
        return null;
    }
}

