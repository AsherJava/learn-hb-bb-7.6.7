/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.graphics;

public class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
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
}

