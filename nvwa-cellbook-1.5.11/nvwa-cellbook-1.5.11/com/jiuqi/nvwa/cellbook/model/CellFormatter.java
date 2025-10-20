/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.model;

import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CellFormatter
implements Serializable,
Cloneable {
    public static final Logger LOGGER = LoggerFactory.getLogger("CellBook");
    private static final long serialVersionUID = 1L;
    private String formatCode = "";

    public Object clone() {
        CellFormatter cellFormatter = null;
        try {
            cellFormatter = (CellFormatter)super.clone();
        }
        catch (CloneNotSupportedException e) {
            LOGGER.error("\u590d\u5236\u5bf9\u8c61\u62a5\u9519\uff01", e);
        }
        return cellFormatter;
    }

    public String getFormatCode() {
        return this.formatCode;
    }

    public void setFormatCode(String formatCode) {
        if (null == formatCode) {
            formatCode = "";
        }
        this.formatCode = formatCode;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.formatCode == null ? 0 : this.formatCode.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        CellFormatter other = (CellFormatter)obj;
        return !(this.formatCode == null ? other.formatCode != null : !this.formatCode.equals(other.formatCode));
    }
}

