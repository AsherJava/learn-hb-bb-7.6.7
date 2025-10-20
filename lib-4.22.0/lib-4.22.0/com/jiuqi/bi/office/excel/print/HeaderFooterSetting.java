/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.office.excel.print;

import com.jiuqi.bi.office.excel.print.DifferentFirst;
import com.jiuqi.bi.office.excel.print.DifferentOddEven;
import com.jiuqi.bi.office.excel.print.HeaderFooter;
import java.io.Serializable;
import org.json.JSONObject;

public final class HeaderFooterSetting
implements Serializable,
Cloneable {
    private static final long serialVersionUID = -8944876135699349611L;
    private HeaderFooter headerFooter;
    private DifferentOddEven differentOddEven;
    private DifferentFirst differentFirst;
    private boolean scaleWithDoc;
    private boolean alignWithMargins;

    public HeaderFooter getHeaderFooter() {
        return this.headerFooter;
    }

    public DifferentOddEven getDifferentOddEven() {
        return this.differentOddEven;
    }

    public DifferentFirst getDifferentFirst() {
        return this.differentFirst;
    }

    public boolean isScaleWithDoc() {
        return this.scaleWithDoc;
    }

    public boolean isAlignWithMargins() {
        return this.alignWithMargins;
    }

    public JSONObject toJson() {
        JSONObject json_headerAndFooter = new JSONObject();
        if (this.headerFooter != null) {
            json_headerAndFooter.put("headerFooter", (Object)this.headerFooter.toJson());
        }
        if (this.differentOddEven != null) {
            json_headerAndFooter.put("differentOddEven", (Object)this.differentOddEven.toJson());
        }
        if (this.differentFirst != null) {
            json_headerAndFooter.put("differentFirst", (Object)this.differentFirst.toJson());
        }
        json_headerAndFooter.put("scaleWithDoc", this.scaleWithDoc);
        json_headerAndFooter.put("alignWithMargins", this.alignWithMargins);
        return json_headerAndFooter;
    }

    public void fromJson(JSONObject json_headerAndFooter) {
        this.headerFooter = null;
        JSONObject json_headerFooter = json_headerAndFooter.optJSONObject("headerFooter");
        if (json_headerFooter != null) {
            this.headerFooter = new HeaderFooter();
            this.headerFooter.fromJson(json_headerFooter);
        }
        this.differentOddEven = null;
        JSONObject json_differentOddEven = json_headerAndFooter.optJSONObject("differentOddEven");
        if (json_differentOddEven != null) {
            this.differentOddEven = new DifferentOddEven();
            this.differentOddEven.fromJson(json_differentOddEven);
        }
        this.differentFirst = null;
        JSONObject json_differentFirst = json_headerAndFooter.optJSONObject("differentFirst");
        if (json_differentFirst != null) {
            this.differentFirst = new DifferentFirst();
            this.differentFirst.fromJson(json_differentFirst);
        }
        this.scaleWithDoc = json_headerAndFooter.optBoolean("scaleWithDoc");
        this.alignWithMargins = json_headerAndFooter.optBoolean("alignWithMargins");
    }

    protected Object clone() {
        try {
            HeaderFooterSetting cloned = (HeaderFooterSetting)super.clone();
            cloned.headerFooter = this.headerFooter == null ? null : (HeaderFooter)this.headerFooter.clone();
            cloned.differentOddEven = this.differentOddEven == null ? null : (DifferentOddEven)this.differentOddEven.clone();
            cloned.differentFirst = this.differentFirst == null ? null : (DifferentFirst)this.differentFirst.clone();
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

