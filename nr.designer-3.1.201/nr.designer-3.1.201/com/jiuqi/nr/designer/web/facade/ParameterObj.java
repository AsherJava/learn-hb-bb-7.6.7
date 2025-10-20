/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.designer.web.facade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class ParameterObj {
    @JsonProperty
    private String name;
    @JsonProperty
    private String title;
    @JsonProperty
    private List<Integer> dataTypes;
    @JsonProperty
    private boolean canOmit;

    @JsonIgnore
    public ParameterObj(String name, String title, int dataType, boolean canOmit) {
        this.name = name;
        this.title = title;
        this.dataTypes = new ArrayList<Integer>();
        this.dataTypes.add(dataType);
        this.canOmit = canOmit;
    }

    @JsonIgnore
    public ParameterObj(String name, String title) {
        this.name = name;
        this.title = title;
    }

    @JsonIgnore
    public String getName() {
        return this.name;
    }

    @JsonIgnore
    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public String getTitle() {
        return this.title;
    }

    @JsonIgnore
    public void setTitle(String title) {
        this.title = title;
    }
}

