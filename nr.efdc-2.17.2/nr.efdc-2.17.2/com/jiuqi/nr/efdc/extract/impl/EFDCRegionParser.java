/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.extract.impl;

import com.jiuqi.nr.efdc.extract.ExtractDataRegion;
import com.jiuqi.nr.efdc.extract.IRegionParser;

public class EFDCRegionParser
implements IRegionParser {
    @Override
    public ExtractDataRegion parsre(String regionExpression) {
        ExtractDataRegion region = null;
        String[] strs = (regionExpression = regionExpression.substring(1, regionExpression.length() - 1)).split(",");
        if (strs[0].contains("F")) {
            region = new ExtractDataRegion();
            region.setFloat(true);
            region.setRowIndex(Integer.valueOf(strs[0].substring(strs[0].indexOf("F") + 1)));
        } else if (strs[1].contains("F")) {
            region = new ExtractDataRegion();
            region.setFloat(true);
            region.setColIndex(Integer.valueOf(strs[1].substring(strs[0].indexOf("F") + 1)));
        }
        return region;
    }
}

