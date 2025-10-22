/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data;

import java.util.HashMap;
import java.util.Map;

public class SingleDataEnumInfo {
    private String enumCode;
    private Map<String, String> singleMapNetItems;
    private Map<String, String> netMapSingleItems;

    public String getEnumCode() {
        return this.enumCode;
    }

    public void setEnumCode(String enumCode) {
        this.enumCode = enumCode;
    }

    public Map<String, String> getSingleMapNetItems() {
        if (this.singleMapNetItems == null) {
            this.singleMapNetItems = new HashMap<String, String>();
        }
        return this.singleMapNetItems;
    }

    public void setSingleMapNetItems(Map<String, String> singleMapNetItems) {
        this.singleMapNetItems = singleMapNetItems;
    }

    public Map<String, String> getNetMapSingleItems() {
        if (this.netMapSingleItems == null) {
            this.netMapSingleItems = new HashMap<String, String>();
        }
        return this.netMapSingleItems;
    }

    public void setNetMapSingleItems(Map<String, String> netMapSingleItems) {
        this.netMapSingleItems = netMapSingleItems;
    }
}

