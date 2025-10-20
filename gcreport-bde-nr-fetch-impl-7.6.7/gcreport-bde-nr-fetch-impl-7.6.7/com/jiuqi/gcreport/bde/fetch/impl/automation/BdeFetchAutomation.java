/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.common.automation.annotation.CommonAutomation
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nvwa.core.automation.annotation.AutomationType
 *  com.jiuqi.nvwa.core.automation.annotation.ExecuteOperation
 *  com.jiuqi.nvwa.core.automation.annotation.MetaOperation
 *  com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo
 *  com.jiuqi.nvwa.framework.automation.api.AutomationParameter
 *  com.jiuqi.nvwa.framework.automation.bean.ExecuteContext
 *  com.jiuqi.nvwa.framework.automation.enums.AutomationValueResultDataTypeEnum
 *  com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker
 *  com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker
 *  com.jiuqi.nvwa.framework.automation.result.ValueResult
 */
package com.jiuqi.gcreport.bde.fetch.impl.automation;

import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.common.automation.annotation.CommonAutomation;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.bde.fetch.impl.automation.enums.FetchAutomationParameterEnum;
import com.jiuqi.gcreport.bde.fetch.impl.automation.intf.FetchAutomationParameterDTO;
import com.jiuqi.gcreport.bde.fetch.impl.automation.intf.FetchAutomationResult;
import com.jiuqi.gcreport.bde.fetch.impl.automation.service.impl.BatchFetchAutomationServiceImpl;
import com.jiuqi.gcreport.bde.fetch.impl.automation.service.impl.FetchAutomationServiceImpl;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nvwa.core.automation.annotation.AutomationType;
import com.jiuqi.nvwa.core.automation.annotation.ExecuteOperation;
import com.jiuqi.nvwa.core.automation.annotation.MetaOperation;
import com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo;
import com.jiuqi.nvwa.framework.automation.api.AutomationParameter;
import com.jiuqi.nvwa.framework.automation.bean.ExecuteContext;
import com.jiuqi.nvwa.framework.automation.enums.AutomationValueResultDataTypeEnum;
import com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker;
import com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker;
import com.jiuqi.nvwa.framework.automation.result.ValueResult;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;

@AutomationType(category="gcreport", id="gcreport-bde-fetch", title="BDE\u53d6\u6570", icon="icon-64-xxx")
@CommonAutomation(path="/BDE")
public class BdeFetchAutomation {
    private final Logger LOGGER = LoggerFactory.getLogger(BdeFetchAutomation.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private BatchFetchAutomationServiceImpl batchFetchService;
    @Autowired
    private FetchAutomationServiceImpl fetchService;

    @ExecuteOperation
    public IOperationInvoker<ValueResult> automationFetch() {
        return (instance, context) -> {
            FetchAutomationParameterDTO parameter = null;
            try {
                parameter = this.parseParameter(context);
            }
            catch (Exception e) {
                this.LOGGER.error("ETL\u81ea\u52a8\u5316\u5bf9\u8c61\u53c2\u6570\u89e3\u6790\u5931\u8d25\uff0c\u8be6\u7ec6\u4fe1\u606f:{}", (Object)e.getMessage(), (Object)e);
                FetchAutomationResult result = new FetchAutomationResult().failure("ETL\u81ea\u52a8\u5316\u5bf9\u8c61\u53c2\u6570\u89e3\u6790\u5931\u8d25\uff0c\u8be6\u7ec6\u4fe1\u606f:" + e.getMessage());
                return new ValueResult((Object)JsonUtils.writeValueAsString((Object)result), AutomationValueResultDataTypeEnum.STRING);
            }
            try {
                FetchAutomationResult result = parameter.getUnitCode().contains(";") ? this.batchFetchService.doFetch(parameter) : this.fetchService.doFetch(parameter);
                return new ValueResult((Object)JsonUtils.writeValueAsString((Object)result), AutomationValueResultDataTypeEnum.STRING);
            }
            catch (Exception e) {
                this.LOGGER.error("\u7528\u6237\u3010{}\u3011ETL\u81ea\u52a8\u5316\u5bf9\u8c61\u53d6\u6570\u6267\u884c\u5931\u8d25\uff0c\u8be6\u7ec6\u4fe1\u606f:{}", parameter.getUser(), e.getMessage(), e);
                FetchAutomationResult fetchLog = new FetchAutomationResult().failure("ETL\u81ea\u52a8\u5316\u5bf9\u8c61\u53d6\u6570\u6267\u884c\u5931\u8d25\uff0c\u8be6\u7ec6\u4fe1\u606f:" + e.getMessage());
                return new ValueResult((Object)JsonUtils.writeValueAsString((Object)fetchLog), AutomationValueResultDataTypeEnum.STRING);
            }
        };
    }

    private FetchAutomationParameterDTO parseParameter(ExecuteContext context) {
        FetchAutomationParameterDTO parameter = BdeFetchAutomation.parseBasicParameter(context);
        BdeCommonUtil.initNpUser((String)parameter.getUser());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(parameter.getTaskKey());
        Assert.isNotNull((Object)taskDefine, (String)String.format("\u6839\u636e\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u7684\u62a5\u8868\u4efb\u52a1", parameter.getTaskKey()), (Object[])new Object[0]);
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(parameter.getSchemeKey());
        Assert.isNotNull((Object)formScheme, (String)String.format("\u6839\u636e\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u7684\u62a5\u8868\u65b9\u6848", parameter.getSchemeKey()), (Object[])new Object[0]);
        return parameter;
    }

    private static FetchAutomationParameterDTO parseBasicParameter(ExecuteContext context) {
        FetchAutomationParameterDTO parameter = new FetchAutomationParameterDTO();
        for (FetchAutomationParameterEnum parameterEnum : FetchAutomationParameterEnum.values()) {
            PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(parameter.getClass(), parameterEnum.getName());
            if (propertyDescriptor == null) {
                throw new FatalBeanException("Could not read property '" + parameterEnum.getName() + "' from " + parameter.getClass());
            }
            Method writeMethod = propertyDescriptor.getWriteMethod();
            if (writeMethod == null) {
                throw new FatalBeanException("Could not find writeMethod '" + parameterEnum.getName() + "' from " + parameter.getClass());
            }
            try {
                writeMethod.invoke(parameter, parameterEnum.parseVal(context));
            }
            catch (Exception ex) {
                throw new FatalBeanException("Could not read property '" + propertyDescriptor.getName() + "' from " + parameter.getClass(), ex);
            }
        }
        return parameter;
    }

    @MetaOperation
    public IMetaInvoker meta() {
        return instance -> {
            AutomationMetaInfo metaInfo = new AutomationMetaInfo();
            metaInfo.setParameterList(Arrays.stream(FetchAutomationParameterEnum.values()).map(parameter -> new AutomationParameter(parameter.getName(), parameter.getTitle(), parameter.getDataType(), parameter.getDefaultValue())).collect(Collectors.toList()));
            metaInfo.setFieldInfoList(new ArrayList());
            return metaInfo;
        };
    }
}

