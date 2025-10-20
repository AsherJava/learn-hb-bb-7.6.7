/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.office.excel.print;

import com.jiuqi.bi.office.excel.print.FontText;
import java.io.Serializable;
import org.json.JSONObject;

public class Header
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 5372198681840949838L;
    private FontText left;
    private FontText center;
    private FontText right;

    public FontText getLeft() {
        return this.left;
    }

    public FontText getCenter() {
        return this.center;
    }

    public FontText getRight() {
        return this.right;
    }

    public JSONObject toJson() {
        JSONObject json_header = new JSONObject();
        if (this.left != null) {
            json_header.put("left", (Object)this.left.toJson());
        }
        if (this.center != null) {
            json_header.put("center", (Object)this.center.toJson());
        }
        if (this.right != null) {
            json_header.put("right", (Object)this.right.toJson());
        }
        return json_header;
    }

    public void fromJson(JSONObject json_header) {
        this.left = null;
        JSONObject json_left = json_header.optJSONObject("left");
        if (json_left != null) {
            this.left = new FontText();
            this.left.fromJson(json_left);
        }
        this.center = null;
        JSONObject json_center = json_header.optJSONObject("center");
        if (json_center != null) {
            this.center = new FontText();
            this.center.fromJson(json_center);
        }
        this.right = null;
        JSONObject json_right = json_header.optJSONObject("right");
        if (json_right != null) {
            this.right = new FontText();
            this.right.fromJson(json_right);
        }
    }

    protected Object clone() {
        try {
            Header cloned = (Header)super.clone();
            cloned.left = this.left == null ? null : (FontText)this.left.clone();
            cloned.center = this.center == null ? null : (FontText)this.center.clone();
            cloned.right = this.right == null ? null : (FontText)this.right.clone();
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

