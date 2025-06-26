package com.dhentech.PersonalFinancialControlAPI.globalExceptions.enums;

public enum ProblemType {

    RESOURCE_NOT_FOUND_TITLE(
            "/ResourceNotFound",
            "Resource Not Found",
            "The resource you tried to access does not exist."
    ),

    METHOD_ARGUMENT_NOT_VALID(
            "/invalid-data",
            "Invalid Data",
            "One or more fields are invalid. Please check and try again."
    );

    private final String path;
    private final String title;
    private final String message;

    ProblemType(String path, String title, String message) {
        this.path = path;
        this.title = title;
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}
