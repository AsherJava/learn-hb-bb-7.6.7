/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.common.TaskType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.controller.IPrintDesignTimeController
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.examine.web.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.controller.IPrintDesignTimeController;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.examine.common.ParaType;
import com.jiuqi.nr.examine.web.bean.ErrorQueryVo;
import com.jiuqi.nr.examine.web.bean.ParaNode;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParamExamineService {
    private static final Logger logger = LoggerFactory.getLogger(ParamExamineService.class);
    @Autowired
    private IDesignTimeViewController nrDesignController;
    @Autowired
    private IFormulaDesignTimeController formulaController;
    @Autowired
    private IPrintDesignTimeController printController;

    public ParaNode getParaNode(ErrorQueryVo errorQueryVo) {
        ParaNode node = new ParaNode();
        if (StringUtils.isNotEmpty((String)errorQueryVo.paraKey)) {
            switch (ParaType.forValue(errorQueryVo.paraType)) {
                case TASK: {
                    node.addChild(this.initTaskNode(errorQueryVo.paraKey, null));
                    break;
                }
                case FORMSCHEME: {
                    node.addChild(this.initFormSchemeNode(errorQueryVo.paraKey, null));
                    break;
                }
                case FORMGROUP: {
                    node.addChild(this.initFormGroupNode(errorQueryVo.paraKey, null));
                    break;
                }
                case FORM: {
                    node.addChild(this.initFormNode(errorQueryVo.paraKey, null));
                    break;
                }
                case REGION: {
                    node.addChild(this.initRegionNode(errorQueryVo.paraKey, null));
                    break;
                }
                case LINK: {
                    node.addChild(this.initLinkNode(errorQueryVo.paraKey, null));
                    break;
                }
                case FORMULASCHEME: {
                    node.addChild(this.initFormulaSchemeNode(errorQueryVo.paraKey, null));
                    break;
                }
                case FORMULA: {
                    node.addChild(this.initFormulaNode(errorQueryVo.paraKey, null));
                    break;
                }
                case PRINTSCHEME: {
                    node.addChild(this.initPringSchemeNode(errorQueryVo.paraKey, null));
                    break;
                }
            }
        }
        return node;
    }

    private ParaNode initPringSchemeNode(String paraKey, ParaNode childNode) {
        DesignPrintTemplateDefine printSchemeDefine = null;
        try {
            printSchemeDefine = this.printController.queryPrintTemplateDefine(paraKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (printSchemeDefine == null) {
            return childNode;
        }
        ParaNode node = new ParaNode();
        node.title = printSchemeDefine.getTitle();
        node.paraType = ParaType.PRINTSCHEME.getValue();
        node.addChild(childNode);
        return this.initFormSchemeNode(printSchemeDefine.getPrintSchemeKey(), node);
    }

    private ParaNode initFormulaNode(String paraKey, ParaNode childNode) {
        DesignFormulaDefine formulaDefine = null;
        try {
            formulaDefine = this.formulaController.queryFormulaDefine(paraKey);
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
        }
        if (formulaDefine == null) {
            return childNode;
        }
        ParaNode node = new ParaNode();
        node.title = formulaDefine.getCode();
        node.paraType = ParaType.FORMULA.getValue();
        node.addChild(childNode);
        return this.initFormSchemeNode(formulaDefine.getFormulaSchemeKey(), node);
    }

    private ParaNode initFormulaSchemeNode(String paraKey, ParaNode childNode) {
        DesignFormulaSchemeDefine formulaSchemeDefine = this.formulaController.queryFormulaSchemeDefine(paraKey);
        if (formulaSchemeDefine == null) {
            return childNode;
        }
        ParaNode node = new ParaNode();
        node.title = formulaSchemeDefine.getTitle();
        node.paraType = ParaType.FORMULASCHEME.getValue();
        node.addChild(childNode);
        return this.initFormSchemeNode(formulaSchemeDefine.getFormSchemeKey(), node);
    }

    private List<ParaNode> initLinkNode(String paraKey, ParaNode childNode) {
        DesignDataLinkDefine link = this.nrDesignController.queryDataLinkDefine(paraKey);
        if (link == null) {
            if (childNode == null) {
                return null;
            }
            ArrayList<ParaNode> list = new ArrayList<ParaNode>();
            list.add(childNode);
            return list;
        }
        ParaNode node = new ParaNode();
        node.title = link.getTitle();
        node.paraType = ParaType.LINK.getValue();
        node.addChild(childNode);
        return this.initRegionNode(link.getRegionKey(), node);
    }

    private List<ParaNode> initRegionNode(String paraKey, ParaNode childNode) {
        DesignDataRegionDefine region = this.nrDesignController.queryDataRegionDefine(paraKey);
        if (region == null) {
            if (childNode == null) {
                return null;
            }
            ArrayList<ParaNode> list = new ArrayList<ParaNode>();
            list.add(childNode);
            return list;
        }
        ParaNode node = new ParaNode();
        node.title = region.getTitle();
        node.paraType = ParaType.REGION.getValue();
        node.addChild(childNode);
        return this.initFormNode(region.getFormKey(), node);
    }

    private List<ParaNode> initFormNode(String paraKey, ParaNode childNode) {
        DesignFormDefine formDefine = this.nrDesignController.queryFormById(paraKey);
        if (formDefine == null) {
            if (childNode == null) {
                return null;
            }
            ArrayList<ParaNode> list = new ArrayList<ParaNode>();
            list.add(childNode);
            return list;
        }
        List groups = this.nrDesignController.getFormGroupsByFormId(paraKey);
        if (groups == null) {
            if (childNode == null) {
                return null;
            }
            ArrayList<ParaNode> list = new ArrayList<ParaNode>();
            list.add(childNode);
            return list;
        }
        return groups.stream().map(group -> {
            ParaNode node = new ParaNode();
            node.title = formDefine.getTitle();
            node.paraType = ParaType.FORM.getValue();
            node.addChild(childNode);
            return this.initFormGroupNode(group.getKey(), node);
        }).filter(n -> n != null).collect(Collectors.toList());
    }

    private ParaNode initFormGroupNode(String paraKey, ParaNode childNode) {
        DesignFormGroupDefine formGroupDefine = this.nrDesignController.queryFormGroup(paraKey);
        if (formGroupDefine == null) {
            return childNode;
        }
        ParaNode node = new ParaNode();
        node.title = formGroupDefine.getTitle();
        node.paraType = ParaType.FORMGROUP.getValue();
        node.addChild(childNode);
        return this.initFormSchemeNode(formGroupDefine.getFormSchemeKey(), node);
    }

    private ParaNode initFormSchemeNode(String paraKey, ParaNode childNode) {
        DesignFormSchemeDefine formSchemeDefine = this.nrDesignController.queryFormSchemeDefine(paraKey);
        if (formSchemeDefine == null) {
            return childNode;
        }
        ParaNode node = new ParaNode();
        node.title = formSchemeDefine.getTitle();
        node.paraType = ParaType.FORMSCHEME.getValue();
        node.addChild(childNode);
        return this.initTaskNode(formSchemeDefine.getTaskKey(), node);
    }

    private ParaNode initTaskNode(String paraKey, ParaNode childNode) {
        DesignTaskDefine taskDefine = this.nrDesignController.queryTaskDefine(paraKey);
        if (taskDefine == null) {
            return childNode;
        }
        ParaNode node = new ParaNode();
        node.title = taskDefine.getTitle();
        node.paraType = ParaType.TASK.getValue();
        node.addChild(childNode);
        return node;
    }

    public List<DesignTaskDefine> getTask() {
        return this.nrDesignController.getAllTaskDefinesByType(TaskType.TASK_TYPE_DEFAULT);
    }

    public List<DesignFormSchemeDefine> getSchemeInTask(String taskKey) {
        try {
            return this.nrDesignController.queryFormSchemeByTask(taskKey);
        }
        catch (JQException e) {
            return Collections.emptyList();
        }
    }
}

