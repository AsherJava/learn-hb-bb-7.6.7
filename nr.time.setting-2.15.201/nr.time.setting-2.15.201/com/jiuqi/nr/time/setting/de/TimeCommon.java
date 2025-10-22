/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.time.setting.de;

import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.time.setting.bean.TimeSettingInfo;
import com.jiuqi.nr.time.setting.util.TdEntityHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TimeCommon {
    @Autowired
    private TdEntityHelper entityHelper;
    @Autowired
    private SystemUserService systemUserService;

    public Map<String, String> userMap(List<TimeSettingInfo> queryTableData) {
        HashMap<String, String> userMap = new HashMap<String, String>();
        for (TimeSettingInfo settimeInfo : queryTableData) {
            userMap.put(settimeInfo.getOperator(), settimeInfo.getOperatorOfUnitId());
        }
        return userMap;
    }

    public Map<Integer, String> getUnitOfParent(String formSchemeKey, String period, Map<String, String> userMap) {
        HashMap<Integer, String> unitOfParent = new HashMap<Integer, String>();
        for (Map.Entry<String, String> map : userMap.entrySet()) {
            String user = map.getKey();
            String unitid = map.getValue();
            User systemUser = this.getSystemUser();
            if (systemUser != null && systemUser.getId().equals(user)) {
                unitOfParent.put(0, unitid);
                continue;
            }
            if (unitid != null) {
                List<IEntityRow> allRows = this.entityHelper.getEntityRow(formSchemeKey, period);
                if (allRows.size() <= 0) continue;
                for (IEntityRow iEntityRow : allRows) {
                    String id = iEntityRow.getEntityKeyData();
                    String title = iEntityRow.getTitle();
                    if (!unitid.contains(id)) continue;
                    String[] parentsEntityKeyDataPath = iEntityRow.getParentsEntityKeyDataPath();
                    unitOfParent.put(parentsEntityKeyDataPath.length, id);
                }
                continue;
            }
            unitOfParent.put(9999, user);
        }
        return unitOfParent;
    }

    private User getSystemUser() {
        List users = this.systemUserService.getUsers();
        if (users != null && users.size() > 0) {
            return (User)users.get(0);
        }
        return null;
    }
}

