/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.exception.CreateSystemTableException
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.paramcheck.NODDLDeployExecutor
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.CatalogModelService
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelRegisterService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.annotation.deploy;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.annotation.deploy.helper.FormCellAnnotationTablePubHelper;
import com.jiuqi.nr.definition.exception.CreateSystemTableException;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.paramcheck.NODDLDeployExecutor;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.CatalogModelService;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelRegisterService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class NODDLFormCellAnnotationObserver
implements NODDLDeployExecutor {
    private static final Logger logger = LoggerFactory.getLogger(NODDLFormCellAnnotationObserver.class);
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private DataModelDeployService dataModelDeployService;
    @Autowired
    private DataModelRegisterService dataModelRegisterService;
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private CatalogModelService catalogModelService;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;

    public List<String> preDeploy(String taskKey) {
        ArrayList<String> result = new ArrayList<String>();
        FormCellAnnotationTablePubHelper helper = new FormCellAnnotationTablePubHelper(this.designDataModelService, this.nrDesignTimeController, this.catalogModelService, this.iEntityMetaService, this.periodEngineService);
        DesignTaskDefine taskDefine = this.nrDesignTimeController.queryTaskDefine(taskKey);
        List schemes = null;
        try {
            schemes = this.nrDesignTimeController.queryFormSchemeByTask(taskKey);
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
        }
        if (schemes != null) {
            for (DesignFormSchemeDefine scheme : schemes) {
                this.appendAnnotationDdl(taskDefine, scheme, helper, result);
                this.appendDataLinkFileDdl(taskDefine, scheme, helper, result);
                this.appendCommentDdl(taskDefine, scheme, helper, result);
                this.appendTypeDdl(taskDefine, scheme, helper, result);
            }
        }
        return result;
    }

    public void doDeploy(String taskKey) {
        ArrayList<String> deployTables = new ArrayList<String>();
        List schemes = null;
        try {
            schemes = this.nrDesignTimeController.queryFormSchemeByTask(taskKey);
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
        }
        if (schemes != null) {
            for (DesignFormSchemeDefine scheme : schemes) {
                String annotitionTableCode = "SYS_FMCEAN_" + scheme.getFormSchemeCode();
                this.appendDeployTableModel(deployTables, annotitionTableCode);
                String dataLinkFileTableCode = "SYS_FMCEANDF_" + scheme.getFormSchemeCode();
                this.appendDeployTableModel(deployTables, dataLinkFileTableCode);
                String commentTableCode = "SYS_FMCEANCO_" + scheme.getFormSchemeCode();
                this.appendDeployTableModel(deployTables, commentTableCode);
                String typeTableCode = "SYS_FMCEANTYPE_" + scheme.getFormSchemeCode();
                this.appendDeployTableModel(deployTables, typeTableCode);
            }
        }
        for (String deployTable : deployTables) {
            try {
                this.dataModelRegisterService.registerTable(deployTable);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public double getOrder() {
        return 2.0;
    }

    private void appendAnnotationDdl(DesignTaskDefine taskDefine, DesignFormSchemeDefine scheme, FormCellAnnotationTablePubHelper helper, List<String> result) {
        try {
            String tableId = helper.modelingAnnotition(taskDefine, (FormSchemeDefine)scheme);
            List ddl = this.dataModelDeployService.getDeployTableSqls(tableId);
            if (!CollectionUtils.isEmpty(ddl)) {
                result.addAll(ddl);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void appendDataLinkFileDdl(DesignTaskDefine taskDefine, DesignFormSchemeDefine scheme, FormCellAnnotationTablePubHelper helper, List<String> result) {
        try {
            String tableId = helper.modelingDataLinkFile(taskDefine, (FormSchemeDefine)scheme);
            List ddl = this.dataModelDeployService.getDeployTableSqls(tableId);
            if (!CollectionUtils.isEmpty(ddl)) {
                result.addAll(ddl);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void appendCommentDdl(DesignTaskDefine taskDefine, DesignFormSchemeDefine scheme, FormCellAnnotationTablePubHelper helper, List<String> result) {
        try {
            String tableId = helper.modelingComment(taskDefine, (FormSchemeDefine)scheme);
            List ddl = this.dataModelDeployService.getDeployTableSqls(tableId);
            if (!CollectionUtils.isEmpty(ddl)) {
                result.addAll(ddl);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void appendTypeDdl(DesignTaskDefine taskDefine, DesignFormSchemeDefine scheme, FormCellAnnotationTablePubHelper helper, List<String> result) {
        try {
            String tableId = helper.modelingType(taskDefine, (FormSchemeDefine)scheme);
            List ddl = this.dataModelDeployService.getDeployTableSqls(tableId);
            if (!CollectionUtils.isEmpty(ddl)) {
                result.addAll(ddl);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void appendDeployTableModel(List<String> deployTables, String tableCode) {
        DesignTableModelDefine table = this.getDesTableByCode(tableCode);
        if (table != null) {
            deployTables.add(table.getID());
        }
    }

    public DesignTableModelDefine getDesTableByCode(String code) {
        DesignTableModelDefine table = null;
        try {
            table = this.designDataModelService.getTableModelDefineByCode(code);
        }
        catch (Exception e) {
            throw new CreateSystemTableException(String.format("query table %s error.", code), (Throwable)e);
        }
        return table;
    }
}

