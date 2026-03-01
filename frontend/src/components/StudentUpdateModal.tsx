import { useState } from "react";
import type { StudentResponse } from "../types/StudentResponse";
import type { UpdateStudentFormValues } from "../types/UpdateStudentFormValues";
import type { CourseStatus } from "../types/CourseStatus";
import * as FaIcons from "react-icons/fa";

type StudentUpdateModalProps = {
  /**
   * 更新対象の受講生詳細データです。
   * フォームの初期値として使用します。
   */
  student: StudentResponse;
  /**
   * モーダルを閉じるためのコールバック関数です。
   * ×ボタン押下時に呼ばれます。
   */
  onClose: () => void;
  /**
   * 更新ボタン押下時に呼ばれる関数です。
   * フォームの入力値（payload）を受け取り、API更新処理を実行します。
   */
  onUpdate: (payload: UpdateStudentFormValues) => Promise<void>;
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
  },
  courseName: {
    fontWeight: "bold",
    fontSize: "16px",
    marginBottom: "12px",
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
  statusTabs: {
    display: "flex",
    gap: "8px",
  },
  statusTab: {
    padding: "8px 16px",
    border: "1px solid #ddd",
    backgroundColor: "#fff",
    borderRadius: "4px",
    cursor: "pointer",
    fontSize: "14px",
    transition: "all 0.2s",
  },
  statusTabActive: {
    padding: "8px 16px",
    border: "1px solid #2196f3",
    backgroundColor: "#2196f3",
    color: "#fff",
    borderRadius: "4px",
    cursor: "pointer",
    fontSize: "14px",
    transition: "all 0.2s",
  },
};

/**
 * 受講生更新モーダルの表示・動作を管理するコンポーネントです。
 */
export const StudentUpdateModal = (props: StudentUpdateModalProps) => {
  const { student, onClose, onUpdate } = props;

  const [formData, setFormData] = useState({
    studentFullName: student.student.studentFullName,
    studentFurigana: student.student.studentFurigana,
    studentNickname: student.student.studentNickname ?? "",
    email: student.student.email,
    prefecture: student.student.prefecture ?? "",
    city: student.student.city ?? "",
    age: student.student.age?.toString() ?? "",
    gender: student.student.gender ?? "",
    studentRemark: student.student.studentRemark ?? "",
  });

  const [courseStatuses, setCourseStatuses] = useState<
    { statusId: number; courseId: number; status: CourseStatus }[]
  >(
    student.courseList.map((course) => ({
      statusId: course.status.statusId,
      courseId: course.status.courseId,
      status: course.status.status,
    })),
  );

  const [isSubmitting, setIsSubmitting] = useState(false);

  /**
   * 入力状態に変化があった際にその情報を保持してstateを更新します。
   */
  const handleInputChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
  ) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  /**
   * コース情報が新しく更新された際にその情報を保持してstateを更新します。
   */
  const handleStatusChange = (index: number, newStatus: CourseStatus) => {
    setCourseStatuses((prev) =>
      prev.map((course, i) =>
        i === index ? { ...course, status: newStatus } : course,
      ),
    );
  };

  /**
   * 更新フォーム送信時の処理です。
   * 現在の更新情報で関数を実行し、その後モーダルを閉じます。
   */
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsSubmitting(true);

    try {
      const payload: UpdateStudentFormValues = {
        student: {
          studentId: student.student.studentId,
          studentFullName: formData.studentFullName,
          studentFurigana: formData.studentFurigana,
          studentNickname: formData.studentNickname || null,
          email: formData.email,
          prefecture: formData.prefecture || null,
          city: formData.city || null,
          age: formData.age ? parseInt(formData.age) : null,
          gender: formData.gender || null,
          studentRemark: formData.studentRemark || null,
          studentIsDeleted: student.student.studentIsDeleted,
        },
        courseList: courseStatuses.map((courseStatus) => ({
          status: {
            statusId: courseStatus.statusId,
            courseId: courseStatus.courseId,
            status: courseStatus.status,
          },
        })),
      };

      await onUpdate(payload);
      alert("更新しました");
      onClose();
    } catch (err: unknown) {
      if (err instanceof Error) {
        alert(`更新に失敗しました: ${err.message}`);
      } else {
        alert("更新に失敗しました");
      }
    } finally {
      setIsSubmitting(false);
    }
  };

  /**
   * モーダル外を押下した際にモーダルを閉じます。
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

        <h2 style={styles.title}>受講生情報の編集</h2>

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
            <h3 style={styles.sectionTitle}>コースステータス</h3>
            {student.courseList.length > 0 ? (
              student.courseList.map((courseDetail, index) => (
                <div
                  key={courseDetail.course.courseId}
                  style={styles.courseItem}
                >
                  <div style={styles.courseName}>
                    {courseDetail.course.courseName}
                  </div>
                  <div style={styles.statusTabs}>
                    {statusOptions.map((status) => (
                      <button
                        key={status}
                        type="button"
                        style={
                          courseStatuses[index].status === status
                            ? styles.statusTabActive
                            : styles.statusTab
                        }
                        onClick={() => handleStatusChange(index, status)}
                      >
                        {status}
                      </button>
                    ))}
                  </div>
                </div>
              ))
            ) : (
              <p>受講中のコースはありません</p>
            )}
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
              {isSubmitting ? "更新中..." : "更新する"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};
