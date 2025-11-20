import { useEffect, useState } from "react";
import { StudentResponse } from "../types/StudentResponce";
import { getStudentList } from "../api/student";
import { Link } from "react-router-dom";

export const StudentList = () => {
  const [students, setStudens] = useState<StudentResponse[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchStudents = async () => {
      try {
        const data = await getStudentList();
        setStudens(data);
      } catch (err: any) {
        setError(err.message || "一覧取得に失敗しました");
      } finally {
        setLoading(false);
      }
    };

    fetchStudents();
  }, []);

  if (loading) return <div>読み込み中...</div>;
  if (error) return <div>エラー: {error}</div>;

  return (
    <div>
      <h1>学生一覧</h1>
      <table>
        <thead>
          <tr>
            <th>名前</th>
            <th>ふりがな</th>
            <th>ニックネーム</th>
            <th>メール</th>
            <th>都道府県</th>
            <th>市区町村</th>
            <th>年齢</th>
            <th>性別</th>
            <th>備考</th>
            <th>コース情報</th>
          </tr>
        </thead>
        <tbody>
          {students.map((studentDetail) => (
            <tr key={studentDetail.student.studentId}>
              <td>
                <Link to={`/students/${studentDetail.student.studentId}`}>
                  {studentDetail.student.studentFullName}
                </Link>
              </td>
              <td>{studentDetail.student.studentFurigana}</td>
              <td>{studentDetail.student.studentNickname ?? "-"}</td>
              <td>{studentDetail.student.email}</td>
              <td>{studentDetail.student.prefecture ?? "-"}</td>
              <td>{studentDetail.student.city ?? "-"}</td>
              <td>{studentDetail.student.age ?? "-"}</td>
              <td>{studentDetail.student.gender ?? "-"}</td>
              <td>{studentDetail.student.studentRemark ?? "-"}</td>
              <td>
                {studentDetail.courseList.map((courseDetail) => (
                  <div key={courseDetail.course.courseId}>
                    {courseDetail.course.courseName} (
                    {courseDetail.status.status})
                  </div>
                ))}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};
