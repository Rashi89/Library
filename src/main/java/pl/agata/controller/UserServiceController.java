package pl.agata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.agata.books.Book;
import pl.agata.config.UserServiceConfig;
import pl.agata.rental.Rental;
import pl.agata.service.BasketService;
import pl.agata.service.BookService;
import pl.agata.service.UserService;
import pl.agata.user.User;


import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Controller
@SessionAttributes({"loginUser","errors"})
public class UserServiceController {

    private UserServiceConfig userServiceConf;
    private int id;
    private String name;
    private String surname;
    private String password;
    private User user;
    private List<Rental> rentals;
    private int rentalsQuantity;
    private int basketBooksQuantity;

    @Autowired
    public UserServiceController(UserServiceConfig userServiceConf) {
        this.userServiceConf = userServiceConf;

    }

    public ResponseEntity<UserService> getUserService() throws SQLException {
        UserService userService = userServiceConf.getUserService();
        return ResponseEntity.ok(userService);
    }

    @GetMapping("/user")
    public HashMap<String,Object> showUserPage(@SessionAttribute("loginUser")User user, Book book, Model model) throws SQLException {
        rentals = new ArrayList<>();
        UserService userService = userServiceConf.getUserService();
        BasketService basketService = new BasketService(userServiceConf.getDataService().getDBService());
        rentals=userService.getRentalsUser(user);
        rentalsQuantity = userService.quantityRentalsUser(rentals);
        basketBooksQuantity = basketService.getBasketSize(user.getId());
        HashMap<String,Object> maps = new HashMap<>();
        maps.put("book",book);
        maps.put("user",user);
        maps.put("rentals",rentals);
        maps.put("rentalsQuantity",rentalsQuantity);
        maps.put("basketQuantity", basketBooksQuantity);
        model.addAllAttributes(maps);
        return maps;
    }
    @PostMapping("/userRejestr")
    public String rejestrUser(User user, Model model, BindingResult result) throws SQLException, NoSuchAlgorithmException {

        UserService userService = userServiceConf.getUserService();
        String error="aaaa";
        String name = user.getName();
        String surname = user.getSurname();
        String password = user.getPassword();
        model.addAttribute("errors",error);
        if(name!="" && surname!="" && password!="") {
            user = userService.createUser(name, surname, password);
        }
        return "redirect:/index";
    }


    @PostMapping("/loginUsersIndex")
    public String loginUser(@ModelAttribute("user") User user,@ModelAttribute("error") String error, Model model, BindingResult result) throws SQLException, NoSuchAlgorithmException {

        UserService userService = userServiceConf.getUserService();
        String name = user.getName();
        String surname = user.getSurname();
        String password = user.getPassword();
        user = userService.loginUser(name, surname, password);

        if (user != null) {
            model.addAttribute("loginUser", user);
            return "redirect:/loginUsersIndex";
        } else {
            model.addAttribute("errors",error);

           return "redirect:/index";
        }
    }

}