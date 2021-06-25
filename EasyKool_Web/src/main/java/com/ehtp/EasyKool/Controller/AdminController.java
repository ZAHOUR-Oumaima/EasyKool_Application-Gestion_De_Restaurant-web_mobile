package com.ehtp.EasyKool.Controller;

import com.ehtp.EasyKool.Model.*;
import com.ehtp.EasyKool.Service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
//@RequestMapping(value = "/dashboard")
public class AdminController {

   /* public ArrayList<Order> hashToOrder(ArrayList<HashMap<String, String>> hash){
        ArrayList<Order> orders = new ArrayList<>();
        for(HashMap<String, String> hashCount: hash){
            Order order = new Order(hashCount.get("articleid"),hashCount.get("articlename"),hashCount.get("quantity"),hashCount.get("points"),hashCount.get("date"));
            orders.add(order);
        }
        return orders;
    }*/

    String oldName;

    String date;
    String indexDate;

    @RequestMapping(value = "/dashboard")
    public String dashboard(Model model){
        model.addAttribute("nbrReservations", new OrderService().calculNbrReservation());
        model.addAttribute("requests", new AdminService().getOrders());
        return "dashboard";
    }

    @RequestMapping(value = "/menu")
    public String menu(){
        return "menu";
    }

    @RequestMapping(value = "/tableArticle")
    public String tableArticle(@RequestParam(value = "nomArticle", required = false) String name,Model model){
        if(name != null){
            new ArticleService().deleteArticle(name);
        }
        model.addAttribute("articles",new ArticleService().getArticles());
        return "tableArticle";
    }

    @GetMapping(value = "/addArticle")
    public String addArticle(Model model){
        model.addAttribute("article",new Article());
        return "addArticle";
    }

    @PostMapping("/tableArticle")
    public String addArticleSubmit(@ModelAttribute Article article, Model model)  {
        new ArticleService().addArticle(article);
        return "redirect:/tableArticle";
    }

    @GetMapping("/editArticle")
    public String editArticle(@RequestParam(value = "nomArticle", required = false) String name,Model model){
        Article article = new ArticleService().getArticleByName(name);
        oldName = name;
        model.addAttribute("oldArticle", article);
        return "editArticle";
    }

    @PostMapping("/tableArticleUpdated")
    public String updateArticle(@ModelAttribute Article article, Model model){
        new ArticleService().editArticle(oldName,article);
        model.addAttribute("articles",new ArticleService().getArticles());
        return "/tableArticle";
    }

    @GetMapping("/menu")
    public String menuByDay(@RequestParam(value = "dayIndex", defaultValue = "0") String index,Model model){

        date = new MenuService().getDateByDay(index);
        indexDate = index;

        ArrayList<Article> articles = new ArrayList<>(),Breakfast  = new ArrayList<>(),lunch  = new ArrayList<>(),dinner  = new ArrayList<>();
        ArrayList<Article> BreakfastMenu = new ArrayList<>(), lunchMenu = new ArrayList<>(), dinnerMenu = new ArrayList<>();

        articles = new ArticleService().getArticles();
        Breakfast = new ArticleService().getArticleByType("Breakfast",articles);
        lunch = new ArticleService().getArticleByType("lunch",articles);
        dinner = new ArticleService().getArticleByType("dinner",articles);

        BreakfastMenu = new MenuService().getMenu(date,"breakfast");
        lunchMenu = new MenuService().getMenu(date,"lunch");
        dinnerMenu = new MenuService().getMenu(date,"dinner");


        model.addAttribute("Breakfast", Breakfast);
        model.addAttribute("lunch", lunch);
        model.addAttribute("dinner", dinner);

        model.addAttribute("BreakfastMenu",BreakfastMenu);
        model.addAttribute("lunchMenu",lunchMenu);
        model.addAttribute("dinnerMenu",dinnerMenu);

        model.addAttribute("dateDay", date);

        model.addAttribute("breakfastObject",new Articles());
        model.addAttribute("lunchObject",new Articles());
        model.addAttribute("dinnerObject",new Articles());



        return "menu";
    }

    @PostMapping("/menuBreakfast")
    public String menuAddedBreakfast(@ModelAttribute Articles lesArticles, Model model){
        ArrayList<Article> articles = new ArrayList<>();

        for(String art: lesArticles.getArticles()){
            if(art != null){
                articles.add(new ArticleService().getArticleByName(art));
            }
        }

        new MenuService().addMenuBreakfast(date, articles);

        return "redirect:/menu?dayIndex="+indexDate;
    }

    @PostMapping("/menuLunch")
    public String menuAddedLunch(@ModelAttribute Articles lesArticles, Model model){

        ArrayList<Article> articles = new ArrayList<>();

        for(String art: lesArticles.getArticles()){
            if(art != null){
                articles.add(new ArticleService().getArticleByName(art));
            }
        }

        new MenuService().addMenuLunch(date, articles);

        return "redirect:/menu?dayIndex="+indexDate;
    }

    @PostMapping("/menuDinner")
    public String menuAddedDinner(@ModelAttribute Articles lesArticles, Model model){

        ArrayList<Article> articles = new ArrayList<>();

        for(String art: lesArticles.getArticles()){
            if(art != null){
                articles.add(new ArticleService().getArticleByName(art));
            }
        }

        new MenuService().addMenuDinner(date, articles);

        return "redirect:/menu?dayIndex="+indexDate;
    }



}
