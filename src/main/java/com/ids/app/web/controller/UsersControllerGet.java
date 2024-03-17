package com.ids.app.web.controller;

import com.ids.app.domain.UserDto;
import com.ids.app.domain.service.UserDtoService;
import com.ids.app.domain.service.WebScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersControllerGet {
    private final UserDtoService userDtoService;
    private final WebScraperService scraperService;
    @Autowired
    UsersControllerGet(UserDtoService userDtoService,WebScraperService scraperService){
        this.scraperService = scraperService;
        this.userDtoService = userDtoService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>>getAll(){
        List<UserDto> list = userDtoService.getAll();
        return new ResponseEntity<>(userDtoService.getByColumns(), HttpStatus.OK);
    }
    @GetMapping("/user")
    public ResponseEntity<?>getById(@RequestParam(name = "id")long id){
        return new ResponseEntity<>(userDtoService.getById(id),HttpStatus.OK);
    }

    /*
     @GetMapping("/scrape")
    public List<String> scrapeWebPage(){
        return scraperService.scrapeWeb();
    }
     */

}
