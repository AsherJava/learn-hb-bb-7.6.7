/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.nr.single.core.para.parser.print;

import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.nr.single.core.para.parser.print.GraphItem;
import com.jiuqi.nr.single.core.para.parser.print.PrintReadUtil;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphGroup
extends GraphItem {
    private boolean loaded = false;
    private List<GraphItem> items = new ArrayList<GraphItem>();

    @Override
    public final void loadFromStream(Stream stream) throws IOException, StreamException {
        GraphItem item;
        super.loadFromStream(stream);
        int count = ReadUtil.readIntValue(stream);
        for (int i = 0; i < count && null != (item = PrintReadUtil.loadGraphItem2(stream)); ++i) {
            this.items.add(item);
        }
        this.loaded = true;
    }

    public int getSize() {
        return this.items.size();
    }

    public List<GraphItem> getItems() {
        return this.items;
    }

    public void setItems(List<GraphItem> items) {
        this.items = items;
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}

