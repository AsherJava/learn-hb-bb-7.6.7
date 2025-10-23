/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.IMeasureConfig
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineGetterImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineImpl
 */
package com.jiuqi.nr.task.form.form.dto;

import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.IMeasureConfig;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineGetterImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineImpl;
import com.jiuqi.nr.task.form.dto.DataCore;
import com.jiuqi.nr.task.form.ext.dto.ConfigDTO;
import com.jiuqi.nr.task.form.form.exception.FormRunTimeException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;

public class FormDTO
extends DataCore {
    public static final String NO_DIMES_SION = "9493b4eb-6516-48a8-a878-25a63a23e63a;NotDimession";
    public static final String NOT_DIMES_SION = "NotDimession";
    public static final String AMOUNT = "9493b4eb-6516-48a8-a878-25a63a23e63a";
    public static final String AMOUNT_WITHOUT_UNIT = "9493b4eb-6516-48a8-a878-25a63a23e63a;";
    public static final List<String> DIMES_UNIT = Arrays.asList("YUAN", "BAIYUAN", "QIANYUAN", "WANYUAN", "BAIWAN", "QIANWAN", "YIYUAN", "WANYI");
    private String formCode;
    private String desc;
    private String serialNumber;
    private boolean isgather;
    private String readOnlyCondition;
    private String formCondition;
    private String measureUnit;
    private IMeasureConfig measureConfig;
    private FormType formType;
    private List<ConfigDTO> configDTO;
    private String formScheme;
    private String formGroupKey;
    private Map<String, Object> formExtension;

    public FormDTO() {
    }

    public FormDTO(DesignFormDefine definition) {
        if (definition == null) {
            return;
        }
        this.setKey(definition.getKey());
        this.formCode = definition.getFormCode();
        this.setTitle(definition.getTitle());
        this.serialNumber = definition.getSerialNumber();
        this.isgather = definition.getIsGather();
        this.measureUnit = definition.getMeasureUnit();
        if (StringUtils.isEmpty(this.measureUnit)) {
            DesignFormDefineGetterImpl designFormDefineGetter = new DesignFormDefineGetterImpl(definition);
            this.measureUnit = designFormDefineGetter.getMeasureUnit();
        }
        if (StringUtils.hasLength(this.measureUnit)) {
            String[] measureStr = this.measureUnit.split(";");
            if (measureStr.length == 2) {
                if (AMOUNT.equals(measureStr[0])) {
                    if (!DIMES_UNIT.contains(measureStr[1]) && !NOT_DIMES_SION.equals(measureStr[1])) {
                        this.measureUnit = AMOUNT_WITHOUT_UNIT;
                    }
                } else {
                    this.measureUnit = AMOUNT_WITHOUT_UNIT;
                }
            } else {
                this.measureUnit = AMOUNT_WITHOUT_UNIT;
            }
        }
        this.formCondition = definition.getFormCondition();
        this.readOnlyCondition = definition.getReadOnlyCondition();
        this.formType = definition.getFormType();
        this.formScheme = definition.getFormScheme();
        this.formExtension = definition.getFormExtension();
    }

    public DesignFormDefine toDesignFormDefine() {
        DesignFormDefineImpl designFormDefine = new DesignFormDefineImpl();
        this.toDesignFormDefine((DesignFormDefine)designFormDefine);
        return designFormDefine;
    }

    public void toDesignFormDefine(DesignFormDefine designFormDefine) {
        if (designFormDefine != null) {
            Map formExtension;
            designFormDefine.setFormCode(this.formCode);
            designFormDefine.setTitle(this.getTitle());
            designFormDefine.setSerialNumber(this.serialNumber);
            designFormDefine.setFormType(this.formType);
            designFormDefine.setIsGather(this.isgather);
            boolean hasMeasureUnit = false;
            if (NOT_DIMES_SION.equals(this.measureUnit)) {
                this.measureUnit = AMOUNT_WITHOUT_UNIT;
                hasMeasureUnit = true;
            } else if (NO_DIMES_SION.equals(this.measureUnit)) {
                hasMeasureUnit = true;
            } else if (StringUtils.hasLength(this.measureUnit)) {
                String[] measureStr = this.measureUnit.split(";");
                if (measureStr.length == 2) {
                    if (AMOUNT.equals(measureStr[0])) {
                        if (DIMES_UNIT.contains(measureStr[1])) {
                            hasMeasureUnit = true;
                        } else {
                            this.measureUnit = AMOUNT_WITHOUT_UNIT;
                        }
                    } else {
                        this.measureUnit = AMOUNT_WITHOUT_UNIT;
                    }
                } else {
                    this.measureUnit = AMOUNT_WITHOUT_UNIT;
                }
            }
            if (!hasMeasureUnit && !StringUtils.hasLength(designFormDefine.getMeasureUnit())) {
                this.measureUnit = null;
            }
            designFormDefine.setMeasureUnit(this.measureUnit);
            designFormDefine.setFormCondition(this.formCondition);
            designFormDefine.setReadOnlyCondition(this.readOnlyCondition);
            designFormDefine.setFormScheme(this.formScheme);
            if (this.formExtension != null) {
                for (Map.Entry entry : this.formExtension.entrySet()) {
                    System.out.println("key= " + (String)entry.getKey() + " and value= " + entry.getValue());
                    designFormDefine.addExtensions((String)entry.getKey(), entry.getValue());
                }
            }
            if (designFormDefine.getFormType() == FormType.FORM_TYPE_INSERTANALYSIS && ((formExtension = designFormDefine.getFormExtension()) == null || formExtension.get("analysisGuid") == null)) {
                throw new FormRunTimeException("\u5206\u6790\u8868\u7c7b\u578b\u62a5\u8868\u5173\u8054\u5206\u6790\u8868\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570");
            }
        }
    }

    public boolean checkProperty() {
        boolean checkResult = true;
        if (!(StringUtils.hasLength(this.getKey()) && StringUtils.hasLength(this.getTitle()) && StringUtils.hasLength(this.getFormCode()))) {
            checkResult = false;
        }
        return checkResult;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public boolean getIsgather() {
        return this.isgather;
    }

    public boolean isIsgather() {
        return this.isgather;
    }

    public void setIsgather(boolean isgather) {
        this.isgather = isgather;
    }

    public String getReadOnlyCondition() {
        return this.readOnlyCondition;
    }

    public void setReadOnlyCondition(String readOnlyCondition) {
        this.readOnlyCondition = readOnlyCondition;
    }

    public String getFormCondition() {
        return this.formCondition;
    }

    public void setFormCondition(String formCondition) {
        this.formCondition = formCondition;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public IMeasureConfig getMeasureConfig() {
        return this.measureConfig;
    }

    public void setMeasureConfig(IMeasureConfig measureConfig) {
        this.measureConfig = measureConfig;
    }

    public FormType getFormType() {
        return this.formType;
    }

    public void setFormType(FormType formType) {
        this.formType = formType;
    }

    public List<ConfigDTO> getConfigDTO() {
        return this.configDTO;
    }

    public void setConfigDTO(List<ConfigDTO> configDTO) {
        this.configDTO = configDTO;
    }

    public String getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(String formScheme) {
        this.formScheme = formScheme;
    }

    public String getFormGroupKey() {
        return this.formGroupKey;
    }

    public void setFormGroupKey(String formGroupKey) {
        this.formGroupKey = formGroupKey;
    }

    public Map<String, Object> getFormExtension() {
        return this.formExtension;
    }

    public void setFormExtension(Map<String, Object> formExtension) {
        this.formExtension = formExtension;
    }

    public void addExtensions(String key, Object value) {
        if (this.formExtension == null) {
            this.formExtension = new HashMap<String, Object>();
        }
        this.formExtension.put(key, value);
    }

    public Object getExtensionProp(String key) {
        if (this.formExtension == null) {
            return null;
        }
        return this.formExtension.get(key);
    }
}

