package code.with.vanilson.email;

import lombok.Getter;

@Getter
public enum EmailTemplateName {
    ACTIVATION_ACCOUNT("activation-account");

    private final String templateName;

    EmailTemplateName(String name) {
        this.templateName = name;
    }
}
