/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.common.FormulaSchemeDisplayMode
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.impl.DesignFormulaSchemeDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.EFDCPeriodSettingDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.MouldDataDefineImpl
 *  com.jiuqi.nr.definition.util.ServeCodeService
 */
package com.jiuqi.nr.designer.web.facade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.common.FormulaSchemeDisplayMode;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.impl.DesignFormulaSchemeDefineImpl;
import com.jiuqi.nr.definition.internal.impl.EFDCPeriodSettingDefineImpl;
import com.jiuqi.nr.definition.internal.impl.MouldDataDefineImpl;
import com.jiuqi.nr.definition.util.ServeCodeService;
import com.jiuqi.nr.designer.web.facade.EFDCSettingObj;
import java.util.Date;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FormulaSchemeObj {
    private static final Logger log = LoggerFactory.getLogger(FormulaSchemeObj.class);
    private String key;
    private String formSchemeKey;
    private String title;
    private String order;
    private boolean isDefault;
    private int formulaSchemeType;
    private Date updateTime;
    private String ownerLevelAndId;
    private boolean sameServeCode;
    private int displayMode;
    private boolean show;
    private EFDCSettingObj efdcSettingObj;
    private int menuApplyType;

    public static FormulaSchemeObj toFormulaSchemeObj(DesignFormulaSchemeDefine define) {
        ServeCodeService serveCodeService = (ServeCodeService)BeanUtil.getBean(ServeCodeService.class);
        FormulaSchemeObj obj = new FormulaSchemeObj();
        obj.setKey(define.getKey());
        obj.setTitle(define.getTitle());
        obj.setDefault(define.isDefault());
        if (define.getFormulaSchemeType() != null) {
            obj.setFormulaSchemeType(define.getFormulaSchemeType().getValue());
        }
        if (define.getFormSchemeKey() != null) {
            obj.setFormSchemeKey(define.getFormSchemeKey());
        }
        obj.setOrder(define.getOrder());
        obj.setUpdateTime(define.getUpdateTime());
        obj.setOwnerLevelAndId(define.getOwnerLevelAndId());
        try {
            obj.setSameServeCode(serveCodeService.isSameServeCode(define.getOwnerLevelAndId()));
        }
        catch (JQException e) {
            log.error(e.getMessage(), e);
        }
        if (define.getDisplayMode() != null) {
            obj.setDisplayMode(define.getDisplayMode().getValue());
        }
        obj.setEfdcSettingObj(FormulaSchemeObj.toEFDCSettingObj(define.getEfdcPeriodSettingDefineImpl()));
        obj.setShow(define.isShow());
        obj.setMenuApplyType(define.getFormulaSchemeMenuApply());
        return obj;
    }

    private static EFDCSettingObj toEFDCSettingObj(EFDCPeriodSettingDefineImpl impl) {
        if (!Objects.isNull(impl)) {
            EFDCSettingObj obj = new EFDCSettingObj();
            obj.setKey(impl.getKey());
            obj.setType(impl.getMouldDataDefine().getType());
            obj.setMouldData(impl.getMouldDataDefine().getData());
            return obj;
        }
        return null;
    }

    public static DesignFormulaSchemeDefineImpl toDesignFormulaSchemeDefine(FormulaSchemeObj obj) {
        DesignFormulaSchemeDefineImpl impl = new DesignFormulaSchemeDefineImpl();
        impl.setKey(obj.getKey());
        impl.setTitle(obj.getTitle());
        impl.setDefault(obj.isDefault());
        impl.setFormulaSchemeType(FormulaSchemeType.forValue((int)obj.getFormulaSchemeType()));
        impl.setFormSchemeKey(obj.getFormSchemeKey());
        impl.setUpdateTime(obj.getUpdateTime());
        impl.setOwnerLevelAndId(obj.getOwnerLevelAndId());
        impl.setDisplayMode(FormulaSchemeDisplayMode.forValue((int)obj.getDisplayMode()));
        impl.setOrder(obj.getOrder());
        impl.setShow(obj.getShow());
        impl.setEFDCPeriodSettingDefineImpl(FormulaSchemeObj.toEFDCPeriodSettingDefineImpl(obj.getEfdcSettingObj(), obj.getKey()));
        impl.setFormulaSchemeMenuApply(obj.getMenuApplyType());
        return impl;
    }

    private static EFDCPeriodSettingDefineImpl toEFDCPeriodSettingDefineImpl(EFDCSettingObj obj, String formulaSchemeKey) {
        if (!Objects.isNull(obj)) {
            EFDCPeriodSettingDefineImpl efdc = new EFDCPeriodSettingDefineImpl();
            MouldDataDefineImpl mould = new MouldDataDefineImpl();
            efdc.setKey(obj.getKey());
            efdc.setFormulaSchemeKey(formulaSchemeKey);
            mould.setType(obj.getType());
            mould.setData(obj.getMouldData());
            efdc.setMouldDataDefine(mould);
            return efdc;
        }
        return null;
    }

    public int getDisplayMode() {
        return this.displayMode;
    }

    public void setDisplayMode(int displayMode) {
        this.displayMode = displayMode;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean isDefault() {
        return this.isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public int getFormulaSchemeType() {
        return this.formulaSchemeType;
    }

    public void setFormulaSchemeType(int formulaSchemeType) {
        this.formulaSchemeType = formulaSchemeType;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public boolean getSameServeCode() {
        return this.sameServeCode;
    }

    public void setSameServeCode(boolean sameServeCode) {
        this.sameServeCode = sameServeCode;
    }

    public boolean getShow() {
        return this.show;
    }

    public void setShow(boolean showScheme) {
        this.show = showScheme;
    }

    public EFDCSettingObj getEfdcSettingObj() {
        return this.efdcSettingObj;
    }

    public void setEfdcSettingObj(EFDCSettingObj efdcSettingObj) {
        this.efdcSettingObj = efdcSettingObj;
    }

    public int getMenuApplyType() {
        return this.menuApplyType;
    }

    public void setMenuApplyType(int menuApplyType) {
        this.menuApplyType = menuApplyType;
    }
}

