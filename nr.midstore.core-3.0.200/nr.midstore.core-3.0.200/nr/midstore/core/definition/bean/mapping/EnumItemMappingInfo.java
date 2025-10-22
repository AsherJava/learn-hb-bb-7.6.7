/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.mapping.bean.BaseDataItemMapping
 */
package nr.midstore.core.definition.bean.mapping;

import com.jiuqi.nr.mapping.bean.BaseDataItemMapping;

public class EnumItemMappingInfo {
    private BaseDataItemMapping baseItem;
    private String itemCode;
    private String mappingCode;

    public EnumItemMappingInfo() {
    }

    public EnumItemMappingInfo(BaseDataItemMapping item) {
        this.baseItem = item;
        this.itemCode = item.getBaseItemCode();
        this.mappingCode = item.getMappingCode();
    }

    public String getItemCode() {
        return this.itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getMappingCode() {
        return this.mappingCode;
    }

    public void setMappingCode(String mappingCode) {
        this.mappingCode = mappingCode;
    }

    public BaseDataItemMapping getBaseItem() {
        return this.baseItem;
    }

    public void setBaseItem(BaseDataItemMapping baseItem) {
        this.baseItem = baseItem;
    }
}

