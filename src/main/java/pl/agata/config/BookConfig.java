package pl.agata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.agata.service.BookService;

import java.sql.SQLException;

@Configuration
public class BookConfig {

    private DataBase DBservice;

    public BookConfig() {
        this.DBservice = new DataBase();
    }

    public DataBase getDataService() {
        return DBservice;
    }

    @Bean
    public BookService getBookService() throws SQLException {
        BookService bookService = new BookService(DBservice.getDBService());
        return bookService;
    }

}
