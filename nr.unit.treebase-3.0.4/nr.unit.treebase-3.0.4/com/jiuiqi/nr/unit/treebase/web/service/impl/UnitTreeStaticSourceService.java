/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.bpm.de.dataflow.service.ITreeNodeIconColorService
 *  com.jiuqi.nr.bpm.de.dataflow.tree.TreeNodeColorInfo
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  javax.annotation.Resource
 */
package com.jiuiqi.nr.unit.treebase.web.service.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.web.response.WorkFlowStatusConfig;
import com.jiuiqi.nr.unit.treebase.web.service.IUnitTreeStaticSourceService;
import com.jiuqi.nr.bpm.de.dataflow.service.ITreeNodeIconColorService;
import com.jiuqi.nr.bpm.de.dataflow.tree.TreeNodeColorInfo;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UnitTreeStaticSourceService
implements IUnitTreeStaticSourceService {
    @Resource
    private ITreeNodeIconColorService flowWorkStateIcon;

    @Override
    public List<WorkFlowStatusConfig> createWorkflowStatusSource(IUnitTreeContext context) {
        ArrayList<WorkFlowStatusConfig> sources = new ArrayList<WorkFlowStatusConfig>();
        FormSchemeDefine formScheme = context.getFormScheme();
        if (formScheme != null) {
            boolean isNodeIconType = this.flowWorkStateIcon.isNodeIconType();
            List infoList = this.flowWorkStateIcon.getStateNodeIconColor(formScheme.getKey());
            for (TreeNodeColorInfo info : infoList) {
                WorkFlowStatusConfig impl = new WorkFlowStatusConfig();
                impl.setCode(info.getState());
                impl.setTitle(info.getStateName());
                impl.setColor(isNodeIconType ? "#0f0f0f" : info.getColor());
                sources.add(impl);
            }
            TreeNodeColorInfo ordinalInfo = infoList.stream().filter(ent -> UploadState.ORIGINAL_UPLOAD.name().equals(ent.getState())).findFirst().get();
            WorkFlowStatusConfig impl = new WorkFlowStatusConfig();
            impl.setCode(UploadState.PART_START.name());
            impl.setTitle(ordinalInfo.getStateName());
            impl.setColor(isNodeIconType ? "#0f0f0f" : ordinalInfo.getColor());
            sources.add(impl);
        }
        return sources;
    }
}

