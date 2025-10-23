/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.zbquery.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.zbquery.model.DimensionAttributeField;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.model.QueryDimensionType;
import com.jiuqi.nr.zbquery.model.QueryObjectType;
import com.jiuqi.nr.zbquery.model.ZBField;
import com.jiuqi.nr.zbquery.util.ZBQueryI18nUtils;

public class FullNameWrapper {
    public static final String MD_ORG = "MD_ORG";
    private static final String MD_ORG_ = "MD_ORG_";
    private QueryObjectType type;
    private String fullName;
    private boolean orgDimension;
    private String schemeName;
    private String tableName;
    private String fieldName;

    public FullNameWrapper(QueryObjectType type, String fullName) {
        this(type, fullName, false);
    }

    public FullNameWrapper(QueryObjectType type, String fullName, boolean orgDimension) {
        this.type = type;
        this.fullName = fullName;
        this.orgDimension = orgDimension;
        this.parse();
    }

    private void parse() {
        if (StringUtils.isEmpty((String)this.fullName)) {
            return;
        }
        String[] names = StringUtils.split((String)this.fullName, (String)".");
        switch (this.type) {
            case DIMENSION: {
                if (names.length > 2) {
                    this.tableName = names[0] + "." + names[1] + "." + names[2];
                    break;
                }
                this.tableName = this.orgDimension && names[0].startsWith(MD_ORG_) ? MD_ORG : names[0];
                break;
            }
            case DIMENSIONATTRIBUTE: {
                if (names.length > 3) {
                    this.tableName = names[0] + "." + names[1] + "." + names[2];
                    this.fieldName = names[3];
                    break;
                }
                this.tableName = this.orgDimension && names[0].startsWith(MD_ORG_) ? MD_ORG : names[0];
                this.fieldName = names[1];
                break;
            }
            case ZB: {
                if (names.length > 2) {
                    this.schemeName = names[0];
                    this.tableName = names[1];
                    this.fieldName = names[2];
                    break;
                }
                this.tableName = names[0];
                this.fieldName = names[1];
                break;
            }
            case FORMULA: {
                this.fieldName = names[0];
                break;
            }
        }
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getSchemeName() {
        return this.schemeName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public String getQueryName() {
        return this.tableName + "." + this.fieldName;
    }

    public static String getKeyFullName(QueryDimension dimension) {
        if (dimension.getDimensionType() == QueryDimensionType.PERIOD) {
            if (dimension.isSpecialPeriodType()) {
                return dimension.getFullName() + "." + "P_CODE";
            }
            return dimension.getFullName() + "." + "P_TIMEKEY";
        }
        if (dimension.getDimensionType() == QueryDimensionType.INNER && dimension.isVirtualDimension()) {
            FullNameWrapper wrapper = new FullNameWrapper(QueryObjectType.ZB, dimension.getFullName());
            return wrapper.getQueryName();
        }
        if (dimension.getDimensionType() == QueryDimensionType.CHILD) {
            String[] names = StringUtils.split((String)dimension.getFullName(), (String)".");
            return names[0] + "." + names[1];
        }
        if (StringUtils.isNotEmpty((String)dimension.getBizKey())) {
            return dimension.getFullName() + "." + dimension.getBizKey();
        }
        return dimension.getFullName() + "." + "CODE";
    }

    public static String getParentFullName(QueryDimension dimension) {
        if (dimension.getDimensionType() != QueryDimensionType.PERIOD && (dimension.getDimensionType() == QueryDimensionType.MASTER && dimension.isOrgDimension() || dimension.isTreeStructure())) {
            return dimension.getFullName() + "." + "PARENTCODE";
        }
        return null;
    }

    public static String getOrderFullName(QueryDimension dimension) {
        if (dimension.getDimensionType() == QueryDimensionType.CHILD) {
            throw new RuntimeException(ZBQueryI18nUtils.getMessage("zbquery.exception.notSupportOrderField001", new Object[0]));
        }
        if (dimension.getDimensionType() == QueryDimensionType.INNER && dimension.isVirtualDimension()) {
            throw new RuntimeException(ZBQueryI18nUtils.getMessage("zbquery.exception.notSupportOrderField002", new Object[0]));
        }
        return dimension.getFullName() + "." + "H_ORDER";
    }

    public static String getBizkeyOrderFullName(ZBField zbField) {
        FullNameWrapper wrapper = new FullNameWrapper(QueryObjectType.ZB, zbField.getFullName());
        return wrapper.getTableName() + "." + "BIZKEYORDER";
    }

    public static String getMessageAlias(QueryDimension dimension) {
        String keyFullName = FullNameWrapper.getKeyFullName(dimension);
        FullNameWrapper fullNameWrapper = new FullNameWrapper(QueryObjectType.DIMENSIONATTRIBUTE, keyFullName, dimension.isOrgDimension());
        if (dimension.getDimensionType() == QueryDimensionType.PERIOD) {
            if (dimension.isSpecialPeriodType()) {
                return "P_TIMEKEY";
            }
            return fullNameWrapper.getFieldName();
        }
        if (dimension.getDimensionType() == QueryDimensionType.CHILD) {
            if (dimension.getPeriodType() != null && dimension.getPeriodType() != PeriodType.DEFAULT) {
                return fullNameWrapper.getFieldName();
            }
            return FullNameWrapper.convertMdOrg(fullNameWrapper.getQueryName());
        }
        if (!dimension.isVirtualDimension()) {
            return fullNameWrapper.getQueryName();
        }
        return null;
    }

    public static String getMessageAlias(QueryDimension dimension, DimensionAttributeField attribute) {
        if (dimension.getDimensionType() == QueryDimensionType.PERIOD) {
            if (!dimension.isSpecialPeriodType()) {
                return attribute.getName();
            }
            return "P_TIMEKEY";
        }
        FullNameWrapper fullNameWrapper = new FullNameWrapper(QueryObjectType.DIMENSIONATTRIBUTE, attribute.getFullName(), dimension.isOrgDimension());
        if (dimension.getDimensionType() == QueryDimensionType.CHILD) {
            if (dimension.getPeriodType() != null && dimension.getPeriodType() != PeriodType.DEFAULT) {
                return fullNameWrapper.getFieldName();
            }
            return FullNameWrapper.convertMdOrg(fullNameWrapper.getQueryName());
        }
        return fullNameWrapper.getQueryName();
    }

    public static String getMessageAlias_Calibre(String calibreName) {
        return calibreName + "." + "CODE";
    }

    private static String convertMdOrg(String messageAlias) {
        if (messageAlias.startsWith(MD_ORG_)) {
            Object[] vs = StringUtils.split((String)messageAlias, (String)".");
            vs[0] = MD_ORG;
            messageAlias = StringUtils.join((Object[])vs, (String)".");
        }
        return messageAlias;
    }

    public static String getEntityId(QueryDimension dimension) {
        if (dimension.getDimensionType() != QueryDimensionType.PERIOD) {
            if (StringUtils.isNotEmpty((String)dimension.getEntityId())) {
                return dimension.getEntityId();
            }
            if (dimension.isOrgDimension()) {
                return dimension.getName() + "@ORG";
            }
            if (!dimension.isCalibreDimension() && !dimension.isVirtualDimension()) {
                return dimension.getName() + "@BASE";
            }
        }
        throw new RuntimeException(ZBQueryI18nUtils.getMessage("zbquery.exception.onlySupportOrgBase", new Object[0]));
    }

    public static boolean isParentField(DimensionAttributeField dimAttr) {
        return "PARENTCODE".equalsIgnoreCase(dimAttr.getName());
    }
}

