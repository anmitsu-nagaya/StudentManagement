import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { StudentResponse } from "../types/StudentResponce";
import { getFilterStudentList } from "../api/student";

export const StudentDetail = () => {
  const { id } = useParams<{ id: string }>();
  const [student, setStudent] = useState<StudentResponse | null>(null);

  useEffect(() => {
    if (id) {
      getFilterStudentList({ studentId: id }).then((results) => {
        if (results.length > 0) setStudent(results[0]);
      });
    }
  }, [id]);

  if (!student) return <div>読み込み中...</div>;

  return (
    <div>
      <h1>{student.student.studentFullName} の詳細</h1>
      {/* 他の詳細情報をここに表示 */}
    </div>
  );
};
