/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 */
package com.jiuqi.nr.task.i18n.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.nr.task.i18n.bean.dto.I18nBaseDTO;
import com.jiuqi.nr.task.i18n.bean.dto.I18nFloatRegionDTO;
import com.jiuqi.nr.task.i18n.bean.dto.I18nFormDTO;
import com.jiuqi.nr.task.i18n.bean.dto.I18nFormGroupDTO;
import com.jiuqi.nr.task.i18n.bean.dto.I18nFormSchemeDTO;
import com.jiuqi.nr.task.i18n.bean.dto.I18nFormulaDTO;
import com.jiuqi.nr.task.i18n.bean.dto.I18nFormulaSchemeDTO;
import com.jiuqi.nr.task.i18n.bean.dto.I18nTaskDTO;
import com.jiuqi.nr.task.i18n.bean.dto.I18nTaskOrgAliasDTO;
import com.jiuqi.nr.task.i18n.common.I18nResourceType;
import com.jiuqi.nr.task.i18n.provider.I18nServiceProvider;
import com.jiuqi.nr.task.i18n.workshop.I18nFloatRegionWorkShop;
import com.jiuqi.nr.task.i18n.workshop.I18nFormGroupWorkShop;
import com.jiuqi.nr.task.i18n.workshop.I18nFormSchemeWorkShop;
import com.jiuqi.nr.task.i18n.workshop.I18nFormWorkShop;
import com.jiuqi.nr.task.i18n.workshop.I18nFormulaSchemeWorkShop;
import com.jiuqi.nr.task.i18n.workshop.I18nFormulaWorkShop;
import com.jiuqi.nr.task.i18n.workshop.I18nTaskOrgAliasWorkShop;
import com.jiuqi.nr.task.i18n.workshop.I18nTaskWorkShop;
import com.jiuqi.nr.task.i18n.workshop.I18nWorkShop;
import java.util.HashMap;
import java.util.Map;

public class I18nWorkShopFactory {
    private static final Map<Integer, I18nWorkShop> workShopMap = new HashMap<Integer, I18nWorkShop>();

    public static I18nWorkShop createWorkShop(I18nResourceType workShopType, I18nServiceProvider serviceProvider) {
        switch (workShopType) {
            case TASK_TITLE: {
                I18nTaskWorkShop taskWorkShop = (I18nTaskWorkShop)workShopMap.get(workShopType.getValue());
                if (taskWorkShop == null) {
                    taskWorkShop = new I18nTaskWorkShop(serviceProvider);
                    workShopMap.put(workShopType.getValue(), taskWorkShop);
                }
                return taskWorkShop;
            }
            case FORM_SCHEME_TITLE: {
                I18nFormSchemeWorkShop formSchemeWorkShop = (I18nFormSchemeWorkShop)workShopMap.get(workShopType.getValue());
                if (formSchemeWorkShop == null) {
                    formSchemeWorkShop = new I18nFormSchemeWorkShop(serviceProvider);
                    workShopMap.put(workShopType.getValue(), formSchemeWorkShop);
                }
                return formSchemeWorkShop;
            }
            case FORM_GROUP_TITLE: {
                I18nFormGroupWorkShop formGroupWorkShop = (I18nFormGroupWorkShop)workShopMap.get(workShopType.getValue());
                if (formGroupWorkShop == null) {
                    formGroupWorkShop = new I18nFormGroupWorkShop(serviceProvider);
                    workShopMap.put(workShopType.getValue(), formGroupWorkShop);
                }
                return formGroupWorkShop;
            }
            case FORMULA_SCHEME_TITLE: {
                I18nFormulaSchemeWorkShop formulaSchemeWorkShop = (I18nFormulaSchemeWorkShop)workShopMap.get(workShopType.getValue());
                if (formulaSchemeWorkShop == null) {
                    formulaSchemeWorkShop = new I18nFormulaSchemeWorkShop(serviceProvider);
                    workShopMap.put(workShopType.getValue(), formulaSchemeWorkShop);
                }
                return formulaSchemeWorkShop;
            }
            case FORMULA_DESC: {
                I18nFormulaWorkShop formulaWorkShop = (I18nFormulaWorkShop)workShopMap.get(workShopType.getValue());
                if (formulaWorkShop == null) {
                    formulaWorkShop = new I18nFormulaWorkShop(serviceProvider);
                    workShopMap.put(workShopType.getValue(), formulaWorkShop);
                }
                return formulaWorkShop;
            }
            case FORM: 
            case FORM_TITLE: 
            case FORM_STYLE: {
                I18nFormWorkShop formWorkShop = (I18nFormWorkShop)workShopMap.get(workShopType.getValue());
                if (formWorkShop == null) {
                    formWorkShop = new I18nFormWorkShop(serviceProvider);
                    workShopMap.put(workShopType.getValue(), formWorkShop);
                }
                return formWorkShop;
            }
            case FLOAT_REGION_TAB_TITLE: {
                I18nFloatRegionWorkShop floatRegionWorkShop = (I18nFloatRegionWorkShop)workShopMap.get(workShopType.getValue());
                if (floatRegionWorkShop == null) {
                    floatRegionWorkShop = new I18nFloatRegionWorkShop(serviceProvider);
                    workShopMap.put(workShopType.getValue(), floatRegionWorkShop);
                }
                return floatRegionWorkShop;
            }
            case TASK_ORG_ALIAS: {
                I18nTaskOrgAliasWorkShop taskOrgAliasWorkShop = (I18nTaskOrgAliasWorkShop)workShopMap.get(workShopType.getValue());
                if (taskOrgAliasWorkShop == null) {
                    taskOrgAliasWorkShop = new I18nTaskOrgAliasWorkShop(serviceProvider);
                    workShopMap.put(workShopType.getValue(), taskOrgAliasWorkShop);
                }
                return taskOrgAliasWorkShop;
            }
        }
        return null;
    }

    public static I18nBaseDTO getTrulyDTO(I18nResourceType resourceType, JsonNode node) {
        switch (resourceType) {
            case TASK_TITLE: {
                return new I18nTaskDTO(node);
            }
            case FORM_SCHEME_TITLE: {
                return new I18nFormSchemeDTO(node);
            }
            case FORM_GROUP_TITLE: {
                return new I18nFormGroupDTO(node);
            }
            case FORMULA_SCHEME_TITLE: {
                return new I18nFormulaSchemeDTO(node);
            }
            case FORMULA_DESC: {
                return new I18nFormulaDTO(node);
            }
            case FORM: {
                return new I18nFormDTO(node);
            }
            case FLOAT_REGION_TAB_TITLE: {
                return new I18nFloatRegionDTO(node);
            }
            case TASK_ORG_ALIAS: {
                return new I18nTaskOrgAliasDTO(node);
            }
        }
        return null;
    }
}

