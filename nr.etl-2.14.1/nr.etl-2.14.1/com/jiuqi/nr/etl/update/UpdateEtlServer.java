/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator
 *  module.manager.intf.CustomClassExecutor
 */
package com.jiuqi.nr.etl.update;

import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.nr.etl.common.EtlServeEntity;
import com.jiuqi.nr.etl.common.ServeType;
import com.jiuqi.nr.etl.service.internal.EtlServeEntityDao;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator;
import java.time.Instant;
import javax.sql.DataSource;
import module.manager.intf.CustomClassExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class UpdateEtlServer
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        Logger logger = LoggerFactory.getLogger(UpdateEtlServer.class);
        String url = null;
        String userName = null;
        try {
            SystemOptionOperator system = (SystemOptionOperator)BeanUtils.getBean(SystemOptionOperator.class);
            url = system.query("ETLUrl");
            userName = system.query("ETLUserName");
            String passWord = system.query("ETLPassword");
            if (StringUtils.hasLength(url)) {
                EtlServeEntity entity = new EtlServeEntity();
                if (url.startsWith("https://")) {
                    entity.setProtocol("https");
                    entity.setUrl(url.substring("https://".length()));
                } else if (url.startsWith("http://")) {
                    entity.setProtocol("http");
                    entity.setUrl(url.substring("http://".length()));
                } else {
                    return;
                }
                entity.setUserName(userName);
                entity.setPwd(passWord);
                entity.setType(ServeType.ETL);
                entity.setCreateUser("auto-update");
                entity.setCreateTime(Instant.now());
                EtlServeEntityDao bean = (EtlServeEntityDao)BeanUtils.getBean(EtlServeEntityDao.class);
                bean.save(entity);
                logger.info("\u7cfb\u7edf\u9009\u9879\u914d\u7f6e\u670d\u52a1\u4fe1\u606f url:{},username:{},pwd:*** ------->>>>> {}", url, userName, entity);
            }
        }
        catch (Exception e) {
            logger.error("\u7cfb\u7edf\u9009\u9879\u914d\u7f6e\u670d\u52a1\u4fe1\u606f url:{},username:{},pwd:*** ------->>>>> \u5347\u7ea7\u5931\u8d25\uff0c\u8bf7\u5230\u7cfb\u7edf\u9009\u9879\u91cd\u65b0\u914d\u7f6e", url, userName, e);
        }
    }
}

