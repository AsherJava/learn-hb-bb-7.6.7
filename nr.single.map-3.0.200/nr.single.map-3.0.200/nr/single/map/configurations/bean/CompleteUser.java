/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.bean;

public enum CompleteUser {
    EMPTY(0),
    IMPORTUSER(1);

    private int intValue;

    private CompleteUser(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }
}

