/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 */
package com.jiuqi.nr.zbselector.define;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.zbselector.define.FormData;
import com.jiuqi.nr.zbselector.define.FormGroupDataSerializer;
import java.util.List;

@JsonSerialize(using=FormGroupDataSerializer.class)
public class FormGroupData {
    private String id;
    private String title;
    private String parentId;
    private List<FormData> forms;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setForms(List<FormData> forms) {
        this.forms = forms;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public List<FormData> getForms() {
        return this.forms;
    }
}

