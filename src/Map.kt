data class Map(val width: Int, val height: Int, val mountains: List<Position>, val treasures: MutableMap<Position, Int>) {
    fun isMountain(position: Position): Boolean {
        return mountains.contains(position)
    }

    fun isTreasure(position: Position): Boolean {
        return treasures.contains(position)
    }

    fun isInsideMap(position: Position): Boolean {
        return position.x in 0..<width && position.y in 0..<height
    }

    fun collectTreasure(position: Position) {
        val nbOfTreasures = treasures[position]
        if (treasures.containsKey(position) && nbOfTreasures != null) {
            treasures[position] = nbOfTreasures.minus(1)
        }
    }
}