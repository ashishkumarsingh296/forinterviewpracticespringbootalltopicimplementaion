//package com.example.forinterviewpracticespringbootalltopicimplementaion.sharding;
//
//public final class ShardContextHolder {
//    private static final ThreadLocal<String> current = new ThreadLocal<>();
//
//    private ShardContextHolder() {}
//
//    public static void setShard(String shardKey) {
//        current.set(shardKey);
//    }
//
//    public static String getShard() {
//        return current.get();
//    }
//
//    public static void clear() {
//        current.remove();
//    }
//}
