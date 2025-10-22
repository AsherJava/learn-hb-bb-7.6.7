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
import com.jiuqi.nr.single.core.para.parser.print.PointRec;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GMultiPointsGraph
extends GraphItem {
    private List<PointRec> points;

    @Override
    public void loadFromStream(Stream is) throws IOException, StreamException {
        super.loadFromStream(is);
        int len = ReadUtil.readIntValue(is);
        this.points = new ArrayList<PointRec>();
        for (int i = 0; i < len; ++i) {
            int x = ReadUtil.readIntValue(is);
            int y = ReadUtil.readIntValue(is);
            PointRec p = new PointRec();
            p.setX(x);
            p.setY(y);
            this.points.add(p);
        }
    }

    public List<PointRec> getPoints() {
        return this.points;
    }

    public void setPoints(List<PointRec> points) {
        this.points = points;
    }
}

