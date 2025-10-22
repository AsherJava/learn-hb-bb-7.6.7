/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.builder.EqualsBuilder
 *  org.apache.commons.lang3.builder.HashCodeBuilder
 */
package com.jiuqi.nr.bpm.de.dataflow.step.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class BatchNoOperate {
    private String id;
    private String code;
    private String name;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BatchNoOperate)) {
            return false;
        }
        BatchNoOperate batchNoOperate = (BatchNoOperate)o;
        return new EqualsBuilder().append((Object)this.id, (Object)batchNoOperate.getId()).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 37).append((Object)this.id).toHashCode();
    }
}

