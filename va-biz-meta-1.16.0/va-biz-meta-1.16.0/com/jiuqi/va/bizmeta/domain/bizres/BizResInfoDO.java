/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Table
 */
package com.jiuqi.va.bizmeta.domain.bizres;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

@Table(name="BIZ_RES_INFO")
public class BizResInfoDO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private Long ver;
    private String groupname;
    private String resname;
    private Integer filesize;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date uploadtime;
    private String etag;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getGroupname() {
        return this.groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getResname() {
        return this.resname;
    }

    public void setResname(String resname) {
        this.resname = resname;
    }

    public Long getVer() {
        return this.ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }

    public Integer getFilesize() {
        return this.filesize;
    }

    public void setFilesize(Integer filesize) {
        this.filesize = filesize;
    }

    public Date getUploadtime() {
        return this.uploadtime;
    }

    public void setUploadtime(Date uploadtime) {
        this.uploadtime = uploadtime;
    }

    public String getEtag() {
        return this.etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }
}

