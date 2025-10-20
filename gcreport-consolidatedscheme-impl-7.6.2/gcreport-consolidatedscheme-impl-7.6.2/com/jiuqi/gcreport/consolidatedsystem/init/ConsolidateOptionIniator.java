/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ReclassifyOtherInfoVO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  io.jsonwebtoken.lang.Collections
 *  jcifs.util.Base64
 */
package com.jiuqi.gcreport.consolidatedsystem.init;

import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.option.ConsolidatedOptionEO;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ReclassifyOtherInfoVO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.np.sql.CustomClassExecutor;
import io.jsonwebtoken.lang.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import jcifs.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ConsolidateOptionIniator
implements CustomClassExecutor {
    private static Logger logger = LoggerFactory.getLogger(ConsolidateOptionIniator.class);
    private final String dimsionQuerySql = "select * from GC_DIMENSION";
    private final String optionQuerySql = "select * from GC_CONSOPTION";
    private final String optionSaveSql = "update GC_CONSOPTION set OPTIONDATA = ? where ID = ?";

    public void execute(DataSource dataSource) throws Exception {
        Map<String, String> dimsionId2Code = EntNativeSqlDefaultDao.getInstance().selectMap("select * from GC_DIMENSION", new Object[0]).stream().collect(Collectors.toMap(v -> v.get("ID").toString(), v -> v.get("CODE").toString()));
        List optionLists = EntNativeSqlDefaultDao.getInstance().selectMap("select * from GC_CONSOPTION", new Object[0]);
        for (Map optionMap : optionLists) {
            ReclassifyOtherInfoVO reduceReclassifyOtherInfo;
            List reduceDimsionIds;
            ReclassifyOtherInfoVO reclassifyOtherInfoVO;
            List reclassifyDimsionIds;
            ConsolidatedOptionEO optionEO = new ConsolidatedOptionEO();
            optionEO.setId(optionMap.get("ID").toString());
            optionEO.setSystemId(optionMap.get("SYSTEMID").toString());
            optionEO.setData(optionMap.get("OPTIONDATA").toString());
            String jsonString = new String(Base64.decode((String)optionEO.getData()));
            ConsolidatedOptionVO optionVO = null;
            try {
                optionVO = (ConsolidatedOptionVO)JsonUtils.readValue((String)jsonString, ConsolidatedOptionVO.class);
            }
            catch (Exception e) {
                logger.error("id\u4e3a\uff1a" + optionEO.getId() + "\u7684\u5408\u5e76\u4f53\u7cfbdata\u8f6c\u6362vo\u5bf9\u8c61\u5931\u8d25\uff0c\u539f\u56e0\u4e3a\uff1a" + e.getMessage(), e);
                continue;
            }
            List managementDimensionList = optionVO.getManagementDimension();
            if (!Collections.isEmpty((Collection)managementDimensionList)) {
                ArrayList<String> newDimsionList = new ArrayList<String>();
                for (String managementDimension : managementDimensionList) {
                    Object suffix;
                    String dimensionId;
                    int colonIndex = managementDimension.indexOf(58);
                    if (colonIndex != -1) {
                        dimensionId = managementDimension.substring(0, colonIndex);
                        suffix = managementDimension.substring(colonIndex);
                    } else {
                        dimensionId = managementDimension;
                        suffix = "";
                    }
                    if (dimsionId2Code.containsKey(dimensionId)) {
                        newDimsionList.add(dimsionId2Code.get(dimensionId) + (String)suffix);
                        continue;
                    }
                    newDimsionList.add(managementDimension);
                    logger.error("\u5408\u5e76\u9009\u9879id\u4e3a:" + managementDimension + "\u7684\u7ba1\u7406\u4f1a\u8ba1\u7ef4\u5ea6\u672a\u627e\u5230\u5bf9\u5e94code");
                }
                optionVO.setManagementDimension(newDimsionList);
            }
            if (!Collections.isEmpty((Collection)(reclassifyDimsionIds = (reclassifyOtherInfoVO = optionVO.getReclassifyOtherInfo()).getDimensionIds()))) {
                ArrayList<String> newDimensionIds = new ArrayList<String>();
                for (String dimensionId : reclassifyDimsionIds) {
                    newDimensionIds.add(dimsionId2Code.getOrDefault(dimensionId, dimensionId));
                }
                reclassifyOtherInfoVO.setDimensionIds(newDimensionIds);
            }
            if (!Collections.isEmpty((Collection)(reduceDimsionIds = (reduceReclassifyOtherInfo = optionVO.getReduceReclassifyOtherInfo()).getDimensionIds()))) {
                ArrayList<String> newDimensionIds = new ArrayList<String>();
                for (String dimensionId : reduceDimsionIds) {
                    newDimensionIds.add(dimsionId2Code.getOrDefault(dimensionId, dimensionId));
                }
                reduceReclassifyOtherInfo.setDimensionIds(newDimensionIds);
            }
            String optionJSONString = JsonUtils.writeValueAsString((Object)optionVO);
            String encode = Base64.encode((byte[])optionJSONString.getBytes());
            optionEO.setData(encode);
            EntNativeSqlDefaultDao.getInstance().execute("update GC_CONSOPTION set OPTIONDATA = ? where ID = ?", Arrays.asList(optionEO.getData(), optionEO.getId()));
        }
    }
}

