/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonSubTypes
 *  com.fasterxml.jackson.annotation.JsonSubTypes$Type
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$As
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 */
package com.jiuqi.nr.survey.model.cell;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jiuqi.nr.survey.model.Element;
import com.jiuqi.nr.survey.model.cell.BooleanQuestionCell;
import com.jiuqi.nr.survey.model.cell.CheckBoxQuestionCell;
import com.jiuqi.nr.survey.model.cell.CommentQuestionCell;
import com.jiuqi.nr.survey.model.cell.DropDownQuestionCell;
import com.jiuqi.nr.survey.model.cell.ExpressionQuestionCell;
import com.jiuqi.nr.survey.model.cell.FileQuestionCell;
import com.jiuqi.nr.survey.model.cell.NumberQuestionCell;
import com.jiuqi.nr.survey.model.cell.PeriodQuestionCell;
import com.jiuqi.nr.survey.model.cell.RadioGroupQuestionCell;
import com.jiuqi.nr.survey.model.cell.RatingQuestionCell;
import com.jiuqi.nr.survey.model.cell.TagBoxQuestionCell;
import com.jiuqi.nr.survey.model.cell.TextQuestionCell;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="cellType", visible=true)
@JsonSubTypes(value={@JsonSubTypes.Type(value=DropDownQuestionCell.class, name="dropdown"), @JsonSubTypes.Type(value=CheckBoxQuestionCell.class, name="checkbox"), @JsonSubTypes.Type(value=RadioGroupQuestionCell.class, name="radiogroup"), @JsonSubTypes.Type(value=TagBoxQuestionCell.class, name="tagbox"), @JsonSubTypes.Type(value=TextQuestionCell.class, name="text"), @JsonSubTypes.Type(value=CommentQuestionCell.class, name="comment"), @JsonSubTypes.Type(value=BooleanQuestionCell.class, name="boolean"), @JsonSubTypes.Type(value=ExpressionQuestionCell.class, name="expression"), @JsonSubTypes.Type(value=RatingQuestionCell.class, name="rating"), @JsonSubTypes.Type(value=NumberQuestionCell.class, name="number"), @JsonSubTypes.Type(value=PeriodQuestionCell.class, name="period"), @JsonSubTypes.Type(value=FileQuestionCell.class, name="file")})
public class CellColumn
extends Element {
    private String cellType;

    public String getCellType() {
        return this.cellType;
    }

    public void setCellType(String cellType) {
        this.cellType = cellType;
    }
}

