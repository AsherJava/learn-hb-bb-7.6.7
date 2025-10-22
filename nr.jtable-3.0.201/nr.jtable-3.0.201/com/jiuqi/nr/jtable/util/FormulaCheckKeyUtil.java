/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.UUID;

public class FormulaCheckKeyUtil {
    public static String buildOldDesKey(String formSchemeKey, String formulaSchemeKey, String formKey, String formulaKey, int globRow, int globCol, Map<String, DimensionValue> dimensionSet) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        for (DimensionValue value : dimensionSet.values()) {
            dimensionValueSet.setValue(value.getName(), (Object)value.getValue());
        }
        if (dimensionValueSet.hasValue("RECORDKEY")) {
            Object value = dimensionValueSet.getValue("RECORDKEY");
            dimensionValueSet.clearValue("RECORDKEY");
            dimensionValueSet.setValue("ID", value);
        } else if (!dimensionValueSet.hasValue("ID")) {
            dimensionValueSet.setValue("ID", (Object)"null");
        }
        if (!dimensionValueSet.hasValue("VERSIONID")) {
            dimensionValueSet.setValue("VERSIONID", (Object)"00000000-0000-0000-0000-000000000000");
        }
        if (dimensionValueSet.hasValue("ALLCKR_ASYNCTASKID")) {
            dimensionValueSet.clearValue("ALLCKR_ASYNCTASKID");
        }
        String masterStr = formSchemeKey + ";" + formulaSchemeKey + ";" + formKey + ";" + formulaKey + ";" + globRow + ";" + globCol + ";" + dimensionValueSet.toString();
        return FormulaCheckKeyUtil.tofakeUUID(masterStr).toString();
    }

    public static UUID tofakeUUID(String string) {
        int i;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException nsae) {
            throw new InternalError("MD5 not supported", nsae);
        }
        byte[] data = md.digest(string.getBytes());
        long msb = 0L;
        long lsb = 0L;
        assert (data.length == 16) : "data must be 16 bytes in length";
        for (i = 0; i < 8; ++i) {
            msb = msb << 8 | (long)(data[i] & 0xFF);
        }
        for (i = 8; i < 16; ++i) {
            lsb = lsb << 8 | (long)(data[i] & 0xFF);
        }
        return new UUID(msb, lsb);
    }

    public static String buildDesKey(String formSchemeKey, String formulaSchemeKey, String formKey, String formulaKey, int globRow, int globCol, Map<String, DimensionValue> dimensionSet) {
        String formulaID = formulaKey.substring(0, 36);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        for (DimensionValue value : dimensionSet.values()) {
            dimensionValueSet.setValue(value.getName(), (Object)value.getValue());
        }
        if (dimensionValueSet.hasValue("RECORDKEY")) {
            Object value = dimensionValueSet.getValue("RECORDKEY");
            dimensionValueSet.clearValue("RECORDKEY");
            dimensionValueSet.setValue("ID", value);
        } else if (!dimensionValueSet.hasValue("ID")) {
            dimensionValueSet.setValue("ID", (Object)"null");
        }
        if (!dimensionValueSet.hasValue("VERSIONID")) {
            dimensionValueSet.setValue("VERSIONID", (Object)"00000000-0000-0000-0000-000000000000");
        }
        if (dimensionValueSet.hasValue("ALLCKR_ASYNCTASKID")) {
            dimensionValueSet.clearValue("ALLCKR_ASYNCTASKID");
        }
        String masterStr = formSchemeKey + ";" + formulaSchemeKey + ";" + formKey + ";" + formulaID + ";" + DataEngineConsts.FormulaType.CHECK.getValue() + ";" + globRow + ";" + globCol + ";" + dimensionValueSet.toString();
        return FormulaCheckKeyUtil.tofakeUUID(masterStr).toString();
    }
}

