/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.multcheck2.web.vo;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.multcheck2.provider.MultcheckContext;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

public class MultcheckEnvContext
extends MultcheckContext
implements Serializable {
    private Map<String, DimensionValue> dimSetMap;

    public MultcheckEnvContext() {
    }

    public MultcheckEnvContext(MultcheckEnvContext context) {
        BeanUtils.copyProperties(context, this);
        this.dimSetMap = new HashMap<String, DimensionValue>();
        if (!CollectionUtils.isEmpty(context.dimSetMap)) {
            for (String dim : context.dimSetMap.keySet()) {
                this.dimSetMap.put(dim, new DimensionValue(context.dimSetMap.get(dim)));
            }
        }
        this.setDims(null);
    }

    public Map<String, DimensionValue> getDimSetMap() {
        return this.dimSetMap;
    }

    public void setDimSetMap(Map<String, DimensionValue> dimSetMap) {
        this.dimSetMap = dimSetMap;
    }
}

