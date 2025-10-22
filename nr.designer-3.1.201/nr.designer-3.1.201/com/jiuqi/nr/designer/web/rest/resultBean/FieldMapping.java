/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.resultBean;

import com.jiuqi.nr.designer.web.treebean.FieldObject;
import java.util.List;

public class FieldMapping {
    private String fieldText;
    private List<FieldObject> fieldObjects;

    public FieldMapping() {
    }

    public FieldMapping(String fieldText, List<FieldObject> fieldObjects) {
        this.fieldText = fieldText;
        this.fieldObjects = fieldObjects;
    }

    public String getFieldText() {
        return this.fieldText;
    }

    public void setFieldText(String fieldText) {
        this.fieldText = fieldText;
    }

    public List<FieldObject> getFieldObjects() {
        return this.fieldObjects;
    }

    public void setFieldObjects(List<FieldObject> fieldObjects) {
        this.fieldObjects = fieldObjects;
    }
}

