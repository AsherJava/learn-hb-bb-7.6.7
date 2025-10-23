/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping2.config;

import com.jiuqi.nr.mapping2.dto.JIOContent;
import com.jiuqi.nr.mapping2.dto.JIOUploadParam;
import com.jiuqi.nr.mapping2.service.JIOConfigService;
import com.jiuqi.nr.mapping2.service.JIOProvider;
import com.jiuqi.nr.mapping2.service.ZbIniProvider;
import com.jiuqi.nr.mapping2.web.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value={"com.jiuqi.nr.mapping2"})
public class MappingConfig {
    @Autowired
    JIOConfigService jioService;

    @Bean
    @ConditionalOnMissingBean(value={JIOProvider.class})
    public JIOProvider defaultJIOProvider() {
        return new JIOProvider(){

            @Override
            public Result execute(String fileOssKey, byte[] JIOFile, String msKey, JIOUploadParam jioUpload) {
                MappingConfig.this.jioService.saveJIO(msKey, fileOssKey, new byte[10], new JIOContent());
                return Result.success(null, "success");
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(value={ZbIniProvider.class})
    public ZbIniProvider defaultZbIniProvider() {
        return new ZbIniProvider(){

            @Override
            public Result uploadZbByINI(String msKey, String formCode, byte[] file) {
                return Result.success(null, "\u5bfc\u5165\u6210\u529f\uff01");
            }
        };
    }
}

