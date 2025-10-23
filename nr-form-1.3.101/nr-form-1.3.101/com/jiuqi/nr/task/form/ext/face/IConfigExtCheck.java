/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.ext.face;

import com.jiuqi.nr.task.form.dto.CheckResult;
import com.jiuqi.nr.task.form.ext.dto.ConfigDTO;
import java.util.List;
import org.springframework.lang.NonNull;

public interface IConfigExtCheck {
    @NonNull
    public CheckResult doCheck(String var1, List<ConfigDTO> var2);
}

