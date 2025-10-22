/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.enumcheck.utils;

import com.jiuqi.bi.util.StringUtils;
import java.util.HashMap;
import java.util.HashSet;

public class EnumFilterCondition {
    private HashMap<String, String> skipFormDic = new HashMap();
    private HashMap<String, String> skipZBDic = new HashMap();
    private HashSet<String> fmlDic = new HashSet();

    public void init(String enumFilterStr) {
        if (StringUtils.isEmpty((String)enumFilterStr)) {
            return;
        }
        for (String item : enumFilterStr.split(";")) {
            int idxE = (item = item.trim()).indexOf(61);
            if (idxE > 0) {
                String formCode = item.substring(0, idxE);
                if (!formCode.contains("[")) {
                    this.skipFormDic.put(formCode, item.substring(idxE + 1));
                    continue;
                }
                this.skipZBDic.put(formCode, item.substring(idxE + 1));
                continue;
            }
            this.skipFormDic.put(item, null);
        }
        for (String fml : this.skipFormDic.values()) {
            if (!StringUtils.isNotEmpty((String)fml) || this.fmlDic.contains(fml)) continue;
            this.fmlDic.add(fml);
        }
        for (String fml : this.skipZBDic.values()) {
            if (!StringUtils.isNotEmpty((String)fml) || this.fmlDic.contains(fml)) continue;
            this.fmlDic.add(fml);
        }
    }

    public boolean isFilter(String formCode) {
        return this.skipFormDic.containsKey(formCode);
    }

    public String getFromFilterFml(String formCode) {
        return this.skipFormDic.get(formCode);
    }

    public String getZbFilterFml(String zbFullCode) {
        return this.skipZBDic.get(zbFullCode);
    }

    public HashSet<String> getAllFilterFml() {
        return this.fmlDic;
    }
}

