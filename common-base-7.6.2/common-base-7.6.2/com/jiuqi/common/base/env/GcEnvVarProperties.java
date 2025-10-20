/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base.env;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="jiuqi.gcreport.env", ignoreInvalidFields=true)
public class GcEnvVarProperties {
    public static final String GC_ENV_VAR_PRO_ROOT = "jiuqi.gcreport.env";
    private boolean inittabledefine = true;
    private boolean orglocalcache = true;
    private boolean logdetail = true;

    public boolean isInittabledefine() {
        return this.inittabledefine;
    }

    public void setInittabledefine(boolean inittabledefine) {
        this.inittabledefine = inittabledefine;
    }

    public boolean isOrglocalcache() {
        return this.orglocalcache;
    }

    public void setOrglocalcache(boolean orglocalcache) {
        this.orglocalcache = orglocalcache;
    }

    public void setLogdetail(boolean logdetail) {
        this.logdetail = logdetail;
    }

    public boolean isLogdetail() {
        return this.logdetail;
    }
}

