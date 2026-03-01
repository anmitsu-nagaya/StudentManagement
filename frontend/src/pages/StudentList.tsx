import { useState } from "react";
import { useStudents } from "../hooks/useStudents";
import { StudentsTable } from "../components/StudentsTable";
import { Header } from "../components/StudentListHeader";
import { StudentRegisterModal } from "../components/StudentRegisterModal";
import { StudentFilterModal } from "../components/StudentFilterModal";
import { StudentDetailModal } from "../components/StudentDetailModal";
import { StudentUpdateModal } from "../components/StudentUpdateModal";
import type { StudentResponse } from "../types/StudentResponse";

export const StudentList = () => {
  /**
   * 受講生に関するstateとAPI処理をまとめたカスタムフックです。
   * students・loadingはstate、それ以外はAPI処理を行う関数です。
   */
  const {
    students, // 受講生詳細データの一覧（state）。レンダリング時に一覧取得APIが実行されます。
    loading, // データ取得中かどうかを示すフラグです。
    fetchStudents, // 受講生一覧を取得する関数です。条件指定が可能です。
    handleRegister, // 新規受講生を登録する関数です。
    handleUpdate, // 受講生情報を更新する関数です。
    handleDelete, // 受講生を論理削除する関数です。
    handleSearch, // 条件検索を行う関数です。
  } = useStudents();

  /**
   * モーダルのstateを管理します
   */
  const [showRegisterModal, setShowRegisterModal] = useState(false);
  const [showFilterModal, setShowFilterModal] = useState(false);
  const [showDetailModal, setshowDetailModal] =
    useState<StudentResponse | null>(null);
  const [showUpdateModal, setshowUpdateModal] =
    useState<StudentResponse | null>(null);

  /**
   * 名前を押下した時、詳細モーダルを表示します。
   * @param student
   */
  const handleNameClick = (student: StudentResponse) => {
    setshowDetailModal(student);
  };

  /**
   * 鉛筆ボタンを押下した時、該当受講生をstudentsから探して更新モーダルに表示します。
   * @param studentId 押下された受講生の受講生ID
   */
  const handleUpdateClick = (studentId: string) => {
    const student = students.find((s) => s.student.studentId === studentId);
    if (student) {
      setshowUpdateModal(student);
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
      {showDetailModal && (
        <StudentDetailModal
          student={showDetailModal}
          onClose={() => setshowDetailModal(null)}
        />
      )}

      {/* 更新モーダル */}
      {showUpdateModal && (
        <StudentUpdateModal
          student={showUpdateModal}
          onClose={() => setshowUpdateModal(null)}
          onUpdate={(payload) =>
            handleUpdate(payload.student.studentId, payload)
          }
        />
      )}
    </div>
  );
};
