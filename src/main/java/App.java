import java.util.HashMap;
import java.util.List;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;


public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    /* Index --> List of Brands */
    get("/brands", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      List<Brand> brands = Brand.all();
      model.put("brands", brands);
      model.put("allStores", Store.all());
      model.put("template", "templates/brands.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    /* Index --> List of Stores */
    get("/stores", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      List<Store> stores = Store.all();
      model.put("stores", stores);
      model.put("template", "/templates/stores.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    /* List of brands -> POST a new brand*/
    post("/brands", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("brandname");
      Brand newBrand = new Brand(name);
      newBrand.save();
      response.redirect("/brands");
      return null;
    });

    /* List of stores -> POST a new store*/
    post("/stores", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      Store newStore = new Store(name);
      newStore.save();
      response.redirect("/stores");
      return null;
    });

    /* List of brands --> view an individual brand */
    get("/brands/:id", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Brand brand = Brand.find(id);
      model.put("brand", brand);
      model.put("allStores", Store.all());
      model.put("template", "templates/brand.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    /* List of stores --> view an individual store */
    get("/stores/:id", (request,response) ->{
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Store store = Store.find(id);
      model.put("store", store);
      model.put("allBrands", Brand.all());
      model.put("template", "templates/store.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    /* Individual brand --> POST an store */
    post("/add_stores", (request, response) -> {
      int brandId = Integer.parseInt(request.queryParams("brand_id"));
      int storeId = Integer.parseInt(request.queryParams("store_id"));
      Store store = Store.find(storeId);
      Brand brand = Brand.find(brandId);
      brand.addStore(store);
      response.redirect("/brands/" + brandId);
      return null;
    });

    /* Individual store --> POST an brand */
    post("/add_brands", (request, response) -> {
      int brandId = Integer.parseInt(request.queryParams("brand_id"));
      int storeId = Integer.parseInt(request.queryParams("store_id"));
      Store store = Store.find(storeId);
      Brand brand = Brand.find(brandId);
      store.addBrand(brand);
      response.redirect("/stores/" + storeId);
      return null;
    });

    get("/brands/:id/edit", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Brand editBrand = Brand.find(id);
      model.put("editBrand", editBrand);
      List<Brand> brands = Brand.all();
      model.put("brands", brands);
      model.put("allStores", Store.all());
      model.put("template", "templates/brands.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/brands/:id/edit", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Brand editBrand = Brand.find(id);
      String brandname = request.queryParams("brandname");
      editBrand.update(brandname);
      List<Brand> brands = Brand.all();
      model.put("brands", brands);
      model.put("allStores", Store.all());
      model.put("template", "templates/brands.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stores/:id/edit", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Store editStore = Store.find(id);
      model.put("editStore", editStore);
      List<Store> stores = Store.all();
      model.put("stores", stores);
      model.put("allBrands", Brand.all());
      model.put("template", "templates/stores.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stores/:id/edit", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Store editStore = Store.find(id);
      String name = request.queryParams("name");
      editStore.update(name);
      List<Store> stores = Store.all();
      model.put("stores", stores);
      model.put("allBrands", Brand.all());
      model.put("template", "templates/stores.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/brands/:id/delete", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Brand deleteBrand = Brand.find(id);
      deleteBrand.delete(id);
      List<Brand> brands = Brand.all();
      model.put("brands", brands);
      model.put("allStores", Store.all());
      model.put("template", "templates/brands.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stores/:id/delete", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Store deleteStore = Store.find(id);
      deleteStore.delete();
      List<Store> stores = Store.all();
      model.put("stores", stores);
      model.put("template", "templates/stores.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
