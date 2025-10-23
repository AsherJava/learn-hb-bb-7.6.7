/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.OperateBusinessKeyCollection
 */
package com.jiuqi.nr.workflow2.events.helper;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.OperateBusinessKeyCollection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StepBusinessObjectCollection
extends OperateBusinessKeyCollection {
    private final Map<DimensionCombination, IEntityRow> dimensionCombinationToEntityRowMap;
    private final Map<IBusinessObject, WFMonitorCheckResult> businessObjectCheckStateMap = new HashMap<IBusinessObject, WFMonitorCheckResult>();

    public StepBusinessObjectCollection(IBusinessKeyCollection businessKeyCollection, Map<DimensionCombination, IEntityRow> dimensionCombinationToEntityRowMap) {
        super(businessKeyCollection);
        this.dimensionCombinationToEntityRowMap = dimensionCombinationToEntityRowMap;
    }

    public IEntityRow getMapEntityRow(IBusinessObject businessObject) {
        return this.dimensionCombinationToEntityRowMap.get(businessObject.getDimensions());
    }

    public void putBusinessObjectCheckState(IBusinessObject businessObject, WFMonitorCheckResult checkResult) {
        this.businessObjectCheckStateMap.put(businessObject, checkResult);
    }

    public IBusinessObjectCollection getUnCheckBusinessKeyCollection() {
        ArrayList<IBusinessObject> businessObjects = new ArrayList<IBusinessObject>();
        for (IBusinessObject businessObject : this.businessObjects) {
            WFMonitorCheckResult checkResult = this.businessObjectCheckStateMap.get(businessObject);
            if (checkResult != null && checkResult != WFMonitorCheckResult.UN_CHECK) continue;
            businessObjects.add(businessObject);
        }
        return new OperateBusinessKeyCollection(this.taskKey, businessObjects);
    }

    public boolean containsObject(IBusinessObject businessObject) {
        return this.businessObjects.contains(businessObject);
    }
}

