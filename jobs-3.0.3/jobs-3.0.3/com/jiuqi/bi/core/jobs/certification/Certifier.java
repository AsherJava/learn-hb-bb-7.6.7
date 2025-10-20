/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.certification;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.certification.CertificationInfo;

public interface Certifier {
    public boolean certify(JobContext var1, String var2, CertificationInfo var3) throws Exception;
}

