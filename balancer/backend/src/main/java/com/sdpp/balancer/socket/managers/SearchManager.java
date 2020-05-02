package com.sdpp.balancer.socket.managers;

import com.sdpp.balancer.rest.domain.DocumentFile;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class SearchManager implements Callable<List<DocumentFile>> {


    private final List<DocumentFile> documents;
    private final LinkedHashMap<String, String> params;
    private List<DocumentFile> filteredDocuments;


    public SearchManager(List<DocumentFile> documents, LinkedHashMap<String,String> params){

        this.params = params;
        this.documents = documents;
        this.filteredDocuments = new ArrayList<>();
    }

    @Override
    public List<DocumentFile> call() throws Exception {

        filteredDocuments = documents.stream()
                .filter(d -> params.get("name") == null || d.getName().contains(params.get("name")))
                .filter(d -> params.get("author") == null || d.getMeta().getAuthor().contains(params.get("author")))
                .filter(d -> {
                    if(params.get("sizeFiter") == null) return true;
                    int size = Integer.parseInt(params.get("size"));
                    if(params.get("sizeFiter").equalsIgnoreCase("minor")){
                        return d.getMeta().getSize() < size;
                    } else {
                        return d.getMeta().getSize() > size;
                    }
                })
                .collect(Collectors.toList());

        return filteredDocuments;
    }
}
