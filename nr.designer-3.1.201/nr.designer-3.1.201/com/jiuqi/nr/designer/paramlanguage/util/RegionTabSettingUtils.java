/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.RegionTabSettingDefine
 *  com.jiuqi.nr.definition.internal.impl.RegionTabSettingData
 */
package com.jiuqi.nr.designer.paramlanguage.util;

import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.internal.impl.RegionTabSettingData;
import com.jiuqi.nr.designer.paramlanguage.vo.RegionTabSettingObject;
import java.util.ArrayList;
import java.util.List;

public class RegionTabSettingUtils {
    public static List<RegionTabSettingDefine> tabSettingObjListConversion(List<RegionTabSettingObject> regionTabSettingObjects) {
        ArrayList<RegionTabSettingDefine> regionTabSettingDefines = new ArrayList<RegionTabSettingDefine>();
        for (RegionTabSettingObject regionTabSettingObject : regionTabSettingObjects) {
            RegionTabSettingData regionTabSettingDefine = new RegionTabSettingData();
            regionTabSettingDefine.setId(regionTabSettingObject.getID());
            regionTabSettingDefine.setBindingExpression(regionTabSettingObject.getBindingExpression());
            regionTabSettingDefine.setDisplayCondition(regionTabSettingObject.getDisplayCondition());
            regionTabSettingDefine.setFilterCondition(regionTabSettingObject.getFilterCondition());
            regionTabSettingDefine.setTitle(regionTabSettingObject.getTitle());
            regionTabSettingDefine.setOrder(regionTabSettingObject.getOrder());
            regionTabSettingDefine.setRowNum(regionTabSettingObject.getRowNum());
            regionTabSettingDefines.add((RegionTabSettingDefine)regionTabSettingDefine);
        }
        return regionTabSettingDefines;
    }
}

