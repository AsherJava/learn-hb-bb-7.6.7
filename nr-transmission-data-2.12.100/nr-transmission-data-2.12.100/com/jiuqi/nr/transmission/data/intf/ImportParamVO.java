/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.intf;

import com.jiuqi.nr.transmission.data.intf.ImportParam;
import java.util.List;

public class ImportParamVO
extends ImportParam {
    private List<String> fileKeyLists;
    private List<String> allFileKeyList;

    public List<String> getFileKeyLists() {
        return this.fileKeyLists;
    }

    public void setFileKeyLists(List<String> fileKeyLists) {
        this.fileKeyLists = fileKeyLists;
    }

    public List<String> getAllFileKeyList() {
        return this.allFileKeyList;
    }

    public void setAllFileKeyList(List<String> allFileKeyList) {
        this.allFileKeyList = allFileKeyList;
    }

    public static ImportParam importParamVoTo(ImportParamVO importParamVO) {
        ImportParam importParam = new ImportParam();
        if (importParamVO != null) {
            importParam.setImportType(importParamVO.getImportType());
            importParam.setExecuteKey(importParamVO.getExecuteKey());
            importParam.setFileKey(importParamVO.getFileKey());
            importParam.setDoUpload(importParamVO.getDoUpload());
            importParam.setAllowForceUpload(importParamVO.getAllowForceUpload());
            importParam.setDescription(importParamVO.getDescription());
            importParam.setUserInfoParam(importParamVO.getUserInfoParam());
            importParam.setMappingSchemeKey(importParamVO.getMappingSchemeKey());
        }
        return importParam;
    }
}

