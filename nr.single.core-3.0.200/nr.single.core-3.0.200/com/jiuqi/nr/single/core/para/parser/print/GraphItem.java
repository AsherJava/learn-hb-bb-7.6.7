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
import com.jiuqi.nr.single.core.para.parser.print.IGraphItem;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import java.io.IOException;

public class GraphItem
implements IGraphItem {
    private int privateLockPos;
    private int privateTag;

    public int getLockPos() {
        return this.privateLockPos;
    }

    public void setLockPos(int value) {
        this.privateLockPos = value;
    }

    public int getTag() {
        return this.privateTag;
    }

    public void setTag(int value) {
        this.privateTag = value;
    }

    @Override
    public void loadFromStream(Stream is) throws IOException, StreamException {
        this.setLockPos(ReadUtil.readIntValue(is));
        this.setTag(ReadUtil.readIntValue(is));
    }
}

