package pl.agata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.agata.service.DBService;

import java.sql.SQLException;

@Configuration
public class DataBase {

    public DataBase() {
    }

    @Bean
    public DBService getDBService() throws SQLException {
        DBService dataService = new pl.agata.service.DBService("jdbc:mysql://localhost/library","root","");
        dataService.configure();
        return dataService;
    }
    public void close() throws SQLException {
        getDBService().closeStatement();
    }

}
