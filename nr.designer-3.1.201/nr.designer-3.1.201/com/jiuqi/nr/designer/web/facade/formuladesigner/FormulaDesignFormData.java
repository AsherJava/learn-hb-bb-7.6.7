/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.util.LineProp
 */
package com.jiuqi.nr.designer.web.facade.formuladesigner;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.util.LineProp;
import com.jiuqi.nr.designer.bean.IBaseData;
import com.jiuqi.nr.designer.web.facade.formuladesigner.FormulaDesignFormDataDeserializer;
import com.jiuqi.nr.designer.web.facade.formuladesigner.FormulaDesignFormDataSerializer;
import com.jiuqi.nr.designer.web.facade.formuladesigner.FormulaDesignLinksData;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonSerialize(using=FormulaDesignFormDataSerializer.class)
@JsonDeserialize(using=FormulaDesignFormDataDeserializer.class)
public class FormulaDesignFormData {
    private static final Logger log = LoggerFactory.getLogger(FormulaDesignFormData.class);
    public static final String CONST_FORMKEY = "formkey";
    public static final String CONST_LINKES = "links";
    public static final String CONST_GRIDDATA = "griddata";
    public static final String CONST_REGIONS = "regions";
    public static final String CONST_ENUMS = "enums";
    public static final String CONST_FIELD = "field";
    public static final String CONST_LINEPROPS = "LINEPROPS";
    private String formKey;
    private List<FormulaDesignLinksData> links;
    private byte[] griddata;
    private List<DataRegionDefine> regions;
    private List<IBaseData> enums;
    private String field;
    private List<LineProp> propList;

    public List<LineProp> getPropList() {
        return this.propList;
    }

    public void setPropList(List<LineProp> propList) {
        this.propList = propList;
    }

    public void setPropList(String propList) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, new Class[]{LineProp.class});
            this.propList = (List)objectMapper.readValue(propList, javaType);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<IBaseData> getEnums() {
        return this.enums;
    }

    public void setEnums(List<IBaseData> enums) {
        this.enums = enums;
    }

    public void setEnums(String enums) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, new Class[]{IBaseData.class});
            this.enums = (List)objectMapper.readValue(enums, javaType);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public List<DataRegionDefine> getRegions() {
        return this.regions;
    }

    public void setRegions(List<DataRegionDefine> regions) {
        this.regions = regions;
    }

    public void setRegions(String regions) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, new Class[]{DataRegionDefine.class});
            this.regions = (List)objectMapper.readValue(regions, javaType);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void setFormKey(String key) {
        this.formKey = key;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setLinks(List<FormulaDesignLinksData> links) {
        this.links = links;
    }

    public void setLinks(String links) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, new Class[]{FormulaDesignLinksData.class});
            this.links = (List)objectMapper.readValue(links, javaType);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public List<FormulaDesignLinksData> getLinks() {
        return this.links;
    }

    public void setGridData(byte[] griddata) {
        this.griddata = griddata;
    }

    public byte[] getGridData() {
        return this.griddata == null ? new byte[]{} : this.griddata;
    }
}

