/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.enumaration;

public enum FileKind {
    ENTITY_FILE(0),
    FORMULA_FILE(1),
    ZB_FILE(2),
    JIO_FILE(3);

    private int file;

    private FileKind(int file) {
        this.file = file;
    }

    public int getFile() {
        return this.file;
    }
}

