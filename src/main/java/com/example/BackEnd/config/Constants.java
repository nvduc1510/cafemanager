package com.example.BackEnd.config;

public class Constants {
    private Constants() {
    }

    public static final  boolean IS_CROSS_ALLOW = true;

    public static final String JWT_SECRET = "Nguyen_Van_Duc";
    public static final long JWT_EXPIRATION = 160*60*60;

    //config endpoints public
    public static final String[] ENDPOINTS_PUBLIC = new String[] {
            "/login/**",
            "/register/**",
            "/error/**",
            "/home/**",
            "/reset_password/**"
    };
    //config endpoints for user role
    public static final String[] ENDPOINTS_WITH_ROLE = new String[] {
            "/user/**",
            "/game/**",
            "/category/**",
            "/userGame/**",
            "/gamingHistory"
    };
    public static final String[] ENDPOINTS_WITH_ROLE_ADMIN = new String[] {
            "/admin/**",
    };
    public static final String[] ATTRIBUTES_TO_TOKEN = new String[] {
            "userId",
            "userFullName",
            "email",
            "userRole"
    };
    public static final String GET_ALL_SUCCESS = "Lấy ra tất cả bản ghi thành công";
    public static final String GET_ALL_FALSE = "Không có dữ liệu!";
    public static final String SUCCESS = "Thành công!";
    public static final String CREATE_FALSE = "Thêm mới thất bại!";
    public static final String CREATE_SUCCESS = "Thêm mới thành công!";
    public static final String UPDATE_SUCCESS = "Cập nhật thành công!";
    public static final String UPDATE_FAlSE = "Cập nhật thất bại!";
    public static final String GET_FALSE = "Không có dữ liệu!";
    public static final String NOT_ROLE = "Bạn không có quyền truy cập!";
    public static final String REGISTER_FALSE = "Register thất bại!";
    public static final String DELETE_SUCCESS = "Xóa thành công!";
    public static final String DELETE_FALSE = "Xóa thất bại!";

    // gửi mail
    public static final String SUBJECT_MAIL = "Xác nhận thông tin khách hàng!";
    public static final String VALID_GAME = "Không thể thích hai lần!";

    //
    public static final String VALID_EMAIL = "Email đã tồn tại!";
    public static final String INVALID_EMAIL = "Email không tồn tại!";

    //product
    public static final String VALID_PRODUCT = "Sản phẩm đã tồn tại";


    // Collections
    public static final String INVALID_COLLECTIONS = "Loại sản phẩm không tồn tại";

}
