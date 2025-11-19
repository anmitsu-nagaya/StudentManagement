import { useEffect, useState } from "react";
import type { StudentResponse } from "../types/StudentResponce";
import {
  getFilterStudentList,
  getStudentList,
  registerStudent,
} from "../api/student";
import { StudentsTable } from "../components/StudentsTable";
import { Header } from "../components/StudentListHeader";
import type { NewStudentFormValues } from "../types/NewStudentFormValues";
import { StudentRegisterModal } from "../components/StudentRegisterModal";
import { StudentFilterModal } from "../components/StudentFilterModal";

export const StudentList = () => {
  const [students, setStudens] = useState<StudentResponse[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [activeTab, setActiveTab] = useState<"students" | "courses">(
    "students"
  );
  const [showRegisterModal, setShowRegisterModal] = useState(false);
  const [showFilterModal, setShowFilterModal] = useState(false);

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

  const handleRegister = async (payload: NewStudentFormValues) => {
    await registerStudent(payload);
    const updatedList = await getStudentList();
    setStudens(updatedList);
  };

  const handleRegisterClick = () => {
    setShowRegisterModal(true);
  };

  const handleFilterClick = () => {
    setShowFilterModal(true);
  };

  const handleClearFilter = async () => {
    try {
      setLoading(true);
      const data = await getStudentList();
      setStudens(data);
    } catch (err: unknown) {
      if (err instanceof Error) {
        alert(`データ取得に失敗しました: ${err.message}`);
      } else {
        alert("データ取得に失敗しました");
      }
    } finally {
      setLoading(false);
    }
  };

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
    try {
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
      setStudens(results);
    } catch (err: unknown) {
      if (err instanceof Error) {
        alert(`検索に失敗しました: ${err.message}`);
      } else {
        alert("検索に失敗しました");
      }
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <div>読み込み中...</div>;
  if (error) return <div>エラー: {error}</div>;

  return (
    <div>
      <Header
        activeTab={activeTab}
        onTabChange={setActiveTab}
        onRegisterClick={handleRegisterClick}
        onFilterClick={handleFilterClick}
        onClearFilterClick={handleClearFilter}
      />
      <StudentsTable students={students} onStudentsUpdate={setStudens} />
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
