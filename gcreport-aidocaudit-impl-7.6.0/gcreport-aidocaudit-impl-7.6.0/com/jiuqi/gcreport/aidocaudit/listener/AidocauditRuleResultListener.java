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
import com.jiuqi.gcreport.aidocaudit.dto.MQRuleResultDTO;
import com.jiuqi.gcreport.aidocaudit.service.IAidocauditRuleService;
import com.jiuqi.va.join.api.domain.JoinListener;
import com.jiuqi.va.join.api.domain.ReplyStatus;
import com.jiuqi.va.join.api.domain.ReplyTo;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AidocauditRuleResultListener
implements JoinListener {
    private final Logger logger = LoggerFactory.getLogger(AidocauditRuleResultListener.class);
    @Autowired
    private IAidocauditRuleService ruleService;

    public String getJoinName() {
        return "AIDOCAUDIT_RULE_RESULT";
    }

    public ReplyTo onMessage(String message) {
        MQRuleResultDTO resultDTO;
        try {
            String decodedMessage = new String(Base64.getDecoder().decode(message.substring(1, message.length() - 1)), StandardCharsets.UTF_8);
            resultDTO = (MQRuleResultDTO)JsonUtils.readValue((String)decodedMessage, MQRuleResultDTO.class);
        }
        catch (Exception e) {
            this.logger.error("\u683c\u5f0f\u8f6c\u6362\u9519\u8bef", e);
            throw new BusinessRuntimeException("\u683c\u5f0f\u8f6c\u6362\u9519\u8bef");
        }
        if ("failed".equals(resultDTO.getTaskStatus())) {
            this.ruleService.handerErrorMessage(resultDTO);
            this.logger.error("\u65b0\u589e\u8bc4\u5206\u6a21\u677f\u5f02\u5e38,\u8bc4\u5206\u6a21\u677f:{}", (Object)resultDTO.getTempName());
            throw new BusinessRuntimeException("\u65b0\u589e\u8bc4\u5206\u6a21\u677f\u5f02\u5e38\uff0c" + resultDTO.getErrorMsg());
        }
        try {
            this.ruleService.handerMessage(resultDTO);
        }
        catch (Exception e) {
            this.logger.error("\u6d88\u606f\u5904\u7406\u5f02\u5e38", e);
            this.ruleService.handerErrorMessage(resultDTO);
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

