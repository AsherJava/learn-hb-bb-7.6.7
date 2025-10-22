/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.examine.facade.DataClearParamObj
 *  com.jiuqi.nr.examine.service.IDataSchemeDataClearExtendService
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.data.logic.internal.service.impl;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.data.logic.internal.util.CheckTableNameUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.examine.facade.DataClearParamObj;
import com.jiuqi.nr.examine.service.IDataSchemeDataClearExtendService;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class DataClear
implements IDataSchemeDataClearExtendService {
    private static final Logger logger = LoggerFactory.getLogger(DataClear.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private NrdbHelper nrdbHelper;
    @Autowired
    private INvwaDataAccessProvider dataAccessProvider;
    @Autowired
    private DataModelService dataModelService;

    public void doClear(DataClearParamObj clearParam) {
        List<FormSchemeDefine> allFormSchemesByTask = this.getAllFormSchemesByTask(clearParam.getTaskKey());
        this.clearCheckTables(allFormSchemesByTask);
        this.clearCheckSchemeRecordTable(allFormSchemesByTask);
    }

    private void clearCheckTables(List<FormSchemeDefine> formSchemes) {
        List<String> allTableNames = this.getAllTableNames(formSchemes);
        if (!CollectionUtils.isEmpty(allTableNames)) {
            for (String table : allTableNames) {
                try {
                    if (this.nrdbHelper.isEnableNrdb()) {
                        NvwaQueryModel queryModel = new NvwaQueryModel();
                        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(table);
                        List columnModelDefinesByTable = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
                        columnModelDefinesByTable.forEach(o -> queryModel.getColumns().add(new NvwaQueryColumn(o)));
                        INvwaUpdatableDataAccess updatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
                        DataAccessContext context = new DataAccessContext(this.dataModelService);
                        INvwaDataUpdator dataUpdator = updatableDataAccess.openForUpdate(context);
                        dataUpdator.deleteAll();
                        dataUpdator.commitChanges(context);
                    } else {
                        this.jdbcTemplate.execute("TRUNCATE TABLE " + table);
                    }
                    logger.info("{}\u8868\u6570\u636e\u6e05\u9664\u6210\u529f", (Object)table);
                }
                catch (Exception e) {
                    logger.error(table + "\u8868\u6570\u636e\u6e05\u9664\u5931\u8d25:" + e.getMessage(), e);
                }
            }
        }
    }

    private void clearCheckSchemeRecordTable(List<FormSchemeDefine> formSchemes) {
        if (!CollectionUtils.isEmpty(formSchemes)) {
            Object[] args = formSchemes.stream().map(IBaseMetaItem::getKey).toArray();
            String sql = this.getSql(args.length);
            try {
                this.jdbcTemplate.update(sql, args);
                logger.info("{}\u8868\u6570\u636e\u6e05\u9664\u6210\u529f", (Object)"NR_PARAM_REVIEW_INFO");
            }
            catch (Exception e) {
                logger.error("NR_PARAM_REVIEW_INFO\u8868\u6570\u636e\u6e05\u9664\u5931\u8d25:" + e.getMessage(), e);
            }
        }
    }

    private String getSql(int argLength) {
        StringBuilder s = new StringBuilder();
        s.append("DELETE FROM %s WHERE %s IN(");
        for (int i = 0; i < argLength; ++i) {
            s.append("?,");
        }
        s.setLength(s.length() - 1);
        s.append(")");
        return String.format(s.toString(), "NR_PARAM_REVIEW_INFO", "RWIF_FM_SCHEME");
    }

    private List<String> getAllTableNames(List<FormSchemeDefine> formSchemes) {
        ArrayList<String> allTableNames = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(formSchemes)) {
            for (FormSchemeDefine formSchemeDefine : formSchemes) {
                String formSchemeCode = formSchemeDefine.getFormSchemeCode();
                allTableNames.add(CheckTableNameUtil.getAllCKRTableName(formSchemeCode));
                allTableNames.add(CheckTableNameUtil.getCKRTableName(formSchemeCode));
                allTableNames.add(CheckTableNameUtil.getCKDTableName(formSchemeCode));
            }
        }
        return allTableNames;
    }

    private List<FormSchemeDefine> getAllFormSchemesByTask(List<String> taskKey) {
        ArrayList<FormSchemeDefine> result = new ArrayList<FormSchemeDefine>();
        if (!CollectionUtils.isEmpty(taskKey)) {
            for (String task : taskKey) {
                List formSchemeDefines = null;
                try {
                    formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(task);
                }
                catch (Exception e) {
                    logger.error("\u83b7\u53d6\u4efb\u52a1" + task + "\u4e0b\u62a5\u8868\u65b9\u6848\u5f02\u5e38:" + e.getMessage(), e);
                }
                if (CollectionUtils.isEmpty(formSchemeDefines)) continue;
                result.addAll(formSchemeDefines);
            }
        }
        return result;
    }
}

