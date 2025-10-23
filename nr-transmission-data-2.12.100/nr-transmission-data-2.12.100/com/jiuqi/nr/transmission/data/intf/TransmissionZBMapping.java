/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.mapping2.bean.ZBMapping
 */
package com.jiuqi.nr.transmission.data.intf;

import com.jiuqi.nr.mapping2.bean.ZBMapping;
import org.springframework.util.StringUtils;

public class TransmissionZBMapping {
    private String key;
    private String msKey;
    private String form;
    private String regionCode;
    private String table;
    private String zbCode;
    private String desTable;
    private String mapping;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMsKey() {
        return this.msKey;
    }

    public void setMsKey(String msKey) {
        this.msKey = msKey;
    }

    public String getForm() {
        return this.form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getRegionCode() {
        return this.regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getTable() {
        return this.table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getZbCode() {
        return this.zbCode;
    }

    public void setZbCode(String zbCode) {
        this.zbCode = zbCode;
    }

    public String getDesTable() {
        return this.desTable;
    }

    public void setDesTable(String desTable) {
        this.desTable = desTable;
    }

    public String getMapping() {
        return this.mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public static TransmissionZBMapping getTransmissionZBMappingImport(ZBMapping zBMapping) {
        TransmissionZBMapping transmissionZBMapping = new TransmissionZBMapping();
        transmissionZBMapping.setKey(zBMapping.getKey());
        transmissionZBMapping.setMsKey(zBMapping.getMsKey());
        transmissionZBMapping.setForm(zBMapping.getForm());
        transmissionZBMapping.setRegionCode(zBMapping.getRegionCode());
        transmissionZBMapping.setMapping(zBMapping.getZbCode());
        transmissionZBMapping.setDesTable(zBMapping.getTable());
        String mapping = zBMapping.getMapping();
        if (StringUtils.hasText(mapping)) {
            int startIndex = -1;
            int endIndex = mapping.indexOf("]");
            startIndex = mapping.indexOf("[");
            if (startIndex > -1 && endIndex > startIndex) {
                transmissionZBMapping.setZbCode(mapping.substring(startIndex + 1, endIndex));
                transmissionZBMapping.setTable(mapping.substring(0, startIndex));
                return transmissionZBMapping;
            }
        }
        transmissionZBMapping.setZbCode(mapping);
        transmissionZBMapping.setTable(zBMapping.getTable());
        return transmissionZBMapping;
    }

    public static TransmissionZBMapping getTransmissionZBMappingExport(ZBMapping zBMapping) {
        TransmissionZBMapping transmissionZBMapping = new TransmissionZBMapping();
        transmissionZBMapping.setKey(zBMapping.getKey());
        transmissionZBMapping.setMsKey(zBMapping.getMsKey());
        transmissionZBMapping.setForm(zBMapping.getForm());
        transmissionZBMapping.setRegionCode(zBMapping.getRegionCode());
        transmissionZBMapping.setZbCode(zBMapping.getZbCode());
        transmissionZBMapping.setTable(zBMapping.getTable());
        String mapping = zBMapping.getMapping();
        if (StringUtils.hasText(mapping)) {
            int startIndex = -1;
            int endIndex = mapping.indexOf("]");
            startIndex = mapping.indexOf("[");
            if (startIndex > -1 && endIndex > startIndex) {
                transmissionZBMapping.setDesTable(mapping.substring(0, startIndex));
                transmissionZBMapping.setMapping(mapping.substring(startIndex + 1, endIndex));
                return transmissionZBMapping;
            }
        }
        transmissionZBMapping.setDesTable(zBMapping.getTable());
        transmissionZBMapping.setMapping(mapping);
        return transmissionZBMapping;
    }
}

