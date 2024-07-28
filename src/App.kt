import java.io.File

object App {
    fun play(players: MutableList<Player>, map: Map) {
        players.forEach { player ->
            player.sequence.forEach { move ->
                player.move(map, move)
            }
        }
    }

    fun readInputFile(filePath: String): Pair<Map, MutableList<Player>> {
        val file = File(filePath)
        val mountains = mutableListOf<Position>()
        val treasures = mutableMapOf<Position, Int>()
        val players = mutableListOf<Player>()
        var width = 0
        var height = 0

        file.forEachLine { line ->
            val instructions = line.split(" - ")
            when (instructions[0]) {
                "C" -> {
                    width = instructions[1].toInt()
                    height = instructions[2].toInt()
                }
                "M" -> {
                    mountains.add(Position(instructions[1].toInt(), instructions[2].toInt()))
                }
                "T" -> {
                    treasures[Position(instructions[1].toInt(), instructions[2].toInt())] = instructions[3].toInt()
                }
                "A" -> {
                    val position = Position(instructions[2].toInt(), instructions[3].toInt())
                    val direction = Direction.valueOf(instructions[4])
                    val sequence = instructions[5]
                    players.add(Player(instructions[1], position, direction, sequence))
                }
            }
        }

        return Map(width, height, mountains, treasures) to players
    }

    fun writeOutputFile(filePath: String, map: Map, players: List<Player>) {
        val file = File(filePath)
        file.printWriter().use { out ->
            out.println("C - ${map.width} - ${map.height}")
            map.mountains.forEach { mountain ->
                out.println("M - ${mountain.x} - ${mountain.y}")
            }
            map.treasures.forEach { (position, quantity) ->
                out.println("T - ${position.x} - ${position.y} - $quantity")
            }
            players.forEach { player ->
                out.println("A - ${player.name} - ${player.position.x} - ${player.position.y} - ${player.direction} - ${player.treasures}")
            }
        }
    }
}