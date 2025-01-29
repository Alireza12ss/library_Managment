package com.example.library.util;

public class ResponseUtil{

    public static<T> ApiResponse<T> success(T model){
        return new ApiResponse<>(
                true,
                200,
                "success",
                model
                );
    }

    public static<T> ApiResponse<T> created(T model){
        return new ApiResponse<>(
                true,
                201,
                "created",
                model
        );
    }

    public static<T> ApiResponse<T> updated(T model){
        return new ApiResponse<>(
                true,
                204,
                "updated",
                model
        );
    }

//    public ApiResponse<T> notFound(String message){
//        return new ApiResponse<>(
//                false,
//                404,
//                message,
//                null
//        );
//    }
}
