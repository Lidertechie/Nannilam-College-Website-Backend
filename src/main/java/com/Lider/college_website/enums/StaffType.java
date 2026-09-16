package com.Lider.college_website.enums;

public enum StaffType {

    TEACHING("Teaching Staff"),
    NON_TEACHING("Non Teaching Staff");

    private final String displayName;

    StaffType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}