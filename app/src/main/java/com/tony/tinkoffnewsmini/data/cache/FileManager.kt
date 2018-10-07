package com.tony.tinkoffnews.data.cache

import java.io.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Helper class to do operations on regular files/directories.
 */
@Singleton
class FileManager @Inject constructor() {

    internal fun writeToFile(file: File, fileContent: String) {
        if (!file.exists()) {
            try {
                val writer = FileWriter(file)
                writer.write(fileContent)
                writer.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun readFileContent(file: File): String {
        val fileContentBuilder = StringBuilder()
        if (file.exists()) {
            var stringLine: String?
            try {
                val fileReader = FileReader(file)
                val bufferedReader = BufferedReader(fileReader)
                stringLine = bufferedReader.readLine()
                while (stringLine != null) {
                    fileContentBuilder.append(stringLine).append("\n")
                    stringLine = bufferedReader.readLine()
                }
                bufferedReader.close()
                fileReader.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return fileContentBuilder.toString()
    }

    fun exists(file: File): Boolean {
        return file.exists()
    }

    fun clearDirectory(directory: File): Boolean {
        if (directory.exists()) {
            return directory.delete()
        }
        return false
    }
}
