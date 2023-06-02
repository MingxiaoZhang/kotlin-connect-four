package model

class Grid(width: Int, height: Int, private val length: Int) {

    private val grid = Array2D(width, height)

    private fun max(item: Pair<Player, Int>, other: Pair<Player, Int>): Pair<Player, Int> {
        return if (item.first == Player.NONE && other.first != Player.NONE) other
        else if (item.first != Player.NONE && other.first == Player.NONE) item
        else if (other.second > item.second) other
        else item
    }

    private fun longestChain(list: List<Player>) : Pair<Player, Int> {
        var curCount = Pair(list[0], 1)
        var maxCount = curCount.copy()
        (1 until list.count()).forEach { idx ->
            curCount = if (list[idx] == curCount.first) {
                Pair(curCount.first, curCount.second + 1)
            } else {
                maxCount = max(maxCount, curCount)
                Pair(list[idx], 1)
            }
        }
        return max(maxCount, curCount)
    }

    fun hasWon() : Player {
        var playerWon = Pair(Player.NONE, 0)

        grid.getColumns().forEach { // check columns
            playerWon = max(playerWon, longestChain(it))
        }

        grid.getRows().forEach { // check rows
            playerWon = max(playerWon, longestChain(it))
        }
        grid.getDiagonals().forEach { // check diagonals top-right -> bottom-left
            playerWon = max(playerWon, longestChain(it))
        }

        grid.getDiagonals2().forEach { // check diagonals bottom-right -> top-left
            playerWon = max(playerWon, longestChain(it))
        }

        return if (playerWon.second >= length) playerWon.first else Player.NONE
    }

    fun hasDraw() : Boolean {
        return grid.getColumns().any { list -> list.any { it == Player.NONE } }.not()
    }

    fun dropPiece(column: Int, player: Player): Piece? {
        val row = grid.getColumn(column).count { it.player == Player.NONE } // number of unoccupied spots in the column
        if (row == 0) return null
        grid.setCell(column, row - 1, player)                                 // set ownership for piece furthest down in the column
        return grid.getCell(column, row - 1)                                  // return piece
    }

    fun getGround(column: Int): Int {
        return grid.getColumn(column).count { it.player == Player.NONE }
    }
}