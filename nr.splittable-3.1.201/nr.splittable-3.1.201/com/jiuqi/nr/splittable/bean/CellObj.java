/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.splittable.bean;

public class CellObj
implements Comparable<CellObj> {
    private int posY;
    private int posX;

    public CellObj() {
    }

    public CellObj(int posY, int posX) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return this.posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    @Override
    public int compareTo(CellObj o) {
        if (o.posY == this.posY) {
            return this.posX - o.posX;
        }
        return this.posY - o.posY;
    }

    public boolean equals(Object obj) {
        if (obj instanceof CellObj) {
            CellObj cellObj = (CellObj)obj;
            return this.posX == cellObj.posX && this.posY == cellObj.posY;
        }
        return false;
    }
}

