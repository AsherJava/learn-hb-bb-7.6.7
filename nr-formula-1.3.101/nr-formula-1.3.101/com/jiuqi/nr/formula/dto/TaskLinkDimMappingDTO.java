/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.dto;

import java.util.ArrayList;
import java.util.List;
import org.springframework.util.StringUtils;

public class TaskLinkDimMappingDTO {
    private String dimKey;
    private String dimData;

    public static List<TaskLinkDimMappingDTO> toMappings(String dimRule) {
        ArrayList<TaskLinkDimMappingDTO> mappings = new ArrayList<TaskLinkDimMappingDTO>();
        if (!StringUtils.hasText(dimRule)) {
            return mappings;
        }
        String[] dimRuleObjs = dimRule.split("/");
        for (int i = 0; i < dimRuleObjs.length; ++i) {
            String dimRuleObj = dimRuleObjs[i];
            String[] dimAndDimData = dimRuleObj.split(";");
            TaskLinkDimMappingDTO mapping = new TaskLinkDimMappingDTO();
            mapping.setDimKey(dimAndDimData[0]);
            mapping.setDimData(dimAndDimData[1]);
            mappings.add(mapping);
        }
        return mappings;
    }

    public String getDimKey() {
        return this.dimKey;
    }

    public void setDimKey(String dimKey) {
        this.dimKey = dimKey;
    }

    public String getDimData() {
        return this.dimData;
    }

    public void setDimData(String dimData) {
        this.dimData = dimData;
    }

    public String toString() {
        StringBuffer string = new StringBuffer();
        string.append(this.dimKey);
        string.append(";");
        string.append(this.dimData);
        string.append("/");
        return string.toString();
    }
}

