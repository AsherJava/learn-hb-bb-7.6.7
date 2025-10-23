/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.blob.conf;

import com.jiuqi.np.blob.impl.BlobContainerFileImpl;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="jiuqi.np.blob")
public class NpBlobProperties {
    public static final String PROP_PREFIX = "jiuqi.np.blob";
    private String type = "DB";
    private String rootName = "NpBlobStorage";
    @Value(value="${jiuqi.np.blob.path:#{null}}")
    private String rootPath;

    public String getRootPath() {
        return this.rootPath;
    }

    public void setRootPath() {
        Properties properties = System.getProperties();
        if (null == properties.get("jiuqi.np.blob.path")) {
            properties.setProperty("jiuqi.np.blob.path", this.rootPath);
            System.setProperties(properties);
        }
    }

    public String getRootName() {
        return this.rootName;
    }

    public void setRootName(String rootName) {
        BlobContainerFileImpl.setROOTNAME(rootName);
        this.rootName = rootName;
    }

    public String getDataSourceType() {
        return this.type;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

