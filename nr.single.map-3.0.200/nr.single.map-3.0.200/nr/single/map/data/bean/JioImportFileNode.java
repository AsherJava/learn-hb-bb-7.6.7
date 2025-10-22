/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data.bean;

import java.io.Serializable;

public class JioImportFileNode
implements Serializable {
    private static final long serialVersionUID = 1L;
    String fileName;
    String fileKey;
    String filePath;
    String configKey;

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getConfigKey() {
        return this.configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}

