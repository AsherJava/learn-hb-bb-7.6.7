/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository;

import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import java.util.HashSet;
import java.util.Set;

public class BusinessObjectSet {
    private final Set<IBusinessObject> set = new HashSet<IBusinessObject>();

    public BusinessObjectSet(IBusinessObjectCollection bizKeyCollection) {
        for (IBusinessObject bizObject : bizKeyCollection) {
            this.set.add(bizObject);
        }
    }

    public boolean contains(IBusinessObject businessObject) {
        return this.set.contains(businessObject);
    }
}

