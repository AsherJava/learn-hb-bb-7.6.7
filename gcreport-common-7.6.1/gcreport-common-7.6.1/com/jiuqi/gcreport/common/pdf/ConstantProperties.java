/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 */
package com.jiuqi.gcreport.common.pdf;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.common.pdf.WordUtil;
import java.io.File;
import java.io.IOException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties
@PropertySource(value={"classpath:/config/constants.properties"})
public class ConstantProperties {
    private String pdfSuffix;
    private String htmlSuffix;
    private String zipSuffix;
    private String tempFilePath;
    private String mongoDirectory;
    private String pdfTemplateName;
    private String htmlTemplateName;
    private String templatePath;
    private String simsunTtl;
    private String arialuniTtl;

    public String getPdfSuffix() {
        return this.pdfSuffix;
    }

    public void setPdfSuffix(String pdfSuffix) {
        this.pdfSuffix = pdfSuffix;
    }

    public String getHtmlSuffix() {
        return this.htmlSuffix;
    }

    public void setHtmlSuffix(String htmlSuffix) {
        this.htmlSuffix = htmlSuffix;
    }

    public String getZipSuffix() {
        return this.zipSuffix;
    }

    public void setZipSuffix(String zipSuffix) {
        this.zipSuffix = zipSuffix;
    }

    public String getTempFilePath() {
        String normalizedPath = null;
        try {
            normalizedPath = new File(WordUtil.rootPath, this.tempFilePath).getCanonicalPath();
        }
        catch (IOException e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        File file = new File(normalizedPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return normalizedPath + File.separator;
    }

    public void setTempFilePath(String tempFilePath) {
        this.tempFilePath = tempFilePath;
    }

    public String getPdfTemplateName() {
        return this.pdfTemplateName;
    }

    public void setPdfTemplateName(String pdfTemplateName) {
        this.pdfTemplateName = pdfTemplateName;
    }

    public String getHtmlTemplateName() {
        return this.htmlTemplateName;
    }

    public void setHtmlTemplateName(String htmlTemplateName) {
        this.htmlTemplateName = htmlTemplateName;
    }

    public String getTemplatePath() {
        return this.templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getSimsunTtl() {
        return this.simsunTtl;
    }

    public void setSimsunTtl(String simsunTtl) {
        this.simsunTtl = simsunTtl;
    }

    public String getArialuniTtl() {
        return this.arialuniTtl;
    }

    public void setArialuniTtl(String arialuniTtl) {
        this.arialuniTtl = arialuniTtl;
    }

    public String getMongoDirectory() {
        return this.mongoDirectory;
    }

    public void setMongoDirectory(String mongoDirectory) {
        this.mongoDirectory = mongoDirectory;
    }
}

