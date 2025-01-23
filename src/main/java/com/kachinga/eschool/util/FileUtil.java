package com.kachinga.eschool.util;

import com.kachinga.eschool.config.ApiConfig;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Base64;

/**
 * Utility class for file-related operations such as converting files to Base64 strings.
 */
public final class FileUtil {

    /**
     * Converts a given file to a Base64-encoded string.
     *
     * @param file The file to be converted.
     * @return A Base64-encoded string representation of the file's contents.
     */
    public static String convertFileToBase64(File file) {
        try {
            if (file.exists()) {
                byte[] bytes = FileUtils.readFileToByteArray(file);
                return Base64.getEncoder().encodeToString(bytes);
            } else {
                String uploadDirectory = ApiConfig.UPLOADS_DIRECTORY; // Assuming ApiConfig is a class with constants
                File notFoundFile = new File(uploadDirectory, "no-file.pdf"); // Default file if the specified one is not found
                byte[] bytes = FileUtils.readFileToByteArray(notFoundFile);
                return Base64.getEncoder().encodeToString(bytes);
            }
        } catch (Exception e) {
            // If an exception occurs during the process, return a Base64-encoded string of "No file found"
            byte[] bytesEncoded = Base64.getEncoder().encode("No file found".getBytes());
            return Base64.getEncoder().encodeToString(bytesEncoded);
        }
    }
}
