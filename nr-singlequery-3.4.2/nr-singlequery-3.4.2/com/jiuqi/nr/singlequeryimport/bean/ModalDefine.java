/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.singlequeryimport.bean;

import com.jiuqi.nr.singlequeryimport.bean.Blocks;
import com.jiuqi.nr.singlequeryimport.bean.QueryInfo;

public class ModalDefine {
    private String ShareType;
    private String FunType;
    private String Title;
    private String ModalId;
    private String GroupId;
    private QueryInfo QueryInfo;
    private Blocks Blocks;

    public String getShareType() {
        return this.ShareType;
    }

    public void setShareType(String ShareType) {
        this.ShareType = ShareType;
    }

    public String getFunType() {
        return this.FunType;
    }

    public void setFunType(String FunType) {
        this.FunType = FunType;
    }

    public String getTitle() {
        return this.Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getModalId() {
        return this.ModalId;
    }

    public void setModalId(String ModalId) {
        this.ModalId = ModalId;
    }

    public String getGroupId() {
        return this.GroupId;
    }

    public void setGroupId(String GroupId) {
        this.GroupId = GroupId;
    }

    public QueryInfo getQueryInfo() {
        return this.QueryInfo;
    }

    public void setQueryInfo(QueryInfo QueryInfo2) {
        this.QueryInfo = QueryInfo2;
    }

    public Blocks getBlocks() {
        return this.Blocks;
    }

    public void setBlocks(Blocks Blocks2) {
        this.Blocks = Blocks2;
    }
}

