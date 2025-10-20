/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.gcreport.definition.impl.basic.init;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.internal.EntDaoCacheManager;
import com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.DefinitionAutoCollectionService;
import com.jiuqi.gcreport.definition.impl.basic.init.table.va.service.EntVaModelDataInitService;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModuleSync
implements ModuleInitiator {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void init(ServletContext context) {
    }

    public void initWhenStarted(ServletContext context) {
        try {
            EntVaModelDataInitService vaBaseDataInitService = (EntVaModelDataInitService)SpringContextUtils.getBean(EntVaModelDataInitService.class);
            assert (vaBaseDataInitService != null);
            vaBaseDataInitService.init(false, null);
        }
        catch (Exception e) {
            this.logger.error("\u521d\u59cb\u5316\u57fa\u7840\u6570\u636e\u8868\u5931\u8d25", e);
        }
        DefinitionAutoCollectionService definitionAutoCollectionService = (DefinitionAutoCollectionService)SpringContextUtils.getBean(DefinitionAutoCollectionService.class);
        assert (definitionAutoCollectionService != null);
        definitionAutoCollectionService.execute();
        EntDaoCacheManager manager = (EntDaoCacheManager)SpringBeanUtils.getBean(EntDaoCacheManager.class);
        manager.initTable();
    }
}

