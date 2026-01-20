package com.college.gatepass.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse<T>{
    private boolean success;
    private T data;
    private Object error;

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> res = new ApiResponse<>();
        res.success = true;
        res.data = data;
        return res;
    }

    // ✅ CHANGE String → Object
    public static <T> ApiResponse<T> error(Object error) {
        ApiResponse<T> res = new ApiResponse<>();
        res.success = false;
        res.error = error;
        return res;
    }
}
