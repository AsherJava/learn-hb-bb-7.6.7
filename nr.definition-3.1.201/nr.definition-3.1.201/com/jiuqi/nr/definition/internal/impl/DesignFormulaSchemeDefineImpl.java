/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.common.FormulaSchemeDisplayMode;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.EFDCPeriodSettingDefineImpl;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_FORMULASCHEME_DES")
public class DesignFormulaSchemeDefineImpl
implements DesignFormulaSchemeDefine {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="fs_form_scheme_key", isPk=false)
    private String formSchemeKey;
    @DBAnno.DBField(dbField="fs_desc")
    private String description;
    @DBAnno.DBField(dbField="fs_list_in_menu", tranWith="transFormulaSchemeDisplayMode", dbType=Integer.class, appType=FormulaSchemeDisplayMode.class)
    private FormulaSchemeDisplayMode displayMode;
    @DBAnno.DBField(dbField="fs_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="fs_title")
    private String title;
    @DBAnno.DBField(dbField="fs_order", isOrder=true)
    private String order = OrderGenerator.newOrder();
    @DBAnno.DBField(dbField="fs_version")
    private String version;
    @DBAnno.DBField(dbField="fs_level")
    private String ownerLevelAndId;
    @DBAnno.DBField(dbField="fs_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updateTime;
    @DBAnno.DBField(dbField="fs_type", tranWith="transFormulaSchemeType", dbType=Integer.class, appType=FormulaSchemeType.class)
    private FormulaSchemeType formulaSchemeType;
    @DBAnno.DBField(dbField="fs_default", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean defaultScheme;
    @DBAnno.DBField(dbField="fs_show", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean showScheme = true;
    private EFDCPeriodSettingDefineImpl efdcPeriodSettingDefineImpl;
    @DBAnno.DBField(dbField="fs_menu_apply")
    private int formulaSchemeMenuApply;

    @Override
    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public FormulaSchemeDisplayMode getDisplayMode() {
        return this.displayMode;
    }

    @Override
    public boolean getReserveAllZeroRow() {
        return false;
    }

    public String getKey() {
        return this.key;
    }

    public String getTitle() {
        return this.title;
    }

    public String getOrder() {
        return this.order;
    }

    public String getVersion() {
        return this.version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setDisplayMode(FormulaSchemeDisplayMode displayMode) {
        this.displayMode = displayMode;
    }

    @Override
    public void setReserveAllZeroRow(boolean reserveAllZeroRow) {
    }

    @Override
    public FormulaSchemeType getFormulaSchemeType() {
        return this.formulaSchemeType;
    }

    public int getFormulaSchemeTypeDB() {
        return this.formulaSchemeType == null ? FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT.getValue() : this.formulaSchemeType.getValue();
    }

    public void setFormulaSchemeTypeDB(Integer type) {
        this.formulaSchemeType = FormulaSchemeType.forValue(type);
    }

    @Override
    public void setFormulaSchemeType(FormulaSchemeType formulaSchemeType) {
        this.formulaSchemeType = formulaSchemeType;
    }

    @Override
    public boolean isDefault() {
        return this.defaultScheme;
    }

    @Override
    public boolean setDefault(boolean isDefault) {
        this.defaultScheme = isDefault;
        return this.defaultScheme;
    }

    @Override
    public boolean isShow() {
        return this.showScheme;
    }

    @Override
    public EFDCPeriodSettingDefineImpl getEfdcPeriodSettingDefineImpl() {
        return this.efdcPeriodSettingDefineImpl;
    }

    @Override
    public boolean setShow(boolean showScheme) {
        this.showScheme = showScheme;
        return this.showScheme;
    }

    @Override
    public void setEFDCPeriodSettingDefineImpl(EFDCPeriodSettingDefineImpl efdcPeriodSettingDefineImpl) {
        this.efdcPeriodSettingDefineImpl = efdcPeriodSettingDefineImpl;
    }

    @Override
    public int getFormulaSchemeMenuApply() {
        return this.formulaSchemeMenuApply;
    }

    @Override
    public void setFormulaSchemeMenuApply(int formulaSchemeMenuApply) {
        this.formulaSchemeMenuApply = formulaSchemeMenuApply;
    }
}

