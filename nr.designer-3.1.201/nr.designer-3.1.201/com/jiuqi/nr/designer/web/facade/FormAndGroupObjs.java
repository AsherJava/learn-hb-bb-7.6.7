/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.designer.web.facade.FormObj;
import com.jiuqi.nr.designer.web.treebean.FormGroupObject;
import java.util.List;

public class FormAndGroupObjs {
    private List<FormObj> formObjs;
    private List<FormGroupObject> formGroupObjects;

    public List<FormObj> getFormObjs() {
        return this.formObjs;
    }

    public void setFormObjs(List<FormObj> formObjs) {
        this.formObjs = formObjs;
    }

    public List<FormGroupObject> getFormGroupObjects() {
        return this.formGroupObjects;
    }

    public void setFormGroupObjects(List<FormGroupObject> formGroupObjects) {
        this.formGroupObjects = formGroupObjects;
    }
}

