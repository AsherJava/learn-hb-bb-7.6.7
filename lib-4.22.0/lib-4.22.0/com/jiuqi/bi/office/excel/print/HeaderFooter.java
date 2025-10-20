/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.office.excel.print;

import com.jiuqi.bi.office.excel.print.Footer;
import com.jiuqi.bi.office.excel.print.Header;
import java.io.Serializable;
import org.json.JSONObject;

public final class HeaderFooter
implements Serializable,
Cloneable {
    private static final long serialVersionUID = -7052987805813825555L;
    private Header header;
    private Footer footer;

    public Header getHeader() {
        return this.header;
    }

    public Footer getFooter() {
        return this.footer;
    }

    public JSONObject toJson() {
        JSONObject json_headerAndFooter = new JSONObject();
        if (this.header != null) {
            json_headerAndFooter.put("header", (Object)this.header.toJson());
        }
        if (this.footer != null) {
            json_headerAndFooter.put("footer", (Object)this.footer.toJson());
        }
        return json_headerAndFooter;
    }

    public void fromJson(JSONObject json_headerAndFooter) {
        this.header = null;
        JSONObject json_header = json_headerAndFooter.optJSONObject("header");
        if (json_header != null) {
            this.header = new Header();
            this.header.fromJson(json_header);
        }
        this.footer = null;
        JSONObject json_footer = json_headerAndFooter.optJSONObject("footer");
        if (json_footer != null) {
            this.footer = new Footer();
            this.footer.fromJson(json_footer);
        }
    }

    protected Object clone() {
        try {
            HeaderFooter cloned = (HeaderFooter)super.clone();
            cloned.header = this.header == null ? null : (Header)this.header.clone();
            cloned.footer = this.footer == null ? null : (Footer)this.footer.clone();
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

