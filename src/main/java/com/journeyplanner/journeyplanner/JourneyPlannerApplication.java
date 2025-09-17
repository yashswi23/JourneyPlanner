package com.journeyplanner.journeyplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class JourneyPlannerApplication {
    public static void main(String[] args) {
        boolean cliMode = false;

        // check if CLI mode requested
        for (String arg : args) {
            if (arg.equalsIgnoreCase("--mode=cli")) {
                cliMode = true;
                break;
            }
        }

        SpringApplication app = new SpringApplication(JourneyPlannerApplication.class);

        if (cliMode) {
            // ✅ run without web server
            app.setWebApplicationType(WebApplicationType.NONE);
            // ✅ activate cli profile so JourneyCLI runs
            System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "cli");
            System.out.println("🚀 Starting in CLI mode...");
        } else {
            System.out.println("🌐 Starting in REST API mode...");
        }

        app.run(args);
    }
}
