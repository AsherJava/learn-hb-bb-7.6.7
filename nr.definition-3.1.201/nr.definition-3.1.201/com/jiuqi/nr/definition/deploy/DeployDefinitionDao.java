/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 *  com.jiuqi.np.definition.internal.impl.DesignTableGroupDefineImpl
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.definition.deploy;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.np.definition.internal.impl.DesignTableGroupDefineImpl;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.deploy.DataRegionItem;
import com.jiuqi.nr.definition.deploy.FieldDefineItem;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink;
import com.jiuqi.nr.definition.facade.formula.FormulaConditionLink;
import com.jiuqi.nr.definition.internal.dao.RunTimeBigDataTableDao;
import com.jiuqi.nr.definition.internal.impl.DesignBigDataTable;
import com.jiuqi.nr.definition.internal.impl.DesignDataLinkDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignDataLinkMappingDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignDataRegionDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignEntityLinkageDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignEnumLinkageSettingDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import com.jiuqi.nr.definition.internal.impl.DesignFormSchemeDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormulaDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormulaSchemeDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormulaVariableDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignPrintComTemDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignPrintSettingDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignPrintTemplateDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignPrintTemplateSchemeDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignRegionSettingDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignReportTagDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignReportTemplateDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignSchemePeriodLink;
import com.jiuqi.nr.definition.internal.impl.DesignTaskDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignTaskLinkDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignTaskLinkOrgMappingDefineImpl;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormulaDefineImpl;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormulaVariableDefineImp;
import com.jiuqi.nr.definition.internal.impl.formula.DesignFormulaConditionImpl;
import com.jiuqi.nr.definition.internal.impl.formula.DesignFormulaConditionLinkImpl;
import com.jiuqi.nr.definition.internal.service.DesignPrintTemplateDefineService;
import com.jiuqi.nr.definition.internal.service.DesignPrintTemplateSchemeDefineService;
import com.jiuqi.nr.definition.internal.service.TaskOrgLinkService;
import com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage;
import com.jiuqi.nr.definition.paramlanguage.entity.SysParamLanguage;
import com.jiuqi.nr.definition.paramlanguage.service.DefaultLanguageService;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class DeployDefinitionDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DefaultLanguageService defaultLanguageService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private TaskOrgLinkService orgLinkService;
    private static final int MAXINCOUNT = 900;
    private static final String SPLIT_CHAR = ";";
    private static final String DES_TASK_DEFINE = "NR_PARAM_TASK_DES";
    private static final String TASK_DEFINE = "NR_PARAM_TASK";
    private static final String TASK_KEY = "tk_key";
    private static final String FORM_SCHEME_DEFINE = "NR_PARAM_FORMSCHEME";
    private static final String DES_FORM_SCHEME_DEFINE = "NR_PARAM_FORMSCHEME_DES";
    private static final String FORM_SCHEME_TASK_KEY = "fc_task_key";
    private static final String FORM_SCHEME_KEY = "fc_key";
    private static final String FORM_GROUP_DEFINE = "NR_PARAM_FORMGROUP";
    private static final String DES_FORM_GROUP_DEFINE = "NR_PARAM_FORMGROUP_DES";
    private static final String FORM_GROUP_SCHEME_KEY = "fg_form_scheme_key";
    private static final String FORM_GROUP_KEY = "fg_key";
    private static final String FORM_GROUP_LINK = "NR_PARAM_FORMGROUPLINK";
    private static final String DES_FORM_GROUP_LINK = "NR_PARAM_FORMGROUPLINK_DES";
    private static final String FORM_GROUP_LINK_KEY = "fgl_group_key";
    private static final String FORM_DEFINE = "NR_PARAM_FORM";
    private static final String DES_FORM_DEFINE = "NR_PARAM_FORM_DES";
    private static final String FORM_KEY = "fm_key";
    private static final String DATA_REGION_DEFINE = "NR_PARAM_DATAREGION";
    private static final String DES_DATA_REGION_DEFINE = "NR_PARAM_DATAREGION_DES";
    private static final String REGION_FORM_KEY = "dr_form_key";
    private static final String REGION_KEY = "dr_key";
    private static final String REGION_REGION_SETTING = "dr_region_setting";
    private static final String DATA_LINK_DEFINE = "NR_PARAM_DATALINK";
    private static final String DES_DATA_LINK_DEFINE = "NR_PARAM_DATALINK_DES";
    private static final String LINK_REGION_KEY = "dl_region_key";
    private static final String LINK_KEY = "dl_key";
    private static final String LINK_EXPRESSION = "dl_expression";
    private static final String REGION_SETTING = "NR_PARAM_REGIONSETTING";
    private static final String DES_REGION_SETTING = "NR_PARAM_REGIONSETTING_DES";
    private static final String SETTING_KEY = "rs_key";
    private static final String FORMULA_SCHEME_DEFINE = "NR_PARAM_FORMULASCHEME";
    private static final String DES_FORMULA_SCHEME_DEFINE = "NR_PARAM_FORMULASCHEME_DES";
    private static final String FORMULA_SCHEME_FORM_SCHEME_KEY = "fs_form_scheme_key";
    private static final String FORMULA_SCHEME_KEY = "fs_key";
    private static final String FORMULA_DEFINE = "NR_PARAM_FORMULA";
    private static final String DES_FORMULA_DEFINE = "NR_PARAM_FORMULA_DES";
    private static final String FORMULA_FORMULA_SCHEME_KEY = "fl_scheme_key";
    private static final String FORMULA_KEY = "fl_key";
    private static final String FORMULA_VARIABLE_DEFINE = "NR_PARAM_FORMULAVARIABLE";
    private static final String DES_FORMULA_VARIABLE_DEFINE = "NR_PARAM_FORMULAVARIABLE_DES";
    private static final String FORMULA_FORMULA_VARIABLE_SCHEME_KEY = "fa_fs_key";
    private static final String FORMULA_VARIABLE_KEY = "fa_key";
    private static final String BIG_DATA_TABLE = "NR_PARAM_BIGDATATABLE";
    private static final String DES_BIG_DATA_TABLE = "NR_PARAM_BIGDATATABLE_DES";
    private static final String BIG_DATA_KEY = "bd_key";
    private static final String BIG_DATA_CODE = "bd_code";
    private static final String BIG_DATA_LANG = "bd_lang";
    private static final String PRINT_SCHEME_DEFINE = "NR_PARAM_PRINTSCHEME";
    private static final String DES_PRINT_SCHEME_DEFINE = "NR_PARAM_PRINTSCHEME_DES";
    private static final String PRINT_SCHEME_FORM_SCHEME_KEY = "ps_form_scheme_key";
    private static final String PRINT_SCHEME_KEY = "ps_key";
    private static final String PRINT_COMTEM_DEFINE = "NR_PARAM_PRINTCOMTEM";
    private static final String DES_PRINT_COMTEM_DEFINE = "NR_PARAM_PRINTCOMTEM_DES";
    private static final String PRINT_COMTEM_SCHEME_KEY = "PC_PRINT_SCHEME_KEY";
    private static final String PRINT_SETTING_DEFINE = "NR_PARAM_PRINTSETTING";
    private static final String DES_PRINT_SETTING_DEFINE = "NR_PARAM_PRINTSETTING_DES";
    private static final String PRINT_SETTING_SCHEME_KEY = "PS_PRINT_SCHEME_KEY";
    private static final String PRINT_TEMPLATE_DEFINE = "NR_PARAM_PRINTTEMPLATE";
    private static final String DES_PRINT_TEMPLATE_DEFINE = "NR_PARAM_PRINTTEMPLATE_DES";
    private static final String PRINT_TEMPLATE_PRINT_SCHEME_KEY = "pt_print_scheme_key";
    private static final String PRINT_TEMPLATE_KEY = "pt_key";
    private static final String TASK_LINK = "NR_PARAM_TASKLINK";
    private static final String TASK_LINK_ORG_MAPPING = "NR_PARAM_TASKLINK_ORG";
    private static final String DES_TASK_LINK = "NR_PARAM_TASKLINK_DES";
    private static final String DES_TASK_LINK_ORG_MAPPING = "NR_PARAM_TASKLINK_ORG_DES";
    private static final String TASK_LINK_KEY = "tr_key";
    private static final String TASK_LINK_FORM_SCHEME_KEY = "tr_current_form_scheme_key";
    private static final String TASK_LINK_ORG_MAPPING_KEY = "tor_tr_key";
    private static final String ENTITY_LINKAGE = "sys_entitylinkage";
    private static final String DES_ENTITY_LINKAGE = "des_sys_entitylinkage";
    private static final String ENTITY_LINKAGE_KEY = "el_key";
    private static final String ENUM_LINKAGE = "sys_enumlinkage";
    private static final String DES_ENUM_LINKAGE = "des_sys_enumlinkage";
    private static final String ENUM_LINKAGE_KEY = "el_key";
    private static final String DES_FIELD_DEFINE = "des_sys_fielddefine";
    private static final String FIELD_TABLE_KEY = "fd_own_table";
    private static final String FIELD_KEY = "fd_key";
    private static final String FIELD_REF_KEY = "fd_ref_field";
    private static final String DES_TABLE_DEFINE = "des_sys_tabledefine";
    private static final String TABLE_KEY = "td_key";
    private static final String TABLE_BIZKEY = "td_bizkey";
    private static final String TABLE_GROUP = "sys_tablegroup";
    private static final String DES_TABLE_GROUP = "des_sys_tablegroup";
    private static final String TABLE_GROUP_KEY = "tg_key";
    private static final String PARAM_LANGUAGE = "sys_param_language";
    private static final String DES_PARAM_LANGUAGE = "des_param_language";
    private static final String PARAM_LANGUAGE_KEY = "pl_resource_key";
    private static final String SCHEME_PERIOD_LINK = "NR_PARAM_SCHEMEPERIODLINK";
    private static final String SCHEME_PERIOD_LINK_DES = "NR_PARAM_SCHEMEPERIODLINK_DES";
    private static final String SCHEME_PERIOD_LINK_SC = "sp_scheme_key";
    private static final String DATALINK_MAPPING = "NR_PARAM_DATALINKMAPPING";
    private static final String DATALINK_MAPPING_DES = "NR_PARAM_DATALINKMAPPING_DES";
    private static final String DATALINK_MAPPING_ID = "DLM_ID";
    public static final String RPT_TAG_DES = "NR_PARAM_RPT_TAG_DES";
    public static final String RPT_TAG = "NR_PARAM_RPT_TAG";
    public static final String RPT_KEY = "RT_RPT_KEY";
    private static final String RUNTIME_BEGIN = "sys_";
    private static final String DESTIME_BEGIN = "des_";
    private static final String NEW_DESTIME_AFTER = "_DES";
    private static final String NR_PARAM_DIMFILTER = "NR_PARAM_DIMFILTER";
    private static final String NR_PARAM_DIMFILTER_DES = "NR_PARAM_DIMFILTER_DES";
    static final RowMapper<FieldDefineItem> OBJECT_MAPPER = new RowMapper<FieldDefineItem>(){

        public FieldDefineItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            FieldDefineItem fieldDefineItem = new FieldDefineItem();
            int colIndex = 0;
            fieldDefineItem.setOwnTableKey(rs.getString(++colIndex));
            fieldDefineItem.setReferKey(rs.getString(++colIndex));
            return fieldDefineItem;
        }
    };

    public List<FieldDefineItem> getTableKeyByRegion(String regionKey) {
        return this.getObjectsByJoin(regionKey);
    }

    public Set<String> getMasterKeyByTable(Set<String> tableKeys) {
        if (tableKeys.size() == 0) {
            return new HashSet<String>();
        }
        return this.getMasterKeys(tableKeys);
    }

    public Set<String> getReferKeyByMasterKeys(Set<String> masterKeys) {
        if (masterKeys.size() == 0) {
            return new HashSet<String>();
        }
        return this.getReferFieldKeys(masterKeys);
    }

    private Set<String> getReferFieldKeys(Set<String> masterKeys) {
        if (masterKeys.size() > 900) {
            return this.batchGetReferFieldKeys(masterKeys);
        }
        int index = 0;
        StringBuilder masterKeyBuilder = new StringBuilder();
        Object[] argValue = new Object[masterKeys.size()];
        for (String tk : masterKeys) {
            masterKeyBuilder.append("?,");
            argValue[index] = tk;
            ++index;
        }
        masterKeyBuilder.setLength(masterKeyBuilder.length() - 1);
        StringBuilder selectSql = new StringBuilder();
        selectSql.append(" select ");
        selectSql.append(FIELD_REF_KEY);
        selectSql.append(" from ");
        selectSql.append(DES_FIELD_DEFINE);
        selectSql.append(" where ");
        selectSql.append(FIELD_KEY);
        selectSql.append(" in(");
        selectSql.append((CharSequence)masterKeyBuilder);
        selectSql.append(")");
        List rows = this.jdbcTemplate.queryForList(selectSql.toString(), argValue);
        Iterator it = rows.iterator();
        HashSet<String> referFieldKeys = new HashSet<String>();
        while (it.hasNext()) {
            Map userMap = (Map)it.next();
            if (userMap.get(FIELD_REF_KEY) == null) continue;
            referFieldKeys.add((String)userMap.get(FIELD_REF_KEY));
        }
        return referFieldKeys;
    }

    private Set<String> batchGetReferFieldKeys(Set<String> masterKeys) {
        int loopCount = masterKeys.size() % 900 == 0 ? masterKeys.size() / 900 : masterKeys.size() / 900 + 1;
        HashSet<String> resultSet = new HashSet<String>();
        ArrayList<String> masterKeyList = new ArrayList<String>(masterKeys.size());
        masterKeyList.addAll(masterKeys);
        for (int index = 0; index < loopCount; ++index) {
            Set<String> childKeys = this.getLoopKeys(masterKeyList, index * 900, 900);
            resultSet.addAll(this.getReferFieldKeys(childKeys));
        }
        return resultSet;
    }

    private Set<String> getMasterKeys(Set<String> tableKeys) {
        if (tableKeys.size() > 900) {
            return this.batchGetMasterKeys(tableKeys);
        }
        StringBuilder tableKeyBuilder = new StringBuilder();
        Object[] argValue = new Object[tableKeys.size()];
        int index = 0;
        for (String tk : tableKeys) {
            tableKeyBuilder.append("?,");
            argValue[index] = tk;
            ++index;
        }
        tableKeyBuilder.setLength(tableKeyBuilder.length() - 1);
        StringBuilder selectSql = new StringBuilder();
        selectSql.append(" select ");
        selectSql.append(TABLE_BIZKEY);
        selectSql.append(" from ");
        selectSql.append(DES_TABLE_DEFINE);
        selectSql.append(" where ");
        selectSql.append(TABLE_KEY);
        selectSql.append(" in (");
        selectSql.append((CharSequence)tableKeyBuilder);
        selectSql.append(")");
        List rows = this.jdbcTemplate.queryForList(selectSql.toString(), argValue);
        Iterator it = rows.iterator();
        HashSet<String> masterKeys = new HashSet<String>();
        while (it.hasNext()) {
            String[] masterKeyArray;
            Map userMap = (Map)it.next();
            String tk = (String)userMap.get(TABLE_BIZKEY);
            if (StringUtils.isEmpty((String)tk)) continue;
            for (String tka : masterKeyArray = tk.split(SPLIT_CHAR)) {
                masterKeys.add(tka);
            }
        }
        return masterKeys;
    }

    private Set<String> batchGetMasterKeys(Set<String> tableKeys) {
        int loopCount = tableKeys.size() % 900 == 0 ? tableKeys.size() / 900 : tableKeys.size() / 900 + 1;
        HashSet<String> resultSet = new HashSet<String>();
        ArrayList<String> tableList = new ArrayList<String>(tableKeys.size());
        tableList.addAll(tableKeys);
        for (int index = 0; index < loopCount; ++index) {
            Set<String> childKeys = this.getLoopKeys(tableList, index * 900, 900);
            resultSet.addAll(this.getMasterKeys(childKeys));
        }
        return resultSet;
    }

    private Set<String> getLoopKeys(List<String> tableList, int startIndex, int count) {
        HashSet<String> resultSet = new HashSet<String>();
        int tableSize = tableList.size();
        for (int index = 0; index < count && startIndex < tableSize; ++index, ++startIndex) {
            resultSet.add(tableList.get(startIndex));
        }
        return resultSet;
    }

    private List<FieldDefineItem> getObjectsByJoin(String regionKey) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("select ");
        selectSql.append("t.");
        selectSql.append(FIELD_TABLE_KEY);
        selectSql.append(",");
        selectSql.append("t.");
        selectSql.append(FIELD_REF_KEY);
        selectSql.append(" from ");
        selectSql.append(DES_FIELD_DEFINE);
        selectSql.append(" t ");
        selectSql.append(" where ");
        selectSql.append("t.");
        selectSql.append(FIELD_KEY);
        selectSql.append(" in ");
        selectSql.append("(");
        selectSql.append(" select ");
        selectSql.append("o.");
        selectSql.append(LINK_EXPRESSION);
        selectSql.append(" from ");
        selectSql.append(DES_DATA_LINK_DEFINE);
        selectSql.append(" o ");
        selectSql.append(" where ");
        selectSql.append(" o. ");
        selectSql.append(LINK_REGION_KEY);
        selectSql.append("=?");
        selectSql.append(")");
        Object[] args = new Object[]{regionKey};
        List tableIds = this.jdbcTemplate.query(selectSql.toString(), args, OBJECT_MAPPER);
        return tableIds;
    }

    public void deleteTaskDefines(Set<String> taskKeys, boolean isFromDesToSys) {
        List<String> codeValues = Arrays.asList("FLOWSETTING");
        if (isFromDesToSys) {
            this.deleteObjects(TASK_DEFINE, TASK_KEY, taskKeys);
            this.deleteObjects(PARAM_LANGUAGE, PARAM_LANGUAGE_KEY, taskKeys);
            this.deleteBigDataTable(taskKeys, BIG_DATA_TABLE, codeValues, this.defaultLanguageService.getDefaultLanguage());
        } else {
            this.deleteObjects(DES_TASK_DEFINE, TASK_KEY, taskKeys);
            this.deleteObjects(DES_PARAM_LANGUAGE, PARAM_LANGUAGE_KEY, taskKeys);
            this.deleteBigDataTable(taskKeys, DES_BIG_DATA_TABLE, codeValues, this.defaultLanguageService.getDefaultLanguage());
        }
    }

    public void insertTaskDefines(Set<String> taskKeys, boolean isFromDesToSys) {
        List<String> codeValues = Arrays.asList("FLOWSETTING");
        this.insertObjectsFromDesignTime(DesignTaskDefineImpl.class, TASK_KEY, taskKeys, isFromDesToSys);
        this.insertObjectsParamLanguage(DesParamLanguage.class, PARAM_LANGUAGE_KEY, taskKeys, isFromDesToSys);
        this.insertBigDataTable(DesignBigDataTable.class, null, taskKeys, codeValues, this.defaultLanguageService.getDefaultLanguage(), isFromDesToSys);
    }

    public void deleteTaskI18n(Set<String> taskKeys, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            this.deleteObjects(PARAM_LANGUAGE, PARAM_LANGUAGE_KEY, taskKeys);
            this.deleteTaskOrgLinkParamLanguage(PARAM_LANGUAGE, PARAM_LANGUAGE_KEY, taskKeys);
        } else {
            this.deleteObjects(DES_PARAM_LANGUAGE, PARAM_LANGUAGE_KEY, taskKeys);
            this.deleteTaskOrgLinkParamLanguage(DES_PARAM_LANGUAGE, PARAM_LANGUAGE_KEY, taskKeys);
        }
    }

    private void deleteTaskOrgLinkParamLanguage(String tableName, String fieldName, Set<String> taskKeys) {
        Set<String> orgLinkKeys = this.getOrgLinks(taskKeys);
        if (!CollectionUtils.isEmpty(orgLinkKeys)) {
            this.deleteObjects(tableName, fieldName, orgLinkKeys);
        }
    }

    public void insertTaskI18n(Set<String> taskKeys, boolean isFromDesToSys) {
        this.insertObjectsParamLanguage(DesParamLanguage.class, PARAM_LANGUAGE_KEY, taskKeys, isFromDesToSys);
        this.insertTaskOrgLinkParamLanguage(taskKeys, isFromDesToSys);
    }

    private void insertTaskOrgLinkParamLanguage(Set<String> taskKeys, boolean isFromDesToSys) {
        Set<String> orgLinkKeys = this.getOrgLinks(taskKeys);
        this.insertObjectsParamLanguage(DesParamLanguage.class, PARAM_LANGUAGE_KEY, orgLinkKeys, isFromDesToSys);
    }

    private Set<String> getOrgLinks(Set<String> taskKeys) {
        HashSet<String> orgLinkKeys = new HashSet<String>();
        for (String taskKey : taskKeys) {
            List<TaskOrgLinkDefine> orgLinks = this.orgLinkService.getByTask(taskKey);
            if (CollectionUtils.isEmpty(orgLinks)) continue;
            orgLinkKeys.addAll(orgLinks.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet()));
        }
        return orgLinkKeys;
    }

    public void deleteFlowDataByTask(Set<String> taskKeys, boolean isFromDesToSys) {
        List<String> codeValues = Arrays.asList("FLOWSETTING");
        if (isFromDesToSys) {
            this.deleteBigDataTable(taskKeys, BIG_DATA_TABLE, codeValues, this.defaultLanguageService.getDefaultLanguage());
        } else {
            this.deleteBigDataTable(taskKeys, DES_BIG_DATA_TABLE, codeValues, this.defaultLanguageService.getDefaultLanguage());
        }
    }

    public void insertFlowDataByTask(Set<String> taskKeys, boolean isFromDesToSys) {
        List<String> codeValues = Arrays.asList("FLOWSETTING");
        this.insertBigDataTable(DesignBigDataTable.class, null, taskKeys, codeValues, this.defaultLanguageService.getDefaultLanguage(), isFromDesToSys);
    }

    public void deleteFormSchemeDefinesByTask(Set<String> taskKeys, boolean isFromDesToSys) {
        List<String> codeValues = Arrays.asList("FLOWSETTING", "ANALYSIS_PARAM");
        if (isFromDesToSys) {
            String selectSql = this.getFieldByForeignKey(FORM_SCHEME_DEFINE, FORM_SCHEME_KEY, FORM_SCHEME_TASK_KEY);
            this.deleteBigDataTable(selectSql, BIG_DATA_TABLE, taskKeys, codeValues, this.defaultLanguageService.getDefaultLanguage());
            this.deleteObjects(FORM_SCHEME_DEFINE, FORM_SCHEME_TASK_KEY, taskKeys);
        } else {
            String selectSql = this.getFieldByForeignKey(DES_FORM_SCHEME_DEFINE, FORM_SCHEME_KEY, FORM_SCHEME_TASK_KEY);
            this.deleteBigDataTable(selectSql, DES_BIG_DATA_TABLE, taskKeys, codeValues, RunTimeBigDataTableDao.DEFAULT_lAND);
            this.deleteObjects(DES_FORM_SCHEME_DEFINE, FORM_SCHEME_TASK_KEY, taskKeys);
        }
    }

    public void deleteFormSchemeDefines(Set<String> formSchemeKeys, boolean isFromDesToSys) {
        List<String> codeValues = Arrays.asList("FLOWSETTING", "ANALYSIS_PARAM");
        if (isFromDesToSys) {
            this.deleteBigDataTable(formSchemeKeys, BIG_DATA_TABLE, codeValues, this.defaultLanguageService.getDefaultLanguage());
            this.deleteObjects(FORM_SCHEME_DEFINE, FORM_SCHEME_KEY, formSchemeKeys);
            this.deleteObjects(PARAM_LANGUAGE, PARAM_LANGUAGE_KEY, formSchemeKeys);
        } else {
            this.deleteBigDataTable(formSchemeKeys, DES_BIG_DATA_TABLE, codeValues, RunTimeBigDataTableDao.DEFAULT_lAND);
            this.deleteObjects(DES_FORM_SCHEME_DEFINE, FORM_SCHEME_KEY, formSchemeKeys);
            this.deleteObjects(DES_PARAM_LANGUAGE, PARAM_LANGUAGE_KEY, formSchemeKeys);
        }
    }

    public void deleteSchemePeriodLinks(Set<String> formSchemeKeys, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            this.deleteObjects(SCHEME_PERIOD_LINK, SCHEME_PERIOD_LINK_SC, formSchemeKeys);
        } else {
            this.deleteObjects(SCHEME_PERIOD_LINK_DES, SCHEME_PERIOD_LINK_SC, formSchemeKeys);
        }
    }

    public void insertSchemePeriodLinks(Set<String> formSchemeKeys, boolean isFromDesToSys) {
        this.insertObjectsFromDesignTime(DesignSchemePeriodLink.class, SCHEME_PERIOD_LINK_SC, formSchemeKeys, isFromDesToSys);
    }

    public void insertFormSchemeDefines(Set<String> formSchemeKeys, boolean isFromDesToSys) {
        List<String> codeValues = Arrays.asList("FLOWSETTING", "ANALYSIS_PARAM");
        this.insertBigDataTable(DesignBigDataTable.class, null, formSchemeKeys, codeValues, this.defaultLanguageService.getDefaultLanguage(), isFromDesToSys);
        this.insertObjectsFromDesignTime(DesignFormSchemeDefineImpl.class, FORM_SCHEME_KEY, formSchemeKeys, isFromDesToSys);
        this.insertObjectsParamLanguage(DesParamLanguage.class, PARAM_LANGUAGE_KEY, formSchemeKeys, isFromDesToSys);
    }

    public void deleteFormGroupDefinesByFormScheme(Set<String> formSchemeKeys, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            this.deleteObjects(FORM_GROUP_DEFINE, FORM_GROUP_SCHEME_KEY, formSchemeKeys);
        } else {
            this.deleteObjects(DES_FORM_GROUP_DEFINE, FORM_GROUP_SCHEME_KEY, formSchemeKeys);
        }
    }

    public void insertFormGroupDefinesByFormScheme(Set<String> formSchemeKeys, boolean isFromDesToSys) {
        this.insertObjectsFromDesignTime(DesignFormGroupDefineImpl.class, FORM_GROUP_SCHEME_KEY, formSchemeKeys, isFromDesToSys);
    }

    public void deleteFormGroupDefines(Set<String> formGroupKeys, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            this.deleteObjects(FORM_GROUP_DEFINE, FORM_GROUP_KEY, formGroupKeys);
            this.deleteObjects(PARAM_LANGUAGE, PARAM_LANGUAGE_KEY, formGroupKeys);
        } else {
            this.deleteObjects(DES_FORM_GROUP_DEFINE, FORM_GROUP_KEY, formGroupKeys);
            this.deleteObjects(DES_PARAM_LANGUAGE, PARAM_LANGUAGE_KEY, formGroupKeys);
        }
    }

    public void insertFormGroupDefines(Set<String> formGroupKeys, boolean isFromDesToSys) {
        this.insertObjectsFromDesignTime(DesignFormGroupDefineImpl.class, FORM_GROUP_KEY, formGroupKeys, isFromDesToSys);
        this.insertObjectsParamLanguage(DesParamLanguage.class, PARAM_LANGUAGE_KEY, formGroupKeys, isFromDesToSys);
    }

    public void deleteFormGroupLinks(Set<String> formGroupKeys, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            this.deleteObjects(FORM_GROUP_LINK, FORM_GROUP_LINK_KEY, formGroupKeys);
        } else {
            this.deleteObjects(DES_FORM_GROUP_LINK, FORM_GROUP_LINK_KEY, formGroupKeys);
        }
    }

    public void insertFormGroupLinks(Set<String> formGroupKeys, boolean isFromDesToSys) {
        this.insertObjectsFromDesignTime(DesignFormGroupLink.class, FORM_GROUP_LINK_KEY, formGroupKeys, isFromDesToSys);
    }

    public void deleteFormDefines(Set<String> formKeys, boolean isFromDesToSys) {
        List<String> codeValues = Arrays.asList("FORM_DATA", "FILLING_GUIDE", "BIG_SCRIPT_EDITOR", "BIG_SURVEY_DATA", "ANALYSIS_FORM_PARAM");
        if (isFromDesToSys) {
            this.deleteObjects(FORM_DEFINE, FORM_KEY, formKeys);
            this.deleteObjects(PARAM_LANGUAGE, PARAM_LANGUAGE_KEY, formKeys);
            Set<String> fieldKeys = this.getRunFieldIdByFormKeys(formKeys);
            this.deleteObjects(PARAM_LANGUAGE, PARAM_LANGUAGE_KEY, fieldKeys);
            this.deleteBigDataTable(formKeys, BIG_DATA_TABLE, codeValues);
        } else {
            this.deleteObjects(DES_FORM_DEFINE, FORM_KEY, formKeys);
            this.deleteObjects(DES_PARAM_LANGUAGE, PARAM_LANGUAGE_KEY, formKeys);
            Set<String> fieldKeys = this.getFieldIdByFormKeys(formKeys);
            this.deleteObjects(DES_PARAM_LANGUAGE, PARAM_LANGUAGE_KEY, fieldKeys);
            this.deleteBigDataTable(formKeys, DES_BIG_DATA_TABLE, codeValues);
        }
    }

    public void insertFormDefines(Set<String> formKeys, boolean isFromDesToSys) {
        List<String> codeValues = Arrays.asList("FORM_DATA", "FILLING_GUIDE", "BIG_SCRIPT_EDITOR", "BIG_SURVEY_DATA", "ANALYSIS_FORM_PARAM");
        this.insertObjectsFromDesignTime(DesignFormDefineImpl.class, FORM_KEY, formKeys, isFromDesToSys);
        this.insertObjectsParamLanguage(DesParamLanguage.class, PARAM_LANGUAGE_KEY, formKeys, isFromDesToSys);
        Set<String> fieldKeys = this.getFieldIdByFormKeys(formKeys);
        this.insertObjectsParamLanguage(DesParamLanguage.class, PARAM_LANGUAGE_KEY, fieldKeys, isFromDesToSys);
        this.insertBigDataTable(DesignBigDataTable.class, null, formKeys, codeValues, isFromDesToSys);
    }

    public void deleteDataRegionsByForm(Set<String> formKeys, boolean isFromDesToSys) {
        List<String> codeValues = Arrays.asList("REGION_TAB", "REGION_ORDER");
        if (isFromDesToSys) {
            String selectSql = this.getFieldByForeignKey(DATA_REGION_DEFINE, REGION_KEY, REGION_FORM_KEY);
            this.deleteBigDataTable(selectSql, BIG_DATA_TABLE, formKeys, codeValues);
            this.deleteObjects(DATA_REGION_DEFINE, REGION_FORM_KEY, formKeys);
        } else {
            String selectSql = this.getFieldByForeignKey(DES_DATA_REGION_DEFINE, REGION_KEY, REGION_FORM_KEY);
            this.deleteBigDataTable(selectSql, DES_BIG_DATA_TABLE, formKeys, codeValues);
            this.deleteObjects(DES_DATA_REGION_DEFINE, REGION_FORM_KEY, formKeys);
        }
    }

    public void insertDataRegionsByForm(Set<String> formKeys, boolean isFromDesToSys) {
        List<String> codeValues = Arrays.asList("REGION_TAB", "REGION_ORDER");
        String selectSql = this.getFieldByForeignKey(DES_DATA_REGION_DEFINE, REGION_KEY, REGION_FORM_KEY);
        this.insertBigDataTable(DesignBigDataTable.class, selectSql, formKeys, codeValues, isFromDesToSys);
        this.insertObjectsFromDesignTime(DesignDataRegionDefineImpl.class, REGION_FORM_KEY, formKeys, isFromDesToSys);
    }

    public void deleteDataRegions(Set<String> regionKeys, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            this.deleteObjects(DATA_REGION_DEFINE, REGION_KEY, regionKeys);
        } else {
            this.deleteObjects(DES_DATA_REGION_DEFINE, REGION_KEY, regionKeys);
        }
    }

    public void insertDataRegions(Set<String> regionKeys, boolean isFromDesToSys) {
        this.insertObjectsFromDesignTime(DesignDataRegionDefineImpl.class, REGION_KEY, regionKeys, isFromDesToSys);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean batchDelete() {
        DataSource dataSource = this.jdbcTemplate.getDataSource();
        if (null == dataSource) {
            return false;
        }
        try (Connection connection = dataSource.getConnection();){
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            boolean bl = database.isDatabase("DERBY");
            return bl;
        }
        catch (SQLException sQLException) {
            return false;
        }
    }

    public void deleteDataLinks(String taskKey, Set<String> linkKeys, boolean isFromDesToSys) {
        if (StringUtils.isEmpty((String)taskKey) || !isFromDesToSys || this.batchDelete()) {
            this.deleteDataLinks(linkKeys, isFromDesToSys);
        } else {
            this.jdbcTemplate.update("delete from nr_param_bigdatatable where bd_code = 'ATTACHMENT' and bd_lang = 1 and bd_key in (\r\n\tselect k.bd_key from (select t.bd_key from nr_param_bigdatatable t\r\n\t\tleft join nr_param_bigdatatable_des d on t.bd_key = d.bd_key and t.bd_code = d.bd_code and t.bd_lang = d.bd_lang\r\n\t\tleft join nr_param_datalink l on t.bd_key = l.dl_key \r\n\t\tleft join nr_param_dataregion r on l.dl_region_key = r.dr_key \r\n\t\tleft join nr_param_form f on r.dr_form_key = f.fm_key \r\n\t\tleft join nr_param_formscheme s on f.fm_formscheme = s.fc_key \r\n\t\twhere s.fc_task_key = ? and t.bd_code = 'ATTACHMENT' and t.bd_lang = 1 and (t.bd_updatetime <> d.bd_updatetime or d.bd_key is null)\r\n\t) k\r\n)", pss -> pss.setString(1, taskKey));
            this.jdbcTemplate.update("delete from nr_param_datalink where dl_key in (\r\n\tselect k.dl_key from (select l.dl_key from nr_param_datalink l  \r\n\t\tleft join nr_param_datalink_des d on l.dl_key = d.dl_key\r\n\t\tleft join nr_param_dataregion r on l.dl_region_key = r.dr_key \r\n\t\tleft join nr_param_form f on r.dr_form_key = f.fm_key \r\n\t\tleft join nr_param_formscheme s on f.fm_formscheme = s.fc_key \r\n\t\twhere s.fc_task_key = ? and (l.dl_updatetime <> d.dl_updatetime or d.dl_key is null)\r\n\t) k\r\n)", pss -> pss.setString(1, taskKey));
        }
    }

    public void insertDataLinks(String taskKey, Set<String> linkKeys, boolean isFromDesToSys) {
        if (StringUtils.isEmpty((String)taskKey) || !isFromDesToSys || this.batchDelete()) {
            this.insertDataLinks(linkKeys, isFromDesToSys);
        } else {
            this.jdbcTemplate.update("insert into nr_param_datalink\r\nselect d.* from nr_param_datalink_des d\r\nleft join nr_param_datalink l on d.dl_key = l.dl_key\r\nleft join nr_param_dataregion_des r on d.dl_region_key = r.dr_key \r\nleft join nr_param_form_des f on r.dr_form_key = f.fm_key \r\nleft join nr_param_formscheme_des s on f.fm_formscheme = s.fc_key \r\nwhere s.fc_task_key = ? and l.dl_key is null", pss -> pss.setString(1, taskKey));
            this.jdbcTemplate.update("insert into nr_param_bigdatatable\r\nselect d.* from nr_param_bigdatatable_des d \r\nleft join nr_param_bigdatatable t on t.bd_key = d.bd_key and t.bd_code = d.bd_code and t.bd_lang = d.bd_lang\r\nleft join nr_param_datalink_des l on d.bd_key = l.dl_key \r\nleft join nr_param_dataregion_des r on l.dl_region_key = r.dr_key \r\nleft join nr_param_form_des f on r.dr_form_key = f.fm_key \r\nleft join nr_param_formscheme_des s on f.fm_formscheme = s.fc_key \r\nwhere s.fc_task_key = ? and d.bd_code = 'ATTACHMENT' and d.bd_lang = 1 and t.bd_key is null", pss -> pss.setString(1, taskKey));
        }
    }

    public void deleteDataLinks(Set<String> linkKeys, boolean isFromDesToSys) {
        List<String> codeValues = Arrays.asList("ATTACHMENT");
        if (isFromDesToSys) {
            this.deleteBigDataTable(linkKeys, BIG_DATA_TABLE, codeValues, this.defaultLanguageService.getDefaultLanguage());
            this.deleteObjects(DATA_LINK_DEFINE, LINK_KEY, linkKeys);
        } else {
            this.deleteBigDataTable(linkKeys, DES_BIG_DATA_TABLE, codeValues, RunTimeBigDataTableDao.DEFAULT_lAND);
            this.deleteObjects(DES_DATA_LINK_DEFINE, LINK_KEY, linkKeys);
        }
    }

    public void insertDataLinks(Set<String> linkKeys, boolean isFromDesToSys) {
        List<String> codeValues = Arrays.asList("ATTACHMENT");
        this.insertObjectsFromDesignTime(DesignDataLinkDefineImpl.class, LINK_KEY, linkKeys, isFromDesToSys);
        this.insertBigDataTable(DesignBigDataTable.class, null, linkKeys, codeValues, this.defaultLanguageService.getDefaultLanguage(), isFromDesToSys);
    }

    public void deleteDataLinksByRegion(Set<String> regionKeys, boolean isFromDesToSys) {
        List<String> codeValues = Arrays.asList("ATTACHMENT");
        if (isFromDesToSys) {
            String selectSql = this.getFieldByForeignKey(DATA_LINK_DEFINE, LINK_KEY, LINK_REGION_KEY);
            this.deleteBigDataTable(selectSql, BIG_DATA_TABLE, regionKeys, codeValues, this.defaultLanguageService.getDefaultLanguage());
            this.deleteObjects(DATA_LINK_DEFINE, LINK_REGION_KEY, regionKeys);
        } else {
            String selectSql = this.getFieldByForeignKey(DATA_LINK_DEFINE, LINK_KEY, LINK_REGION_KEY);
            this.deleteBigDataTable(selectSql, DES_BIG_DATA_TABLE, regionKeys, codeValues, RunTimeBigDataTableDao.DEFAULT_lAND);
            this.deleteObjects(DES_DATA_LINK_DEFINE, LINK_REGION_KEY, regionKeys);
        }
    }

    public void deleteDataLinksByForm(Set<String> formKeys, boolean isFromDesToSys) {
        List<String> codeValues = Arrays.asList("ATTACHMENT");
        if (isFromDesToSys) {
            String selectSql = this.getFieldByForeignForeignKey(DATA_LINK_DEFINE, LINK_KEY, LINK_REGION_KEY, REGION_KEY, DATA_REGION_DEFINE, REGION_FORM_KEY);
            this.deleteBigDataTable(selectSql, BIG_DATA_TABLE, formKeys, codeValues, this.defaultLanguageService.getDefaultLanguage());
            this.deleteObjectsByForeignKey(DATA_LINK_DEFINE, LINK_REGION_KEY, DATA_REGION_DEFINE, REGION_KEY, REGION_FORM_KEY, formKeys);
        } else {
            String selectSql = this.getFieldByForeignForeignKey(DES_DATA_LINK_DEFINE, LINK_KEY, LINK_REGION_KEY, REGION_KEY, DES_DATA_REGION_DEFINE, REGION_FORM_KEY);
            this.deleteBigDataTable(selectSql, DES_BIG_DATA_TABLE, formKeys, codeValues, this.defaultLanguageService.getDefaultLanguage());
            this.deleteObjectsByForeignKey(DES_DATA_LINK_DEFINE, LINK_REGION_KEY, DES_DATA_REGION_DEFINE, REGION_KEY, REGION_FORM_KEY, formKeys);
        }
    }

    public void insertDataLinksByForm(Set<String> formKeys, boolean isFromDesToSys) {
        List<String> codeValues = Arrays.asList("ATTACHMENT");
        String selectSql = this.getFieldByForeignForeignKey(DES_DATA_LINK_DEFINE, LINK_KEY, LINK_REGION_KEY, REGION_KEY, DES_DATA_REGION_DEFINE, REGION_FORM_KEY);
        this.insertObjectsByForeignKey(DesignDataLinkDefineImpl.class, LINK_REGION_KEY, DATA_REGION_DEFINE, REGION_KEY, REGION_FORM_KEY, formKeys, isFromDesToSys);
        this.insertBigDataTable(DesignBigDataTable.class, selectSql, formKeys, codeValues, this.defaultLanguageService.getDefaultLanguage(), isFromDesToSys);
    }

    public void deleteFormulaSchemesByFormScheme(Set<String> formSchemeKeys, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            this.deleteObjects(FORMULA_SCHEME_DEFINE, FORMULA_SCHEME_FORM_SCHEME_KEY, formSchemeKeys);
        } else {
            this.deleteObjects(DES_FORMULA_SCHEME_DEFINE, FORMULA_SCHEME_FORM_SCHEME_KEY, formSchemeKeys);
        }
    }

    public void insertFormulaSchemesByFormScheme(Set<String> formSchemeKeys, boolean isFromDesToSys) {
        this.insertObjectsFromDesignTime(DesignFormulaSchemeDefineImpl.class, FORMULA_SCHEME_FORM_SCHEME_KEY, formSchemeKeys, isFromDesToSys);
    }

    public void deleteFormulaSchemes(Set<String> formulaSchemeKeys, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            this.deleteObjects(FORMULA_SCHEME_DEFINE, FORMULA_SCHEME_KEY, formulaSchemeKeys);
            this.deleteObjects(PARAM_LANGUAGE, PARAM_LANGUAGE_KEY, formulaSchemeKeys);
        } else {
            this.deleteObjects(DES_FORMULA_SCHEME_DEFINE, FORMULA_SCHEME_KEY, formulaSchemeKeys);
            this.deleteObjects(DES_PARAM_LANGUAGE, PARAM_LANGUAGE_KEY, formulaSchemeKeys);
        }
    }

    public void insertFormulaSchemes(Set<String> formulaSchemeKeys, boolean isFromDesToSys) {
        this.insertObjectsFromDesignTime(DesignFormulaSchemeDefineImpl.class, FORMULA_SCHEME_KEY, formulaSchemeKeys, isFromDesToSys);
        this.insertObjectsParamLanguage(DesParamLanguage.class, PARAM_LANGUAGE_KEY, formulaSchemeKeys, isFromDesToSys);
    }

    public void deleteFormulasByFormulaScheme(Set<String> formulaSchemeKeys, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            this.deleteObjects(FORMULA_DEFINE, FORMULA_FORMULA_SCHEME_KEY, formulaSchemeKeys);
        } else {
            this.deleteObjects(DES_FORMULA_DEFINE, FORMULA_FORMULA_SCHEME_KEY, formulaSchemeKeys);
        }
    }

    public void deleteFormulas(Set<String> formulaKeys, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            this.deleteObjects(FORMULA_DEFINE, FORMULA_KEY, formulaKeys);
            this.deleteObjects(PARAM_LANGUAGE, PARAM_LANGUAGE_KEY, formulaKeys);
        } else {
            this.deleteObjects(DES_FORMULA_DEFINE, FORMULA_KEY, formulaKeys);
            this.deleteObjects(DES_PARAM_LANGUAGE, PARAM_LANGUAGE_KEY, formulaKeys);
        }
    }

    public void insertFormulas(Set<String> formulaKeys, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            this.insertObjectsFromDesignTime(DesignFormulaDefineImpl.class, FORMULA_KEY, formulaKeys, isFromDesToSys);
            this.insertObjectsParamLanguage(DesParamLanguage.class, PARAM_LANGUAGE_KEY, formulaKeys, isFromDesToSys);
        } else {
            this.insertObjectsFromDesignTime(RunTimeFormulaDefineImpl.class, FORMULA_KEY, formulaKeys, isFromDesToSys);
            this.insertObjectsParamLanguage(SysParamLanguage.class, PARAM_LANGUAGE_KEY, formulaKeys, isFromDesToSys);
        }
    }

    public void deleteFormulaVariables(Set<String> formulaVariableKeys, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            this.deleteObjects(FORMULA_VARIABLE_DEFINE, FORMULA_VARIABLE_KEY, formulaVariableKeys);
        } else {
            this.deleteObjects(DES_FORMULA_VARIABLE_DEFINE, FORMULA_VARIABLE_KEY, formulaVariableKeys);
        }
    }

    public void insertFormulaVariables(Set<String> formulaVariableKeys, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            this.insertObjectsFromDesignTime(DesignFormulaVariableDefineImpl.class, FORMULA_VARIABLE_KEY, formulaVariableKeys, isFromDesToSys);
        } else {
            this.insertObjectsFromDesignTime(RunTimeFormulaVariableDefineImp.class, FORMULA_VARIABLE_KEY, formulaVariableKeys, isFromDesToSys);
        }
    }

    public void deletePrintSchemes(Set<String> printSchemeKeys, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            List<String> codeValues = Arrays.asList(DesignPrintTemplateSchemeDefineService.BIG_PRINT_ATTR_DATA, DesignPrintTemplateSchemeDefineService.BIG_PRINT_GATHER_DATA);
            this.deleteBigDataTable(printSchemeKeys, BIG_DATA_TABLE, codeValues, this.defaultLanguageService.getDefaultLanguage());
            this.deleteObjects(PRINT_SCHEME_DEFINE, PRINT_SCHEME_KEY, printSchemeKeys);
            this.deleteObjects(PARAM_LANGUAGE, PARAM_LANGUAGE_KEY, printSchemeKeys);
        } else {
            List<String> codeValues = Arrays.asList(DesignPrintTemplateSchemeDefineService.BIG_PRINT_ATTR_DATA, DesignPrintTemplateSchemeDefineService.BIG_PRINT_GATHER_DATA);
            this.deleteBigDataTable(printSchemeKeys, DES_BIG_DATA_TABLE, codeValues, RunTimeBigDataTableDao.DEFAULT_lAND);
            this.deleteObjects(DES_PRINT_SCHEME_DEFINE, PRINT_SCHEME_KEY, printSchemeKeys);
            this.deleteObjects(DES_PARAM_LANGUAGE, PARAM_LANGUAGE_KEY, printSchemeKeys);
        }
        this.deletePrintComTem(printSchemeKeys, isFromDesToSys);
    }

    public void insertPrintSchemes(Set<String> printSchemeKeys, boolean isFromDesToSys) {
        List<String> codeValues = Arrays.asList(DesignPrintTemplateSchemeDefineService.BIG_PRINT_ATTR_DATA, DesignPrintTemplateSchemeDefineService.BIG_PRINT_GATHER_DATA);
        this.insertBigDataTable(DesignBigDataTable.class, null, printSchemeKeys, codeValues, this.defaultLanguageService.getDefaultLanguage(), isFromDesToSys);
        this.insertObjectsFromDesignTime(DesignPrintTemplateSchemeDefineImpl.class, PRINT_SCHEME_KEY, printSchemeKeys, isFromDesToSys);
        this.insertObjectsParamLanguage(DesParamLanguage.class, PARAM_LANGUAGE_KEY, printSchemeKeys, isFromDesToSys);
        this.insertPrintComTem(printSchemeKeys, isFromDesToSys);
    }

    public void deletePrintComTem(Set<String> printSchemeKeys, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            this.deleteBigDataTable(printSchemeKeys, BIG_DATA_TABLE, Collections.singletonList("PRINTS_COMTEM_DATA"), this.defaultLanguageService.getDefaultLanguage());
            this.deleteObjects(PRINT_COMTEM_DEFINE, PRINT_COMTEM_SCHEME_KEY, printSchemeKeys);
        } else {
            this.deleteBigDataTable(printSchemeKeys, DES_BIG_DATA_TABLE, Collections.singletonList("PRINTS_COMTEM_DATA"), this.defaultLanguageService.getDefaultLanguage());
            this.deleteObjects(DES_PRINT_COMTEM_DEFINE, PRINT_COMTEM_SCHEME_KEY, printSchemeKeys);
        }
    }

    public void insertPrintComTem(Set<String> printSchemeKeys, boolean isFromDesToSys) {
        this.insertBigDataTable(DesignBigDataTable.class, null, printSchemeKeys, Collections.singletonList("PRINTS_COMTEM_DATA"), this.defaultLanguageService.getDefaultLanguage(), isFromDesToSys);
        this.insertObjectsFromDesignTime(DesignPrintComTemDefineImpl.class, PRINT_COMTEM_SCHEME_KEY, printSchemeKeys, isFromDesToSys);
    }

    public void deletePrintSchemesByFormScheme(Set<String> formSchemeKeys, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            List<String> codeValues = Arrays.asList(DesignPrintTemplateSchemeDefineService.BIG_PRINT_ATTR_DATA, DesignPrintTemplateSchemeDefineService.BIG_PRINT_GATHER_DATA);
            String selectSql = this.getFieldByForeignKey(PRINT_SCHEME_DEFINE, PRINT_SCHEME_KEY, PRINT_SCHEME_FORM_SCHEME_KEY);
            this.deleteBigDataTable(selectSql, BIG_DATA_TABLE, formSchemeKeys, codeValues, this.defaultLanguageService.getDefaultLanguage());
            this.deleteObjects(PRINT_SCHEME_DEFINE, PRINT_SCHEME_FORM_SCHEME_KEY, formSchemeKeys);
        } else {
            List<String> codeValues = Arrays.asList(DesignPrintTemplateSchemeDefineService.BIG_PRINT_ATTR_DATA, DesignPrintTemplateSchemeDefineService.BIG_PRINT_GATHER_DATA);
            String selectSql = this.getFieldByForeignKey(DES_PRINT_SCHEME_DEFINE, PRINT_SCHEME_KEY, PRINT_SCHEME_FORM_SCHEME_KEY);
            this.deleteBigDataTable(selectSql, DES_BIG_DATA_TABLE, formSchemeKeys, codeValues, RunTimeBigDataTableDao.DEFAULT_lAND);
            this.deleteObjects(DES_PRINT_SCHEME_DEFINE, PRINT_SCHEME_FORM_SCHEME_KEY, formSchemeKeys);
        }
    }

    public void deletePrintSetting(Set<String> printSchemeKeys, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            this.deleteObjects(PRINT_SETTING_DEFINE, PRINT_SETTING_SCHEME_KEY, printSchemeKeys);
        } else {
            this.deleteObjects(DES_PRINT_SETTING_DEFINE, PRINT_SETTING_SCHEME_KEY, printSchemeKeys);
        }
    }

    public void insertPrintSetting(Set<String> printSchemeKeys, boolean isFromDesToSys) {
        this.insertObjectsFromDesignTime(DesignPrintSettingDefineImpl.class, PRINT_SETTING_SCHEME_KEY, printSchemeKeys, isFromDesToSys);
    }

    public void deletePrintTemplates(Set<String> printTemplateKeys, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            List<String> codeValues = Arrays.asList(DesignPrintTemplateDefineService.BIG_PRINT_LABLE_DATA, DesignPrintTemplateDefineService.BIG_PRINT_TEMPLATE_DATA);
            this.deleteBigDataTable(printTemplateKeys, BIG_DATA_TABLE, codeValues, this.defaultLanguageService.getDefaultLanguage());
            this.deleteObjects(PRINT_TEMPLATE_DEFINE, PRINT_TEMPLATE_KEY, printTemplateKeys);
        } else {
            List<String> codeValues = Arrays.asList(DesignPrintTemplateDefineService.BIG_PRINT_LABLE_DATA, DesignPrintTemplateDefineService.BIG_PRINT_TEMPLATE_DATA);
            this.deleteBigDataTable(printTemplateKeys, DES_BIG_DATA_TABLE, codeValues, RunTimeBigDataTableDao.DEFAULT_lAND);
            this.deleteObjects(DES_PRINT_TEMPLATE_DEFINE, PRINT_TEMPLATE_KEY, printTemplateKeys);
        }
    }

    public void insertPrintTemplates(Set<String> printTemplateKeys, boolean isFromDesToSys) {
        List<String> codeValues = Arrays.asList(DesignPrintTemplateDefineService.BIG_PRINT_LABLE_DATA, DesignPrintTemplateDefineService.BIG_PRINT_TEMPLATE_DATA);
        this.insertBigDataTable(DesignBigDataTable.class, null, printTemplateKeys, codeValues, this.defaultLanguageService.getDefaultLanguage(), isFromDesToSys);
        this.insertObjectsFromDesignTime(DesignPrintTemplateDefineImpl.class, PRINT_TEMPLATE_KEY, printTemplateKeys, isFromDesToSys);
    }

    public void deletePrintTemplatesByPrintScheme(Set<String> printSchemeKeys, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            List<String> codeValues = Arrays.asList(DesignPrintTemplateDefineService.BIG_PRINT_LABLE_DATA, DesignPrintTemplateDefineService.BIG_PRINT_TEMPLATE_DATA);
            String selectSql = this.getFieldByForeignKey(PRINT_TEMPLATE_DEFINE, PRINT_TEMPLATE_KEY, PRINT_TEMPLATE_PRINT_SCHEME_KEY);
            this.deleteBigDataTable(selectSql, BIG_DATA_TABLE, printSchemeKeys, codeValues, this.defaultLanguageService.getDefaultLanguage());
            this.deleteObjects(PRINT_TEMPLATE_DEFINE, PRINT_TEMPLATE_PRINT_SCHEME_KEY, printSchemeKeys);
        } else {
            List<String> codeValues = Arrays.asList(DesignPrintTemplateDefineService.BIG_PRINT_LABLE_DATA, DesignPrintTemplateDefineService.BIG_PRINT_TEMPLATE_DATA);
            String selectSql = this.getFieldByForeignKey(DES_PRINT_TEMPLATE_DEFINE, PRINT_TEMPLATE_KEY, PRINT_TEMPLATE_PRINT_SCHEME_KEY);
            this.deleteBigDataTable(selectSql, DES_BIG_DATA_TABLE, printSchemeKeys, codeValues, RunTimeBigDataTableDao.DEFAULT_lAND);
            this.deleteObjects(DES_PRINT_TEMPLATE_DEFINE, PRINT_TEMPLATE_PRINT_SCHEME_KEY, printSchemeKeys);
        }
    }

    public void deleteTaskLinks(Set<String> taskLinkKeys, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            this.deleteObjects(TASK_LINK, TASK_LINK_KEY, taskLinkKeys);
            this.deleteObjects(TASK_LINK_ORG_MAPPING, TASK_LINK_ORG_MAPPING_KEY, taskLinkKeys);
        } else {
            this.deleteObjects(DES_TASK_LINK, TASK_LINK_KEY, taskLinkKeys);
        }
    }

    public void insertTaskLinks(Set<String> taskLinkKeys, boolean isFromDesToSys) {
        this.insertObjectsFromDesignTime(DesignTaskLinkDefineImpl.class, TASK_LINK_KEY, taskLinkKeys, isFromDesToSys);
        this.insertObjectsFromDesignTime(DesignTaskLinkOrgMappingDefineImpl.class, TASK_LINK_ORG_MAPPING_KEY, taskLinkKeys, isFromDesToSys);
    }

    public void deleteTaskLinksByFormScheme(Set<String> formSchemes, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            this.deleteObjects(TASK_LINK, TASK_LINK_FORM_SCHEME_KEY, formSchemes);
        } else {
            this.deleteObjects(TASK_LINK, TASK_LINK_FORM_SCHEME_KEY, formSchemes);
        }
    }

    public void insertTaskLinksByFormScheme(Set<String> formSchemes, boolean isFromDesToSys) {
        this.insertObjectsFromDesignTime(DesignTaskLinkDefineImpl.class, TASK_LINK_FORM_SCHEME_KEY, formSchemes, isFromDesToSys);
    }

    public void deleteEntityLinkages(Set<String> entityViewKeys, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            this.deleteObjects(ENTITY_LINKAGE, "el_key", entityViewKeys);
        } else {
            this.deleteObjects(DES_ENTITY_LINKAGE, "el_key", entityViewKeys);
        }
    }

    public void insertEntityLinkages(Set<String> entityViewKeys, boolean isFromDesToSys) {
        this.insertObjectsFromDesignTime(DesignEntityLinkageDefineImpl.class, "el_key", entityViewKeys, isFromDesToSys);
    }

    public void deleteEnumLinkages(Set<String> entityViewKeys, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            this.deleteObjects(ENUM_LINKAGE, "el_key", entityViewKeys);
        } else {
            this.deleteObjects(DES_ENUM_LINKAGE, "el_key", entityViewKeys);
        }
    }

    public void insertEnumLinkages(Set<String> entityViewKeys, boolean isFromDesToSys) {
        this.insertObjectsFromDesignTime(DesignEnumLinkageSettingDefineImpl.class, "el_key", entityViewKeys, isFromDesToSys);
    }

    public void deleteRegionSettings(Set<String> regionSettingKeys, boolean isFromDesToSys) {
        List<String> codeValues = Arrays.asList("REGION_TAB", "REGION_ORDER", "BIG_REGION_CARD", "BIG_REGION_SURVEY", "EXTENTSTYLE", "REGION_LT_ROW_STYLES");
        if (isFromDesToSys) {
            this.deleteBigDataTable(regionSettingKeys, BIG_DATA_TABLE, codeValues);
            this.deleteObjects(REGION_SETTING, SETTING_KEY, regionSettingKeys);
        } else {
            this.deleteBigDataTable(regionSettingKeys, DES_BIG_DATA_TABLE, codeValues);
            this.deleteObjects(DES_REGION_SETTING, SETTING_KEY, regionSettingKeys);
        }
    }

    public void insertRegionSettings(Set<String> regionSettingKeys, boolean isFromDesToSys) {
        List<String> codeValues = Arrays.asList("REGION_TAB", "REGION_ORDER", "BIG_REGION_CARD", "BIG_REGION_SURVEY", "EXTENTSTYLE", "REGION_LT_ROW_STYLES");
        this.insertBigDataTable(DesignBigDataTable.class, null, regionSettingKeys, codeValues, isFromDesToSys);
        this.insertObjectsFromDesignTime(DesignRegionSettingDefineImpl.class, SETTING_KEY, regionSettingKeys, isFromDesToSys);
    }

    public List<DataRegionItem> getRegionKeysByForm(String formKey) {
        List<String> fieldNames = Arrays.asList(REGION_KEY, REGION_REGION_SETTING);
        String selectSql = this.getSelectSql(DES_DATA_REGION_DEFINE, fieldNames, REGION_FORM_KEY);
        Object[] paramValue = new Object[]{formKey.toString()};
        RowMapper rowMapper = (rs, rowNum) -> {
            DataRegionItem regionItem = new DataRegionItem();
            String regionKey = rs.getString(1);
            regionItem.setRegionKey(regionKey);
            String regionSettingKey = rs.getString(2);
            if (!StringUtils.isEmpty((String)regionSettingKey)) {
                regionItem.setRegionSettingKey(regionSettingKey);
            }
            return regionItem;
        };
        List regionItems = this.jdbcTemplate.query(selectSql.toString(), paramValue, rowMapper);
        return regionItems;
    }

    public List<String> getLinkKeysByRegion(String regionKey) {
        return this.getKeysByForeignKey(DES_DATA_LINK_DEFINE, LINK_KEY, LINK_REGION_KEY, regionKey);
    }

    public List<String> getFormulaKeysByFormulaScheme(String formulaSchemeKey) {
        return this.getKeysByForeignKey(DES_FORMULA_DEFINE, FORMULA_KEY, FORMULA_FORMULA_SCHEME_KEY, formulaSchemeKey);
    }

    private List<String> getKeysByForeignKey(String tableName, String keyField, String foreignField, String foreignKey) {
        String selectSql = this.getSelectSql(tableName, keyField, foreignField);
        Object[] paramValue = new Object[]{foreignKey};
        List fieldKeys = this.jdbcTemplate.query(selectSql.toString(), paramValue, this.getRowMapper());
        return fieldKeys;
    }

    private String getSelectSql(String tableName, String keyField, String foreignField) {
        List<String> fieldNames = Arrays.asList(keyField);
        return this.getSelectSql(tableName, fieldNames, foreignField);
    }

    private String getSelectSql(String tableName, List<String> fieldNames, String foreignField) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("select ");
        boolean addDot = false;
        for (String fieldName : fieldNames) {
            if (addDot) {
                selectSql.append(",");
            }
            addDot = true;
            selectSql.append(fieldName);
        }
        selectSql.append(" from ");
        selectSql.append(tableName);
        selectSql.append(" where ");
        selectSql.append(foreignField);
        selectSql.append("=?");
        return selectSql.toString();
    }

    protected RowMapper<String> getRowMapper() {
        return (rs, rowNum) -> {
            String dataValue = rs.getString(1);
            return dataValue;
        };
    }

    private void deleteObjects(String tableName, String fieldName, Set<String> fieldValues) {
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append(" delete from ");
        deleteSql.append(tableName);
        deleteSql.append(" where ");
        deleteSql.append(fieldName);
        deleteSql.append("=?");
        List<Object[]> batchArgs = this.getBatchArgs(fieldValues);
        this.jdbcTemplate.batchUpdate(deleteSql.toString(), batchArgs);
    }

    private void insertObjectsFromDesignTime(Class<?> commonTable, String fieldName, Set<String> fieldValues, boolean isFromDesToSys) {
        String tarTable;
        String srcTable;
        DBAnno.DBTable dbTable = commonTable.getAnnotation(DBAnno.DBTable.class);
        String tablename = dbTable.dbTable();
        String runtimeTableName = tablename = tablename.substring(0, tablename.indexOf(NEW_DESTIME_AFTER));
        String destimeTableName = tablename + NEW_DESTIME_AFTER;
        if (isFromDesToSys) {
            srcTable = destimeTableName;
            tarTable = runtimeTableName;
        } else {
            srcTable = runtimeTableName;
            tarTable = destimeTableName;
        }
        StringBuffer insertSQL = new StringBuffer("insert into ");
        StringBuffer selectSQL = new StringBuffer(" select ");
        insertSQL.append(tarTable).append(" (");
        Field[] fields = commonTable.getDeclaredFields();
        int i = 0;
        for (Field field : fields) {
            if (!field.isAnnotationPresent(DBAnno.DBField.class)) continue;
            if (i > 0) {
                insertSQL.append(",");
                selectSQL.append(",");
            }
            DBAnno.DBField fieldAnno = field.getAnnotation(DBAnno.DBField.class);
            insertSQL.append(fieldAnno.dbField());
            selectSQL.append(fieldAnno.dbField());
            ++i;
        }
        if (i > 0) {
            insertSQL.append(") ");
        }
        selectSQL.append(" from ").append(srcTable).append(" ");
        insertSQL.append(selectSQL);
        insertSQL.append(" where ");
        insertSQL.append(fieldName);
        insertSQL.append("=?");
        List<Object[]> batchArgs = this.getBatchArgs(fieldValues);
        this.jdbcTemplate.batchUpdate(insertSQL.toString(), batchArgs, 1000, (ps, args) -> ps.setString(1, (String)args[0]));
    }

    private void insertObjectsParamLanguage(Class<?> commonTable, String fieldName, Set<String> fieldValues, boolean isFromDesToSys) {
        String tarTable;
        String srcTable;
        DBAnno.DBTable dbTable = commonTable.getAnnotation(DBAnno.DBTable.class);
        String tablename = dbTable.dbTable();
        tablename = tablename.substring(RUNTIME_BEGIN.length(), tablename.length());
        String runtimeTableName = RUNTIME_BEGIN + tablename;
        String destimeTableName = DESTIME_BEGIN + tablename;
        if (isFromDesToSys) {
            srcTable = destimeTableName;
            tarTable = runtimeTableName;
        } else {
            srcTable = runtimeTableName;
            tarTable = destimeTableName;
        }
        StringBuffer insertSQL = new StringBuffer("insert into ");
        StringBuffer selectSQL = new StringBuffer(" select ");
        insertSQL.append(tarTable).append(" (");
        Field[] fields = commonTable.getDeclaredFields();
        int i = 0;
        for (Field field : fields) {
            if (!field.isAnnotationPresent(DBAnno.DBField.class)) continue;
            if (i > 0) {
                insertSQL.append(",");
                selectSQL.append(",");
            }
            DBAnno.DBField fieldAnno = field.getAnnotation(DBAnno.DBField.class);
            insertSQL.append(fieldAnno.dbField());
            selectSQL.append(fieldAnno.dbField());
            ++i;
        }
        if (i > 0) {
            insertSQL.append(") ");
        }
        selectSQL.append(" from ").append(srcTable).append(" ");
        insertSQL.append(selectSQL);
        insertSQL.append(" where ");
        insertSQL.append(fieldName);
        insertSQL.append("=?");
        List<Object[]> batchArgs = this.getBatchArgs(fieldValues);
        this.jdbcTemplate.batchUpdate(insertSQL.toString(), batchArgs);
    }

    private Set<String> getFieldIdByFormKeys(Set<String> formKeys) {
        HashSet<String> fieldKeys = new HashSet<String>();
        for (String formKey : formKeys) {
            List<DesignDataLinkDefine> linksInForm = this.designTimeViewController.getAllLinksInForm(formKey);
            for (DesignDataLinkDefine dataLinkDefine : linksInForm) {
                fieldKeys.add(dataLinkDefine.getLinkExpression());
            }
        }
        return fieldKeys;
    }

    private Set<String> getRunFieldIdByFormKeys(Set<String> formKeys) {
        HashSet<String> fieldKeys = new HashSet<String>();
        for (String formKey : formKeys) {
            List<DataLinkDefine> linksInForm = this.runTimeViewController.getAllLinksInForm(formKey);
            for (DataLinkDefine dataLinkDefine : linksInForm) {
                fieldKeys.add(dataLinkDefine.getLinkExpression());
            }
        }
        return fieldKeys;
    }

    private void deleteObjectsByForeignKey(String tableName, String foreignField, String foreignTable, String foreignKeyField, String conditionField, Set<String> foreignValues) {
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append(" delete from ");
        deleteSql.append(tableName);
        deleteSql.append(" where ");
        deleteSql.append(foreignField);
        deleteSql.append(" in (");
        deleteSql.append(" select ");
        deleteSql.append(foreignKeyField);
        deleteSql.append(" from ");
        deleteSql.append(foreignTable);
        deleteSql.append(" where ");
        deleteSql.append(conditionField);
        deleteSql.append("=?");
        deleteSql.append(")");
        List<Object[]> batchArgs = this.getBatchArgs(foreignValues);
        this.jdbcTemplate.batchUpdate(deleteSql.toString(), batchArgs);
    }

    private void insertObjectsByForeignKey(Class<?> commonTable, String foreignField, String foreignTable, String foreignKeyField, String conditionField, Set<String> foreignValues, boolean isFromDesToSys) {
        String tarTable;
        String srcTable;
        DBAnno.DBTable dbTable = commonTable.getAnnotation(DBAnno.DBTable.class);
        String tablename = dbTable.dbTable();
        String runtimeTableName = tablename = tablename.substring(0, tablename.indexOf(NEW_DESTIME_AFTER));
        String destimeTableName = tablename + NEW_DESTIME_AFTER;
        if (isFromDesToSys) {
            srcTable = destimeTableName;
            tarTable = runtimeTableName;
        } else {
            srcTable = runtimeTableName;
            tarTable = destimeTableName;
        }
        StringBuffer insertSQL = new StringBuffer("insert into ");
        StringBuffer selectSQL = new StringBuffer(" select ");
        insertSQL.append(tarTable).append(" (");
        Field[] fields = commonTable.getDeclaredFields();
        int i = 0;
        for (Field field : fields) {
            if (!field.isAnnotationPresent(DBAnno.DBField.class)) continue;
            if (i > 0) {
                insertSQL.append(",");
                selectSQL.append(",");
            }
            DBAnno.DBField fieldAnno = field.getAnnotation(DBAnno.DBField.class);
            insertSQL.append(fieldAnno.dbField());
            selectSQL.append(fieldAnno.dbField());
            ++i;
        }
        if (i > 0) {
            insertSQL.append(") ");
        }
        selectSQL.append(" from ").append(srcTable).append(" ");
        insertSQL.append(selectSQL);
        insertSQL.append(" where ");
        insertSQL.append(foreignField);
        insertSQL.append(" in (");
        insertSQL.append(" select ");
        insertSQL.append(foreignKeyField);
        insertSQL.append(" from ");
        insertSQL.append(foreignTable);
        insertSQL.append(" where ");
        insertSQL.append(conditionField);
        insertSQL.append("=?");
        insertSQL.append(")");
        List<Object[]> batchArgs = this.getBatchArgs(foreignValues);
        this.jdbcTemplate.batchUpdate(insertSQL.toString(), batchArgs);
    }

    private List<Object[]> getBatchArgs(Set<String> formKeys) {
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        for (String formKey : formKeys) {
            String[] params = new String[]{formKey};
            batchArgs.add(params);
        }
        return batchArgs;
    }

    private void insertBigDataTable(Class<?> commonTable, String selectSql, Set<String> keyValues, List<String> codeValues, int langValue, boolean isFromDesToSys) {
        String tarTable;
        String srcTable;
        DBAnno.DBTable dbTable = commonTable.getAnnotation(DBAnno.DBTable.class);
        String tablename = dbTable.dbTable();
        String runtimeTableName = tablename = tablename.substring(0, tablename.indexOf(NEW_DESTIME_AFTER));
        String destimeTableName = tablename + NEW_DESTIME_AFTER;
        if (isFromDesToSys) {
            srcTable = destimeTableName;
            tarTable = runtimeTableName;
        } else {
            srcTable = runtimeTableName;
            tarTable = destimeTableName;
        }
        StringBuffer insertSQL = new StringBuffer("insert into ");
        StringBuffer selectSQL = new StringBuffer(" select ");
        insertSQL.append(tarTable).append(" (");
        Field[] fields = commonTable.getDeclaredFields();
        int i = 0;
        for (Field field : fields) {
            if (!field.isAnnotationPresent(DBAnno.DBField.class)) continue;
            if (i > 0) {
                insertSQL.append(",");
                selectSQL.append(",");
            }
            DBAnno.DBField fieldAnno = field.getAnnotation(DBAnno.DBField.class);
            insertSQL.append(fieldAnno.dbField());
            selectSQL.append(fieldAnno.dbField());
            ++i;
        }
        if (i > 0) {
            insertSQL.append(") ");
        }
        selectSQL.append(" from ").append(srcTable).append(" ");
        insertSQL.append(selectSQL);
        insertSQL.append(" where ");
        insertSQL.append(BIG_DATA_KEY);
        if (selectSql == null) {
            insertSQL.append("=? ");
        } else {
            insertSQL.append(" in (");
            insertSQL.append(selectSql);
            insertSQL.append(") ");
        }
        insertSQL.append("and ");
        insertSQL.append(BIG_DATA_CODE);
        insertSQL.append("=? and ");
        insertSQL.append(BIG_DATA_LANG);
        insertSQL.append("=?");
        List<Object[]> batchArgs = this.getBatchArgs(keyValues, codeValues, langValue);
        this.jdbcTemplate.batchUpdate(insertSQL.toString(), batchArgs);
    }

    private void insertBigDataTable(Class<?> commonTable, String selectSql, Set<String> keyValues, List<String> codeValues, boolean isFromDesToSys) {
        String tarTable;
        String srcTable;
        DBAnno.DBTable dbTable = commonTable.getAnnotation(DBAnno.DBTable.class);
        String tablename = dbTable.dbTable();
        String runtimeTableName = tablename = tablename.substring(0, tablename.indexOf(NEW_DESTIME_AFTER));
        String destimeTableName = tablename + NEW_DESTIME_AFTER;
        if (isFromDesToSys) {
            srcTable = destimeTableName;
            tarTable = runtimeTableName;
        } else {
            srcTable = runtimeTableName;
            tarTable = destimeTableName;
        }
        StringBuffer insertSQL = new StringBuffer("insert into ");
        StringBuffer selectSQL = new StringBuffer(" select ");
        insertSQL.append(tarTable).append(" (");
        Field[] fields = commonTable.getDeclaredFields();
        int i = 0;
        for (Field field : fields) {
            if (!field.isAnnotationPresent(DBAnno.DBField.class)) continue;
            if (i > 0) {
                insertSQL.append(",");
                selectSQL.append(",");
            }
            DBAnno.DBField fieldAnno = field.getAnnotation(DBAnno.DBField.class);
            insertSQL.append(fieldAnno.dbField());
            selectSQL.append(fieldAnno.dbField());
            ++i;
        }
        if (i > 0) {
            insertSQL.append(") ");
        }
        selectSQL.append(" from ").append(srcTable).append(" ");
        insertSQL.append(selectSQL);
        insertSQL.append(" where ");
        insertSQL.append(BIG_DATA_KEY);
        if (selectSql == null) {
            insertSQL.append("=? ");
        } else {
            insertSQL.append(" in (");
            insertSQL.append(selectSql);
            insertSQL.append(") ");
        }
        insertSQL.append("and ");
        insertSQL.append(BIG_DATA_CODE);
        insertSQL.append("=?");
        List<Object[]> batchArgs = this.getBatchArgs(keyValues, codeValues);
        this.jdbcTemplate.batchUpdate(insertSQL.toString(), batchArgs);
    }

    private void deleteBigDataTable(Set<String> keyValues, String tableName, List<String> codeValues, int langValue) {
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append(" delete from ");
        deleteSql.append(tableName);
        deleteSql.append(" where ");
        deleteSql.append(BIG_DATA_KEY);
        deleteSql.append("=? and ");
        deleteSql.append(BIG_DATA_CODE);
        deleteSql.append("=? and ");
        deleteSql.append(BIG_DATA_LANG);
        deleteSql.append("=?");
        List<Object[]> batchArgs = this.getBatchArgs(keyValues, codeValues, langValue);
        this.jdbcTemplate.batchUpdate(deleteSql.toString(), batchArgs);
    }

    private void deleteBigDataTable(Set<String> keyValues, String tableName, List<String> codeValues) {
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append(" delete from ");
        deleteSql.append(tableName);
        deleteSql.append(" where ");
        deleteSql.append(BIG_DATA_KEY);
        deleteSql.append("=? and ");
        deleteSql.append(BIG_DATA_CODE);
        deleteSql.append("=?");
        List<Object[]> batchArgs = this.getBatchArgs(keyValues, codeValues);
        this.jdbcTemplate.batchUpdate(deleteSql.toString(), batchArgs);
    }

    private void deleteBigDataTable(String selectSql, String tableName, Set<String> keyValues, List<String> codeValues, int langValue) {
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append(" delete from ");
        deleteSql.append(tableName);
        deleteSql.append(" where ");
        deleteSql.append(BIG_DATA_KEY);
        deleteSql.append(" in (");
        deleteSql.append(selectSql);
        deleteSql.append(") and ");
        deleteSql.append(BIG_DATA_CODE);
        deleteSql.append("=? and ");
        deleteSql.append(BIG_DATA_LANG);
        deleteSql.append("=?");
        List<Object[]> batchArgs = this.getBatchArgs(keyValues, codeValues, langValue);
        this.jdbcTemplate.batchUpdate(deleteSql.toString(), batchArgs);
    }

    private void deleteBigDataTable(String selectSql, String tableName, Set<String> keyValues, List<String> codeValues) {
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append(" delete from ");
        deleteSql.append(tableName);
        deleteSql.append(" where ");
        deleteSql.append(BIG_DATA_KEY);
        deleteSql.append(" in (");
        deleteSql.append(selectSql);
        deleteSql.append(") and ");
        deleteSql.append(BIG_DATA_CODE);
        deleteSql.append("=?");
        List<Object[]> batchArgs = this.getBatchArgs(keyValues, codeValues);
        this.jdbcTemplate.batchUpdate(deleteSql.toString(), batchArgs);
    }

    private String getFieldByForeignKey(String tableName, String fieldName, String foreignField) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("select ");
        selectSql.append(fieldName);
        selectSql.append(" from ");
        selectSql.append(tableName);
        selectSql.append(" where ");
        selectSql.append(foreignField);
        selectSql.append("=?");
        return selectSql.toString();
    }

    private String getFieldByForeignForeignKey(String tableName, String fieldName, String foreignField, String foreignKeyField, String foreignTable, String conditionField) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("select ");
        selectSql.append(fieldName);
        selectSql.append(" from ");
        selectSql.append(tableName);
        selectSql.append(" where ");
        selectSql.append(foreignField);
        selectSql.append(" in (");
        selectSql.append(" select ");
        selectSql.append(foreignKeyField);
        selectSql.append(" from ");
        selectSql.append(foreignTable);
        selectSql.append(" where ");
        selectSql.append(conditionField);
        selectSql.append("=?");
        selectSql.append(")");
        return selectSql.toString();
    }

    private List<Object[]> getBatchArgs(Set<String> keyValues, List<String> codeValues, int langValue) {
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        for (String codeValue : codeValues) {
            for (String keyValue : keyValues) {
                Object[] params = new Object[]{keyValue, codeValue, langValue};
                batchArgs.add(params);
            }
        }
        return batchArgs;
    }

    private List<Object[]> getBatchArgs(Set<String> keyValues, List<String> codeValues) {
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        for (String codeValue : codeValues) {
            for (String keyValue : keyValues) {
                Object[] params = new Object[]{keyValue, codeValue};
                batchArgs.add(params);
            }
        }
        return batchArgs;
    }

    public void deleteTableGroup(Set<String> groupKeys, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            this.deleteObjects(TABLE_GROUP, TABLE_GROUP_KEY, groupKeys);
        } else {
            this.deleteObjects(DES_TABLE_GROUP, TABLE_GROUP_KEY, groupKeys);
        }
    }

    public void insertTableGroup(Set<String> groupKeys, boolean isFromDesToSys) {
        this.insertObjectsFromDesignTime(DesignTableGroupDefineImpl.class, TABLE_GROUP_KEY, groupKeys, isFromDesToSys);
    }

    public void deleteDataLinkMapping(Set<String> ids, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            this.deleteObjects(DATALINK_MAPPING, DATALINK_MAPPING_ID, ids);
        } else {
            this.deleteObjects(DATALINK_MAPPING_DES, DATALINK_MAPPING_ID, ids);
        }
    }

    public void insertDataLinkMapping(Set<String> ids, boolean isFromDesToSys) {
        this.insertObjectsFromDesignTime(DesignDataLinkMappingDefineImpl.class, DATALINK_MAPPING_ID, ids, isFromDesToSys);
    }

    public void deleteReportTemplateDefines(Set<String> templateKeys, boolean isDes) {
        if (isDes) {
            this.deleteObjects("NR_PARAM_REPORT_TEMPLATE_DES", "RT_KEY", templateKeys);
        } else {
            this.deleteObjects("NR_PARAM_REPORT_TEMPLATE", "RT_KEY", templateKeys);
        }
    }

    public void insertReportTemplateDefines(Set<String> templateKeys, boolean isFromDesToSys) {
        this.insertObjectsFromDesignTime(DesignReportTemplateDefineImpl.class, "RT_KEY", templateKeys, isFromDesToSys);
    }

    public void deleteReportTagDefine(Set<String> rptKeys, boolean isDes) {
        if (isDes) {
            this.deleteObjects(RPT_TAG_DES, RPT_KEY, rptKeys);
        } else {
            this.deleteObjects(RPT_TAG, RPT_KEY, rptKeys);
        }
    }

    public void insertReportTagDefine(Set<String> tagKeys, boolean isFromDesToSys) {
        this.insertObjectsFromDesignTime(DesignReportTagDefineImpl.class, RPT_KEY, tagKeys, isFromDesToSys);
    }

    public void deleteDimensionFilters(Set<String> ids, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            this.deleteObjects(NR_PARAM_DIMFILTER, "DF_FORM_SCHEME_KEY", ids);
        } else {
            this.deleteObjects(NR_PARAM_DIMFILTER_DES, "DF_TASK_KEY", ids);
        }
    }

    public void insertDimensionFilters(Set<String> ids, boolean isFromDesToSys) {
        String sql = isFromDesToSys ? "INSERT INTO NR_PARAM_DIMFILTER SELECT T2.DF_KEY, T2.DF_ENTITY_ID, T1.FC_KEY AS DF_FORM_SCHEME_KEY, T2.DF_LIST_TYPE, T2.DF_TYPE, T2.DF_VALUE FROM NR_PARAM_FORMSCHEME_DES T1, NR_PARAM_DIMFILTER_DES T2 WHERE T1.FC_TASK_KEY = T2.DF_TASK_KEY AND T1.FC_KEY = ?" : "INSERT INTO NR_PARAM_DIMFILTER_DES SELECT T2.DF_KEY, T2.DF_ENTITY_ID, T1.FC_TASK_KEY AS DF_TSAK_KEY , T2.DF_LIST_TYPE, T2.DF_TYPE, T2.DF_VALUE FROM NR_PARAM_FORMSCHEME T1, NR_PARAM_DIMFILTER T2 WHERE T1.FC_KEY = T2.DF_FORM_SCHEME_KEY AND T1.FC_KEY = ?";
        this.jdbcTemplate.batchUpdate(sql, this.getBatchArgs(ids));
    }

    public void insertFormulaConditions(Set<String> designs, Set<String> runtimes, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            if (!CollectionUtils.isEmpty(designs)) {
                this.insertObjectsFromDesignTime(DesignFormulaConditionImpl.class, "FC_KEY", designs, isFromDesToSys);
            }
        } else if (!CollectionUtils.isEmpty(runtimes)) {
            this.insertObjectsFromDesignTime(DesignFormulaConditionImpl.class, "FC_KEY", runtimes, isFromDesToSys);
        }
    }

    public void deleteFormulaConditions(Set<String> designs, Set<String> runtimes, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            if (!CollectionUtils.isEmpty(runtimes)) {
                this.deleteObjects("NR_PARAM_FM_CONDITION", "FC_KEY", runtimes);
            }
        } else if (!CollectionUtils.isEmpty(designs)) {
            this.deleteObjects("NR_PARAM_FM_CONDITION_DES", "FC_KEY", designs);
        }
    }

    public void deleteFormulaConditionLinks(Set<String> designKeys, Set<String> timeKeys, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            if (!CollectionUtils.isEmpty(timeKeys)) {
                this.deleteObjects("NR_PARAM_CONDITION_LINK", "CL_FM_KEY", timeKeys);
            }
        } else if (!CollectionUtils.isEmpty(designKeys)) {
            this.deleteObjects("NR_PARAM_CONDITION_LINK_DES", "CL_FM_KEY", designKeys);
        }
    }

    public void insertFormulaConditionLinks(Set<String> designTimeKeys, Set<String> runTimeKeys, boolean isFromDesToSys) {
        if (isFromDesToSys) {
            if (!CollectionUtils.isEmpty(designTimeKeys)) {
                this.insertObjectsFromDesignTime(DesignFormulaConditionLinkImpl.class, "CL_FM_KEY", designTimeKeys, isFromDesToSys);
            }
        } else if (!CollectionUtils.isEmpty(runTimeKeys)) {
            this.insertObjectsFromDesignTime(DesignFormulaConditionLinkImpl.class, "CL_FM_KEY", runTimeKeys, isFromDesToSys);
        }
    }

    public void deployFormulaConditionLink(Set<DesignFormulaConditionLink> designLinks, Set<FormulaConditionLink> runLinks) {
        if (!CollectionUtils.isEmpty(runLinks)) {
            this.jdbcTemplate.batchUpdate("DELETE FROM NR_PARAM_CONDITION_LINK WHERE CL_KEY = ? AND CL_FM_KEY = ? AND CL_FORMULA_SCHEME = ?", runLinks, runLinks.size(), (ps, arg) -> {
                ps.setString(1, arg.getConditionKey());
                ps.setString(2, arg.getFormulaKey());
                ps.setString(3, arg.getFormulaSchemeKey());
            });
        }
        if (!CollectionUtils.isEmpty(designLinks)) {
            this.jdbcTemplate.batchUpdate("INSERT INTO NR_PARAM_CONDITION_LINK (CL_KEY, CL_FM_KEY, CL_FORMULA_SCHEME) VALUES (?,?,?)", designLinks, designLinks.size(), (ps, arg) -> {
                ps.setString(1, arg.getConditionKey());
                ps.setString(2, arg.getFormulaKey());
                ps.setString(3, arg.getFormulaSchemeKey());
            });
        }
    }
}

