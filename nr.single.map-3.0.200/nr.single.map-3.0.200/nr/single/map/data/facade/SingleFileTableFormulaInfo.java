/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data.facade;

import java.io.Serializable;
import java.util.Map;
import nr.single.map.data.facade.SingleFileFormulaItem;

public interface SingleFileTableFormulaInfo
extends Serializable {
    public String getSingleTableCode();

    public void setSingleTableCode(String var1);

    public String getNetFormCode();

    public void setNetFormCode(String var1);

    public String getNetFormKey();

    public void setNetFormKey(String var1);

    public Map<String, SingleFileFormulaItem> getFormulaItems();

    public void copyFrom(SingleFileTableFormulaInfo var1);
}

