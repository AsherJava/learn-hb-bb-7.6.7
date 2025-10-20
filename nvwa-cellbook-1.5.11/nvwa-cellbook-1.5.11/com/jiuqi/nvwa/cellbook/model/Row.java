/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.model;

import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Row
implements Serializable,
Cloneable {
    public static final Logger LOGGER = LoggerFactory.getLogger("CellBook");
    private static final long serialVersionUID = 1L;
    private int size = 28;
    private boolean auto;
    private boolean hidden;
    private boolean fold;

    public Object clone() {
        Row row = null;
        try {
            row = (Row)super.clone();
        }
        catch (CloneNotSupportedException e) {
            LOGGER.error("\u590d\u5236\u5bf9\u8c61\u62a5\u9519\uff01", e);
        }
        return row;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isAuto() {
        return this.auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isFold() {
        return this.fold;
    }

    public void setFold(boolean fold) {
        this.fold = fold;
    }
}

