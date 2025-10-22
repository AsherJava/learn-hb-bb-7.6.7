/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.paramcheck.NODDLDeployExecutor
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  com.jiuqi.nvwa.definition.common.TableModelType
 *  com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelRegisterService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  com.jiuqi.xlib.utils.CollectionUtils
 */
package com.jiuqi.nr.time.setting.util;

import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.paramcheck.NODDLDeployExecutor;
import com.jiuqi.nr.time.setting.util.NvwaDataModelCreateUtil;
import com.jiuqi.nr.time.setting.util.TimeSettingObserver;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import com.jiuqi.nvwa.definition.common.TableModelType;
import com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelRegisterService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import com.jiuqi.xlib.utils.CollectionUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NoddlTimeSettingObserveDeployExecutor
implements NODDLDeployExecutor {
    private static final Logger logger = LoggerFactory.getLogger(TimeSettingObserver.class);
    @Autowired
    IDesignTimeViewController nrDesignController;
    @Autowired
    NvwaDataModelCreateUtil nvwaDataModelCreateUtil;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private DataModelDeployService dataModelDeployService;
    @Autowired
    private DataModelRegisterService dataModelRegisterService;

    public List<String> preDeploy(String taskKey) {
        ArrayList<String> result = new ArrayList<String>();
        List schemes = null;
        try {
            DesignTaskDefine taskDefine = this.nrDesignTimeController.queryTaskDefine(taskKey);
            schemes = this.nrDesignTimeController.queryFormSchemeByTask(taskKey);
            for (DesignFormSchemeDefine scheme : schemes) {
                String tableKey = this.deployTable(taskDefine, (FormSchemeDefine)scheme);
                List ddl = this.dataModelDeployService.getDeployTableSqls(tableKey);
                if (CollectionUtils.isEmpty((Collection)ddl)) continue;
                result.addAll(ddl);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public void doDeploy(String taskKey) {
        List schemes = null;
        try {
            schemes = this.nrDesignTimeController.queryFormSchemeByTask(taskKey);
            for (DesignFormSchemeDefine scheme : schemes) {
                String tableCode = "NR_TIME_SETTING_" + scheme.getFormSchemeCode();
                DesignTableModelDefine tableDefine = this.designDataModelService.getTableModelDefineByCode(tableCode);
                if (tableDefine == null) continue;
                try {
                    this.dataModelRegisterService.registerTable(tableDefine.getID());
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public double getOrder() {
        return 0.0;
    }

    public String deployTable(DesignTaskDefine taskDefine, FormSchemeDefine scheme) throws Exception {
        String tableKey = null;
        try {
            String tableCode = "NR_TIME_SETTING_" + scheme.getFormSchemeCode();
            DesignTableModelDefine tableDefine = this.nvwaDataModelCreateUtil.getDesTableByCode(tableCode);
            boolean doInsert = true;
            if (tableDefine == null) {
                tableDefine = this.nvwaDataModelCreateUtil.createTableDefine();
            } else {
                doInsert = false;
            }
            tableDefine.setCode(tableCode);
            tableDefine.setDesc("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u7684\u586b\u62a5\u65f6\u95f4\u8868");
            tableDefine.setTitle("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u7684\u586b\u62a5\u65f6\u95f4\u8868");
            tableDefine.setName(tableCode);
            tableDefine.setType(TableModelType.DEFAULT);
            tableDefine.setKind(TableModelKind.DEFAULT);
            tableDefine.setOwner("NR");
            tableKey = tableDefine.getID();
            StringBuffer tableEnityMasterKeys = new StringBuffer();
            ArrayList<DesignColumnModelDefine> createFieldList = new ArrayList<DesignColumnModelDefine>();
            ArrayList<DesignColumnModelDefine> modifyFieldList = new ArrayList<DesignColumnModelDefine>();
            Map<String, DesignColumnModelDefine> filedMap = this.nvwaDataModelCreateUtil.getFiledMap(tableKey);
            this.nvwaDataModelCreateUtil.initDwPeriodDimField(tableKey, tableEnityMasterKeys, taskDefine, scheme, createFieldList, modifyFieldList, filedMap);
            this.initOtherFiled(tableKey, createFieldList, modifyFieldList, tableEnityMasterKeys);
            tableDefine.setBizKeys(tableEnityMasterKeys.toString());
            tableDefine.setKeys(tableEnityMasterKeys.toString());
            if (doInsert) {
                DesignCatalogModelDefine sysTableGroupByParent = this.nvwaDataModelCreateUtil.createCatlogModelDefine();
                tableDefine.setCatalogID(sysTableGroupByParent.getID());
                this.nvwaDataModelCreateUtil.insertTableModelDefine(tableDefine);
            } else {
                this.nvwaDataModelCreateUtil.updateTableModelDefine(tableDefine);
            }
            if (createFieldList.size() > 0) {
                this.nvwaDataModelCreateUtil.insertFields(createFieldList.toArray(new DesignColumnModelDefine[1]));
            }
            if (modifyFieldList.size() > 0) {
                this.nvwaDataModelCreateUtil.updateFields(modifyFieldList.toArray(new DesignColumnModelDefine[1]));
            }
            return tableKey;
        }
        catch (Exception e) {
            logger.error("\u521b\u5efa\u586b\u62a5\u65f6\u95f4\u8868\u5931\u8d25,\u4efb\u52a1:" + taskDefine.getTaskCode() + "|" + taskDefine.getTitle(), e);
            return tableKey;
        }
    }

    private void initOtherFiled(String tableKey, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, StringBuffer tableEnityMasterKeys) {
        try {
            this.nvwaDataModelCreateUtil.initField_String(tableKey, "formscheme_id", null, createFieldList, modifyFieldList, 50);
            this.nvwaDataModelCreateUtil.initField_Time_Stamp(tableKey, "submit_starttime", null, createFieldList, modifyFieldList);
            this.nvwaDataModelCreateUtil.initField_Time_Stamp(tableKey, "submit_deadlinetime", null, createFieldList, modifyFieldList);
            this.nvwaDataModelCreateUtil.initField_Time_Stamp(tableKey, "repay_daealine", null, createFieldList, modifyFieldList);
            this.nvwaDataModelCreateUtil.initField_String(tableKey, "operator", null, createFieldList, modifyFieldList, 50);
            this.nvwaDataModelCreateUtil.initField_String(tableKey, "operator_unitid", null, createFieldList, modifyFieldList, 1000);
            this.nvwaDataModelCreateUtil.initField_Time_Stamp(tableKey, "createtime", null, createFieldList, modifyFieldList);
            this.nvwaDataModelCreateUtil.initField_Time_Stamp(tableKey, "updatetime", null, createFieldList, modifyFieldList);
            this.nvwaDataModelCreateUtil.initField_Integer(tableKey, "unit_level", null, createFieldList, modifyFieldList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

