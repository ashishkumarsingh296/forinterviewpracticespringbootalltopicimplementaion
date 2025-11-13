package com.example.forinterviewpracticespringbootalltopicimplementaion.constants;

public final class ApiPathConstants {

    private ApiPathConstants() {}

    // Base paths
    public static final String BASE_PATH = "/api";
    // User-specific mappings
    public static final String CREATE_USER = "/createUsers";
    public static final String GET_ALL_USERS = "/allUsers";
    public static final String USER_BY_ID = "/usersBy/{id}";
    public static final String UPDATE_USER = "updateUser";
    public static final String DELETE_USER = "/deleteUser/{id}";

    // Product-specific mappings
    public static final String CREATE_PRODUCT = "/createProducts";
    public static final String GET_ALL_PRODUCTS = "/allProducts";
    public static final String PRODUCT_BY_ID = "/productBy/{id}";
    public static final String UPDATE_PRODUCT = "updateProduct";
    public static final String DELETE_PRODUCT = "/deleteProduct/{id}";
}
