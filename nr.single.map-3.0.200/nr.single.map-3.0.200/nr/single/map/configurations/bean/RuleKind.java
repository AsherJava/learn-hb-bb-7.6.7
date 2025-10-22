/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.bean;

public enum RuleKind {
    UNIT_MAP_IMPORT(0),
    UNIT_MAP_EXPORT(1);

    private int intValue;

    private RuleKind(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }
}

