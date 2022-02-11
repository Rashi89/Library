package pl.agata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.agata.service.BasketService;

import java.sql.SQLException;

@Configuration
public class BasketConfig {
    private DataBase DBservice;

    public BasketConfig() {this.DBservice = new DataBase();
    }

    public DataBase getDataService() {
        return DBservice;
    }
    @Bean
    public BasketService getBasketService() throws SQLException {
        BasketService basketService = new BasketService(DBservice.getDBService());
        return basketService;
    }
}
