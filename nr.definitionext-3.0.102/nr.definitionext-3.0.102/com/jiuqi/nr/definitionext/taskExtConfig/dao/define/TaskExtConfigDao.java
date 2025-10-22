/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.SplitTableHelper
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.internal.db.BaseDao
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.definitionext.taskExtConfig.dao.define;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.SplitTableHelper;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definitionext.taskExtConfig.model.TaskExtConfigDefine;
import com.jiuqi.nr.definitionext.taskExtConfig.model.TaskExtTransUtils;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TaskExtConfigDao
extends BaseDao {
    private static final Logger logger = LoggerFactory.getLogger(TaskExtConfigDao.class);
    @Resource
    private IDataDefinitionRuntimeController dataCtrl;
    @Autowired(required=false)
    private SplitTableHelper splitTableHelper;
    private static String EXT_extType = "extType";
    private static String EXT_taskKey = "taskKey";
    private static String EXT_schemaKey = "schemaKey";
    private Class<TaskExtConfigDefine> implClass = TaskExtConfigDefine.class;

    public Class<?> getClz() {
        return this.implClass;
    }

    public Class<?> getExternalTransCls() {
        return TaskExtTransUtils.class;
    }

    public int insertDefine(TaskExtConfigDefine taskExtConfigDefine) throws Exception {
        return this.insert(taskExtConfigDefine);
    }

    public int updateDefine(TaskExtConfigDefine taskExtConfigDefine) throws Exception {
        return this.update(taskExtConfigDefine);
    }

    public int deleteDefineById(String id) throws Exception {
        return this.delete(id);
    }

    public TaskExtConfigDefine getDefineById(String id) throws Exception {
        return (TaskExtConfigDefine)this.getByKey(id, this.implClass);
    }

    public List<TaskExtConfigDefine> getDefineList() throws Exception {
        return this.list(this.implClass);
    }

    public List<TaskExtConfigDefine> getDefinesByType(String type) throws Exception {
        return this.list(new String[]{EXT_extType}, new Object[]{type}, this.implClass);
    }

    public List<TaskExtConfigDefine> getDefinesBySchemakey(String schemaKey, String extType) throws Exception {
        return this.list(new String[]{EXT_schemaKey, EXT_extType}, new Object[]{schemaKey, extType}, this.implClass);
    }

    public String getLibraryTableName(String tableName) {
        try {
            com.jiuqi.nr.entity.engine.executors.ExecutorContext context;
            String libraryTableName;
            if (this.splitTableHelper != null && !(libraryTableName = this.splitTableHelper.getCurrentSplitTableName((ExecutorContext)(context = new com.jiuqi.nr.entity.engine.executors.ExecutorContext(this.dataCtrl)), tableName)).isEmpty()) {
                return libraryTableName;
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return tableName;
    }
}

