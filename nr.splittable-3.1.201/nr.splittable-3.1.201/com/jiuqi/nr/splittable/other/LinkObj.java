/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.splittable.other;

public class LinkObj
implements Comparable<LinkObj> {
    int posx;
    int posy;

    public int getPosy() {
        return this.posy;
    }

    public int getPosx() {
        return this.posx;
    }

    public LinkObj() {
    }

    public LinkObj(int posY, int posX) {
        this.posy = posY;
        this.posx = posX;
    }

    @Override
    public int compareTo(LinkObj o) {
        if (o.posy == this.posy) {
            return this.posx - o.posx;
        }
        return this.posy - o.posy;
    }
}

