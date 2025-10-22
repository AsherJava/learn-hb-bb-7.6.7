/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata.idtocode;

import com.jiuqi.nr.bpm.movedata.NrExecutionEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrHistoricProcessInstanceEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrHistoricVariableInstanceEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrVariableInstanceEntityImpl;
import java.util.List;

public interface IBpmIdToCodeTransferDao {
    public List<NrExecutionEntityImpl> queryExecutionByBuinessKey();

    public List<NrHistoricProcessInstanceEntityImpl> queryHistoricProcessInstanceByBuinessKey();

    public List<NrVariableInstanceEntityImpl> queryNrVariableInstanceEntityImpl();

    public List<NrHistoricVariableInstanceEntityImpl> queryNrHistoricVariableInstanceEntityImpl();

    public boolean updateExecutionById(List<NrExecutionEntityImpl> var1);

    public boolean updateHistoricProcessInstanceById(List<NrHistoricProcessInstanceEntityImpl> var1);

    public boolean updateNrVariableInstanceEntityImpl(List<NrVariableInstanceEntityImpl> var1);

    public boolean updateNrHistoricVariableInstanceEntityImpl(List<NrHistoricVariableInstanceEntityImpl> var1);
}

