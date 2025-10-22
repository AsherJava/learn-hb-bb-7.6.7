/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package nr.single.para.compare.internal.system;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import nr.single.para.compare.definition.common.TaskFindModeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SingleParaOptionsService {
    @Autowired
    private INvwaSystemOptionService systemOptionService;

    public boolean isEnumItemOnlyCode() {
        String value = this.systemOptionService.get("single_para_option_id", "singlepara_option_enumitemmode");
        if (StringUtils.isEmpty((String)value)) {
            return false;
        }
        return "1".equalsIgnoreCase(value);
    }

    public boolean isFieldMatchOnlyCode() {
        String value = this.systemOptionService.get("single_para_option_id", "singlepara_option_parafindmode");
        if (StringUtils.isEmpty((String)value)) {
            return false;
        }
        return "1".equalsIgnoreCase(value);
    }

    public boolean isFieldMatchOnlyGlobalCode() {
        String value = this.systemOptionService.get("single_para_option_id", "singlepara_option_parafindmode");
        if (StringUtils.isEmpty((String)value)) {
            return false;
        }
        return "3".equalsIgnoreCase(value);
    }

    public boolean isFieldMatchOnlyMapping() {
        String value = this.systemOptionService.get("single_para_option_id", "singlepara_option_parafindmode");
        if (StringUtils.isEmpty((String)value)) {
            return false;
        }
        return "2".equalsIgnoreCase(value);
    }

    public boolean isFieldMatchCodeFirstTitle() {
        String value = this.systemOptionService.get("single_para_option_id", "singlepara_option_parafindmode");
        if (StringUtils.isEmpty((String)value)) {
            return false;
        }
        return "4".equalsIgnoreCase(value);
    }

    public boolean isFieldMatchTitleFirstCode() {
        String value = this.systemOptionService.get("single_para_option_id", "singlepara_option_parafindmode");
        if (StringUtils.isEmpty((String)value)) {
            return false;
        }
        return "5".equalsIgnoreCase(value);
    }

    public boolean isTaskFindChange() {
        String value = this.systemOptionService.get("single_para_option_id", "singlepara_option_taskfindmode");
        if (StringUtils.isEmpty((String)value)) {
            return false;
        }
        return "1".equalsIgnoreCase(value);
    }

    public boolean isTaskFindYear() {
        String value = this.systemOptionService.get("single_para_option_id", "singlepara_option_taskfindmode");
        if (StringUtils.isEmpty((String)value)) {
            return false;
        }
        return "2".equalsIgnoreCase(value);
    }

    public boolean isTaskStandardMode() {
        String value = this.systemOptionService.get("single_para_option_id", "singlepara_option_taskfindmode");
        if (StringUtils.isEmpty((String)value)) {
            return true;
        }
        return "0".equalsIgnoreCase(value);
    }

    public TaskFindModeType getTaskFindMode() {
        String value = this.systemOptionService.get("single_para_option_id", "singlepara_option_taskfindmode");
        if (StringUtils.isEmpty((String)value)) {
            return TaskFindModeType.TASKFIND_DEFAULT;
        }
        if ("2".equalsIgnoreCase(value)) {
            return TaskFindModeType.TASKFIND_YEAR;
        }
        if ("1".equalsIgnoreCase(value)) {
            return TaskFindModeType.TASKFIND_CHANGE;
        }
        return TaskFindModeType.TASKFIND_DEFAULT;
    }
}

