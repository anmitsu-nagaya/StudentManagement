## 目次
1. [サービス概要](#サービス概要)
2. [作成背景](#作成背景)
3. [主な使用技術](#主な使用技術)
4. [機能一覧](#機能一覧)
5. [使用イメージ](#使用イメージ)
6. [設計書](#設計書)
7. [テスト](#テスト)
8. [力をいれたところ](#力をいれたところ)
9. [今後の展望](#今後の展望)
 
## サービス概要

このプロジェクトは、IT技術を教えるスクールが受講生の情報を保持・分析するための管理システムです。  
スクール運営者が使用することを想定しており、CRUD操作中心のシンプルで使いやすい設計を目指しています。

## 作成背景

Java/Spring Bootを教えるプログラミングスクールで学んだ内容を、学習成果として形にするために作成しました。

### 講座での学習内容
- **実装スタイル**：「先に自分で実装 → プルリクエストでレビュー → 講師の解説」という流れで開発
- **習得技術**：REST API設計、テスト駆動開発など実務で頻繁に使用される技術

### 独自で追加実装した内容
- **機能拡張**：講座の要件定義に対し、新たな機能（後述💡マーク）を独自に実装。DB設計から見直し、CRUD処理をすべて再実装
- **フロントエンド開発**：バックエンドAPIの動作確認と実務での連携を想定し、TypeScript/Reactで実装（基礎文法を独学後、AIを活用して実装）

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
※💡：機能拡張

| 機能       | 詳細                            |
|:---------|:----------------------------------------------------------------|
| 受講生の全件検索 | すべての**受講生詳細**を取得します   |
| 受講生詳細の条件検索 💡 | 氏名・コース・申込状況など複数テーブルを跨ぐ検索条件を指定し、条件に該当する**受講生詳細**を取得します<br/>※リクエストはクエリパラメータで個別フィールドを指定     |      |
| 受講生詳細の新規登録 | 氏名や居住地域などの**受講生**の情報と、**受講コース**・**申込状況** をセットで登録します<br/>※リクエストには**登録用受講生詳細**を使用          |
| 受講生詳細の更新 | IDを指定し、任意の**受講生詳細**を更新します<br/>※リクエストには**更新用受講生詳細**を使用<br/>※削除処理については論理削除として実装しているため、更新処理として行います |

※ 言葉の定義は以下のとおりです

- **受講生**：氏名、居住地域、年齢などをもつオブジェクト
- **受講コース**：受講コース名をもつオブジェクト（受講生に対して1：多の関係）
- **申込状況** 💡：仮申込、本申込といった申込状況、開始日などをもつオブジェクト
- **受講生詳細**：上記3つを統合したオブジェクト ※DBでは正規化されて3テーブルに分離していますが、APIでは統合した形で扱います
- **登録用受講生詳細**：新規登録時のリクエストボディ
- **更新用受講生詳細**：更新時のリクエストボディ

## 使用イメージ
### 受講生一覧画面
![一覧画面](https://github.com/user-attachments/assets/151681f8-5103-4f62-adbe-3524342d4979)

#### 受講生詳細画面
https://github.com/user-attachments/assets/10ec51f3-40cf-4051-bac3-33178116f553

### 条件検索
https://github.com/user-attachments/assets/e6285355-fe4e-47d5-ac0f-03acbd22c322

### 新規登録
https://github.com/user-attachments/assets/7ad96b5d-b17e-4108-9346-9c9808d0b051

### 更新
https://github.com/user-attachments/assets/6401f9d6-a052-4b8a-818b-4fb44914e7cd

### 削除
https://github.com/user-attachments/assets/c69faf30-0bcd-451a-93c8-c3c86382eb62

## 設計書

### API仕様書
![report表紙](https://github.com/user-attachments/assets/37ff6931-e8a5-4814-8eae-335d6ed21558)
<details><summary>全件検索の仕様書画面</summary>

![全件取得](https://github.com/user-attachments/assets/a203531f-014b-4f88-962a-af8adca89176)
</details>

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
| GET           | /students                           | 受講生詳細の取得 | 
| GET           | /students/filter                      | 条件検索後の受講生詳細の取得　クエリパラメータで指定します                     |
| POST          | /register-student                           | 新規受講生と新規受講コースの登録                              |
| PUT           | /update-student                           | 受講生詳細の更新                              |

### シーケンス図

#### 受講生の条件検索フロー
```mermaid
sequenceDiagram
    actor User
    participant API as Spring Boot API
    participant DB as Database
    Note right of User: 受講生の条件検索フロー
    User ->>+ API: GET /students/filter (クエリパラメータ: 受講生詳細フィールドの項目)
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
    User ->>+ API: POST /register-student (リクエストボディ：登録用受講生詳細)
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
    User ->>+ API: PUT /update-student（リクエストボディ：更新用受講生詳細）
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
### 🔶仕様の妥当性検証と変更提案
自作課題で与えられた元の仕様に対し、責務の整理を行った上で技術的妥当性とビジネス要件への適合性に関する変更提案を行いました。変更に伴い、講師・メンターとの双方向のやり取りを行いました。

具体的には以下の変更を行い、「クライアントが使いやすく、かつ保守性の高い仕様」に改善することができました。
- 受講開始日・受講終了日を受講生コース情報から申込状況に移動
- 申込状況に仮申込日と本申込日を追加

<details><summary>仕様変更詳細</summary>

**受講生コース情報の定義**
- ID
- 受講生情報のID
- コース名
- ❌削除：~~受講開始日~~
- ❌削除：~~受講修了予定日~~

**申し込み状況の定義**
- ID
- 受講生コース情報のID
- 申込状況
- ✅追加：仮申込日
- ✅追加：本申込日
- ✅移動：受講開始日
- ✅移動：受講終了日

</details>

<details><summary>改善点詳細</summary>

|  | クライアント目線 | シンプルな実装・責務を明確にした実装 |
|---|---|---|
| **元の仕様** | 受講中・受講修了の2つの日付情報のみの登録に留まっていた | カラムを増やしたいという要望が来たときにどっちのDBに追加すべきか分かりづらかった |
| **変更後** | 4つの申し込み状況すべての日付情報が管理可能になった<br>それに伴い、ビジネス課題に対するデータ管理がしやすくなった | DBの責務に沿って、カラムの追加・削除に柔軟に対応できるようになり、保守性が高まった |

</details>

<details><summary>仕様変更に至ったプロセス</summary>

**1. 課題の発見**  
受講生コース情報と申込状況が1：1の関係であることに着目し、「2つのDBに分けている理由」について思考した結果、「テーブルを統合できるのではないか」という疑問を持ちました。

**2. 責務の分析**  
両テーブルの役割を整理し、分離の妥当性を検証しました。
- **受講生コース情報**：受講生とコースの1：多の関係を管理。静的なコース属性（コース名など）を保持
- **申込状況**：更新頻度が高く、動的に変化するデータを管理。業務上のトリガー判断（リマインド送信、教材送付など）の基準として使用される想定。拡張性も必要

**3. 改善案の検討**  
分析の結果、日付情報は「申込状況」で一元管理すべきと判断しました。
- **変更内容**：受講開始日・受講終了日を受講生コース情報から申込状況に移動
- **理由**：日付は申込の進行状況に紐づく動的データであり、コースの静的属性とは性質が異なる

**4. メンターへの相談**  
講師・メンターに変更案を提示し、技術的妥当性とビジネス要件への適合性を確認しました。

**5. 最終決定**  
承認を得て、課題の要件定義から一部仕様を変更し、独自の設計を採用しました。

</details>

### 🔶ユースケースに基づいた設計
- **データ活用の観点からの設計**  <br>
分析に活用することを想定し、既存のレコードを保持するようなDB処理を行っています。  <br>
具体的には、「受講生の削除機能をUPDATE処理を使い論理削除として実装」といった対応をしており、以下のようなデータ活用が可能です。
  - 退会者属性の傾向を分析し、マーケティングに役立てる
  - 退会者数をKPIとしてモニタリングし、一定水準を下回った場合に早期に着手できるようにする

- **業務効率化とヒューマンエラー防止**  <br>
運営管理者の業務フローを想定し、手動入力によるミスを防ぐ設計を実装しています。  <br>
具体的には、以下の自動化処理により、データの正確性を担保しています。
  - ステータス変更時の日付自動設定：申込状況が更新されると、該当する日付（仮申込日・本申込日・受講開始日など）を更新時点の日付に自動で記録
  - プライマリーキーの自動採番：登録時にUUID・連番IDを自動生成し、管理者の入力負担を軽減

### 🔶コード品質・保守性を考慮した設計
- **効果的なバリデーション、例外処理**<br>
バリデーションに正規表現などを活用し、データの整合性を確保しました。<br>
また、エラーがあった際にユーザーが適切に修正できるよう、 バリデーションエラー(クエリパラメータ、リクエストボディ)、JSON文法エラーなどのハンドリングを行い、クライアント側にエラーメッセージが表示されるようにしました。

- **実装意図が伝わりやすいコーディング・ドキュメント作成**<br>
具体的には以下の3点を行いました。
    - コード内でのドキュメント作成：主要なクラス・メソッドにJavadocやOpenAPIアノテーションを利用したドキュメントを記述しました
    - 命名へのこだわり: クラス名やメソッド名に挙動が想像しやすい言葉を選定し、可読性を向上させました
    - 読みやすいレビュー依頼: プルリクエストでのレビュー依頼時に概要を把握しやすいよう、変更点・変更目的・特にレビューいただきたい箇所などを明示しました

- **テストの責務分離**<br>
コントローラ層のテスト肥大化を防ぐため、以下のようにテストの責務を整理しました。これにより、各層の責務が明確になり、テストの保守性が向上しました。
  - DTOクラス：正常系はコントローラ層、異常系はDTOクラス単位
  - 内部処理用クラス：すべてオブジェクトクラス単位

## 今後の展望
- 全体に関わる改修
  - 受講コース追加機能の実装
  - 日付の手動変更の実装
  - 登録・更新に専用のDTOが本当に必要か検討
  - コース名をenum型に変更、enumにコース追加・削除できるようにする

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










