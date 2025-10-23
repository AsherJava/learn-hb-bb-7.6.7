/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.multcheck2.common;

import com.jiuqi.nr.multcheck2.common.MCColumnDefine;
import com.jiuqi.nr.multcheck2.common.MCTableDefine;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.util.ArrayList;
import java.util.List;

public class MCTableConsts {
    public static List<MCTableDefine> TABLE_INFO = new ArrayList<MCTableDefine>(){
        {
            this.add(new MCTableDefine("NR_MCR_RECORD_", "\u7efc\u5408\u5ba1\u6838\u8bb0\u5f55\u7ed3\u679c\u8868", Record.COLS, "MRR_KEY"));
            this.add(new MCTableDefine("NR_MCR_SCHEME_", "\u7efc\u5408\u5ba1\u6838\u65b9\u6848\u7ed3\u679c\u8868", Scheme.COLS, "MRS_KEY"));
            this.add(new MCTableDefine("NR_MCR_ITEM_", "\u7efc\u5408\u5ba1\u6838\u9879\u7ed3\u679c\u8868", Item.COLS, "MRI_KEY"));
            this.add(new MCTableDefine("NR_MCR_ITEMORG_", "\u7efc\u5408\u5ba1\u6838\u9879\u7ed3\u679c\u5355\u4f4d\u660e\u7ec6\u8868", Org.COLS, "MRIO_KEY"));
            this.add(new MCTableDefine("NR_MCR_ERRORINFO_", "\u7efc\u5408\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u8868", Error.COLS, "MEI_KEY"));
        }
    };
    public static String[] TABLE_PERFIX = new String[]{"NR_MCR_RECORD_", "NR_MCR_SCHEME_", "NR_MCR_ITEM_", "NR_MCR_ITEMORG_", "NR_MCR_ERRORINFO_"};

    public static class Error {
        public static final String TABLE = "NR_MCR_ERRORINFO_";
        public static final String F_KEY = "MEI_KEY";
        public static final String F_TASK = "MEI_TASK";
        public static final String F_PERIOD = "MEI_PERIOD";
        public static final String F_ORG = "MEI_ORG";
        public static final String F_DIM = "MEI_DIM";
        public static final String F_TYPE = "MSI_TYPE";
        public static final String F_RESOURCE = "MEI_RESOURCE";
        public static final String F_DESCRIPTION = "MEI_DESCRIPTION";
        public static final String F_TIME = "MEI_UPDATE_TIME";
        public static final String F_USER = "MEI_USER";
        public static final int SIZE = 9;
        public static List<MCColumnDefine> COLS = new ArrayList<MCColumnDefine>(){
            {
                this.add(new MCColumnDefine(Error.F_KEY, ColumnModelType.STRING, 40, false, true, false));
                this.add(new MCColumnDefine(Error.F_TASK, ColumnModelType.STRING, 40, false, false, true));
                this.add(new MCColumnDefine(Error.F_PERIOD, ColumnModelType.STRING, 40, false, false, true));
                this.add(new MCColumnDefine(Error.F_ORG, ColumnModelType.STRING, 60, false, false, true));
                this.add(new MCColumnDefine(Error.F_DIM, ColumnModelType.STRING, 40, false, false, false, true));
                this.add(new MCColumnDefine(Error.F_TYPE, ColumnModelType.STRING, 100, false, false, true));
                this.add(new MCColumnDefine(Error.F_RESOURCE, ColumnModelType.STRING, 200, true, false, true));
                this.add(new MCColumnDefine(Error.F_DESCRIPTION, ColumnModelType.STRING, 2000, false));
                this.add(new MCColumnDefine(Error.F_TIME, ColumnModelType.DATETIME, 14, true));
                this.add(new MCColumnDefine(Error.F_USER, ColumnModelType.STRING, 200, false));
            }
        };
    }

    public static class Org {
        public static final String TABLE = "NR_MCR_ITEMORG_";
        public static final String F_KEY = "MRIO_KEY";
        public static final String F_RKEY = "MRR_KEY";
        public static final String F_IKEY = "MSI_KEY";
        public static final String F_TYPE = "MSI_TYPE";
        public static final String F_ORG = "MRIO_ORG";
        public static final String F_DIM = "MRIO_DIM";
        public static final String F_RESOURCE = "MRIO_RESOURCE";
        public static final String F_RESULT = "MRIO_RESULT";
        public static final String F_TIME = "MRIO_UPDATE_TIME";
        public static final int SIZE = 8;
        public static List<MCColumnDefine> COLS = new ArrayList<MCColumnDefine>(){
            {
                this.add(new MCColumnDefine(Org.F_KEY, ColumnModelType.STRING, 40, false, true, false));
                this.add(new MCColumnDefine(Org.F_RKEY, ColumnModelType.STRING, 40, false, false, true));
                this.add(new MCColumnDefine(Org.F_IKEY, ColumnModelType.STRING, 40, false, false, true));
                this.add(new MCColumnDefine(Org.F_TYPE, ColumnModelType.STRING, 100, false));
                this.add(new MCColumnDefine(Org.F_ORG, ColumnModelType.STRING, 60, false, false, true));
                this.add(new MCColumnDefine(Org.F_DIM, ColumnModelType.STRING, 40, false, false, false, true));
                this.add(new MCColumnDefine(Org.F_RESOURCE, ColumnModelType.STRING, 200, true, false, true));
                this.add(new MCColumnDefine(Org.F_RESULT, ColumnModelType.STRING, 2000, true));
                this.add(new MCColumnDefine(Org.F_TIME, ColumnModelType.DATETIME, 14, true));
            }
        };
    }

    public static class Item {
        public static final String TABLE = "NR_MCR_ITEM_";
        public static final String F_KEY = "MRI_KEY";
        public static final String F_RKEY = "MRR_KEY";
        public static final String F_SKEY = "MS_KEY";
        public static final String F_IKEY = "MSI_KEY";
        public static final String F_STATE = "MRI_STATE";
        public static final String F_SUCCESS = "MRI_SUCCESS";
        public static final String F_FAILED = "MRI_FAILED";
        public static final String F_IGNORE = "MRI_IGNORE";
        public static final String F_BEGIN = "MRI_BEGIN_TIME";
        public static final String F_END = "MRI_END_TIME";
        public static final String F_CONFIG = "MRI_RUN_CONFIG";
        public static final int SIZE = 11;
        public static List<MCColumnDefine> COLS = new ArrayList<MCColumnDefine>(){
            {
                this.add(new MCColumnDefine(Item.F_KEY, ColumnModelType.STRING, 40, false, true, false));
                this.add(new MCColumnDefine(Item.F_RKEY, ColumnModelType.STRING, 40, false, false, true));
                this.add(new MCColumnDefine(Item.F_SKEY, ColumnModelType.STRING, 40, false));
                this.add(new MCColumnDefine(Item.F_IKEY, ColumnModelType.STRING, 40, false));
                this.add(new MCColumnDefine(Item.F_STATE, ColumnModelType.INTEGER, 1, false));
                this.add(new MCColumnDefine(Item.F_SUCCESS, ColumnModelType.INTEGER, 10, false));
                this.add(new MCColumnDefine(Item.F_FAILED, ColumnModelType.INTEGER, 10, false));
                this.add(new MCColumnDefine(Item.F_IGNORE, ColumnModelType.INTEGER, 10, false));
                this.add(new MCColumnDefine(Item.F_BEGIN, ColumnModelType.DATETIME, 14, true));
                this.add(new MCColumnDefine(Item.F_END, ColumnModelType.DATETIME, 14, true));
                this.add(new MCColumnDefine(Item.F_CONFIG, ColumnModelType.CLOB));
            }
        };
    }

    public static class Scheme {
        public static final String TABLE = "NR_MCR_SCHEME_";
        public static final String F_KEY = "MRS_KEY";
        public static final String F_RKEY = "MRR_KEY";
        public static final String F_SKEY = "MS_KEY";
        public static final String F_BEGIN = "MRS_BEGIN_TIME";
        public static final String F_END = "MRS_END_TIME";
        public static final String F_ORGS = "MRS_ORGS";
        public static final int SIZE = 6;
        public static List<MCColumnDefine> COLS = new ArrayList<MCColumnDefine>(){
            {
                this.add(new MCColumnDefine(Scheme.F_KEY, ColumnModelType.STRING, 40, false, true, false));
                this.add(new MCColumnDefine(Scheme.F_RKEY, ColumnModelType.STRING, 40, false, false, true));
                this.add(new MCColumnDefine(Scheme.F_SKEY, ColumnModelType.STRING, 40, false));
                this.add(new MCColumnDefine(Scheme.F_BEGIN, ColumnModelType.DATETIME, 14, true));
                this.add(new MCColumnDefine(Scheme.F_END, ColumnModelType.DATETIME, 14, true));
                this.add(new MCColumnDefine(Scheme.F_ORGS, ColumnModelType.CLOB));
            }
        };
    }

    public static class Record {
        public static final String TABLE = "NR_MCR_RECORD_";
        public static final String F_KEY = "MRR_KEY";
        public static final String F_TASK = "MRR_TASK";
        public static final String F_PERIOD = "MRR_PERIOD";
        public static final String F_SOURCE = "MRR_SOURCE";
        public static final String F_SKEY = "MS_KEY";
        public static final String F_DIM = "MRR_DIM";
        public static final String F_SUCCESS = "MRR_SUCCESS";
        public static final String F_FAILED = "MRR_FAILED";
        public static final String F_BEGIN = "MRR_BEGIN_TIME";
        public static final String F_END = "MRR_END_TIME";
        public static final String F_USER = "MRR_USER";
        public static final int SIZE = 11;
        public static List<MCColumnDefine> COLS = new ArrayList<MCColumnDefine>(){
            {
                this.add(new MCColumnDefine(Record.F_KEY, ColumnModelType.STRING, 40, false, true, false));
                this.add(new MCColumnDefine(Record.F_TASK, ColumnModelType.STRING, 40, false, false, true));
                this.add(new MCColumnDefine(Record.F_PERIOD, ColumnModelType.STRING, 40, false, false, true));
                this.add(new MCColumnDefine(Record.F_SOURCE, ColumnModelType.INTEGER, 1, false));
                this.add(new MCColumnDefine(Record.F_SKEY, ColumnModelType.STRING, 40, true));
                this.add(new MCColumnDefine(Record.F_DIM, ColumnModelType.CLOB));
                this.add(new MCColumnDefine(Record.F_SUCCESS, ColumnModelType.INTEGER, 10, false));
                this.add(new MCColumnDefine(Record.F_FAILED, ColumnModelType.INTEGER, 10, false));
                this.add(new MCColumnDefine(Record.F_BEGIN, ColumnModelType.DATETIME, 14, true));
                this.add(new MCColumnDefine(Record.F_END, ColumnModelType.DATETIME, 14, true));
                this.add(new MCColumnDefine(Record.F_USER, ColumnModelType.STRING, 200, false, false, true));
            }
        };
    }
}

