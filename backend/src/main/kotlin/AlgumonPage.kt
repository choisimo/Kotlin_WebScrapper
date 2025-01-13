import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

// page_url = https://www.algumon.com/
class AlgumonPage(driver: WebDriver) {
    init {
        PageFactory.initElements(driver, this)
    }

    @FindBy(css = "li.post-li")
    lateinit var productList: MutableList<WebElement>

    // 데이터를 수집하는 메서드
    fun getProductDetails(): List<Map<String, String>> {
        if (!::productList.isInitialized) {
            throw IllegalStateException("Product list is not initialized")
        }

        val products = mutableListOf<Map<String, String>>()
        for (product in productList) {
            try {
                val name = product.findElement(org.openqa.selenium.By.cssSelector("a.product-link")).text
                val price = try {
                    product.findElement(org.openqa.selenium.By.cssSelector("small.product-price")).text
                } catch (e: Exception) {
                    "가격 정보 없음"
                }
                products.add(mapOf("name" to name, "price" to price))
            } catch (e: Exception) {
                println("Error processing product: ${e.message}")
            }
        }
        return products
    }
}

// 메인 함수
// 메인 함수
fun main() {
    // WebDriver 초기화 (ChromeDriver 예시)
    val driver: WebDriver = ChromeDriver()
    try {
        driver.get("https://www.algumon.com/")

        // 요소 로딩을 기다리는 WebDriverWait 설정 (최대 10초)
        val duration : Duration = 10.seconds
        val wait = WebDriverWait(driver, duration.toJavaDuration())
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li.post-li")))

        // AlgumonPage 객체 생성
        val algumonPage = AlgumonPage(driver)

        PageScroller().scrollToBottom(driver, 20)

        // 데이터 수집
        val products = algumonPage.getProductDetails()
        for (product in products) {
            println("Product Name: ${product["name"]}, Price: ${product["price"]}")
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        // WebDriver 종료
        driver.quit()
    }
}
