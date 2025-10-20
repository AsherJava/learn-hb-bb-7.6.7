/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.certification;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.certification.CertificationInfo;
import com.jiuqi.bi.core.jobs.certification.Certifier;

public class DefaultCertifier
implements Certifier {
    @Override
    public boolean certify(JobContext context, String user, CertificationInfo certification) {
        return true;
    }
}

