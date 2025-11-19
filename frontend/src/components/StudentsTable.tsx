import { Link } from "react-router-dom";
import type { StudentResponse } from "../types/StudentResponce";
import * as FaIcons from "react-icons/fa";
import { useNavigate } from "react-router-dom";

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
  students: StudentResponse[];
};

export const StudentsTable = (props: StudentsTableProps) => {
  const { students } = props;
  const navigate = useNavigate();

  const handleUpdateClick = (studentId: string) => {
    console.log("編集ボタンがクリックされました");
    console.log(`遷移先: /students/${studentId}/edit`);
    navigate(`/students/${studentId}/edit`);
  };

  return (
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
                <Link
                  to={`/students/${studentDetail.student.studentId}`}
                  style={styles.link}
                >
                  {studentDetail.student.studentFullName}
                </Link>
              </td>
              <td style={styles.td}>{studentDetail.student.studentFurigana}</td>
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
                    handleUpdateClick(studentDetail.student.studentId)
                  }
                >
                  <FaIcons.FaPen />
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};
