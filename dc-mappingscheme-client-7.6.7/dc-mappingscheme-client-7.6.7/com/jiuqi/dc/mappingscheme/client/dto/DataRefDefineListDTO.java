/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.client.dto;

import java.io.Serializable;

public class DataRefDefineListDTO
implements Serializable {
    private static final long serialVersionUID = 5164916814384640745L;
    private String dataSchemeCode;
    private String code;
    private String searchKey;

    public DataRefDefineListDTO() {
    }

    public DataRefDefineListDTO(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public DataRefDefineListDTO(String dataSchemeCode, String code) {
        this.dataSchemeCode = dataSchemeCode;
        this.code = code;
    }

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSearchKey() {
        return this.searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
}

