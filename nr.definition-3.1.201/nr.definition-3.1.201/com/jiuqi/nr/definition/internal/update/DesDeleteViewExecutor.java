/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.update;

import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao;
import com.jiuqi.nr.definition.internal.dao.DesignDataLinkDefineDao;
import com.jiuqi.nr.definition.internal.dao.DesignDataRegionDefineDao;
import com.jiuqi.nr.definition.internal.impl.DesignBigDataTable;
import com.jiuqi.nr.definition.internal.impl.DesignDataLinkDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignDataRegionDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.definition.internal.service.DesignTaskDefineService;
import com.jiuqi.nr.definition.internal.update.DesTaskUpdate;
import com.jiuqi.nr.definition.internal.update.EntityViewCache;
import com.jiuqi.nr.definition.internal.update.dao.EntityViewDefineUp;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DesDeleteViewExecutor {
    private final Logger logger = LoggerFactory.getLogger(DesDeleteViewExecutor.class);
    @Autowired
    private DesignTaskDefineService designTaskDefineService;
    @Autowired
    private DesTaskUpdate desTaskUpdate;
    @Autowired
    private DesignDataRegionDefineDao designDataRegionDefineDao;
    @Autowired
    private DesignDataLinkDefineDao linkDefineDao;
    @Autowired
    private DesignBigDataTableDao bigDataDao;

    public void update(EntityViewCache cache) {
        try {
            List<DesignTaskDefine> designTaskDefines = this.designTaskDefineService.queryAllTaskDefine();
            this.logger.info("\u5171\u67e5\u51fa {} \u4e2a\u4efb\u52a1\u5b9a\u4e49\uff0c\u5f00\u59cb\u5347\u7ea7", (Object)designTaskDefines.size());
            int step = 0;
            for (DesignTaskDefine taskDefine : designTaskDefines) {
                ++step;
                try {
                    byte[] data;
                    DesignBigDataTable designBigDataTable = this.bigDataDao.queryigDataDefine(taskDefine.getKey(), "FLOWSETTING");
                    if (designBigDataTable != null && (data = designBigDataTable.getData()) != null) {
                        taskDefine.setFlowsSetting(DesignTaskFlowsDefine.bytesToTaskFlowsData(data));
                    }
                    this.desTaskUpdate.updateTask(taskDefine, cache);
                    this.logger.info("\u7b2c{}\u4e2a\u4efb\u52a1\u5b9a\u4e49\u6b63\u5728\u5347\u7ea7\uff0c\u8bf7\u8010\u5fc3\u7b49\u5f85", (Object)step);
                }
                catch (Exception e) {
                    this.logger.info("\u7b2c{}\u4e2a\u4efb\u52a1\u5b9a\u4e49\u5347\u7ea7\u5931\u8d25", (Object)step);
                    this.logger.error(taskDefine.getKey(), e);
                }
            }
            this.logger.info("\u4efb\u52a1\u5b9a\u4e49\uff0c\u5347\u7ea7\u5b8c\u6bd5");
        }
        catch (Exception e) {
            this.logger.error("\u67e5\u4efb\u52a1\u5b9a\u4e49\u5931\u8d25\uff0c\u8df3\u8fc7\u5347\u7ea7", e);
        }
        this.regionsUpdate(cache);
        this.linkUpdate(cache);
    }

    private void linkUpdate(EntityViewCache cache) {
        List links;
        try {
            links = this.linkDefineDao.list(" dl_view_key is not null", null, DesignDataLinkDefineImpl.class);
        }
        catch (Exception e) {
            this.logger.error("\u94fe\u63a5\u5b9a\u4e49\u67e5\u8be2\u5931\u8d25\u8df3\u8fc7\u5347\u7ea7", e);
            return;
        }
        this.logger.info("\u5171\u67e5\u51fa {} \u4e2a\u94fe\u63a5\u5b9a\u4e49\uff0c\u5f00\u59cb\u5347\u7ea7", (Object)links.size());
        int step = 0;
        int fail = 0;
        for (DesignDataLinkDefineImpl link : links) {
            try {
                ++step;
                String selectViewKey = "";
                EntityViewDefineUp value = cache.getValue(selectViewKey);
                if (value != null) {
                    link.setFilterExpression(value.getRowFilterExpression());
                    link.setIgnorePermissions(!value.isFilterRowByAuthority());
                    this.linkDefineDao.update(link);
                }
                this.logger.info("\u7b2c{}\u4e2a\u94fe\u63a5\u5b9a\u4e49\u6b63\u5728\u5347\u7ea7\uff0c\u8bf7\u8010\u5fc3\u7b49\u5f85", (Object)step);
            }
            catch (Exception e) {
                ++fail;
                this.logger.info("\u7b2c{}\u4e2a\u94fe\u63a5\u5b9a\u4e49\u5347\u7ea7\u5931\u8d25", (Object)step);
                this.logger.error(link.getKey(), e);
            }
        }
        this.logger.info("\u94fe\u63a5\u5b9a\u4e49\uff0c\u5347\u7ea7\u5b8c\u6bd5\uff0c\u6210\u529f{}\u4e2a\uff0c\u5931\u8d25{}\u4e2a", (Object)(links.size() - fail), (Object)fail);
    }

    private void regionsUpdate(EntityViewCache cache) {
        List regions;
        try {
            regions = this.designDataRegionDefineDao.list(" dr_master_key is not null", null, DesignDataRegionDefineImpl.class);
        }
        catch (Exception e) {
            this.logger.error("\u533a\u57df\u5b9a\u4e49\u67e5\u8be2\u5931\u8d25\u8df3\u8fc7\u5347\u7ea7", e);
            return;
        }
        int step = 0;
        int fail = 0;
        this.logger.info("\u5171\u67e5\u51fa {} \u4e2a\u533a\u57df\u5b9a\u4e49\uff0c\u5f00\u59cb\u5347\u7ea7", (Object)regions.size());
        for (DesignDataRegionDefineImpl region : regions) {
            try {
                ++step;
                String masterEntitiesKey = region.getMasterEntitiesKey();
                ArrayList<String> entityIds = new ArrayList<String>();
                for (String view : masterEntitiesKey.split(";")) {
                    EntityViewDefineUp value = cache.getValue(view);
                    if (value == null) continue;
                    entityIds.add(value.getEntityId());
                }
                if (!entityIds.isEmpty()) {
                    region.setMasterEntitiesKey(String.join((CharSequence)";", entityIds));
                    this.designDataRegionDefineDao.update(region);
                }
                this.logger.info("\u7b2c{}\u4e2a\u533a\u57df\u5b9a\u4e49\u6b63\u5728\u5347\u7ea7\uff0c\u8bf7\u8010\u5fc3\u7b49\u5f85", (Object)step);
            }
            catch (Exception e) {
                ++fail;
                this.logger.info("\u7b2c{}\u4e2a\u533a\u57df\u5b9a\u4e49\u5347\u7ea7\u5931\u8d25", (Object)step);
                this.logger.error(region.getKey(), e);
            }
        }
        this.logger.info("\u533a\u57df\u5b9a\u4e49\uff0c\u5347\u7ea7\u5b8c\u6bd5\uff0c\u6210\u529f {} \u4e2a\uff0c\u5931\u8d25 {} \u4e2a", (Object)(regions.size() - fail), (Object)fail);
    }
}

