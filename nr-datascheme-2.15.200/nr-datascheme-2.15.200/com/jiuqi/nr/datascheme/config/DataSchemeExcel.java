/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value={"classpath:DataSchemeExcel.properties"}, encoding="UTF-8")
@ConfigurationProperties(prefix="head", ignoreUnknownFields=false)
public class DataSchemeExcel {
    private String title;
    private String code;
    private String dataFieldType;
    private String precision;
    private String decimal;
    private String nullable;
    private String defaultValue;
    private String dataFieldApplyType;
    private String desc;
    private String refDataEntityKey;
    private String verification;
    private String verificationDesc;
    private String dimension;
    private String measureUnit;
    private String dataFieldGatherType;
    private String showFormat;
    private String useAuthority;
    private String error;
    private String allowMultipleSelect;
    private String dataFieldKind;
    private String allowUndefinedCode;
    private String changeWithPeriod;
    private String generateVersion;
    private String encrypted;
    private String dataMaskName;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDataFieldType() {
        return this.dataFieldType;
    }

    public void setDataFieldType(String dataFieldType) {
        this.dataFieldType = dataFieldType;
    }

    public String getPrecision() {
        return this.precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public String getDecimal() {
        return this.decimal;
    }

    public void setDecimal(String decimal) {
        this.decimal = decimal;
    }

    public String getNullable() {
        return this.nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDataFieldApplyType() {
        return this.dataFieldApplyType;
    }

    public void setDataFieldApplyType(String dataFieldApplyType) {
        this.dataFieldApplyType = dataFieldApplyType;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRefDataEntityKey() {
        return this.refDataEntityKey;
    }

    public void setRefDataEntityKey(String refDataEntityKey) {
        this.refDataEntityKey = refDataEntityKey;
    }

    public String getVerification() {
        return this.verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public String getVerificationDesc() {
        return this.verificationDesc;
    }

    public void setVerificationDesc(String verificationDesc) {
        this.verificationDesc = verificationDesc;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public String getDataFieldGatherType() {
        return this.dataFieldGatherType;
    }

    public void setDataFieldGatherType(String dataFieldGatherType) {
        this.dataFieldGatherType = dataFieldGatherType;
    }

    public String getShowFormat() {
        return this.showFormat;
    }

    public void setShowFormat(String showFormat) {
        this.showFormat = showFormat;
    }

    public String getUseAuthority() {
        return this.useAuthority;
    }

    public void setUseAuthority(String useAuthority) {
        this.useAuthority = useAuthority;
    }

    public String getAllowMultipleSelect() {
        return this.allowMultipleSelect;
    }

    public void setAllowMultipleSelect(String allowMultipleSelect) {
        this.allowMultipleSelect = allowMultipleSelect;
    }

    public String getDataFieldKind() {
        return this.dataFieldKind;
    }

    public void setDataFieldKind(String dataFieldKind) {
        this.dataFieldKind = dataFieldKind;
    }

    public String getAllowUndefinedCode() {
        return this.allowUndefinedCode;
    }

    public void setAllowUndefinedCode(String allowUndefinedCode) {
        this.allowUndefinedCode = allowUndefinedCode;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getChangeWithPeriod() {
        return this.changeWithPeriod;
    }

    public void setChangeWithPeriod(String changeWithPeriod) {
        this.changeWithPeriod = changeWithPeriod;
    }

    public String getGenerateVersion() {
        return this.generateVersion;
    }

    public void setGenerateVersion(String generateVersion) {
        this.generateVersion = generateVersion;
    }

    public String getDimension() {
        return this.dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getEncrypted() {
        return this.encrypted;
    }

    public void setEncrypted(String encrypted) {
        this.encrypted = encrypted;
    }

    public String getDataMaskName() {
        return this.dataMaskName;
    }

    public void setDataMaskName(String dataMaskName) {
        this.dataMaskName = dataMaskName;
    }

    public String[] getHeader() {
        return new String[]{this.title, this.code, this.dataFieldType, this.precision, this.decimal, this.nullable, this.defaultValue, this.dataFieldApplyType, this.desc, this.refDataEntityKey, this.verification, this.verificationDesc, this.dimension, this.measureUnit, this.dataFieldGatherType, this.showFormat, this.allowMultipleSelect, this.dataFieldKind, this.allowUndefinedCode, this.changeWithPeriod, this.generateVersion, this.encrypted, this.dataMaskName};
    }
}

