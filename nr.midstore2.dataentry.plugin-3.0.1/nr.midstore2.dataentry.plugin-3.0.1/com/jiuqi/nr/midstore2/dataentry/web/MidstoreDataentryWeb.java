/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.midstore.core.definition.common.ExchangeModeType
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeDTO
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeInfoDTO
 *  com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSchemeInfoService
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  nr.midstore2.data.extension.bean.ReportDataSourceDTO
 *  nr.midstore2.data.param.IReportMidstoreSchemeQueryService
 *  nr.midstore2.data.util.IReportMidstoreDimensionService
 *  org.json.JSONException
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.midstore2.dataentry.web;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.midstore2.dataentry.asynctask.MidstorePullAsyncTaskExecutor;
import com.jiuqi.nr.midstore2.dataentry.bean.MidstoreDataentryParam;
import com.jiuqi.nr.midstore2.dataentry.bean.MidstoreParam;
import com.jiuqi.nr.midstore2.dataentry.service.IMidstoreService;
import com.jiuqi.nr.midstore2.dataentry.web.vo.FormVo;
import com.jiuqi.nr.midstore2.dataentry.web.vo.MidstoreDataentryVO;
import com.jiuqi.nr.midstore2.dataentry.web.vo.MidstoreResultVO;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.midstore.core.definition.common.ExchangeModeType;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeDTO;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeInfoDTO;
import com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSchemeInfoService;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import nr.midstore2.data.extension.bean.ReportDataSourceDTO;
import nr.midstore2.data.param.IReportMidstoreSchemeQueryService;
import nr.midstore2.data.util.IReportMidstoreDimensionService;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/midstore2"})
public class MidstoreDataentryWeb {
    private static final Logger logger = LoggerFactory.getLogger(MidstoreDataentryWeb.class);
    public static final String FLAG = ",";
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private IReportMidstoreSchemeQueryService nrMidstoreService;
    @Autowired
    private IMidstoreSchemeInfoService midstoreSchemeInfoService;
    @Autowired
    private IReportMidstoreDimensionService reportDimService;
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IMidstoreService midstoreService;
    @Autowired
    private IEntityMetaService entityMetaService;

    @PostMapping(value={"/midstore-pull-push"})
    @ApiOperation(value="\u4e2d\u95f4\u5e93\u53d6\u6570\u63a8\u6570")
    public AsyncTaskInfo midstorePull(@Valid @RequestBody MidstoreParam midstoreInfo) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(midstoreInfo.getContext().getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(midstoreInfo.getContext().getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)midstoreInfo));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new MidstorePullAsyncTaskExecutor());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        String asyncTaskId = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(asyncTaskId);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    private JSONObject getJson(String jsonStr) {
        try {
            return new JSONObject(jsonStr);
        }
        catch (JSONException e) {
            logger.warn("\u4e2d\u95f4\u5e93\u6309\u94ae\u53c2\u6570\u8f6cjson\u5f02\u5e38\uff1a\uff1a" + jsonStr, e);
            return null;
        }
    }

    private String getString(JSONObject json, String key) {
        if (json.has(key)) {
            return json.getString(key);
        }
        return null;
    }

    @PostMapping(value={"/checkParams"})
    @ApiOperation(value="\u68c0\u67e5\u5f55\u5165\u53c2\u6570")
    public MidstoreDataentryVO checkParams(@Valid @RequestBody MidstoreDataentryParam param) {
        MidstoreDataentryVO res = new MidstoreDataentryVO();
        String paramDef = param.getMidstoreCodes();
        if (!StringUtils.hasText(paramDef)) {
            res.setSuccess(false);
            res.setErrorMsg("\u6267\u884c\u4e2d\u95f4\u5e93\u6309\u94ae\u53c2\u6570\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5\uff01");
            return res;
        }
        TaskDefine taskDefine = this.viewController.queryTaskDefine(param.getTaskKey());
        String[] midstores = null;
        JSONObject json = this.getJson(paramDef);
        if (json == null) {
            midstores = paramDef.split(FLAG);
        } else {
            String dims;
            String org;
            String overImport;
            String midstoreScheme = this.getString(json, "midstoreScheme");
            if (!StringUtils.hasText(midstoreScheme)) {
                res.setSuccess(false);
                res.setErrorMsg("\u6267\u884c\u4e2d\u95f4\u5e93\u6309\u94ae\u53c2\u6570\u5fc5\u987b\u914d\u7f6e\u4e2d\u95f4\u5e93\u65b9\u6848\uff0c\u8bf7\u68c0\u67e5\uff01");
                return res;
            }
            midstores = midstoreScheme.split(FLAG);
            String form = this.getString(json, "form");
            if (StringUtils.hasText(form)) {
                if ("all".equals(form)) {
                    res.getFormMap().put("form", "all_form");
                } else if ("curr".equals(form)) {
                    res.getFormMap().put("form", "cur_form");
                } else if ("custom".equals(form)) {
                    res.getFormMap().put("form", "choose_form");
                } else {
                    String formScheme = param.getFormScheme();
                    if (!StringUtils.hasText(formScheme)) {
                        try {
                            SchemePeriodLinkDefine schemePeriodLinkDefine = this.viewController.querySchemePeriodLinkByPeriodAndTask(param.getPeriod(), taskDefine.getKey());
                            formScheme = schemePeriodLinkDefine.getSchemeKey();
                        }
                        catch (Exception e) {
                            logger.error("\u5f55\u5165\u4e2d\u95f4\u5e93\u6309\u94ae\u68c0\u67e5\u5f02\u5e38\uff1a\u4efb\u52a1=" + taskDefine.getKey() + ",\u65f6\u671f=" + param.getPeriod() + ",\u5f02\u5e38:" + e.getMessage(), e);
                        }
                    }
                    String[] formArr = form.split(FLAG);
                    ArrayList<FormVo> formVos = new ArrayList<FormVo>();
                    for (String f : formArr) {
                        FormDefine formDefine = null;
                        try {
                            formDefine = this.runTimeAuthViewController.queryFormByCodeInScheme(formScheme, f);
                        }
                        catch (Exception e) {
                            logger.error("\u5f55\u5165\u4e2d\u95f4\u5e93\u6309\u94ae\u68c0\u67e5\u5f02\u5e38\uff1a\u62a5\u8868=" + f + ",\u5f02\u5e38:" + e.getMessage(), e);
                            continue;
                        }
                        formVos.add(new FormVo(formDefine.getKey()));
                    }
                    res.getFormMap().put("form", "choose_form");
                    res.getFormMap().put("currSelection", formVos);
                }
            }
            if ("true".equalsIgnoreCase(overImport = this.getString(json, "overImport"))) {
                res.setOverImport(true);
            }
            if (StringUtils.hasText(org = this.getString(json, "MD_ORG"))) {
                if ("all".equals(org)) {
                    res.getEntityMap().put("MD_ORG", "allMD_ORG");
                } else if ("curr".equals(org)) {
                    res.getEntityMap().put("MD_ORG", "currentMD_ORG");
                } else if ("custom".equals(org)) {
                    res.getEntityMap().put("MD_ORG", "chooseMD_ORG");
                    res.getEntityMap().put("MD_ORGChooseNum", 0);
                } else {
                    String[] orgArr = org.split(FLAG);
                    res.getEntityMap().put("MD_ORG", "chooseMD_ORG");
                    res.getEntityMap().put("MD_ORGChooseNum", orgArr.length);
                    res.getEntityMap().put("MD_ORGChoose", orgArr);
                }
            }
            if (StringUtils.hasText(dims = taskDefine.getDims())) {
                String[] dimArr;
                for (String dim : dimArr = dims.split(";")) {
                    String dimName;
                    String dimValue;
                    IEntityDefine entity = this.entityMetaService.queryEntity(dim);
                    if (entity == null || !StringUtils.hasText(dimValue = this.getString(json, dimName = entity.getDimensionName()))) continue;
                    if ("all".equals(dimValue)) {
                        res.getEntityMap().put(dimName, "all" + dimName);
                        continue;
                    }
                    if ("curr".equals(dimValue)) {
                        res.getEntityMap().put(dimName, "current" + dimName);
                        continue;
                    }
                    if ("PROVIDER_BASECURRENCY".equals(dimValue)) {
                        res.getEntityMap().put(dimName, dimValue);
                        continue;
                    }
                    if ("custom".equals(dimValue)) {
                        res.getEntityMap().put(dimName, "choose" + dimName);
                        res.getEntityMap().put(dimName + "ChooseNum", 0);
                        continue;
                    }
                    String[] dimValueArr = dimValue.split(FLAG);
                    res.getEntityMap().put(dimName, "choose" + dimName);
                    res.getEntityMap().put(dimName + "ChooseNum", dimValueArr.length);
                    res.getEntityMap().put(dimName + "Choose", dimValueArr);
                }
            }
        }
        ArrayList<String> midstoreList = new ArrayList<String>(Arrays.asList(midstores));
        ExchangeModeType modeType = param.isPullData() ? ExchangeModeType.EXCHANGE_GET : ExchangeModeType.EXCHANGE_POST;
        String entityId = MidstoreDataentryWeb.getContentBeforeAt(param.getEntityId());
        List taskSchemes = this.nrMidstoreService.getSchemesByTask(param.getTaskKey());
        HashMap<String, String> keyToCode = new HashMap<String, String>();
        HashMap<String, MidstoreSchemeInfoDTO> infoMap = new HashMap<String, MidstoreSchemeInfoDTO>();
        ArrayList<String> taskSchemeCodes = new ArrayList<String>();
        for (MidstoreSchemeDTO dto : taskSchemes) {
            if (!midstoreList.contains(dto.getCode()) || dto.getExchangeMode() != modeType) continue;
            if (StringUtils.hasText(entityId)) {
                MidstoreSchemeInfoDTO info = this.midstoreSchemeInfoService.getBySchemeKey(dto.getKey());
                infoMap.put(dto.getKey(), info);
                if (!info.getOrgDataName().equals(entityId)) continue;
                taskSchemeCodes.add(dto.getCode());
                keyToCode.put(dto.getKey(), dto.getCode());
                continue;
            }
            taskSchemeCodes.add(dto.getCode());
            keyToCode.put(dto.getKey(), dto.getCode());
        }
        if (CollectionUtils.isEmpty(taskSchemeCodes)) {
            res.setSuccess(false);
            res.setErrorMsg("\u53c2\u6570\u914d\u7f6e\u9519\u8bef\uff1a\u6ca1\u6709\u5c5e\u4e8e\u5f53\u524d\u4efb\u52a1\u7684" + (modeType == ExchangeModeType.EXCHANGE_GET ? "\u53d6" : "\u63a8") + "\u6570\u4e2d\u95f4\u5e93\u65b9\u6848\uff01");
            return res;
        }
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime());
        List list = this.nrMidstoreService.getDataSoruceBySchemeCodes(taskSchemeCodes);
        for (ReportDataSourceDTO dto : list) {
            MidstoreSchemeInfoDTO info = infoMap.containsKey(dto.getSchemeKey()) ? (MidstoreSchemeInfoDTO)infoMap.get(dto.getSchemeKey()) : this.midstoreSchemeInfoService.getBySchemeKey(dto.getSchemeKey());
            String fromPeriod = this.reportDimService.getNrPeriodFromDe(taskDefine.getDateTime(), info.getFromPeriod());
            String toPeriod = this.reportDimService.getNrPeriodFromDe(taskDefine.getDateTime(), info.getToPeriod());
            if (!info.isAllPeriod() && (StringUtils.hasText(fromPeriod) && periodProvider.comparePeriod(fromPeriod, param.getPeriod()) > 0 || StringUtils.hasText(toPeriod) && periodProvider.comparePeriod(param.getPeriod(), toPeriod) > 0)) {
                taskSchemeCodes.remove(keyToCode.get(dto.getSchemeKey()));
            }
            if (dto.isUseDimensionField()) continue;
            res.setHasDimension(false);
        }
        if (CollectionUtils.isEmpty(taskSchemeCodes)) {
            res.setSuccess(false);
            res.setErrorMsg("\u53c2\u6570\u914d\u7f6e\u9519\u8bef\uff1a\u6ca1\u6709\u7b26\u5408\u5f53\u524d\u65f6\u671f\u7684" + (modeType == ExchangeModeType.EXCHANGE_GET ? "\u53d6" : "\u63a8") + "\u6570\u4e2d\u95f4\u5e93\u65b9\u6848\uff01");
            return res;
        }
        res.setMidstoreCodes(taskSchemeCodes);
        return res;
    }

    private static String getContentBeforeAt(String str) {
        if (str == null) {
            return null;
        }
        int atIndex = str.indexOf(64);
        if (atIndex != -1) {
            return str.substring(0, atIndex);
        }
        return str;
    }

    @PostMapping(value={"/export"})
    @ApiOperation(value="\u5bfc\u51faexcel")
    public void export(@Valid @RequestBody MidstoreResultVO result, HttpServletResponse response) throws JQException {
        this.midstoreService.exportResult(result, response);
    }
}

