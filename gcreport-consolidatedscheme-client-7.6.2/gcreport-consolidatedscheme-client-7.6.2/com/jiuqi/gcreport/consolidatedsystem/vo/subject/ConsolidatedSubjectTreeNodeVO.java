/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.consolidatedsystem.vo.subject;

import com.jiuqi.gcreport.consolidatedsystem.vo.subject.GcConsolidatedSubjectVo;
import java.util.ArrayList;
import java.util.List;

public class ConsolidatedSubjectTreeNodeVO {
    public static final String DATATYPE_ROOT = "root";
    public static final String DATATYPE_FOLDER = "folder";
    public static final String DATATYPE_LEAF = "leaf";
    private String dataType;
    private Boolean expand;
    private Boolean startFlag;
    private Boolean edit;
    private String editTitle;
    private Boolean loaded;
    private List<ConsolidatedSubjectTreeNodeVO> children;
    private String id;
    private String code;
    private String title;
    private String ordinal;
    private String parentCode;
    private Boolean consolidationFlag;
    private Integer attri;
    private Integer orient;

    public ConsolidatedSubjectTreeNodeVO() {
    }

    public ConsolidatedSubjectTreeNodeVO(GcConsolidatedSubjectVo eo) {
        this.id = eo.getId();
        this.code = eo.getCode();
        this.title = new StringBuffer("[").append(eo.getCode()).append("] ").append(eo.getTitle()).toString();
        this.ordinal = eo.getSortOrder();
        this.parentCode = eo.getParentCode();
        this.consolidationFlag = eo.getConsolidationFlag();
        this.edit = false;
        this.editTitle = "";
        this.attri = eo.getAttri();
        this.orient = eo.getOrient();
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Boolean getExpand() {
        if (this.expand == null) {
            return false;
        }
        return this.expand;
    }

    public void setExpand(Boolean expand) {
        this.expand = expand;
    }

    public Boolean getStartFlag() {
        return this.startFlag;
    }

    public void setStartFlag(Boolean startFlag) {
        this.startFlag = startFlag;
    }

    public Boolean getEdit() {
        return this.edit;
    }

    public void setEdit(Boolean edit) {
        this.edit = edit;
    }

    public String getEditTitle() {
        return this.editTitle;
    }

    public void setEditTitle(String editTitle) {
        this.editTitle = editTitle;
    }

    public Boolean getLoaded() {
        if (this.loaded == null) {
            return false;
        }
        return this.loaded;
    }

    public void setLoaded(Boolean loaded) {
        this.loaded = loaded;
    }

    public List<ConsolidatedSubjectTreeNodeVO> getChildren() {
        if (this.children == null) {
            this.children = new ArrayList<ConsolidatedSubjectTreeNodeVO>();
        }
        return this.children;
    }

    public void setChildren(List<ConsolidatedSubjectTreeNodeVO> children) {
        this.children = children;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(String ordinal) {
        this.ordinal = ordinal;
    }

    public String getParentCode() {
        return this.parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Boolean getConsolidationFlag() {
        if (this.consolidationFlag != null) {
            return this.consolidationFlag;
        }
        return true;
    }

    public void setConsolidationFlag(Boolean consolidationFlag) {
        this.consolidationFlag = consolidationFlag;
    }

    public Integer getAttri() {
        return this.attri;
    }

    public void setAttri(Integer attri) {
        this.attri = attri;
    }

    public Integer getOrient() {
        return this.orient;
    }

    public void setOrient(Integer orient) {
        this.orient = orient;
    }
}

