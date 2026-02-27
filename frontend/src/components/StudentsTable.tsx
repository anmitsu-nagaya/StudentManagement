import { useState } from "react";
import type { StudentResponse } from "../types/StudentResponse";
import * as FaIcons from "react-icons/fa";
//import { useNavigate } from "react-router-dom";
import { StudentDetailModal } from "./StudentDetailModal";
import { getFilterStudentList, updateStudent } from "../api/student";
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
  /**
   * APIから受け取る受講生詳細データ型
   */
  students: StudentResponse[];
  /**
   * 更新された受講生詳細データを渡すコールバック関数
   */
  onStudentsUpdate: (students: StudentResponse[]) => void;
};

/**
 * 受講生詳細リストテーブルの表示・動作を管理するコンポーネント
 */
export const StudentsTable = (props: StudentsTableProps) => {
  const { students, onStudentsUpdate } = props;
  const [selectedStudent, setSelectedStudent] =
    useState<StudentResponse | null>(null);

  const [editingStudent, setEditingStudent] = useState<StudentResponse | null>(
    null,
  );
  /**
   * 更新したい受講生の編集ボタン押下時に、押下された受講生に関する受講生詳細データをAPIから受け取りstateを更新する
   */
  const handleUpdateClick = async (studentId: string) => {
    const results = await getFilterStudentList({ studentId });
    if (results.length > 0) {
      setEditingStudent(results[0]);
    }
  };

  /**
   * 編集モーダルにて更新ボタン押下時に更新情報を更新し、論理削除されていない受講生を再取得するAPI操作を行った結果の受講生詳細データでstateを更新する
   */
  const handleUpdate = async (payload: UpdateStudentFormValues) => {
    await updateStudent(payload.student.studentId, payload);
    const updatedList = await getFilterStudentList({ studentIsDeleted: false });
    onStudentsUpdate(updatedList);
  };

  /**
   * 受講生詳細リストにおいて削除ボタン押下時に、論理削除フラグをtrueに更新して一覧を再取得するAPI処理を行った結果の受講生詳細データでsstateを更新する
   */
  const handleDeleteClick = async (studentId: string) => {
    if (!window.confirm("本当に削除しますか？")) {
      return;
    }

    try {
      const studentDetail = students.find(
        (s) => s.student.studentId === studentId,
      );

      if (!studentDetail) {
        alert("受講生が見つかりませんでした");
        return;
      }

      const courses = studentDetail.courseList;

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
        courseList: courses.map((course) => ({
          status: {
            statusId: course.status.statusId,
            courseId: course.status.courseId,
            status: course.status.status,
          },
        })),
      };

      await updateStudent(updatePayload.student.studentId, updatePayload);

      alert("削除しました");

      // 一覧を再取得
      const updatedList = await getFilterStudentList({
        studentIsDeleted: false,
      });
      onStudentsUpdate(updatedList);
    } catch (err: unknown) {
      if (err instanceof Error) {
        alert(`削除に失敗しました: ${err.message}`);
      } else {
        alert("削除に失敗しました");
      }
    }
  };

  /**
   * 一覧の受講生の名前を押下した際の該当の受講生に関する受講生詳細データでstateを更新する
   */
  const handleNameClick = (student: StudentResponse) => {
    setSelectedStudent(student);
  };

  /**
   * 受講生詳細モーダルの閉じるボタンを押下した際にstateを更新する
   */
  const handleCloseDetailModal = () => {
    setSelectedStudent(null);
  };

  /**
   * 受講生更新モーダルの閉じるボタンを押下した際にstateを更新する
   */
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
