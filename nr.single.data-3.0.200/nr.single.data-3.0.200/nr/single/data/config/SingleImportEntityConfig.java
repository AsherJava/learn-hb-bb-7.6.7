/*
 * Decompiled with CFR 0.152.
 */
package nr.single.data.config;

import java.util.List;
import nr.single.data.datain.service.ITaskFileImportEntityService;
import nr.single.data.datain.service.TaskFileImportEntityServiceManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages={"nr.single.data.datain.internal.service.org", "nr.single.data.datain.internal.service.basedata", "nr.single.data.datain.internal.service.fmdm"})
@Configuration
public class SingleImportEntityConfig {
    public SingleImportEntityConfig(List<ITaskFileImportEntityService> nwaDSProviders) {
        for (ITaskFileImportEntityService nwaDSProvider : nwaDSProviders) {
            TaskFileImportEntityServiceManager.getInstance().regService(nwaDSProvider);
        }
    }
}

