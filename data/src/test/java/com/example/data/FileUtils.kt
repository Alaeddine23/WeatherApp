package com.example.data

import org.apache.commons.io.IOUtils
import java.io.IOException

class FileUtils {
    companion object {
        private val CLASS_LOADER = FileUtils::class.java.classLoader
        fun read(fileName: String): String? = try {
            val jsonStream = CLASS_LOADER.getResourceAsStream(fileName)
            val json = IOUtils.toString(jsonStream)
            json
        } catch (e: IOException) {
            null
        }
    }
}