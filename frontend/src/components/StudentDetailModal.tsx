import type { StudentResponse } from "../types/StudentResponce";
import * as FaIcons from "react-icons/fa";

type StudentDetailModalProps = {
  /**
   * 受講生詳細／一覧取得APIから返却されるレスポンスデータ
   */
  student: StudentResponse;
  /**
   * モーダルを閉じるためのコールバック関数
   */
  onClose: () => void;
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
    maxHeight: "80vh",
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
  infoGrid: {
    display: "grid",
    gridTemplateColumns: "150px 1fr",
    gap: "12px",
    fontSize: "14px",
  },
  label: {
    fontWeight: "bold",
    color: "#666",
  },
  value: {
    color: "#333",
  },
  courseItem: {
    padding: "12px",
    backgroundColor: "#f5f5f5",
    borderRadius: "4px",
    marginBottom: "8px",
  },
  courseName: {
    fontWeight: "bold",
    fontSize: "16px",
    marginBottom: "8px",
  },
  courseInfo: {
    display: "grid",
    gridTemplateColumns: "120px 1fr",
    gap: "8px",
    fontSize: "14px",
  },
};

/**
 * 受講生詳細モーダルの表示・動作を管理するコンポーネント
 * @param props student onClose
 * @returns
 */
export const StudentDetailModal = (props: StudentDetailModalProps) => {
  const { student, onClose } = props;

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

        <h2 style={styles.title}>{student.student.studentFullName} の詳細</h2>

        <div style={styles.section}>
          <h3 style={styles.sectionTitle}>基本情報</h3>
          <div style={styles.infoGrid}>
            <span style={styles.label}>名前:</span>
            <span style={styles.value}>{student.student.studentFullName}</span>

            <span style={styles.label}>ふりがな:</span>
            <span style={styles.value}>{student.student.studentFurigana}</span>

            <span style={styles.label}>ニックネーム:</span>
            <span style={styles.value}>
              {student.student.studentNickname ?? "-"}
            </span>

            <span style={styles.label}>メールアドレス:</span>
            <span style={styles.value}>{student.student.email}</span>

            <span style={styles.label}>年齢:</span>
            <span style={styles.value}>{student.student.age ?? "-"}</span>

            <span style={styles.label}>性別:</span>
            <span style={styles.value}>{student.student.gender ?? "-"}</span>

            <span style={styles.label}>都道府県:</span>
            <span style={styles.value}>
              {student.student.prefecture ?? "-"}
            </span>

            <span style={styles.label}>市区町村:</span>
            <span style={styles.value}>{student.student.city ?? "-"}</span>

            <span style={styles.label}>備考:</span>
            <span style={styles.value}>
              {student.student.studentRemark ?? "-"}
            </span>
          </div>
        </div>

        <div style={styles.section}>
          <h3 style={styles.sectionTitle}>受講コース情報</h3>
          {student.courseList.length > 0 ? (
            student.courseList.map((courseDetail) => (
              <div key={courseDetail.course.courseId} style={styles.courseItem}>
                <div style={styles.courseName}>
                  {courseDetail.course.courseName}
                </div>
                <div style={styles.courseInfo}>
                  <span style={styles.label}>ステータス:</span>
                  <span style={styles.value}>{courseDetail.status.status}</span>

                  <span style={styles.label}>仮申込日:</span>
                  <span style={styles.value}>
                    {courseDetail.status.temporaryAppliedAt}
                  </span>

                  <span style={styles.label}>本申込日:</span>
                  <span style={styles.value}>
                    {courseDetail.status.officialAppliedAt ?? "-"}
                  </span>

                  <span style={styles.label}>受講開始日:</span>
                  <span style={styles.value}>
                    {courseDetail.status.courseStartedAt ?? "-"}
                  </span>

                  <span style={styles.label}>受講終了日:</span>
                  <span style={styles.value}>
                    {courseDetail.status.courseCompletedAt ?? "-"}
                  </span>
                </div>
              </div>
            ))
          ) : (
            <p>受講中のコースはありません</p>
          )}
        </div>
      </div>
    </div>
  );
};
