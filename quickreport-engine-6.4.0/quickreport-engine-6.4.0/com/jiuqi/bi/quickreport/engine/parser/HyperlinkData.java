/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.engine.parser;

import com.jiuqi.bi.util.StringUtils;
import java.io.Serializable;

public final class HyperlinkData
implements Cloneable,
Serializable {
    private static final long serialVersionUID = 1L;
    private final String text;
    private final String url;
    private final String target;

    public HyperlinkData(String text, String url) {
        this(text, url, null);
    }

    public HyperlinkData(String text, String url, String target) {
        this.text = text;
        this.url = url;
        this.target = target;
    }

    public String text() {
        return this.text;
    }

    public String url() {
        return this.url;
    }

    public String target() {
        return this.target;
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError(e.getMessage());
        }
    }

    public String toString() {
        return this.text + "(" + this.url + ")";
    }

    public int hashCode() {
        int h = this.text == null ? 0 : this.text.hashCode() * 31;
        h = this.url == null ? h : h + this.url.hashCode();
        return this.target == null ? h : h + this.target.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HyperlinkData)) {
            return false;
        }
        HyperlinkData data = (HyperlinkData)obj;
        return StringUtils.equals((String)this.text, (String)data.text) && StringUtils.equals((String)this.url, (String)data.url) && StringUtils.equals((String)this.target, (String)data.target);
    }
}

