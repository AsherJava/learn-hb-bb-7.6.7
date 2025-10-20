/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.model;

import com.jiuqi.nvwa.cellbook.model.CellColor;
import java.io.Serializable;
import java.util.List;

public class CellTheme
implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<CellColor> palette = CellColor.PALLETTE;

    public List<CellColor> getPalette() {
        return this.palette;
    }

    public void setPalette(List<CellColor> palette) {
        this.palette = palette;
    }
}

