/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package nr.single.map.configurations.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SingleConfigInfo
implements Serializable {
    private static final long serialVersionUID = 5164322232672970981L;
    private String configKey;
    private String taskKey;
    private String schemeKey;
    private String configName;
    private String configCode;
    private String fileFlag;
    private String taskFlag;
    private String order;

    public SingleConfigInfo() {
    }

    public SingleConfigInfo(String configKey, String taskKey, String schemeKey, String configName, String fileFlag, String taskFlag, String order) {
        this.configKey = configKey;
        this.taskKey = taskKey;
        this.schemeKey = schemeKey;
        this.configName = configName;
        this.fileFlag = fileFlag;
        this.taskFlag = taskFlag;
        this.order = order;
    }

    public String getConfigKey() {
        return this.configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public String getConfigName() {
        return this.configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getFileFlag() {
        return this.fileFlag;
    }

    public void setFileFlag(String fileFlag) {
        this.fileFlag = fileFlag;
    }

    public String getTaskFlag() {
        return this.taskFlag;
    }

    public void setTaskFlag(String taskFlag) {
        this.taskFlag = taskFlag;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getConfigCode() {
        return this.configCode;
    }

    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }
}

