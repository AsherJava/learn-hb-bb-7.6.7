/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.office.excel.print;

import java.io.Serializable;
import org.json.JSONObject;

public final class Font
implements Serializable,
Cloneable {
    private static final long serialVersionUID = -5898896434762165325L;
    private String family;
    private float size;
    private String color;
    private boolean bold;
    private boolean italic;
    private boolean underline;

    public String getFamily() {
        return this.family;
    }

    public float getSize() {
        return this.size;
    }

    public String getColor() {
        return this.color;
    }

    public boolean isBold() {
        return this.bold;
    }

    public boolean isItalic() {
        return this.italic;
    }

    public boolean isUnderline() {
        return this.underline;
    }

    public JSONObject toJson() {
        JSONObject json_font = new JSONObject();
        json_font.put("family", (Object)this.family);
        json_font.put("size", this.size);
        json_font.put("color", (Object)this.color);
        json_font.put("bold", this.bold);
        json_font.put("italic", this.italic);
        json_font.put("underline", this.underline);
        return json_font;
    }

    public void fromJson(JSONObject json_font) {
        this.family = json_font.optString("family");
        this.size = json_font.optFloat("size");
        this.color = json_font.optString("color");
        this.bold = json_font.optBoolean("bold");
        this.italic = json_font.optBoolean("italic");
        this.underline = json_font.optBoolean("underline");
    }

    protected Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

