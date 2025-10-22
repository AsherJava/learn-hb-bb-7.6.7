/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 */
package nr.single.map.configurations.internal.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.util.List;
import nr.single.map.configurations.bean.MappingConfig;
import nr.single.map.configurations.bean.RuleMap;
import nr.single.map.configurations.bean.UnitMapping;
import nr.single.map.configurations.deserializer.HandleSavePojoDeserializer;
import nr.single.map.data.internal.SingleFileFieldInfoImpl;
import nr.single.map.data.internal.SingleFileFormulaItemImpl;

@JsonDeserialize(using=HandleSavePojoDeserializer.class)
public class HandleSavePojo
implements Serializable {
    private static final long serialVersionUID = -5782862089980070932L;
    public static final String MAPPING_KEY = "mappingConfigKey";
    public static final String FORMULASCHEME_KEY = "formulaSchemeKey";
    public static final String FORMULA_INFOS = "formulaInfos";
    public static final String ZB_FIELDS = "zbFields";
    public static final String MAP_RULE = "mapRule";
    public static final String UNIT_MAPPING = "mapping";
    public static final String CONFIG_NAME = "config";
    public static final String FORMCODE_NAME = "formCode";
    private String mappingConfigKey;
    private String formulaSchemeKey;
    private String formCode;
    private List<SingleFileFormulaItemImpl> formulaInfos;
    private List<SingleFileFieldInfoImpl> zbFields;
    private List<RuleMap> mapRule;
    private UnitMapping mapping;
    private MappingConfig config;

    public HandleSavePojo() {
    }

    public HandleSavePojo(String mappingConfigKey, String formulaSchemeKey, String formCode, List<SingleFileFormulaItemImpl> formulaInfos, List<SingleFileFieldInfoImpl> zbFields, List<RuleMap> mapRule, UnitMapping mapping, MappingConfig config) {
        this.formulaSchemeKey = formulaSchemeKey;
        this.mappingConfigKey = mappingConfigKey;
        this.formCode = formCode;
        this.formulaInfos = formulaInfos;
        this.zbFields = zbFields;
        this.mapRule = mapRule;
        this.mapping = mapping;
        this.config = config;
    }

    public static long getSerialVersionUID() {
        return -5782862089980070932L;
    }

    public String getMappingConfigKey() {
        return this.mappingConfigKey;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public void setMappingConfigKey(String mappingConfigKey) {
        this.mappingConfigKey = mappingConfigKey;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public List<SingleFileFormulaItemImpl> getFormulaInfos() {
        return this.formulaInfos;
    }

    public void setFormulaInfos(List<SingleFileFormulaItemImpl> formulaInfos) {
        this.formulaInfos = formulaInfos;
    }

    public List<SingleFileFieldInfoImpl> getZbFields() {
        return this.zbFields;
    }

    public void setZbFields(List<SingleFileFieldInfoImpl> zbFields) {
        this.zbFields = zbFields;
    }

    public List<RuleMap> getMapRule() {
        return this.mapRule;
    }

    public void setMapRule(List<RuleMap> mapRule) {
        this.mapRule = mapRule;
    }

    public UnitMapping getMapping() {
        return this.mapping;
    }

    public void setMapping(UnitMapping mapping) {
        this.mapping = mapping;
    }

    public MappingConfig getConfig() {
        return this.config;
    }

    public void setConfig(MappingConfig config) {
        this.config = config;
    }
}

