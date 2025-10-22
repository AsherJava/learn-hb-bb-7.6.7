/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.annotation.message;

import java.io.Serializable;

public class LinkMappingMessage
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String linkKey;
    private int posX;
    private int posY;

    public LinkMappingMessage(String linkKey, int posX, int posY) {
        this.linkKey = linkKey;
        this.posX = posX;
        this.posY = posY;
    }

    public String getLinkKey() {
        return this.linkKey;
    }

    public void setLinkKey(String linkKey) {
        this.linkKey = linkKey;
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
}

