/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.designer.paramlanguage.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jiuqi.nr.designer.paramlanguage.vo.BigDataSaveObjectDeserializer;
import com.jiuqi.nr.designer.paramlanguage.vo.RegionTabSettingObject;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.List;
import java.util.Map;

@JsonDeserialize(using=BigDataSaveObjectDeserializer.class)
public class BigDataSaveObject {
    private Map<String, Grid2Data> grid2Data;
    private Map<String, List<RegionTabSettingObject>> regionTabSettingData;
    private Map<String, String> fillingGuideData;
    private String language;

    public Map<String, String> getFillingGuideData() {
        return this.fillingGuideData;
    }

    public void setFillingGuideData(Map<String, String> fillingGuideData) {
        this.fillingGuideData = fillingGuideData;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Map<String, Grid2Data> getGrid2Data() {
        return this.grid2Data;
    }

    public void setGrid2Data(Map<String, Grid2Data> grid2Data) {
        this.grid2Data = grid2Data;
    }

    public Map<String, List<RegionTabSettingObject>> getRegionTabSettingData() {
        return this.regionTabSettingData;
    }

    public void setRegionTabSettingData(Map<String, List<RegionTabSettingObject>> regionTabSettingData) {
        this.regionTabSettingData = regionTabSettingData;
    }
}

