package com.example.jsonlabnew.converter;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.jsonlabnew.converter.annotation.Json;
import com.example.jsonlabnew.converter.annotation.Xml;

import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class JsonXmlConverterFactory extends Converter.Factory {
    final Converter.Factory xml = SimpleXmlConverterFactory.createNonStrict(new Persister(new AnnotationStrategy()));
    final Converter.Factory gson = GsonConverterFactory.create();

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(
            Type type, Annotation[] annotations, Retrofit retrofit) {

        Log.i("JsonXmlConverterFactory", "check annotations");

        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == Xml.class) {
                Log.i("JsonXmlConverterFactory", "annotation = xml");
                return xml.responseBodyConverter(type, annotations, retrofit);
            }
            if (annotation.annotationType() == Json.class) {
                Log.i("JsonXmlConverterFactory", "annotation = json");
                return gson.responseBodyConverter(type, annotations, retrofit);
            }
        }
        return null;
    }

    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        Log.i("JsonXmlConverterFactory", "check annotations");

        for (Annotation annotation : methodAnnotations) {
            if (annotation.annotationType() == Xml.class) {
                Log.i("JsonXmlConverterFactory", "annotation = xml");
                return xml.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
            }
            if (annotation.annotationType() == Json.class) {
                Log.i("JsonXmlConverterFactory", "annotation = json");
                return gson.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
            }
        }
        return null;

    }
}
