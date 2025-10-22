/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.param.input;

import com.jiuqi.nr.data.logic.facade.param.input.CheckResultObj;
import com.jiuqi.nr.data.logic.facade.param.input.QueryContext;
import com.jiuqi.nr.data.logic.facade.param.input.QueryVariable;
import com.jiuqi.nr.data.logic.facade.param.output.CheckDescription;
import com.jiuqi.nr.data.logic.internal.entity.FmlCheckResultEntity;

public enum QueryCol implements QueryVariable
{
    FORMULA_CHECK_TYPE{

        @Override
        public String toSql(QueryContext queryContext) {
            if (queryContext.isAllCheck()) {
                return queryContext.getCkrTableAlia() + "." + "ALLCKR_FORMULACHECKTYPE";
            }
            return queryContext.getCkrTableAlia() + "." + "CKR_FORMULACHECKTYPE";
        }

        @Override
        public String toFml(QueryContext queryContext) {
            if (queryContext.isAllCheck()) {
                return queryContext.getCkrTableName() + "[" + "ALLCKR_FORMULACHECKTYPE" + "]";
            }
            return queryContext.getCkrTableName() + "[" + "CKR_FORMULACHECKTYPE" + "]";
        }

        @Override
        public Object getColData(CheckResultObj checkResultData) {
            FmlCheckResultEntity fmlCheckResultEntity = checkResultData.getFmlCheckResultEntity();
            return fmlCheckResultEntity == null ? null : Integer.valueOf(fmlCheckResultEntity.getFormulaCheckType());
        }
    }
    ,
    CHECK_ERROR_DES{

        @Override
        public String toSql(QueryContext queryContext) {
            return queryContext.getCkdTableAlia() + "." + "CKD_DESCRIPTION";
        }

        @Override
        public String toFml(QueryContext queryContext) {
            return queryContext.getCkdTableName() + "[" + "CKD_DESCRIPTION" + "]";
        }

        @Override
        public Object getColData(CheckResultObj checkResultData) {
            CheckDescription checkDescription = checkResultData.getCheckDescription();
            return checkDescription == null ? null : checkDescription.getDescription();
        }
    }
    ,
    ERROR_DES_STATE{

        @Override
        public String toSql(QueryContext queryContext) {
            return queryContext.getCkdTableAlia() + "." + "CKD_STATE";
        }

        @Override
        public String toFml(QueryContext queryContext) {
            return queryContext.getCkdTableName() + "[" + "CKD_STATE" + "]";
        }

        @Override
        public Object getColData(CheckResultObj checkResultData) {
            CheckDescription checkDescription = checkResultData.getCheckDescription();
            return checkDescription == null ? null : checkDescription.getState();
        }
    };


    abstract Object getColData(CheckResultObj var1);
}

