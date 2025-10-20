/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.grid2;

import com.jiuqi.np.grid2.CellStringListItem;
import com.jiuqi.np.grid2.Stream2;
import com.jiuqi.np.grid2.StreamException2;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CellStringList
implements Serializable {
    private static final long serialVersionUID = -2547803517815443581L;
    private List<CellStringListItem> list = new ArrayList<CellStringListItem>();

    public void add(CellStringListItem item) {
        this.list.add(item);
    }

    public void clear() {
        this.list.clear();
    }

    public List<CellStringListItem> getStringListItemList() {
        return this.list;
    }

    public boolean hasValue() {
        return this.list.size() > 0;
    }

    public void loadFromStream(Stream2 stream) throws StreamException2 {
        int count = stream.readInt();
        for (int i = 0; i < count; ++i) {
            CellStringListItem item = new CellStringListItem();
            item.loadFromStream(stream);
            this.list.add(item);
        }
    }

    public void saveToStream(Stream2 stream) throws StreamException2 {
        stream.writeInt(this.list.size());
        for (CellStringListItem item : this.list) {
            item.saveToStream(stream);
        }
    }
}

