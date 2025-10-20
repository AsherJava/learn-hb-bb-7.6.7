/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.api;

import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import java.util.List;

public interface IParamLanguageController {
    public String getTaskTitle(String var1, String var2);

    public String getFormSchemeTitle(String var1, String var2);

    public String getFormulaSchemeTitle(String var1, String var2);

    public String getPrintSchemeTitle(String var1, String var2);

    public String getFormGroupTitle(String var1, String var2);

    public String getFormTitle(String var1, String var2);

    public byte[] getFormStyle(String var1, String var2);

    public List<RegionTabSettingDefine> getDataRegionTab(String var1, String var2);

    @Deprecated
    public byte[] getDesignFormStyle(String var1, String var2);

    public String getFormulaDescript(String var1, String var2);

    public String getFullingGuide(String var1, String var2);

    public String getTaskOrgLink(String var1, String var2);
}

