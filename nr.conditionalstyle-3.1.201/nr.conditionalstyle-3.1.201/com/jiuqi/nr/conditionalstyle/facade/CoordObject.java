/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.conditionalstyle.facade;

public class CoordObject {
    private int posX;
    private int posY;

    public CoordObject() {
    }

    public CoordObject(int posX, int posY) {
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

    public boolean equals(Object a) {
        if (a instanceof CoordObject) {
            CoordObject coord = (CoordObject)a;
            return coord.getPosX() == this.posX && coord.getPosY() == this.posY;
        }
        return super.equals(a);
    }
}

