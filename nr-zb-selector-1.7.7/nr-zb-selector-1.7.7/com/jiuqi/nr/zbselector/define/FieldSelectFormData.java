/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 */
package com.jiuqi.nr.zbselector.define;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.zbselector.define.FieldSelectFormDataDeserializer;
import com.jiuqi.nr.zbselector.define.FieldSelectFormDataSerializer;
import com.jiuqi.nr.zbselector.service.impl.FieldSelectLinksData;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonSerialize(using=FieldSelectFormDataSerializer.class)
@JsonDeserialize(using=FieldSelectFormDataDeserializer.class)
public class FieldSelectFormData {
    private static final Logger log = LoggerFactory.getLogger(FieldSelectFormData.class);
    public static final String CONST_FORMKEY = "formkey";
    public static final String CONST_REGIONS = "regions";
    public static final String CONST_LINKES = "links";
    public static final String CONST_GRIDDATA = "griddata";
    private String formKey;
    private List<String> regions;
    private Map<String, List<FieldSelectLinksData>> links;
    private byte[] griddata;

    public void setFormKey(String key) {
        this.formKey = key;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }

    public void setRegions(String regions) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, new Class[]{String.class});
            this.regions = (List)objectMapper.readValue(regions, javaType);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public List<String> getRegions() {
        return this.regions;
    }

    public void setLinks(Map<String, List<FieldSelectLinksData>> links) {
        this.links = links;
    }

    public void setLinks(String links) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(Map.class, new Class[]{List.class, FieldSelectLinksData.class});
            this.links = (Map)objectMapper.readValue(links, javaType);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public Map<String, List<FieldSelectLinksData>> getLinks() {
        return this.links;
    }

    public void setGridData(byte[] griddata) {
        this.griddata = griddata;
    }

    public byte[] getGridData() {
        return this.griddata == null ? new byte[]{} : this.griddata;
    }
}

