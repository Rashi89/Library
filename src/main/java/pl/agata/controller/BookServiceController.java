package pl.agata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import pl.agata.books.Book;
import pl.agata.config.BasketConfig;
import pl.agata.config.BookConfig;
import pl.agata.service.BasketService;
import pl.agata.service.BookService;
import pl.agata.user.User;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class BookServiceController {

    private BookConfig bookConfig;
    private BasketConfig basketConfig;
    private List<Book> books;
    private User user;
    private String title;
    private String author;
    private int basketBooksQuantity;

    @Autowired
    public BookServiceController(BookConfig bookConfig, BasketConfig basketConfig) {
        this.bookConfig = bookConfig;
        this.basketConfig = basketConfig;

    }

    //@GetMapping("/book")
    public ResponseEntity<BookService> getBookService() throws SQLException {
        BookService bookService =  bookConfig.getBookService();
        return ResponseEntity.ok(bookService);
    }

    @GetMapping("/index")
    public String showMainPage(Book book, User user, Model model) throws SQLException {
        this.books = new ArrayList<>();
        BookService bookService =  bookConfig.getBookService();
        books.clear();
        this.books = bookService.addBook();
        model.addAttribute("books",this.books);
        return "index";
    }

    @GetMapping("/loginUsersIndex")
    public String showLoginMainPage(Book book,@SessionAttribute("loginUser") User user, Model model) throws SQLException {
        books = new ArrayList<>();
        HashMap<String,Object> maps = new HashMap<>();
        BookService bookService =  bookConfig.getBookService();
        BasketService basketService = new BasketService(bookConfig.getDataService().getDBService());
        this.books = bookService.addBook();
        basketBooksQuantity = basketService.getBasketSize(user.getId());
        maps.put("user",user);
        maps.put("books",this.books);
        maps.put("basketQuantity", basketBooksQuantity);
        model.addAllAttributes(maps);
        return "loginUsersIndex";
    }

    @GetMapping("/index/search/{title}")
    public String showSearchPage(Book book, User user, Model model) throws SQLException {
        this.books.clear();
        this.books = new ArrayList<>();

        BookService bookService =  bookConfig.getBookService();
        System.out.println(book.getTitle());
        this.books = bookService.searchBook(book.getTitle());
        System.out.println(books.size());
        model.addAttribute("books",this.books);
        return "index";
    }

    @PostMapping("/index/search")
    public String searchBook(Book book, User user, Model model,BindingResult result) throws SQLException {
        BookService bookService =  bookConfig.getBookService();
        String title = book.getTitle();
        this.books= bookService.searchBook(title);
        return "redirect:/index/search/"+title;
    }

    @GetMapping("/loginUsersIndex/search/{title}")
    public String showLoginUserSearchPage(Book book, @SessionAttribute("loginUser")User user, Model model) throws SQLException {
        this.books.clear();
        this.books = new ArrayList<>();
        HashMap<String,Object> maps = new HashMap<>();
        BookService bookService =  bookConfig.getBookService();
        this.books = bookService.searchBook(book.getTitle());
        BasketService basketService = new BasketService(bookConfig.getDataService().getDBService());
        basketBooksQuantity = basketService.getBasketSize(user.getId());
        maps.put("user",user);
        maps.put("books",this.books);
        maps.put("basketQuantity", basketBooksQuantity);
        model.addAllAttributes(maps);
        return "loginUsersIndex";
    }

    @PostMapping("/loginUsersIndex/search")
    public String searchUserLoginBook(Book book, @SessionAttribute("loginUser")User user, Model model,BindingResult result) throws SQLException {
        BookService bookService =  bookConfig.getBookService();
        String title = book.getTitle();
        this.books= bookService.searchBook(title);
        return "redirect:/loginUsersIndex/search/"+title;
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    @GetMapping("/book/{id}")
    public String showBooksDetails(@SessionAttribute(required=false,name="loginUser")User user, Book book, Model model, @PathVariable int id) throws SQLException {
        BookService bookService = bookConfig.getBookService();
        BasketService basketService = new BasketService(bookConfig.getDataService().getDBService());
        String url,urlUser,urlSearch;

        book = bookService.getBookInformationById(id);
        int quantity[] = {1};
        HashMap<String, Object> maps = new HashMap<>();
        if(user != null) {
            basketBooksQuantity = basketService.getBasketSize(user.getId());
            url = "/loginUsersIndex";
            urlUser="/user";
            urlSearch="/loginUsersIndex/search";
            maps.put("url",url);
            maps.put("urlUser",urlUser);
            maps.put("urlSearch",urlSearch);
            maps.put("basketQuantity", basketBooksQuantity);
            maps.put("user", user);
            if(book.getQuantity()!=0){
                int info[] = {};
                maps.put("quantity",quantity);
                maps.put("info",info);
            } else {
                int info[] = {1};
                maps.put("info",info);
            }
        } else {
            url = "/index";
            urlSearch="/index/search";
            maps.put("urlSearch",urlSearch);
            maps.put("url",url);
        }
        maps.put("book", book);
        model.addAllAttributes(maps);
        return "bookDetails";
    }

    @GetMapping("/book/basket/{idB}/{idU}")
    public String addBookToBasket(@SessionAttribute("loginUser") User user, Book book, Model model, @PathVariable int idB,@PathVariable int idU) throws SQLException {
        books = new ArrayList<>();
        BasketService basketService = new BasketService(bookConfig.getDataService().getDBService());
        basketBooksQuantity = basketService.getBasketSize(user.getId())+1;
        books = basketService.addBookToBasket(idU,idB);
        HashMap<String,Object> maps = new HashMap<>();
        maps.put("user",user);
        maps.put("basket",books);
        maps.put("basketQuantity", basketBooksQuantity);
        model.addAllAttributes(maps);
        if(basketBooksQuantity == 0){
            return "emptyBasket";
        } else {
            return "basket";
        }

    }
    @GetMapping("/basket/{idU}")
    public String showBasket(@SessionAttribute("loginUser") User user, Book book, Model model,@PathVariable int idU) throws SQLException {
        books = new ArrayList<>();
        BasketService basketService = new BasketService(bookConfig.getDataService().getDBService());
        books = basketService.getBasket(idU);
        basketBooksQuantity = basketService.getBasketSize(user.getId());
        HashMap<String,Object> maps = new HashMap<>();
        maps.put("user",user);
        maps.put("basket",books);
        maps.put("basketQuantity", basketBooksQuantity);
        model.addAllAttributes(maps);
        if(basketBooksQuantity == 0){
            return "emptyBasket";
        } else {
            return "basket";
        }
    }

    @PostMapping("/basketDel/{idU}")
    public String cancelBasket(User user, Model model, BindingResult result,@PathVariable int idU) throws SQLException {
        BasketService basketService = new BasketService(bookConfig.getDataService().getDBService());
        basketService.deleteBasket(idU);
        return "redirect:/basket/{idU}";

    }
    @PostMapping("/basketRental/{idU}")
    public String rentalBasket(User user, Model model, BindingResult result,@PathVariable int idU) throws SQLException {
        BasketService basketService = new BasketService(bookConfig.getDataService().getDBService());
        books = basketService.getBasket(idU);
        for(int i=0;i<books.size();i++) {
            basketService.addBasketToRental(idU, books.get(i).getId());
        }
        basketService.deleteBasket(idU);
        return "redirect:/basket/{idU}";

    }

}
