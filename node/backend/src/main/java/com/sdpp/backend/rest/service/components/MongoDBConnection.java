package com.sdpp.backend.rest.service.components;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sdpp.backend.rest.domain.DocumentFile;
import com.sdpp.backend.rest.util.FileUtil;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Component
public class MongoDBConnection{

    private static final Logger logger = LoggerFactory.getLogger(MongoDBConnection.class);
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static String databaseName = "SDPP";
    private static final String PREF = "";
    private static final String DOT = ".";

    public MongoDBConnection(Properties props){
        String url = String.valueOf(props.get("url"));
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        mongoClient = new MongoClient(url, MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
        database = mongoClient.getDatabase(databaseName);
        initDbWithFolderContent();
    }

    void initDbWithFolderContent(){
        try {
            List<DocumentFile> docs = FileUtil.getSharedFolderList();
            MongoCollection<DocumentFile> collection = database.getCollection(DocumentFile.class.getCanonicalName(), DocumentFile.class);
            collection.deleteMany(new BasicDBObject());
            docs.forEach(collection::insertOne);
        } catch (IOException e) {
            logger.error("Error while initializing DB w folder content",e);
        }

    }

    public <T> List<T> getAllEntities(Class<T> clazz) {
        MongoCollection<T> collection = database.getCollection(clazz.getCanonicalName(), clazz);
        FindIterable<T> its = collection.find();
        return its.into(new ArrayList<T>());
    }

    public <T> T getEntity(Class<T> clazz, T toFind){
        MongoCollection<T> collection = database.getCollection(clazz.getCanonicalName(), clazz);
        Document bson = anyObjectToBSONForQuery(PREF, toFind);
        FindIterable<T> its = collection.find(bson);
        return its.first();
    }


    <T> void insertEntity(Class<T> clazz, T doc) {
        MongoCollection<T> collection = database.getCollection(clazz.getCanonicalName(), clazz);
        collection.insertOne(doc);
    }

    <T> void removeEntity(Class<T> clazz, T doc) {
        MongoCollection<T> collection = database.getCollection(clazz.getCanonicalName(), clazz);
        Document bson = anyObjectToBSONForQuery(PREF, doc);
        collection.findOneAndDelete(bson);
    }


    private <T> Document anyObjectToBSONForQuery(String prex, T toFind) {
        Document bson = new Document();
        try {
            Class<?> clazz = toFind.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object fieldObject = field.get(toFind);
                String name = field.getName();
                if(fieldObject != null){
                    if(isJavaClass(fieldObject.getClass())){
                        bson.append(prex.concat(name), fieldObject);
                    }else{
                        Document doc = anyObjectToBSONForQuery(name.concat(DOT),fieldObject);
                        for (Map.Entry<String, Object> kv : doc.entrySet()) {
                            bson.append(kv.getKey(),kv.getValue());
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            logger.info("Error while creating query from BSON",e);
        }
        return bson;
    }

    private boolean isJavaClass(Class<?> c){
        return c.getCanonicalName().startsWith("java") | c.isPrimitive();
    }

}
