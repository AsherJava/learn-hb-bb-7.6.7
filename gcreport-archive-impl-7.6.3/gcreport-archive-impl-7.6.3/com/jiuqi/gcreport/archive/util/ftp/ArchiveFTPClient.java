/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.net.ftp.FTPClient
 */
package com.jiuqi.gcreport.archive.util.ftp;

import org.apache.commons.net.ftp.FTPClient;

public class ArchiveFTPClient
extends FTPClient {
    private String rootPatch;

    public String getRootPatch() {
        return this.rootPatch;
    }

    public void setRootPatch(String rootPatch) {
        this.rootPatch = rootPatch;
    }
}

