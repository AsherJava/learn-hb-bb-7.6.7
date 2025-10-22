/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.data.estimation.service.IEstimationSchemeTemplateService
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationSchemeTemplate
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.data.estimation.web.service;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.data.estimation.service.IEstimationSchemeTemplateService;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationSchemeTemplate;
import com.jiuqi.nr.data.estimation.web.response.EstimationFormulaSchemeInfo;
import com.jiuqi.nr.data.estimation.web.response.EstimationSchemeInfo;
import com.jiuqi.nr.data.estimation.web.service.EstimationSchemeInfoBuilder;
import com.jiuqi.nr.data.estimation.web.service.EstimationSchemeTemplateBuilder;
import com.jiuqi.nr.data.estimation.web.tasktree.EstimationTaskTreeBuilder;
import com.jiuqi.nr.data.estimation.web.tasktree.EstimationTaskTreeNode;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EstimationSchemeManageService {
    @Resource
    private SystemIdentityService identityService;
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Resource
    private IEstimationSchemeTemplateService estimationSchemeService;
    @Resource
    private IFormulaRunTimeController formulaRunTimeController;

    public List<ITree<EstimationTaskTreeNode>> loadingTaskTree() {
        String currentUserId = NpContextHolder.getContext().getUserId();
        boolean isAdmin = this.identityService.isSystemIdentity(currentUserId);
        if (isAdmin) {
            EstimationTaskTreeBuilder treeBuilder = new EstimationTaskTreeBuilder(this.runTimeViewController);
            return treeBuilder.getTree();
        }
        return new ArrayList<ITree<EstimationTaskTreeNode>>();
    }

    public EstimationSchemeInfo loadManageEstimationScheme(String formSchemeId) {
        EstimationSchemeInfoBuilder dataBuilder = new EstimationSchemeInfoBuilder(this.runTimeViewController, this.formulaRunTimeController);
        IEstimationSchemeTemplate estimationScheme = this.estimationSchemeService.findSchemeTemplateByFormScheme(formSchemeId);
        if (estimationScheme != null) {
            return dataBuilder.build(estimationScheme);
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeId);
        return dataBuilder.build(formScheme);
    }

    public List<EstimationFormulaSchemeInfo> loadEstimationCalcFormulaSchemes(String formSchemeId) {
        IEstimationSchemeTemplate estimationScheme = this.estimationSchemeService.findSchemeTemplateByFormScheme(formSchemeId);
        if (estimationScheme != null) {
            List accessFormulaSchemes = estimationScheme.getCalcFormulaSchemes();
            return accessFormulaSchemes.stream().map(fs -> {
                EstimationFormulaSchemeInfo eFormulaSchemeInfo = new EstimationFormulaSchemeInfo();
                eFormulaSchemeInfo.setFormulaSchemeId(fs.getKey());
                eFormulaSchemeInfo.setFormulaSchemeTitle(fs.getTitle());
                return eFormulaSchemeInfo;
            }).collect(Collectors.toList());
        }
        return new ArrayList<EstimationFormulaSchemeInfo>();
    }

    public boolean validEstimationSchemeCode(String formSchemeId, String schemeCode) {
        return this.estimationSchemeService.hasSchemeCode(formSchemeId, schemeCode);
    }

    public String saveEstimationScheme(EstimationSchemeInfo schemeInfo) {
        EstimationSchemeTemplateBuilder templateBuilder = new EstimationSchemeTemplateBuilder(this.runTimeViewController, this.formulaRunTimeController);
        return this.estimationSchemeService.saveEstimationSchemeTemplate(templateBuilder.build(schemeInfo));
    }
}

