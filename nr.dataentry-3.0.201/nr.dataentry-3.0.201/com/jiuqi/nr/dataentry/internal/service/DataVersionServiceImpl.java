/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.jtable.exception.JTableException
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.snapshot.DataVersion
 *  com.jiuqi.nr.snapshot.ISnapshotCompare
 *  com.jiuqi.nr.snapshot.SnapshotService
 *  com.jiuqi.nr.snapshot.bean.FormCompareDifference
 *  com.jiuqi.nr.snapshot.impl.DataVersionImpl
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DataVersionData;
import com.jiuqi.nr.dataentry.bean.DataVersionParam;
import com.jiuqi.nr.dataentry.exception.DataEntryNoTaskException;
import com.jiuqi.nr.dataentry.internal.service.FormGroupProvider;
import com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.paramInfo.BatchDimensionValueFormInfo;
import com.jiuqi.nr.dataentry.paramInfo.DataVersionCompareParam;
import com.jiuqi.nr.dataentry.paramInfo.DimensionValueFormInfo;
import com.jiuqi.nr.dataentry.service.IDataVersionService;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.snapshot.DataVersion;
import com.jiuqi.nr.snapshot.ISnapshotCompare;
import com.jiuqi.nr.snapshot.SnapshotService;
import com.jiuqi.nr.snapshot.bean.FormCompareDifference;
import com.jiuqi.nr.snapshot.impl.DataVersionImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataVersionServiceImpl
implements IDataVersionService {
    private static final Logger logger = LoggerFactory.getLogger(DataVersionServiceImpl.class);
    @Autowired
    private SnapshotService snapshotService;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private ISnapshotCompare snapshotCompare;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    public CacheObjectResourceRemote cacheObjectResourceRemote;
    @Autowired
    private NpApplication npApplication;
    @Resource
    IRunTimeViewController viewCtrl;
    @Autowired
    FormGroupProvider formGroupProvider;

    @Override
    public ReturnInfo createOrUpdateDataVersion(DataVersionParam param) throws JTableException {
        String dataVersionObj;
        TaskDefine taskDefine = null;
        try {
            taskDefine = this.runtimeView.queryTaskDefine(param.getContext().getTaskKey());
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw new DataEntryNoTaskException(new String[]{"\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u7684\u4efb\u52a1"});
        }
        if (null == param.getDataVersionId()) {
            param.setDataVersionId(UUID.randomUUID().toString());
        }
        if ("1".equals(dataVersionObj = this.taskOptionController.getValue(taskDefine.getKey(), "DATA_VERSION"))) {
            return this.createOrUpdateDataVersion(param, true);
        }
        return this.createOrUpdateDataVersion(param, false);
    }

    private List<DataVersion> queryAllWithoutUserName(JtableContext context) {
        ArrayList<DataVersion> list = new ArrayList();
        try {
            list = this.snapshotService.queryVersion(this.getDimensionCombination(context.getDimensionSet()), context.getFormSchemeKey());
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return list;
    }

    @Override
    public List<DataVersionData> queryAll(JtableContext context) {
        ArrayList<DataVersionData> resultList = new ArrayList<DataVersionData>();
        List list = null;
        try {
            list = this.snapshotService.queryVersion(this.getDimensionCombination(context.getDimensionSet()), context.getFormSchemeKey());
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (list == null) {
            return new ArrayList<DataVersionData>();
        }
        HashMap<String, User> userCache = new HashMap<String, User>();
        for (DataVersion dataVersion : list) {
            DataVersionData dataVersionData = new DataVersionData();
            String createUser = dataVersion.getCreatUser();
            if (userCache.containsKey(createUser)) {
                User user = (User)userCache.get(createUser);
                if (user != null) {
                    dataVersionData.setCreatUser(user.getName());
                }
            } else {
                Optional find = this.userService.find(dataVersion.getCreatUser());
                User user = null;
                if (find.isPresent()) {
                    dataVersionData.setCreatUser(((User)find.get()).getName());
                    user = (User)find.get();
                } else {
                    Optional find2 = this.systemUserService.find(dataVersion.getCreatUser());
                    if (find2.isPresent()) {
                        dataVersionData.setCreatUser(((SystemUser)find2.get()).getName());
                        user = (User)find2.get();
                    }
                }
                userCache.put(createUser, user);
            }
            dataVersionData.init(dataVersion);
            resultList.add(dataVersionData);
        }
        return resultList;
    }

    @Override
    public ReturnInfo createOrUpdateDataVersion(DataVersionParam param, boolean open) throws JTableException {
        ReturnInfo returnInfo = new ReturnInfo();
        if (open) {
            if (!param.isEdit()) {
                List<DataVersion> dataVersions;
                String userId = NpContextHolder.getContext().getUserId();
                DataVersionImpl dataVersion = new DataVersionImpl();
                dataVersion.setAutoCreated(param.isAutoCreated());
                dataVersion.setCreatTime(new Date());
                dataVersion.setCreatUser(userId);
                dataVersion.setDescribe(param.getDescribe());
                dataVersion.setVersionId(param.getDataVersionId());
                dataVersion.setDimensionValueSet(this.getDimensionCombination(param.getContext().getDimensionSet()));
                if (param.isAutoCreated()) {
                    dataVersions = this.queryAllWithoutUserName(param.getContext());
                    int max = 1;
                    for (DataVersion haveDataVersion : dataVersions) {
                        String title;
                        int num;
                        if (!haveDataVersion.isAutoCreated() || (num = Integer.valueOf((title = haveDataVersion.getTitle()).substring(title.indexOf("V") + 1, title.length())).intValue()) < max) continue;
                        max = num + 1;
                    }
                    dataVersion.setTitle(param.getTitle() + "_V" + max);
                } else {
                    dataVersions = this.queryAllWithoutUserName(param.getContext());
                    for (DataVersion haveDataVersion : dataVersions) {
                        if (!param.getTitle().trim().equals(haveDataVersion.getTitle().trim())) continue;
                        returnInfo.setMessage("\u7248\u672c\u540d\u79f0\u4e0d\u80fd\u91cd\u590d\uff01");
                        return returnInfo;
                    }
                    dataVersion.setTitle(param.getTitle());
                }
                try {
                    dataVersion.setFormSchemeKey(param.getContext().getFormSchemeKey());
                    this.snapshotService.createVersion((DataVersion)dataVersion);
                    returnInfo.setMessage("success");
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    returnInfo.setMessage("\u751f\u6210\u6570\u636e\u7248\u672c\u5931\u8d25");
                }
            } else {
                List queryAll = null;
                try {
                    queryAll = this.snapshotService.queryVersion(this.getDimensionCombination(param.getContext().getDimensionSet()), param.getContext().getFormSchemeKey());
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
                param.getContext().getDimensionSet().remove("VERSIONID");
                List<DataVersionData> dataVersions = this.queryAll(param.getContext());
                for (DataVersionData haveDataVersion : dataVersions) {
                    if (!param.getTitle().trim().equals(haveDataVersion.getTitle().trim())) continue;
                    returnInfo.setMessage("\u7248\u672c\u540d\u79f0\u4e0d\u80fd\u91cd\u590d\uff01");
                    return returnInfo;
                }
                for (DataVersion dataVersion : queryAll) {
                    if (!param.getDataVersionId().equals(dataVersion.getVersionId())) continue;
                    DataVersionImpl updateDataVersion = (DataVersionImpl)dataVersion;
                    updateDataVersion.setTitle(param.getTitle());
                    updateDataVersion.setDescribe(param.getDescribe());
                    updateDataVersion.setFormSchemeKey(param.getContext().getFormSchemeKey());
                    try {
                        this.snapshotService.modifyVersion((DataVersion)updateDataVersion);
                        returnInfo.setMessage("success");
                    }
                    catch (Exception e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                        returnInfo.setMessage("\u4fee\u6539\u6570\u636e\u7248\u672c\u5931\u8d25");
                    }
                    break;
                }
            }
        } else {
            returnInfo.setMessage("\u6ca1\u6709\u542f\u7528\u6570\u636e\u7248\u672c");
        }
        return returnInfo;
    }

    @Override
    public ReturnInfo deleteDataVersion(DataVersionParam param) {
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            this.snapshotService.deleteVersion(param.getContext().getFormSchemeKey(), param.getDataVersionId());
            returnInfo.setMessage("success");
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            returnInfo.setMessage("\u5220\u9664\u6570\u636e\u7248\u672c\u5931\u8d25");
        }
        return returnInfo;
    }

    @Override
    public AsyncTaskInfo overwriteDataVersion(DataVersionParam param) {
        String asyncId = UUID.randomUUID().toString();
        SimpleAsyncProgressMonitor simpleAsyncProgressMonitor = new SimpleAsyncProgressMonitor(asyncId, this.cacheObjectResourceRemote);
        DataVersionImpl dataVersion = new DataVersionImpl();
        dataVersion.setDimensionValueSet(this.getDimensionCombination(param.getContext().getDimensionSet()));
        dataVersion.setVersionId(param.getDataVersionId());
        dataVersion.setFormSchemeKey(param.getContext().getFormSchemeKey());
        ArrayList<String> forms = new ArrayList<String>();
        String FormKeys = String.join((CharSequence)";", this.viewCtrl.queryAllFormKeysByFormScheme(param.getContext().getFormSchemeKey()));
        BatchDimensionValueFormInfo batchDimensionValueFormInfo = this.formGroupProvider.getForms(param.getContext(), FormKeys, Consts.FormAccessLevel.FORM_DATA_WRITE);
        List<DimensionValueFormInfo> acessFormInfos = batchDimensionValueFormInfo.getAccessFormInfos();
        for (int formInfoIndex = 0; formInfoIndex < acessFormInfos.size(); ++formInfoIndex) {
            DimensionValueFormInfo dimensionValueFormInfo = acessFormInfos.get(formInfoIndex);
            forms.addAll(dimensionValueFormInfo.getForms());
        }
        try {
            this.npApplication.asyncRun(() -> {
                try {
                    this.snapshotService.overwriteDefaultVersionOfFormList(forms, (DataVersion)dataVersion, (IMonitor)simpleAsyncProgressMonitor);
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            });
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return simpleAsyncProgressMonitor.getProgressInfo();
    }

    @Override
    public List<FormCompareDifference> compareDataVersion(DataVersionCompareParam param) {
        return this.snapshotCompare.compareVersionData(param.getContext().getDimensionSet(), param.getContext().getFormSchemeKey(), param.getFormKeys(), param.getInitialVersionId().toString(), param.getCompareVersionId().toString());
    }

    private DimensionCombination getDimensionCombination(Map<String, DimensionValue> dimensionSet) {
        DimensionCombinationBuilder builder = new DimensionCombinationBuilder(DimensionValueSetUtil.getDimensionValueSet(dimensionSet));
        return builder.getCombination();
    }
}

