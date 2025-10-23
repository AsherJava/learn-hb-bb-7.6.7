/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.workflow2.engine.core.process.runtime;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public interface IBusinessObjectCollection
extends Iterable<IBusinessObject> {
    public static final IBusinessObjectCollection EMPTY = new IBusinessObjectCollection(){

        @Override
        public Iterator<IBusinessObject> iterator() {
            return Collections.emptyIterator();
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public DimensionCollection getDimensions() {
            return new DimensionCollectionBuilder().getCollection();
        }

        @Override
        public Collection<String> getDeimensionObject(DimensionCombination dimension) {
            return Collections.emptyList();
        }
    };

    public int size();

    public DimensionCollection getDimensions();

    public Collection<String> getDeimensionObject(DimensionCombination var1);
}

