package com.ehtp.EasyKool.Controller;

import com.ehtp.EasyKool.Model.AdminUser;
import com.ehtp.EasyKool.Model.Article;
import com.ehtp.EasyKool.Service.AdminUserService;
import com.ehtp.EasyKool.Service.MenuService;
import com.ehtp.EasyKool.Service.OrderService;
import com.google.firebase.database.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class HomeController {

    /*@RequestMapping(value = { "/test" })
    public String test(Model model) {


        // ajouter des plats a un menu à un jour donnée
        ArrayList<Article> articles = new ArrayList<Article>();
        articles.add(new Article("blabla","blabla","blabla","blabla","blabla"));
        articles.add(new Article("blabla2","blabla2","blabla2","blabla2","blabla2"));
        articles.add(new Article("blabla3","blabla3","blabla3","blabla3","blabla3"));
        new MenuService().addMenu("20-05-2021","lunch",articles);

        ArrayList<Article> getArticles = new ArrayList<Article>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("menu");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    getArticles.add(data.child("20-05-2021").child("Breakfast").getValue(Article.class));
                }
                model.addAttribute("listArticles",getArticles);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return "test";

    }*/

    @GetMapping("/")
    public String loginForm(Model model){
        model.addAttribute("user", new AdminUser());
        return "index";
    }

    @PostMapping("/dashboard")
    public String loginSubmit(@ModelAttribute AdminUser user, Model model){
        if(new AdminUserService().verifyUser(user) == true){ return "redirect:/dashboard"; }
        return "redirect:/";
    }

}
