/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.ext.service;

import com.jiuqi.nr.task.form.ext.dto.ConfigDTO;
import java.util.List;

public interface IComponentConfigExtService {
    public List<ConfigDTO> getDefaultConfigs();

    public List<ConfigDTO> getConfigs(String var1);

    public void saveConfigs(String var1, List<ConfigDTO> var2);
}

