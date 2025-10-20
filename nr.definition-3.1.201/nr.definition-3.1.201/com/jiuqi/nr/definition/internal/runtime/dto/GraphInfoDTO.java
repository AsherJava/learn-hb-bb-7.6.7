/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.dto;

import com.jiuqi.nr.definition.internal.runtime.dto.GraphGroupDTO;
import java.util.Objects;

public class GraphInfoDTO {
    private final String key;
    private final String name;
    private long size;
    private GraphGroupDTO group;

    public GraphInfoDTO(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return this.key;
    }

    public String getName() {
        return this.name;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public GraphGroupDTO getGroup() {
        return this.group;
    }

    protected void setGroup(GraphGroupDTO group) {
        this.group = group;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GraphInfoDTO)) {
            return false;
        }
        GraphInfoDTO that = (GraphInfoDTO)o;
        return Objects.equals(this.key, that.key) && Objects.equals(this.name, that.name);
    }

    public int hashCode() {
        return Objects.hash(this.key, this.name);
    }
}

