/**
 * by @W!L 2019
 */
package org.whispersystems.textsecuregcm.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class EmailConfiguration {

	@NotNull
	@JsonProperty
	private boolean enable;

	@JsonProperty
    private String smtpUser;

    @JsonProperty
    private String smtpPass;

    @JsonProperty
    private String smtpHost;

    @JsonProperty
    private String smtpPort;

    @JsonProperty
    private String email;

    @JsonProperty
    private boolean allDomainsAllowed;

    @JsonProperty
    private List<String> allowedDomains;

    public boolean isEnable() {
    	return enable;
    }

    public String getSmtpUser() {
    	return smtpUser;
    }

    public String getSmtpPass() {
    	return smtpPass;
    }

    public String getSmtpHost() {
    	return smtpHost;
    }

    public String getSmtpPort() {
    	return smtpPort;
    }

    public String getEmail() {
    	return email;
    }

    public boolean allDomainsAllowed() {
        return allDomainsAllowed;
    }

    public List<String> getAllowedDomains() {
        return allowedDomains;
    }
}