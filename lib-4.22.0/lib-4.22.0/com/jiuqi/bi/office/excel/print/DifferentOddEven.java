/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.office.excel.print;

import com.jiuqi.bi.office.excel.print.HeaderFooter;
import java.io.Serializable;
import org.json.JSONObject;

public final class DifferentOddEven
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 4176456371324523650L;
    private boolean enable;
    private HeaderFooter odd;
    private HeaderFooter even;

    public boolean isEnable() {
        return this.enable;
    }

    public HeaderFooter getOdd() {
        return this.odd;
    }

    public HeaderFooter getEven() {
        return this.even;
    }

    public JSONObject toJson() {
        JSONObject json_differentOddEven = new JSONObject();
        json_differentOddEven.put("enable", this.enable);
        if (this.odd != null) {
            json_differentOddEven.put("odd", (Object)this.odd.toJson());
        }
        if (this.even != null) {
            json_differentOddEven.put("even", (Object)this.even.toJson());
        }
        return json_differentOddEven;
    }

    public void fromJson(JSONObject json_differentOddEven) {
        this.enable = json_differentOddEven.optBoolean("enable");
        this.odd = null;
        JSONObject json_odd = json_differentOddEven.optJSONObject("odd");
        if (json_odd != null) {
            this.odd = new HeaderFooter();
            this.odd.fromJson(json_odd);
        }
        this.even = null;
        JSONObject json_even = json_differentOddEven.optJSONObject("even");
        if (json_even != null) {
            this.even = new HeaderFooter();
            this.even.fromJson(json_even);
        }
    }

    protected Object clone() {
        try {
            DifferentOddEven cloned = (DifferentOddEven)super.clone();
            cloned.odd = this.odd == null ? null : (HeaderFooter)this.odd.clone();
            cloned.even = this.even == null ? null : (HeaderFooter)this.even.clone();
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

