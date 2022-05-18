package kz.narxoz.springSecurity.narxozsecurity.narxozsecurity.controllers;


import kz.narxoz.springSecurity.narxozsecurity.narxozsecurity.model.*;
import kz.narxoz.springSecurity.narxozsecurity.narxozsecurity.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private PlaneRepository planeRepository;

    @Autowired
    private DirectionRepository directionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;








    @GetMapping(value = "/")
    public String indexPage(Model model){
        List<Plane> planes = planeRepository.findAll();
        model.addAttribute("plane" , planes);


        return "index";
    }



    @GetMapping(value = "/login")
    public String login(Model model){
        model.addAttribute("currentUser" , getCurrentUser());
        return "login";
    }




    @PostMapping(value = "/adduser")
    public String adduser(@RequestParam(name = "user_email")String email,
                          @RequestParam(name = "user_password")String password,
                          @RequestParam(name = "user_full_name")String name){
            Users users = new Users();
            users.setEmail(email);
            users.setPassword(password);
            users.setFullName(name);
            userRepository.save(users);

        return "redirect:/login";
    }



    @GetMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model){
        model.addAttribute("currentUser" , getCurrentUser());
        return "profile";
    }

    @GetMapping(value = "/adminpanel")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String admin(Model model){
        model.addAttribute("currentUser" , getCurrentUser());
        return "adminpanel";
    }






    @GetMapping(value = "/moderatorpanel")
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR')")
    public String moderator(Model model){
        model.addAttribute("currentUser" , getCurrentUser());
        return "moderatorpanel";
    }

    @GetMapping(value = "/403")
    public String accessDeniedPage(Model model){
        model.addAttribute("currentUser" , getCurrentUser());
        return "403";
    }

    private Users getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            Users currentUser = (Users) authentication.getPrincipal();
            return currentUser;
        }
        return null;
    }

    @GetMapping(value = "/addClothing")
    public String ClothingAdd(Model model){
        List <Plane> planes = planeRepository.findAll();
        model.addAttribute("plane" , planes);

        List <Direction> directions = directionRepository.findAll();
        model.addAttribute("direction" , directions);



        return "addClothing";
    }

    @PostMapping(value = "/addClothing")
    public String addPlayer(@RequestParam(name = "price")int price,
                            @RequestParam(name = "number") String number,
                            @RequestParam(name = "place") String  place,
                            @RequestParam(name = "dire_id") Long direId){
        Direction direction = directionRepository.findById(direId).orElse(null);

        if(direction != null ) {
            Plane plane = new Plane();
            plane.setPrice(price);
            plane.setNumber(number);
            plane.setPlace(place);
            plane.setDirection(direction);
            planeRepository.save(plane);
        }

        return "redirect:/addClothing";
    }

    @GetMapping(value = "/setting")
    public String setting(Model model){
        List<Users> users = userRepository.findAll();
        model.addAttribute("user" , users);
        return "setting";
    }


    @GetMapping(value = "/details/{id}")
    public String details(@PathVariable(name = "id")Long id , Model model){
        Plane plane = planeRepository.findById(id).orElse(null);
        model.addAttribute("plane" , plane);

        List <Direction> directions = directionRepository.findAll();
        model.addAttribute("direction" , directions);


        return "Details";
    }

    @GetMapping(value = "/buy/{id}")
    public String Buy(@PathVariable(name = "id")Long id , Model model){
        Plane plane = planeRepository.findById(id).orElse(null);
        model.addAttribute("plane" , plane);

        List <Direction> directions = directionRepository.findAll();
        model.addAttribute("direction" , directions);


        return "Buy";
    }

    @PostMapping(value = "/saveClothing")
    public String savePlayer(@RequestParam(name = "id")Long id,
                             @RequestParam(name = "price")int price,
                             @RequestParam(name = "number")String  number,
                             @RequestParam(name = "place")String  place,
                             @RequestParam(name = "dire_id")Long direId){
        Plane plane = planeRepository.findById(id).orElse(null);
        Direction direction = directionRepository.findById(direId).orElse(null);
        if(plane != null && direction != null ) {
            plane.setPrice(price);
            plane.setNumber(number);
            plane.setPlace(place);
            plane.setDirection(direction);
            planeRepository.save(plane);
            return "redirect:/details/" + id;
        }
        return "redirect:/addClothing";
    }

    @PostMapping(value = "/delete")
    public String deletePlayer(@RequestParam(name = "id")Long id){
        Plane plane = planeRepository.findById(id).orElse(null);
        if(plane != null) {
            planeRepository.delete(plane);
        }
        return "redirect:/addClothing";
    }






    @GetMapping(value = "/detailsu/{id}")
    public String detailsu(@PathVariable(name = "id")Long id , Model model){
        Users users = userRepository.findById(id).orElse(null);
        model.addAttribute("user" , users);

        return "detailsu";
    }

    @PostMapping(value = "/saveUser")
    public String savePlayer(@RequestParam(name = "id")Long id,
                             @RequestParam(name = "email")String email,
                             @RequestParam(name = "full_name")String name){
        Users users = userRepository.findById(id).orElse(null);
        if(users != null) {
            users.setEmail(email);
            users.setFullName(name);
            userRepository.save(users);
            return "redirect:/detailsu/" + id;
        }
        return "redirect:/profile";
    }



    @PostMapping(value = "/deleteUser")
    public String deleteUser(@RequestParam(name = "id")Long id){
        Users users = userRepository.findById(id).orElse(null);
        if(users != null) {
            userRepository.delete(users);
        }
        return "redirect:/profile";
    }

    @GetMapping(value = "/players")
    public String players(Model model){
        List<Plane> planes = planeRepository.findAll();
        model.addAttribute("plane" , planes);


        return "Players";
    }





}
