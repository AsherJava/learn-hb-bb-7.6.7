/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 */
package nr.single.map.data.facade;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import nr.single.map.data.internal.SingleFileFormulaItemImpl;

@JsonDeserialize(as=SingleFileFormulaItemImpl.class)
public interface SingleFileFormulaItem
extends Serializable {
    public String getSingleFormulaCode();

    public void setSingleFormulaCode(String var1);

    public String getSingleFormulaExp();

    public void setSingleFormulaExp(String var1);

    public String getNetFormulaCode();

    public void setNetFormulaCode(String var1);

    public String getNetFormulaExp();

    public void setNetFormulaExp(String var1);

    public String getNetFormulaKey();

    public void setNetFormulaKey(String var1);

    public String getSingleSchemeName();

    public void setSingleSchemeName(String var1);

    public String getNetSchemeName();

    public void setNetSchemeName(String var1);

    public String getNetSchemeKey();

    public void setNetSchemeKey(String var1);

    public String getSingleTableCode();

    public void setSingleTableCode(String var1);

    public String getNetFormCode();

    public void setNetFormCode(String var1);

    public String getNetFormKey();

    public void setNetFormKey(String var1);

    public String getImportIndex();

    public void setImportIndex(String var1);

    public void copyFrom(SingleFileFormulaItem var1);
}

