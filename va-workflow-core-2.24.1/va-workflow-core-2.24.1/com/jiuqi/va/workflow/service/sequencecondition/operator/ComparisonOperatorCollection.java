/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.service.sequencecondition.operator;

import com.jiuqi.va.workflow.domain.OperatorEnum;
import com.jiuqi.va.workflow.service.sequencecondition.operator.ComparisonOperator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public final class ComparisonOperatorCollection {
    private static ComparisonOperator greaterThan;
    private static ComparisonOperator lessThan;
    private static ComparisonOperator equals;
    private static ComparisonOperator notEquals;
    private static ComparisonOperator contains;
    private static ComparisonOperator notContains;
    private static ComparisonOperator greaterThanOrEquals;
    private static ComparisonOperator lessThanOrEquals;

    private ComparisonOperatorCollection() {
    }

    public static ComparisonOperator greaterThan() {
        if (greaterThan == null) {
            greaterThan = new ComparisonOperator(){

                @Override
                public String getName() {
                    return OperatorEnum.GREATER_THAN.getName();
                }

                @Override
                public boolean compare(Object value1, Object value2) {
                    if (value1 instanceof Comparable && value2 instanceof Comparable) {
                        Comparable comparable1 = (Comparable)value1;
                        Comparable comparable2 = (Comparable)value2;
                        return comparable1.compareTo(comparable2) > 0;
                    }
                    return false;
                }
            };
        }
        return greaterThan;
    }

    public static ComparisonOperator lessThan() {
        if (lessThan == null) {
            lessThan = new ComparisonOperator(){

                @Override
                public String getName() {
                    return OperatorEnum.LESS_THAN.getName();
                }

                @Override
                public boolean compare(Object value1, Object value2) {
                    if (value1 instanceof Comparable && value2 instanceof Comparable) {
                        Comparable comparable1 = (Comparable)value1;
                        Comparable comparable2 = (Comparable)value2;
                        return comparable1.compareTo(comparable2) < 0;
                    }
                    return false;
                }
            };
        }
        return lessThan;
    }

    public static ComparisonOperator equals() {
        if (equals == null) {
            equals = new ComparisonOperator(){

                @Override
                public String getName() {
                    return OperatorEnum.EQUALS.getName();
                }

                @Override
                public boolean compare(Object value1, Object value2) {
                    List list2;
                    if (value2 instanceof List && (list2 = (List)value2).size() == 1) {
                        value2 = list2.get(0);
                    }
                    if (value1 instanceof Comparable && value2 instanceof Comparable) {
                        Comparable comparable1 = (Comparable)value1;
                        Comparable comparable2 = (Comparable)value2;
                        return comparable1.compareTo(comparable2) == 0;
                    }
                    return Objects.equals(value1, value2);
                }
            };
        }
        return equals;
    }

    public static ComparisonOperator notEquals() {
        if (notEquals == null) {
            notEquals = new ComparisonOperator(){

                @Override
                public String getName() {
                    return OperatorEnum.NOT_EQUALS.getName();
                }

                @Override
                public boolean compare(Object value1, Object value2) {
                    List list2;
                    if (value2 instanceof List && (list2 = (List)value2).size() == 1) {
                        value2 = list2.get(0);
                    }
                    if (value1 instanceof Comparable && value2 instanceof Comparable) {
                        Comparable comparable1 = (Comparable)value1;
                        Comparable comparable2 = (Comparable)value2;
                        return comparable1.compareTo(comparable2) != 0;
                    }
                    return !Objects.equals(value1, value2);
                }
            };
        }
        return notEquals;
    }

    public static ComparisonOperator contains() {
        if (contains == null) {
            contains = new ComparisonOperator(){

                @Override
                public String getName() {
                    return OperatorEnum.CONTAINS.getName();
                }

                @Override
                public boolean compare(Object value1, Object value2) {
                    if (value1 instanceof List && value2 instanceof List) {
                        List list1 = (List)value1;
                        List list2 = (List)value2;
                        return new HashSet(list1).containsAll(list2);
                    }
                    if (value2 instanceof List) {
                        List list2 = (List)value2;
                        return list2.contains(value1);
                    }
                    return false;
                }
            };
        }
        return contains;
    }

    public static ComparisonOperator notContains() {
        if (notContains == null) {
            notContains = new ComparisonOperator(){

                @Override
                public String getName() {
                    return OperatorEnum.NOT_CONTAINS.getName();
                }

                @Override
                public boolean compare(Object value1, Object value2) {
                    if (value1 instanceof List && value2 instanceof List) {
                        List list1 = (List)value1;
                        List list2 = (List)value2;
                        return !new HashSet(list1).containsAll(list2);
                    }
                    if (value2 instanceof List) {
                        List list2 = (List)value2;
                        return !list2.contains(value1);
                    }
                    return false;
                }
            };
        }
        return notContains;
    }

    public static ComparisonOperator greaterThanOrEquals() {
        if (greaterThanOrEquals == null) {
            greaterThanOrEquals = new ComparisonOperator(){

                @Override
                public String getName() {
                    return OperatorEnum.GREATER_THAN_OR_EQUALS.getName();
                }

                @Override
                public boolean compare(Object value1, Object value2) {
                    if (value1 instanceof Comparable && value2 instanceof Comparable) {
                        Comparable comparable1 = (Comparable)value1;
                        Comparable comparable2 = (Comparable)value2;
                        return comparable1.compareTo(comparable2) >= 0;
                    }
                    return false;
                }
            };
        }
        return greaterThanOrEquals;
    }

    public static ComparisonOperator lessThanOrEquals() {
        if (lessThanOrEquals == null) {
            lessThanOrEquals = new ComparisonOperator(){

                @Override
                public String getName() {
                    return OperatorEnum.LESS_THAN_OR_EQUALS.getName();
                }

                @Override
                public boolean compare(Object value1, Object value2) {
                    if (value1 instanceof Comparable && value2 instanceof Comparable) {
                        Comparable comparable1 = (Comparable)value1;
                        Comparable comparable2 = (Comparable)value2;
                        return comparable1.compareTo(comparable2) <= 0;
                    }
                    return false;
                }
            };
        }
        return lessThanOrEquals;
    }
}

