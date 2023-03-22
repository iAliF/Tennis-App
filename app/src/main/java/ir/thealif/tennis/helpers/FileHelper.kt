package ir.thealif.tennis.helpers

import android.content.Context
import ir.thealif.tennis.models.PlayerModel
import org.json.JSONArray
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader

class FileHelper(private val context: Context) {
    companion object {
        const val FILENAME = "players.json"

        private fun getJsonArray(playerModels: ArrayList<PlayerModel>): JSONArray {
            val array = JSONArray()

            playerModels.forEach { model ->
                array.put(model.name)
            }

            return array
        }

        private fun convertJsonArray(array: JSONArray): ArrayList<PlayerModel> {
            val playersList = ArrayList<PlayerModel>()

            for (index in 0 until array.length()) {
                val item = array[index]
                if (item is String) {
                    playersList.add(PlayerModel(item))
                }
            }

            return playersList
        }
    }

    fun saveData(playerModels: ArrayList<PlayerModel>) {
        val array = getJsonArray(playerModels)
        writeFile(array.toString())
    }

    private fun writeFile(data: String) {
        val fileOutStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE)
        fileOutStream.write(data.toByteArray())
        fileOutStream.close()
    }

    private fun readFile(): String? {
        try {
            val fileInStream = context.openFileInput(FILENAME)
            val inStreamReader = InputStreamReader(fileInStream)
            val bufferedReader = BufferedReader(inStreamReader)
            val stringBuilder = StringBuilder()
            var text: String?
            while (run {
                    text = bufferedReader.readLine()
                    text
                } != null) {
                stringBuilder.append(text)
            }

            return stringBuilder.toString()
        } catch (err: FileNotFoundException) {
            return null
        }
    }

    fun loadData(): ArrayList<PlayerModel> {
        val fileData = readFile() ?: return ArrayList()
        val jsonArray = JSONArray(fileData)
        return convertJsonArray(jsonArray)
    }
}