/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.datacrud;

import com.jiuqi.nr.datacrud.ISaveInfo;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.SaveReturnRes;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.datacrud.spi.TypeParseStrategy;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;

public interface SaveDataBuilder {
    public SaveDataBuilder resetRegionKey(String var1) throws CrudException;

    public void setFormulaSchemeKey(String var1);

    public int addLink(String var1);

    public ReturnRes addRow(DimensionCombination var1, int var2);

    public ReturnRes setData(int var1, Object var2);

    public SaveReturnRes checkData();

    public ISaveInfo build();

    public SaveDataBuilder registerParseStrategy(int var1, TypeParseStrategy var2);

    public TypeParseStrategy getTypeParseStrategy(int var1);

    public SaveDataBuilder installParseStrategy();

    public SaveDataBuilder unInstallParseStrategy();

    public void setDefaultTypeStrategy(TypeParseStrategy var1);

    public void enableDeleteBeforeSave(boolean var1);

    public SaveDataBuilder where(RowFilter var1);
}

