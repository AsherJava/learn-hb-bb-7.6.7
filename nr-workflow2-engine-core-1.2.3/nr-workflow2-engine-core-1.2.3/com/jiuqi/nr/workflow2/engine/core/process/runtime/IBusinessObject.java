/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.workflow2.engine.core.process.runtime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectDeserializer;

@JsonDeserialize(using=BusinessObjectDeserializer.class)
public interface IBusinessObject {
    public DimensionCombination getDimensions();
}

