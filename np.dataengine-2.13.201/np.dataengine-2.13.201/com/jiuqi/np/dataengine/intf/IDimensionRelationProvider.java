/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public interface IDimensionRelationProvider {
    default public List<String> getRelationValuesByDim(ExecutorContext context, String dimension, String mainDimValue, String period) {
        return null;
    }

    default public List<String> getRelationValuesByDim(ExecutorContext context, String dimension, String mainDimValue, String period, String linkAlias) {
        if (StringUtils.isEmpty((CharSequence)linkAlias)) {
            return this.getRelationValuesByDim(context, dimension, mainDimValue, period);
        }
        return null;
    }

    default public Map<String, List<String>> getAllRelationValuesByDim(ExecutorContext context, String dimension, List<String> mainDimValues, String period) {
        HashMap<String, List<String>> allRelationDimValues = new HashMap<String, List<String>>();
        for (String mainDimValue : mainDimValues) {
            List<String> relationDimValues = this.getRelationValuesByDim(context, dimension, mainDimValue, period);
            if (relationDimValues == null) continue;
            allRelationDimValues.put(mainDimValue, relationDimValues);
        }
        return allRelationDimValues;
    }

    default public Map<String, List<String>> getAllRelationValuesByDim(ExecutorContext context, String dimension, List<String> mainDimValues, String period, String linkAlias) {
        if (StringUtils.isEmpty((CharSequence)linkAlias)) {
            return this.getAllRelationValuesByDim(context, dimension, mainDimValues, period);
        }
        return null;
    }

    default public boolean is1v1RelationDim(ExecutorContext context, String dimension, String linkAlias) {
        return false;
    }
}

