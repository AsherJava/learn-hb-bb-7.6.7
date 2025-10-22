/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  javax.servlet.ServletContext
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.invest.monthcalcscheme.task;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.invest.investbill.bill.task.InvestBillModuleInitiator;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ChangeScenarioDataTaskInit
implements InvestBillModuleInitiator {
    private transient Logger logger = LoggerFactory.getLogger(ChangeScenarioDataTaskInit.class);

    public void init(ServletContext context) throws Exception {
    }

    @Transactional
    public void initWhenStarted(ServletContext context) {
        try {
            String querySql = "select count(*) from MD_CHANGERATIO";
            int count = EntNativeSqlDefaultDao.getInstance().count(querySql, new Object[0]);
            if (count > 0) {
                try {
                    JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringContextUtils.getBean(JdbcTemplate.class);
                    String sql = "select count(*) from MD_CHANGERATIO where CHANGETYPE is not null";
                    if ((Integer)jdbcTemplate.queryForObject(sql, Integer.class) > 0) {
                        return;
                    }
                    this.logger.info("\u521d\u59cb\u5316\u53d8\u52a8\u573a\u666f\uff1a\u5b58\u5728\u5386\u53f2\u6570\u636e\uff0c\u4e3a\u65e7\u9879\u76ee\uff0c\u65e0\u9700\u589e\u52a0\u53d8\u52a8\u573a\u666f\u57fa\u7840\u9879\uff0c \u5c06\u53d8\u52a8\u7c7b\u578b\u7684\u5b57\u6bb5\u7684\u503c\u8bbe\u7f6e\u4e3a01");
                    String updateSql = "update MD_CHANGERATIO set CHANGETYPE = '01' where CHANGETYPE is null";
                    jdbcTemplate.execute(updateSql);
                }
                catch (Exception e) {
                    this.logger.error("\u53d8\u52a8\u573a\u666f\u66f4\u65b0\u53d8\u52a8\u7c7b\u578b\u5b57\u6bb5\u51fa\u9519" + e.getMessage(), e);
                }
            } else {
                ClassPathResource resource = new ClassPathResource("config/vabasedata/initdata/MD_CHANGERATIO.json");
                String changeScenarioData = IOUtils.toString(resource.getInputStream(), "UTF-8").trim();
                List baseDataDOList = (List)JsonUtils.readValue((String)changeScenarioData, (TypeReference)new TypeReference<List<BaseDataDO>>(){});
                baseDataDOList.forEach(item -> {
                    Map fieldsMap = (Map)item.get((Object)"fields");
                    fieldsMap.forEach((k, v) -> {
                        if (!k.equalsIgnoreCase("id")) {
                            item.put(k.toLowerCase(), v);
                        }
                    });
                });
                BaseDataClient baseDataClient = (BaseDataClient)SpringContextUtils.getBean(BaseDataClient.class);
                BaseDataBatchOptDTO param = new BaseDataBatchOptDTO();
                param.setDataList(baseDataDOList);
                R r1 = baseDataClient.batchAdd(param);
                if (r1.getCode() == 1) {
                    throw new RuntimeException("\u65b0\u9879\u76ee\u521d\u59cb\u5316\u53d8\u52a8\u573a\u666f\u51fa\u9519" + r1.getMsg());
                }
            }
        }
        catch (Exception e) {
            this.logger.error("\u521d\u59cb\u5316\u53d8\u52a8\u573a\u666f\u51fa\u9519" + e.getMessage(), e);
        }
    }
}

