/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.grid2.MemStream2
 *  com.jiuqi.np.grid2.ReadMemStream2
 *  com.jiuqi.np.grid2.Stream2
 *  com.jiuqi.np.grid2.StreamException2
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.grid2.MemStream2;
import com.jiuqi.np.grid2.ReadMemStream2;
import com.jiuqi.np.grid2.Stream2;
import com.jiuqi.np.grid2.StreamException2;
import com.jiuqi.nr.definition.facade.DesignRegionTabSettingDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegionTabSettingData
implements Serializable,
DesignRegionTabSettingDefine {
    private static final Logger logger = LoggerFactory.getLogger(RegionTabSettingData.class);
    private String title;
    private String displayCondition;
    private String filterCondition;
    private String bindingExpression;
    private String order;
    private int rowNum;
    private String id = "";

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public static byte[] regionTabSettingDataToBytes(List<RegionTabSettingDefine> data) {
        if (data == null) {
            return null;
        }
        MemStream2 store = new MemStream2();
        try {
            RegionTabSettingData.saveToStream((Stream2)store, data);
            return store.getBytes();
        }
        catch (StreamException2 ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    public static List<DesignRegionTabSettingDefine> bytesToRegionTabSettingData(byte[] data) {
        if (data == null) {
            return null;
        }
        List<DesignRegionTabSettingDefine> regionTabSettingData = null;
        try {
            ReadMemStream2 s = new ReadMemStream2();
            s.writeBuffer(data, 0, data.length);
            s.setPosition(0L);
            regionTabSettingData = RegionTabSettingData.loadFromStream((Stream2)s);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            regionTabSettingData = null;
        }
        return regionTabSettingData;
    }

    public static void saveToStream(Stream2 stream, List<RegionTabSettingDefine> regionTabSettingData) throws StreamException2 {
        stream.writeInt(regionTabSettingData.get(0).getRowNum());
        stream.writeInt(regionTabSettingData.size());
        for (RegionTabSettingDefine regionTabSettingObj : regionTabSettingData) {
            byte[] data = stream.encodeString(regionTabSettingObj.getTitle());
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(regionTabSettingObj.getDisplayCondition());
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(regionTabSettingObj.getFilterCondition());
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(regionTabSettingObj.getBindingExpression());
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(regionTabSettingObj.getOrder());
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
            data = stream.encodeString(regionTabSettingObj.getId());
            stream.writeInt(data.length);
            stream.writeBuffer(data, 0, data.length);
        }
    }

    public static List<DesignRegionTabSettingDefine> loadFromStream(Stream2 stream) throws StreamException2 {
        ArrayList<DesignRegionTabSettingDefine> regionTabSettingDataList = new ArrayList<DesignRegionTabSettingDefine>();
        int TabSettingrowNum = stream.readInt();
        int countInDB = stream.readInt();
        for (int i = 0; i < countInDB; ++i) {
            RegionTabSettingData regionTabSettingData = new RegionTabSettingData();
            regionTabSettingData.setRowNum(TabSettingrowNum);
            int length = stream.readInt();
            regionTabSettingData.setTitle(stream.readString(length));
            length = stream.readInt();
            regionTabSettingData.setDisplayCondition(stream.readString(length));
            length = stream.readInt();
            regionTabSettingData.setFilterCondition(stream.readString(length));
            length = stream.readInt();
            regionTabSettingData.setBindingExpression(stream.readString(length));
            length = stream.readInt();
            regionTabSettingData.setOrder(stream.readString(length));
            length = stream.readInt();
            regionTabSettingData.setId(stream.readString(length));
            regionTabSettingDataList.add(regionTabSettingData);
        }
        return regionTabSettingDataList;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDisplayCondition() {
        return this.displayCondition;
    }

    @Override
    public void setDisplayCondition(String displayCondition) {
        this.displayCondition = displayCondition;
    }

    @Override
    public String getFilterCondition() {
        return this.filterCondition;
    }

    @Override
    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    @Override
    public String getBindingExpression() {
        return this.bindingExpression;
    }

    @Override
    public void setBindingExpression(String bindingExpression) {
        this.bindingExpression = bindingExpression;
    }

    @Override
    public String getOrder() {
        return this.order;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public int getRowNum() {
        return this.rowNum;
    }

    @Override
    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public static List<DesignRegionTabSettingDefine> mergeTabSetting(List<DesignRegionTabSettingDefine> defaultTabs, List<DesignRegionTabSettingDefine> otherTabs) {
        for (DesignRegionTabSettingDefine defaultTab : defaultTabs) {
            for (DesignRegionTabSettingDefine otherTab : otherTabs) {
                if (!defaultTab.getId().equals(otherTab.getId())) continue;
                defaultTab.setTitle(otherTab.getTitle());
            }
        }
        return defaultTabs;
    }

    public static List<DesignRegionTabSettingDefine> transAllData(List<DesignRegionTabSettingDefine> defaultTabs) {
        for (DesignRegionTabSettingDefine defaultTab : defaultTabs) {
            if (!"\u6240\u6709\u6570\u636e".equals(defaultTab.getTitle())) continue;
            defaultTab.setTitle("AllData");
        }
        return defaultTabs;
    }
}

