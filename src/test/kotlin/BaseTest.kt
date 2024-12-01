import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.Path

open class BaseTest {

    val solution = Class.forName(javaClass.name.replace("Test", ""))
        .getDeclaredConstructor()
        .newInstance() as Solution

    open fun testPart1Sample() {}
    open fun testPart1() {}
    open fun testPart2Sample() {}
    open fun testPart2() {}

    fun getInputString(): String {
        return Files.readString(getInputPath())
    }

    fun getInputLines(): List<String> {
        return Files.readAllLines(getInputPath())
    }

    private fun getInputPath(): Path {
        val day = javaClass.name //aoc2015.Day1Test
            .substringAfter('.') //Day1Test
            .replace("Test", "") //Day1
            .lowercase() //day1
        // `getResource` includes the module path by default
        val resource = javaClass.getResource("input/$day.txt")
        return Path(resource!!.path)
    }
}