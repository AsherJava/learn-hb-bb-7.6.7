/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.authz2.Role
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 */
package nr.single.map.data.bean;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.authz2.Role;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.util.ArrayList;
import java.util.List;
import nr.single.map.data.util.SingleParamProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlowData {
    private static Logger log = LoggerFactory.getLogger(SingleParamProvider.class);
    private String flowType;
    private List<EntityViewData> entitys = new ArrayList<EntityViewData>();
    private boolean subTable;
    private boolean dataConfirm;
    private boolean submitExplain;
    private boolean returnVersion;
    private boolean allowTakeBack;
    private boolean allowModifyData;
    private boolean unitSubmitForCensorship;
    private String selectedRoleKey;
    private List<Integer> erroStatus;
    private List<Integer> promptStatus;
    private boolean UnitSubmitForForce = false;

    public List<Integer> getErroStatus() {
        return this.erroStatus;
    }

    public void setErroStatus(List<Integer> erroStatus) {
        this.erroStatus = erroStatus;
    }

    public List<Integer> getPromptStatus() {
        return this.promptStatus;
    }

    public void setPromptStatus(List<Integer> promptStatus) {
        this.promptStatus = promptStatus;
    }

    public String getFlowType() {
        return this.flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public List<EntityViewData> getEntitys() {
        return this.entitys;
    }

    public void setEntitys(List<EntityViewData> entitys) {
        this.entitys = entitys;
    }

    public boolean isSubTable() {
        return this.subTable;
    }

    public void setSubTable(boolean subTable) {
        this.subTable = subTable;
    }

    public boolean isDataConfirm() {
        return this.dataConfirm;
    }

    public void setDataConfirm(boolean dataConfirm) {
        this.dataConfirm = dataConfirm;
    }

    public boolean isSubmitExplain() {
        return this.submitExplain;
    }

    public void setSubmitExplain(boolean submitExplain) {
        this.submitExplain = submitExplain;
    }

    public boolean isReturnVersion() {
        return this.returnVersion;
    }

    public void setReturnVersion(boolean returnVersion) {
        this.returnVersion = returnVersion;
    }

    public boolean isAllowTakeBack() {
        return this.allowTakeBack;
    }

    public void setAllowTakeBack(boolean allowTakeBack) {
        this.allowTakeBack = allowTakeBack;
    }

    public boolean isAllowModifyData() {
        return this.allowModifyData;
    }

    public void setAllowModifyData(boolean allowModifyData) {
        this.allowModifyData = allowModifyData;
    }

    public boolean isUnitSubmitForCensorship() {
        return this.unitSubmitForCensorship;
    }

    public void setUnitSubmitForCensorship(boolean unitSubmitForCensorship) {
        this.unitSubmitForCensorship = unitSubmitForCensorship;
    }

    public String getSelectedRoleKey() {
        return this.selectedRoleKey;
    }

    public void setSelectedRoleKey(String selectedRoleKey) {
        this.selectedRoleKey = selectedRoleKey;
    }

    public boolean isUnitSubmitForForce() {
        return this.UnitSubmitForForce;
    }

    public void setUnitSubmitForForce(boolean unitSubmitForForce) {
        this.UnitSubmitForForce = unitSubmitForForce;
    }

    public void init(TaskFlowsDefine taskFlowsDefine) {
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        RoleService roleService = (RoleService)BeanUtil.getBean(RoleService.class);
        this.flowType = taskFlowsDefine.getFlowsType().name();
        String masterEntitiesKey = taskFlowsDefine.getDesignTableDefines();
        if (StringUtils.isNotEmpty((String)masterEntitiesKey)) {
            try {
                this.entitys = jtableParamService.getEntityList(masterEntitiesKey);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        this.subTable = taskFlowsDefine.isSubTable();
        this.dataConfirm = taskFlowsDefine.isDataConfirm();
        this.submitExplain = taskFlowsDefine.isSubmitExplain();
        this.dataConfirm = taskFlowsDefine.isDataConfirm();
        this.returnVersion = taskFlowsDefine.isReturnVersion();
        this.allowTakeBack = taskFlowsDefine.isAllowTakeBack();
        this.allowModifyData = taskFlowsDefine.isAllowModifyData();
        this.unitSubmitForCensorship = taskFlowsDefine.isUnitSubmitForCensorship();
        this.selectedRoleKey = taskFlowsDefine.getSelectedRoleKey();
        String selectedRoleForceKey = taskFlowsDefine.getSelectedRoleForceKey();
        String identityId = NpContextHolder.getContext().getIdentityId();
        List byIdentitys = roleService.getByIdentity(identityId);
        SystemIdentityService sysIdentityService = (SystemIdentityService)BeanUtil.getBean(SystemIdentityService.class);
        for (Role identity : byIdentitys) {
            if (!sysIdentityService.isSystemIdentity(identityId) && (!identity.getId().toString().equals(selectedRoleForceKey) || !taskFlowsDefine.isUnitSubmitForForce())) continue;
            this.UnitSubmitForForce = true;
        }
        String[] erroStatusArray = new String[]{};
        if (taskFlowsDefine.getErroStatus() != null && taskFlowsDefine.getErroStatus().contains(";")) {
            erroStatusArray = taskFlowsDefine.getErroStatus().split(";");
        }
        ArrayList<String> filterErrorStatus = new ArrayList<String>();
        for (String erro : erroStatusArray) {
            filterErrorStatus.add(erro);
        }
        ArrayList<String> array = new ArrayList<String>();
        AuditTypeDefineService auditTypeDefineService = (AuditTypeDefineService)BeanUtil.getBean(AuditTypeDefineService.class);
        try {
            List auditTypes = auditTypeDefineService.queryAllAuditType();
            if (auditTypes == null || auditTypes.size() == 0) {
                throw new Exception();
            }
            for (Object auditType : auditTypes) {
                array.add(auditType.getCode().toString());
            }
        }
        catch (Exception e) {
            array.add("1");
            array.add("2");
            array.add("4");
        }
        ArrayList filterErroStatusArray = new ArrayList();
        for (int i = 0; i < array.size(); ++i) {
            if (filterErrorStatus.contains(array.get(i))) continue;
            filterErroStatusArray.add(array.get(i));
        }
        ArrayList<Integer> erroStatus = new ArrayList<Integer>();
        for (String str : filterErroStatusArray) {
            erroStatus.add(Integer.parseInt(str));
        }
        if (erroStatus.size() == 0) {
            erroStatus.add(-1);
        }
        this.erroStatus = erroStatus;
        String[] promtStatusArray = new String[]{};
        if (taskFlowsDefine.getPromptStatus() != null && taskFlowsDefine.getPromptStatus().contains(";")) {
            promtStatusArray = taskFlowsDefine.getPromptStatus().split(";");
        }
        ArrayList<Integer> promptStatus = new ArrayList<Integer>();
        for (String str : promtStatusArray) {
            try {
                promptStatus.add(Integer.parseInt(str));
            }
            catch (NumberFormatException e) {
                log.error(e.getMessage(), e);
            }
        }
        this.promptStatus = promptStatus;
    }
}

