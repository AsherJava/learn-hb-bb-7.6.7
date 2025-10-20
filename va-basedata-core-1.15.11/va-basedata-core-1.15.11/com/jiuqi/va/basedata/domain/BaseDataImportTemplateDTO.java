/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.mapper.domain.PageDTO
 */
package com.jiuqi.va.basedata.domain;

import com.jiuqi.va.basedata.domain.BaseDataImportTemplateDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.mapper.domain.PageDTO;
import java.util.List;
import java.util.Map;

public class BaseDataImportTemplateDTO
extends BaseDataImportTemplateDO
implements PageDTO {
    private static final long serialVersionUID = 1L;
    private boolean pagination;
    private int offset;
    private int limit;
    private String searchKey;
    private String resultKey;
    private Map<String, List<BaseDataDTO>> importDataMap;
    private BaseDataImportTemplateDO tempImportTemplate;
    private String unitcode;
    private String rootCode;
    private int showStopped = 0;

    public boolean isPagination() {
        return this.pagination;
    }

    public void setPagination(boolean pagination) {
        this.pagination = pagination;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSearchKey() {
        return this.searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public Map<String, List<BaseDataDTO>> getImportDataMap() {
        return this.importDataMap;
    }

    public void setImportDataMap(Map<String, List<BaseDataDTO>> importDataMap) {
        this.importDataMap = importDataMap;
    }

    public String getResultKey() {
        return this.resultKey;
    }

    public void setResultKey(String resultKey) {
        this.resultKey = resultKey;
    }

    public BaseDataImportTemplateDO getTempImportTemplate() {
        return this.tempImportTemplate;
    }

    public void setTempImportTemplate(BaseDataImportTemplateDO tempImportTemplate) {
        this.tempImportTemplate = tempImportTemplate;
    }

    public String getUnitcode() {
        return this.unitcode;
    }

    public void setUnitcode(String unitcode) {
        this.unitcode = unitcode;
    }

    public String getRootCode() {
        return this.rootCode;
    }

    public void setRootCode(String rootCode) {
        this.rootCode = rootCode;
    }

    public int getShowStopped() {
        return this.showStopped;
    }

    public void setShowStopped(int showStopped) {
        this.showStopped = showStopped;
    }
}

