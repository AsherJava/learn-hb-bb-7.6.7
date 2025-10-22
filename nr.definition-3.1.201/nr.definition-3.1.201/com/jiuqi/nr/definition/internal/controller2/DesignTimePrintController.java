/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.util.OrderGenerator
 *  com.jiuqi.xg.process.ITemplateDocument
 *  com.jiuqi.xg.process.ITemplateElement
 *  com.jiuqi.xg.process.ITemplateObject
 *  com.jiuqi.xg.process.ITemplateObjectFactory
 *  com.jiuqi.xg.process.ITemplatePage
 *  com.jiuqi.xg.process.obj.ImageTemplateObject
 *  com.jiuqi.xg.process.obj.PageTemplateObject
 *  com.jiuqi.xg.process.util.SerializeUtil
 *  com.jiuqi.xlib.resource.IResource
 *  com.jiuqi.xlib.resource.IResourceManager
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.definition.internal.controller2;

import com.jiuqi.grid.GridData;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum2;
import com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintComTemDefine;
import com.jiuqi.nr.definition.facade.DesignPrintSettingDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateDefine;
import com.jiuqi.nr.definition.facade.print.DataTransformUtil;
import com.jiuqi.nr.definition.facade.print.PrintSchemeAttributeDefine;
import com.jiuqi.nr.definition.facade.print.PrintTemplateAttributeDefine;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.definition.facade.print.common.other.PrintUtil;
import com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject;
import com.jiuqi.nr.definition.internal.impl.DesignPrintSettingDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignPrintTemplateDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignPrintTemplateSchemeDefineImpl;
import com.jiuqi.nr.definition.internal.impl.print.PrintSchemeAttributeDefineImpl;
import com.jiuqi.nr.definition.internal.impl.print.PrintTemplateAttributeDefineImpl;
import com.jiuqi.nr.definition.internal.impl.print.WordLabelDefineImpl;
import com.jiuqi.nr.definition.internal.service.DesignBigDataService;
import com.jiuqi.nr.definition.internal.service.DesignPrintComTemDefineService;
import com.jiuqi.nr.definition.internal.service.DesignPrintSettingDefineService;
import com.jiuqi.nr.definition.internal.service.DesignPrintTemplateDefineService;
import com.jiuqi.nr.definition.internal.service.DesignPrintTemplateSchemeDefineService;
import com.jiuqi.nr.definition.paramcheck.IDesignParamCheckService;
import com.jiuqi.nr.definition.print.common.PrintElementUtils;
import com.jiuqi.nr.definition.util.ParamPackage;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.util.OrderGenerator;
import com.jiuqi.xg.process.ITemplateDocument;
import com.jiuqi.xg.process.ITemplateElement;
import com.jiuqi.xg.process.ITemplateObject;
import com.jiuqi.xg.process.ITemplateObjectFactory;
import com.jiuqi.xg.process.ITemplatePage;
import com.jiuqi.xg.process.obj.ImageTemplateObject;
import com.jiuqi.xg.process.obj.PageTemplateObject;
import com.jiuqi.xg.process.util.SerializeUtil;
import com.jiuqi.xlib.resource.IResource;
import com.jiuqi.xlib.resource.IResourceManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DesignTimePrintController
implements IDesignTimePrintController {
    private static final Logger logger = LoggerFactory.getLogger(DesignTimePrintController.class);
    @Autowired
    private DesignPrintComTemDefineService printComTemDefineService;
    @Autowired
    private DesignPrintTemplateDefineService printService;
    @Autowired
    private DesignPrintTemplateSchemeDefineService printSchemeService;
    @Autowired
    private DesignBigDataService designBigDataService;
    @Autowired
    private IDesignParamCheckService iDesignParamCheckService;
    @Autowired
    private DesignPrintSettingDefineService designPrintSettingDefineService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;

    @Override
    public DesignPrintTemplateSchemeDefine initPrintTemplateScheme() {
        DesignPrintTemplateSchemeDefineImpl designPrintTemplateSchemeDefine = new DesignPrintTemplateSchemeDefineImpl();
        designPrintTemplateSchemeDefine.setKey(UUIDUtils.getKey());
        designPrintTemplateSchemeDefine.setUpdateTime(new Date());
        designPrintTemplateSchemeDefine.setOrder(OrderGenerator.newOrder());
        return designPrintTemplateSchemeDefine;
    }

    @Override
    public void insertPrintTemplateScheme(DesignPrintTemplateSchemeDefine printTemplateSchemeDefine) {
        try {
            DesignPrintTemplateSchemeDefine designPrintTemplateSchemeDefines = ParamPackage.convertPrintScheme(printTemplateSchemeDefine);
            this.iDesignParamCheckService.checkPrintTemplateScheme(designPrintTemplateSchemeDefines);
            this.printSchemeService.insertPrintTemplateSchemeDefine(designPrintTemplateSchemeDefines);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_SCHEME_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_SCHEME_INSERT, (Throwable)e);
        }
    }

    @Override
    public void updatePrintTemplateScheme(DesignPrintTemplateSchemeDefine printTemplateSchemeDefine) {
        try {
            DesignPrintTemplateSchemeDefine designPrintTemplateSchemeDefines = ParamPackage.convertPrintScheme(printTemplateSchemeDefine);
            this.iDesignParamCheckService.checkPrintTemplateScheme(designPrintTemplateSchemeDefines);
            this.printSchemeService.updatePrintTemplateSchemeDefine(designPrintTemplateSchemeDefines);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_SCHEME_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_SCHEME_UPDATE, (Throwable)e);
        }
    }

    @Override
    @Transactional
    public void deletePrintTemplateScheme(String[] keys) {
        try {
            for (String key : keys) {
                this.deletePrintTemplateByScheme(key);
                this.printComTemDefineService.deleteByScheme(key);
                this.designPrintSettingDefineService.delete(key);
            }
            this.printSchemeService.delete(keys);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_SCHEME_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_SCHEME_DELETE, (Throwable)e);
        }
    }

    @Override
    public List<DesignPrintTemplateSchemeDefine> listAllPrintTemplateScheme() {
        try {
            return ParamPackage.packagePrintScheme(this.printSchemeService.listAllPrintTemplateScheme(), this.designBigDataService);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_SCHEME_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_SCHEME_QUERY, (Throwable)e);
        }
    }

    @Override
    public DesignPrintTemplateSchemeDefine getPrintTemplateScheme(String key) {
        try {
            return ParamPackage.packagePrintScheme(this.printSchemeService.getPrintTemplateScheme(key), this.designBigDataService);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_SCHEME_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_SCHEME_QUERY, (Throwable)e);
        }
    }

    @Override
    public List<DesignPrintTemplateSchemeDefine> listPrintTemplateSchemeByFormScheme(String formScheme) {
        try {
            return ParamPackage.packagePrintScheme(this.printSchemeService.getAllPrintSchemeByFormScheme(formScheme, false), this.designBigDataService);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_SCHEME_QUERY.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_SCHEME_QUERY, (Throwable)e);
        }
    }

    @Override
    public void insertCommonAttribute(String printScheme, PrintSchemeAttributeDefine attributeDefine) {
        try {
            this.updateCommonAttribute(printScheme, attributeDefine);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_SCHEME_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_SCHEME_UPDATE, (Throwable)e);
        }
    }

    @Override
    public void updateCommonAttribute(String printScheme, PrintSchemeAttributeDefine attributeDefine) {
        try {
            byte[] data = DataTransformUtil.serializeToByteArray(attributeDefine);
            this.designBigDataService.updateBigDataDefine(printScheme, "PRINTS_ATTR_DATA", data);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_SCHEME_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_SCHEME_UPDATE, (Throwable)e);
        }
    }

    @Override
    public void deleteCommonAttribute(String printScheme) {
        try {
            this.designBigDataService.deleteBigDataDefine(printScheme, "PRINTS_ATTR_DATA");
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_SCHEME_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_SCHEME_DELETE, (Throwable)e);
        }
    }

    @Override
    public PrintSchemeAttributeDefine getPrintSchemeAttribute(DesignPrintTemplateSchemeDefine printScheme) {
        PrintSchemeAttributeDefine define = null;
        if (null != printScheme && null != printScheme.getCommonAttribute() && printScheme.getCommonAttribute().length > 0) {
            define = DataTransformUtil.deserialize(printScheme.getCommonAttribute(), PrintSchemeAttributeDefine.class);
        } else {
            define = new PrintSchemeAttributeDefineImpl();
            for (WordLabelDefine label : PrintUtil.getDefaultWordLabels()) {
                define.getWordLabels().add(label);
            }
        }
        return define;
    }

    @Override
    public void setPrintSchemeAttribute(DesignPrintTemplateSchemeDefine printScheme, PrintSchemeAttributeDefine define) {
        if (null != define) {
            printScheme.setCommonAttribute(DataTransformUtil.serializeToByteArray(define));
        } else {
            printScheme.setCommonAttribute(null);
        }
    }

    @Override
    public DesignPrintTemplateDefine initPrintTemplate() {
        DesignPrintTemplateDefineImpl designPrintTemplateDefine = new DesignPrintTemplateDefineImpl();
        designPrintTemplateDefine.setKey(UUIDUtils.getKey());
        designPrintTemplateDefine.setUpdateTime(new Date());
        designPrintTemplateDefine.setOrder(OrderGenerator.newOrder());
        return designPrintTemplateDefine;
    }

    @Override
    public void insertPrintTemplate(DesignPrintTemplateDefine printTemplateDefine) {
        try {
            DesignPrintTemplateDefine designPrintTemplateDefine = ParamPackage.convertPrintTemplate(printTemplateDefine);
            this.printService.insertTemplate(designPrintTemplateDefine);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_TEMPLATE_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_INSERT, (Throwable)e);
        }
    }

    @Override
    public void updatePrintTemplate(DesignPrintTemplateDefine printTemplateDefine) {
        try {
            DesignPrintTemplateDefine designPrintTemplateDefine = ParamPackage.convertPrintTemplate(printTemplateDefine);
            this.printService.updateTemplate(designPrintTemplateDefine);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_TEMPLATE_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_UPDATE, (Throwable)e);
        }
    }

    @Override
    public void insertPrintTemplate(DesignPrintTemplateDefine[] printTemplateDefines) {
        try {
            DesignPrintTemplateDefine[] designPrintTemplateDefines = ParamPackage.convertPrintTemplate(printTemplateDefines);
            this.printService.insertTemplates(designPrintTemplateDefines);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_TEMPLATE_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_INSERT, (Throwable)e);
        }
    }

    @Override
    public void updatePrintTemplate(DesignPrintTemplateDefine[] printTemplateDefines) {
        try {
            DesignPrintTemplateDefine[] designPrintTemplateDefines = ParamPackage.convertPrintTemplate(printTemplateDefines);
            this.printService.updateTemplates(designPrintTemplateDefines);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_TEMPLATE_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_UPDATE, (Throwable)e);
        }
    }

    @Override
    @Transactional
    public void deletePrintTemplate(String[] keys) {
        try {
            this.printService.delete(keys);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_TEMPLATE_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_DELETE, (Throwable)e);
        }
    }

    @Override
    @Transactional
    public void deletePrintTemplateByScheme(String printScheme) {
        try {
            List<DesignPrintTemplateDefine> printTemplateDefines = this.listPrintTemplateByScheme(printScheme);
            String[] strings = (String[])printTemplateDefines.stream().map(IBaseMetaItem::getKey).toArray(String[]::new);
            this.deletePrintTemplate(strings);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_TEMPLATE_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_DELETE, (Throwable)e);
        }
    }

    @Override
    @Transactional
    public void deletePrintTemplateBySchemeAndForm(String printScheme, String formKey) {
        try {
            DesignPrintTemplateDefine printTemplateBySchemeAndForm = this.getPrintTemplateBySchemeAndForm(printScheme, formKey);
            if (null != printTemplateBySchemeAndForm) {
                this.deletePrintTemplate(new String[]{printTemplateBySchemeAndForm.getKey()});
            }
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_TEMPLATE_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_DELETE, (Throwable)e);
        }
    }

    @Override
    public void deletePrintTemplateByForm(String ... formKey) {
        try {
            this.printService.deleteByForm(formKey);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_TEMPLATE_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_DELETE, (Throwable)e);
        }
    }

    @Override
    public DesignPrintTemplateDefine getPrintTemplate(String key) {
        try {
            return ParamPackage.packagePrintTemplate(this.printService.queryPrintTemplateDefine(key, false), this.designBigDataService);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_TEMPLATE_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_DELETE, (Throwable)e);
        }
    }

    @Override
    public DesignPrintTemplateDefine getPrintTemplateBySchemeAndForm(String printScheme, String formKey) {
        try {
            return ParamPackage.packagePrintTemplate(this.printService.getPrintTemplateBySchemeAndForm(printScheme, formKey), this.designBigDataService);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_TEMPLATE_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_DELETE, (Throwable)e);
        }
    }

    @Override
    public List<DesignPrintTemplateDefine> listPrintTemplateByForm(String formKey) {
        try {
            return ParamPackage.packagePrintTemplate(this.printService.listPrintTemplateByForm(formKey), this.designBigDataService);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_TEMPLATE_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_DELETE, (Throwable)e);
        }
    }

    @Override
    public List<DesignPrintTemplateDefine> listPrintTemplateByScheme(String printScheme) {
        try {
            return ParamPackage.packagePrintTemplate(this.printService.listPrintTemplateByScheme(printScheme), this.designBigDataService);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_TEMPLATE_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_DELETE, (Throwable)e);
        }
    }

    @Override
    public DesignPrintComTemDefine getPrintComTem(String key) {
        return this.printComTemDefineService.getByKey(key);
    }

    @Override
    public DesignPrintComTemDefine getPrintComTemBySchemeAndCode(String printScheme, String code) {
        return this.printComTemDefineService.getBySchemeAndCode(printScheme, code);
    }

    @Override
    public List<DesignPrintComTemDefine> listPrintComTemByScheme(String printScheme) {
        return this.printComTemDefineService.listByScheme(printScheme);
    }

    @Override
    public List<DesignPrintComTemDefine> listPrintComTemBySchemeWithoutBigData(String printScheme) {
        return this.printComTemDefineService.listBySchemeWithoutBigData(printScheme);
    }

    @Override
    public void deletePrintComTem(String key) {
        try {
            this.printComTemDefineService.deleteByKey(key);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_TEMPLATE_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_DELETE, (Throwable)e);
        }
    }

    @Override
    public void deletePrintComTemByScheme(String printScheme) {
        try {
            this.printComTemDefineService.deleteByScheme(printScheme);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_TEMPLATE_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_DELETE, (Throwable)e);
        }
    }

    @Override
    public void deletePrintComTemBySchemeAndCode(String printScheme, String code) {
        try {
            this.printComTemDefineService.deleteBySchemeAndCode(printScheme, code);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_TEMPLATE_DELETE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_DELETE, (Throwable)e);
        }
    }

    @Override
    public void insertPrintComTem(DesignPrintComTemDefine define) {
        this.checkPrintComTem(Collections.singletonList(define));
        try {
            this.printComTemDefineService.insert(define);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_TEMPLATE_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_INSERT, (Throwable)e);
        }
    }

    @Override
    public void insertPrintComTem(List<DesignPrintComTemDefine> defines) {
        if (null == defines || defines.isEmpty()) {
            return;
        }
        this.checkPrintComTem(defines);
        try {
            this.printComTemDefineService.insert(defines.toArray(new DesignPrintComTemDefine[0]));
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_TEMPLATE_INSERT.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_INSERT, (Throwable)e);
        }
    }

    private void checkPrintComTem(List<DesignPrintComTemDefine> defines) {
        if (null == defines || defines.isEmpty()) {
            return;
        }
        HashSet<String> printSchemeKeys = new HashSet<String>();
        for (DesignPrintComTemDefine designPrintComTemDefine : defines) {
            if (designPrintComTemDefine.isDefault()) continue;
            printSchemeKeys.add(designPrintComTemDefine.getPrintSchemeKey());
        }
        if (printSchemeKeys.isEmpty()) {
            return;
        }
        HashSet<String> formSchemeKeys = new HashSet<String>();
        for (String printSchemeKey : printSchemeKeys) {
            DesignPrintTemplateSchemeDefine scheme = this.printSchemeService.queryPrintTemplateSchemeDefine(printSchemeKey, false);
            formSchemeKeys.add(scheme.getFormSchemeKey());
        }
        List<DesignFormSchemeDefine> list = this.designTimeViewController.listFormScheme(formSchemeKeys);
        for (DesignFormSchemeDefine formScheme : list) {
            DesignTaskDefine task = this.designTimeViewController.getTask(formScheme.getTaskKey());
            if (!"1.0".equals(task.getVersion())) continue;
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_INSERT, "\u4efb\u52a1\u8bbe\u8ba11.0\u6253\u5370\u4e0d\u652f\u6301\u591a\u6bcd\u7248\uff0c\u8bf7\u5347\u7ea7\u53c2\u6570\u7248\u672c");
        }
    }

    @Override
    public void updatePrintComTem(DesignPrintComTemDefine define) {
        try {
            this.printComTemDefineService.update(define);
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_TEMPLATE_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_UPDATE, (Throwable)e);
        }
    }

    @Override
    public void updatePrintComTem(List<DesignPrintComTemDefine> defines) {
        if (null == defines || defines.isEmpty()) {
            return;
        }
        try {
            this.printComTemDefineService.update(defines.toArray(new DesignPrintComTemDefine[0]));
        }
        catch (Exception e) {
            logger.error(NrDefinitionErrorEnum2.PRINT_TEMPLATE_UPDATE.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_UPDATE, (Throwable)e);
        }
    }

    @Override
    public boolean existCoverTemplateDocument(String printSchemeKey) {
        DesignPrintTemplateSchemeDefine scheme = this.getPrintTemplateScheme(printSchemeKey);
        return null != scheme && null != scheme.getGatherCoverData() && 0 < scheme.getGatherCoverData().length;
    }

    @Override
    public ITemplateDocument initCoverTemplateDocument(String printSchemeKey) {
        return PrintElementUtils.newTemplateDocument();
    }

    @Override
    public ITemplateDocument getCoverTemplateDocument(String printSchemeKey) {
        DesignPrintTemplateSchemeDefine scheme = this.getPrintTemplateScheme(printSchemeKey);
        if (null != scheme.getGatherCoverData() && scheme.getGatherCoverData().length > 0) {
            return PrintElementUtils.toTemplateDocument(scheme.getGatherCoverData());
        }
        return PrintElementUtils.newTemplateDocument();
    }

    @Override
    public void updateCoverTemplateDocument(String printSchemeKey, ITemplateDocument document) {
        DesignPrintTemplateSchemeDefine scheme = this.getPrintTemplateScheme(printSchemeKey);
        scheme.setGatherCoverData(PrintElementUtils.toByteArray(document));
        scheme.setUpdateTime(new Date());
        this.updatePrintTemplateScheme(scheme);
    }

    @Override
    public ITemplateDocument initCommonTemplateDocument(String printSchemeKey) {
        DesignPrintTemplateSchemeDefine scheme = this.getPrintTemplateScheme(printSchemeKey);
        if (null == scheme) {
            return null;
        }
        return PrintElementUtils.newTemplateDocument(this.getPrintSchemeAttribute(scheme));
    }

    @Override
    public ITemplateDocument getCommonTemplateDocument(String printSchemeKey) {
        DesignPrintComTemDefine define = this.printComTemDefineService.getByKey(printSchemeKey);
        if (null == define) {
            DesignPrintTemplateSchemeDefine scheme = this.getPrintTemplateScheme(printSchemeKey);
            ITemplateDocument document = PrintElementUtils.newTemplateDocument(this.getPrintSchemeAttribute(scheme));
            DesignPrintComTemDefine common = this.initPrintComTem();
            common.setKey(printSchemeKey);
            common.setCode("DEFAULT");
            common.setTitle("\u9ed8\u8ba4\u6bcd\u7248");
            common.setPrintSchemeKey(printSchemeKey);
            common.setTemplateData(PrintElementUtils.toByteArray(document));
            common.setOrder("00000000");
            this.insertPrintComTem(common);
            return document;
        }
        if (null == define.getTemplateData() || define.getTemplateData().length == 0) {
            DesignPrintTemplateSchemeDefine scheme = this.getPrintTemplateScheme(printSchemeKey);
            ITemplateDocument document = PrintElementUtils.newTemplateDocument(this.getPrintSchemeAttribute(scheme));
            define.setTemplateData(PrintElementUtils.toByteArray(document));
            this.updatePrintComTem(define);
            return document;
        }
        return PrintElementUtils.toTemplateDocument(define.getTemplateData());
    }

    @Override
    public ITemplateDocument getCommonTemplateDocument(String printSchemeKey, String commonCode) {
        DesignPrintComTemDefine define = this.printComTemDefineService.getBySchemeAndCode(printSchemeKey, commonCode);
        if (null != define && null != define.getTemplateData() && define.getTemplateData().length > 0) {
            return PrintElementUtils.toTemplateDocument(define.getTemplateData());
        }
        return null;
    }

    @Override
    public void updateCommonTemplateDocument(String printSchemeKey, ITemplateDocument document) {
        DesignPrintComTemDefine define = this.printComTemDefineService.getByKey(printSchemeKey);
        if (null == define) {
            define = this.printComTemDefineService.init(printSchemeKey, "\u9ed8\u8ba4\u6bcd\u7248");
            define.setKey(printSchemeKey);
            define.setCode("DEFAULT");
            define.setTemplateData(PrintElementUtils.toByteArray(document));
            define.setUpdateTime(new Date());
            this.insertPrintComTem(define);
        } else {
            define.setTemplateData(PrintElementUtils.toByteArray(document));
            define.setUpdateTime(new Date());
            this.updatePrintComTem(define);
        }
    }

    private void syncPageSetting(ITemplateDocument commonTemplate, ITemplateDocument reportTemplate) {
        PageTemplateObject reportPage = (PageTemplateObject)reportTemplate.getPage(0);
        PageTemplateObject commonTemPage = (PageTemplateObject)commonTemplate.getPage(0);
        reportPage.setPaper(commonTemPage.getPaper());
        reportPage.setOrientation(commonTemPage.getOrientation());
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void updateCommonTemplateDocument(String printSchemeKey, String commonKey, boolean sync, ITemplateDocument document) {
        DesignPrintComTemDefine comTemDefine;
        byte[] comTemData = PrintElementUtils.toByteArray(document);
        if (null == commonKey || "commonTem".equals(commonKey)) {
            commonKey = printSchemeKey;
        }
        if (null == (comTemDefine = this.printComTemDefineService.getByKey(commonKey))) {
            if (!commonKey.equals(printSchemeKey)) {
                logger.error(NrDefinitionErrorEnum2.PRINT_TEMPLATE_UPDATE.getMessage(), (Object)"\u6253\u5370\u6bcd\u7248\u4e0d\u5b58\u5728");
                throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_UPDATE, "\u6253\u5370\u6bcd\u7248\u4e0d\u5b58\u5728");
            }
            comTemDefine = this.printComTemDefineService.init(printSchemeKey, "\u9ed8\u8ba4\u6bcd\u7248");
            comTemDefine.setKey(printSchemeKey);
            comTemDefine.setCode("DEFAULT");
            comTemDefine.setTemplateData(comTemData);
            this.insertPrintComTem(comTemDefine);
            comTemDefine.setUpdateTime(new Date());
        } else {
            comTemDefine.setTemplateData(comTemData);
            comTemDefine.setUpdateTime(new Date());
            this.updatePrintComTem(comTemDefine);
        }
        if (sync) {
            document = PrintElementUtils.toTemplateDocument(comTemData);
            String comTemDefineCode = comTemDefine.getCode();
            List<DesignPrintTemplateDefine> allPrintTemplates = this.listPrintTemplateByScheme(printSchemeKey);
            List<DesignPrintTemplateDefine> defines = allPrintTemplates.stream().filter(d -> comTemDefineCode.equals(d.getComTemCode())).collect(Collectors.toList());
            this.sync(document, defines);
            this.sync(comTemDefine, allPrintTemplates);
        }
    }

    private void sync(DesignPrintComTemDefine comTemDefine, List<DesignPrintTemplateDefine> allPrintTemplates) {
        if (comTemDefine.isDefault()) {
            Set existFormKeys = allPrintTemplates.stream().map(PrintTemplateDefine::getFormKey).collect(Collectors.toSet());
            DesignPrintTemplateSchemeDefine printTemplateScheme = this.getPrintTemplateScheme(comTemDefine.getPrintSchemeKey());
            List<DesignFormDefine> designFormDefines = this.designTimeViewController.listFormByFormScheme(printTemplateScheme.getFormSchemeKey());
            List formKeys = designFormDefines.stream().map(IBaseMetaItem::getKey).filter(k -> !existFormKeys.contains(k)).collect(Collectors.toList());
            ArrayList<DesignPrintTemplateDefine> inserts = new ArrayList<DesignPrintTemplateDefine>();
            for (String formKey : formKeys) {
                DesignPrintTemplateDefine define = this.initPrintTemplate();
                define.setPrintSchemeKey(comTemDefine.getPrintSchemeKey());
                define.setFormKey(formKey);
                define.setComTemCode(comTemDefine.getCode());
                define.setTemplateData(SerializeUtil.serialize((ITemplateObject)this.initTemplateDocument(define.getPrintSchemeKey(), define.getComTemCode(), define.getFormKey())).getBytes());
                define.setAutoRefreshForm(true);
                define.setFormUpdateTime(null);
                define.setUpdateTime(new Date());
                inserts.add(define);
            }
            if (!inserts.isEmpty()) {
                this.insertPrintTemplate(inserts.toArray(new DesignPrintTemplateDefine[0]));
            }
        }
    }

    private void sync(ITemplateDocument document, List<DesignPrintTemplateDefine> defines) {
        IResourceManager commonResource = document.getResourceManager();
        HashMap<String, ITemplateElement> commonMap = new HashMap<String, ITemplateElement>();
        ArrayList<String> commonIds = new ArrayList<String>();
        for (ITemplateElement element : document.getPage(0).getTemplateElements()) {
            element.setID(PrintElementUtils.linkCommon(element.getID()));
            commonMap.put(element.getID(), element);
            commonIds.add(element.getID());
        }
        for (DesignPrintTemplateDefine define : defines) {
            if (null == define.getTemplateData()) continue;
            HashMap<String, ITemplateElement> elementMap = new HashMap<String, ITemplateElement>();
            ArrayList<String> elementIds = new ArrayList<String>();
            ITemplateDocument template = PrintElementUtils.toTemplateDocument(define.getTemplateData());
            this.syncPageSetting(document, template);
            for (ITemplateElement element : template.getPage(0).getTemplateElements()) {
                elementMap.put(element.getID(), element);
                if (!PrintElementUtils.isLinkCommon(element.getID())) {
                    elementIds.add(element.getID());
                    continue;
                }
                if (!commonMap.containsKey(element.getID())) {
                    template.getPage(0).remove(element);
                    continue;
                }
                elementIds.add(element.getID());
            }
            ArrayList ids = new ArrayList(commonIds);
            for (int i = 0; i < elementIds.size(); ++i) {
                if (PrintElementUtils.isLinkCommon((String)elementIds.get(i))) continue;
                ids.add(0 == i ? 0 : ids.indexOf(elementIds.get(i - 1)) + 1, elementIds.get(i));
            }
            ITemplatePage page = template.getPage(0);
            page.clear();
            for (String id : ids) {
                Object element = (ITemplateElement)commonMap.get(id);
                if (element instanceof ReportTemplateObject) {
                    ReportTemplateObject comReport = (ReportTemplateObject)SerializeUtil.deserialize((String)SerializeUtil.serialize((ITemplateObject)element), (ITemplateObjectFactory)PrintElementUtils.FACTORY);
                    if (null == comReport) continue;
                    ReportTemplateObject report = (ReportTemplateObject)((Object)elementMap.get(id));
                    if (null == report) {
                        for (ITemplateElement value : elementMap.values()) {
                            if (!(value instanceof ReportTemplateObject)) continue;
                            report = (ReportTemplateObject)value;
                        }
                    }
                    if (null == report) {
                        Grid2Data style = this.designTimeViewController.getFormStyle(define.getFormKey());
                        GridData gridData = new GridData();
                        if (null != style) {
                            PrintUtil.grid2DataToGridData(style, gridData);
                        }
                        comReport.setReportGuid(define.getFormKey());
                        comReport.setGridData(gridData);
                    } else {
                        DesignTimePrintController.copy(report, comReport);
                    }
                    element = comReport;
                }
                if (element instanceof ImageTemplateObject) {
                    ImageTemplateObject image = (ImageTemplateObject)element;
                    String imageId = image.getImageId();
                    IResource resource = commonResource.getResource(imageId);
                    template.getResourceManager().createResource(resource.getKind(), resource.getName(), resource.getLocal(), resource.getBytes());
                }
                if (null == element) {
                    element = (ITemplateElement)elementMap.get(id);
                }
                page.add(element);
            }
            define.setTemplateData(PrintElementUtils.toByteArray(template));
            define.setUpdateTime(new Date());
        }
        if (!defines.isEmpty()) {
            this.updatePrintTemplate(defines.toArray(new DesignPrintTemplateDefine[0]));
        }
    }

    private static void copy(ReportTemplateObject report, ReportTemplateObject comReport) {
        comReport.setReportGuid(report.getReportGuid());
        comReport.setReportTitle(report.getReportTitle());
        comReport.setGridData(report.getGridData());
        comReport.setAnalReport(report.isAnalReport());
        comReport.setAutoBorderLinePrint(report.isAutoBorderLinePrint());
        comReport.setUnderLinePrint(report.isUnderLinePrint());
        comReport.setOddLoRc(report.isOddLoRc());
        comReport.setEvenLoRc(report.isEvenLoRc());
        comReport.setPaginateConfigEdit(report.isPaginateConfigEdit());
        comReport.setResizeConfigEdit(report.isResizeConfigEdit());
        if (comReport.isPaginateConfigEdit()) {
            comReport.setPaginateConfig(report.getPaginateConfig());
            comReport.setRowPaginateFirst(report.isRowPaginateFirst());
        }
        if (comReport.isResizeConfigEdit()) {
            comReport.setResizeConfig(report.getResizeConfig());
        }
    }

    @Override
    public ITemplateDocument initTemplateDocument(String printSchemeKey, String formKey) {
        Grid2Data style = this.designTimeViewController.getFormStyle(formKey);
        if (null == style) {
            return null;
        }
        return PrintElementUtils.toTemplateDocument(this.initCommonTemplateDocument(printSchemeKey), style);
    }

    @Override
    public ITemplateDocument initTemplateDocument(String printSchemeKey, String commonCode, String formKey) {
        Grid2Data style = this.designTimeViewController.getFormStyle(formKey);
        if (null == style) {
            return null;
        }
        ITemplateDocument document = this.getCommonTemplateDocument(printSchemeKey, commonCode);
        if (null == document) {
            DesignPrintTemplateSchemeDefine scheme = this.getPrintTemplateScheme(printSchemeKey);
            document = PrintElementUtils.newTemplateDocument(this.getPrintSchemeAttribute(scheme));
        }
        return PrintElementUtils.toTemplateDocument(document, style);
    }

    @Override
    public ITemplateDocument getTemplateDocument(DesignPrintTemplateDefine define) {
        if (null != define) {
            return PrintElementUtils.toTemplateDocument(define, () -> {
                ITemplateDocument common = this.getCommonTemplateDocument(define.getPrintSchemeKey(), define.getComTemCode());
                if (null == common) {
                    common = this.getCommonTemplateDocument(define.getPrintSchemeKey());
                }
                return common;
            }, () -> this.designTimeViewController.getFormStyle(define.getFormKey()));
        }
        return null;
    }

    @Override
    public PrintTemplateAttributeDefine getPrintTemplateAttribute(DesignPrintTemplateDefine printTemplate) {
        PrintTemplateAttributeDefine define = null;
        define = null != printTemplate && null != printTemplate.getLabelData() && printTemplate.getLabelData().length > 0 ? DataTransformUtil.deserialize(printTemplate.getLabelData(), PrintTemplateAttributeDefine.class) : new PrintTemplateAttributeDefineImpl();
        return define;
    }

    @Override
    public void setPrintTemplateAttribute(DesignPrintTemplateDefine printTemplate, PrintTemplateAttributeDefine define) {
        if (null != define) {
            printTemplate.setLabelData(DataTransformUtil.serializeToByteArray(define));
        } else {
            printTemplate.setLabelData(null);
        }
    }

    @Override
    public WordLabelDefine initWordLabel() {
        return new WordLabelDefineImpl();
    }

    @Override
    public DesignPrintSettingDefine initPrintSetting() {
        return new DesignPrintSettingDefineImpl();
    }

    @Override
    public DesignPrintSettingDefine getPrintSettingDefine(String printSchemeKey, String formKey) {
        return this.designPrintSettingDefineService.query(printSchemeKey, formKey);
    }

    @Override
    public List<DesignPrintSettingDefine> listPrintSettingDefine(String printSchemeKey) {
        return this.designPrintSettingDefineService.list(printSchemeKey);
    }

    @Override
    public void deletePrintSettingDefine(String printSchemeKey) {
        this.designPrintSettingDefineService.delete(printSchemeKey);
    }

    @Override
    public void deletePrintSettingDefine(String printSchemeKey, String formKey) {
        this.designPrintSettingDefineService.delete(printSchemeKey, formKey);
    }

    @Override
    public void insertPrintSettingDefine(DesignPrintSettingDefine designPrintSettingDefine) {
        this.designPrintSettingDefineService.insert(designPrintSettingDefine);
    }

    @Override
    public void insertPrintSettingDefine(List<DesignPrintSettingDefine> designPrintSettingDefines) {
        this.designPrintSettingDefineService.insert(designPrintSettingDefines);
    }

    @Override
    public void updatePrintSettingDefine(DesignPrintSettingDefine designPrintSettingDefine) {
        this.designPrintSettingDefineService.update(designPrintSettingDefine);
    }

    @Override
    public void updatePrintSettingDefine(List<DesignPrintSettingDefine> designPrintSettingDefines) {
        this.designPrintSettingDefineService.update(designPrintSettingDefines);
    }
}

