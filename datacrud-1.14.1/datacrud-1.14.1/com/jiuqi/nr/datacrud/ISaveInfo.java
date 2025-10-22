/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud;

import com.jiuqi.nr.datacrud.IRegionInfo;
import com.jiuqi.nr.datacrud.SaveData;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import java.util.Iterator;

public interface ISaveInfo
extends IRegionInfo {
    public SaveData getSaveData();

    @Override
    public RegionRelation getRegionRelation();

    public String getFormulaSchemeKey();

    public boolean enableDeleteBeforeSave();

    public Iterator<RowFilter> rowFilterItr();
}

