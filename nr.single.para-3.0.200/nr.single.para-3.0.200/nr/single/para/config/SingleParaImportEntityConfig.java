/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.config;

import java.util.List;
import nr.single.para.parain.service.EntityDefineImportServiceManager;
import nr.single.para.parain.service.IEntityDefineImportService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages={"nr.single.para.parain.internal.service3.entity"})
@Configuration
public class SingleParaImportEntityConfig {
    public SingleParaImportEntityConfig(List<IEntityDefineImportService> nwaDSProviders) {
        for (IEntityDefineImportService nwaDSProvider : nwaDSProviders) {
            EntityDefineImportServiceManager.getInstance().regService(nwaDSProvider);
        }
    }
}

