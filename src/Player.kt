data class Player(val name: String, var position: Position, var direction: Direction, val sequence: String, var treasures: Int = 0) {

    fun move(map: Map, move: Char) {
        when (move) {
            'A' -> moveForward(map)
            'G' -> turnLeft()
            'D' -> turnRight()
        }
    }

    private fun turnRight() {
        direction = when (direction) {
            Direction.N -> Direction.E
            Direction.E -> Direction.S
            Direction.S -> Direction.O
            Direction.O -> Direction.N
        }
    }

    private fun turnLeft() {
        direction = when (direction) {
            Direction.N -> Direction.O
            Direction.O -> Direction.S
            Direction.S -> Direction.E
            Direction.E -> Direction.N
        }
    }

    private fun moveForward(map: Map) {
        val newPosition = when (direction) {
            Direction.N -> position.copy(y = position.y - 1)
            Direction.S -> position.copy(y = position.y + 1)
            Direction.O -> position.copy(x = position.x - 1)
            Direction.E -> position.copy(x = position.x + 1)
        }

        if (map.isInsideMap(newPosition) && !map.isMountain(newPosition)) {
            position = newPosition
            if (map.isTreasure(newPosition)) {
                map.collectTreasure(newPosition)
                collectTreasure()
            }
        }
    }

    private fun collectTreasure() = ++treasures
}