# Kế hoạch Cải thiện UX/UI cho TheLuxe

## 1. Tầm nhìn & Mục tiêu

Mục tiêu là chuyển đổi giao diện của TheLuxe từ một ứng dụng chỉ tập trung vào chức năng sang một trải nghiệm người dùng sang trọng, tinh tế và hiện đại, xứng tầm với một thương hiệu thời trang cao cấp. Giao diện sẽ lấy cảm hứng từ các đối thủ như FARFETCH, tập trung vào không gian trắng, hình ảnh chất lượng cao và sự tối giản.

---

## 2. Giai đoạn 1: Xây dựng Nền tảng Phong cách (Style Foundation)

Đây là bước nền tảng, định hình cảm nhận chung cho toàn bộ ứng dụng.

| Nhiệm vụ | Mô tả chi tiết | File ảnh hưởng |
| :--- | :--- | :--- |
| **1. Thiết lập Bảng màu** | Định nghĩa một bảng màu tinh tế: Nền (off-white), Chữ chính (near-black), Chữ phụ (grey), và một màu Nhấn (subtle gold). | `app/src/main/res/values/colors.xml` |
| **2. Thiết lập Phông chữ** | Tích hợp Google Fonts:<br>- **Playfair Display (Serif)** cho tiêu đề lớn.<br>- **Montserrat (Sans-serif)** cho văn bản thông thường. | `app/src/main/res/font/`, `app/src/main/res/values/themes.xml` |
| **3. Cập nhật Theme chính** | Áp dụng màu sắc và phông chữ mới vào theme chính của ứng dụng để đảm bảo tính nhất quán. | `app/src/main/res/values/themes.xml` |

---

## 3. Giai đoạn 2: Thiết kế lại các Thành phần và Màn hình chính

Áp dụng nền tảng phong cách vào các màn hình quan trọng.

| Nhiệm vụ | Mô tả chi tiết | File ảnh hưởng |
| :--- | :--- | :--- |
| **1. Màn hình Đăng nhập/Đăng ký** | - Thêm logo (placeholder).<br>- Tùy chỉnh `MaterialButton` theo phong cách mới.<br>- Tăng không gian trắng. | `activity_login.xml`, `activity_register.xml` |
| **2. Danh sách Sản phẩm** | - **Thay đổi bố cục:** Chuyển sang `GridLayoutManager` (2 cột).<br>- **Thiết kế lại Thẻ Sản phẩm:**<br>  - Sử dụng `CardView` để tạo chiều sâu.<br>  - Hình ảnh sản phẩm là trung tâm.<br>  - Thêm biểu tượng Wishlist trực tiếp trên ảnh. | `fragment_product_list.xml`, `item_product.xml` |
| **3. Chi tiết Sản phẩm** | - Tăng kích thước hình ảnh sản phẩm.<br>- Sắp xếp lại thông tin văn bản một cách hợp lý.<br>- Tùy chỉnh lại các nút bấm. | `activity_product_detail.xml` |
| **4. Thanh điều hướng dưới cùng** | - Thay thế các icon mặc định bằng các icon tùy chỉnh, tinh tế hơn. | `bottom_nav_menu.xml`, `drawable/` |

---

## 4. Giai đoạn 3: Tinh chỉnh và Hoàn thiện

Các bước cuối cùng để nâng cao trải nghiệm.

| Nhiệm vụ | Mô tả chi tiết |
| :--- | :--- |
| **1. Thêm Hiệu ứng chuyển động** | Thêm các hiệu ứng chuyển động tinh tế (subtle animations) khi chuyển đổi giữa các màn hình hoặc khi các phần tử xuất hiện. |
| **2. Tối ưu hóa cho các kích thước màn hình** | Đảm bảo giao diện hiển thị tốt trên nhiều loại thiết bị, từ điện thoại nhỏ đến máy tính bảng. |

