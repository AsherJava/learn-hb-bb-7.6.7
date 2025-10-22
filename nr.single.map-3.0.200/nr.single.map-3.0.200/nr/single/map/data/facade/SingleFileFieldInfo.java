/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.jiuqi.np.definition.common.FieldType
 */
package nr.single.map.data.facade;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jiuqi.np.definition.common.FieldType;
import java.io.Serializable;
import nr.single.map.data.internal.SingleFileFieldInfoImpl;

@JsonDeserialize(as=SingleFileFieldInfoImpl.class)
public interface SingleFileFieldInfo
extends Serializable {
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

    public String getTableCode();

    public void setTableCode(String var1);

    public String getFormCode();

    public void setFormCode(String var1);

    public String getEnumCode();

    public void setEnumCode(String var1);

    public String getDefaultValue();

    public void setDefaultValue(String var1);

    public String getNetTableCode();

    public void setNetTableCode(String var1);

    public String getNetFieldCode();

    public void setNetFieldCode(String var1);

    public String getNetFieldKey();

    public void setNetFieldKey(String var1);

    public String getNetDataLinkKey();

    public void setNetDataLinkKey(String var1);

    public String getNetFormCode();

    public void setNetFormCode(String var1);

    public String getNetFieldValue();

    public void setNetFieldValue(String var1);

    public String getImportIndex();

    public void setImportIndex(String var1);

    public int getFloatEnumType();

    public void setFloatEnumType(int var1);

    public String getFloatEnumCode();

    public void setFloatEnumCode(String var1);

    public int getFloatEnumOrder();

    public void setFloatEnumOrder(int var1);

    public void copyFrom(SingleFileFieldInfo var1);

    public String getRegionKey();

    public void setRegionKey(String var1);

    public String getRegionCode();

    public void setRegionCode(String var1);
}

