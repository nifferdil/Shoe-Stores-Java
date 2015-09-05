import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.sql2o.*;
import java.util.ArrayList;


public class AppIntegrationTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Shoe Stores");
  }

  @Test
  public void brandFormIsDisplayed() {
    goTo("http://localhost:4567/");
    click("a", withText("Add or view a brand"));
    assertThat(pageSource()).contains("Add a new brand");
  }

  @Test
  public void storeFormIsDisplayed() {
    goTo("http://localhost:4567/");
    click("a", withText("Add or view a store"));
    assertThat(pageSource()).contains("Add store");
  }

  @Test
  public void brandNameIsDisplayedInListWhenCreated() {
    goTo("http://localhost:4567/brands");
    fill("#brandname").with("Addidas");
    submit(".btn");
    assertThat(pageSource()).contains("Addidas");
  }

  @Test
  public void storeNameIsDisplayedInListWhenCreated() {
    goTo("http://localhost:4567/stores");
    fill("#name").with("Foot Locker");
    submit(".btn");
    assertThat(pageSource()).contains("Foot Locker");
  }

  @Test
  public void brandHasItsOwnPage() {
    Brand testBrand = new Brand("Puma");
    testBrand.save();
    goTo("http://localhost:4567/brands");
    click("a", withText("Puma"));
    assertThat(pageSource()).contains("Puma");
  }

  @Test
  public void storeHasItsOwnPage() {
    Store testStore = new Store("Foot Locker");
    testStore.save();
    goTo("http://localhost:4567/stores");
    click("a", withText("Foot Locker"));
    assertThat(pageSource()).contains("Here are the brands for this store:");
  }

  @Test
  public void storeIsDeleted() {
    Store myStore = new Store("Payless");
    myStore.save();
    goTo("http://localhost:4567/stores");
    click("a", withText("Payless"));
    click("a", withText("Delete this store"));
    assertThat(pageSource()).doesNotContain("Payless");
  }

  @Test
  public void storeIsUpdated() {
    Store myStore = new Store("Payless");
    myStore.save();
    goTo("http://localhost:4567/stores");
    fill("#name").with("Payless Shoes");
    submit(".btn");
    assertThat(pageSource()).contains("Payless Shoes");
  }
}
