/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.va.join.api.domain.JoinListener
 *  com.jiuqi.va.join.api.domain.ReplyStatus
 *  com.jiuqi.va.join.api.domain.ReplyTo
 */
package com.jiuqi.gcreport.aidocaudit.listener;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.aidocaudit.dto.MQScoreResultDTO;
import com.jiuqi.gcreport.aidocaudit.service.IAidocauditResultService;
import com.jiuqi.va.join.api.domain.JoinListener;
import com.jiuqi.va.join.api.domain.ReplyStatus;
import com.jiuqi.va.join.api.domain.ReplyTo;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AidocauditScoreResultListener
implements JoinListener {
    private final Logger logger = LoggerFactory.getLogger(AidocauditScoreResultListener.class);
    @Autowired
    private IAidocauditResultService resultService;

    public String getJoinName() {
        return "AIDOCAUDIT_SCORE_RESULT";
    }

    public ReplyTo onMessage(String message) {
        MQScoreResultDTO resultDTO;
        try {
            String decodedMessage = new String(Base64.getDecoder().decode(message.substring(1, message.length() - 1)), StandardCharsets.UTF_8);
            resultDTO = (MQScoreResultDTO)JsonUtils.readValue((String)decodedMessage, MQScoreResultDTO.class);
        }
        catch (Exception e) {
            this.logger.error("\u683c\u5f0f\u8f6c\u6362\u9519\u8bef", e);
            throw new BusinessRuntimeException("\u683c\u5f0f\u8f6c\u6362\u9519\u8bef");
        }
        Map<String, String> business = resultDTO.getBusiness();
        if ("failed".equals(resultDTO.getTaskStatus())) {
            this.resultService.handerErrorMessage(business);
            this.logger.error("\u8bc4\u5206\u5f02\u5e38,\u672a\u8fdb\u884c\u4fdd\u5b58,\u65f6\u671f{},\u5355\u4f4d:{},\u8bc4\u5206\u6a21\u677fID:{},\u5f02\u5e38\u539f\u56e0:{}", business.get("PERIOD"), business.get("\u5355\u4f4d"), resultDTO.getTempId(), resultDTO.getErrorMsg());
            throw new BusinessRuntimeException("\u8bc4\u5206\u5f02\u5e38,\u672a\u8fdb\u884c\u4fdd\u5b58");
        }
        try {
            this.resultService.handerMessage(resultDTO);
        }
        catch (Exception e) {
            this.resultService.handerErrorMessage(business);
            this.logger.error("\u6d88\u606f\u5904\u7406\u5f02\u5e38", e);
            return new ReplyTo(ReplyStatus.FAIL_REJECT, "reject");
        }
        return new ReplyTo(ReplyStatus.SUCESS, "sucess");
    }

    public ReplyTo onException() {
        return new ReplyTo(ReplyStatus.FAIL_REJECT);
    }

    public int getMaxConsumers() {
        return 1;
    }

    public int getBatchSize() {
        return 1;
    }

    public boolean isAutoStart() {
        return true;
    }
}

