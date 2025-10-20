/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.basic.base.template.EntNativeSqlTemplate
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 */
package com.jiuqi.gcreport.inputdata.function.sumhb.dao;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.basic.base.template.EntNativeSqlTemplate;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.function.sumhb.dao.SumhbClearDao;
import com.jiuqi.gcreport.inputdata.function.sumhb.entity.SumHbTempEO;
import com.jiuqi.gcreport.inputdata.function.sumhb.service.SumHbTempServiceImpl;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class SumhbTempDao {
    private EntNativeSqlTemplate template;
    private SumHbTempServiceImpl sumHbTempService;
    private Logger logger = LoggerFactory.getLogger(Logger.class);
    private String batchId;
    private String sumBatchId;
    private String insertSql = "insert into %s (batchId,formId,subjectCode,id) values (?,?,?,?)";
    private boolean isTempTable = false;
    private final String tableName;
    private static final String HANASQL_NAME = "HANA";
    private static final String ORACLESQL_NAME = "ORACLE";
    private static final String DM_NAME = "DM";

    public static SumhbTempDao newInstance() {
        return new SumhbTempDao();
    }

    public void insert(List<SumHbTempEO> sumHbTempItems) {
        if (CollectionUtils.isEmpty(sumHbTempItems)) {
            this.batchId = null;
            return;
        }
        this.batchId = UUIDUtils.newUUIDStr();
        this.sumHbTempService.addBatchSumHbTempsByIsTempTableFlag(this.template, sumHbTempItems, this.batchId, this.insertSql, this.isTempTable);
    }

    public void insertSum(List<SumHbTempEO> sumHbTempItems) {
        if (CollectionUtils.isEmpty(sumHbTempItems)) {
            this.sumBatchId = null;
            return;
        }
        this.sumBatchId = UUIDUtils.newUUIDStr();
        this.sumHbTempService.addBatchSumHbTempsByIsTempTableFlag(this.template, sumHbTempItems, this.sumBatchId, this.insertSql, this.isTempTable);
    }

    public String getBatchId() {
        return this.batchId;
    }

    public String getSumBatchId() {
        return this.sumBatchId;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void clear() {
    }

    private SumhbTempDao() {
        String dataBaseName;
        this.template = (EntNativeSqlTemplate)SpringContextUtils.getBean(EntNativeSqlTemplate.class);
        this.sumHbTempService = (SumHbTempServiceImpl)SpringContextUtils.getBean(SumHbTempServiceImpl.class);
        try {
            dataBaseName = this.template.getDatabase().getName();
        }
        catch (SQLException e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.function.sumhbtemptablemsg"), (Throwable)e);
        }
        if (HANASQL_NAME.equalsIgnoreCase(dataBaseName) || ORACLESQL_NAME.equalsIgnoreCase(dataBaseName) || DM_NAME.equalsIgnoreCase(dataBaseName)) {
            this.tableName = "GC_SUMHBTEMP";
        } else {
            Random random = new Random();
            int tableNum = random.nextInt(10) + 1;
            int offset = ((SumhbClearDao)SpringContextUtils.getBean(SumhbClearDao.class)).getOffset();
            this.tableName = "GC_SUMHBNOTEMP" + (tableNum += offset);
            this.isTempTable = true;
        }
        this.insertSql = String.format(this.insertSql, this.tableName);
    }
}

