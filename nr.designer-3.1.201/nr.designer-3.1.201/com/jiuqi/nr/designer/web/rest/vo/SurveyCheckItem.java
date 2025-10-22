/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.nr.survey.model.Choice
 *  com.jiuqi.nr.survey.model.ValueBean
 *  com.jiuqi.nr.survey.model.common.QuestionType
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.designer.web.rest.vo.SurveyDataFieldVO;
import com.jiuqi.nr.designer.web.rest.vo.SurveyErrorType;
import com.jiuqi.nr.survey.model.Choice;
import com.jiuqi.nr.survey.model.ValueBean;
import com.jiuqi.nr.survey.model.common.QuestionType;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import java.io.Serializable;
import java.util.List;

public class SurveyCheckItem
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int number;
    private String id;
    private String name;
    private String title;
    private QuestionType type;
    private SurveyErrorType errorType;
    private boolean readOnly;
    private List<String> zb;
    private String linkId;
    private List<Choice> choices;
    private SurveyDataFieldVO dataField;
    private BaseDataDefineDTO baseDataDefine;
    @JsonIgnore
    private ValueBean obj;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public QuestionType getType() {
        return this.type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public SurveyErrorType getErrorType() {
        return this.errorType;
    }

    public void setErrorType(SurveyErrorType errorType) {
        this.errorType = errorType;
    }

    public List<String> getZb() {
        return this.zb;
    }

    public void setZb(List<String> zb) {
        this.zb = zb;
    }

    public String getLinkId() {
        return this.linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public List<Choice> getChoices() {
        return this.choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SurveyDataFieldVO getDataField() {
        return this.dataField;
    }

    public void setDataField(SurveyDataFieldVO dataField) {
        this.dataField = dataField;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public BaseDataDefineDTO getBaseDataDefine() {
        return this.baseDataDefine;
    }

    public void setBaseDataDefine(BaseDataDefineDTO baseDataDefine) {
        this.baseDataDefine = baseDataDefine;
    }

    public ValueBean getObj() {
        return this.obj;
    }

    public void setObj(ValueBean obj) {
        this.obj = obj;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
        result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        SurveyCheckItem other = (SurveyCheckItem)obj;
        if (this.id == null ? other.id != null : !this.id.equals(other.id)) {
            return false;
        }
        return !(this.name == null ? other.name != null : !this.name.equals(other.name));
    }
}

