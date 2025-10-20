/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.excel.print;

import com.jiuqi.nvwa.cellbook.excel.print.Footer;
import com.jiuqi.nvwa.cellbook.excel.print.Header;
import java.io.Serializable;

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

