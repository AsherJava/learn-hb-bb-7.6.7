/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.internal.deploy.impl.DataSchemeDeployServiceImpl
 *  com.jiuqi.nr.datascheme.internal.service.impl.cache2.DataSchemeCacheService
 *  com.jiuqi.nr.query.datascheme.extend.DataTableFactoryManager
 *  com.jiuqi.nr.query.datascheme.extend.IDataTableFactory
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package nr.starter.datascheme.init;

import com.jiuqi.nr.datascheme.internal.deploy.impl.DataSchemeDeployServiceImpl;
import com.jiuqi.nr.datascheme.internal.service.impl.cache2.DataSchemeCacheService;
import com.jiuqi.nr.query.datascheme.extend.DataTableFactoryManager;
import com.jiuqi.nr.query.datascheme.extend.IDataTableFactory;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import java.util.List;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSchemeServiceInit
implements ModuleInitiator {
    @Autowired
    private DataSchemeCacheService dataSchemeCacheService;
    @Autowired
    private DataSchemeDeployServiceImpl dataSchemeDeployService;
    @Autowired(required=false)
    private List<IDataTableFactory> factorys;

    public void init(ServletContext context) throws Exception {
    }

    public void initWhenStarted(ServletContext context) throws Exception {
        if (this.factorys != null) {
            for (IDataTableFactory factory : this.factorys) {
                DataTableFactoryManager.getInstance().registerFactory(factory);
            }
        }
        this.dataSchemeDeployService.fixDeployStatus();
        this.dataSchemeCacheService.init();
    }
}

