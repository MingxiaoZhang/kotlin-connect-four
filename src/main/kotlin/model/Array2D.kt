package model

import kotlin.math.max
import kotlin.math.min

/**
 * Stores a 2D array.
 *
 * @param width width of the array (x-coordinate)
 * @param height height of the array (y-coordinate)
 */
data class Array2D(private val width: Int, private val height: Int) {

    private var data = Array(width * height) { Piece(it % width, it / width) }

    fun setCell(x: Int, y: Int, value: Player) {
        data[x + y * width].player = value
    }

    fun getCell(x: Int, y: Int) : Piece {
        return data[x + y * width]
    }

    fun getRows() : List<List<Player>> {
        return List<List<Player>>(height) { y -> List<Player>(width) { x -> data[x + width * y].player } }
    }

    fun getColumn(x: Int) : List<Piece> {
        return List<Piece>(height) { y -> data[x + width * y] }
    }

    fun getColumns() : List<List<Player>> {
        return List<List<Player>>(width) { x -> List<Player>(height) { y -> data[x + width * y].player } }
    }

    fun getDiagonals() : List<List<Player>> {
        val mini = min(width, height)
        val maxi = max(width, height)
        return List<List<Player>>(width + height - 1) { d ->
            if (d < mini - 1) { // d=0,1
                List<Player>(d + 1) { n -> data[d - n + width * (n)].player }
            } else if (d < maxi) { // d=2,3,4
                if (width >= height) List<Player>(mini) { n -> data[d - n + width * (n)].player }
                else List<Player>(mini) { n -> data[width - 1 - n + width * (d - width + 1 + n)].player  }
            } else { // d=5,6
                List<Player>(maxi + mini - d - 1) { n -> data[width - 1 - n + width * (-width + d + n + 1)].player }
            }
        }
    }

    fun getDiagonals2() : List<List<Player>> {
        val mini = min(width, height)
        val maxi = max(width, height)
        return List<List<Player>>(width + height - 1) { d ->
            if (d < mini - 1) {
                List<Player>(d + 1) { n -> data[d - n + width * (height - 1 - n)].player }
            } else if (d < maxi) {// d=2,3,4
                if (width >= height) List<Player>(mini) { n -> data[d - n + width * (height - 1 - n)].player }
                else List<Player>(mini) { n -> data[width - 1 - n + width * (height - d + height - width - n)].player  }
                //width - 1 - n + width * (d - width + 1 + height - 1 - n)
            } else {
                List<Player>(maxi + mini - d - 1) { n -> data[width - n - 1 + width * (height - 1 + width - d - n - 1)].player }
            }
        }
    }

    override fun toString(): String {
        return data.foldIndexed("") { idx, acc, cur ->
            acc + cur + if(idx % width == width - 1) "\n" else ""
        }
    }
}