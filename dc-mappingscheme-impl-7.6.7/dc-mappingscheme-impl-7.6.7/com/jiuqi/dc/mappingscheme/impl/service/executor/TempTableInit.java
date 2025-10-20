/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.dc.mappingscheme.impl.service.executor;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TempTableInit
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(TempTableInit.class);

    public void execute(DataSource dataSource) throws Exception {
        logger.info("\u5f00\u59cb\u521b\u5efa\u4e34\u65f6\u8868");
        DataSchemeService dataSchemeService = (DataSchemeService)SpringContextUtils.getBean(DataSchemeService.class);
        List<DataSchemeDTO> dataSchemeDTOS = dataSchemeService.listAll();
        for (DataSchemeDTO dataSchemeDTO : dataSchemeDTOS) {
            try {
                dataSchemeService.initTemporaryTable(dataSchemeDTO.getId());
            }
            catch (Exception e) {
                logger.info(String.format("\u6570\u636e\u6e90\u3010%1$s\u3011\u521d\u59cb\u5316\u4e34\u65f6\u8868\u5931\u8d25", dataSchemeDTO.getName()));
            }
        }
        logger.info("\u521b\u5efa\u4e34\u65f6\u8868\u7ed3\u675f");
    }
}

