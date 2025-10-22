/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.controller.IPrintDesignTimeController
 *  com.jiuqi.nr.definition.facade.DesignPrintComTemDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintSettingDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.designer.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.controller.IPrintDesignTimeController;
import com.jiuqi.nr.definition.facade.DesignPrintComTemDefine;
import com.jiuqi.nr.definition.facade.DesignPrintSettingDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.designer.service.IPrintSchemeService;
import com.jiuqi.nr.designer.web.rest.vo.PrintAttributeVo;
import com.jiuqi.nr.designer.web.rest.vo.PrintSchemeVo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrintSchemeServiceImpl
implements IPrintSchemeService {
    private static final String DEFAULT_PRINTSCHEME_TITLE = "\u9ed8\u8ba4\u6253\u5370\u65b9\u6848";
    @Autowired
    private IPrintDesignTimeController printController;
    @Autowired
    private IDesignTimePrintController designTimePrintController;

    @Override
    public PrintSchemeVo queryDefaultPrintScheme(String taskKey, String fromSchemeKey) throws Exception {
        List allPrintSchemes = this.printController.getAllPrintSchemeByFormScheme(fromSchemeKey);
        if (allPrintSchemes != null && !allPrintSchemes.isEmpty()) {
            return PrintSchemeVo.toPrintSchemeVo((DesignPrintTemplateSchemeDefine)allPrintSchemes.get(0));
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String createDefaultPrintScheme(String taskKey, String fromSchemeKey) throws Exception {
        DesignPrintTemplateSchemeDefine designPirntSchemeDefine = this.printController.createPrintTemplateSchemeDefine();
        designPirntSchemeDefine.setTitle(DEFAULT_PRINTSCHEME_TITLE);
        designPirntSchemeDefine.setOrder(OrderGenerator.newOrder());
        designPirntSchemeDefine.setFormSchemeKey(fromSchemeKey);
        designPirntSchemeDefine.setTaskKey(taskKey);
        this.printController.setPrintSchemeAttribute(designPirntSchemeDefine, PrintAttributeVo.defaultAttributeDefine());
        return this.printController.insertPrintTemplateSchemeDefine(designPirntSchemeDefine);
    }

    @Override
    public List<PrintSchemeVo> queryPrintSchemes(String formSchemeKey) throws Exception {
        ArrayList<PrintSchemeVo> printSchemeVos = new ArrayList<PrintSchemeVo>();
        List printSchemes = this.printController.getAllPrintSchemeByFormScheme(formSchemeKey);
        if (printSchemes != null && !printSchemes.isEmpty()) {
            printSchemes.forEach(printScheme -> printSchemeVos.add(PrintSchemeVo.toPrintSchemeVo(printScheme)));
        }
        return printSchemeVos;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String addPrintScheme(PrintSchemeVo printSchemeVo) throws Exception {
        DesignPrintTemplateSchemeDefine printSchemeDefine = this.printController.createPrintTemplateSchemeDefine();
        printSchemeDefine.setTitle(printSchemeVo.getTitle());
        printSchemeDefine.setTaskKey(printSchemeVo.getTaskKey());
        printSchemeDefine.setFormSchemeKey(printSchemeVo.getFormSchemeKey());
        printSchemeDefine.setOrder(OrderGenerator.newOrder());
        return this.printController.insertPrintTemplateSchemeDefine(printSchemeDefine);
    }

    @Override
    public void updatePrintScheme(PrintSchemeVo printSchemeVo) throws Exception {
        DesignPrintTemplateSchemeDefine printSchemeDefine = this.printController.queryPrintTemplateSchemeDefine(printSchemeVo.getKey());
        printSchemeDefine.setTitle(printSchemeVo.getTitle());
        printSchemeDefine.setUpdateTime(new Date());
        this.printController.updatePrintTemplateSchemeDefine(printSchemeDefine);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String copyPrintScheme(PrintSchemeVo printSchemeVo) throws Exception {
        List settings;
        String targetPrintSchemeKey = UUIDUtils.getKey();
        DesignPrintTemplateSchemeDefine printSchemeDefine = this.printController.queryPrintTemplateSchemeDefine(printSchemeVo.getKey());
        printSchemeDefine.setKey(targetPrintSchemeKey);
        printSchemeDefine.setTitle(printSchemeVo.getTitle());
        printSchemeDefine.setOrder(OrderGenerator.newOrder());
        this.printController.insertPrintTemplateSchemeDefine(printSchemeDefine);
        List sourceTemplates = this.printController.queryAllPrintTemplate(printSchemeVo.getKey());
        List comTemDefines = this.designTimePrintController.listPrintComTemByScheme(printSchemeVo.getKey());
        ArrayList<DesignPrintComTemDefine> targetComTemDefines = new ArrayList<DesignPrintComTemDefine>();
        for (DesignPrintComTemDefine comTemDefine : comTemDefines) {
            targetComTemDefines.add(this.designTimePrintController.copyPrintComTem(comTemDefine, targetPrintSchemeKey));
        }
        this.designTimePrintController.insertPrintComTem(targetComTemDefines);
        if (sourceTemplates != null && !sourceTemplates.isEmpty()) {
            DesignPrintTemplateDefine[] targetTemplates = new DesignPrintTemplateDefine[sourceTemplates.size()];
            for (int i = 0; i < targetTemplates.length; ++i) {
                DesignPrintTemplateDefine source = (DesignPrintTemplateDefine)sourceTemplates.get(i);
                source.setKey(UUIDUtils.getKey());
                source.setPrintSchemeKey(targetPrintSchemeKey);
                targetTemplates[i] = source;
            }
            this.printController.insertTemplates(targetTemplates);
        }
        if ((settings = this.designTimePrintController.listPrintSettingDefine(printSchemeVo.getKey())) != null && !settings.isEmpty()) {
            for (DesignPrintSettingDefine setting : settings) {
                setting.setPrintSchemeKey(targetPrintSchemeKey);
            }
            this.designTimePrintController.insertPrintSettingDefine(settings);
        }
        return targetPrintSchemeKey;
    }

    @Override
    public void deletePrintScheme(String printSchemeKey) throws Exception {
        this.printController.deletePrintTemplateDefineByScheme(printSchemeKey);
        this.printController.deletePrintTemplateSchemeDefine(printSchemeKey);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void exchangeOrder(String sourceKey, String targetKey) throws Exception {
        this.printController.exchangePrintTemplateSchemeOrder(sourceKey, targetKey);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteAllPrintSchemeByTask(String taskKey) throws Exception {
        List printSchemes = this.printController.getAllPrintSchemeByTask(taskKey);
        if (printSchemes != null) {
            for (DesignPrintTemplateSchemeDefine printScheme : printSchemes) {
                this.deletePrintScheme(printScheme.getKey());
            }
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteAllPrintSchemeByFormScheme(String formSchemeKey) throws Exception {
        List printSchemes = this.printController.getAllPrintSchemeByFormScheme(formSchemeKey);
        if (printSchemes != null) {
            for (DesignPrintTemplateSchemeDefine printScheme : printSchemes) {
                this.deletePrintScheme(printScheme.getKey());
            }
        }
    }

    @Override
    public void deleteTempleteByPrintScheme(String printSchemeKey) throws Exception {
        this.printController.deletePrintTemplateDefineByScheme(printSchemeKey);
    }
}

