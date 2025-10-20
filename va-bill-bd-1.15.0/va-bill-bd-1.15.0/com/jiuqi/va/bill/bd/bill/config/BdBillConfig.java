/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.join.api.domain.JoinDeclare
 *  tk.mybatis.spring.annotation.MapperScan
 */
package com.jiuqi.va.bill.bd.bill.config;

import com.jiuqi.va.join.api.domain.JoinDeclare;
import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.va.bill.bd.bill"})
@MapperScan(basePackages={"com.jiuqi.va.bill.bd.bill.dao"})
public class BdBillConfig {
    @Bean
    public JoinDeclare billBdCreateBillQueue() {
        return new JoinDeclare(){

            public String getTitle() {
                return "\u751f\u6210\u57fa\u7840\u6570\u636e\u961f\u5217";
            }

            public String getName() {
                return "VA_BILL_BD_CREATEBILL";
            }
        };
    }

    @Bean
    public JoinDeclare billBdChangeBillQueue() {
        return new JoinDeclare(){

            public String getTitle() {
                return "\u53d8\u66f4\u57fa\u7840\u6570\u636e\u961f\u5217";
            }

            public String getName() {
                return "VA_BILL_BD_CHANGEBILL";
            }
        };
    }

    @Bean
    public JoinDeclare billBdDeleteBillQueue() {
        return new JoinDeclare(){

            public String getTitle() {
                return "\u5220\u9664\u57fa\u7840\u6570\u636e\u961f\u5217";
            }

            public String getName() {
                return "VA_BILL_BD_DELETEEBILL";
            }
        };
    }

    @Bean
    public JoinDeclare billBdSyncRefDataBillQueue() {
        return new JoinDeclare(){

            public String getTitle() {
                return "\u540c\u6b65\u57fa\u7840\u6570\u636e\u6d88\u606f\u961f\u5217";
            }

            public String getName() {
                return "VA_BILL_BD_SYNCREFDATA";
            }
        };
    }

    @Bean(name={"billBdMessageSource"})
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setUseCodeAsDefaultMessage(true);
        messageBundle.setBasenames("classpath:messages/messages", "classpath:messages/VaBillBd");
        messageBundle.setDefaultEncoding("UTF-8");
        messageBundle.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return messageBundle;
    }
}

