/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.jtable.params.output.DimValSetSerializ;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class FormCheckStatusInfo
implements Externalizable {
    private static final long serialVersionUID = -9157465435529028807L;
    private String formKey;
    private DimensionValueSet dimensionValueSet;
    private int status;

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String toString() {
        return this.formKey + ";" + this.dimensionValueSet.toString();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.formKey);
        DimValSetSerializ dimValSetSerializ = new DimValSetSerializ();
        for (int i = 0; i < this.dimensionValueSet.size(); ++i) {
            dimValSetSerializ.setValue(this.dimensionValueSet.getName(i), dimValSetSerializ.getValue(i));
            dimValSetSerializ.getKeys().add(this.dimensionValueSet.getName(i));
            dimValSetSerializ.getVals().add(this.dimensionValueSet.getValue(i));
        }
        out.writeObject(dimValSetSerializ);
        out.writeInt(this.status);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.formKey = (String)in.readObject();
        DimValSetSerializ dimValSetSerializ = (DimValSetSerializ)in.readObject();
        this.dimensionValueSet = new DimensionValueSet();
        for (int i = 0; i < dimValSetSerializ.getKeys().size(); ++i) {
            this.dimensionValueSet.setValue(dimValSetSerializ.getKeys().get(i), dimValSetSerializ.getVals().get(i));
        }
        this.status = in.readInt();
    }
}

