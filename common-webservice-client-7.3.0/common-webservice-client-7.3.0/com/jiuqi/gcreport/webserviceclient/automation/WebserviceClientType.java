/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.common.automation.annotation.CommonAutomation
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.api.enums.AuthType
 *  com.jiuqi.gcreport.basedata.impl.service.GcBaseDataService
 *  com.jiuqi.nvwa.core.automation.annotation.AutomationType
 *  com.jiuqi.nvwa.core.automation.annotation.ExecuteOperation
 *  com.jiuqi.nvwa.core.automation.annotation.MetaOperation
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.framework.automation.api.AutomationFieldInfo
 *  com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo
 *  com.jiuqi.nvwa.framework.automation.api.AutomationParameter
 *  com.jiuqi.nvwa.framework.automation.api.AutomationParameterOption
 *  com.jiuqi.nvwa.framework.automation.bean.ExecuteContext
 *  com.jiuqi.nvwa.framework.automation.enums.AutomationParameterDataTypeEnum
 *  com.jiuqi.nvwa.framework.automation.exception.AutomationException
 *  com.jiuqi.nvwa.framework.automation.exception.AutomationExecuteException
 *  com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker
 *  com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker
 *  com.jiuqi.nvwa.framework.automation.result.JsonResult
 */
package com.jiuqi.gcreport.webserviceclient.automation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.common.automation.annotation.CommonAutomation;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.api.enums.AuthType;
import com.jiuqi.gcreport.basedata.impl.service.GcBaseDataService;
import com.jiuqi.gcreport.webserviceclient.service.WebserviceClientService;
import com.jiuqi.gcreport.webserviceclient.task.WebserviceClientExecuter;
import com.jiuqi.gcreport.webserviceclient.vo.WebserviceClientParam;
import com.jiuqi.nvwa.core.automation.annotation.AutomationType;
import com.jiuqi.nvwa.core.automation.annotation.ExecuteOperation;
import com.jiuqi.nvwa.core.automation.annotation.MetaOperation;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.framework.automation.api.AutomationFieldInfo;
import com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo;
import com.jiuqi.nvwa.framework.automation.api.AutomationParameter;
import com.jiuqi.nvwa.framework.automation.api.AutomationParameterOption;
import com.jiuqi.nvwa.framework.automation.bean.ExecuteContext;
import com.jiuqi.nvwa.framework.automation.enums.AutomationParameterDataTypeEnum;
import com.jiuqi.nvwa.framework.automation.exception.AutomationException;
import com.jiuqi.nvwa.framework.automation.exception.AutomationExecuteException;
import com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker;
import com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker;
import com.jiuqi.nvwa.framework.automation.result.JsonResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@AutomationType(category="gcreport", id="webservice-client-type", title="WS\u5ba2\u6237\u7aef", icon="icon-64-xxx")
@CommonAutomation(path="/\u6570\u636e\u5f55\u5165")
public class WebserviceClientType {
    private Logger logger = LoggerFactory.getLogger(WebserviceClientType.class);
    @Autowired
    private GcBaseDataService baseDataService;
    @Autowired
    private WebserviceClientService webserviceClientService;

    @ExecuteOperation
    public IOperationInvoker<JsonResult> invoke() {
        return (instance, context) -> {
            try {
                WebserviceClientParam webserviceClientParam = this.getWebserviceClientParam(context);
                ArrayList<String> taskLogs = new ArrayList<String>();
                new WebserviceClientExecuter().executeWebservicClient(webserviceClientParam, taskLogs);
                this.logger.info(StringUtils.join((Object[])taskLogs.toArray(), (String)";\n"));
                return new JsonResult(new ObjectMapper().writeValueAsString((Object)context.getParameterMap()));
            }
            catch (Exception e) {
                this.logger.error("WS\u5ba2\u6237\u7aef\u81ea\u52a8\u5316\u6267\u884c\u5f02\u5e38\uff1a" + e.getMessage(), e);
                throw new AutomationExecuteException((Throwable)e);
            }
        };
    }

    private WebserviceClientParam getWebserviceClientParam(ExecuteContext context) throws AutomationException {
        String periodStr = context.getParameterValue("DATATIME");
        String orgCodes = context.getParameterValue("UNITCODE");
        List<String> orgCodeList = Arrays.asList(orgCodes.split(";"));
        if (CollectionUtils.isEmpty(orgCodeList)) {
            throw new BusinessRuntimeException("\u672a\u914d\u7f6e\u5355\u4f4d\u4ee3\u7801");
        }
        String wsClientCode = context.getParameterValue("wsClientCode");
        WebserviceClientParam webserviceClientParam = new WebserviceClientParam();
        webserviceClientParam.setPeriodString(periodStr);
        webserviceClientParam.setOrgCodeList(orgCodeList);
        webserviceClientParam.setWsClientBaseDataCode(wsClientCode);
        this.logger.info("WS\u5ba2\u6237\u7aef\u81ea\u52a8\u5316\u5bf9\u8c61\u6267\u884c\u53c2\u6570\uff1a{}", (Object)JsonUtils.writeValueAsString((Object)webserviceClientParam));
        return webserviceClientParam;
    }

    @MetaOperation
    public IMetaInvoker metaOperation() {
        return instance -> {
            AutomationMetaInfo metaInfo = new AutomationMetaInfo();
            ArrayList<AutomationFieldInfo> fieldInfoList = new ArrayList<AutomationFieldInfo>();
            fieldInfoList.add(new AutomationFieldInfo("DATATIME", "\u65f6\u671f", Integer.valueOf(6)));
            fieldInfoList.add(new AutomationFieldInfo("UNITCODE", "\u5355\u4f4d", Integer.valueOf(6)));
            metaInfo.setFieldInfoList(fieldInfoList);
            List parameterList = fieldInfoList.stream().map(fieldInfo -> new AutomationParameter(fieldInfo.getName(), fieldInfo.getTitle(), AutomationParameterDataTypeEnum.STRING, null, true)).collect(Collectors.toList());
            metaInfo.setParameterList(parameterList);
            TableModelDefine clientBaseData = this.webserviceClientService.getWsClientBaseData("MD_WS_CLIENT");
            String wsClientTitle = clientBaseData.getTitle() + "\u4ee3\u7801";
            fieldInfoList.add(new AutomationFieldInfo("wsClientCode", wsClientTitle, Integer.valueOf(6)));
            AutomationParameter wsClientParam = new AutomationParameter("wsClientCode", wsClientTitle, AutomationParameterDataTypeEnum.STRING, null, true);
            List wsBaseDatas = this.baseDataService.queryBasedataItems("MD_WS_CLIENT", AuthType.NONE);
            List parameterOptions = wsBaseDatas.stream().map(baseData -> new AutomationParameterOption(baseData.getCode(), baseData.getTitle())).collect(Collectors.toList());
            wsClientParam.setOptions(parameterOptions);
            parameterList.add(wsClientParam);
            return metaInfo;
        };
    }
}

