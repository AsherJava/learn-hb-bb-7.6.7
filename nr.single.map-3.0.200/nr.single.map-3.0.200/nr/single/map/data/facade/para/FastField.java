/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldType
 */
package nr.single.map.data.facade.para;

import com.jiuqi.np.definition.common.FieldType;

public interface FastField {
    public String getFieldCode();

    public void setFieldCode(String var1);

    public FieldType getFieldType();

    public void setFieldType(FieldType var1);

    public int getFieldSize();

    public void setFieldSize(int var1);

    public int getFieldDecimal();

    public void setFieldDecimal(int var1);

    public String getFieldValue();

    public void setFieldValue(String var1);

    public String getFieldTitle();

    public void setFieldTitle(String var1);

    public String getTableCode();

    public void setTableCode(String var1);

    public String getFormCode();

    public void setFormCode(String var1);

    public String getEnumCode();

    public void setEnumCode(String var1);

    public String getDefaultValue();

    public void setDefaultValue(String var1);

    public int getPosX();

    public void setPosX(int var1);

    public int getPosY();

    public void setPosY(int var1);

    public int getColNum();

    public void setColNum(int var1);

    public int getRowNum();

    public void setRowNum(int var1);

    public boolean getDoSum();

    public void setDoSum(boolean var1);

    public boolean getJEDWData();

    public void setJEDWData(boolean var1);
}

