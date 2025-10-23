/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.ProcessDimensionCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class OperateBusinessKeyCollection
implements IBusinessKeyCollection,
IBusinessObjectCollection {
    protected final String taskKey;
    protected final List<IBusinessObject> businessObjects;
    protected DimensionCollection dimensionCollection;

    public OperateBusinessKeyCollection(String taskKey, List<IBusinessObject> businessObjects) {
        this.taskKey = taskKey;
        this.businessObjects = businessObjects;
        this.dimensionCollection = new ProcessDimensionCollection(businessObjects.stream().map(IBusinessObject::getDimensions).collect(Collectors.toList()));
    }

    public OperateBusinessKeyCollection(IBusinessKey businessKey) {
        this(businessKey.getTask(), new ArrayList<IBusinessObject>());
        this.businessObjects.add(businessKey.getBusinessObject());
        this.dimensionCollection = new ProcessDimensionCollection(businessKey.getBusinessObject().getDimensions());
    }

    public OperateBusinessKeyCollection(IBusinessKeyCollection businessKeyCollection) {
        this(businessKeyCollection.getTask(), new ArrayList<IBusinessObject>());
        ArrayList<DimensionCombination> combinations = new ArrayList<DimensionCombination>();
        IBusinessObjectCollection businessObjectCollection = businessKeyCollection.getBusinessObjects();
        for (IBusinessObject businessObject : businessObjectCollection) {
            this.businessObjects.add(businessObject);
            combinations.add(businessObject.getDimensions());
        }
        this.dimensionCollection = new ProcessDimensionCollection(combinations);
    }

    public String getTask() {
        return this.taskKey;
    }

    public IBusinessObjectCollection getBusinessObjects() {
        return this;
    }

    public int size() {
        return this.businessObjects.size();
    }

    public DimensionCollection getDimensions() {
        return this.dimensionCollection;
    }

    public Collection<String> getDeimensionObject(DimensionCombination dimension) {
        return Collections.emptyList();
    }

    @NotNull
    public Iterator<IBusinessObject> iterator() {
        return this.businessObjects.iterator();
    }

    public void appendBusinessObject(IBusinessObject businessObject) {
        this.businessObjects.add(businessObject);
    }
}

