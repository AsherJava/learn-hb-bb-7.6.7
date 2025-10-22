/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.np.log.enums.OperLevelSerializer
 */
package com.jiuqi.nr.common.log;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.np.log.enums.OperLevelSerializer;
import com.jiuqi.nr.common.log.NRLogCustomAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class NRLogEntity {
    private String module;
    private String title;
    private String message;
    private NRLogCustomAttribute taskAttribute;
    private List<NRLogCustomAttribute> dwAttributes;
    private NRLogCustomAttribute periodAttribute;
    @JsonSerialize(using=OperLevelSerializer.class)
    private OperLevel operLevel;

    public String getModule() {
        return this.module;
    }

    public NRLogEntity(String module, OperLevel operLevel) {
        this.module = module;
        this.operLevel = operLevel;
    }

    public NRLogEntity(String module, String title, String message, OperLevel operLevel) {
        this.module = module;
        this.title = title;
        this.message = message;
        this.operLevel = operLevel;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OperLevel getOperLevel() {
        return this.operLevel;
    }

    public void setOperLevel(OperLevel operLevel) {
        this.operLevel = operLevel;
    }

    public NRLogCustomAttribute getTaskAttribute() {
        return this.taskAttribute;
    }

    public void setTaskAttribute(NRLogCustomAttribute taskAttribute) {
        this.taskAttribute = taskAttribute;
    }

    public List<NRLogCustomAttribute> getDwAttribute() {
        return this.dwAttributes;
    }

    public void setDwAttribute(List<NRLogCustomAttribute> dwAttributes) {
        this.dwAttributes = dwAttributes;
    }

    public void setDwAttribute(NRLogCustomAttribute dwAttribute) {
        if (this.dwAttributes == null) {
            this.dwAttributes = new ArrayList<NRLogCustomAttribute>();
        }
        this.dwAttributes.add(dwAttribute);
    }

    public NRLogCustomAttribute getPeriodAttribute() {
        return this.periodAttribute;
    }

    public void setPeriodAttribute(NRLogCustomAttribute periodAttribute) {
        this.periodAttribute = periodAttribute;
    }

    public Map<String, String> getCustomMap() {
        HashMap<String, String> map = new HashMap<String, String>(8);
        if (this.taskAttribute != null) {
            map.put("\u4efb\u52a1", this.taskAttribute.toString());
        }
        if (!CollectionUtils.isEmpty(this.dwAttributes)) {
            map.put("\u5355\u4f4d", this.dwAttributes.stream().map(d -> d.toString()).collect(Collectors.joining(",")));
        }
        if (this.periodAttribute != null) {
            map.put("\u65f6\u671f", this.periodAttribute.toString());
        }
        return map;
    }
}

