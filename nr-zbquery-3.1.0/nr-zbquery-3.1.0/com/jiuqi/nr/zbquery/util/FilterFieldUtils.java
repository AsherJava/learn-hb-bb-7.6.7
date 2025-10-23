/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.query.model.MeasureDescriptor
 */
package com.jiuqi.nr.zbquery.util;

import com.jiuqi.bi.query.model.MeasureDescriptor;
import com.jiuqi.nr.zbquery.engine.executor.QueryModelBuilder;
import com.jiuqi.nr.zbquery.model.FilterField;
import com.jiuqi.nr.zbquery.model.FilterType;
import com.jiuqi.nr.zbquery.model.FormulaField;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.model.QueryDimensionType;
import com.jiuqi.nr.zbquery.model.QueryField;
import com.jiuqi.nr.zbquery.model.QueryObject;
import com.jiuqi.nr.zbquery.model.QueryObjectType;
import com.jiuqi.nr.zbquery.util.FullNameWrapper;
import java.util.List;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class FilterFieldUtils {
    private static final String AND = "AND";
    private static final String OR = "OR";
    private static final String LEFT_BRACKET = "(";
    private static final String RIGHT_BRACKET = ")";

    private static String buildFilterExpression(QueryModelBuilder queryModelBuilder, List<FilterField> filterFields) {
        if (CollectionUtils.isEmpty(filterFields)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < filterFields.size(); ++i) {
            String filterExp = FilterFieldUtils.buildFilterExpression(queryModelBuilder, filterFields.get(i));
            if (filterExp != null) {
                if (filterExp.contains(" OR ")) {
                    filterExp = LEFT_BRACKET + filterExp + RIGHT_BRACKET;
                }
                sb.append(filterExp);
            }
            if (filterFields.size() == i + 1) continue;
            sb.append(" AND ");
        }
        return sb.toString();
    }

    private static String buildFilterExpression(QueryModelBuilder queryModelBuilder, FilterField filterField) {
        String queryName;
        QueryObject queryObject = queryModelBuilder.getModelFinder().getQueryObject(filterField.getFullName());
        if (queryObject.getType() == QueryObjectType.FORMULA) {
            String alias = queryModelBuilder.getFullNameAliasMapper().get(filterField.getFullName());
            queryName = ((FormulaField)queryObject).getFormula();
            for (MeasureDescriptor measure : queryModelBuilder.getQueryModel().getMeasures()) {
                if (!measure.getAlias().equals(alias)) continue;
                queryName = measure.getExpression();
                break;
            }
        } else {
            boolean orgDimension = false;
            if (queryObject.getType() == QueryObjectType.DIMENSIONATTRIBUTE) {
                QueryDimension queryDim = queryModelBuilder.getModelFinder().getQueryDimension(queryObject.getParent());
                orgDimension = queryDim.getDimensionType() == QueryDimensionType.CHILD ? queryModelBuilder.getModelFinder().getQueryDimension(queryDim.getParent()).isOrgDimension() : queryDim.isOrgDimension();
            }
            FullNameWrapper wrapper = new FullNameWrapper(queryObject.getType(), queryObject.getFullName(), orgDimension);
            queryName = wrapper.getQueryName();
        }
        int dataType = 6;
        if (queryObject instanceof QueryField) {
            dataType = ((QueryField)queryObject).getDataType();
        }
        return FilterFieldUtils.buildFilterExpression(queryName, dataType, filterField);
    }

    public static String buildFilterExpression(String queryName, int dataType, FilterField filterField) {
        List<FilterType> filterTypes = FilterFieldUtils.getFilterTypes(filterField);
        if (!StringUtils.hasLength(queryName) || CollectionUtils.isEmpty(filterTypes)) {
            return null;
        }
        return FilterFieldUtils.buildFilterExpression(queryName, dataType, filterTypes);
    }

    public static List<FilterType> getFilterTypes(FilterField filterField) {
        return filterField.getFilterTypes();
    }

    public static String buildFilterExpression(String queryName, int dataType, List<FilterType> filterTypes) {
        if (dataType == 3 || dataType == 5 || dataType == 8 || dataType == 10) {
            return FilterFieldUtils.buildFilterExpression_Number(queryName, filterTypes);
        }
        if (dataType == 6) {
            return FilterFieldUtils.buildFilterExpression_String(queryName, filterTypes);
        }
        if (dataType == 1) {
            return FilterFieldUtils.buildFilterExpression_Boolean(queryName, filterTypes);
        }
        if (dataType == 2) {
            return FilterFieldUtils.buildFilterExpression_Datetime(queryName, filterTypes);
        }
        return null;
    }

    public static String buildFilterExpression_Number(String queryName, List<FilterType> filterTypes) {
        if (filterTypes.contains((Object)FilterType.NULL) && filterTypes.contains((Object)FilterType.ZERO) && filterTypes.contains((Object)FilterType.NEGATIVE) && filterTypes.contains((Object)FilterType.POSITIVE)) {
            return null;
        }
        if (filterTypes.contains((Object)FilterType.ZERO) && filterTypes.contains((Object)FilterType.NEGATIVE) && filterTypes.contains((Object)FilterType.POSITIVE)) {
            return String.format("%s IS NOT NULL", queryName);
        }
        if (filterTypes.contains((Object)FilterType.ZERO) && filterTypes.contains((Object)FilterType.NEGATIVE)) {
            return String.format("%s <= 0", queryName);
        }
        if (filterTypes.contains((Object)FilterType.ZERO) && filterTypes.contains((Object)FilterType.POSITIVE)) {
            return String.format("%s >= 0", queryName);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < filterTypes.size(); ++i) {
            sb.append(FilterFieldUtils.buildFilterExpression(queryName, 3, filterTypes.get(i)));
            if (filterTypes.size() == i + 1) continue;
            sb.append(" OR ");
        }
        return sb.toString();
    }

    public static String buildFilterExpression_String(String queryName, List<FilterType> filterTypes) {
        if (filterTypes.contains((Object)FilterType.NULL) && filterTypes.contains((Object)FilterType.NOT_NULL)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < filterTypes.size(); ++i) {
            sb.append(FilterFieldUtils.buildFilterExpression(queryName, 6, filterTypes.get(i)));
            if (filterTypes.size() == i + 1) continue;
            sb.append(" OR ");
        }
        return sb.toString();
    }

    public static String buildFilterExpression_Datetime(String queryName, List<FilterType> filterTypes) {
        if (filterTypes.contains((Object)FilterType.NULL) && filterTypes.contains((Object)FilterType.ZERO) && filterTypes.contains((Object)FilterType.NEGATIVE) && filterTypes.contains((Object)FilterType.POSITIVE)) {
            return null;
        }
        if (filterTypes.contains((Object)FilterType.ZERO) && filterTypes.contains((Object)FilterType.NEGATIVE) && filterTypes.contains((Object)FilterType.POSITIVE)) {
            return String.format("%s IS NOT NULL", queryName);
        }
        if (filterTypes.contains((Object)FilterType.ZERO) && filterTypes.contains((Object)FilterType.NEGATIVE)) {
            return String.format("%s <= 0", queryName);
        }
        if (filterTypes.contains((Object)FilterType.ZERO) && filterTypes.contains((Object)FilterType.POSITIVE)) {
            return String.format("%s >= 0", queryName);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < filterTypes.size(); ++i) {
            sb.append(FilterFieldUtils.buildFilterExpression(queryName, 2, filterTypes.get(i)));
            if (filterTypes.size() == i + 1) continue;
            sb.append(" OR ");
        }
        return sb.toString();
    }

    public static String buildFilterExpression_Boolean(String queryName, List<FilterType> filterTypes) {
        if (filterTypes.contains((Object)FilterType.NULL) && filterTypes.contains((Object)FilterType.TRUE) && filterTypes.contains((Object)FilterType.FALSE)) {
            return null;
        }
        if (filterTypes.contains((Object)FilterType.TRUE) && filterTypes.contains((Object)FilterType.FALSE)) {
            return String.format("%s IS NOT NULL", queryName);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < filterTypes.size(); ++i) {
            sb.append(FilterFieldUtils.buildFilterExpression(queryName, 1, filterTypes.get(i)));
            if (filterTypes.size() == i + 1) continue;
            sb.append(" OR ");
        }
        return sb.toString();
    }

    public static String buildFilterExpression(String queryName, int dataType, FilterType filterType) {
        String expression;
        switch (filterType) {
            case NULL: {
                if (dataType == 6) {
                    expression = String.format("%s IS NULL OR %s = ''", queryName, queryName);
                    break;
                }
                expression = String.format("%s IS NULL", queryName, queryName);
                break;
            }
            case NOT_NULL: {
                if (dataType == 6) {
                    expression = String.format("%s IS NOT NULL && %s <> ''", queryName, queryName);
                    break;
                }
                expression = String.format("%s IS NOT NULL", queryName);
                break;
            }
            case NEGATIVE: {
                expression = String.format("%s < 0", queryName);
                break;
            }
            case POSITIVE: {
                expression = String.format("%s > 0", queryName);
                break;
            }
            case ZERO: {
                expression = String.format("%s = 0", queryName);
                break;
            }
            case TRUE: {
                expression = String.format("%s = TRUE", queryName);
                break;
            }
            case FALSE: {
                expression = String.format("%s = FALSE", queryName);
                break;
            }
            default: {
                expression = null;
            }
        }
        return expression;
    }
}

