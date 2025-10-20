/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.gcreport.nr.impl.uploadstate.util.intern;

import com.jiuqi.gcreport.nr.impl.uploadstate.util.intern.FormLockedState;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.intern.FormUploadState;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nr.definition.facade.FormDefine;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class AbstractFormState {
    public UploadState queryUploadState(DimensionParamsVO param, String orgId, String formId) {
        return FormUploadState.queryUploadState(param, orgId, formId);
    }

    public List<UploadState> queryUploadState(DimensionParamsVO param, String orgId, List<String> formIds) {
        return FormUploadState.queryUploadState(param, orgId, formIds);
    }

    public boolean isFormLocked(DimensionParamsVO param, String orgId, String formId) {
        return FormLockedState.isFormLocked(param, orgId, formId);
    }

    public boolean isFormLocked(DataEntryContext dataEntryContext, String formId) {
        return FormLockedState.isFormLocked(dataEntryContext, formId);
    }

    public String filterLockedForm(Collection<FormDefine> formDefines, String taskId, String schemeId, Map<String, DimensionValue> dimensionSetMap) {
        return FormLockedState.filterLockedForm(formDefines, taskId, schemeId, dimensionSetMap);
    }
}

