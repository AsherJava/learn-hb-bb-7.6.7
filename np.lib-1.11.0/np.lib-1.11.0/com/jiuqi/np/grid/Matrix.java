/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.np.grid;

import com.jiuqi.bi.util.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Matrix
implements Serializable {
    private static final long serialVersionUID = 2105076797600846714L;
    private int xCount;
    private int yCount;
    private List list = new ArrayList();

    public int getXCount() {
        return this.xCount;
    }

    public int getYCount() {
        return this.yCount;
    }

    public void setSize(int xSize, int ySize) {
        int j;
        List data;
        int i;
        if (xSize < 0 || ySize < 0) {
            return;
        }
        int c = 0;
        if (this.xCount != xSize) {
            for (i = this.xCount; i < xSize; ++i) {
                this.list.add(new ArrayList());
            }
            for (i = c = this.list.size(); i > xSize; --i) {
                this.list.remove(i - 1);
            }
            this.xCount = xSize;
        }
        if (this.yCount < ySize) {
            for (i = 0; i < this.xCount; ++i) {
                data = (List)this.list.get(i);
                if (data == null) continue;
                for (j = this.yCount; j < ySize; ++j) {
                    data.add(null);
                }
            }
            this.yCount = ySize;
        }
        if (this.yCount > ySize) {
            for (i = 0; i < this.xCount; ++i) {
                data = (List)this.list.get(i);
                if (data == null) continue;
                for (j = c = data.size(); j > ySize; --j) {
                    data.remove(j - 1);
                }
            }
            this.yCount = ySize;
        }
    }

    public void resetSize(int xSize, int ySize) {
        this.setSize(xSize, ySize);
        for (int x = 0; x < this.getXCount(); ++x) {
            for (int y = 0; y < this.getYCount(); ++y) {
                this.setElement(x, y, null);
            }
        }
    }

    public Object getElement(int x, int y) {
        if (x >= 0 && y >= 0 && x < this.xCount && y < this.yCount) {
            List data = (List)this.list.get(x);
            if (data != null && y < data.size()) {
                return data.get(y);
            }
            return null;
        }
        return null;
    }

    public void setElement(int x, int y, Object obj) {
        if (x >= 0 && y >= 0 && x < this.xCount && y < this.yCount) {
            ArrayList<Object> data = (ArrayList<Object>)this.list.get(x);
            if (data == null && obj != null) {
                data = new ArrayList<Object>();
                this.list.set(x, data);
            }
            if (data != null) {
                for (int i = data.size(); i < this.yCount; ++i) {
                    data.add(null);
                }
                data.set(y, obj);
            }
        }
    }

    public boolean hasElement(Object o) {
        for (int i = 0; i < this.list.size(); ++i) {
            List data = (List)this.list.get(i);
            if (data == null || data.indexOf(o) < 0) continue;
            return true;
        }
        return false;
    }

    public void xDelete(int x) {
        this.list.remove(x);
        --this.xCount;
    }

    public void xDelete(int x, int count) {
        for (int i = 0; i < count; ++i) {
            this.xDelete(x);
        }
    }

    public void yDelete(int y) {
        for (int i = 0; i < this.list.size(); ++i) {
            List data = (List)this.list.get(i);
            if (data == null || y >= data.size()) continue;
            data.remove(y);
        }
        --this.yCount;
    }

    public void yDelete(int y, int count) {
        for (int i = 0; i < count; ++i) {
            this.yDelete(y);
        }
    }

    public void xInsert(int x, int count) {
        for (int i = 0; i < count; ++i) {
            this.list.add(x, null);
        }
        this.xCount += count;
    }

    public void yInsert(int y, int count) {
        if (y < 0 || y > this.yCount) {
            return;
        }
        for (int i = 0; i < this.list.size(); ++i) {
            List data = (List)this.list.get(i);
            if (data == null || y > data.size()) continue;
            for (int k = 0; k < count; ++k) {
                data.add(y, null);
            }
        }
        this.yCount += count;
    }

    public void xExchange(int x1, int x2) {
        if (x1 < 0 || x2 < 0 || x1 >= this.xCount || x2 >= this.xCount) {
            return;
        }
        Object data = this.list.get(x1);
        this.list.set(x1, this.list.get(x2));
        this.list.set(x2, data);
    }

    public void yExchange(int y1, int y2) {
        if (y1 < 0 || y2 < 0 || y1 >= this.yCount || y2 >= this.yCount || y1 == y2) {
            return;
        }
        for (int i = 0; i < this.list.size(); ++i) {
            List data = (List)this.list.get(i);
            if (data == null || y1 >= data.size() && y2 >= data.size()) continue;
            for (int k = data.size(); k < this.yCount; ++k) {
                data.add(null);
            }
            Object o = data.get(y1);
            data.set(y1, data.get(y2));
            data.set(y2, o);
        }
    }

    public void xCopy(int fromIndex, int toIndex) {
        List data = (List)this.list.get(fromIndex);
        if (data == null) {
            this.list.set(toIndex, null);
        } else {
            ArrayList newData = new ArrayList();
            newData.addAll(data);
            this.list.set(toIndex, newData);
        }
    }

    public void yCopy(int fromIndex, int toIndex) {
        for (int i = 0; i < this.list.size(); ++i) {
            List data = (List)this.list.get(i);
            if (data == null || fromIndex >= data.size() && toIndex >= data.size()) continue;
            for (int k = data.size(); k < this.yCount; ++k) {
                data.add(null);
            }
            data.set(toIndex, data.get(fromIndex));
        }
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append('[');
        for (int y = 0; y < this.yCount; ++y) {
            if (y > 0) {
                buffer.append(';').append(StringUtils.LINE_SEPARATOR).append(' ');
            }
            for (int x = 0; x < this.xCount; ++x) {
                if (x > 0) {
                    buffer.append(", ");
                }
                buffer.append(this.getElement(x, y));
            }
        }
        buffer.append(']');
        return buffer.toString();
    }
}

