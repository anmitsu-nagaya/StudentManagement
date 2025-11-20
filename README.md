## サービス概要

このプロジェクトは、IT技術を教えるスクールが受講生の情報を保持・分析するための管理システムです。  
スクール運営者が使用することを想定しており、CRUD操作中心のシンプルで使いやすい設計を目指しています。

## 作成背景

JavaやSpring Bootの学習成果を形にするために作成しました。  
実務で頻繁に使用される以下の技術やツールを採用しています。

- REST APIの設計と実装: データのCRUD操作をサポート
- 自動テスト: JUnitを使用して単体テストを実装
- AWSを使用したデプロイ: クラウド環境へのアプリケーション展開

## 主な使用技術

### バックエンド
![badge](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=ED8B00)
![badge](https://img.shields.io/badge/SpringBoot-3.5.6-%236DB33F?logo=spring)

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
![badge](https://img.shields.io/badge/Gradle-02303A?logo=gradle&logoColor=white)
![badge](https://img.shields.io/badge/ESLint-3A33D1?logo=eslint)
![badge](https://img.shields.io/badge/Prettier-F7B93E?style=flat&logo=Prettier&logoColor=white)
![badge](https://img.shields.io/badge/npm-CB3837?logo=npm&logoColor=white)
![badge](https://img.shields.io/badge/Git-F05032?logo=git&logoColor=white)
![badge](https://img.shields.io/badge/GitHub-%23181717?logo=github&logoColor=white)
![badge](https://img.shields.io/badge/-intellij%20IDEA-000.svg?logo=intellij-idea&style=flat)
![badge](https://img.shields.io/badge/Visual%20Studio%20Code-007ACC?logo=visualstudiocode&logoColor=fff)

## 機能一覧

| 機能       | 詳細                                                              |
|:---------|:----------------------------------------------------------------|
| 受講生詳細の登録 | 氏名や居住地域などの受講生の情報と、受講コース・申込状況をセットで登録します                          |
| 受講生詳細の条件検索 | 氏名・居住地域・コース・申込状況などの検索条件を指定し、条件に該当する受講生詳細を取得します　                          |                                      |
| 受講生のID検索 | IDを指定し、一意の受講生詳細を取得します                                           |
| 受講生詳細の更新 | IDを指定し、任意の受講生詳細を更新します<br/>※削除処理については論理削除として実装しているため、更新処理として行います |


※ 言葉の定義は以下のとおりです

- 受講生： 氏名、居住地域、年齢などをもつオブジェクト
- 受講コース： 受講コース名をもつオブジェクト
- 申込状況： 仮申込,本申込といった申込状況、開始日などをもつオブジェクト
- 受講生詳細： 受講生、受講コース（申込状況含む）をもつオブジェクト

## 使用イメージ
### 受講生一覧画面
![image](https://github.com/user-attachments/assets/151681f8-5103-4f62-adbe-3524342d4979)

#### 受講生詳細画面
https://github.com/user-attachments/assets/10ec51f3-40cf-4051-bac3-33178116f553

### 条件検索
https://github.com/user-attachments/assets/e6285355-fe4e-47d5-ac0f-03acbd22c322

### 新規登録
https://github.com/user-attachments/assets/6496a131-aaf4-449d-a289-4806e3c6b276

### 更新
https://github.com/user-attachments/assets/6401f9d6-a052-4b8a-818b-4fb44914e7cd

### 削除


## 設計書

### API仕様書
https://github.com/user-attachments/assets/b7d6f6a9-2015-40b7-9228-ddb45f6fcbc5

### ER図
```mermaid
erDiagram
  STUDENTS ||--|{ STUDENTS_COURSES : "enrolls"
  STUDENTS_COURSES ||--||STUDENTS_COURCES_STATUS: "has"
  
  STUDENTS {
    char(36) student_id PK
    varchar(100) student_full_name "ユーザー名"
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
    enum status FK "('仮申込', '本申込', '受講中', '受講終了')"
    timestamp temporary_applied_at
    timestamp official_applied_at
    timestamp course_started_at
    timestamp course_completed_at
  }
```

### APIのURL設計

| HTTP<br/>メソッド | URL                                 | 処理内容                                  | 
|---------------|-------------------------------------|---------------------------------------|
| POST          | /register-student                           | 受講生詳細の作成                              |
| GET           | /students                           | 受講生詳細の取得 | 
| GET           | /students/filter                      | 条件検索後の受講生詳細の取得  <br/>クエリパラメータで指定します                     |
| PUT           | /update-student                           | 受講生詳細の更新                              |

### シーケンス図

#### 受講生詳細の登録フロー
```mermaid
sequenceDiagram
    actor User
    participant API as Spring Boot API
    participant DB as Database
    Note right of User: 受講生詳細の登録フロー
    User ->>+ API: POST /register-student (リクエストボディ：登録用受講生詳細)
    API ->> API: 入力データ検証
    alt 入力データが有効な場合
        API ->> API: 受講生詳細型にコンバート
        API ->> API: UUIDなどのデフォルト値を設定
        API ->> DB: INSERT受講生
        API ->> DB: INSERT受講コース
        API ->> DB: INSERT申込状況
        API -->> User: 200 登録処理が成功しました。
    else 入力データが無効な場合
        API -->>- User: 400 エラーメッセージ
    end
```

#### 受講生の条件検索フロー
```mermaid
sequenceDiagram
    actor User
    participant API as Spring Boot API
    participant DB as Database
    Note right of User: 受講生の条件検索フロー
    User ->>+ API: GET /students/filter (クエリパラメータ: 受講生詳細フィールドの項目)
    API ->> API: 入力データ検証
    alt 入力データが有効な場合
        API ->> DB: SELECT受講生詳細(条件検索)
        DB -->> API: 検索結果用受講生詳細
        API ->> API: 受講生詳細型にコンバート
        API -->> User: 200 受講生詳細
    else 入力データが無効な場合
        API -->>- User: 400 エラーメッセージ
    end
```

#### 受講生詳細の更新フロー
```mermaid
sequenceDiagram
    actor User
    participant API as Spring Boot API
    participant DB as Database
    Note right of User: 受講生詳細の更新フロー
    User ->>+ API: PUT /update-student（リクエストボディ：更新用受講生詳細）
    API ->> API: 入力データ検証
    alt 入力データが正しい場合
        API ->> API: 受講生詳細型にコンバート
        API ->> DB: UPDATE受講生
        API ->> DB: UPDATE受講コース
        API ->> API: 申込状況を抽出
        alt 申込状況：本申込
            API ->> DB: SELECT受講生(コースID検索)
            API ->> API: 仮申込日(検索結果)と本申込日(処理時点)をセット
        else 申込状況：受講中
            API ->> DB: SELECT受講生(コースID検索)
            API ->> API: 仮申込日・本申込日(検索結果)と受講開始日(処理時点)・受講終了日(300日後)をセット
        else 申込状況：受講修了
            API ->> DB: SELECT受講生(コースID検索)
            API ->> API: 仮申込日・本申込日・受講開始日(検索結果)と受講終了日(処理時点)をセット
        end
        API ->> DB: UPDATE申込状況
        API -->> User: 200 更新処理が成功しました。
    else 入力データが無効な場合
        API -->>- User: 400 エラーメッセージ
    end

```

## テスト
以下のテストをJUnit5で実装し、動作を検証しています。<br>
![image](https://github.com/user-attachments/assets/e775bbc0-7f9f-47aa-be10-ce927b3c67dc)

## 力をいれたところ
### 🔶仕様の妥当性検証と変更提案
自作課題として与えられた要件定義に対し、以下のフローで変更の提案を行いました。
- **疑問**：受講生コース情報と申込状況のDBが1：1であるならば、これらを分ける必要性は何か？
- **責務を整理**
  - 受講生コース情報：受講生とコースの関係が1：多であることを想定して管理するために必要。静的なコース属性。
  - 申し込み状況：更新頻度が高いDBであり、動的に変化する。<br>管理者はこのDBから、業務上のトリガー判断（申し込みリマインド送信、教材送付など）をすることが多いと想定。<br>申し込み状況の拡張もしやすい。
- **自己判断**：ユーザー視点から考えると、受講生コース情報と申込状況の定義の変更が必要ではないか？
- **相談**：講師・メンターに直接質問
- **結果**：仕様の一部を課題とは異なる独自の仕様に変更
    <details><summary>🔄仕様変更結果</summary>

    - 受講生コース情報の定義
        - ID
        - 受講生情報のID
        - コース名
        - ❌削除：~~受講開始日~~
        - ❌削除：~~受講修了予定日~~
    - 申し込み状況の定義
        - ID
        - 受講生コース情報のID
        - 申込状況
        - ✅追加：仮申込日
        - ✅追加：本申込日
        - ✅追加：受講開始日
        - ✅追加：受講終了日
    </details>

### 🔶ユースケースに基づいた設計
- データ活用
分析に活用することを想定し、既存のレコードを保持するようなDB処理を行っています。<br>
具体的には、「受講生の削除機能をUPDATE処理を使い論理削除として実装」といった対応をしています。これにより、以下のようなデータ活用が可能です。
  - 退会者属性の傾向を分析し、マーケティングに役立てる
  - 退会者数をKPIとしてモニタリングし、一定水準を下回った場合に早期に着手できるようにする

- 業務フロー
また、ヒューマンエラーの起きにくいシステムを想定した実装をすることで、ユーザーの業務フローに対しニーズの高い開発を意識しています。具体的には、「各ステータスの日付の自動取得」、「3つのIDの自動採番・自動取得」を行っています。
  - 問い合わせが来たら自動で仮申込状態に登録
  - 教材システムに初めて受講生がアクセスしたら自動で受講中に更新
  - 登録時に自動でプライマリーキーを設定

### 🔶コード品質・保守性を考慮した設計
- 効果的なバリデーション、例外処理
バリデーションに正規表現などを活用し、データの整合性を確保しました。<br>
また、エラーがあった際にユーザーが適切に修正できるよう、 バリデーションエラー、意図していない操作をされた際のBadRequestエラーなどのハンドリングを行い、クライエント側にエラーメッセージが表示されるようにしました。

- 実装意図が伝わりやすいコーディング・ドキュメント作成
具体的には以下の3点を行いました
    - コード内でのドキュメント作成：主要なクラス・メソッドにJavadocやOpenAPIアノテーションを利用したドキュメントを記述しました
    - 命名へのこだわり: クラス名やメソッド名に挙動が想像しやすい言葉を選定し、可読性を向上させました
    - 読みやすいレビュー依頼: プルリクエストでのレビュー依頼時に概要を把握しやすいよう、変更点・変更目的・特にレビューいただきたい箇所などを明示しました

- テストの責務分離
コントローラ層のテスト肥大化を防ぐため、以下のようにテストの責務を整理しました。これにより、各層の責務が明確になり、テストの保守性が向上しました。
  - DTOクラス：正常系はコントローラ層、異常系はDTOクラス単位
  - 内部処理用クラス：すべてオブジェクトクラス単位

## 今後の展望
- 全体に関わる改修
  - 受講コース追加機能の実装
  - 日付の手動設定・変更の実装
  - 登録・更新に専用のDTOが本当に必要か検討

- インフラ環境の構築（学習予定）
    - AWSへのデプロイ（EC2、RDS、ELB）
    - CI/CDパイプライン構築（GitHub Actions）
    - Docker導入

- バックエンドの修正
  - 認証機能の実装

- フロントエンドの修正
    - コース一覧画面の追加
    - フィルター機能の追加
    - AIを活用し短期間で作成したため、以下のリファクタリングが必要
      - コンポーネント設計の見直し（再利用性・保守性の向上）
      - 命名規則の統一とコードの整理
      - 状態管理の最適化



