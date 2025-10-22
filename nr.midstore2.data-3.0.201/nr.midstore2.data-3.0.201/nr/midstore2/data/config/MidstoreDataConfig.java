/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore2.data.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(value={"nr.midstore2.data.extension", "nr.midstore2.data.publish.internal", "nr.midstore2.data.work.internal", "nr.midstore2.data.work.internal.fix", "nr.midstore2.data.work.internal.floating", "nr.midstore2.data.param.internal", "nr.midstore2.data.util.internal", "nr.midstore2.data.util.internal.auth", "nr.midstore2.data.service.internal", "nr.midstore2.data.midstoreresult.service.impl", "nr.midstore2.data.midstoreresult.dao"})
@Configuration
public class MidstoreDataConfig {
}

