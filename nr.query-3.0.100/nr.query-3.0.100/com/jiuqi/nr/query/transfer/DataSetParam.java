/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.definition.common.StringUtils
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.query.transfer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.nr.query.dataset.dao.IDataSetDefineDao;
import com.jiuqi.nr.query.dataset.dao.IDataSetGroupDao;
import com.jiuqi.nr.query.dataset.defines.DataSetDefine;
import com.jiuqi.nr.query.dataset.defines.DataSetGroupDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service(value="dataSetParam")
public class DataSetParam {
    private static final Logger log = LoggerFactory.getLogger(DataSetParam.class);
    private static final String ERROR_CREATE_FILE = "\u751f\u6210\u6587\u4ef6\u9519\u8bef";
    @Autowired
    private IDataSetDefineDao dataSetDefineDao;
    @Autowired
    private IDataSetGroupDao dataSetGroupDao;
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    ObjectMapper mapper = new ObjectMapper();

    private String[] getGroupPath(String groupId) {
        ArrayList<String> groups = new ArrayList<String>();
        groups.add(groupId);
        if ("0000-0000-0000-00000".equals(groupId)) {
            return groups.toArray(new String[1]);
        }
        DataSetGroupDefine parentGroup = this.dataSetGroupDao.QueryDataSetGroupById(groupId);
        if (parentGroup == null) {
            return groups.toArray(new String[1]);
        }
        List<DataSetGroupDefine> allGroups = this.dataSetGroupDao.QueryDataSetGroup();
        HashMap<String, String> pathMap = new HashMap<String, String>();
        allGroups.forEach(e -> pathMap.put(e.getId(), e.getParent()));
        this.buildGroupPath(groups, pathMap);
        String[] path = new String[groups.size()];
        int index = 0;
        Iterator iterator = groups.iterator();
        while (iterator.hasNext()) {
            String group;
            path[index] = group = (String)iterator.next();
            ++index;
        }
        return path;
    }

    private void buildGroupPath(List<String> groups, Map<String, String> pathMap) {
        String parent;
        String group = groups.get(groups.size() - 1);
        if (!"0000-0000-0000-00000".equals(group) && !StringUtils.isEmpty((String)(parent = pathMap.get(group)))) {
            groups.add(parent);
            this.buildGroupPath(groups, pathMap);
        }
    }

    private List<DataSetDefine> getDataSetDefineList(List<Map<String, Object>> results) {
        return results.stream().map(this::createDataSetDefine).collect(Collectors.toList());
    }

    private DataSetDefine createDataSetDefine(Map<String, Object> map) {
        DataSetDefine define = new DataSetDefine();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            define.setProperty(entry.getKey(), entry.getValue());
        }
        return define;
    }

    private void getGroup(String groupId, Map<String, DataSetGroupDefine> groupMap) {
        if ("0000-0000-0000-00000".equals(groupId)) {
            return;
        }
        DataSetGroupDefine group = this.dataSetGroupDao.QueryDataSetGroupById(groupId);
        groupMap.put(groupId, group);
        this.getGroup(group.getParent(), groupMap);
    }
}

