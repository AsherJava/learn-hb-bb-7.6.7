/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.file.ini;

import nr.single.map.configurations.file.ini.MemStream;
import nr.single.map.configurations.file.ini.StreamIni;

public class MemStreamIni
extends StreamIni {
    public MemStreamIni() {
        super(new MemStream());
    }

    @Override
    public void clear() {
        super.clear();
        this.replaceStream(new MemStream());
    }
}

