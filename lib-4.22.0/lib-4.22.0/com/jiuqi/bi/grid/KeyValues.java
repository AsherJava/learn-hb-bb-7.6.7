/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 *  com.jiuqi.bi.util.inifile.StreamIni
 */
package com.jiuqi.bi.grid;

import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.bi.util.inifile.StreamIni;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

class KeyValues
implements Serializable {
    private static final long serialVersionUID = -3805640823898321062L;
    private static final String GRID_KEY_SIGN = "KEYS";
    private Map<String, String> keyMaps = new HashMap<String, String>();

    KeyValues() {
    }

    public String getValue(String key) {
        return this.keyMaps.getOrDefault(key, "");
    }

    public void setValue(String key, String value) {
        if (this.keyMaps.containsKey(key)) {
            this.keyMaps.remove(key);
        }
        if (value != null && value.length() > 0) {
            this.keyMaps.put(key, value);
        }
    }

    public boolean getAsBoolean(String key) {
        return "1".equals(this.getValue(key));
    }

    public void setAsBoolean(String key, boolean value) {
        this.setValue(key, value ? "1" : "0");
    }

    public int getAsInt(String key) {
        String value = this.getValue(key);
        if (value == null || value.length() == 0) {
            return 0;
        }
        return Integer.parseInt(value);
    }

    public void setAsInt(String key, int value) {
        this.setValue(key, Integer.toString(value));
    }

    public void clear() {
        this.keyMaps.clear();
    }

    private void loadFromStreamIni(Stream stream) throws StreamException {
        this.keyMaps.clear();
        if (stream == null || stream.getSize() == 0L) {
            return;
        }
        StreamIni ini = new StreamIni(stream);
        int c = ini.readInteger("general", "count", 0);
        for (int i = 0; i < c; ++i) {
            String key = ini.readString("keys", Integer.toString(i), null);
            String value = ini.readString("values", Integer.toString(i), null);
            if (key == null || key.length() <= 0 || value == null || value.length() <= 0) continue;
            this.keyMaps.put(key, value);
        }
    }

    public void saveToStreamIni(Stream stream) throws StreamException {
        stream.setSize(0L);
        StreamIni ini = new StreamIni(stream);
        int c = 0;
        for (Map.Entry<String, String> e : this.keyMaps.entrySet()) {
            String key = e.getKey();
            String value = e.getValue();
            ini.writeString("keys", Integer.toString(c), key);
            ini.writeString("values", Integer.toString(c), value);
            ++c;
        }
        ini.writeInteger("general", "count", c);
        ini.update();
    }

    public void loadFromStream(Stream stream) throws StreamException {
        this.keyMaps.clear();
        if (stream.getSize() < 8L) {
            return;
        }
        stream.seek(-4L, 2);
        String sign = stream.readString(4);
        if ("BINI".equals(sign)) {
            this.loadFromStreamIni(stream);
        } else if (GRID_KEY_SIGN.equals(sign)) {
            stream.seek(0L, 0);
            int c = stream.readInt();
            for (int i = 0; i < c; ++i) {
                String key = stream.readStringBySize();
                String value = stream.readStringBySize();
                if (key == null || value == null) continue;
                this.keyMaps.put(key, value);
            }
        }
    }

    public void saveToStream(Stream stream) throws StreamException {
        stream.writeInt(this.keyMaps.size());
        for (Map.Entry<String, String> e : this.keyMaps.entrySet()) {
            stream.writeStringWithSize(e.getKey());
            stream.writeStringWithSize(e.getValue());
        }
        stream.writeString(GRID_KEY_SIGN);
    }
}

