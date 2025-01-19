# springmvcPreExer

## 專案概述

springmvcPreExer 是一個基於 Java 的學習專案，旨在實踐 Spring MVC 和 SSM 概念與架構，專案模擬了 DispatcherServlet 的請求分發功能，並使用 OpenSessionViewFilter 和 TransactionManager / ThreadLocal 進行交易管理。此外，通過 ContextLoaderListener 加載 IOC 容器，實現自定義 SSM 功能，而畫面使用 Thymeleaf 渲染，實現了一個簡易的會員管理系統。

## 設定檔

- `applicationContext.xml`： 管理 bean。
- `jdbc.properties`： 通用 JDBC 配置。

## 需求

- JDK 版本： 11+
- MySQL 版本： 8
- Maven 版本： 3.9.9
- Tomcat 9.0.83

## 使用方式

1. **安裝依賴：** 確保已安裝 JDK 和相關資料庫，並設定相關依賴。
2. **配置資料庫：** 修改 `jdbc.properties` 等設定檔中的參數，以匹配您的資料庫設定。
3. **導入專案：** 使用 IDE（如 IntelliJ IDEA 或 Eclipse）導入專案。
4. **執行測試：** 選擇並運行對應的測試類進行學習。

## 注意事項

- 確保資料庫服務已啟動，並正確設定用戶名與密碼。
- 在執行涉及資源的操作時，請檢查相關文件路徑是否有效。

## 主要學習資源

- Servlet & JSP技術手冊 : 邁向Spring Boot 作者：林信良。
- 【尚硅谷】2022版JavaWeb教學(全新技術棧,全程實戰)。

## 貢獻者

此專案僅為學習用途。
