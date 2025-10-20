/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextOrganization
 */
package com.jiuqi.nvwa.login.domain;

import com.jiuqi.np.core.context.ContextOrganization;
import java.io.Serializable;
import java.util.Objects;

public class NvwaContextOrg
implements ContextOrganization,
Serializable {
    private static final long serialVersionUID = 1L;
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

    public int hashCode() {
        return Objects.hash(this.code, this.id, this.name);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        NvwaContextOrg other = (NvwaContextOrg)obj;
        return Objects.equals(this.code, other.code) && Objects.equals(this.id, other.id) && Objects.equals(this.name, other.name);
    }
}

