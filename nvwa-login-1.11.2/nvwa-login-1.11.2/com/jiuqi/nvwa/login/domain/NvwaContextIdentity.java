/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextIdentity
 */
package com.jiuqi.nvwa.login.domain;

import com.jiuqi.np.core.context.ContextIdentity;
import java.io.Serializable;
import java.util.Objects;

public class NvwaContextIdentity
implements ContextIdentity,
Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private String orgCode;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public int hashCode() {
        return Objects.hash(this.id, this.orgCode, this.title);
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
        NvwaContextIdentity other = (NvwaContextIdentity)obj;
        return Objects.equals(this.id, other.id) && Objects.equals(this.orgCode, other.orgCode) && Objects.equals(this.title, other.title);
    }
}

