/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.singlequeryimport.bean;

import com.jiuqi.nr.singlequeryimport.bean.ModalDefines;
import java.util.List;

public class QueryModalGroup {
    private ModalDefines ModalDefines;
    private String ShareType;
    private String FunType;
    private String Title;
    private String Id;
    private String ParentID;
    private List<QueryModalGroup> QueryModalGroup;

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

    public String getId() {
        return this.Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getParentID() {
        return this.ParentID;
    }

    public void setParentID(String ParentID) {
        this.ParentID = ParentID;
    }

    public List<QueryModalGroup> getQueryModalGroup() {
        return this.QueryModalGroup;
    }

    public void setQueryModalGroup(List<QueryModalGroup> queryModalGroup) {
        this.QueryModalGroup = queryModalGroup;
    }

    public ModalDefines getModalDefines() {
        return this.ModalDefines;
    }

    public void setModalDefines(ModalDefines ModalDefines2) {
        this.ModalDefines = ModalDefines2;
    }
}

