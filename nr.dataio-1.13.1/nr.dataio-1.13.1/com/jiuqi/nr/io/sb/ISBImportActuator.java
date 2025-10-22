/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.fielddatacrud.ISBActuator
 *  com.jiuqi.nr.fielddatacrud.ImportInfo
 */
package com.jiuqi.nr.io.sb;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.fielddatacrud.ISBActuator;
import com.jiuqi.nr.io.sb.SBImportActuatorType;
import com.jiuqi.nr.io.sb.bean.ImportInfo;
import java.util.Collection;
import java.util.List;

public interface ISBImportActuator
extends ISBActuator {
    public SBImportActuatorType getType();

    default public int getActuatorType() {
        return this.getType().getValue();
    }

    public void setDataFields(List<DataField> var1);

    public void setMdCodeScope(Collection<String> var1);

    public void prepare();

    public void put(List<Object> var1);

    public ImportInfo commit();

    default public com.jiuqi.nr.fielddatacrud.ImportInfo commitData() {
        com.jiuqi.nr.fielddatacrud.ImportInfo result = new com.jiuqi.nr.fielddatacrud.ImportInfo();
        ImportInfo commit = this.commit();
        result.setDimValues(commit.getDimValues());
        return result;
    }

    public void close();
}

