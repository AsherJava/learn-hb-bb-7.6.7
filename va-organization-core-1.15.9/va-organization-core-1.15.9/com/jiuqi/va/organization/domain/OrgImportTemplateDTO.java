/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.mapper.domain.PageDTO
 */
package com.jiuqi.va.organization.domain;

import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.mapper.domain.PageDTO;
import com.jiuqi.va.organization.domain.OrgImportTemplateDO;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrgImportTemplateDTO
extends OrgImportTemplateDO
implements PageDTO {
    private static final long serialVersionUID = 1L;
    private boolean pagination;
    private int offset;
    private int limit;
    private String searchKey;
    private String resultKey;
    private Object versionDate;
    private String rootCode;
    private int showStopped = 0;
    private Map<String, List<OrgDTO>> importDataMap;
    private OrgImportTemplateDO tempImportTemplate;

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

    public Map<String, List<OrgDTO>> getImportDataMap() {
        return this.importDataMap;
    }

    public void setImportDataMap(Map<String, List<OrgDTO>> importDataMap) {
        this.importDataMap = importDataMap;
    }

    public String getResultKey() {
        return this.resultKey;
    }

    public void setResultKey(String resultKey) {
        this.resultKey = resultKey;
    }

    public Object getVersionDate() {
        if (this.versionDate == null) {
            return null;
        }
        if (this.versionDate instanceof Date) {
            return (Date)this.versionDate;
        }
        if (this.versionDate instanceof Number) {
            this.versionDate = new Date(((Number)this.versionDate).longValue());
        } else if (this.versionDate instanceof String) {
            if (this.versionDate.toString().indexOf("-", 1) > 0) {
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                this.versionDate = sf.format((String)this.versionDate);
            } else {
                this.versionDate = new Date(new BigDecimal((String)this.versionDate).longValue());
            }
        }
        return (Date)this.versionDate;
    }

    public void setVersionDate(Object versionDate) {
        this.versionDate = versionDate;
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

    public OrgImportTemplateDO getTempImportTemplate() {
        return this.tempImportTemplate;
    }

    public void setTempImportTemplate(OrgImportTemplateDO tempImportTemplate) {
        this.tempImportTemplate = tempImportTemplate;
    }
}

