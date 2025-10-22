/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.message;

import com.jiuqi.nr.snapshot.message.DataRegionAndFields;
import java.io.Serializable;
import java.util.List;

public class DataRegionRange
implements Serializable {
    private static final long serialVersionUID = 3008872913113630374L;
    private String formKey;
    private List<DataRegionAndFields> dataRegionAndFieldsList;

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public List<DataRegionAndFields> getDataRegionAndFieldsList() {
        return this.dataRegionAndFieldsList;
    }

    public void setDataRegionAndFieldsList(List<DataRegionAndFields> dataRegionAndFieldsList) {
        this.dataRegionAndFieldsList = dataRegionAndFieldsList;
    }
}

