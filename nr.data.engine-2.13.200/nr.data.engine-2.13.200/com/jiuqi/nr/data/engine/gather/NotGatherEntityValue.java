/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.dataengine.common.Convert
 */
package com.jiuqi.nr.data.engine.gather;

import com.jiuqi.nr.data.engine.gather.GatherEntityValue;
import com.jiuqi.nvwa.dataengine.common.Convert;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class NotGatherEntityValue
extends GatherEntityValue {
    private Map<String, Set<String>> entityForms = new HashMap<String, Set<String>>();
    private Map<String, Set<String>> formEntities;
    private List<String> nidValues = new ArrayList<String>();
    private List<String> nfidValues = new ArrayList<String>();

    public void setEntityForms(Map<String, Set<String>> entityForms) {
        this.entityForms = entityForms;
    }

    public NotGatherEntityValue parse() {
        if (!CollectionUtils.isEmpty(this.entityForms) && this.nidValues.isEmpty()) {
            for (Map.Entry<String, Set<String>> entry : this.entityForms.entrySet()) {
                Set<String> forms = entry.getValue();
                for (String formKey : forms) {
                    this.nidValues.add(entry.getKey());
                }
                this.nfidValues.addAll(forms.stream().map(e -> e.toUpperCase()).collect(Collectors.toList()));
            }
        }
        return this;
    }

    @Override
    public List<String> getIdValues() {
        return this.nidValues;
    }

    @Override
    public List<String> getAllTempColumns() {
        ArrayList<String> columns = new ArrayList<String>();
        columns.add("NT_ID");
        columns.add("NT_FID");
        columns.add("EXECUTION_ID");
        columns.add("TIME_");
        return columns;
    }

    @Override
    public Object[] getColumnValues(int index, boolean isUUID, String executionId) {
        Object[] values = new Object[9];
        values[0] = isUUID ? Convert.toUUID((String)this.nidValues.get(index)) : this.nidValues.get(index);
        values[1] = isUUID ? Convert.toUUID((String)this.nfidValues.get(index)) : this.nfidValues.get(index);
        values[2] = executionId;
        values[3] = new Timestamp(System.currentTimeMillis());
        int dimIndex = 4;
        for (Map.Entry entry : this.dimValues.entrySet()) {
            values[dimIndex] = CollectionUtils.isEmpty(this.dimValues) ? Integer.valueOf(0) : ((List)this.dimValues.get(entry.getKey())).get(index);
            ++dimIndex;
        }
        int extra = 5 - this.dimValues.size();
        if (extra > 0) {
            for (int i = 0; i < extra; ++i) {
                values[dimIndex] = null;
                ++dimIndex;
            }
        }
        return values;
    }

    public Map<String, Set<String>> getNotGatherUnitByFormKey() {
        if (this.formEntities == null) {
            this.formEntities = new HashMap<String, Set<String>>();
            for (Map.Entry<String, Set<String>> entry : this.entityForms.entrySet()) {
                String unit = entry.getKey();
                Set<String> forms = entry.getValue();
                for (String form : forms) {
                    this.formEntities.computeIfAbsent(form, k -> new HashSet()).add(unit);
                }
            }
        }
        return this.formEntities;
    }
}

