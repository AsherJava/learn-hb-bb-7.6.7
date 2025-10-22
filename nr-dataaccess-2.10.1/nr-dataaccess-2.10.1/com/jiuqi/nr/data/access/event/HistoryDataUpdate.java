/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.data.access.event;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.data.access.common.TableConsts;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HistoryDataUpdate
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        IRuntimeDataSchemeService dataSchemeService = (IRuntimeDataSchemeService)SpringBeanUtils.getBean(IRuntimeDataSchemeService.class);
        DataModelService dataModelService = (DataModelService)SpringBeanUtils.getBean(DataModelService.class);
        DesignDataModelService designDataModelService = (DesignDataModelService)SpringBeanUtils.getBean(DesignDataModelService.class);
        DataModelDeployService dataModelDeployService = (DataModelDeployService)SpringBeanUtils.getBean(DataModelDeployService.class);
        List dataSchemes = dataSchemeService.getAllDataScheme();
        for (DataScheme dataScheme : dataSchemes) {
            logger.info("\u6570\u636e\u65b9\u6848\uff1a" + dataScheme.getTitle() + "\u9501\u5b9a\u8bb0\u5f55\u8868\u5347\u7ea7\u5f00\u59cb");
            try {
                String tableCode = TableConsts.getSysTableName("NR_STATE_%s_FORMLOCK", dataScheme.getBizCode());
                TableModelDefine tableDefine = dataModelService.getTableModelDefineByCode(tableCode);
                if (tableDefine != null) {
                    DesignColumnModelDefine columnModelDefine = designDataModelService.getColumnModelDefineByCode(tableDefine.getID(), "FL_USER");
                    columnModelDefine.setNullAble(false);
                    columnModelDefine.setDefaultValue("-");
                    designDataModelService.updateColumnModelDefine(columnModelDefine);
                    String keys = tableDefine.getKeys();
                    if (!keys.contains(columnModelDefine.getID())) {
                        DesignTableModelDefine designTableDefine = designDataModelService.getTableModelDefine(tableDefine.getID());
                        designTableDefine.setKeys(keys + ";" + columnModelDefine.getID());
                        designTableDefine.setBizKeys(tableDefine.getBizKeys() + ";" + columnModelDefine.getID());
                        designDataModelService.updateTableModelDefine(designTableDefine);
                        dataModelDeployService.deployTableUnCheck(designTableDefine.getID());
                    }
                }
                logger.info("\u6570\u636e\u65b9\u6848\uff1a{}\u9501\u5b9a\u8bb0\u5f55\u8868\u5347\u7ea7\u5b8c\u6210\u3002", (Object)dataScheme.getTitle());
            }
            catch (Exception e) {
                logger.error("\u6570\u636e\u65b9\u6848\uff1a{}\u9501\u5b9a\u8bb0\u5f55\u8868\u5347\u7ea7\u5931\u8d25\uff01", (Object)dataScheme.getTitle(), (Object)e);
            }
        }
    }
}

