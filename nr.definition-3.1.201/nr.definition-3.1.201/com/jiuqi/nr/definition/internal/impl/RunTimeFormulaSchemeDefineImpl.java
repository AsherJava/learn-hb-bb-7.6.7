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
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.EFDCPeriodSettingDefineImpl;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_FORMULASCHEME")
public class RunTimeFormulaSchemeDefineImpl
implements FormulaSchemeDefine {
    private static final long serialVersionUID = 4006212960834514848L;
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

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDisplayMode(FormulaSchemeDisplayMode displayMode) {
        this.displayMode = displayMode;
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

    public void setFormulaSchemeType(FormulaSchemeType formulaSchemeType) {
        this.formulaSchemeType = formulaSchemeType;
    }

    @Override
    public boolean isDefault() {
        return this.defaultScheme;
    }

    public boolean setDefault(boolean isDefault) {
        this.defaultScheme = isDefault;
        return this.defaultScheme;
    }

    @Override
    public boolean isShow() {
        return this.showScheme;
    }

    public boolean setShow(boolean showScheme) {
        this.showScheme = showScheme;
        return this.showScheme;
    }

    @Override
    public EFDCPeriodSettingDefineImpl getEfdcPeriodSettingDefineImpl() {
        return this.efdcPeriodSettingDefineImpl;
    }

    public void setEfdcPeriodSettingDefineImpl(EFDCPeriodSettingDefineImpl efdcPeriodSettingDefineImpl) {
        this.efdcPeriodSettingDefineImpl = efdcPeriodSettingDefineImpl;
    }

    @Override
    public int getFormulaSchemeMenuApply() {
        return this.formulaSchemeMenuApply;
    }

    public void setFormulaCalculationType(int formulaSchemeMenuApply) {
        this.formulaSchemeMenuApply = formulaSchemeMenuApply;
    }
}

