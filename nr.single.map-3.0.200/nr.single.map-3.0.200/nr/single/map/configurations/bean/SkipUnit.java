/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 */
package nr.single.map.configurations.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.util.List;
import nr.single.map.configurations.deserializer.SkipUnitDeserializer;

@JsonDeserialize(using=SkipUnitDeserializer.class)
public class SkipUnit
implements Serializable {
    private static final long serialVersionUID = 2785432824575071029L;
    public static final String UNITKEY_CODE = "unitKey";
    public static final String FORMULA_CODE = "formula";
    private List<String> unitKey;
    private String formula;

    public List<String> getUnitKey() {
        return this.unitKey;
    }

    public void setUnitKey(List<String> unitKey) {
        this.unitKey = unitKey;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }
}

