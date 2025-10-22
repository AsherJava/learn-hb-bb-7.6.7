/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.vo;

import java.util.List;
import nr.single.map.configurations.vo.CommonEnumMapping;

public class EnumItemMappingVO {
    private String configKey;
    private String enumCode;
    private List<CommonEnumMapping> enumItems;

    public String getConfigKey() {
        return this.configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getEnumCode() {
        return this.enumCode;
    }

    public void setEnumCode(String enumCode) {
        this.enumCode = enumCode;
    }

    public List<CommonEnumMapping> getEnumItems() {
        return this.enumItems;
    }

    public void setEnumItems(List<CommonEnumMapping> enumItems) {
        this.enumItems = enumItems;
    }
}

