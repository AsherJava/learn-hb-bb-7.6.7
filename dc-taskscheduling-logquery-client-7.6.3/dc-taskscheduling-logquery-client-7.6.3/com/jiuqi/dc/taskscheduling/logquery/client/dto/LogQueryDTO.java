/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.intf.impl.DcTenantDTO
 */
package com.jiuqi.dc.taskscheduling.logquery.client.dto;

import com.jiuqi.dc.base.common.intf.impl.DcTenantDTO;
import com.jiuqi.dc.taskscheduling.logquery.client.vo.DataHandleLogVO;
import java.util.List;

public class LogQueryDTO
extends DcTenantDTO {
    private static final long serialVersionUID = -8088024797093835427L;
    private String id;
    private List<String> unitCodes;
    private Integer acctYear;
    private Integer acctPeriod;
    private Integer page;
    private Integer pageSize;
    private List<DataHandleLogVO> logList;
    private int totalNum;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getUnitCodes() {
        return this.unitCodes;
    }

    public void setUnitCodes(List<String> unitCodes) {
        this.unitCodes = unitCodes;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public Integer getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(Integer acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<DataHandleLogVO> getLogList() {
        return this.logList;
    }

    public void setLogList(List<DataHandleLogVO> logList) {
        this.logList = logList;
    }

    public int getTotalNum() {
        return this.totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }
}

