/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model.link;

import com.jiuqi.nr.survey.model.Element;
import com.jiuqi.nr.survey.model.MatrixfloatQuestion;
import com.jiuqi.nr.survey.model.cell.CellColumn;
import com.jiuqi.nr.survey.model.cell.CellRow;
import com.jiuqi.nr.survey.model.common.QuestionCellType;
import com.jiuqi.nr.survey.model.common.QuestionType;
import com.jiuqi.nr.survey.model.define.IChoicesQuestion;
import com.jiuqi.nr.survey.model.link.ISurveyQuestionLinkProvider;
import com.jiuqi.nr.survey.model.link.SurveyQuestion;
import com.jiuqi.nr.survey.model.link.SurveyQuestionLink;
import java.util.ArrayList;
import java.util.List;

public class MatrixfloatQuestionLinkProviderImpl
implements ISurveyQuestionLinkProvider {
    @Override
    public boolean accept(Element element) {
        return QuestionType.MATRIXFLOAT.value().equals(element.getType());
    }

    @Override
    public SurveyQuestion getLinks(Element element) {
        String linkId;
        List<String> zb;
        String name;
        MatrixfloatQuestion question = (MatrixfloatQuestion)element;
        SurveyQuestion surveyQuestion = new SurveyQuestion();
        surveyQuestion.setName(question.getName());
        surveyQuestion.setType(QuestionType.MATRIXFLOAT);
        List<CellColumn> columns = question.getColumns();
        List<CellRow> rows = question.getRows();
        ArrayList<SurveyQuestionLink> links = new ArrayList<SurveyQuestionLink>();
        surveyQuestion.setLinks(links);
        if (null != columns && !columns.isEmpty()) {
            for (CellColumn cellColumn : columns) {
                name = cellColumn.getName();
                zb = cellColumn.getZb();
                linkId = cellColumn.getLinkId();
                String cellType = cellColumn.getCellType();
                String filterFormula = "";
                QuestionType questionType = null;
                if (QuestionCellType.DROPDOWN.value().equals(cellType)) {
                    questionType = QuestionType.DROPDOWN;
                    filterFormula = ((IChoicesQuestion)((Object)cellColumn)).getFilterFormula();
                } else if (QuestionCellType.CHECKBOX.value().equals(cellType)) {
                    questionType = QuestionType.CHECKBOX;
                    filterFormula = ((IChoicesQuestion)((Object)cellColumn)).getFilterFormula();
                } else if (QuestionCellType.RADIOGROUP.value().equals(cellType)) {
                    questionType = QuestionType.RADIOGROUP;
                    filterFormula = ((IChoicesQuestion)((Object)cellColumn)).getFilterFormula();
                } else if (QuestionCellType.TAGBOX.value().equals(cellType)) {
                    questionType = QuestionType.TAGBOX;
                    filterFormula = ((IChoicesQuestion)((Object)cellColumn)).getFilterFormula();
                } else if (QuestionCellType.TEXT.value().equals(cellType)) {
                    questionType = QuestionType.TEXT;
                } else if (QuestionCellType.COMMENT.value().equals(cellType)) {
                    questionType = QuestionType.COMMENT;
                } else if (QuestionCellType.BOOLEAN.value().equals(cellType)) {
                    questionType = QuestionType.BOOLEAN;
                } else if (QuestionCellType.EXPRESSION.value().equals(cellType)) {
                    questionType = QuestionType.EXPRESSION;
                } else if (QuestionCellType.RATING.value().equals(cellType)) {
                    questionType = QuestionType.RATING;
                } else if (QuestionCellType.NUMBER.value().equals(cellType)) {
                    questionType = QuestionType.NUMBER;
                } else if (QuestionCellType.PERIOD.value().equals(cellType)) {
                    questionType = QuestionType.PERIOD;
                }
                String title = null != cellColumn.getTitle() && cellColumn.getTitle().length() > 0 ? cellColumn.getTitle() : element.getName();
                SurveyQuestionLink link = new SurveyQuestionLink(cellColumn, questionType, name, zb, linkId, title);
                link.setFilterFormula(filterFormula);
                link.setMatrix(true);
                links.add(link);
            }
        }
        if (null != rows && !rows.isEmpty()) {
            for (CellRow cellRow : rows) {
                name = cellRow.getValue();
                zb = cellRow.getZb();
                linkId = cellRow.getLinkId();
                QuestionType questionType = QuestionType.CHECKBOX;
                String title = null != cellRow.getText() && cellRow.getText().length() > 0 ? cellRow.getText() : element.getName();
                SurveyQuestionLink link = new SurveyQuestionLink(cellRow, questionType, name, zb, linkId, title);
                link.setMatrix(true);
                links.add(link);
            }
        }
        return surveyQuestion;
    }
}

