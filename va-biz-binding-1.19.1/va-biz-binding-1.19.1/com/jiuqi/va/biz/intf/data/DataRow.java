/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.data;

import com.jiuqi.va.biz.domain.CheckFieldResult;
import com.jiuqi.va.biz.intf.data.DataRowState;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface DataRow {
    public Object getId();

    public long getVersion();

    public Object getMasterId();

    public Object getGroupId();

    public double getOrdinal();

    public DataRowState getState();

    public DataRow getOriginRow();

    public boolean isNull(String var1);

    public boolean getBoolean(String var1);

    public byte getByte(String var1);

    public short getShort(String var1);

    public int getInt(String var1);

    public long getLong(String var1);

    public float getFloat(String var1);

    public double getDouble(String var1);

    public char getChar(String var1);

    public String getString(String var1);

    public Date getDate(String var1);

    public UUID getUUID(String var1);

    public BigDecimal getBigDecimal(String var1);

    public byte[] getBytes(String var1);

    public Object getValue(String var1);

    public <T> T getValue(String var1, Class<T> var2);

    public Object getRawValue(String var1);

    public boolean isNull(int var1);

    public boolean getBoolean(int var1);

    public byte getByte(int var1);

    public short getShort(int var1);

    public int getInt(int var1);

    public long getLong(int var1);

    public float getFloat(int var1);

    public double getDouble(int var1);

    public char getChar(int var1);

    public String getString(int var1);

    public Date getDate(int var1);

    public UUID getUUID(int var1);

    public BigDecimal getBigDecimal(int var1);

    public byte[] getBytes(int var1);

    public Object getValue(int var1);

    public <T> T getValue(int var1, Class<T> var2);

    public Object getRawValue(int var1);

    public void setValue(int var1, Object var2);

    public List<CheckFieldResult> checkFieldValue(String var1, Object var2);

    public void setValueWithCheck(int var1, Object var2);

    public void setValue(String var1, Object var2);

    public void setValueWithCheck(String var1, Object var2);

    public Map<String, Object> getUpdateData();

    public Map<String, Object> getData();

    public List<Object> getFrontData();

    public void setData(Map<String, Object> var1);

    public void setData(List<Object> var1, List<Object> var2);

    public void setDataWithCheck(Map<String, Object> var1);

    public void setDataWithCheck(List<Object> var1, List<Object> var2);

    public int getIndex();

    public boolean isModified(String var1);

    public boolean isModified(int var1);

    public void setCalcValue(String var1, List<Object> var2);

    public List<CheckResult> setValueWithFormulaCheck(String var1, Object var2);

    public List<CheckResult> setValueWithFormulaCheck(int var1, Object var2);
}

