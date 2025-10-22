/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.state.common.StateConst
 *  com.jiuqi.nr.state.pojo.StateEntites
 *  com.jiuqi.nr.state.pojo.StatePojo
 *  com.jiuqi.nr.state.service.IStateSevice
 *  com.jiuqi.nr.unit.treecommon.utils.NRSystemUtils
 *  javax.annotation.Resource
 */
package com.jiuiqi.nr.unit.treebase.node.state;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.state.common.StateConst;
import com.jiuqi.nr.state.pojo.StateEntites;
import com.jiuqi.nr.state.pojo.StatePojo;
import com.jiuqi.nr.state.service.IStateSevice;
import com.jiuqi.nr.unit.treecommon.utils.NRSystemUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class TerminalStateManagement {
    @Resource
    private IStateSevice stateService;
    @Resource
    private IUnitTreeContextWrapper contextWrapper;
    @Resource
    private UnitTreeEntityDataQuery entityRowQuery;

    public boolean saveOrUpdateData(IUnitTreeContext context, String nodeKey) {
        StateConst terminalState = this.getTerminalState(context, nodeKey);
        StateConst stateConst = terminalState = terminalState == null ? StateConst.STARTFILL : terminalState;
        if (terminalState.getValue() == StateConst.STARTFILL.getValue()) {
            terminalState = StateConst.ENDFILL;
        } else if (terminalState.getValue() == StateConst.ENDFILL.getValue()) {
            terminalState = StateConst.STARTFILL;
        }
        IEntityTable dataTable = this.entityRowQuery.makeRangeFullTreeData(context, Collections.singletonList(nodeKey));
        List allChildRows = dataTable.getAllChildRows(nodeKey);
        ArrayList<String> allChildRowKeys = new ArrayList<String>(0);
        allChildRowKeys.add(nodeKey);
        allChildRows.forEach(row -> allChildRowKeys.add(row.getEntityKeyData()));
        StatePojo statePojo = new StatePojo();
        statePojo.setDims(this.buildDimValueSet(context, allChildRowKeys));
        statePojo.setFormSchemeKey(context.getFormScheme().getKey());
        statePojo.setUserId(NRSystemUtils.getCurrentUserId());
        statePojo.setState(terminalState.getValue());
        return this.stateService.saveOrUpdateData(statePojo);
    }

    public StateConst getTerminalState(IUnitTreeContext context, String nodeKey) {
        DimensionValueSet dimValueSet = this.buildDimValueSet(context, nodeKey);
        StateEntites stateEntites = this.getStateEntites(context.getFormScheme());
        stateEntites.setDims(dimValueSet);
        Map stateMap = this.stateService.getStateInfo(stateEntites);
        return (StateConst)stateMap.get(dimValueSet);
    }

    public Map<DimensionValueSet, StateConst> batchTerminalState(IUnitTreeContext context, List<String> entityDataKeys) {
        StateEntites stateEntites = this.getStateEntites(context.getFormScheme());
        stateEntites.setDims(this.buildDimValueSet(context, entityDataKeys));
        return this.stateService.getStateInfo(stateEntites);
    }

    public DimensionValueSet buildDimValueSet(IUnitTreeContext context) {
        return this.contextWrapper.buildDimensionValueSet(context);
    }

    private DimensionValueSet buildDimValueSet(IUnitTreeContext context, String nodeKey) {
        DimensionValueSet dimValueSet = this.buildDimValueSet(context);
        dimValueSet.setValue(context.getEntityDefine().getDimensionName(), (Object)nodeKey);
        return dimValueSet;
    }

    private DimensionValueSet buildDimValueSet(IUnitTreeContext context, List<String> entityDataKeys) {
        DimensionValueSet dimValueSet = this.buildDimValueSet(context);
        dimValueSet.setValue(context.getEntityDefine().getDimensionName(), entityDataKeys);
        return dimValueSet;
    }

    private StateEntites getStateEntites(FormSchemeDefine formScheme) {
        StateEntites stateEntites = new StateEntites();
        stateEntites.setFormSchemeKey(formScheme.getKey());
        stateEntites.setUserId(NRSystemUtils.getCurrentUserId());
        return stateEntites;
    }
}

