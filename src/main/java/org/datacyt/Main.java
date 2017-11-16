package org.datacyt;

/**
 * Hello world!
 *
 */
import com.google.gson.GsonBuilder;
import org.apache.velocity.app.VelocityEngine;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main {

    public static DataTableModel dtm= null;
    public static void main(String[] args) throws Exception{

        staticFileLocation("/public");


        get("/biaxial/getparams", (request, response) -> {

            int x = Integer.parseInt(request.queryParams("X"));
            int y = Integer.parseInt(request.queryParams("Y"));

            Map<String, Object> model = new HashMap<>();
            model.put("name", "Nikolay");
            model.put("X",x);
            model.put("Y",y);

            return new ModelAndView(model, "templates/hello.vtl");

        }, new VelocityTemplateEngine());

        get("/biaxial/getparams", (request, response) -> {
            GsonBuilder gson = new GsonBuilder();

            return gson.toString();
        });


        get("/vtl", (request, response) -> {

            int x = Integer.parseInt(request.queryParams("X"));
            int y = Integer.parseInt(request.queryParams("Y"));

            Map<String, Object> model = new HashMap<>();
            model.put("name", "Nikolay");
            model.put("X",x);
            model.put("Y",y);

            return new ModelAndView(model, "templates/hello.vtl");

        }, new VelocityTemplateEngine());




        get("/hello", (request, response) -> {
            return "<html><body><img src=\"http://localhost:4567/biaxial?X=3&Y=7\"></body></html>";
        });

        get("/biaxial", (request, response) -> {

            int x = Integer.parseInt(request.queryParams("X"));
            int y = Integer.parseInt(request.queryParams("Y"));

            if(dtm==null){
                System.out.println("Loading the table...");
                dtm = new DataTableModel(new File("C:\\Users\\Nikolay Samusik\\Documents\\BM2_cct_normalized_07_non-Neutrophils_asinh.csv"));
                System.out.println("Done");
            }

            System.out.println("drawing the biaxial: X=" +x +  " Y=" + y);


            BiaxialPlot bp = new BiaxialPlot(dtm);
            BufferedImage bi = bp.getImage(x,y);
            ImageIO.write(bi,"PNG", new File("C:\\Users\\Nikolay Samusik\\Documents\\plot.png"));

            Path path = Paths.get("C:\\Users\\Nikolay Samusik\\Documents\\plot.png");
            byte[] data = null;
            try {
                data = Files.readAllBytes(path);
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            HttpServletResponse raw = response.raw();
            response.type("image/png");
            response.status(200);
            try {
                raw.getOutputStream().write(data);
                raw.getOutputStream().flush();
                raw.getOutputStream().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        });
    }
}