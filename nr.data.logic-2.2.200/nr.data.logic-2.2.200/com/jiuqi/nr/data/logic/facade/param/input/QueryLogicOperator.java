/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.param.input;

import com.jiuqi.nr.data.logic.facade.param.input.QueryContext;
import com.jiuqi.nr.data.logic.facade.param.input.QueryVariable;

public enum QueryLogicOperator implements QueryVariable
{
    AND{

        @Override
        public String toSql(QueryContext queryContext) {
            return "AND";
        }

        @Override
        public String toFml(QueryContext queryContext) {
            return "AND";
        }
    }
    ,
    OR{

        @Override
        public String toSql(QueryContext queryContext) {
            return "OR";
        }

        @Override
        public String toFml(QueryContext queryContext) {
            return "OR";
        }
    };

}

