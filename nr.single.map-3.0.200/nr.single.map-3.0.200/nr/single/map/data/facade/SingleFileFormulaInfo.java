/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data.facade;

import java.io.Serializable;
import java.util.Map;
import nr.single.map.data.facade.SingleFileTableFormulaInfo;

public interface SingleFileFormulaInfo
extends Serializable {
    public String getSingleSchemeName();

    public void setSingleSchemeName(String var1);

    public String getNetSchemeName();

    public void setNetSchemeName(String var1);

    public String getNetSchemeKey();

    public void setNetSchemeKey(String var1);

    public Map<String, SingleFileTableFormulaInfo> getTableFormulaInfos();

    public void copyFrom(SingleFileFormulaInfo var1);
}

