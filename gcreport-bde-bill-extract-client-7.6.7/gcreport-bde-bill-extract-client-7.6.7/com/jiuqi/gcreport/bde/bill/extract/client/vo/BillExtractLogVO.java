/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.gcreport.bde.bill.extract.client.vo;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.bde.bill.extract.client.vo.BillExtractItemLogVO;
import java.util.List;
import java.util.stream.Collectors;

public class BillExtractLogVO {
    private String errorLog;
    private List<BillExtractItemLogVO> itemLogs = CollectionUtils.newArrayList();

    public BillExtractLogVO(Integer taskNum) {
    }

    public BillExtractLogVO() {
    }

    public List<BillExtractItemLogVO> getItemLogs() {
        return this.itemLogs;
    }

    public void addItemLog(BillExtractItemLogVO itemLog) {
        Assert.isNotNull((Object)itemLog);
        this.itemLogs.add(itemLog);
    }

    public Integer getTaskNum() {
        return this.itemLogs.size();
    }

    public Integer getSuccessNum() {
        return this.itemLogs.stream().filter(item -> Boolean.TRUE.equals(item.getSuccess())).collect(Collectors.toList()).size();
    }

    public Integer getFailedNum() {
        return this.itemLogs.stream().filter(item -> !Boolean.TRUE.equals(item.getSuccess())).collect(Collectors.toList()).size();
    }

    public String getErrorLog() {
        return this.errorLog;
    }

    public void setErrorLog(String errorLog) {
        this.errorLog = errorLog;
    }
}

