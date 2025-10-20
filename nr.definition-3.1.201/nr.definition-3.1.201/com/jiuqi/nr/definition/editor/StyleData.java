/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.editor;

import com.jiuqi.nr.definition.editor.LinkData;
import java.util.List;

public class StyleData {
    private String formKey;
    private List<LinkData> links;
    private byte[] griddata;

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public List<LinkData> getLinks() {
        return this.links;
    }

    public void setLinks(List<LinkData> links) {
        this.links = links;
    }

    public byte[] getGriddata() {
        return this.griddata;
    }

    public void setGriddata(byte[] griddata) {
        this.griddata = griddata;
    }
}

