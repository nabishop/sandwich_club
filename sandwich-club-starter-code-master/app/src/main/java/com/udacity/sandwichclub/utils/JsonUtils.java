package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            //get root
            JSONObject root = new JSONObject(json);
            //get name obj
            //get values in name obj
            String mainName = root.getJSONObject("name").getString("mainName");
            //get aka names
            JSONArray aka = root.getJSONObject("name").getJSONArray("alsoKnownAs");
            List<String> akaList = new ArrayList<>();
            for (int x = 0; x < aka.length(); x++) {
                akaList.add(aka.getString(x));
            }
            //get place of origin
            String origin = root.getString("placeOfOrigin");
            //get description
            String description = root.getString("description");
            //get image
            String image = root.getString("image");

            //log verbose to make sure they are correct values
            Log.v("Parsing", "mainName is: " + mainName);
            Log.v("Parsing", "origin is: " + origin);
            Log.v("Parsing", "description is: " + description);
            Log.v("Parsing", "image is: " + image);

            //get ingredients
            JSONArray ingredients = root.getJSONArray("ingredients");
            List<String> ingredientList = new ArrayList<>();
            for (int x = 0; x < ingredients.length(); x++) {
                ingredientList.add(ingredients.getString(x));
            }
            //return my sandwhich
            return new Sandwich(mainName, akaList, origin, description, image, ingredientList);

        }   //else do catch stuff
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
