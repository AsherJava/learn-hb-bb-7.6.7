/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.join.api.domain.JoinDeclare
 */
package com.jiuqi.va.workflow.mq.delegate;

import com.jiuqi.va.join.api.domain.JoinDeclare;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VaWorkflowDelegateQueue {
    public static final String QUEUENAME = "VA_WORKFLOW_DELEGATE_SCHEDULE";

    @Bean
    public JoinDeclare sendAndRetakeDelegateQueue() {
        return new JoinDeclare(){

            public String getTitle() {
                return "\u53d1\u9001\u548c\u6536\u56de\u59d4\u6258\u5f85\u529e\u961f\u5217";
            }

            public String getName() {
                return VaWorkflowDelegateQueue.QUEUENAME;
            }

            public boolean isDurable() {
                return false;
            }
        };
    }
}

