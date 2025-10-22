/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.paramInfo;

import java.util.ArrayList;
import java.util.List;

public class BatchExportOrImportFileType {
    private String fileType;
    private String fileTypeName;

    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileTypeName() {
        return this.fileTypeName;
    }

    public void setFileTypeName(String fileTypeName) {
        this.fileTypeName = fileTypeName;
    }

    public static List<BatchExportOrImportFileType> getAllBatchExportType() {
        ArrayList<BatchExportOrImportFileType> result = new ArrayList<BatchExportOrImportFileType>();
        BatchExportOrImportFileType batchExportFileTypeOFExcel = new BatchExportOrImportFileType();
        batchExportFileTypeOFExcel.setFileType("EXCEL");
        batchExportFileTypeOFExcel.setFileTypeName("EXCEL");
        BatchExportOrImportFileType batchExportFileTypeOFPdf = new BatchExportOrImportFileType();
        batchExportFileTypeOFPdf.setFileType("PDF");
        batchExportFileTypeOFPdf.setFileTypeName("PDF");
        BatchExportOrImportFileType batchExportFileTypeOFTXT = new BatchExportOrImportFileType();
        batchExportFileTypeOFTXT.setFileType("TXT");
        batchExportFileTypeOFTXT.setFileTypeName("TXT");
        BatchExportOrImportFileType batchExportFileTypeOFJio = new BatchExportOrImportFileType();
        batchExportFileTypeOFJio.setFileType("JIO");
        batchExportFileTypeOFJio.setFileTypeName("JIO");
        BatchExportOrImportFileType batchExportFileTypeOFCSV = new BatchExportOrImportFileType();
        batchExportFileTypeOFCSV.setFileType("CSV");
        batchExportFileTypeOFCSV.setFileTypeName("CSV");
        result.add(batchExportFileTypeOFExcel);
        result.add(batchExportFileTypeOFJio);
        result.add(batchExportFileTypeOFTXT);
        result.add(batchExportFileTypeOFPdf);
        result.add(batchExportFileTypeOFCSV);
        return result;
    }

    public static List<BatchExportOrImportFileType> getAllBatchImpotyType() {
        ArrayList<BatchExportOrImportFileType> result = new ArrayList<BatchExportOrImportFileType>();
        BatchExportOrImportFileType batchExportFileTypeOFExcel = new BatchExportOrImportFileType();
        batchExportFileTypeOFExcel.setFileType("EXCEL");
        batchExportFileTypeOFExcel.setFileTypeName("EXCEL");
        BatchExportOrImportFileType batchExportFileTypeOFTXT = new BatchExportOrImportFileType();
        batchExportFileTypeOFTXT.setFileType("TXT");
        batchExportFileTypeOFTXT.setFileTypeName("TXT");
        BatchExportOrImportFileType batchExportFileTypeOFJio = new BatchExportOrImportFileType();
        batchExportFileTypeOFJio.setFileType("JIO");
        batchExportFileTypeOFJio.setFileTypeName("JIO");
        BatchExportOrImportFileType batchExportFileTypeOFCSV = new BatchExportOrImportFileType();
        batchExportFileTypeOFCSV.setFileType("CSV");
        batchExportFileTypeOFCSV.setFileTypeName("CSV");
        result.add(batchExportFileTypeOFExcel);
        result.add(batchExportFileTypeOFJio);
        result.add(batchExportFileTypeOFTXT);
        result.add(batchExportFileTypeOFCSV);
        return result;
    }
}

