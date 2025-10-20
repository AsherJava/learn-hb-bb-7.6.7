/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Column
 *  javax.persistence.Id
 *  javax.persistence.Table
 */
package com.jiuqi.va.bizmeta.domain.metagroup;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="META_GROUP")
public class MetaGroupDO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    @Id
    private UUID id;
    private String name;
    private String title;
    @Column(name="VERSIONNO")
    private Long versionNo;
    @Column(name="ROWVERSION")
    private Long rowVersion;
    @Column(name="MODULENAME")
    private String moduleName;
    @Column(name="METATYPE")
    private String metaType;
    @Column(name="PARENTNAME")
    private String parentName;
    @Column(name="UNIQUECODE")
    private String uniqueCode;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
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

    public Long getVersionNo() {
        return this.versionNo;
    }

    public void setVersionNo(long versionNo) {
        this.versionNo = versionNo;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getMetaType() {
        return this.metaType;
    }

    public void setMetaType(String metaType) {
        this.metaType = metaType;
    }

    public String getParentName() {
        return this.parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getUniqueCode() {
        return this.uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public Long getRowVersion() {
        return this.rowVersion;
    }

    public void setRowVersion(Long rowVersion) {
        this.rowVersion = rowVersion;
    }
}

