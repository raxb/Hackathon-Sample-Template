package fish.payara.jumpstartjee;

import lombok.Getter;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
@Getter
public class ConfigPropertyProvider {

	@Inject
	@ConfigProperty(name = "jakarta.version")
	private String jakartaVersion;

	@Inject
	@ConfigProperty(name = "application.server")
	private String applicationServer;
}
