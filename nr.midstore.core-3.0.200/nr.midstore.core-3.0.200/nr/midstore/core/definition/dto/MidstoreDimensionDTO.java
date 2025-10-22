/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package nr.midstore.core.definition.dto;

import com.jiuqi.nr.common.params.DimensionValue;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MidstoreDimensionDTO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }
}

