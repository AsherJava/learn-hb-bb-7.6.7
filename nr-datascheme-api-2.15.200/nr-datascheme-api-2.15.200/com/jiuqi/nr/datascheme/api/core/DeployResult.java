/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonSetter
 *  com.fasterxml.jackson.annotation.JsonValue
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 */
package com.jiuqi.nr.datascheme.api.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonValue;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DeployResultDetail;
import com.jiuqi.nr.datascheme.api.core.DeployStatusEnum;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class DeployResult {
    private Result result;
    private String message;
    private List<DeployResultDetail> details;

    public DeployResult() {
        this(Result.SUCCESS, "");
    }

    public DeployResult(Result result, String message) {
        this.result = result;
        this.message = message;
    }

    @Deprecated
    @JsonIgnore
    public boolean getCheckState() {
        return this.result != Result.CHECK_ERROR;
    }

    @Deprecated
    @JsonSetter(value="checkState")
    public void setCheckState(boolean checkState) {
        this.result = Result.CHECK_ERROR;
    }

    @Deprecated
    @JsonIgnore
    public List<DeployResultDetail> getCheckDetials() {
        if (!this.getCheckState()) {
            return this.details;
        }
        return Collections.emptyList();
    }

    @Deprecated
    @JsonSetter(value="checkDetials")
    public void setCheckDetials(List<DeployResultDetail> checkDetials) {
        if (CollectionUtils.isEmpty(checkDetials)) {
            return;
        }
        if (null == this.details) {
            this.details = new ArrayList<DeployResultDetail>();
        }
        this.details.addAll(checkDetials);
    }

    @Deprecated
    @JsonIgnore
    public int getDeployStateValue() {
        switch (this.result) {
            case CHECK_ERROR: 
            case DEPLOY_ERROR: {
                return DeployStatusEnum.FAIL.getValue();
            }
        }
        return DeployStatusEnum.SUCCESS.getValue();
    }

    @Deprecated
    @JsonSetter(value="deployState")
    public void setDeployStateValue(int deployState) {
        if (Result.CHECK_ERROR == this.result) {
            return;
        }
        DeployStatusEnum status = DeployStatusEnum.valueOf(deployState);
        this.result = status == DeployStatusEnum.FAIL ? Result.DEPLOY_ERROR : Result.SUCCESS;
    }

    @Deprecated
    @JsonIgnore
    public List<DeployResultDetail> getDeployDetials() {
        if (this.result != Result.CHECK_ERROR) {
            return this.details;
        }
        return Collections.emptyList();
    }

    @Deprecated
    @JsonSetter(value="deployDetials")
    public void setDeployDetials(List<DeployResultDetail> deployDetials) {
        if (CollectionUtils.isEmpty(deployDetials)) {
            return;
        }
        if (null == this.details) {
            this.details = new ArrayList<DeployResultDetail>();
        }
        this.details.addAll(deployDetials);
    }

    @Deprecated
    @JsonIgnore
    public String getDeployMessage() {
        return this.message;
    }

    @Deprecated
    @JsonSetter(value="deployMessage")
    public void setDeployMessage(String deployMessage) {
        this.message = deployMessage;
    }

    @JsonIgnore
    @Deprecated
    public void setDeployState(DeployStatusEnum deployState) {
        this.result = deployState == DeployStatusEnum.FAIL ? Result.DEPLOY_ERROR : Result.SUCCESS;
    }

    @JsonIgnore
    @Deprecated
    public DeployStatusEnum getDeployState() {
        switch (this.result) {
            case CHECK_ERROR: 
            case DEPLOY_ERROR: {
                return DeployStatusEnum.FAIL;
            }
        }
        return DeployStatusEnum.SUCCESS;
    }

    @Deprecated
    @JsonIgnore
    public boolean isSuccess() {
        return this.getCheckState() && DeployStatusEnum.SUCCESS == this.getDeployState();
    }

    @JsonIgnore
    public String getDeployErrorMessage() {
        if (StringUtils.hasText(this.message)) {
            return this.message;
        }
        if (Result.CHECK_ERROR == this.result) {
            if (CollectionUtils.isEmpty(this.getCheckDetials())) {
                return null;
            }
            return this.getCheckDetials().stream().filter(d -> !d.isSuccess()).map(d -> StringUtils.collectionToCommaDelimitedString(d.getErrorMsg())).collect(Collectors.joining());
        }
        if (Result.DEPLOY_ERROR == this.result) {
            if (CollectionUtils.isEmpty(this.getDeployDetials())) {
                return null;
            }
            return this.getDeployDetials().stream().filter(d -> !d.isSuccess()).map(d -> StringUtils.collectionToCommaDelimitedString(d.getErrorMsg())).collect(Collectors.joining());
        }
        return null;
    }

    @JsonIgnore
    public String getDeployDetialMessage() {
        if (StringUtils.hasText(this.message)) {
            return this.message;
        }
        if (!this.getCheckState()) {
            if (CollectionUtils.isEmpty(this.getCheckDetials())) {
                return null;
            }
            return this.getCheckDetials().stream().filter(d -> !d.isSuccess()).map(DeployResultDetail::checkDetail).collect(Collectors.joining("\r\n"));
        }
        if (CollectionUtils.isEmpty(this.getDeployDetials())) {
            return null;
        }
        return this.getDeployDetials().stream().map(DeployResultDetail::deployDetail).collect(Collectors.joining("\r\n"));
    }

    public void addCheckError(DataTable dataTable, String msg) {
        this.result = Result.CHECK_ERROR;
        if (null == this.details) {
            this.details = new ArrayList<DeployResultDetail>();
        }
        this.details.add(new DeployResultDetail(dataTable.getKey(), dataTable.getCode(), dataTable.getTitle(), Collections.singletonList(msg)));
    }

    public void addCheckError(DesignTableModelDefine tableModel, String msg) {
        this.result = Result.CHECK_ERROR;
        if (null == this.details) {
            this.details = new ArrayList<DeployResultDetail>();
        }
        this.details.add(new DeployResultDetail(tableModel.getID(), tableModel.getCode(), tableModel.getTitle(), Collections.singletonList(msg)));
    }

    public void addDeployDetail(DataTable dataTable, List<String> errorMsg, List<String> warnMsg) {
        if (!CollectionUtils.isEmpty(errorMsg)) {
            this.result = Result.DEPLOY_ERROR;
        } else if (!CollectionUtils.isEmpty(warnMsg) && this.result == Result.SUCCESS) {
            this.result = Result.DEPLOY_WARN;
        }
        if (null == this.details) {
            this.details = new ArrayList<DeployResultDetail>();
        }
        this.details.add(new DeployResultDetail(dataTable.getKey(), dataTable.getCode(), dataTable.getTitle(), errorMsg, warnMsg));
    }

    public void addDeployDetail(DesignTableModelDefine tableModel, List<String> msg) {
        if (!CollectionUtils.isEmpty(msg)) {
            this.result = Result.DEPLOY_ERROR;
        }
        if (null == this.details) {
            this.details = new ArrayList<DeployResultDetail>();
        }
        this.details.add(new DeployResultDetail(tableModel.getID(), tableModel.getCode(), tableModel.getTitle(), msg));
    }

    public Result getResult() {
        return this.result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DeployResultDetail> getDetails() {
        return this.details;
    }

    public void setDetails(List<DeployResultDetail> details) {
        this.details = details;
    }

    public static enum Result {
        CHECK_ERROR(0),
        DEPLOY_ERROR(1),
        DEPLOY_WARN(2),
        SUCCESS(3);

        private final int value;

        private Result(int value) {
            this.value = value;
        }

        @JsonValue
        public int getValue() {
            return this.value;
        }

        @JsonCreator
        public static Result getResult(int value) {
            for (Result r : Result.values()) {
                if (r.getValue() != value) continue;
                return r;
            }
            return SUCCESS;
        }
    }
}

