package com.journeyplanner.journeyplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class JourneyPlannerApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(JourneyPlannerApplication.class);

        // ✅ Always run without web server
        app.setWebApplicationType(WebApplicationType.NONE);

        // ✅ Always activate CLI profile
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "cli");

        System.out.println("🚀 Starting Journey Planner in CLI mode...");

        app.run(args);
    }
}
