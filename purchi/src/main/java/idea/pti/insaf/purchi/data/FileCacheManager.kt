package idea.pti.insaf.purchi.data

import android.content.Context
import java.io.File

class FileCacheManager(private val context: Context) {

    private val cacheDirectory: File = context.cacheDir
    private val cache: MutableMap<String, String> = mutableMapOf()

    fun fileExists(fileKey: String): Boolean {
        val file = File(cacheDirectory, fileKey)
        return file.exists()
    }

    fun getFileDataAsString(fileKey: String): String? {
        // Check if the file key is in the cache
        if (cache.containsKey(fileKey)) {
            return cache[fileKey]
        }

        // If not in the cache, check if the file exists in the file system
        if (fileExists(fileKey)) {
            val file = File(cacheDirectory, fileKey)
            val fileData = file.readText()
            // Cache the file data for future use
            cache[fileKey] = fileData
            return fileData
        }

        // File not found
        return null
    }

    fun saveFileData(fileKey: String, fileData: String) {
        // Save file data to both cache and file system
        cache[fileKey] = fileData
        val file = File(cacheDirectory, fileKey)
        file.writeText(fileData)
    }

    fun deleteFile(fileKey: String): Boolean {
        // Check if the file exists before attempting deletion
        if (fileExists(fileKey)) {
            // Remove file data from cache
            cache.remove(fileKey)

            // Delete file from file system
            val file = File(cacheDirectory, fileKey)
            return file.delete()
        }

        // File not found, consider it as "deleted"
        return true
    }
}
