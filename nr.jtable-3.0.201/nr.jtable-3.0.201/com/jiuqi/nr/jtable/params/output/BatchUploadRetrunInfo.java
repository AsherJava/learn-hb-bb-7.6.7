/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.jtable.params.output.ExternalBatchUploadResult;
import com.jiuqi.nr.jtable.params.output.LevelUploadInfo;
import com.jiuqi.nr.jtable.params.output.LevelUploadObj;
import com.jiuqi.nr.jtable.params.output.MultCheckResult;
import com.jiuqi.nr.jtable.params.output.UploadBeforeCheck;
import com.jiuqi.nr.jtable.params.output.UploadBeforeNodeCheck;
import com.jiuqi.nr.jtable.params.output.UploadReturnInfo;
import java.io.Serializable;
import java.util.List;

public class BatchUploadRetrunInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private UploadBeforeCheck uploadBeforeCheck;
    private UploadBeforeNodeCheck uploadBeforeNodeCheck;
    private UploadReturnInfo uploadReturnInfo;
    private List<LevelUploadInfo> levelUploadInfo;
    private String completeMsg;
    private LevelUploadObj levelUploadObj = new LevelUploadObj();
    private String status;
    private ExternalBatchUploadResult externalBatchUploadResult;
    private MultCheckResult multCheckResult;
    private String finalaccountsAudit;

    public List<LevelUploadInfo> getLevelUploadInfo() {
        return this.levelUploadInfo;
    }

    public void setLevelUploadInfo(List<LevelUploadInfo> levelUploadInfo) {
        this.levelUploadInfo = levelUploadInfo;
    }

    public UploadBeforeCheck getUploadBeforeCheck() {
        return this.uploadBeforeCheck;
    }

    public void setUploadBeforeCheck(UploadBeforeCheck uploadBeforeCheck) {
        this.uploadBeforeCheck = uploadBeforeCheck;
    }

    public UploadBeforeNodeCheck getUploadBeforeNodeCheck() {
        return this.uploadBeforeNodeCheck;
    }

    public void setUploadBeforeNodeCheck(UploadBeforeNodeCheck uploadBeforeNodeCheck) {
        this.uploadBeforeNodeCheck = uploadBeforeNodeCheck;
    }

    public UploadReturnInfo getUploadReturnInfo() {
        return this.uploadReturnInfo;
    }

    public void setUploadReturnInfo(UploadReturnInfo uploadReturnInfo) {
        this.uploadReturnInfo = uploadReturnInfo;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LevelUploadObj getLevelUploadObj() {
        return this.levelUploadObj;
    }

    public void setLevelUploadObj(LevelUploadObj levelUploadObj) {
        this.levelUploadObj = levelUploadObj;
    }

    public String getCompleteMsg() {
        return this.completeMsg;
    }

    public void setCompleteMsg(String completeMsg) {
        this.completeMsg = completeMsg;
    }

    public ExternalBatchUploadResult getExternalBatchUploadResult() {
        return this.externalBatchUploadResult;
    }

    public void setExternalBatchUploadResult(ExternalBatchUploadResult externalBatchUploadResult) {
        this.externalBatchUploadResult = externalBatchUploadResult;
    }

    public MultCheckResult getMultCheckResult() {
        return this.multCheckResult;
    }

    public void setMultCheckResult(MultCheckResult multCheckResult) {
        this.multCheckResult = multCheckResult;
    }

    public String getFinalaccountsAudit() {
        return this.finalaccountsAudit;
    }

    public void setFinalaccountsAudit(String finalaccountsAudit) {
        this.finalaccountsAudit = finalaccountsAudit;
    }
}

