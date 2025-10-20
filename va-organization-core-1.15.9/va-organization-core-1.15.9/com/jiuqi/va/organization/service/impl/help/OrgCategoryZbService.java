/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.basedata.BaseDataStorageUtil
 *  com.jiuqi.va.domain.common.DataTypeUtil
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.enumdata.EnumDataDTO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.EnumDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableModel
 *  com.jiuqi.va.mapper.dao.CommonDao
 *  com.jiuqi.va.mapper.domain.SqlDTO
 */
package com.jiuqi.va.organization.service.impl.help;

import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.BaseDataStorageUtil;
import com.jiuqi.va.domain.common.DataTypeUtil;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.enumdata.EnumDataDTO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.EnumDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableModel;
import com.jiuqi.va.mapper.dao.CommonDao;
import com.jiuqi.va.mapper.domain.SqlDTO;
import com.jiuqi.va.organization.common.OrgCategoryTransUtil;
import com.jiuqi.va.organization.common.OrgCoreI18nUtil;
import com.jiuqi.va.organization.domain.ZBDTO;
import com.jiuqi.va.organization.service.OrgCategoryService;
import com.jiuqi.va.organization.service.OrgDataService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class OrgCategoryZbService {
    @Autowired
    private CommonDao commonDao;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private EnumDataClient enumDataClient;
    private OrgCategoryService categoryService;
    private OrgDataService orgDataService;
    private OrgCategoryTransUtil defineTransUtil;

    private OrgCategoryService getOrgCategoryService() {
        if (this.categoryService == null) {
            this.categoryService = (OrgCategoryService)ApplicationContextRegister.getBean(OrgCategoryService.class);
        }
        return this.categoryService;
    }

    private OrgDataService getOrgDataService() {
        if (this.orgDataService == null) {
            this.orgDataService = (OrgDataService)ApplicationContextRegister.getBean(OrgDataService.class);
        }
        return this.orgDataService;
    }

    private OrgCategoryTransUtil getOrgCategoryTransUtil() {
        if (this.defineTransUtil == null) {
            this.defineTransUtil = (OrgCategoryTransUtil)ApplicationContextRegister.getBean(OrgCategoryTransUtil.class);
        }
        return this.defineTransUtil;
    }

    public List<ZBDTO> listZB(OrgCategoryDO param) {
        Object ltf;
        if (!StringUtils.hasText(param.getName())) {
            return new ArrayList<ZBDTO>();
        }
        OrgCategoryDO orgCatDTO = new OrgCategoryDO();
        orgCatDTO.setTenantName(param.getTenantName());
        orgCatDTO.setName(param.getName());
        orgCatDTO.setDeepClone(false);
        OrgCategoryDO orgCategoryDTO = this.getOrgCategoryService().get(orgCatDTO);
        ArrayList<ZBDTO> zbDTOs = new ArrayList<ZBDTO>();
        if (orgCategoryDTO != null) {
            List zbs = orgCategoryDTO.getZbs();
            for (ZB zb : zbs) {
                ZBDTO zbDTO = new ZBDTO();
                zbDTO.setOrgcategoryname(orgCatDTO.getName());
                zbDTO.setId(zb.getId());
                zbDTO.setName(zb.getName());
                zbDTO.setTitle(zb.getTitle());
                zbDTO.setDatatype(zb.getDatatype());
                zbDTO.setOrdernum(zb.getOrdernum());
                zbDTO.setRelatetype(zb.getRelatetype());
                zbDTO.setReltablename(zb.getReltablename());
                zbDTO.setPrecision(zb.getPrecision());
                zbDTO.setDecimal(zb.getDecimal());
                zbDTO.setRequiredflag(zb.getRequiredflag());
                zbDTO.setUniqueflag(zb.getUniqueflag());
                zbDTO.setSolidityflag(zb.getSolidityflag());
                zbDTO.setMultiple(zb.getMultiple());
                zbDTO.setDefaultVal(zb.getDefaultVal());
                if (StringUtils.hasText(zb.getReltablename()) && (zb.getRelatetype() == null || zb.getRelatetype() == 1) && zb.getPrecision() < 200) {
                    zbDTO.setPrecision(200);
                }
                zbDTOs.add(zbDTO);
            }
        }
        if ((ltf = param.getExtInfo("languageTransFlag")) != null && ((Boolean)ltf).booleanValue()) {
            this.getOrgCategoryTransUtil().transCategoryShowFields(param.getName(), zbDTOs);
        }
        return zbDTOs;
    }

    public R addZB(ZBDTO zbDTO) {
        zbDTO.setSolidityflag(0);
        if (!StringUtils.hasText(zbDTO.getOrgcategoryname())) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.parameter.missing.category", new Object[0]));
        }
        String zbName = zbDTO.getName();
        JDialectUtil jDialect = JDialectUtil.getInstance();
        if (jDialect.isReservedWord(zbName) || zbName.equalsIgnoreCase("pagination") || zbName.equalsIgnoreCase("authtype") || zbName.equalsIgnoreCase("categoryname") || zbName.equalsIgnoreCase("versiontitle") || zbName.equalsIgnoreCase("expression")) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgcategory.field.check.reserved", zbName));
        }
        List columns = BaseDataStorageUtil.getTemplateFields();
        for (DataModelColumn dataModelColumn : columns) {
            if (!zbName.equalsIgnoreCase(dataModelColumn.getColumnName())) continue;
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgcategory.field.check.existed", zbName));
        }
        OrgCategoryDO orgCatDTO = new OrgCategoryDO();
        orgCatDTO.setName(zbDTO.getOrgcategoryname());
        OrgCategoryDO orgCategoryDTO = this.getOrgCategoryService().get(orgCatDTO);
        if (orgCategoryDTO != null) {
            R checkResult;
            if (orgCategoryDTO.getZbByName(zbName) != null) {
                return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgcategory.field.check.existed", zbName));
            }
            if (zbDTO.getDatatype() == 2 && zbDTO.getRelatetype() != null && StringUtils.hasText(zbDTO.getDefaultVal()) && (checkResult = this.checkRelateDefaultVal(zbDTO)).getCode() != 0) {
                return checkResult;
            }
            zbDTO.setId(UUID.randomUUID());
            orgCategoryDTO.syncZb((ZB)zbDTO);
            return this.getOrgCategoryService().update(orgCategoryDTO);
        }
        return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgcategory.update.not.exist", new Object[0]));
    }

    private R checkRelateDefaultVal(ZBDTO zbDTO) {
        Integer relateType = zbDTO.getRelatetype();
        String relTableName = zbDTO.getReltablename();
        String defaultVal = zbDTO.getDefaultVal();
        int count = 0;
        if (relateType == 1) {
            BaseDataDTO baseDataDTO = new BaseDataDTO();
            baseDataDTO.setTenantName(zbDTO.getTenantName());
            baseDataDTO.setTableName(relTableName);
            baseDataDTO.setObjectcode(defaultVal);
            baseDataDTO.setPagination(Boolean.valueOf(false));
            baseDataDTO.setAuthType(BaseDataOption.AuthType.NONE);
            baseDataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
            PageVO baseDataDOList = this.baseDataClient.list(baseDataDTO);
            count = baseDataDOList.getTotal();
        } else if (relateType == 2) {
            EnumDataDTO enumDataDTO = new EnumDataDTO();
            enumDataDTO.setTenantName(zbDTO.getTenantName());
            enumDataDTO.setBiztype(relTableName);
            enumDataDTO.setVal(defaultVal);
            List enumDataDOList = this.enumDataClient.list(enumDataDTO);
            count = enumDataDOList.size();
        } else if (relateType == 4) {
            OrgDTO orgDTO = new OrgDTO();
            orgDTO.setTenantName(zbDTO.getTenantName());
            orgDTO.setCategoryname(relTableName);
            orgDTO.setCode(defaultVal);
            orgDTO.setPagination(Boolean.valueOf(false));
            orgDTO.setAuthType(OrgDataOption.AuthType.ACCESS);
            orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
            PageVO<OrgDO> orgDOPageVOList = this.getOrgDataService().list(orgDTO);
            count = orgDOPageVOList.getTotal();
        }
        if (count == 0) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgcategory.field.check.defaultValue.invalid", new Object[0]));
        }
        return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
    }

    public R updateZB(ZBDTO param) {
        if (!StringUtils.hasText(param.getOrgcategoryname())) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.parameter.missing.category", new Object[0]));
        }
        OrgCategoryDO orgCatDTO = new OrgCategoryDO();
        orgCatDTO.setName(param.getOrgcategoryname());
        OrgCategoryDO orgCategoryDO = this.getOrgCategoryService().get(orgCatDTO);
        if (orgCategoryDO != null) {
            ZB oldZb = orgCategoryDO.getZbByName(param.getName());
            if (oldZb != null) {
                R checkResult;
                if (param.getDatatype() == 2 && param.getRelatetype() != null && StringUtils.hasText(param.getDefaultVal()) && (checkResult = this.checkRelateDefaultVal(param)).getCode() != 0) {
                    return checkResult;
                }
            } else {
                return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgcategory.field.check.not.exist", param.getName()));
            }
            orgCategoryDO.syncZb((ZB)param);
            return this.getOrgCategoryService().update(orgCategoryDO);
        }
        return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgcategory.update.not.exist", new Object[0]));
    }

    public R removeZB(List<ZBDTO> objs) {
        if (objs == null || objs.isEmpty()) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.parameter.data.empty", new Object[0]));
        }
        String tenantName = ShiroUtil.getTenantName();
        String categoryname = objs.get(0).getOrgcategoryname();
        if (!StringUtils.hasText(categoryname)) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.parameter.missing.category", new Object[0]));
        }
        OrgCategoryDO orgCatDTO = new OrgCategoryDO();
        orgCatDTO.setName(categoryname);
        OrgCategoryDO orgCategoryDO = this.getOrgCategoryService().get(orgCatDTO);
        if (orgCategoryDO == null) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgcategory.update.not.exist", new Object[0]));
        }
        categoryname = orgCategoryDO.getName();
        ZB oldZb = null;
        for (ZBDTO param : objs) {
            oldZb = orgCategoryDO.getZbByName(param.getName());
            if (oldZb == null || oldZb.getSolidityflag() != null && oldZb.getSolidityflag() == 1 || this.checkExistsData(tenantName, categoryname, param.getName())) continue;
            orgCategoryDO.getZbs().remove(oldZb);
        }
        orgCategoryDO.setExtinfo(JSONUtil.toJSONString((Object)orgCategoryDO.getZbs()));
        return this.getOrgCategoryService().update(orgCategoryDO);
    }

    private boolean checkExistsData(String tenantName, String tbName, String colName) {
        String sqlTemp = "select count(0) as cnt from %s where %s is not null";
        SqlDTO sqlDTO = new SqlDTO(tenantName, sqlTemp);
        sqlDTO.setSql(String.format(sqlTemp, tbName, colName));
        try {
            if (this.commonDao.countBySql(sqlDTO) > 0) {
                return true;
            }
        }
        catch (Exception e) {
            return false;
        }
        String subTableName = tbName + "_SUBLIST";
        JDialectUtil jDialect = JDialectUtil.getInstance();
        JTableModel jtm = new JTableModel(tenantName, subTableName);
        if (jDialect.hasTable(jtm)) {
            sqlTemp = "select count(0) as cnt from %s where FIELDNAME = '%s'";
            sqlDTO.setSql(String.format(sqlTemp, subTableName, colName));
            try {
                if (this.commonDao.countBySql(sqlDTO) > 0) {
                    return true;
                }
            }
            catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public R checkZb(OrgCategoryDO orgCategoryDO, OrgCategoryDO oldOrgCategory) {
        List oldZbs = oldOrgCategory.getZbs();
        if (oldZbs.isEmpty()) {
            return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
        }
        HashMap<String, ZB> zbNameSet = new HashMap<String, ZB>();
        List zbs = orgCategoryDO.getZbs();
        for (ZB zb : zbs) {
            zbNameSet.put(zb.getName().toUpperCase(), zb);
        }
        String tenantName = orgCategoryDO.getTenantName();
        String tbName = orgCategoryDO.getName();
        String zbName = null;
        ZB newZB = null;
        HashSet<String> removeZB = new HashSet<String>();
        for (ZB oldZB : oldZbs) {
            zbName = oldZB.getName().toUpperCase();
            if (zbNameSet.containsKey(zbName)) {
                newZB = (ZB)zbNameSet.get(zbName);
                if (!newZB.getId().equals(DataTypeUtil.UUID_EMPTY) && !newZB.getId().equals(oldZB.getId())) {
                    return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgcategory.update.version.changed", new Object[0]));
                }
                newZB.setId(UUID.randomUUID());
                continue;
            }
            if (this.checkExistsData(tenantName, tbName, zbName)) {
                zbs.add(oldZB);
                continue;
            }
            removeZB.add(zbName);
        }
        R rs = R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
        rs.put("removeZB", removeZB);
        return rs;
    }

    public R moveZB(List<ZBDTO> objs) {
        if (objs == null || objs.size() < 2) {
            return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
        }
        String categoryname = objs.get(0).getOrgcategoryname();
        if (!StringUtils.hasText(categoryname)) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.parameter.missing.category", new Object[0]));
        }
        OrgCategoryDO orgCatDTO = new OrgCategoryDO();
        orgCatDTO.setName(categoryname);
        OrgCategoryDO orgCategoryDO = this.getOrgCategoryService().get(orgCatDTO);
        if (orgCategoryDO == null) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgcategory.update.not.exist", new Object[0]));
        }
        int oldSize = orgCategoryDO.getZbs().size();
        if (oldSize != objs.size()) {
            return R.ok((String)OrgCoreI18nUtil.getMessage("org.error.common.operate", new Object[0]));
        }
        List zbs = JSONUtil.parseArray((String)JSONUtil.toJSONString(objs), ZB.class);
        orgCategoryDO.setExtinfo(JSONUtil.toJSONString((Object)zbs));
        orgCategoryDO.addExtInfo("onlyMove", (Object)true);
        return this.getOrgCategoryService().update(orgCategoryDO);
    }
}

