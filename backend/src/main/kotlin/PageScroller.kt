import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver

open class PageScroller {

    fun scrollToBottom(driver: WebDriver, maxScrolls: Int? = 3) {
        val jsExecutor = driver as JavascriptExecutor
        var scrolls = 0
        var lastHeight = jsExecutor.executeScript("return document.body.scrollHeight") as Long

        while (scrolls < maxScrolls!!) {
            // Scroll down to bottom
            jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight);")

            Thread.sleep(1000)

            // Loading more content
            val newHeight = jsExecutor.executeScript("return document.body.scrollHeight") as Long
            if (newHeight == lastHeight) {
                break
            }

            lastHeight = newHeight
            scrolls++
        }
    }
}