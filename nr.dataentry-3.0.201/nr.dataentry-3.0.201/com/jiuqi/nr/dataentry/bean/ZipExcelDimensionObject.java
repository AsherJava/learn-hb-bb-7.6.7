/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class ZipExcelDimensionObject
implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> dimension;
    private String fileName;
    private String date;
    private String dateCode;
    private String fileAddress;

    public File getFile() throws IOException {
        try {
            PathUtils.validatePathManipulation((String)this.fileAddress);
        }
        catch (SecurityContentException e) {
            throw new RuntimeException(e);
        }
        File excelFile = new File(this.fileAddress);
        return excelFile;
    }

    public List<String> getDimension() {
        return this.dimension;
    }

    public void setDimension(List<String> dimension) {
        this.dimension = dimension;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateCode() {
        return this.dateCode;
    }

    public void setDateCode(String dateCode) {
        this.dateCode = dateCode;
    }

    public String getFileAddress() {
        return this.fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }
}

