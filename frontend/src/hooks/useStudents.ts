import { useState, useEffect } from "react";
import {
  filterStudentApi,
  registerStudentApi,
  updateStudentApi,
} from "../api/student";
import type { StudentResponse } from "../types/StudentResponse";
import type { NewStudentFormValues } from "../types/NewStudentFormValues";
import type { UpdateStudentFormValues } from "../types/UpdateStudentFormValues";

type FilterParams = {
  studentFullName?: string;
  studentFurigana?: string;
  studentNickname?: string;
  email?: string;
  prefecture?: string;
  city?: string;
  ageFrom?: number;
  ageTo?: number;
  gender?: string;
  courseName?: string;
  status?: string;
};

/**
 * 受講生に関するstateとAPI処理をまとめたカスタムフックです。
 *
 * @returns students - 受講生詳細データの一覧
 * @returns loading - データ取得中かどうかを示すフラグ
 * @returns fetchStudents - 受講生一覧を取得する関数（条件指定可）
 * @returns handleRegister - 新規受講生を登録する関数
 * @returns handleUpdate - 受講生情報を更新する関数
 * @returns handleDelete - 受講生を論理削除する関数
 */
export const useStudents = () => {
  const [students, setStudents] = useState<StudentResponse[]>([]);
  const [loading, setLoading] = useState(true);

  /**
   * 一覧取得APIを実行してstateを更新します。
   * @param filters 指定された条件
   */
  const fetchStudents = async (filters?: FilterParams) => {
    const data = await filterStudentApi({
      ...filters,
      studentIsDeleted: false,
    });
    setStudents(data);
  };

  /**
   * studentsが空配列のときにデータ取得中の状態を示すフラグです。
   */
  useEffect(() => {
    const load = async () => {
      await fetchStudents();
      setLoading(false);
    };
    load();
  }, []);

  /**
   * 新規登録APIを実行して一覧を再取得します。
   * @param payload 登録モーダルに入力されたデータ
   */
  const handleRegister = async (payload: NewStudentFormValues) => {
    await registerStudentApi(payload);
    await fetchStudents();
  };

  /**
   * 更新APIを実行して一覧を再取得します。
   * @param studentId 更新の対象の受講生ID
   * @param payload 更新モーダルに入力されたデータ
   */
  const handleUpdate = async (
    studentId: string,
    payload: UpdateStudentFormValues,
  ) => {
    await updateStudentApi(studentId, payload);
    await fetchStudents();
  };

  /**
   * 更新APIを実行して論理削除を行い、一覧を再取得します。
   * @param studentId 論理削除対象の受講生ID
   */
  const handleDelete = async (studentId: string) => {
    if (!window.confirm("本当に削除しますか？")) return;

    const studentDetail = students.find(
      (s) => s.student.studentId === studentId,
    );
    if (!studentDetail) {
      alert("受講生が見つかりませんでした");
      return;
    }

    const deletePayload: UpdateStudentFormValues = {
      student: {
        ...studentDetail.student,
        studentIsDeleted: true,
      },
      courseList: studentDetail.courseList.map((course) => ({
        status: {
          statusId: course.status.statusId,
          courseId: course.status.courseId,
          status: course.status.status,
        },
      })),
    };

    try {
      await updateStudentApi(studentId, deletePayload);
      alert("削除しました");
      await fetchStudents();
    } catch (err: unknown) {
      if (err instanceof Error) {
        alert(`削除に失敗しました: ${err.message}`);
      } else {
        alert("削除に失敗しました");
      }
    }
  };

  /**
   * 指定した条件を引数にして一覧取得APIを実行します。
   * @param filters 検索条件
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
    await fetchStudents({
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
    });
  };

  return {
    students,
    loading,
    fetchStudents,
    handleRegister,
    handleUpdate,
    handleDelete,
    handleSearch,
  };
};
