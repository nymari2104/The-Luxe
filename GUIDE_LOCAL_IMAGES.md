# 📸 Hướng dẫn sử dụng Local Images trong TheLuxe

## 🎯 **CÁCH THÊM ẢNH LOCAL**

### **Bước 1: Chuẩn bị ảnh**

#### **Quy tắc đặt tên:**
```
✅ ĐÚNG:
- product_tshirt.jpg
- product_jeans_blue.jpg  
- product_sneakers_01.jpg
- img_leather_jacket.jpg

❌ SAI:
- Product T-Shirt.jpg        (có space, có chữ hoa)
- áo-thun.jpg                 (có dấu)
- product#1.jpg               (có ký tự đặc biệt)
- 123product.jpg              (bắt đầu bằng số)
```

#### **Kích thước khuyến nghị:**
```
Width: 800-1200px
Height: 1000-1500px (tỷ lệ 4:5 hoặc 2:3)
Format: JPG (cho photos), PNG (nếu cần transparent)
Quality: 80-90%
File size: < 500KB per image
```

#### **Tools resize ảnh:**
- **Online:** tinypng.com, squoosh.app
- **Desktop:** Paint, GIMP, Photoshop
- **Batch:** IrfanView (Windows)

---

### **Bước 2: Copy vào Drawable**

#### **Option A: Trong Android Studio**
```
1. Mở Android Studio
2. Project view → app → src → main → res → drawable
3. Chuột phải → Show in Explorer
4. Copy ảnh vào folder này
5. Quay lại Android Studio → File → Sync Project with Gradle Files
```

#### **Option B: Trực tiếp**
```
Paste ảnh vào:
C:\Users\nguye\AndroidStudioProjects\The-Luxe-1\app\src\main\res\drawable\

Hoặc dùng command:
copy "C:\Downloads\your_image.jpg" "C:\Users\nguye\AndroidStudioProjects\The-Luxe-1\app\src\main\res\drawable\product_1.jpg"
```

---

### **Bước 3: Update ProductRepository**

**File:** `app/src/main/java/com/example/theluxe/repository/ProductRepository.java`

**CÁCH 1: Dùng Resource ID (Recommended)**

```java
// Trong constructor:
productList.add(new Product(
    "1", 
    "Classic White T-Shirt", 
    "Essentials", 
    "Premium cotton t-shirt with a timeless design.", 
    499000, 
    "R.drawable.product_tshirt",  // ← Tên file trong drawable
    "Classic", 
    "outfit1"
));
```

**CÁCH 2: Dùng URI String**

```java
productList.add(new Product(
    "1", 
    "Classic White T-Shirt", 
    "Essentials", 
    "Premium cotton t-shirt with a timeless design.", 
    499000, 
    "android.resource://com.example.theluxe/drawable/product_tshirt",
    "Classic", 
    "outfit1"
));
```

**CÁCH 3: Keep URL format**

```java
productList.add(new Product(
    "1", 
    "Classic White T-Shirt", 
    "Essentials", 
    "Premium cotton t-shirt with a timeless design.", 
    499000, 
    "drawable://product_tshirt",  // Custom prefix
    "Classic", 
    "outfit1"
));
```

---

### **Bước 4: Update Glide Loading Logic**

#### **ProductAdapter.java - Update để support local images:**

```java
// Find the Glide loading section (around line 62-68)

// TRƯỚC:
Glide.with(holder.itemView.getContext())
    .load(product.getImageUrl())
    .placeholder(R.drawable.placeholder_product)
    .error(R.drawable.error_image)
    .centerCrop()
    .diskCacheStrategy(DiskCacheStrategy.ALL)
    .into(holder.imageViewProduct);

// SAU (support both online & local):
String imageUrl = product.getImageUrl();
if (imageUrl.startsWith("drawable://")) {
    // Local drawable
    String drawableName = imageUrl.replace("drawable://", "");
    int resourceId = holder.itemView.getContext().getResources()
        .getIdentifier(drawableName, "drawable", holder.itemView.getContext().getPackageName());
    
    Glide.with(holder.itemView.getContext())
        .load(resourceId)
        .placeholder(R.drawable.placeholder_product)
        .error(R.drawable.error_image)
        .centerCrop()
        .into(holder.imageViewProduct);
} else {
    // Online URL
    Glide.with(holder.itemView.getContext())
        .load(imageUrl)
        .placeholder(R.drawable.placeholder_product)
        .error(R.drawable.error_image)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(holder.imageViewProduct);
}
```

#### **Hoặc tạo Helper Method:**

```java
// Add method to ProductAdapter class:

private void loadProductImage(ImageView imageView, String imageUrl) {
    Context context = imageView.getContext();
    
    if (imageUrl.startsWith("drawable://")) {
        // Load from drawable
        String drawableName = imageUrl.replace("drawable://", "");
        int resourceId = context.getResources()
            .getIdentifier(drawableName, "drawable", context.getPackageName());
        
        Glide.with(context)
            .load(resourceId > 0 ? resourceId : R.drawable.error_image)
            .placeholder(R.drawable.placeholder_product)
            .centerCrop()
            .into(imageView);
    } else if (imageUrl.startsWith("http")) {
        // Load from URL
        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.placeholder_product)
            .error(R.drawable.error_image)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView);
    } else {
        // Invalid URL
        imageView.setImageResource(R.drawable.error_image);
    }
}

// Then use it:
loadProductImage(holder.imageViewProduct, product.getImageUrl());
```

---

### **Bước 5: Update các Adapters khác**

**Cần update cùng logic ở:**

1. **CartAdapter.java** (line ~66-72)
2. **FeaturedProductAdapter.java** (line ~42-48)
3. **ProductDetailActivity.java** (line ~65-71)

**Template để replace:**

```java
// OLD:
Glide.with(context)
    .load(product.getImageUrl())
    ...
    .into(imageView);

// NEW:
loadProductImage(imageView, product.getImageUrl());
```

---

## 📦 **VÍ DỤ ĐẦY ĐỦ**

### **Scenario: Thêm 3 ảnh products**

**Step 1: Chuẩn bị ảnh**
```
Files trên Desktop:
- IMG_1234.jpg → Rename to: product_tshirt_white.jpg
- photo-002.jpg → Rename to: product_jeans_blue.jpg  
- DSC_5678.jpg → Rename to: product_sneakers_white.jpg
```

**Step 2: Resize (nếu cần)**
```
Dùng tinypng.com:
- Upload 3 files
- Download compressed versions
```

**Step 3: Copy vào drawable**
```powershell
copy "C:\Users\nguye\Desktop\product_tshirt_white.jpg" "C:\Users\nguye\AndroidStudioProjects\The-Luxe-1\app\src\main\res\drawable\"

copy "C:\Users\nguye\Desktop\product_jeans_blue.jpg" "C:\Users\nguye\AndroidStudioProjects\The-Luxe-1\app\src\main\res\drawable\"

copy "C:\Users\nguye\Desktop\product_sneakers_white.jpg" "C:\Users\nguye\AndroidStudioProjects\The-Luxe-1\app\src\main\res\drawable\"
```

**Step 4: Update ProductRepository.java**
```java
// Product 1
productList.add(new Product("1", "Classic White T-Shirt", "Essentials", 
    "Premium cotton t-shirt with a timeless design.", 
    499000, 
    "drawable://product_tshirt_white",
    "Classic", "outfit1"));

// Product 6
productList.add(new Product("6", "Classic Blue Jeans", "Denim Co", 
    "Classic fit jeans made from premium denim.", 
    1299000, 
    "drawable://product_jeans_blue",
    "Classic", "outfit1"));

// Product 7
productList.add(new Product("7", "White Leather Sneakers", "Urban Steps", 
    "Clean white sneakers with premium leather finish.", 
    999000, 
    "drawable://product_sneakers_white",
    "Classic", "outfit1"));
```

**Step 5: Sync & Rebuild**
```
File → Sync Project with Gradle Files
Build → Rebuild Project
Run app
```

---

## ⚠️ **LƯU Ý QUAN TRỌNG**

### **1. File Size Impact**
```
10 images × 500KB = 5MB added to APK
20 images × 500KB = 10MB added to APK

→ APK sẽ lớn hơn!
→ Download time tăng
→ Storage trên điện thoại tăng
```

**Solution:**
- Compress images trước khi add
- Dùng JPG thay vì PNG (nhỏ hơn)
- Consider online hosting cho production

### **2. No Updates After Release**
```
❌ User đã install app → Không thể change ảnh
✅ Online URLs → Có thể change bất cứ lúc nào
```

### **3. Build Time**
```
Many images = Longer build time
```

---

## 🔄 **MIX ONLINE & LOCAL**

**Bạn có thể mix cả 2 approaches:**

```java
// Some products from Unsplash (online)
productList.add(new Product("1", "T-Shirt", "Brand", "Desc", 
    499000, 
    "https://images.unsplash.com/photo-123?w=800&q=80",
    "Classic", "outfit1"));

// Some products from drawable (local)
productList.add(new Product("2", "Jeans", "Brand", "Desc", 
    1299000, 
    "drawable://product_jeans",
    "Classic", "outfit1"));

// Glide helper sẽ tự động detect và load đúng
```

---

## 📊 **SO SÁNH: LOCAL vs ONLINE**

| Aspect | Local (Drawable) | Online (URL) |
|--------|------------------|--------------|
| **Setup** | Easy - just copy files | Need hosting |
| **Speed** | Instant | Depends on network |
| **Offline** | ✅ Always work | ❌ Need cache first |
| **APK Size** | ⬆️ Increases | ✅ No impact |
| **Updates** | ❌ Need new release | ✅ Instant |
| **Scalability** | ❌ Limited | ✅ Unlimited |
| **Cost** | FREE | $0-$50/month |

---

## 🎯 **RECOMMENDATION**

### **For MVP/Demo:**
```
✅ Use LOCAL images
   - Professional look
   - Works offline
   - No hosting needed
   - Good for 10-20 products
```

### **For Production:**
```
✅ Use ONLINE images (Cloudinary/S3)
   - Unlimited products
   - Can update anytime
   - Smaller APK
   - Better for scaling
```

---

## 🚀 **QUICK START COMMAND**

```powershell
# Mở drawable folder
explorer "C:\Users\nguye\AndroidStudioProjects\The-Luxe-1\app\src\main\res\drawable"

# Copy ảnh từ Desktop (example)
copy "C:\Users\nguye\Desktop\*.jpg" "C:\Users\nguye\AndroidStudioProjects\The-Luxe-1\app\src\main\res\drawable\"

# Sync project
# In Android Studio: File → Sync Project with Gradle Files
```

---

## ❓ **TROUBLESHOOTING**

### **Q: Ảnh không hiện sau khi copy?**
```
1. Check file name: phải lowercase, no spaces
2. Sync project: File → Sync Project
3. Clean build: Build → Clean Project
4. Rebuild: Build → Rebuild Project
```

### **Q: App crash khi load ảnh local?**
```
Check:
1. File name đúng chưa?
2. Resource ID tìm được không?
3. Log error: adb logcat | grep "Glide"
```

### **Q: Ảnh bị pixelated/blurry?**
```
Solution:
1. Resize ảnh lên 1000px width trở lên
2. Dùng scaleType="centerCrop"
3. Check image quality khi resize
```

---

**Version:** 1.0  
**Last Updated:** October 26, 2025

