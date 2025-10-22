/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.message;

import com.jiuqi.nr.snapshot.message.DifferenceData;
import java.io.Serializable;
import java.util.List;

public class DifferenceDataItem
implements Serializable {
    private static final long serialVersionUID = 3711546584452129027L;
    private String fieldKey;
    private String fieldCode;
    private String fieldTitle;
    private List<DifferenceData> differenceDatas;

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public List<DifferenceData> getDifferenceDatas() {
        return this.differenceDatas;
    }

    public void setDifferenceDatas(List<DifferenceData> differenceDatas) {
        this.differenceDatas = differenceDatas;
    }
}

