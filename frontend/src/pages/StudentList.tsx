import { useState } from "react";
import { useStudents } from "../hooks/useStudents";
import { StudentsTable } from "../components/StudentsTable";
import { Header } from "../components/StudentListHeader";
import { StudentRegisterModal } from "../components/StudentRegisterModal";
import { StudentFilterModal } from "../components/StudentFilterModal";
import { StudentDetailModal } from "../components/StudentDetailModal"; // 追加
import { StudentUpdateModal } from "../components/StudentUpdateModal"; // 追加
import type { StudentResponse } from "../types/StudentResponse";

export const StudentList = () => {
  const {
    students,
    loading,
    fetchStudents,
    handleRegister,
    handleUpdate,
    handleDelete,
    handleSearch,
  } = useStudents();

  // モーダルのstate管理を全てここに集約
  const [showRegisterModal, setShowRegisterModal] = useState(false);
  const [showFilterModal, setShowFilterModal] = useState(false);
  const [selectedStudent, setSelectedStudent] =
    useState<StudentResponse | null>(null);
  const [editingStudent, setEditingStudent] = useState<StudentResponse | null>(
    null,
  );

  // 名前クリック → 詳細モーダル表示
  const handleNameClick = (student: StudentResponse) => {
    setSelectedStudent(student);
  };

  // 鉛筆クリック → 該当受講生をstudentsから探して更新モーダル表示
  const handleUpdateClick = (studentId: string) => {
    const student = students.find((s) => s.student.studentId === studentId);
    if (student) {
      setEditingStudent(student);
    }
  };

  if (loading) return <div>読み込み中...</div>;

  return (
    <div>
      <Header
        activeTab="students"
        onRegisterClick={() => setShowRegisterModal(true)}
        onFilterClick={() => setShowFilterModal(true)}
        onClearFilterClick={() => fetchStudents()}
      />
      <StudentsTable
        students={students}
        onNameClick={handleNameClick}
        onUpdateClick={handleUpdateClick}
        onDelete={handleDelete}
      />

      {/* 登録モーダル */}
      {showRegisterModal && (
        <StudentRegisterModal
          onClose={() => setShowRegisterModal(false)}
          onRegister={handleRegister}
        />
      )}

      {/* 検索モーダル */}
      {showFilterModal && (
        <StudentFilterModal
          onClose={() => setShowFilterModal(false)}
          onSearch={handleSearch}
        />
      )}

      {/* 詳細モーダル */}
      {selectedStudent && (
        <StudentDetailModal
          student={selectedStudent}
          onClose={() => setSelectedStudent(null)}
        />
      )}

      {/* 更新モーダル */}
      {editingStudent && (
        <StudentUpdateModal
          student={editingStudent}
          onClose={() => setEditingStudent(null)}
          onUpdate={(payload) =>
            handleUpdate(payload.student.studentId, payload)
          }
        />
      )}
    </div>
  );
};
