/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.mapping.bean.BaseDataMapping
 *  org.apache.commons.lang3.StringUtils
 */
package nr.midstore.core.definition.bean.mapping;

import com.jiuqi.nr.mapping.bean.BaseDataMapping;
import java.util.HashMap;
import java.util.Map;
import nr.midstore.core.definition.bean.mapping.EnumItemMappingInfo;
import org.apache.commons.lang3.StringUtils;

public class EnumMappingInfo {
    private BaseDataMapping baseDataMapping;
    private String baseDataCode;
    private String mappingCode;
    private String mappingTitle;
    private Map<String, EnumItemMappingInfo> itemMappings;
    private Map<String, EnumItemMappingInfo> srcItemMappings;

    public EnumMappingInfo() {
    }

    public EnumMappingInfo(BaseDataMapping baseDataMapping) {
        this.baseDataMapping = baseDataMapping;
        this.baseDataCode = baseDataMapping.getBaseDataCode();
        this.mappingCode = baseDataMapping.getMappingCode();
        this.mappingTitle = baseDataMapping.getMappingTitle();
    }

    public void add(EnumItemMappingInfo item) {
        if (StringUtils.isNotEmpty((CharSequence)item.getItemCode()) && !this.getItemMappings().containsKey(item.getItemCode())) {
            this.getItemMappings().put(item.getItemCode(), item);
        }
        if (StringUtils.isNotEmpty((CharSequence)item.getMappingCode()) && !this.getSrcItemMappings().containsKey(item.getMappingCode())) {
            this.getSrcItemMappings().put(item.getMappingCode(), item);
        }
    }

    public BaseDataMapping getBaseDataMapping() {
        return this.baseDataMapping;
    }

    public void setBaseDataMapping(BaseDataMapping baseDataMapping) {
        this.baseDataMapping = baseDataMapping;
    }

    public String getBaseDataCode() {
        return this.baseDataCode;
    }

    public void setBaseDataCode(String baseDataCode) {
        this.baseDataCode = baseDataCode;
    }

    public String getMappingCode() {
        return this.mappingCode;
    }

    public void setMappingCode(String mappingCode) {
        this.mappingCode = mappingCode;
    }

    public String getMappingTitle() {
        return this.mappingTitle;
    }

    public void setMappingTitle(String mappingTitle) {
        this.mappingTitle = mappingTitle;
    }

    public Map<String, EnumItemMappingInfo> getItemMappings() {
        if (this.itemMappings == null) {
            this.itemMappings = new HashMap<String, EnumItemMappingInfo>();
        }
        return this.itemMappings;
    }

    public void setItemMappings(Map<String, EnumItemMappingInfo> itemMappings) {
        this.itemMappings = itemMappings;
    }

    public Map<String, EnumItemMappingInfo> getSrcItemMappings() {
        if (this.srcItemMappings == null) {
            this.srcItemMappings = new HashMap<String, EnumItemMappingInfo>();
        }
        return this.srcItemMappings;
    }

    public void setSrcItemMappings(Map<String, EnumItemMappingInfo> srcItemMappings) {
        this.srcItemMappings = srcItemMappings;
    }
}

