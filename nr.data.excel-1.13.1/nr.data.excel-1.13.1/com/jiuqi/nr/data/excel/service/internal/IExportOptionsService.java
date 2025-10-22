/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.access.param.SecretLevel
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.data.excel.service.internal;

import com.jiuqi.nr.data.access.param.SecretLevel;
import com.jiuqi.nr.data.excel.obj.DimensionData;
import com.jiuqi.nr.data.excel.param.TitleShowSetting;
import com.jiuqi.nr.definition.facade.FormDefine;

public interface IExportOptionsService {
    public String getFormShow(TitleShowSetting var1, FormDefine var2);

    public String getDWShow(TitleShowSetting var1, DimensionData var2, String var3, String var4, SecretLevel var5);

    public String getDWShow(TitleShowSetting var1, DimensionData var2, String var3, String var4);

    public String getSysSeparator(TitleShowSetting var1);

    public int getMaxUnitNum();

    public boolean autoFillIsNullTable();

    public boolean simplifyExpFileHierarchy(TitleShowSetting var1);

    public String getZeroShow(String var1);

    public Integer getMeasureChangeDefaultDecimal(String var1);

    public boolean expExcelFormula(String var1);

    public int getMemPerfLevel();

    public int getSheetSplitMaxNum();

    public int getSheetFloatMax();

    public int getQueryPageLimit();
}

