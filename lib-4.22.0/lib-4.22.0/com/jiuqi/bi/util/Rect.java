/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util;

import java.io.Serializable;

public final class Rect
implements Serializable,
Cloneable {
    private static final long serialVersionUID = -6663315924751917853L;
    public int left;
    public int top;
    public int right;
    public int bottom;
    public static final int RECTS_EQUAL = 0;
    public static final int RECTS_CONTAIN = 1;
    public static final int RECTS_INSIDE = -1;
    public static final int RECTS_EXCLUDE = 2;
    public static final int RECTS_CROSS = -2;

    public Rect() {
    }

    public Rect(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public Rect(Rect rect) {
        this.left = rect.left;
        this.top = rect.top;
        this.right = rect.right;
        this.bottom = rect.bottom;
    }

    public boolean inRect(int x, int y) {
        return x >= this.left && x <= this.right && y >= this.top && y <= this.bottom;
    }

    public boolean equals(Object o) {
        Rect rect = (Rect)o;
        return this.left == rect.left && this.top == rect.top && this.right == rect.right && this.bottom == rect.bottom;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer("[(");
        buffer.append(this.left);
        buffer.append(",");
        buffer.append(this.top);
        buffer.append("),(");
        buffer.append(this.right);
        buffer.append(",");
        buffer.append(this.bottom);
        buffer.append(")]");
        return buffer.toString();
    }

    public boolean contains(Rect rect) {
        return this.left <= rect.left && this.top <= rect.top && this.right >= rect.right && this.bottom >= rect.bottom;
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public static int compareRects(Rect rect1, Rect rect2) {
        if (rect1.equals(rect2)) {
            return 0;
        }
        if (rect1.contains(rect2)) {
            return 1;
        }
        if (rect2.contains(rect1)) {
            return -1;
        }
        if (rect1.right < rect2.left || rect1.left > rect2.right || rect1.bottom < rect2.top || rect1.top > rect2.bottom) {
            return 2;
        }
        return -2;
    }
}

