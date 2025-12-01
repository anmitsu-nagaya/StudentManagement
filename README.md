# 受講生管理システム

## 目次
1. [サービス概要](#サービス概要)
2. [主な使用技術](#主な使用技術)
3. [機能一覧](#機能一覧)
4. [使用イメージ](#使用イメージ)
5. [設計書](#設計書)
6. [テスト](#テスト)
7. [力をいれたところ](#力をいれたところ)
8. [今後の展望](#今後の展望)
 
## サービス概要

このプロジェクトは、IT技術を教える学校が受講生の情報を保持・分析するための管理システムです。  
学校運営者が使用することを想定しており、CRUD操作中心のシンプルで使いやすい設計を目指しています。

## 主な使用技術

### バックエンド
![badge](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=ED8B00)
![badge](https://img.shields.io/badge/SpringBoot-3.5.6-%236DB33F?logo=spring)
![badge](https://img.shields.io/badge/Gradle-8.14.3-02303A?logo=gradle&logoColor=white)

### フロントエンド
![badge](https://shields.io/badge/TypeScript-5.9.3-3178C6?logo=TypeScript&logoColor=3178C6)
![badge](https://img.shields.io/badge/React-19.2.0-61DAFB?logo=react&logoColor=#61DAFB)
![badge](https://img.shields.io/badge/vite-7.2.2-646CFF?logo=vite&logoColor=#646CFF)


### データベース・O/Rマッパー
![badge](https://img.shields.io/badge/MySQL-%234479A1?logo=mysql&logoColor=white)
![badge](https://img.shields.io/badge/MyBatis-%23DC382D?logoColor=white)
![badge](https://img.shields.io/badge/H2%20Database-09476B?logo=h2database&logoColor=white)


### 使用ツール
![badge](https://img.shields.io/badge/Junit5-%2325A162?logo=junit5&logoColor=white)
![badge](https://img.shields.io/badge/Postman-%23FF6C37?logo=postman&logoColor=white)
![badge](https://img.shields.io/badge/Swagger-%2385EA2D?logo=swagger&logoColor=white)
![badge](https://img.shields.io/badge/ESLint-3A33D1?logo=eslint)
![badge](https://img.shields.io/badge/Prettier-F7B93E?style=flat&logo=Prettier&logoColor=white)
![badge](https://img.shields.io/badge/npm-CB3837?logo=npm&logoColor=white)
![badge](https://img.shields.io/badge/Git-F05032?logo=git&logoColor=white)
![badge](https://img.shields.io/badge/GitHub-%23181717?logo=github&logoColor=white)
![badge](https://img.shields.io/badge/-IntelliJ%20IDEA-000.svg?logo=intellij-idea&style=flat)
![badge](https://img.shields.io/badge/VS%20Code-007ACC?logo=visualstudiocode&logoColor=fff)


## 機能一覧

| 機能       | 詳細                            |
|:---------|:----------------------------------------------------------------|
| 受講生詳細の条件検索 | 氏名・コース・申込状況など複数テーブルを跨ぐ検索条件を指定し、条件に該当する**受講生詳細**を取得します<br/>※リクエストはクエリパラメータで個別フィールドを指定     |      |
| 受講生詳細の新規登録 | 氏名や居住地域などの**受講生**の情報と、**受講コース**・**申込状況** をセットで登録します<br/>※リクエストには**登録用受講生詳細**を使用          |
| 受講生詳細の更新 | IDを指定し、任意の**受講生詳細**を更新します<br/>※リクエストには**更新用受講生詳細**を使用<br/>※削除処理については論理削除として実装しているため、更新処理として行います |

※ 言葉の定義は以下のとおりです

- **受講生**：氏名、居住地域、年齢などをもつオブジェクト
- **受講コース**：受講コース名をもつオブジェクト（受講生に対して1：多の関係）
- **申込状況** ：仮申込、本申込といった申込状況、開始日などをもつオブジェクト
- **受講生詳細**：上記3つを統合したオブジェクト <br/>※DBでは正規化されて3テーブルに分離していますが、APIでは統合した形で扱います
- **登録用受講生詳細**：新規登録時のリクエストボディ
- **更新用受講生詳細**：更新時のリクエストボディ

## 使用イメージ
### 受講生一覧画面（赤字：ボタン押下による実行内容）
<img width="1320" height="598" alt="一覧画面_ボタン説明 drawio" src="https://github.com/user-attachments/assets/62eaddd9-4f03-463e-8c56-76f6c0285f80" />

### フォーム一覧
<img width="850" height="941" alt="フォーム一覧 drawio" src="https://github.com/user-attachments/assets/18f0cd41-09e5-4a4a-afbc-edc5c096d0ca" />

### 実行動画
<details><summary>条件検索</summary>
 
https://github.com/user-attachments/assets/e6285355-fe4e-47d5-ac0f-03acbd22c322
</details>
<details><summary>新規登録</summary>
 
https://github.com/user-attachments/assets/7ad96b5d-b17e-4108-9346-9c9808d0b051
</details>
<details><summary>更新</summary>
 
https://github.com/user-attachments/assets/6401f9d6-a052-4b8a-818b-4fb44914e7cd
</details>
<details><summary>削除</summary>
 
https://github.com/user-attachments/assets/c69faf30-0bcd-451a-93c8-c3c86382eb62
</details>

## 設計書

### API仕様書
![report表紙](https://github.com/user-attachments/assets/fa7b0bea-266a-4fa7-9355-725ba1913001)

<details><summary>条件検索の仕様書画面</summary>

![条件検索1](https://github.com/user-attachments/assets/01e0a302-bb4e-4a55-bd61-ba38a7aba92c)
![条件検索2](https://github.com/user-attachments/assets/cbb11853-f6db-4cf9-bf9a-e711109727bb)
</details>

<details><summary>新規登録の仕様書画面</summary>

![登録1](https://github.com/user-attachments/assets/371b5fd7-aad5-4570-966d-0309cf57f1ad)
![登録2](https://github.com/user-attachments/assets/1321c655-eb7c-4daa-b500-aaa7234567c1)
![登録3](https://github.com/user-attachments/assets/14f0d780-187d-45c9-ba53-3b3376b4e78d)
![登録4](https://github.com/user-attachments/assets/6d37bcdb-4426-4a9e-9f06-1f95bbfc4913)
</details>

<details><summary>更新の仕様書画面</summary>

![更新1](https://github.com/user-attachments/assets/f2792de9-e0ba-495b-ab75-fc00fa6c729c)
![更新2](https://github.com/user-attachments/assets/177d7075-eadf-40d4-8080-95d4dfd08de1)
![更新3](https://github.com/user-attachments/assets/4b0eb020-c970-4839-bf22-93a46cbbc7ff)
![更新4](https://github.com/user-attachments/assets/5959f612-7f25-4c70-9ace-4ca2fa25b6c8)
</details>


### ER図
```mermaid
erDiagram
  STUDENTS ||--|{ STUDENTS_COURSES : "enrolls"
  STUDENTS_COURSES ||--||STUDENTS_COURCES_STATUS: "has"
  
  STUDENTS {
    char(36) student_id PK
    varchar(100) student_full_name 
    varchar(100) student_furigana
    varchar(50) student_nickname
    varchar(254) email "UNIQUE"
    varchar(10) prefecture
    varchar(50) city
    int age
    varchar(20) gender
    varchar(500) student_remark
    tinyint student_is_deleted
  }

  STUDENTS_COURSES {
    int course_id PK
    char(36) student_id FK
    varchar(50) course_name
  }

  STUDENTS_COURCES_STATUS {
    int status_id PK
    int course_id FK
    enum status "('仮申込', '本申込', '受講中', '受講終了')"
    timestamp temporary_applied_at "仮登録日"
    timestamp official_applied_at "本申込日"
    timestamp course_started_at "受講開始日"
    timestamp course_completed_at "受講終了日（予定日）"
  }
```

### APIのURL設計

| HTTP<br/>メソッド | URL                                 | 処理内容                                  | 
|---------------|-------------------------------------|---------------------------------------|
| GET           | /api/students                           | 受講生詳細の取得　クエリパラメータで条件の指定が可能です | 
| POST          | /api/students                           | 新規受講生と新規受講コースの登録               |
| PUT           | /api/students                           | 受講生詳細の更新                              |

### シーケンス図

#### 受講生の条件検索フロー
```mermaid
sequenceDiagram
    actor User
    participant API as Spring Boot API
    participant DB as Database
    Note right of User: 受講生の条件検索フロー
    User ->>+ API: GET /api/students (クエリパラメータ: 受講生詳細フィールドの項目)
    API ->> API: 入力データ検証(バリデーションチェック)
    alt 入力データが無効な場合
        API -->>- User: 400 エラーメッセージ
    else 入力データが有効な場合
        API ->> DB: SELECT受講生詳細(条件検索)
        DB -->> API: 検索結果（JOIN結果・受講生ごとに複数行）
        API ->> API: 重複を削除し受講生詳細型に統合
        API -->> User: 200 受講生詳細
    end
```

#### 受講生詳細の登録フロー
```mermaid
sequenceDiagram
    actor User
    participant API as Spring Boot API
    participant DB as Database
    Note right of User: 受講生詳細の登録フロー
    User ->>+ API: POST /api/students (リクエストボディ：登録用受講生詳細)
    API ->> API: 入力データ検証(JSON文法チェック)
    
    alt JSONパース失敗
        API -->> User: 400 JSON文法エラー
    else JSONパース成功
        API ->> API: 入力データ検証(バリデーションチェック)
        
        alt バリデーション失敗
            API -->> User: 400 入力値が不正です
        else バリデーション成功
            API ->> API: 受講生詳細型にコンバート
            API ->> API: UUIDなどのデフォルト値を設定
            API ->> DB: INSERT受講生
            API ->> DB: INSERT受講コース
            API ->> DB: INSERT申込状況
            API -->> User: 200 登録処理が成功しました
        end
    end
    
    deactivate API
```

#### 受講生詳細の更新フロー
```mermaid
sequenceDiagram
    actor User
    participant API as Spring Boot API
    participant DB as Database
    Note right of User: 受講生詳細の更新フロー
    User ->>+ API: PUT /api/students（リクエストボディ：更新用受講生詳細）
    API ->> API: 入力データ検証(JSON文法チェック)
    
    alt JSONパース失敗
        API -->> User: 400 JSON文法エラー
    else JSONパース成功
        API ->> API: 入力データ検証(バリデーションチェック)
        
        alt バリデーション失敗
            API -->> User: 400 入力値が不正です
        else バリデーション成功
            API ->> API: 受講生詳細型にコンバート
            API ->> DB: UPDATE受講生
            API ->> DB: UPDATE受講コース
            
            Note over API: ステータスに応じて日付を自動設定
            
            alt ステータス：本申込
                API ->> DB: SELECT申込状況（前回データ取得）
                API ->> API: 仮申込日：前回データを保持<br/>本申込日：現在時刻を設定
            else ステータス：受講中
                API ->> DB: SELECT申込状況（前回データ取得）
                API ->> API: 仮申込日・本申込日：前回データを保持<br/>受講開始日：現在時刻<br/>受講終了日：300日後を設定
            else ステータス：受講終了
                API ->> DB: SELECT申込状況（前回データ取得）
                API ->> API: 仮申込日・本申込日・受講開始日：前回データを保持<br/>受講終了日：現在時刻を設定
            end
            
            API ->> DB: UPDATE申込状況
            API -->> User: 200 更新処理が成功しました
        end
    end
    
    deactivate API

```

## テスト
以下のテストをJUnit5で実装し、動作を検証しています。<br>
![testreport](https://github.com/user-attachments/assets/e775bbc0-7f9f-47aa-be10-ce927b3c67dc)


## 力をいれたところ
### 🔶要件定義の見直し：クライアントが使いやすく、かつ保守性の高い仕様の追求
![移行図](https://github.com/user-attachments/assets/91cc806f-d2eb-4929-8827-60952c60bf33)

以下のプロセスで当初予定していた要件定義に変更を加えました。

**1. ver1に対する疑問**  <br>
 - 受講生コース情報と申込状況が1：1の関係である意味
 - この2つのテーブルは統合したほうが実装がシンプルなのではないか？
 - では、それぞれのテーブルの責務はなんだろうか？

**2. 責務の分析**  <br>
- **受講生コース情報**：受講生とコースの1：多の関係を管理。静的なコース属性（コース名など）を保持。
- **申し込み状況**：更新頻度が高く、動的に変化するデータを管理。業務上のトリガー判断（リマインド送信、教材送付など）の基準として使用される想定。拡張性も必要。
- **ver1の課題①（クライアント視点）**：動的データが2つのテーブルに分散しており、データ管理が非効率。
- **ver1の課題②（開発者視点）**：テーブルの責務境界が不明確で、機能拡張時の設計判断が困難。
- **改善案**：動的データ（日付情報）を申込状況テーブルに集約し、責務を明確化。

**3. 改善によるメリット**  <br>
|  | クライアント目線 | 開発者目線 |
|---|---|---|
| **ver1** | 日付情報が2項目のみで、申込状況の追跡が不十分 | テーブルの責務が曖昧で、拡張時の判断が困難 |
| **ver2** | 4つの申込状況すべての日付を管理可能 | 責務が明確になり、保守性が向上 |

**4. 上記を現役エンジニアのメンターに相談し、ver2の実装へ**  <br>

### 🔶ユースケースに基づいた設計
- **データ活用の観点からの設計**  <br>
分析に活用することを想定し、既存のレコードを保持するようなDB処理を行っています。  <br>
具体的には、「受講生の削除機能をUPDATE処理を使い論理削除として実装」といった対応をしており、以下のようなデータ活用が可能です。
  - 退会者属性の傾向を分析し、マーケティングに役立てる。
  - 退会者数をKPIとしてモニタリングし、一定水準を下回った場合に早期に着手できるようにする。

- **業務効率化とヒューマンエラー防止**  <br>
運営管理者の業務フローを想定し、手動入力によるミスを防ぐ設計を実装しています。  <br>
具体的には、以下の自動化処理により、データの正確性を担保しています。
  - ステータス変更時の日付自動設定：申込状況が更新されると、該当する日付（仮申込日・本申込日・受講開始日など）を更新時点の日付に自動で記録。
  - プライマリーキーの自動採番：登録時にUUID・連番IDを自動生成し、管理者の入力負担を軽減。

### 🔶コード品質・保守性を考慮した設計
- **効果的なバリデーション、例外処理**<br>
バリデーションに正規表現などを活用し、データの整合性を確保しました。<br>
また、エラーがあった際にユーザーが適切に修正できるよう、 バリデーションエラー(クエリパラメータ、リクエストボディ)、JSON文法エラーなどのハンドリングを行い、クライアント側にエラーメッセージが表示されるようにしました。

- **実装意図が伝わりやすいコーディング・ドキュメント作成**<br>
具体的には以下の3点を行いました。
    - コード内でのドキュメント作成：主要なクラス・メソッドにJavadocやOpenAPIアノテーションを利用したドキュメントを記述しました。
    - 命名へのこだわり: クラス名やメソッド名に挙動が想像しやすい言葉を選定し、可読性を向上させました。
    - 読みやすいレビュー依頼: プルリクエストでのレビュー依頼時に概要を把握しやすいよう、変更点・変更目的・特にレビューいただきたい箇所などを明示しました。

- **テストの責務分離**<br>
コントローラ層のテスト肥大化を防ぐため、以下のようにテストの責務を整理しました。これにより、各層の責務が明確になり、テストの保守性が向上しました。
  - DTOクラス：正常系はコントローラ層、異常系はDTOクラス単位
  - 内部処理用クラス：すべてオブジェクトクラス単位

## 今後の展望
- 全体に関わる改修
  - 2つ目以降の受講コース追加機能の実装
  - 日付の手動変更の実装
  - 登録・更新に専用のDTOが本当に必要か検討
  - コース名をenum型に変更し、enumに新規開校コース追加・旧コース削除ができるようにする
  - ログイン機能の実装

- インフラ環境の構築（スクールにて現在学習中）
    - AWSへのデプロイ（EC2、RDS、ELB）
    - CI/CDパイプライン構築（GitHub Actions）
    - Docker導入

- バックエンドの修正
  - 認証機能の実装
  - エラーハンドリングの見直し

- フロントエンドの修正
  - コース一覧画面の追加
  - フィルター機能の追加
  - AIを活用し短期間で作成したため、以下のリファクタリングが必要
    - コンポーネント設計の見直し（再利用性・保守性の向上）
    - 命名規則の統一とコードの整理
    - 状態管理の最適化









