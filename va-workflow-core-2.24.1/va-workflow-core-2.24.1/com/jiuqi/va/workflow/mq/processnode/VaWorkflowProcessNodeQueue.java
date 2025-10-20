/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.join.api.domain.JoinDeclare
 */
package com.jiuqi.va.workflow.mq.processnode;

import com.jiuqi.va.join.api.domain.JoinDeclare;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VaWorkflowProcessNodeQueue {
    @Bean
    public JoinDeclare vaWorkflowProcessNodeAddQueue() {
        return new JoinDeclare(){

            public String getTitle() {
                return "\u6d41\u7a0b\u8f68\u8ff9\u6dfb\u52a0\u961f\u5217";
            }

            public String getName() {
                return "VA_WORKFLOW_NODE_ADD_QUEUE";
            }
        };
    }

    @Bean
    public JoinDeclare vaWorkflowProcessNodeAddBatchQueue() {
        return new JoinDeclare(){

            public String getTitle() {
                return "\u6d41\u7a0b\u8f68\u8ff9\u6279\u91cf\u6dfb\u52a0\u961f\u5217";
            }

            public String getName() {
                return "VA_WORKFLOW_NODE_ADD_BATCH_QUEUE";
            }
        };
    }

    @Bean
    public JoinDeclare vaWorkflowProcessNodeUpdateQueue() {
        return new JoinDeclare(){

            public String getTitle() {
                return "\u6d41\u7a0b\u8f68\u8ff9\u66f4\u65b0\u961f\u5217";
            }

            public String getName() {
                return "VA_WORKFLOW_NODE_UPDATE_QUEUE";
            }
        };
    }

    @Bean
    public JoinDeclare vaWorkflowProcessNodeChangeQueue() {
        return new JoinDeclare(){

            public String getTitle() {
                return "\u6d41\u7a0b\u8f68\u8ff9\u53d8\u66f4\u961f\u5217";
            }

            public String getName() {
                return "VA_WORKFLOW_NODE_CHANGE_QUEUE";
            }
        };
    }
}

