/**
 *
 * Safety Net Alerts Program Launcher based on Spring
 * @author: Pablo Miranda
 *
 */

package com.safety.net.alerts;

import com.safety.net.alerts.controller.EndPointsHandlerController;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class SafetyNetAlertsApplication {

	public static void main(String[] args) {

		Logger logger = LogManager.getLogger(EndPointsHandlerController.class);
		SpringApplication.run(SafetyNetAlertsApplication.class, args);

	}

}
