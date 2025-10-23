/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.io;

public final class Version {
    private final short x;
    private final short y;
    private final short z;
    private static final String SPLITCHAR = ".";
    public static final Version V1_0_0 = new Version(1, 0, 0);
    public static final Version ERR = new Version(-1, -1, -1);

    private Version(short x, short y, short z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean equals(Object other) {
        if (other instanceof Version) {
            Version v = (Version)other;
            return this.x == v.x && this.y == v.y && this.z == v.z;
        }
        return false;
    }

    public String toString() {
        return this.x + SPLITCHAR + this.y + SPLITCHAR + this.z;
    }

    public static Version valueOf(String versionText) {
        if (versionText == null) {
            return ERR;
        }
        String[] versionNumbers = versionText.split(SPLITCHAR);
        short x = 0;
        short y = 0;
        short z = 0;
        try {
            if (versionNumbers.length > 0) {
                x = (short)Integer.parseInt(versionNumbers[0]);
            }
            if (versionNumbers.length > 1) {
                y = (short)Integer.parseInt(versionNumbers[1]);
            }
            if (versionNumbers.length > 2) {
                z = (short)Integer.parseInt(versionNumbers[2]);
            }
        }
        catch (NumberFormatException e) {
            return ERR;
        }
        return new Version(x, y, z);
    }
}

