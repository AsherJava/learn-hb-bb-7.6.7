/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFieldDefineFinder
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.definition.internal.env;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFieldDefineFinder;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.dao.RuntimeFmlCompileParamDao;
import com.jiuqi.nr.definition.internal.env.FmlTrackExecEnvironment;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FmlTrackFieldDefineFinder
implements IFieldDefineFinder {
    private RuntimeFmlCompileParamDao runtimeFmlCompileParamDao;
    private IEntityMetaService entityMetaService;
    private IRunTimeViewController runTimeViewController;
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private final Map<String, Map<String, FieldDefine>> key_fieldMap = new HashMap<String, Map<String, FieldDefine>>();
    private static final Logger logger = LoggerFactory.getLogger(FmlTrackFieldDefineFinder.class);

    public FmlTrackFieldDefineFinder(ExecutorContext context) {
        FmlTrackExecEnvironment env = (FmlTrackExecEnvironment)context.getEnv();
        String formSchemeKey = env.getFormSchemeKey();
        RuntimeFmlCompileParamDao paramDao = this.getRuntimeFmlCompileParamDao();
        IEntityMetaService entityMetaService = this.getEntityMetaService();
        IRunTimeViewController runTimeViewController = this.getRunTimeViewController();
        IDataDefinitionRuntimeController dataDefinitionRuntimeController = this.getDataDefinitionRuntimeController();
        FormSchemeDefine formScheme = runTimeViewController.getFormScheme(formSchemeKey);
        TableModelDefine tableModel = entityMetaService.getTableModel(formScheme.getDw());
        String tableModelID = tableModel.getID();
        List<FieldDefine> allFieldInFormScheme = paramDao.getAllZBKindDataFieldInFormScheme(formSchemeKey);
        List allFieldsInTable = new ArrayList();
        try {
            allFieldsInTable = dataDefinitionRuntimeController.getAllFieldsInTable(tableModelID);
        }
        catch (Exception e) {
            logger.error(e.getMessage() + "\u5c01\u9762\u4ee3\u7801\u6307\u6807\u67e5\u8be2\u5f02\u5e38", e);
        }
        allFieldInFormScheme.addAll(allFieldsInTable);
        HashMap<String, FieldDefine> fieldMap = new HashMap<String, FieldDefine>();
        for (FieldDefine fieldDefine : allFieldInFormScheme) {
            fieldMap.put(fieldDefine.getCode(), fieldDefine);
        }
        this.key_fieldMap.put(formSchemeKey, fieldMap);
    }

    public FieldDefine findFieldDefine(ExecutorContext context, String fieldCode) throws Exception {
        FmlTrackExecEnvironment env = (FmlTrackExecEnvironment)context.getEnv();
        String formSchemeKey = env.getFormSchemeKey();
        if (StringUtils.isEmpty((String)formSchemeKey)) {
            return null;
        }
        Map<String, FieldDefine> fieldMap = this.key_fieldMap.get(formSchemeKey);
        if (fieldMap == null) {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            TableModelDefine tableModel = this.entityMetaService.getTableModel(formScheme.getDw());
            String tableModelID = tableModel.getID();
            List<FieldDefine> allFieldInFormScheme = this.runtimeFmlCompileParamDao.getAllZBKindDataFieldInFormScheme(formSchemeKey);
            List allFieldsInTable = new ArrayList();
            try {
                allFieldsInTable = this.dataDefinitionRuntimeController.getAllFieldsInTable(tableModelID);
            }
            catch (Exception e) {
                logger.error(e.getMessage() + "\u5c01\u9762\u4ee3\u7801\u6307\u6807\u67e5\u8be2\u5f02\u5e38", e);
            }
            allFieldInFormScheme.addAll(allFieldsInTable);
            HashMap<String, FieldDefine> fieldMap1 = new HashMap<String, FieldDefine>();
            for (FieldDefine fieldDefine : allFieldInFormScheme) {
                fieldMap1.put(fieldDefine.getCode(), fieldDefine);
            }
            this.key_fieldMap.put(formSchemeKey, fieldMap1);
            fieldMap = this.key_fieldMap.get(formSchemeKey);
        }
        return fieldMap.get(fieldCode);
    }

    public FieldDefine findFieldDefine(ExecutorContext context, String fieldCode, int periodType) throws Exception {
        return this.findFieldDefine(context, fieldCode);
    }

    public FieldDefine findFieldDefine(ExecutorContext context, String dataScheme, String fieldCode) throws Exception {
        return this.findFieldDefine(context, fieldCode);
    }

    private RuntimeFmlCompileParamDao getRuntimeFmlCompileParamDao() {
        if (null == this.runtimeFmlCompileParamDao) {
            this.runtimeFmlCompileParamDao = (RuntimeFmlCompileParamDao)BeanUtil.getBean(RuntimeFmlCompileParamDao.class);
        }
        return this.runtimeFmlCompileParamDao;
    }

    private IEntityMetaService getEntityMetaService() {
        if (null == this.entityMetaService) {
            this.entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        }
        return this.entityMetaService;
    }

    private IRunTimeViewController getRunTimeViewController() {
        if (null == this.runTimeViewController) {
            this.runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        }
        return this.runTimeViewController;
    }

    public IDataDefinitionRuntimeController getDataDefinitionRuntimeController() {
        if (null == this.dataDefinitionRuntimeController) {
            this.dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        }
        return this.dataDefinitionRuntimeController;
    }
}

