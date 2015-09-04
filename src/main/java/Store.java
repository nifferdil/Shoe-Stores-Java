import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Store {
  private int id;
  private String name;
  
  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Store(String name) {
    this.name = name;
  }

  public static List<Store> all() {
    String sql = "SELECT id, name FROM stores ORDER BY name ASC";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Store.class);
    }
  }

  @Override
  public boolean equals(Object otherStore){
    if (!(otherStore instanceof Store)) {
      return false;
    } else {
      Store newStore = (Store) otherStore;
      return this.getName().equals(newStore.getName());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stores(name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .executeUpdate()
      .getKey();
    }
  }

  public static Store find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stores WHERE id=:id";
      Store Store = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Store.class);
      return Store;
    }
  }

  public void addBrand(Brand brand) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stores_brands (brand_id, store_id) VALUES (:brand_id, :store_id)";
      con.createQuery(sql)
      .addParameter("brand_id", brand.getId())
      .addParameter("store_id", this.getId())
      .executeUpdate();
    }
  }

  public ArrayList<Brand> getBrands() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT brand_id FROM stores_brands WHERE store_id = :store_id";
      List<Integer> brandIds = con.createQuery(sql)
      .addParameter("store_id", this.getId())
      .executeAndFetch(Integer.class);

      ArrayList<Brand> brands = new ArrayList<Brand>();

      for (Integer brandId : brandIds) {
        String brandQuery = "Select * From brands WHERE id = :brandId";
        Brand brand = con.createQuery(brandQuery)
        .addParameter("brandId", brandId)
        .executeAndFetchFirst(Brand.class);
        brands.add(brand);
      }
      return brands;
    }
  }

  public void update(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE stores SET name = :name WHERE id = :id";
      con.createQuery(sql)
      .addParameter("name", name)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM stores WHERE id = :id;";
      con.createQuery(deleteQuery)
      .addParameter("id", id)
      .executeUpdate();

      String joinDeleteQuery = "DELETE FROM stores_brands WHERE store_id = :storeId";
      con.createQuery(joinDeleteQuery)
      .addParameter("storeId", this.getId())
      .executeUpdate();
    }
  }
}
