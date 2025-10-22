/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package nr.midstore2.data.extension.bean;

import com.jiuqi.nr.common.params.DimensionValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReportDimEntityCache {
    private Set<String> entitySingleDims;
    private List<String> entitySingleIds;
    private Map<String, String> entitySingleDimAndFields;
    private Map<String, String> entityDimAndEntityIds;
    private Map<String, DimensionValue> singleDimValueSet;
    private Map<String, Map<String, DimensionValue>> entitySingleDimValues;
    private Map<String, Map<String, DimensionValue>> entitySingleDimList;

    public Set<String> getEntitySingleDims() {
        if (this.entitySingleDims == null) {
            this.entitySingleDims = new HashSet<String>();
        }
        return this.entitySingleDims;
    }

    public void setEntitySingleDims(Set<String> entitySingleDims) {
        this.entitySingleDims = entitySingleDims;
    }

    public List<String> getEntitySingleIds() {
        if (this.entitySingleIds == null) {
            this.entitySingleIds = new ArrayList<String>();
        }
        return this.entitySingleIds;
    }

    public void setEntitySingleIds(List<String> entitySingleIds) {
        this.entitySingleIds = entitySingleIds;
    }

    public Map<String, Map<String, DimensionValue>> getEntitySingleDimList() {
        if (this.entitySingleDimList == null) {
            this.entitySingleDimList = new HashMap<String, Map<String, DimensionValue>>();
        }
        return this.entitySingleDimList;
    }

    public void setEntitySingleDimList(Map<String, Map<String, DimensionValue>> entitySingleDimList) {
        this.entitySingleDimList = entitySingleDimList;
    }

    public Map<String, Map<String, DimensionValue>> getEntitySingleDimValues() {
        if (this.entitySingleDimValues == null) {
            this.entitySingleDimValues = new HashMap<String, Map<String, DimensionValue>>();
        }
        return this.entitySingleDimValues;
    }

    public void setEntitySingleDimValues(Map<String, Map<String, DimensionValue>> entitySingleDimValues) {
        this.entitySingleDimValues = entitySingleDimValues;
    }

    public Map<String, String> getEntitySingleDimAndFields() {
        if (this.entitySingleDimAndFields == null) {
            this.entitySingleDimAndFields = new HashMap<String, String>();
        }
        return this.entitySingleDimAndFields;
    }

    public void setEntitySingleDimAndFields(Map<String, String> entitySingleDimAndFields) {
        this.entitySingleDimAndFields = entitySingleDimAndFields;
    }

    public Map<String, String> getEntityDimAndEntityIds() {
        if (this.entityDimAndEntityIds == null) {
            this.entityDimAndEntityIds = new HashMap<String, String>();
        }
        return this.entityDimAndEntityIds;
    }

    public void setEntityDimAndEntityIds(Map<String, String> entityDimAndEntityIds) {
        this.entityDimAndEntityIds = entityDimAndEntityIds;
    }

    public Map<String, DimensionValue> getSingleDimValueSet() {
        if (this.singleDimValueSet == null) {
            this.singleDimValueSet = new HashMap<String, DimensionValue>();
        }
        return this.singleDimValueSet;
    }

    public void setSingleDimValueSet(Map<String, DimensionValue> singleDimValueSet) {
        this.singleDimValueSet = singleDimValueSet;
    }
}

