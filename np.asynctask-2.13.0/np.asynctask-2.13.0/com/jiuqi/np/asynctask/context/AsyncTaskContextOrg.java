/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.np.core.context.ContextOrganization
 */
package com.jiuqi.np.asynctask.context;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.np.core.context.ContextOrganization;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AsyncTaskContextOrg
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
}

