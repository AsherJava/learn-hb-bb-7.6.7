/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.common.IndexModelType
 *  com.jiuqi.nvwa.definition.common.TableModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.period.dao;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.period.common.utils.PeriodException;
import com.jiuqi.nr.period.common.utils.PeriodTableColumn;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.dao.PeriodDao;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.impl.PeriodDataDefineImpl;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.common.IndexModelType;
import com.jiuqi.nvwa.definition.common.TableModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PeriodDataDao {
    private static final Logger logger = LoggerFactory.getLogger(PeriodDataDao.class);
    @Autowired
    DesignDataModelService designDataModelService;
    @Autowired
    DataModelService dataModelService;
    @Autowired
    DataModelDeployService dataModelDeployService;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    private PeriodDao periodDao;

    private DesignColumnModelDefine[] createColumnModelDefineArray(String tableID) throws Exception {
        DesignColumnModelDefine columnID = this.designDataModelService.createColumnModelDefine();
        columnID.setCode(PeriodTableColumn.KEY.getCode());
        columnID.setColumnType(PeriodTableColumn.KEY.getType());
        columnID.setName(PeriodTableColumn.KEY.getCode());
        columnID.setTitle(PeriodTableColumn.KEY.getTitle());
        columnID.setTableID(tableID);
        columnID.setPrecision(PeriodTableColumn.KEY.getLenght());
        columnID.setOrder((double)PeriodTableColumn.KEY.getOrder());
        DesignColumnModelDefine columnCode = this.designDataModelService.createColumnModelDefine();
        columnCode.setCode(PeriodTableColumn.CODE.getCode());
        columnCode.setColumnType(PeriodTableColumn.CODE.getType());
        columnCode.setName(PeriodTableColumn.CODE.getCode());
        columnCode.setTitle(PeriodTableColumn.CODE.getTitle());
        columnCode.setTableID(tableID);
        columnCode.setPrecision(PeriodTableColumn.CODE.getLenght());
        columnCode.setOrder((double)PeriodTableColumn.CODE.getOrder());
        DesignColumnModelDefine columnAlias = this.designDataModelService.createColumnModelDefine();
        columnAlias.setCode(PeriodTableColumn.ALIAS.getCode());
        columnAlias.setColumnType(PeriodTableColumn.ALIAS.getType());
        columnAlias.setName(PeriodTableColumn.ALIAS.getCode());
        columnAlias.setTitle(PeriodTableColumn.ALIAS.getTitle());
        columnAlias.setTableID(tableID);
        columnAlias.setPrecision(PeriodTableColumn.ALIAS.getLenght());
        columnAlias.setNullAble(PeriodTableColumn.ALIAS.getNullable());
        columnAlias.setOrder((double)PeriodTableColumn.ALIAS.getOrder());
        DesignColumnModelDefine columnTitle = this.designDataModelService.createColumnModelDefine();
        columnTitle.setCode(PeriodTableColumn.TITLE.getCode());
        columnTitle.setColumnType(PeriodTableColumn.TITLE.getType());
        columnTitle.setName(PeriodTableColumn.TITLE.getCode());
        columnTitle.setTitle(PeriodTableColumn.TITLE.getTitle());
        columnTitle.setTableID(tableID);
        columnTitle.setPrecision(PeriodTableColumn.TITLE.getLenght());
        columnTitle.setNullAble(PeriodTableColumn.TITLE.getNullable());
        columnTitle.setOrder((double)PeriodTableColumn.TITLE.getOrder());
        DesignColumnModelDefine columnStartDate = this.designDataModelService.createColumnModelDefine();
        columnStartDate.setCode(PeriodTableColumn.STARTDATE.getCode());
        columnStartDate.setColumnType(PeriodTableColumn.STARTDATE.getType());
        columnStartDate.setName(PeriodTableColumn.STARTDATE.getCode());
        columnStartDate.setTitle(PeriodTableColumn.STARTDATE.getTitle());
        columnStartDate.setTableID(tableID);
        columnStartDate.setNullAble(PeriodTableColumn.STARTDATE.getNullable());
        columnStartDate.setOrder((double)PeriodTableColumn.STARTDATE.getOrder());
        DesignColumnModelDefine columnEndDate = this.designDataModelService.createColumnModelDefine();
        columnEndDate.setCode(PeriodTableColumn.ENDDATE.getCode());
        columnEndDate.setColumnType(PeriodTableColumn.ENDDATE.getType());
        columnEndDate.setName(PeriodTableColumn.ENDDATE.getCode());
        columnEndDate.setTitle(PeriodTableColumn.ENDDATE.getTitle());
        columnEndDate.setTableID(tableID);
        columnEndDate.setNullAble(PeriodTableColumn.ENDDATE.getNullable());
        columnEndDate.setOrder((double)PeriodTableColumn.ENDDATE.getOrder());
        DesignColumnModelDefine columnYear = this.designDataModelService.createColumnModelDefine();
        columnYear.setCode(PeriodTableColumn.YEAR.getCode());
        columnYear.setColumnType(PeriodTableColumn.YEAR.getType());
        columnYear.setName(PeriodTableColumn.YEAR.getCode());
        columnYear.setTitle(PeriodTableColumn.YEAR.getTitle());
        columnYear.setTableID(tableID);
        columnYear.setPrecision(PeriodTableColumn.YEAR.getLenght());
        columnYear.setNullAble(PeriodTableColumn.YEAR.getNullable());
        columnYear.setOrder((double)PeriodTableColumn.YEAR.getOrder());
        DesignColumnModelDefine columnQuarter = this.designDataModelService.createColumnModelDefine();
        columnQuarter.setCode(PeriodTableColumn.QUARTER.getCode());
        columnQuarter.setColumnType(PeriodTableColumn.QUARTER.getType());
        columnQuarter.setName(PeriodTableColumn.QUARTER.getCode());
        columnQuarter.setTitle(PeriodTableColumn.QUARTER.getTitle());
        columnQuarter.setTableID(tableID);
        columnQuarter.setPrecision(PeriodTableColumn.QUARTER.getLenght());
        columnQuarter.setNullAble(PeriodTableColumn.QUARTER.getNullable());
        columnQuarter.setOrder((double)PeriodTableColumn.QUARTER.getOrder());
        DesignColumnModelDefine columnMonth = this.designDataModelService.createColumnModelDefine();
        columnMonth.setCode(PeriodTableColumn.MONTH.getCode());
        columnMonth.setColumnType(PeriodTableColumn.MONTH.getType());
        columnMonth.setName(PeriodTableColumn.MONTH.getCode());
        columnMonth.setTitle(PeriodTableColumn.MONTH.getTitle());
        columnMonth.setTableID(tableID);
        columnMonth.setPrecision(PeriodTableColumn.MONTH.getLenght());
        columnMonth.setNullAble(PeriodTableColumn.MONTH.getNullable());
        columnMonth.setOrder((double)PeriodTableColumn.MONTH.getOrder());
        DesignColumnModelDefine columnDay = this.designDataModelService.createColumnModelDefine();
        columnDay.setCode(PeriodTableColumn.DAY.getCode());
        columnDay.setColumnType(PeriodTableColumn.DAY.getType());
        columnDay.setName(PeriodTableColumn.DAY.getCode());
        columnDay.setTitle(PeriodTableColumn.DAY.getTitle());
        columnDay.setTableID(tableID);
        columnDay.setPrecision(PeriodTableColumn.DAY.getLenght());
        columnDay.setNullAble(PeriodTableColumn.DAY.getNullable());
        columnDay.setOrder((double)PeriodTableColumn.DAY.getOrder());
        DesignColumnModelDefine columnTimeKey = this.designDataModelService.createColumnModelDefine();
        columnTimeKey.setCode(PeriodTableColumn.TIMEKEY.getCode());
        columnTimeKey.setColumnType(PeriodTableColumn.TIMEKEY.getType());
        columnTimeKey.setName(PeriodTableColumn.TIMEKEY.getCode());
        columnTimeKey.setTitle(PeriodTableColumn.TIMEKEY.getTitle());
        columnTimeKey.setTableID(tableID);
        columnTimeKey.setPrecision(PeriodTableColumn.TIMEKEY.getLenght());
        columnTimeKey.setNullAble(PeriodTableColumn.TIMEKEY.getNullable());
        columnTimeKey.setOrder((double)PeriodTableColumn.TIMEKEY.getOrder());
        DesignColumnModelDefine columnDays = this.designDataModelService.createColumnModelDefine();
        columnDays.setCode(PeriodTableColumn.DAYS.getCode());
        columnDays.setColumnType(PeriodTableColumn.DAYS.getType());
        columnDays.setName(PeriodTableColumn.DAYS.getCode());
        columnDays.setTitle(PeriodTableColumn.DAYS.getTitle());
        columnDays.setTableID(tableID);
        columnDays.setPrecision(PeriodTableColumn.DAYS.getLenght());
        columnDays.setNullAble(PeriodTableColumn.DAYS.getNullable());
        columnDays.setOrder((double)PeriodTableColumn.DAYS.getOrder());
        DesignColumnModelDefine columnCreateTime = this.designDataModelService.createColumnModelDefine();
        columnCreateTime.setCode(PeriodTableColumn.CREATETIME.getCode());
        columnCreateTime.setColumnType(PeriodTableColumn.CREATETIME.getType());
        columnCreateTime.setName(PeriodTableColumn.CREATETIME.getCode());
        columnCreateTime.setTitle(PeriodTableColumn.CREATETIME.getTitle());
        columnCreateTime.setTableID(tableID);
        columnCreateTime.setNullAble(PeriodTableColumn.CREATETIME.getNullable());
        columnCreateTime.setOrder((double)PeriodTableColumn.CREATETIME.getOrder());
        DesignColumnModelDefine columnCreateUser = this.designDataModelService.createColumnModelDefine();
        columnCreateUser.setCode(PeriodTableColumn.CREATEUSER.getCode());
        columnCreateUser.setColumnType(PeriodTableColumn.CREATEUSER.getType());
        columnCreateUser.setName(PeriodTableColumn.CREATEUSER.getCode());
        columnCreateUser.setTitle(PeriodTableColumn.CREATEUSER.getTitle());
        columnCreateUser.setTableID(tableID);
        columnCreateUser.setPrecision(PeriodTableColumn.CREATEUSER.getLenght());
        columnCreateUser.setNullAble(PeriodTableColumn.CREATEUSER.getNullable());
        columnCreateUser.setOrder((double)PeriodTableColumn.CREATEUSER.getOrder());
        DesignColumnModelDefine columnUpdateTime = this.designDataModelService.createColumnModelDefine();
        columnUpdateTime.setCode(PeriodTableColumn.UPDATETIME.getCode());
        columnUpdateTime.setColumnType(PeriodTableColumn.UPDATETIME.getType());
        columnUpdateTime.setName(PeriodTableColumn.UPDATETIME.getCode());
        columnUpdateTime.setTitle(PeriodTableColumn.UPDATETIME.getTitle());
        columnUpdateTime.setTableID(tableID);
        columnUpdateTime.setNullAble(PeriodTableColumn.UPDATETIME.getNullable());
        columnUpdateTime.setOrder((double)PeriodTableColumn.UPDATETIME.getOrder());
        DesignColumnModelDefine columnUpdateUser = this.designDataModelService.createColumnModelDefine();
        columnUpdateUser.setCode(PeriodTableColumn.UPDATEUSER.getCode());
        columnUpdateUser.setColumnType(PeriodTableColumn.UPDATEUSER.getType());
        columnUpdateUser.setName(PeriodTableColumn.UPDATEUSER.getCode());
        columnUpdateUser.setTitle(PeriodTableColumn.UPDATEUSER.getTitle());
        columnUpdateUser.setTableID(tableID);
        columnUpdateUser.setPrecision(PeriodTableColumn.UPDATEUSER.getLenght());
        columnUpdateUser.setNullAble(PeriodTableColumn.UPDATEUSER.getNullable());
        columnUpdateUser.setOrder((double)PeriodTableColumn.UPDATEUSER.getOrder());
        DesignColumnModelDefine columnSimple = this.designDataModelService.createColumnModelDefine();
        columnSimple.setCode(PeriodTableColumn.SIMPLETITLE.getCode());
        columnSimple.setColumnType(PeriodTableColumn.SIMPLETITLE.getType());
        columnSimple.setName(PeriodTableColumn.SIMPLETITLE.getCode());
        columnSimple.setTitle(PeriodTableColumn.SIMPLETITLE.getTitle());
        columnSimple.setTableID(tableID);
        columnSimple.setPrecision(PeriodTableColumn.SIMPLETITLE.getLenght());
        columnSimple.setNullAble(PeriodTableColumn.SIMPLETITLE.getNullable());
        columnSimple.setOrder((double)PeriodTableColumn.SIMPLETITLE.getOrder());
        return new DesignColumnModelDefine[]{columnID, columnCode, columnAlias, columnTitle, columnStartDate, columnEndDate, columnYear, columnQuarter, columnMonth, columnDay, columnTimeKey, columnDays, columnCreateTime, columnCreateUser, columnUpdateTime, columnUpdateUser, columnSimple};
    }

    public DesignColumnModelDefine createSimpleTitle(String tableID) {
        DesignColumnModelDefine columnSimple = this.designDataModelService.createColumnModelDefine();
        columnSimple.setCode(PeriodTableColumn.SIMPLETITLE.getCode());
        columnSimple.setColumnType(PeriodTableColumn.SIMPLETITLE.getType());
        columnSimple.setName(PeriodTableColumn.SIMPLETITLE.getCode());
        columnSimple.setTitle(PeriodTableColumn.SIMPLETITLE.getTitle());
        columnSimple.setTableID(tableID);
        columnSimple.setPrecision(PeriodTableColumn.SIMPLETITLE.getLenght());
        columnSimple.setNullAble(PeriodTableColumn.SIMPLETITLE.getNullable());
        columnSimple.setOrder((double)PeriodTableColumn.SIMPLETITLE.getOrder());
        return columnSimple;
    }

    public void createAndDeployTable(String code, String name, String desc, String entityKey) throws Exception {
        code = code.toUpperCase();
        DesignTableModelDefine tableModelDefine = this.designDataModelService.createTableModelDefine();
        DesignColumnModelDefine[] columnModelDefineArray = this.createColumnModelDefineArray(tableModelDefine.getID());
        this.designDataModelService.insertColumnModelDefines(columnModelDefineArray);
        tableModelDefine.setCode(code);
        tableModelDefine.setTitle(name);
        tableModelDefine.setName(code);
        tableModelDefine.setKeys(columnModelDefineArray[1].getID());
        tableModelDefine.setBizKeys(columnModelDefineArray[1].getID());
        tableModelDefine.setDesc(desc);
        tableModelDefine.setType(TableModelType.DATA);
        this.designDataModelService.insertTableModelDefine(tableModelDefine);
        this.designDataModelService.addIndexToTable(tableModelDefine.getID(), new String[]{columnModelDefineArray[10].getID()}, "NORM_" + code, IndexModelType.NORMAL);
        this.dataModelDeployService.deployTable(tableModelDefine.getID());
    }

    public void insertDataList(String code, List<PeriodDataDefineImpl> list) throws Exception {
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        IPeriodEntity periodEntity = this.periodDao.queryPeriodByCode(code);
        TableModelDefine table = this.dataModelService.getTableModelDefineByCode(PeriodUtils.addPrefix(code));
        List columns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
        columns = columns.stream().sorted((a, b) -> Double.compare(a.getOrder(), b.getOrder())).collect(Collectors.toList());
        for (ColumnModelDefine column : columns) {
            queryModel.getColumns().add(new NvwaQueryColumn(column));
        }
        INvwaUpdatableDataAccess dataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        INvwaDataUpdator updaor = dataAccess.openForUpdate(context);
        for (PeriodDataDefineImpl define : list) {
            INvwaDataRow newRow = updaor.addInsertRow();
            newRow.setValue(0, (Object)define.getKey());
            newRow.setValue(1, (Object)define.getCode());
            newRow.setValue(2, (Object)define.getAlias());
            newRow.setValue(3, (Object)define.getTitle());
            newRow.setValue(4, (Object)define.getStartDate());
            newRow.setValue(5, (Object)define.getEndDate());
            newRow.setValue(6, (Object)define.getYear());
            newRow.setValue(7, (Object)define.getQuarter());
            newRow.setValue(8, (Object)define.getMonth());
            newRow.setValue(9, (Object)define.getDay());
            newRow.setValue(10, (Object)define.getTimeKey());
            newRow.setValue(11, (Object)define.getDays());
            newRow.setValue(12, (Object)define.getCreateTime());
            newRow.setValue(13, (Object)define.getCreateUser());
            newRow.setValue(14, (Object)define.getUpdateTime());
            newRow.setValue(15, (Object)define.getUpdateUser());
            newRow.setValue(16, (Object)define.getSimpleTitle());
        }
        updaor.commitChanges(context);
    }

    public List<PeriodDataDefineImpl> getDataListByCode(String code) throws Exception {
        IPeriodEntity periodEntity = this.periodDao.queryPeriodByCode(code);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine table = this.dataModelService.getTableModelDefineByCode(PeriodUtils.addPrefix(code));
        List columns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
        columns = columns.stream().sorted((a, b) -> Double.compare(a.getOrder(), b.getOrder())).collect(Collectors.toList());
        for (ColumnModelDefine column : columns) {
            queryModel.getColumns().add(new NvwaQueryColumn(column));
        }
        INvwaDataAccess dataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        MemoryDataSet result = dataAccess.executeQuery(context);
        Iterator iterator = result.iterator();
        ArrayList<PeriodDataDefineImpl> list = new ArrayList<PeriodDataDefineImpl>();
        while (iterator.hasNext()) {
            DataRow dataRow = (DataRow)iterator.next();
            PeriodDataDefineImpl p = new PeriodDataDefineImpl();
            p.setKey(dataRow.getString(0));
            p.setCode(dataRow.getString(1));
            p.setAlias(dataRow.getString(2));
            p.setTitle(dataRow.getString(3));
            p.setStartDate(dataRow.getDate(4) != null ? dataRow.getDate(4).getTime() : null);
            p.setEndDate(dataRow.getDate(5) != null ? dataRow.getDate(5).getTime() : null);
            p.setYear(dataRow.getInt(6));
            p.setQuarter(dataRow.getInt(7));
            p.setMonth(dataRow.getInt(8));
            p.setDay(dataRow.getInt(9));
            p.setTimeKey(dataRow.getString(10));
            p.setDays(dataRow.getInt(11));
            p.setCreateTime(dataRow.getDate(12) != null ? dataRow.getDate(12).getTime() : null);
            p.setCreateUser(dataRow.getString(13));
            p.setUpdateTime(dataRow.getDate(14) != null ? dataRow.getDate(14).getTime() : null);
            p.setUpdateUser(dataRow.getString(15));
            p.setSimpleTitle(dataRow.getString(16));
            list.add(p);
        }
        if (PeriodUtils.isPeriod13(periodEntity.getCode(), periodEntity.getPeriodType())) {
            return list.stream().sorted(Comparator.comparing(PeriodDataDefineImpl::getCode)).collect(Collectors.toList());
        }
        return list.stream().sorted((a, b) -> {
            if (null == a.getStartDate()) {
                return 1;
            }
            if (null == b.getStartDate()) {
                return -1;
            }
            return a.getStartDate().compareTo(b.getStartDate());
        }).collect(Collectors.toList());
    }

    public List<PeriodDataDefineImpl> getDataListSqls(String code) throws Exception {
        Iterator<DataRow> iterator = this.getDataRow(code);
        ArrayList<PeriodDataDefineImpl> list = new ArrayList<PeriodDataDefineImpl>();
        while (iterator.hasNext()) {
            DataRow dataRow = iterator.next();
            PeriodDataDefineImpl p = new PeriodDataDefineImpl();
            p.setKey(dataRow.getString(0));
            p.setCode(dataRow.getString(1));
            p.setAlias(dataRow.getString(2));
            p.setTitle(dataRow.getString(3));
            p.setStartDate(dataRow.getDate(4) != null ? dataRow.getDate(4).getTime() : null);
            p.setEndDate(dataRow.getDate(5) != null ? dataRow.getDate(5).getTime() : null);
            p.setYear(dataRow.getInt(6));
            p.setQuarter(dataRow.getInt(7));
            p.setMonth(dataRow.getInt(8));
            p.setDay(dataRow.getInt(9));
            p.setTimeKey(dataRow.getString(10));
            p.setDays(dataRow.getInt(11));
            p.setCreateTime(dataRow.getDate(12) != null ? dataRow.getDate(12).getTime() : null);
            p.setCreateUser(dataRow.getString(13));
            p.setUpdateTime(dataRow.getDate(14) != null ? dataRow.getDate(14).getTime() : null);
            p.setUpdateUser(dataRow.getString(15));
            p.setSimpleTitle(dataRow.getString(16));
            list.add(p);
        }
        return list.stream().sorted((a, b) -> {
            if (null == a.getStartDate()) {
                return 1;
            }
            if (null == b.getStartDate()) {
                return -1;
            }
            return a.getStartDate().compareTo(b.getStartDate());
        }).collect(Collectors.toList());
    }

    public List<PeriodDataDefineImpl> getDataListWithOutSimpleTitle(String code) throws Exception {
        Iterator<DataRow> iterator = this.getDataRow(code);
        ArrayList<PeriodDataDefineImpl> list = new ArrayList<PeriodDataDefineImpl>();
        while (iterator.hasNext()) {
            DataRow dataRow = iterator.next();
            PeriodDataDefineImpl p = new PeriodDataDefineImpl();
            p.setKey(dataRow.getString(0));
            p.setCode(dataRow.getString(1));
            p.setAlias(dataRow.getString(2));
            p.setTitle(dataRow.getString(3));
            p.setStartDate(dataRow.getDate(4) != null ? dataRow.getDate(4).getTime() : null);
            p.setEndDate(dataRow.getDate(5) != null ? dataRow.getDate(5).getTime() : null);
            p.setYear(dataRow.getInt(6));
            p.setQuarter(dataRow.getInt(7));
            p.setMonth(dataRow.getInt(8));
            p.setDay(dataRow.getInt(9));
            p.setTimeKey(dataRow.getString(10));
            p.setDays(dataRow.getInt(11));
            p.setCreateTime(dataRow.getDate(12) != null ? dataRow.getDate(12).getTime() : null);
            p.setCreateUser(dataRow.getString(13));
            p.setUpdateTime(dataRow.getDate(14) != null ? dataRow.getDate(14).getTime() : null);
            p.setUpdateUser(dataRow.getString(15));
            list.add(p);
        }
        return list.stream().sorted((a, b) -> {
            if (null == a.getStartDate()) {
                return 1;
            }
            if (null == b.getStartDate()) {
                return -1;
            }
            return a.getStartDate().compareTo(b.getStartDate());
        }).collect(Collectors.toList());
    }

    private Iterator<DataRow> getDataRow(String code) throws Exception {
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine table = this.dataModelService.getTableModelDefineByCode(PeriodUtils.addPrefix(code));
        List columns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
        columns = columns.stream().sorted((a, b) -> Double.compare(a.getOrder(), b.getOrder())).collect(Collectors.toList());
        for (ColumnModelDefine column : columns) {
            queryModel.getColumns().add(new NvwaQueryColumn(column));
        }
        INvwaDataAccess dataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        MemoryDataSet result = dataAccess.executeQuery(context);
        Iterator iterator = result.iterator();
        return iterator;
    }

    public void deteteTable(IPeriodEntity pd) {
        DesignTableModelDefine designTableModelDefine = this.designDataModelService.getTableModelDefineByCode(PeriodUtils.addPrefix(pd.getCode()));
        this.designDataModelService.deleteColumnModelDefineByTable(designTableModelDefine.getID());
        this.designDataModelService.deleteIndexsByTable(designTableModelDefine.getID());
        this.designDataModelService.deleteTableModelDefine(designTableModelDefine.getID());
        try {
            this.dataModelDeployService.deployTable(designTableModelDefine.getID());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void insertDate(String code, PeriodDataDefineImpl periodDataDefine) throws Exception {
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        IPeriodEntity periodEntity = this.periodDao.queryPeriodByCode(code);
        TableModelDefine table = this.dataModelService.getTableModelDefineByCode(PeriodUtils.addPrefix(code));
        List columns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
        columns = columns.stream().sorted((a, b) -> Double.compare(a.getOrder(), b.getOrder())).collect(Collectors.toList());
        for (ColumnModelDefine column : columns) {
            queryModel.getColumns().add(new NvwaQueryColumn(column));
        }
        queryModel.getColumnFilters().put(columns.get(1), periodDataDefine.getCode());
        INvwaUpdatableDataAccess dataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        INvwaUpdatableDataSet result = dataAccess.executeQueryForUpdate(context);
        if (result.size() != 0) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_117);
        }
        INvwaDataRow newRow = result.appendRow();
        newRow.setValue(0, (Object)periodDataDefine.getKey());
        newRow.setValue(1, (Object)periodDataDefine.getCode());
        newRow.setValue(2, (Object)periodDataDefine.getAlias());
        newRow.setValue(3, (Object)periodDataDefine.getTitle());
        newRow.setValue(4, (Object)periodDataDefine.getStartDate());
        newRow.setValue(5, (Object)periodDataDefine.getEndDate());
        newRow.setValue(6, (Object)periodDataDefine.getYear());
        newRow.setValue(7, (Object)periodDataDefine.getQuarter());
        newRow.setValue(8, (Object)periodDataDefine.getMonth());
        newRow.setValue(9, (Object)periodDataDefine.getDay());
        newRow.setValue(10, (Object)periodDataDefine.getTimeKey());
        newRow.setValue(11, (Object)periodDataDefine.getDays());
        newRow.setValue(12, (Object)periodDataDefine.getCreateTime());
        newRow.setValue(13, (Object)periodDataDefine.getCreateUser());
        newRow.setValue(14, (Object)periodDataDefine.getUpdateTime());
        newRow.setValue(15, (Object)periodDataDefine.getUpdateUser());
        newRow.setValue(16, (Object)periodDataDefine.getSimpleTitle());
        result.commitChanges(context);
    }

    public List<PeriodDataDefineImpl> queryCustomPeriodData(String code) throws Exception {
        return this.getDataListByCode(code);
    }

    public void insertCustomPeriodData(String code, PeriodDataDefineImpl periodDataDefine) throws Exception {
        this.insertDate(code, periodDataDefine);
    }

    public void updateCustomPeriodData(String code, PeriodDataDefineImpl periodDataDefine) throws Exception {
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        IPeriodEntity periodEntity = this.periodDao.queryPeriodByCode(code);
        TableModelDefine table = this.dataModelService.getTableModelDefineByCode(PeriodUtils.addPrefix(code));
        List columns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
        columns = columns.stream().sorted((a, b) -> Double.compare(a.getOrder(), b.getOrder())).collect(Collectors.toList());
        for (ColumnModelDefine column : columns) {
            queryModel.getColumns().add(new NvwaQueryColumn(column));
        }
        queryModel.getColumnFilters().put(columns.get(0), periodDataDefine.getKey());
        INvwaUpdatableDataAccess da1 = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        INvwaUpdatableDataSet result = da1.executeQueryForUpdate(context);
        INvwaUpdatableDataAccess dataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        INvwaDataUpdator updaor = dataAccess.openForUpdate(context);
        INvwaDataRow newRow = updaor.addUpdateRow(result.getRow(0).getRowKey());
        newRow.setValue(0, (Object)periodDataDefine.getKey());
        newRow.setValue(1, (Object)periodDataDefine.getCode());
        newRow.setValue(2, (Object)periodDataDefine.getAlias());
        newRow.setValue(3, (Object)periodDataDefine.getTitle());
        newRow.setValue(4, (Object)periodDataDefine.getStartDate());
        newRow.setValue(5, (Object)periodDataDefine.getEndDate());
        newRow.setValue(6, (Object)periodDataDefine.getYear());
        newRow.setValue(7, (Object)periodDataDefine.getQuarter());
        newRow.setValue(8, (Object)periodDataDefine.getMonth());
        newRow.setValue(9, (Object)periodDataDefine.getDay());
        newRow.setValue(10, (Object)periodDataDefine.getTimeKey());
        newRow.setValue(11, (Object)periodDataDefine.getDays());
        newRow.setValue(12, (Object)((Calendar)result.getRow(0).getValue(12)).getTime());
        newRow.setValue(13, result.getRow(0).getValue(13));
        newRow.setValue(14, (Object)periodDataDefine.getUpdateTime());
        newRow.setValue(15, (Object)periodDataDefine.getUpdateUser());
        newRow.setValue(16, (Object)periodDataDefine.getSimpleTitle());
        updaor.commitChanges(context);
    }

    public void deleteCustomPeriodData(String code, String key) throws Exception {
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine table = this.dataModelService.getTableModelDefineByCode(PeriodUtils.addPrefix(code));
        List columns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
        columns = columns.stream().sorted((a, b) -> Double.compare(a.getOrder(), b.getOrder())).collect(Collectors.toList());
        for (ColumnModelDefine column : columns) {
            queryModel.getColumns().add(new NvwaQueryColumn(column));
        }
        queryModel.getColumnFilters().put(columns.get(0), key);
        INvwaUpdatableDataAccess dataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        INvwaUpdatableDataSet result = dataAccess.executeQueryForUpdate(context);
        INvwaUpdatableDataAccess dataAccess2 = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        INvwaDataUpdator updaor = dataAccess2.openForUpdate(context);
        if (result.size() == 1) {
            INvwaDataRow row = updaor.addDeleteRow();
            row.setKeyValue((ColumnModelDefine)columns.get(0), (Object)key);
            updaor.commitChanges(context);
        }
    }

    public void deleteCustomPeriodDatas(String code, List<String> ids) throws Exception {
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine table = this.dataModelService.getTableModelDefineByCode(PeriodUtils.addPrefix(code));
        List columns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
        columns = columns.stream().sorted((a, b) -> Double.compare(a.getOrder(), b.getOrder())).collect(Collectors.toList());
        for (ColumnModelDefine column : columns) {
            queryModel.getColumns().add(new NvwaQueryColumn(column));
        }
        INvwaUpdatableDataAccess dataAccess2 = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        INvwaDataUpdator updaor = dataAccess2.openForUpdate(context);
        for (String key : ids) {
            INvwaDataRow row = updaor.addDeleteRow();
            row.setKeyValue((ColumnModelDefine)columns.get(0), (Object)key);
        }
        updaor.commitChanges(context);
    }

    public PeriodDataDefineImpl getDataByDataCode(String tableCode, String dataCode) throws Exception {
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine table = this.dataModelService.getTableModelDefineByCode(PeriodUtils.addPrefix(tableCode));
        List allColumns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
        allColumns = allColumns.stream().sorted((a, b) -> Double.compare(a.getOrder(), b.getOrder())).collect(Collectors.toList());
        for (ColumnModelDefine column : allColumns) {
            queryModel.getColumns().add(new NvwaQueryColumn(column));
        }
        queryModel.getColumnFilters().put(allColumns.get(1), dataCode);
        INvwaDataAccess dataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        MemoryDataSet result = dataAccess.executeQuery(context);
        PeriodDataDefineImpl pdi = new PeriodDataDefineImpl();
        if (result.size() == 1) {
            DataRow dataRow = result.get(0);
            pdi.setKey(dataRow.getString(0));
            pdi.setCode(dataRow.getString(1));
            pdi.setAlias(dataRow.getString(2));
            pdi.setTitle(dataRow.getString(3));
            pdi.setStartDate(dataRow.getDate(4) != null ? dataRow.getDate(4).getTime() : null);
            pdi.setEndDate(dataRow.getDate(5) != null ? dataRow.getDate(5).getTime() : null);
            pdi.setYear(dataRow.getInt(6));
            pdi.setQuarter(dataRow.getInt(7));
            pdi.setMonth(dataRow.getInt(8));
            pdi.setDay(dataRow.getInt(9));
            pdi.setTimeKey(dataRow.getString(10));
            pdi.setDays(dataRow.getInt(11));
            pdi.setCreateTime(dataRow.getDate(12) != null ? dataRow.getDate(12).getTime() : null);
            pdi.setCreateUser(dataRow.getString(13));
            pdi.setUpdateTime(dataRow.getDate(14) != null ? dataRow.getDate(14).getTime() : null);
            pdi.setUpdateUser(dataRow.getString(15));
            pdi.setSimpleTitle(dataRow.getString(16));
        }
        if (pdi.getKey() != null) {
            return pdi;
        }
        return null;
    }

    public TableModelDefine getTableModelDefine(String tableCode) {
        return this.dataModelService.getTableModelDefineByCode(PeriodUtils.addPrefix(tableCode));
    }

    public TableModelDefine getTableModelDefineByKey(String key) {
        return this.dataModelService.getTableModelDefineById(key);
    }

    public List<ColumnModelDefine> getColumnModelDefinesByTable(String tableid) {
        return this.dataModelService.getColumnModelDefinesByTable(tableid);
    }
}

