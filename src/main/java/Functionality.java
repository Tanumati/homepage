import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;

public class Functionality extends Utils {
    SoftAssert softassert= new SoftAssert();

    @BeforeMethod
     public void SetUp(){
        ToLaunchBrowser();
    }
    @Test
     public void UserShouldBeAbleToAddTwoProductInAddToCompareListSuccessfully(){
        // user able to click on third product add to compare list
        ClickElement(By.xpath("//input[@onclick='return AjaxCart.addproducttocomparelist(\"/compareproducts/add/18\"),!1']"));
        //wait for  top of the page green pop up is display
        WaitForElementvisible(By.xpath("//div[@class='bar-notification success']"),5);
        // compare the string text
        String expectedmessage1 = "The product has been added to your product comparison";
        String actualmessage1 = GetTextFromElement(By.xpath("//div[@class='bar-notification success']"));
        //check the assertion
        softassert.assertEquals(expectedmessage1,actualmessage1);
        //close the green bar pop up
        ClickElement(By.xpath("//span[@class=\"close\"]"));
        // user able to click on fourth product add to compare list
        ClickElement(By.xpath("//input[@onclick='return AjaxCart.addproducttocomparelist(\"/compareproducts/add/43\"),!1']"));
        //wait for top of the page green pop up is display
        WaitForElementvisible(By.xpath("//div[@class='bar-notification success']"),5);
        //compare the string text
        String expectedmsg2 = "The product has been added to your product comparison";
        String actualmsg2 = GetTextFromElement(By.xpath("//div[@class='bar-notification success']"));
        softassert.assertEquals(expectedmsg2,actualmsg2);
        //close the green bar popup
        ClickElement(By.xpath("//span[@class=\"close\"]"));
        //click on compare product list
        WaitForElementsvisible(By.xpath("//a[@href=\"/compareproducts\"]"),5);
        ClickElement(By.xpath("//a[@href=\"/compareproducts\"]"));
        //assert to check first product name in the list
        String expectedtext1 = "$25 Virtual Gift Card";
        String actualtext1 = GetTextFromElement(By.linkText("$25 Virtual Gift Card"));
        softassert.assertEquals(expectedtext1,actualtext1);
        //assert to check second product name in the list
        String expectedtext2 = "HTC One M8 Android L 5.0 Lollipop";
        String actualtext2 = GetTextFromElement(By.linkText("HTC One M8 Android L 5.0 Lollipop"));
        softassert.assertEquals(expectedtext2,actualtext2);
        //click on product clear list
        ClickElement(By.xpath("//a[@class='clear-list']"));
        //clear product and get the message
         String expectedText = "You have no items to compare.";
         String actualText = GetTextFromElement(By.className("no-data"));
        softassert.assertEquals(expectedText,actualText);
        //softassert.assertAll();
    }
    @Test
    public void UserShouldBeAbleToAddCommentSuccessfullyAndAlwaysDisplayAtTheBottom(){
        // click on details
        ClickElement(By.xpath("//div/div[2]/div/a[@class=\"read-more\"]"));
        // send text in title
        EnterText(By.id("AddNewComment_CommentTitle"),LoadProps.getProperty("Title"));
        //assertion fir title
        String expectedtitle = "Congratulation";
        String actualtitle = GetTextFromElement(By.className("enter-comment-title"));
        softassert.assertEquals(expectedtitle,actualtitle);
        // send text in comment box
        EnterText(By.id("AddNewComment_CommentText"),LoadProps.getProperty("comment"));
        // assertion for comment
        String expectedcomment = "This is really great news and your success!";
        String actualcomment = GetTextFromElement(By.id("AddNewComment_CommentText"));
        softassert.assertEquals(expectedcomment,actualcomment);
        // click on new comment
        ClickElement(By.xpath("//input[@name=\"add-comment\"]"));
        //get text suucessfully
        String expectedmsg = "News comment is successfully added";
        String actualmsg = GetTextFromElement(By.xpath("//div[@class=\"result\"]"));
        // successfully msg assertion
        softassert.assertEquals(expectedmsg,actualmsg);
        JavascriptExecutor js = (JavascriptExecutor)driver;
        // this object will scrool the page down
        js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
        //verify  my message at bottom of  the page
        // String expectedmsg1= "This is really great news and your success!";
        // String actualmsg2 = GetTextFromElement(By.xpath("//p[contains(text(),'This is really great news and your success!')"));
        // softassert.assertEquals(expectedmsg1,actualmsg2);
        // softassert.assertAll();
    }
    @Test
    public void UserShouldBeAbleToSearchSpecificBrand(){
        //enter the text in search box
        EnterText(By.xpath("//input[@class=\"search-box-text ui-autocomplete-input\"]"),LoadProps.getProperty("productname"));
        // click on the search button
        ClickElement(By.xpath("//input[@value=\"Search\"]"));
        // find web elements
        List<WebElement> itemlist = driver.findElements(By.xpath("//div[@class=\"product-grid\"]"));
        // declaring a string array to store all the web elements
        List<String> textlist = new ArrayList<String>();
        for(WebElement e : itemlist){
            textlist.add(e.getText());
        }
        System.out.println(textlist.toString());
        //assert method to compare
        softassert.assertTrue(textlist.toString().contains("nike"));
        //enter any text  out of box
        EnterText(By.xpath("//input[@class=\"search-box-text ui-autocomplete-input\"]"),LoadProps.getProperty("outofboxname"));
        // click on the search button
        ClickElement(By.xpath("//input[@value=\"Search\"]"));
        //string text expected
        String expectedmessage = "No products were found that matched your criteria.";
        String actualmessage = GetTextFromElement(By.xpath("//div[@class=\"no-result\"]"));
        softassert.assertEquals(expectedmessage,actualmessage);
       // softassert.assertAll();
    }
     @AfterMethod
     public void teardown(){
        ToClosebrowser();
    }

}
