/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.util;

import com.jiuqi.bi.util.StringUtils;
import java.io.Serializable;

public class Version
implements Comparable<Version>,
Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private int major;
    private int minor;
    private int revision;
    private String buildVersion;

    public Version(int major) {
        this(major, 0);
    }

    public Version(int major, int minor) {
        this(major, minor, 0);
    }

    public Version(int major, int minor, int revision) {
        this(major, minor, revision, null);
    }

    public Version(int major, int minor, int revision, String buildVersion) {
        if (major < 0) {
            throw new IllegalArgumentException("major number must be nonnegative");
        }
        if (minor < 0) {
            throw new IllegalArgumentException("minor number must be nonnegative");
        }
        if (revision < 0) {
            throw new IllegalArgumentException("revision number must be nonnegative");
        }
        this.major = major;
        this.minor = minor;
        this.revision = revision;
        this.buildVersion = buildVersion;
    }

    public Version(String version) {
        if (version == null) {
            throw new IllegalArgumentException("version string is null");
        }
        this.parseVersion(version);
    }

    public String toString() {
        return this.major + "." + this.minor + "." + this.revision + (this.buildVersion == null ? "" : "." + this.buildVersion);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Version other = (Version)obj;
        if (this.buildVersion == null ? other.buildVersion != null : !this.buildVersion.equals(other.buildVersion)) {
            return false;
        }
        if (this.major != other.major) {
            return false;
        }
        if (this.minor != other.minor) {
            return false;
        }
        return this.revision == other.revision;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.buildVersion == null ? 0 : this.buildVersion.hashCode());
        result = 31 * result + this.major;
        result = 31 * result + this.minor;
        result = 31 * result + this.revision;
        return result;
    }

    @Override
    public int compareTo(Version o) {
        if (this.major == o.major) {
            if (this.minor == o.minor) {
                if (this.revision == o.revision) {
                    return 0;
                }
                return this.revision - o.revision;
            }
            return this.minor - o.minor;
        }
        return this.major - o.major;
    }

    public static Version parseVersionString(String version) {
        return new Version(version);
    }

    private void parseVersion(String version) {
        String[] splited;
        int[] ps;
        int p = -1;
        for (int s : ps = new int[]{version.indexOf(95), version.indexOf(43), version.indexOf(45)}) {
            if (p == -1) {
                p = s;
                continue;
            }
            if (s == -1) continue;
            p = Math.min(p, s);
        }
        if (p >= 0) {
            this.buildVersion = version.substring(p + 1);
            version = version.substring(0, p);
        }
        if ((splited = version.split("\\.", 4)).length == 0) {
            throw new IllegalArgumentException("Illegal version format");
        }
        String majorString = splited[0];
        try {
            this.major = Integer.parseInt(majorString);
            if (this.major < 0) {
                throw new IllegalArgumentException("major number must be nonnegative");
            }
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("major must be number");
        }
        if (splited.length > 1) {
            String minorString = splited[1];
            try {
                this.minor = Integer.parseInt(minorString);
                if (this.minor < 0) {
                    throw new IllegalArgumentException("minor number must be nonnegative");
                }
            }
            catch (NumberFormatException e) {
                throw new IllegalArgumentException("minor must be number");
            }
        }
        if (splited.length > 2) {
            String revisionString = splited[2];
            try {
                this.revision = Integer.parseInt(revisionString);
                if (this.revision < 0) {
                    throw new IllegalArgumentException("revision number must be nonnegative");
                }
            }
            catch (NumberFormatException e) {
                throw new IllegalArgumentException("revision must be number");
            }
        }
        if (splited.length > 3 && !StringUtils.isEmpty((String)splited[3])) {
            this.buildVersion = splited[3];
        }
    }

    public static void main(String[] args) {
        String version = System.getProperty("java.version");
        System.out.println(version);
        Version.print("1.8.0_66");
        Version.print("1.8.0-66");
        Version.print("1.8.0+b-66");
        Version.print("1");
        Version.print("");
        Version.print("2.3");
        Version.print("3.4.50");
        Version.print("3.4.5.v20190215");
        Version.print("3.-4.5");
        Version.print("3.1a.3");
        Version.print("214.324");
        Version.print("192.168.2.-1.3.54.v23");
        Version.compare("1", "2");
        Version.compare("3.12", "3.2");
        Version.compare("4.1", "3.9");
        Version.compare("2.8.2", "3.2");
        Version.compare("4.1.0.v20190215", "4.1");
    }

    private static void print(String version) {
        System.out.print(version + " : ");
        try {
            System.out.println(new Version(version));
        }
        catch (Exception e) {
            System.out.println("error " + e.getMessage());
        }
    }

    private static void compare(String version1, String version2) {
        Version v2;
        Version v1;
        System.out.print(version1 + " compare to " + version2 + " : ");
        try {
            v1 = new Version(version1);
        }
        catch (Exception e) {
            System.out.println("error version1 parse failed");
            return;
        }
        try {
            v2 = new Version(version2);
        }
        catch (Exception e) {
            System.out.println("error version2 parse failed");
            return;
        }
        int result = v1.compareTo(v2);
        if (result > 0) {
            System.out.println(version1 + " is greater than " + version2);
        } else if (result < 0) {
            System.out.println(version1 + " is less than " + version2);
        } else {
            System.out.println(version1 + " is equal to " + version2);
        }
    }

    public int getMajor() {
        return this.major;
    }

    public int getMinor() {
        return this.minor;
    }

    public int getRevision() {
        return this.revision;
    }

    public String getBuildVersion() {
        return this.buildVersion;
    }
}

