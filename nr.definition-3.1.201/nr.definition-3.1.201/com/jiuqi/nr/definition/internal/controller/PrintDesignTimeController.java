/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.internal.log.Log
 */
package com.jiuqi.nr.definition.internal.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.internal.log.Log;
import com.jiuqi.nr.definition.controller.IPrintDesignTimeController;
import com.jiuqi.nr.definition.exception.DesignCheckException;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum2;
import com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.print.PrintSchemeAttributeDefine;
import com.jiuqi.nr.definition.facade.print.PrintTemplateAttributeDefine;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.definition.internal.impl.DesignPrintTemplateDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignPrintTemplateSchemeDefineImpl;
import com.jiuqi.nr.definition.internal.impl.print.WordLabelDefineImpl;
import com.jiuqi.nr.definition.internal.service.DesignPrintTemplateDefineService;
import com.jiuqi.nr.definition.internal.service.DesignPrintTemplateSchemeDefineService;
import com.jiuqi.nr.definition.paramcheck.IDesignParamCheckService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrintDesignTimeController
implements IPrintDesignTimeController {
    @Autowired
    private DesignPrintTemplateDefineService printService;
    @Autowired
    private DesignPrintTemplateSchemeDefineService printSchemeService;
    @Autowired
    private IDesignParamCheckService designParamCheckService;

    @Override
    public DesignPrintTemplateSchemeDefine createPrintTemplateSchemeDefine() {
        DesignPrintTemplateSchemeDefineImpl define = new DesignPrintTemplateSchemeDefineImpl();
        define.setKey(UUIDUtils.getKey());
        return define;
    }

    @Override
    public String insertPrintTemplateSchemeDefine(DesignPrintTemplateSchemeDefine printTemplateSchemeDefine) {
        String id = null;
        try {
            this.designParamCheckService.checkPrintTemplateScheme(printTemplateSchemeDefine);
            id = this.printSchemeService.insertPrintTemplateSchemeDefine(printTemplateSchemeDefine);
        }
        catch (DesignCheckException e) {
            throw e;
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return id;
    }

    @Override
    public void updatePrintTemplateSchemeDefine(DesignPrintTemplateSchemeDefine printTemplateSchemeDefine) {
        try {
            this.designParamCheckService.checkPrintTemplateScheme(printTemplateSchemeDefine);
            this.printSchemeService.updatePrintTemplateSchemeDefine(printTemplateSchemeDefine);
        }
        catch (DesignCheckException e) {
            throw e;
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void deletePrintTemplateSchemeDefine(String printTemplateSchemeKey) {
        try {
            this.printService.deleteBySchemeAndFormKeys(printTemplateSchemeKey, new String[0]);
            this.printSchemeService.delete(printTemplateSchemeKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void exchangePrintTemplateSchemeOrder(String orinPrintTemplateSchemeKey, String targetPrintTemplateSchemeKey) {
        try {
            this.printSchemeService.exchangePrintTemplateSchemeOrder(orinPrintTemplateSchemeKey, targetPrintTemplateSchemeKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public List<DesignPrintTemplateSchemeDefine> getAllPrintScheme() {
        List<DesignPrintTemplateSchemeDefine> defines = null;
        try {
            defines = this.printSchemeService.queryAllPrintTemplateSchemeDefine();
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return defines;
    }

    @Override
    public DesignPrintTemplateSchemeDefine queryPrintTemplateSchemeDefine(String printSchemeKey) {
        DesignPrintTemplateSchemeDefine define = null;
        try {
            define = this.printSchemeService.queryPrintTemplateSchemeDefine(printSchemeKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return define;
    }

    @Override
    public List<DesignPrintTemplateSchemeDefine> getAllPrintSchemeByTask(String taskKey) {
        List<DesignPrintTemplateSchemeDefine> defines = null;
        try {
            defines = this.printSchemeService.getAllPrintSchemeByTask(taskKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return defines;
    }

    @Override
    public List<DesignPrintTemplateSchemeDefine> getAllPrintSchemeByFormScheme(String formSchemeKey) {
        List<DesignPrintTemplateSchemeDefine> defines = null;
        try {
            defines = this.printSchemeService.getAllPrintSchemeByFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return defines;
    }

    @Override
    public DesignPrintTemplateDefine createPrintTemplateDefine() {
        DesignPrintTemplateDefineImpl define = null;
        try {
            define = new DesignPrintTemplateDefineImpl();
            define.setKey(UUIDUtils.getKey());
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return define;
    }

    @Override
    public String insertPrintTemplateDefine(DesignPrintTemplateDefine printTemplateDefine) {
        String id = null;
        try {
            id = this.printService.insertPrintTemplateDefine(printTemplateDefine);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return id;
    }

    @Override
    public void updatePrintTemplateDefine(DesignPrintTemplateDefine printTemplateDefine) {
        try {
            this.printService.updatePrintTemplateDefine(printTemplateDefine);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void deletePrintTemplateDefine(String printTemplateKey) {
        try {
            this.printService.delete(printTemplateKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void deletePrintTemplateDefineByScheme(String printSchemeKey) {
        try {
            List<DesignPrintTemplateDefine> defines = this.printService.queryAllPrintTemplateDefineByScheme(printSchemeKey);
            if (defines != null && defines.size() > 0) {
                for (DesignPrintTemplateDefine designPrintTemplateDefine : defines) {
                    this.printService.delete(designPrintTemplateDefine.getKey());
                }
            }
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
    }

    @Override
    public void deletePrintTemplateDefine(String printSchemeKey, String ... formKeys) {
        if (null != formKeys && 0 != formKeys.length) {
            try {
                this.printService.deleteBySchemeAndFormKeys(printSchemeKey, formKeys);
            }
            catch (Exception e) {
                throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_DELETE, (Throwable)e);
            }
        }
    }

    @Override
    public void deletePrintTemplateDefineByForm(String ... formKeys) {
        if (null != formKeys && 0 != formKeys.length) {
            try {
                this.printService.deleteByForm(formKeys);
            }
            catch (Exception e) {
                throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_DELETE, (Throwable)e);
            }
        }
    }

    @Override
    public DesignPrintTemplateDefine queryPrintTemplateDefine(String printTemplateKey) {
        DesignPrintTemplateDefine define = null;
        try {
            define = this.printService.queryPrintTemplateDefine(printTemplateKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return define;
    }

    @Override
    public DesignPrintTemplateDefine queryPrintTemplateDefineBySchemeAndForm(String printSchemeKey, String formKey) {
        DesignPrintTemplateDefine define = null;
        try {
            define = this.printService.queryPrintTemplateDefineBySchemeAndForm(printSchemeKey, formKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return define;
    }

    @Override
    public List<DesignPrintTemplateDefine> getAllPrintTemplateInScheme(String printSchemeKey) {
        List<DesignPrintTemplateDefine> defines = null;
        try {
            defines = this.printService.queryAllPrintTemplateDefineByScheme(printSchemeKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return defines;
    }

    @Override
    public WordLabelDefine createWordLabelDefine() {
        WordLabelDefineImpl define = new WordLabelDefineImpl();
        return define;
    }

    @Override
    public PrintSchemeAttributeDefine getPrintSchemeAttribute(DesignPrintTemplateSchemeDefine printScheme) {
        return this.printSchemeService.getPrintSchemeAttribute(printScheme);
    }

    @Override
    public void setPrintSchemeAttribute(DesignPrintTemplateSchemeDefine printScheme, PrintSchemeAttributeDefine define) {
        this.printSchemeService.setPrintSchemeAttribute(printScheme, define);
    }

    @Override
    public PrintTemplateAttributeDefine getPrintTemplateAttribute(DesignPrintTemplateDefine printTemplate) {
        return this.printService.getPrintTemplateAttribute(printTemplate);
    }

    @Override
    public void setPrintTemplateAttribute(DesignPrintTemplateDefine printTemplate, PrintTemplateAttributeDefine define) {
        this.printService.setPrintTemplateAttribute(printTemplate, define);
    }

    @Override
    public List<DesignPrintTemplateDefine> queryAllPrintTemplate(String printSchemeKey) {
        return this.printService.queryAllPrintTemplate(printSchemeKey);
    }

    @Override
    public int[] insertTemplates(DesignPrintTemplateDefine[] templates) {
        try {
            return this.printService.insertTemplates(templates);
        }
        catch (Exception e) {
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_INSERT, (Throwable)e);
        }
    }

    @Override
    public int[] updateTemplates(DesignPrintTemplateDefine[] templates) {
        try {
            return this.printService.updateTemplates(templates);
        }
        catch (Exception e) {
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_UPDATE, (Throwable)e);
        }
    }

    @Override
    public DesignPrintTemplateSchemeDefine queryPrintTemplateSchemeDefine(String printSchemeKey, boolean withBinaryData) {
        DesignPrintTemplateSchemeDefine define = null;
        try {
            define = this.printSchemeService.queryPrintTemplateSchemeDefine(printSchemeKey, withBinaryData);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return define;
    }

    @Override
    public List<DesignPrintTemplateDefine> getAllPrintTemplateInScheme(String printSchemeKey, boolean withBinaryData) {
        List<DesignPrintTemplateDefine> defines = null;
        try {
            defines = this.printService.queryAllPrintTemplateDefineByScheme(printSchemeKey, withBinaryData);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return defines;
    }

    @Override
    public List<DesignPrintTemplateSchemeDefine> getAllPrintSchemeByFormScheme(String formSchemeKey, boolean withBinaryData) {
        List<DesignPrintTemplateSchemeDefine> defines = null;
        try {
            defines = this.printSchemeService.getAllPrintSchemeByFormScheme(formSchemeKey, withBinaryData);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return defines;
    }

    @Override
    public DesignPrintTemplateDefine queryPrintTemplateDefine(String printTemplateKey, boolean withBinaryData) {
        DesignPrintTemplateDefine define = null;
        try {
            define = this.printService.queryPrintTemplateDefine(printTemplateKey, withBinaryData);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return define;
    }

    @Override
    public DesignPrintTemplateDefine queryPrintTemplateDefineBySchemeAndForm(String printSchemeKey, String formKey, boolean withBinaryData) {
        DesignPrintTemplateDefine define = null;
        try {
            define = this.printService.queryPrintTemplateDefineBySchemeAndForm(printSchemeKey, formKey, withBinaryData);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return define;
    }
}

