/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.spire.license.LicenseProvider
 */
package com.jiuqi.nvwa.cellbook.excel.print.spire;

import com.spire.license.LicenseProvider;

class SpireInitializer {
    private static boolean SPIRE_INITIALIZED = false;
    private static final String SPIRE_KEY = "tgEA5iqiuE0FA/PVtLP8CQ+2sNcFOdbKtg+YMRGB7CqKYBZ+uTANhsGlFYQWt1vGXszjYVPy/+x5pvwN/HA1EB4QnPS/AYHs0jxVny8LWFlE5Uex1bPetcCPR/aE5v9KzdKJnwmru5yDQpv5IWlymr6htMmX2B6jzmmrDaZ/mMsCenovJKg1mkP7++/yf3X0KJ4+LG0NDCGmU22qDAlJdAjObIYTstIH+XHE6caY1rcWmhvEVsOyemUF9QzwhypB5KMiTf4R+baKSBdnms0bZojva3Ga9xiFg8YIgLBt/c7Orn1SGSm+5UmNlA82lvhYM5931qsfIC4WmmlHAt0hLxzMhxemtF+foxDyQ8JK3QjrgWcSfKxtyrm162hsgM4yRKsAYlgc09e99bYBmdXNvNAKll7+Sdh7IUlog87NG+z5+jOIo2pUhEy8cjv9SNm0eUXLCpqgL1D6on7j7SNP3yKbmH5hzpj9yfW1FWBUGMBy937JCVc+bgXxzZDE20IBULKPAV+106dSpvgr+Y7N0A20uS6vCXtFxpp+nOypw5Rnn1yF0TYf5X9WMXYqa8LPxzkohRSoWWGZs8y7PJbRgHEP49RQ1491FAzdwuzc+I6sqmNAev6VosZofhCJ2Xa9+o0n2f+tmy7yMPKBk4gCKRNOB/riZwDiPDrWrQcBagx1ox/UAbqO3RdPLfu4rNtFoUn78xWiy7pSy4MSQgkcNnJ+bgPQupyxJr2QNYgujuqCO19N1WaDFXTtvz95fnbEMPLk8mO8Ebq1GzqiuCd5R7gkMfCcEJhZshnflI8EC8pMevq4zpo4Dom9fyYpirZBcnetx+fBz16KrgwB5Enr8kZNnRo0CVYgr84UXpPxU+LTDkvHaI9Bj1Ya+cKucyRmpFZCoWrMcMYzFyu7QtfROMvDictHYFHnq523zJONhsXTA0JM7FtdIDM5JNdfgq8KQ7FzdbiSMmejSoQE0LxuX8mmVOqpg2m7wAynSOUllBvJ6wElKmaV+I6KfA7zFQebCaK4T+kme6NfhjGCAeXn9n43rSnIkzDrLhbjjuaYkDAi46u/zU2G4RIorIjj/pDOkXKuNSIVCs18Ikg2TBLMdzzclKVH7Vm1MDEH0YahATO6mPBS9Wb16So6HGFCTtEz9R1YekofGojOLbZMJM0u8HCiP5OIqg9OsXKMi6pu6PwvQPVHU/qlnQ+Bwa6dfVP/YuiSqIu6v9pFNIKOy1Vc/gyKldXV6C40HxGozjYwSE7OAzkyGLvSGrIV6oPPPSdLWIQYn/GV35tVMIIDDbqltk9FgO/Wqd+uF3fXxnEyHHccKcQLF7byKP4Li0OvegpD3++XFQqwzVk4tVYsnOME1Yr/9M8CXmWVkQMSe/mCL47hkggiq8IJmB234eufiCtZlLSH37FX3/1sMFTw9m0ElWRt0iG3m8BDKCzEzDgj05mQUxlvEfQqC+zLQonbAxk8hy/HbUck0zDWS/a51lV5PbjYHRgC6EG+DtPeUBW6WNn3RIAIn7wZ1jIe/rtba2rPKfN3t/6eRN8lYA+mbt57PcxrMZQv/npK";

    SpireInitializer() {
    }

    static void initialize() {
        if (SPIRE_INITIALIZED) {
            return;
        }
        LicenseProvider.setLicenseKey((String)SPIRE_KEY);
        SPIRE_INITIALIZED = true;
    }
}

