/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 *  com.jiuqi.bi.util.inifile.StreamIni
 */
package com.jiuqi.bi.grid;

import com.jiuqi.bi.grid.LabelItem;
import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.inifile.StreamIni;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LabelItems
implements Serializable,
Cloneable {
    private static final long serialVersionUID = -979682237771664309L;
    private List<LabelItem> list = new ArrayList<LabelItem>();

    public LabelItem add() {
        LabelItem result = new LabelItem();
        this.list.add(result);
        return result;
    }

    public LabelItem add(int index) {
        LabelItem result = new LabelItem();
        this.list.add(index, result);
        return result;
    }

    public void remove(int index) {
        this.list.remove(index);
    }

    public void clear() {
        this.list.clear();
    }

    public int size() {
        return this.list.size();
    }

    public LabelItem get(int index) {
        return this.list.get(index);
    }

    public LabelItem[] getLabelsByPos(int position) {
        int i;
        LabelItem[] result = null;
        ArrayList<LabelItem> l = new ArrayList<LabelItem>();
        for (i = 0; i < this.size(); ++i) {
            if (this.get(i).getPosition() != position) continue;
            l.add(this.get(i));
        }
        if (l.size() > 0) {
            result = new LabelItem[l.size()];
            for (i = 0; i < l.size(); ++i) {
                result[i] = (LabelItem)l.get(i);
            }
        }
        return result;
    }

    public int indexOfName(String labelName) {
        int result = -1;
        for (int i = 0; i < this.size(); ++i) {
            if (!this.get(i).getName().equalsIgnoreCase(labelName)) continue;
            result = i;
            break;
        }
        return result;
    }

    public void loadFromStream(Stream stream) throws StreamException {
        this.clear();
        if (stream.getSize() > 0L) {
            stream.setPosition(0L);
            StreamIni ini = new StreamIni(stream);
            for (int i = 0; i < ini.readInteger("general", "count", 0); ++i) {
                this.add().loadFromIni(i, ini);
            }
        }
    }

    public void saveToStream(Stream stream) throws StreamException {
        stream.setSize(0L);
        if (this.size() > 0) {
            StreamIni ini = new StreamIni(stream);
            for (int i = 0; i < this.size(); ++i) {
                this.get(i).saveToIni(i, ini);
            }
            ini.writeInteger("general", "count", this.size());
            ini.update();
        }
    }

    public void ver10Tover11() {
        for (int i = 0; i < this.size(); ++i) {
            LabelItem item = this.get(i);
            if (item.getPosition() >= LabelItem.LABEL_POS_MAPS.length) continue;
            item.setPosition(LabelItem.LABEL_POS_MAPS[item.getPosition()]);
        }
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append('[');
        for (int i = 0; i < this.size(); ++i) {
            if (i > 0) {
                buffer.append(',').append(StringUtils.LINE_SEPARATOR);
            }
            buffer.append(this.get(i).toString());
        }
        buffer.append(']');
        return buffer.toString();
    }
}

