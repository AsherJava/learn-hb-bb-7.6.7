/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.join.api.domain.JoinDeclare
 */
package com.jiuqi.va.workflow.mq.listener;

import com.jiuqi.va.join.api.domain.JoinDeclare;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Deprecated
public class VaWorkflowQueueDeprecated {
    @Bean
    public JoinDeclare vaWorkflowSubmitQueueDeprecated() {
        return new JoinDeclare(){

            public String getTitle() {
                return "\u5de5\u4f5c\u6d41\u63d0\u4ea4\u961f\u5217";
            }

            public String getName() {
                return "va.workflow.submit.queue";
            }
        };
    }

    @Bean
    public JoinDeclare vaWorkflowCompleteQueueDeprecated() {
        return new JoinDeclare(){

            public String getTitle() {
                return "\u5de5\u4f5c\u6d41\u5ba1\u6279\u961f\u5217";
            }

            public String getName() {
                return "va.workflow.complete.queue";
            }
        };
    }

    @Bean
    public JoinDeclare vaWorkflowRetractQueueDeprecated() {
        return new JoinDeclare(){

            public String getTitle() {
                return "\u5de5\u4f5c\u6d41\u53d6\u56de\u961f\u5217";
            }

            public String getName() {
                return "va.workflow.retract.queue";
            }
        };
    }

    @Bean
    public JoinDeclare vaWorkflowTriggerQueueDeprecated() {
        return new JoinDeclare(){

            public String getTitle() {
                return "\u5de5\u4f5c\u6d41\u89e6\u53d1\u961f\u5217";
            }

            public String getName() {
                return "va.workflow.trigger.queue";
            }
        };
    }

    @Bean
    public JoinDeclare vaWorkflowRestartQueueDeprecated() {
        return new JoinDeclare(){

            public String getTitle() {
                return "\u5de5\u4f5c\u6d41\u89e6\u53d1\u961f\u5217";
            }

            public String getName() {
                return "va.workflow.restart.queue";
            }
        };
    }
}

