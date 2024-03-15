package myy803.diplomas_project.model;

public enum Role {
	STUDENT("STUDENT"),
    PROFESSOR("PROFESSOR");

    private final String value;

    private Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
