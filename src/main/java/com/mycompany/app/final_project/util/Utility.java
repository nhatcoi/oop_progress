package com.mycompany.app.final_project.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mycompany.app.final_project.models.Room;
import org.json.JSONArray;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Type;

public class Utility {
    public static List<Room> readJSONFile(String filename) {
        try (Reader reader = new FileReader(filename)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Room>>() {}.getType();

            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    public static void writeJSONFile(List<Room> rooms, String filename) {
        try (Writer writer = new FileWriter(filename)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(rooms, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
