package pl.agata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.agata.service.UserService;

import java.sql.SQLException;

@Configuration
public class UserServiceConfig {

    private DataBase DBservice;

    public UserServiceConfig() {
        this.DBservice = new DataBase();
    }

    public DataBase getDataService() {
        return DBservice;
    }
    @Bean
    public UserService getUserService() throws SQLException {
        UserService userService = new UserService(DBservice.getDBService());
        return userService;
    }
}
