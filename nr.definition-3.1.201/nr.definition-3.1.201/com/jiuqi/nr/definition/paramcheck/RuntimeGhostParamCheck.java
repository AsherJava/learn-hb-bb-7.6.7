/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.nr.common.paramcheck.bean.ErrorParam
 *  com.jiuqi.nr.common.paramcheck.common.ParamType
 *  com.jiuqi.nr.common.paramcheck.ext.ErrorTypeEnum
 *  com.jiuqi.nr.common.paramcheck.service.ACheckService
 *  com.jiuqi.nr.common.paramcheck.service.CheckParam
 */
package com.jiuqi.nr.definition.paramcheck;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.common.paramcheck.bean.ErrorParam;
import com.jiuqi.nr.common.paramcheck.common.ParamType;
import com.jiuqi.nr.common.paramcheck.ext.ErrorTypeEnum;
import com.jiuqi.nr.common.paramcheck.service.ACheckService;
import com.jiuqi.nr.common.paramcheck.service.CheckParam;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormGroupLink;
import com.jiuqi.nr.definition.internal.service.RunTimeDataLinkDefineService;
import com.jiuqi.nr.definition.internal.service.RunTimeDataRegionDefineService;
import com.jiuqi.nr.definition.internal.service.RunTimeFormDefineService;
import com.jiuqi.nr.definition.internal.service.RunTimeFormGroupDefineService;
import com.jiuqi.nr.definition.internal.service.RunTimeFormSchemeDefineService;
import com.jiuqi.nr.definition.internal.service.RunTimeFormulaDefineService;
import com.jiuqi.nr.definition.internal.service.RunTimeFormulaSchemeDefineService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@CheckParam(name="runtimeGhostParamCheck", title="\u8fd0\u884c\u671f\u5e7d\u7075\u53c2\u6570\u68c0\u67e5", type=ParamType.NR_PARAM)
public class RuntimeGhostParamCheck
extends ACheckService {
    private static final Logger logger = LogFactory.getLogger(RuntimeGhostParamCheck.class);
    @Autowired
    private RunTimeFormSchemeDefineService formSchemeService;
    @Autowired
    private RunTimeFormDefineService formService;
    @Autowired
    private RunTimeDataRegionDefineService regionService;
    @Autowired
    private RunTimeDataLinkDefineService dataLinkService;
    @Autowired
    private RunTimeFormulaSchemeDefineService formulaSchemeService;
    @Autowired
    private RunTimeFormulaDefineService formulaService;
    @Autowired
    private RunTimeFormGroupDefineService formGroupService;
    private ObjectMapper mapper = new ObjectMapper();
    private static final String GHOST_FORMSCHEME = "GHOST_FORMSCHEME";
    private static final String GHOST_FORMGROUP = "GHOST_FORMGROUP";
    private static final String GHOST_FORM = "GHOST_FORM";
    private static final String GHOST_REGION = "GHOST_REGION";
    private static final String GHOST_DATALINK = "GHOST_DATALINK";
    private static final String GHOST_FORMULASCHEME = "GHOST_FORMULASCHEME";
    private static final String GHOST_FORMULAS = "GHOST_FORMULAS";

    public boolean execute() throws JQException {
        this.clearErrorData();
        try {
            this.checkFormScheme();
            this.checkFormulaScheme();
            this.checkFormula();
            this.checkFormGroup();
            this.checkForm();
            this.checkDataRegion();
            this.checkDataLink();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_1001);
        }
        return false;
    }

    private void checkFormula() {
        List<FormulaDefine> ghostFormulas = this.formulaService.listGhostFormula();
        ArrayList<String> ghostFormulaKeys = new ArrayList<String>();
        if (ghostFormulas.size() > 0) {
            ghostFormulas.forEach(link -> ghostFormulaKeys.add(link.getKey()));
            if (ghostFormulaKeys.size() > 0) {
                ErrorParam error = new ErrorParam(UUIDUtils.getKey(), ErrorTypeEnum.GHOST_ERROR.getError(), "\u516c\u5f0f\u65e0\u6548", (Object)new GhostObject(GHOST_FORMULAS, ghostFormulaKeys).toString(), "\u5b58\u5728" + ghostFormulaKeys.size() + "\u4e2a\u65e0\u6548\u516c\u5f0f", null);
                this.addErrorData(error);
            }
        }
    }

    private void checkFormulaScheme() {
        List<FormulaSchemeDefine> ghostSchmes = this.formulaSchemeService.listGhostScheme();
        ArrayList<String> ghostShemeKeys = new ArrayList<String>();
        if (ghostSchmes.size() > 0) {
            ghostSchmes.forEach(link -> ghostShemeKeys.add(link.getKey()));
            if (ghostShemeKeys.size() > 0) {
                ErrorParam error = new ErrorParam(UUIDUtils.getKey(), ErrorTypeEnum.GHOST_ERROR.getError(), "\u516c\u5f0f\u65b9\u6848\u65e0\u6548", (Object)new GhostObject(GHOST_FORMULASCHEME, ghostShemeKeys).toString(), "\u5b58\u5728" + ghostShemeKeys.size() + "\u4e2a\u65e0\u6548\u516c\u5f0f\u65b9\u6848", null);
                this.addErrorData(error);
            }
        }
    }

    private void checkDataLink() {
        List<DataLinkDefine> ghostLinks = this.dataLinkService.listGhostLink();
        ArrayList<String> ghostLinkKeys = new ArrayList<String>();
        if (ghostLinks.size() > 0) {
            ghostLinks.forEach(link -> ghostLinkKeys.add(link.getKey()));
            if (ghostLinkKeys.size() > 0) {
                ErrorParam error = new ErrorParam(UUIDUtils.getKey(), ErrorTypeEnum.GHOST_ERROR.getError(), "\u6570\u636e\u8fde\u63a5\u65e0\u6548", (Object)new GhostObject(GHOST_DATALINK, ghostLinkKeys).toString(), "\u5b58\u5728" + ghostLinkKeys.size() + "\u4e2a\u65e0\u6548\u6570\u636e\u94fe\u63a5", null);
                this.addErrorData(error);
            }
        }
    }

    private void checkDataRegion() {
        List<DataRegionDefine> ghostRegions = this.regionService.listGhostRegion();
        ArrayList<String> ghostRegionKeys = new ArrayList<String>();
        if (ghostRegions.size() > 0) {
            ghostRegions.forEach(region -> ghostRegionKeys.add(region.getKey()));
            if (ghostRegionKeys.size() > 0) {
                ErrorParam error = new ErrorParam(UUIDUtils.getKey(), ErrorTypeEnum.GHOST_ERROR.getError(), "\u6570\u636e\u533a\u57df\u65e0\u6548", (Object)new GhostObject(GHOST_REGION, ghostRegionKeys).toString(), "\u5b58\u5728" + ghostRegionKeys.size() + "\u4e2a\u65e0\u6548\u6570\u636e\u533a\u57df", null);
                this.addErrorData(error);
            }
        }
    }

    private void checkForm() {
        List<FormDefine> ghostForms = this.formService.listGhostForm();
        ArrayList<String> ghostFormKeys = new ArrayList<String>();
        if (ghostForms.size() > 0) {
            ghostForms.forEach(form -> {
                if (form.getFormType() != FormType.FORM_TYPE_ENTITY) {
                    ghostFormKeys.add(form.getKey());
                }
            });
            if (ghostFormKeys.size() > 0) {
                ErrorParam error = new ErrorParam(UUIDUtils.getKey(), ErrorTypeEnum.GHOST_ERROR.getError(), "\u62a5\u8868\u65e0\u6548", (Object)new GhostObject(GHOST_FORM, ghostFormKeys).toString(), "\u5b58\u5728" + ghostFormKeys.size() + "\u4e2a\u65e0\u6548\u62a5\u8868", null);
                this.addErrorData(error);
            }
        }
    }

    private void checkFormGroup() {
        List<FormGroupDefine> ghostGroups = this.formGroupService.listGhostGroup();
        ArrayList<String> ghostGroupKeys = new ArrayList<String>();
        if (ghostGroups.size() > 0) {
            ghostGroups.forEach(group -> ghostGroupKeys.add(group.getKey()));
            ErrorParam error = new ErrorParam(UUIDUtils.getKey(), ErrorTypeEnum.GHOST_ERROR.getError(), "\u62a5\u8868\u5206\u7ec4\u65e0\u6548", (Object)new GhostObject(GHOST_FORMGROUP, ghostGroupKeys).toString(), "\u5b58\u5728" + ghostGroupKeys.size() + "\u4e2a\u65e0\u6548\u62a5\u8868\u5206\u7ec4", null);
            this.addErrorData(error);
        }
    }

    private void checkFormScheme() throws Exception {
        List<FormSchemeDefine> ghostSchemes = this.formSchemeService.listGhostSchemes();
        ArrayList<String> ghostSchemeKeys = new ArrayList<String>();
        if (ghostSchemes.size() > 0) {
            ghostSchemes.forEach(scheme -> ghostSchemeKeys.add(scheme.getKey()));
            ErrorParam error = new ErrorParam(UUIDUtils.getKey(), ErrorTypeEnum.GHOST_ERROR.getError(), "\u62a5\u8868\u65b9\u6848\u65e0\u6548", (Object)new GhostObject(GHOST_FORMSCHEME, ghostSchemeKeys).toString(), "\u5b58\u5728" + ghostSchemeKeys.size() + "\u4e2a\u65e0\u6548\u62a5\u8868\u65b9\u6848", null);
            this.addErrorData(error);
        }
    }

    private void deleteFormSchemes(List<String> keys) throws Exception {
        if (keys == null || keys.size() == 0) {
            return;
        }
        this.formSchemeService.delete(keys.toArray(new String[1]));
        ArrayList<String> schemeKeys = new ArrayList<String>();
        ArrayList<String> groupKeys = new ArrayList<String>();
        for (String key : keys) {
            List<FormulaSchemeDefine> formulaSchemes;
            List<FormGroupDefine> groups = this.formGroupService.queryFormGroupDefinesByScheme(key);
            if (groups.size() > 0) {
                groups.forEach(group -> groupKeys.add(group.getKey()));
            }
            if ((formulaSchemes = this.formulaSchemeService.queryFormulaSchemeDefineByFormScheme(key)).size() <= 0) continue;
            formulaSchemes.forEach(scheme -> schemeKeys.add(scheme.getKey()));
        }
        this.deleteFormGroups(groupKeys);
        this.deleteFormulaSchemes(schemeKeys);
    }

    private void deleteFormulaSchemes(List<String> keys) throws Exception {
        if (keys == null || keys.size() == 0) {
            return;
        }
        this.formulaSchemeService.delete(keys.toArray(new String[1]));
        for (String key : keys) {
            this.formulaService.deleteByScheme(key);
        }
    }

    private void deleteFormGroups(List<String> keys) throws Exception {
        if (keys == null || keys.size() == 0) {
            return;
        }
        this.formGroupService.delete(keys.toArray(new String[1]));
        ArrayList<String> formKeys = new ArrayList<String>();
        for (String key : keys) {
            List<FormDefine> forms = this.formService.queryFormDefineByGroupId(key);
            if (forms.size() <= 0) continue;
            for (FormDefine form : forms) {
                List<RunTimeFormGroupLink> groups;
                this.formService.removeFormFromGroup(form.getKey(), key);
                if (form.getFormType() == FormType.FORM_TYPE_ENTITY || (groups = this.formService.getFormGroupLinksByFormId(form.getKey())).size() != 0) continue;
                formKeys.add(form.getKey());
            }
        }
        this.deleteForms(formKeys);
    }

    private void deleteForms(List<String> keys) throws Exception {
        if (keys == null || keys.size() == 0) {
            return;
        }
        this.formService.delete(keys.toArray(new String[1]));
        ArrayList<String> regionKeys = new ArrayList<String>();
        for (String key : keys) {
            List<DataRegionDefine> regions = this.regionService.getAllRegionsInForm(key);
            if (regions.size() <= 0) continue;
            regions.forEach(region -> regionKeys.add(region.getKey()));
        }
        this.deleteRegions(regionKeys);
    }

    private void deleteRegions(List<String> keys) throws Exception {
        if (keys == null || keys.size() == 0) {
            return;
        }
        this.regionService.delete(keys.toArray(new String[1]));
        for (String key : keys) {
            this.dataLinkService.deleteByRegion(key);
        }
    }

    public boolean fix(Object object) throws JQException {
        logger.info(String.valueOf(object));
        try {
            List errorParams = (List)this.mapper.readValue(object.toString(), (TypeReference)new TypeReference<List<ErrorParam>>(){});
            for (ErrorParam errorParam : errorParams) {
                Object data = errorParam.getData();
                if (!(data instanceof String)) continue;
                GhostObject ghostObj = new GhostObject(data.toString());
                if (ghostObj.type.equalsIgnoreCase(GHOST_FORMSCHEME)) {
                    this.deleteFormSchemes(ghostObj.keys);
                    continue;
                }
                if (ghostObj.type.equalsIgnoreCase(GHOST_FORMULASCHEME)) {
                    this.deleteFormulaSchemes(ghostObj.keys);
                    continue;
                }
                if (ghostObj.type.equalsIgnoreCase(GHOST_FORMGROUP)) {
                    this.deleteFormGroups(ghostObj.keys);
                    continue;
                }
                if (ghostObj.type.equalsIgnoreCase(GHOST_FORM)) {
                    this.deleteForms(ghostObj.keys);
                    continue;
                }
                if (ghostObj.type.equalsIgnoreCase(GHOST_REGION)) {
                    this.deleteRegions(ghostObj.keys);
                    continue;
                }
                if (ghostObj.type.equalsIgnoreCase(GHOST_DATALINK)) {
                    this.deleteDataLinks(ghostObj.keys);
                    continue;
                }
                if (!ghostObj.type.equalsIgnoreCase(GHOST_FORMULAS)) continue;
                this.deleteFormulas(ghostObj.keys);
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_1002);
        }
        return true;
    }

    private void deleteFormulas(List<String> keys) throws Exception {
        if (keys == null || keys.size() == 0) {
            return;
        }
        this.formulaService.delete(keys.toArray(new String[1]));
    }

    private void deleteDataLinks(List<String> keys) throws Exception {
        if (keys == null || keys.size() == 0) {
            return;
        }
        this.dataLinkService.delete(keys.toArray(new String[1]));
    }

    class GhostObject {
        private final Logger logger = LogFactory.getLogger(GhostObject.class);
        String type;
        List<String> keys;

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<String> getKeys() {
            return this.keys;
        }

        public void setKeys(List<String> keys) {
            this.keys = keys;
        }

        public GhostObject(String type, List<String> keys) {
            this.type = type;
            this.keys = keys;
        }

        public GhostObject(String objStr) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode taskObjNode = mapper.readTree(objStr);
                this.type = taskObjNode.get("type").asText();
                this.keys = (List)mapper.readValue(taskObjNode.get("keys").toString(), mapper.getTypeFactory().constructParametricType(List.class, new Class[]{String.class}));
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), (Throwable)e);
            }
        }

        public String toString() {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.writeValueAsString((Object)this);
            }
            catch (JsonProcessingException e) {
                this.logger.error(e.getMessage(), (Throwable)e);
                return super.toString();
            }
        }
    }
}

