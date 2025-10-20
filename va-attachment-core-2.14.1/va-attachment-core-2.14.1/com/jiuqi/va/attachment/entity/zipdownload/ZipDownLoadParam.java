/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.SchemeEntity
 */
package com.jiuqi.va.attachment.entity.zipdownload;

import com.jiuqi.va.attachment.domain.SchemeEntity;
import java.util.List;
import java.util.Map;

public class ZipDownLoadParam {
    private Map<String, SchemeEntity> schemeEntityMap;
    private String pathName;
    private String folderName;
    private List<String> downLoadInfoList;

    public Map<String, SchemeEntity> getSchemeEntityMap() {
        return this.schemeEntityMap;
    }

    public void setSchemeEntityMap(Map<String, SchemeEntity> schemeEntityMap) {
        this.schemeEntityMap = schemeEntityMap;
    }

    public String getPathName() {
        return this.pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public List<String> getDownLoadInfoList() {
        return this.downLoadInfoList;
    }

    public void setDownLoadInfoList(List<String> downLoadInfoList) {
        this.downLoadInfoList = downLoadInfoList;
    }

    public String getFolderName() {
        return this.folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String toString() {
        return "ZipDownLoadParam{schemeEntityMap=" + this.schemeEntityMap + ", pathName='" + this.pathName + '\'' + ", folderName='" + this.folderName + '\'' + ", downLoadInfoList=" + this.downLoadInfoList + '}';
    }
}

