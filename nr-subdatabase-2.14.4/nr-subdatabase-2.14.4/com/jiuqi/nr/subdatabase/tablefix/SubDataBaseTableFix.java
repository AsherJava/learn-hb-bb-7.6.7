/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.datascheme.fix.core.DeployFixExtendParam
 *  com.jiuqi.nr.datascheme.fix.service.IDataSchemeDeployFixExtendService
 *  com.jiuqi.nr.datascheme.fix.utils.DeployFixUtils
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.interval.dao.ColumnModelDao
 *  com.jiuqi.nvwa.definition.interval.dao.DataModelDao
 *  com.jiuqi.nvwa.definition.interval.dao.IndexModelDao
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.subdatabase.tablefix;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.datascheme.fix.core.DeployFixExtendParam;
import com.jiuqi.nr.datascheme.fix.service.IDataSchemeDeployFixExtendService;
import com.jiuqi.nr.datascheme.fix.utils.DeployFixUtils;
import com.jiuqi.nr.subdatabase.facade.SubDataBase;
import com.jiuqi.nr.subdatabase.service.SubDataBaseService;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.interval.dao.ColumnModelDao;
import com.jiuqi.nvwa.definition.interval.dao.DataModelDao;
import com.jiuqi.nvwa.definition.interval.dao.IndexModelDao;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubDataBaseTableFix
implements IDataSchemeDeployFixExtendService {
    @Autowired
    private SubDataBaseService subDataBaseService;
    @Autowired
    private DeployFixUtils deployFixUtils;
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private DataModelDao dataModelDao;
    @Autowired
    private ColumnModelDao columnModelDao;
    @Autowired
    private IndexModelDao indexModelDao;

    public void clearOldModel(DeployFixExtendParam param) {
        List<SubDataBase> subDataBases = this.subDataBaseService.getSubDataBaseObjByDataScheme(param.getDataSchemeKey());
        HashMap<String, String> map = new HashMap<String, String>();
        for (SubDataBase subDataBase : subDataBases) {
            Set tableModelKeys = param.getTableModelKey();
            for (String tableModelKey : tableModelKeys) {
                DesignTableModelDefine tableModelDefine = this.designDataModelService.getTableModelDefine(tableModelKey + subDataBase.getCode());
                String subDataBaseName = tableModelDefine.getName();
                if (param.isTableNeedBackUp()) {
                    String tempName = this.deployFixUtils.renameLogicTable(subDataBaseName);
                    map.put(subDataBaseName, tempName);
                } else {
                    this.deployFixUtils.deleteLogicTable(subDataBaseName);
                }
                this.deleteNWParam(tableModelKey + subDataBase.getCode());
            }
        }
        param.setFixContext(map);
    }

    public void reDeploy(DeployFixExtendParam param) {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        List<SubDataBase> subDataBases = this.subDataBaseService.getSubDataBaseObjByDataScheme(param.getDataSchemeKey());
        Map fixContext = param.getFixContext();
        for (SubDataBase subDataBase : subDataBases) {
            Set tableModelKeys = param.getTableModelKey();
            try {
                this.subDataBaseService.createAndDeploy(tableModelKeys, subDataBase.getCode(), param.getDataSchemeKey());
                for (String tableModelKey : tableModelKeys) {
                    String tempName;
                    DesignTableModelDefine tableModelDefine = this.designDataModelService.getTableModelDefine(tableModelKey);
                    String subDataBaseName = subDataBase.getCode() + tableModelDefine.getName();
                    if (!param.isTableNeedBackUp() || !StringUtils.isNotEmpty((String)(tempName = (String)fixContext.get(subDataBaseName)))) continue;
                    this.deployFixUtils.transferData(subDataBaseName, tempName);
                }
            }
            catch (Exception e) {
                logger.error("\u5206\u5e93" + subDataBase.getTitle() + "\u4fee\u590d\u8868\u5931\u8d25\uff01", e);
            }
        }
    }

    private void deleteNWParam(String tableModelKey) {
        this.designDataModelService.delteTableModel(tableModelKey);
        this.dataModelDao.deleteRunTableDefines(new String[]{tableModelKey});
        this.columnModelDao.deleteColumnModelDefineByTable(tableModelKey);
        this.indexModelDao.removeIndexsByTable(tableModelKey);
    }
}

