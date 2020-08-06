import com.github.jknack.handlebars.Handlebars;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;
public class App {
    public static String render(Map<Object, String> model, String templatePath) {
        return new HandlebarsTemplateEngine().render(new ModelAndView(model, templatePath));
    }
    public static void main(String[] args){
        ArrayList<String> list = new ArrayList<>();

        // root is 'src/main/resources', so put files in 'src/main/resources/public'
        staticFiles.location("/public"); // Static files
        //below is a route (get),
        get("/greet/", (request, response) -> {
            return "Hello World"; });

       get("/greet/:username", (request, response) -> {
            return "Hello: " + request.params(":username");
        });

      get( "/greet/:name/language/:language",(request, response) -> {
        if ( request.params(":language").equals("xhosa")){
            return "Molweni " + request.params(":name") + ", ndiyathemba uvuke kakuhle";
        }
        else if(request.params(":language").equals("english")){
            return "Hello " + request.params(":name") + ", hoping all is well on your side.";
        }
        else if(request.params(":language").equals("sotho")){
            return "Dumelang le kai" + request.params(":name");
        }
        else {
            return "Language not selected " + request.params(":name") ;
        }
       });
        get("/hello", (req, res) -> {
            Map<String, Object> map = new HashMap<>();
            return new ModelAndView(map, "hello.handlebars");
        }, new HandlebarsTemplateEngine());

        post("/hello", (req, res) -> {
            Map<String, Object> map = new HashMap<>(); //havent gone through Mappings
            // create the greeting message
            String username = req.queryParams("username");
            String greeting = "hello, " + username;
            if(!list.contains(username)) {
                list.add(username);
            }
            // put it in the map which is passed to the template - the value will be merged into the template
            map.put("greeting", greeting);
            map.put("users", list);
            return new ModelAndView(map, "hello.handlebars");
        }, new HandlebarsTemplateEngine());
    }
}
