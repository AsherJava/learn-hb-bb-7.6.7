/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.nvwa.definition.common.AggrType
 *  com.jiuqi.nvwa.definition.common.ApplyType
 *  com.jiuqi.nvwa.definition.common.ColumnModelKind
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.entity.internal.model.impl;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nvwa.definition.common.AggrType;
import com.jiuqi.nvwa.definition.common.ApplyType;
import com.jiuqi.nvwa.definition.common.ColumnModelKind;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import org.springframework.util.ObjectUtils;

public class EntityAttributeImpl
implements IEntityAttribute {
    private String ID;
    private String tableID;
    private String code;
    private String name;
    private String title;
    private String desc;
    private String catagory;
    private ColumnModelType columnType;
    private int precision;
    private int decimal;
    private boolean nullAble;
    private String defaultValue;
    private String referTableID;
    private String referColumnID;
    private String filter;
    private boolean multival;
    private AggrType aggrType;
    private ApplyType applyType;
    private String showFormat;
    private String measureUnit;
    private ColumnModelKind kind;
    private double order;
    private boolean supportI18n;
    private String sceneId;
    private String masked;

    public String getID() {
        return this.ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTableID() {
        return this.tableID;
    }

    public void setTableID(String tableID) {
        this.tableID = tableID;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCatagory() {
        return this.catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public ColumnModelType getColumnType() {
        return this.columnType;
    }

    public void setColumnType(ColumnModelType columnType) {
        this.columnType = columnType;
    }

    public int getPrecision() {
        return this.precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getDecimal() {
        return this.decimal;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }

    public boolean isNullAble() {
        return this.nullAble;
    }

    public void setNullAble(boolean nullAble) {
        this.nullAble = nullAble;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getReferTableID() {
        return this.referTableID;
    }

    public void setReferTableID(String referTableID) {
        this.referTableID = referTableID;
    }

    public String getReferColumnID() {
        return this.referColumnID;
    }

    public void setReferColumnID(String referColumnID) {
        this.referColumnID = referColumnID;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public boolean isMultival() {
        return this.multival;
    }

    public void setMultival(boolean multival) {
        this.multival = multival;
    }

    public AggrType getAggrType() {
        return this.aggrType;
    }

    public void setAggrType(AggrType aggrType) {
        this.aggrType = aggrType;
    }

    public ApplyType getApplyType() {
        return this.applyType;
    }

    public void setApplyType(ApplyType applyType) {
        this.applyType = applyType;
    }

    public String getShowFormat() {
        return this.showFormat;
    }

    public void setShowFormat(String showFormat) {
        this.showFormat = showFormat;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public ColumnModelKind getKind() {
        return this.kind;
    }

    public void setKind(ColumnModelKind kind) {
        this.kind = kind;
    }

    public double getOrder() {
        return this.order;
    }

    public String getLocaleTitle() {
        I18nHelper i18nHelper = (I18nHelper)SpringBeanUtils.getBean((String)"DataModelManage", I18nHelper.class);
        String localeTitle = i18nHelper.getMessage(this.ID);
        return ObjectUtils.isEmpty(localeTitle) ? this.title : localeTitle;
    }

    public void setOrder(double order) {
        this.order = order;
    }

    public String getSceneId() {
        return this.sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    @Override
    public boolean isSupportI18n() {
        return this.supportI18n;
    }

    @Override
    public String masked() {
        return this.masked;
    }

    public void setMasked(String masked) {
        this.masked = masked;
    }

    public void setSupportI18n(boolean supportI18n) {
        this.supportI18n = supportI18n;
    }

    public static IEntityAttribute transferFromColumn(ColumnModelDefine columnModelDefine) {
        EntityAttributeImpl impl = new EntityAttributeImpl();
        impl.setID(columnModelDefine.getID());
        impl.setTableID(columnModelDefine.getTableID());
        impl.setCode(columnModelDefine.getCode());
        impl.setName(columnModelDefine.getName());
        impl.setTitle(columnModelDefine.getTitle());
        impl.setDesc(columnModelDefine.getDesc());
        impl.setCatagory(columnModelDefine.getCatagory());
        impl.setColumnType(columnModelDefine.getColumnType());
        impl.setPrecision(columnModelDefine.getPrecision());
        impl.setDecimal(columnModelDefine.getDecimal());
        if (columnModelDefine.getCode().equals("PARENTCODE")) {
            impl.setNullAble(true);
        } else {
            impl.setNullAble(columnModelDefine.isNullAble());
        }
        impl.setDefaultValue(columnModelDefine.getDefaultValue());
        impl.setReferTableID(columnModelDefine.getReferTableID());
        impl.setReferColumnID(columnModelDefine.getReferColumnID());
        impl.setFilter(columnModelDefine.getFilter());
        impl.setMultival(columnModelDefine.isMultival());
        impl.setAggrType(columnModelDefine.getAggrType());
        impl.setApplyType(columnModelDefine.getApplyType());
        impl.setShowFormat(columnModelDefine.getShowFormat());
        impl.setMeasureUnit(columnModelDefine.getMeasureUnit());
        impl.setKind(columnModelDefine.getKind());
        impl.setOrder(columnModelDefine.getOrder());
        impl.setSceneId(columnModelDefine.getSceneId());
        return impl;
    }
}

