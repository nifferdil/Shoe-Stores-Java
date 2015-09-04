import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class BrandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  //Test whether the array is empty or nor
  @Test
  public void all_emptyAtFirst() {
    assertEquals(Brand.all().size(), 0);
  }

  // Test for override objects
  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Brand firstBrand = new Brand("Addidas");
    Brand secondBrand = new Brand("Addidas");
    assertTrue(firstBrand.equals(secondBrand));
  }

  @Test
  public void save_savesObjectIntoDatabase() {
    Brand myBrand = new Brand("Addidas");
    myBrand.save();
    Brand savedBrand = Brand.all().get(0);
    assertTrue(savedBrand.equals(myBrand));
  }

  @Test
  public void save_assignsIdToObject() {
    Brand myBrand = new Brand("Addidas");
    myBrand.save();
    Brand savedBrand = Brand.all().get(0);
    assertEquals(myBrand.getId(), savedBrand.getId());
  }

  @Test
  public void find_findsBrandInDatabase_true() {
    Brand myBrand = new Brand("Addidas");
    myBrand.save();
    Brand savedBrand = Brand.find(myBrand.getId());
    assertTrue(myBrand.equals(savedBrand));
  }

  @Test
  public void addStore_addsStoreToBrand() {
    Store myStore = new Store("Foot Locker");
    myStore.save();

    Brand myBrand = new Brand("Addidas");
    myBrand.save();

    myBrand.addStore(myStore);
    Store savedStore = myBrand.getStores().get(0);
    assertTrue(myStore.equals(savedStore));
  }

  @Test
  public void getStores_returnsAllStores_ArrayList() {
    Store myStore = new Store("Foot Locker");
    myStore.save();

    Brand myBrand = new Brand("Addidas");
    myBrand.save();

    myBrand.addStore(myStore);
    List savedStores = myBrand.getStores();
    assertEquals(savedStores.size(), 1);
  }

  @Test
  public void delete_deletesAllBrandsAndListsAssociations() {
    Store myStore = new Store("Foot Locker");
    myStore.save();

    Brand myBrand = new Brand("Addidas");
    myBrand.save();

    myBrand.addStore(myStore);
    myBrand.delete();
    assertEquals(myStore.getBrands().size(), 0);
  }
}
