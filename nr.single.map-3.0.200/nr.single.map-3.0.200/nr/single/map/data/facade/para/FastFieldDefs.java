/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data.facade.para;

import java.util.List;
import nr.single.map.data.facade.para.FastField;

public interface FastFieldDefs {
    public int fieldNameToIndex();

    public int fieldHLNumToIndex(int var1, int var2);

    public FastField getField(int var1);

    public int getFieldCount();

    public String getFieldCode(int var1);

    public void addField(FastField var1);

    public int getChildDefsCount();

    public FastFieldDefs getSuperFields();

    public FastFieldDefs GetChildDefs(int var1);

    public FastFieldDefs GetChildDefsByIndex(int var1);

    public int getFloatingIndex();

    public void setFloatingIndex(int var1);

    public int getFloatCount();

    public int getKeyFieldCount();

    public boolean getHasLocalKey();

    public boolean getKeyUnique();

    public boolean getMultiData();

    public int getRecordLength();

    public List<String> getFloatCodes();
}

