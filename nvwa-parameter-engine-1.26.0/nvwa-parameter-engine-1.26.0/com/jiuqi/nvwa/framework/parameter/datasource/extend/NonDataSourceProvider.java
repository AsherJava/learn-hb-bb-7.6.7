/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.FormulaParser
 *  com.jiuqi.bi.text.DateFormatEx
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.time.TimeCalcException
 *  com.jiuqi.bi.util.time.TimeHelper
 */
package com.jiuqi.nvwa.framework.parameter.datasource.extend;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.FormulaParser;
import com.jiuqi.bi.text.DateFormatEx;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.bi.util.time.TimeHelper;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultItem;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.ParameterUtils;
import com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceRangeValues;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterHierarchyFilterItem;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.DefaultParameterValueFormat;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NonDataSourceProvider
implements IParameterDataSourceProvider {
    @Override
    public ParameterResultset getDefaultValue(ParameterDataSourceContext context) throws ParameterException {
        AbstractParameterValueConfig valueCfg = context.getModel().getValueConfig();
        AbstractParameterValue defaultVal = valueCfg.getDefaultValue();
        if (defaultVal == null) {
            return ParameterResultset.EMPTY_RESULTSET;
        }
        IParameterValueFormat format = ParameterUtils.createValueFormat(context.getModel().getDatasource());
        ArrayList<ParameterResultItem> list = new ArrayList<ParameterResultItem>();
        String mode = valueCfg.getDefaultValueMode();
        int dataType = context.getModel().getDataType();
        int tg = context.getModel().getDatasource().getTimegranularity();
        if (tg == -1) {
            tg = 5;
        }
        if (mode.equals("appoint")) {
            List<Object> valuelist = defaultVal.toValueList(format);
            for (Object value : valuelist) {
                list.add(new ParameterResultItem(this.adjustDateValue(value, dataType, tg), this.formatValueTitle(value, dataType, tg)));
            }
        } else if (mode.equals("expr")) {
            String formula = (String)defaultVal.getValue();
            Object rs = this.computeFormula(formula);
            if (rs != null) {
                list.add(new ParameterResultItem(this.adjustDateValue(rs, dataType, tg), this.formatValueTitle(rs, dataType, tg)));
            }
        } else if (mode.equals("first")) {
            List<Object> valuelist = defaultVal.toValueList(format);
            list.add(new ParameterResultItem(this.adjustDateValue(valuelist.get(0), dataType, tg), this.formatValueTitle(valuelist.get(0), dataType, tg)));
        }
        return new ParameterResultset(list);
    }

    private String formatValueTitle(Object value, int dataType, int granularity) throws ParameterException {
        if (value == null) {
            return null;
        }
        if (dataType == 2) {
            String pattern = this.getDefaultPattern(granularity);
            DateFormatEx sdf1 = new DateFormatEx(pattern);
            if (value instanceof String) {
                return (String)value;
            }
            if (value instanceof Date) {
                return sdf1.format((Date)value);
            }
            if (value instanceof Calendar) {
                return sdf1.format(((Calendar)value).getTime());
            }
            throw new ParameterException("\u65e5\u671f\u53c2\u6570\u53d6\u503c\u5bf9\u8c61\u9519\u8bef");
        }
        return value.toString();
    }

    private String getDefaultPattern(int granularity) {
        String pattern = "yyyy\u5e74MM\u6708dd\u65e5";
        if (granularity == 0) {
            pattern = "yyyy\u5e74";
        } else if (granularity == 3) {
            pattern = "yyyy\u5e74MM\u6708";
        } else if (granularity == 2) {
            pattern = "yyyy\u5e74\u7b2cQ\u5b63\u5ea6";
        } else if (granularity == 4) {
            pattern = "yyyy\u5e74TTT";
        } else if (granularity == 1) {
            pattern = "yyyy\u5e74BBB";
        } else if (granularity == 6) {
            pattern = "yyyy\u5e74\u7b2cw\u5468";
        }
        return pattern;
    }

    private Object adjustDateValue(Object value, int dataType, int granularity) throws ParameterException {
        if (value == null) {
            return null;
        }
        if (dataType == 2) {
            Calendar date;
            if (value instanceof String) {
                date = DefaultParameterValueFormat.parseDate((String)value);
            } else if (value instanceof Date) {
                date = Calendar.getInstance();
                date.setTime((Date)value);
            } else if (value instanceof Calendar) {
                date = (Calendar)value;
            } else {
                throw new ParameterException("\u65e5\u671f\u53c2\u6570\u53d6\u503c\u5bf9\u8c61\u9519\u8bef");
            }
            try {
                return TimeHelper.alignDate((Calendar)date, (int)granularity);
            }
            catch (TimeCalcException e) {
                throw new ParameterException(e.getMessage(), e);
            }
        }
        return value;
    }

    private Object computeFormula(String formula) throws ParameterException {
        if (StringUtils.isEmpty((String)formula)) {
            return null;
        }
        FormulaParser parser = FormulaParser.getInstance();
        try {
            IExpression expr = parser.parseEval(formula, null);
            return expr.evaluate(null);
        }
        catch (SyntaxException e) {
            throw new ParameterException(e.getMessage(), e);
        }
    }

    @Override
    public ParameterResultset getCandidateValue(ParameterDataSourceContext context, ParameterHierarchyFilterItem hierarchyFilter) throws ParameterException {
        return ParameterResultset.EMPTY_RESULTSET;
    }

    @Override
    public ParameterResultset compute(ParameterDataSourceContext context, AbstractParameterValue value) throws ParameterException {
        if (value == null) {
            return null;
        }
        ArrayList<ParameterResultItem> list = new ArrayList<ParameterResultItem>();
        IParameterValueFormat format = ParameterUtils.createValueFormat(context.getModel().getDatasource());
        int dataType = context.getModel().getDataType();
        int tg = context.getModel().getDatasource().getTimegranularity();
        if (tg == -1) {
            tg = 5;
        }
        if (value.isFormulaValue()) {
            String formula = (String)value.getValue();
            Object rs = this.computeFormula(formula);
            if (rs != null) {
                list.add(new ParameterResultItem(this.adjustDateValue(rs, dataType, tg), this.formatValueTitle(rs, dataType, tg)));
            }
        } else {
            List<Object> valuelist = value.toValueList(format);
            for (Object v : valuelist) {
                list.add(new ParameterResultItem(this.adjustDateValue(v, dataType, tg), this.formatValueTitle(v, dataType, tg)));
            }
        }
        return new ParameterResultset(list);
    }

    @Override
    public ParameterResultset search(ParameterDataSourceContext context, List<String> searchValues) throws ParameterException {
        return ParameterResultset.EMPTY_RESULTSET;
    }

    @Override
    public List<DataSourceCandidateFieldInfo> getDataSourceCandidateFields(AbstractParameterDataSourceModel datasourceModel) throws ParameterException {
        return null;
    }

    @Override
    public ParameterDataSourceRangeValues getDataSourceCandidateRange(ParameterDataSourceContext context) throws ParameterException {
        ParameterModel model = context.getModel();
        if (model != null) {
            AbstractParameterValueConfig valueConfig = model.getValueConfig();
            String minValue = null;
            String maxValue = null;
            if (StringUtils.isNotEmpty((String)valueConfig.getAcceptMinValue())) {
                minValue = model.getDataType() == 2 ? valueConfig.getAcceptMinValue().replaceAll("-", "") : valueConfig.getAcceptMinValue();
            }
            if (StringUtils.isNotEmpty((String)valueConfig.getAcceptMaxValue())) {
                maxValue = model.getDataType() == 2 ? valueConfig.getAcceptMaxValue().replaceAll("-", "") : valueConfig.getAcceptMaxValue();
            }
            return new ParameterDataSourceRangeValues(minValue, maxValue);
        }
        return null;
    }
}

