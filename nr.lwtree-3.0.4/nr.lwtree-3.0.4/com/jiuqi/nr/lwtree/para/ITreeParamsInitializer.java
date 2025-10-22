/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.NrPeriodConst
 *  org.json.JSONObject
 */
package com.jiuqi.nr.lwtree.para;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.lwtree.para.ITreeLoadPara;
import com.jiuqi.nr.lwtree.query.IEntityTableQueryer;
import com.jiuqi.nr.period.common.utils.NrPeriodConst;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

public class ITreeParamsInitializer {
    private ITreeLoadPara loadParam;
    protected IEntityTableQueryer dataEngineMgr;
    private Map<String, DimensionValue> otherUploadDimValueSet;
    private String mainDimName = null;
    private IEntityDefine entityDefine;
    private IRunTimeViewController formRtCtl = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
    private IEntityMetaService metaService;

    public ITreeParamsInitializer(ITreeLoadPara loadParam) {
        this.dataEngineMgr = (IEntityTableQueryer)BeanUtil.getBean(IEntityTableQueryer.class);
        this.metaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        this.loadParam = loadParam;
        this.init();
    }

    public void init() {
        this.entityDefine = this.metaService.queryEntity(this.loadParam.getViewKey());
        if (null == this.entityDefine) {
            LoggerFactory.getLogger(this.getClass()).error("\u5355\u4f4d\u6811\u67e5\u8be2\u89c6\u56fe\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
    }

    public String getEntityId() {
        return this.entityDefine.getId();
    }

    public String getPeriodEntityId() {
        TaskDefine taskDefine = this.getTaskDefine();
        if (null != taskDefine) {
            return taskDefine.getDateTime();
        }
        return null;
    }

    public IEntityDefine getIEntityDefine() {
        return this.entityDefine;
    }

    public IEntityModel getIEntityModel() {
        IEntityModel entityModel = null;
        entityModel = this.metaService.getEntityModel(this.entityDefine.getId());
        return entityModel;
    }

    public FormSchemeDefine getFormScheme() {
        FormSchemeDefine formScheme = null;
        if (StringUtils.isNotEmpty((String)this.loadParam.getFormSchemeKey())) {
            formScheme = this.formRtCtl.getFormScheme(this.loadParam.getFormSchemeKey());
        }
        return formScheme;
    }

    public TaskDefine getTaskDefine() {
        TaskDefine taskDefine = null;
        FormSchemeDefine formScheme = this.getFormScheme();
        if (formScheme != null) {
            taskDefine = this.formRtCtl.queryTaskDefine(formScheme.getTaskKey());
        }
        return taskDefine;
    }

    public String getPeriod() {
        return this.loadParam.getPeriod();
    }

    public boolean isStartFlow() {
        boolean startFlowState = false;
        FormSchemeDefine formScheme = this.getFormScheme();
        if (formScheme != null) {
            TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
            FlowsType flowsType = flowsSetting.getFlowsType();
            switch (flowsType) {
                case DEFAULT: {
                    startFlowState = true;
                    break;
                }
                case NOSTARTUP: {
                    startFlowState = false;
                    break;
                }
                case WORKFLOW: {
                    startFlowState = true;
                    break;
                }
                default: {
                    startFlowState = false;
                }
            }
        }
        return startFlowState;
    }

    public boolean isDefaultWorkFlow() {
        TaskFlowsDefine flowsSetting;
        FlowsType flowsType;
        boolean def = false;
        FormSchemeDefine formScheme = this.getFormScheme();
        if (formScheme != null && Objects.requireNonNull(flowsType = (flowsSetting = formScheme.getFlowsSetting()).getFlowsType()) == FlowsType.DEFAULT) {
            def = true;
        }
        return def;
    }

    public boolean hasContainBBLX() {
        return this.getBBLXAttribute() != null;
    }

    public IEntityAttribute getBBLXAttribute() {
        IEntityModel entityModel = this.getIEntityModel();
        return entityModel.getBblxField();
    }

    public boolean hasDataConfirm() {
        FormSchemeDefine formScheme = this.getFormScheme();
        if (formScheme != null) {
            TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
            return flowsSetting.isDataConfirm();
        }
        return false;
    }

    public boolean canShowCode() {
        return false;
    }

    public boolean canDraggable() {
        return false;
    }

    public String getFormSchemeKey() {
        FormSchemeDefine formScheme = this.getFormScheme();
        if (formScheme != null) {
            return formScheme.getKey();
        }
        return null;
    }

    public String getMainDimName() {
        if (StringUtils.isEmpty((String)this.mainDimName)) {
            this.mainDimName = this.dataEngineMgr.makeEntityDimName(this.entityDefine.getId());
        }
        return this.mainDimName;
    }

    public String getPeriodDimName() {
        return NrPeriodConst.DATETIME;
    }

    public List<String> getUploadEntityDimNames() {
        String uploadEntityIds;
        TaskFlowsDefine flowsSetting;
        ArrayList<String> uploadDimNames = new ArrayList<String>();
        FormSchemeDefine formScheme = this.getFormScheme();
        if (formScheme != null && (flowsSetting = formScheme.getFlowsSetting()) != null && StringUtils.isNotEmpty((String)(uploadEntityIds = flowsSetting.getDesignTableDefines()))) {
            String[] entityIds;
            for (String entityId : entityIds = uploadEntityIds.split(";")) {
                String dimName = this.dataEngineMgr.makeEntityDimName(entityId);
                if (!StringUtils.isNotEmpty((String)dimName)) continue;
                uploadDimNames.add(dimName);
            }
        }
        return uploadDimNames;
    }

    public Map<String, DimensionValue> getOtherUploadDimValueSet() {
        if (this.otherUploadDimValueSet == null) {
            this.otherUploadDimValueSet = new HashMap<String, DimensionValue>();
            List<String> uploadEntityDimNames = this.getUploadEntityDimNames();
            String mainDimName = this.getMainDimName();
            if (!uploadEntityDimNames.isEmpty()) {
                uploadEntityDimNames.removeIf(dimName -> dimName.equals(mainDimName) || dimName.equals("DATATIME"));
                Map<String, DimensionValue> dimensions = this.loadParam.getDimValueSet();
                if (dimensions != null) {
                    for (String dimName2 : uploadEntityDimNames) {
                        this.otherUploadDimValueSet.put(dimName2, dimensions.get(dimName2));
                    }
                }
            }
        }
        return this.otherUploadDimValueSet;
    }

    public String getViewKey() {
        return this.loadParam.getViewKey();
    }

    public JSONObject getCustomVariable() {
        return this.loadParam.getCustomVariable();
    }

    public String getExpression() {
        return this.loadParam.getExpression();
    }
}

