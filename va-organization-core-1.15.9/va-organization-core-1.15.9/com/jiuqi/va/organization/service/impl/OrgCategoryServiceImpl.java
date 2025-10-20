/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.DateTimeUtil
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.common.StorageUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.OrgVersionDTO
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.extend.DataModelTemplateEntity
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.organization.service.impl;

import com.jiuqi.va.domain.common.DateTimeUtil;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.common.StorageUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.extend.DataModelTemplateEntity;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableModel;
import com.jiuqi.va.organization.common.OrgAsyncTask;
import com.jiuqi.va.organization.common.OrgCategoryTransUtil;
import com.jiuqi.va.organization.common.OrgCoreI18nUtil;
import com.jiuqi.va.organization.dao.VaOrgCategoryDao;
import com.jiuqi.va.organization.dao.VaOrgVersionDao;
import com.jiuqi.va.organization.domain.OrgCategorySyncCacheDTO;
import com.jiuqi.va.organization.domain.OrgDataSyncCacheDTO;
import com.jiuqi.va.organization.domain.ZBDTO;
import com.jiuqi.va.organization.service.OrgCategoryService;
import com.jiuqi.va.organization.service.OrgVersionService;
import com.jiuqi.va.organization.service.impl.help.OrgCategoryCacheService;
import com.jiuqi.va.organization.service.impl.help.OrgCategoryZbService;
import com.jiuqi.va.organization.service.impl.help.OrgContextService;
import com.jiuqi.va.organization.service.impl.help.OrgDataCacheService;
import com.jiuqi.va.organization.storage.OrgCategoryStorage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service(value="vaOrgCategoryServiceImpl")
public class OrgCategoryServiceImpl
implements OrgCategoryService {
    @Autowired
    private VaOrgCategoryDao orgCategoryDao;
    @Autowired
    private DataModelClient dataModelClient;
    @Autowired
    private OrgVersionService orgVersionService;
    @Autowired
    private VaOrgVersionDao orgVersionDao;
    @Autowired
    private OrgCategoryZbService zbService;
    @Autowired
    private OrgCategoryCacheService orgCategoryCacheService;
    @Autowired
    private OrgAsyncTask orgAsyncTask;
    @Autowired
    private OrgContextService orgContextService;
    private OrgCategoryTransUtil defineTransUtil;
    private String ignoreCheckTableNameLength = "ignoreCheckTableNameLength";
    private OrgDataCacheService orgDataCacheService = null;

    private OrgCategoryTransUtil getOrgCategoryTransUtil() {
        if (this.defineTransUtil == null) {
            this.defineTransUtil = (OrgCategoryTransUtil)ApplicationContextRegister.getBean(OrgCategoryTransUtil.class);
        }
        return this.defineTransUtil;
    }

    private OrgDataCacheService getOrgDataCacheService() {
        if (this.orgDataCacheService == null) {
            this.orgDataCacheService = (OrgDataCacheService)ApplicationContextRegister.getBean(OrgDataCacheService.class);
        }
        return this.orgDataCacheService;
    }

    @Override
    public OrgCategoryDO get(OrgCategoryDO param) {
        Object ltf = param.getExtInfo("languageTransFlag");
        if (ltf != null && ((Boolean)ltf).booleanValue()) {
            param.setDeepClone(true);
        }
        OrgCategoryDO data = null;
        if (StringUtils.hasText(param.getName())) {
            data = this.orgCategoryCacheService.getByName(param);
        } else {
            PageVO<OrgCategoryDO> page = this.list(param);
            if (page != null && page.getTotal() == 1) {
                data = (OrgCategoryDO)page.getRows().get(0);
            }
        }
        if (data != null && ltf != null && ((Boolean)ltf).booleanValue()) {
            this.getOrgCategoryTransUtil().transCategory(data);
        }
        return data;
    }

    @Override
    public PageVO<OrgCategoryDO> list(OrgCategoryDO param) {
        Object ltf = param.getExtInfo("languageTransFlag");
        if (ltf != null && ((Boolean)ltf).booleanValue()) {
            param.setDeepClone(true);
        }
        PageVO page = new PageVO();
        List<Object> rsList = new ArrayList();
        if (StringUtils.hasText(param.getName())) {
            OrgCategoryDO data = this.orgCategoryCacheService.getByName(param);
            if (data != null) {
                rsList.add(data);
            }
        } else {
            rsList = this.orgCategoryCacheService.list(param);
        }
        if (rsList.size() > 1) {
            Collections.sort(rsList, (o1, o2) -> o1.getOrdernum().compareTo(o2.getOrdernum()));
        }
        if (ltf != null && ((Boolean)ltf).booleanValue()) {
            this.getOrgCategoryTransUtil().transCategorys(rsList);
        }
        page.setRows(rsList);
        page.setRs(R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0])));
        page.setTotal(rsList.size());
        return page;
    }

    @Override
    public R add(OrgCategoryDO orgCategoryDO) {
        int flag;
        JTableModel jtm;
        String name = orgCategoryDO.getName();
        if (!"MD_ORG".equals(name) && !name.startsWith("MD_ORG_")) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgcategory.check.name.starts.with", "MD_ORG_"));
        }
        if (this.orgContextService.isCheckTableNameLength() && name.length() > 22 && orgCategoryDO.getExtInfo(this.ignoreCheckTableNameLength) == null) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgcategory.check.name.length.exceed", 22));
        }
        JDialectUtil jDialect = JDialectUtil.getInstance();
        if (jDialect.hasTable(jtm = new JTableModel(orgCategoryDO.getTenantName(), name))) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgcategory.add.physical.table.existed", new Object[0]));
        }
        OrgCategoryDO orgCatDTO = new OrgCategoryDO();
        orgCatDTO.setName(name);
        OrgCategoryDO oldOrgCategory = this.get(orgCatDTO);
        if (oldOrgCategory != null) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgcategory.add.existed", new Object[0]));
        }
        orgCategoryDO.setId(UUID.randomUUID());
        orgCategoryDO.setCreatetime(new Date());
        if ("MD_ORG".equals(name)) {
            orgCategoryDO.setOrdernum(new BigDecimal(0));
        } else {
            orgCategoryDO.setOrdernum(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        }
        List zbs = orgCategoryDO.getZbs();
        if (!zbs.isEmpty()) {
            R crs = this.checkReservedWord(zbs);
            if (crs.getCode() != 0) {
                return crs;
            }
            orgCategoryDO.setExtinfo(JSONUtil.toJSONString((Object)zbs));
        }
        if ((flag = this.orgCategoryDao.insert(orgCategoryDO)) > 0) {
            R crs = this.pushDataModel(orgCategoryDO, null);
            if (crs.getCode() != 0) {
                OrgCategoryDO newDO = new OrgCategoryDO();
                newDO.setId(orgCategoryDO.getId());
                this.orgCategoryDao.delete(newDO);
                return crs;
            }
            this.handleMultiSubList(orgCategoryDO);
            this.initVersionData(orgCategoryDO);
            this.syncCache(false, false, name);
        }
        LogUtil.add((String)"\u673a\u6784\u7c7b\u578b\u7ba1\u7406", (String)"\u65b0\u589e", (String)"", (String)orgCategoryDO.getName(), (String)(flag > 0 ? "\u65b0\u589e\u6210\u529f" : "\u65b0\u589e\u5931\u8d25"));
        return flag > 0 ? R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0])) : R.error((String)OrgCoreI18nUtil.getMessage("org.error.common.operate", new Object[0]));
    }

    private R checkReservedWord(List<ZB> zbs) {
        JDialectUtil jdUtil = JDialectUtil.getInstance();
        ArrayList<String> reservedWordList = new ArrayList<String>();
        HashSet<String> zbSet = new HashSet<String>();
        String zbName = null;
        for (ZB zb : zbs) {
            zbName = zb.getName();
            if (jdUtil.isReservedWord(zb.getName())) {
                reservedWordList.add(zb.getName());
                continue;
            }
            if (zbName.equalsIgnoreCase("pagination") || zbName.equalsIgnoreCase("authtype") || zbName.equalsIgnoreCase("expression") || zbName.equalsIgnoreCase("versiontitle")) {
                reservedWordList.add(zb.getName());
                continue;
            }
            zbSet.add(zbName.toUpperCase());
        }
        DataModelTemplateEntity template = StorageUtil.getDataModelTemplate((String)"org");
        List columns = template.getTemplateFields();
        for (DataModelColumn dataModelColumn : columns) {
            if (!zbSet.contains(dataModelColumn.getColumnName())) continue;
            reservedWordList.add(dataModelColumn.getColumnName());
        }
        if (!reservedWordList.isEmpty()) {
            StringBuilder reservedWordStr = new StringBuilder();
            for (String reservedWord : reservedWordList) {
                reservedWordStr.append(",").append(reservedWord);
            }
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgcategory.field.check.reserved", reservedWordStr.substring(1)));
        }
        return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
    }

    private R pushDataModel(OrgCategoryDO orgCategoryDO, Set<String> removeZB) {
        DataModelDO dataModelDO = new DataModelDO();
        dataModelDO.setTenantName(orgCategoryDO.getTenantName());
        dataModelDO.setGroupcode("system");
        dataModelDO.setBiztype(DataModelType.BizType.BASEDATA);
        dataModelDO.setName(orgCategoryDO.getName());
        dataModelDO.setTitle(orgCategoryDO.getTitle());
        if (orgCategoryDO.getExtInfo(this.ignoreCheckTableNameLength) != null) {
            dataModelDO.addExtInfo(this.ignoreCheckTableNameLength, orgCategoryDO.getExtInfo(this.ignoreCheckTableNameLength));
        }
        HashMap<String, DataModelColumn> columnMap = new HashMap<String, DataModelColumn>();
        DataModelTemplateEntity template = StorageUtil.getDataModelTemplate((String)"org", (String)orgCategoryDO.getName());
        List columns = template.getTemplateFields();
        for (DataModelColumn column : columns) {
            columnMap.put(column.getColumnName(), column);
        }
        dataModelDO.setColumns(columns);
        List zbs = orgCategoryDO.getZbs();
        HashSet<String> multipleColumn = new HashSet<String>();
        if (!zbs.isEmpty()) {
            DataModelColumn dmColumn = null;
            String zbName = null;
            for (ZB zb : zbs) {
                zbName = zb.getName().toUpperCase();
                if (columnMap.containsKey(zbName)) continue;
                dmColumn = dataModelDO.addColumn(zb.getName()).columnTitle(zb.getTitle());
                columnMap.put(zbName, dmColumn);
                if (zb.getDatatype() == 1) {
                    dmColumn.columnType(DataModelType.ColumnType.UUID).lengths(new Integer[]{36});
                } else if (zb.getDatatype() == 2 || zb.getDatatype() == 7) {
                    if (!StringUtils.hasText(zb.getReltablename())) {
                        dmColumn.columnType(DataModelType.modelTypeToColumnType((int)zb.getDatatype())).lengths(new Integer[]{zb.getPrecision()});
                    } else {
                        if (zb.getReltablename().contains(".")) {
                            dmColumn.columnType(DataModelType.modelTypeToColumnType((int)zb.getDatatype())).lengths(new Integer[]{zb.getPrecision()}).mapping(zb.getReltablename()).mappingType(zb.getRelatetype());
                            continue;
                        }
                        if (zb.getRelatetype() == null) {
                            zb.setRelatetype(Integer.valueOf(1));
                        }
                        if (zb.getRelatetype() == 1) {
                            if (zb.getPrecision() < 200) {
                                zb.setPrecision(Integer.valueOf(200));
                            }
                            dmColumn.columnType(DataModelType.modelTypeToColumnType((int)zb.getDatatype())).lengths(new Integer[]{zb.getPrecision()}).mapping(zb.getReltablename() + ".OBJECTCODE").mappingType(zb.getRelatetype());
                        } else if (zb.getRelatetype() == 2) {
                            dmColumn.columnType(DataModelType.modelTypeToColumnType((int)zb.getDatatype())).lengths(new Integer[]{zb.getPrecision()}).mapping(zb.getReltablename() + ".VAL").mappingType(zb.getRelatetype());
                        } else if (zb.getRelatetype() == 4) {
                            dmColumn.columnType(DataModelType.modelTypeToColumnType((int)zb.getDatatype())).lengths(new Integer[]{zb.getPrecision()}).mapping(zb.getReltablename() + ".CODE").mappingType(zb.getRelatetype());
                        }
                    }
                } else if (zb.getDatatype() == 3 || zb.getDatatype() == 4) {
                    dmColumn.columnType(DataModelType.modelTypeToColumnType((int)zb.getDatatype())).lengths(new Integer[]{zb.getPrecision(), zb.getDecimal()});
                } else if (zb.getDatatype() == 5 || zb.getDatatype() == 6) {
                    dmColumn.columnType(DataModelType.modelTypeToColumnType((int)zb.getDatatype()));
                } else if (zb.getDatatype() == 8) {
                    dmColumn.columnType(DataModelType.ColumnType.INTEGER).lengths(new Integer[]{1}).mappingType(Integer.valueOf(0));
                }
                if (StringUtils.hasText(zb.getDefaultVal())) {
                    dmColumn.setDefaultVal(zb.getDefaultVal());
                }
                dmColumn.setNullable(Boolean.valueOf(zb.getRequiredflag() == null || zb.getRequiredflag() == 0));
                if (zb.getMultiple() == null || zb.getMultiple() != 1) continue;
                multipleColumn.add(zb.getName());
            }
            dataModelDO.addExtInfo("multipleColumn", multipleColumn);
        }
        HashMap<String, DataModelColumn> oldColumnMap = new HashMap<String, DataModelColumn>();
        DataModelDTO otrParam = new DataModelDTO();
        otrParam.setTenantName(orgCategoryDO.getTenantName());
        otrParam.setName(orgCategoryDO.getName());
        DataModelDO oldModel = this.dataModelClient.get(otrParam);
        if (oldModel != null) {
            String oldColName = null;
            for (DataModelColumn oldCol : oldModel.getColumns()) {
                oldColName = oldCol.getColumnName();
                oldColumnMap.put(oldColName, oldCol);
                if (oldColName.equals("NAME") || oldColName.equals("SHORTNAME")) {
                    if (((DataModelColumn)columnMap.get(oldColName)).getLengths()[0] >= oldCol.getLengths()[0]) continue;
                    ((DataModelColumn)columnMap.get(oldColName)).setLengths(oldCol.getLengths());
                    continue;
                }
                if (columnMap.containsKey(oldColName) || removeZB != null && removeZB.contains(oldColName)) continue;
                dataModelDO.getColumns().add(oldCol);
            }
        }
        dataModelDO.setIndexConsts(template.getTemplateIndexs());
        R r = this.dataModelClient.pushComplete(dataModelDO);
        if (r.getCode() != 0) {
            return r;
        }
        for (DataModelColumn column : columns) {
            if (!StringUtils.hasText(column.getDefaultVal()) || oldColumnMap.containsKey(column.getColumnName()) && StringUtils.hasText(((DataModelColumn)oldColumnMap.get(column.getColumnName())).getDefaultVal())) continue;
            String tenantName = ShiroUtil.getTenantName();
            OrgDTO param = new OrgDTO();
            param.setTenantName(tenantName);
            param.setCategoryname(orgCategoryDO.getName());
            OrgDataSyncCacheDTO bdsc = new OrgDataSyncCacheDTO();
            bdsc.setTenantName(tenantName);
            bdsc.setOrgDTO(param);
            bdsc.setForceUpdate(true);
            this.orgAsyncTask.execute(() -> this.getOrgDataCacheService().pushSyncMsg(bdsc));
            break;
        }
        return r;
    }

    private void initVersionData(OrgCategoryDO orgCategoryDO) {
        OrgVersionDO orgVersionDO = new OrgVersionDO();
        orgVersionDO.setTenantName(orgCategoryDO.getTenantName());
        orgVersionDO.setCategoryname(orgCategoryDO.getName());
        orgVersionDO.setTitle("-");
        orgVersionDO.setValidtime(new Date(DateTimeUtil.getMinTime()));
        this.orgVersionService.add(orgVersionDO);
    }

    @Override
    public R update(OrgCategoryDO orgCategoryDO) {
        OrgCategoryDO orgCatDTO = new OrgCategoryDO();
        orgCatDTO.setName(orgCategoryDO.getName());
        orgCatDTO.setDeepClone(false);
        OrgCategoryDO oldOrgCategory = this.get(orgCatDTO);
        if (oldOrgCategory == null) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgcategory.update.not.exist", new Object[0]));
        }
        orgCategoryDO.setId(oldOrgCategory.getId());
        orgCategoryDO.setName(oldOrgCategory.getName());
        boolean onlyMove = orgCategoryDO.getExtInfo("onlyMove") != null;
        boolean onlyEditBasicInfo = orgCategoryDO.getExtInfo("onlyEditBasicInfo") != null;
        Set removeZB = null;
        if (onlyEditBasicInfo) {
            if (orgCategoryDO.getVersionflag() != null && orgCategoryDO.getVersionflag().equals(oldOrgCategory.getVersionflag()) && orgCategoryDO.getVersionflag() == 0) {
                OrgVersionDTO orgVersionDTO = new OrgVersionDTO();
                orgVersionDTO.setCategoryname(orgCategoryDO.getName());
                orgVersionDTO.setVersionDate(new Date());
                OrgVersionDO orgVersionDO = this.orgVersionDao.find(orgVersionDTO);
                if (orgVersionDO != null) {
                    Calendar ca = Calendar.getInstance();
                    ca.setTime(orgVersionDO.getValidtime());
                    if (ca.get(1) != 1970) {
                        return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgcategory.update.version.control.off", new Object[0]));
                    }
                }
            }
            orgCategoryDO.setExtinfo(null);
        } else if (!onlyMove) {
            R rs = this.zbService.checkZb(orgCategoryDO, oldOrgCategory);
            if (rs.getCode() != 0) {
                return rs;
            }
            List zbs = orgCategoryDO.getZbs();
            if (!zbs.isEmpty()) {
                R crs = this.checkReservedWord(zbs);
                if (crs.getCode() != 0) {
                    return crs;
                }
                ZB zb2 = null;
                Iterator iterator = zbs.iterator();
                while (iterator.hasNext()) {
                    zb2 = (ZB)iterator.next();
                    if (!zb2.getName().equalsIgnoreCase("ORGCODE")) continue;
                    iterator.remove();
                }
                orgCategoryDO.setExtinfo(JSONUtil.toJSONString((Object)zbs));
            }
            removeZB = (Set)rs.get((Object)"removeZB");
        }
        int flag = this.orgCategoryDao.updateByPrimaryKeySelective(orgCategoryDO);
        if (flag > 0 && !onlyEditBasicInfo) {
            this.handleMultiSubList(orgCategoryDO);
            R r = this.pushDataModel(orgCategoryDO, removeZB);
            flag = r.getCode() == 0 ? 1 : 0;
        }
        if (flag > 0) {
            this.syncCache(false, false, orgCategoryDO.getName());
        }
        LogUtil.add((String)"\u673a\u6784\u7c7b\u578b\u7ba1\u7406", (String)"\u66f4\u65b0", (String)"", (String)orgCategoryDO.getName(), (String)(flag > 0 ? "\u66f4\u65b0\u6210\u529f" : "\u66f4\u65b0\u5931\u8d25"));
        return flag > 0 ? R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0])) : R.error((String)OrgCoreI18nUtil.getMessage("org.error.common.operate", new Object[0]));
    }

    private void handleMultiSubList(OrgCategoryDO orgCategoryDTO) {
        List zbList = orgCategoryDTO.getZbs();
        if (zbList != null && !zbList.isEmpty()) {
            for (ZB zb : zbList) {
                if (zb.getMultiple() == null || zb.getMultiple() != 1) continue;
                OrgCategoryStorage.initDetailTable(orgCategoryDTO.getName(), orgCategoryDTO.getTenantName());
                break;
            }
        }
    }

    @Override
    public R remove(OrgCategoryDO orgCategoryDO) {
        if (this.checkRelated(orgCategoryDO)) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.orgcategory.remove.reference.existed", new Object[0]));
        }
        DataModelDO dataModelDO = new DataModelDO();
        dataModelDO.setName(orgCategoryDO.getName());
        R rs = this.dataModelClient.remove(dataModelDO);
        if (rs.getCode() != 0) {
            return rs;
        }
        int flag = this.orgCategoryDao.delete(orgCategoryDO);
        if (flag > 0) {
            this.syncCache(false, true, orgCategoryDO.getName());
            OrgVersionDO orgVersionParam = new OrgVersionDO();
            orgVersionParam.setCategoryname(orgCategoryDO.getName());
            this.orgVersionDao.delete(orgVersionParam);
        }
        LogUtil.add((String)"\u673a\u6784\u7c7b\u578b\u7ba1\u7406", (String)"\u5220\u9664", (String)"", (String)orgCategoryDO.getName(), (String)(flag > 0 ? "\u5220\u9664\u6210\u529f" : "\u5220\u9664\u5931\u8d25"));
        return flag > 0 ? R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0])) : R.error((String)OrgCoreI18nUtil.getMessage("org.error.common.operate", new Object[0]));
    }

    private boolean checkRelated(OrgCategoryDO orgCategoryDO) {
        String curTableName = orgCategoryDO.getName();
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setDeepClone(Boolean.valueOf(false));
        PageVO dataModels = this.dataModelClient.list(dataModelDTO);
        for (DataModelDO dataModel : dataModels.getRows()) {
            if (dataModel.getName().equalsIgnoreCase(curTableName)) continue;
            for (DataModelColumn column : dataModel.getColumns()) {
                if (!StringUtils.hasText(column.getMapping()) || !column.getMapping().split("\\.")[0].equalsIgnoreCase(curTableName)) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    public List<ZBDTO> listZB(OrgCategoryDO param) {
        return this.zbService.listZB(param);
    }

    @Override
    public R addZB(ZBDTO zbDTO) {
        return this.zbService.addZB(zbDTO);
    }

    @Override
    public R updateZB(ZBDTO param) {
        return this.zbService.updateZB(param);
    }

    @Override
    public R removeZB(List<ZBDTO> objs) {
        return this.zbService.removeZB(objs);
    }

    @Override
    public R moveZB(List<ZBDTO> objs) {
        return this.zbService.moveZB(objs);
    }

    @Override
    public R moveCategory(List<OrgCategoryDO> params) {
        if (params.size() != 2) {
            return R.error((String)OrgCoreI18nUtil.getMessage("org.error.common.operate", new Object[0]));
        }
        OrgCategoryDO param = new OrgCategoryDO();
        PageVO<OrgCategoryDO> page = this.list(param);
        OrgCategoryDO od1 = params.get(0);
        OrgCategoryDO od2 = params.get(1);
        for (OrgCategoryDO orgCategoryDO : page.getRows()) {
            if (orgCategoryDO.getId().equals(od1.getId())) {
                od1.setName(orgCategoryDO.getName());
                od2.setOrdernum(orgCategoryDO.getOrdernum());
            }
            if (!orgCategoryDO.getId().equals(od2.getId())) continue;
            od2.setName(orgCategoryDO.getName());
            od1.setOrdernum(orgCategoryDO.getOrdernum());
        }
        this.orgCategoryDao.updateByPrimaryKeySelective(od1);
        this.orgCategoryDao.updateByPrimaryKeySelective(od2);
        this.syncCache(false, false, od1.getName(), od2.getName());
        return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
    }

    @Override
    public R syncCache(OrgCategoryDO orgCategoryDO) {
        String name = orgCategoryDO.getName();
        this.syncCache(name == null, false, name);
        return R.ok((String)OrgCoreI18nUtil.getMessage("org.success.common.operate", new Object[0]));
    }

    private void syncCache(boolean isForceUpdate, boolean isRemove, String ... categoryNames) {
        OrgCategorySyncCacheDTO ocscd = new OrgCategorySyncCacheDTO();
        ocscd.setTenantName(ShiroUtil.getTenantName());
        if (categoryNames == null) {
            ocscd.setForceUpdate(true);
        } else {
            ocscd.setCategoryNames(categoryNames);
            ocscd.setForceUpdate(isForceUpdate);
            ocscd.setRemove(isRemove);
        }
        this.orgCategoryCacheService.pushSyncMsg(ocscd);
    }
}

