/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.nr.splittable.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;

public class MergedCell {
    private int left;
    private int top;
    private int right;
    private int bottom;

    public MergedCell() {
    }

    public MergedCell(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        MergedCell that = (MergedCell)o;
        return this.left == that.left && this.top == that.top && this.right == that.right && this.bottom == that.bottom;
    }

    public int hashCode() {
        return Objects.hash(this.left, this.top, this.right, this.bottom);
    }

    public int getLeft() {
        return this.left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return this.top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getRight() {
        return this.right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getBottom() {
        return this.bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    @JsonIgnore
    public int getRowIndex() {
        return this.left;
    }

    @JsonIgnore
    public int getColumnIndex() {
        return this.top;
    }

    @JsonIgnore
    public int getRowSpan() {
        return this.bottom - this.top + 1;
    }

    @JsonIgnore
    public int getColSpan() {
        return this.right - this.left + 1;
    }
}

