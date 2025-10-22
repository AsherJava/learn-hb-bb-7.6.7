/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.database.temp.ITempTableMeta
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.common.temptable.common.BaseDynamicTypeEnum
 *  com.jiuqi.nr.dynamic.temptable.common.DynamicTempTableStatusEnum
 *  com.jiuqi.nr.dynamic.temptable.domain.CreatTableRule
 *  com.jiuqi.nr.dynamic.temptable.domain.DynamicTempTableInfo
 *  com.jiuqi.nr.dynamic.temptable.framework.builder.DynamicTempTableQueryBuilder
 *  com.jiuqi.nr.dynamic.temptable.framework.builder.ITableQueryInfo
 *  com.jiuqi.nr.dynamic.temptable.service.IDynamicTempTableManageService
 *  com.jiuqi.nvwa.temptable.pool.noddl.NoDDLTempTableHelper
 *  io.swagger.annotations.Api
 *  javax.annotation.PostConstruct
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.system.check.controller;

import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.database.temp.ITempTableMeta;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.common.temptable.common.BaseDynamicTypeEnum;
import com.jiuqi.nr.dynamic.temptable.common.DynamicTempTableStatusEnum;
import com.jiuqi.nr.dynamic.temptable.domain.CreatTableRule;
import com.jiuqi.nr.dynamic.temptable.domain.DynamicTempTableInfo;
import com.jiuqi.nr.dynamic.temptable.framework.builder.DynamicTempTableQueryBuilder;
import com.jiuqi.nr.dynamic.temptable.framework.builder.ITableQueryInfo;
import com.jiuqi.nr.dynamic.temptable.service.IDynamicTempTableManageService;
import com.jiuqi.nr.system.check.common.SCErrorEnum;
import com.jiuqi.nr.system.check.common.Util;
import com.jiuqi.nr.system.check.model.request.TempTableObj;
import com.jiuqi.nvwa.temptable.pool.noddl.NoDDLTempTableHelper;
import io.swagger.annotations.Api;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/system-check/temp-table"})
@Api(tags={"\u7cfb\u7edf\u68c0\u67e5"})
public class NoDDLTempTableController {
    private static final Logger log = LoggerFactory.getLogger(NoDDLTempTableController.class);
    @Autowired
    private NoDDLTempTableHelper noDDLTempTableHelper;
    @Value(value="${jiuqi.nvwa.temptable.pool:false}")
    private boolean enableTempTablePool;
    @Value(value="${jiuqi.nvwa.databaseLimitMode:false}")
    private boolean databaseLimitMode;
    @Autowired
    private IDynamicTempTableManageService dynamicTempTableManageService;
    private Map<String, BaseDynamicTypeEnum> dynamicTempTableTypes;

    @PostConstruct
    public void init() {
        BaseDynamicTypeEnum[] values = BaseDynamicTypeEnum.values();
        this.dynamicTempTableTypes = new HashMap<String, BaseDynamicTypeEnum>(values.length);
        for (BaseDynamicTypeEnum value : values) {
            this.dynamicTempTableTypes.put(value.getType(), value);
        }
    }

    @GetMapping(value={"/list"})
    public List<TempTableObj> allITempTableMetas() throws JQException {
        if (!this.enableTempTablePool || !this.databaseLimitMode) {
            throw new JQException((ErrorEnum)SCErrorEnum.SYSTEM_CHECK_EXCEPTION_004);
        }
        this.refreshCache();
        List allITempTableMetas = this.noDDLTempTableHelper.getAllITempTableMetas();
        Map<Integer, TempTableObj> dynamicTempTableObjs = this.getAllDynamicTempTableObjs();
        ArrayList<TempTableObj> res = new ArrayList<TempTableObj>(allITempTableMetas.size());
        for (ITempTableMeta tempTableMeta : allITempTableMetas) {
            if (this.dynamicTempTableTypes.containsKey(tempTableMeta.getType())) {
                TempTableObj tempTableObj = this.dynamicTempTable(tempTableMeta, dynamicTempTableObjs);
                if (tempTableObj == null) continue;
                res.add(tempTableObj);
                continue;
            }
            if (CollectionUtils.isEmpty(tempTableMeta.getLogicFields()) || CollectionUtils.isEmpty(tempTableMeta.getPrimaryKeyFields())) continue;
            TempTableObj obj = new TempTableObj();
            obj.setType(tempTableMeta.getType());
            obj.setTypeTitle(tempTableMeta.getType());
            obj.setDescription(tempTableMeta.getTypeTitle());
            try {
                obj.setFreeCount(this.noDDLTempTableHelper.getFreeTempTablesByType(tempTableMeta.getType()));
                obj.setTableNames(this.noDDLTempTableHelper.getAllTempTablesByType(tempTableMeta.getType()));
                obj.setTotalCount(obj.getTableNames() != null ? obj.getTableNames().size() : 0);
                res.add(obj);
            }
            catch (Exception e) {
                log.error("\u67e5\u8be2\u4e34\u65f6\u8868\u4fe1\u606f\u5f02\u5e38: {}", (Object)obj.getType(), (Object)e);
            }
        }
        return res;
    }

    private TempTableObj dynamicTempTable(ITempTableMeta tempTableMeta, Map<Integer, TempTableObj> dynamicTempTableObjs) {
        BaseDynamicTypeEnum baseDynamicTypeEnum = this.dynamicTempTableTypes.get(tempTableMeta.getType());
        if (baseDynamicTypeEnum != null) {
            return dynamicTempTableObjs.get(baseDynamicTypeEnum.getColumnCount());
        }
        return null;
    }

    private void refreshCache() throws JQException {
        try {
            this.noDDLTempTableHelper.refrushCache();
        }
        catch (JobExecutionException e) {
            log.error("\u5237\u65b0NODDL\u4e34\u65f6\u8868\u7f13\u5b58\uff1a{}", (Object)e.getMessage(), (Object)e);
            throw Util.getError("TEMP_TABLE_OBJ", (Exception)((Object)e));
        }
    }

    private Map<Integer, TempTableObj> getAllDynamicTempTableObjs() {
        HashMap<Integer, TempTableObj> res = new HashMap<Integer, TempTableObj>();
        this.dynamicTempTableTypes.forEach((k, v) -> {
            DynamicTempTableQueryBuilder builder = new DynamicTempTableQueryBuilder();
            builder.addQueryColumnCount(v.getColumnCount());
            builder.setQueryStatus(DynamicTempTableStatusEnum.All);
            ITableQueryInfo build = builder.build();
            try {
                List dynamicTempTables = this.dynamicTempTableManageService.getDynamicTempTables(build);
                TempTableObj tempTableObj = new TempTableObj();
                tempTableObj.setType(v.getType());
                tempTableObj.setTypeTitle(v.getType());
                tempTableObj.setTableNames(Collections.emptyList());
                tempTableObj.setTotalCount(0);
                tempTableObj.setDescription(v.getType());
                tempTableObj.setExpandCount(0);
                tempTableObj.setFreeCount(0);
                if (dynamicTempTables != null && !dynamicTempTables.isEmpty()) {
                    tempTableObj.setTableNames(dynamicTempTables.stream().map(DynamicTempTableInfo::getTableName).collect(Collectors.toList()));
                    tempTableObj.setTotalCount(tempTableObj.getTableNames().size());
                    tempTableObj.setFreeCount((int)dynamicTempTables.stream().filter(t -> DynamicTempTableStatusEnum.AVAILABLE.equals((Object)t.getStatus())).count());
                }
                res.put(v.getColumnCount(), tempTableObj);
            }
            catch (SQLException e) {
                log.error("\u83b7\u53d6\u52a8\u6001\u4e34\u65f6\u8868\u5f02\u5e38\uff1a{}", (Object)e.getMessage(), (Object)e);
            }
        });
        return res;
    }

    @PostMapping(value={"/generate"})
    public List<String> generate(@RequestBody List<TempTableObj> objs) throws JQException {
        ArrayList<String> res = new ArrayList<String>(objs.size() * 2);
        List tempTableMetas = this.noDDLTempTableHelper.getAllITempTableMetas();
        Map<String, ITempTableMeta> tableMetaMap = tempTableMetas.stream().collect(Collectors.toMap(ITempTableMeta::getType, t -> t));
        for (TempTableObj obj : objs) {
            if (obj.getExpandCount() <= 0) continue;
            try {
                List sqls;
                if (this.dynamicTempTableTypes.containsKey(obj.getType())) {
                    int columnCount = this.dynamicTempTableTypes.get(obj.getType()).getColumnCount();
                    sqls = this.dynamicTempTableManageService.getCreateTableDDLs(new CreatTableRule(columnCount, obj.getExpandCount().intValue()));
                } else {
                    ITempTableMeta tempTableMeta = tableMetaMap.get(obj.getType());
                    sqls = this.noDDLTempTableHelper.getCreateTableDDLs(tempTableMeta, obj.getExpandCount().intValue());
                }
                res.addAll(sqls);
            }
            catch (Exception e) {
                log.error("\u4e34\u65f6\u8868SQL\u751f\u6210\u5931\u8d25\uff1a{}", (Object)obj.getType(), (Object)e);
                throw Util.getError("TEMP_TABLE_OBJ", String.format("\u4e34\u65f6\u8868SQL\u751f\u6210\u5931\u8d25\uff1a%s", obj.getType()));
            }
        }
        return res;
    }
}

