/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.bean;

public enum UnitState {
    UN_UPLOAD(0),
    SUBMIT(1),
    UPLOAD(2),
    REJECT(3),
    CONFIRM(4);

    private int intValue;

    private UnitState(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }
}

