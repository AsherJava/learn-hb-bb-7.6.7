/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.ext.base;

import com.jiuqi.nr.task.form.ext.dto.ConfigDTO;
import java.util.List;

public interface IBaseData {
    public ConfigDTO getDefaultConfig();

    public ConfigDTO getConfig(String var1);

    public void saveConfigs(String var1, List<ConfigDTO> var2);
}

