/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.mapper.domain.PageDTO
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.workflow.domain;

import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.mapper.domain.PageDTO;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;

public class WorkflowExtendQueryDTO
extends TenantDO
implements PageDTO {
    private static final long serialVersionUID = 7413094842384368036L;
    private String unitcode;
    private List<String> unitCodeList;
    private boolean querySubordinateAndSelf;
    private String mappingTable;
    private String hashKey;
    private int offset;
    private int limit;
    private boolean pagination;
    private String searchKey;
    private BaseDataDTO baseDataDTO;

    public String getUnitcode() {
        return this.unitcode;
    }

    public void setUnitcode(String unitcode) {
        this.unitcode = unitcode;
    }

    public List<String> getUnitCodeList() {
        return this.unitCodeList;
    }

    public void setUnitCodeList(List<String> unitCodeList) {
        this.unitCodeList = unitCodeList;
    }

    public boolean isQuerySubordinateAndSelf() {
        return this.querySubordinateAndSelf;
    }

    public void setQuerySubordinateAndSelf(boolean querySubordinateAndSelf) {
        this.querySubordinateAndSelf = querySubordinateAndSelf;
    }

    public String getMappingTable() {
        return this.mappingTable;
    }

    public void setMappingTable(String mappingTable) {
        this.mappingTable = mappingTable;
    }

    public String getHashKey() {
        return this.hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
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

    public boolean isPagination() {
        return this.pagination;
    }

    public void setPagination(boolean pagination) {
        this.pagination = pagination;
    }

    public String getSearchKey() {
        return this.searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public BaseDataDTO getBaseDataDTO() {
        return this.baseDataDTO;
    }

    public void setBaseDataDTO(BaseDataDTO baseDataDTO) {
        this.baseDataDTO = baseDataDTO;
    }
}

