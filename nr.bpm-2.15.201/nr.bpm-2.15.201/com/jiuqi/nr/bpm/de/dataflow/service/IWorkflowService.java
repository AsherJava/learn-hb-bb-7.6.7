/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.bpm.de.dataflow.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.List;
import java.util.Map;

public interface IWorkflowService {
    public boolean isActionDone(FormSchemeDefine var1, UploadState var2);

    public boolean isActionDone(FormSchemeDefine var1, DimensionValueSet var2, String var3, String var4, UploadState var5);

    public Map<DimensionValueSet, Boolean> isActionDone(FormSchemeDefine var1, DimensionValueSet var2, List<String> var3, List<String> var4, UploadState var5);

    public List<UploadStateNew> getDataByActionCode(FormSchemeDefine var1, List<UploadState> var2);

    public List<UploadStateNew> getDataByActionCode(FormSchemeDefine var1, DimensionValueSet var2, List<UploadState> var3);

    public List<UploadStateNew> getDataByActionCode(FormSchemeDefine var1, DimensionValueSet var2, List<String> var3, List<String> var4, List<UploadState> var5);
}

