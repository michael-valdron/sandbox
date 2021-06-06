package com.example.blog;

public class StringUtil {
    public static String toSlug(String src) {
        return String.join("-", src.toLowerCase()
            .replaceAll("\n", " ")
            .replaceAll("[^a-z\\d\\s]", " ")
            .split(" ")
        ).replaceAll("-+", "-");
    }
}
