/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.nr.io.service;

import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.io.tsd.dto.AnalysisParam;
import com.jiuqi.nr.io.tsd.dto.Form;
import com.jiuqi.nr.io.tsd.dto.PackageData;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;

public interface ParamsMappingService {
    public ParamsMapping getParamsMapping(@NotNull String var1);

    public ParamsMapping getParamsMapping(AnalysisParam var1, @NotNull PackageData var2);

    public Map<String, String> getOrgMapping(@NotNull String var1);

    public TaskDefine resolveTaskDefine(PackageData var1);

    public FormSchemeDefine resolveFormSchemeDefine(PackageData var1, String var2);

    public FormDefine resolveFormDefine(Form var1, List<FormDefine> var2);
}

