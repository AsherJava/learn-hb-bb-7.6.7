/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.components;

public enum SysDim {
    DATATIME{

        @Override
        public String getTitle() {
            return "\u65f6\u671f";
        }
    }
    ,
    MDCODE{

        @Override
        public String getTitle() {
            return "\u4e3b\u7ef4\u5ea6";
        }

        @Override
        public String alias() {
            return "MD_ORG";
        }
    }
    ,
    MD_CURRENCY{

        @Override
        public String getTitle() {
            return "\u5e01\u79cd";
        }
    }
    ,
    MD_STAGE{

        @Override
        public String getTitle() {
            return "\u9636\u6bb5";
        }
    }
    ,
    MD_MGRVER{

        @Override
        public String getTitle() {
            return "\u7ba1\u7406\u7248\u672c";
        }
    }
    ,
    MD_SCENE{

        @Override
        public String getTitle() {
            return "\u60c5\u666f";
        }
    };


    public abstract String getTitle();

    public String alias() {
        return this.name();
    }

    public static boolean isDataTime(String dimCode) {
        return DATATIME.name().equals(dimCode);
    }

    public static boolean isSysDim(String dimCode) {
        SysDim[] values = SysDim.values();
        int size = values.length;
        for (int i = 0; i < size; ++i) {
            if (!values[i].name().equals(dimCode) && !values[i].alias().equals(dimCode)) continue;
            return true;
        }
        return false;
    }
}

