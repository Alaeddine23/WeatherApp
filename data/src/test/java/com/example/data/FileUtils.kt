package com.example.data

import java.io.InputStreamReader

class FileUtils {
    companion object {
        private val CLASS_LOADER = FileUtils::class.java.classLoader

        fun readJson(fileName: String): String {
            val reader = InputStreamReader(CLASS_LOADER.getResourceAsStream(fileName))
            val content = reader.readText()
            reader.close()
            return content
        }

    }
}