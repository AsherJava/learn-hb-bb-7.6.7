/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model.link;

import com.jiuqi.nr.survey.model.Element;
import com.jiuqi.nr.survey.model.MatrixdynamicQuestion;
import com.jiuqi.nr.survey.model.cell.CellColumn;
import com.jiuqi.nr.survey.model.common.QuestionCellType;
import com.jiuqi.nr.survey.model.common.QuestionType;
import com.jiuqi.nr.survey.model.define.IChoicesQuestion;
import com.jiuqi.nr.survey.model.link.ISurveyQuestionLinkProvider;
import com.jiuqi.nr.survey.model.link.SurveyQuestion;
import com.jiuqi.nr.survey.model.link.SurveyQuestionLink;
import java.util.ArrayList;
import java.util.List;

public class MatrixdynamicQuestionLinkProviderImpl
implements ISurveyQuestionLinkProvider {
    @Override
    public boolean accept(Element element) {
        return QuestionType.MATRIXDYNAMIC.value().equals(element.getType());
    }

    @Override
    public SurveyQuestion getLinks(Element element) {
        MatrixdynamicQuestion question = (MatrixdynamicQuestion)element;
        SurveyQuestion surveyQuestion = new SurveyQuestion();
        surveyQuestion.setName(question.getName());
        surveyQuestion.setType(QuestionType.MATRIXDYNAMIC);
        List<CellColumn> columns = question.getColumns();
        if (null != columns && !columns.isEmpty()) {
            ArrayList<SurveyQuestionLink> links = new ArrayList<SurveyQuestionLink>();
            surveyQuestion.setLinks(links);
            for (CellColumn cellColumn : columns) {
                String name = cellColumn.getName();
                List<String> zb = cellColumn.getZb();
                String linkId = cellColumn.getLinkId();
                String cellType = cellColumn.getCellType();
                QuestionType questionType = null;
                String filterFormula = "";
                if (QuestionCellType.DROPDOWN.value().equals(cellType)) {
                    questionType = QuestionType.DROPDOWN;
                    filterFormula = ((IChoicesQuestion)((Object)element)).getFilterFormula();
                } else if (QuestionCellType.CHECKBOX.value().equals(cellType)) {
                    questionType = QuestionType.CHECKBOX;
                    filterFormula = ((IChoicesQuestion)((Object)element)).getFilterFormula();
                } else if (QuestionCellType.RADIOGROUP.value().equals(cellType)) {
                    questionType = QuestionType.RADIOGROUP;
                    filterFormula = ((IChoicesQuestion)((Object)element)).getFilterFormula();
                } else if (QuestionCellType.TAGBOX.value().equals(cellType)) {
                    questionType = QuestionType.TAGBOX;
                    filterFormula = ((IChoicesQuestion)((Object)element)).getFilterFormula();
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
                } else if (QuestionCellType.FILE.value().equals(cellType)) {
                    questionType = QuestionType.FILE;
                } else if (QuestionCellType.FILEPOOL.value().equals(cellType)) {
                    questionType = QuestionType.FILEPOOL;
                }
                String title = null != cellColumn.getTitle() && cellColumn.getTitle().length() > 0 ? cellColumn.getTitle() : cellColumn.getName();
                SurveyQuestionLink link = new SurveyQuestionLink(cellColumn, questionType, name, zb, linkId, title);
                link.setFilterFormula(filterFormula);
                link.setMatrix(true);
                links.add(link);
            }
        }
        return surveyQuestion;
    }
}

