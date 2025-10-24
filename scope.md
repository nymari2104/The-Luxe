# Kế hoạch và Phạm vi dự án "TheLuxe" - Trợ lý Thời trang AI

## 1. Tầm nhìn dự án

**TheLuxe** không chỉ là một ứng dụng thương mại điện tử bán đồ thời trang xa xỉ, mà là một **trợ lý phong cách cá nhân** sử dụng trí tuệ nhân tạo (AI). Mục tiêu là mang đến trải nghiệm mua sắm độc đáo, được cá nhân hóa sâu sắc, giúp người dùng tìm thấy những sản phẩm không chỉ phù hợp với vóc dáng mà còn cả phong cách và cá tính riêng của họ.

Đối thủ cạnh tranh: FARFETCH, Net-a-Porter, SSENSE.
Điểm khác biệt chính: Hệ thống đề xuất thông minh dựa trên dữ liệu chi tiết của người dùng (số đo, gu thẩm mỹ) để đưa ra gợi ý như một stylist chuyên nghiệp.

---

## 2. Các tính năng chính (Features Scope)

### Giai đoạn 1: MVP (Sản phẩm khả dụng tối thiểu)

Mục tiêu của giai đoạn này là ra mắt một ứng dụng thương mại điện tử hoạt động tốt với các chức năng cốt lõi.

| Tính năng | Mô tả |
| :--- | :--- |
| **Đăng ký / Đăng nhập** | - Đăng ký bằng Email/Mật khẩu.<br>- Đăng nhập an toàn.<br>- (Tùy chọn) Đăng nhập bằng Google/Facebook. |
| **Hồ sơ người dùng** | - Quản lý thông tin cá nhân cơ bản: Tên, địa chỉ giao hàng, số điện thoại.<br>- **Nền tảng cho AI**: Thêm các trường: tuổi, chiều cao, cân nặng. |
| **Trình duyệt sản phẩm** | - Hiển thị sản phẩm theo danh mục (Quần áo, Giày, Túi xách, Phụ kiện).<br>- Bộ lọc cơ bản: Thương hiệu, Kích cỡ, Màu sắc, Giá.<br>- Chức năng tìm kiếm theo tên sản phẩm. |
| **Trang chi tiết sản phẩm** | - Hiển thị nhiều hình ảnh chất lượng cao.<br>- Mô tả chi tiết, chất liệu, hướng dẫn kích cỡ.<br>- Giá, các tùy chọn (size, màu).<br>- Nút "Thêm vào giỏ hàng". |
| **Giỏ hàng** | - Xem lại các sản phẩm đã chọn.<br>- Thay đổi số lượng, xóa sản phẩm.<br>- Hiển thị tổng giá trị đơn hàng. |
| **Thanh toán với Stripe** | - Quy trình thanh toán an toàn, tích hợp Stripe SDK.<br>- Nhập thông tin giao hàng và thông tin thẻ.<br>- Xác nhận và hoàn tất đơn hàng. |
| **Lịch sử đơn hàng** | - Xem danh sách các đơn hàng đã đặt.<br>- Xem chi tiết từng đơn hàng. |

### Giai đoạn 2: Tích hợp AI và Cá nhân hóa

Sau khi MVP hoạt động ổn định, chúng ta sẽ tập trung vào tính năng tạo nên sự khác biệt.

| Tính năng | Mô tả |
| :--- | :--- |
| **Hồ sơ người dùng nâng cao** | - Thêm các trường dữ liệu cho AI: "Gu thời trang của bạn" (chọn từ các style có sẵn: Cổ điển, Tối giản, Đường phố...), "Số đo cơ thể" (tùy chọn). |
| **Engine Đề xuất AI v1** | - **Thu thập dữ liệu**: Lịch sử xem sản phẩm, sản phẩm đã thích, sản phẩm đã mua và thông tin trong hồ sơ.<br>- **Xử lý**: AI phân tích dữ liệu để hiểu "gu" của người dùng.<br>- **Đề xuất**: Hiển thị một mục riêng "Gợi ý cho bạn" trên trang chủ với các sản phẩm phù hợp. |
| **Danh sách yêu thích (Wishlist)** | - Người dùng có thể lưu lại các sản phẩm họ quan tâm.<br>- Dữ liệu này là đầu vào quan trọng cho Engine AI. |
| **Hệ thống đánh giá sản phẩm** | - Người dùng có thể để lại đánh giá và xếp hạng sao cho sản phẩm đã mua. |

### Giai đoạn 3: Hoàn thiện và Mở rộng

| Tính năng | Mô tả |
| :--- | :--- |
| **Engine Đề xuất AI v2** | - **Phối đồ thông minh**: AI không chỉ gợi ý sản phẩm mà còn gợi ý cách phối chúng thành một bộ trang phục hoàn chỉnh.<br>- **Tìm kiếm bằng hình ảnh**: Cho phép người dùng tải lên hình ảnh để tìm các sản phẩm tương tự. |
| **Thông báo đẩy (Push Noti)**| - Gửi thông báo về các sản phẩm mới phù hợp với gu người dùng.<br>- Thông báo về chương trình giảm giá, đơn hàng. |
| **Trang quản trị (Admin Panel)** | - Xây dựng giao diện web cho admin để quản lý sản phẩm, đơn hàng, người dùng một cách hiệu quả.<br>- **Công nghệ**: Spring Boot, Thymeleaf, Spring Security. |

### Giai đoạn 4: Cải tiến và Hoàn thiện

| Tính năng | Mô tả |
| :--- | :--- |
| **Lịch sử mua hàng** | - Cho phép người dùng xem danh sách các đơn hàng đã đặt, bao gồm trạng thái và tổng giá trị. |
| **Cải thiện UI trang Profile** | - Thiết kế lại trang hồ sơ người dùng để có giao diện hiện đại, thân thiện và sắp xếp thông tin hợp lý hơn. |
| **Chat Real-time với Admin** | - Xây dựng tính năng chat thời gian thực để người dùng có thể liên hệ trực tiếp với người quản trị.<br>- **Công nghệ**: Firebase Realtime Database hoặc Firestore. |
| **Thông tin Liên hệ** | - Tạo một màn hình mới để hiển thị thông tin cửa hàng: Số điện thoại, Địa chỉ và tích hợp bản đồ. |

---

## 3. Kế hoạch triển khai (Game Plan)

Đây là lộ trình từng bước để biến ý tưởng thành sản phẩm thực tế.

### Bước 1: Chuẩn bị và Thiết kế (Tuần 1-2)

1.  **Công việc**:
    *   **Thiết lập môi trường**: Cài đặt Android Studio, Git, và các công cụ cần thiết.
    *   **Thiết kế UI/UX**: Vẽ wireframe và mockup cho tất cả các màn hình trong Giai đoạn 1. Giao diện cần sang trọng, tối giản, phù hợp với sản phẩm luxury.
    *   **Thiết kế kiến trúc hệ thống**:
        *   **App Android (Java)**: Quyết định kiến trúc (ví dụ: MVVM - Model-View-ViewModel).
        *   **Backend**: Thiết kế các API endpoints cần thiết (RESTful API).
        *   **Cơ sở dữ liệu**: Thiết kế schema cho các bảng (Users, Products, Orders, etc.).

### Bước 2: Xây dựng Backend cho MVP (Tuần 3-6)

1.  **Công nghệ**: Spring Boot (Java), PostgreSQL/MySQL (Database), Dịch vụ Cloud (AWS, Google Cloud).
2.  **Công việc**:
    *   Xây dựng API cho Đăng ký/Đăng nhập (sử dụng JWT để bảo mật).
    *   Xây dựng API CRUD (Tạo, Đọc, Cập nhật, Xóa) cho Sản phẩm.
    *   Xây dựng API cho Giỏ hàng và Đơn hàng.
    *   Tích hợp Stripe API phía backend để xử lý thanh toán.

### Bước 3: Xây dựng App Android cho MVP (Tuần 5-10)

1.  **Công nghệ**: Java, Android SDK, Retrofit (để gọi API), Glide/Picasso (để tải ảnh).
2.  **Công việc**:
    *   Xây dựng giao diện người dùng từ thiết kế.
    *   Kết nối ứng dụng với Backend API.
    *   Triển khai luồng Đăng ký, Đăng nhập, Hiển thị sản phẩm.
    *   Triển khai chức năng Giỏ hàng.
    *   Tích hợp Stripe Android SDK cho quy trình thanh toán.
    *   Hiển thị lịch sử đơn hàng.

### Bước 4: Kiểm thử và Ra mắt MVP (Tuần 11-12)

1.  **Công việc**:
    *   Viết Unit Test và Instrumentation Test.
    *   Kiểm thử toàn bộ luồng hoạt động trên nhiều thiết bị khác nhau.
    *   Sửa lỗi.
    *   Triển khai Backend lên cloud.
    *   Đăng tải ứng dụng lên Google Play Store.

### Bước 5: Phát triển Giai đoạn 2 & 3 (Sau khi ra mắt MVP)

1.  **Xây dựng Engine AI**:
    *   **Công nghệ**: Python, Scikit-learn, TensorFlow/PyTorch.
    *   **Công việc**: Đây là một dự án con. Bắt đầu bằng việc xây dựng một mô hình đề xuất đơn giản (ví dụ: Collaborative Filtering) dựa trên dữ liệu thu thập được từ MVP. Triển khai mô hình này như một service riêng và cung cấp API cho backend chính.
2.  **Tích hợp vào App và Backend**:
    *   Nâng cấp App Android và Backend để hỗ trợ các tính năng của Giai đoạn 2 và 3.
    *   Lặp lại chu trình: **Phát triển -> Kiểm thử -> Triển khai** cho mỗi tính năng mới.

---

## 4. Kết luận

Bằng cách chia dự án thành các giai đoạn rõ ràng, chúng ta có thể tập trung nguồn lực, giảm thiểu rủi ro và sớm đưa sản phẩm ra thị trường (với MVP). Thành công của TheLuxe sẽ phụ thuộc vào việc thực thi xuất sắc các tính năng e-commerce cơ bản và chất lượng của Engine AI - yếu tố tạo nên sự khác biệt cốt lõi.
