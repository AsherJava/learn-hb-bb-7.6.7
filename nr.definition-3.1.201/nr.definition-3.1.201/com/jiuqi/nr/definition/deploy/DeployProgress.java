/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.progress.ProgressItem
 */
package com.jiuqi.nr.definition.deploy;

import com.jiuqi.np.definition.progress.ProgressItem;

public enum DeployProgress {
    ANALYSE("\u5206\u6790\u53c2\u6570", 0, 0),
    DEPLOY_TABLE("\u53d1\u5e03\u6570\u636e\u65b9\u6848", 1, 0),
    DEPLOY_TASK("\u53d1\u5e03\u62a5\u8868\u53c2\u6570", 2, 0),
    DEPLOY_FORM("\u53d1\u5e03\u62a5\u8868", 2, 30),
    DEPLOY_FORMULA("\u53d1\u5e03\u516c\u5f0f", 2, 50),
    DEPLOY_PRINT("\u53d1\u5e03\u6253\u5370\u65b9\u6848", 2, 80),
    DEPLOY_FORM_FIELD_INFO("\u53d1\u5e03\u62a5\u8868\u6307\u6807\u4fe1\u606f", 2, 85),
    DEPLOY_CONDITIONAL_STYLE("\u53d1\u5e03\u6761\u4ef6\u6837\u5f0f", 2, 86),
    DEPLOY_REPORT_TEMPLATE("\u53d1\u5e03\u5206\u6790\u62a5\u544a\u6a21\u677f", 2, 87),
    DEPLOY_CACHE("\u5237\u65b0\u7f13\u5b58", 2, 90),
    DEPLOY_EVENT("\u53d1\u5e03\u5176\u5b83\u53c2\u6570", 3, 0),
    FINISH("\u53d1\u5e03\u5b8c\u6210", 4, 0);

    private final String message;
    private final int stepCount;
    private final int currentProgress;

    private DeployProgress(String message, int stepCount, int currentProgress) {
        this.message = message;
        this.stepCount = stepCount;
        this.currentProgress = currentProgress;
    }

    public String getMessage() {
        return this.message;
    }

    public int getStepCount() {
        return this.stepCount;
    }

    public int getCurrentProgress() {
        return this.currentProgress;
    }

    public static ProgressItem getProgressItem(String progressId) {
        return DeployProgress.getProgressItem(progressId, true);
    }

    public static ProgressItem getProgressItem(String progressId, boolean deployDataScheme) {
        ProgressItem progressItem = new ProgressItem();
        progressItem.setProgressId(progressId);
        progressItem.addStepTitle(ANALYSE.getMessage());
        if (deployDataScheme) {
            progressItem.addStepTitle(DEPLOY_TABLE.getMessage());
        }
        progressItem.addStepTitle(DEPLOY_TASK.getMessage());
        progressItem.addStepTitle(DEPLOY_EVENT.getMessage());
        return progressItem;
    }
}

