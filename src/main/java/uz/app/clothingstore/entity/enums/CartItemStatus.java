package uz.app.clothingstore.entity.enums;

public enum CartItemStatus {
    ACTIVE,      // Savatchada hali xarid qilinmagan, faol holat
    RESERVED,    // Foydalanuvchi buyurtma qilishni boshladi, lekin hali to‘lanmadi
    PURCHASED,   // Xarid qilingan va to‘lov amalga oshirilgan
    CANCELLED,   // Foydalanuvchi buyurtmani bekor qilgan
    OUT_OF_STOCK // Mahsulot zaxirada yo‘q, shuning uchun savatchada kutish holati
}
