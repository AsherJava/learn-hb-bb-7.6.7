/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.paraout.bean;

import java.io.Serializable;

public class ParaExportParam
implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean downloadTask;
    private boolean downloadFormScheme;
    private boolean downloadEnum;
    private boolean downloadForm;
    private boolean downloadTaskLink;
    private boolean downloadFormula;
    private boolean downloadPrint;
    private boolean downloadQuery;

    public void selectAll() {
        this.downloadTask = true;
        this.downloadFormScheme = true;
        this.downloadEnum = true;
        this.downloadForm = true;
        this.downloadTaskLink = true;
        this.downloadFormula = true;
        this.downloadPrint = true;
        this.downloadQuery = true;
    }

    public void notSelectAll() {
        this.downloadTask = false;
        this.downloadFormScheme = false;
        this.downloadEnum = false;
        this.downloadForm = false;
        this.downloadTaskLink = false;
        this.downloadFormula = false;
        this.downloadPrint = false;
        this.downloadQuery = false;
    }

    public boolean isDownloadTask() {
        return this.downloadTask;
    }

    public void setDownloadTask(boolean downloadTask) {
        this.downloadTask = downloadTask;
    }

    public boolean isDownloadFormScheme() {
        return this.downloadFormScheme;
    }

    public void setDownloadFormScheme(boolean downloadFormScheme) {
        this.downloadFormScheme = downloadFormScheme;
    }

    public boolean isDownloadEnum() {
        return this.downloadEnum;
    }

    public void setDownloadEnum(boolean downloadEnum) {
        this.downloadEnum = downloadEnum;
    }

    public boolean isDownloadForm() {
        return this.downloadForm;
    }

    public void setDownloadForm(boolean downloadForm) {
        this.downloadForm = downloadForm;
    }

    public boolean isDownloadTaskLink() {
        return this.downloadTaskLink;
    }

    public void setDownloadTaskLink(boolean downloadTaskLink) {
        this.downloadTaskLink = downloadTaskLink;
    }

    public boolean isDownloadFormula() {
        return this.downloadFormula;
    }

    public void setDownloadFormula(boolean downloadFormula) {
        this.downloadFormula = downloadFormula;
    }

    public boolean isDownloadPrint() {
        return this.downloadPrint;
    }

    public void setDownloadPrint(boolean downloadPrint) {
        this.downloadPrint = downloadPrint;
    }

    public boolean isDownloadQuery() {
        return this.downloadQuery;
    }

    public void setDownloadQuery(boolean downloadQuery) {
        this.downloadQuery = downloadQuery;
    }
}

