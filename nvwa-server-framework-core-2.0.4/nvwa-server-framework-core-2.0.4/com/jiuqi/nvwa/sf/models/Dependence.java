/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jdom2.Element
 */
package com.jiuqi.nvwa.sf.models;

import java.io.Serializable;
import org.jdom2.Element;

public class Dependence
implements Serializable {
    private static final long serialVersionUID = 1L;
    public String id;
    public String minVersion;
    public boolean includeMinVersion;
    public String maxVersion;
    public boolean includeMaxVersion;

    void load(Element e) throws Exception {
        Element max;
        this.id = e.getAttributeValue("id");
        if (this.id == null || this.id.length() == 0) {
            throw new Exception("\u4f9d\u8d56\u6a21\u5757\u672a\u8bbe\u7f6eid");
        }
        Element min = e.getChild("minversion");
        if (min != null) {
            this.minVersion = min.getText();
            this.includeMinVersion = Boolean.parseBoolean(min.getAttributeValue("included"));
        }
        if ((max = e.getChild("maxversion")) != null) {
            this.maxVersion = max.getText();
            this.includeMaxVersion = Boolean.parseBoolean(max.getText());
        }
    }
}

