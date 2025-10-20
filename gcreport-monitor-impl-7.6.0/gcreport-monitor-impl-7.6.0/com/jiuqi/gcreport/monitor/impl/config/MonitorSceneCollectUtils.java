/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.monitor.api.common.MonitorSceneEnum
 *  com.jiuqi.gcreport.monitor.api.common.MonitorSceneGroupEnum
 *  com.jiuqi.gcreport.monitor.api.inf.MonitorScene
 */
package com.jiuqi.gcreport.monitor.impl.config;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.monitor.api.common.MonitorSceneEnum;
import com.jiuqi.gcreport.monitor.api.common.MonitorSceneGroupEnum;
import com.jiuqi.gcreport.monitor.api.inf.MonitorScene;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MonitorSceneCollectUtils {
    private static List<MonitorScene> allMonitorScenes = new ArrayList<MonitorScene>();

    public static List<MonitorScene> getAllMonitorScene() {
        return MonitorSceneCollectUtils.getAllMonitorScenes();
    }

    public static List<MonitorScene> getAllMonitorSceneEnum() {
        return MonitorSceneCollectUtils.getAllMonitorScenes();
    }

    public static List<MonitorSceneGroupEnum> getSceneGroups() {
        ArrayList<MonitorSceneGroupEnum> monitorSceneGroups = new ArrayList<MonitorSceneGroupEnum>();
        MonitorSceneCollectUtils.getAllMonitorScenes().stream().map(MonitorScene::getMonitorScene).filter(monitorSceneEnum -> monitorSceneEnum != null && !monitorSceneGroups.contains(monitorSceneEnum.getSceneGroup())).forEach(monitorSceneEnum -> monitorSceneGroups.add(monitorSceneEnum.getSceneGroup()));
        return monitorSceneGroups;
    }

    public static MonitorSceneGroupEnum getSceneGroup(String groupCode) {
        List<MonitorSceneGroupEnum> sceneGroups = MonitorSceneCollectUtils.getSceneGroups();
        if (sceneGroups != null && sceneGroups.size() > 0) {
            for (MonitorSceneGroupEnum tempSceneGroup : sceneGroups) {
                if (!tempSceneGroup.getCode().equals(groupCode)) continue;
                return tempSceneGroup;
            }
        }
        return null;
    }

    public static List<MonitorSceneEnum> getScenes(MonitorSceneGroupEnum monitorSceneGroup) {
        ArrayList<MonitorSceneEnum> monitorScenes = new ArrayList<MonitorSceneEnum>();
        for (MonitorScene tempMonitorScene : MonitorSceneCollectUtils.getAllMonitorScenes()) {
            MonitorSceneEnum monitorSceneEnum = tempMonitorScene.getMonitorScene();
            if (monitorSceneEnum == null || !monitorSceneEnum.getSceneGroup().equals((Object)monitorSceneGroup)) continue;
            monitorScenes.add(monitorSceneEnum);
        }
        return monitorScenes;
    }

    public static List<MonitorScene> getMonitorScenes(MonitorSceneGroupEnum monitorSceneGroup) {
        ArrayList<MonitorScene> monitorScenes = new ArrayList<MonitorScene>();
        for (MonitorScene tempMonitorScene : MonitorSceneCollectUtils.getAllMonitorScenes()) {
            MonitorSceneEnum monitorSceneEnum = tempMonitorScene.getMonitorScene();
            if (monitorSceneEnum == null || !monitorSceneEnum.getSceneGroup().equals((Object)monitorSceneGroup)) continue;
            monitorScenes.add(tempMonitorScene);
        }
        return monitorScenes;
    }

    public static MonitorScene getMonitorScene(String sceneCode) {
        for (MonitorScene tempMonitorScene : MonitorSceneCollectUtils.getAllMonitorScenes()) {
            MonitorSceneEnum monitorSceneEnum = tempMonitorScene.getMonitorScene();
            if (monitorSceneEnum == null || !monitorSceneEnum.getCode().equals(sceneCode)) continue;
            return tempMonitorScene;
        }
        return null;
    }

    public static MonitorScene getMonitorScene(MonitorSceneEnum monitorSceneEnum) {
        for (MonitorScene tempMonitorScene : MonitorSceneCollectUtils.getAllMonitorScenes()) {
            if (!tempMonitorScene.getMonitorScene().equals((Object)monitorSceneEnum)) continue;
            return tempMonitorScene;
        }
        return null;
    }

    public static List<MonitorScene> getAllMonitorScenes() {
        if (CollectionUtils.isEmpty(allMonitorScenes)) {
            Collection allScenes = SpringContextUtils.getBeans(MonitorScene.class);
            allMonitorScenes.addAll(allScenes);
        }
        return allMonitorScenes;
    }
}

