
fun main(args: Array<String>) {
    val (map, players) = App.readInputFile(args[0])

    App.play(players, map)

    App.writeOutputFile("output.txt", map, players)

}