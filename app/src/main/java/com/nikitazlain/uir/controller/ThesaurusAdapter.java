package com.nikitazlain.uir.controller;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.nikitazlain.uir.entity.ThesaurusEntity;
import com.nikitazlain.uir.entity.container.AbstractContainer;

import java.lang.reflect.Type;

/**
 * Created by nikitazlain on 30.05.17.
 */

public class ThesaurusAdapter implements JsonDeserializer<AbstractContainer<ThesaurusEntity>> {
    @Override
    public AbstractContainer<ThesaurusEntity> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }
}
