/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.np.grid2.MemStream2
 *  com.jiuqi.np.grid2.ReadMemStream2
 *  com.jiuqi.np.grid2.Stream2
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.definition.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.np.grid2.MemStream2;
import com.jiuqi.np.grid2.ReadMemStream2;
import com.jiuqi.np.grid2.Stream2;
import com.jiuqi.nr.definition.util.LineProp;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtentStyle
implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(ExtentStyle.class);
    private static final long serialVersionUID = 6470826896503183700L;
    @JsonProperty(value="lineProps")
    private List<LineProp> lineProps;
    @JsonProperty(value="griddata")
    private Grid2Data griddata;
    @JsonProperty(value="regionKey")
    private String regionKey;

    @JsonIgnore
    public List<LineProp> getLineProps() {
        return this.lineProps;
    }

    @JsonIgnore
    public void setLineProps(List<LineProp> lineProps) {
        this.lineProps = lineProps;
    }

    @JsonIgnore
    public Grid2Data getGriddata() {
        return this.griddata;
    }

    @JsonIgnore
    public void setGriddata(Grid2Data griddata) {
        this.griddata = griddata;
    }

    @JsonIgnore
    public String getRegionKey() {
        return this.regionKey;
    }

    @JsonIgnore
    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public static byte[] designTaskFlowsDefineToBytes(ExtentStyle data) {
        if (data == null) {
            return null;
        }
        MemStream2 store = new MemStream2();
        try {
            ExtentStyle.saveToStream((Stream2)store, data);
            return store.getBytes();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    public static void saveToStream(Stream2 stream, ExtentStyle extentStyle) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(extentStyle.getLineProps());
        byte[] data = byteArrayOutputStream.toByteArray();
        stream.writeInt(data.length);
        stream.writeBuffer(data, 0, data.length);
        data = Grid2Data.gridToBytes((Grid2Data)extentStyle.getGriddata());
        stream.writeInt(data.length);
        stream.writeBuffer(data, 0, data.length);
        data = stream.encodeString(String.valueOf(extentStyle.getRegionKey()));
        stream.writeInt(data.length);
        stream.writeBuffer(data, 0, data.length);
    }

    public static ExtentStyle loadFromStream(Stream2 stream) throws Exception {
        ExtentStyle extentStyle = new ExtentStyle();
        int length = stream.readInt();
        byte[] b = new byte[length];
        int read1 = stream.read(b, 0, length);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(b);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        List list = (List)objectInputStream.readObject();
        extentStyle.setLineProps(list);
        length = stream.readInt();
        byte[] bs = new byte[length];
        int read = stream.read(bs, 0, length);
        extentStyle.setGriddata(Grid2Data.bytesToGrid((byte[])bs));
        length = stream.readInt();
        extentStyle.setRegionKey(stream.readString(length));
        return extentStyle;
    }

    public static ExtentStyle bytesToTaskFlowsData(byte[] data) {
        if (data == null) {
            return null;
        }
        ExtentStyle extentStyle = null;
        try {
            ReadMemStream2 s = new ReadMemStream2();
            s.writeBuffer(data, 0, data.length);
            s.setPosition(0L);
            extentStyle = ExtentStyle.loadFromStream((Stream2)s);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            extentStyle = null;
        }
        return extentStyle;
    }
}

