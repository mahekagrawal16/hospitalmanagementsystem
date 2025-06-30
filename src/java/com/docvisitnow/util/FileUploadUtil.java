/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.docvisitnow.util;
import java.io.*;
import java.nio.file.*;
import jakarta.servlet.http.Part;
/**
 *
 * @author mahekagrawal
 */
public class FileUploadUtil {
    public static String saveFile(Part filePart, String uploadDir) throws IOException {
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String realPath = uploadDir + File.separator + fileName;
        Files.createDirectories(Paths.get(uploadDir));
        filePart.write(realPath);
        return realPath; // or relative path to save in DB
    }

}
