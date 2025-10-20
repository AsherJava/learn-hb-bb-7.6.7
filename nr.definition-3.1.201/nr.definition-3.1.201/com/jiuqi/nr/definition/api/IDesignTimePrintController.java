/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.UUIDUtils
 *  com.jiuqi.util.OrderGenerator
 *  com.jiuqi.xg.process.ITemplateDocument
 *  com.jiuqi.xg.process.ITemplateElement
 *  com.jiuqi.xg.process.ITemplateObject
 *  com.jiuqi.xg.process.ITemplateObjectFactory
 *  com.jiuqi.xg.process.ITemplatePage
 *  com.jiuqi.xg.process.util.SerializeUtil
 */
package com.jiuqi.nr.definition.api;

import com.jiuqi.nr.definition.facade.DesignPrintComTemDefine;
import com.jiuqi.nr.definition.facade.DesignPrintSettingDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.print.PrintSchemeAttributeDefine;
import com.jiuqi.nr.definition.facade.print.PrintTemplateAttributeDefine;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject;
import com.jiuqi.nr.definition.internal.impl.DesignPrintComTemDefineImpl;
import com.jiuqi.nr.definition.print.common.PrintElementUtils;
import com.jiuqi.nvwa.definition.common.UUIDUtils;
import com.jiuqi.util.OrderGenerator;
import com.jiuqi.xg.process.ITemplateDocument;
import com.jiuqi.xg.process.ITemplateElement;
import com.jiuqi.xg.process.ITemplateObject;
import com.jiuqi.xg.process.ITemplateObjectFactory;
import com.jiuqi.xg.process.ITemplatePage;
import com.jiuqi.xg.process.util.SerializeUtil;
import java.util.List;
import org.springframework.beans.BeanUtils;

public interface IDesignTimePrintController {
    public DesignPrintTemplateSchemeDefine initPrintTemplateScheme();

    default public DesignPrintTemplateSchemeDefine copyPrintTemplateScheme(DesignPrintTemplateSchemeDefine source, String targetTask, String targetFormScheme) {
        DesignPrintTemplateSchemeDefine define = this.initPrintTemplateScheme();
        BeanUtils.copyProperties(source, define);
        define.setKey(UUIDUtils.getKey());
        define.setTaskKey(targetTask);
        define.setFormSchemeKey(targetFormScheme);
        return define;
    }

    public void insertPrintTemplateScheme(DesignPrintTemplateSchemeDefine var1);

    public void updatePrintTemplateScheme(DesignPrintTemplateSchemeDefine var1);

    public void deletePrintTemplateScheme(String[] var1);

    public List<DesignPrintTemplateSchemeDefine> listAllPrintTemplateScheme();

    public DesignPrintTemplateSchemeDefine getPrintTemplateScheme(String var1);

    public List<DesignPrintTemplateSchemeDefine> listPrintTemplateSchemeByFormScheme(String var1);

    public void insertCommonAttribute(String var1, PrintSchemeAttributeDefine var2);

    public void updateCommonAttribute(String var1, PrintSchemeAttributeDefine var2);

    public void deleteCommonAttribute(String var1);

    public PrintSchemeAttributeDefine getPrintSchemeAttribute(DesignPrintTemplateSchemeDefine var1);

    public void setPrintSchemeAttribute(DesignPrintTemplateSchemeDefine var1, PrintSchemeAttributeDefine var2);

    public DesignPrintTemplateDefine initPrintTemplate();

    default public DesignPrintTemplateDefine copyPrintTemplate(DesignPrintTemplateDefine source, String targetScheme, String targetForm) {
        DesignPrintTemplateDefine define = this.initPrintTemplate();
        BeanUtils.copyProperties(source, define);
        define.setKey(UUIDUtils.getKey());
        define.setPrintSchemeKey(targetScheme);
        define.setFormKey(targetForm);
        define.setLabelData(source.getLabelData());
        byte[] data = source.getTemplateData();
        ITemplateDocument template = (ITemplateDocument)SerializeUtil.deserialize((String)new String(data), (ITemplateObjectFactory)PrintElementUtils.FACTORY);
        if (null != template) {
            for (ITemplatePage page : template.getPages()) {
                for (ITemplateElement element : page.getTemplateElements()) {
                    if (!(element instanceof ReportTemplateObject)) continue;
                    ReportTemplateObject object = (ReportTemplateObject)element;
                    object.setReportGuid(targetForm);
                }
            }
            data = SerializeUtil.serialize((ITemplateObject)template).getBytes();
        }
        define.setTemplateData(data);
        return define;
    }

    public void insertPrintTemplate(DesignPrintTemplateDefine var1);

    public void insertPrintTemplate(DesignPrintTemplateDefine[] var1);

    public void updatePrintTemplate(DesignPrintTemplateDefine var1);

    public void updatePrintTemplate(DesignPrintTemplateDefine[] var1);

    public void deletePrintTemplate(String[] var1);

    public void deletePrintTemplateByScheme(String var1);

    public void deletePrintTemplateBySchemeAndForm(String var1, String var2);

    public void deletePrintTemplateByForm(String ... var1);

    public DesignPrintTemplateDefine getPrintTemplate(String var1);

    public DesignPrintTemplateDefine getPrintTemplateBySchemeAndForm(String var1, String var2);

    public List<DesignPrintTemplateDefine> listPrintTemplateByForm(String var1);

    public List<DesignPrintTemplateDefine> listPrintTemplateByScheme(String var1);

    default public DesignPrintComTemDefine copyPrintComTem(DesignPrintComTemDefine source, String targetPrintScheme) {
        DesignPrintComTemDefineImpl define = new DesignPrintComTemDefineImpl();
        if (source.isDefault()) {
            define.setKey(targetPrintScheme);
        } else {
            define.setKey(UUIDUtils.getKey());
        }
        define.setPrintSchemeKey(targetPrintScheme);
        define.setCode(source.getCode());
        define.setTitle(source.getTitle());
        define.setDescription(source.getDescription());
        define.setOrder(source.getOrder());
        define.setUpdateTime(source.getUpdateTime());
        define.setTemplateData(source.getTemplateData());
        define.setVersion(source.getVersion());
        define.setOwnerLevelAndId(source.getOwnerLevelAndId());
        return define;
    }

    default public DesignPrintComTemDefine initPrintComTem() {
        DesignPrintComTemDefineImpl define = new DesignPrintComTemDefineImpl();
        define.setKey(UUIDUtils.getKey());
        define.setOrder(OrderGenerator.newOrder());
        define.setCode(OrderGenerator.newOrder());
        return define;
    }

    public DesignPrintComTemDefine getPrintComTem(String var1);

    public DesignPrintComTemDefine getPrintComTemBySchemeAndCode(String var1, String var2);

    public List<DesignPrintComTemDefine> listPrintComTemByScheme(String var1);

    public List<DesignPrintComTemDefine> listPrintComTemBySchemeWithoutBigData(String var1);

    public void deletePrintComTem(String var1);

    public void deletePrintComTemByScheme(String var1);

    public void deletePrintComTemBySchemeAndCode(String var1, String var2);

    public void insertPrintComTem(DesignPrintComTemDefine var1);

    public void insertPrintComTem(List<DesignPrintComTemDefine> var1);

    public void updatePrintComTem(DesignPrintComTemDefine var1);

    public void updatePrintComTem(List<DesignPrintComTemDefine> var1);

    public boolean existCoverTemplateDocument(String var1);

    public ITemplateDocument initCoverTemplateDocument(String var1);

    public ITemplateDocument getCoverTemplateDocument(String var1);

    public void updateCoverTemplateDocument(String var1, ITemplateDocument var2);

    public ITemplateDocument initCommonTemplateDocument(String var1);

    public ITemplateDocument getCommonTemplateDocument(String var1);

    public ITemplateDocument getCommonTemplateDocument(String var1, String var2);

    public void updateCommonTemplateDocument(String var1, ITemplateDocument var2);

    public void updateCommonTemplateDocument(String var1, String var2, boolean var3, ITemplateDocument var4);

    public ITemplateDocument initTemplateDocument(String var1, String var2);

    public ITemplateDocument initTemplateDocument(String var1, String var2, String var3);

    public ITemplateDocument getTemplateDocument(DesignPrintTemplateDefine var1);

    default public ITemplateDocument getTemplateDocumentBySchemeAndForm(String printSchemeKey, String formKey) {
        DesignPrintTemplateDefine define = this.getPrintTemplateBySchemeAndForm(printSchemeKey, formKey);
        if (null != define) {
            return this.getTemplateDocument(define);
        }
        return this.initTemplateDocument(printSchemeKey, "DEFAULT", formKey);
    }

    public PrintTemplateAttributeDefine getPrintTemplateAttribute(DesignPrintTemplateDefine var1);

    public void setPrintTemplateAttribute(DesignPrintTemplateDefine var1, PrintTemplateAttributeDefine var2);

    public WordLabelDefine initWordLabel();

    default public WordLabelDefine copyWordLabel(WordLabelDefine source) {
        WordLabelDefine define = this.initWordLabel();
        BeanUtils.copyProperties(source, define);
        return define;
    }

    @Deprecated
    default public DesignPrintSettingDefine createDesignPrintSettingDefine() {
        return this.initPrintSetting();
    }

    public DesignPrintSettingDefine initPrintSetting();

    default public DesignPrintSettingDefine copyPrintSetting(DesignPrintSettingDefine source, String targetScheme, String targetForm) {
        DesignPrintSettingDefine define = this.initPrintSetting();
        BeanUtils.copyProperties(source, define);
        define.setPrintSchemeKey(targetScheme);
        define.setFormKey(targetForm);
        return define;
    }

    public DesignPrintSettingDefine getPrintSettingDefine(String var1, String var2);

    public List<DesignPrintSettingDefine> listPrintSettingDefine(String var1);

    public void deletePrintSettingDefine(String var1);

    public void deletePrintSettingDefine(String var1, String var2);

    public void insertPrintSettingDefine(DesignPrintSettingDefine var1);

    public void insertPrintSettingDefine(List<DesignPrintSettingDefine> var1);

    public void updatePrintSettingDefine(DesignPrintSettingDefine var1);

    public void updatePrintSettingDefine(List<DesignPrintSettingDefine> var1);
}

