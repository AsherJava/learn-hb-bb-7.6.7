/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  nr.midstore.core.definition.common.PublishStateType
 */
package nr.midstore.design.vo;

import nr.midstore.core.definition.common.PublishStateType;
import nr.midstore.design.domain.ExchangeSchemeDTO;

public class ExchangeSchemeVO
extends ExchangeSchemeDTO {
    private PublishStateType publishState;
    private String taskCode;
    private String taskTitle;
    private String configName;

    public PublishStateType getPublishState() {
        return this.publishState;
    }

    public void setPublishState(PublishStateType publishState) {
        this.publishState = publishState;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getConfigName() {
        return this.configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }
}

