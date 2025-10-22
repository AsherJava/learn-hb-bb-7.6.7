/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.file.ini;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import nr.single.map.configurations.file.ini.Ident;
import nr.single.map.configurations.file.ini.IniBuffer;
import nr.single.map.configurations.file.ini.Section;
import nr.single.map.configurations.file.ini.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BufferIni {
    private static final Logger logger = LoggerFactory.getLogger(BufferIni.class);
    private List<Section> list = new ArrayList<Section>();
    private IniBuffer buffer;
    private boolean founded;

    protected IniBuffer buffer() {
        return this.buffer;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void readInfo() {
        this.list.clear();
        if (!this.buffer.reading() && !this.buffer.gotoEntry(true)) {
            return;
        }
        this.buffer.beginRead();
        try {
            int l = this.buffer.readInfoInteger();
            for (int i = 0; i < l; ++i) {
                Section sec = new Section(this.buffer.readInfoString(), this.buffer);
                this.list.add(sec);
                sec.readInfo();
            }
        }
        finally {
            this.buffer.endRead();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void writeInfo() {
        this.buffer.beginWrite();
        try {
            int l = this.count();
            this.buffer.writeInfoInteger(l);
            for (int i = 0; i < l; ++i) {
                Section sec = this.get(i);
                this.buffer.writeInfoString(sec.name());
                sec.writeInfo();
            }
        }
        finally {
            this.buffer.endWrite();
        }
        if (!this.buffer.writeing()) {
            this.buffer.writeEntry();
        }
    }

    public BufferIni(IniBuffer aBuffer) {
        this.buffer = aBuffer;
    }

    public Section get(int index) {
        return this.list.get(index);
    }

    public int count() {
        return this.list.size();
    }

    public void clear() {
        this.list.clear();
    }

    public void update() {
        this.writeInfo();
    }

    public void eraseSection(String section) {
        int i = this.find(section);
        if (this.founded) {
            this.list.remove(i);
        }
    }

    public void eraseSection(int index) {
        this.list.remove(index);
    }

    public void deleteKey(String section, String ident) {
        int i = this.find(section);
        if (this.founded) {
            this.get(i).deleteKey(ident);
        }
    }

    public void writeNull(String section, String ident) {
        this.deleteKey(section, ident);
    }

    public void writeBoolean(String section, String ident, boolean value) {
        Section sec = this.findCreateSection(section);
        sec.writeBoolean(ident, value);
    }

    public void writeDouble(String section, String ident, double value) {
        Section sec = this.findCreateSection(section);
        sec.writeDouble(ident, value);
    }

    public void writeDateTime(String section, String ident, long value) {
        Section sec = this.findCreateSection(section);
        sec.writeDateTime(ident, value);
    }

    public void writeInteger(String section, String ident, int value) {
        Section sec = this.findCreateSection(section);
        sec.writeInteger(ident, value);
    }

    public void writeLong(String section, String ident, long value) {
        Section sec = this.findCreateSection(section);
        sec.writeLong(ident, value);
    }

    public void writeString(String section, String ident, String value) {
        Section sec = this.findCreateSection(section);
        sec.writeString(ident, value);
    }

    public void writeStream(String section, String ident, Stream stream) {
        Section sec = this.findCreateSection(section);
        sec.writeStream(ident, stream);
    }

    public BufferIni writeIni(String section, String ident) {
        Section sec = this.findCreateSection(section);
        return sec.writeIni(ident);
    }

    public boolean sectionExists(String section) {
        this.find(section);
        return this.founded;
    }

    public boolean valueExists(String section, String ident) {
        return this.findNode(section, ident) != null;
    }

    public boolean isNull(String section, String ident) {
        return !this.valueExists(section, ident);
    }

    public int find(String section) {
        this.founded = false;
        int l = 0;
        int h = this.list.size() - 1;
        while (l <= h) {
            int i = (l + h) / 2;
            int c = this.get(i).name().compareTo(section);
            if (c < 0) {
                l = i + 1;
                continue;
            }
            h = i - 1;
            if (c != 0) continue;
            this.founded = true;
            l = i;
        }
        return l;
    }

    public Section findSection(String section) {
        int i = this.find(section);
        if (this.founded) {
            return this.get(i);
        }
        return null;
    }

    public Section findCreateSection(String section) {
        int i = this.find(section);
        if (this.founded) {
            return this.get(i);
        }
        Section sec = new Section(section, this.buffer);
        this.list.add(i, sec);
        return sec;
    }

    public Ident findNode(String section, String ident) {
        Ident ret = null;
        Section sec = this.findSection(section);
        if (sec != null) {
            ret = sec.findNode(ident);
        }
        return ret;
    }

    public Ident findCreateNode(String section, String ident) {
        Section sec = this.findCreateSection(section);
        return sec.findNodeCreate(ident);
    }

    public BufferIni readIni(String section, String ident) {
        Section sec = this.findSection(section);
        if (sec == null) {
            return null;
        }
        return sec.readIni(ident);
    }

    public boolean readBoolean(String section, String ident, boolean defValue) {
        Section sec = this.findSection(section);
        boolean result = sec == null ? defValue : sec.readBoolean(ident, defValue);
        return result;
    }

    public double readDouble(String section, String ident, double defValue) {
        Section sec = this.findSection(section);
        if (sec == null) {
            return defValue;
        }
        return sec.readDouble(ident, defValue);
    }

    public long readDateTime(String section, String ident, long defValue) {
        Section sec = this.findSection(section);
        if (sec == null) {
            return defValue;
        }
        return sec.readDateTime(ident, defValue);
    }

    public int readInteger(String section, String ident, int defValue) {
        Section sec = this.findSection(section);
        if (sec == null) {
            return defValue;
        }
        return sec.readInteger(ident, defValue);
    }

    public long readLong(String section, String ident, long defValue) {
        Section sec = this.findSection(section);
        if (sec == null) {
            return defValue;
        }
        return sec.readLong(ident, defValue);
    }

    public String readString(String section, String ident, String defValue) {
        Section sec = this.findSection(section);
        if (sec == null) {
            return defValue;
        }
        return sec.readString(ident, defValue);
    }

    public void readStream(String section, String ident, Stream stream) {
        Section sec = this.findSection(section);
        if (sec != null) {
            sec.readStream(ident, stream);
        }
    }

    public void loadFromStream(Stream aStream) {
        this.buffer.loadFromStream(aStream);
        this.readInfo();
    }

    public void loadFromStream(InputStream inStream) {
        try {
            this.buffer.loadFromStream(inStream);
            this.readInfo();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public void replaceStream(Stream aStream) {
        this.buffer.replaceStream(aStream);
        this.readInfo();
    }

    public void saveToStream(Stream aStream) {
        this.writeInfo();
        this.buffer.saveToStream(aStream);
    }

    public void saveToStream(OutputStream outStream) {
        try {
            this.writeInfo();
            this.buffer.saveToStream(outStream);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public void loadFromFile(String fileName) {
        try {
            this.buffer.loadFromFile(fileName);
            this.readInfo();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public void saveToFile(String fileName) {
        try {
            this.update();
            this.buffer.saveToFile(fileName);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}

