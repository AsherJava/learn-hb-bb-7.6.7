/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.sf.adapter.spring.watermark.inner;

import com.jiuqi.nvwa.sf.adapter.spring.watermark.Watermark;
import org.json.JSONObject;

public class LogoWatermark
extends Watermark {
    private int mode = 2;
    private byte[] image;

    @Override
    public int getMode() {
        return this.mode;
    }

    public byte[] getImage() {
        return this.image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public JSONObject toJson() throws Exception {
        JSONObject json = super.toJson();
        json.put("image", (Object)this.image);
        json.put("mode", this.mode);
        return json;
    }

    @Override
    public void fromJson(JSONObject json) {
        super.fromJson(json);
        this.image = (byte[])json.opt("image");
        this.mode = json.optInt("mode");
    }
}

