/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.definition.deploy;

import com.jiuqi.nr.definition.deploy.DataRegionItem;
import com.jiuqi.nr.definition.deploy.DeployDefinitionDao;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink;
import com.jiuqi.nr.definition.facade.formula.FormulaConditionLink;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class, RuntimeException.class})
@Service
public class DeployDefinitionService {
    @Autowired
    DeployDefinitionDao deployDefinitionDao;

    public void deleteTaskDefines(Set<String> taskKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteTaskDefines(taskKeys, isFromDesToSys);
    }

    public void insertTaskDefines(Set<String> taskKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertTaskDefines(taskKeys, isFromDesToSys);
    }

    public void deleteTaskI18n(Set<String> taskKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteTaskI18n(taskKeys, isFromDesToSys);
    }

    public void insertTaskI18n(Set<String> taskKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertTaskI18n(taskKeys, isFromDesToSys);
    }

    public void deleteFlowDataByTask(Set<String> taskKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteFlowDataByTask(taskKeys, isFromDesToSys);
    }

    public void insertFlowDataByTask(Set<String> taskKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertFlowDataByTask(taskKeys, isFromDesToSys);
    }

    public void deleteFormSchemeDefinesByTask(Set<String> taskKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteFormSchemeDefinesByTask(taskKeys, isFromDesToSys);
    }

    public void deleteFormSchemeDefines(Set<String> formSchemeKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteFormSchemeDefines(formSchemeKeys, isFromDesToSys);
    }

    public void insertFormSchemeDefines(Set<String> formSchemeKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertFormSchemeDefines(formSchemeKeys, isFromDesToSys);
    }

    public void deleteSchemePeriodLinks(Set<String> formSchemeKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteSchemePeriodLinks(formSchemeKeys, isFromDesToSys);
    }

    public void insertSchemePeriodLinks(Set<String> formSchemeKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertSchemePeriodLinks(formSchemeKeys, isFromDesToSys);
    }

    public void deleteFormGroupDefinesByFormScheme(Set<String> formSchemeKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteFormGroupDefinesByFormScheme(formSchemeKeys, isFromDesToSys);
    }

    public void insertFormGroupDefinesByFormScheme(Set<String> formSchemeKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertFormGroupDefinesByFormScheme(formSchemeKeys, isFromDesToSys);
    }

    public void deleteFormGroupDefines(Set<String> formGroupKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteFormGroupDefines(formGroupKeys, isFromDesToSys);
    }

    public void insertFormGroupDefines(Set<String> formGroupKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertFormGroupDefines(formGroupKeys, isFromDesToSys);
    }

    public void deleteFormGroupLinks(Set<String> formGroupKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteFormGroupLinks(formGroupKeys, isFromDesToSys);
    }

    public void insertFormGroupLinks(Set<String> formGroupKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertFormGroupLinks(formGroupKeys, isFromDesToSys);
    }

    public void deleteFormDefines(Set<String> formKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteFormDefines(formKeys, isFromDesToSys);
    }

    public void insertFormDefines(Set<String> formKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertFormDefines(formKeys, isFromDesToSys);
    }

    public void deleteDataRegionsByForm(Set<String> formKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteDataRegionsByForm(formKeys, isFromDesToSys);
    }

    public void insertDataRegionsByForm(Set<String> formKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertDataRegionsByForm(formKeys, isFromDesToSys);
    }

    public void deleteDataRegions(Set<String> regionKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteDataRegions(regionKeys, isFromDesToSys);
    }

    public void insertDataRegions(Set<String> regionKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertDataRegions(regionKeys, isFromDesToSys);
    }

    public void deleteDataLinks(String taskKey, Set<String> linkKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteDataLinks(taskKey, linkKeys, isFromDesToSys);
    }

    public void insertDataLinks(String taskKey, Set<String> linkKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertDataLinks(taskKey, linkKeys, isFromDesToSys);
    }

    public void deleteDataLinks(Set<String> linkKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteDataLinks(linkKeys, isFromDesToSys);
    }

    public void insertDataLinks(Set<String> linkKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertDataLinks(linkKeys, isFromDesToSys);
    }

    public void deleteDataLinksByRegion(Set<String> regionKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteDataLinksByRegion(regionKeys, isFromDesToSys);
    }

    public void deleteDataLinksByForm(Set<String> formKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteDataLinksByForm(formKeys, isFromDesToSys);
    }

    public void insertDataLinksByForm(Set<String> formKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertDataLinksByForm(formKeys, isFromDesToSys);
    }

    public void deleteFormulaSchemesByFormScheme(Set<String> formSchemeKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteFormulaSchemesByFormScheme(formSchemeKeys, isFromDesToSys);
    }

    public void insertFormulaSchemesByFormScheme(Set<String> formSchemeKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertFormulaSchemesByFormScheme(formSchemeKeys, isFromDesToSys);
    }

    public void deleteFormulaSchemes(Set<String> formulaSchemeKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteFormulaSchemes(formulaSchemeKeys, isFromDesToSys);
    }

    public void insertFormulaSchemes(Set<String> formulaSchemeKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertFormulaSchemes(formulaSchemeKeys, isFromDesToSys);
    }

    public void deleteFormulas(Set<String> formulaKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteFormulas(formulaKeys, isFromDesToSys);
    }

    public void insertFormulas(Set<String> formulaKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertFormulas(formulaKeys, isFromDesToSys);
    }

    public void deleteFormulasByFormulaScheme(Set<String> formulaSchemeKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteFormulasByFormulaScheme(formulaSchemeKeys, isFromDesToSys);
    }

    public void deleteFormulaVariables(Set<String> formulaVariableKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteFormulaVariables(formulaVariableKeys, isFromDesToSys);
    }

    public void insertFormulaVariables(Set<String> formulaVariableKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertFormulaVariables(formulaVariableKeys, isFromDesToSys);
    }

    public void deletePrintSchemes(Set<String> printSchemeKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deletePrintSchemes(printSchemeKeys, isFromDesToSys);
    }

    public void insertPrintSchemes(Set<String> printSchemeKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertPrintSchemes(printSchemeKeys, isFromDesToSys);
    }

    public void deletePrintSetting(Set<String> printSchemeKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deletePrintSetting(printSchemeKeys, isFromDesToSys);
    }

    public void insertPrintSetting(Set<String> printSchemeKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertPrintSetting(printSchemeKeys, isFromDesToSys);
    }

    public void deletePrintSchemesByFormScheme(Set<String> formSchemeKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deletePrintSchemesByFormScheme(formSchemeKeys, isFromDesToSys);
    }

    public void deletePrintTemplates(Set<String> printTemplateKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deletePrintTemplates(printTemplateKeys, isFromDesToSys);
    }

    public void insertPrintTemplates(Set<String> printTemplateKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertPrintTemplates(printTemplateKeys, isFromDesToSys);
    }

    public void deletePrintTemplatesByPrintScheme(Set<String> printSchemeKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deletePrintTemplatesByPrintScheme(printSchemeKeys, isFromDesToSys);
    }

    public void deleteTaskLinks(Set<String> taskLinks, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteTaskLinks(taskLinks, isFromDesToSys);
    }

    public void insertTaskLinks(Set<String> taskLinks, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertTaskLinks(taskLinks, isFromDesToSys);
    }

    public void deleteTaskLinksByFormScheme(Set<String> formSchemes, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteTaskLinksByFormScheme(formSchemes, isFromDesToSys);
    }

    public void insertTaskLinksByFormScheme(Set<String> formSchemes, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertTaskLinksByFormScheme(formSchemes, isFromDesToSys);
    }

    public void deleteEntityLinkages(Set<String> entityViewKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteEntityLinkages(entityViewKeys, isFromDesToSys);
    }

    public void insertEntityLinkages(Set<String> entityViewKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertEntityLinkages(entityViewKeys, isFromDesToSys);
    }

    public void deleteEnumLinkages(Set<String> entityViewKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteEnumLinkages(entityViewKeys, isFromDesToSys);
    }

    public void insertEnumLinkages(Set<String> entityViewKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertEnumLinkages(entityViewKeys, isFromDesToSys);
    }

    public void deleteRegionSettings(Set<String> regionSettingKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteRegionSettings(regionSettingKeys, isFromDesToSys);
    }

    public void insertRegionSettings(Set<String> regionSettingKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertRegionSettings(regionSettingKeys, isFromDesToSys);
    }

    public List<DataRegionItem> getRegionKeysByForm(String formKey) {
        return this.deployDefinitionDao.getRegionKeysByForm(formKey);
    }

    public List<String> getLinkKeysByRegion(String regionKey) {
        return this.deployDefinitionDao.getLinkKeysByRegion(regionKey);
    }

    public List<String> getFormulaKeysByFormulaScheme(String formulaSchemeKey) {
        return this.deployDefinitionDao.getFormulaKeysByFormulaScheme(formulaSchemeKey);
    }

    public void deleteTableGroup(Set<String> groupKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteTableGroup(groupKeys, isFromDesToSys);
    }

    public void insertTableGroup(Set<String> groupKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertTableGroup(groupKeys, isFromDesToSys);
    }

    public void deleteDataLinkMapping(Set<String> ids, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteDataLinkMapping(ids, isFromDesToSys);
    }

    public void insertDataLinkMapping(Set<String> ids, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertDataLinkMapping(ids, isFromDesToSys);
    }

    public void deleteDimensionFilters(Set<String> ids, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteDimensionFilters(ids, isFromDesToSys);
    }

    public void insertDimensionFilters(Set<String> ids, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertDimensionFilters(ids, isFromDesToSys);
    }

    public void deleteFormulaConditions(Set<String> dSchemeKeys, Set<String> rSchemeKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteFormulaConditions(dSchemeKeys, rSchemeKeys, isFromDesToSys);
    }

    public void insertFormulaConditions(Set<String> dKeys, Set<String> rKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertFormulaConditions(dKeys, rKeys, isFromDesToSys);
    }

    public void deleteFormulaConditionLinks(Set<String> designTimeKeys, Set<String> runTimeKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.deleteFormulaConditionLinks(designTimeKeys, runTimeKeys, isFromDesToSys);
    }

    public void insertFormulaConditionLinks(Set<String> designTimeKeys, Set<String> runTimeKeys, boolean isFromDesToSys) {
        this.deployDefinitionDao.insertFormulaConditionLinks(designTimeKeys, runTimeKeys, isFromDesToSys);
    }

    public void deployFormulaConditionLink(Set<DesignFormulaConditionLink> designLinks, Set<FormulaConditionLink> runLinks) {
        this.deployDefinitionDao.deployFormulaConditionLink(designLinks, runLinks);
    }
}

