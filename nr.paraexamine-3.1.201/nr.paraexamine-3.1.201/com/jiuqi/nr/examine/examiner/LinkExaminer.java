/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.examine.examiner;

import com.jiuqi.nr.examine.common.ErrorType;
import com.jiuqi.nr.examine.common.ParaType;
import com.jiuqi.nr.examine.examiner.AbstractExaminer;
import com.jiuqi.nr.examine.task.ExamineTask;
import java.util.List;

public class LinkExaminer
extends AbstractExaminer {
    public LinkExaminer(ExamineTask task) {
        super(task);
    }

    @Override
    protected ParaType getParaType() {
        return ParaType.LINK;
    }

    @Override
    protected void checkRefuse() {
        this.checkRegionRefuse();
        this.checkFieldRefuse();
    }

    private void checkRegionRefuse() {
        List links = this.task.getEnv().getDesignParaCheckController().listLinkNotInRegion();
        links.forEach(link -> this.writeRefuse(link.getKey(), null, ErrorType.LINK_REFUSE_REGION));
    }

    private void checkFieldRefuse() {
    }

    @Override
    protected void checkQuote() {
        this.checkDataCorridinateDuplicate();
        this.checkView();
    }

    private void checkView() {
        List keys = this.task.getEnv().getDesignParaCheckController().listLinkKeyRefuseView(this.task.getKey());
        keys.forEach(key -> this.writeRefuse((String)key, null, ErrorType.LINK_QUOTE_VIEW_LOST));
        keys = this.task.getEnv().getDesignParaCheckController().listLinkKeyViewQuoteError(this.task.getKey());
        keys.forEach(key -> this.writeRefuse((String)key, null, ErrorType.LINK_QUOTE_VIEW_WRONG));
    }

    private void checkDataCorridinateDuplicate() {
        List keys = this.task.getEnv().getDesignParaCheckController().listLinkKeyDataCoordinatesDuplicate(this.task.getKey());
        if (keys != null) {
            keys.forEach(key -> this.writeQuotError((String)key, null, ErrorType.LINK_QUOTE_DUPLICATE_DATA));
        }
    }

    @Override
    protected void checkError() {
        this.checkPhysicalCorridinateDuplicate();
    }

    private void checkPhysicalCorridinateDuplicate() {
        List keys = this.task.getEnv().getDesignParaCheckController().listLinkKeyPhysicalCoordinatesDuplicate(this.task.getKey());
        if (keys != null) {
            keys.forEach(key -> this.writeError((String)key, null, ErrorType.LINK_ERROR_DUPLICATE_PHYSICAL));
        }
    }

    @Override
    protected void init() {
    }
}

