/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.fielddatacrud.spi.IParamDataProvider
 */
package com.jiuqi.nr.data.text.spi;

import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.fielddatacrud.spi.IParamDataProvider;

public interface IParamDataFileProvider
extends IParamDataProvider {
    public ParamsMapping getParamMapping();
}

