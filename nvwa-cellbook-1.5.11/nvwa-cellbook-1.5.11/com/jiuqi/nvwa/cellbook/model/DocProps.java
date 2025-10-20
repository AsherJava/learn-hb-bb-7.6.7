/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.model;

import com.jiuqi.nvwa.cellbook.constant.DocModel;
import java.io.Serializable;

public class DocProps
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String version = "2.0.0";
    private String encoding = "UTF-8";
    private String creator;
    private long created;
    private String modifior;
    private long modified;
    private String company;
    private String docSecurity;
    private DocModel model = DocModel.EDIT;

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public long getCreated() {
        return this.created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getModifior() {
        return this.modifior;
    }

    public void setModifior(String modifior) {
        this.modifior = modifior;
    }

    public long getModified() {
        return this.modified;
    }

    public void setModified(long modified) {
        this.modified = modified;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDocSecurity() {
        return this.docSecurity;
    }

    public void setDocSecurity(String docSecurity) {
        this.docSecurity = docSecurity;
    }

    public DocModel getModel() {
        return this.model;
    }

    public void setModel(DocModel model) {
        this.model = model;
    }
}

