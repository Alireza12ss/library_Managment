package com.example.library.util;

import com.example.library.dto.ResultDto;

public class ResponseUtil{

    public static<T> ResultDto<T> success(T model){
        return new ResultDto<>(
                true,
                200,
                "success",
                model
                );
    }

    public static<T> ResultDto<T> created(T model){
        return new ResultDto<>(
                true,
                201,
                "created",
                model
        );
    }

    public static<T> ResultDto<T> updated(T model){
        return new ResultDto<>(
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
