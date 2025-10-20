/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 */
package com.jiuqi.gcreport.basedata.impl.init.data;

import com.jiuqi.va.domain.datamodel.DataModelColumn;
import java.util.List;
import java.util.Map;

public class VaBaseDataInitDefine {
    private String name;
    private String title;
    private String groupname;
    private Integer structtype;
    private String groupfieldname;
    private Integer sharetype;
    private String sharefieldname;
    private String levelcode;
    private Integer dimensionflag;
    private Integer versionflag;
    private Integer authflag;
    private Integer actauthflag;
    private Integer inspecttitle;
    private Integer readonly;
    private Integer solidifyflag;
    private Integer cachedisabled;
    private Integer dummyflag;
    private String sqldefine;
    private List<DataModelColumn> columns;
    private List<Map<String, Object>> showFields;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroupname() {
        return this.groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public Integer getStructtype() {
        return this.structtype;
    }

    public void setStructtype(Integer structtype) {
        this.structtype = structtype;
    }

    public String getGroupfieldname() {
        return this.groupfieldname;
    }

    public void setGroupfieldname(String groupfieldname) {
        this.groupfieldname = groupfieldname;
    }

    public Integer getSharetype() {
        return this.sharetype;
    }

    public void setSharetype(Integer sharetype) {
        this.sharetype = sharetype;
    }

    public String getSharefieldname() {
        return this.sharefieldname;
    }

    public void setSharefieldname(String sharefieldname) {
        this.sharefieldname = sharefieldname;
    }

    public String getLevelcode() {
        return this.levelcode;
    }

    public void setLevelcode(String levelcode) {
        this.levelcode = levelcode;
    }

    public Integer getDimensionflag() {
        return this.dimensionflag;
    }

    public void setDimensionflag(Integer dimensionflag) {
        this.dimensionflag = dimensionflag;
    }

    public Integer getVersionflag() {
        return this.versionflag;
    }

    public void setVersionflag(Integer versionflag) {
        this.versionflag = versionflag;
    }

    public Integer getAuthflag() {
        return this.authflag;
    }

    public void setAuthflag(Integer authflag) {
        this.authflag = authflag;
    }

    public Integer getActauthflag() {
        return this.actauthflag;
    }

    public void setActauthflag(Integer actauthflag) {
        this.actauthflag = actauthflag;
    }

    public Integer getInspecttitle() {
        return this.inspecttitle;
    }

    public void setInspecttitle(Integer inspecttitle) {
        this.inspecttitle = inspecttitle;
    }

    public Integer getReadonly() {
        return this.readonly;
    }

    public void setReadonly(Integer readonly) {
        this.readonly = readonly;
    }

    public Integer getSolidifyflag() {
        return this.solidifyflag;
    }

    public void setSolidifyflag(Integer solidifyflag) {
        this.solidifyflag = solidifyflag;
    }

    public Integer getCachedisabled() {
        return this.cachedisabled;
    }

    public void setCachedisabled(Integer cachedisabled) {
        this.cachedisabled = cachedisabled;
    }

    public Integer getDummyflag() {
        return this.dummyflag;
    }

    public void setDummyflag(Integer dummyflag) {
        this.dummyflag = dummyflag;
    }

    public String getSqldefine() {
        return this.sqldefine;
    }

    public void setSqldefine(String sqldefine) {
        this.sqldefine = sqldefine;
    }

    public List<DataModelColumn> getColumns() {
        return this.columns;
    }

    public void setColumns(List<DataModelColumn> columns) {
        this.columns = columns;
    }

    public List<Map<String, Object>> getShowFields() {
        return this.showFields;
    }

    public void setShowFields(List<Map<String, Object>> showFields) {
        this.showFields = showFields;
    }
}

