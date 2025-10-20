/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.grid;

import com.jiuqi.bi.util.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Matrix<E>
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int xCount;
    private int yCount;
    private List<List<E>> list = new ArrayList<List<E>>();

    public Matrix() {
    }

    public Matrix(int xSize, int ySize) {
        this();
        this.setSize(xSize, ySize);
    }

    public int getXCount() {
        return this.xCount;
    }

    public int getYCount() {
        return this.yCount;
    }

    public void setSize(int xSize, int ySize) {
        if (xSize < 0 || ySize < 0) {
            throw new IllegalArgumentException("xSize = " + xSize + ", ySize = " + ySize);
        }
        if (ySize > this.yCount) {
            while (this.list.size() < ySize) {
                this.list.add(null);
            }
        } else if (ySize < this.yCount) {
            this.list.subList(ySize, this.list.size()).clear();
        }
        this.yCount = ySize;
        if (xSize < this.xCount) {
            this.list.stream().filter(Objects::nonNull).forEach(values -> {
                if (values.size() > xSize) {
                    values.subList(xSize, values.size()).clear();
                }
            });
        }
        this.xCount = xSize;
    }

    public void resetSize(int xSize, int ySize) {
        this.setSize(xSize, ySize);
        this.list.stream().filter(Objects::nonNull).forEach(values -> Collections.fill(values, null));
    }

    public E getElement(int x, int y) {
        if (x < 0 || x >= this.xCount || y < 0 || y >= this.yCount) {
            return null;
        }
        List<E> values = this.list.get(y);
        if (values == null) {
            return null;
        }
        return x < values.size() ? (E)values.get(x) : null;
    }

    public void setElement(int x, int y, E obj) {
        if (x < 0 || x >= this.xCount || y < 0 || y >= this.yCount) {
            return;
        }
        List<E> values = this.list.get(y);
        if (obj == null) {
            if (values != null && x < values.size()) {
                values.set(x, null);
            }
        } else {
            if (values == null) {
                values = new ArrayList();
                this.list.set(y, values);
            }
            while (values.size() < x) {
                values.add(null);
            }
            if (values.size() == x) {
                values.add(obj);
            } else {
                values.set(x, obj);
            }
        }
    }

    public boolean hasElement(E o) {
        return this.list.stream().anyMatch(values -> values != null && values.contains(o));
    }

    public void xDelete(int x) {
        if (x < 0 || x >= this.xCount) {
            throw new IndexOutOfBoundsException("x = " + x + ", xCount = " + this.xCount);
        }
        this.list.stream().filter(Objects::nonNull).forEach(values -> {
            if (values.size() > x) {
                values.remove(x);
            }
        });
        --this.xCount;
    }

    public void xDelete(int x, int count) {
        if (x < 0 || x >= this.xCount || count < 0 || x + count > this.xCount) {
            throw new IndexOutOfBoundsException("x = " + x + ", count = " + count + ", xCount = " + this.xCount);
        }
        if (count == this.xCount) {
            Collections.fill(this.list, null);
        } else {
            this.list.stream().filter(Objects::nonNull).forEach(values -> {
                if (x < values.size()) {
                    values.subList(x, Math.min(values.size(), x + count)).clear();
                }
            });
        }
        this.xCount -= count;
    }

    public void yDelete(int y) {
        if (y < 0 || y >= this.yCount) {
            throw new IndexOutOfBoundsException("y = " + y + ", yCount = " + this.yCount);
        }
        this.list.remove(y);
        --this.yCount;
    }

    public void yDelete(int y, int count) {
        if (y < 0 || y >= this.yCount || count < 0 || y + count > this.yCount) {
            throw new IndexOutOfBoundsException("y = " + y + ", count = " + count + ", yCount = " + this.yCount);
        }
        this.list.subList(y, y + count).clear();
        this.yCount -= count;
    }

    public void xInsert(int x, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count = " + count);
        }
        if (count == 0) {
            return;
        }
        if (x < 0 || x > this.xCount) {
            throw new IndexOutOfBoundsException("x = " + x + ", xCount = " + this.xCount);
        }
        if (count == 1) {
            this.list.stream().filter(Objects::nonNull).forEach(values -> {
                if (x < values.size()) {
                    values.add(x, null);
                }
            });
        } else {
            List<Object> items = Arrays.asList(new Object[count]);
            this.list.stream().filter(Objects::nonNull).forEach(values -> {
                if (x < values.size()) {
                    values.addAll(x, items);
                }
            });
        }
        this.xCount += count;
    }

    public void yInsert(int y, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count = " + count);
        }
        if (count == 0) {
            return;
        }
        if (y < 0 || y > this.yCount) {
            throw new IndexOutOfBoundsException("y = " + y + ", yCount = " + this.yCount);
        }
        for (int i = 0; i < count; ++i) {
            this.list.add(y, null);
        }
        this.yCount += count;
    }

    public void xExchange(int x1, int x2) {
        int high;
        int low;
        if (x1 < 0 || x1 >= this.xCount || x2 < 0 || x2 >= this.xCount) {
            throw new IndexOutOfBoundsException("x1 = " + x1 + ", x2 = " + x2 + ", xCount = " + this.xCount);
        }
        if (x1 == x2) {
            return;
        }
        if (x1 <= x2) {
            low = x1;
            high = x2;
        } else {
            low = x2;
            high = x1;
        }
        this.list.stream().filter(Objects::nonNull).forEach(values -> {
            Object value;
            if (values.size() > low && ((value = values.get(low)) != null || values.size() > high)) {
                while (values.size() <= high) {
                    values.add(null);
                }
                values.set(low, values.get(high));
                values.set(high, value);
            }
        });
    }

    public void yExchange(int y1, int y2) {
        if (y1 < 0 || y1 >= this.yCount || y2 < 0 || y2 >= this.yCount) {
            throw new IndexOutOfBoundsException("y1 = " + y1 + ", y2 = " + y2 + ", yCount = " + this.yCount);
        }
        if (y1 == y2) {
            return;
        }
        List<E> values = this.list.get(y1);
        this.list.set(y1, this.list.get(y2));
        this.list.set(y2, values);
    }

    public void xCopy(int fromIndex, int toIndex) {
        if (fromIndex < 0 || fromIndex >= this.xCount || toIndex < 0 || toIndex >= this.xCount) {
            throw new IndexOutOfBoundsException("xFrom = " + fromIndex + ", xTo = " + toIndex + ", xCount = " + this.xCount);
        }
        this.list.stream().filter(Objects::nonNull).forEach(values -> {
            Object value;
            Object e = value = fromIndex < values.size() ? (Object)values.get(fromIndex) : null;
            if (value == null) {
                if (toIndex < values.size()) {
                    values.set(toIndex, null);
                }
            } else {
                while (values.size() < toIndex) {
                    values.add(null);
                }
                if (values.size() == toIndex) {
                    values.add(value);
                } else {
                    values.set(toIndex, value);
                }
            }
        });
    }

    public void yCopy(int fromIndex, int toIndex) {
        if (fromIndex < 0 || fromIndex >= this.yCount || toIndex < 0 || toIndex >= this.yCount) {
            throw new IndexOutOfBoundsException("yFrom = " + fromIndex + ", + yTo = " + toIndex + ", yCount = " + this.yCount);
        }
        List<E> values = this.list.get(fromIndex);
        if (values == null) {
            this.list.set(toIndex, null);
        } else {
            this.list.set(toIndex, new ArrayList<E>(values));
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

