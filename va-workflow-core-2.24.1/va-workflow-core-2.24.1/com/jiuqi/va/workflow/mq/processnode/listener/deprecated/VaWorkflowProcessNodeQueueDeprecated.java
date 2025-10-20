/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.join.api.domain.JoinDeclare
 */
package com.jiuqi.va.workflow.mq.processnode.listener.deprecated;

import com.jiuqi.va.join.api.domain.JoinDeclare;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Deprecated
public class VaWorkflowProcessNodeQueueDeprecated {
    @Bean
    public JoinDeclare vaWorkflowProcessNodeAddQueueDeclare() {
        return new JoinDeclare(){

            public String getTitle() {
                return "\u6d41\u7a0b\u8f68\u8ff9\u6dfb\u52a0\u961f\u5217";
            }

            public String getName() {
                return "va.workflow.node.add.queue";
            }
        };
    }

    @Bean
    public JoinDeclare vaWorkflowProcessNodeAddBatchQueueDeclare() {
        return new JoinDeclare(){

            public String getTitle() {
                return "\u6d41\u7a0b\u8f68\u8ff9\u6279\u91cf\u6dfb\u52a0\u961f\u5217";
            }

            public String getName() {
                return "va.workflow.node.add.batch.queue";
            }
        };
    }

    @Bean
    public JoinDeclare vaWorkflowProcessNodeUpdateQueueDeclare() {
        return new JoinDeclare(){

            public String getTitle() {
                return "\u6d41\u7a0b\u8f68\u8ff9\u66f4\u65b0\u961f\u5217";
            }

            public String getName() {
                return "va.workflow.node.update.queue";
            }
        };
    }

    @Bean
    public JoinDeclare vaWorkflowProcessNodeChangeQueueDeclare() {
        return new JoinDeclare(){

            public String getTitle() {
                return "\u6d41\u7a0b\u8f68\u8ff9\u53d8\u66f4\u961f\u5217";
            }

            public String getName() {
                return "va.workflow.ProcessNode.change.queue";
            }
        };
    }
}

