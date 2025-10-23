/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping.config;

import com.jiuqi.nr.mapping.dto.JIOUploadParam;
import com.jiuqi.nr.mapping.service.JIOProvider;
import com.jiuqi.nr.mapping.web.vo.Result;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value={"com.jiuqi.nr.mapping"})
public class MappingSchemeConfig {
    @Bean
    @ConditionalOnMissingBean(value={JIOProvider.class})
    public JIOProvider defaultJIOProvider() {
        return new JIOProvider(){

            @Override
            public Result execute(byte[] JIOFile, String msKey, JIOUploadParam param) {
                return Result.success(null, "\u4e0a\u4f20\u6210\u529f");
            }
        };
    }
}

