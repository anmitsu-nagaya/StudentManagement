import type { StudentResponse } from "../types/StudentResponse";
import * as FaIcons from "react-icons/fa";

const styles = {
  tableContainer: {
    width: "100%",
    backgroundColor: "#fff",
    padding: "20px",
  },
  table: {
    width: "100%",
    borderCollapse: "collapse" as const,
    backgroundColor: "#fff",
  },
  th: {
    backgroundColor: "#f5f5f5",
    padding: "12px 16px",
    textAlign: "left" as const,
    borderBottom: "2px solid #e0e0e0",
    fontSize: "14px",
    fontWeight: 600,
    color: "#666",
  },
  td: {
    padding: "12px 16px",
    borderBottom: "1px solid #f0f0f0",
    fontSize: "14px",
    color: "#333",
  },
  tr: {
    transition: "background-color 0.2s",
  },
  link: {
    color: "#2196f3",
    textDecoration: "none",
  },
  iconButton: {
    padding: "6px 10px",
    border: "1px solid #ddd",
    backgroundColor: "#fff",
    borderRadius: "4px",
    cursor: "pointer",
    display: "inline-flex",
    alignItems: "center",
    justifyContent: "center",
    fontSize: "14px",
    color: "#2196f3",
    marginRight: "8px",
  },
  deleteButton: {
    padding: "6px 10px",
    border: "1px solid #ddd",
    backgroundColor: "#fff",
    borderRadius: "4px",
    cursor: "pointer",
    display: "inline-flex",
    alignItems: "center",
    justifyContent: "center",
    fontSize: "14px",
    color: "#2196f3",
  },
};

type StudentsTableProps = {
  /**
   * StudentListから渡される受講生詳細データの一覧です。
   */
  students: StudentResponse[];
  /**
   * 名前ボタンの押下により、詳細表示対象の受講生情報を初期値として詳細表示モーダルを開く関数です。
   */
  onNameClick: (student: StudentResponse) => void;
  /**
   * 更新ボタンの押下により、更新対象の受講生情報を初期値として更新モーダルを開く関数です。
   */
  onUpdateClick: (studentId: string) => void;
  /**
   * 削除ボタンの押下により、論理削除処理を実行する関数です。
   */
  onDelete: (studentId: string) => Promise<void>;
};

/**
 * 受講生テーブルを管理するコンポーネントです。
 */
export const StudentsTable = (props: StudentsTableProps) => {
  const { students, onNameClick, onUpdateClick, onDelete } = props;
  return (
    <>
      <div style={styles.tableContainer}>
        <table style={styles.table}>
          <thead>
            <tr>
              <th style={styles.th}>名前</th>
              <th style={styles.th}>ふりがな</th>
              <th style={styles.th}>ニックネーム</th>
              <th style={styles.th}>メール</th>
              <th style={styles.th}>都道府県</th>
              <th style={styles.th}>市区町村</th>
              <th style={styles.th}>年齢</th>
              <th style={styles.th}>性別</th>
              <th style={styles.th}>備考</th>
              <th style={styles.th}>コース情報</th>
              <th style={styles.th}></th>
            </tr>
          </thead>
          <tbody>
            {students.map((studentDetail) => (
              <tr
                key={studentDetail.student.studentId}
                style={styles.tr}
                onMouseEnter={(e) =>
                  (e.currentTarget.style.backgroundColor = "#f8f9fa")
                }
                onMouseLeave={(e) =>
                  (e.currentTarget.style.backgroundColor = "transparent")
                }
              >
                <td style={styles.td}>
                  <button
                    onClick={() => onNameClick(studentDetail)}
                    style={{
                      ...styles.link,
                      background: "none",
                      border: "none",
                      cursor: "pointer",
                      padding: 0,
                    }}
                  >
                    {studentDetail.student.studentFullName}
                  </button>
                </td>
                <td style={styles.td}>
                  {studentDetail.student.studentFurigana}
                </td>
                <td style={styles.td}>
                  {studentDetail.student.studentNickname ?? "-"}
                </td>
                <td style={styles.td}>{studentDetail.student.email}</td>
                <td style={styles.td}>
                  {studentDetail.student.prefecture ?? "-"}
                </td>
                <td style={styles.td}>{studentDetail.student.city ?? "-"}</td>
                <td style={styles.td}>{studentDetail.student.age ?? "-"}</td>
                <td style={styles.td}>{studentDetail.student.gender ?? "-"}</td>
                <td style={styles.td}>
                  {studentDetail.student.studentRemark ?? "-"}
                </td>
                <td style={styles.td}>
                  {studentDetail.courseList.map((courseDetail) => (
                    <div key={courseDetail.course.courseId}>
                      {courseDetail.course.courseName} (
                      {courseDetail.status.status})
                    </div>
                  ))}
                </td>
                <td style={styles.td}>
                  <button
                    style={styles.iconButton}
                    onClick={() =>
                      onUpdateClick(studentDetail.student.studentId)
                    }
                  >
                    <FaIcons.FaPen />
                  </button>
                  <button
                    style={styles.deleteButton}
                    onClick={() => onDelete(studentDetail.student.studentId)}
                  >
                    <FaIcons.FaTrash />
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  );
};
