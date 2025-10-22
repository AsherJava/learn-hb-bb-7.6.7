/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.OrderGenerator
 *  javax.validation.constraints.NotNull
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package nr.single.map.configurations.dao.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.OrderGenerator;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.SingleConfigInfo;
import nr.single.map.configurations.bean.SingleMappingConfig;
import nr.single.map.configurations.dao.ConfigDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class ConfigDaoImpl
implements ConfigDao {
    private static final Logger logger = LoggerFactory.getLogger(ConfigDaoImpl.class);
    private static final String SQL_QUERY = "select * from %s ";
    private static final String SQL_SIMPLE = "select s_id,s_task,s_scheme,s_taskflag,s_fileflag,s_configname,s_order from %s ";
    private static final String TABLE_NAME = "sys_singlemapping";
    private static final String S_ID = "s_id";
    private static final String S_TASK = "s_task";
    private static final String S_SCHEME = "s_scheme";
    private static final String S_CONFIG = "s_config";
    private static final String S_TASKFLAG = "s_taskflag";
    private static final String S_FILEFLAG = "s_fileflag";
    private static final String S_CONFIGNAME = "s_configname";
    private static final String S_ORDER = "s_order";
    @Autowired
    private JdbcTemplate template;
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void insert(ISingleMappingConfig config) {
        String sql = String.format("insert into %s (%s,%s,%s,%s,%s,%s,%s,%S) values(?,?,?,?,?,?,?,?)", TABLE_NAME, S_ID, S_TASK, S_SCHEME, S_CONFIG, S_TASKFLAG, S_FILEFLAG, S_CONFIGNAME, S_ORDER);
        try {
            this.template.update(sql, new Object[]{config.getMappingConfigKey().toString(), config.getTaskKey().toString(), config.getSchemeKey().toString(), this.mapper.writeValueAsString((Object)config), config.getTaskInfo().getSingleTaskFlag(), config.getTaskInfo().getSingleFileFlag(), config.getConfigName(), OrderGenerator.newOrder()});
        }
        catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public ISingleMappingConfig query(String key) {
        String sql = String.format("select * from %s where %s=?", TABLE_NAME, S_ID);
        Object[] args = new Object[]{key};
        return (ISingleMappingConfig)this.template.query(sql, args, rs -> {
            if (rs.next()) {
                return this.buildSingleConfig(rs);
            }
            return null;
        });
    }

    @Override
    public SingleConfigInfo queryInfo(String key) {
        String sql = String.format("select s_id,s_task,s_scheme,s_taskflag,s_fileflag,s_configname,s_order from %s where %s=?", TABLE_NAME, S_ID);
        Object[] args = new Object[]{key};
        return (SingleConfigInfo)this.template.query(sql, args, rs -> {
            if (rs.next()) {
                return this.buildConfigInfo(rs);
            }
            return null;
        });
    }

    @Override
    public void update(ISingleMappingConfig config) {
        String sql = String.format("update %s set %s=?,%s=?,%s=?,%s=?,%s=?,%s=? where %s=?", TABLE_NAME, S_TASK, S_SCHEME, S_CONFIG, S_TASKFLAG, S_FILEFLAG, S_CONFIGNAME, S_ID);
        this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            this.template.update(sql, new Object[]{config.getTaskKey(), config.getSchemeKey(), this.mapper.writeValueAsString((Object)config), config.getTaskInfo().getSingleTaskFlag(), config.getTaskInfo().getSingleFileFlag(), config.getConfigName(), config.getMappingConfigKey()});
        }
        catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void delete(String configKey) {
        String sql = String.format("delete from %s where %s=?", TABLE_NAME, S_ID);
        this.template.update(sql, new Object[]{configKey});
    }

    @Override
    public List<SingleConfigInfo> queryConfigByTask(String task) {
        String sql = String.format("select s_id,s_task,s_scheme,s_taskflag,s_fileflag,s_configname,s_order from %s where %s=?", TABLE_NAME, S_TASK);
        Object[] args = new Object[]{task};
        return this.getSingleConfigInfos(sql, args);
    }

    @Override
    public List<SingleConfigInfo> queryConfigByScheme(String scheme) {
        String sql = String.format("select s_id,s_task,s_scheme,s_taskflag,s_fileflag,s_configname,s_order from %s where %s=?", TABLE_NAME, S_SCHEME);
        Object[] args = new Object[]{scheme};
        return this.getSingleConfigInfos(sql, args);
    }

    @Override
    public List<ISingleMappingConfig> getConfigByScheme(@NotNull String scheme) {
        String sql = String.format("select * from %s where %s=?", TABLE_NAME, S_SCHEME);
        Object[] args = new Object[]{scheme};
        return (List)this.template.query(sql, args, rs -> {
            ArrayList<ISingleMappingConfig> infos = new ArrayList<ISingleMappingConfig>();
            while (rs.next()) {
                infos.add(this.buildSingleConfig(rs));
            }
            return infos;
        });
    }

    @Override
    public SingleConfigInfo queryConfigByKey(String key) {
        String sql = String.format("select s_id,s_task,s_scheme,s_taskflag,s_fileflag,s_configname,s_order from %s where %s=?", TABLE_NAME, S_ID);
        Object[] args = new Object[]{key};
        List<SingleConfigInfo> list = this.getSingleConfigInfos(sql, args);
        SingleConfigInfo result = null;
        if (null != list && list.size() > 0) {
            result = list.get(0);
        }
        return result;
    }

    @Override
    public List<SingleConfigInfo> queryAllConfigInfo() {
        String sql = String.format(SQL_SIMPLE, TABLE_NAME);
        return this.getSingleConfigInfos(sql, null);
    }

    @Override
    public List<SingleConfigInfo> queryConfigBySingleTask(String taskFlag) {
        String sql = String.format("select s_id,s_task,s_scheme,s_taskflag,s_fileflag,s_configname,s_order from %s where %s=?", TABLE_NAME, S_TASKFLAG);
        Object[] args = new Object[]{taskFlag};
        return this.getSingleConfigInfos(sql, args);
    }

    @Override
    public void updateInfo(SingleConfigInfo info) {
        String sql = String.format("update %s set %s=?,%s=?,%s=?,%s=?,%s=?,%s=? where %s=?", TABLE_NAME, S_TASK, S_SCHEME, S_TASKFLAG, S_FILEFLAG, S_CONFIGNAME, S_ORDER, S_ID);
        this.template.update(sql, new Object[]{info.getTaskKey(), info.getSchemeKey(), info.getTaskFlag(), info.getFileFlag(), info.getConfigName(), info.getOrder(), info.getConfigKey()});
    }

    private List<SingleConfigInfo> getSingleConfigInfos(String sql, Object[] args) {
        return (List)this.template.query(sql, args, rs -> {
            ArrayList<SingleConfigInfo> infos = new ArrayList<SingleConfigInfo>();
            while (rs.next()) {
                infos.add(this.buildConfigInfo(rs));
            }
            return infos;
        });
    }

    ISingleMappingConfig buildSingleConfig(ResultSet rs) throws SQLException {
        SingleMappingConfig singleMappingConfig = new SingleMappingConfig();
        try {
            singleMappingConfig = (SingleMappingConfig)this.mapper.readValue(rs.getString("S_CONFIG"), SingleMappingConfig.class);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return singleMappingConfig;
    }

    SingleConfigInfo buildConfigInfo(ResultSet rs) throws SQLException {
        int index = 1;
        SingleConfigInfo info = new SingleConfigInfo();
        info.setConfigKey(rs.getString(index));
        info.setTaskKey(rs.getString(++index));
        info.setSchemeKey(rs.getString(++index));
        info.setTaskFlag(rs.getString(++index));
        info.setFileFlag(rs.getString(++index));
        info.setConfigName(rs.getString(++index));
        info.setOrder(rs.getString(++index));
        return info;
    }
}

