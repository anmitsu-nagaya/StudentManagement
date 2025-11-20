import { Link } from "react-router-dom";
import type { StudentResponse } from "../types/StudentResponce";

const style = {
  //backgroundColor: "#c6e5d9",
  //borderCollapse: "collapse",
  width: "100%",
  minHeight: "500px",
  padding: "20px",
  margin: "20px",
  borderRadius: "5px",
};

type StudentsTableProps = {
  students: StudentResponse[];
};

export const StudentsTable = (props: StudentsTableProps) => {
  const { students } = props;

  return (
    <table style={style}>
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
                  {courseDetail.course.courseName} ({courseDetail.status.status}
                  )
                </div>
              ))}
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};
