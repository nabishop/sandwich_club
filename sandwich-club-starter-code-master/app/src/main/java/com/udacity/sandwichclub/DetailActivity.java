package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        //text views
        TextView ingredients = findViewById(R.id.ingredients_tv);
        TextView description = findViewById(R.id.description_tv);
        TextView aka = findViewById(R.id.also_known_tv);
        TextView origin = findViewById(R.id.origin_tv);
        //lists
        List<String> ingredientsList = sandwich.getIngredients();
        List<String> akaList = sandwich.getAlsoKnownAs();
        //strings to put into ingredients and aka
        //separated by new line characters
        String ingreds = "";
        String akas = "";
        //copy ingredient list into string, separating each by newline character
        for (int x = 0; x < ingredientsList.size(); x++) {
            ingreds += ingredientsList.get(x);
            //add newline if not last ingredient for spacing
            if (x != ingredientsList.size() - 1) {
                ingreds += "\n";
            }
        }
        //copy "also known as" list into string
        for (int i = 0; i < akaList.size(); i++) {
            akas += akaList.get(i);
            if (i != akaList.size() - 1) {
                akas += "\n";
            }
        }
        //set texts
        description.setText(filler(sandwich.getDescription()));
        origin.setText(filler(sandwich.getPlaceOfOrigin()));
        aka.setText(filler(akas));
        ingredients.setText(filler(ingreds));

    }

    private String filler(String txt) {
        if (txt.isEmpty()) {
            return "Not Provided";
        } else {
            return txt;
        }
    }
}
