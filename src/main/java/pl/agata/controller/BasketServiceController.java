package pl.agata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import pl.agata.books.Book;
import pl.agata.config.BasketConfig;
import pl.agata.service.BasketService;
import pl.agata.service.BookService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BasketServiceController {

    private BasketConfig basketConfig;
    private List<Book> books;

    @Autowired
    public BasketServiceController(BasketConfig basketConfig) {
        books = new ArrayList<>();
        this.basketConfig = basketConfig;
    }
    public ResponseEntity<BasketService> getBasketService() throws SQLException {
        BasketService basketService =  basketConfig.getBasketService();
        return ResponseEntity.ok(basketService);
    }
}
