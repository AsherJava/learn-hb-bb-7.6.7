/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.join.api.domain.JoinDeclare
 */
package com.jiuqi.va.workflow.mq;

import com.jiuqi.va.join.api.domain.JoinDeclare;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VaWorkflowQueue {
    @Bean
    public JoinDeclare vaWorkflowSubmitQueue() {
        return new JoinDeclare(){

            public String getTitle() {
                return "\u5de5\u4f5c\u6d41\u63d0\u4ea4\u961f\u5217";
            }

            public String getName() {
                return "VA_WORKFLOW_SUBMIT_QUEUE";
            }
        };
    }

    @Bean
    public JoinDeclare vaWorkflowCompleteQueue() {
        return new JoinDeclare(){

            public String getTitle() {
                return "\u5de5\u4f5c\u6d41\u5ba1\u6279\u961f\u5217";
            }

            public String getName() {
                return "VA_WORKFLOW_COMPLETE_QUEUE";
            }
        };
    }

    @Bean
    public JoinDeclare vaWorkflowRetractQueue() {
        return new JoinDeclare(){

            public String getTitle() {
                return "\u5de5\u4f5c\u6d41\u53d6\u56de\u961f\u5217";
            }

            public String getName() {
                return "VA_WORKFLOW_RETRACT_QUEUE";
            }
        };
    }

    @Bean
    public JoinDeclare vaWorkflowTriggerQueue() {
        return new JoinDeclare(){

            public String getTitle() {
                return "\u5de5\u4f5c\u6d41\u89e6\u53d1\u961f\u5217";
            }

            public String getName() {
                return "VA_WORKFLOW_TRIGGER_QUEUE";
            }
        };
    }

    @Bean
    public JoinDeclare vaWorkflowRestartQueue() {
        return new JoinDeclare(){

            public String getTitle() {
                return "\u5de5\u4f5c\u6d41\u89e6\u53d1\u961f\u5217";
            }

            public String getName() {
                return "VA_WORKFLOW_RESTART_QUEUE";
            }
        };
    }

    @Bean
    public JoinDeclare vaWorkflowRetractLockQueue() {
        return new JoinDeclare(){

            public String getName() {
                return "VA_WORKFLOW_RETRACT_LOCK_QUEUE";
            }

            public String getTitle() {
                return "\u5de5\u4f5c\u6d41\u53d6\u56de\u9501\u5b9a";
            }
        };
    }
}

