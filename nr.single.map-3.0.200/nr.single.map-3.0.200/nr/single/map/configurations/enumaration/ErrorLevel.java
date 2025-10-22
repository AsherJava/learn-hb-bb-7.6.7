/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.enumaration;

public enum ErrorLevel {
    ERROR_INFO(0),
    WARN_INFO(1);

    private int level;

    private ErrorLevel() {
    }

    private ErrorLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }
}

