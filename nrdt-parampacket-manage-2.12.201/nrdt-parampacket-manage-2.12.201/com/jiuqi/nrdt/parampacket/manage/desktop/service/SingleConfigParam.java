/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IExportContext
 *  com.jiuqi.bi.transfer.engine.intf.IImportContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.configuration.controller.IBusinessConfigurationController
 *  com.jiuqi.nr.configuration.facade.BusinessConfigurationDefine
 *  com.jiuqi.nr.configuration.internal.impl.BusinessConfigurationDefineImpl
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.param.transfer.definition.spi.TaskTransfer
 *  org.jdom2.Attribute
 *  org.jdom2.Element
 */
package com.jiuqi.nrdt.parampacket.manage.desktop.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IExportContext;
import com.jiuqi.bi.transfer.engine.intf.IImportContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.configuration.controller.IBusinessConfigurationController;
import com.jiuqi.nr.configuration.facade.BusinessConfigurationDefine;
import com.jiuqi.nr.configuration.internal.impl.BusinessConfigurationDefineImpl;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.param.transfer.definition.spi.TaskTransfer;
import com.jiuqi.nrdt.parampacket.manage.desktop.exception.ExceptionEnum;
import com.jiuqi.nrdt.parampacket.manage.desktop.service.TaskService;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.jdom2.Attribute;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="singleConfigParam")
public class SingleConfigParam
implements TaskTransfer {
    private static final Logger log = LoggerFactory.getLogger(SingleConfigParam.class);
    @Autowired
    private IBusinessConfigurationController configurationController;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private TaskService taskService;
    ObjectMapper mapper = new ObjectMapper();
    public static final String SINGLE_CONFIG_ID = "87dbefff-2dca-4172-bbab-9e316ff45cff";
    public static final String JIOPARA = "JIOPARA";
    public static final String JIOANAL = "JIOANAL";
    public static final String IMPORTEXPORT = "IMPORTEXPORT";
    public static final String IMPORTEXPORTINI = "IMPORTEXPORT.INI";
    public static final String JIOQUERY = "JIOQUERY";
    public static final String TASKSIGN_TSK = "TASKSIGN.TSK";
    public static final String FMDM_JQT = "FMDM.JQT";
    public static final String MJTKFG_JQX = "MJTKFG.JQX";

    public String getId() {
        return SINGLE_CONFIG_ID;
    }

    public byte[] exportTaskData(IExportContext iExportContext, String taskKey) throws TransferException {
        try {
            TaskDefine taskDefine = this.runTimeAuthViewController.queryTaskDefine(taskKey);
            if (taskDefine == null) {
                throw new JQException((ErrorEnum)ExceptionEnum.UNIVERSAL, "\u4efb\u52a1\u3010" + taskKey + "\u3011\u4e0d\u5b58\u5728");
            }
            List configurations = this.configurationController.getConfigurationByTaskWithoutContent(taskDefine.getKey());
            List<BusinessConfigurationDefine> collect = configurations.stream().filter(e -> JIOPARA.equals(e.getCategory()) && (IMPORTEXPORT.equals(e.getCode()) || IMPORTEXPORTINI.equals(e.getCode()))).collect(Collectors.toList());
            this.addContent(collect);
            BusinessConfigurationDefine sign = this.configurationController.getConfiguration(taskDefine.getKey(), TASKSIGN_TSK);
            collect.add(sign);
            BusinessConfigurationDefine fmdm = this.configurationController.getConfiguration(taskDefine.getKey(), FMDM_JQT);
            collect.add(fmdm);
            List<FormSchemeDefine> formSchemeDefines = this.taskService.queryFormSchemeInTask(taskKey);
            for (FormSchemeDefine fromScheme : formSchemeDefines) {
                BusinessConfigurationDefine mjtkfg = this.configurationController.getConfiguration(taskDefine.getKey(), fromScheme.getKey(), MJTKFG_JQX);
                if (mjtkfg == null) continue;
                collect.add(mjtkfg);
            }
            log.info("\u5355\u673a\u7248\u914d\u7f6e\u52a0\u8f7d\u5b8c\u6210\u3002");
            byte[] bytes = this.mapper.writeValueAsBytes(collect);
            log.info("\u5355\u673a\u7248\u914d\u7f6e\u5bfc\u51fa\u6210\u529f\u3002");
            return bytes;
        }
        catch (Exception e2) {
            log.info("\u4efb\u52a1\u914d\u7f6e\u53c2\u6570\u6570\u636e\u5bfc\u51fa\u5f02\u5e38\uff1a" + e2.getMessage());
            return new byte[0];
        }
    }

    public void importTaskData(IImportContext context, String taskKey, byte[] data) throws TransferException {
        try {
            log.info("\u5355\u673a\u7248\u914d\u7f6e\u53c2\u6570\u5bfc\u5165\u5f00\u59cb\u3002");
            JavaType javaType = this.mapper.getTypeFactory().constructParametricType(List.class, new Class[]{BusinessConfigurationDefineImpl.class});
            List configurations = (List)this.mapper.readValue(data, javaType);
            log.info("\u5355\u673a\u7248\u914d\u7f6e\u53c2\u6570\u89e3\u6790\u8f6c\u6362\u6210\u529f\u3002");
            try {
                int size = 0;
                for (BusinessConfigurationDefine configurationDefine : configurations) {
                    try {
                        if (configurationDefine == null) {
                            log.warn("\u5355\u673a\u7248\u914d\u7f6e\u52a0\u8f7d\u5f02\u5e38\uff1a\u5355\u673a\u7248\u914d\u7f6e\u4e0d\u5b58\u5728");
                            continue;
                        }
                        BusinessConfigurationDefine configuration = this.configurationController.getConfiguration(configurationDefine.getTaskKey(), configurationDefine.getFormSchemeKey(), configurationDefine.getCode());
                        if (configuration != null) {
                            this.configurationController.updateConfiguration(configurationDefine);
                        } else {
                            this.configurationController.addConfiguration(configurationDefine);
                        }
                        ++size;
                    }
                    catch (Exception e) {
                        log.error("\u5355\u673a\u7248\u914d\u7f6e\u52a0\u8f7d\u5f02\u5e38\uff1a" + e.getMessage(), e);
                    }
                }
                log.info("\u5355\u673a\u7248\u914d\u7f6e\u53c2\u6570\u6570\u636e\u5bfc\u5165\u6210\u529f\uff0c\u5bfc\u5165\u6210\u529f\u6761\u6570\uff1a" + size);
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
                log.info("\u5355\u673a\u7248\u914d\u7f6e\u53c2\u6570\u5bfc\u5165\u5f02\u5e38\uff1a " + e.getMessage());
            }
        }
        catch (IOException e) {
            log.info("\u4efb\u52a1\u914d\u7f6e\u53c2\u6570\u5bfc\u5165\u5f02\u5e38\uff1a " + e.getMessage());
        }
    }

    private void addContent(List<BusinessConfigurationDefine> configurationDefines) {
        for (BusinessConfigurationDefine define : configurationDefines) {
            String configurationContent = this.configurationController.getConfigurationContent(define.getTaskKey(), define.getFormSchemeKey(), define.getCode());
            define.setConfigurationContent(configurationContent);
        }
    }

    public void toDocumentTaskExtra(String taskKey, Element element) {
        log.info("xml\u6302\u8f7d\u5355\u673a\u7248\u914d\u7f6e");
        try {
            TaskDefine taskDefine = this.runTimeAuthViewController.queryTaskDefine(taskKey);
            if (taskDefine == null) {
                throw new JQException((ErrorEnum)ExceptionEnum.UNIVERSAL, "\u4efb\u52a1\u3010" + taskKey + "\u3011\u4e0d\u5b58\u5728");
            }
            BusinessConfigurationDefine singleConfig = this.configurationController.getConfiguration(taskDefine.getKey(), TASKSIGN_TSK);
            String singConfig = this.mapper.writeValueAsString((Object)singleConfig);
            element.setAttribute("singleConfig", singConfig);
            log.info("xml\u6302\u8f7d\u5355\u673a\u7248\u914d\u7f6e\u5b8c\u6210");
        }
        catch (Exception e) {
            log.info("xml\u6302\u8f7d\u5355\u673a\u7248\u914d\u7f6e\u51fa\u9519\uff1a " + e.getMessage());
        }
    }

    public Object loadBusinessTaskExtra(Element element) {
        log.info("\u8bfb\u53d6xml\u5355\u673a\u7248\u914d\u7f6e");
        try {
            Attribute singleConfigAtr = element.getAttribute("singleConfig");
            if (singleConfigAtr == null) {
                return null;
            }
            String singleConfigAtrValue = singleConfigAtr.getValue();
            if (StringUtils.isEmpty((String)singleConfigAtrValue)) {
                return null;
            }
            log.info("\u8bfb\u53d6xml\u5355\u673a\u7248\u914d\u7f6e\u5b8c\u6210");
            return this.mapper.readValue(singleConfigAtrValue, BusinessConfigurationDefineImpl.class);
        }
        catch (Exception e) {
            log.info("\u8bfb\u53d6xml\u5355\u673a\u7248\u914d\u7f6e\u5931\u8d25");
            return null;
        }
    }
}

