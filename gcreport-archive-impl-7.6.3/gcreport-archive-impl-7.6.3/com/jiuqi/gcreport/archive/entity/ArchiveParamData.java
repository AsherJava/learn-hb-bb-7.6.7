/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.archive.entity;

import com.jiuqi.gcreport.archive.common.FileType;
import com.jiuqi.gcreport.archive.entity.ArchiveAttachFileInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ArchiveParamData {
    private String archiveInfoId;
    private String orgCode;
    private String dataTime;
    private String pdfFileUrl;
    private String pdfFileName;
    private String pdfFileSize;
    private String pdfFileDigitalDigest;
    private String excelFileUrl;
    private String excelFileName;
    private String excelFileSize;
    private String excelFileDigitalDigest;
    private String attachmentFileUrl;
    private String attachmentFileName;
    private Integer attachmentFileSize;
    private String attachmentDigitalDigest;
    private List<ArchiveAttachFileInfo> attachmentFiles = new ArrayList<ArchiveAttachFileInfo>();
    private Map<String, Object> extendParam;
    private List<FileType> outputType = new ArrayList<FileType>();
    private Integer attachCount;

    public String getArchiveInfoId() {
        return this.archiveInfoId;
    }

    public void setArchiveInfoId(String archiveInfoId) {
        this.archiveInfoId = archiveInfoId;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getPdfFileUrl() {
        return this.pdfFileUrl;
    }

    public void setPdfFileUrl(String pdfFileUrl) {
        this.pdfFileUrl = pdfFileUrl;
    }

    public String getPdfFileName() {
        return this.pdfFileName;
    }

    public void setPdfFileName(String pdfFileName) {
        this.pdfFileName = pdfFileName;
    }

    public String getPdfFileSize() {
        return this.pdfFileSize;
    }

    public void setPdfFileSize(String pdfFileSize) {
        this.pdfFileSize = pdfFileSize;
    }

    public String getPdfFileDigitalDigest() {
        return this.pdfFileDigitalDigest;
    }

    public void setPdfFileDigitalDigest(String pdfFileDigitalDigest) {
        this.pdfFileDigitalDigest = pdfFileDigitalDigest;
    }

    public String getExcelFileUrl() {
        return this.excelFileUrl;
    }

    public void setExcelFileUrl(String excelFileUrl) {
        this.excelFileUrl = excelFileUrl;
    }

    public String getExcelFileName() {
        return this.excelFileName;
    }

    public void setExcelFileName(String excelFileName) {
        this.excelFileName = excelFileName;
    }

    public String getExcelFileSize() {
        return this.excelFileSize;
    }

    public void setExcelFileSize(String excelFileSize) {
        this.excelFileSize = excelFileSize;
    }

    public String getExcelFileDigitalDigest() {
        return this.excelFileDigitalDigest;
    }

    public void setExcelFileDigitalDigest(String excelFileDigitalDigest) {
        this.excelFileDigitalDigest = excelFileDigitalDigest;
    }

    public String getAttachmentFileUrl() {
        return this.attachmentFileUrl;
    }

    public void setAttachmentFileUrl(String attachmentFileUrl) {
        this.attachmentFileUrl = attachmentFileUrl;
    }

    public String getAttachmentFileName() {
        return this.attachmentFileName;
    }

    public void setAttachmentFileName(String attachmentFileName) {
        this.attachmentFileName = attachmentFileName;
    }

    public Integer getAttachmentFileSize() {
        return this.attachmentFileSize;
    }

    public void setAttachmentFileSize(Integer attachmentFileSize) {
        this.attachmentFileSize = attachmentFileSize;
    }

    public String getAttachmentDigitalDigest() {
        return this.attachmentDigitalDigest;
    }

    public void setAttachmentDigitalDigest(String attachmentDigitalDigest) {
        this.attachmentDigitalDigest = attachmentDigitalDigest;
    }

    public Integer getAttachCount() {
        return this.attachCount;
    }

    public void setAttachCount(Integer attachCount) {
        this.attachCount = attachCount;
    }

    public List<FileType> getOutputType() {
        return this.outputType;
    }

    public void setOutputType(List<FileType> outputType) {
        this.outputType = outputType;
    }

    public void addType(FileType fileType) {
        this.outputType.add(fileType);
    }

    public Map<String, Object> getExtendParam() {
        return this.extendParam;
    }

    public void setExtendParam(Map<String, Object> extendParam) {
        this.extendParam = extendParam;
    }

    public ArchiveParamData(String orgCode, String dataTime) {
        this.orgCode = orgCode;
        this.dataTime = dataTime;
    }

    public ArchiveParamData() {
    }

    public void addAttachmentFile(String fileName, String filePath, int fileSize, String digitalDigest) {
        this.attachmentFiles.add(new ArchiveAttachFileInfo(fileName, filePath, fileSize, digitalDigest));
    }

    public List<ArchiveAttachFileInfo> getAttachmentFiles() {
        return this.attachmentFiles;
    }

    public String getAllFilePaths() {
        return this.attachmentFiles.stream().map(ArchiveAttachFileInfo::getFileUrl).collect(Collectors.joining(";"));
    }

    public String getAllFileNames() {
        return this.attachmentFiles.stream().map(ArchiveAttachFileInfo::getFileName).collect(Collectors.joining(";"));
    }
}

