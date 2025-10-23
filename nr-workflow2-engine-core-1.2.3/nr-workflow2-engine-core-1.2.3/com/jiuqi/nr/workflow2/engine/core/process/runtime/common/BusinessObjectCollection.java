/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.workflow2.engine.core.process.runtime.common;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.DimensionObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.IDimensionObjectMapping;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class BusinessObjectCollection
implements IBusinessObjectCollection {
    private final DimensionCollection dimensionCollection;
    private final IDimensionObjectMapping dimensionFormMapping;
    private final IDimensionObjectMapping dimensionFormGroupMapping;

    private BusinessObjectCollection(DimensionCollection dimensionCollection, IDimensionObjectMapping dimensionFormMapping, IDimensionObjectMapping dimensionFormGroupMapping) {
        this.dimensionCollection = dimensionCollection;
        this.dimensionFormMapping = dimensionFormMapping;
        this.dimensionFormGroupMapping = dimensionFormGroupMapping;
    }

    public static BusinessObjectCollection newDimensionObjectCollection(DimensionCollection dimensionCollection) {
        return new BusinessObjectCollection(dimensionCollection, null, null);
    }

    public static BusinessObjectCollection newFormObjectCollection(DimensionCollection dimensionCollection, IDimensionObjectMapping dimensionFormMapping) {
        return new BusinessObjectCollection(dimensionCollection, dimensionFormMapping, null);
    }

    public static BusinessObjectCollection newFormGroupObjectCollection(DimensionCollection dimensionCollection, IDimensionObjectMapping dimensionFormGroupMapping) {
        return new BusinessObjectCollection(dimensionCollection, null, dimensionFormGroupMapping);
    }

    @Override
    public int size() {
        return this.dimensionCollection.getDimensionCombinations().size();
    }

    @Override
    public Iterator<IBusinessObject> iterator() {
        if (this.dimensionFormMapping != null) {
            return new FormObjectIterator();
        }
        if (this.dimensionFormGroupMapping != null) {
            return new FormGroupObjectIterator();
        }
        return new DimensionObjectIterator();
    }

    @Override
    public DimensionCollection getDimensions() {
        return this.dimensionCollection;
    }

    @Override
    public Collection<String> getDeimensionObject(DimensionCombination dimension) {
        if (this.dimensionFormMapping != null) {
            return this.dimensionFormMapping.getObject(dimension);
        }
        if (this.dimensionFormGroupMapping != null) {
            return this.dimensionFormGroupMapping.getObject(dimension);
        }
        return Collections.emptyList();
    }

    private class FormGroupObjectIterator
    implements Iterator<IBusinessObject> {
        private final Iterator<DimensionCombination> dimensionIterator;
        private DimensionCombination dimension;
        private Iterator<String> objectIterator;

        private FormGroupObjectIterator() {
            this.dimensionIterator = BusinessObjectCollection.this.dimensionCollection.getDimensionCombinations().iterator();
        }

        @Override
        public boolean hasNext() {
            while ((this.objectIterator == null || !this.objectIterator.hasNext()) && this.dimensionIterator.hasNext()) {
                this.dimension = this.dimensionIterator.next();
                Collection<String> objects = BusinessObjectCollection.this.dimensionFormGroupMapping.getObject(this.dimension);
                if (objects == null) continue;
                this.objectIterator = objects.iterator();
            }
            return this.dimension != null && this.objectIterator != null && this.objectIterator.hasNext();
        }

        @Override
        public IBusinessObject next() {
            return new FormGroupObject(this.dimension, this.objectIterator.next());
        }
    }

    private class FormObjectIterator
    implements Iterator<IBusinessObject> {
        private final Iterator<DimensionCombination> dimensionIterator;
        private DimensionCombination dimension;
        private Iterator<String> objectIterator;

        private FormObjectIterator() {
            this.dimensionIterator = BusinessObjectCollection.this.dimensionCollection.getDimensionCombinations().iterator();
        }

        @Override
        public boolean hasNext() {
            while ((this.objectIterator == null || !this.objectIterator.hasNext()) && this.dimensionIterator.hasNext()) {
                this.dimension = this.dimensionIterator.next();
                Collection<String> objects = BusinessObjectCollection.this.dimensionFormMapping.getObject(this.dimension);
                if (objects == null) continue;
                this.objectIterator = objects.iterator();
            }
            return this.dimension != null && this.objectIterator != null && this.objectIterator.hasNext();
        }

        @Override
        public IBusinessObject next() {
            return new FormObject(this.dimension, this.objectIterator.next());
        }
    }

    private class DimensionObjectIterator
    implements Iterator<IBusinessObject> {
        private final Iterator<DimensionCombination> dimensionIterator;

        private DimensionObjectIterator() {
            this.dimensionIterator = BusinessObjectCollection.this.dimensionCollection.getDimensionCombinations().iterator();
        }

        @Override
        public boolean hasNext() {
            return this.dimensionIterator.hasNext();
        }

        @Override
        public IBusinessObject next() {
            return new DimensionObject(this.dimensionIterator.next());
        }
    }
}

