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
import com.jiuqi.nr.single.core.para.parser.print.GridPrintTextData;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GridPrintTextsDef
implements Cloneable {
    private int privateTopSpace;
    private int privateBottomSpace;
    private List<GridPrintTextData> list = new ArrayList<GridPrintTextData>();

    public int getTopSpace() {
        return this.privateTopSpace;
    }

    public void setTopSpace(int value) {
        this.privateTopSpace = value;
    }

    public int getBottomSpace() {
        return this.privateBottomSpace;
    }

    public void setBottomSpace(int value) {
        this.privateBottomSpace = value;
    }

    public void ini(Stream stream) throws IOException, StreamException {
        this.list.clear();
        int count = ReadUtil.readIntValue(stream);
        for (int i = 0; i < count; ++i) {
            GridPrintTextData textData = new GridPrintTextData();
            this.list.add(textData);
            textData.LoadFromStream(stream);
        }
    }

    public int getCount() {
        return this.list.size();
    }

    public GridPrintTextData getTextData(int index) {
        if (index < 0 || index >= this.list.size()) {
            throw new RuntimeException("GridPrintTextData Out Of List");
        }
        return this.list.get(index);
    }
}

