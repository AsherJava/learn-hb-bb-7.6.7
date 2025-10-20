/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  com.jiuqi.common.expimp.progress.service.ProgressService
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.inputdata.api.InputDataCheckClient
 *  com.jiuqi.gcreport.inputdata.check.InputDataCheckCondition
 *  com.jiuqi.gcreport.inputdata.check.InputDataCheckInitCondition
 *  com.jiuqi.gcreport.inputdata.check.InputDataCheckUpdateMemoVO
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.inputdata.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.common.expimp.progress.service.ProgressService;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.api.InputDataCheckClient;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckCondition;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckInitCondition;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckUpdateMemoVO;
import com.jiuqi.gcreport.inputdata.check.env.impl.InputDataCheckEnvContextImpl;
import com.jiuqi.gcreport.inputdata.check.service.InputDataCheckService;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@Primary
@RestController
public class InputDataCheckController
implements InputDataCheckClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private InputDataCheckService inputDataCheckService;
    @Autowired
    private ProgressService<InputDataCheckEnvContextImpl, List<String>> progressService;

    public BusinessResponseEntity<Object> checkTabDatas(InputDataCheckCondition inputDataCheckCondition) {
        return BusinessResponseEntity.ok(this.inputDataCheckService.checkTabDatas(inputDataCheckCondition));
    }

    public BusinessResponseEntity<Object> unCheckTabDatas(InputDataCheckCondition inputDataCheckCondition) {
        return BusinessResponseEntity.ok(this.inputDataCheckService.unCheckTabDatas(inputDataCheckCondition));
    }

    public BusinessResponseEntity<Object> allCheckTabDatas(InputDataCheckCondition inputDataCheckCondition) {
        return BusinessResponseEntity.ok(this.inputDataCheckService.allCheckTabDatas(inputDataCheckCondition));
    }

    public BusinessResponseEntity<Object> initData(InputDataCheckInitCondition inputDataCheckInitCondition) {
        try {
            return BusinessResponseEntity.ok((Object)this.inputDataCheckService.initData(inputDataCheckInitCondition));
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return BusinessResponseEntity.error((String)e.getMessage());
        }
    }

    public BusinessResponseEntity<Object> autoCheck(InputDataCheckCondition inputDataCheckCondition) {
        GcOrgTypeUtils.setContextEntityId((String)inputDataCheckCondition.getUnitDefine());
        return BusinessResponseEntity.ok((Object)this.inputDataCheckService.autoCheck(inputDataCheckCondition));
    }

    public BusinessResponseEntity<ProgressData<List<String>>> querySnStartProgress(String sn) {
        ProgressData progressData = this.progressService.queryProgressData(sn);
        return BusinessResponseEntity.ok((Object)progressData);
    }

    public BusinessResponseEntity<Object> manualCheck(InputDataCheckCondition inputDataCheckCondition) {
        return BusinessResponseEntity.ok((Object)this.inputDataCheckService.manualCheck(inputDataCheckCondition));
    }

    public BusinessResponseEntity<Object> manualCheckSave(InputDataCheckCondition inputDataCheckCondition) {
        try {
            this.inputDataCheckService.manualCheckSave(inputDataCheckCondition);
        }
        catch (Exception e) {
            this.logger.error("\u624b\u52a8\u5bf9\u8d26\u4fdd\u5b58\u5931\u8d25", e);
            return BusinessResponseEntity.error((String)(GcI18nUtil.getMessage((String)"gc.inputdata.check.failmsg") + e.getMessage()));
        }
        return BusinessResponseEntity.ok((Object)GcI18nUtil.getMessage((String)"gc.inputdata.check.successmsg"));
    }

    public BusinessResponseEntity<Object> cancelCheck(InputDataCheckCondition inputDataCheckCondition) {
        String resultMsg = GcI18nUtil.getMessage((String)"gc.inputdata.check.cancelsuccess");
        try {
            resultMsg = this.inputDataCheckService.cancelCheck(inputDataCheckCondition);
        }
        catch (Exception e) {
            this.logger.error("\u53d6\u6d88\u5bf9\u8d26\u5931\u8d25", e);
            return BusinessResponseEntity.error((String)(GcI18nUtil.getMessage((String)"gc.inputdata.check.failmsg") + e.getMessage()));
        }
        return BusinessResponseEntity.ok((Object)resultMsg);
    }

    public BusinessResponseEntity<Object> updateMemo(InputDataCheckUpdateMemoVO dataCheckUpdateMemoVO) {
        try {
            this.inputDataCheckService.updateMemo(dataCheckUpdateMemoVO);
        }
        catch (Exception e) {
            this.logger.error("\u4fee\u6539\u5dee\u5f02\u539f\u56e0\u5931\u8d25", e);
            return BusinessResponseEntity.error((String)(GcI18nUtil.getMessage((String)"gc.inputdata.check.failmsg") + e.getMessage()));
        }
        return BusinessResponseEntity.ok((Object)GcI18nUtil.getMessage((String)"gc.inputdata.check.successmsg"));
    }

    public BusinessResponseEntity<Object> sumAmt(String taskId, List<String> inputDataIds) {
        return BusinessResponseEntity.ok((Object)this.inputDataCheckService.sumAmt(taskId, inputDataIds));
    }
}

