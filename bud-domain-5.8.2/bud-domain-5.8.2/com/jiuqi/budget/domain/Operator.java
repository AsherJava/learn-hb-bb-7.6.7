/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.domain;

import java.lang.reflect.Array;

public enum Operator {
    EQUALS("="){

        @Override
        public boolean doJudge(Object left, Object right) {
            if (left == null) {
                return right == null;
            }
            if (right == null) {
                return false;
            }
            return left.toString().equals(right.toString());
        }
    }
    ,
    NOT_EQUALS("!="){

        @Override
        public boolean doJudge(Object left, Object right) {
            return !EQUALS.doJudge(left, right);
        }
    }
    ,
    IN("in"){

        @Override
        public boolean doJudge(Object left, Object right) {
            if (this.hasNullVal(left, right)) {
                return false;
            }
            if (!left.getClass().isInstance(right)) {
                return false;
            }
            if (left instanceof String) {
                return ((String)right).contains((String)left);
            }
            if (left.getClass().isArray()) {
                int rightLength;
                int leftLength = Array.getLength(left);
                if (leftLength > (rightLength = Array.getLength(right))) {
                    return false;
                }
                for (int leftIndex = 0; leftIndex < leftLength; ++leftIndex) {
                    Object leftObj = Array.get(left, leftIndex);
                    boolean find = false;
                    for (int rightIndex = 0; rightIndex < rightLength; ++rightIndex) {
                        Object rightObj = Array.get(right, rightIndex);
                        if (!EQUALS.doJudge(leftObj, rightObj)) continue;
                        find = true;
                        break;
                    }
                    if (find) continue;
                    return false;
                }
                return true;
            }
            return false;
        }
    }
    ,
    NOT_IN("not in"){

        @Override
        public boolean doJudge(Object left, Object right) {
            return !IN.doJudge(left, right);
        }
    }
    ,
    START_WITH("startWith"){

        @Override
        public boolean doJudge(Object left, Object right) {
            if (this.hasNullVal(left, right)) {
                return false;
            }
            if (left instanceof String && right instanceof String) {
                int rightLength;
                int leftLength = ((String)left).length();
                if (leftLength < (rightLength = ((String)right).length())) {
                    return false;
                }
                for (int i = 0; i < rightLength; ++i) {
                    char rightChar;
                    char leftChar = ((String)left).charAt(i);
                    if (leftChar == (rightChar = ((String)right).charAt(i))) continue;
                    return false;
                }
                return true;
            }
            return false;
        }
    }
    ,
    NOT_START_WITH("not startWith"){

        @Override
        public boolean doJudge(Object left, Object right) {
            return !START_WITH.doJudge(left, right);
        }
    }
    ,
    END_WITH("endWith"){

        @Override
        public boolean doJudge(Object left, Object right) {
            if (this.hasNullVal(left, right)) {
                return false;
            }
            if (left instanceof String && right instanceof String) {
                int rightLength;
                int leftLength = ((String)left).length();
                if (leftLength < (rightLength = ((String)right).length())) {
                    return false;
                }
                for (int i = 1; i <= rightLength; ++i) {
                    char rightChar;
                    char leftChar = ((String)left).charAt(leftLength - i);
                    if (leftChar == (rightChar = ((String)right).charAt(rightLength - i))) continue;
                    return false;
                }
                return true;
            }
            return false;
        }
    }
    ,
    NOT_END_WITH("not endWith"){

        @Override
        public boolean doJudge(Object left, Object right) {
            return !END_WITH.doJudge(left, right);
        }
    };

    private final String title;

    private Operator(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public abstract boolean doJudge(Object var1, Object var2);

    boolean hasNullVal(Object left, Object right) {
        return left == null || right == null;
    }
}

