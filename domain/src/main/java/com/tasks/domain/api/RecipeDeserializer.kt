package com.tasks.domain.api

import com.google.gson.*
import com.tasks.domain.room.Recipe
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

class RecipeDeserializer : JsonDeserializer<Recipe> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Recipe {
        val jsonObject = json.asJsonObject

        val name = jsonObject.get("name").asString
        val description = jsonObject.get("description").asString
        val ingredients = jsonObject.get("ingredients").asString

        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        return Recipe(name, description, ingredients, timestamp)
    }
}