/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.excel.EasyExcel
 *  com.alibaba.excel.read.builder.ExcelReaderBuilder
 *  com.alibaba.excel.read.listener.ReadListener
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.feign.client.OrgDataClient
 */
package com.jiuqi.common.systemparam.executor;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.listener.ReadListener;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.systemparam.consts.EntSystemParamInitConst;
import com.jiuqi.common.systemparam.executor.orgdata.EntOrgDataImpExcelListener;
import com.jiuqi.common.systemparam.util.EntParamInitFileUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntOrgDataInitExecutor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String INIT_ORG_DATA_FILE_PATH = EntSystemParamInitConst.SYSTEM_PARAM_BASE_URL + "initOrgData.xlsx";

    public void execute(DataSource dataSource) throws Exception {
        this.execute(dataSource, Arrays.asList("MD_ORG"));
    }

    public void execute(DataSource dataSource, List<String> orgTypeList) throws Exception {
        Map<String, List<OrgDTO>> impParseData;
        InputStream inputStream = EntParamInitFileUtil.getResourceInputStream(this.INIT_ORG_DATA_FILE_PATH);
        Object object = null;
        try {
            if (inputStream == null) {
                this.logger.info("\u672a\u627e\u5230\u9884\u7f6e\u7ec4\u7ec7\u673a\u6784\u6570\u636e\u8d44\u6e90\uff0c\u8df3\u8fc7\u7ec4\u7ec7\u673a\u6784\u521d\u59cb\u5316\uff01");
                return;
            }
            EntOrgDataImpExcelListener listener = new EntOrgDataImpExcelListener();
            ((ExcelReaderBuilder)((ExcelReaderBuilder)EasyExcel.read((InputStream)inputStream).headRowNumber(Integer.valueOf(1))).registerReadListener((ReadListener)listener)).doReadAll();
            impParseData = listener.getImpParseData();
        }
        catch (Throwable listener) {
            object = listener;
            throw listener;
        }
        finally {
            if (inputStream != null) {
                if (object != null) {
                    try {
                        inputStream.close();
                    }
                    catch (Throwable throwable) {
                        ((Throwable)object).addSuppressed(throwable);
                    }
                } else {
                    inputStream.close();
                }
            }
        }
        OrgDataClient orgDataClient = (OrgDataClient)SpringContextUtils.getBean(OrgDataClient.class);
        if (orgTypeList.contains("MD_ORG")) {
            List<OrgDTO> mdOrgDataList = impParseData.get("MD_ORG");
            mdOrgDataList.stream().forEach(item -> {
                R r = orgDataClient.add(item);
                this.logger.info("\u521d\u59cb\u5316\u884c\u653f\u7ec4\u7ec7\u673a\u6784\u5355\u4f4d\u3010" + item.getCode() + "\u3011\uff1a" + r.getMsg());
            });
        }
        for (String orgType : impParseData.keySet()) {
            if ("MD_ORG".equals(orgType) || !orgTypeList.contains(orgType)) continue;
            impParseData.get(orgType).forEach(item -> {
                R r = orgDataClient.add(item);
                this.logger.info("\u521d\u59cb\u5316\u7ec4\u7ec7\u673a\u6784\u3010" + orgType + "\u3011\u5355\u4f4d\u3010" + item.getCode() + "\u3011\uff1a" + r.getMsg());
            });
        }
    }
}

