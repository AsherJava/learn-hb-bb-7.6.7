/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataReader;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.DataTypes;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ConstantUnit;
import com.jiuqi.np.dataengine.executors.CountStatUnit;
import com.jiuqi.np.dataengine.executors.CurrencyAvgUnit;
import com.jiuqi.np.dataengine.executors.CurrencyMedianUnit;
import com.jiuqi.np.dataengine.executors.CurrencySumUnit;
import com.jiuqi.np.dataengine.executors.EvalExecutor;
import com.jiuqi.np.dataengine.executors.EvalItem;
import com.jiuqi.np.dataengine.executors.FirstStatUnit;
import com.jiuqi.np.dataengine.executors.FloatAvgUnit;
import com.jiuqi.np.dataengine.executors.FloatMedianUnit;
import com.jiuqi.np.dataengine.executors.FloatSumUnit;
import com.jiuqi.np.dataengine.executors.IntAvgUnit;
import com.jiuqi.np.dataengine.executors.IntSumUnit;
import com.jiuqi.np.dataengine.executors.LastStatUnit;
import com.jiuqi.np.dataengine.executors.MaxStatUnit;
import com.jiuqi.np.dataengine.executors.MinStatUnit;
import com.jiuqi.np.dataengine.executors.NoneStatUnit;
import com.jiuqi.np.dataengine.executors.StatRow;
import com.jiuqi.np.dataengine.executors.StatUnit;
import com.jiuqi.np.dataengine.executors.StringSumUnit;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.query.QueryContext;

public class StatItem
implements StatUnit,
DataReader {
    private StatUnit statUnit;
    private EvalItem valueEval = null;
    private EvalItem conditionEval = null;
    private QueryContext qContext;
    private int dataType;
    private int index = -1;
    public static final String[] STAT_KIND_MODE_NAMES = new String[]{"", "SUM", "COUNT", "AVG", "MAX", "MIN", "FIRST", "LAST", "LJ", "DISTINCTCOUNT", "MEDIAN", "CONSTANT"};
    public static final String[] STAT_KIND_MODE_NAMES2 = new String[]{"", "0", "1", "AVG", "2", "3", "4", "5", "LJ", "DISTINCTCOUNT", "MEDIAN", "CONSTANT"};
    public static final String[] STAT_KIND_MODE_TITLES = new String[]{"", "\u6c42\u548c", "\u8ba1\u6570", "\u5e73\u5747", "\u6700\u5927", "\u6700\u5c0f", "\u7b2c\u4e00\u4e2a", "\u6700\u540e\u4e00\u4e2a", "\u7d2f\u79ef", "\u4e0d\u91cd\u590d\u8ba1\u6570", "\u4e2d\u4f4d\u6570", "\u56fa\u5b9a\u503c"};

    public StatItem(int statKind, int dataType) {
        this.dataType = dataType;
        this.statUnit = StatItem.createStatUnit(statKind, dataType);
    }

    public StatItem(int statKind, int dataType, int index) {
        this(statKind, dataType);
        this.index = index;
    }

    public void setValue(EvalExecutor valueEvalExecutor, int valueIndex) {
        if (this.qContext == null) {
            this.qContext = valueEvalExecutor.context;
        }
        this.valueEval = valueEvalExecutor.getItem(valueIndex);
    }

    public void setCondition(EvalExecutor conditionEvalExecutor, int conditionIndex) {
        if (this.qContext == null) {
            this.qContext = conditionEvalExecutor.context;
        }
        this.conditionEval = conditionEvalExecutor.getItem(conditionIndex);
    }

    @Override
    public int getStatKind() {
        return this.statUnit.getStatKind();
    }

    @Override
    public int getDataType() {
        return this.statUnit.getResultType();
    }

    @Override
    public Object readData() {
        return this.statUnit.getResult();
    }

    @Override
    public int getResultType() {
        return this.statUnit.getResultType();
    }

    @Override
    public AbstractData getResult() {
        return this.statUnit.getResult();
    }

    public AbstractData getResult(QueryContext qContext) {
        if (qContext.isBatch()) {
            return qContext.getStatItemCollection().getStatResult(this);
        }
        return this.statUnit.getResult();
    }

    public IASTNode getValueNode() {
        return this.valueEval.getExpression();
    }

    @Override
    public void reset() {
        this.statUnit.reset();
    }

    public void runStatistic() throws DataTypeException {
        if (this.conditionEval == null || this.conditionEval.getReault().getAsBool()) {
            this.statUnit.statistic(this.valueEval.getReault());
        }
    }

    public void runStatistic(QueryContext qContext, DimensionValueSet currentMasterKeys) throws DataTypeException {
        if (this.conditionEval == null || this.conditionEval.getReault().getAsBool()) {
            StatRow row = qContext.getStatItemCollection().getRow(currentMasterKeys);
            row.statistic(this.valueEval.getReault(), this);
        }
    }

    @Override
    public void statistic(AbstractData value) {
        this.statUnit.statistic(value);
    }

    public static StatUnit createStatUnit(int statKind, int dataType) {
        switch (statKind) {
            case 0: {
                return new NoneStatUnit(dataType);
            }
            case 2: {
                return new CountStatUnit();
            }
            case 5: {
                return new MinStatUnit(dataType);
            }
            case 4: {
                return new MaxStatUnit(dataType);
            }
            case 6: {
                return new FirstStatUnit(dataType);
            }
            case 7: {
                return new LastStatUnit(dataType);
            }
            case 1: {
                switch (dataType) {
                    case 4: {
                        return new IntSumUnit();
                    }
                    case 3: {
                        return new FloatSumUnit();
                    }
                    case 10: {
                        return new CurrencySumUnit();
                    }
                    case 6: {
                        return new StringSumUnit();
                    }
                }
                break;
            }
            case 3: {
                switch (dataType) {
                    case 4: {
                        return new IntAvgUnit();
                    }
                    case 3: {
                        return new FloatAvgUnit();
                    }
                    case 10: {
                        return new CurrencyAvgUnit();
                    }
                }
                break;
            }
            case 10: {
                switch (dataType) {
                    case 3: 
                    case 4: {
                        return new FloatMedianUnit();
                    }
                    case 10: {
                        return new CurrencyMedianUnit();
                    }
                }
                break;
            }
            case 11: {
                return new ConstantUnit(dataType);
            }
        }
        return new FirstStatUnit(dataType);
    }

    public static StatUnit createStatUnit(int dataType) {
        if (DataTypes.isNum(dataType)) {
            return StatItem.createStatUnit(1, dataType);
        }
        return StatItem.createStatUnit(5, dataType);
    }

    public static int parseStatKind(String statKind) throws ExpressionException {
        int kind = StatItem.tryParseStatKind(statKind);
        if (kind >= 0) {
            return kind;
        }
        throw new ExpressionException("\u201c" + statKind + "\u201d\u4e0d\u662f\u6709\u6548\u7684\u7edf\u8ba1\u65b9\u5f0f");
    }

    public static int tryParseStatKind(String statKind) {
        int i;
        if (statKind == null) {
            return 0;
        }
        statKind = statKind.toUpperCase();
        for (i = 0; i < STAT_KIND_MODE_NAMES.length; ++i) {
            if (!statKind.equalsIgnoreCase(STAT_KIND_MODE_NAMES[i])) continue;
            return i;
        }
        for (i = 0; i < STAT_KIND_MODE_NAMES2.length; ++i) {
            if (!statKind.equalsIgnoreCase(STAT_KIND_MODE_NAMES2[i])) continue;
            return i;
        }
        for (i = 0; i < STAT_KIND_MODE_TITLES.length; ++i) {
            if (!statKind.equals(STAT_KIND_MODE_TITLES[i])) continue;
            return i;
        }
        return -1;
    }

    public static String getSqlFunction(int statKind) {
        switch (statKind) {
            case 6: {
                return "MIN";
            }
            case 7: {
                return "MAX";
            }
            case 0: 
            case 8: {
                return null;
            }
        }
        return STAT_KIND_MODE_NAMES[statKind];
    }

    public int getIndex() {
        return this.index;
    }

    public String toString() {
        StringBuilder buff = new StringBuilder();
        try {
            FormulaShowInfo formulaShowInfo = new FormulaShowInfo(DataEngineConsts.FormulaShowType.DATA);
            if (this.conditionEval != null) {
                buff.append("if ");
                IASTNode condition = this.conditionEval.getExpression();
                condition.interpret((IContext)this.qContext, buff, Language.FORMULA, (Object)formulaShowInfo);
                buff.append(" then ");
            }
            buff.append(STAT_KIND_MODE_NAMES[this.getStatKind()]).append("(");
            IASTNode eval = this.valueEval.getExpression();
            eval.interpret((IContext)this.qContext, buff, Language.FORMULA, (Object)formulaShowInfo);
            buff.append(")");
        }
        catch (InterpretException interpretException) {
            // empty catch block
        }
        return buff.toString();
    }
}

