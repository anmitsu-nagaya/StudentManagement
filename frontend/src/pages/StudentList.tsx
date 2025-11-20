import { useEffect, useState } from "react";
import type { StudentResponse } from "../types/StudentResponce";
import { getStudentList } from "../api/student";
import { StudentsTable } from "../components/StudentsTable";
import * as FaIcons from "react-icons/fa";

export const StudentList = () => {
  const [students, setStudens] = useState<StudentResponse[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchStudents = async () => {
      try {
        const data = await getStudentList();
        setStudens(data);
      } catch (err: unknown) {
        if (err instanceof Error) {
          setError(err.message);
        } else {
          setError("一覧取得に失敗しました");
        }
      } finally {
        setLoading(false);
      }
    };
    fetchStudents();
  }, []);

  if (loading) return <div>読み込み中...</div>;
  if (error) return <div>エラー: {error}</div>;

  // const handleRegister = () => {
  //   // ここに新規登録画面へ遷移する処理やモーダルを開く処理を書く
  //   console.log("新規登録ボタンが押されました");
  // };

  // const handleUpdate = () => {
  //   // 選択した学生を更新する処理を書く
  //   console.log("更新ボタンが押されました");
  // };

  return (
    <div>
      <div className="header-container">
        <div className="tabs">
          <button className="tab active">受講生一覧</button>
          <button className="tab">コース一覧</button>
        </div>
        <div className="header-actions">
          <button
            className="icon-button"
            onClick={() => console.log("新規登録")}
          >
            <FaIcons.FaPlus />
          </button>
          <button className="icon-button" onClick={() => console.log("検索")}>
            <FaIcons.FaFilter />
          </button>
        </div>
      </div>
      <StudentsTable students={students} />
    </div>
  );
};
