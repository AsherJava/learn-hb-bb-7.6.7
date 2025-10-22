/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.common.service.Mappings;
import com.jiuqi.nr.data.common.service.QueryMappings;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataCommonUtils {
    private static final Logger log = LoggerFactory.getLogger(DataCommonUtils.class);

    public static void writeVersionInfo(DimensionValueSet dimensionValueSet, List<String> formCodes, String filePath) throws IOException {
        DataCommonUtils.writeVersionInfoExtra(dimensionValueSet, formCodes, filePath, null);
    }

    public static void writeVersionInfoExtra(DimensionValueSet dimensionValueSet, List<String> formCodes, String filePath, Map<String, String> extraAttributes) throws IOException {
        HashMap<String, Object> versionDim = new HashMap<String, Object>();
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            versionDim.put(dimensionValueSet.getName(i), dimensionValueSet.getValue(i));
        }
        StringBuilder sb = new StringBuilder();
        sb.append("version:").append("v1").append("\n");
        sb.append("createTime:").append(LocalDateTime.now(ZoneId.of("Z")).atZone(ZoneId.of("Z"))).append("\n");
        sb.append("form:").append(formCodes.toString()).append("\n");
        sb.append("dimensions:").append(((Object)versionDim).toString()).append("\n");
        if (extraAttributes != null) {
            for (String attribute : extraAttributes.keySet()) {
                sb.append(attribute).append(":").append(extraAttributes.get(attribute)).append("\n");
            }
        }
    }

    public static void buildMappings(String formSchemeKey, String filePath, QueryMappings queryMappings) throws IOException {
        Mappings mappings = queryMappings.queryMappings(formSchemeKey);
        if (mappings != null) {
            HashMap<String, Map<String, String>> mappingTable = new HashMap<String, Map<String, String>>();
            mappingTable.put("TASK", mappings.getTaskCode());
            mappingTable.put("FORMSCHEME", mappings.getFormSchemeCode());
            mappingTable.put("FORM", mappings.getFormCodes());
            mappingTable.put("FORMULASCHEME", mappings.getFormulaSchemeCode());
            mappingTable.put("FORMULA", mappings.getFormulaCodes());
            mappingTable.put("ORG", mappings.getOrgs());
            ObjectMapper mapper = new ObjectMapper();
            byte[] bytes = mapper.writeValueAsBytes(mappingTable);
            try (FileOutputStream out = new FileOutputStream(FilenameUtils.normalize(filePath + "MAPPINGS"));
                 ByteArrayInputStream is = new ByteArrayInputStream(bytes);){
                byte[] buff = new byte[1024];
                int len = 0;
                while ((len = is.read(buff)) != -1) {
                    ((OutputStream)out).write(buff, 0, len);
                }
            }
            catch (Exception e) {
                log.error("\u5199\u6620\u5c04\u6587\u4ef6\u51fa\u9519{}", (Object)e.getMessage());
            }
        }
    }

    public static DimensionCollection removeDimValuesFromCollection(DimensionCollection dimensionCollection, String dimName, Set<Object> dimValues) {
        List dimensionCombinations = dimensionCollection.getDimensionCombinations();
        Iterator iterator = dimensionCombinations.iterator();
        while (iterator.hasNext()) {
            DimensionCombination dimensionCombination = (DimensionCombination)iterator.next();
            Object value = dimensionCombination.getValue(dimName);
            if (!dimValues.contains(value)) continue;
            iterator.remove();
        }
        return DimensionCollectionUtil.buildCollection((List)dimensionCombinations);
    }
}

