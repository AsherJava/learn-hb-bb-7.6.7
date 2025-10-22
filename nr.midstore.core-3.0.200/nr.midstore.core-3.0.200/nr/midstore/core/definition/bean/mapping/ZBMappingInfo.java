/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.mapping.bean.ZBMapping
 */
package nr.midstore.core.definition.bean.mapping;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.mapping.bean.ZBMapping;

public class ZBMappingInfo {
    private String fieldKey;
    private DataField DataField;
    private ZBMapping zbMapping;
    private String mapingTable;
    private String mappingzb;

    public ZBMappingInfo() {
    }

    public ZBMappingInfo(ZBMapping zbMapping) {
        this.zbMapping = zbMapping;
        this.mappingzb = zbMapping.getMapping();
        this.mapingTable = "";
        if (StringUtils.isNotEmpty((String)this.mappingzb) && this.mappingzb.contains("[")) {
            int id1 = this.mappingzb.indexOf("[");
            int id2 = this.mappingzb.indexOf("]");
            this.mapingTable = this.mappingzb.substring(0, id1);
            this.mappingzb = this.mappingzb.substring(id1 + 1, id2);
        }
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public DataField getDataField() {
        return this.DataField;
    }

    public void setDataField(DataField dataField) {
        this.DataField = dataField;
    }

    public ZBMapping getZbMapping() {
        return this.zbMapping;
    }

    public void setZbMapping(ZBMapping zbMapping) {
        this.zbMapping = zbMapping;
    }

    public String getMapingTable() {
        return this.mapingTable;
    }

    public void setMapingTable(String mapingTable) {
        this.mapingTable = mapingTable;
    }

    public String getMappingzb() {
        return this.mappingzb;
    }

    public void setMappingzb(String mappingzb) {
        this.mappingzb = mappingzb;
    }
}

