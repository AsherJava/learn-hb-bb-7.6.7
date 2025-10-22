/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.message.pojo;

import java.io.File;
import java.io.Serializable;
import java.util.Set;

public class MailDTO
implements Serializable {
    private static final long serialVersionUID = 4042276207480326405L;
    private String from;
    private Set<String> to;
    private String subject;
    private String text;
    private boolean html;
    private Set<String> cc;
    private Set<String> bcc;
    private File[] files;

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Set<String> getTo() {
        return this.to;
    }

    public void setTo(Set<String> to) {
        this.to = to;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCc(Set<String> cc) {
        this.cc = cc;
    }

    public void setBcc(Set<String> bcc) {
        this.bcc = bcc;
    }

    public File[] getFiles() {
        return this.files;
    }

    public void setFiles(File[] files) {
        this.files = files;
    }

    public Set<String> getCc() {
        return this.cc;
    }

    public Set<String> getBcc() {
        return this.bcc;
    }

    public boolean isHtml() {
        return this.html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }

    public String toString() {
        return "MailVO{, from='" + this.from + '\'' + ", to='" + this.to + '\'' + ", subject='" + (this.subject == null ? "" : String.join((CharSequence)",", this.subject)) + '\'' + ", text='" + this.text + '\'' + ", cc='" + (this.cc == null ? "" : String.join((CharSequence)",", this.cc)) + '\'' + ", bcc='" + (this.bcc == null ? "" : String.join((CharSequence)",", this.bcc)) + '\'' + '}';
    }
}

