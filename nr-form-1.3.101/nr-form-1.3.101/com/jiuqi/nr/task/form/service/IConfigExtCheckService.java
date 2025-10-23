/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.service;

import com.jiuqi.nr.task.form.dto.CheckResult;
import com.jiuqi.nr.task.form.ext.dto.ConfigDTO;
import java.util.List;

public interface IConfigExtCheckService {
    public CheckResult checkLinkConfigs(String var1, List<ConfigDTO> var2);

    public CheckResult checkRegionConfigs(String var1, List<ConfigDTO> var2);

    public CheckResult checkFieldConfigs(String var1, List<ConfigDTO> var2);

    public CheckResult checkComponentConfigs(String var1, List<ConfigDTO> var2);
}

