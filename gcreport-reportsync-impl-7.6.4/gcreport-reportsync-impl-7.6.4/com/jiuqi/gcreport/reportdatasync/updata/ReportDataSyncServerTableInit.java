/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.DefinitionAutoCollectionService
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.gcreport.reportdatasync.updata;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.DefinitionAutoCollectionService;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncServerInfoEO;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncServerListEO;
import com.jiuqi.gcreport.reportdatasync.enums.ReportDataServerType;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

public class ReportDataSyncServerTableInit
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    String GC_SERVERINFO_SQL = "  select * from GC_DATASYNC_SERVERINFO  t";

    public void execute(DataSource dataSource) {
        try {
            ((DefinitionAutoCollectionService)SpringContextUtils.getBean(DefinitionAutoCollectionService.class)).initTableDefineByTableName("GC_DATASYNC_SERVERLIST");
            ((DefinitionAutoCollectionService)SpringContextUtils.getBean(DefinitionAutoCollectionService.class)).initTableDefineByTableName("GC_MULTILEVEL_ORGDATA_LOG");
            ((DefinitionAutoCollectionService)SpringContextUtils.getBean(DefinitionAutoCollectionService.class)).initTableDefineByTableName("GC_PARAMSYNC_RECEIVE_LOGITEM");
            ((DefinitionAutoCollectionService)SpringContextUtils.getBean(DefinitionAutoCollectionService.class)).initTableDefineByTableName("GC_DATASYNC_RECEIVE");
            ((DefinitionAutoCollectionService)SpringContextUtils.getBean(DefinitionAutoCollectionService.class)).initTableDefineByTableName("GC_DATASYNC_UPLOAD_LOG");
            List serverInfoEOList = EntNativeSqlDefaultDao.newInstance((String)"GC_DATASYNC_SERVERINFO", ReportDataSyncServerInfoEO.class).selectEntity(this.GC_SERVERINFO_SQL, new Object[0]);
            if (CollectionUtils.isEmpty((Collection)serverInfoEOList)) {
                return;
            }
            ReportDataSyncServerInfoEO serverInfoEO = (ReportDataSyncServerInfoEO)((Object)serverInfoEOList.get(0));
            ReportDataSyncServerInfoVO serverInfoVO = new ReportDataSyncServerInfoVO();
            BeanUtils.copyProperties((Object)serverInfoEO, serverInfoVO);
            serverInfoVO.setSyncParamFlag(serverInfoEO.getSyncParamFlag() != null && serverInfoEO.getSyncParamFlag().equals(1) ? Boolean.TRUE : Boolean.FALSE);
            serverInfoVO.setSyncModifyFlag(serverInfoEO.getSyncModifyFlag() != null && serverInfoEO.getSyncModifyFlag().equals(1) ? Boolean.TRUE : Boolean.FALSE);
            serverInfoVO.setFileFormat("nrd");
            HashMap<String, String> content = new HashMap<String, String>();
            content.put("targetUrl", serverInfoVO.getTargetUrl());
            content.put("targetUserName", serverInfoVO.getTargetUserName());
            content.put("targetPwd", serverInfoVO.getTargetPwd());
            content.put("targetPwd2", serverInfoVO.getTargetPwd());
            content.put("url", serverInfoVO.getUrl());
            content.put("userName", serverInfoVO.getUserName());
            content.put("pwd", serverInfoVO.getPwd());
            content.put("pwd2", serverInfoVO.getPwd());
            content.put("encryptType", serverInfoVO.getEncryptType());
            content.put("targetEncryptType", serverInfoVO.getTargetEncryptType());
            EntNativeSqlDefaultDao serverListDao = EntNativeSqlDefaultDao.newInstance((String)"GC_DATASYNC_SERVERLIST", ReportDataSyncServerListEO.class);
            ReportDataSyncServerListEO serverInfo = new ReportDataSyncServerListEO();
            BeanUtils.copyProperties(serverInfoVO, (Object)serverInfo);
            serverInfo.setModifyTime(new Date());
            serverInfo.setSourceType(serverInfoVO.getSourceType());
            serverInfo.setSyncParamFlag(serverInfoVO.getSyncParamFlag() != false ? 1 : 0);
            serverInfo.setSupportAll(1);
            serverInfo.setServerType(ReportDataServerType.MANUAL.getCode());
            serverInfo.setSyncType("reportdata");
            serverInfo.setSyncMethod("DEFAULT");
            serverInfo.setStartFlag(1);
            serverInfo.setUrl(serverInfoVO.getTargetUrl());
            serverInfo.setUserName(serverInfoVO.getTargetUserName());
            serverInfo.setPwd(serverInfoVO.getTargetPwd());
            serverInfo.setEncryptType(serverInfoVO.getTargetEncryptType());
            serverInfo.setContent(JsonUtils.writeValueAsString(content));
            serverListDao.add((BaseEntity)serverInfo);
        }
        catch (Exception e) {
            this.logger.error("\u56fd\u8d44\u59d4\u53c2\u76ee\u6807\u670d\u52a1\u5668\u7ba1\u7406\u8868\u521b\u5efa\u5931\u8d25\uff0c\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }
}

