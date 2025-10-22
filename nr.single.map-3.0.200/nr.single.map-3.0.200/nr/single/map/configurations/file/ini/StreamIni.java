/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.file.ini;

import nr.single.map.configurations.file.ini.BufferIni;
import nr.single.map.configurations.file.ini.Stream;
import nr.single.map.configurations.file.ini.StreamIniBuffer;

public class StreamIni
extends BufferIni {
    public StreamIni(Stream aStream) {
        super(new StreamIniBuffer(aStream));
        this.readInfo();
    }
}

