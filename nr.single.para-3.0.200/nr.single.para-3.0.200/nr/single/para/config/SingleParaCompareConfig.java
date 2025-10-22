/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.config;

import nr.single.para.asyn.SingleCompareController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(value={"nr.single.para.compare.internal.defintion.dao", "nr.single.para.compare.internal.defintion.dao2", "nr.single.para.compare.internal.defintion.service", "nr.single.para.compare.internal.service", "nr.single.para.compare.internal.system", "nr.single.para.file"})
@Configuration
public class SingleParaCompareConfig {
    @Bean
    public SingleCompareController getSingleCompareController() {
        return new SingleCompareController();
    }
}

