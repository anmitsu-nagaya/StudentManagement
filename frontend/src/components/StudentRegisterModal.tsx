import { useState } from "react";
import type { NewStudentFormValues } from "../types/NewStudentFormValues";
import * as FaIcons from "react-icons/fa";

type StudentRegisterModalProps = {
  /**
   * モーダルを閉じるためのコールバック関数
   */
  onClose: () => void;
  /**
   * モーダルに入力された新規登録受講生データを渡すためのコールバック関数
   */
  onRegister: (payload: NewStudentFormValues) => Promise<void>;
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
    maxWidth: "800px",
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
  textarea: {
    padding: "8px 12px",
    border: "1px solid #ddd",
    borderRadius: "4px",
    fontSize: "14px",
    width: "100%",
    minHeight: "80px",
    resize: "vertical" as const,
  },
  courseItem: {
    padding: "16px",
    backgroundColor: "#f5f5f5",
    borderRadius: "4px",
    marginBottom: "12px",
    display: "flex",
    gap: "12px",
    alignItems: "center",
  },
  courseInput: {
    flex: 1,
    padding: "8px 12px",
    border: "1px solid #ddd",
    borderRadius: "4px",
    fontSize: "14px",
  },
  addCourseButton: {
    padding: "8px 16px",
    border: "1px solid #2196f3",
    backgroundColor: "#fff",
    color: "#2196f3",
    borderRadius: "4px",
    cursor: "pointer",
    fontSize: "14px",
    display: "flex",
    alignItems: "center",
    gap: "4px",
  },
  removeCourseButton: {
    padding: "6px 10px",
    border: "1px solid #ddd",
    backgroundColor: "#fff",
    borderRadius: "4px",
    cursor: "pointer",
    fontSize: "14px",
    color: "#f44336",
  },
  buttonContainer: {
    display: "flex",
    justifyContent: "flex-end",
    gap: "12px",
    marginTop: "24px",
  },
  cancelButton: {
    padding: "10px 24px",
    border: "1px solid #ddd",
    backgroundColor: "#fff",
    borderRadius: "4px",
    cursor: "pointer",
    fontSize: "14px",
  },
  submitButton: {
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
 * 受講生新規登録モーダルを管理するコンポーネント
 */
export const StudentRegisterModal = (props: StudentRegisterModalProps) => {
  const { onClose, onRegister } = props;

  const [formData, setFormData] = useState({
    studentFullName: "",
    studentFurigana: "",
    studentNickname: "",
    email: "",
    prefecture: "",
    city: "",
    age: "",
    gender: "",
    studentRemark: "",
  });

  const [course, setCourse] = useState<{ courseName: string }>({
    courseName: "",
  });

  const [isSubmitting, setIsSubmitting] = useState(false);

  /**
   * 登録項目の入力状態に変更があったらStateを更新する。
   * すべての登録項目に入力されるわけではないため、スプレッド構文を使用して入力された登録項目のみstateを更新する。
   */
  const handleInputChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
  ) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  /**
   * コース名の入力状態に変更があったらstateを更新する。
   * @param value コース名
   */
  const handleCourseChange = (value: string) => {
    setCourse({ courseName: value });
  };

  /**
   * 登録フォーム送信時の処理。
   * 現在の登録上表を親コンポーネントに渡し、その後モーダルを閉じる。
   */
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsSubmitting(true);
    const payload: NewStudentFormValues = {
      student: {
        studentFullName: formData.studentFullName,
        studentFurigana: formData.studentFurigana,
        studentNickname: formData.studentNickname || undefined,
        email: formData.email,
        prefecture: formData.prefecture || undefined,
        city: formData.city || undefined,
        age: formData.age ? parseInt(formData.age) : undefined,
        gender: formData.gender || undefined,
        studentRemark: formData.studentRemark || undefined,
      },
      courseList: [{ course: course }],
    };
    await onRegister(payload);
    alert("登録しました");
    onClose();
    setIsSubmitting(false);
  };

  /**
   * モーダル外をクリックしたときにモーダルを閉じる処理
   */
  const handleOverlayClick = (e: React.MouseEvent<HTMLDivElement>) => {
    if (e.target === e.currentTarget) {
      onClose();
    }
  };

  return (
    <div style={styles.overlay} onClick={handleOverlayClick}>
      <div style={styles.modal}>
        <button style={styles.closeButton} onClick={onClose}>
          <FaIcons.FaTimes />
        </button>

        <h2 style={styles.title}>受講生 新規登録</h2>

        <form onSubmit={handleSubmit}>
          <div style={styles.section}>
            <h3 style={styles.sectionTitle}>基本情報</h3>

            <div style={styles.formGrid}>
              <label style={styles.label}>名前 *</label>
              <input
                type="text"
                name="studentFullName"
                value={formData.studentFullName}
                onChange={handleInputChange}
                style={styles.input}
                required
              />

              <label style={styles.label}>ふりがな *</label>
              <input
                type="text"
                name="studentFurigana"
                value={formData.studentFurigana}
                onChange={handleInputChange}
                style={styles.input}
                required
              />

              <label style={styles.label}>ニックネーム</label>
              <input
                type="text"
                name="studentNickname"
                value={formData.studentNickname}
                onChange={handleInputChange}
                style={styles.input}
              />

              <label style={styles.label}>メール *</label>
              <input
                type="email"
                name="email"
                value={formData.email}
                onChange={handleInputChange}
                style={styles.input}
                required
              />

              <label style={styles.label}>都道府県</label>
              <input
                type="text"
                name="prefecture"
                value={formData.prefecture}
                onChange={handleInputChange}
                style={styles.input}
              />

              <label style={styles.label}>市区町村</label>
              <input
                type="text"
                name="city"
                value={formData.city}
                onChange={handleInputChange}
                style={styles.input}
              />

              <label style={styles.label}>年齢</label>
              <input
                type="number"
                name="age"
                value={formData.age}
                onChange={handleInputChange}
                style={styles.input}
                min="0"
              />

              <label style={styles.label}>性別</label>
              <input
                type="text"
                name="gender"
                value={formData.gender}
                onChange={handleInputChange}
                style={styles.input}
                placeholder="例: 男性、女性、その他"
              />

              <label style={styles.label}>備考</label>
              <textarea
                name="studentRemark"
                value={formData.studentRemark}
                onChange={handleInputChange}
                style={styles.textarea}
              />
            </div>
          </div>

          <div style={styles.section}>
            <h3 style={styles.sectionTitle}>受講コース</h3>
            <div style={styles.courseItem}>
              <input
                type="text"
                value={course.courseName}
                onChange={(e) => handleCourseChange(e.target.value)}
                style={styles.courseInput}
                placeholder="コース名を入力"
                required
              />
            </div>
          </div>

          <div style={styles.buttonContainer}>
            <button
              type="button"
              style={styles.cancelButton}
              onClick={onClose}
              disabled={isSubmitting}
            >
              キャンセル
            </button>
            <button
              type="submit"
              style={styles.submitButton}
              disabled={isSubmitting}
            >
              {isSubmitting ? "登録中..." : "登録する"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};
