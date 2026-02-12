package id.web.saka.fountation.organization.department;

public enum DepartmentStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    DISABLED("DISABLED");

    private final String value;

    DepartmentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static DepartmentStatus fromValue(String value) {
        for (DepartmentStatus status : values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + value);
    }
}

