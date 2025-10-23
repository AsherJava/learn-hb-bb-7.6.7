/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.mapping.dao.MappingSchemeDao
 */
package com.jiuqi.nr.mapping2.config;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.mapping2.common.NrMappingUtil;
import com.jiuqi.nr.mapping2.dao.JIOConfigDao;
import com.jiuqi.nr.mapping2.provider.NrMappingParam;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.mapping.dao.MappingSchemeDao;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class MappingTypeUpdateExecutor
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(MappingTypeUpdateExecutor.class);
    private JIOConfigDao jioDao = (JIOConfigDao)SpringBeanUtils.getBean(JIOConfigDao.class);
    private MappingSchemeDao schemeDao = (MappingSchemeDao)SpringBeanUtils.getBean(MappingSchemeDao.class);

    public void execute(DataSource dataSource) throws Exception {
        ArrayList<MappingScheme> needModifySchemes = new ArrayList<MappingScheme>();
        List schemes = this.schemeDao.getAll();
        if (!CollectionUtils.isEmpty(schemes)) {
            for (MappingScheme scheme : schemes) {
                NrMappingParam nrMappingParam;
                if (!scheme.getSource().contains("NR-MAPPING-FACTORY") || (nrMappingParam = NrMappingUtil.getNrMappingParam(scheme)) == null || StringUtils.hasText(nrMappingParam.getType())) continue;
                if (this.jioDao.isExist(scheme.getKey())) {
                    nrMappingParam.setType("JIO");
                } else {
                    nrMappingParam.setType("MIDSTORE");
                }
                NrMappingUtil.updateSchemeNrParam(scheme, nrMappingParam);
                needModifySchemes.add(scheme);
            }
        }
        if (!CollectionUtils.isEmpty(needModifySchemes)) {
            this.schemeDao.batchModifyEXT(needModifySchemes);
        }
    }
}

