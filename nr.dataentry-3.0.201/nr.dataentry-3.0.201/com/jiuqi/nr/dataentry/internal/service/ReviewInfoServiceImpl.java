/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.logic.facade.service.ICheckSchemeRecordService
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.nr.data.logic.facade.service.ICheckSchemeRecordService;
import com.jiuqi.nr.dataentry.bean.ReviewInfoParam;
import com.jiuqi.nr.dataentry.internal.service.util.CheckReviewTransformUtil;
import com.jiuqi.nr.dataentry.service.IReviewInfoService;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewInfoServiceImpl
implements IReviewInfoService {
    private static final Logger logger = LoggerFactory.getLogger(ReviewInfoServiceImpl.class);
    @Autowired
    private ICheckSchemeRecordService checkSchemeRecordService;
    @Autowired
    private CheckReviewTransformUtil checkReviewTransformUtil;

    @Override
    public ReviewInfoParam getReviewInfoTable(String formSchemeKey) {
        return this.checkReviewTransformUtil.getReviewInfoParam(this.checkSchemeRecordService.queryCheckSchemeRecord(formSchemeKey));
    }

    @Override
    public ReviewInfoParam getFormulaSchemeInfoTable(String formSchemeKey, String formulaSchemeKey) {
        ReviewInfoParam reviewInfoTable = this.getReviewInfoTable(formSchemeKey);
        String formulaSchemeKeys = reviewInfoTable.getCheckInfo().getFormulaSchemeKeys();
        if (formulaSchemeKeys.equals(formulaSchemeKey)) {
            return reviewInfoTable;
        }
        return null;
    }

    @Override
    public ReturnInfo saveOrUpdatetRevienInfo(ReviewInfoParam param) {
        ReturnInfo returnInfo = new ReturnInfo();
        this.checkSchemeRecordService.saveCheckSchemeRecord(this.checkReviewTransformUtil.getCheckResultQueryParam(param.getCheckInfo()));
        returnInfo.setMessage("success");
        return returnInfo;
    }
}

