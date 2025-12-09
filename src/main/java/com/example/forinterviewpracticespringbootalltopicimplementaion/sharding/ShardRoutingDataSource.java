//package com.example.forinterviewpracticespringbootalltopicimplementaion.sharding;
//
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//
//public class ShardRoutingDataSource extends AbstractRoutingDataSource {
//    @Override
//    protected Object determineCurrentLookupKey() {
//        String key = ShardContextHolder.getShard();
//        // fallback to default shard if null
//        return (key != null) ? key : "shard0";
//    }
//}
