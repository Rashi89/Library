package pl.agata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import pl.agata.config.DataBase;

import java.sql.SQLException;

@Controller
public class DataServiceController {

    private DataBase DBservice;

    @Autowired
    public DataServiceController(DataBase DBservice) {
        this.DBservice = DBservice;
    }

    public ResponseEntity<pl.agata.service.DBService> getDataService() throws SQLException {
        pl.agata.service.DBService data = DBservice.getDBService();
        return ResponseEntity.ok(data);
    }


}
