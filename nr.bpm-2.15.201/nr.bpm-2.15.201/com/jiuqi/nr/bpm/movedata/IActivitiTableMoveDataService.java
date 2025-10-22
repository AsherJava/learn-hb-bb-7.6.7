/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.bpm.movedata;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.movedata.NrActvityGeneralByteArray;
import com.jiuqi.nr.bpm.movedata.NrDeadLetterJobEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrEventSubscrEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrExecutionEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrIdentityLinkEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrJobEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrSuspendedJobEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrTaskEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrTimerJobEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrVariableInstanceEntityImpl;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.List;

public interface IActivitiTableMoveDataService {
    public void importUploadStateNew(BusinessKey var1, UploadStateNew var2);

    public UploadStateNew exportUploadStateNew(DimensionValueSet var1, String var2, FormSchemeDefine var3);

    public void importUploadActionsNew(BusinessKey var1, List<UploadRecordNew> var2);

    public List<UploadRecordNew> exportUploadActionsNew(DimensionValueSet var1, String var2, FormSchemeDefine var3);

    public void importExecutionEntity(List<NrExecutionEntityImpl> var1);

    public List<NrExecutionEntityImpl> exportExecutionEntity(String var1);

    public void importTaskEntityImpl(List<NrTaskEntityImpl> var1);

    public List<NrTaskEntityImpl> exportTaskEntityImpl(String var1);

    public void importIdentityLinkEntityImpl(List<NrIdentityLinkEntityImpl> var1);

    public List<NrIdentityLinkEntityImpl> exportIdentityLinkEntityImpl(String var1);

    public void importVariableInstanceEntityImpl(List<NrVariableInstanceEntityImpl> var1);

    public List<NrVariableInstanceEntityImpl> exportVariableInstanceEntityImpl(String var1);

    public void importNrDeadLetterJobEntityImpl(List<NrDeadLetterJobEntityImpl> var1);

    public List<NrDeadLetterJobEntityImpl> exportNrDeadLetterJobEntityImpl(String var1);

    public void importNrJobEntityImpl(List<NrJobEntityImpl> var1);

    public List<NrJobEntityImpl> exportNrJobEntityImpl(String var1);

    public void importNrSuspendedJobEntityImpl(List<NrSuspendedJobEntityImpl> var1);

    public List<NrSuspendedJobEntityImpl> exportNrSuspendedJobEntityImpl(String var1);

    public void importNrTimerJobEntityImpl(List<NrTimerJobEntityImpl> var1);

    public List<NrTimerJobEntityImpl> exportNrTimerJobEntityImpl(String var1);

    public void importNrEventSubscrEntityImpl(List<NrEventSubscrEntityImpl> var1);

    public List<NrEventSubscrEntityImpl> exportNrEventSubscrEntityImpl(String var1);

    public void importNrActvityGeneralByteArray(List<NrActvityGeneralByteArray> var1);

    public List<NrActvityGeneralByteArray> exportNrActvityGeneralByteArray(String var1);

    public void deleteByProcessId(String var1, List<String> var2);
}

