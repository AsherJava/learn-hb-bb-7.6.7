/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.util.Guid
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.exception.TaskExsitsException
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.util.tree.Tree
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.dataentry.bean.AuthorityOptions
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 *  com.jiuqi.nr.dataentry.bean.FormsQueryResult
 *  com.jiuqi.nr.dataentry.tree.FormTree
 *  com.jiuqi.nr.dataentry.tree.FormTreeItem
 *  com.jiuqi.nr.dataentry.util.authUtil.CheckAuthOfResourceUtil
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormGroupService
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService
 *  com.jiuqi.nr.jtable.annotation.JLoggable
 *  com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.ResponseBody
 */
package com.jiuqi.nr.etl.web;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.util.Guid;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.exception.TaskExsitsException;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.util.tree.Tree;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.dataentry.bean.AuthorityOptions;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nr.dataentry.bean.FormsQueryResult;
import com.jiuqi.nr.dataentry.tree.FormTree;
import com.jiuqi.nr.dataentry.tree.FormTreeItem;
import com.jiuqi.nr.dataentry.util.authUtil.CheckAuthOfResourceUtil;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormGroupService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService;
import com.jiuqi.nr.etl.asynctask.ETLAsyncTaskExecutor;
import com.jiuqi.nr.etl.common.ETLConfigErrorEnum;
import com.jiuqi.nr.etl.common.EtlExecuteInfo;
import com.jiuqi.nr.etl.common.EtlExecuteParam;
import com.jiuqi.nr.etl.common.EtlInfo;
import com.jiuqi.nr.etl.common.EtlServeEntity;
import com.jiuqi.nr.etl.common.EtlTaskVO;
import com.jiuqi.nr.etl.common.ServeType;
import com.jiuqi.nr.etl.common.TreeNodeImpl;
import com.jiuqi.nr.etl.common.UniversalTask;
import com.jiuqi.nr.etl.service.IEtlOrNrdlService;
import com.jiuqi.nr.etl.service.internal.EtlServeEntityDao;
import com.jiuqi.nr.jtable.annotation.JLoggable;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@JQRestController
@RequestMapping(value={"/api/v1/etl/"})
@Api(tags={"ETL\u670d\u52a1\u8c03\u7528"})
public class ETLController {
    @Resource
    private IEtlOrNrdlService etlOrNrdlService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private EtlServeEntityDao etlServeEntityDao;
    @Autowired
    private IRuntimeFormGroupService formGroupService;
    @Autowired
    private IRuntimeFormService formService;
    @Autowired
    private CheckAuthOfResourceUtil checkAuthOfResourceUtil;
    @Autowired
    private SystemIdentityService systemIdentityService;

    @PostMapping(value={"forms"})
    public FormsQueryResult getAllForms(@RequestBody DataEntryContext context) {
        String schemeKey = context.getFormSchemeKey();
        List formGroupDefines = this.formGroupService.getAllFormGroupsInFormScheme(schemeKey);
        FormsQueryResult result = new FormsQueryResult();
        FormTree formTree = this.getFormTree(formGroupDefines);
        result.setTree(formTree);
        return result;
    }

    private FormTree getFormTree(List<FormGroupDefine> formGroupDefines) {
        FormTreeItem rootItem = new FormTreeItem();
        rootItem.setCode("root");
        Tree tree = new Tree((Object)rootItem);
        this.addChildren((Tree<FormTreeItem>)tree, formGroupDefines);
        FormTree formTree = new FormTree();
        formTree.setTree(tree);
        return formTree;
    }

    private Tree<FormTreeItem> addChildren(Tree<FormTreeItem> node, List<FormGroupDefine> formGroupDefines) {
        for (FormGroupDefine formGroup : formGroupDefines) {
            List formDefines = this.formService.getFormsInGroupOrderByGroupLink(formGroup.getKey(), false);
            if (formDefines.size() <= 0) continue;
            FormTreeItem groupItem = new FormTreeItem();
            groupItem.setKey(formGroup.getKey());
            groupItem.setCode(formGroup.getCode());
            groupItem.setTitle(formGroup.getTitle());
            groupItem.setType("group");
            Tree child = node.addChild((Object)groupItem);
            for (FormDefine form : formDefines) {
                FormTreeItem reportItem = new FormTreeItem();
                reportItem.setKey(form.getKey());
                reportItem.setCode(form.getFormCode());
                reportItem.setTitle(form.getTitle());
                reportItem.setSerialNumber(form.getSerialNumber());
                reportItem.setFormType(form.getFormType().toString());
                reportItem.setType("form");
                child.addChild((Object)reportItem);
            }
        }
        return node;
    }

    @GetMapping(value={"getAllTask"})
    public List<EtlTaskVO> getAllTask() throws JQException {
        ArrayList<EtlTaskVO> all = new ArrayList<EtlTaskVO>();
        List<? extends UniversalTask> allTask = this.etlOrNrdlService.getAllTask();
        for (UniversalTask universalTask : allTask) {
            all.add(new EtlTaskVO(universalTask.getTaskGuid(), universalTask.getTaskName()));
        }
        return all;
    }

    @GetMapping(value={"findTaskByName"})
    public UniversalTask findTaskByName(String taskName) {
        return this.etlOrNrdlService.findTaskByName(taskName);
    }

    @PostMapping(value={"executeETL"})
    public EtlExecuteInfo executeETL(@RequestBody EtlExecuteParam param) throws JQException {
        return this.etlOrNrdlService.execute(param);
    }

    @RequestMapping(value={"/getTreeNodes"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1\u53ca\u5176\u4e0b\u5c5e\u62a5\u8868\u65b9\u6848")
    public List<TreeNodeImpl> getTreeNodes() throws JQException {
        List<TreeNodeImpl> treeNodes = null;
        try {
            treeNodes = this.etlOrNrdlService.getReportTask();
            if (treeNodes.size() <= 0) {
                throw new JQException((ErrorEnum)ETLConfigErrorEnum.ETL_TREE_NODE_ERROR_ENUM);
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ETLConfigErrorEnum.ETL_TREE_NODE_ERROR_ENUM);
        }
        return treeNodes;
    }

    @JLoggable(value="\u67e5\u8be2\u6743\u9650")
    @RequestMapping(value={"checkETLAuth"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u6743\u9650")
    public Boolean checkETLAuth(@RequestBody String taskKey) throws InterruptedException {
        return this.checkAuthOfResourceUtil.checkResourceAuthOfType(taskKey, AuthorityOptions.ETL);
    }

    @JLoggable(value="ETL\u53d6\u6570")
    @RequestMapping(value={"etl-fetch"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="ETL\u53d6\u6570")
    @NRContextBuild
    public AsyncTaskInfo batchCalculateForm(@RequestBody EtlInfo etlInfo) throws InterruptedException {
        boolean hasAuth = this.checkAuthOfResourceUtil.checkResourceAuthOfType(etlInfo.getTaskKey(), AuthorityOptions.ETL);
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        if (!hasAuth) {
            asyncTaskInfo.setId("");
            asyncTaskInfo.setProcess(Double.valueOf(0.0));
            asyncTaskInfo.setResult("");
            asyncTaskInfo.setDetail((Object)"noAuth");
            asyncTaskInfo.setState(TaskState.ERROR);
            asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        } else {
            String masterKey = "";
            masterKey = masterKey + "etl" + etlInfo.getTaskKey() + etlInfo.getFormKey() + etlInfo.getFormSchemeKey() + etlInfo.getUnitIds();
            Map<String, DimensionValue> dimensionSet = etlInfo.getDimensionSet();
            for (String dimensionSetKey : dimensionSet.keySet()) {
                masterKey = masterKey + dimensionSetKey + dimensionSet.get(dimensionSetKey);
            }
            UUID tofakeUUID = ETLController.tofakeUUID(masterKey);
            String guid = Guid.newGuid();
            try {
                NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
                npRealTimeTaskInfo.setTaskKey(etlInfo.getContext().getTaskKey());
                npRealTimeTaskInfo.setFormSchemeKey(etlInfo.getContext().getFormSchemeKey());
                npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)((Object)etlInfo)));
                npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new ETLAsyncTaskExecutor());
                String asynTaskID = this.asyncTaskManager.publishUniqueTask(npRealTimeTaskInfo, AsynctaskPoolType.ASYNCTASK_ETL.getName(), guid);
                asyncTaskInfo.setId(asynTaskID);
                asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
            }
            catch (TaskExsitsException e) {
                asyncTaskInfo.setId(e.getTaskId());
                asyncTaskInfo.setState(TaskState.ERROR);
                asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
                asyncTaskInfo.setResult("\u60a8\u521b\u5efa\u7684\u4efb\u52a1\u5df2\u7ecf\u5728\u7b49\u5f85\u6216\u6267\u884c\u4e2d\u3002");
            }
        }
        return asyncTaskInfo;
    }

    public static UUID tofakeUUID(String string) {
        int i;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException nsae) {
            throw new InternalError("MD5 not supported", nsae);
        }
        byte[] data = md.digest(string.getBytes());
        long msb = 0L;
        long lsb = 0L;
        assert (data.length == 16) : "data must be 16 bytes in length";
        for (i = 0; i < 8; ++i) {
            msb = msb << 8 | (long)(data[i] & 0xFF);
        }
        for (i = 8; i < 16; ++i) {
            lsb = lsb << 8 | (long)(data[i] & 0xFF);
        }
        return new UUID(msb, lsb);
    }

    @RequiresPermissions(value={"\u62a5\u8868:ETL:\u670d\u52a1\u914d\u7f6e"})
    @RequestMapping(value={"etl-serve"}, method={RequestMethod.GET})
    public EtlServeEntity getServe() {
        EtlServeEntity etlServeEntity = this.etlServeEntityDao.getServerInfo().orElse(null);
        if (etlServeEntity != null) {
            etlServeEntity.setPwd(null);
        }
        return etlServeEntity;
    }

    @RequiresPermissions(value={"\u62a5\u8868:ETL:\u670d\u52a1\u914d\u7f6e"})
    @RequestMapping(value={"etl-serve-save"}, method={RequestMethod.POST})
    @Transactional(rollbackFor={Exception.class})
    public Map<String, Object> saveServe(@RequestBody @SFDecrypt EtlServeEntity serve) {
        Map<String, Object> res = this.testServe(serve);
        if (((Boolean)res.get("status")).booleanValue()) {
            this.etlServeEntityDao.delete();
            serve.setCreateTime(Instant.now());
            serve.setCreateUser(NpContextHolder.getContext().getUserId());
            serve.setType(ServeType.valueOf((Integer)res.get("type")));
            this.etlServeEntityDao.save(serve);
            res.put("message", "\u4fdd\u5b58\u6210\u529f");
        }
        return res;
    }

    @RequestMapping(value={"etl-serve-test"}, method={RequestMethod.POST})
    public Map<String, Object> testServe(@RequestBody @SFDecrypt EtlServeEntity serve) {
        HashMap<String, Object> res = new HashMap<String, Object>();
        String url = serve.getAddress();
        String userName = serve.getUserName();
        String pwd = serve.getPwd();
        String identityId = NpContextHolder.getContext().getIdentityId();
        if (this.systemIdentityService.isSystemByUserId(identityId)) {
            int flag = this.etlOrNrdlService.testLink(url, userName, pwd);
            if (flag == 1) {
                res.put("status", true);
                res.put("message", "ETL\u670d\u52a1\u8fde\u63a5\u6210\u529f");
                res.put("type", 1);
            } else if (flag == 2) {
                res.put("status", true);
                res.put("message", "\u6570\u636e\u96c6\u6210\u670d\u52a1\u8fde\u63a5\u6210\u529f");
                res.put("type", 2);
            } else {
                res.put("status", false);
                res.put("message", "\u670d\u52a1\u8fde\u63a5\u5931\u8d25");
                res.put("type", 1);
            }
        } else {
            res.put("status", false);
            res.put("message", "\u8bf7\u4f7f\u7528\u7ba1\u7406\u5458\u7528\u6237\u64cd\u4f5c\uff01");
            res.put("type", 1);
        }
        return res;
    }
}

