/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.dc.base.impl.ncell.org.vo;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

public class OrgTypeVO
implements Serializable {
    private static final long serialVersionUID = 6214176462079313639L;
    private String id;
    @NotNull(message="\u5355\u4f4d\u7c7b\u578b\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u5355\u4f4d\u7c7b\u578b\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a") String name;
    @NotNull(message="\u5355\u4f4d\u7c7b\u578b\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u5355\u4f4d\u7c7b\u578b\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a") String title;
    private String description;
    private String table;
    private String extinfo;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTable() {
        return this.table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtinfo() {
        return this.extinfo;
    }

    public void setExtinfo(String extinfo) {
        this.extinfo = extinfo;
    }
}

