/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 */
package com.jiuqi.va.biz.ruler.common.consts;

import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.domain.datamodel.DataModelType;
import java.util.ArrayList;
import java.util.stream.Stream;

public class RefTableFieldProvider {
    private static final RefTableFieldProvider provider = new RefTableFieldProvider();
    private static final ArrayList<RefTableStructure> USERREFTABLE = new ArrayList<RefTableStructure>(){
        private static final long serialVersionUID = 1L;
        {
            RefTableFieldProvider refTableFieldProvider = provider;
            refTableFieldProvider.getClass();
            this.add(refTableFieldProvider.new RefTableStructure("ID", "\u6807\u8bc6", 33));
            RefTableFieldProvider refTableFieldProvider2 = provider;
            refTableFieldProvider2.getClass();
            this.add(refTableFieldProvider2.new RefTableStructure("NAME", "\u540d\u79f0", 6));
            RefTableFieldProvider refTableFieldProvider3 = provider;
            refTableFieldProvider3.getClass();
            this.add(refTableFieldProvider3.new RefTableStructure("USERNAME", "\u767b\u5f55\u540d", 6));
            RefTableFieldProvider refTableFieldProvider4 = provider;
            refTableFieldProvider4.getClass();
            this.add(refTableFieldProvider4.new RefTableStructure("UNITCODE", "\u6240\u5c5e\u673a\u6784", 6));
            RefTableFieldProvider refTableFieldProvider5 = provider;
            refTableFieldProvider5.getClass();
            this.add(refTableFieldProvider5.new RefTableStructure("PWD", "\u5bc6\u7801", 6));
            RefTableFieldProvider refTableFieldProvider6 = provider;
            refTableFieldProvider6.getClass();
            this.add(refTableFieldProvider6.new RefTableStructure("SEX", "\u6027\u522b", 3));
            RefTableFieldProvider refTableFieldProvider7 = provider;
            refTableFieldProvider7.getClass();
            this.add(refTableFieldProvider7.new RefTableStructure("TELEPHONE", "\u7535\u8bdd", 6));
            RefTableFieldProvider refTableFieldProvider8 = provider;
            refTableFieldProvider8.getClass();
            this.add(refTableFieldProvider8.new RefTableStructure("WECHAT", "\u5fae\u4fe1", 6));
            RefTableFieldProvider refTableFieldProvider9 = provider;
            refTableFieldProvider9.getClass();
            this.add(refTableFieldProvider9.new RefTableStructure("QQ", "QQ\u53f7", 6));
            RefTableFieldProvider refTableFieldProvider10 = provider;
            refTableFieldProvider10.getClass();
            this.add(refTableFieldProvider10.new RefTableStructure("EMAIL", "\u90ae\u7bb1", 6));
            RefTableFieldProvider refTableFieldProvider11 = provider;
            refTableFieldProvider11.getClass();
            this.add(refTableFieldProvider11.new RefTableStructure("CERTTYPE", "\u8bc1\u4ef6\u7c7b\u578b", 6));
            RefTableFieldProvider refTableFieldProvider12 = provider;
            refTableFieldProvider12.getClass();
            this.add(refTableFieldProvider12.new RefTableStructure("IDCARD", "\u8bc1\u4ef6ID", 6));
            RefTableFieldProvider refTableFieldProvider13 = provider;
            refTableFieldProvider13.getClass();
            this.add(refTableFieldProvider13.new RefTableStructure("BIRTHDAY", "\u751f\u65e5", 2));
        }
    };
    private static final ArrayList<RefTableStructure> ENUMREFTABLE = new ArrayList<RefTableStructure>(){
        private static final long serialVersionUID = 1L;
        {
            RefTableFieldProvider refTableFieldProvider = provider;
            refTableFieldProvider.getClass();
            this.add(refTableFieldProvider.new RefTableStructure("TITLE", "\u540d\u79f0", 6));
            RefTableFieldProvider refTableFieldProvider2 = provider;
            refTableFieldProvider2.getClass();
            this.add(refTableFieldProvider2.new RefTableStructure("VAL", "\u503c", 6));
            RefTableFieldProvider refTableFieldProvider3 = provider;
            refTableFieldProvider3.getClass();
            this.add(refTableFieldProvider3.new RefTableStructure("DESCRIPTION", "\u63cf\u8ff0", 6));
        }
    };

    public static final Stream<RefTableStructure> getRefTableByRefType(int refTableType) {
        if (3 == refTableType) {
            return USERREFTABLE.stream();
        }
        if (2 == refTableType) {
            return ENUMREFTABLE.stream();
        }
        throw new RuntimeException(BizBindingI18nUtil.getMessage("va.bizbinding.reftablefieldprovider.notexisttable", new Object[]{refTableType}));
    }

    public static final Stream<RefTableStructure> getRefTableByTableName(String tableName) {
        int refTableType = FormulaUtils.getRefTableType(tableName.toUpperCase());
        return RefTableFieldProvider.getRefTableByRefType(refTableType);
    }

    private RefTableFieldProvider() {
    }

    public class RefTableStructure {
        private String fieldName;
        private String fieldTitle;
        private int fieldType;

        private RefTableStructure(String fieldName, String fieldTitle, int fieldType) {
            this.fieldName = fieldName;
            this.fieldTitle = fieldTitle;
            this.fieldType = fieldType;
        }

        public String getFieldName() {
            return this.fieldName;
        }

        public String getFieldTitle() {
            return this.fieldTitle;
        }

        public int getFieldType() {
            return this.fieldType;
        }

        public DataModelType.ColumnType getColumnType() {
            return this.valueOf(this.fieldType);
        }

        public DataModelType.ColumnType valueOf(int dataType) {
            switch (dataType) {
                case 33: {
                    return DataModelType.ColumnType.UUID;
                }
                case 2: {
                    return DataModelType.ColumnType.DATE;
                }
                case 3: {
                    return DataModelType.ColumnType.INTEGER;
                }
                case 10: {
                    return DataModelType.ColumnType.NUMERIC;
                }
                case 6: {
                    return DataModelType.ColumnType.NVARCHAR;
                }
            }
            return null;
        }
    }
}

