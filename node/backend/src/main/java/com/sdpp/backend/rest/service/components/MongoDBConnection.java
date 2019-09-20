package com.sdpp.backend.rest.service.components;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sdpp.backend.rest.domain.DocumentFile;
import com.sdpp.backend.rest.util.FileUtil;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Component
public class MongoDBConnection{

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static String databaseName = "SDPP";

    public MongoDBConnection(Properties props){
        String url = String.valueOf(props.get("url"));
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        mongoClient = new MongoClient(url, MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
        database = mongoClient.getDatabase(databaseName);
        initDbWithFolderContent();
    }

    private void initDbWithFolderContent(){
        try {

            List<DocumentFile> docs = FileUtil.getSharedFolderList();
            MongoCollection<DocumentFile> collection = database.getCollection(DocumentFile.class.getCanonicalName(), DocumentFile.class);
            collection.deleteMany(new BasicDBObject());
            collection.insertMany(docs);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public <T> List<T> getAllEntities(Class<T> clazz) {
        MongoCollection<T> collection = database.getCollection(clazz.getCanonicalName(), clazz);
        List<T> docs = new ArrayList<>();
        collection.find().forEach((Consumer<? super T>) docs::add);
        return docs;
    }
}
