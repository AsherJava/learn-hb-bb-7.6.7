/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 */
package nr.single.map.configurations.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.map.configurations.bean.UnitCustomMapping;
import nr.single.map.configurations.deserializer.UnitMappingDeserializer;

@JsonDeserialize(using=UnitMappingDeserializer.class)
public class UnitMapping
implements Serializable {
    private static final long serialVersionUID = 5279654505993148420L;
    public static final String UNIT_INFOS = "unitInfos";
    public static final String PERIOD_MAPPING = "periodMapping";
    private List<UnitCustomMapping> unitInfos;
    private Map<String, String> periodMapping;

    public UnitMapping() {
    }

    public UnitMapping(List<UnitCustomMapping> unitInfos, Map<String, String> periodMapping) {
        this.unitInfos = unitInfos;
        this.periodMapping = periodMapping;
    }

    public List<UnitCustomMapping> getUnitInfos() {
        return this.unitInfos;
    }

    public void setUnitInfos(List<UnitCustomMapping> unitInfos) {
        this.unitInfos = unitInfos;
    }

    public Map<String, String> getPeriodMapping() {
        return this.periodMapping;
    }

    public void setPeriodMapping(Map<String, String> periodMapping) {
        this.periodMapping = periodMapping;
    }

    public void addPeriod(String key, String value) {
        if (this.periodMapping == null) {
            this.periodMapping = new HashMap<String, String>();
        }
        if (key == null) {
            throw new IllegalArgumentException("\u65f6\u671f\u6620\u5c04\u7684\u952e\u4e0d\u80fd\u4e3a\u7a7a.");
        }
        this.periodMapping.put(key, value);
    }
}

