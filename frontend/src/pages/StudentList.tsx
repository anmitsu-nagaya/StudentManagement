import { useEffect, useState } from "react";
import type { StudentResponse } from "../types/StudentResponce";
import { getFilterStudentList, registerStudent } from "../api/student";
import { StudentsTable } from "../components/StudentsTable";
import { Header } from "../components/StudentListHeader";
import type { NewStudentFormValues } from "../types/NewStudentFormValues";
import { StudentRegisterModal } from "../components/StudentRegisterModal";
import { StudentFilterModal } from "../components/StudentFilterModal";

export const StudentList = () => {
  const [students, setStudents] = useState<StudentResponse[]>([]);
  const [loading, setLoading] = useState(true);
  const [activeTab, setActiveTab] = useState<"students" | "courses">(
    "students",
  );
  const [showRegisterModal, setShowRegisterModal] = useState(false);
  const [showFilterModal, setShowFilterModal] = useState(false);

  /**
   * 初回表示時に、APIのGET処理で論理削除されていない受講生一覧を取得し state に反映します。
   * 一覧画面では再取得を行わない設計のため、依存配列は空にしています。
   */
  useEffect(() => {
    const fetchStudents = async () => {
      const data = await getFilterStudentList({ studentIsDeleted: false });
      setStudents(data);
      setLoading(false);
    };
    fetchStudents();
  }, []);

  /**
   * 新規登録された受講生を反映するため、登録処理後に受講生一覧を再取得するAPI処理を行い、 state を更新します。
   * 一覧は論理削除されていない受講生のみを対象とします。
   * @param payload 新規受講生の入力値
   */
  const handleRegister = async (payload: NewStudentFormValues) => {
    await registerStudent(payload);
    const updatedList = await getFilterStudentList({ studentIsDeleted: false });
    setStudents(updatedList);
  };

  /**
   * 登録ボタン押下時に、新規登録用モーダルを表示します。
   * UI の表示切り替えのみを行い、データ操作は行いません。
   */
  const handleRegisterClick = () => {
    setShowRegisterModal(true);
  };

  /**
   * 編集ボタン押下時に、編集用モーダルを表示します。
   * UI の表示切り替えのみを行い、データ操作は行いません。
   */
  const handleFilterClick = () => {
    setShowFilterModal(true);
  };

  /**
   * フィルター解除ボタン押下時に、論理削除されていない受講生一覧を取得します。
   * UI の表示切り替えのみを行い、データ操作は行いません。
   */
  const handleClearFilterClick = async () => {
    const data = await getFilterStudentList({ studentIsDeleted: false });
    setStudents(data);
    setLoading(false);
  };

  /**
   * 検索モーダルで確定した検索条件を受け取り、API 経由で条件に一致する受講生一覧を取得して state を更新します。
   * @param filters 検索モーダルで入力・確定された検索条件
   */
  const handleSearch = async (filters: {
    studentFullName: string;
    studentFurigana: string;
    studentNickname: string;
    email: string;
    prefecture: string;
    city: string;
    ageFrom: string;
    ageTo: string;
    gender: string;
    courseName: string;
    status: string;
  }) => {
    setLoading(true);
    const params = {
      studentFullName: filters.studentFullName || undefined,
      studentFurigana: filters.studentFurigana || undefined,
      studentNickname: filters.studentNickname || undefined,
      email: filters.email || undefined,
      prefecture: filters.prefecture || undefined,
      city: filters.city || undefined,
      ageFrom: filters.ageFrom ? parseInt(filters.ageFrom) : undefined,
      ageTo: filters.ageTo ? parseInt(filters.ageTo) : undefined,
      gender: filters.gender || undefined,
      courseName: filters.courseName || undefined,
      status: filters.status || undefined,
    };
    const results = await getFilterStudentList(params);
    setStudents(results);
    setLoading(false);
  };

  if (loading) return <div>読み込み中...</div>;

  return (
    <div>
      <Header
        activeTab={activeTab}
        onTabChange={setActiveTab}
        onRegisterClick={handleRegisterClick}
        onFilterClick={handleFilterClick}
        onClearFilterClick={handleClearFilterClick}
      />
      <StudentsTable students={students} onStudentsUpdate={setStudents} />
      {showRegisterModal && (
        <StudentRegisterModal
          onClose={() => setShowRegisterModal(false)}
          onRegister={handleRegister}
        />
      )}

      {showFilterModal && (
        <StudentFilterModal
          onClose={() => setShowFilterModal(false)}
          onSearch={handleSearch}
        />
      )}
    </div>
  );
};
