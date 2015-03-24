package configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"controllers", "services", "com.picsauditing.dao", "com.picsauditing.secure", "com.picsauditing.model", "com.picsauditing.loader"})
public class AppConfig {

}