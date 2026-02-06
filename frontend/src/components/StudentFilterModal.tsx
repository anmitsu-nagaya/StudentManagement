import { useState } from "react";
import type { CourseStatus } from "../types/CourseStatus";
import * as FaIcons from "react-icons/fa";

/**
 * 受講生検索モーダルで使用する検索条件の入力値。
 * 各項目は部分一致検索や範囲検索のため文字列で保持する。
 */
type FilterFormValues = {
  studentFullName: string;
  studentFurigana: string;
  studentNickname: string;
  email: string;
  prefecture: string;
  city: string;
  ageFrom: string;
  ageTo: string;
  gender: string;
  courseName: string;
  status: CourseStatus | "";
};

type StudentFilterModalProps = {
  /**
   * モーダルを閉じるためのコールバック関数
   */
  onClose: () => void;

  /**
   * モーダルに入力された検索条件を渡すためのコールバック関数
   * @param filters 入力された検索条件
   */
  onSearch: (filters: FilterFormValues) => void;
};

const styles = {
  overlay: {
    position: "fixed" as const,
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    backgroundColor: "rgba(0, 0, 0, 0.5)",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    zIndex: 1000,
  },
  modal: {
    backgroundColor: "#fff",
    borderRadius: "8px",
    padding: "24px",
    maxWidth: "700px",
    width: "90%",
    maxHeight: "95vh",
    overflow: "auto",
    position: "relative" as const,
  },
  closeButton: {
    position: "absolute" as const,
    top: "16px",
    right: "16px",
    border: "none",
    backgroundColor: "transparent",
    fontSize: "24px",
    cursor: "pointer",
    color: "#666",
  },
  title: {
    fontSize: "24px",
    fontWeight: "bold",
    marginBottom: "24px",
    paddingRight: "40px",
  },
  section: {
    marginBottom: "24px",
  },
  sectionTitle: {
    fontSize: "18px",
    fontWeight: "bold",
    marginBottom: "12px",
    color: "#333",
    borderBottom: "2px solid #2196f3",
    paddingBottom: "8px",
  },
  formGrid: {
    display: "grid",
    gridTemplateColumns: "150px 1fr",
    gap: "16px",
    alignItems: "center",
    marginBottom: "12px",
  },
  label: {
    fontWeight: "bold",
    color: "#666",
  },
  input: {
    padding: "8px 12px",
    border: "1px solid #ddd",
    borderRadius: "4px",
    fontSize: "14px",
    width: "100%",
  },
  select: {
    padding: "8px 12px",
    border: "1px solid #ddd",
    borderRadius: "4px",
    fontSize: "14px",
    width: "100%",
  },
  buttonContainer: {
    display: "flex",
    justifyContent: "flex-end",
    gap: "12px",
    marginTop: "24px",
  },
  clearButton: {
    padding: "10px 24px",
    border: "1px solid #ddd",
    backgroundColor: "#fff",
    borderRadius: "4px",
    cursor: "pointer",
    fontSize: "14px",
  },
  cancelButton: {
    padding: "10px 24px",
    border: "1px solid #ddd",
    backgroundColor: "#fff",
    borderRadius: "4px",
    cursor: "pointer",
    fontSize: "14px",
  },
  searchButton: {
    padding: "10px 24px",
    border: "none",
    backgroundColor: "#2196f3",
    color: "#fff",
    borderRadius: "4px",
    cursor: "pointer",
    fontSize: "14px",
    fontWeight: "bold",
  },
};

/**
 * 受講生詳細検索が入力されるモーダルの動作を管理するコンポーネント。
 *
 * @param props onClose onSearch
 * @returns モーダルHTML
 */
export const StudentFilterModal = (props: StudentFilterModalProps) => {
  const { onClose, onSearch } = props;

  /**
   * 検索フォームの入力状態を管理するstate。
   * 入力途中の値を保持し、確定後にonSerchの引数として親コンポーネントに渡る。
   */
  const [formData, setFormData] = useState<FilterFormValues>({
    studentFullName: "",
    studentFurigana: "",
    studentNickname: "",
    email: "",
    prefecture: "",
    city: "",
    ageFrom: "",
    ageTo: "",
    gender: "",
    courseName: "",
    status: "",
  });

  /**
   * 検索項目の入力状態に変更があったらStateを更新する。
   * すべての検索項目に入力されるわけではないため、スプレッド構文を使用して入力された検索項目のみstateを更新する。
   */
  const handleInputChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>,
  ) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  /**
   * 全項目を未入力の状態でstateを更新する。
   */
  const handleClear = () => {
    setFormData({
      studentFullName: "",
      studentFurigana: "",
      studentNickname: "",
      email: "",
      prefecture: "",
      city: "",
      ageFrom: "",
      ageTo: "",
      gender: "",
      courseName: "",
      status: "",
    });
  };

  /**
   * 検索フォーム送信時の処理。
   * 現在の検索条件を親コンポーネントに渡し、その後モーダルを閉じる。
   */
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSearch(formData);
    onClose();
  };

  /**
   * モーダル外をクリックしたときにモーダルを閉じる処理
   */
  const handleOverlayClick = (e: React.MouseEvent<HTMLDivElement>) => {
    if (e.target === e.currentTarget) {
      onClose();
    }
  };

  const statusOptions: CourseStatus[] = [
    "仮申込",
    "本申込",
    "受講中",
    "受講修了",
  ];

  return (
    <div style={styles.overlay} onClick={handleOverlayClick}>
      <div style={styles.modal}>
        <button style={styles.closeButton} onClick={onClose}>
          <FaIcons.FaTimes />
        </button>

        <h2 style={styles.title}>受講生 検索</h2>

        <form onSubmit={handleSubmit}>
          <div style={styles.section}>
            <h3 style={styles.sectionTitle}>基本情報</h3>

            <div style={styles.formGrid}>
              <label style={styles.label}>名前</label>
              <input
                type="text"
                name="studentFullName"
                value={formData.studentFullName}
                onChange={handleInputChange}
                style={styles.input}
                placeholder="部分一致で検索"
              />

              <label style={styles.label}>ふりがな</label>
              <input
                type="text"
                name="studentFurigana"
                value={formData.studentFurigana}
                onChange={handleInputChange}
                style={styles.input}
                placeholder="部分一致で検索"
              />

              <label style={styles.label}>ニックネーム</label>
              <input
                type="text"
                name="studentNickname"
                value={formData.studentNickname}
                onChange={handleInputChange}
                style={styles.input}
                placeholder="部分一致で検索"
              />

              <label style={styles.label}>メール</label>
              <input
                type="text"
                name="email"
                value={formData.email}
                onChange={handleInputChange}
                style={styles.input}
                placeholder="部分一致で検索"
              />

              <label style={styles.label}>都道府県</label>
              <input
                type="text"
                name="prefecture"
                value={formData.prefecture}
                onChange={handleInputChange}
                style={styles.input}
                placeholder="部分一致で検索"
              />

              <label style={styles.label}>市区町村</label>
              <input
                type="text"
                name="city"
                value={formData.city}
                onChange={handleInputChange}
                style={styles.input}
                placeholder="部分一致で検索"
              />

              <label style={styles.label}>年齢</label>
              <div
                style={{ display: "flex", gap: "8px", alignItems: "center" }}
              >
                <input
                  type="number"
                  name="ageFrom"
                  value={formData.ageFrom}
                  onChange={handleInputChange}
                  style={styles.input}
                  min="0"
                  placeholder="20"
                />
                <span>〜</span>
                <input
                  type="number"
                  name="ageTo"
                  value={formData.ageTo}
                  onChange={handleInputChange}
                  style={styles.input}
                  min="0"
                  placeholder="29"
                />
              </div>

              <label style={styles.label}>性別</label>
              <input
                type="text"
                name="gender"
                value={formData.gender}
                onChange={handleInputChange}
                style={styles.input}
                placeholder="部分一致で検索"
              />
            </div>
          </div>

          <div style={styles.section}>
            <h3 style={styles.sectionTitle}>コース情報</h3>

            <div style={styles.formGrid}>
              <label style={styles.label}>コース名</label>
              <input
                type="text"
                name="courseName"
                value={formData.courseName}
                onChange={handleInputChange}
                style={styles.input}
                placeholder="部分一致で検索"
              />

              <label style={styles.label}>ステータス</label>
              <select
                name="status"
                value={formData.status}
                onChange={handleInputChange}
                style={styles.select}
              >
                <option value="">すべて</option>
                {statusOptions.map((status) => (
                  <option key={status} value={status}>
                    {status}
                  </option>
                ))}
              </select>
            </div>
          </div>

          <div style={styles.buttonContainer}>
            <button
              type="button"
              style={styles.clearButton}
              onClick={handleClear}
            >
              クリア
            </button>
            <button type="button" style={styles.cancelButton} onClick={onClose}>
              キャンセル
            </button>
            <button type="submit" style={styles.searchButton}>
              検索
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};
