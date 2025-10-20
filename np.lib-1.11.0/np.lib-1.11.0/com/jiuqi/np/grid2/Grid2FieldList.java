/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.grid2;

import com.jiuqi.np.grid2.Grid2CellField;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Grid2FieldList
implements Serializable {
    private static final long serialVersionUID = 1016447076061657293L;
    private List<Grid2CellField> list = new ArrayList<Grid2CellField>();

    public void addMergeRect(Grid2CellField rect) {
        this.list.add(rect);
    }

    public void remove(Grid2CellField rect) {
        this.list.remove(rect);
    }

    public void remove(int index) {
        this.list.remove(index);
    }

    public Grid2CellField get(int i) {
        return this.list.get(i);
    }

    public int count() {
        return this.list.size();
    }
}

