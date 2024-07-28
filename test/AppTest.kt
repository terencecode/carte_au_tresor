import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File

class AppTest {

    @Test
    fun testReadInputFile() {
        val (map, players) = App.readInputFile("test/resources/input.txt")

        Assertions.assertEquals(3, map.width)
        Assertions.assertEquals(4, map.height)
        Assertions.assertEquals(listOf(Position(1, 0), Position(2, 1)), map.mountains)
        Assertions.assertEquals(mutableMapOf(Position(0, 3) to 2, Position(1, 3) to 3), map.treasures)
        Assertions.assertEquals(listOf(Player("Lara", Position(1, 1), Direction.S, "AADADAGGA")), players)
    }

    @Test
    fun testTreasuresCollection() {
        val player = Player("Lara", Position(1, 1), Direction.S, "AADADAGGA")
        val width = 3
        val height = 4
        val mountain1 = Position(1, 0)
        val mountain2 = Position(2, 1)
        val treasuresLocation1 = Position(0, 3) to 2
        val treasuresLocation2 = Position(1, 3) to 3
        val map = Map(width, height, mutableListOf(mountain1, mountain2), mutableMapOf(treasuresLocation1, treasuresLocation2))
        val initialMap = map.copy()
        App.play(mutableListOf(player), map)

        val expectedMap = initialMap.copy(treasures = mutableMapOf(
            treasuresLocation1.first to treasuresLocation1.second - 2,
            treasuresLocation2.first to treasuresLocation2.second - 1)
        )

        Assertions.assertEquals(Position(0, 3), player.position)
        Assertions.assertEquals(map, expectedMap)
    }

    @Test
    fun testCannotWalkOverMountain() {
        val player = Player("Lara", Position(1, 1), Direction.S, "GAAGAAGA")
        val width = 3
        val height = 4
        val mountain1 = Position(1, 0)
        val mountain2 = Position(2, 1)
        val treasuresLocation1 = Position(0, 3) to 2
        val treasuresLocation2 = Position(1, 3) to 3
        val map = Map(width, height, mutableListOf(mountain1, mountain2), mutableMapOf(treasuresLocation1, treasuresLocation2))
        val initialMap = map.copy()
        App.play(mutableListOf(player), map)

        val expectedMap = initialMap.copy()

        Assertions.assertEquals(Position(0, 1), player.position)
        Assertions.assertEquals(map, expectedMap)
    }

    @Test
    fun testCannotWalkBeyondMap() {
        val player = Player("Lara", Position(1, 1), Direction.S, "GGGAAGA")
        val width = 3
        val height = 4
        val mountain1 = Position(1, 0)
        val mountain2 = Position(2, 1)
        val treasuresLocation1 = Position(0, 3) to 2
        val treasuresLocation2 = Position(1, 3) to 3
        val map = Map(width, height, mutableListOf(mountain1, mountain2), mutableMapOf(treasuresLocation1, treasuresLocation2))
        val initialMap = map.copy()
        App.play(mutableListOf(player), map)

        val expectedMap = initialMap.copy()

        Assertions.assertEquals(Position(0, 2), player.position)
        Assertions.assertEquals(map, expectedMap)
    }

    @Test
    fun testWriteOutputFile() {
        val player = Player("Lara", Position(0, 3), Direction.S, "AADADAGGA", 3)
        val mapXLength = 3
        val mapYLength = 4
        val mountain1 = Position(1, 0)
        val mountain2 = Position(2, 1)
        val treasuresLocation1 = Position(0, 3) to 0
        val treasuresLocation2 = Position(1, 3) to 1
        val map = Map(mapXLength, mapYLength, mutableListOf(mountain1, mountain2), mutableMapOf(treasuresLocation1, treasuresLocation2))

        App.writeOutputFile("test/resources/output.txt", map, mutableListOf(player))
        val outputFile = File("output.txt")

        Assertions.assertEquals(
"""C - 3 - 4
M - 1 - 0
M - 2 - 1
T - 0 - 3 - 0
T - 1 - 3 - 2
A - Lara - 0 - 3 - S - 3
""", outputFile.readText())
    }


    companion object {
        @JvmStatic
        @AfterAll
        fun cleanUp() {
            File("test/resources/output.txt").delete()
        }
    }
}