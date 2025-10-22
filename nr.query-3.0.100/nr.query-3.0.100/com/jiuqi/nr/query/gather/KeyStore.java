/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.gather;

public class KeyStore {
    private int keyCode;
    private int modifiers;

    public KeyStore() {
    }

    public KeyStore(int keyCode, int modifiers) {
        this.keyCode = keyCode;
        this.modifiers = modifiers;
    }

    public int getKeyCode() {
        return this.keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getModifiers() {
        return this.modifiers;
    }

    public void setModifiers(int modifiers) {
        this.modifiers = modifiers;
    }

    public String toString() {
        return "\"keyCode\":\"" + this.keyCode + "\", \"modifiers\":\"" + this.modifiers + "\"";
    }
}

