/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.office.excel.print;

import com.jiuqi.bi.office.excel.print.Font;
import java.io.Serializable;
import org.json.JSONObject;

public final class FontText
implements Serializable,
Cloneable {
    private static final long serialVersionUID = -4120883524002168613L;
    private String text;
    private Font font;

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Font getFont() {
        return this.font;
    }

    public JSONObject toJson() {
        JSONObject json_fontText = new JSONObject();
        json_fontText.put("text", (Object)this.text);
        if (this.font != null) {
            json_fontText.put("font", (Object)this.font.toJson());
        }
        return json_fontText;
    }

    public void fromJson(JSONObject json_fontText) {
        this.text = json_fontText.optString("text");
        this.font = null;
        JSONObject json_font = json_fontText.optJSONObject("font");
        if (json_font != null) {
            this.font = new Font();
            this.font.fromJson(json_font);
        }
    }

    protected Object clone() {
        try {
            FontText cloned = (FontText)super.clone();
            cloned.font = this.font == null ? null : (Font)this.font.clone();
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

