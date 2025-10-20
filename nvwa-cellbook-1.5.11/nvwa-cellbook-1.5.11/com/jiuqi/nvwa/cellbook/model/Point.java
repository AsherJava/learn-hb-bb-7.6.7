/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.model;

import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Point
implements Serializable,
Cloneable {
    public static final Logger LOGGER = LoggerFactory.getLogger("CellBook");
    private static final long serialVersionUID = 1L;
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Object clone() {
        Point point = null;
        try {
            point = (Point)super.clone();
        }
        catch (CloneNotSupportedException e) {
            LOGGER.error("\u590d\u5236\u5bf9\u8c61\u62a5\u9519\uff01", e);
        }
        return point;
    }

    public int hashCode() {
        return this.x ^ this.y;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Point)) {
            return false;
        }
        Point p = (Point)o;
        return p.x == this.x && p.y == this.y;
    }

    public String toString() {
        return "Point{" + this.x + "," + this.y + "}";
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

