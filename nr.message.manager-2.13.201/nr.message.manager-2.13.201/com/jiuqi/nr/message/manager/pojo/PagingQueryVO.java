/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.message.manager.pojo;

import com.jiuqi.nr.message.manager.pojo.MessageVO;
import java.util.List;

@Deprecated
public class PagingQueryVO {
    private Integer totalRows;
    private Integer curPage;
    private Integer pageSize;
    private Integer totalPages;
    private Integer startIndex;
    private List<MessageVO> listVO;

    public PagingQueryVO(Integer totalRows, Integer curPage, Integer pageSize) {
        this.totalRows = totalRows;
        this.pageSize = pageSize;
        this.totalPages = totalRows % pageSize == 0 ? totalRows / pageSize : totalRows / pageSize + 1;
        this.curPage = curPage < 1 || curPage > this.totalPages ? 1 : curPage;
        this.startIndex = (curPage - 1) * pageSize;
    }

    public Integer getTotalRows() {
        return this.totalRows;
    }

    public Integer getCurPage() {
        return this.curPage;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public Integer getTotalPages() {
        return this.totalPages;
    }

    public Integer getStartIndex() {
        return this.startIndex;
    }

    public List<MessageVO> getListVO() {
        return this.listVO;
    }

    public void setListVO(List<MessageVO> listVO) {
        this.listVO = listVO;
    }

    public String toString() {
        return "PagingQueryVO [totalRows=" + this.totalRows + ", curPage=" + this.curPage + ", pageSize=" + this.pageSize + ", totalPages=" + this.totalPages + ", startIndex=" + this.startIndex + ", listVO=" + this.listVO + "]";
    }
}

