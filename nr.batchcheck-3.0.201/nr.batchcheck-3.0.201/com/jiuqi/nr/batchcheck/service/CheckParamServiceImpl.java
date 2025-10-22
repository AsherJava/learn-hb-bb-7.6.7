/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.nr.dataentry.internal.service.FormGroupProvider
 *  com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo
 *  com.jiuqi.nr.dataentry.paramInfo.FormGroupData
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.jtable.params.base.FormData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batchcheck.service;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.nr.batchcheck.bean.CheckFromInfo;
import com.jiuqi.nr.batchcheck.bean.CheckParamImpl;
import com.jiuqi.nr.batchcheck.dao.IBatchCheckInfoDao;
import com.jiuqi.nr.batchcheck.service.IBatchCheckParamService;
import com.jiuqi.nr.dataentry.internal.service.FormGroupProvider;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo;
import com.jiuqi.nr.dataentry.paramInfo.FormGroupData;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CheckParamServiceImpl
implements IBatchCheckParamService {
    private static final Logger logger = LoggerFactory.getLogger(CheckParamServiceImpl.class);
    @Resource
    private FormGroupProvider formGroupProvider;
    @Resource
    private IBatchCheckInfoDao batchCheckDao;
    @Resource
    private RunTimeAuthViewController runTimeAuthViewController;
    @Resource
    private IFormulaRunTimeController runtimeFormulaController;
    @Resource
    private IJtableEntityService iJtableEntityService;
    @Resource
    private IJtableParamService jtableParamService;

    @Override
    public List<CheckFromInfo> getAllFormsByScheme(String formSchemeKey) {
        ArrayList<CheckFromInfo> allFormInfo = new ArrayList<CheckFromInfo>();
        List formGroupList = this.formGroupProvider.getFormGroupList(formSchemeKey);
        this.addAllGroup(formGroupList);
        for (FormGroupData group : formGroupList) {
            List reports = group.getReports();
            for (FormData report : reports) {
                CheckFromInfo info = new CheckFromInfo();
                info.setCode(report.getCode());
                info.setId(report.getKey());
                info.setTitle(report.getTitle());
                info.setGroupKey(group.getKey());
                info.setGroupTitle(group.getTitle());
                allFormInfo.add(info);
            }
        }
        return allFormInfo;
    }

    private void addAllGroup(List<FormGroupData> formGroupList) {
        for (FormGroupData group : formGroupList) {
            if (group.getGroups() == null || group.getGroups().size() <= 0) continue;
            formGroupList.addAll(group.getGroups());
        }
    }

    @Override
    public CheckParamImpl getBatchCheckParam() {
        String identityId = NpContextHolder.getContext().getIdentityId();
        if (Objects.isNull(identityId)) {
            return null;
        }
        CheckParamImpl impl = this.batchCheckDao.queryBatchCheckInfoByUserId(identityId);
        if (impl == null || impl.getCheckInfo() == null) {
            return null;
        }
        BatchCheckInfo checkInfo = impl.getCheckInfo();
        JtableContext context = checkInfo.getContext();
        String taskKey = context.getTaskKey();
        String formSchemeKey = context.getFormSchemeKey();
        String formulaSchemeKey = checkInfo.getFormulaSchemeKeys();
        String[] split = null;
        if (formulaSchemeKey != null) {
            split = formulaSchemeKey.split(";");
        }
        TaskDefine queryTaskDefine = null;
        FormSchemeDefine formScheme = null;
        try {
            queryTaskDefine = this.runTimeAuthViewController.queryTaskDefine(taskKey);
            formScheme = this.runTimeAuthViewController.getFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            logger.warn("\u67e5\u8be2\u4efb\u52a1\u6216\u62a5\u8868\u65b9\u6848\u5931\u8d25");
        }
        String formulaSchemeTitle = "";
        for (String key : split) {
            FormulaSchemeDefine formulaSchemeDefine = this.runtimeFormulaController.queryFormulaSchemeDefine(key);
            if (formulaSchemeDefine == null) continue;
            formulaSchemeTitle = formulaSchemeTitle + formulaSchemeDefine.getTitle() + ";";
        }
        impl.setTaskName(queryTaskDefine == null ? "\u672a\u77e5\u65b9\u6848" : queryTaskDefine.getTitle());
        impl.setFormSchemeTitle(formScheme == null ? "\u672a\u77e5\u62a5\u8868" : formScheme.getTitle());
        impl.setFormulaSchemeTitle(StringUtils.isEmpty((String)formulaSchemeTitle) ? "\u672a\u77e5\u516c\u5f0f\u65b9\u6848" : formulaSchemeTitle.substring(0, formulaSchemeTitle.length() - 1));
        return impl;
    }

    public boolean addBatchCheckParam(CheckParamImpl impl) {
        String asyncTaskId = impl.getAsyncTaskId();
        String identityId = NpContextHolder.getContext().getIdentityId();
        return this.batchCheckDao.addBatchCheckInfo(impl, identityId, asyncTaskId);
    }

    @Override
    public Instant updataBatchCheckParam(CheckParamImpl impl) throws Exception {
        CheckParamImpl batchCheckParam = this.getBatchCheckParam();
        Instant now = Instant.now();
        impl.setCurrentTime(now);
        if (batchCheckParam == null) {
            if (this.addBatchCheckParam(impl)) {
                return now;
            }
        } else {
            String asyncTaskId = impl.getAsyncTaskId();
            String identityId = NpContextHolder.getContext().getIdentityId();
            if (this.batchCheckDao.updataBatchCheckInfo(impl, identityId, asyncTaskId)) {
                return now;
            }
        }
        throw new Exception("\u5ba1\u6838\u8f85\u52a9\u4fe1\u606f\u5b58\u50a8\u5931\u8d25\uff01");
    }
}

