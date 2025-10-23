/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject;
import com.jiuqi.nr.workflow2.engine.dflt.utils.DimensionUtils;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class UnitBusinessObjectCollection
implements IBusinessObjectCollection {
    private static final Collection<String> OBJS = Arrays.asList("_NULL_");
    private final DimensionCollection dimensions;
    private final List<IBusinessObject> businessObjects;

    private UnitBusinessObjectCollection(DimensionCollection dimensions, List<IBusinessObject> businessObjects) {
        this.dimensions = dimensions;
        this.businessObjects = businessObjects;
    }

    public static UnitBusinessObjectCollection buildFormObjectCollection(Collection<DimensionCombination> dimensions) {
        List<IBusinessObject> businessObjects = dimensions.stream().map(t -> new FormObject(t, "_NULL_")).collect(Collectors.toList());
        DimensionCollection dimensionCollection = DimensionUtils.combineDimensions(dimensions);
        return new UnitBusinessObjectCollection(dimensionCollection, businessObjects);
    }

    public static UnitBusinessObjectCollection buildFormGroupObjectCollection(Collection<DimensionCombination> dimensions) {
        List<IBusinessObject> businessObjects = dimensions.stream().map(t -> new FormGroupObject(t, "_NULL_")).collect(Collectors.toList());
        DimensionCollection dimensionCollection = DimensionUtils.combineDimensions(dimensions);
        return new UnitBusinessObjectCollection(dimensionCollection, businessObjects);
    }

    public Iterator<IBusinessObject> iterator() {
        return this.businessObjects.iterator();
    }

    public int size() {
        return this.businessObjects.size();
    }

    public DimensionCollection getDimensions() {
        return this.dimensions;
    }

    public Collection<String> getDeimensionObject(DimensionCombination dimension) {
        return OBJS;
    }
}

