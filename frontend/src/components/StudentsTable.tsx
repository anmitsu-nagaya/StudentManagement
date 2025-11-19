import { useState } from "react";
import type { StudentResponse } from "../types/StudentResponce";
import * as FaIcons from "react-icons/fa";
//import { useNavigate } from "react-router-dom";
import { StudentDetailModal } from "./StudentDetailModal";
import {
  getFilterStudentList,
  getStudentList,
  updateStudent,
} from "../api/student";
import type { UpdateStudentFormValues } from "../types/UpdateStudentFormValues";
import { StudentUpdateModal } from "./StudentUpdateModal";

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
  onStudentsUpdate: (students: StudentResponse[]) => void;
};

export const StudentsTable = (props: StudentsTableProps) => {
  const { students, onStudentsUpdate } = props;
  //const navigate = useNavigate();
  const [selectedStudent, setSelectedStudent] =
    useState<StudentResponse | null>(null);

  const [editingStudent, setEditingStudent] = useState<StudentResponse | null>(
    null
  );

  const handleUpdateClick = async (studentId: string) => {
    try {
      const results = await getFilterStudentList({ studentId });
      if (results.length > 0) {
        setEditingStudent(results[0]);
      }
    } catch (err: unknown) {
      if (err instanceof Error) {
        alert(`データ取得に失敗しました: ${err.message}`);
      }
    }
  };

  const handleUpdate = async (payload: UpdateStudentFormValues) => {
    await updateStudent(payload);
    const updatedList = await getStudentList();
    onStudentsUpdate(updatedList);
  };

  const handleDeleteClick = async (studentId: string) => {
    if (!window.confirm("本当に削除しますか？")) {
      return;
    }

    try {
      const results = await getFilterStudentList({ studentId: studentId });

      if (results.length === 0) {
        alert("受講生が見つかりませんでした");
        return;
      }

      const studentDetail = results[0];

      const updatePayload: UpdateStudentFormValues = {
        student: {
          studentId: studentDetail.student.studentId,
          studentFullName: studentDetail.student.studentFullName,
          studentFurigana: studentDetail.student.studentFurigana,
          studentNickname: studentDetail.student.studentNickname,
          email: studentDetail.student.email,
          prefecture: studentDetail.student.prefecture,
          city: studentDetail.student.city,
          age: studentDetail.student.age,
          gender: studentDetail.student.gender,
          studentRemark: studentDetail.student.studentRemark,
          studentIsDeleted: true, // ← trueに設定
        },
        courseList: studentDetail.courseList.map((course) => ({
          statusId: course.status.statusId,
          courseId: course.status.courseId,
          status: course.status.status,
        })),
      };

      await updateStudent(updatePayload);

      alert("削除しました");

      // 一覧を再取得
      const updatedList = await getStudentList();
      onStudentsUpdate(updatedList);
    } catch (err: unknown) {
      if (err instanceof Error) {
        alert(`削除に失敗しました: ${err.message}`);
      } else {
        alert("削除に失敗しました");
      }
    }
  };

  const handleNameClick = (student: StudentResponse) => {
    setSelectedStudent(student);
  };

  const handleCloseDetailModal = () => {
    setSelectedStudent(null);
  };

  const handleCloseUpdateModal = () => {
    setEditingStudent(null);
  };

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
                    onClick={() => handleNameClick(studentDetail)}
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
                      handleUpdateClick(studentDetail.student.studentId)
                    }
                  >
                    <FaIcons.FaPen />
                  </button>
                  <button
                    style={styles.deleteButton}
                    onClick={() =>
                      handleDeleteClick(studentDetail.student.studentId)
                    }
                  >
                    <FaIcons.FaTrash />
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {selectedStudent && (
        <StudentDetailModal
          student={selectedStudent}
          onClose={handleCloseDetailModal}
        />
      )}

      {editingStudent && (
        <StudentUpdateModal
          student={editingStudent}
          onClose={handleCloseUpdateModal}
          onUpdate={handleUpdate}
        />
      )}
    </>
  );
};
