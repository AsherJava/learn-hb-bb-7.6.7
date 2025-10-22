/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.file;

import java.util.HashMap;
import java.util.Map;

public class SingleFileInfoContain {
    private Map<String, Object> infoList = new HashMap<String, Object>();

    public Map<String, Object> getInfoList() {
        return this.infoList;
    }

    public String readString(String Section2, String Ident2, String Default) {
        Map list;
        String rcode = Default;
        if (null != this.infoList.get(Section2) && null != (list = (Map)this.infoList.get(Section2)).get(Ident2)) {
            rcode = (String)list.get(Ident2);
        }
        return rcode;
    }

    public void writeString(String Section2, String Ident2, String Value) {
        Map<String, String> list = null;
        if (null != this.infoList.get(Section2)) {
            list = (Map)this.infoList.get(Section2);
        } else {
            list = new HashMap();
            this.infoList.put(Section2, list);
        }
        if (null != list.get(Ident2)) {
            list.remove(Ident2);
            list.put(Ident2, Value);
        } else {
            list.put(Ident2, Value);
        }
    }

    public String getCombineText() {
        String rcode = "";
        for (Map.Entry<String, Object> entry : this.infoList.entrySet()) {
            String key = entry.getKey();
            Object val = entry.getValue();
            rcode = rcode + '[' + key + ']' + '\r' + '\n';
            Map list = (Map)val;
            for (Map.Entry entry2 : list.entrySet()) {
                String key2 = (String)entry2.getKey();
                Object val2 = entry2.getValue();
                rcode = rcode + key2 + '=' + (String)val2 + '\r' + '\n';
            }
            rcode = rcode + '\r' + '\n';
        }
        return rcode;
    }

    public void setCombineText(String Value) {
    }

    public void clearCombineText() {
        this.infoList.clear();
    }
}

