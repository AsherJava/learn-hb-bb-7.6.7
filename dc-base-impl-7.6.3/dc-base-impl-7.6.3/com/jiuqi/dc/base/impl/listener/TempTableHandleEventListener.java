/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.event.TempTableHandleEvent
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.TableDefineConvertHelper
 *  com.jiuqi.gcreport.definition.impl.enums.TemporaryTableTypeEnum
 */
package com.jiuqi.dc.base.impl.listener;

import com.jiuqi.dc.base.common.event.TempTableHandleEvent;
import com.jiuqi.dc.base.impl.definition.DcTempTableDefinitionService;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector;
import com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.TableDefineConvertHelper;
import com.jiuqi.gcreport.definition.impl.enums.TemporaryTableTypeEnum;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class TempTableHandleEventListener
implements ApplicationListener<TempTableHandleEvent> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DcTempTableDefinitionService tempTableDefinitionService;

    @Override
    public void onApplicationEvent(TempTableHandleEvent event) {
        try {
            this.execute(event);
        }
        catch (Exception e) {
            this.logger.error("\u4e34\u65f6\u8868\u521d\u59cb\u5316\u5904\u7406\u9519\u8bef", e);
        }
    }

    private void execute(TempTableHandleEvent event) {
        EntityTableCollector entityTableCollector = EntityTableCollector.getInstance();
        List entityList = entityTableCollector.getEntitys().stream().filter(e -> e.getClass().getName().startsWith("com.jiuqi.dc") || e.getClass().getName().startsWith("com.jiuqi.gcreport.financialcheckImpl")).filter(entity -> !Objects.equals(entityTableCollector.getDbTableByType(entity.getClass()).tempTableType(), TemporaryTableTypeEnum.PHYSICAL)).collect(Collectors.toList());
        for (BaseEntity entity2 : entityList) {
            try {
                TableDefineConvertHelper tableDefineConvertHelper = TableDefineConvertHelper.newInstance((BaseEntity)entity2, (DBTable)entityTableCollector.getDbTableByType(entity2.getClass()));
                DefinitionTableV tableDefine = tableDefineConvertHelper.convert();
                this.tempTableDefinitionService.initTableDefine(tableDefine);
            }
            catch (Exception e2) {
                this.logger.error(entity2.getTableName() + "\u4e34\u65f6\u8868\u521d\u59cb\u5316\u5904\u7406\u5931\u8d25\uff1a", e2);
            }
        }
    }
}

