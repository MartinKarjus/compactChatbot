package conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@Import(DbConfig.class)
@ComponentScan(basePackages = {"controller", "bot", "bot.chatfuelapi"})
@EnableScheduling
public class MvcConfig {



}