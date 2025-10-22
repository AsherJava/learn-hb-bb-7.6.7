/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Identifiable
 */
package com.jiuqi.nr.query.dataset.Authority;

import com.jiuqi.np.authz2.Identifiable;

public class DataSetResource
implements Identifiable {
    private String id;
    private String type;
    private String title;

    public DataSetResource() {
    }

    public DataSetResource(String id, String type, String title) {
        this.id = id;
        this.type = type;
        this.title = title;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

