/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.journalsingle.vo;

import com.jiuqi.gcreport.journalsingle.vo.JournalPostRuleVO;
import java.util.ArrayList;
import java.util.List;

public class JournalPageVO<T> {
    private int count;
    private int pageSize;
    private int pageNum;
    private List<T> details = new ArrayList<T>();
    private List<JournalPostRuleVO> list = new ArrayList<JournalPostRuleVO>();

    public JournalPageVO(int count, List<T> details) {
        this.count = count;
        this.details = details;
    }

    public JournalPageVO(int count, int pageSize, int pageNum, List<JournalPostRuleVO> list) {
        this.count = count;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.list = list;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getDetails() {
        return this.details;
    }

    public void setDetails(List<T> details) {
        this.details = details;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<JournalPostRuleVO> getList() {
        return this.list;
    }

    public void setList(List<JournalPostRuleVO> list) {
        this.list = list;
    }
}

