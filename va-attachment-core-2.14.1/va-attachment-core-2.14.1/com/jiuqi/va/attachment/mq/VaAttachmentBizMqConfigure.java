/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.schedule.ScheduleTaskFeedback
 *  com.jiuqi.va.join.api.common.JoinTemplate
 *  com.jiuqi.va.join.api.domain.JoinDeclare
 *  com.jiuqi.va.join.api.domain.ReplyStatus
 *  com.jiuqi.va.join.api.domain.ReplyTo
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.attachment.mq;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.schedule.ScheduleTaskFeedback;
import com.jiuqi.va.join.api.common.JoinTemplate;
import com.jiuqi.va.join.api.domain.JoinDeclare;
import com.jiuqi.va.join.api.domain.ReplyStatus;
import com.jiuqi.va.join.api.domain.ReplyTo;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class VaAttachmentBizMqConfigure {
    @Bean
    public JoinDeclare vaAttachmentBizRecycleBinClearDeclare() {
        return new JoinDeclare(){

            public String getTitle() {
                return "\u9644\u4ef6\u56de\u6536\u7ad9\u6570\u636e\u6e05\u9664";
            }

            public String getName() {
                return "VA_ATTACHMENT_RECYCLE_BIN_CLEAR_MQ";
            }

            public boolean isDurable() {
                return false;
            }
        };
    }

    @Bean
    public JoinDeclare vaAttachmentBizCleanUseLessDeclare() {
        return new JoinDeclare(){

            public String getTitle() {
                return "\u9644\u4ef6\u5783\u573e\u6570\u636e\u6e05\u9664";
            }

            public String getName() {
                return "VA_ATTACHMENT_CLEAN_USE_LESS_MQ";
            }

            public boolean isDurable() {
                return false;
            }
        };
    }

    @Bean
    public JoinDeclare vaAttachmentBizConfirmDataExecuteDeclare() {
        return new JoinDeclare(){

            public String getTitle() {
                return "\u9644\u4ef6\u786e\u8ba4\u8868\u6570\u636e\u786e\u8ba4";
            }

            public String getName() {
                return "VA_ATTACHMENT_CONFIRM_DATA_EXECUTE_MQ";
            }

            public boolean isDurable() {
                return false;
            }
        };
    }

    public static ReplyTo getReplyTo(TenantDO tenantDO, String logid, JoinTemplate joinTemplate) {
        ScheduleTaskFeedback feedback = new ScheduleTaskFeedback();
        feedback.setFdstatus(Integer.valueOf(1));
        feedback.setLogid(UUID.fromString(logid));
        feedback.setTenantName(tenantDO.getTenantName());
        feedback.setFdmessage("\u6267\u884c\u6210\u529f");
        joinTemplate.send("MQ_SCHEDULE_TASK_FEEDBACK", JSONUtil.toJSONString((Object)feedback));
        return new ReplyTo(ReplyStatus.SUCESS, JSONUtil.toJSONString((Object)R.ok((String)"\u6267\u884c\u6210\u529f")));
    }
}

