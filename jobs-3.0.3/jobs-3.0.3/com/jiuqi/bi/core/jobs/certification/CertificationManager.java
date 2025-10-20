/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.certification;

import com.jiuqi.bi.core.jobs.certification.Certifier;
import com.jiuqi.bi.core.jobs.certification.DefaultCertifier;

public class CertificationManager {
    private static final CertificationManager instance = new CertificationManager();
    private Certifier certifier = new DefaultCertifier();

    private CertificationManager() {
    }

    public static CertificationManager getInstance() {
        return instance;
    }

    public Certifier getCertifier() {
        return this.certifier;
    }

    public void setCertifier(Certifier certifier) {
        if (certifier == null) {
            certifier = new DefaultCertifier();
        }
        this.certifier = certifier;
    }
}

