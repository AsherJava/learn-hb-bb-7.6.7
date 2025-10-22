/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nvwa.definition.common.IndexModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
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
import com.jiuqi.nvwa.definition.common.IndexModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LockHistoryDataUpdate
implements CustomClassExecutor {
    public void execute(DataSource dataSource) {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        IRuntimeDataSchemeService dataSchemeService = (IRuntimeDataSchemeService)SpringBeanUtils.getBean(IRuntimeDataSchemeService.class);
        DesignDataModelService designDataModelService = (DesignDataModelService)SpringBeanUtils.getBean(DesignDataModelService.class);
        DataModelService dataModelService = (DataModelService)SpringBeanUtils.getBean(DataModelService.class);
        DataModelDeployService dataModelDeployService = (DataModelDeployService)SpringBeanUtils.getBean(DataModelDeployService.class);
        List dataSchemes = dataSchemeService.getAllDataScheme();
        for (DataScheme dataScheme : dataSchemes) {
            logger.info("\u6570\u636e\u65b9\u6848\uff1a{}\u9501\u5b9a\u5386\u53f2\u8868\u8868\u5347\u7ea7\u5f00\u59cb", (Object)dataScheme.getTitle());
            try {
                String tableCode = TableConsts.getSysTableName("NR_STATE_%s_FORMLOCK_HIS", dataScheme.getBizCode());
                DesignTableModelDefine tableDefine = designDataModelService.getTableModelDefineByCode(tableCode);
                if (tableDefine != null) {
                    List columnModelDefinesByTable = designDataModelService.getColumnModelDefinesByTable(tableDefine.getID());
                    String dwKey = null;
                    String periodKey = null;
                    String formFieldKey = null;
                    String formSchemeFieldKey = null;
                    String flFieldKey = null;
                    for (ColumnModelDefine columnInfo : columnModelDefinesByTable) {
                        if ("MDCODE".equals(columnInfo.getCode())) {
                            dwKey = columnInfo.getID();
                            continue;
                        }
                        if ("PERIOD".equals(columnInfo.getCode())) {
                            periodKey = columnInfo.getID();
                            continue;
                        }
                        if ("FLH_FORM".equals(columnInfo.getCode())) {
                            formFieldKey = columnInfo.getID();
                            continue;
                        }
                        if ("FLH_FORMSCHEME".equals(columnInfo.getCode())) {
                            formSchemeFieldKey = columnInfo.getID();
                            continue;
                        }
                        if (!"FLH_ID".equals(columnInfo.getCode())) continue;
                        flFieldKey = columnInfo.getID();
                    }
                    TableModelDefine tableModelDefine = dataModelService.getTableModelDefineByCode(tableCode);
                    String keys = tableModelDefine.getKeys();
                    if (keys.equals(flFieldKey)) continue;
                    tableDefine.setKeys(flFieldKey);
                    tableDefine.setBizKeys(flFieldKey);
                    String[] indexColumns = new String[]{dwKey, formFieldKey, periodKey, formSchemeFieldKey};
                    designDataModelService.deleteIndexsByTable(tableDefine.getID());
                    designDataModelService.updateTableModelDefine(tableDefine);
                    designDataModelService.addIndexToTable(tableDefine.getID(), indexColumns, "IDX_FORMLOCK_HIS_" + dataScheme.getBizCode(), IndexModelType.NORMAL);
                    dataModelDeployService.deployTableUnCheck(tableDefine.getID());
                }
                logger.info("\u6570\u636e\u65b9\u6848\uff1a{}\u9501\u5b9a\u5386\u53f2\u8868\u5347\u7ea7\u5b8c\u6210\u3002", (Object)dataScheme.getTitle());
            }
            catch (Exception e) {
                logger.error("\u6570\u636e\u65b9\u6848\uff1a{}\u9501\u5b9a\u5386\u53f2\u8868\u5347\u7ea7\u5931\u8d25\uff01", (Object)dataScheme.getTitle(), (Object)e);
            }
        }
    }
}

