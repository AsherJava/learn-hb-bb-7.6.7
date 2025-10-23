/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintComTemDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintSettingDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.paramcheck.IDesignParamCheckService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.print.service.Impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintComTemDefine;
import com.jiuqi.nr.definition.facade.DesignPrintSettingDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.paramcheck.IDesignParamCheckService;
import com.jiuqi.nr.print.common.PrintSchemeMoveType;
import com.jiuqi.nr.print.helper.PrintSchemeHelper;
import com.jiuqi.nr.print.service.IPrintSchemeService;
import com.jiuqi.nr.print.web.vo.PrintAttributeVo;
import com.jiuqi.nr.print.web.vo.PrintSchemeVo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class PrintSchemeServiceImpl
implements IPrintSchemeService {
    private static final String DEFAULT_PRINTSCHEME_TITLE = "\u9ed8\u8ba4\u6253\u5370\u65b9\u6848";
    @Autowired
    private IDesignTimePrintController printController;
    @Autowired
    private PrintSchemeHelper schemeHelper;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IDesignParamCheckService paramCheckService;

    @Override
    public PrintSchemeVo getDefaultPrintScheme(String taskKey, String fromSchemeKey) {
        List allPrintSchemes = this.printController.listPrintTemplateSchemeByFormScheme(fromSchemeKey);
        if (allPrintSchemes != null && !allPrintSchemes.isEmpty()) {
            return PrintSchemeVo.toPrintSchemeVo((DesignPrintTemplateSchemeDefine)allPrintSchemes.get(0));
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void initDefaultPrintScheme(String taskKey, String fromSchemeKey) {
        DesignPrintTemplateSchemeDefine designPrintSchemeDefine = this.printController.initPrintTemplateScheme();
        designPrintSchemeDefine.setTitle(DEFAULT_PRINTSCHEME_TITLE);
        designPrintSchemeDefine.setFormSchemeKey(fromSchemeKey);
        designPrintSchemeDefine.setTaskKey(taskKey);
        this.printController.setPrintSchemeAttribute(designPrintSchemeDefine, PrintAttributeVo.defaultAttributeDefine());
        this.printController.insertPrintTemplateScheme(designPrintSchemeDefine);
    }

    @Override
    public List<PrintSchemeVo> listPrintSchemeByFormScheme(String formSchemeKey) {
        ArrayList<PrintSchemeVo> printSchemeVos = new ArrayList<PrintSchemeVo>();
        List printSchemes = this.printController.listPrintTemplateSchemeByFormScheme(formSchemeKey);
        if (printSchemes != null && !printSchemes.isEmpty()) {
            printSchemes.forEach(printScheme -> printSchemeVos.add(PrintSchemeVo.toPrintSchemeVo(printScheme)));
        }
        return printSchemeVos;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String insertPrintScheme(PrintSchemeVo printSchemeVo) {
        DesignPrintTemplateSchemeDefine printSchemeDefine = this.printController.initPrintTemplateScheme();
        DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(printSchemeVo.getFormSchemeKey());
        printSchemeDefine.setTitle(printSchemeVo.getTitle());
        printSchemeDefine.setTaskKey(formScheme.getTaskKey());
        printSchemeDefine.setFormSchemeKey(printSchemeVo.getFormSchemeKey());
        printSchemeDefine.setDescription(printSchemeVo.getDesc());
        this.printController.setPrintSchemeAttribute(printSchemeDefine, PrintAttributeVo.defaultAttributeDefine());
        this.printController.insertPrintTemplateScheme(printSchemeDefine);
        return printSchemeDefine.getKey();
    }

    @Override
    public void updatePrintScheme(PrintSchemeVo printSchemeVo) {
        DesignPrintTemplateSchemeDefine printSchemeDefine = this.printController.getPrintTemplateScheme(printSchemeVo.getKey());
        printSchemeDefine.setTitle(printSchemeVo.getTitle());
        printSchemeDefine.setDescription(printSchemeVo.getDesc());
        printSchemeDefine.setUpdateTime(new Date());
        this.printController.updatePrintTemplateScheme(printSchemeDefine);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String copyPrintScheme(PrintSchemeVo printSchemeVo) {
        String targetPrintSchemeKey = this.copyPrintScheme(printSchemeVo.getKey(), printSchemeVo.getTitle());
        this.copyPrintComTem(printSchemeVo.getKey(), targetPrintSchemeKey);
        this.copyPrintTemplate(printSchemeVo.getKey(), targetPrintSchemeKey);
        this.copyPrintSetting(printSchemeVo.getKey(), targetPrintSchemeKey);
        return targetPrintSchemeKey;
    }

    private void copyPrintComTem(String printSchemeKey, String targetPrintSchemeKey) {
        List comTemDefines = this.printController.listPrintComTemByScheme(printSchemeKey);
        ArrayList<DesignPrintComTemDefine> targetComTemDefines = new ArrayList<DesignPrintComTemDefine>();
        for (DesignPrintComTemDefine comTemDefine : comTemDefines) {
            targetComTemDefines.add(this.printController.copyPrintComTem(comTemDefine, targetPrintSchemeKey));
        }
        this.printController.insertPrintComTem(targetComTemDefines);
    }

    private void copyPrintSetting(String printSchemeKey, String targetPrintSchemeKey) {
        List defines = this.printController.listPrintSettingDefine(printSchemeKey);
        if (defines != null && !defines.isEmpty()) {
            for (DesignPrintSettingDefine define : defines) {
                define.setPrintSchemeKey(targetPrintSchemeKey);
            }
            this.printController.insertPrintSettingDefine(defines);
        }
    }

    private String copyPrintScheme(String printSchemeKey, String printSchemeTitle) {
        String targetPrintSchemeKey = UUIDUtils.getKey();
        DesignPrintTemplateSchemeDefine printSchemeDefine = this.printController.getPrintTemplateScheme(printSchemeKey);
        printSchemeDefine.getCommonAttribute();
        printSchemeDefine.getGatherCoverData();
        printSchemeDefine.setKey(targetPrintSchemeKey);
        if (!StringUtils.hasLength(printSchemeTitle)) {
            printSchemeDefine.setTitle(this.schemeHelper.getCopyPrintSchemeTitle(printSchemeDefine.getTitle()));
        } else {
            printSchemeDefine.setTitle(printSchemeTitle);
        }
        printSchemeDefine.setUpdateTime(new Date());
        printSchemeDefine.setOrder(OrderGenerator.newOrder());
        this.printController.insertPrintTemplateScheme(printSchemeDefine);
        return targetPrintSchemeKey;
    }

    private void copyPrintTemplate(String originPrintSchemeKey, String targetPrintSchemeKey) {
        List sourceTemplates = this.printController.listPrintTemplateByScheme(originPrintSchemeKey);
        if (sourceTemplates != null && !sourceTemplates.isEmpty()) {
            DesignPrintTemplateDefine[] targetTemplates = new DesignPrintTemplateDefine[sourceTemplates.size()];
            for (int i = 0; i < targetTemplates.length; ++i) {
                DesignPrintTemplateDefine source = (DesignPrintTemplateDefine)sourceTemplates.get(i);
                source.getTemplateData();
                source.getLabelData();
                source.setKey(UUIDUtils.getKey());
                source.setPrintSchemeKey(targetPrintSchemeKey);
                targetTemplates[i] = source;
            }
            this.printController.insertPrintTemplate(targetTemplates);
        }
    }

    @Override
    public void deletePrintScheme(String printSchemeKey) {
        DesignPrintTemplateSchemeDefine printTemplateScheme = this.printController.getPrintTemplateScheme(printSchemeKey);
        List designPrintTemplateSchemeDefines = this.printController.listPrintTemplateSchemeByFormScheme(printTemplateScheme.getFormSchemeKey());
        if (!CollectionUtils.isEmpty(designPrintTemplateSchemeDefines) && designPrintTemplateSchemeDefines.size() == 1 && ((DesignPrintTemplateSchemeDefine)designPrintTemplateSchemeDefines.get(0)).getKey().equals(printSchemeKey)) {
            throw new RuntimeException("\u4e0d\u5141\u8bb8\u5220\u9664\u6240\u6709\u6253\u5370\u65b9\u6848");
        }
        this.printController.deletePrintTemplateByScheme(printSchemeKey);
        this.printController.deletePrintTemplateScheme(new String[]{printSchemeKey});
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void exchangeOrder(String sourceKey, String targetKey) {
        DesignPrintTemplateSchemeDefine orinScheme = this.printController.getPrintTemplateScheme(sourceKey);
        DesignPrintTemplateSchemeDefine targetScheme = this.printController.getPrintTemplateScheme(targetKey);
        if (null != orinScheme && null != targetScheme) {
            String oldOrder = orinScheme.getOrder();
            orinScheme.setOrder(targetScheme.getOrder());
            orinScheme.setUpdateTime(new Date());
            targetScheme.setOrder(oldOrder);
            targetScheme.setUpdateTime(new Date());
            this.printController.updatePrintTemplateScheme(orinScheme);
            this.printController.updatePrintTemplateScheme(targetScheme);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deletePrintSchemeByFormScheme(String formSchemeKey) {
        List printSchemes = this.printController.listPrintTemplateSchemeByFormScheme(formSchemeKey);
        if (printSchemes != null) {
            for (DesignPrintTemplateSchemeDefine printScheme : printSchemes) {
                this.deletePrintScheme(printScheme.getKey());
            }
        }
    }

    @Override
    public void printSchemeMove(String sourceKey, String formSchemeKey, PrintSchemeMoveType moveType) {
        List schemes = this.printController.listPrintTemplateSchemeByFormScheme(formSchemeKey);
        String targetSchemeKey = "";
        for (int i = 0; i < schemes.size(); ++i) {
            if (!((DesignPrintTemplateSchemeDefine)schemes.get(i)).getKey().equals(sourceKey)) continue;
            if (PrintSchemeMoveType.MOVE_UP.equals((Object)moveType)) {
                targetSchemeKey = ((DesignPrintTemplateSchemeDefine)schemes.get(i - 1)).getKey();
                break;
            }
            targetSchemeKey = ((DesignPrintTemplateSchemeDefine)schemes.get(i + 1)).getKey();
            break;
        }
        if (StringUtils.hasText(targetSchemeKey)) {
            this.exchangeOrder(sourceKey, targetSchemeKey);
        }
    }

    @Override
    public void deleteTemplateByPrintScheme(String printSchemeKey) {
        this.printController.deletePrintTemplateByScheme(printSchemeKey);
    }

    @Override
    public void checkTitle(PrintSchemeVo printSchemeVo) {
        DesignPrintTemplateSchemeDefine printScheme = this.printController.getPrintTemplateScheme(printSchemeVo.getKey());
        if (printScheme != null) {
            printScheme.setTitle(printSchemeVo.getTitle());
        } else {
            printScheme = this.printController.initPrintTemplateScheme();
            printScheme.setTitle(printSchemeVo.getTitle());
            printScheme.setFormSchemeKey(printSchemeVo.getFormSchemeKey());
        }
        this.paramCheckService.checkPrintTemplateScheme(printScheme);
    }
}

