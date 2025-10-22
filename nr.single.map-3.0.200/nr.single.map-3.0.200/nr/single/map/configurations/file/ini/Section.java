/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.file.ini;

import java.util.HashMap;
import java.util.Map;
import nr.single.map.configurations.file.ini.BufferIni;
import nr.single.map.configurations.file.ini.Ident;
import nr.single.map.configurations.file.ini.IniBuffer;
import nr.single.map.configurations.file.ini.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Section {
    private static final Logger logger = LoggerFactory.getLogger(Section.class);
    private String name;
    private IniBuffer buffer;
    private Map<String, Ident> identMap;

    public Section(String aName, IniBuffer aBuffer) {
        this.name = aName;
        this.buffer = aBuffer;
        this.identMap = new HashMap<String, Ident>();
    }

    public Map<String, Ident> getIdentMap() {
        return this.identMap;
    }

    public String name() {
        return this.name;
    }

    public int count() {
        return this.identMap.size();
    }

    public void readInfo() {
        this.clear();
        int l = this.buffer.readInfoInteger();
        for (int i = 0; i < l; ++i) {
            Ident node = new Ident();
            node.setName(this.buffer.readInfoString());
            node.setType(this.buffer.readInfoByte());
            node.setPosition(this.buffer.readInfoInteger());
            if (node.getType() == 7) {
                node.setChild(new BufferIni(this.buffer));
                node.getChild().readInfo();
            }
            this.identMap.put(node.getName(), node);
        }
    }

    public void writeInfo() {
        this.buffer.writeInfoInteger(this.count());
        for (Ident node : this.identMap.values()) {
            this.buffer.writeInfoString(node.getName());
            this.buffer.writeInfoByte(node.getType());
            this.buffer.writeInfoInteger(node.getPosition());
            if (node.getChild() == null) continue;
            node.getChild().writeInfo();
        }
    }

    public void clear() {
        this.identMap.clear();
    }

    public void deleteKey(String ident) {
        this.identMap.remove(ident);
    }

    public void writeNull(String ident) {
        this.deleteKey(ident);
    }

    public void writeBoolean(String ident, boolean value) {
        Ident node = this.findNodeCreate(ident);
        node.setType((byte)1);
        node.setChild(null);
        node.setPosition(this.buffer.writeBoolean(value));
    }

    public void writeDouble(String ident, double value) {
        Ident node = this.findNodeCreate(ident);
        node.setType((byte)3);
        node.setChild(null);
        node.setPosition(this.buffer.writeDouble(value));
    }

    public void writeDateTime(String ident, long value) {
        this.writeLong(ident, value);
    }

    public void writeInteger(String ident, int value) {
        Ident node = this.findNodeCreate(ident);
        node.setType((byte)2);
        node.setChild(null);
        node.setPosition(this.buffer.writeInteger(value));
    }

    public void writeLong(String ident, long value) {
        Ident node = this.findNodeCreate(ident);
        node.setType((byte)4);
        node.setChild(null);
        node.setPosition(this.buffer.writeLong(value));
    }

    public void writeString(String ident, String value) {
        if (value == null) {
            this.deleteKey(ident);
        } else {
            Ident node = this.findNodeCreate(ident);
            node.setType((byte)5);
            node.setChild(null);
            node.setPosition(this.buffer.writeString(value));
        }
    }

    public void writeStream(String ident, Stream stream) {
        try {
            Ident node = this.findNodeCreate(ident);
            node.setType((byte)6);
            node.setChild(null);
            int l = (int)(stream.getSize() - stream.getPosition());
            node.setPosition(this.buffer.writeInteger(l));
            this.buffer.writeStream(stream, l);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public BufferIni writeIni(String ident) {
        Ident node = this.findNodeCreate(ident);
        node.setType((byte)7);
        if (node.getChild() == null) {
            node.setChild(new BufferIni(this.buffer));
        }
        return node.getChild();
    }

    public Ident findNode(String ident) {
        return this.identMap.get(ident);
    }

    public Ident findNodeCreate(String ident) {
        Ident ret = null;
        ret = this.identMap.get(ident);
        if (ret == null) {
            ret = new Ident();
            ret.setName(ident);
            this.identMap.put(ident, ret);
        }
        return ret;
    }

    public boolean isNull(String ident) {
        Ident node = this.findNode(ident);
        return node == null;
    }

    public BufferIni readIni(String ident) {
        Ident ret = this.identMap.get(ident);
        if (ret != null) {
            return ret.getChild();
        }
        return null;
    }

    public boolean readBoolean(String ident, boolean defValue) {
        Ident node = this.findNode(ident);
        if (node != null) {
            return this.buffer.readBoolean(node.getPosition());
        }
        return defValue;
    }

    public double readDouble(String ident, double defValue) {
        Ident node = this.findNode(ident);
        if (node != null) {
            return this.buffer.readDouble(node.getPosition());
        }
        return defValue;
    }

    public long readDateTime(String ident, long defValue) {
        return this.readLong(ident, defValue);
    }

    public int readInteger(String ident, int defValue) {
        Ident node = this.findNode(ident);
        if (node != null) {
            return this.buffer.readInteger(node.getPosition());
        }
        return defValue;
    }

    public long readLong(String ident, long defValue) {
        Ident node = this.findNode(ident);
        if (node != null) {
            return this.buffer.readLong(node.getPosition());
        }
        return defValue;
    }

    public String readString(String ident, String defValue) {
        Ident node = this.findNode(ident);
        if (node != null) {
            return this.buffer.readString(node.getPosition());
        }
        return defValue;
    }

    public void readStream(String ident, Stream stream) {
        Ident node = this.findNode(ident);
        if (node != null) {
            try {
                int l = this.buffer.readInteger(node.getPosition());
                this.buffer.readStream(stream, node.getPosition() + 4, l);
            }
            catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    }
}

