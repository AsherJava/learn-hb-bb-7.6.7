/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.definition.impl.basic.init.table.nvwa.service.impl;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.intf.ITableGroupDefine;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionValueV;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector;
import com.jiuqi.gcreport.definition.impl.basic.init.table.nvwa.base.DeployTableProcessor;
import com.jiuqi.gcreport.definition.impl.basic.init.table.service.TableDefinitionService;
import com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.TableDefineConvertHelper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class TableDefinitionServiceImpl
implements TableDefinitionService {
    private static Logger logger = LoggerFactory.getLogger(TableDefinitionServiceImpl.class);

    @Override
    public void initTableDefine(DefinitionTableV tableDefine) throws Exception {
        if (tableDefine.getOwnerGroup() == null) {
            tableDefine.setOwnerGroup(new ITableGroupDefine(){}.initGroup(tableDefine));
        }
        DeployTableProcessor.newInstance(tableDefine).deploy();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void initTableDefineByTableName(String tableName) throws Exception {
        EntityTableCollector entityTableCollector = EntityTableCollector.getInstance();
        BaseEntity entity = entityTableCollector.getEntityByName(tableName);
        if (entity == null) {
            return;
        }
        DBTable dbTable = entityTableCollector.getDbTableByType(entity.getClass());
        DeployTableProcessor.newInstance(TableDefineConvertHelper.newInstance(entity, dbTable).convert()).deploy();
    }

    @Override
    public void initData(List<DefinitionValueV> initDatas) {
        initDatas.forEach(d -> {
            if (StringUtils.hasText(d.getTableName()) && d.getId() != null) {
                ((TableDefinitionService)SpringContextUtils.getBean(TableDefinitionService.class)).saveOrUpdateData((DefinitionValueV)d);
            }
        });
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveOrUpdateData(DefinitionValueV definitionValue) {
        try {
            EntNativeSqlDefaultDao<DefinitionValueV> dao = EntNativeSqlDefaultDao.newInstance(definitionValue.getTableName(), DefinitionValueV.class);
            dao.saveOrUpdate(definitionValue);
        }
        catch (Exception e) {
            logger.error("\u6267\u884c\u521d\u59cb\u6570\u636e\u53d1\u751f\u5f02\u5e38\uff1a" + definitionValue.toString(), e);
        }
    }
}

