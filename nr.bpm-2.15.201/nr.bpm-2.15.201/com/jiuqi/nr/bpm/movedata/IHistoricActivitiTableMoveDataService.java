/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata;

import com.jiuqi.nr.bpm.movedata.NrHistoricActivityInstanceEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrHistoricAttachmentImpl;
import com.jiuqi.nr.bpm.movedata.NrHistoricCommentImpl;
import com.jiuqi.nr.bpm.movedata.NrHistoricDetailImpl;
import com.jiuqi.nr.bpm.movedata.NrHistoricIdentityLinkEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrHistoricProcessInstanceEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrHistoricTaskInstanceEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrHistoricVariableInstanceEntityImpl;
import java.util.List;

public interface IHistoricActivitiTableMoveDataService {
    public void importHistoricActivityInstanceEntityImpl(List<NrHistoricActivityInstanceEntityImpl> var1, String var2);

    public List<NrHistoricActivityInstanceEntityImpl> exportHistoricActivityInstanceEntityImpl(String var1);

    public void importHistoricIdentityLinkEntityImpl(List<NrHistoricIdentityLinkEntityImpl> var1, String var2);

    public List<NrHistoricIdentityLinkEntityImpl> exportHistoricIdentityLinkEntityImpl(String var1);

    public void importHistoricProcessInstanceEntityImpl(List<NrHistoricProcessInstanceEntityImpl> var1, String var2);

    public List<NrHistoricProcessInstanceEntityImpl> exportHistoricProcessInstanceEntityImpl(String var1);

    public void importHistoricTaskInstanceEntityImpl(List<NrHistoricTaskInstanceEntityImpl> var1, String var2);

    public List<NrHistoricTaskInstanceEntityImpl> exportHistoricTaskInstanceEntityImpl(String var1);

    public void importHistoricVariableInstanceEntityImpl(List<NrHistoricVariableInstanceEntityImpl> var1, String var2);

    public List<NrHistoricVariableInstanceEntityImpl> exportHistoricVariableInstanceEntityImpl(String var1);

    public void importHistoricAttachmentEntityImpl(List<NrHistoricAttachmentImpl> var1, String var2);

    public List<NrHistoricAttachmentImpl> exportHistoricAttachmentEntityImpl(String var1);

    public void importHistoricCommentEntityImpl(List<NrHistoricCommentImpl> var1, String var2);

    public List<NrHistoricCommentImpl> exportHistoricCommentEntityImpl(String var1);

    public void importHistoricDetailEntityImpl(List<NrHistoricDetailImpl> var1, String var2);

    public List<NrHistoricDetailImpl> exportHistoricDetailEntityImpl(String var1);
}

