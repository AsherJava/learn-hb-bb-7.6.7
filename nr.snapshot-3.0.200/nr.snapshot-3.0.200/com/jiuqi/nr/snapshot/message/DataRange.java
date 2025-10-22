/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.message;

import com.jiuqi.nr.snapshot.message.DataRegionRange;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataRange
implements Serializable {
    private static final long serialVersionUID = 7846435828169368281L;
    private List<DataRegionRange> formAndFields = new ArrayList<DataRegionRange>();

    public List<DataRegionRange> getFormAndFields() {
        return this.formAndFields;
    }

    public void setFormAndFields(List<DataRegionRange> formAndFields) {
        this.formAndFields = formAndFields;
    }
}

