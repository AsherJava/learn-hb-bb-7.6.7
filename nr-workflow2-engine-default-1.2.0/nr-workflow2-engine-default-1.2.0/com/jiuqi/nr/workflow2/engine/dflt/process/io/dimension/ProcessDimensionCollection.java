/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionSoftImpl
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionSoftImpl;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class ProcessDimensionCollection
extends DimensionCollectionSoftImpl {
    public ProcessDimensionCollection(DimensionCombination combination) {
        super(Arrays.asList(combination));
    }

    public ProcessDimensionCollection(@NotNull List<DimensionCombination> combinations) {
        super(new ArrayList<DimensionCombination>(new HashSet<DimensionCombination>(combinations)));
    }
}

