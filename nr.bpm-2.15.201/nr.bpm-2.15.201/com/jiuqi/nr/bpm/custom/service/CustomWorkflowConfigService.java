/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.service.IDesignFormSchemeService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityDefineAssist
 */
package com.jiuqi.nr.bpm.custom.service;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl;
import com.jiuqi.nr.bpm.custom.bean.CurrencyDefine;
import com.jiuqi.nr.bpm.custom.bean.FormschemeData;
import com.jiuqi.nr.bpm.custom.bean.FormulaSchemeData;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowTreeNode;
import com.jiuqi.nr.bpm.custom.dao.CustomWorkFolwDao;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.service.IDesignFormSchemeService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityDefineAssist;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CustomWorkflowConfigService {
    @Autowired
    private IFormulaDesignTimeController formulaDesignTimeController;
    @Autowired
    private DefinitionAuthorityProvider definitionAuthority;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IDataDefinitionRuntimeController definitionRuntimeController;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    @Qualifier(value="runtimeDao")
    CustomWorkFolwDao runtimeCustomWorkflowDao;
    @Autowired
    @Qualifier(value="designDao")
    CustomWorkFolwDao designCustomWorkflowDao;
    @Autowired
    private IEntityDefineAssist iEntityDefineAssist;
    @Autowired
    private IDesignFormSchemeService iFormSchemeService;

    public List<FormschemeData> queryFormSchemeDefines(String taskKey) {
        ArrayList<FormschemeData> formSchemeDefines = new ArrayList<FormschemeData>();
        try {
            List formSchemes = this.designTimeViewController.queryFormSchemeByTask(taskKey);
            for (FormSchemeDefine formSchemeDefine : formSchemes) {
                if (!this.definitionAuthority.canReadFormScheme(formSchemeDefine.getKey())) continue;
                formSchemeDefines.add(new FormschemeData(formSchemeDefine.getKey(), formSchemeDefine.getTitle()));
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return formSchemeDefines;
    }

    public List<FormulaSchemeData> queryFormulaSchemeDefines(String formSchemeKey) {
        ArrayList<FormulaSchemeData> formulaSchemeDataList = new ArrayList<FormulaSchemeData>();
        List formulaSchemeDefines = this.formulaDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeKey);
        DesignFormulaSchemeDefine defaultFormulaSchemeInFormScheme = this.formulaDesignTimeController.getDefaultFormulaSchemeInFormScheme(formSchemeKey);
        FormulaSchemeData formulaSchemeData = null;
        for (DesignFormulaSchemeDefine formulaSchemeDefine : formulaSchemeDefines) {
            if (!this.definitionAuthority.canReadFormulaScheme(formulaSchemeDefine.getKey()) || formulaSchemeDefine.getFormulaSchemeType() != FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT) continue;
            formulaSchemeData = new FormulaSchemeData();
            formulaSchemeData.setKey(formulaSchemeDefine.getKey());
            formulaSchemeData.setTitle(formulaSchemeDefine.getTitle());
            formulaSchemeData.setDefaultFormula(false);
            formulaSchemeDataList.add(formulaSchemeData);
        }
        for (FormulaSchemeData formula : formulaSchemeDataList) {
            if (!defaultFormulaSchemeInFormScheme.getKey().equals(formula.getKey())) continue;
            formula.setDefaultFormula(true);
        }
        return formulaSchemeDataList;
    }

    public List<CurrencyDefine> queryCurrencyData() throws Exception {
        ArrayList<CurrencyDefine> currencyDatas = new ArrayList<CurrencyDefine>();
        RunTimeEntityViewDefineImpl viewDefine = new RunTimeEntityViewDefineImpl();
        viewDefine.setEntityId("MD_CURRENCY@BASE");
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        iEntityQuery.setEntityView((EntityViewDefine)viewDefine);
        iEntityQuery.sorted(true);
        ExecutorContext context = new ExecutorContext(this.definitionRuntimeController);
        context.setPeriodView("MD_CURRENCY@BASE");
        IEntityTable iEntityTable = iEntityQuery.executeReader((IContext)context);
        List allRows = iEntityTable.getAllRows();
        if (null != allRows && allRows.size() != 0) {
            for (IEntityRow allRow : allRows) {
                currencyDatas.add(new CurrencyDefine(allRow.getCode(), allRow.getTitle()));
            }
        }
        return currencyDatas;
    }

    public List<WorkFlowTreeNode> queryWorkflowByTaskKey(String taskKey) {
        ArrayList<WorkFlowTreeNode> groupTNodes = new ArrayList<WorkFlowTreeNode>();
        List<WorkFlowDefine> workFlowDefineByTaskKey = this.runtimeCustomWorkflowDao.getWorkFlowDefineByTaskKey(taskKey);
        HashMap<String, WorkFlowDefine> map = new HashMap<String, WorkFlowDefine>();
        for (WorkFlowDefine workFlowDefine : workFlowDefineByTaskKey) {
            if (map.get(workFlowDefine.getId()) != null) continue;
            map.put(workFlowDefine.getId(), workFlowDefine);
        }
        List<WorkFlowDefine> designDefines = this.designCustomWorkflowDao.getWorkFlowDefineByTaskKey(taskKey);
        for (WorkFlowDefine d : designDefines) {
            if (map.get(d.getId()) != null) continue;
            map.put(d.getId(), d);
        }
        boolean bl = true;
        for (Map.Entry mp : map.entrySet()) {
            boolean bl2;
            WorkFlowDefine value = (WorkFlowDefine)mp.getValue();
            WorkFlowTreeNode tnode = this.getWorkFlowTreeNode(value);
            if (bl2) {
                tnode.setSelected(true);
                bl2 = false;
            } else {
                tnode.setSelected(false);
            }
            groupTNodes.add(tnode);
        }
        return groupTNodes;
    }

    public List<WorkFlowTreeNode> queryPublishedWorkflowByTaskKey(String taskKey) {
        ArrayList<WorkFlowTreeNode> groupTNodes = new ArrayList<WorkFlowTreeNode>();
        HashMap<String, WorkFlowDefine> map = new HashMap<String, WorkFlowDefine>();
        List<WorkFlowDefine> workFlowDefineByTaskKey = this.runtimeCustomWorkflowDao.getWorkFlowDefineByTaskKey(taskKey);
        for (WorkFlowDefine workFlowDefine : workFlowDefineByTaskKey) {
            map.putIfAbsent(workFlowDefine.getId(), workFlowDefine);
        }
        for (Map.Entry entry : map.entrySet()) {
            WorkFlowDefine value = (WorkFlowDefine)entry.getValue();
            WorkFlowTreeNode tnode = this.getWorkFlowTreeNode(value);
            groupTNodes.add(tnode);
        }
        return groupTNodes;
    }

    public WorkFlowTreeNode getWorkFlowTreeNode(WorkFlowDefine define) {
        WorkFlowTreeNode tnode = new WorkFlowTreeNode();
        tnode.setData(define);
        tnode.setTitle(define.getTitle());
        tnode.setGroup(false);
        tnode.setHasChildern(false);
        return tnode;
    }

    public boolean enableCurrency(String taskKey) {
        Boolean reportDimension;
        boolean contains;
        String dims;
        if (com.jiuqi.bi.util.StringUtils.isEmpty((String)taskKey)) {
            return false;
        }
        DesignTaskDefine designTaskDefine = this.designTimeViewController.queryTaskDefine(taskKey);
        boolean openCurrency = this.iEntityDefineAssist.existCurrencyAttributes(designTaskDefine.getDw());
        return openCurrency && Objects.nonNull(designTaskDefine) && StringUtils.hasLength(dims = designTaskDefine.getDims()) && (contains = dims.contains("MD_CURRENCY@BASE")) && (reportDimension = this.iFormSchemeService.isReportDimension(taskKey, "MD_CURRENCY@BASE")) == false;
    }
}

