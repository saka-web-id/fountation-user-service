package id.web.saka.fountation.organization.company;

public enum CompanyStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    DISABLED("DISABLED");

    private final String value;

    CompanyStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CompanyStatus fromValue(String value) {
        for (CompanyStatus status : values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + value);
    }
}
