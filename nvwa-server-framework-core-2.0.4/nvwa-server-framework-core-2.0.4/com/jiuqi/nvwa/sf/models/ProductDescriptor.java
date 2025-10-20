/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.models;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

public class ProductDescriptor
implements Serializable {
    private static final String PROP_ID = "id";
    private static final String PROP_VERSION = "version";
    private static final String PROP_DESC = "desc";
    private String id;
    private String version;
    private String desc;
    private Properties properties = new Properties();

    public void load(InputStream is) throws IOException {
        Properties p = new Properties();
        p.load(is);
        this.id = p.getProperty(PROP_ID);
        if (this.id == null) {
            throw new IllegalArgumentException("Null product id");
        }
        this.version = p.getProperty(PROP_VERSION);
        if (this.version == null) {
            throw new IllegalArgumentException("Null product version");
        }
        this.desc = p.getProperty(PROP_DESC);
        this.properties = new Properties(p);
    }

    public String getId() {
        return this.id;
    }

    public String getVersion() {
        return this.version;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getProperty(String key, String defaultValue) {
        return this.properties.getProperty(key, defaultValue);
    }
}

