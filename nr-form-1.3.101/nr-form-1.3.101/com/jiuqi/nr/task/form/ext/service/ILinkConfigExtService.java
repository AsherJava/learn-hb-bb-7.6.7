/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.ext.service;

import com.jiuqi.nr.task.form.ext.dto.ConfigDTO;
import java.util.List;
import java.util.Map;

public interface ILinkConfigExtService {
    public Map<String, List<ConfigDTO>> listConfigs(String var1, List<String> var2);

    public void saveConfigs(String var1, List<ConfigDTO> var2);

    public List<ConfigDTO> listConfigs(String var1);
}

