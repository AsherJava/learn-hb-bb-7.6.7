/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nvwa.mapping.service.IMappingSchemeService
 *  org.json.JSONObject
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.mapping2.config;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.mapping2.bean.ZBMapping;
import com.jiuqi.nvwa.mapping.service.IMappingSchemeService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import javax.sql.DataSource;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class MappingRegionUpdateExecutor
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(MappingRegionUpdateExecutor.class);
    private JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringBeanUtils.getBean(JdbcTemplate.class);
    private IRunTimeViewController rtViewCtrl = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
    private IMappingSchemeService mappingSchemeService = (IMappingSchemeService)SpringBeanUtils.getBean(IMappingSchemeService.class);
    private static final String TABLENAME = "NVWA_MAPPING_NR_ZB";
    private static final String KEY = "MZ_KEY";
    private static final String MAPPINGSCHEME = "MZ_MAPPINGSCHEME";
    private static final String FORM = "MZ_FORM_CODE";
    private static final String TABLE = "MZ_TABLE_CODE";
    private static final String REGION = "MZ_REGION_CODE";
    private static final String ZB = "MZ_ZB_CODE";
    private static final String MAPPING = "MZ_MAPPING";
    private static final String ZB_MAPPING;
    private static final Function<ResultSet, ZBMapping> ENTITY_READER_ZB_MAPPING;

    public void execute(DataSource dataSource) throws Exception {
        String formSchemeKey;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.logger.info("\u6a21\u5757[\u5973\u5a32\u6620\u5c04\u65b9\u6848NR\u6269\u5c55]\u6307\u6807\u6620\u5c04\u4fee\u590dregionCode\u5f00\u59cb\uff1a" + sdf.format(new Date()));
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s IS NULL OR %s = '' ORDER BY %s", ZB_MAPPING, TABLENAME, REGION, REGION, FORM);
        List list = this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_ZB_MAPPING.apply(rs));
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        HashMap<String, String> mTof = new HashMap<String, String>();
        List allSchemes = this.mappingSchemeService.getAllSchemes();
        for (Object mappingScheme : allSchemes) {
            JSONObject jsonObject;
            String NrTask;
            String extParam = mappingScheme.getExtParam();
            if (!StringUtils.hasText(extParam) || !extParam.contains("NrTask") || !StringUtils.hasText(NrTask = (jsonObject = new JSONObject(extParam)).getString("NrTask")) || !NrTask.contains("formSchemeKey") || !StringUtils.hasText(formSchemeKey = (jsonObject = new JSONObject(NrTask)).getString("formSchemeKey"))) continue;
            mTof.put(mappingScheme.getKey(), formSchemeKey);
        }
        HashMap cache = new HashMap();
        for (ZBMapping mapping : list) {
            String regionCode;
            String formCode = mapping.getForm();
            String zbCode = mapping.getZbCode();
            formSchemeKey = (String)mTof.get(mapping.getMsKey());
            HashMap<String, String> zbCache = (HashMap<String, String>)cache.get(formSchemeKey + "@" + formCode);
            if (zbCache == null) {
                zbCache = new HashMap<String, String>();
                cache.put(formSchemeKey + "@" + formCode, zbCache);
            }
            if (!StringUtils.hasText(regionCode = (String)zbCache.get(zbCode))) {
                FormDefine formDefine = this.rtViewCtrl.queryFormByCodeInScheme(formSchemeKey, formCode);
                if (formDefine == null) continue;
                List regions = this.rtViewCtrl.getAllRegionsInForm(formDefine.getKey());
                for (DataRegionDefine region : regions) {
                    List fieldKeys = this.rtViewCtrl.getFieldKeysInRegion(region.getKey());
                    for (String fieldKey : fieldKeys) {
                        FieldDefine fieldDefine = this.rtViewCtrl.queryFieldDefine(fieldKey);
                        if (fieldDefine == null) continue;
                        zbCache.put(fieldDefine.getCode(), region.getCode());
                    }
                }
            }
            mapping.setRegionCode(StringUtils.hasText((String)zbCache.get(zbCode)) ? (String)zbCache.get(zbCode) : "");
        }
        String SQL_UPDATE = String.format("UPDATE %s SET %s = ? WHERE %s = ?", TABLENAME, REGION, KEY);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (ZBMapping mapping : list) {
            Object[] param = new Object[]{mapping.getRegionCode(), mapping.getKey()};
            args.add(param);
        }
        this.jdbcTemplate.batchUpdate(SQL_UPDATE, args);
        this.logger.info("\u6a21\u5757[\u5973\u5a32\u6620\u5c04\u65b9\u6848NR\u6269\u5c55]\u6307\u6807\u6620\u5c04\u4fee\u590dregionCode\u7ed3\u675f\uff1a" + sdf.format(new Date()));
    }

    static {
        StringBuilder builder = new StringBuilder();
        ZB_MAPPING = builder.append(KEY).append(",").append(MAPPINGSCHEME).append(",").append(FORM).append(",").append(TABLE).append(",").append(REGION).append(",").append(ZB).append(",").append(MAPPING).toString();
        ENTITY_READER_ZB_MAPPING = rs -> {
            ZBMapping zb = new ZBMapping();
            int index = 1;
            try {
                zb.setKey(rs.getString(index++));
                zb.setMsKey(rs.getString(index++));
                zb.setForm(rs.getString(index++));
                zb.setTable(rs.getString(index++));
                zb.setRegionCode(rs.getString(index++));
                zb.setZbCode(rs.getString(index++));
                zb.setMapping(rs.getString(index++));
            }
            catch (SQLException e) {
                throw new RuntimeException("read NVWA_MAPPING_NR_ZB error.", e);
            }
            return zb;
        };
    }
}

