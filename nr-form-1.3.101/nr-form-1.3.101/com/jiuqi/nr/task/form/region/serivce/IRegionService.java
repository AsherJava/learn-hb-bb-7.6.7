/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.region.serivce;

import com.jiuqi.nr.task.form.ext.dto.RegionQuery;
import com.jiuqi.nr.task.form.region.dto.DataRegionDTO;
import com.jiuqi.nr.task.form.region.dto.DataRegionSettingDTO;
import java.util.List;

public interface IRegionService {
    public DataRegionSettingDTO getSetting(RegionQuery var1);

    public DataRegionDTO getSimpleSetting(RegionQuery var1);

    public List<DataRegionDTO> listDataRegion(String var1);

    public void saveDataRegionSetting(String var1, List<DataRegionSettingDTO> var2);

    public List<DataRegionSettingDTO> listDataRegionSetting(String var1);

    public DataRegionSettingDTO getRegionSetting(String var1);

    public void insertDefaultRegion(DataRegionSettingDTO var1);
}

