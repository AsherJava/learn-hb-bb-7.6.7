/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.observer.Observer
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.message.NrTaskDeleteEvent
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  com.jiuqi.nvwa.definition.common.TableModelType
 *  com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 */
package com.jiuqi.nr.dataentry.deploy;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.observer.Observer;
import com.jiuqi.nr.dataentry.deploy.util.DeployUtil;
import com.jiuqi.nr.dataentry.deploy.util.NvwaDataModelDeployUtil;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.message.NrTaskDeleteEvent;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import com.jiuqi.nvwa.definition.common.TableModelType;
import com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

public class FormLockObserver
implements Observer,
ApplicationListener<NrTaskDeleteEvent> {
    @Autowired
    IDesignTimeViewController nrDesignController;
    @Autowired
    private NvwaDataModelDeployUtil nvwaDataModelDeployUtil;

    public boolean isAsyn() {
        return false;
    }

    public String getName() {
        return "\u62a5\u8868\u9501\u5b9a\u8868" + this.getClass().getName();
    }

    public void excute(Object[] objs) throws Exception {
        if (objs == null || objs.length == 0) {
            return;
        }
        for (Object obj : objs) {
            if (!(obj instanceof String)) continue;
            String taskID = (String)obj;
            DesignTaskDefine taskDefine = this.nrDesignController.queryTaskDefine(taskID);
            List schemes = this.nrDesignController.queryFormSchemeByTask(taskID);
            if (schemes == null) {
                return;
            }
            for (DesignFormSchemeDefine scheme : schemes) {
                this.deployFormLock(taskDefine, (FormSchemeDefine)scheme);
            }
        }
    }

    private void deployFormLock(DesignTaskDefine taskDefine, FormSchemeDefine scheme) throws Exception {
        String tableCode = "SYS_FMLK_" + scheme.getFormSchemeCode();
        DesignTableModelDefine tableDefine = this.nvwaDataModelDeployUtil.getDesTableByCode(tableCode);
        boolean doInsert = true;
        if (tableDefine == null) {
            tableDefine = this.nvwaDataModelDeployUtil.createTableDefine();
        } else {
            doInsert = false;
        }
        tableDefine.setCode(tableCode);
        tableDefine.setDesc("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u62a5\u8868\u9501\u5b9a\u8868");
        tableDefine.setTitle("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u62a5\u8868\u9501\u5b9a\u8868");
        tableDefine.setName(tableCode);
        tableDefine.setType(TableModelType.DEFAULT);
        tableDefine.setKind(TableModelKind.DEFAULT);
        tableDefine.setOwner("NR");
        String tableKey = tableDefine.getID();
        StringBuffer tableEnityMasterKeys = new StringBuffer();
        ArrayList<DesignColumnModelDefine> createFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> modifyFieldList = new ArrayList<DesignColumnModelDefine>();
        String entitiesKey = this.nrDesignController.getFormSchemeEntity(scheme.getKey());
        if (StringUtils.isEmpty((String)entitiesKey)) {
            entitiesKey = taskDefine.getMasterEntitiesKey();
        }
        if (StringUtils.isEmpty((String)entitiesKey)) {
            throw new Exception("\u62a5\u8868\u65b9\u6848\u3001\u4efb\u52a1\u6ca1\u6709\u8bbe\u7f6e\u4e3b\u952e\u65e0\u6cd5\u521b\u5efa\u62a5\u8868\u9501\u5b9a\u8868");
        }
        String[] entitiesKeyArr = entitiesKey.split(";");
        this.nvwaDataModelDeployUtil.initDwPeriodDimField(tableKey, tableEnityMasterKeys, entitiesKeyArr, createFieldList, modifyFieldList);
        tableEnityMasterKeys.append(DeployUtil.initField_String(tableKey, "FMLK_FORMSCHEMEKEY", null, createFieldList, modifyFieldList, 50, this.nvwaDataModelDeployUtil)).append(";");
        tableEnityMasterKeys.append(DeployUtil.initField_String(tableKey, "FMLK_FORMKEY", null, createFieldList, modifyFieldList, 50, this.nvwaDataModelDeployUtil)).append(";");
        DeployUtil.initField_String(tableKey, "FMLK_RECID", null, createFieldList, modifyFieldList, 50, this.nvwaDataModelDeployUtil);
        DeployUtil.initField_String(tableKey, "FMLK_USER", null, createFieldList, modifyFieldList, 50, this.nvwaDataModelDeployUtil);
        DeployUtil.initField_Boolean(tableKey, "FMLK_ISLOCK", null, createFieldList, modifyFieldList, this.nvwaDataModelDeployUtil);
        tableDefine.setBizKeys(tableEnityMasterKeys.toString());
        tableDefine.setKeys(tableEnityMasterKeys.toString());
        if (doInsert) {
            DesignCatalogModelDefine sysTableGroupByParent = this.nvwaDataModelDeployUtil.createCatlogModelDefine();
            tableDefine.setCatalogID(sysTableGroupByParent.getID());
            this.nvwaDataModelDeployUtil.insertTableModelDefine(tableDefine);
        } else {
            this.nvwaDataModelDeployUtil.updateTableModelDefine(tableDefine);
        }
        if (createFieldList.size() > 0) {
            this.nvwaDataModelDeployUtil.insertFields(createFieldList.toArray(new DesignColumnModelDefine[1]));
        }
        if (modifyFieldList.size() > 0) {
            this.nvwaDataModelDeployUtil.updateFields(modifyFieldList.toArray(new DesignColumnModelDefine[1]));
        }
        this.nvwaDataModelDeployUtil.deployTable(tableDefine.getID());
    }

    @Override
    public void onApplicationEvent(NrTaskDeleteEvent event) {
        List formSchemeDefines = event.getFormSchemeDefines();
        if (formSchemeDefines == null || formSchemeDefines.size() <= 0) {
            return;
        }
        for (FormSchemeDefine scheme : formSchemeDefines) {
            this.nvwaDataModelDeployUtil.deployDeleteTableByCode("SYS_FMLK_" + scheme.getFormSchemeCode());
        }
    }
}

