/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.context.DataModelSyncCacheDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelIndex
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.feign.util.LogUtil
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableException
 *  com.jiuqi.va.mapper.common.JTableModel
 *  com.jiuqi.va.mapper.dao.CommonDao
 *  com.jiuqi.va.mapper.domain.SqlDTO
 *  com.jiuqi.va.mapper.domain.TableColumnDO
 *  com.jiuqi.va.mapper.jdialect.model.ColumnModel
 *  com.jiuqi.va.mapper.jdialect.model.IndexModel
 */
package com.jiuqi.va.datamodel.service.impl;

import com.jiuqi.va.context.DataModelSyncCacheDTO;
import com.jiuqi.va.datamodel.common.DataModelCoreI18nUtil;
import com.jiuqi.va.datamodel.dao.VaDataModelPublishedDao;
import com.jiuqi.va.datamodel.domain.DataModelPublishedDO;
import com.jiuqi.va.datamodel.service.VaDataModelPublishedService;
import com.jiuqi.va.datamodel.service.impl.help.VaDataModelCacheService;
import com.jiuqi.va.datamodel.service.impl.help.VaDataModelExchangeService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelIndex;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.feign.util.LogUtil;
import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableException;
import com.jiuqi.va.mapper.common.JTableModel;
import com.jiuqi.va.mapper.dao.CommonDao;
import com.jiuqi.va.mapper.domain.SqlDTO;
import com.jiuqi.va.mapper.domain.TableColumnDO;
import com.jiuqi.va.mapper.jdialect.model.ColumnModel;
import com.jiuqi.va.mapper.jdialect.model.IndexModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class VaDataModelPublishedServiceImpl
implements VaDataModelPublishedService {
    private static Logger logger = LoggerFactory.getLogger(VaDataModelPublishedServiceImpl.class);
    private String errorOperate = "datamodel.error.common.operate";
    private String errorCheckLengthExceed = "datamodel.error.definition.check.name.length.exceed";
    private String successOperate = "datamodel.success.common.operate";
    private String keyOldData = "oldData";
    @Autowired
    private VaDataModelPublishedDao dataModelDao;
    @Autowired
    private VaDataModelCacheService cacheService;
    @Autowired
    private CommonDao commonDao;
    @Autowired
    private VaDataModelExchangeService exchangeService;
    @Value(value="${va-datamodel-check-tablename-length:true}")
    private boolean checkTableNameLength = true;

    @Override
    public DataModelDO get(DataModelDTO param) {
        if (StringUtils.hasText(param.getName())) {
            return this.cacheService.getByName(param);
        }
        PageVO<DataModelDO> page = this.list(param);
        if (page != null && page.getTotal() == 1) {
            return (DataModelDO)page.getRows().get(0);
        }
        return null;
    }

    @Override
    public PageVO<DataModelDO> list(DataModelDTO param) {
        String searchKey;
        ArrayList<DataModelDO> rsList = new ArrayList<DataModelDO>();
        if (StringUtils.hasText(param.getName())) {
            DataModelDO dataModelDO = this.cacheService.getByName(param);
            if (dataModelDO != null) {
                rsList.add(dataModelDO);
            }
            return new PageVO(rsList, rsList.size());
        }
        String groupcode = param.getGroupcode();
        if (!StringUtils.hasText(groupcode)) {
            groupcode = null;
        }
        searchKey = (searchKey = param.getSearchKey()) != null && StringUtils.hasText(searchKey.trim()) ? searchKey.trim().toUpperCase() : null;
        List<DataModelDO> list = this.cacheService.list(param);
        for (DataModelDO dataModelDO : list) {
            if (param.getBiztype() != null && dataModelDO.getBiztype() != param.getBiztype() || groupcode != null && !groupcode.equals(dataModelDO.getGroupcode()) || searchKey != null && !dataModelDO.getName().contains(searchKey) && !dataModelDO.getTitle().contains(searchKey)) continue;
            rsList.add(dataModelDO);
        }
        if (rsList.size() > 1) {
            Collections.sort(rsList, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        }
        return new PageVO(rsList, rsList.size());
    }

    private int modify(DataModelDO dataModelDO, DataModelDO oldData) {
        int cnt = 0;
        DataModelPublishedDO newData = this.convertDefineJson(dataModelDO);
        newData.setVer(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        newData.setModifytime(new Date());
        if (ShiroUtil.getUser() != null) {
            newData.setModifyuser(ShiroUtil.getUser().getId());
        }
        if (oldData == null) {
            newData.setId(UUID.randomUUID());
            newData.setCreatetime(newData.getModifytime());
            cnt = this.dataModelDao.insert((Object)newData);
        } else {
            newData.setId(oldData.getId());
            cnt = this.dataModelDao.updateByPrimaryKeySelective((Object)newData);
        }
        if (cnt > 0) {
            this.syncCache(dataModelDO.getTenantName(), newData, false);
            LogUtil.add((String)"\u6570\u636e\u5efa\u6a21", (String)(oldData == null ? "\u521b\u5efa" : "\u66f4\u65b0"), (String)dataModelDO.getTitle(), (String)dataModelDO.getName(), (String)newData.getDefinedata());
        }
        return cnt;
    }

    private void syncCache(String tenantName, DataModelPublishedDO newData, boolean isRemove) {
        DataModelSyncCacheDTO dmscd = new DataModelSyncCacheDTO();
        dmscd.setTenantName(tenantName);
        dmscd.setVer(newData.getVer());
        dmscd.setDataModel((DataModelDO)newData);
        dmscd.setRemove(isRemove);
        this.cacheService.pushSyncMsg(dmscd);
        if (!isRemove) {
            try {
                this.exchangeService.exchange(newData);
            }
            catch (Throwable e) {
                logger.error("\u4e0e\u5176\u4ed6\u5e73\u53f0\u4ea4\u4e92\u5efa\u6a21\u4fe1\u606f", e);
            }
        }
    }

    @Override
    public R push(DataModelDO newData) {
        R ckRs = this.moddifyCheck(newData);
        if (ckRs.getCode() != 0) {
            logger.error("\u5efa\u6a21\u5408\u5e76\u53d1\u5e03\u5931\u8d25\uff1a" + newData.getName() + "-" + ckRs.getMsg());
            LogUtil.add((String)"\u6570\u636e\u5efa\u6a21", (String)"\u5408\u5e76\u53d1\u5e03", (String)newData.getTitle(), (String)newData.getName(), (String)ckRs.getMsg());
            return ckRs;
        }
        DataModelDO oldData = ckRs.containsKey((Object)this.keyOldData) ? (DataModelDO)ckRs.get((Object)this.keyOldData) : null;
        R r = this.syncTableStructure(newData, oldData, false);
        if (r.getCode() != 0) {
            return r;
        }
        if (this.modify(newData, oldData) <= 0) {
            return R.error((String)DataModelCoreI18nUtil.getMessage(this.errorOperate, new Object[0]));
        }
        return R.ok((String)DataModelCoreI18nUtil.getMessage(this.successOperate, new Object[0]));
    }

    @Override
    public R updateBaseInfo(DataModelDO dataModelDO) {
        int cnt;
        String tenantName = dataModelDO.getTenantName();
        if (!StringUtils.hasText(dataModelDO.getName())) {
            return R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.parameter.missing", new Object[0]));
        }
        DataModelDTO param = new DataModelDTO();
        param.setTenantName(tenantName);
        param.setName(dataModelDO.getName());
        DataModelDO oldData = this.get(param);
        if (oldData == null) {
            return R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.definition.update.not.found", new Object[0]));
        }
        DataModelPublishedDO newData = new DataModelPublishedDO();
        newData.convert(oldData);
        if (StringUtils.hasText(dataModelDO.getTitle())) {
            newData.setTitle(dataModelDO.getTitle());
        }
        if (StringUtils.hasText(dataModelDO.getGroupcode())) {
            newData.setGroupcode(dataModelDO.getGroupcode());
        }
        newData.setDefinedata(JSONUtil.toJSONString((Object)((Object)newData)));
        newData.setId(oldData.getId());
        newData.setVer(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        newData.setModifytime(new Date());
        if (ShiroUtil.getUser() != null) {
            newData.setModifyuser(ShiroUtil.getUser().getId());
        }
        if ((cnt = this.dataModelDao.updateByPrimaryKeySelective((Object)newData)) <= 0) {
            return R.error((String)DataModelCoreI18nUtil.getMessage(this.errorOperate, new Object[0]));
        }
        this.syncCache(tenantName, newData, false);
        LogUtil.add((String)"\u6570\u636e\u5efa\u6a21", (String)"\u66f4\u65b0", (String)dataModelDO.getTitle(), (String)dataModelDO.getName(), (String)newData.getDefinedata());
        return R.ok((String)DataModelCoreI18nUtil.getMessage(this.successOperate, new Object[0]));
    }

    @Override
    public R pushComplete(DataModelDO dataModelDO) {
        R ckRs = this.moddifyCheck(dataModelDO);
        if (ckRs.getCode() != 0) {
            logger.error("\u5efa\u6a21\u5168\u91cf\u53d1\u5e03\u5931\u8d25\uff1a" + dataModelDO.getName() + "-" + ckRs.getMsg());
            LogUtil.add((String)"\u6570\u636e\u5efa\u6a21", (String)"\u5168\u91cf\u53d1\u5e03", (String)dataModelDO.getTitle(), (String)dataModelDO.getName(), (String)ckRs.getMsg());
            return ckRs;
        }
        DataModelDO oldData = ckRs.containsKey((Object)this.keyOldData) ? (DataModelDO)ckRs.get((Object)this.keyOldData) : null;
        R r = this.syncTableStructure(dataModelDO, oldData, true);
        if (r.getCode() != 0) {
            return r;
        }
        if (this.modify(dataModelDO, oldData) <= 0) {
            return R.error((String)DataModelCoreI18nUtil.getMessage(this.errorOperate, new Object[0]));
        }
        return R.ok((String)DataModelCoreI18nUtil.getMessage(this.successOperate, new Object[0]));
    }

    @Override
    public R pushIncrement(DataModelDO dataModelDO) {
        R ckRs = this.moddifyCheck(dataModelDO);
        if (ckRs.getCode() != 0) {
            logger.error("\u5efa\u6a21\u589e\u91cf\u53d1\u5e03\u5931\u8d25\uff1a" + dataModelDO.getName() + "-" + ckRs.getMsg());
            LogUtil.add((String)"\u6570\u636e\u5efa\u6a21", (String)"\u589e\u91cf\u53d1\u5e03", (String)dataModelDO.getTitle(), (String)dataModelDO.getName(), (String)ckRs.getMsg());
            return ckRs;
        }
        DataModelDO oldData = ckRs.containsKey((Object)this.keyOldData) ? (DataModelDO)ckRs.get((Object)this.keyOldData) : null;
        R r = this.syncTableStructure(dataModelDO, oldData, false);
        if (r.getCode() != 0) {
            return r;
        }
        LinkedHashMap<String, DataModelColumn> newColsMap = new LinkedHashMap<String, DataModelColumn>();
        if (oldData != null) {
            for (DataModelColumn dataModelColumn : oldData.getColumns()) {
                newColsMap.put(dataModelColumn.getColumnName(), dataModelColumn);
            }
        }
        for (DataModelColumn dataModelColumn : dataModelDO.getColumns()) {
            newColsMap.put(dataModelColumn.getColumnName(), dataModelColumn);
        }
        ArrayList newColList = new ArrayList();
        newColList.addAll(newColsMap.values());
        DataModelDO newData = oldData == null ? dataModelDO : oldData;
        newData.setColumns(newColList);
        newData.setTenantName(dataModelDO.getTenantName());
        if (this.modify(newData, oldData) <= 0) {
            return R.error((String)DataModelCoreI18nUtil.getMessage(this.errorOperate, new Object[0]));
        }
        return R.ok((String)DataModelCoreI18nUtil.getMessage(this.successOperate, new Object[0]));
    }

    private R moddifyCheck(DataModelDO newData) {
        if (newData.getName() == null) {
            return R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.definition.check.name.missing", new Object[0]));
        }
        if (newData.getBiztype() == null) {
            return R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.definition.check.biztype.missing", new Object[0]));
        }
        List newCols = newData.getColumns();
        if (newCols == null || newCols.isEmpty()) {
            return R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.definition.check.column.missing", new Object[0]));
        }
        boolean ignoreCheckTableNameLength = newData.getExtInfo("ignoreCheckTableNameLength") != null;
        HashSet<String> columnSet = new HashSet<String>();
        String colName = null;
        DataModelType.ColumnType colType = null;
        for (DataModelColumn dataModelColumn : newCols) {
            colName = dataModelColumn.getColumnName();
            if (colName == null) {
                return R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.column.check.name.empty", new Object[0]));
            }
            if (this.checkTableNameLength && !ignoreCheckTableNameLength && colName.length() > 30) {
                return R.error((String)(DataModelCoreI18nUtil.getMessage("datamodel.error.column.check.name.length.exceed", 30) + ":" + colName));
            }
            if (columnSet.contains(colName)) {
                return R.error((String)(DataModelCoreI18nUtil.getMessage("datamodel.error.column.check.duplicate", new Object[0]) + ":" + colName));
            }
            columnSet.add(colName);
            if (dataModelColumn.getColumnTitle() == null) {
                return R.error((String)(DataModelCoreI18nUtil.getMessage("datamodel.error.column.check.title.empty", new Object[0]) + ":" + colName));
            }
            colType = dataModelColumn.getColumnType();
            if (colType == null) {
                return R.error((String)(DataModelCoreI18nUtil.getMessage("datamodel.error.column.check.type.empty", new Object[0]) + ":" + colName));
            }
            if (colType != DataModelType.ColumnType.INTEGER && colType != DataModelType.ColumnType.NUMERIC && colType != DataModelType.ColumnType.NVARCHAR || dataModelColumn.getLengths() != null) continue;
            return R.error((String)(DataModelCoreI18nUtil.getMessage("datamodel.error.column.check.length.empty", new Object[0]) + ":" + colName));
        }
        DataModelDTO param = new DataModelDTO();
        param.setTenantName(newData.getTenantName());
        param.setName(newData.getName());
        DataModelDO oldData = this.get(param);
        if (this.checkTableNameLength && oldData == null && !ignoreCheckTableNameLength) {
            if (newData.getBiztype() == DataModelType.BizType.BILL && Integer.valueOf(9).equals(newData.getSubBiztype())) {
                if (newData.getName().length() > 28) {
                    return R.error((String)DataModelCoreI18nUtil.getMessage(this.errorCheckLengthExceed, 28));
                }
            } else if (newData.getBiztype() == DataModelType.BizType.BASEDATA) {
                if (newData.getName().length() > 22) {
                    return R.error((String)DataModelCoreI18nUtil.getMessage(this.errorCheckLengthExceed, 22));
                }
            } else if (newData.getName().length() > 30) {
                return R.error((String)DataModelCoreI18nUtil.getMessage(this.errorCheckLengthExceed, 30));
            }
        }
        R rs = R.ok((String)DataModelCoreI18nUtil.getMessage(this.successOperate, new Object[0]));
        rs.put(this.keyOldData, (Object)oldData);
        return rs;
    }

    private R syncTableStructure(DataModelDO newDate, DataModelDO oldData, boolean complete) {
        List newCols = newDate.getColumns();
        JDialectUtil jdUtil = JDialectUtil.getInstance();
        ArrayList<String> reservedWordList = new ArrayList<String>();
        for (Object dataModelColumn : newCols) {
            dataModelColumn.setColumnName(dataModelColumn.getColumnName());
            if (!jdUtil.isReservedWord(dataModelColumn.getColumnName())) continue;
            reservedWordList.add(dataModelColumn.getColumnName());
        }
        if (!reservedWordList.isEmpty()) {
            StringBuilder reservedWordStr = new StringBuilder("[");
            for (String reservedWord : reservedWordList) {
                reservedWordStr.append(reservedWord).append(",");
            }
            String error = DataModelCoreI18nUtil.getMessage("datamodel.error.definition.publish.reserved.fields", reservedWordStr.substring(0, reservedWordStr.length() - 1) + "]");
            logger.error("\u5efa\u6a21\u53d1\u5e03\u5931\u8d25:" + newDate.getName() + "-" + error);
            LogUtil.add((String)"\u6570\u636e\u5efa\u6a21", (String)"\u53d1\u5e03", (String)newDate.getTitle(), (String)newDate.getName(), (String)error);
            return R.error((String)error);
        }
        JTableModel jTableModel = new JTableModel(newDate.getTenantName(), newDate.getName());
        JDialectUtil jdutil = JDialectUtil.getInstance();
        boolean hasExists = jdutil.hasTable(jTableModel);
        if (!hasExists) {
            JTableModel jtm = this.converJTableModel(newDate, null);
            try {
                jdUtil.createTable(jtm);
            }
            catch (JTableException e) {
                logger.error("\u5efa\u6a21\u521b\u5efa\u7269\u7406\u8868\u5f02\u5e38", e);
                LogUtil.add((String)"\u6570\u636e\u5efa\u6a21", (String)"\u65b0\u5efa\u7269\u7406\u8868", (String)newDate.getTitle(), (String)newDate.getName(), (String)e.getMessage());
                return R.error((String)(DataModelCoreI18nUtil.getMessage("datamodel.exception.physical.table.create", new Object[0]) + DataModelCoreI18nUtil.getMessage("datamodel.exception.log.details.viewing.prompt", new Object[0])));
            }
            return R.ok((String)DataModelCoreI18nUtil.getMessage(this.successOperate, new Object[0]));
        }
        HashSet<String> oldColsSet = new HashSet<String>();
        List oldCols = jdUtil.getTableColumns(newDate.getTenantName(), newDate.getName());
        for (TableColumnDO tableColumnDO : oldCols) {
            oldColsSet.add(tableColumnDO.getColumnName().toUpperCase());
        }
        Object needCompareObj = newDate.getExtInfo("needCompare");
        boolean needCompare = oldData != null && (needCompareObj == null || (Boolean)needCompareObj != false);
        HashMap<String, DataModelColumn> oldModelColsMap = null;
        if (oldData != null) {
            oldModelColsMap = new HashMap<String, DataModelColumn>();
            for (DataModelColumn dataModelColumn : oldData.getColumns()) {
                oldModelColsMap.put(dataModelColumn.getColumnName(), dataModelColumn);
            }
        }
        ArrayList<DataModelColumn> addColList = new ArrayList<DataModelColumn>();
        ArrayList<DataModelColumn> upColList = new ArrayList<DataModelColumn>();
        HashSet<String> newColsSet = new HashSet<String>();
        String key = null;
        for (DataModelColumn dmcol : newCols) {
            key = dmcol.getColumnName();
            newColsSet.add(key);
            if (oldColsSet.contains(key)) {
                if (dmcol.isPkey() != null && dmcol.isPkey() != Boolean.FALSE || needCompare && this.columnEquals(dmcol, (DataModelColumn)oldModelColsMap.get(key))) continue;
                upColList.add(dmcol);
                continue;
            }
            addColList.add(dmcol);
        }
        DataModelDO tempModel = new DataModelDO();
        tempModel.setTenantName(newDate.getTenantName());
        tempModel.setName(newDate.getName());
        tempModel.setBiztype(newDate.getBiztype());
        tempModel.setDistributeByReplication(newDate.isDistributeByReplication());
        if (!addColList.isEmpty()) {
            tempModel.setColumns(addColList);
            JTableModel addjtm = this.converJTableModel(tempModel, null);
            try {
                jdUtil.addColumn(addjtm);
            }
            catch (JTableException e) {
                logger.error("\u5efa\u6a21\u65b0\u589e\u7269\u7406\u5b57\u6bb5\u5f02\u5e38", e);
                LogUtil.add((String)"\u6570\u636e\u5efa\u6a21", (String)"\u65b0\u589e\u7269\u7406\u5b57\u6bb5", (String)newDate.getTitle(), (String)newDate.getName(), (String)e.getMessage());
                return R.error((String)(DataModelCoreI18nUtil.getMessage("datamodel.exception.physical.fields.add", new Object[0]) + DataModelCoreI18nUtil.getMessage("datamodel.exception.log.details.viewing.prompt", new Object[0])));
            }
        }
        if (!upColList.isEmpty()) {
            tempModel.setColumns(upColList);
            JTableModel upjtm = this.converJTableModel(tempModel, oldModelColsMap);
            try {
                jdUtil.modifyColumn(upjtm);
            }
            catch (JTableException e) {
                logger.error("\u5efa\u6a21\u66f4\u65b0\u7269\u7406\u5b57\u6bb5\u5f02\u5e38", e);
                LogUtil.add((String)"\u6570\u636e\u5efa\u6a21", (String)"\u66f4\u65b0\u7269\u7406\u5b57\u6bb5", (String)newDate.getTitle(), (String)newDate.getName(), (String)e.getMessage());
                return R.error((String)(DataModelCoreI18nUtil.getMessage("datamodel.exception.physical.fields.update", new Object[0]) + DataModelCoreI18nUtil.getMessage("datamodel.exception.log.details.viewing.prompt", new Object[0])));
            }
        }
        if (complete) {
            JTableModel delModel = new JTableModel(newDate.getTenantName(), newDate.getName());
            for (String oldKey : oldColsSet) {
                if (newColsSet.contains(oldKey)) continue;
                if (this.checkExistsData(newDate.getTenantName(), newDate.getName(), oldKey)) {
                    String error = DataModelCoreI18nUtil.getMessage("datamodel.error.physical.field.delete.data.existed", oldKey);
                    logger.error("\u5efa\u6a21\u5220\u9664\u7269\u7406\u5b57\u6bb5\u5f02\u5e38\uff1a" + newDate.getName() + " - " + error);
                    LogUtil.add((String)"\u6570\u636e\u5efa\u6a21", (String)"\u5220\u9664\u7269\u7406\u5b57\u6bb5", (String)newDate.getTitle(), (String)newDate.getName(), (String)error);
                    return R.error((String)error);
                }
                delModel.addColumn(oldKey);
            }
            if (!delModel.getColumns().isEmpty()) {
                try {
                    jdUtil.dropColumn(delModel);
                }
                catch (JTableException e) {
                    logger.error("\u5efa\u6a21\u5220\u9664\u7269\u7406\u5b57\u6bb5\u5931\u8d25", e);
                    LogUtil.add((String)"\u6570\u636e\u5efa\u6a21", (String)"\u5220\u9664\u7269\u7406\u5b57\u6bb5", (String)newDate.getTitle(), (String)newDate.getName(), (String)e.getMessage());
                    return R.error((String)(DataModelCoreI18nUtil.getMessage("datamodel.exception.physical.fields.delete", new Object[0]) + DataModelCoreI18nUtil.getMessage("datamodel.exception.log.details.viewing.prompt", new Object[0])));
                }
            }
        }
        return R.ok((String)DataModelCoreI18nUtil.getMessage(this.successOperate, new Object[0]));
    }

    private boolean columnEquals(DataModelColumn newDmcol, DataModelColumn oldDmcol) {
        if (oldDmcol == null) {
            return false;
        }
        if (newDmcol.getColumnType() != oldDmcol.getColumnType()) {
            return false;
        }
        if (newDmcol.getLengths() != null && oldDmcol.getLengths() != null) {
            if (newDmcol.getLengths().length != oldDmcol.getLengths().length) {
                return false;
            }
            for (int i = 0; i < newDmcol.getLengths().length; ++i) {
                if (newDmcol.getLengths()[i].intValue() == oldDmcol.getLengths()[i].intValue()) continue;
                return false;
            }
        }
        if (newDmcol.getNullable() == null ? oldDmcol.getNullable() != null : !newDmcol.getNullable().equals(oldDmcol.getNullable())) {
            return false;
        }
        return !(newDmcol.getDefaultVal() == null ? oldDmcol.getDefaultVal() != null : !newDmcol.getDefaultVal().equals(oldDmcol.getDefaultVal()));
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
        catch (Exception exception) {
            // empty catch block
        }
        return false;
    }

    @Override
    public R remove(DataModelDO dataModelDO) {
        DataModelPublishedDO dmpParam = new DataModelPublishedDO();
        dmpParam.setTenantName(dataModelDO.getTenantName());
        dmpParam.setName(dataModelDO.getName());
        DataModelPublishedDO old = (DataModelPublishedDO)((Object)this.dataModelDao.selectOne((Object)dmpParam));
        if (old == null) {
            return R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.definition.delete.not.exist", new Object[0]));
        }
        if (this.checkExistsData(dataModelDO.getTenantName(), dataModelDO.getName(), "id")) {
            return R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.definition.delete.data.existed", new Object[0]));
        }
        this.dataModelDao.delete((Object)dmpParam);
        this.syncCache(dataModelDO.getTenantName(), old, true);
        JDialectUtil jdUtil = JDialectUtil.getInstance();
        JTableModel deljtm = new JTableModel(dataModelDO.getTenantName(), dataModelDO.getName());
        try {
            jdUtil.dropTable(deljtm);
        }
        catch (JTableException e) {
            logger.error("\u6570\u636e\u5efa\u6a21\u5220\u9664\u7269\u7406\u8868\u5931\u8d25", e);
            return R.error((String)DataModelCoreI18nUtil.getMessage(this.errorOperate, new Object[0]));
        }
        LogUtil.add((String)"\u6570\u636e\u5efa\u6a21", (String)"\u5220\u9664", (String)old.getTitle(), (String)old.getName(), (String)old.getDefinedata());
        return R.ok((String)DataModelCoreI18nUtil.getMessage(this.successOperate, new Object[0]));
    }

    private DataModelPublishedDO convertDefineJson(DataModelDO dataModelDO) {
        DataModelPublishedDO define = new DataModelPublishedDO();
        define.convert(dataModelDO);
        define.setDefinedata(JSONUtil.toJSONString((Object)((Object)define)));
        return define;
    }

    private JTableModel converJTableModel(DataModelDO dataModelDO, Map<String, DataModelColumn> oldModelColsMap) {
        List dmis;
        JTableModel jtm = new JTableModel(dataModelDO.getTenantName(), dataModelDO.getName());
        List dmcs = dataModelDO.getColumns();
        if (dmcs != null) {
            DataModelColumn oldModelCold = null;
            for (DataModelColumn dmc : dmcs) {
                ColumnModel cm = jtm.column(dmc.getColumnName());
                switch (dmc.getColumnType()) {
                    case UUID: {
                        cm.VARCHAR(Integer.valueOf(36));
                        break;
                    }
                    case NVARCHAR: {
                        cm.NVARCHAR(dmc.getLengths()[0]);
                        break;
                    }
                    case INTEGER: {
                        cm.INTEGER(new Integer[]{dmc.getLengths()[0]});
                        break;
                    }
                    case NUMERIC: {
                        cm.NUMERIC(new Integer[]{dmc.getLengths()[0], dmc.getLengths().length > 1 ? dmc.getLengths()[1] : 0});
                        break;
                    }
                    case DATE: {
                        cm.DATE();
                        break;
                    }
                    case TIMESTAMP: {
                        cm.TIMESTAMP();
                        break;
                    }
                    case CLOB: {
                        cm.CLOB();
                        break;
                    }
                }
                if (dmc.getPkey() != null) {
                    cm.setPkey(dmc.isPkey());
                }
                if (oldModelColsMap != null) {
                    oldModelCold = oldModelColsMap.get(cm.getColumnName());
                }
                if (StringUtils.hasText(dmc.getDefaultVal())) {
                    if (!(dmc.getColumnType() != DataModelType.ColumnType.NVARCHAR && dmc.getColumnType() != DataModelType.ColumnType.CLOB || dmc.getDefaultVal().startsWith("'") || dmc.getDefaultVal().endsWith("'"))) {
                        cm.setDefaultValue("'" + dmc.getDefaultVal() + "'");
                    } else {
                        cm.setDefaultValue(dmc.getDefaultVal());
                    }
                } else if (oldModelCold != null && StringUtils.hasText(oldModelCold.getDefaultVal())) {
                    cm.setDefaultValue("null");
                }
                if (dmc.getNullable() != null) {
                    cm.setNullable(dmc.isNullable());
                    continue;
                }
                if (oldModelCold == null || oldModelCold.isNullable().booleanValue()) continue;
                cm.setNullable(Boolean.valueOf(true));
            }
        }
        if ((dmis = dataModelDO.getIndexConsts()) != null) {
            for (DataModelIndex dmi : dmis) {
                IndexModel im = jtm.index(dmi.getIndexName());
                im.setColumnList(dmi.getColumnList());
                if (dmi.isUnique() != null) {
                    im.setUnique(dmi.isUnique());
                    continue;
                }
                im.setUnique(Boolean.valueOf(false));
            }
        }
        if (dataModelDO.getBiztype() == DataModelType.BizType.BASEDATA) {
            dataModelDO.setDistributeByReplication(true);
            jtm.setDistributeByReplication(true);
        } else {
            jtm.setDistributeByReplication(dataModelDO.isDistributeByReplication());
        }
        return jtm;
    }

    @Override
    public List<String> listGroup(DataModelDTO param) {
        List<String> list = this.dataModelDao.listGroup(param);
        String searchKey = param.getSearchKey();
        if (StringUtils.hasText(searchKey)) {
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                if (it.next().contains(searchKey)) continue;
                it.remove();
            }
        }
        return list;
    }

    @Override
    public R syncCache(DataModelDTO param) {
        DataModelSyncCacheDTO dmscd = new DataModelSyncCacheDTO();
        if (param.getName() != null) {
            dmscd.setDataModel((DataModelDO)param);
        } else {
            dmscd.setForceUpdate(true);
        }
        this.cacheService.pushSyncMsg(dmscd);
        return R.ok((String)DataModelCoreI18nUtil.getMessage(this.successOperate, new Object[0]));
    }
}

