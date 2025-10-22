/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 */
package com.jiuqi.nr.fielddatacrud;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.fielddatacrud.ImportInfo;
import java.util.Collection;
import java.util.List;

public interface ISBActuator
extends AutoCloseable {
    public int getActuatorType();

    public void setDataFields(List<DataField> var1);

    public void setMdCodeScope(Collection<String> var1);

    public void prepare();

    public void put(List<Object> var1);

    public ImportInfo commitData();

    @Override
    public void close();
}

